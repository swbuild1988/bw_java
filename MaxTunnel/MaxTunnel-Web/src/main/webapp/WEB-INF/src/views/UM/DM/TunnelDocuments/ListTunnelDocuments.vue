<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="5">
                    <span class="conditionTitle">文件名称：</span>
                    <Input v-model="conditions.documentName" placeholder="请输入文件名称" style="width: 60%"/>
                </Col>
                <Col span="5">
                    <span class="conditionTitle">文件类型：</span>
                    <Select v-model="conditions.fileType" style="width: 60%">
                        <Option value="null">所有</Option>
                        <Option v-for="type in selectList.fileTypes" :value="type.val" :key="type.val">{{ type.key }}</Option>
                    </Select>
                </Col>
                <Col span="5">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime">
                    </DatePicker>
                </Col>
                <Col span="5">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime">
                    </DatePicker>
                </Col>
                <Col span="4">
                     <Button type="primary" @click="resetPageAndSearch" icon="ios-search" >查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <div class="nullData" v-show="isNullData">暂无数据</div>
        	<Row>
        		<Col span="6" v-for="file in documents" :key="file.id" class="files">
        			<input v-if="showCheckBox" type="checkbox" class="checkbox" :value="file.id" v-model="ids">
        			<div class="fileItem">
                        <div style="display: table-cell;vertical-align: middle;text-align: center;">
        				<div class="name">
        					<Icon :type="file.fileTypeName == '文档' ? 'document' : (file.fileTypeName == '视频' ? 'ios-videocam' : 'image')"></Icon>
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
                        </div>
                        <div class="operations">
                        	<Tooltip content="下载">
                                <!-- Chrome -->
                        		<a :href="'/'+ApiUrl.split('/')[3]+'/files/download/' + file.id" :download="file.name" v-if="!IEbrowser"><Icon type="android-download" color="#3399ff" class="downloadIcon"></Icon></a>
                                <!--  IE -->
                                <Icon :type="isLoading && file.id == curFile ? 'load-c' : 'android-download'" color="#3399ff" size="30" @click.native="download(file.id,file.name)" :class="[{'download': !isLoading},{'demo-spin-icon-load': isLoading && file.id == curFile}]" v-if="IEbrowser"></Icon>
                        	</Tooltip>
                        </div>
        			</div>
        		</Col>
        	</Row>
        </div>
	    <Button v-if="!showCheckBox" type="error" @click="showCheckBox = true" class="delete">删除</Button>
	    <div class="confirm" v-if="showCheckBox">
	    	<Button type="primary" @click="del">确认</Button>
	    	<Button type="ghost" @click="cancel">取消</Button>
	    </div>
        <div class="page">
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle">
            </Page>
        </div>
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
                docTypeSon: null,
                startTime: null,
                endTime: null
            },
            selectList: {
            	fileTypes:[]
            },
            pageStyle: {
                position: 'absolute',
                bottom: '20px',
                right: '15px'
            },
            showCheckBox: false,
            ids: [],
            text: null,
            isLoading: false,
            curFile: null,
            IEbrowser: null,
            isNullData: false
        }
    },
    watch:{
        '$route': function () {
            let ids = this.$route.params.id.split('.')
            this.conditions.docType = ids[0]
            this.conditions.docTypeSon = ids[1] ? ids[1] : null
            this.search()
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
                docType: +this.conditions.docType,
                docTypeSon: +this.conditions.docTypeSon
            }
            return Object.assign({}, param)
        }
    },
    mounted(){
        let ids = this.$route.params.id.split('.')
        this.conditions.docType = ids[0]
        this.conditions.docTypeSon = ids[1] ? ids[1] : null
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
        },
        resetPageAndSearch(){
            this.page.pageNum = 1
            this.search()
        },
        search() {
            let _this = this
          if(new Date(_this.conditions.startTime)>new Date(_this.conditions.endTime)){
            _this.$Message.error('开始时间必须小于结束时间！');
            return;
          }
            FileService.filesDataGrid(_this.params).then(
                function(result){
                    _this.documents = []
                    _this.isNullData = result.list.length ? false : true
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
                        _this.resetPageAndSearch()
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
            this.resetPageAndSearch()
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
    height: 20vh;
    overflow: hidden;
   /* background-color: rgba(94,147,165,0.6);*/
   background-image: url('../../../../assets/UM/documents.jpg');
   background-size: 100% 100%;
   display: table;
}
.name{
    /*margin-top: 56px;*/
	text-align: center;
	font-size: 2vmin;
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
    font-size: 1.66vmin;
}
.operations{
	position: absolute;
	bottom: 2%;
	right: 4%;
}
.delete{
	position: absolute;
    bottom: 20px;
    left: 15px;
}
.docType{
	display: inline-block;
	margin-left: 6px;
    font-size: 1.66vmin;
    padding: 4% 4%;
}
.files{
	position: relative;
}
.checkbox{
	position: absolute;
	top: 50%;
	left: 0.8vmin;
    width: 2vmin;
    height: 2vmin;
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
    font-size: 3vmin;
}
.downloadIcon{
    font-size: 2.5vmin;
}

/*select*/
.conditions >>> .ivu-select-selected-value{
    font-size: 1.28vmin !important;
    height: 2.57vmin !important;
    line-height: 1.2rem !important;
    padding-top: 0.64vmin !important;
}

.conditions >>> .ivu-select-placeholder{
    font-size: 1.28vmin !important;
    height: 2.57vmin !important;
    line-height: 2vmin !important;
    padding-top: 0.64vmin !important;
}

.conditions >>> .ivu-select-selection{
    height: 3.2vmin;
}
/*input*/
.conditions >>> .ivu-input {
    height: 3.2vmin;
    font-size: 1.28vmin;
}

 /*日期选择*/
.conditions >>> .ivu-date-picker-header {
    height: 3.2vmin;
    line-height: 3.2vmin;
}

.conditions >>> .ivu-picker-panel-icon-btn{
    font-size: 1.66vmin;
    width: 1.28vmin;
    height: 2.5vmin;
}

.conditions >>> .ivu-date-picker-header-label{
    font-size: 1.66vmin;
}

@media (min-width: 1921px){
    .conditions >>> .ivu-date-picker-cells {
        width: 15vmin;
        font-size: 1.66vmin;
    }

    .conditions >>> .ivu-date-picker-cells-cell{
        width: 2vmin;
    }
    .conditions >>> .ivu-date-picker-cells-header span{
        padding-right: 2.5rem;
    }
    .conditions >>> .ivu-date-picker-cells span em{
        width: 2.4vmin;
        height: 2.4vmin;
    }
}
.page >>> .ivu-select-selection{
    height: 3.2vmin;
}
.page >>> .ivu-select-selected-value{
    font-size: 1.2vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.page >>> .ivu-select-placeholder{
    font-size: 1.2vmin;
    height: 3vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-options-elevator input{
    font-size: 1.2vmin;
    height: 3vmin;
}
.page >>> .ivu-page-options-elevator{
    display: inline-block;
    height: 3.2vmin;
    line-height: 3.2vmin;
}
.page >>> .ivu-page-next{
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-next .ivu-icon{
    font-size: 1.6vmin;
}
.page >>> .ivu-page-prev{
    height: 3.2vmin;
    line-height: 3vmin;
}
.page >>> .ivu-page-prev .ivu-icon{
    font-size: 1.6vmin;
}
</style>

