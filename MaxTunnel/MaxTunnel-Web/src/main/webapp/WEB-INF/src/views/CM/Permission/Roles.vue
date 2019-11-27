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
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset('roleForm')" :mask-closable="false">
            <Form ref="roleForm" :model="modal.info" :rules="roleForm" :label-width="120">
                <FormItem label="角色名称：" prop="roleName">
                    <Input v-model="modal.info.roleName" placeholder="请输入角色名称" :disabled="modal.type === 'authorize'"/>
                </FormItem>
                <div v-if="modal.type !== 'authorize'">
                    <span>路由：</span>
                    <Tree :data="routes" show-checkbox multiple ref="routeTree"></Tree>
                </div>
                <div v-else>
                    <span>操作：</span>
                    <div v-if="modal.type === 'authorize'" style="margin:20px;">
                        <Table    
                        ref="permissionTable" 
                        :columns="permissionColumns" 
                        :data="permissionData" 
                        @on-selection-change="getSelections" ></Table>
                    </div>
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
import { asyncRouterMap } from '../../../router'

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
                        title: '功能权限',
                        key: 'routList',
                        align: 'center',
                        width: 240,
                        render: (h,params) => {
                            if(params.row.roleName === 'admin'){
                                return h('div','具有所有权限')
                            } else {
                                return h('div',params.row.routList.slice(0,40) + '......')
                            }
                        }
                    },
                    {
                        title: '数据权限',
                        key: 'permissionList',
                        align: 'center',
                        width: 500,
                        render: (h,params) => {
                            if(params.row.roleName === 'admin'){
                                return h('div','具有所有权限')
                            } else {
                                return h('div',params.row.permissionList.map(item=>{
                                    return h('div',{
                                        style: {
                                            position: 'relative',
                                            margin: '10px 0'
                                        }
                                    },[h('span',{
                                        style: {
                                            width: '10%',
                                            position: 'absolute',
                                            top: '0',
                                            left: '0'
                                        }
                                    },item.c1),
                                    h('div',{
                                        style: {
                                            margin: '0 18% 0 4%',
                                            width: '60%',
                                            overflowX: 'hidden',
                                            display: 'inline-block'
                                        }
                                    },item.c2.join(',')),
                                    h('span',{
                                        style: {
                                            position: 'absolute',
                                            top: '0',
                                            right: '0',
                                            width: '30%'
                                        }
                                    },item.c3.length ? item.c3.join(',') : item.c1 + '所有资源')])
                                }))
                               
                            }
                        }
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
                                },'功能权限配置'),
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
                                },'数据权限配置')
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
                        id: null,
                        roleName: null,
                        routList: []
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
                checkPerList: [],
                routes: [],
                permissionColumns: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
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
                    }
                ],
                permissionData: [],
            };
        },
        components: {
           
        },
        mounted() {
           this.resetPageSearch()
           this.routes = this.parseRoute(asyncRouterMap)
        },
        methods: {
            parseRoute(arr){
                return arr.reduce((curArr,curVal)=>{   
                    let obj = {}
                    obj.title = curVal.name
                    obj.checked = false
                    if(curVal.children){
                        obj.children = this.parseRoute(curVal.children)
                    }
                    return curVal.name ? curArr.concat(obj) : curArr
                },[])
            },
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
                        this.modal.type = 'authorize';
                        this.permissionData = res[1];
                        this.$nextTick(()=>{
                            let list = this.$refs.permissionTable.$refs.tbody.objData
                            console.log(list)
                            res[0].permissionList.forEach(per=>{
                                for(let checkItem in list){
                                    if(per.id === list[checkItem].id){
                                        list[checkItem]._isChecked = true
                                    }
                                }
                            })
                        })
                    }
                )
            },
            add(){
               this.modal.isShow = true;
               this.modal.type = 'add';
               this.modal.info.id = null
            },
            initSelectedRoutes(arr,nameArr){
                arr.map(item=>{
                    if(item.children){
                        this.initSelectedRoutes(item.children,nameArr)
                    } else {
                        let findFlag = nameArr.find(name=>{
                            return name === item.title
                        })
                        if(findFlag) {
                            item.checked = true
                        }
                    }
                })
            },
            edit(id){
                PermissionConfigService.getRoleDetail(id).then(
                    res=>{
                        this.modal.title = '修改权限';
                        this.modal.isShow = true;
                        this.modal.info = res;
                        this.modal.type = 'edit';
                        let nameArr = res.routList.split(',')
                        this.initSelectedRoutes(this.routes,nameArr)
                    },
                    error=>{
                        this.$Message.error('查询失败');
                    }
                )
            },
            getParentTitles(data, id){
                var titles=[];
                function loop(json){
                for(var obj of json){
                    if(obj.children){
                        if(JSON.stringify(obj).match(id)) {
                            titles.push(obj.title);
                        }
                        if(obj.id !== id) {
                            loop(obj.children);
                        } else {
                            break;
                        }
                    }
                    
                }
                };
                loop(data);
                return titles;
            },
            save(name){
                if(this.modal.type !== 'authorize'){
                    const checkedNodes = this.$refs.routeTree.getCheckedNodes();   
                    let tmpTitles = [];  
                    checkedNodes.map(item => {
                        const parents = this.getParentTitles(this.routes, item.id);
                        tmpTitles = tmpTitles.concat(parents);
                    });
                    
                    checkedNodes.map(item => {
                        tmpTitles.push(item.title);
                    });
                   
                    this.modal.info.routList = Array.from(new Set(tmpTitles)).join(',') + ',';
                }
             
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
                } else {
                    this.resetSelectRoutes(this.routes)
                }
            },
            resetSelectRoutes(arr){
                arr.map(item=>{
                    item.checked = false
                    
                    if(item.children){
                        item.indeterminate = false
                        item.expand = false
                        this.resetSelectRoutes(item.children)
                    }
                })
            },
            resetPageSearch(){
                this.page.pageNum = 1;
                this.search();
            },
            getSelections(selection){
                this.checkPerList = []
                selection.map(select=>{
                    this.checkPerList.push(select.id)
                })
            }
        }
    };
</script>

<style scoped>
    .inputWidth{
        width: 60%;
    }
    .ivu-modal {
        width: 60%;
    }
</style>