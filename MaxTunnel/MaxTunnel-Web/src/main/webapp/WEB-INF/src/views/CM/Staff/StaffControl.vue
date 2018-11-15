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
                <span>个人账号：</span>
                <Input v-model="researchInfo.accountId" placeholder="支持模糊查询" class="inputWidth" />
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
            <Col span="12">
                <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                <Button type="error" size="small" @click="addNewStaff()">新增员工</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col> 
        </Row>
        <Table border ref="selection" :columns="columns7" :data="data6" @on-selection-change="startdelete" style="margin:20px;"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator class="pageStyle"></Page>
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
                    align: 'center'
                },
                {
                    title: '员工姓名',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '个人账号',
                    key: 'accountId',
                    align: 'center'
                },
            ],
            data6:[],
            page:{
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0 
            },
            positions:[],
            departments:[],
            deleteShow: false,
            deleteSelect:[]
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
                    let allinfo = [];
                    for( let index in data.list){
                        let info = {};
                        info.id = data.list[index].id;
                        info.name = data.list[index].name;
                        info.deptId = data.list[index].dept.id;
                        info.deptName = data.list[index].dept.name;
                        info.positionId = data.list[index].position.id;
                        info.positionName = data.list[index].position.name;
                        info.accountId = data.list[index].accountId;
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
            })
        },
        addNewStaff(){

        },
        startdelete(){

        },
        alldelete(){

        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        getPositionList(){        //获取所有职位列表
            this.axios.get('/positions').then( res => {
                let {code,data} = res.data;
                let _positions = [];
                if(code == 200){
                    for(let i = 0; i<data.length; i++){
                        let position = {};
                        position.id = data[i].id;
                        position.name = data[i].name;
                        _positions.push(position);
                    }
                    this.positions = _positions;
                }
            })
        },
        getDepartmentList(){
            this.axios.get('/departments').then( res => {
                let {code,data} = res.data;
                let _departments = [];
                if(code == 200){
                    for(let i = 0; i<data.length; i++){
                        let department = {};
                        department.id = data[i].id;
                        department.name = data[i].name;
                        _departments.push(department);
                    }
                    this.departments = _departments;
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
.pageStyle{
    text-align: right;
    margin-top: 100px;
    margin-right: 10px;
}
</style>


