<template>
    <div :style="backStyle">
        <Form :model="task" :label-width="140">
            <h2 class="formTitle" v-show="this.pageType==4">提交任务执行结果</h2>
            <h2 class="formTitle" v-show="this.pageType==1">巡检任务详情</h2>
            <FormItem label="所属计划：">
                <Input type="text" v-model="task.planName" readonly></Input>
            </FormItem>
            <FormItem label="巡检人：">
                <Input type="text" v-model="task.inspectManName" readonly></Input>
            </FormItem>
            <FormItem label="巡检进度：">
                <Input type="text" v-model="task.processStatus" readonly></Input>
            </FormItem>
            <FormItem label="是否完成：">
                <Input type="text" :value="task.isFinished?'完成':'未完成'" readonly></Input>
            </FormItem>
            <FormItem label="任务开始时间：">
                <DatePicker type="datetime" v-model="task.startTime" placeholder="请输入巡检任务开始时间" style="width: 100%" readonly></DatePicker>
            </FormItem>
            <FormItem label="任务结束时间：">
                <DatePicker type="datetime" v-model="task.endTime" placeholder="请输入巡检任务结束时间" style="width: 100%" readonly></DatePicker>
            </FormItem>
            <FormItem label="巡检记录：">
                <Table :columns="columns10" :data="this.task.records"></Table>
                <!-- <Button type="dashed" long @click="handleAddRecords" icon="plus-round" v-show="this.pageType==4">添加巡检记录</Button> -->
            </FormItem>
            <FormItem label="巡检描述：">
                <Input v-model="task.describe" type="textarea" :rows="4" placeholder="请输入巡检描述" readonly></Input>
            </FormItem>
            <FormItem style="text-align: center" v-show="this.pageType==4">
                <Button type="primary">提交</Button>
                <Button type="ghost" style="margin-left: 8px">取消 </Button>
            </FormItem>
        </Form>
    </div>
</template>
<script>
import types from '../../../../../static/Enum.json'
import showDefect from '../../../../components/UM/OMM/show-defect.vue';
import { TunnelService } from '../../../../services/tunnelService'
import { PatrolService } from '../../../../services/patrolService'
import { EnumsService } from '../../../../services/enumsService'
export default {
    components: { showDefect },
    data(){
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            progress: 1,
            task:{
                createTime:null,
                id:null,
                inspectManId:null,
                inspectManName:null,
                inspectTime:null,
                isFinished:false,
                planId:null,
                planName:null,
                processInstanceId:null,
                processStatus:null,
                processType:null,
                processTypeName:null,
                record:[],
                taskTime:null,
                startTime: null,
                endTime: null
            },
            addRecords:[{
                index: 1,
                place: '',
                recordTime: null,
                content: '',
                hasDefect: false,
                status: 1,
                taskId: 1,
                defect: [],
            }],
            tunnel: [],
            type: [],
            level: [],
            patrolResult:[
                {id: 1, val: 1, key: '已完成'},
                {id: 2, val: 2, key: '未完成'},
                {id: 3, val: 3, key: '进行中'}
            ],
            showAddDefect: false,
            columns10: [
                {
                    type: 'expand',
                    width: 50,
                    render: (h, params) => {
                        return h(showDefect, {
                            props: {
                                row: params.row,
                            },
                        })
                    }
                },
                {
                    title: '所属仓段',
                    render: (h,params) =>{
                        return h('span',params.row.area.name)
                    }
                },
                {
                    title: '所属区段',
                    render: (h,params) =>{
                        return h('span',params.row.store.name)
                    }
                },
                {
                    title: '巡检时间',
                    key: 'recordTime',
                    render: (h,params) => {
                        if(params.row.recordTime!=null){
                            return h('span',
                                new Date(params.row.recordTime).format("yyyy-MM-dd hh:mm:s")
                            )
                        }
                    }
                },
                {
                    title: '巡检描述',
                    key: 'content'
                },
                // {
                //     title: '操作',
                //     render: (h,params) => {
                //         return h('Button',{
                //             props: {
                //                 type: 'error',
                //                 size: 'small'
                //             },
                //             on: {
                //                 click: () => {
                //                     this.remove(params.index)
                //                 }
                //             }
                //         },'删除')
                //     }
                // }
            ],
            areas:[],
            stores:[],
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                height: '100%',
                position: 'relative',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%',
                paddingTop: '30px'
            }
        }    
    },
    mounted(){
        this.task.id =  this.$route.params.id;
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnel = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        // this.axios.get('/tunnels').then(response=>{
        //     let{code,data} = response.data
        //     if(code==200){
        //         this.tunnel = data
        //     }
        // })

        // 获取任务
        PatrolService.getTDetailByPlanId(this.task.id).then(
            (result)=>{
                _this.task = result;
                if(result.startTime!=null&&result.endTime!=null){
                    _this.task.startTime = new Date(result.startTime).format('yyyy-MM-dd hh:mm:s')
                    _this.task.endTime = new Date(result.endTime).format('yyyy-MM-dd hh:mm:s')
                }
            },
            (error)=>{
                _this.Log.info(error)
            })
        // this.axios.get('/inspection-tasks/'+this.task.id).then(response => {    
        //     let {code, data} = response.data;
        //     this.task = data;
        //     if(data.startTime!=null&&data.endTime!=null){
        //         this.task.startTime = new Date(data.startTime).format('yyyy-MM-dd hh:mm:s')
        //         this.task.endTime = new Date(data.endTime).format('yyyy-MM-dd hh:mm:s')
        //     }
        // })

        //危险等级
        EnumsService.getDefectLevel().then(
            (result)=>{
                _this.level = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        // this.axios.get("/defectLevel/enum").then(response=>{
        //     let { code,data } = response.data
        //     if(code==200){
        //         this.level = data
        //     }
        // })

        //缺陷类型
        EnumsService.getDefectType().then(
            (result)=>{
                _this.type = result
            },
            (error)=>{
                _this.Log.info = error
            })
        // this.axios.get("/defectType/enum").then(response=>{
        //     let{ code,data } = response.data
        //     if(code==200){
        //         this.type = data
        //     }
        // })

        //获取所属区域
        // this.axios.get('/tunnels/'+this.defectTunnelId+'/areas').then(response=>{
        //     let{code,data} = response.data
        //     if(code==200){
        //         this.areas=data
        //     }
        // })

        //获取所属仓段
        // this.axios.get('tunnels/1/stores').then(response=>{
        //     let{code,data} = response.data
        //     if(code==200){
        //         this.stores = data
        //     }
        // })
    },
    methods: {
        //modal 添加巡检记录
        // handleAdd(){
        //     this.addRecords.push({
        //         place: '',
        //         recordTime: null,
        //         createTime: new Date(),
        //         content: '',
        //         hasDefect: false,
        //         status: 1,
        //         taskId: this.task.id,
        //         defect:null
        //     })
        // },
        // handleRemove(index){
        //     if(index!=0){
        //         this.addRecords[index].status=0;
        //     }
        // },
        //添加缺陷
        // addDefect(index){
        //     if(this.addRecords[index].hasDefect){
        //         this.addRecords[index].defect = {
        //             id: null,
        //             tunnelId: 1,
        //             createTime: '',
        //             editTime: null,
        //             status:1,
        //             type: 2,
        //             objName: null,
        //             level: 1,
        //             description: '',
        //             objId: null,
        //         }
        //     }
        //     else{
        //         this.addRecords[index].defect = null
        //     }
        // },
        //添加巡检记录 打开model框
        // handleAddRecords(){
        //     this.showAddDefect = true
        //     // console.log("111")
        //     this.addRecords = [{
        //         place: '',
        //         recordTime: null,
        //         createTime: new Date(),
        //         content: '',
        //         hasDefect: false,
        //         status: 1,
        //         taskId: this.task.id,
        //         defect:[],
        //     }]
        // },
        // ok(){
        //     this.task.records=this.addRecords.concat(this.task.records)
        // },
        //提交巡检任务执行结果
        // submitTask(){
        //     this.task.createTime=new Date(this.task.createTime).getTime()
        //     this.task.taskTime = new Date()
        //     this.axios.put('/inspection-tasks',this.task).then(response=>{
        //         this.$router.push('/UM/myNews/queryMyTask');
        //     })
        //     .catch(function(error) {
        //         console.log(error);
        //     });
        // }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 780px;
    margin: 10px auto;
    background: #fff;
    padding: 10px 20px;
}
.adddefect,.addRecords{
    width: 100%;
    border: 1px solid #ccc;
    border-collapse: collapse;
}
.addRecords tr,.addRecords th{
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 4px 7px;
}
.addRecords td{
    padding: 4px;
    text-align: center;
}
.addRecords th{
    padding: 9px 7px;
}
.addRecords{
    margin: 5px;
}
</style>