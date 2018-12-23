<template>
    <div class="progressFans">
        <div v-if="switchData">
            <!-- true风扇开启 -->
            <img src='../../../assets/UM/fanstart.gif' class="imgP" v-if="objId == 10">
             <!-- 百叶开启 -->
            <img src="../../../assets/UM/blindopen.png" class="imgP" v-if="objId == 58">
            <!-- 水泵开启 -->
            <img src="../../../assets/UM/pumpstart.gif" class="imgP" v-if="objId == 59">
            <!-- 打开井盖 -->
            <img src="../../../assets/UM/coveropen.png" class="cover" v-if="objId == 56">
        </div>
        <div v-if="!switchData">
            <!-- false风扇关闭 -->
            <img src='../../../assets/UM/fanstop.png' class="imgP" v-if="objId == 10">
            <!-- 百叶关闭 -->
            <img src="../../../assets/UM/blindclose.png" class="imgP" v-if="objId == 58">
            <!-- 水泵关闭 -->
            <img src="../../../assets/UM/pumpstop.png" class="imgP" v-if="objId == 59">
            <!-- 关闭井盖 -->
            <img src="../../../assets/UM/coverclose.png" class="cover" v-if="objId == 56">
        </div>
    </div>
</template>
<script>
import fanstart from '../../../assets/UM/fanstart.gif'
import fanstop from "../../../assets/UM/fanstop.png"
export default {
    name:"fanscontroller",
    props:{
        switchData:{
            default: false
        },
        objId: {
            required: true
        }
    }
}
</script>
<style scoped>
.progressFans{
    height:100%;
    width:100%;
}
.imgP{
    height: 60%;
    width: 66%;
    position: relative;
    float: right;
    margin-top: -8%;
}
.cover{
    height: 60%;
    width: 60%;
    position: relative;
    float: right;
    margin-top: -4%;
}
</style>

