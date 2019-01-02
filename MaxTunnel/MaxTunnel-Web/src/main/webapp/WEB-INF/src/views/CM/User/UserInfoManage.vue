<template>
    <div>
        <h1 style="margin-left: 20px;margin-top:10px">用户信息管理</h1>
        <div style="margin:20px">
            <Row>
                <Col span="6">
                    <span>用户名：</span>
                    <Input v-model="searchValue" placeholder="输入要查询的用户名" style="width: 60%"/>
                </Col>
                <Col span="6">
                    <span style="display: inline-block;vertical-align: middle;">性别：</span>
                    <Cascader :data="highResearch" change-on-select placeholder="条件查询" style="width: 60%;display: inline-block;"></Cascader>
                </Col>
                <Col span="6">
                    <Button type="primary" icon="ios-search" size="small">查询</Button>
                    <Button type="error" size="small" @click="addNewUser()">新增用户</Button>
                </Col>
            </Row>
        </div>
        <div style="margin:20px;">
            <Table stripe :columns="columns7" :data="data6"></Table>
        </div>
        <user-modal v-bind="modalInfo"></user-modal>
        <div>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
                  placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
                  :style='pageStyle'></Page>
        </div>
        <Modal v-model="modalInfo.show.state" :title="modalInfo.title">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="90">
                <FormItem label="用户名" prop="username">
                    <Input v-model="formValidate.username" placeholder="请输入用户名"></Input>
                </FormItem>
                <FormItem label="密码" prop="password">
                    <Input v-model="formValidate.password" placeholder="请输入密码"></Input>
                </FormItem>
                <FormItem label="确认密码" prop="_password">
                    <Input v-model="formValidate._password" placeholder="请输入确认密码" @on-blur="checkPwd()"></Input>
                    <div class="ivu-form-item-error-tip"  v-show="validatePwdCheck">确认密码与初始密码不一致</div>
                </FormItem>
                <FormItem label="年龄" prop="age">
                    <Input v-model="formValidate.age" placeholder="请输入年龄"></Input>
                </FormItem>
                <FormItem label="性别" prop="gender">
                    <RadioGroup v-model="formValidate.gender">
                        <Radio label="male">男</Radio>
                        <Radio label="female">女</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="真实姓名" prop="realName">
                    <Input v-model="formValidate.realName" placeholder="请输入真实姓名"></Input>
                </FormItem>
                <FormItem label="联系方式" prop="phone">
                    <Input v-model="formValidate.phone" placeholder="请输入联系方式"></Input>
                </FormItem>
                <FormItem label="角色" prop="role">
                    <Select v-model="formValidate.role">
                        <Option v-for="item in roleList" :value="item.label" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" @click="modalOk('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import UserModal from '../../../views/CM/User/UserModal'
    export default {
        name: "user-info-manage",
        data() {
            return {
                //表格中的属性列表
                columns7: [
                    {
                        title: '用户名',
                        key: 'username',
                    },
                    {
                        title: '密码',
                        key: 'password'
                    },
                    {
                        title: '年龄',
                        key: 'age'
                    },
                    {
                        title: '性别',
                        key: 'sex'
                    },
                    {
                        title: '真实姓名',
                        key: 'realName'
                    },
                    {
                        title: '联系方式',
                        key: 'phone'
                    },
                    {
                        title: '角色',
                        key: 'role'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.edit(params.index)
                                        }
                                    }
                                }, '修改'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.confirmRemove(params.index)
                                        }
                                    }
                                }, '删除')
                            ]);
                        }
                    }
                ], 
                //暂时使用的临时数据
                data6: [
                    {
                        username: 'Brown',
                        password: '123456',
                        age: '18',
                        sex: '男',
                        realName: '王一',
                        phone: '11111',
                        role: '普通用户'
                    },
                    {
                        username: 'working',
                        password: '123456',
                        age: '28',
                        sex: '女',
                        realName: '王二',
                        phone: '555555555',
                        role: '巡检员工'
                    },
                    {
                        username: 'abc',
                        password: '123456',
                        age: '38',
                        sex: '男',
                        realName: '王三',
                        phone: '33333',
                        role: '管廊主任'
                    },
                    {
                        username: 'zzzzzz',
                        password: '123456',
                        age: '48',
                        sex: '女',
                        realName: '王四',
                        phone: '444444',
                        role: '后台管理员'
                    }
                ],
                //修改用户信息时控制弹出框
                modalInfo: {
                    title: '修改用户信息',
                    condition: -2,
                    show:{state:false},
                    userInfo:{},
                },
                _index: 0,
                collectValue: '',
                searchValue: '',
                //人员权限
                roleList: [
                  {
                    value: '1',
                    label: '系统管理员'
                  },
                  {
                    value: '2',
                    label: '普通用户'
                  },
                  {
                    value: '3',
                    label: '巡检员工'
                  },
                  {
                    value: '4',
                    label: '巡检班主'
                  },
                  {
                    value: '5',
                    label: '管廊主任'
                  },
                  {
                    value: '6',
                    label: '后台管理员'
                  }
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
                highResearch: [{
                    value: 'male',
                    label: '男',
                    children: [
                        {
                            value: 'young',
                            label: '18周岁以下'
                        },
                        {
                            value: 'middle',
                            label: '18~60周岁'
                        },
                        {
                            value: 'old',
                            label: '60周岁以上'
                        }
                    ]
                }, {
                    value: 'female',
                    label: '女',
                    children: [
                        {
                            value: 'young',
                            label: '18周岁以下'
                        },
                        {
                            value: 'middle',
                            label: '18~60周岁'
                        },
                        {
                            value: 'old',
                            label: '60周岁以上'
                        }
                    ],
                    }
                ],
                formValidate: {
                    username:'',
                    password:'',
                    _password:'',           //确认密码
                    age:'',
                    gender:'',
                    realName:'',
                    phone:'',
                    role:''                //权限
                },
                validatePwdCheck: false,
                ruleValidate: {
                    username: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    _password: [
                        { required: true, message: '确认密码不能为空', trigger: 'blur' }
                    ],
                    age: [
                        { required: true, message: '年龄不能为空', trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '请勾选性别', trigger: 'change' }
                    ],
                    realName: [
                        { required: true, message: '真实姓名不能为空', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '联系方式不能为空', trigger: 'blur' }
                    ],
                    role: [
                        { required: true, message: '勾选用户角色', trigger: 'change' }
                    ]
                }
            }
        },
        mounted(){
            this.showTable();
        },
        computed: {
            params() {
                let param = {
                    pageNum: this.page.pageNum,
                    pageSize: this.page.pageSize,
                }
            }
        },
        methods: {
            handlePage() {
            },
            handlePageSize() {
            },
            confirmRemove(index){
                this.$Modal.confirm({
                    title: '删除确认',
                    content: '<p>确认要删除此条信息吗？</p>',
                    onOk: () => {
                        this.$Message.info('已删除');
                        this.remove(index);
                    },
                    onCancel: () => {
                        this.$Message.info('已取消操作');
                    }
                });
            },
            showTable(){
                this.page.pageTotal = this.data6.length;
            },
            edit (index) {              //修改信息
                this.modalInfo.title = '修改用户信息';
                this.modalInfo.condition = index;
                this.modalInfo.userInfo=JSON.parse(JSON.stringify(this.data6[index]));
                this._index = index;
                this.modalInfo.show.state = ! this.modalInfo.show.state;
            },
            addNewUser(){    //新增用户操作
                this.modalInfo.title = '增加新用户';
                this.modalInfo.condition = -1;
                this.modalInfo.show.state = !this.modalInfo.show.state;
            },
            remove (index) {
                this.data6.splice(index, 1);
            },
            //点击确认后才会更改表格显示
            saveChange(){
                this.data6[this._index] = this.changeInfo.userInfo;
            },
            //修改用户信息//添加用户信息
            modalOk (name) {
                this.$refs[name].validate((valid) => {
                    if (valid&&this.validatePwdCheck==false) {
                        this.$Message.success('成功!');
                        this.modalInfo.show.state = false
                    } else {
                    this.$Message.error('失败!');
                    }
                })
            },
            checkPwd(){
                if(this.formValidate.password!=null&&this.formValidate.password!=this.formValidate._password){
                    this.validatePwdCheck = true
                }else{
                    this.validatePwdCheck = false
                }
            }
        },
        components:{
            UserModal
        }
    }
</script>

<style scoped>

</style>


