<template>
    <div>
        <Row class="conditions">
            <Col span="6">
                预案名称：
                <Select class="queryConditionWidth" v-model="queryCondition.planId">
                    <Option value=null>所有</Option>
                    <Option v-for="item in plans" :key="item.val" :value="item.val">{{item.key}}</Option>
                </Select>
            </Col>
            <Col span="6">
                职位名称：
                <Input v-model="queryCondition.name" class="queryConditionWidth"></Input>
            </Col>
            <Col span="6">
                <Button type="primary" size="small" @click="queryPosition">查询</Button>
                <Button type="error" size="small" @click="showAddPosition">添加</Button>
            </Col>
        </Row>
        <div class="list">
            <Table :columns="positionColumns" :data="positionData"></Table>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
        placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
        <Modal
            v-model="isShow"
            :title="modalTitle"
        >
            <Form ref="positionInfo" :model="positionInfo" :label-width="100" :rules="positionInfoRules">
                <FormItem label="所属预案：" prop="planId">
                    <Select v-model="positionInfo.planId">
                        <Option v-for="item in plans" :key="item.val" :value="item.val">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="小组名称：" prop="name">
                    <Input v-model="positionInfo.name"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" @click="addPosition('positionInfo')" v-show="isAdd">确定</Button>
                <Button type="primary" @click="editPosition('positionInfo')" v-show="isEdit">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
import { EnumsService } from "@/services/enumsService";
import { PlanPersonService } from "@/services/planPersonal"
export default {
    data(){
        return{
            queryCondition: {
                planId: null,
                name: null
            },
            positionColumns: [
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '预览名称',
                    key: 'planName',
                    align: 'center'
                },
                {
                    title: '小组名称',
                    key: 'name',
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
            positionData: [],
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
            isShow: false,
            modalTitle: '新增预案小组',
            positionInfo: {
                planId: null,
                name: null,
                id: null
            },
            plans: [],
            isAdd: true,
            isEdit: true,
            positionInfoRules: {
                planId: [
                    { type: 'number', required: true, message: '所属预案不能为空', trigger: 'change' }
                ],
                name: [
                    { required: true, message: '小组名称不能为空', trigger: 'blur' }
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
        this.queryPosition()
    },
    computed: {
        researches() {
            let research = {
                planId: this.queryCondition.planId,
                name: this.queryCondition.name,
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum
            };
            return Object.assign({}, research);
        }
    },
    methods: {
        //查询
        queryPosition(){
            PlanPersonService.queryPositions(this.researches).then(
                result => {
                    this.positionData = result.list
                    this.page.pageTotal = result.total
                },
                error => {
                    this.$Message.error(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryPosition();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryPosition();
        },
        //添加
        showAddPosition(){
            this.isShow = true
            this.isAdd = true
            this.isEdit = false
            this.modalTitle = '新增预案相关小组'
        },
        addPosition(name){
            var addInfo = {
                planId: this.positionInfo.planId,
                name: this.positionInfo.name
            }
            this.$refs[name].validate((valid) => {
                if(valid){
                    PlanPersonService.addPosition(addInfo).then(
                        result => {
                            this.queryPosition()
                            this.isShow = false
                            this.$Message.success("添加成功！")
                            this.handleReset(name)
                        },
                        error => {
                            this.$Message.error("添加失败！")
                        }
                    )
                }else{
                    this.$Message.error("请填写必须的信息")
                }
            })
        },
        // 修改
        edit(id){
            this.isShow = true
            this.isAdd = false
            this.isEdit = true
            this.modalTitle = '修改预案相关小组'
            PlanPersonService.getPositionById(id).then(
                result => {
                    this.positionInfo = result
                },
                error => {
                    this.$Message.error("读取失败！")
                }
            )
        },
        editPosition(name){
            this.$refs[name].validate((valid) => {
                if(valid){
                    PlanPersonService.editPositionInfo(this.positionInfo).then(
                        result => {
                            this.$Message.success("修改成功！")
                            this.isShow = false
                            this.queryPosition()
                            this.handleReset(name)
                        },
                        error => {
                            this.$Message.error("修改失败！")
                        }
                    )
                }else{
                    this.$Message.error("请填写必须的信息")
                }
            })
        },
        del(id){
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除这个职位吗？</p>",
                onOk: () => {
                    PlanPersonService.delPosition(id).then(
                        result => {
                            this.$Message.success("删除成功！")
                            this.queryPosition()
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
        handleReset(name){
            this.$refs[name].resetFields()
        }
    }
}
</script>
<style scoped>
.queryConditionWidth{
    width: 60%;
}
</style>


