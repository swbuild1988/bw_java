<template>
    <div class="boxBody">
        <div :style="wrapperBgStyle" class="dataWrapper">
            <div style="width: 100%;text-align:center;align-self:center;">
                <img :src="imgSrc" style="height:5vmin;width:5vmin;vertical-align: middle;">
                <span>{{label}}</span>
                <span style="font-size: 2.6vmin;">{{value}}</span>
                <span>{{unit}}</span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "dataBox2",
    props: {
        minWidth: {
            default: 120
        },
        maxWidth: {
            default: 280
        },
        backGroundColor1: {
            default: "#ffce2d"
        },
        backGroundColor2: {
            default: "#ffce2d"
        },
        value: {
            default: 0.0
        },
        unit: {
            default: "千瓦时"
        },
        label: {
            default: "label"
        },
        imgSrc: {
            default: ""
        }
    },
    data() {
        return {
            wrapperBgStyle: {
                background:
                    "-webkit-linear-gradient(top" +
                    this.backGroundColor1 +
                    ", " +
                    this.backGroundColor2 +
                    ")",
                background:
                    "-o-linear-gradient(bottom, " +
                    this.backGroundColor1 +
                    ", " +
                    this.backGroundColor2 +
                    ")",
                background:
                    "-moz-linear-gradient(bottom, " +
                    this.backGroundColor1 +
                    ", " +
                    this.backGroundColor2 +
                    ")",
                background:
                    "linear-gradient(to bottom, " +
                    this.backGroundColor1 +
                    " , " +
                    this.backGroundColor2 +
                    ")"
            }
        };
    }
};
</script>

<style scoped>
.boxBody {
    height: 11vh;
    background: url("../../../assets/UM/dataBox2Bg.png") no-repeat;
    width: 100%;
    background-size: 100% 100%;
    color: #fff;
    font-size: 1.8vmin;
    overflow: hidden;
}
.dataWrapper {
    height: 96%;
    width: 98%;
    display: flex;
    margin: 0.6% auto;
}
</style>

