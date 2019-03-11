<template>
  <div>
  	<Row>
  		<Col span="2" offset="22"> 
  			<Button type="ghost" @click="back" class="backBtn" style="background-color: #f0f0f0">返回</Button>
  		</Col>  
  	</Row>
    <Row>
    	<Col span="12" offset="1">
            <div class="cameraList">
                <span style="font-size: 1.8vmin;font-weight: bold;color:#fff;">摄像头：</span>
                <Select v-model="curVideo.areaId" id="cameras" @on-change="getCameras" class="select" placeholder="请选择区域">
                    <Option v-for="area in areaList" :value="area.id" :key="area.id" class="option">{{ area.name }}</Option>
                   <!--  <Option value="" key="0" class="option">空</Option> -->
                </Select>
                <Select v-model="curVideo.storeId" id="cameras" @on-change="getCameras" class="select" placeholder="请选择监测仓">
                    <Option v-for="store in storeList" :value="store.id" :key="store.id" class="option">{{ store.name }}</Option>
                </Select>
                <Select v-model="curVideo.id" id="cameras" @on-change="changVideo" class="select">
                    <Option v-for="camera in cameraList" :value="camera.id" :key="camera.id" class="option">{{ camera.name }}</Option>
                    <!-- <Option value="" key="0" class="option">空</Option> -->
                </Select>
            </div>
    		    <div class="camera">
                <video-component v-bind:video="curVideo" v-bind:id="'positionSetting'"></video-component>
            </div>
    	</Col>
    	<Col span="9" offset="1">
    		<Row class="controls">
    			<div class="titleCtr">云台控制</div>
    			<Col span="8" offset="4">
            <div class="controlContent">
    				  <video-control @startDirectCtrl="start" @stopDirectCtrl="stop" v-bind:isDisabled="isDisabled" style="margin-top: 10px"></video-control>
            </div>
    			</Col>
    		</Row>
        <div class="posBox">
      		<div class="titlePos">预置位</div>
      		<Button type="primary" @click="add" class="add">添加</Button>
      		<div class="posContent">	
  	    		<div class="positions" v-for="(pos,index) in perPositions" :key="index">
              <!-- <div class="posTag"></div> -->
  	    			<span class="name">{{ pos }}</span>
  	    			<div class="options">
  	    				<Button type="primary" size="small" @click="set(pos)">到达预置位</Button>
  	    				<Button type="primary" size="small" icon="edit" @click="edit(pos)"></Button>
  	    				<Button type="error" size="small" icon="trash-a" @click="del(pos)"></Button>
  	    			</div>
  	    		</div>
      		</div>
        </div>
        <Modal v-model="isAdd" title="添加预置位" @on-ok="addConfirm" @on-cancel="cancel">
          <Input type="text" v-model="name" class="text" v-if="isAdd" placeholder="请填写预置位名称"/>
        </Modal>
    		<!-- <Input type="text" v-model="name" class="text" v-if="isAdd" placeholder="请填写预置位名称"/>
    		<br>
			<Button type="primary" @click="addConfirm" v-if="isAdd" class="btn">确定</Button>
			<Button type="ghost" @click="cancel" class="cancel" v-if="isAdd">取消</Button> -->
    	</Col>
    </Row>
    <Modal v-model="isEdit" title="确认" @on-ok="editOk" :styles="{top: '80px', left: '20%',width: '300px'}">
        <p>确定修改当前位置为{{ curPosition }}吗？</p>
    </Modal>
    <Modal v-model="isDel" title="确认" @on-ok="delOk" :styles="{top: '80px', left: '20%',width: '300px'}">
        <p>确定删除{{ curPosition }}吗？</p>
    </Modal>
  </div>
</template>

<script>
import VideoControl from '../../../../components/UM/MAM/videoControls/VideoControl'
import { VideoService } from '../../../../services/videoService'
import { TunnelService } from '../../../../services/tunnelService' 
import VideoComponent from "../../../../components/Common/Video/VideoComponent"

export default {
    name: "videoPositionSetting",
    data() {
    	return {
	        // val: 'rtsp://admin:123456@192.168.3.202:554/h264/ch1/main/av_stream',
	        perPositions: [],
            curPosition: null,
            isShow: false,
            isAdd: false,
            name: null,
            isEdit: false,
            isDel: false,
            curVideo: {
                id: null,
                url: '',
                tunnelId: null,
                storeId: null,
                areaId: null
            },
            cameraList: [],
            areaList: [],
            storeList: [],
            isDisabled: null,
    	};
    },
    components: { VideoControl,VideoComponent },
    mounted(){
  		  this.curVideo.tunnelId = this.$route.params.camera.tunnelId 
        console.log(this.curVideo.tunnelId)
        let camera = this.$route.params.camera
        this.isDisabled = !camera.positionSupport
        this.getAreaAndStores()
    },
    computed:{
        param() {
        	return {
        		presetName: this.curPosition
        	}
        },
        addParam() {
        	return {
        		presetName: this.name
        	}
        }
    },
    methods:{
        getAreaAndStores(){
          let _this = this
          Promise.all([TunnelService.getStoresByTunnelId(this.curVideo.tunnelId),TunnelService.getAreasByTunnelId(this.curVideo.tunnelId)]).then(
            result=>{
              _this.storeList = result[0]
              _this.areaList = result[1]
              _this.curVideo.areaId = _this.$route.params.camera.areaId
              _this.curVideo.storeId = _this.$route.params.camera.storeId
              _this.getCameras()
            },
            error=>{
              _this.Log.info(error)
            })
        },
        getCameras() {
            let _this = this
            let conditions = {
              tunnelId: this.curVideo.tunnelId,
              storeId: this.curVideo.storeId,
              areaId: this.curVideo.areaId
            }
            VideoService.getCamerasByConditions(conditions).then(
                result => {
                    _this.cameraList = result
                    _this.curVideo = _this.$route.params.camera
                    _this.getPositions()
                },
                error => {
                    _this.Log.info(error)
                }
            )
        },
    	getPositions() {
        let _this = this
        VideoService.getPresetsByCameraId(_this.curVideo.id).then(
            result=>{
                _this.perPositions = []
                _this.perPositions = result
            },
            error=>{
              _this.Log.info(error)
            })
    	},
  		back() {
  			this.$router.push('/UM/VideoMonitoring/details/' + this.curVideo.tunnelId)
  		},
  		start(data) {
        let _this = this
          VideoService.cameraMove(_this.curVideo.id,data.direction).then(
              (result)=>{
                  _this.Log.info('move success')
              },
              (error)=>{
                  _this.Log.info(error)
              })
  		},
  		stop(data) {
          let _this = this
          VideoService.cameraStop(_this.curVideo.id).then(
              result=>{
                  _this.Log.info('stop success')
              },
              error=>{
                  _this.Log.info(error)
              })
  		},
  		indentChange(indent) {
            console.log('Scene '+indent)  
        },
        set(name) {
          let _this = this
          VideoService.goToPreset(_this.curVideo.id,name).then(
              result=>{
                  _this.Log.info('setted')
              },
              error=>{
                  _this.Log.info(error)
              })
        },
        del(name) {
        	this.isDel = true
        	this.curPosition = name
        },
        edit(name) {
        	this.isEdit = true
        	this.curPosition = name
        },
        add() {
        	this.isAdd = true
        },
        addConfirm() {
        	let addFlag = true
        	this.perPositions.forEach(pos=>{
        		if(pos.name == this.name){
        			this.$Message.error({
	                    content: '名称已被占用，请输入其他名称',
	                    duration: 5,
	                })
	                addFlag = false
        		}
        	})
        	if(addFlag){
            let _this = this
            VideoService.addPreset(_this.curVideo.id,_this.addParam).then(
              (result)=>{
                _this.isAdd = false
                _this.getPositions()
              },
              (error)=>{
                _this.Log.info(error)
              })
        	}
        },
        cancel()　{
        	this.isAdd = false
        	this.name = null
        },
        editOk() {
          let _this = this
            VideoService.editPreset(_this.curVideo.id,_this.param).then(
              (result)=>{
                _this.isEdit = false
                _this.getPositions()
              },
              (error)=>{
                _this.Log.info(error)
              })
        },
        delOk() {
          let _this = this
            VideoService.deletePreset(_this.curVideo.id,_this.param).then(
              (result)=>{
                _this.getPositions()
              },
              (error)=>{
                _this.Log.info(error)
              })
        },
        changVideo()　{
            let curVideoInfo = this.cameraList.find(camera=>{
                return camera.id == this.curVideo.id
            })
            // this.curVideo.url = curVideoInfo.url
            if(curVideoInfo.ptzOperationsSupported){
                this.isDisabled = false
            }else{
                this.isDisabled = true
            }
            this.getPositions()
        }
	}
};
</script>
<style scoped>
	.camera{
		margin-top: 30px;
    padding: 6%;
    background: url('../../../../assets/UM/videoSettingBody.png') no-repeat;
    background-size: 100% 100%;
    height: 66vh;
	}
	.backBtn{
		margin: 10px;
		cursor: pointer;
	}
	.positions{
		/*background-color: rgb(53,122,163);*/
		padding: 1vmin 1vmin 1vmin 2vmin;
    color: #fff;
    margin: 1vmin;
    position: relative;
    background: url('../../../../assets/UM/border.png') no-repeat;
    background-size: 100% 100%;
    /*border-radius: 6px;*/
    line-height: 2.6vmin;
	}
	.indent{
		margin-right: 10px;
		margin-top: 2vh;
	}
	.small{
		margin-top: 10px;
	}
	.name{
		font-size: 1.66vmin;
    margin-left: 0.8vmin;
	}
	.posContent{
		margin-top: 5vh;
    margin-bottom: 10px;
		max-height: 40vh;
		overflow-y: auto;
		width: 88%;
    margin-left: 6%;

	}
	.set{
		float: right;
		margin-right: 10%;
	}
	.options{
		float: right;
	}
	.add{
		float: right;
		margin: 5% 5% 0 0;
	}
	.titlePos{
		margin: 10px;
    font-size: 1.8vmin;
    font-weight: bold;
    background: url('../../../../assets/UM/title.png') no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    position: absolute;
    width: 42%;
    height: 4vmin;
    top: -10px;
    left: -6px;
    padding: 8px 0;
	}
	.titleCtr{
    margin: 10px;
    font-size: 1.8vmin;
    font-weight: bold;
    position: absolute;
    width: 42%;
    height: 4vmin;
    top: -20px;
    left: -10px;
    background: url('../../../../assets/UM/title.png') no-repeat;
    background-size: 100% 100%;
    color: #fff;
    text-align: center;
    padding: 8px 0;
	}
	.edit{
		margin-top: 8vh;
	}
	.text{
		width: 62%;
		margin-top: 10px;
		margin-left: 5%;
	}
	.btn{
		margin-top: 10px;
		float: right;
		margin-right: 32%;
	}
	.cancel{
		float: right;
		margin-top: 10px;
		margin-right: 4vw;
	}
    .cameraList{
        height: 6vh;
        padding: 1.2vmin;
       /* width: 60%;*/
        background: url('../../../../assets/UM/title.png') no-repeat;
        background-size: 100% 100%;
        text-align: center;
    }
    .changVideo{
        float: right;
    }
    .select{
        width:10vw;
        z-index: 9999;
    }
    .posBox{
      background: url('../../../../assets/UM/videoSettingBody.png') no-repeat;
      background-size: 100% 100%;
      margin-top: 10px;
      border-radius: 6px;
      height: 50vh;
      position: relative;
      overflow: hidden;
    }
    .posTag{
      position: absolute;
      background-color: rgb(185,121,144);
      height: 100%;
      width: 6px;
      top: 0;
      left: 0;
      border-radius: 6px;
    }
    .controls{
      /*background-color: rgba(37,81,153,0.3);*/
      /*background-color: #f0f0f0;*/
      background: url('../../../../assets/UM/videoSettingBody.png') no-repeat;
      background-size: 100% 100%;
      height: 24vh;
      position: relative;
    }
    .controlContent{
      margin-top: 16%;
      height: 20vh;
      width: 100%;
    }

    .cameraList >>> .ivu-select-selected-value{
        font-size: 1.28vmin !important;
        height: 2.57vmin !important;
        line-height: 2vmin !important;
        padding-top: 0.64vmin !important;
        width: 90% !important;
    }
    .cameraList >>> .ivu-select-placeholder{
        font-size: 1.28vmin !important;
        height: 2.57vmin !important; 
        line-height: 2vmin !important;
        padding-top: 0.64vmin !important;
        width: 90% !important;
    }

    .cameraList >>> .ivu-select-selection{
        height: 3.2vmin !important;
    }
</style>