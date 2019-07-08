<template>
    <div>
        <!-- 员工管理 -->
        <h1 style="margin-left: 20px;margin-top:10px">员工管理</h1>
        <Row style="margin: 20px;">
            <Col span="6">
                <span>员工姓名：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth"/>
            </Col>
            <Col span="6">
                <span class="word42">账号</span>
                <span>：</span>
                <Input v-model="researchInfo.account" placeholder="支持精确查询" class="inputWidth"/>
            </Col>
            <Col span="6">
                <span class="word42">性别</span>
                <span>：</span>
                <Select v-model="researchInfo.sex" placeholder="请选择性别" class="inputWidth">
                    <Option :value="null" key="0">不限</Option>
                    <Option value="男" key="1">男</Option>
                    <Option value="女" key="2">女</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>所属部门：</span>
                <Select v-model="researchInfo.deptId" placeholder="请选择所属部门" class="inputWidth">
                    <Option value="null">不限</Option>
                    <Option
                        v-for="item in departments"
                        :value="item.id"
                        :key="item.id"
                    >{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>担任职位：</span>
                <Select v-model="researchInfo.positionId" placeholder="请选择担任职位" class="inputWidth">
                    <Option value="null">不限</Option>
                    <Option v-for="item in positions" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>开始时间</span>
                <span>：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择开始时间"
                    class="inputWidth"
                    v-model="researchInfo.startTime"
                ></DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间</span>
                <span>：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择结束时间"
                    class="inputWidth"
                    v-model="researchInfo.endTime"
                ></DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary" size="small" icon="ios-search" @click="resetPageRearch()">查询</Button>
                <Button type="error" size="small" @click="addNewStaff()">新增员工</Button>
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button>
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>
        </Row>
        <div style="margin-left: 3vmin;">账号为登录时用户名，初始密码为123456</div>
        <Table
            border
            ref="selection"
            :columns="staffInfoColumns"
            :data="staffInfoData"
            @on-selection-change="startdelete"
            style="margin:20px;"
        ></Table>
        <Page
            :total="page.pageTotal"
            :current="page.pageNum"
            show-total
            placement="top"
            @on-change="handlePage"
            show-elevator
            :style="pageStyle"
        ></Page>
        <Modal v-model="isAddStaff" :title="staffTitle" @on-cancel="handleReset('staffInfo')">
            <Form ref="staffInfo" :model="staffInfo" :rules="ruleValidate" :label-width="120">
                <FormItem label="员工姓名：" prop="name">
                    <Input v-model="staffInfo.name" placeholder="请输入员工姓名" :readonly="isEdit"></Input>
                </FormItem>
                <FormItem label="员工账号：" prop="account" v-if="isEdit">
                    <Input v-model="staffInfo.account" placeholder="请输入员工账号"></Input>
                    <div class="ivu-form-item-error-tip" v-show="isAccount">账号信息重复，请重新填写</div>
                </FormItem>
                <FormItem label="性别" prop="sex">
                    <RadioGroup v-model="staffInfo.sex">
                        <Radio label="男">男</Radio>
                        <Radio label="女">女</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="所属部门：" prop="deptId">
                    <Select v-model="staffInfo.deptId" placeholder="请选择所属部门">
                        <Option
                            v-for="item in departments"
                            :value="item.id"
                            :key="item.id"
                        >{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="职位名称：" prop="positionId">
                    <Select v-model="staffInfo.positionId" placeholder="请选择担任职位">
                        <Option
                            v-for="item in positions"
                            :value="item.id"
                            :key="item.id"
                        >{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="入职时间：" prop="hireDate">
                    <DatePicker
                        type="datetime"
                        placeholder="请选择入职时间"
                        style="width: 100%"
                        v-model="staffInfo.hireDate"
                    ></DatePicker>
                </FormItem>
                <FormItem label="联系方式：" prop="telphone">
                    <Input v-model="staffInfo.telphone" placeholder="请输入联系方式"></Input>
                </FormItem>
                <FormItem label="角色：" prop="roles" v-if="isEdit">
                    <Select v-model="staffInfo.roles" multiple>
                        <Option
                            v-for="item in authModal.roleList"
                            :value="item.id"
                            :key="item.id"
                        >{{ item.roleName }}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" @click="submitStaffInfo('staffInfo')" v-if="isEdit==false">确定</Button>
                <Button
                    type="primary"
                    @click="submitEidtStaffInfo('staffInfo')"
                    v-if="isEdit==true"
                >确定</Button>
            </div>
        </Modal>
        <Modal v-model="authModal.isShow" title="分配角色" @on-cancel="authModal.info.roleIds = []">
            <Form :model="authModal.info" :label-width="120">
                <FormItem label="员工姓名：">
                    <Input v-model="authModal.info.name" readonly/>
                </FormItem>
                <FormItem label="角色：">
                    <Select v-model="authModal.info.roleIds" multiple>
                        <Option
                            v-for="item in authModal.roleList"
                            :value="item.id"
                            :key="item.id"
                        >{{ item.roleName }}</Option>
                    </Select>
                    <div class="ivu-form-item-error-tip" v-show="authModal.error">请选择角色</div>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" @click="save">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { StaffService } from "../../../services/staffService";
import PermissionConfigService from "../../../services/permissionConfig";

export default {
    name: "staff-control",
    data() {
        return {
            ids: "",
            researchInfo: {
                name: null,
                account: null,
                sex: null,
                deptId: null,
                positionId: null,
                accountId: null,
                startTime: null,
                endTime: null,
                outside: 1
            },
            staffInfoColumns: [
                {
                    type: "selection",
                    width: 60,
                    align: "center"
                },
                {
                    type: "index",
                    align: "center",
                    width: 60
                },
                {
                    title: "员工姓名",
                    key: "name",
                    align: "center"
                },
                {
                    title: "账号",
                    key: "account",
                    align: "center"
                },
                {
                    title: "性别",
                    key: "sex",
                    align: "center"
                },
                {
                    title: "所属部门",
                    align: "center",
                    render: (h, params) => {
                        return h("div", params.row.dept.name);
                    }
                },
                {
                    title: "职位",
                    align: "center",
                    render: (h, params) => {
                        return h("div", params.row.position.name);
                    }
                },
                {
                    title: "联系方式",
                    align: "center",
                    key: "telphone"
                },
                {
                    title: "入职时间",
                    align: "center",
                    key: "hireDate",
                    render: (h, params) => {
                        if (params.row.hireDate != null) {
                            let time = new Date(params.row.hireDate).format(
                                "yyyy-MM-dd hh:mm:s"
                            );
                            return h("div", time);
                        }
                    }
                },
                {
                    title: "角色",
                    align: "center",
                    render: (h, params) => {
                        var roleNameList = "";
                        let temp = params.row.acctInfo.roles;
                        if (temp != undefined && temp.length > 0) {
                            temp.forEach(function(element) {
                                roleNameList += element.roleName + ",";
                            });
                            roleNameList = roleNameList.substring(
                                0,
                                roleNameList.length - 1
                            );
                            return h("div", roleNameList);
                        }
                    }
                },
                {
                    title: "操作",
                    align: "center",
                    width: 200,
                    render: (h, params) => {
                        if (
                            params.row.acctInfo.roles &&
                            params.row.acctInfo.roles.length == 0
                        ) {
                            return h("div", [
                                h(
                                    "Button",
                                    {
                                        props: {
                                            type: "error",
                                            size: "small"
                                        },
                                        style: {
                                            marginRight: "5px"
                                        },
                                        on: {
                                            click: () => {
                                                this.resetPass(params.row.id);
                                            }
                                        }
                                    },
                                    "重置密码"
                                ),
                                h(
                                    "Button",
                                    {
                                        props: {
                                            type: "primary",
                                            size: "small"
                                        },
                                        style: {
                                            marginRight: "5px"
                                        },
                                        on: {
                                            click: () => {
                                                this.authorize(
                                                    params.row.id,
                                                    params.row.name
                                                );
                                            }
                                        }
                                    },
                                    "分配角色"
                                )
                            ]);
                        } else {
                            return h("div", [
                                h(
                                    "Button",
                                    {
                                        props: {
                                            type: "error",
                                            size: "small"
                                        },
                                        style: {
                                            marginRight: "5px"
                                        },
                                        on: {
                                            click: () => {
                                                this.resetPass(params.row.id);
                                            }
                                        }
                                    },
                                    "重置密码"
                                ),
                                h(
                                    "Button",
                                    {
                                        props: {
                                            type: "primary",
                                            size: "small"
                                        },
                                        on: {
                                            click: () => {
                                                this.editStaffInfo(
                                                    params.row.id
                                                );
                                            }
                                        }
                                    },
                                    "修改"
                                )
                            ]);
                        }
                    }
                }
            ],
            staffInfoData: [],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "35px",
                right: "40px"
            },
            positions: [],
            departments: [],
            deleteShow: false,
            deleteSelect: [],
            isAddStaff: false,
            staffTitle: "",
            staffInfo: {
                name: null,
                // account: null,
                deptId: null,
                positionId: null,
                hireDate: null,
                sex: null,
                telphone: null,
                outside: 1,
                roles: []
            },
            isEdit: false,
            isAccount: false,
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "请输入员工姓名",
                        trigger: "blur"
                    }
                ],
                account: [
                    {
                        required: true,
                        message: "请输入员工账号",
                        trigger: "blur"
                    }
                ],
                deptId: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择所属部门",
                        trigger: "change"
                    }
                ],
                positionId: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择职位",
                        trigger: "change"
                    }
                ],
                hireDate: [
                    {
                        type: "date",
                        required: true,
                        message: "请选择入职时间",
                        trigger: "change"
                    }
                ],
                telphone: [
                    {
                        required: true,
                        message: "联系方式不能为空",
                        trigger: "blur"
                    },
                    { validator: this.validatePhone, trigger: "blur" }
                ],
                sex: [
                    {
                        required: true,
                        message: "性别不能为空",
                        trigger: "change"
                    }
                ],
                roles: [
                    {
                        type: "array",
                        required: true,
                        message: "角色不能为空",
                        trigger: "change"
                    }
                ]
            },
            authModal: {
                roleList: [],
                isShow: false,
                error: false,
                info: {
                    userId: null,
                    name: null,
                    roleIds: []
                }
            }
        };
    },
    computed: {
        researches() {
            let research = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                account: this.researchInfo.account,
                sex: this.researchInfo.sex,
                deptId: this.researchInfo.deptId,
                positionId: this.researchInfo.positionId,
                accountId: this.researchInfo.accountId,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime(),
                outside: this.researchInfo.outside
            };
            return Object.assign({}, research);
        }
    },
    mounted() {
        //获取所有职位列表
        StaffService.getPositionList().then(res => {
            this.positions = res;
        });
        //获取所有的部门
        StaffService.getDepartmentList().then(res => {
            this.departments = res;
        });
        this.showTable();
    },
    methods: {
        showTable() {
            StaffService.getStaffInfo(this.researches).then(res => {
                this.staffInfoData = res.list;
                this.page.pageTotal = res.total;
            });
        },
        //弹出新增员工modal框
        addNewStaff() {
            this.isAddStaff = true;
            this.staffTitle = "新增员工";
            this.isEdit = false;
        },
        //提交新增员工信息
        submitStaffInfo(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    var addStaffInfo = {
                        deptId: this.staffInfo.deptId,
                        hireDate: this.staffInfo.hireDate,
                        name: this.staffInfo.name,
                        positionId: this.staffInfo.positionId,
                        sex: this.staffInfo.sex,
                        telphone: this.staffInfo.telphone,
                        outside: this.staffInfo.outside
                    };
                    StaffService.addStaffInfo(addStaffInfo).then(res => {
                        this.$Message.success("新增员工信息成功!");
                        this.isAddStaff = false;
                        this.resetPageRearch();
                        this.handleReset(name);
                    });
                } else {
                    this.$Message.error("新增员工信息失败!");
                }
            });
        },
        //勾选要删除的员工信息
        startdelete(selection) {
            if (selection.length != 0) {
                this.deleteShow = true;
                this.deleteSelect = selection;
            } else {
                this.deleteShow = false;
            }
        },
        //删除操作
        alldelete() {
            this.$Modal.confirm({
                title: "员工管理",
                content: "<p>确定批量删除员工信息吗</p>",
                onOk: () => {
                    for (var i = 0; i < this.deleteSelect.length; i++) {
                        this.ids += this.deleteSelect[i].id + ",";
                    }
                    this.ids = this.ids.substring(0, this.ids.length - 1);
                    StaffService.delStaff(this.ids).then(res => {
                        this.resetPageRearch();
                    });
                },
                onCancel: () => {}
            });
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        resetPageRearch() {
            this.page.pageNum = 1;
            this.showTable();
        },
        //check telphone
        validatePhone(rule, value, callback) {
            if (!/^(1[358496]\d{9})$/.test(value)) {
                callback(new Error("请输入正确的联系方式"));
            } else {
                callback();
            }
        },
        //密码重置
        resetPass(id) {
            this.$Modal.confirm({
                title: "密码重置",
                content: "<p>确定要重置密码吗</p>",
                onOk: () => {
                    StaffService.resetPassword(id).then(res => {
                        this.$Message.success("密码已重置");
                    });
                },
                onCancel: () => {}
            });
        },
        //修改员工信息
        editStaffInfo(id) {
            StaffService.getStaffInfoById(id).then(res => {
                this.isEdit = true;
                this.staffInfo = res;
                this.staffInfo.positionId = res.position.id;
                this.staffInfo.deptId = res.dept.id;
                this.staffInfo.hireDate = new Date(res.hireDate).format(
                    "yyyy-MM-dd hh:mm:s"
                );
                this.isAddStaff = true;
                this.staffTitle = "修改员工信息";
                this.staffInfo.roles = [];
                res.acctInfo.roles.forEach(role => {
                    this.staffInfo.roles.push(role.id);
                });
            });
            // 获取所有角色
            PermissionConfigService.getAllRoles().then(res => {
                this.authModal.roleList = res;
            });
        },
        //提交修改的员工信息
        submitEidtStaffInfo(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    let params = {
                        userId: this.staffInfo.id,
                        roleIds: this.staffInfo.roles
                    };
                    Promise.all([
                        PermissionConfigService.staffAuthorize(params),
                        this.axios.put("staffs", this.staffInfo)
                    ]).then(res => {
                        let { code, data, msg } = res[1].data;
                        if (res[0] && code == 200) {
                            this.$Message.success("修改员工信息成功!");
                            this.isAddStaff = false;
                            this.resetPageRearch();
                            this.isAccount = false;
                            this.handleReset(name);
                        } else {
                            this.$Message.error("修改失败");
                        }
                    });
                } else {
                    this.$Message.error("修改员工信息失败!");
                }
            });
        },
        handleReset(name) {
            this.$refs[name].resetFields();
        },
        // 分配角色
        authorize(id, name) {
            this.authModal.info.userId = id;
            this.authModal.isShow = true;
            this.authModal.info.name = name;
            // 获取所有角色
            PermissionConfigService.getAllRoles().then(
                res => {
                    this.authModal.roleList = res;
                },
                error => {
                    this.$Message.error("获取角色失败");
                }
            );
        },
        save() {
            if (this.authModal.info.roleIds.length == 0) {
                this.authModal.error = true;
            } else {
                PermissionConfigService.staffAuthorize(
                    this.authModal.info
                ).then(
                    res => {
                        this.$Message.success("分配角色成功");
                        this.authModal.isShow = false;
                        this.authModal.error = false;
                        this.authModal.info.roleIds = [];
                        this.$nextTick(() => {
                            this.resetPageRearch();
                        });
                    },
                    error => {
                        this.$Message.success("分配角色失败");
                    }
                );
            }
        }
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
.ivu-col.ivu-col-span-6 {
    margin-bottom: 20px;
}
.word42 {
    letter-spacing: 2em;
    margin-right: -2em;
}
</style>


