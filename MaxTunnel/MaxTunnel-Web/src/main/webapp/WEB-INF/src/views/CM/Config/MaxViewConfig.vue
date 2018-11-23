<template>
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">MaxView终端配置</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>终端名称</span><span>：</span>
                <Input v-model="condition.name" placeholder="请输入监测对象名称" class="inputWidth"/>
            </Col>
            <Col span="6">
                <span>开始时间</span><span>：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="condition.startTime">
                </DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间</span><span>：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="condition.endTime">
                </DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary" size="small"  icon="ios-search" @click="search">查询</Button>
                <Button type="error" size="small" @click="add">新增终端</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col> 
        </Row>
        <div style="margin:20px;">
            <Table border ref="selection" :columns="columns7" :data="tableData" @on-selection-change="startdelete"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
            @on-change="handlePage" show-elevator class="pageStyle"></Page>
        </div>
        <div>
            <max-view-config-module v-bind="config" @addOrEdit="save"></max-view-config-module>
        </div>
    </div>
</template>

<script>
import { MaxViewService } from '../../../services/MaxViewService'
import MaxViewConfigModule from '../../CM/Config/MaxViewConfigModule.vue'

export default {
    name: "MaxViewConfig",
    data(){
        return {
            condition: {
                startTime: null,
                endTime: null,
                name: null
            },
            columns7:[
                {
                    type: 'selection',
                    width: 60,
                    align: 'center'
                },
                {
                    type: 'index',
                    width: 60,
                    align: 'center'
                },
                {
                    title: '终端名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: 'IP',
                    key: 'ip',
                    align: 'center'
                },
                {
                    title: '端口号',
                    key: 'port',
                    align: 'center'
                },
                {
                    title: '登录账号',
                    key: 'username',
                    align: 'center'
                },
                {
                    title: '创建时间',
                    key: 'crtTime',
                    align: 'center'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 100,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginLeft: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.row.id)
                                    }
                                }
                            }, '修改')
                        ]);
                    }
                }
            ],
            tableData:[],
            deleteShow: false,
            deleteSelect:[],
            page:{
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            config: {
                show: {
                    state: false
                },
                editInfo: null,
                type: null
            }
        }
    },
    computed:{
        searchParams(){
            let params = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.condition.name,
                startTime: this.condition.startTime,
                endTime: this.condition.endTime
            }
            return Object.assign({}, params)
        }
    },
    mounted(){
        this.search()       
    },
    methods:{
        search(){
            let _this = this
            MaxViewService.TerminalDatagird(this.searchParams).then(
                result=>{
                    _this.tableData = result.list
                    _this.tableData.forEach(data=>{
                        data.crtTime = new Date(data.crtTime).format('yyyy-MM-dd hh:mm:s')
                    })
                    _this.page.pageTotal = result.total
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.search();
        },
        startdelete(selection){
            if(selection.length != 0){
                this.deleteShow = true;
                this.deleteSelect = selection;
            }else{
                this.deleteShow = false;
            }
        },
        add() {
            this.config.show.state = !this.config.show.state
            this.config.type = 'add'
        },
        save(data) {
            let _this = this
            if(this.config.type == 'add'){
                MaxViewService.addTerminal(data).then(
                result=>{
                    _this.$Message.success('添加成功！');
                    _this.config.show.state = !_this.config.show.state
                    _this.search()
                },
                error=>{
                    _this.Log.info(error)
                })
            } else {
                MaxViewService.updateTerminal(data).then(
                    result=>{
                        _this.$Message.success('更新成功！');
                        _this.config.show.state = !_this.config.show.state
                        _this.search()
                    },
                    error=>{
                        _this.Log.info(error)
                    })
            }
            
        },
        edit(id) {
            let _this = this
            MaxViewService.getDetailById(id).then(
                result=>{
                    _this.config.editInfo = []
                    _this.config.editInfo = result
                    _this.config.type = 'edit'
                    _this.config.show.state = !_this.config.show.state
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        alldelete(){
            this.$Modal.confirm({
                title: '删除确认',
                content: '<p>确认要删除选中的信息吗？</p>',
                onOk: () => {
                    let ids=this.deleteSelect[0].id;
                    for(let i=1;i<this.deleteSelect.length;i++){
                        ids += "," + this.deleteSelect[i].id;
                    }
                    let _this = this
                    MaxViewService.batchDeleteTerminals(ids).then(
                        result=>{
                            _this.$Message.info('已删除')
                            _this.deleteShow = false
                            _this.search()
                        },
                        error=>{
                            _this.Log.info(error)
                        })  
                },
                onCancel: () => {
                    this.$Message.info('已取消操作');
                    this.search();
                }
            });
        }
    },
    components:{
        MaxViewConfigModule
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
.word63{
    letter-spacing: 1.5em;
    margin-right: -1.5em
}
.word64{
    letter-spacing: 0.67em;
    margin-right: -0.67em;
}
.word65{
    letter-spacing: 0.25em;
    margin-right: -0.25em;
}
</style>


