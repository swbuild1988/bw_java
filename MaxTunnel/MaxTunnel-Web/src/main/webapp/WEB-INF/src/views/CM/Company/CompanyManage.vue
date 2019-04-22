<template>
    <div>
        <h1 style="margin-left: 20px;margin-top:10px">企业管理</h1>
        <Row style="margin: 20px;">
            <Col span="5">
                <span>企业名称：</span>
                <Input v-model="conditions.name" placeholder="请输入企业名称" class="inputWidth" />
            </Col>
            <Col span="5">
                <span>开户行：</span>
                <Select class="inputWidth" v-model="conditions.bank"> 
                    <Option value="null">所有</Option>
                    <Option v-for="item in selectLists.banks" :value="item.val" :key="item.val">{{ item.key }}</Option>
                </Select>
            </Col>
            <Col span="5">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="conditions.startTime">
                </DatePicker>
            </Col>
            <Col span="5">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="conditions.endTime">
                </DatePicker>
            </Col>  
            <Col span="4">
                <Button type="primary" size="small"  icon="ios-search" @click="resetPageSearch">查询</Button>
                <Button type="error" size="small" @click="add">新增企业信息</Button> 
                <Button :disabled="!deleteAttr.isShow" type="warning" size="small" @click="alldelete">批量删除</Button>
            </Col>
        </Row>
        <Table 
            border 
            ref="selection" 
            :columns="companyColumns" 
            :data="companiesData" 
            @on-selection-change="startdelete" 
            style="margin:20px;"
        ></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset('companyForm')">
            <Form ref="companyForm" :model="modal.info" :rules="companyForm" :label-width="120">
                <FormItem label="企业名称：" prop="name">
                    <Input v-model="modal.info.name" placeholder="请输入企业名称"/>
                </FormItem>
                <FormItem label="信用代码：" prop="creditNo">
                    <Input v-model="modal.info.creditNo" placeholder="请输入信用代码" />
                </FormItem>
                <FormItem label="开户行：" prop="bank">
                    <Select v-model="modal.info.bank">
                        <Option v-for="item in selectLists.banks" :value="item.val" :key="item.val">{{ item.key }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="账号：" prop="account">
                    <Input v-model="modal.info.account" placeholder="请输入账号" />
                </FormItem>
                <FormItem label="注册地址信息：" prop="address">
                    <Input v-model="modal.info.address" placeholder="请输入注册地址信息" />
                </FormItem>
                <FormItem label="注册电话：" prop="phone">
                    <Input v-model="modal.info.phone" placeholder="请输入注册电话" />
                </FormItem>
                <FormItem label="邮箱：" prop="mail">
                    <Input v-model="modal.info.mail" placeholder="请输入邮箱" />
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save('companyForm')">确定</Button>
            </div> 
        </Modal>
    </div>
</template>

<script>
import { EnumsService } from '../../../services/enumsService';
import CompanyService from '../../../services/companyService'

    export default {
        data() {
            const validateEmail = (rule,value,callback) => {
                if (value === '') {
                    callback(new Error('请输入邮箱'))
                } else {
                    let reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                    if(!reg.test(value)){
                        callback(new Error('邮箱格式不对'))
                    } else {
                        callback()
                    }
                }
            }
            const validatePhone = (rule,value,callback) => {
                if (value === '') {
                    callback(new Error('请输入注册电话'))
                } else {
                    let isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
                    let isMob= /^1(3|4|5|7|8)\d{9}$/; 
                    if(isPhone.test(value) || isMob.test(value)){
                        callback()
                    } else {
                        callback(new Error('电话格式不对'))
                    }
                }
            }
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
                companyColumns: [
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
                        title: '企业名称',
                        key: 'name',
                        align: 'center'
                    },
                    {
                        title: '开户行',
                        key: 'bankName',
                        align: 'center'
                    },
                    {
                        title: '账号',
                        key: 'account',
                        align: 'center'
                    },
                    {
                        title: '注册地址',
                        key: 'address',
                        align: 'center'
                    },
                    {
                        title: '注册电话',
                        key: 'phone',
                        align: 'center'
                    },
                    {
                        title: '邮箱',
                        key: 'mail',
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
                                },'修改')
                            ])
                        }
                    }
                ],
                companiesData: [],
                deleteAttr: {
                    isShow: false,
                    selection: [],
                    ids: '' 
                },
                modal: {
                    isShow: false,
                    title: '添加权限',
                    info: {
                        name: null,
                        creditNo: null,
                        account: null,
                        bank: null,
                        address: null,
                        phone: null,
                        mail: null
                    },
                    type: 'add'
                },
                companyForm: {
                    name: [
                        { required: true, message: '请输入角色名称', trigger: 'blur' }
                    ],
                    creditNo: [
                        { required: true, message: '请输入信用代码', trigger: 'blur' }
                    ],
                    account: [
                        { required: true, message: '请输入账号', trigger: 'blur' }
                    ],
                    bank: [
                        { type: 'number', required: true, message: '请选择开户行', trigger: 'change' }
                    ],
                    address: [
                        { required: true, message: '请输入地址', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '请输入注册电话', trigger: 'blur' },
                        { validator: validatePhone, trigger: 'blur' }
                    ],
                    mail: [
                        { required: true, message: '请输入邮箱' },
                        { validator: validateEmail, trigger: 'blur' }
                    ]
                },
                conditions: {
                    name: null,
                    startTime: null,
                    endTime: null,
                    bank: null
                },
                selectLists:{
                    banks: []
                }
            };
        },
        components: {
           
        },
        mounted() {
            this.init()
            this.resetPageSearch()
        },
        methods: {
            init(){
                EnumsService.getBanks().then(
                    response=>{
                        this.selectLists.banks = response
                    },
                    error=>{
                        this.Log.info(error)
                    }
                )
            },
            search(){
                let params = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                    name: this.conditions.name,
                    bank: this.conditions.bank === 'null' ? null : this.conditions.bank,
                    startTime: new Date(this.conditions.startTime).getTime(),
                    endTime: new Date(this.conditions.endTime).getTime()
                }
                let _this = this;
                CompanyService.companiesDatagrid(params).then(
                    res=>{
                        _this.page.pageTotal = res.total;
                        _this.companiesData = res.list;
                    }
                )
            },
            add(){
               this.modal.isShow = true;
               this.modal.type = 'add';
            },
            edit(id){
                CompanyService.getcompanyDetail(id).then(
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
                        CompanyService.addCompany(this.modal.info).then(
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
                        CompanyService.editCompany(this.modal.info).then(
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
                        CompanyService.batchDeleteCompanys(this.deleteAttr.ids).then(
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