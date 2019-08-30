<template>
    <div class="formBG">
        <div class="formTitle" v-show="this.$route.params.isFinished!=true">提交任务执行结果</div>
        <div class="formTitle" v-show="this.$route.params.isFinished==true">任务执行结果详情</div>
        <div class="formHeight">
        <Form ref="task" :model="task" :rules="taskRules" :label-width="140" ENCTYPE="multipart/form-data">
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
            <FormItem label="任务开始时间：" prop="startTime" class="required">
                <DatePicker type="datetime" v-model="task.startTime" placeholder="请输入巡检任务开始时间" style="width: 100%" :readonly="this.$route.params.isFinished==true"></DatePicker>
            </FormItem>
            <FormItem label="任务结束时间：" prop="endTime">
                <DatePicker type="datetime" v-model="task.endTime" placeholder="请输入巡检任务结束时间" style="width: 100%" :readonly="this.$route.params.isFinished==true"></DatePicker>
            </FormItem>
            <FormItem label="巡检步骤：">
                <ul class="stepsUL">
                    <li v-for="(item, index) in task.steps" :key="index" class="todoLi">
                        <Checkbox v-model="item.isFinished"></Checkbox>
                        <span style="color: #fff">{{index+1}}、</span>
                        <Input class="todoEidt" v-model="item.name" placeholder="请输入要执行的计划步骤" readonly></Input>
                    </li>
                </ul>
            </FormItem>
            <FormItem label="巡检记录：">
                <Table :columns="columns10" :data="this.task.records"></Table>
                <Button type="dashed" long @click="handleAddRecords" icon="plus-round" v-show="this.$route.params.isFinished!=true" style="color: #fff">添加巡检记录</Button>
                <div class="ivu-form-item-error-tip" v-show="errorTip">巡检记录不能为空</div>
            </FormItem>
            <FormItem label="巡检图片">
                <div class="demo-upload-list" v-for="(item, index) in uploadList" :key="index">
                    <template v-if="item.status === 'finished'">               
                        <img :src="item.url">
                        <div class="demo-upload-list-cover">
                            <Icon type="ios-eye-outline" @click.native="handleView(item.url)"></Icon>
                            <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
                        </div>
                    </template>
                    <template v-else>
                        <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
                    </template>
                </div>
                <image-from-url
                    class="imageFromUrl"
                    v-for="(item, index) in imgUrlData" :key="index"
                    :url="item.url"
                ></image-from-url>
                <Upload
                    v-show="this.$route.params.isFinished!=true" 
                    ref="upload"
                    :show-upload-list="false"
                    :default-file-list="defaultList"
                    :on-success="handleSuccess"
                    :format="['jpg','jpeg','png','gif']"
                    :max-size="2048"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleMaxSize"
                    multiple
                    type="drag"
                    :action=action
                    style="display: inline-block;width:58px;">
                    <div style="width: 58px;height:58px;line-height: 58px;">
                        <Icon type="camera" size="20"></Icon>
                    </div>
                </Upload>
                <Modal title="View Image" v-model="visible">
                    <img :src="imgUrl" v-if="visible" style="width: 100%">
                </Modal>
            </FormItem>
            <FormItem label="巡检描述：">
                <Input v-model="task.describe" type="textarea" :rows="4" placeholder="请输入巡检描述" :readonly="this.$route.params.isFinished==true"></Input>
            </FormItem>
            <div style="text-align: center;margin-left: 10vmin" v-show="this.$route.params.isFinished!=true">
                <Button type="ghost" style="margin-right: 8px"  @click="goBack()">返回 </Button>
                <Button type="primary" @click="submitTask('task')" :disabled="isDisable">提交</Button>
            </div>
            <div style="text-align: center;margin-left: 11vmin" v-show="this.$route.params.isFinished==true">
                <Button type="ghost"  @click="goBack()">返回 </Button>
            </div>
        </Form>
        </div>
        <Modal
            v-model="showAddDefect"
            title="添加缺陷"
            :width="modalWidth"
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
                        <Button type="error" @click="handleRecord(index)" class="records" :disabled="index==0">删除</Button>
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
                                            <Option v-for="(item,index) in objs" :key="index" :value="item.key">{{item.key}}</Option>
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
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";
import { DefectService } from "../../../../services/defectService"
export default {
    components: { expandRow, ImageFromUrl },
    data(){
        const checkDenTime = (rule, value, callback) => {
            if(value!=null&&value<=this.task.startTime){
                callback(new Error('任务结束时间不能早于任务开始时间'))
            }else{
                callback()
            }
        }
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
                    width: window.innerWidth/100*10,
                    align: 'center',
                    render: (h,params) => {
                        return h('span',
                            new Date(params.row.recordTime).format('yyyy-MM-dd hh:mm:s')
                        )
                    }
                },
                {
                    title: '巡检描述',
                    key: 'content',
                    render: (h,params) => {
                        let temp = params.row.content
                        if(temp.length>10)
                            temp = temp.substring(0,9)
                        else
                            temp = temp
                        return h(
                            'span',
                            {
                                domProps: {
                                    title: params.row.content
                                }
                            },
                            temp
                        )
                    }
                },
            ],
            areas:[],
            stores:[],
            type: [],
            objs:[],
            defectLevel:[],
            modalWidth: null,
            saveRecords: [],
            taskRules: {
                startTime: [
                    { type: 'date', required: true, message: '任务开始时间不能为空', trigger: 'change' }
                ],
                endTime: [
                    { type: 'date', required: true, message: '任务结束时间不能为空', trigger: 'change' },
                    { validator: checkDenTime, trigger: 'change' }
                ]
            },
            isDisable: false,
            errorTip: false,
            action: null,
            defaultList: [
                // {
                //     'name': 'a42bdcc1178e62b4694c830f028db5c0',
                //     'url': 'https://o5wwk8baw.qnssl.com/a42bdcc1178e62b4694c830f028db5c0/avatar'
                // },
                // {
                //     'name': 'bc7521e033abdd1e92222d733590f104',
                //     'url': 'https://o5wwk8baw.qnssl.com/bc7521e033abdd1e92222d733590f104/avatar'
                // }
            ],
            imgUrl: '',
            visible: false,
            uploadList: [],
            imgUrlData: []
        }    
    },
    watch: {
        'task.record': function(newVal, oldVal){
            if(newVal){
                if(newVal.length>0){
                    this.errorTip = false
                }else{
                    this.errorTip = true
                }
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
        //图片上传   
        this.action = this.ApiUrl+'/multifiles/'+this.task.id+'/files'
        this.uploadList = this.$refs.upload.fileList;
        this.getModalWidth()
        // 获取图片列表
        PatrolService.getImgList(this.task.id).then(
            result => {
                result.forEach(item=>{
                    let multifilesUrl = this.ApiUrl+'/multifiles/'+item.id+'/img'
                    this.imgUrlData.push({url: multifilesUrl})
                })
            },
            error => {
                this.Log.info(error)
            }
        )
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
        handleRecord(index){
            this.$Modal.confirm({
                title: '巡检记录',
                width:"25vw",
                content: '<p>是否删除这条巡检记录</p>',
                onOk: () => {
                    if(index!=0){
                        this.addRecords[index].status=0;
                    }
                },
                onCancel: () => {

                }
            })
        },
        //添加缺陷
        addDefect(index){
            if(this.addRecords[index].hasDefect){
                //对象名
                let mes={
                    areaId : this.addRecords[index].area.id,
                    storeId : this.addRecords[index].store.id
                }
                DefectService.getObj(mes).then(
                    result => {
                        this.objs = result
                    },
                    error => {
                        this.Log.info(error)
                    }
                )
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
                if(this.task.records.length>0){
                    this.errorTip = false
                }else{
                    this.errorTip = true
                }
            })
        },
        //提交巡检任务执行结果
        submitTask(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid) => {
                    if(valid&&this.task.records.length>0){
                        this.task.records.forEach(item => {
                            if(item.id){
        
                            }else{
                                this.saveRecords.push(item)
                            }
                        })
                        this.errorTip = false
                        this.task.records = this.saveRecords
                        PatrolService.submitPatrolTask(this.task).then(
                            result => {
                                if(this.$route.params.isFinished==undefined){
                                    this.$router.push('/UM/myNews/queryMyTask');
                                }else{
                                    this.$router.push('/UM/myTasks/query');
                                }
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }else{
                        this.errorTip = true
                    }
                })
            },2000)
        },
        //返回
        goBack(){
            this.$router.back(-1);
        },
        getModalWidth(){
            this.modalWidth = document.body.offsetWidth*0.7
        },
        //图片上传
        handleView (name) {
            this.imgUrl = name;
            this.visible = true;
        },
        handleRemove (file) {
            this.$Modal.confirm({
                title: '删除巡检图片',
                content: '<p>确定要删除这张巡检图片吗</p>',
                onOk: () => {
                    const fileList = this.$refs.upload.fileList;
                    this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
                },
                onCancel: () => {}
            })
        },
        handleSuccess (res, file) {
            file.url = this.ApiUrl+"/"+res.data.url
            file.name = res.data.name
            this.$Message.success('图片上传成功')
        },
        handleFormatError (file) {
            this.$Notice.warning({
                title: '图片格式错误',
                desc: file.name + '的格式错误，请选择jpg，jpeg，png，gif格式的图片'
            });
        },
        handleMaxSize (file) {
            this.$Notice.warning({
                title: '图片超出最大限制',
                desc: file.name + '太大了，图片最大不能超过2M'
            });
        }
    }
}
</script>
<style scoped>
.todoLi{
    line-height: 3.5vh;
    height: 3.5vh;
    margin-right: 0.5vw;
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 0.5vh;
}
.todoLi .todoEidt{
    border: none;
    flex: 1;
    margin-right: 0.5vw;
    padding-left: 0.5vw;
}
.ivu-form.ivu-form-label-right{
    width: 800px;
    margin: 10px auto;
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
    padding: 0.7vmin 0.9vmin;
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
.stepsUL{
    max-height: 10vmin;
    overflow-y: auto;
}
.stepsUL::-webkit-scrollbar{
    width: 0.4vmin;
    height: 0.4vmin;
}
.stepsUL::-webkit-scrollbar-thumb{
    border-radius: 0.5vmin;
    -webkit-box-shadow: inset 0 0 5px rgba(228, 198, 198, 0.2);
    background: rgba(0, 0, 0, 0.2)
}
.stepsUL::-webkit-scrollbar-track{
    border-radius: 0;
    -webkit-box-shadow: inset 0 0 5px rgba(221, 208, 208, 0.2);
    background: rgba(0, 0, 0, 0.1)
}
/* 图片上传 */
.demo-upload-list, .imageFromUrl{
    display: inline-block;
    width: 6vmin;
    height: 6vmin;
    text-align: center;
    line-height: 6vmin;
    border: 0.1vmin solid transparent;
    border-radius: 0.4vmin;
    overflow: hidden;
    background: #fff;
    position: relative;
    box-shadow: 0 1px 1px rgba(0,0,0,.2);
    margin-right: 0.4vmin;
}
.demo-upload-list img{
    width: 100%;
    height: 100%;
}
.demo-upload-list-cover{
    display: none;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0,0,0,.6);
}
.demo-upload-list:hover .demo-upload-list-cover{
    display: block;
}
.demo-upload-list-cover i{
    color: #fff;
    font-size: 20px;
    cursor: pointer;
    margin: 0 2px;
}
.addIconBox{
    height: 5.8vmin;
    width: 5.8vmin;
    line-height: 5.8vmin;
}
.uploadBox{
    display: inline-block;
    width: 5.8vmin
}
.goBack{
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
.formBG{
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
    background-size: 100% 100%;
    padding-top: 3vmin;
    padding-bottom: 3vmin;
}

.formBG >>> .ivu-form-item-label{
    color: #fff;
}
.formBG >>>.ivu-form .ivu-form-item-required .ivu-form-item-label:before{
    color: #00fff6;
    content: '★';
    display: inline-block;
    margin-right: 0.4vmin;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
}
.formTitle{
    font-size: 2.2vmin;
    color: #fff;
    margin-top: -3.2vmin;
}
@media (min-width: 2200px){
    .ivu-form.ivu-form-label-right{
        width: 50%;
        padding: 1vmin 2vmin;
    }
    .ivu-form-item >>> .ivu-form-item-label{
        width: 14vmin !important;
        line-height: 2.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content{
        margin-left: 14vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-select,.ivu-select >>> .ivu-select-selection,.ivu-input-wrapper >>> .ivu-input,.ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,.ivu-select.ivu-select-single >>> .ivu-select-placeholder
    {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
    .demo-upload-list{
        width: 6vmin;
        height: 6vmin;
        line-height: 6vmin;
        border: 0.1vmin solid transparent;
        border-radius: 0.4vmin;
        box-shadow: 0 0.1vmin 0.1vmin rgba(0,0,0,.2);
        margin-right: 0.4vmin;
    }
    .ivu-icon-camera{
        font-size: 3vmin !important;
    }
    .ivu-upload-drag {
        border: 0.1vmin dashed #dddee1;
    }
    .ivu-checkbox >>> .ivu-checkbox-inner{
        width: 1.4vmin !important;
        height: 1.4vmin !important;
        border: 0.1vmin solid #dddee1;
    }
    .records.ivu-checkbox-wrapper >>> .ivu-checkbox-inner{
        width: 1.4vmin;
        height: 1.4vmin;
        border: 0.1vmin solid #dddee1;
    }

    .records.ivu-checkbox-wrapper >>> .ivu-checkbox-inner:after{
        width: 0.6vmin;
        height: 0.9vmin;
        top: 0.1vmin;
        left: 0.4vmin;
    }
}
</style>