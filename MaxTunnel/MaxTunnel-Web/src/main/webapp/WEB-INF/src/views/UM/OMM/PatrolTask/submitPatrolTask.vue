<template>
    <div :style="backStyle">
        <Form :model="task" :label-width="140">
            <h2 class="formTitle" v-show="this.$route.params.isFinished!=true">提交任务执行结果</h2>
            <h2 class="formTitle" v-show="this.$route.params.isFinished==true">任务执行结果详情</h2>
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
                <DatePicker type="datetime" v-model="task.startTime" placeholder="请输入巡检任务开始时间" style="width: 100%" :readonly="this.$route.params.isFinished==true"></DatePicker>
            </FormItem>
            <FormItem label="任务结束时间：">
                <DatePicker type="datetime" v-model="task.endTime" placeholder="请输入巡检任务结束时间" style="width: 100%" :readonly="this.$route.params.isFinished==true"></DatePicker>
            </FormItem>
            <FormItem label="巡检记录：">
                <Table :columns="columns10" :data="this.task.records"></Table>
                <Button type="dashed" long @click="handleAddRecords" icon="plus-round" v-show="this.$route.params.isFinished!=true">添加巡检记录</Button>
            </FormItem>
            <FormItem label="巡检描述：">
                <Input v-model="task.describe" type="textarea" :rows="4" placeholder="请输入巡检描述" :readonly="this.$route.params.isFinished==true"></Input>
            </FormItem>
            <FormItem style="text-align: center" v-show="this.$route.params.isFinished!=true">
                <Button type="primary" @click="submitTask">提交</Button>
                <Button type="ghost" style="margin-left: 8px">取消 </Button>
            </FormItem>
        </Form>
        <Modal
            v-model="showAddDefect"
            title="添加缺陷"
            width="1200"
            @on-ok="ok"
        >

            <table class="addRecords" v-for="(item,index) in addRecords" :key="index" v-if="item.status==1">
                <thead>
                    <tr style="border-top: 2px solid #4a6b9d;">
                        <!-- <th>#</th> -->
                        <th>巡检所属仓段</th>
                        <th>巡检所属区域</th>
                        <th>巡检记录时间</th>
                        <th>巡检记录描述</th>
                        <th>是否有缺陷</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tr>
                    <!-- <td v-if="item.status==1">{{index+1}}</td> -->
                    <td>
                        <Select v-model="item.area.id">
                            <Option v-for="(item,index) in areas" :key="index" :value="item.id">{{item.name}}</Option>
                        </Select>
                    </td>
                    <td>
                        <Select v-model="item.store.id">
                            <Option v-for="(item,index) in stores" :key="index" :value="item.id">{{item.name}}</Option>
                        </Select>
                    </td>
                    <td style="width: 15%">
                        <DatePicker type="datetime" v-model="item.recordTime" placeholder="请收入巡检记录时间" style="width: 100%"></DatePicker>
                    </td>
                    <td>
                        <Input v-model="item.content" type="textarea"  placeholder="请输入巡检记录描述" class="records"></Input>
                    </td>
                    <td>
                        <Checkbox v-model="item.hasDefect" class="records" style="text-align: center;" @on-change="addDefect(index)">是否发现故障</Checkbox>
                    </td>
                    <td>
                        <Button type="ghost" @click="handleRemove(index)" class="records" :disabled="index==0">删除</Button>
                    </td>
                </tr>
                <tr v-if="item.hasDefect" style="padding: 0px;"> 
                    <td colspan="7" style="padding: 0px;">
                        <!-- 缺陷 -->
                        <table class="adddefect">
                            <thead>
                                <tr>
                                    <th style="width: 12%">缺陷名称</th>
                                    <th style="width: 15%">发现时间</th>
                                    <th>缺陷类型</th>
                                    <th v-show="item.defect.type==2">对象名</th>
                                    <th>危险等级</th>
                                    <th style="width: 25%">描述</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td style="width: 12%">
                                        <Input v-model="item.defect.name" type="text" placeholder="请输入缺陷名称"></Input>
                                    </td>
                                    <td style="width: 15%">
                                        <DatePicker type="datetime" v-model="item.defect.createTime" placeholder="请输入发现时间" style="width: 100%"></DatePicker>
                                    </td>
                                    <td>
                                        <Select v-model="item.defect.type">
                                            <Option v-for="(item,index) in type" :key="index" :value="item.val">{{item.key}}</Option>
                                        </Select>
                                    </td>
                                    <td v-show="item.defect.type==2">
                                        <Select v-model="item.defect.objectId">
                                            <Option v-for="(item,index) in objs" :key="index" :value="item.key">{{item.val}}</Option>
                                        </Select>
                                    </td>
                                    <td>
                                        <Select v-model="item.defect.level">
                                            <Option v-for="(item,index) in defectLevel" :key="index" :value="item.val">{{item.key}}</Option>
                                        </Select>
                                    </td>
                                    <td style="width: 25%">
                                        <Input v-model="item.defect.description" type="textarea" placeholder="请输入缺陷描述"></Input>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>

            </table>
            <Button type="dashed" long @click="handleAdd" icon="md-add" style="margin-top: 5px;">添加</Button>
        </Modal>   
    </div>
</template>
<script>
import types from '../../../../../static/Enum.json'
import expandRow from '../../../../components/UM/OMM/table-expand.vue';
import { TunnelService } from '../../../../services/tunnelService'
import { PatrolService } from '../../../../services/patrolService'
import { EnumsService } from '../../../../services/enumsService'
export default {
    components: { expandRow },
    data(){
        return {
            defectTunnelId: 1,
            task:{
                createTime:new Date(),
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
                taskTime: new Date(),
                startTime: null,
                endTime: null,
                tunnelId: null,
                describe: ''
            },
            addRecords:[{
                index: 1,
                area:{
                    id: null    
                },
                store:{
                    id: null
                },
                recordTime: null,
                content: '',
                hasDefect: false,
                status: 1,
                taskId: 1,
                defect: [],
                createTime: new Date()
            }],
            tunnel: [],
            showAddDefect: false,
            columns10: [
                {
                    type: 'expand',
                    width: 50,
                    align: 'center',
                    render: (h, params) => {
                        if(params.row.hasDefect!=false){
                            return h(expandRow, {
                                props: {
                                    row: params.row,
                                },
                            })
                        }
                    }
                },
                {
                    title: '所属仓段',
                    align: 'center',
                    render: (h,params) =>{
                        return h('span',params.row.area.name)
                    }
                },
                {
                    title: '所属区段',
                    align: 'center',
                    render: (h,params) =>{
                        return h('span',params.row.store.name)
                    }
                },
                {
                    title: '巡检记录时间',
                    key: 'recordTime',
                    width: 200,
                    align: 'center',
                    render: (h,params) => {
                        return h('span',
                            new Date(params.row.recordTime).format('yyyy-MM-dd hh:mm:s')
                        )
                    }
                },
                {
                    title: '巡检描述',
                    key: 'content'
                },
            ],
            areas:[],
            stores:[],
            type: [],
            objs:[],
            defectLevel:[],
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            }
        }    
    },
    mounted(){
        this.task.id =  this.$route.params.id;
        let _this = this;
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnel = result
            },
            (error)=>{
                _this.Log.info(error)
        })
        // 获取任务
        PatrolService.getTDetailByPlanId(this.task.id).then(
            (result)=>{
                _this.task = result; 
                if(result.startTime!=null&&result.endTime!=null){
                    _this.task.startTime = new Date(result.startTime).format('yyyy-MM-dd hh:mm:s')
                    _this.task.endTime = new Date(result.endTime).format('yyyy-MM-dd hh:mm:s')
                }
                _this.defectTunnelId = _this.task.tunnelId
            },
            (error)=>{
                _this.Log.info(error)
            })
        //获取所属区域
        TunnelService.getAreasByTunnelId(this.defectTunnelId).then(
            (result)=>{
                _this.areas=result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //获取所属仓段
        TunnelService.getStoresByTunnelId(this.defectTunnelId).then(
            (result)=>{
                _this.stores=result
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
        //缺陷类型
        EnumsService.getDefectType().then(
            (result)=>{
                _this.type = result
            },
            (error)=>{
                _this.Log.info(error)
            })
    },
    methods: {
        //modal 添加巡检记录 内部+add
        handleAdd(){
            this.addRecords.push({
                area:{
                    id:null
                },
                store:{
                    id: null
                },
                recordTime: null,
                content: '',
                hasDefect: false,
                status: 1,
                taskId: this.task.id,
                defect:null,
                createTime: new Date()
            })
        },
        handleRemove(index){
            if(index!=0){
                this.addRecords[index].status=0;
            }
        },
        //添加缺陷
        addDefect(index){
            if(this.addRecords[index].hasDefect){
                //对象名
                let mes={
                    areaId : this.addRecords[index].area.id,
                    storeId : this.addRecords[index].store.id
                }
                this.axios.post("/section/measobjs",(mes)).then(response=>{
                    let { code,data } = response.data
                    if(code==200){
                        this.objs = data
                    }
                })
                this.addRecords[index].defect = {
                    id: null,
                    area:{
                        id:this.addRecords[index].area.id
                    },
                    store:{
                        id: this.addRecords[index].store.id
                    },
                    tunnelId: this.defectTunnelId,
                    name: '',
                    createTime: null,
                    status:1,
                    type: 1,
                    typeName: '',
                    level: 1,
                    levelName: '',
                    description: '',
                    objectId: null,
                    objName: ''
                }
            }
            else{
                this.addRecords[index].defect = null
            }
        },
        //添加巡检记录 打开model框
        handleAddRecords(){
            this.showAddDefect = true
            this.addRecords = [{
                area:{
                    id: null,
                    name: null
                },
                store:{
                    id: null,
                    name: null
                },
                recordTime: new Date(),
                content: '',
                hasDefect: false,
                status: 1,
                taskId: this.task.id,
                defect:null,
                createTime: new Date()
            }] 
        },
        ok(){
            this.addRecords.forEach(b=>{
                var arr = []
                if(b.status==1){
                    this.areas.forEach(a=>{
                        if( a.id == b.area.id){
                            b.area.name = a.name
                            if(b.hasDefect){
                                b.defect.area.name = a.name
                            }
                        }
                    }) 
                    this.stores.forEach(c=>{
                        if(c.id == b.store.id){
                            b.store.name = c.name
                            if(b.hasDefect){
                                b.defect.store.name = c.name
                            }
                        }
                    })
                    if(b.hasDefect){               
                        this.type.forEach(d=>{
                            if(d.val == b.defect.type){
                                b.defect.typeName = d.key
                            }
                        })  
                        this.defectLevel.forEach(e=>{
                            if(e.val == b.defect.level){
                                b.defect.levelName = e.key
                            }
                        })  
                        this.objs.forEach(f=>{
                            if(f.key == b.defect.objectId){
                                b.defect.objName = f.val
                            }
                        })                                                                                                 
                    }
                    arr.push(b)
                }
                    this.task.records=arr.concat(this.task.records)
            })
        },
        //提交巡检任务执行结果
        submitTask(){
            this.axios.put('/inspection-tasks',this.task).then(response=>{
                if(this.$route.params.isFinished==undefined){
                    this.$router.push('/UM/myNews/queryMyTask');
                }else{
                    this.$router.push('/UM/myTasks/query');
                }
            })
            .catch(function(error) {
                console.log(error);
            });
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 800px;
    margin: 10px auto;
    background: #fff;
    padding: 10px 20px;
    margin-top: 40px;
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
.ivu-table th{
    font-size: 14px;
}
.ivu-table-cell{
    font-size: 13px !important;
}
</style>