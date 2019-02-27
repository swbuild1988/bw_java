<template>
    <div>
        <h1 style="margin: 10px 0px 0px 20px;">查询视频</h1>
        <Row style="margin:20px;">
            <Col span="6">
                <span>视频&nbsp;&nbsp;Id&nbsp;:</span>
                <Input  placeholder="请输入视频名称" v-model="video.id" style="width: 60%"/>
            </Col>
            <Col span="6">
                <span class="word43">供应商</span><span>:</span>
                <Select style="width: 60%" v-model="video.vendor">
                    <Option value="null">所有</Option>
                    <Option v-for="vendor in lists.vendors" :value="vendor.val" :key="vendor.key">{{vendor.key}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="word43">服务商</span><span>:</span>
                <Select style="width: 60%" v-model="video.serverId">
                    <Option value="">所有</Option>
                    <Option v-for="vendor in lists.services" :value="vendor.id" :key="vendor.id">{{vendor.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>通道号：</span>
                <Input  placeholder="请输入通道号" v-model="video.channelNo" style="width: 60%"/>
            </Col>
        </Row>
        <Row style="margin:20px;">
            <Col span="6">
                <span>所属管廊</span><span>:</span>
                <Select style="width: 60%" @on-change="tunnelChanged" v-model="video.tunnelId">
                    <Option value="null">所有</Option>
                    <Option v-for="item in lists.tunnels" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>所属区域</span><span>:</span>
                <Select style="width: 60%" v-model="video.areaId">
                    <Option value="">所有</Option>
                    <Option v-for="item in lists.queryAreas" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>所属管舱</span><span>:</span>
                <Select style="width: 60%" v-model="video.storeId">
                    <Option value="null">所有</Option>
                    <Option v-for="item in lists.queryStores" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>所属场景</span><span>:</span>
                <Select style="width: 60%" v-model="video.videoSceneId">
                    <Option value="null">所有</Option>
                    <Option v-for="item in lists.scenes" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
        </Row>
        <Row style="margin:20px;">
            <Col span="6">
                <span>是否使用:</span>
                <Select style="width: 60%" v-model="video.actived">
                    <Option value="null">所有</Option>
                    <Option v-for="item in idUsers" :key="item.key" :value="item.key">{{item.val}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <Button type="primary" size="small" icon="ios-search" @click="resetAndSearch">查询</Button>
                <Button type="error" size="small" @click="add">新增视频</Button>
                <Button type="error" size="small" @click="alldelete()" :disabled="!deleteable">批量删除</Button>
            </Col>
        </Row>
        <div style="margin:20px;">
            <Table stripe border :columns="columns1" :data="data1" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
        </div>
        <Modal 
            v-model="modalProperty.isShow" 
            width='900' 
            style="padding-left: 20px;padding-right: 20px;"
            :title="modalProperty.title">
            <Form ref="video" :model="modalProperty.data" :rules="ruleValidate"  label-position="top">
                <Row>
                    <Col span="12" style="padding: 10px">
                        <FormItem label="ID" prop="id">
                            <Input v-model="modalProperty.data.id" placeholder="请输入视频ID" :disabled="modalProperty.type === 2"/>
                        </FormItem>
                        <FormItem label="视频名称" prop="name">
                            <Input v-model="modalProperty.data.name" placeholder="请输入视频名称" />
                        </FormItem>
                        <FormItem label="所属管廊" prop="tunnelId">
                            <Select v-model="modalProperty.data.tunnelId" @on-change="tunnelChanged">
                                <Option v-for="item in lists.tunnels" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属区域" prop="areaId">
                            <Select v-model="modalProperty.data.areaId">
                                <Option v-for="item in lists.areas" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属管舱" prop="storeId">
                            <Select v-model="modalProperty.data.storeId">
                                <Option v-for="item in lists.stores" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                         <FormItem label="所属供应商" prop="vendor">
                            <Select v-model="modalProperty.data.vendor">
                                <Option v-for="item in lists.vendors" :key="item.key" :value="item.val">{{item.key}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="ip" prop="ip">
                            <Input v-model="modalProperty.data.ip" placeholder="请输入ip" />
                        </FormItem>
                        <FormItem label="视频描述">
                            <Input type="textarea" v-model="modalProperty.data.description" />
                        </FormItem>
                    </Col>
                    <Col span="12" style="padding: 10px">
                        <FormItem label="端口" prop="port">
                            <Input v-model="modalProperty.data.port" placeholder="请输入端口" />
                        </FormItem>
                        <FormItem label="用户名" prop="username">
                            <Input v-model="modalProperty.data.username" placeholder="请输入用户名" />
                        </FormItem>
                        <FormItem label="密码" prop="password">
                            <Input type="password" v-model="modalProperty.data.password" placeholder="请输入密码" />
                        </FormItem>
                        <FormItem label="所属服务商" prop="serverId">
                            <Select v-model="modalProperty.data.serverId">
                                <Option v-for="item in lists.services" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属通道号" prop="channelNo">
                            <Input v-model="modalProperty.data.channelNo" placeholder="请输入通道号" />
                        </FormItem>
                        <FormItem label="所属场景" prop="videoSceneId">
                            <Select v-model="modalProperty.data.videoSceneId">
                                <Option v-for="item in lists.scenes" :key="item.id" :value="item.id">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="是否使用" prop="actived">
                            <Select v-model="modalProperty.data.actived">
                                <Option v-for="item in idUsers" :key="item.key" :value="item.key">{{item.val}}</Option>
                            </Select>
                        </FormItem>
                    </Col>    
                </Row>
            </Form> 
            <div slot="footer">
                <Button type="primary" @click="submitForm('video')">保存</Button>
            </div>  
        </Modal>
    </div>    
</template>
<script>
import { TunnelService } from '../../../services/tunnelService'
import { CMVideoService } from '../../../services/cmVideoService'
import { MeasObjServer } from '../../../services/MeasObjectSerivers'

export default {
    data(){
        const checkId = (rule,value,callback) => {
            if (value === '') {
                    callback(new Error('不能为空'))
            } else {
                if (isNaN(value) ||  value % 1 !== 0) {
                    callback(new Error('请输入整数'))
                } else {
                    if(this.modalProperty.type === 1){
                        MeasObjServer.checkObjId(value).then(
                            result=>{
                                if(!result){
                                    callback(new Error('ID重复，请输入其他ID'))
                                } else {
                                    callback()
                                }
                        })
                    } else {
                        callback()
                    }                  
                }  
            }
        }
        const checkInt = (rule,value,callback) => {
            if (value === '') {
                    callback(new Error('不能为空'))
            } else {
                if (isNaN(value) ||  value % 1 !== 0) {
                    callback(new Error('请输入整数'))
                } else {
                    callback()
                }  
            }
        }
        const checkIp = (rule,value,callback) =>{
             if (value === '') {
                    callback(new Error('不能为空'))
            } else {
                if (isNaN(value.replace(/\./g,''))) {
                    callback(new Error('请输入数字'))
                } else {
                    callback()
                }  
            }
        }
        return{
            columns1:[
                {
                    type: "selection",
                    width: 60,
                    align: "center"
                },
                {
                    title: 'ID',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: '视频名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '所属位置',
                    align: 'center',
                    render: (h,params) =>{
                        return h('span',params.row.tunnelName + params.row.areaName + 
                        params.row.storeName)
                    }
                },
                {
                    title: '所属供应商',
                    key: 'vendorName',
                    align: 'center',
                },
                {
                    title: 'ip',
                    key: 'ip',
                    align: 'center'
                },
                {
                    title: '端口',
                    key: 'port',
                    align: 'center'
                },
                {
                    title: '用户名',
                    key: 'username',
                    align: 'center'
                },
                {
                    title: '所属服务商',
                    align: 'center',
                     render: (h,params) => {
                        return h('span',params.row.videoServerDto.name)
                    }
                },
                {
                    title: '所属通道号',
                    key: 'channelNo',
                    align: 'center'
                },
                {
                    title: '所属场景',
                    key: 'sceneName',
                    align: 'center'
                },
                {
                    title: '视频描述',
                    key: 'description',
                    align: 'center'
                },
                {
                    title: '是否使用',
                    key: 'actived',
                    align: 'center',
                    render: (h,params) => {
                        if(params.row.actived){
                            return h('span','是')
                        } else {
                            return h('span','否')
                        }
                    }
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 100,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginLeft: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row.id)
                                    }
                                }
                            }, '修改')
                        ]);
                    }
                }
            ],
            data1:[
                {id:1,tunnelName:'古城大街',areaName:'污水仓',storeName:'十二段',name:'电力仓视频',
                serverId:3,channelNo:2,sceneName:'场景1',description:'可以正常投入使用',actived:0,ip:'192.168.0.12',port:8067,
                vendorName: 'H5Stream',username: 'admin',videoServerDto: {name: 'H5Stream'}}
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
            modalProperty: {
                isShow: false,
                title: null,
                type: null,
                data: {
                    id: null,
                    tunnelId: null,
                    areaId: null,
                    storeId: null,
                    name: null,
                    objtypeId: 7,
                    datatypeId: 7,
                    serverId: null,
                    channelNo: null,
                    videoSceneId: null,
                    description: null,
                    actived: 0,
                    ip: null,
                    port: null,
                    username: null,
                    password: null,
                    vendor: null
                } 
            },
            video:{
                id: null,
                tunnelId: null,
                areaId: null,
                storeId: null,
                name: null,
                serverId: null,
                channelNo: null,
                videoSceneId: null,
                actived: null,
                ip: null,
                port: null,
                username: null,
                vendor: null
            },
            ruleValidate:{
                id: [
                    { required: true, message: 'ID不能为空', trigger: 'blur' },
                    { validator: checkId, trigger: 'blur' }
                ],
                tunnelId: [
                    { type: 'number',required: true, message: '请选择所属管廊', trigger: 'change' }
                ],
                areaId: [
                    { type: 'number',required: true, message: '请选择所属仓段' }
                ],
                storeId: [
                    { type: 'number',required: true, message: '请选择所属区段', trigger: 'change' }
                ],
                name: [
                    { required: true, message: '视频名称不能为空', trigger: 'blur' }
                ],
                objtypeId: [
                    { required: true, message: '请选择视频对象类型', trigger: 'change' }
                ],
                serverId: [
                    { type: 'number',required: true, message: '请选择所属服务商', trigger: 'change' }
                ],
                channelNo: [
                    { required: true, message: '请输入通道号', trigger: 'blur' },
                    { validator: checkInt, trigger: 'blur' }
                ],
                videoSceneId: [
                    { type: 'number', required: true, message: '请选择所属场景', trigger: 'change' }
                ],
                actived: [
                    { type: 'number', required: true, message: '请选择是否使用', trigger: 'change' }
                ],
                ip: [
                    { required: true, message: 'ip不能为空', trigger: 'blur' },
                    { validator: checkIp, trigger: 'blur' }
                ],
                port: [
                    { required: true, message: '端口不能为空', trigger: 'blur' },
                    { validator: checkInt, trigger: 'blur' }
                ],
                username: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ],
                vendor: [
                    { type: 'number', required: true, message: '请选择供应商', trigger: 'change' }
                ]
            },
            idUsers:[
                { key: 1, val: '否' },
                { key: 2, val: '是' }
            ],
            lists: {
                tunnels: [],
                areas: [],
                stores: [],
                scenes: [],
                services: [],
                vendors: [],
                queryAreas: [],
                queryStores: []
            },
            deleteable: false
        }
    },
    mounted(){
        this.init()
        this.search()
    },
    methods:{
        init(){
            let _this = this
            TunnelService.getTunnels().then(
                (result) => {
                    _this.lists.tunnels = result
                },
                (error) => {
                    _this.Log.info(error)
                }
            )
            CMVideoService.getVideoScenes().then(
                result=>{
                    _this.lists.scenes = result
                },
                error=>{
                    _this.Log.info(error)
                })
            CMVideoService.getVideoServices().then(
                result=>{
                    _this.lists.services = result
                },
                error=>{
                    _this.Log.info(error)
                })
            CMVideoService.getVendor().then(
                result=>{
                    _this.lists.vendors = result
                },
                error => {
                    _this.Log.info(error)
                }
            )
        },
        submitForm(name){
            this.$refs[name].validate((valid)=>{
                if(valid){
                    this.modalProperty.isShow = false
                    let _this = this
                    if(this.modalProperty.type === 1){
                        CMVideoService.addVideo(this.modalProperty.data).then(
                            result=>{
                                _this.$Message.info('添加成功')
                                _this.$nextTick(()=>{
                                    _this.resetAndSearch();
                                })
                            },
                            error=>{
                                _this.$Message.error('添加失败' + error)
                            }
                        )
                    } else {
                        CMVideoService.updateVideo(this.modalProperty.data).then(
                            result=>{
                                _this.$Message.info('修改成功')
                                _this.$nextTick(()=>{
                                    _this.resetAndSearch();
                                })
                            },
                            error=>{
                                _this.$Message.error('修改失败' + error)
                            }
                        )
                    }  
                }
            })
        },
        tunnelChanged(){
            let _this = this
            if(this.modalProperty.data.tunnelId || this.video.tunnelId){
                let tunnelId = this.modalProperty.data.tunnelId ? this.modalProperty.data.tunnelId : this.video.tunnelId
                TunnelService.getAreasByTunnelId(tunnelId).then(
                result=>{
                    if(_this.modalProperty.data.tunnelId){
                        _this.lists.areas = result
                        _this.modalProperty.data.areaId = result[0].id
                    } else {
                        _this.lists.queryAreas = result
                    }                  
                },
                error=>{
                    _this.Log.info(error)
                })
            TunnelService.getStoresByTunnelId(tunnelId).then(
                result=>{
                    if(_this.modalProperty.data.tunnelId){
                        _this.lists.stores = result
                        _this.modalProperty.data.storeId = result[0].id
                    } else {
                        _this.lists.queryStores = result
                    }
                },
                error => {
                    _this.Log.info(error)
                })
            }          
        },
        handlePage(value) {
          this.page.pageNum = value;
          this.search();
        },
        handlePageSize(value) {
            this.page.pageSize = value
            this.resetAndSearch()
        },
        search(){
            let _this = this
            let params = {
                id: this.video.id,
                tunnelId: this.video.tunnelId,
                areaId: this.video.areaId,
                storeId: this.video.storeId,
                // name: this.video.name,
                serverId: this.video.serverId,
                channelNo: this.video.channelNo,
                videoSceneId: this.video.videoSceneId,
                actived: this.video.actived == 1 ? false : (this.video.actived == 2 ? true : null),
                // ip: this.video.ip,
                // port: this.video.port,
                // username: this.video.username,
                vendor: this.video.vendor,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
            }
            CMVideoService.videoDatagrid(params).then(
                result=>{
                    _this.data1 = result.pagedList
                    _this.page.pageTotal = result.total
                },
                error => {
                    _this.$Message.error(error)
                }
            )
        },
        resetAndSearch() {
            this.page.pageNum = 1
            this.search()
        },
        add(){
            for(let item in this.modalProperty.data){
                if(item === 'objtypeId' || item === 'datatypeId') {
                    this.modalProperty.data[item] = 7
                } else {
                    if(item === 'actived' || item === 'tunnelId' || item === 'areaId' 
                    || item === 'storeId' || item === 'serverId' || item === 'videoSceneId' || item === 'vendor'){
                        this.modalProperty.data[item] = 1
                    }  else {
                        this.modalProperty.data[item] = null
                    }       
                }
            }
            this.modalProperty.type = 1
            this.modalProperty.isShow = true
            this.modalProperty.title = '添加视频'
        },
        edit(id) {
            let _this = this
            CMVideoService.getVideoDetailById(id).then(
                result=>{
                    _this.modalProperty.data = result
                    _this.modalProperty.data.actived = result.actived ? 2 : 1
                    for(let item in _this.modalProperty.data){
                        if(item === 'id' || item === 'channelNo' || item === 'port'){
                            _this.modalProperty.data[item] = _this.modalProperty.data[item] + ''
                        }
                    }
                    _this.modalProperty.isShow = true
                    _this.modalProperty.type = 2
                    _this.modalProperty.title = '编辑视频'
                },
                error=>{
                    _this.Log.info(error)
                }
            )
        },
        startdelete(selection) {
            if (selection.length != 0) {
                this.deleteable = true;
                this.deleteSelect = selection;
            } else {
                this.deleteable = false;
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
                let _this = this;
                CMVideoService.batchDeleteVideos(ids).then(
                    result => {
                        _this.$Message.info("已删除");
                        _this.deleteable = false;
                        _this.$nextTick(()=>{
                            _this.resetAndSearch();
                        })
                    },
                    error => {
                        _this.Log.info(error);
                    }
                );
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                    this.resetAndSearch();
                },
            });
        }
    }
}
</script>
<style scoped>
    .word42{
        letter-spacing: 2em;
        margin-right: -2em;
    }
    .word43{
        letter-spacing: 0.65em;
        margin-right: -0.65em;
    }
</style>
