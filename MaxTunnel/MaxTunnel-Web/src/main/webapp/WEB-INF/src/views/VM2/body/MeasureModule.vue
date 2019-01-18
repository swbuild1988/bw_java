<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
        <div class="left">
            <div
                :class="['item',{'atLeft': index % 2 == 0}]"
                v-for="(item,index) in extreData"
                :key="index"
            >
                <p class="name">{{ item.name }}</p>
                <p class="number">{{ item.value + item.unit }}</p>
                <p class="location">{{ item.location }}</p>
            </div>
        </div>
        <div class="right">
            <div
                class="count"
                v-for="(item,index) in countData"
                :key="index"
            >
                <p class="name">{{ item.name }}</p>
                <span class="number">{{ item.value + item.unit }}</span>
                <span class="riseIcons">
                    <Icon
                        :type="item.isRise ? 'arrow-up-c' : 'arrow-down-c'"
                        :color="item.isRise ? 'rgb(247, 26, 56)': 'white'"
                    ></Icon>
                </span>
            </div>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle"
import { MeasObjServer } from '../../../services/MeasObjectSerivers'

export default {
    data() {
        return {
            title: '管廊监测',
            extreData: [],
            countData: []
        };
    },
    components: {
        ModuleTitle
    },
    mounted() { 
        this.init()
    },
    methods: {
        init(){
            let _this = this
            MeasObjServer.getToDayExtreDatas().then(
                result=>{
                    _this.extreData = result
                },
                error=>{
                    _this.Log.info(error)
                })
            MeasObjServer.getMeasTriggerCounts().then(
                result=>{
                    _this.countData = result
                },
                error=>{
                    _this.Log.info(error)
                })
        }
    }
};
</script>

<style scoped>
.Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.Main .Title {
    width: 100%;
    height: 15%;
}
.left {
    position: absolute;
    width: 48%;
    height: 73%;
    top: 18%;
    left: 7%;
    overflow: auto;
}
.item {
    /*background: url("../../../assets/UM/videoBody.png") no-repeat;
    background-size: 100% 100%;*/
    height: 10vmin;
    width: 10vmin;
    border-radius: 50%;
    border: 1px solid #25bbc0;
    margin: 0.6vh auto;
    text-align: center;
    float: right;
}
.atLeft{
    float: left;
}
.name {
    color: white;
    font-size: 1.6vmin;
    margin-top: 1.2vmin;
}
.number {
    color: #37bbcc;
    font-size: 2vmin;
}
.location {
    color: white;
    font-size: 1.2vmin;
}
.right {
    width: 34%;
    height: 73%;
    position: absolute;
    top: 18%;
    right: 6%;
    text-align: center;
    overflow: auto;
}
.count {
    margin: 1vh auto;
    background: url("../../../assets/UM/videoBody.png") no-repeat;
    background-size: 100% 100%;
}
.riseIcons {
    font-size: 2vmin;
    float: right;
    margin-right: 1vw;
}
</style>
