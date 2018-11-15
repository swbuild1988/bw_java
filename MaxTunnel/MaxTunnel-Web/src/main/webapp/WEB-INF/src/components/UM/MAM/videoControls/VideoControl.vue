<template>
    <div class="controlers" id="control">
        <!-- <eight-dir-control @startDirectCtrl="startDirectCtrl" @stopDirectCtrl="stopDirectCtrl"v-bind:isDisabled="isDisabled"></eight-dir-control> -->
        <four-dir-control @startDirectCtrl="startDirectCtrl" @stopDirectCtrl="stopDirectCtrl"v-bind:isDisabled="isDisabled"></four-dir-control>
    </div>
</template>

<script>
import EightDirControl from "./eightDirControl"
import FourDirControl from "./fourDirControl"

export default {
    name: "videoControl",
    props: {
        isDisabled:{
            type: Boolean,
            default: true
        }
    },
    components: { EightDirControl,FourDirControl },
    methods:{
        startDirectCtrl(data){
            this.$emit('startDirectCtrl',data)  
        },
        stopDirectCtrl(data){
            this.$emit('stopDirectCtrl',data) 
        },
        resetHeightOrWidth() {
            let width = document.getElementById('control').offsetWidth
            let height = document.getElementById('control').offsetHeight
            if(width >= height){
                document.getElementById('control').style.width = height + 'px'
            } else {
                document.getElementById('control').style.height = width + 'px'
            }
        }
    },
    mounted() {
        this.resetHeightOrWidth()
        let _this = this
        window.onresize = function(){
            _this.resetHeightOrWidth()
        }
    }
};
</script>

<style scoped>
.controlers {
    position: relative;
    width: 100%;
    height: 100%;
}
</style>
