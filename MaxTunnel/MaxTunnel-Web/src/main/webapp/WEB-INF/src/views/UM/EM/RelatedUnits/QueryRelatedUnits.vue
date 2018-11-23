<template>
    <div class="allDiv">
        <Row class="queryCondition">
            <Col span="6">
                <span>所属管仓段：</span>
                <Input type="text" icon="arrow-down-b" style="width: 60%" :value="sectionName" @click="showTree"></Input>                   
                <Tree class="tree"  :data="treeList" show-checkbox ref="tree" @on-check-change="choiceAll" v-show="isShow"></Tree>
            </Col>
            <Col span="6">
                <span>单位名称：</span>
                <Input type="text" v-model="conditions.name" id="getW"  style="width:60%"></Input>
            </Col>
            <Col span="6">
                <span>联系人：</span>
                <Input type="text" v-model="conditions.contact"  style="width:60%"></Input>
            </Col>
            <Col span="6">
                <span>单位类别：</span>
                <Select v-model="conditions.unitType"  style="width:60%">
                    <Option value=null>所有</Option>
                    <Option v-for="(item,index) in unitType" :value="item.val" :key="index">{{ item.key }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="word025">开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间"  style="width: 60%" v-model="conditions.startTime"></DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间"  style="width: 60%" v-model="conditions.endTime"></DatePicker>
            </Col>
            <Col span="6" offset="6">
                <Button type="primary" icon="ios-search" @click="queryUnits()">查询</Button>
            </Col>
        </Row> 
        <div class="list">   
            <!-- <Row type="flex"   align="top" class="code-row-bg">
                <Col span="6"  v-for="(item,index) in unitInfo" :key='index'>
                    <div class="unitBox">
                        <div class="title">
                            <span>{{item.name}}&nbsp;/</span>
                            <span class="unitType">{{item.unitTypeName}}</span>
                        </div>
                        <div class="address">
                            <Icon type="ios-location" size=15></Icon>
                            {{item.address}}
                        </div>
                        <div class="contact">
                            <div class="contactName">
                                <span><Icon type="android-person" size=15></Icon></span>
                                <span>{{item.contact}}</span>
                            </div>
                            <div class="contactTel">
                                <span><Icon type="ios-telephone" size=15></Icon></span>
                                <span>{{item.tel}}</span>
                            </div>
                            <div class="sections">
                                <div class="sectionTitle">管仓区段：</div>
                                <!-- <span v-for="(item,index) in sections" :key="index">{{item.name}}</span> -->
                                <!--<div class="sectionName">{{item.sectionIds}}</div>
                            </div>    
                        </div>
                        <div class="option">
                            <Button size=small type="primary" @click="edit(index)">编辑</Button>
                            <Button size=small type="error" @click="del(index)">删除</Button>
                            <div class="crtTime">
                                <span><Icon type="android-time"></Icon>{{item.crtTime}}</span>
                            </div>
                        </div>    
                    </div>
                </Col>
            </Row>  -->
            <waterfall 
                :line-gap="200" 
                :watch="unitInfo"
                :align="align"
                :min-line-gap="100"
                :max-line-gap="220"
                :single-max-width="300"
                @reflowed="reflowed"
                ref="waterfall"
            >
                <waterfall-slot
                    v-for="(item,index) in unitInfo"
                    :width="item.width"
                    :height="item.height"
                    :order="index"
                    :key="item.id"
                    move-class="item-move"
                >
                
                
                
                    <div class="unitBox">
                        <div class="title">
                            <span>{{item.name}}&nbsp;/</span>
                            <span class="unitType">{{item.unitTypeName}}</span>
                        </div>
                        <div class="address">
                            <Icon type="ios-location" size=15></Icon>
                            {{item.address}}
                        </div>
                        <div class="contact">
                            <div class="contactName">
                                <span><Icon type="android-person" size=15></Icon></span>
                                <span>{{item.contact}}</span>
                            </div>
                            <div class="contactTel">
                                <span><Icon type="ios-telephone" size=15></Icon></span>
                                <span>{{item.tel}}</span>
                            </div>
                            <div class="sections">
                                <div class="sectionTitle">管仓区段1111：</div>
                                <!-- <span v-for="(item,index) in sections" :key="index">{{item.name}}</span> -->
                                <div class="sectionName">{{item.sectionIds}}</div>
                            </div>    
                        </div>
                        <div class="option">
                            <Button size=small type="primary" @click="edit(index)">编辑</Button>
                            <Button size=small type="error" @click="del(index)">删除</Button>
                            <div class="crtTime">
                                <span><Icon type="android-time"></Icon>{{item.crtTime}}</span>
                            </div>
                        </div>
                    </div>
                
                </waterfall-slot>
            </waterfall>
        </div>            
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle"></Page>   
    </div>    
</template>
<script>
import axios from "axios";
import { TunnelService } from '../../../../services/tunnelService'
import { EnumsService } from '../../../../services/enumsService'
import { RelatedUnitService } from '../../../../services/relatedUnitService'
import Enum from "../../../../../static/Enum.json";
// import oneTree from '../../../../components/UM/EM/oneTree'
//瀑布流
// import Waterfall from '../../../../../node_modules/vue-waterfall/lib/waterfall'
// import WaterfallSlot from '../../../../../node_modules/vue-waterfall/lib/waterfall-slot'
import Waterfall from 'vue-waterfall/lib/waterfall'
import WaterfallSlot from 'vue-waterfall/lib/waterfall-slot'

export default {
    components:{
        // oneTree
        Waterfall,
        WaterfallSlot
    },
    data(){
        return{
            pageSizeComputed: null,
            unitInfo:[],
            conditions:{
                name: null,
                contact: null,
                unitType: null,
                startTime: null,
                endTime: null,
            },
            page:{
                pageNum: 1,
                pageSize: 8,
                pageTotal: null,
            },
            tunnelId: null,
            unitType:[],
            sections:[],
            treeList:[],
            sectionIds: null,
            sectionName: null,
            isShow: false,
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
            },
            align: 'center',
            unitInfo: ItemFactory.get(100),
            isBusy: false
        }
    },
    watch: {
        $route: function() {
            this.tunnelId = this.$route.params.id;
            this.queryUnits()
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.name,
                contact: this.conditions.contact,
                unitType: this.conditions.unitType,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime(),
                sectionIds : this.sectionIds
            };  
        return Object.assign({}, param);
        }
    },
    created() {
        let body = document.querySelector('body')
        body.addEventListener('click',(e)=>{
            if(
                e.target.className=='ivu-checkbox-input' ||
                e.target.className=='ivu-icon ivu-icon-arrow-down-b ivu-input-icon ivu-input-icon-normal' ||
                e.target.className=='ivu-icon ivu-icon-arrow-right-b' ||
                e.target.className=='ivu-tree-children' ||
                e.target.className=='ivu-tree-arrow ivu-tree-arrow-open' ||        
                e.target.className=='ivu-tree-title' ||
                e.target.className=='tree ivu-tree' ||
                e.target.className=='ivu-tree-arrow' ||
                e.target.nodeName=='UL' ||
                e.target.nodeName=='LI'
            ){
                this.isShow=true
            }else{
                this.isShow=false;
            }
        })
    },
    mounted(){
        let _this = this
        TunnelService.getTunnelsTree().then(
            function(result){
                result.forEach(element => {
                    var firstChildren = []
                    let temp={}
                    temp.title = element.name
                    temp.id = element.id
                    if(element.list.length!=0){
                        element.list.forEach(a=>{
                            var secondChildren = []
                            let obj = {}
                            obj.title = a.name
                            obj.id = a.id
                            obj.disabled = true
                            if(a.list.length!=0){
                                obj.disabled = false
                                 a.list.forEach(b=>{
                                    let secondInfo = {}
                                    secondInfo.title = b.name
                                    secondInfo.id = b.id
                                    secondChildren.push(secondInfo)
                                    obj.children = secondChildren
                                })
                            }
                            firstChildren.push(obj)                        
                            temp.children = firstChildren
                        })
                    }
                    _this.treeList.push(temp)                   
                });
            },
            function(error){
                console.log(error)
        })
        EnumsService.getUnitType().then(
            function(result){
                _this.unitType = result
            },
            function(error){
                console.log(error)
        })
        this.queryUnits()
    },  
    methods:{
        queryUnits(){
            let _this = this
            RelatedUnitService.relatedUnitDataGrid(_this.params).then(
                function(result){
                    _this.page.pageTotal = result.total;
                    _this.unitInfo = result.list;
                    for(let index in _this.unitInfo){
                        _this.unitInfo[index].crtTime = new Date(_this.unitInfo[index].crtTime).format('yyyy-MM-dd hh:mm:s')
                        var str = ''
                        for (let k in _this.unitInfo[index].sectionList){
                            if(_this.unitInfo[index].sectionList.length==1){
                                _this.unitInfo[index].sectionIds=_this.unitInfo[index].sectionList[k].name                               
                            }else{
                                str += _this.unitInfo[index].sectionList[k].name+','
                                _this.unitInfo[index].sectionIds = str.substring(0,str.length-1)
                            }
                        }
                    }
                },function(error){
                    console.log(error)
            })
        },
        del(index){
            this.$Modal.confirm({
                title: '相关单位',
                content: '<p>是否删除这条相关单位信息</p>',
                onOk: () => {
                    let _this = this
                    RelatedUnitService.deleteRelatedUnit(_this.unitInfo[index].id).then(
                        function(result){
                            _this.unitInfo.splice(_this.unitInfo[index].id, 1);
                            _this.queryUnits();
                        },
                        function(error){
                            console.log(error)
                        }
                    )
                },
                onCancel: () => {}
            })
        },
        goToMoudle: function(index, type) {
            this.$router.push({
                name: "UMAddRelatedUnits",
                params: {
                    id: this.unitInfo[index].id,
                    type: type
                }
            });
        },
        edit(index) {
            this.goToMoudle(index, Enum.pageType.Edit);
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryUnits();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryUnits();
        },
        choiceAll(){
            let arr = this.$refs.tree.getCheckedNodes()
            var str = ''
            var strIds = ''
            this.sectionName = null
            if(arr.length==0){
                this.sectionIds = null
            }else{
                for(let index in arr){
                    str+=arr[index].title+','
                    this.sectionName = str.substring(0,str.length-1)
                    strIds += arr[index].id+','
                    this.sectionIds = strIds.substring(0,strIds.length-1)
                }
            }
            this.queryUnits()
        },
        showTree(){
            this.isShow = true
        },
        addItems: function(){
            if(!this.isBusy && this.unitInfo.length<500){
                this.isBusy = true
                this.unitInfo.push.apply(this.unitInfo,ItemFactory.get(50))
            }
        },
        shuffle: function(){
            this.unitInfo.sort(function(){
                return Math.random() - 0.5
            })
        },
        reflowed: function(){
            this.isBusy = false
        }
    }
}
</script>
<style scoped>
    .unitBox{
        border: 1px solid#dddfe1;
        width: 85%;
        margin: 10px auto;
        padding: 5px 0px;
        border-radius: 4px;
        box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
    }
    .title{
        font-size: 20PX;
        color: #697480;
        padding: 0px 10px;
    }
    .address{
        color: #88929A;
        padding: 0px 10px;
    }
    .contact{
        background: #f5f5f5;
        line-height: 35px;
        margin: 10px 0px;
        padding: 0px 10px;
    }
    .contactName,.contactTel{
        display: inline-block;
    }
    .contactName{
        width: 41%;
    }
    .crtTime{
        float: right;
        font-size: 12px;
    }
    .option{
        padding: 0px 4px;
    }
    .ivu-icon {
        margin-right: 5px;
        color: #ff9b00;
    }
    .sectionTitle{
        float: left;
    } 
    .sectionName{
        margin-left: 77px;
    }
    .ivu-dropdown-item >>> .ivu-select-dropdown{
        margin-left: 14px;
    }
    .word025{   
        letter-spacing: 0.25em;
        margin-right: -0.25em;
    }
    .tree{
        background: #fff;
        z-index: 2;
        position: absolute;
        margin-left: 75px;
        border: 1px solid #cccccc;
    }
</style>