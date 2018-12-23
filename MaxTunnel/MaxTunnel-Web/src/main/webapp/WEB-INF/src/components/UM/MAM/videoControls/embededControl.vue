<template>
    <div class="emControl" @click="stopPro">
        <div :class="['up',upActive ? 'isActived' : 'buttons']" @mousedown="startVideoDirectCtrl('up')" @mouseup="stopVideoDirectCtrl('up')">
            <Icon type="arrow-up-b" size="20" class="upArrow" color="white"></Icon>
        </div>
        <div :class="['left',leftActive ? 'isActived' : 'buttons']" @mousedown="startVideoDirectCtrl('left')" @mouseup="stopVideoDirectCtrl('left')">
            <Icon type="arrow-left-b" size="20" class="leftArrow" color="white"></Icon>
        </div>
        <div :class="['down',downActive ? 'isActived' : 'buttons']" @mousedown="startVideoDirectCtrl('down')" @mouseup="stopVideoDirectCtrl('down')">
            <Icon type="arrow-down-b" size="20" class="downArrow" color="white"></Icon>
        </div>
        <div :class="['right',rightActive ? 'isActived' : 'buttons']" @mousedown="startVideoDirectCtrl('right')" @mouseup="stopVideoDirectCtrl('right')">
            <Icon type="arrow-right-b" size="20" class="rightArrow" color="white"></Icon>
        </div>
        <div :class="['stop',stopActive ? 'isActived' : 'buttons']" @click="stopAll" @mousedown="stopActive = true" @mouseup="stopActive = false">
            <Icon type="stop" size="18" color="white"></Icon>
        </div>
        <div :class="['zoomIn',zoomInActive ? 'isActived' : 'buttons']" @mousedown="startVideoDirectCtrl('zoomIn')" @mouseup="stopVideoDirectCtrl('zoomIn')">
            <Icon type="plus" size="18" color="white" class="zoomInBtn"></Icon>
        </div>
        <div :class="['zoomOut',zoomOutActive ? 'isActived' : 'buttons']" @mousedown="startVideoDirectCtrl('zoomOut')" @mouseup="stopVideoDirectCtrl('zoomOut')">
            <Icon type="minus" size="18" color="white" class="zoomOutBtn"></Icon>
        </div>
    </div>
</template>

<script>
import { VideoService } from "../../../../services/videoService"

export default {
    name: "embededControl",
    props: {
        camera:{
            type: Object,
            required: true
        },
        isShow: {
            type: Boolean,
            required: true
        }
    },
    data() {
        return {
            upActive: false,
            downActive: false,
            leftActive: false,
            rightActive: false,
            stopActive: false,
            zoomInActive: false,
            zoomOutActive: false,
            curActive: null
        }
    },
    methods:{
        startVideoDirectCtrl(type){
            switch(type){
                case 'up':
                    this.curActive = 14
                    this.upActive = true
                    break
                case 'down':
                    this.curActive = 12
                    this.downActive = true
                    break
                case 'left':
                    this.curActive = 16
                    this.leftActive = true
                    break
                case 'right':
                    this.curActive = 10
                    this.rightActive = true
                    break
                case 'stop':
                    break
                case 'zoomOut':
                    this.curActive = 4
                    this.zoomOutActive = true
                    break
                case 'zoomIn':
                    this.curActive = 22
                    this.zoomInActive = true
                    break
            }
        
            let _this = this
            VideoService.cameraMove(
                _this.camera.id,
                _this.curActive
            ).then(
                result => {
                    _this.Log.info("move success");
                },
                error => {
                    _this.Log.info(error)
                }
            );
        },
        stopVideoDirectCtrl(type){
            this.actived = false
            let direction
            switch(type){
                case 'up':
                    direction = 14
                    this.upActive = false
                    break
                case 'down':
                    direction = 12
                    this.downActive = false
                    break
                case 'left':
                    direction = 16
                     this.leftActive = false
                    break
                case 'right':
                    direction = 10
                     this.rightActive = false
                    break
                case 'zoomOut':
                    direction = 4
                    this.zoomOutActive = false
                    break
                case 'zoomIn':
                    direction = 22
                    this.zoomInActive = false
                    break
            }
            
            let _this = this
            VideoService.cameraStop(_this.cameraId).then(
                result => {
                    _this.Log.info("stop success")
                    _this.curActive = null
                },
                error => {
                    _this.Log.info(error)
                }
            );
        },
        stopAll(){
            if(this.curActive){
                let _this = this
                VideoService.cameraStop(_this.camera.id).then(
                    result => {
                        _this.Log.info("stop success")
                        _this.curActive = null
                    },
                    error => {
                        _this.Log.info(error)
                    }
                );
            }
        },
        stopPro(e) {
            if(this.isShow){
                e.stopPropagation()
            }
        }
    },
    mounted() {
        
    }
};
</script>

<style scoped>
.emControl{
    position: relative;
    height: 100%;
    width: 100%;
}
.up {
    position: absolute;
    top: 0;
    left: 30%;
    width: 40%;
    height: 40%; 
    border-radius: 100% 0 0 0; 
    transform:rotate(45deg);
    -ms-transform:rotate(45deg);     /* IE 9 */
    -moz-transform:rotate(45deg);    /* Firefox */
    -webkit-transform:rotate(45deg); /* Safari 和 Chrome */
    -o-transform:rotate(45deg);  /* Opera */
}
.upArrow{
    position: absolute;
    top: 30%;
    left: 40%;
    transform:rotate(-45deg);
    -ms-transform:rotate(-45deg);    
    -moz-transform:rotate(-45deg);    
    -webkit-transform:rotate(-45deg); 
    -o-transform:rotate(-45deg);
}
.left {
    position: absolute;
    top: 30%;
    left: 0;
    width: 40%;
    height: 40%; 
    border-radius: 100% 0 0 0; 
    transform:rotate(-45deg);
    -ms-transform:rotate(-45deg);    
    -moz-transform:rotate(-45deg);    
    -webkit-transform:rotate(-45deg); 
    -o-transform:rotate(-45deg);  
}
.leftArrow{
    position: absolute;
    top: 30%;
    left: 40%;
    transform:rotate(45deg);
    -ms-transform:rotate(45deg);    
    -moz-transform:rotate(45deg);    
    -webkit-transform:rotate(45deg); 
    -o-transform:rotate(45deg);
}
.down {
    position: absolute;
    top: 60%;
    left: 30%;
    width: 40%;
    height: 40%; 
    border-radius: 100% 0 0 0; 
    transform:rotate(225deg);
    -ms-transform:rotate(225deg);     /* IE 9 */
    -moz-transform:rotate(225deg);    /* Firefox */
    -webkit-transform:rotate(225deg); /* Safari 和 Chrome */
    -o-transform:rotate(225deg);  /* Opera */
}
.downArrow{
    position: absolute;
    top: 26%;
    left: 36%;
    transform:rotate(135deg);
    -ms-transform:rotate(135deg);    
    -moz-transform:rotate(135deg);    
    -webkit-transform:rotate(135deg); 
    -o-transform:rotate(135deg);
}
.right {
    position: absolute;
    top: 30%;
    left: 60%;
    width: 40%;
    height: 40%; 
    border-radius: 100% 0 0 0; 
    transform:rotate(135deg);
    -ms-transform:rotate(135deg);     /* IE 9 */
    -moz-transform:rotate(135deg);    /* Firefox */
    -webkit-transform:rotate(135deg); /* Safari 和 Chrome */
    -o-transform:rotate(135deg);  /* Opera */
}

.rightArrow{
    position: absolute;
    top: 26%;
    left: 46%;
    transform:rotate(225deg);
    -ms-transform:rotate(225deg);    
    -moz-transform:rotate(225deg);    
    -webkit-transform:rotate(225deg); 
    -o-transform:rotate(225deg);
}
.stop{
    margin: auto;  
    position: absolute;  
    top: 30%; 
    left: 30%; 
    width: 40%;
    height: 40%;
    border-radius: 50%;
    text-align: center;
    padding: 12% 0;
}
.zoomIn{
    position: absolute;
    width: 28%;
    height: 28%;
    border-radius: 50%;
    top: 20%;
    left: 94%;
    text-align: center;
    padding: 6% 0;
    display: table;
}
.zoomInBtn,.zoomOutBtn{
    display: table-cell;
    vertical-align: middle;
    text-align: center;
}
.zoomOut{
    position: absolute;
    width: 28%;
    height: 28%;
    border-radius: 50%;
    top: 54%;
    left: 94%;
    text-align: center;
    padding: 6% 0;
    display: table;
}
.buttons{
    background-color: rgb(102,102,102);
    cursor: pointer;
}
.buttons:hover{
    background-color: rgb(37,37,37);
}
.isActived{
    background-color: rgb(0,228,236);
    cursor: pointer;
}
</style>
