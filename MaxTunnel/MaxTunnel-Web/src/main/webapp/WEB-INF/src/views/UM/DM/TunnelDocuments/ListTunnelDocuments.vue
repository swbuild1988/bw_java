<template>
    <div class="allDiv">
        <div class="queryCondition">
            <Row>
                <Col span="6" class="condition">
                    <span class="queryText">文件名称：</span>
                    <Input v-model="conditions.documentName" placeholder="请输入文件名称" style="width: 60%"></Input>
                </Col>
                <Col span="6" class="condition">
                    <span class="queryText">文件类型：</span>
                    <Select v-model="conditions.fileType" style="width: 60%">
                        <Option value="null">所有</Option>
                        <Option v-for="type in selectList.fileTypes" :value="type.val" :key="type.val">{{ type.key }}</Option>
                    </Select>
                </Col>
                <Col span="6" class="condition">
                    <span class="queryText">资料类型：</span>
                    <Select v-model="conditions.docType" style="width: 60%">
                        <Option value="null">所有</Option>
                        <Option v-for="type in selectList.docTypes" :value="type.val" :key="type.val">{{ type.key }}</Option>
                    </Select>
                </Col>
                <Col span="6" class="condition">
                    <span class="queryText">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime">
                    </DatePicker>
                </Col>
                <Col span="6" class="condition">
                    <span class="queryText">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime">
                    </DatePicker>
                </Col>
                <Col span="2" class="condition">
                     <Button type="primary" @click="search" icon="ios-search">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
        	<Row>
        		<Col span="6" v-for="file in documents" :key="file.id" class="files">
        			<input v-if="showCheckBox" type="checkbox" class="checkbox" :value="file.id" v-model="ids">
        			<div class="fileItem">
        				<div class="name">
        					<Icon :type="file.fileTypeName == '文档' ? 'document' : (file.fileTypeName == '视频' ? 'ios-videocam' : 'image')" size="20"></Icon>
        					<span>{{ file.name }}</span>
        				</div>
        				<div class="docType">
        					<Icon type="briefcase"></Icon>
        					<span>{{ file.docTypeName }}</span>
        				</div>
        				<div class="time">
	        				<Icon type="android-time"></Icon>
	                        <span>{{ new Date(file.crtTime).format('yyyy-MM-dd') }}</span>
                        </div>
                        <div class="operations">
                        	<!-- <Tooltip content="预览">
                        		<a :href="'/api/files/download/' + file.id"><Icon type="ios-eye" color="#3399ff" size="28"></Icon></a>
                        	</Tooltip> -->
                        	<Tooltip content="下载">
                                <!-- Chrome -->
                        		<a :href="'/'+ApiUrl.split('/')[3]+'/files/download/' + file.id" :download="file.name" v-if="!IEbrowser"><Icon type="android-download" color="#3399ff" size="25"></Icon></a>
                                <!--  IE -->
                                <Icon :type="isLoading && file.id == curFile ? 'load-c' : 'android-download'" color="#3399ff" size="30" @click.native="download(file.id,file.name)" :class="[{'download': !isLoading},{'demo-spin-icon-load': isLoading && file.id == curFile}]" v-if="IEbrowser"></Icon>
                        	</Tooltip>
                        </div>
        			</div>
        		</Col>
        	</Row>
        </div>
<!--         预览
 -->        <!-- <Modal :title="view.title" v-model="visible">
	        <img v-if="view.type == '图片'" :src="view.src" width="480" height="300">
	        <div v-if="view.type == '视频'">
		        <object type='application/x-vlc-plugin' pluginspage="http://www.videolan.org/" events='true' height="300" width="480" id='vlc'>
	                <param name='mrl' :value="view.src" />
	                <param name='volume' value='50' />
	                <param name='autoplay' value='true' />
	                <param name='loop' value='false' />
	                <param name='fullscreen' value='false' />
	                <param name='controls' value='false' />
	            </object>
        	</div>
        	<div v-if="view.type == '文档'" class="text">
        		<span>{{ text }}</span>
        		<Spin fix v-if="isLoading">
	                <Icon type="load-c" size=18 class="demo-spin-icon-load"></Icon>
	                <div>Loading</div>
	            </Spin>
        	</div>
	    </Modal> -->
	    <Button v-if="!showCheckBox" type="error" @click="showCheckBox = true" class="delete">删除</Button>
	    <div class="confirm" v-if="showCheckBox">
	    	<Button type="primary" @click="del">确认</Button>
	    	<Button type="ghost" @click="cancel">取消</Button>
	    </div>
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
        </Page>
    </div>
</template>

<script>
import { EnumsService } from '../../../../services/enumsService'
import { FileService } from '../../../../services/fileService'
import types from '../../../../../static/Enum.json'
import $ from 'jquery'
export default {
    data(){
        return{
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            documents:[],
            conditions:{
            	documentName: null,
                fileType: null,
                docType: null,
                startTime: null,
                endTime: null
            },
            selectList: {
            	dcvTypes:[],
            	fileTypes:[]
            },
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
            },
            visible: false,
            view: {
            	title: null,
            	src: null,
            	type: null
            },
            showCheckBox: false,
            ids: [],
            text: null,
            isLoading: false,
            curFile: null,
            IEbrowser: null
        }
    },
    computed:{
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.documentName,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime(),
                fileType: this.conditions.fileType,
                docType: this.conditions.docType
            }
            return Object.assign({}, param)
        }
    },
    mounted(){
        this.initData()
        this.search()
        this.getBrowserType()
    },
    methods:{
        initData() {
            let _this = this;
            EnumsService.getFileType().then(
                function(result){
                    _this.selectList.fileTypes = result
                },
                function(error){
                  console.log(error)
            })
            EnumsService.getDocType().then(
                function(result){
                    _this.selectList.docTypes = result
                },
                function(error){
                  console.log(error)
            })
        },
        search() {
            let _this = this
            FileService.filesDataGrid(_this.params).then(
                function(result){
                    _this.documents = []
                    _this.documents = result.list
                    _this.page.pageTotal = result.total
                },
                function(error){
                  console.log(error)
            })
        },
        getBrowserType() {
            let userAgent = navigator.userAgent
            if(userAgent.indexOf('Chrome') > -1){
                this.IEbrowser = false
            }else{
                if(userAgent.indexOf("Trident") > -1 && userAgent.indexOf("rv:11.0") > -1){
                    this.IEbrowser = true
                }
            }
        },
        del() {
        	if(this.ids[0]){
        		let ids = this.ids.join(',')	
                let _this = this
                FileService.deleteFiles(ids).then(
                    function(result){
                        _this.showCheckBox = false
                        _this.ids = []
                        _this.search()
                    },
                    function(error){
                      console.log(error)
                })
        	}else{
        		this.$Message.error('请至少选择一项')
        	}
        	
        },
        handlePage(value) {
            this.page.pageNum = value
            this.search()
        },
        handlePageSize(value) {
            this.page.pageSize = value
            this.search()
        },
        handleView(name,id,type) {
        	this.visible = true
        	this.view.title = name
        	this.view.src = '/api/files/download/' + id
        	this.view.type = type
        	if(type == '文档'){
        		axios.get('files/download/' + id).then(res=>{
        			// this.text = res.data
        			this.isLoading = true
        			let _this = this
        			setTimeout(function(){
        				_this.isLoading = false
        				_this.text = res.data
        			},1000)
        		})
        	}
        },
        cancel() {
        	this.showCheckBox = false
        	this.ids = []
        },
        download(id,name) {
            //IE 
            this.isLoading = true
            this.curFile = id
            let _this = this
            FileService.downloadFile(id).then(
                function(result){
                    let buffer = result
                    let blobObject = new Blob([buffer])
                    window.navigator.msSaveBlob(blobObject, name)
                    _this.isLoading = false
                    },
                function(error){
                  this.$Message.error('下载失败')
            })
        }
    }
}
</script>

<style scoped>
.ivu-icon {
    margin-right: 5px;
    color: #ff9b00;
}
.condition{
	/*padding:6px;*/
}
.fileItem{
	border-bottom: 1px solid #dddfe1;
    /*border-right: 1px solid #dddfe1;*/
    width: 80%;
    margin: 10px auto; 
    border-radius: 4px;
    /*box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);*/
    position: relative;
    height: 200px;
    overflow: hidden;
   /* background-color: rgba(94,147,165,0.6);*/
   background-image: url('../../../../assets/UM/documents.jpg')
}
.name{
    margin-top: 56px;
	text-align: center;
	font-size: 20px;
    font-weight: bold;
	padding: 6px 0;
	max-height: 60px;
	overflow: hidden;
}
.format{
	display: inline-block;
	padding: 6px 10px;
}
.time{
	padding: 4px 10px;
	display: inline-block;
	text-align: right;
    margin-left: 4%;
    font-size: 16px;
}
.operations{
	position: absolute;
	bottom: 2px;
	right: 10px;
}
.delete{
	position: absolute;
    bottom: 20px;
    left: 15px;
}
.docType{
	display: inline-block;
	margin-left: 6px;
    font-size: 16px;
    padding: 10px 12px;
}
.files{
	position: relative;
}
.checkbox{
	position: absolute;
	top: 50%;
	left: 8px;
    width: 20px;
    height: 20px;
}
.confirm{
	position: absolute;
    bottom: 20px;
    left: 15px;
}
.text{
	width: 500px;
	height: 400px;
	overflow: auto;
}
.demo-spin-icon-load{
    animation: ani-demo-spin 1s linear infinite;
}
.download{
    cursor: pointer;
}
.queryText{
    /*font-size: 16px;*/
}
</style>

