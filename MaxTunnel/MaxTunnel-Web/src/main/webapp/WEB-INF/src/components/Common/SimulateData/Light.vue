<template>
    <div class="progressLight">
        <div v-if="switchData">
            <!-- true亮灯 -->
            <img src="../../../assets/UM/openLight.png" class="imgP">
        </div>
        <div v-if="!switchData">
            <!-- false灭灯 -->
            <img src="../../../assets/UM/closeLight.png" class="imgP">
        </div>
    </div>
</template>
<script>
import openLight from '../../../assets/UM/openLight.png'
import closeLight from "../../../assets/UM/closeLight.png"
export default {
    name:"lightController",
    props:{
        switchData:{
            default: false
        }
    }
}
</script>
<style scoped>
.progressLight{
    height:100%;
    width:100%;
}
.imgP{
    height:50%;
    width:50%;
    position: relative;
    float: right;
}
</style>

