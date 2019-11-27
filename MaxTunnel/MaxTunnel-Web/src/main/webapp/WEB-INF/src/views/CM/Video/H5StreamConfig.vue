<template>
    <div>
        <h1 style="margin: 10px 0px 0px 20px;">H5Stream配置</h1>
        <Row style="margin:20px;">
            <Col span="6">
                <span>ID：</span>
                <Input placeholder="请输入ID" v-model="h5StreamConditions.id" class="inputWidth"></Input>
            </Col>
            <Col span="6">
                <span>供应商：</span>
                <Input placeholder="请输入供应商" v-model="h5StreamConditions.vendor" class="inputWidth"></Input>
            </Col>
            <Col span="6"></Col>
            <Col span="9" style="font-size: 1.6vmin">
                <Button type="success" @click="showTable()">查询</Button>
                <vue-xlsx-table @on-select-file="getInsertData">插入H5Stream配置文件</vue-xlsx-table>
                <vue-xlsx-table @on-select-file="getData">重新选择H5Stream配置文件</vue-xlsx-table>
                <ExportCSV :header="H5StreamConfigHeader" :data="H5StreamConfigCSV" :fileName="H5StreamConfigFileName"></ExportCSV>
                <Button type="error" v-on:click="save" style="margin-left: 20px" v-show="saveFlag">重选保存</Button>
                <Button type="error" v-on:click="insert" style="margin-left: 20px" v-show="insertFlag">插入保存</Button>
            </Col>
        </Row>
        <div style="margin:20px;">
            <Table :columns="columnsTable" :data="tableData" v-show="isTable"></Table>
            <Table stripe border :columns="columns" :data="videoConfig" v-show="isAdd"></Table>
            <Table stripe border :columns="columns" :data="insertVideoConfig" v-show="isInsert"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
                  placement="top" @on-change="handlePage" @on-page-size-change="handlePageSize" show-elevator
                  :style="pageStyle"></Page>
        </div>
        <Modal
            v-model="isEdit"
            title="修改H5Stream配置"
        >
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="IP" prop="ip">
                    <Input v-model="formValidate.ip" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="端口" prop="port">
                    <Input v-model="formValidate.port" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="用户名" prop="username">
                    <Input v-model="formValidate.username" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="密码" prop="password">
                    <Input v-model="formValidate.password" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="供应商" prop="vendor">
                    <Input v-model="formValidate.vendor" class="inputWidth"></Input>
                </FormItem>
                <FormItem label="频道" prop="channelNo">
                    <Input v-model="formValidate.channelNo" class="inputWidth"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="submitEdit('formValidate')">保存</Button>
            </div>
        </Modal>    
    </div>    
</template>
<script>
import { VideoService } from '../../../services/videoService'
import ExportCSV from "@/components/UM/OMM/exportCSV.vue"
export default {
    data(){
        return{
            h5StreamConditions: {
                id: null,
                vendor: null
            },
            page: {
                pageTotal: 0,
                pageNum: 1,
                pageSize: 10
            },
            pageStyle:{
                position: 'absolute',
                bottom: '35px',
                right: '40px'
            },
            videoConfig:[],
            insertVideoConfig: [],
            tableData: [],
            columns:[
                {
                    title: 'ID',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: 'IP',
                    key: 'ip',
                    align: 'center'
                },
                {
                    title: '端口',
                    key: 'port',
                    align: 'center'
                },
                {
                    title: '用户名',
                    key: 'username',
                    align: 'center'
                },
                {
                    title: '供应商',
                    key: 'vendor',
                    align: 'center'
                },
                {
                    title: '频道',
                    key: 'channelNo',
                    align: 'center'
                }
            ],
            columnsTable: [
                {
                    title: 'ID',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: 'IP',
                    key: 'ip',
                    align: 'center'
                },
                {
                    title: '端口',
                    key: 'port',
                    align: 'center'
                },
                {
                    title: '用户名',
                    key: 'username',
                    align: 'center'
                },
                {
                    title: '供应商',
                    key: 'vendor',
                    align: 'center'
                },
                {
                    title: '频道',
                    key: 'channelNo',
                    align: 'center'
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        console.log(params.row)
                                        this.editH5StreamConfig(params.row.id)
                                    }
                                }
                            }, '修改')
                        ]);
                    }
                }
            ],
            saveFlag: false,
            insertFlag: false,
            H5StreamConfigHeader: [
                {label: 'ID', prop: "id"},
                {label: 'IP', prop: 'ip'},
                {label: '端口', prop: 'port'},
                {label: '用户名', prop: 'username'},
                {label: '密码', prop: 'password'},
                {label: '供应商', prop: 'vendor'},
                {label: '频道', prop: 'channelNo'}
            ],
            H5StreamConfigCSV: [],
            H5StreamConfigFileName: 'H5Stream配置文件',
            isAdd: false,
            isInsert: false,
            isTable: true,
            isEdit: false,
            configInfo: null,
            formValidate: {
                id: null,
                ip: null,
                port: null,
                username: null,
                password: null,
                vendor: null,
                channelNo: null
            },
            ruleValidate: {}
        }
    },
    components: { ExportCSV },
    mounted(){
        this.getDownloadData()
        this.showTable()
    },
    methods:{
        getData(data){
            this.videoConfig = []
            data.body.forEach(config=>{
                let temp = {}
                temp.id = parseInt(config[data.header[0]]),
                temp.ip = config[data.header[1]],
                temp.port = config[data.header[2]],
                temp.username = config[data.header[3]],
                temp.password = config[data.header[4]],
                temp.vendor = config[data.header[5]],
                temp.channelNo = config[data.header[6]]
                this.videoConfig.push(temp)
            })
            this.isAdd = true
            this.isInsert = false
            this.isTable = false
            this.saveFlag = true
            this.insertFlag = false
        },
        save() {
            let _this = this
            this.Log.info(this.videoConfig)
            VideoService.batchAddH5StreamConfig(this.videoConfig).then(
                result=>{
                    _this.$Message.success("添加成功！")
                    _this.saveFlag = false
                    _this.getDownloadData()
                    this.showTable()
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        getInsertData(data){
            this.saveFlag = false
            this.insertVideoConfig = []
            data.body.forEach(config=>{
                let temp = {}
                temp.id = parseInt(config[data.header[0]]),
                temp.ip = config[data.header[1]],
                temp.port = config[data.header[2]],
                temp.username = config[data.header[3]],
                temp.password = config[data.header[4]],
                temp.vendor = config[data.header[5]],
                temp.channelNo = config[data.header[6]]
                this.insertVideoConfig.push(temp)
            })
            this.isAdd = false
            this.isInsert = true
            this.isTable = false
            this.insertFlag = true
        },
        insert(){
            VideoService.insertH5StreamConfig(this.insertVideoConfig).then(
                result => {
                    this.$Message.success("添加成功！")
                    this.insertFlag = false
                    this.getDownloadData()
                    this.showTable()
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        getDownloadData(){
            VideoService.getDownloadData().then(
                result => {
                    this.H5StreamConfigCSV = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //获取分页数据
        showTable(){
            let params = {
                id: this.h5StreamConditions.id,
                vendor: this.h5StreamConditions.vendor,
                pageSize: this.page.pageSize,
                pageNum: this.page.pageNum
            }
            VideoService.getH5Data(params).then(
                result => {
                    this.page.pageTotal = result.total
                    this.tableData = result.pagedList
                    this.isTable = true
                    this.isAdd = false
                    this.isInsert = false
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        handlePage(value){
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value){
            this.page.pageSize = value;
            this.showTable();
        },
        editH5StreamConfig(id){
            this.isEdit = true
            VideoService.getInfo(id).then(
                result => {
                    this.formValidate = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        submitEdit(){
            VideoService.submitEdit(this.formValidate).then(
                result => {
                    this.$Message.success("修改成功！")
                    this.isEdit = false
                    this.showTable()
                },
                error => {
                    this.$Message.error("修改失败！")
                    this.Log.info(error)
                }
            )
        }
    }
}
</script>
<style scoped>
.download{
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    border: 1px solid #20a0ff;
    -webkit-appearance: none;
    text-align: center;
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    padding: 7px 9px;
    font-size: 12px;
    border-radius: 4px;
    color: #fff;
    background: #f90 !important;
    border: none !important;
}
.inputWidth{
    width: 60%;
}
</style>

