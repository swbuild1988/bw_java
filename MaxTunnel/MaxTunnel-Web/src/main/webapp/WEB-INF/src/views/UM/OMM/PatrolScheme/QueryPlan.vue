<template>
    <div class="allDiv">
        <div class="conditions">
        <Row>
            <Col span="4">
                <span class="conditionTitle">流程状态：</span>
                <Select v-model="conditions.finished" style="width:60%">
                    <Option value=null>所有</Option>
                    <Option v-for="item in workOrderStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
            </Col>
            <Col span="4">
                <span class="conditionTitle">责任班组：</span>
                <Select v-model="conditions.groupId" style="width:60%">
                    <Option value="">所有</Option>
                    <Option v-for="item in Groups" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="4">
                <span class="conditionTitle">开始时间：</span>
                <DatePicker type="datetime" v-model="conditions.startTime" placeholder="请输入开始时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="4">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker type="datetime" v-model="conditions.endTime" placeholder="请输入结束时间" style="width: 60%"></DatePicker>
            </Col>
            <Col span="4">
                <Button type="primary" icon="ios-search" @click="conditionChange()">查询</Button>
            </Col>
        </Row>
        </div>
        <div class="list">
            <!-- <Scroll :on-reach-bottom="handleReachBottom"  height="777px;"> -->
            <div class="nullData" v-show="isNullData">暂无数据</div>
            <Row class="detailsList code-row-bg" type="flex"  align="top">
                <Col span="6" dis-hover v-for="(item,index) in plans" :key="index" ref="planBox">
                    <div class="everyList">
                        <div class="detailsInfo">
                            <div class="details">
                                <div class="details-top">{{item.name}}</div>
                                <Row style="margin-bottom: 1vmin;">
                                    <Col span="7" offset="5">
                                        <p class="planStatus">流程状态</p>
                                        <p class="planStatusDec">{{item.processStatus}}</p>
                                    </Col>
                                    <Col span="12">
                                        <p class="patrolGroup">责任班组</p>
                                        <p class="patrolGroupName">{{item.groupName}}</p>
                                    </Col>
                                    <Col span="19" offset="5">
                                        <Col span="6">
                                            <div class="detailsBtn showBtn" @click="show(index)">详情</div>
                                        </Col>
                                        <Col span="6">
                                            <div class="detailsBtn delBtn" @click="delPlan(item.planId,item.processStatus)">删除</div>
                                        </Col>
                                        <Col span="12">
                                            <div class="createTime">{{item.createTime}}</div>
                                        </Col>
                                    </Col>
                                </Row>
                            </div>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
        <div class="page">
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
            :page-size-opts=[12,24,36] placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        </div>
    </div>
</template>
<script>
import Enum from "../../../../../static/Enum.json";
import { TunnelService } from '../../../../services/tunnelService';
import { PatrolService } from '../../../../services/patrolService';

export default {
    data() {
        return {
            plans: [
                {
                    // id: 1,
                    // tunnelName: '凤岭北路',
                    // name: '张三',
                    // status: '未',
                    // groupName: 'group1'
                }
            ],
            tunnelId: 1,
            tunnels: [],
            Groups: [],
            workOrderStatus: Enum.patrolPlanOrderStatus,
            conditions: {
                tunnelId: null,
                finished: null,
                groupId: null,
                startTime: null,
                endTime: null
            },
            page: {
                pageNum: 1,
                pageSize: 12,
                pageTotal: 0
            },
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
            },
            isNullData:false
        };
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                tunnelId: this.$route.params.id,
                groupId: this.conditions.groupId,
                finished: this.conditions.finished,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime
            };
            return Object.assign({}, param);
        }
    },
    watch:{
        '$route': function () {
            //2. $route发生变化时再次赋值planId
            this.tunnelId = this.$route.params.id;
            this.tunnels.forEach(a => {
                if (a.id == this.tunnelId) {
                    this.conditionChange();
                }
            });
        },
    },
    mounted() {
        let _this = this
        // 从数据库读取select的option选项
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result;
            },
            (error)=>{
                _this.Log.info(error)
            })
        //从数据库中读取所有的小组option选项
        PatrolService.getGroups().then(
            (result)=>{
                _this.Groups = result;
            },
            (error)=>{
                _this.Log.info(error)
            })
        this.conditionChange();
    },
    methods: {
        conditionChange: function() {
            let _this = this
            if(new Date(_this.conditions.startTime)>new Date(_this.conditions.endTime)){
                _this.$Message.error('开始时间必须小于结束时间！');
                return;
            }
            PatrolService.patrolPlanDatagrid(_this.params).then(
                (result)=>{
                    if(result.list.length==0){
                        this.isNullData = true
                    }else{
                        this.isNullData = false
                    }
                    for (let index in result.list) {
                        result.list[index].createTime = new Date(result.list[index].createTime).format("yyyy-MM-dd");
                    }
                    _this.plans = result.list;
                    _this.page.pageTotal = result.total;
                },
                (error)=>{
                    _this.Log.info(error)
                }
            )
        },
        goToMoudle: function(path) {
            this.$router.push(path);
        },
        // type 1:查看， 2：编辑
        goToMoudle2: function(index, type) {
            this.$router.push({
                name: "查看巡检计划详情",
                params: {
                    id: this.plans[index].planId,
                    type: type
                }
            });
        },
        // type 编辑
        goToMoudle3: function(index, type) {
            this.$router.push({
                name: "编辑巡检计划",
                params: {
                    id: this.plans[index].planId,
                    type: type
                }
            });
        },
        show(index) {
            this.goToMoudle2(index, Enum.pageType.Read);
        },
        delPlan(id,processStatus){
            if(processStatus!='审批'){
                this.$Message.warning({
                    content: `该巡检计划已进入“`+processStatus+`”流程阶段，不可删除。<br>巡检计划只有在“审批”流程阶段的时候才可以删除`,
                    duration: 5
                }) 
            }else{
                this.$Modal.confirm({
                    title: '巡检计划',
                    width: '24vw',
                    content: '<p>如果删除这条巡检计划，对应的巡检任务也会被删除</p>',
                    onOk: () => {
                        PatrolService.patrolTaskDel(id).then(
                            resolve => {
                                this.$Message.success("删除成功！")
                                this.conditionChange()
                            },
                            reject => { 
                                this.$Message.error("删除失败！")
                            }
                        )
                    },
                    onCancel: () => {
                    }
                });
            }
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.conditionChange();

        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.conditionChange();
        },
    }
};
</script>
<style scoped>
.planDec {
    margin-right: 5px;
}
.detailsList .everyList{
    width: 82%;
    margin-bottom: 20px;
    background: url("../../../../assets/UM/cardBG.png") no-repeat;
    background-size: 100% 100%;
}
.detailsInfo{
    height: 80%;
}
.icon{
    background-color: #009DFF;
    width: 30px;
    height: 30px;
    border-radius: 100%;
    vertical-align: top;
    margin-top: 15px;
    margin-left: 5px;
    margin-right: 2px;
}
.ivu-icon{
    margin-left: 5px;
    margin-top: 1px;
}
.details{
    padding-top: 1vmin;
}
.details-top{
    font-size: 3.5vmin;
    font-weight: 700;
    text-align: center;
    color: #fff;
}
.planStatus,.patrolGroup,.planStatusDec,.patrolGroupName{
    color:#fff;
    font-size: 1.4vmin;
    line-height: 4.5vmin;
}
.page >>> .ivu-select-selection{
    height: 3.2vmin;
}
.page >>> .ivu-select-selected-value{
    font-size: 1.2vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.page >>> .ivu-select-placeholder{
    font-size: 1.2vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-options-elevator input{
    font-size: 1.2vmin;
    height: 3vmin;
}
.page >>> .ivu-page-options-elevator{
    display: inline-block;
    height: 3.2vmin;
    line-height: 3.2vmin;
}
.page >>> .ivu-page-next{
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-next .ivu-icon{
    font-size: 1.6vmin;
}
.page >>> .ivu-page-prev{
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-prev .ivu-icon{
    font-size: 1.6vmin;
}

.ivu-page>>>.ivu-page-total, .ivu-page>>>.ivu-page-options-elevator{
    color: #fff;
}

 /*日期选择*/
.conditions >>> .ivu-date-picker-header {
    height: 3.2vmin;
    line-height: 3.2vmin;
}

.conditions >>> .ivu-picker-panel-icon-btn{
    font-size: 1.66vmin;
    width: 1.28vmin;
    height: 2.5vmin;
}

.conditions >>> .ivu-date-picker-header-label{
    font-size: 1.66vmin;
}

.detailsBtn{
    color: #fff;
    border-radius: 0.5vmin;
    line-height: 3.5vmin;
    text-align: center;
    width: 5vmin;
    cursor: pointer;
}
.showBtn{
    background: linear-gradient(to top right, #2734e1, #b195ed)
}
.delBtn{
    background: linear-gradient(to top right, #f61a1a, #fa8785)
}
.createTime{
    color: #fff;
}


@media (min-width: 1921px){
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .conditions >>> .ivu-date-picker-cells {
        width: 15vmin;
        font-size: 1.66vmin;
    }

    .conditions >>> .ivu-date-picker-cells-cell{
        width: 2vmin;
    }
    .conditions >>> .ivu-date-picker-cells-header span{
        padding-right: 2.5rem;
    }
}
</style>


