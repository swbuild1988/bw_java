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
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset('permissionForm')">
            <Form ref="permissionForm" :model="modal.info" :rules="permissionForm" :label-width="120">
                <FormItem label="菜单名称：" prop="menuName">
                    <Input v-model="modal.info.menuName" placeholder="请输入菜单名称，如用户" />
                </FormItem>
                <FormItem label="菜单地址：" prop="menuCode">
                    <Input v-model="modal.info.menuCode" placeholder="格式为xxx/xxx，如/CM/user" />
                </FormItem>
                <FormItem label="权限名称：" prop="permissionName">
                    <Input v-model="modal.info.permissionName" placeholder="请输入权限名称,如列表" />
                </FormItem>
                <FormItem label="权限匹配：" prop="permissionCode">
                    <Input v-model="modal.info.permissionCode" placeholder="格式为xxx:xxx，如user:list" />
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
                        title: '菜单',
                        key: 'menuName',
                        align: 'center'
                    },
                    {
                        title: '菜单地址',
                        key: 'menuCode',
                        align: 'center'
                    },
                    {
                        title: '权限',
                        key: 'permissionName',
                        align: 'center'
                    },
                    {
                        title: '权限匹配',
                        key: 'permissionCode',
                        align: 'center'
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
                        menuCode: null,
                        menuName: null,
                        permissionName: null,
                        permissionCode: null
                    },
                    type: 'add'
                },
                permissionForm: {
                    menuCode: [
                        { required: true, message: '请输入菜单名称', trigger: 'blur' }
                    ],
                    menuName: [
                        { required: true, message: '请输入菜单地址', trigger: 'blur' }
                    ],
                    permissionCode: [
                        { required: true, message: '请输入权限匹配', trigger: 'blur' }
                    ],
                    permissionName: [
                        {required: true, message: '请输入权限名称', trigger: 'blur' }
                    ]
                }
            };
        },
        components: {
           
        },
        mounted() {
           this.search()
        },
        methods: {
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
               this.modal.isShow = true;
               this.modal.type = 'add';
            },
            edit(id){
                PermissionConfigService.getPermissionDetail(id).then(
                    res=>{
                        this.modal.title = '修改权限';
                        this.modal.isShow = true;
                        this.modal.info = res;
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
                    PermissionConfigService.addPermission(this.modal.info).then(
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
                    PermissionConfigService.editPermission(this.modal.info).then(
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
            }
        }
    };
</script>

<style scoped>

</style>