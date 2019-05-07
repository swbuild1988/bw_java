<template>
    <div class="allDiv">
        <Row class="conditions">
            <Col span="4">
                姓名：
                <Select v-model="queryCondition.staffId" class="queryConditionWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="4">
                负责预案：
                <Select v-model="queryCondition.planId" class="queryConditionWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in plans" :key="item.val" :value="item.val">{{item.key}}</Option>
                </Select>
            </Col>
            <Col span="4">
                所属小组：
                <Select v-model="queryCondition.groupId" class="queryConditionWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in groups" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="4">
                角色：
                <Select v-model="queryCondition.role" class="queryConditionWidth">
                    <Option value=null>所有</Option>
                    <Option value=1>组长</Option>
                    <Option value=2>组员</Option>
                </Select>
            </Col>
            <Col span="4">
                联系方式：
                <Input v-model="queryCondition.tel" class="queryConditionWidth"></Input>
            </Col>
            <Col span="4">
                <Button type="primary" icon="ios-search" @click="queryMembers">查询</Button>
                <Button type="primary" icon="plus-round" @click="addPersonBtn">添加</Button>
            </Col>
        </Row>
        <div class="list">
            <Table :columns="emerManageColumn" :data="emerManageData"></Table>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
        placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        <Modal

            v-model="isShowAdd"
            :title="modalTitle"
        >
            <Form ref="personInfo" :model="personInfo" :label-width="120" :rules="ruleValidate">
                <FormItem label="姓名：" prop="staffId">
                    <Select v-model="personInfo.staffId">
                        <Option v-for="item in staffs" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属角色：" prop="role">
                    <RadioGroup v-model="personInfo.role">
                        <Radio :label="1">组长</Radio>
                        <Radio :label="2">组员</Radio>
                    </RadioGroup>
                </FormItem>
                <FormItem label="负责预案：" prop="planId">
                    <Select v-model="personInfo.planId" @on-change="changePlan">
                        <Option v-for="item in plans" :key="item.val" :value="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属小组：" prop="groupId">
                    <Select v-model="personInfo.groupId">
                        <Option v-for="item in belongGroups" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="联系方式：" prop="tel">
                    <Input v-model="personInfo.tel"></Input>
                </FormItem>
                <FormItem label="备用联系方式：" prop="tel2">
                    <Input v-model="personInfo.tel2"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" @click="addPerson('personInfo')" v-show="modalTitle=='添加人员信息'">确定</Button>
                <Button type="primary" @click="editPerson('personInfo')" v-show="modalTitle=='修改人员信息'">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import { EnumsService } from "../../../../services/enumsService";
import { PlanPersonService } from "@/services/planPersonal"
export default {
    data(){
        return{
            queryCondition: {
                staffId: null,
                planId: null,
                tel: null,
                groupId: null,
                role: null
            },
            jobs: [],
            plans: [],
            emerManageColumn: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {   
                    title: '姓名',
                    key: 'staff',
                    align: 'center',
                    render: (h, params) => {
                        if(params.row.staff!=null){
                            return h('div', params.row.staff.name)
                        }
                    }
                },
                {
                    title: '所属小组',
                    key: 'group',
                    align: 'center',
                    render: (h, params) => {
                        if(params.row.group!=null){
                            return h('div', params.row.group.name)
                        }
                    }
                },
                {
                    title: '职位',
                    align: 'center',
                    render: (h, params) => {
                        let temp = null
                        if(params.row.group!=null){
                            if(params.row.group.staffId==params.row.staffId){
                                temp = '组长'
                            }else{
                                temp = '组员'
                            }
                        }
                        return h('div', temp)
                    }
                },
                {
                    title: '负责预案',
                    align: 'center',
                    render: (h, params) => {
                        if(params.row.group!=null){
                            return h('div', params.row.group.planName)
                        }
                    }
                },
                {
                    title: '联系方式',
                    key: 'tel',
                    align: 'center'
                },
                {
                    title: '备用联系方式',
                    key: 'tel2',
                    align: 'center'
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button',{
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                class: 'btnShow',
                                on: {
                                    click: () => {
                                        this.edit(params.row.id)
                                    }
                                }
                            },'修改'),
                            h('Button',{
                                props: {
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
            emerManageData: [
                { id: 1, name: '张三', jobName: '安全管理一组组长', perPlan: '通风预案', tel: '15664121126', spareTel: '17554102256' }
            ],
            page: {
                pageTotal: null,
                pageSize: 10, 
                pageNum: 1
            },
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px"
            },
            isShowAdd: false,
            personInfo: {
                id: null,
                staffId: null,
                planId: null,
                role: null,
                groupId: null,
                tel: null,
                tel2: null
            },
            position: [],
            plans: [],
            groups: [],
            belongGroups: [],
            staffs: [],
            modalTitle: null,
            ruleValidate: {
                staffId: [
                    { type: 'number', required: true, message: '姓名不能为空', trigger: 'change' }
                ],
                role: [
                    { type: 'number', required: true, message: '角色不能为空', trigger: 'change' }
                ],
                planId: [
                    { type: 'number', required: true, message: '负责预案不能为空', trigger: 'change' }
                ],
                groupId: [
                    { type: 'number', required: true, message: '所属小组不能为空', trigger: 'change' }                    
                ],
                tel: [
                    { required: true, message: '联系方式不能为空', trigger: 'blur' },
                    { validator: this.validatePhone, trigger: 'blur' }
                ]
            }
        }
    },
    mounted(){
        EnumsService.getPlanType().then(
            result => {
                this.plans = result
            },
            error => {
                this.Log.info(error)
            }             
        )
        this.getGroups()
        this.getStaffs()
        this.queryMembers()
    },
    methods: {
        getGroups(){
            var params = {
                planId: null,
                name: null
            }
            PlanPersonService.queryPositionList(params).then(
                result => {
                    this.groups = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        getStaffs(){
            var params = {
                outside: 1
            }
            PlanPersonService.getStaffs(params).then(
                result => {
                    this.staffs = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        queryMembers(){
            var params = {
                groupId: this.queryCondition.groupId,
                staffId: this.queryCondition.staffId,
                tel: this.queryCondition.tel,
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum,
                planId: this.queryCondition.planId,
                role: this.queryCondition.role
            }
            PlanPersonService.queryGroups(params).then(
                result => {
                    this.emerManageData = result.pagedList
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryMembers()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryMembers()
        },
        edit(id){
            console.log("111")
            this.isShowAdd = true
            this.modalTitle = '修改人员信息'
            PlanPersonService.getDetailsById(id).then(
                result => {
                    this.isShowAdd = true
                    this.personInfo = result
                    if(result.group.staffId==result.staffId){
                        this.personInfo.role=1
                    }else{
                        this.personInfo.role=2
                    }
                    this.personInfo.planId = result.group.planId
                    this.changePlan()
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        editPerson(name){
            var params = {
                id: this.personInfo.id,
                groupId: this.personInfo.groupId,
                staffId: this.personInfo.staffId,
                tel: this.personInfo.tel,
                tel2: this.personInfo.tel2
            }
            this.$refs[name].validate((valid) => {
                if(valid){
                    PlanPersonService.updateMemberInfo(params, this.personInfo.role).then(
                        result => {
                            this.isShowAdd = false
                            this.queryMembers()
                            this.$Message.success("修改成功！")
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error("请填写正确的信息")
                }
            })
        },
        del(id){
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除这个职位吗？</p>",
                onOk: () => {
                    PlanPersonService.delMember(id).then(
                        result => {
                            this.$Message.success("删除成功！")
                            this.queryMembers()
                        },
                        error => {
                            this.$Message.error("删除失败！")
                        }
                    )
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                }
            });
        },
        addPersonBtn(){
            this.isShowAdd = true
            this.modalTitle = '添加人员信息'
        },
        //获取该预案下的小组
        changePlan(){
            var params = {
                planId: this.personInfo.planId
            }
            PlanPersonService.queryPositionList(params).then(
                result => {
                    this.belongGroups = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        // 添加成员
        addPerson(name){
            var params = {
                groupId: this.personInfo.groupId,
                staffId: this.personInfo.staffId,
                tel: this.personInfo.tel,
                tel2: this.personInfo.tel2
            }
            this.$refs[name].validate((valid) => {
                if(valid){
                    PlanPersonService.addGroupMember(params, this.personInfo.role).then(
                        result => {
                            this.isShowAdd = false
                            this.queryMembers()
                            this.$Message.success("添加成功！")
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error("请填写正确的信息")
                }
            })
        },
        validatePhone(rule, value, callback){
            if(!(/^(1[358496]\d{9})$/).test(value)){
                callback( new Error("请输入正确的联系方式") )
            }else{
                callback();
            }
        }
    }
}
</script>
<style scoped>
.queryConditionWidth{
    width: 60%;
}
</style>

