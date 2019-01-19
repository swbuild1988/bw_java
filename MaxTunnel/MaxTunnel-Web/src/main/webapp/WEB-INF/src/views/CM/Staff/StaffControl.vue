<template>
    <div>
        <!-- 员工管理 -->
        <h1 style="margin-left: 20px;margin-top:10px">员工管理</h1>
        <Row style="margin: 20px;">
            <Col span="6">
                <span>员工姓名：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </Col>
            <Col span="6">
                <span>所属部门：</span>
                <Select v-model="researchInfo.deptId" placeholder="请选择所属部门" class="inputWidth">
                    <Option value="null">不限</Option>
                    <Option v-for="item in departments" :value="item.id" :key="item.id">{{item.name}}</Option>         
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
                <Button type="primary" size="small"  icon="ios-search" @click="showTable()">查询</Button>
                <Button type="error" size="small" @click="addNewStaff()">新增员工</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col> 
        </Row>
        <Row style="margin-left:20px;marginBottom:10px;">
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="researchInfo.startTime">
                </DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="researchInfo.endTime">
                </DatePicker>
            </Col>  
        </Row>
        <Table 
            border 
            ref="selection" 
            :columns="columns7" 
            :data="data6" 
            @on-selection-change="startdelete" 
            style="margin:20px;"
        ></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator :style="pageStyle"></Page>
        <Modal
            v-model="isAddStaff"
            :title="staffTitle"         
        >
            <Form ref="staffInfo" :model="staffInfo" :rules="ruleValidate" :label-width="120">
                <FormItem label="员工姓名：" prop="staffname">
                    <Input v-model="staffInfo.staffname" placeholder="请输入员工姓名"></Input>
                </FormItem>
                <FormItem label="所属部门：" prop="deptId">
                    <Select v-model="staffInfo.deptId" placeholder="请选择所属部门">
                        <Option v-for="item in departments" :value="item.id" :key="item.id">{{item.name}}</Option>         
                    </Select>
                </FormItem>
                <FormItem label="职位名称：" prop="positionId">
                    <Select v-model="staffInfo.positionId" placeholder="请选择担任职位">
                        <Option v-for="item in positions" :value="item.id" :key="item.id">{{item.name}}</Option>         
                    </Select>
                </FormItem>
                <FormItem label="入职时间：" prop="crtTime">
                    <DatePicker type="datetime" placeholder="请选择入职时间" style="width: 100%" v-model="staffInfo.crtTime">
                    </DatePicker>
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="submitStaffInfo('staffInfo')">确定</Button>
            </div> 
        </Modal>
    </div>
</template>

<script>
export default {
    name: 'staff-control',
    data(){
        return{
            researchInfo:{
                name:null,
                deptId:null,
                positionId:null,
                accountId:null,
                startTime:null,
                endTime:null
            },
            columns7:[
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    type: 'index',
                    align: 'center',
                    width: 60
                },
                {
                    title: '员工姓名',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '所属部门',
                    align: 'center',
                    render: (h,params) =>{
                        return h('div',params.row.dept.name)
                    }
                },
                {
                    title: '职位',
                    align: 'center',
                    render: (h,params) => {
                        return h('div', params.row.position.name)
                    }
                },
                {
                    title: '入职时间',
                    align: 'center',
                    key: 'crtTime',
                    render: (h,params) => {
                        let time = new Date(params.row.crtTime).format('yyyy-MM-dd hh:mm:s')
                        return h('div',time)
                    }
                }
            ],
            data6:[],
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
            positions:[],
            departments:[],
            deleteShow: false,
            deleteSelect:[],
            //modal info data
            isAddStaff: false,
            staffTitle: '',
            staffInfo: {
                staffname: null,
                deptId: null,
                positionId: null,
                crtTime: null
            },
            ruleValidate: {
                staffname: [
                    { required: true, message: '请输入员工姓名', trigger: 'blur' }
                ],
                deptId: [
                    { type: 'number', required: true, message: '请选择所属部门', trigger: 'change' }
                ],
                positionId: [
                    { type: 'number', required: true, message: '请选择职位', trigger: 'change' }
                ],
                crtTime: [
                    { type: 'date', required: true, message: '请选择入职时间', trigger: 'change' }
                ]
            }
        }
    },
    computed:{
        researches(){
            let research = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                deptId: this.researchInfo.deptId,
                positionId: this.researchInfo.positionId,
                accountId: this.researchInfo.accountId,
                startTime: new Date(this.researchInfo.startTime).getTime(),
                endTime: new Date(this.researchInfo.endTime).getTime()
            };
            return Object.assign({}, research);
        }
    },
    mounted(){
        this.getPositionList();
        this.getDepartmentList();
        this.showTable();
    },
    methods:{
        showTable(){
            this.axios.post('/staffs/datagrid',(this.researches)).then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.data6 = data.list;
                    this.page.pageTotal = data.total;
                }
            })
        },
        //弹出新增员工modal框
        addNewStaff(){
            this.isAddStaff = true
            this.staffTitle = '新增员工'
        },
        //提交新增员工信息
        submitStaffInfo(name){
            this.$refs[name].validate((valid) => {
                if(valid){
                    this.$Message.success('新增员工成功!');
                    this.isAddStaff = false
                }else{
                    this.$Message.error('新增员工失败!');
                }
            })
        },
        //选择删除
        startdelete(selection){
            if (selection.length != 0) {
                this.deleteShow = true;
                this.deleteSelect = selection;
            } else {
                this.deleteShow = false;
            }
        },
        alldelete(){

        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        //获取所有职位列表
        getPositionList(){     
            this.axios.get('/positions').then( res => {
                let {code,data} = res.data;
                if(code == 200){
                    this.positions = data;
                }
            })
        },
        //获取所有的部门
        getDepartmentList(){
            this.axios.get('/departments').then( res => {
                let {code,data} = res.data;
                if(code == 200){
                    this.departments = data;
                }
            })
        }
    }
}
</script>

<style scoped>
.inputWidth{
    width: 60%;
}
</style>


