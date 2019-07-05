<template>
    <div class="plan">
        <div class="planContent">
            <div v-for="(val,key) in plans"
                 :key="key"
                 style="width: 100%;height: 100%"
            >
                <div class="plan-title">
                    <img :src="planContent.planTitleImg">
                    <h3>{{ planContent.processName }}</h3>
                    <!--<h4>进行中</h4>-->
                </div>
                <div class="plan-left">
                    <div class="dashed"></div>
                </div>
                <div class="node"
                     v-for="(item,index) in plans[key].nodeList"
                     :key="index"
                     :style="{ top: ( plans[key].nodeList.length ? ( index + 1 ) * initTop : 0 ) + '%' }"
                >
                    <div class="check" :class="[item.statusVal == 1 ? 'check-completed' : 'check-ongoing']"><span v-if="item.statusVal == 2"></span></div>
                    <div class="tag-boder">
                        <div class="tag"></div>
                    </div>
                    <div class="nodeDetail" >
                        <div class="node-detail-content">
                            <strong :title="item.stepName" @click="(item.stepName === planPersonnelConfirm && item.statusVal === 2) && ( model.show.state = true )">{{ item.stepName }}</strong>
                            <span style="float: right">{{ item.statusStr }}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <personnel-confirm v-bind="model">
            <template slot="body">
               <p style="color: #fff">是否确认？</p>
            </template>
            <template slot="footer">
                <Button type="primary" size="small" @click.native="confirm" style="float: right;margin-right: 5%;margin-bottom: 1%;background:#199FB0 ">确认</Button>
                <Button type="primary" size="small" @click.native="cancel" style="float: right;margin-right: 5%;margin-bottom: 1%;background:#199FB0 ">取消</Button>
            </template>
        </personnel-confirm>
    </div>
</template>

<script>
    import Vue from 'vue'
    import eventBus from '../../../assets/Bus'
    import { PlanService } from "../../../services/planService";
    import personnelConfirm from '../AlarmManage/DescAlarmModel'

    const PLAN_STATE_COMPLETE = 1; // 完成
    const PLAN_STATE_ONGOING = 2; // 进行中
    const PLAN_PERSONNEL_CONFIRM = '值班人员确认';
    const PLAN_OPEN_VIDEOS = '调用摄像头';
    const PLAN_TYPE = {
        FIRE_PLAN: '消防预案'
    };

    export default {
        name: "plan",
        data(){
            return {
                initTop : 8,
                plans:[],
                plan:
                    {
                        processName:"消防预案",
                        planStatus:[
                            {stepName: "开启声光报警", statusStr: "待进行", statusVal: 3},
                            {stepName: PLAN_OPEN_VIDEOS, statusStr: "待进行", statusVal: 3},
                            {stepName: "值班人员确认", statusStr: "待进行", statusVal: 3},
                            {stepName: "打开风机", statusStr: "待进行", statusVal: 3},
                            {stepName: "打开风阀", statusStr: "待进行", statusVal: 3},
                            {stepName: "打开百叶", statusStr: "待进行", statusVal: 3},
                            {stepName: "启动干粉灭火", statusStr: "待进行", statusVal: 3},
                            {stepName: "通知相关单位", statusStr: "待进行", statusVal: 3},
                        ],
                    },
                nodeListStatus:[],
                model:{
                    show:{
                        state:false
                    },
                    tilteProps:{
                        tilte:'值班人员确认', //对话框标题
                    },
                },
                confirmMessage:{
                    nodeListProcessId : null,
                    processInstanceId : null,
                },
                planContent:{
                    processName : null,
                    planTitleImg : ''
                }
            }
        },
        watch:{
            'plans':{
                handler([ processMessage ]){
                    let { planContent } = this;
                    let { processName } = processMessage;

                    planContent.processName =  processName ;

                },
                deep:true
            },
            'planContent.processName'( processName ){
                let { planContent } = this;
                let  titleImg = processName === PLAN_TYPE.FIRE_PLAN ? 'fire_plan' : 'fire_plan_t';

                planContent.planTitleImg =  require(`../../../assets/VM/${ titleImg }.png`);
            }
        },
        computed:{
            planPersonnelConfirm(){
                return PLAN_PERSONNEL_CONFIRM;
            }
        },
        mounted(){
            this.init()
        },
        components:{
            personnelConfirm
        },
        methods:{
            init(){
                Vue.prototype.MQ._InitMQ(1, "/queue/QUEUE_PLAN_VM", "",this.planCallback);
                this.getNodeListStatus();
            },
            planCallback(message){
                let result = JSON.parse(message.body);

                if( result && !this.searchPlan(result) ){
                    let { processInstanceId,processName,nodeList } = result;

                    this.$emit('showPlan',true);

                    this.plans.splice(0);
                    this.plans.push({
                        processInstanceId,
                        processName,
                        nodeList
                    });

                    this.$nextTick( () => this.updatePlan( result ) );

                    eventBus.$emit('getPlanPosition',{
                        planPosition : {
                            X: result.longitude,
                            Y: result.latitude
                        },
                        range:result.range,
                        processInstanceId: processInstanceId });
                }else{

                    this.updatePlan( result );
                }
            },
            searchPlan(result){

                if( !this.plans.length ) return false;

                return this.judgePlan(result) &&  true ;
            },
            updatePlan(result){
                let planStatus = this.judgePlan(result);

                try{
                    result.process.forEach( planProcess => {
                        planStatus.forEach(plan => {
                            if(planProcess.node === plan.stepName && planProcess.status !== plan.statusVal){

                                this.modefyNodeListStatus(plan,planProcess); //修改节点状态

                                if ( planProcess.status === PLAN_STATE_ONGOING ){
                                        this.openVideos(planProcess,result);  //打开相机
                                        this.personnelConfirm(planProcess,result);  //人员确认
                                }
                            }
                        })
                    })
                }catch (err){
                    console.warn('err',err);
                }


            },
            judgePlan(result){
                for( let i=0 ;i<this.plans.length;i++ ){
                    if( this.plans[i].processInstanceId === result.processInstanceId ) return this.plans[i].nodeList;
                }
            },
            openVideos({ node,id },{ processInstanceId }){
                if( node !== PLAN_OPEN_VIDEOS ) return false;

                this.$store.commit('addPlanLinkageVideos',{ id,processInstanceId })
            },
            personnelConfirm({ node,id },{ processInstanceId }){
                if( node !== PLAN_PERSONNEL_CONFIRM ) return false;

                let { model,confirmMessage } = this;

                model.show.state = true;
                confirmMessage.nodeListProcessId = id;
                confirmMessage.processInstanceId = processInstanceId;

            },
            getNodeListStatus(){
                this.nodeListStatus.splice(0);

                PlanService.getNodeListStatus().then(data => this.nodeListStatus.push( ...data ) );
            },
            modefyNodeListStatus(node,processNode){
                node.statusVal = processNode.status;

                this.nodeListStatus.forEach( nodeList => nodeList.val === processNode.status && ( node.statusStr = nodeList.key ));
            },
            confirm(){
                let { model,confirmMessage } = this;

                PlanService.ConfirmStep(confirmMessage.processInstanceId,confirmMessage.nodeListProcessId,'yes' );
                model.show.state = false;
            },
            cancel(){
                let { model } = this;

                model.show.state = false;
            },
        },
        beforeDestroy(){
            Vue.prototype.MQ.closeMQ();
        }

    }
</script>

<style scoped>
    .planContent {
        position: absolute;
        top: 15.5%;
        left: 2%;
        z-index: 1000;
        width: 20%;
        height: 80%;
        padding: .5%;
        border: .3rem solid transparent;
        background: -webkit-gradient(linear,left top, left bottom,from(rgba(0,180,220,0.3)),color-stop(rgba(0,180,220,0.1)),color-stop(rgba(0,180,220,0.1)),to(rgba(0,180,220,0.3)));
        background: linear-gradient(180deg,rgba(0,180,220,0.3),rgba(0,180,220,0.1),rgba(0,180,220,0.1),rgba(0,180,220,0.3));
        border-image: url("../../../assets/VM/border.png") 1%;
        -webkit-box-shadow: 0 0 2rem rgba(0,180,220,.8) inset;
        box-shadow: 0 0 2rem rgba(0,180,220,.8) inset;
    }
    .planContent .node {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        width: 90%;
        z-index: 1000;
        position: absolute;
        top: 10%;
    }
    .planContent .check {
        border-radius: 50px;
        position: relative;
        -ms-flex-negative: 0;
        flex-shrink: 0;
        background-color: rgb(125, 122, 117);
        border: 1px solid rgb(173,168,163);
    }
    .check-ongoing {
        background: url("../../../assets/VM/plan-state-close.png") no-repeat;
        background-size: cover;
    }
    .check-completed {
        background: url("../../../assets/VM/plan-state-open.png") no-repeat;
        background-size: cover;
    }
    .planContent .plan-left {
        -ms-flex-negative: 0;
        flex-shrink: 0;
    }
    .planContent .plan-title {
        color: #fff;
        margin-bottom: 2%;
    }
    .plan-title > img {
        width: 12.5%;
        height: 100%;
        float: left;
    }
    .plan-title > h3 {
        float: left;
        margin-left: 1%;
    }
    .plan-left .dashed {
        width: 0px;
        height: 100%;
        margin-left: 50%;
        border-left-style: dashed;
        border-left-color: darkgray;
        border-left-width: 2px;
    }
    .tag-boder {
        width: 0;
        height: 0;
        border-style: dashed;
        border-color: transparent #2096A3 transparent transparent;
        position: relative;
    }
    .tag {
        width: 0;
        height: 0;
        border: .6rem solid;
        border-style: dashed;
        border-color: transparent #2096A3 transparent transparent;
        position: absolute;
        left: -8px;
        top: -10px;
    }
   .planContent .nodeDetail {
       -webkit-box-flex: 1;
       -ms-flex-positive: 1;
       flex-grow: 1;
       border-radius: 3px;
       padding: .2rem  .2rem  .2rem .3rem;
       display: -webkit-box;
       display: -ms-flexbox;
       display: flex;
       -webkit-box-orient: vertical;
       -webkit-box-direction: normal;
       -ms-flex-direction: column;
       flex-direction: column;
       -webkit-box-shadow: 1px 1px 3px gainsboro;
       margin-left: -1px;
       box-shadow: 0 0 2rem rgba(0,180,220,1) inset;
       background: #2096A3;
       color: #fff;
       border: 1px solid #2096A3;
   }
    .check > span {
        background: red;
        display: block;
        border-radius: 50%;
        position: relative;
    }
    .node-detail-content > strong{
        width: 64%;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        cursor: pointer;
    }
    .node-detail-content > strong:hover {
        color: #BBBBBB;
    }
    @-webkit-keyframes rotating {
        from {
            transform: rotate(0deg);
        }
        to{
            transform: rotate(360deg);
        }
    }
    @keyframes rotating {
        from {
            transform: rotate(0deg);
        }
        to{
            transform: rotate(360deg);
        }
    }
    /* 小屏幕（显示器，小于等于 1920px） */
@media (max-width: 1920px) {
    .planContent .plan-title {
        height: 1.6rem;
        font-size: 1rem;
    }
    .planContent .plan-left {
        width: 1.8rem;
        height: calc(100% - 2em - 1em);
    }
    .planContent .check {
        width: 1.8rem;
        height: 1.8rem;
    }
    .check > span {
        width: .5rem;
        height: .5rem;
        top: -0.25rem;
        left: 0.25rem;
        -webkit-transform-origin: 0.25rem 1rem;
        transform-origin: 0.5rem 1.05rem;
        animation: rotating 2s infinite linear;
    }
    .tag-boder {
        border: 0.6rem solid;
        border-style: dashed;
        border-color: transparent #2096A3 transparent transparent;
    }
    .tag {
        border: .6rem solid;
        border-style: dashed;
        border-color: transparent #2096A3 transparent transparent;
        left: -8px;
        top: -10px;
    }
    .planContent .nodeDetail {
       border-radius: 3px;
       padding: .2rem  .2rem  .2rem .3rem;
    }
}
    /* 大屏幕（显示器，大于等于 1920px） */
@media (min-width: 1921px) {
    .planContent .plan-title {
        height: 3.6rem;
        font-size: 2.2rem;
    }
    .planContent .plan-left {
        width: 4rem;
        height: calc(100% - 2em - 3em);
    }
    .planContent .check {
        width: 4rem;
        height: 4rem;
    }
    .check > span {
        width: 1.5rem;
        height: 1.5rem;
        top: -0.75rem;
        left: 0.75rem;
        -webkit-transform-origin: 0.25rem 1rem;
        transform-origin: 1.5rem 2.6rem;
        animation: rotating 2s infinite linear;
    }
    .tag-boder {
        border: 1rem solid;
        border-style: dashed;
        border-color: transparent #2096A3 transparent transparent;
    }
    .planContent .nodeDetail {
       border-radius: 1rem;
       padding: 0.55rem  1rem  0.3rem 1rem;
       font-size: 1.5rem;
    }
    .tag {
        border: 1rem solid;
        border-style: dashed;
        border-color: transparent #2096A3 transparent transparent;
        left: -16px;
        top: -16px;
    }
}
</style>