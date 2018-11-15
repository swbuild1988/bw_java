<template>
    <div class="allDiv">
        <Row class="queryCondition">
            <Row>
            <Col span="6">
                <span class="conditionTitle">缺陷类型：</span>
                <Select v-model="conditions.type" style="width: 60%">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in defectType" :value="item.val" :key="item.value">{{ item.key }}</Option>
                </Select>
            </Col>
            <Col span="6">    
                <span class="conditionTitle">缺陷状态：</span>
                <Select v-model="conditions.status" style="width: 60%">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in defectStatus" :value="item.val" :key="item.value">{{ item.key }}</Option>
                </Select>
            </Col>
            <Col span="6">    
                <span class="conditionTitle">危险等级：</span>
                <Select v-model="conditions.level" style="width: 60%">
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in defectLevel" :value="item.val" :key="item.value">{{ item.key }}</Option>
                </Select>
            </Col>
            </Row>
            <Row>
            <Col span="6">
                <span class="conditionTitle">开始时间：</span>
                <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary" icon="ios-search" @click="queryCondition()">查询</Button>
            </Col>
            </Row>
        </Row>
        <div class="list">
            <Row>
                <Table :columns='columns' :data="defects"></Table>
            </Row>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        </div>
    </div>
</template>

<script>
import { TunnelService } from '../../../../services/tunnelService'
import { DefectService } from '../../../../services/defectService'
import { EnumsService } from '../../../../services/enumsService'    
import Enum from '../../../../../static/Enum.json'
export default {
    name:"queryDefect",
    data() {
        return {
            columns:[
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },         
                {
                    title:"所属区段",
                    align: 'center',
                    render: (h,params) =>{
                        let temp = params.row.area.name
                        if(temp!=undefined){
                            return h('span',params.row.area.name)
                        }
                    }
                },
                {
                    title:"所属管仓",
                    align: 'center',
                    render: (h,params) => {
                        let temp = params.row.store.name
                        if(temp!=undefined){
                            return h('span',params.row.store.name)
                        }
                    }
                },
                {
                    title: '缺陷名称',
                    align: 'center',
                    key: 'name'
                },
                {
                    title:"发现时间",
                    key:"createTime",
                    width: 200,
                    align: 'center',
                    render: (h, params) => {
                        return h(
                        "div",
                        new Date(params.row.createTime).format("yyyy-MM-dd hh:mm:s")
                        );
                    }
                },
                {
                    title:"缺陷类型",
                    key:"typeName",
                    align: 'center'
                },
                {
                    title:"对象名",
                    key:"objName",
                    align: 'center',
                    render: (h,params) =>{
                        let temp = ''
                        if(params.row.type==2){
                            temp = params.row.objName
                        }else if(params.row.type==1){
                            temp = '无'
                        }
                        return h('div',temp)
                    }
                },
                {
                    title:"危险等级",
                    key:"levelName",
                    align: 'center',
                    render: (h,params) => {
                        return h(
                            'div',
                            {
                                style:{
                                    color:
                                       params.row.level==0 ? 'green'
                                       :  params.row.level==1 ? 'blue'
                                       :  params.row.level==2 ? 'orange'
                                       :  'red' 
                                    
                                }
                            },params.row.levelName
                        )
                    }
                },
                {
                    title:"缺陷状态",
                    key:"statusName",
                    align: 'center'
                },
                {
                    title:"操作",
                    key:"action",
                    align: 'center',
                    width:300,
                    align:'center',
                    render:(h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.enterOrder(params.index)
                                    }
                                }
                            }, '查看'),
                            // h('Button',{
                            //     props: {
                            //         type: 'success',
                            //         size: 'small'
                            //     },
                            //     style: {
                            //         marginRight: '5px'
                            //     },
                            //     on: {
                            //         click: ()=> {
                            //             this.edit(params.index)
                            //         }
                            //     }
                            // },'编辑'),
                             h('Button',{
                                props: {
                                    type: 'info',
                                    size: 'small'
                                },
                                on: {
                                    click: ()=> {
                                        this.queryDetails(params.index)
                                    }
                                }
                            },'查看维修工单')
                        ]);
                    }
                }
            ],
            defects:[
                // {id:1,tunnelName:'凤岭北路',createTime:1529596800000,typeName:'电力故障',objName:'凤岭北路电力故障',levelName:'二级',statusName:'未解决'}
            ],
            tunnels: [],
            defectType: [],
            defectLevel: [],
            defectStatus: [],
            conditions:{
                tunnelId: null,
                type: null,
                level: null,
                status: null,
                startTime: null,
                endTime: null,
            },
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
            },
            page:{
                pageTotal: null,
                pageSize: 10,
                pageNum: 1
            }
        }
    },
    watch:{
        '$route': function () {
          //2. $route发生变化时再次赋值planId
          this.tunnelId = this.$route.params.id;
          this.tunnels.forEach(a => {
            if (a.id == this.tunnelId) {
              this.queryCondition();
            }
          });
        },
    },
    computed: {
        params() {
        let param = {
            pageNum: this.page.pageNum,
            pageSize: this.page.pageSize,
            startTime: this.conditions.startTime,
            endTime: this.conditions.endTime,
            type: this.conditions.type,
            level: this.conditions.level,
            status: this.conditions.status,
            tunnelId: this.$route.params.id  
        };
        return Object.assign({}, param);
        }
    },
    mounted(){
        let _this = this
        //从数据库读取select的option选项
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //缺陷类型
        EnumsService.getDefectType().then(
            (result)=>{
                _this.defectType = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //缺陷状态
        EnumsService.getDefectStatus().then(
            (result)=>{
                _this.defectStatus = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //危险等级
        EnumsService.getDefectLevel().then(
            (result)=>{
                _this.defectLevel = result
            },
            (error)=>{
                _this.Log.info(error)
            })

        this.queryCondition()
    },
    methods: {
        queryCondition(){
            let _this = this 
            DefectService.defectsDatagrid(this.params).then(
                (result)=>{
                    _this.defects=result.list
                    _this.page.pageTotal = result.total
                },
                (error)=>{
                    _this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryCondition()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryCondition()
        },
        goToModule(index,type){
            this.$router.push({
                name: 'DefectDetailsOverhaul',
                params: {
                    id: this.defects[index].id,
                    type: type
                }
            })
        },
        goToMoudle2: function(index, type) {
            this.$router.push({
                name: "UMDetailDefect",
                params: {
                    id: this.defects[index].id,
                    type: type
                }
            });
        },
        enterOrder(index){
            this.goToMoudle2(index, Enum.pageType.Read);
        },
        queryDetails(index){
            this.goToModule(index,Enum.pageType.Read)
        }
    }  
}
</script>


