<template>
    <div>
        <Row style="marginLeft:25px;marginBottom:10px;margin-top:20px">
            <Col span="6">
                <span>监测对象名称</span><span>：</span>
                <Input v-model="researchInfo.name" placeholder="请输入监测对象名称" class="inputWidth"/>
            </Col>
            <Col span="6">
                <span>监测对象ID</span><span>：</span>
                <Input v-model="researchInfo.id" placeholder="请输入监测对象ID" class="inputWidth"/>
            </Col>
            <Col span="6">
                <span>监测对象类型</span><span>：</span>
                <Select v-model="researchInfo.objtypeIds" placeholder="请选择监测对象类型" class="inputWidth" multiple id="objtype">
                    <Option v-for="item in lists.objtypes" :value="item.val" :key="item.key">{{item.key}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <Button type="primary" size="small"  icon="ios-search" @click="search">查询</Button>
            </Col> 
        </Row>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span class="word64">所属管廊</span><span>：</span>
                <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in lists.tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </Col>
  <!--           <Col span="6">
                <Poptip placement="bottom" width="1000">
                    <span class="word65">所属管仓</span><span>：</span>
                    <Input v-model="researchInfo.storeName" placeholder="请选择所属管仓" id="store" style="width: 60%"/>
                    <div class="pop" slot="content">
                        <store-choose v-on:listenToStoreChoose="getStore" v-bind:tunnelId="researchInfo.tunnelId"></store-choose>
                    </div>
                </Poptip>
            </Col>
            <Col span="6">
                <Poptip placement="bottom" width="1000">
                    <span class="word64">所属区域</span><span>：</span>
                    <Input v-model="researchInfo.areaName" placeholder="请选择所属区域" id="area" style="width: 60%"/>
                    <div class="pop" slot="content">
                        <area-choose v-on:listenToAreaChoose="getArea" v-bind:tunnelId="researchInfo.tunnelId"></area-choose>
                    </div>
                </Poptip>
            </Col> -->
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="data6"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
            @on-change="handlePage" show-elevator class="pageStyle"></Page>
        </div>
    </div>
</template>

<script>
import { EnumsService } from '../../../services/enumsService'
import { TunnelService } from '../../../services/tunnelService'
import { MeasObjServer } from '../../../services/MeasObjectSerivers'
import storeChoose from '../../../components/CM/MAM/storeChoose'
import areaChoose from '../../../components/CM/MAM/areaChoose'
export default {
    name: "meas-obj",
    data(){
        return {
            lists: {
                objtypes: [],
                tunnels: [],
                areas: []
            },   
            researchInfo:{
                name: null,
                id: null,
                objtypeIds: null,
                datatypeId: null,
                tunnelId: null,
                storeName: null,
                storeId: null,
                areaId: null,
                areaName: null
            },
            columns7:[
                {
                    title: '选择',
                    width: 80,
                    align: 'center',
                    render: (h,params) => {
                        return h("div",[
                            h("Checkbox",{
                                props: {
                                    value: params.row.checkBox
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.data6[params.index].checkBox = val
                                        this.sendMsg()
                                    }
                                }
                            })
                        ])
                    }
                },
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '监测对象名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '监测对象ID',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: '监测对象类型',
                    key: 'objtypeName',
                    align: 'center'
                },
                // {
                //     title: '位置',
                //     key: 'location',
                //     align: 'center'
                // }
            ],
            data6:[],
            page:{
                pageNum: 1,
                pageSize: 4,
                pageTotal: 0
            },
            deleteShow: false,
            deleteSelect:[]
        }
    },
    props: {
        targetValue: {
            required: false
        },
        isShow: {
            required: false
        }
    },
    computed:{
        researches(){
            if(this.researchInfo.objtypeIds && this.researchInfo.objtypeIds.length == 0){
                this.researchInfo.objtypeIds = null
            }
            if(this.researchInfo.name || this.researchInfo.id || this.researchInfo.objtypeIds || this.researchInfo.datatypeId || this.researchInfo.storeId || this.researchInfo.areaId){
                this.page.pageNum = 1
            }
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                id: this.researchInfo.id,
                objtypeIds: this.researchInfo.objtypeIds,
                datatypeId: this.researchInfo.datatypeId,
                tunnelId: this.researchInfo.tunnelId,
                storeId: this.researchInfo.storeId,
                areaId: this.researchInfo.areaId
            };
            return Object.assign({}, param);
        }
    },
    watch:{
        'targetValue': function(newValue,oldValue){
            this.data6.forEach((obj,index)=>{
                obj.checkBox = false
                if(this.isShow && newValue.indexOf(obj.id+'') > -1){
                    obj.checkBox = true
                }
                this.data6[index].datatypeName = this.data6[index].datatypeName + ' '
            })
            this.sendMsg()
        }
    },
    mounted(){
        this.init()
        this.search()
        // setTimeout(function(){
        //     let width = document.getElementById('objtype').offsetWidth
        //     document.getElementById('store').style.width = width + 'px'
        //     document.getElementById('area').style.width = width + 'px'
        // },300)
    },
    methods:{
        search(){
            let _this = this
            MeasObjServer.measObjDataGrid(this.researches).then(
                result=>{
                    _this.data6 = []
                    _this.data6 = result.list;
                    _this.data6.forEach(measobj=>{
                        measobj.actived = measobj.actived ? '是' : '否'
                        // measobj.location = measobj.section.store.tunnel.name + measobj.section.name
                        measobj.checkBox = false
                    })
                    _this.page.pageTotal = result.total
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        init(){    
            let _this = this 
            EnumsService.getObjType().then(
                result=>{
                    _this.lists.objtypes = result
                },
                error=>{
                    _this.Log.info(error)
                })
            TunnelService.getTunnels().then(
                result=>{
                    _this.lists.tunnels = result
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.search();
        },
        getStore(store){
            this.researchInfo.storeId = store.id;
            this.researchInfo.storeName = store.name;
        },
        getArea(area){
            this.researchInfo.areaId = area.id;
            this.researchInfo.areaName = area.name;
        },
        sendMsg(){
            let objs = {
                ids: [],
                names: []
            }
            this.data6.forEach(obj=>{
                if(obj.checkBox){
                    objs.ids.push(obj.id)
                    objs.names.push(obj.name)
                }
            })
            this.$emit("objsChoose",objs)
        }
    },
    components:{
        storeChoose,
        areaChoose
    }
}
</script>

<style scoped>
.inputWidth{
    width: 60%;
}
.pageStyle{
    text-align: right;
    margin-top: 20px;
    margin-right: 10px;
}
.word63{
    letter-spacing: 1.5em;
    margin-right: -1.5em
}
.word64{
    letter-spacing: 0.67em;
    margin-right: -0.67em;
}
.word65{
    letter-spacing: 0.25em;
    margin-right: -0.25em;
}
</style>


