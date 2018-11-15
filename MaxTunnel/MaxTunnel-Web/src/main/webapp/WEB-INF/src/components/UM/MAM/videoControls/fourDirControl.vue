<template>
    <div class="content">
        <div :class="['up',isDisabled ? 'isDisabled' : (upActive ? 'isActived' : 'buttons')]" @mousedown="startVideoDirectCtrl('up')" @mouseup="stopVideoDirectCtrl('up')">
            <Icon type="arrow-up-b" size="25" class="upArrow" :color="isDisabled ? 'rgb(16,16,16)' : 'white'"></Icon>
        </div>
        <div :class="['left',isDisabled ? 'isDisabled' : (leftActive ? 'isActived' : 'buttons')]" @mousedown="startVideoDirectCtrl('left')" @mouseup="stopVideoDirectCtrl('left')">
            <Icon type="arrow-left-b" size="25" class="leftArrow" :color="isDisabled ? 'rgb(16,16,16)' : 'white'"></Icon>
        </div>
        <div :class="['down',isDisabled ? 'isDisabled' : (downActive ? 'isActived' : 'buttons')]" @mousedown="startVideoDirectCtrl('down')" @mouseup="stopVideoDirectCtrl('down')">
            <Icon type="arrow-down-b" size="25" class="downArrow" :color="isDisabled ? 'rgb(16,16,16)' : 'white'"></Icon>
        </div>
        <div :class="['right',isDisabled ? 'isDisabled' : (rightActive ? 'isActived' : 'buttons')]" @mousedown="startVideoDirectCtrl('right')" @mouseup="stopVideoDirectCtrl('right')">
            <Icon type="arrow-right-b" size="25" class="rightArrow" :color="isDisabled ? 'rgb(16,16,16)' : 'white'"></Icon>
        </div>
        <div class="stopContent">
            <div :class="['stop',isDisabled ? 'isDisabled' : (stopActive ? 'isActived' : 'buttons')]" @click="stopAll" @mousedown="stopActive = true" @mouseup="stopActive = false">
                <Icon type="stop" size="25" :color="isDisabled ? 'rgb(16,16,16)' : 'white'"></Icon>
            </div>
        </div>
        <div :class="['zoomIn',isDisabled ? 'isDisabled' : (zoomInActive ? 'isActived' : 'buttons')]" @mousedown="startVideoDirectCtrl('zoomIn')" @mouseup="stopVideoDirectCtrl('zoomIn')">
            <Icon type="plus" size="25" :color="isDisabled ? 'rgb(16,16,16)' : 'white'" class="zoomInBtn"></Icon>
        </div>
        <div :class="['zoomOut',isDisabled ? 'isDisabled' : (zoomOutActive ? 'isActived' : 'buttons')]" @mousedown="startVideoDirectCtrl('zoomOut')" @mouseup="stopVideoDirectCtrl('zoomOut')">
            <Icon type="minus" size="25" :color="isDisabled ? 'rgb(16,16,16)' : 'white'" class="zoomOutBtn"></Icon>
        </div>
    </div>
</template>

<script>

export default {
    name: "fourDirectionsControl",
    props: {
        isDisabled:{
            type: Boolean,
            default: true
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
            let data = {
                direction: this.curActive,
                state: 'start'
            }
            this.$emit('startDirectCtrl',data)  
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
            this.curActive = null
            let data = {
                direction: direction,
                state: 'stop'
            }
            this.$emit('stopDirectCtrl',data) 
        },
        stopAll(){
            if(this.curActive){
                let data = {
                    direction: this.curActive,
                    state: 'stop'
                }
                this.$emit('stopDirectCtrl',data) 
            }
        }
    },
    mounted() {
        this.Log.info("videoControl start!");
    }
};
</script>

<style scoped>
.content{
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
    top: 30%;
    left: 40%;
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
    top: 30%;
    left: 40%;
    transform:rotate(225deg);
    -ms-transform:rotate(225deg);    
    -moz-transform:rotate(225deg);    
    -webkit-transform:rotate(225deg); 
    -o-transform:rotate(225deg);
}
.stopContent{
    position: absolute;
    top: 30%;
    left: 30%;
    width: 41%;
    height: 41%;
    border-radius: 50%;
    text-align: center;
    padding: 12% 0;
    background-color: white;
}
.stop{
    margin: auto;  
    position: absolute;  
    top: 0; 
    left: 0; 
    bottom: 0; 
    right: 0; 
    width: 80%;
    height: 80%;
    border-radius: 50%;
    text-align: center;
    padding: 20% 0;
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
.isDisabled{
    background-color: lightgray;
}
</style>
