<template>
    <div>
        <h1 style="margin-left: 20px;margin-top:10px">角色管理</h1>
        <Row style="margin: 20px;">
            <Col span="6">
                <span>角色名称：</span>
                <Input v-model="conditions.roleName" placeholder="请输入角色名称" class="inputWidth" />
            </Col>
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="conditions.startTime">
                </DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="conditions.endTime">
                </DatePicker>
            </Col>  
            <Col span="6">
                <Button type="primary" size="small"  icon="ios-search" @click="resetPageSearch">查询</Button>
                <Button type="error" size="small" @click="add">新增角色</Button> 
                <Button :disabled="!deleteAttr.isShow" type="warning" size="small" @click="alldelete">批量删除</Button>
            </Col>
        </Row>
        <Table 
            border 
            ref="selection" 
            :columns="roleColumns" 
            :data="rolesData" 
            @on-selection-change="startdelete" 
            style="margin:20px;"
        ></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset('roleForm')">
            <Form ref="roleForm" :model="modal.info" :rules="roleForm" :label-width="120">
                <FormItem label="角色名称：" prop="roleName">
                    <Input v-model="modal.info.roleName" placeholder="请输入角色名称" :disabled="modal.type === 'authorize'"/>
                </FormItem>
                <div v-if="modal.type === 'authorize'" style="margin:20px;">
                    <Row v-for="menu in permissionList" :key="menu.menuName">
                        <Col span="4">{{menu.menuName}}</Col>
                        <Col span="20">
                            <CheckboxGroup v-model="checkPerList">
                                <Checkbox v-for="per in menu.permissionList" 
                                :key="per.permissionName" :label="per.id">
                                    <span>{{per.permissionName}}</span>
                                </Checkbox>
                            </CheckboxGroup>
                        </Col>
                    </Row>
                </div>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save('roleForm')">确定</Button>
            </div> 
        </Modal>
    </div>
</template>

<script>
import PermissionConfigService from '../../../services/permissionConfig';

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
                roleColumns: [
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
                        title: '角色名称',
                        key: 'roleName',
                        align: 'center'
                    },
                    {
                        title: '创建时间',
                        key: 'crtTime',
                        align: 'center',
                        render: (h,params)=>{
                            let crtTime = params.row.crtTime
                            let time = crtTime == null ? '' : new Date(crtTime).format('yyyy-MM-dd hh:mm:ss')
                            return h('div',time)
                        }
                    },
                    {
                        title: '权限',
                        key: 'permissionList',
                        align: 'center',
                        render: (h,params) => {
                            if(params.row.roleName === 'admin'){
                                return h('div','具有所有权限')
                            } else {
                                let lists = []
                                params.row.permissionList.forEach(per=>{
                                    let menu = lists.find(menu=>{
                                        return per.menuName == menu.menuName
                                    }) 
                                    if(!!menu){
                                        menu.perList.push(per.permissionName)
                                    } else {
                                        let temp = {}
                                        temp.menuName = per.menuName
                                        temp.perList = []
                                        temp.perList.push(per.permissionName)
                                        lists.push(temp)
                                    }
                                })
                             
                                return h('ul',{
                                    style:{
                                        listStyleType: 'none',
                                        width: '100%',
                                        height: '20px'
                                    }
                                },lists.map(list=>{
                                    return h('li',{},[
                                        h('div',{
                                            style: {
                                                float: 'left',
                                                width: '30%',
                                                overflow: 'hidden',
                                                display: 'inline-block'
                                            }
                                        },list.menuName),
                                        h('div',{
                                            style: {
                                                float: 'right',
                                                width: '70%',
                                                overflow: 'hidden'
                                            }
                                        },list.perList.map(per=>{
                                            return h('span',{
                                                style: {
                                                    margin: '0 2px',
                                                    float: 'left'
                                                }
                                            },per)
                                        }))                                      
                                    ])
                                }))
                               
                            }
                        }
                    },
                    {
                        title: '操作',
                        align: 'center',
                        render: (h,params) => {
                            return h('div',[
                                h('Button',{
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.row.id);
                                        }
                                    }
                                },'修改'),
                                h('Button',{
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.authorize(params.row.id);
                                        }
                                    }
                                },'权限配置')
                            ])
                        }
                    }
                ],
                rolesData: [],
                deleteAttr: {
                    isShow: false,
                    selection: [],
                    ids: '' 
                },
                modal: {
                    isShow: false,
                    title: '添加权限',
                    info: {
                        roleName: null,
                    },
                    type: 'add'
                },
                roleForm: {
                    roleName: [
                        { required: true, message: '请输入角色名称', trigger: 'blur' }
                    ]
                },
                conditions: {
                    roleName: null,
                    startTime: null,
                    endTime: null
                },
                permissionList: [],
                checkPerList: []
            };
        },
        components: {
           
        },
        mounted() {
           this.resetPageSearch()
        },
        methods: {
            search(){
                let params = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    roleName: this.conditions.roleName,
                    startTime: new Date(this.conditions.startTime).getTime(),
                    endTime: new Date(this.conditions.endTime).getTime()
                }
                let _this = this;
                PermissionConfigService.rolesDatagrid(params).then(
                    res=>{
                        _this.page.pageTotal = res.total;
                        _this.rolesData = res.list;
                    }
                )
            },
            authorize(id){
                Promise.all([PermissionConfigService.getRoleDetail(id),
                PermissionConfigService.getAllPermissions()]).then(
                    res=>{
                        this.modal.title = '权限配置';
                        this.modal.isShow = true;
                        this.modal.info = res[0];
                        res[0].permissionList.forEach(item=>{
                            this.checkPerList.push(item.id)
                        })
                        this.modal.type = 'authorize';
                        this.permissionList = res[1]
                        if(this.modal.info.roleName === 'admin'){
                            res[1].map(item=>{
                                item.permissionList.map(per=>{
                                    this.checkPerList.push(per.id)
                                })
                            })
                        }
                    }
                )
            },
            add(){
               this.modal.isShow = true;
               this.modal.type = 'add';
            },
            edit(id){
                PermissionConfigService.getRoleDetail(id).then(
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
                switch(this.modal.type){
                    case 'add':
                        PermissionConfigService.addRole(this.modal.info).then(
                            res=>{
                                this.$Message.success('添加成功');
                                this.handleReset(name);
                                this.$nextTick(()=>{
                                    this.resetPageSearch()
                                })
                            },
                            error=>{
                                this.$Message.error('添加失败');
                            }
                        )
                        break;
                    case 'edit':
                        PermissionConfigService.editRole(this.modal.info).then(
                            res=>{
                                this.$Message.success('修改成功');
                                this.handleReset(name);
                                this.$nextTick(()=>{
                                    this.resetPageSearch()
                                })
                            },
                            error=>{
                                this.$Message.error('修改失败');
                            }
                        )
                        break;
                    case 'authorize':
                        let params = {
                            roleId: this.modal.info.id,
                            permissionIds: this.checkPerList
                        }   
                        PermissionConfigService.roleAuthorize(params).then(
                            res=>{
                                this.$Message.success('赋权成功');
                                this.checkPerList = []
                                this.$nextTick(()=>{
                                    this.resetPageSearch()
                                })
                            },
                            error=>{
                                this.$Message.success('赋权失败');
                            }
                        )
                        break;
                }
                this.modal.isShow = false;
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
                        PermissionConfigService.batchDeleteRoles(this.deleteAttr.ids).then(
                            res=>{
                                this.$Message.info('删除成功');
                                this.resetPageSearch();
                            },
                            error=>{
                                this.$Message.error('删除失败');
                            }
                        )
                        this.deleteAttr.ids = '';
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
                if(this.modal.type === 'authorize'){
                    this.checkPerList = []
                }
            },
            resetPageSearch(){
                this.page.pageNum = 1;
                this.search();
            }
        }
    };
</script>

<style scoped>
    .inputWidth{
        width: 60%;
    }
</style>