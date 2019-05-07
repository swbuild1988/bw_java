<template>
    <div>
        <h1 style="margin-left: 20px;margin-top:10px">权限管理</h1>
        <Row style="margin: 20px;">
            <Col span="6" offset="18">
                <!-- <Button type="primary" size="small"  icon="ios-search" @click="search()">查询</Button> -->
                <Button type="error" size="small" @click="add">新增权限</Button> 
                <Button :disabled="!deleteAttr.isShow" type="warning" size="small" @click="alldelete()">批量删除</Button>
            </Col>
        </Row>
        <Table 
            border 
            ref="selection" 
            :columns="permissionColumns" 
            :data="permissionData" 
            @on-selection-change="startdelete" 
            style="margin:20px;"
        ></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset('permissionForm')" :mask-closable="false">
            <Form ref="permissionForm" :model="modal.info" :rules="permissionForm" :label-width="120">
                <FormItem label="模块：" prop="moduleId">
                    <Select v-model="modal.info.moduleId" @on-change="getObjects">
                        <Option v-for="item in selectList.modules" :key="item.val" :value="item.val">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="操作：" prop="actionIds">
                    <Select v-model="modal.info.actionIds" multiple>
                        <Option v-for="item in selectList.actions" :key="item.key" :value="item.val">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="资源：" prop="objectIds" v-show="modal.info.moduleId === 'tunnel'">
                    <Select v-model="modal.info.objectIds" multiple>
                        <Option v-for="item in selectList.objects" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save('permissionForm')">确定</Button>
            </div> 
        </Modal>
    </div>
</template>

<script>
import PermissionConfigService from '../../../services/permissionConfig'
import { EnumsService } from '../../../services/enumsService'
import { TunnelService } from '../../../services/tunnelService'

    export default {
        data() {
            return {
                page:{
                    pageNum: 1,
                    pageSize: 10,
                    pageTotal: 0 
                },
                pageStyle: {
                    position: 'absolute',
                    bottom: '35px',
                    right: '40px'
                },
                permissionColumns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        type: 'index',
                        align: 'center',
                        width: 100
                    },
                    {
                        title: '模块',
                        key: 'etc',
                        align: 'center',
                        render: (h,param)=>{
                            return h('span',param.row.etc.c1)
                        }
                    },
                    {
                        title: '权限',
                        key: 'etc',
                        align: 'center',
                        render:  (h,param)=>{
                            return h('span',param.row.etc.c2.join(' ')) 
                        }
                    },
                    {
                        title: '资源',
                        key: 'etc',
                        align: 'center',
                        render:  (h,param)=>{
                            if(param.row.etc.c3.length){
                                return h('div',param.row.etc.c3.join(' '))
                            } else {
                                return h('div',param.row.etc.c1 + '所有资源')
                            }
                        }
                    },
                    {
                        title: '操作',
                        align: 'center',
                        render: (h,params) => {        
                            return h('Button',{
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row.id);
                                        }
                                    }
                                },'修改')
                        }
                    }
                ],
                permissionData: [],
                deleteAttr: {
                    isShow: false,
                    selection: [],
                    ids: '' 
                },
                modal: {
                    isShow: false,
                    title: '添加权限',
                    info: {
                        id: null,
                        moduleId: null,
                        actionIds: [],
                        objectIds: [],
                    },
                    type: 'add',
                    permissionCode: ''
                },
                permissionForm: {
                    moduleId: [
                        { required: true, message: '请选择菜单', trigger: 'blur' }
                    ],
                    actionIds: [
                        { required: true, message: '请选择操作', trigger: 'blur' }
                    ],
                    objectIds: [
                        { required: true, message: '请选择资源', trigger: 'blur' }
                    ]
                },
                selectList: {
                    modules: [],
                    actions: [],
                    objects: []
                }
            };
        },
        components: {
           
        },
        computed:{
            permissionParam(){
                let actions = this.modal.info.actionIds.length === this.selectList.actions.length ? '*' : this.modal.info.actionIds.join(',')
                let objects
                if(this.modal.info.moduleId === 'tunnel'){
                    objects = this.modal.info.objectIds.length === this.selectList.objects.length ? '*' : this.modal.info.objectIds.join(',')
                } else {
                    objects = '*'
                }
                
                let param
                if(this.modal.type === 'add'){
                    param = {
                        shiroResourceType: this.modal.info.moduleId,
                        shiroOperateType: actions,
                        shiroResourceId: objects
                    }
                } else {
                    param = {
                        id: this.modal.info.id,
                        shiroResourceType: this.modal.info.moduleId,
                        shiroOperateType: actions,
                        shiroResourceId: objects
                    }
                }
                return param
            }
        },
        mounted() {
            this.init()
            this.search()
        },
        methods: {
            init(){
                EnumsService.getModuleList().then(
                    res=>{
                        this.selectList.modules = res
                    },
                    error=>{
                        this.Log.info(error)
                    }
                )
                EnumsService.getActionsList().then(
                    res=>{
                        this.selectList.actions = res
                        this.modal.info.actionIds = []
                        res.forEach(action=>{
                            this.modal.info.actionIds.push(action.val)
                        })
                    },
                    error=>{
                        this.Log.info(error)
                    }
                )
            },
            search(){
                let params = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize
                }
                let _this = this;
                PermissionConfigService.permissionDatagrid(params).then(
                    res=>{
                        _this.page.pageTotal = res.total;
                        _this.permissionData = res.list;
                    }
                )
            },
            add(){
                this.modal.info.id = null
                this.modal.isShow = true;
                this.modal.title = '添加权限';
                this.modal.type = 'add';
            },
            edit(id){
                this.modal.info.id = id
                PermissionConfigService.getPermissionDetail(id).then(
                    res=>{
                        let string = res.permissionCode.split(':')
                        this.modal.title = '修改权限';
                        this.modal.isShow = true;
                        this.modal.info.moduleId = string[0];
                        if(~string[1].indexOf('*')){
                            this.modal.info.actionIds = []
                            this.selectList.actions.forEach(action=>{
                                this.modal.info.actionIds.push(action.val)
                            })        
                        } else {
                            this.modal.info.actionIds = string[1].split(',')      
                        }
                        if(string[0] === 'tunnel'){
                            this.getObjects()
                            this.modal.permissionCode = res.permissionCode
                        }
                        this.modal.type = 'edit';
                    },
                    error=>{
                        this.$Message.error('查询失败');
                    }
                )
            },
            save(name){
                if(this.modal.type === 'add'){
                    let _this = this;
                    PermissionConfigService.addPermission(this.permissionParam).then(
                        res=>{
                            _this.$Message.success('添加成功');
                            _this.handleReset(name);
                            _this.modal.isShow = false;
                            _this.search();
                        },
                        error=>{
                            _this.$Message.error('添加失败');
                        }
                    )
                } else {
                    PermissionConfigService.editPermission(this.permissionParam).then(
                        res=>{
                            this.$Message.success('修改成功');
                            this.handleReset(name);
                            this.modal.isShow = false;
                            this.search();
                        },
                        error=>{
                            this.$Message.error('修改失败');
                        }
                    )
                }
            },
            handlePage(value) {
                this.page.pageNum = value;
                this.search();
            },
            startdelete(selection){
                if (selection.length != 0) {
                    this.deleteAttr.isShow = true;
                    this.deleteAttr.selection = selection;
                } else {
                    this.deleteAttr.isShow = false;
                }
            },
            alldelete(){
                this.$Modal.confirm({
                    title: '权限管理',
                    content: '<p>确定批量删除权限信息吗</p>',
                    onOk: () => {
                        for(var i=0; i<this.deleteAttr.selection.length; i++){
                            this.deleteAttr.ids += this.deleteAttr.selection[i].id + ','
                        }
                        this.deleteAttr.ids = this.deleteAttr.ids.substring(0,this.deleteAttr.ids.length-1)
                        PermissionConfigService.batchDeletePer(this.deleteAttr.ids).then(
                            res=>{
                                this.$Message.info('删除成功');
                                this.search();
                            },
                            error=>{
                                this.$Message.error('删除失败');
                            }
                        )
                        this.deleteAttr.ids = '';
                        this.deleteAttr.isShow = false;
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            getObjects(){
                if(this.modal.info.moduleId === 'tunnel'){
                    TunnelService.getTunnels().then(
                        res=>{
                            this.selectList.objects = res
                            let objects = this.modal.permissionCode.split(':')[2]
                            if(this.modal.type === 'edit' && objects.indexOf('*') < 0){
                                this.modal.info.objectIds = []
                                objects.split(',').map(obj=>{
                                    this.modal.info.objectIds.push(+obj)
                                })
                            } else {
                                this.modal.info.objectIds = []
                                res.forEach(action=>{
                                    this.modal.info.objectIds.push(action.id)
                                })
                            }
                            
                        },
                        error=>{
                            this.Log.info(error)
                        }
                    )
                }
            }
        }
    };
</script>

<style scoped>

</style>