<template>
	<div class="all">
	    <div id="holderPnl_1" class="VideoHolder">
			<div id="dirCtrl_1" class="HolderDirCtrl">
			    <div id="dirCtrlCir_1" class="HolderDirCtrlCircle" @mousemove="mousemoveDirectCircleBtn" @mouseout="mouseoutDirectCircleBtn" @mousedown="mousedownDirectCircleBtn" @mouseup="mouseupDirectCircleBtn" v-if="!isDisabled">
			        <div id="dir_1_1" class="DirectUp" :style="styles.up"></div>
	                <div id='dir_1_7' class='DirectUpRight' :style='styles.upright'></div>
	                <div id='dir_1_4' class='DirectRight' :style='styles.right'></div>
	                <div id='dir_1_8' class='DirectDownRight' :style='styles.downright'></div>
	                <div id='dir_1_2' class='DirectDown' :style='styles.down'></div>
	                <div id='dir_1_6' class='DirectDownLeft' :style='styles.downleft'></div>
	                <div id='dir_1_3' class='DirectLeft' :style='styles.left'></div>
	                <div id='dir_1_5' class='DirectUpLeft' :style='styles.upleft'></div>
	                <div class='DirectAutoArrows' :style='styles.autoarrow'></div>
	                <div id='dir_1_9' class='DirectAutoStart' :style='styles.autostart'></div>
	                <div id='dir_1_99' class='DirectAutoStop' :style='styles.autostop'></div>
			    </div>
			    <div class="HolderDirCtrlCircle" v-if="isDisabled">
			        <div class="DirectUp" :style="disableStyle.up"></div>
	                <div class='DirectUpRight' :style='disableStyle.upright'></div>
	                <div class='DirectRight' :style='disableStyle.right'></div>
	                <div class='DirectDownRight' :style='disableStyle.downright'></div>
	                <div class='DirectDown' :style='disableStyle.down'></div>
	                <div class='DirectDownLeft' :style='disableStyle.downleft'></div>
	                <div class='DirectLeft' :style='disableStyle.left'></div>
	                <div class='DirectUpLeft' :style='disableStyle.upleft'></div>
	                <div class='DirectAutoArrows' :style='disableStyle.autoarrow'></div>
	                <div class='DirectAutoStart' :style='disableStyle.autostart'></div>
	                <div class='DirectAutoStop' :style='disableStyle.autostop'></div>
			    </div>
			</div>
		</div>
		<div>
			<p @mousedown="startVideoDirectCtrl(10)" @mouseup="stopVideoDirectCtrl(10)" class="large" @mousemove="mouseMove('plus')" @mouseout="mouseOut('plus')"><Icon type="ios-plus" size="40" :color="isDisabled ? 'rgb(210,210,210)' : plusState.color "></Icon></p>
            <p @mousedown="startVideoDirectCtrl(11)" @mouseup="stopVideoDirectCtrl(11)" class="small" @mousemove="mouseMove('minus')" @mouseout="mouseOut('minus')"><Icon type="ios-minus" size="40" :color="isDisabled ? 'rgb(210,210,210)': minusState.color "></Icon></p>
		</div>
	</div>
</template>

<script>
import $ from 'jquery'
    export default {
	    name: "eightDirectionControls",
	    props: {
		    isDisabled:{
		      type: Boolean,
		      required: false
		    }
		 },
	    data: function () {
	        return {
	        	VIDEO_DIRCIR_HALFANGLE : Math.PI / 8,
	        	VIDEO_DIRCIR_OVER_CURBTN : -1,
	        	VIDEO_DIRCIR_DOWN_CURBTN : -1,
	        	styles:{
	                up: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_up2.png')+')'
	                },
	                upright: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_up_right2.png')+')'
	                },
	                right: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_right2.png')+')'
	                },
	                downright: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_down_right2.png')+')'
	                },
	                down: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_down2.png')+')'
	                },
	                downleft: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_down_left2.png')+')'
	                },
	                left: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_left2.png')+')'
	                },
	                upleft: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_up_left2.png')+')'
	                },
	                autoarrow: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/directAutoArrows2.png')+')'
	                },
	                autostart: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/directAutoStartBtn2.png')+')'
	                },
	                autostop: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/directAutoStopBtn2.png')+')'
	                }
	            },
	            disableStyle: {
	            	up: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_up1.png')+')'
	                },
	                upright: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_up_right1.png')+')'
	                },
	                right: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_right1.png')+')'
	                },
	                downright: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_down_right1.png')+')'
	                },
	                down: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_down1.png')+')'
	                },
	                downleft: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_down_left1.png')+')'
	                },
	                left: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_left1.png')+')'
	                },
	                upleft: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/direct_up_left1.png')+')'
	                },
	                autoarrow: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/directAutoArrows1.png')+')'
	                },
	                autostart: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/directAutoStartBtn1.png')+')'
	                },
	                autostop: {
	                    backgroundImage: 'url('+ require('../../../../assets/UM/directAutoStopBtn1.png')+')'
	                }
	            },
	            plusState: {
	            	color: 'rgb(102,102,102)'
	            },
	            minusState: {
	            	color: 'rgb(102,102,102)'
	            }
	      };
	    },
	    methods: {
	    	mousemoveDirectCircleBtn(ev) {
	    		let btnNumber = this.getDirectCirleBtnNumber(ev);
			    if (btnNumber <= 0) {
			        this.mouseoutDirectCircleBtn(ev);
			        return;
			    }
			    else if (this.VIDEO_DIRCIR_OVER_CURBTN == btnNumber) {
			        return;
			    }
			    else {
			        if (this.VIDEO_DIRCIR_OVER_CURBTN != -1) { //恢复原来鼠标over的按钮状态
			            this.changeDirectBtnInit(this.VIDEO_DIRCIR_OVER_CURBTN);
			        }

			        this.VIDEO_DIRCIR_OVER_CURBTN = btnNumber;
			        this.changeDirectBtnOver(this.VIDEO_DIRCIR_OVER_CURBTN);

			        if (this.VIDEO_DIRCIR_DOWN_CURBTN >= 1 && this.VIDEO_DIRCIR_DOWN_CURBTN <= 8) { //鼠标点击情况下移动需要释放原来点击的控制
			            this.stopVideoDirectCtrl(this.VIDEO_DIRCIR_DOWN_CURBTN);
			        }
			        this.VIDEO_DIRCIR_DOWN_CURBTN = -1;
			    }
	    	},
	    	getDirectCirleBtnNumber(ev) {
	    		let eve = window.event;
			    let cx = $("#dirCtrlCir_1").offset().left + 67;
			    let cy = $("#dirCtrlCir_1").offset().top + 67;
			    let rx = eve.pageX - cx;
			    let ry = eve.pageY - cy;
			    let btnNumber = -1;

			    let radius = Math.sqrt(rx * rx + ry * ry);
			    if (radius > 67) {
			        btnNumber = -1;
			    }
			    else if (radius < 37) { //中心区域
			        if (rx > -23 && rx < 0 && ry < 11.5 && ry > -11.5) {
			            btnNumber = 9;
			        }
			        else if (rx > 0 && rx < 32 && ry < 11.5 && ry > -11.5) {
			            btnNumber = 99;
			        }
			        else {
			            btnNumber = 0;
			        }
			    }
			    else {
			        let angle = Math.atan2(ry, rx);
			        if (angle > -this.VIDEO_DIRCIR_HALFANGLE && angle <= this.VIDEO_DIRCIR_HALFANGLE) {
			            btnNumber = 4;
			        }
			        else if (angle > this.VIDEO_DIRCIR_HALFANGLE && angle <= 3 * this.VIDEO_DIRCIR_HALFANGLE) {
			            btnNumber = 8;
			        }
			        else if (angle > 3 * this.VIDEO_DIRCIR_HALFANGLE && angle <= 5 * this.VIDEO_DIRCIR_HALFANGLE) {
			            btnNumber = 2;
			        }
			        else if (angle > 5 * this.VIDEO_DIRCIR_HALFANGLE && angle <= 7 * this.VIDEO_DIRCIR_HALFANGLE) {
			            btnNumber = 6;
			        }
			        else if (angle > 7 * this.VIDEO_DIRCIR_HALFANGLE || angle <= -(7 * this.VIDEO_DIRCIR_HALFANGLE)) {
			            btnNumber = 3;
			        }
			        else if (angle > -(7 * this.VIDEO_DIRCIR_HALFANGLE) && angle <= -(5 * this.VIDEO_DIRCIR_HALFANGLE)) {
			            btnNumber = 5;
			        }
			        else if (angle > -(5 * this.VIDEO_DIRCIR_HALFANGLE) && angle <= -(3 * this.VIDEO_DIRCIR_HALFANGLE)) {
			            btnNumber = 1;
			        }
			        else if (angle > -(3 * this.VIDEO_DIRCIR_HALFANGLE) && angle <= -this.VIDEO_DIRCIR_HALFANGLE) {
			            btnNumber = 7;
			        }
			    }
			    return btnNumber;
	    	},
	    	changeDirectBtnInit(btnNumber) {
	    		if (btnNumber == 1 || btnNumber == 2) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "0px");
			    }
			    else if (btnNumber == 7 || btnNumber == 5) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "0px");
			    }
			    else if (btnNumber == 4 || btnNumber == 3) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "0px");
			    }
			    else if (btnNumber == 8 || btnNumber == 6) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "0px");
			    }
			    else if (btnNumber == 9 || btnNumber == 99) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "0px");
			    }
	    	},
	    	mouseoutDirectCircleBtn(ev) {
	    		if (this.VIDEO_DIRCIR_OVER_CURBTN != -1) {
			        this.changeDirectBtnInit(this.VIDEO_DIRCIR_OVER_CURBTN);
			    }
			    if (this.VIDEO_DIRCIR_DOWN_CURBTN >= 1 && this.VIDEO_DIRCIR_DOWN_CURBTN <= 8) {
			        this.stopVideoDirectCtrl(this.VIDEO_DIRCIR_DOWN_CURBTN);
			    }
			    this.VIDEO_DIRCIR_OVER_CURBTN = -1;
			    this.VIDEO_DIRCIR_DOWN_CURBTN = -1;
	    	},
	    	changeDirectBtnOver(btnNumber) {
	    		if (btnNumber == 1 || btnNumber == 2) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-29px");
			    }
			    else if (btnNumber == 7 || btnNumber == 5) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-45px");
			    }
			    else if (btnNumber == 4 || btnNumber == 3) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-51px");
			    }
			    else if (btnNumber == 8 || btnNumber == 6) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-47px");
			    }
			    else if (btnNumber == 9 || btnNumber == 99) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-21px");
			    }
	    	},
	    	mousedownDirectCircleBtn(ev) {
	    		var btnNumber = this.getDirectCirleBtnNumber(ev);
			    if (btnNumber < 0) {
			        this.VIDEO_DIRCIR_OVER_CURBTN = -1;
			        this.VIDEO_DIRCIR_DOWN_CURBTN = -1;
			        return;
			    }
			    this.VIDEO_DIRCIR_DOWN_CURBTN = btnNumber;
			    if (btnNumber == 1 || btnNumber == 2) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-58px");
			    }
			    else if (btnNumber == 7 || btnNumber == 5) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-90px");
			    }
			    else if (btnNumber == 4 || btnNumber == 3) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-102px");
			    }
			    else if (btnNumber == 8 || btnNumber == 6) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-94px");
			    }
			    else if (btnNumber == 9 || btnNumber == 99) {
			        $("#dir_1_" + btnNumber).css("background-position-y", "-42px");
			    }

			    if (this.VIDEO_DIRCIR_DOWN_CURBTN >= 1 && this.VIDEO_DIRCIR_DOWN_CURBTN <= 8) {
			        this.startVideoDirectCtrl(this.VIDEO_DIRCIR_DOWN_CURBTN);
			    }
	    	},
	    	mouseupDirectCircleBtn() {
	    		if (this.VIDEO_DIRCIR_DOWN_CURBTN != -1) {
			        if (this.VIDEO_DIRCIR_DOWN_CURBTN >= 1 && this.VIDEO_DIRCIR_DOWN_CURBTN <= 8) { //启动控制
			            this.stopVideoDirectCtrl(this.VIDEO_DIRCIR_DOWN_CURBTN);
			        }
			        else if (this.VIDEO_DIRCIR_DOWN_CURBTN == 9) { //启动自转
			            this.startVideoDirectCtrl(9);
			        }
			        else if (this.VIDEO_DIRCIR_DOWN_CURBTN == 99) { //停止自转
			            this.stopVideoDirectCtrl(9);
			        }
			        this.changeDirectBtnOver(this.VIDEO_DIRCIR_DOWN_CURBTN);
			    }
			    this.VIDEO_DIRCIR_DOWN_CURBTN = -1;
	    	},
	    	startVideoDirectCtrl(num) {
	    		let direction = null
	    		switch(num) {
	    			case 1:
	    				direction = 14
	    				break
	    			case 2:
	    				direction = 12
	    				break
	    			case 3:
	    				direction = 16
	    				break
	    			case 4:
	    				direction = 10
	    			    break
	    			case 5:
	    				direction = 17
	    			    break
	    			case 6:
	    				direction = 15
	    				break
	    			case 7:
	    				direction = 11
	    				break
	    			case 8:
	    				direction = 9
	    				break
	    			case 9:
	    				direction = '自转'
	    				break
	    			case 10:
	    				direction = 22
	    				this.plusState.color = 'rgb(0,228,236)'
	    				break
	    			case 11:
	    				direction = 4
	    				this.minusState.color = 'rgb(0,228,236)'
	    				break
	    		}
	    		let dir = {
			        direction: direction,
			        state: 'start'
			      };
	    		this.$emit('startDirectCtrl',dir)     
	    	},
	    	stopVideoDirectCtrl(num) {
	    		let direction = null
	    		switch(num) {
	    			case 1:
	    				direction = 14
	    				break
	    			case 2:
	    				direction = 12
	    				break
	    			case 3:
	    				direction = 16
	    				break
	    			case 4:
	    				direction = 10
	    			    break
	    			case 5:
	    				direction = 17
	    			    break
	    			case 6:
	    				direction = 15
	    				break
	    			case 7:
	    				direction = 11
	    				break
	    			case 8:
	    				direction = 9
	    				break
	    			case 9:
	    				direction = '自转'
	    				break
	    			case 10:
	    				direction = 22
	    				this.plusState.color = 'rgb(102,102,102)'
	    				break
	    			case 11:
	    				direction = 4
	    				this.minusState.color = 'rgb(102,102,102)'
	    				break
	    		}
	    		let dir = {
			        direction: direction,
			        state: 'stop'
			      };
			    this.$emit('stopDirectCtrl',dir) 
			    // let _this = this
			    // setTimeout(function(){
			    // 	_this.$emit('stopDirectCtrl',dir) 
			    // },800)
	    	},
	    	mouseMove(type) {
	    		switch(type){
	    			case 'plus':
	    				this.plusState.color = 'rgb(37,37,37)'
	    				break
	    			case 'minus':
	    				this.minusState.color = 'rgb(37,37,37)'
	    		} 
	    	},
	    	mouseOut(type) {
	    		switch(type){
	    			case 'plus':
	    				this.plusState.color = 'rgb(102,102,102)'
	    				break
	    			case 'minus':
	    				this.minusState.color = 'rgb(102,102,102)'
	    		} 
	    	}
	    },
	    mounted() {
	    },
    }
</script>

<style scoped>
   .DirectUp {
	    position: absolute;
	    top: 1px;
	    left: 42px;
	    width: 51px;
	    height: 29px;
	}
	.DirectUpRight {
	    position: absolute;
	    top: 6px;
	    right: 34px;
	    width: 46px;
	    height: 45px;
	}
	.DirectRight {
	    position: absolute;
	    top: 41px;
	    right: 29px;
	    width: 30px;
	    height: 51px;
	}
	.DirectDownRight {
	    position: absolute;
	    top: 82px;
	    right: 34px;
	    width: 46px;
	    height: 47px;
	}
	.DirectDown {
	    position: absolute;
	    top: 105px;
	    left: 42px;
	    width: 51px;
	    height: 29px;
	}
	.DirectDownLeft {
	    position: absolute;
	    top: 82px;
	    left: 6px;
	    width: 46px;
	    height: 47px;
	}
	.DirectLeft {
	    position: absolute;
	    top: 41px;
	    left: 1px;
	    width: 30px;
	    height: 51px;
	}
	.DirectUpLeft {
	    position: absolute;
	    top: 6px;
	    left: 6px;
	    width: 46px;
	    height: 45px;
	}
	.DirectAuto {
	    position: absolute;
	    top: 50px;
	    left: 50px;
	    width: 35px;
	    height: 35px;
	    background-color: #202020;
	    border-radius: 5px;
	}
	.DirectAutoArrows {
	    position: absolute;
	    top: 39px;
	    left: 39px;
	    width: 57px;
	    height: 57px;
	}
	.DirectAutoStart {
	    position: absolute;
	    top: 57px;
	    left: 37px;
	    width: 30px;
	    height: 21px;
	}
	.DirectAutoStop {
	    position: absolute;
	    top: 57px;
	    left: 68px;
	    width: 30px;
	    height: 21px;
	}
	.HolderDirCtrlSet {
	    position: absolute;
	    top: 145px;
	    left: 0px;
	    width: 135px;
	    height: 95px;
	}
	.VideoHolder {
	    position: absolute;
	    top: 0;
	    right: 8px;
	    width: 163px;
	    height: 160px;
	    border-radius: 2px;
	}
	.large{
		position: absolute;
		top: 30px;
		right: -10px;
		cursor: pointer;
	}
	.small{
		position: absolute;
		top: 80px;
		right: -10px;
		cursor: pointer;
	}
	.all{
		position: relative;
		top: 0;
		right: 8px;
	    width: 200px;
	    height: 160px;
	}
</style>

