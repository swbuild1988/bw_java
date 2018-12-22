<template>
  <div>
    <Row class="query">
    	<Col span="9" offset="1">
	        <span>开始时间：</span>
	        <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="videoSelect.startTime">
	        </DatePicker>
	    </Col>
	    <Col span="9">
	        <span>结束时间：</span>
	        <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="videoSelect.endTime">
	        </DatePicker>
	    </Col>
	    <Col span="3">
	        <Button type="primary" icon="ios-search" @click="searchVideo">查询</Button>
	    </Col>
	    <Col span="1" offset="1">
	    	<Button type="ghost" @click="back">返回</Button>
	    </Col>
    </Row>
    <Row class="videos">
    	<Col span="16">
    		<object type='application/x-vlc-plugin' pluginspage="http://www.videolan.org/" events='true' height="560" width="100%" id='vlc'>
                <param name='mrl' value="" />
               <!--  <param name='wmode' value='transparent' /> -->
                <param name='volume' value='50' />
                <param name='autoplay' value='false' />
                <param name='loop' value='false' />
                <param name='fullscreen' value='false' />
                <param name='controls' value='false' />
            </object>
            <Row class="controls">
            	<Col span="2">
            		<Button type="primary" :icon="isPlay ? 'pause' : 'play'" @click="doPlayOrPause"></Button>
            	</Col>
            	<Col span="13">
	            	<div class="progress">
		            	<Button type="primary" icon="ios-skipbackward" @click="skipBack" class="back"></Button>
		            	<span class="curTime">{{ progress.curTime }}</span>
		        	    <Slider v-model="progress.percent"  class="process" @on-change="slide" :tip-format="format"></Slider>
		        	    <span class="totalTime">{{ progress.totalTime }}</span>
		        	    <Button type="primary" icon="ios-skipforward" @click="skipFor" class="forward"></Button>
		        	</div>
        	    </Col>
        	    <Col align="right" class="speed">
        	    	<Select v-model="speed" style="width:100px" @on-change="speedChange">
        	    		<Option value="2">2倍</Option>
				        <Option value="1">正常</Option>
				        <Option value="0.5">0.5倍</Option>
				    </Select>
				</Col>
				<Col span="8" align="right" class="sound">
				    <!-- <ButtonGroup>
				    	<Button type="primary" @click="soundChange(-10)">
				            <Icon type="minus"></Icon>
				            音
				        </Button>
				        <Button type="primary" @click="soundChange(10)">
				            量
				            <Icon type="plus"></Icon>
				        </Button>
				    </ButtonGroup> -->
				    <Icon type="volume-medium" class="volume" size="25"></Icon>
				    <Slider v-model="volume"  class="slide" @on-change="volumeChanged"></Slider>
        	    </Col>
            </Row>
    	</Col>
    	<Col span="8" class="list">
    		<div v-for="video in videoList" :key="video.id" @click="playVideo(video.url)">
    			<Row>
    				<Col span="10">
    					<img :src="robotTop" class="pics">
    			    </Col>
    			    <Col span="14" class="info">
    			    	<h2>{{ video.name }}</h2>
    			    	<p>2018.04.05 00:00:00 - 2018.04.05 00:10:00</p>
    			    	<p>10:05</p>
    			    </Col>
    			</Row>
    		</div>
    	</Col>
    </Row>
  </div>
</template>

<script>
import robotTop from '../../../../assets/UM/robotTop.png'
import $ from 'jquery'
export default {
    name: "videoMonitoringHistory",
    data() {
    	return {
	        videoSelect: {
	            startTime: null,
	            endTime: null
	        },
	        videoList:[
	        	{
	        		id: 1,
	        		name: '视频1',
	        		url: 'https://g1.ykimg.com/051000005B42CE5BAD9AB79E26008846'
	        	},
	        	{
	        		id: 2,
	        		name: '视频2',
	        		url: 'https://g1.ykimg.com/051000005B42CE5BAD9AB79E26008846'
	        	},
	        	{
	        		id: 3,
	        		name: '视频3',
	        		url: 'https://g1.ykimg.com/051000005B42CE5BAD9AB79E26008846'
	        	},
	        	{
	        		id: 4,
	        		name: '视频4',
	        		url: 'https://g1.ykimg.com/051000005B42CE5BAD9AB79E26008846'
	        	}
	        ],
	        robotTop: robotTop,
	        curVideo: 'https://g1.ykimg.com/051000005B42CE5BAD9AB79E26008846',
	        isPlay: false,
	        progress: {
	        	curTime: '00:00:00',
	        	totalTime: '00:00:00',
	        	percent: 0
	        },
	        speed: '1',
	        volume: 50,
	        tunnelId: null
    	};
    },
    mounted(){
  		this.tunnelId = this.$route.params.tunnelId
    },
    methods:{
  		searchVideo() {
        	console.log('一段时间内的视频')
    	},
    	playVideo(url) {
    		console.log('changed')
    		this.curVideo = url
    	},
    	doPlayOrPause() {
    		let vlc = document.getElementById("vlc")
    		if(this.isPlay){
    			// vlc.playlist.stop();
    			vlc.playlist.togglePause()
 				this.isPlay = false
    		}else if(this.progress.percent){
    			if(this.progress.totalTime == this.progress.curTime){
    				this.progress.percent = 0
    				this.progress.curTime = null
    			}
    			vlc.playlist.play()
    			setTimeout(this.checkVedioStatus,500)
    		}else{
				let itemId= vlc.playlist.add(this.curVideo)
				vlc.playlist.playItem(itemId)
				setTimeout(this.checkVedioStatus,500)
    		} 
		},
		checkVedioStatus() {
			let vlc = document.getElementById("vlc")
			if(vlc.input.state>2 && vlc.input.state<5){
				this.isPlay = true
				let videoLength = parseInt(vlc.input.length/1000)
				this.progress.totalTime = this.parseTime(videoLength)
			    setTimeout(this.checkTime,50)
			}else{
				setTimeout(this.checkVedioStatus,500)
			}
		},
		checkTime() {
			let vlc = document.getElementById("vlc")
			if(this.isPlay && this.progress.curTime != this.progress.totalTime){
			    this.progress.curTime = this.parseTime(parseInt(vlc.input.position * vlc.input.length / 1000))
			    this.progress.percent = Math.ceil(vlc.input.position * 100)
			    setTimeout(this.checkTime,50)
			}else{
				this.isPlay = false
				
			}
		},
		parseTime(numLength) {
			let h_time=0;
			let m_time=0;
			let s_time=0;
			if(numLength>=60){
			   m_time=parseInt(numLength/60);
			   s_time=parseInt(numLength%60);
			}else{
			   s_time=numLength;
			}
			if(m_time>=60){
			  h_time=parseInt(m_time/60);
			  m_time=parseInt(m_time%60);
			} 
			h_time = h_time < 10 ? '0' + h_time : h_time
			m_time = m_time < 10 ? '0' + m_time : m_time
			s_time = s_time < 10 ? '0' + s_time : s_time
			
			return h_time+":"+m_time+":"+s_time;
		},
		skipBack() {
			let vlc = document.getElementById("vlc")
			vlc.input.position = vlc.input.position - 0.1 
			this.progress.percent = vlc.input.position *　100
			this.progress.curTime = this.parseTime(parseInt(vlc.input.position * vlc.input.length / 1000))
		},
		skipFor() {
			let vlc = document.getElementById("vlc")
			vlc.input.position = vlc.input.position + 0.1 
			this.progress.percent = vlc.input.position *　100
			this.progress.curTime = this.parseTime(parseInt(vlc.input.position * vlc.input.length / 1000))
		},
		format(val) {
			let value = parseInt(val / 100 * vlc.input.length / 1000)
			return this.parseTime(value)
		},
		slide() {
			let vlc = document.getElementById("vlc")
			vlc.input.position = this.progress.percent / 100
			this.progress.curTime = this.parseTime(parseInt(vlc.input.position * vlc.input.length / 1000))
		},
		speedChange() {
			let vlc = document.getElementById("vlc")
			vlc.input.rate = this.speed
		},
		volumeChanged() {
			let vlc = document.getElementById("vlc")
			vlc.audio.volume = this.volume
		},
		back() {
			this.$router.push('/UM/VideoMonitoring/details/' + this.tunnelId)
		}
	}
};
</script>
<style scoped>
	.videos{
		margin-top: 10px;
		padding: 20px 10px;
		background-color: white;
	}
	.pics{
		width: 100%;
		height: 100px;
	}
	.list{
		padding: 20px;
	}
	.info{
		padding: 0 20px;
	}
	.controls{
		margin-top: 22px;
	}
	.progress{
		position: relative;
	}
	.process{
		width: 56%;
		position: absolute;
		top: -2px;
		left: 110px;
		display: inline-block;
	}
	.curTime{
		position: absolute;
		top: 6px;
		left: 50px;
	}
	.totalTime{
		position: absolute;
		top: 6px;
		right: 50px;
	}
	.query{
		background-color: white;
		padding: 10px;
	}
	.back{
		position: absolute;
		top: 0;
		left: 0;
	}
	.forward{
		position: absolute;
		top: 0;
		right: 0;
	}
	.speed{
		position: absolute;
		top: 0;
		right: 146px;
	}
	.sound{
		position: absolute;
		top: 0;
		right: 0;
	}
	.slide{
		width: 80px;
		display: inline-block;
		position: absolute;
		right: 0;
		top: -4px;
	}
    .volume{
    	position: absolute;
    	top: 0;
    	right: 90px;
    }
</style>