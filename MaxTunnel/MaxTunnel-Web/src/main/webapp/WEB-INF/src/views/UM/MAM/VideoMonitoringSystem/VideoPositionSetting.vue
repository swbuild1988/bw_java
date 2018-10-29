<template>
  <div>
  	<Row>
  		<Col span="2" offset="22"> 
  			<Button type="ghost" @click="back" class="backBtn">返回</Button>
  		</Col>
  	</Row>
    <Row>
    	<Col span="12" class="camera" v-if="isShow">
    		<object type='application/x-vlc-plugin' pluginspage="http://www.videolan.org/" events='true' height="100%" width="100%" id="vlc" wmode="transparent">
                <param name='mrl' :value="val" />
                <param name='wmode' value='transparent' />
                <param name='volume' value='50' />
                <param name='autoplay' value='true' />
                <param name='loop' value='false' />
                <param name='fullscreen' value='true' />
                <param name='controls' value='false' />
            </object>
    	</Col>
    	<Col span="9" offset="1">
    		<Row>
    			<h2 class="titleCtr">云台控制</h2>
    			<Col span="8" offset="4">
    				<VideoControls @startDirectCtrl="start" @stopDirectCtrl="stop"></VideoControls>
    			</Col>
    		</Row>
    		<h2 class="titlePos">预置位</h2>
    		<Button type="primary" @click="add" class="add" v-if="!isAdd">添加</Button>
    		<div class="posContent">	
	    		<div class="positions" v-for="(pos,index) in perPositions" :key="index">
	    			<span class="name">{{ pos }}</span>
	    			<div class="options">
	    				<Button type="primary" @click="set(pos)">Go</Button>
	    				<Button type="primary" icon="edit" @click="edit(pos)"></Button>
	    				<Button type="error" icon="trash-a" @click="del(pos)"></Button>
	    			</div>
	    		</div>
    		</div>
    		<Input type="text" v-model="name" class="text" v-if="isAdd" placeholder="请填写预置位名称"/>
    		<br>
			<Button type="primary" @click="addConfirm" v-if="isAdd" class="btn">确定</Button>
			<Button type="ghost" @click="cancel" class="cancel" v-if="isAdd">取消</Button>
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
import VideoControls from '../../../../components/UM/MAM/VideoControls'
import { VideoService } from '../../../../services/videos'
export default {
    name: "videoPositionSetting",
    data() {
    	return {
	        tunnelId: null,
	        // val: 'rtsp://admin:123456@192.168.3.202:554/h264/ch1/main/av_stream',
	        val: null,
	        perPositions: ['预置位1','预置位2','预置位3','预置位4','预置位预置5','预置位预置位预置位预置位预置位6'],
            curPosition: null,
            isShow: false,
            isAdd: false,
            name: null,
            isEdit: false,
            isDel: false
    	};
    },
    components: { VideoControls },
    mounted(){
  		this.tunnelId = this.$route.params.tunnelId
  		this.id = this.$route.params.cameraId
  		this.val = this.$route.params.url
  		this.getPositions()
    },
    watch: {
    	'val': function(){
    		this.isShow = true
    	}
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
    	getPositions() {
        let _this = this
        VideoService.getPresetsByCameraId(_this.id).then(
            (result)=>{
                _this.perPositions = []
                _this.perPositions = result
            })
    	// 	this.axios.get('videos/' + this.id + '/presets').then(res=>{
	  		// 	let { code,data } = res.data
	  		// 	if(code == 200){
	  		// 		this.perPositions = []
	  		// 		this.perPositions = data
	  		// 	}
	  		// })
    	},
  		back() {
  			this.$router.push('/UM/VideoMonitoring/details/' + this.tunnelId)
  		},
  		start(data) {
        let _this = this
          VideoService.cameraMove(_this.id,data.direction).then(
              (result)=>{
                  console.log('move success')
              },
              (error)=>{
                  console.log(error)
              })
  			// this.axios.get('videos/' + this.id + '/move/'+ data.direction).then(res=>{
     //            console.log("move success", res);
     //        })
  		},
  		stop(data) {
          let _this = this
          VideoService.cameraStop(_this.id).then(
              (result)=>{
                  console.log('stop success')
              },
              (error)=>{
                  console.log(error)
              })
  			  // this.axios.get('videos/'+ this.id +'/stop').then(res=>{
       //          console.log("stop success", res);
       //    })
  		},
  		indentChange(indent) {
            console.log('Scene '+indent)  
        },
        set(name) {
          let _this = this
          VideoService.goToPreset(_this.id,name).then(
              (result)=>{
                  console.log('setted')
              })
        	// this.axios.get('videos/'+ this.id +'/presets/' + name +'/goto').then(res=>{
        	// 	console.log('setted'+res.data)
        	// })
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
            VideoService.addPreset(_this.id,_this.addParam).then(
              (result)=>{
                _this.isAdd = false
                _this.getPositions()
              },
              (error)=>{
                _this.Log.info(error)
              })
        		// this.axios.post('videos/' + this.id + '/presets',this.addParam).then(res=>{
        		// 	let { code,data } = res.data
        		// 	if(code == 200){
        		// 		this.isAdd = false
        		// 		this.getPositions()
        		// 	}
        		// })
        	}
        },
        cancel()　{
        	this.isAdd = false
        	this.name = null
        },
        editOk() {
          let _this = this
            VideoService.editPreset(_this.id,_this.param).then(
              (result)=>{
                _this.isEdit = false
                _this.getPositions()
              },
              (error)=>{
                _this.Log.info(error)
              })
        	// this.axios.put('videos/' + this.id + '/presets',this.param).then(res=>{
         //    	let {code,data} = res.data
         //    	if(code == 200){
         //    		this.isEdit = false
         //    		this.getPositions()
         //    	}
         //    })
        },
        delOk() {
          let _this = this
            VideoService.deletePreset(_this.id,_this.param).then(
              (result)=>{
                _this.getPositions()
              },
              (error)=>{
                _this.Log.info(error)
              })
        	// this.axios.delete('videos/' + this.id + '/presets',{data: this.param}).then(res=>{
         //    	let {code,data} = res.data
         //    	if(code == 200){
         //    		this.getPositions()
         //    	}
         //    })
        }
	}
};
</script>
<style scoped>
	.camera{
		margin: 40px;
		height: 60vh;
	}
	.backBtn{
		margin: 10px;
		cursor: pointer;
	}
	.positions{
		/*background-color: #aaa;*/
		padding: 20px;
		width: 90%;
	}
	.indent{
		margin-right: 10px;
		margin-top: 2vh;
	}
	.small{
		margin-top: 10px;
	}
	.name{
		font-size: 16px;
	}
	.posContent{
		margin-top: 10px;
		max-height: 40vh;
		overflow-y: auto;
		width: 80%;
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
		margin-right: 20%;
		/*margin-top: 10px;*/
	}
	.titlePos{
		display: inline-block;
		/*margin-top: 10px;*/
	}
	.titleCtr{
		margin-bottom: 10px;
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
		margin-right: 10px;
	}
</style>