<template>
    <div>
        <h1 style="text-align:center;margin-bottom:25px;margin-top:10px;">管廊管理</h1>
        <!-- 查找条件 -->
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>管廊名称：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </div>
            </Col>
            <Col span="6">
            <div>
                <span style="marginRight:12px">负责人：</span>
                <Select v-model="researchInfo.responsibilityId" placeholder="请选择负责人" class="inputWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="(item,index) in staffs" :value="item.id" :key="index">{{item.name}}</Option>         
                </Select>
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>建筑单位：</span>
                <Select v-model="researchInfo.constructId" placeholder="请选择建筑单位" class="inputWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="(item,index) in companies" :value="item.id" :key="index">{{item.name}}</Option>
                </Select>
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>运营单位：</span>
                <Select v-model="researchInfo.operationId" placeholder="请选择运营单位" class="inputWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="(item,index) in companies" :value="item.id" :key="index">{{item.name}}</Option>
                </Select>
            </div>
            </Col>
        </Row>
        <Row align="middle" style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="researchInfo.startTime">
                </DatePicker>
                </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="researchInfo.endTime">
                </DatePicker>
            </Col>
            <Col span="10">
                    <Button type="primary" size="small"  icon="ios-search" @click="resetPageSearch()">查询</Button>
                    <!-- <Button type="error" size="small" @click="addNewPipe()">新增管廊</Button>  -->
                    <Button type="info" size="small" @click="autoPoint()">自动生成起点/终点</Button>
                    <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                    <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>    
        </Row>
        <div style="margin: 20px;">
            <Table border ref="selection" :columns="tunnelColumns" :data="tunnelsData" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
                    @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        </div>
        <!-- 添加管廊 -->
        <!-- <div>
            <pipe-module v-bind="addPipeInfo" v-on:listenToAdd="savePipe"></pipe-module>
        </div> -->
        <!-- 修改管廊信息 -->
        <div>
            <pipe-modification ref="pipeModule" v-bind="changePipeInfo" v-on:sendMsgtoManage="saveChangeTunnelInfo"></pipe-modification>
        </div>
    </div>
</template>

<script>
import {PipeService} from '@/services/pipeService'
import PipeModification from "../../CM/Tunnel/PipeModification.vue";
export default {
    name: "pipe-manage",
    data() {
        return {
            tunnelColumns: [
                {
                    type: "selection",
                    width: 60,
                    align: "center"
                },
                {
                    type: "index",
                    width: 60,
                    align: "center"
                },
                {
                    title: "管廊名",
                    key: "name",
                    align: "center"
                },
                {
                    title: "编号",
                    key: "sn",
                    align: "center"
                },
                {
                    title: "管廊长度",
                    key: "length",
                    align: "center"
                },
                {
                    title: '管廊方向',
                    key: 'direction',
                    align: 'center',
                    render: (h,params) => {
                        let tunnelDirection = [
                            { id: 1, direction: '东北方向', value: 1 },
                            { id: 2, direction: '西南方向', value: 2 },
                            { id: 3, direction: '西北方向', value: 3 },
                            { id: 4, direction: '东南方向', value: 4 }
                        ]
                        let temp = null
                        tunnelDirection.forEach(element => {
                            if(params.row.direction==element.value){
                                temp = element.direction
                            }
                        }); 
                        return h('div', temp)      
                    }
                },
                {
                    title: "负责人",
                    key: "responsibility",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.responsibility.name)
                    }
                },
                {
                    title: "建筑单位",
                    key: "construct",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.construct.name)
                    }
                },
                {
                    title: "运营单位",
                    key: "operation",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.operation.name)
                    }
                },
                {
                    title: "MaxView终端",
                    key: "maxviewConfig",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.maxviewConfig.name)
                    }
                },
                {
                    title: '起点（经度、纬度、高度）',
                    key: 'startPoint',
                    align: 'center'
                },
                {
                    title: '终点（经度、纬度、高度）',
                    key: 'endPoint',
                    align: 'center'
                },             
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    width: 80,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.changeBarn(params.row.id);
                                        }
                                    }
                                },
                                "修改"
                            )
                        ]);
                    }
                }
            ],
            tunnelsData: [],
            staffs: [],
            companies: [],
            researchInfo: {
                name: null,
                startTime: null,
                endTime: null,
                responsibilityId: null,
                constructId: null,
                operationId: null
            },
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px"
            },
            changePipeInfo: {
                show: { state: false }
            },
            deleteShow: false,
            deleteSelect: [],
            tunnelDirection: [
                { id: 1, direction: '东北方向', pic: require('@/assets/CM/en.png') , value: 1 },
                { id: 2, direction: '西南方向', pic: require('@/assets/CM/ws.png') , value: 2 },
                { id: 3, direction: '西北方向', pic: require('@/assets/CM/wn.png') , value: 3 },
                { id: 4, direction: '东南方向', pic: require('@/assets/CM/es.png') , value: 4 }
            ]
        };
    },
    mounted() {
        this.resetPageSearch();
        this.getStaffs();
        this.getCompanies();
    },
    computed: {
        researches() {
            // 查询
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                responsibilityId: this.researchInfo.responsibilityId,
                constructId: this.researchInfo.constructId,
                operationId: this.researchInfo.operationId,
                startTime: this.researchInfo.startTime,
                endTime: this.researchInfo.endTime
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        showTable() {
            PipeService.queryTunnels(this.researches).then(
                result => {
                    this.tunnelsData = result.list
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.resetPageSearch();
        },
        // 获取负责人
        getStaffs() {
            var param = {
                outside: 1
            }
            PipeService.getStaffs(param).then(
                result => {
                    this.staffs = result;
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //获取建筑公司、运营单位
        getCompanies() {
            PipeService.getCompanies().then(
                result => {
                    this.companies = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //修改开始
        changeBarn(id) {
            this.changePipeInfo.show.state = true;
            this.$refs.pipeModule.getTunnelInfo(id)
        },
        //修改结束
        saveChangeTunnelInfo(){
            this.changePipeInfo.show.state = false;
            this.resetPageSearch()
            this.$Message.success("修改成功！")
        },
        handleSelectionChange(val) {
            this.deleteSelect = val;
        },
        startdelete(selection) {
            if (selection.length != 0) {
                this.deleteShow = true;
                this.deleteSelect = selection;
            } else {
                this.deleteShow = false;
            }
        },
        alldelete() {
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除选中的信息吗？</p>",
                onOk: () => {
                    let ids = this.deleteSelect[0].id;
                    for (let i = 1; i < this.deleteSelect.length; i++) {
                        ids += "," + this.deleteSelect[i].id;
                    }
                    PipeService.delTunnels(ids).then(
                        result => {
                            this.$Message.info("已删除");
                            this.deleteShow = false;
                            this.resetPageSearch();
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                    this.resetPageSearch();
                }
            });
        },
        resetPageSearch(){
            this.page.pageNum = 1;
            this.showTable();
        },
        //自动生成起点和终点
        autoPoint(){
            if(this.deleteSelect.length>0){
                PipeService.autoPoint(this.deleteSelect[0].id).then(
                    result => {
                            this.$Message.success("成功生成起点和终点")
                    },
                    error => {
                        if(this.deleteSelect.length==0){
                            this.$Message.error("请选择管廊")
                        }else if(this.deleteSelect.length>1&&this.deleteSelect[0].startPoint!=null&&this.deleteSelect[0].endPoint!=null){
                            this.$Message.error("一次只能选择一条管廊")
                        }else if((this.deleteSelect.length==1&&this.deleteSelect[0].startPoint==null)||(this.deleteSelect.length==1&&this.deleteSelect[0].endPoint==null)){
                            this.$Message.error("管廊的起点和终点不能为空")
                        }else if((this.deleteSelect.length>1&&this.deleteSelect[0].startPoint==null)||(this.deleteSelect.length>1&&this.deleteSelect[0].endPoint==null)){
                            this.$Message.error("一次只能选择一条管廊,且管廊的起点和终点不能为空")
                        }
                    }
                )
            }else{
                this.$Message.error("请选择所要自动生成起点和终点的管廊")
            }
        }
    },
    components: {
        // PipeModule,
        PipeModification
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
</style>