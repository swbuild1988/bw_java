<template>
    <div class="allDiv">
        <Row class="conditions">
            <Col span="5">
                姓名：
                <Input v-model="queryCondition.name" class="queryConditionWidth"></Input>
            </Col>
            <Col span="5">
                职位：
                <Select v-model="queryCondition.jobId" class="queryConditionWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in jobs" :key="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="5">
                负责预案：
                <Select v-model="queryCondition.planId" class="queryConditionWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in plans" :key="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="5">
                联系方式：
                <Input v-model="queryCondition.tel" class="queryConditionWidth"></Input>
            </Col>
            <Col span="4">
                <Button type="primary" icon="ios-search">查询</Button>
            </Col>
        </Row>
        <div class="list">
            <Table :columns="emerManageColumn" :data="emerManageData"></Table>
        </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
        placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style='pageStyle'></Page>
    </div>
</template>
<script>
export default {
    data(){
        return{
            queryCondition: {
                name: null,
                jobId: null,
                planId: null,
                tel: null
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
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '职位',
                    key: 'jobName',
                    align: 'center'
                },
                {
                    title: '负责预案',
                    key: 'perPlan',
                    align: 'center'
                },
                {
                    title: '联系方式',
                    key: 'tel',
                    align: 'center'
                },
                {
                    title: '备用联系方式',
                    key: 'spareTel',
                    align: 'center'
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
        }
    },
    methods: {
        handlePage(value) {
            this.page.pageNum = value;
            // this.queryCondition()
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            // this.queryCondition()
        }
    }
}
</script>
<style scoped>
.queryConditionWidth{
    width: 60%;
}
</style>

