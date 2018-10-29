<template>
    <div class="progressFans">
        <div v-if="switchData">
            <!-- true风扇开启 -->
            <img src='../../../assets/UM/fanstart.gif'>
        </div>
        <div v-if="!switchData">
            <!-- false风扇关闭 -->
            <img src='../../../assets/UM/fanstop.png'>
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
        }
    }
}
</script>
<style scoped>
.progressFans{
    height:100%;
    width:100%;
}
</style>

