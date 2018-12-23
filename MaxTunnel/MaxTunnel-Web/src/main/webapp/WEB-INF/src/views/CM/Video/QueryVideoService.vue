<template>
    <div>
        <h1 style="margin: 10px 0px 0px 20px;">查询视频服务</h1>
        <div style="margin:20px;">
            <Row>
                <Col span="6">
                    <span>视频名称</span><span>：</span>
                    <Input v-model="conditions.name" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    <span>供应商</span><span>：</span>
                    <Select style="width: 60%" v-model="conditions.vendor">
                        <Option value="null">所有</Option>
                        <Option v-for="item in vendors" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>版本</span><span>：</span>
                    <Select style="width: 60%" v-model="conditions.vendorVersion">
                        <Option value="null">所有</Option>
                        <Option v-for="item in versions" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span>IP</span><span>：</span>
                    <Input v-model="conditions.ip" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    <span class="word42">端口</span><span>：</span>
                    <Input v-model="conditions.port" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    <span>用户名</span><span>：</span>
                    <Input v-model="conditions.username" style="width: 60%"></Input>
                </Col>
                <Col span="6">
                    <Button type="primary" size="small" @click="addVideoService1()">添加视频服务</Button>
                    <Button type="primary" size="small" @click="queryList()">查询</Button>
                </Col>
            </Row>
        </div>
        <div style="margin:20px;">
            <Table stripe border :columns="columns1" :data="data1"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
        </div>
        <Modal 
            v-model="addVideoService" 
            width='520' 
            style="padding-left: 20px;padding-right: 20px;"
            :title=modalTile>
            <Form ref="videoService" :model="videoService" :rules="ruleValidate" :label-width="70">
                <FormItem label="用户名" prop="name">
                    <Input v-model="videoService.name" placeholder="请输入视频名称"></Input>
                </FormItem>
                <FormItem label="供应商" prop="vendor">
                    <Select v-model="videoService.vendor">
                        <Option v-for="item in vendors" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="版本" prop="vendorVersion">
                    <Select v-model="videoService.vendorVersion">
                        <Option v-for="item in versions" :value="item.val" :key="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="IP" prop="ip">
                    <Input v-model="videoService.ip" placeholder="请输入ip"></Input>
                </FormItem>
                <FormItem label="端口" prop="port">
                    <Input v-model="videoService.port" placeholder="请输入端口号"></Input>
                </FormItem>
                <FormItem label="用户名" prop="username">
                    <Input v-model="videoService.username" placeholder="请输入用户名"></Input>
                </FormItem>
                <FormItem label="密码" prop="password">
                    <Input v-model="videoService.password" placeholder="请输入密码"></Input>
                </FormItem>
                <FormItem label="通道数" prop="channelNum">
                    <Input v-model="videoService.channelNum" placeholder="请输入通道数"></Input>
                </FormItem>
            </Form> 
            <div slot="footer">
                <Button type="primary" @click="submitForm('videoService')" v-if="isAdd">提交</Button>
                <Button type="primary" @click="updateForm('videoService')" v-if="isEdit">更新</Button>
                <Button type="default" @click="cancel('videoService')">取消</Button>
            </div>  
        </Modal>
    </div>    
</template>
<script>
import { CMVideoService } from '../../../services/cmVideoService.js'
export default {
    data(){
        return{
            editVideoService: false,
            isAdd: false,
            isEdit: false,
            modalTile: '',
            isEdit: false,
            columns1:[
                {
                    type: 'index',
                    align: 'center',
                    width: 60
                },
                {
                    title: '视频名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '供应商',
                    key: 'vendorName',
                    align: 'center'
                },
                {
                    title: '版本',
                    key: 'vendorVersionName',
                    align: 'center',
                    width: 100
                },
                {
                    title: 'IP',
                    key: 'ip',
                    align: 'center'
                },
                {
                    title: '端口',
                    key: 'port',
                    align: 'center',
                    width: 100
                },
                {
                    title: '用户名',
                    key: 'username',
                    align: 'center',
                    width: 100
                },
                {
                    title: '密码',
                    key: 'password',
                    align: 'center'
                },
                {
                    title: '通道数',
                    key: 'channelNum',
                    align: 'center',
                    width: 100
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h,params) => {
                        return h('div',[
                            h('Button',{
                                props:{
                                    type: "primary",
                                    size: 'small'
                                },
                                style: {
                                    marginRight: "5px"
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row.id);
                                    }
                                }
                            },'编辑'),
                            h('Button',{
                                props:{
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.del(params.row.id)
                                    }
                                }
                            },'删除')
                        ])
                    }
                }
            ],
            data1:[
                {}
            ],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0,
            },
            pageStyle: {
                position: 'absolute',
                bottom: '35px',
                right: '40px'
            },
            addVideoService: false,
            videoService:{
                id: null,
                name: null,
                vendor: null,
                vendorVersion: null,
                ip: null,
                port: null,
                username: null,
                password: null,
                channelNum: null
            },
            vendors:[],
            versions:[],
            ruleValidate:{
                name: [
                    { required: true, message: '视频名称不能为空', trigger: 'blur' }
                ],
                vendor: [
                    { type: 'number', required: true, message: '供应商不能为空', trigger: 'blur' }
                ],
                vendorVersion: [
                    { type: 'number', required: true, message: '版本不能为空', trigger: 'blur' }
                ],
                ip: [
                    { required: true, message: 'IP不能为空', trigger: 'blur' }
                ],
                port: [
                    { required: true, message: '端口不能为空', trigger: 'blur' }
                ],
                username: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ],
                channelNum: [
                    { required: true, message: '通道数不能为空', trigger: 'blur' }
                ]
            },
            conditions:{
                name: null,
                vendor: null,
                vendorVersion: null,
                ip: null,
                port: null,
                username: null
            }
        }
    },
    computed: {
        params() {
        let param = {
            pageNum: this.page.pageNum,
            pageSize: this.page.pageSize,
            vendor: this.conditions.vendor,
            name: this.conditions.name,
            vendorVersion: this.conditions.vendorVersion,
            ip: this.conditions.ip,
            port: this.conditions.port,
            username: this.conditions.username 
        };
        return Object.assign({}, param);
        }
    },
    mounted(){
        this.queryList()
        CMVideoService.getVendor().then(
            (result) => {
                this.vendors = result
            },
            (error) => {
                this.Log.info(error)
            }
        ),
        CMVideoService.getVersion().then(
            (result)=> {
                this.versions = result
            },
            (error) => {
                this.Log.info(error)
            }
        )
    },
    methods:{
        queryList(){
            let _this = this
            CMVideoService.getVideoService(this.params).then(
                (result)=>{
                    this.data1=result.list
                    _this.page.pageTotal = result.total
                },
                (error)=>{
                    _this.Log.info(error)
                }
            )
        },
        addVideoService1(){
            this.addVideoService = true
            this.modalTile = '添加视频服务'
            this.isAdd = true
            this.isEdit = false
        },
        submitForm(name){
            this.$refs[name].validate((valid)=>{
                if(valid){                   
                    var formInfo = {
                        name: this.videoService.name,
                        vendor: this.videoService.vendor,
                        vendorVersion: this.videoService.vendorVersion,
                        ip: this.videoService.ip,
                        port: this.videoService.port,
                        username: this.videoService.username,
                        password: this.videoService.password,
                        channelNum: this.videoService.channelNum
                    }
                    CMVideoService.addVideoService(formInfo).then(
                        (result)=>{
                            this.addVideoService=false
                            this.queryList()
                        },
                    )
                }
            })
        },
        cancel(name){
            this.$refs[name].resetFields()
            this.addVideoService=false
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryList()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryList()
        },
        edit(id){
            this.isAdd = false
            this.isEdit = true
            this.modalTile = '编辑视频服务'
            CMVideoService.getVideoServiceById(id).then(
                (result)=>{
                    this.videoService = result
                    this.addVideoService = true
                },
                (error)=>{
                    this.Log.info(error)
                }
            )
        },
        updateForm(name){
            this.$refs[name].validate((valid)=>{
                if(valid){
                    var formInfo = {
                        id: this.videoService.id,
                        name: this.videoService.name,
                        vendor: this.videoService.vendor,
                        vendorVersion: this.videoService.vendorVersion,
                        ip: this.videoService.ip,
                        port: this.videoService.port,
                        username: this.videoService.username,
                        password: this.videoService.password,
                        channelNum: this.videoService.channelNum
                    }
                    CMVideoService.editVideoService(formInfo).then(
                        (result)=>{
                            this.addVideoService = false
                        },
                        (error) => {
                            this.Log.info(error)
                        }
                    )
                }
            })
        },
        del(id){
            this.$Modal.confirm({
                title: '视频服务',
                content: '<p>是否删除这条视频服务信息</p>',
                onOk: () => {
                    CMVideoService.delVideoService(id).then(
                        (result) => {
                            this.data1.splice(id,1)
                            this.queryList()
                        },
                        (error) => {
                            this.Log.info(error)
                        }
                    )
                }
            })
        }
    }
}
</script>
<style scoped>
.word42{
    letter-spacing: 2em;
    margin-right: -2em;
}
.ivu-col-span-6{
    margin-bottom: 10px
}
</style>
