<template>
    <div class="dataBoxModul" v-bind:style="{width: boxWidth+'%', height: boxHeight+'vh', background: 'linear-gradient(to right,'+ boxBGLeft+','+boxBGRight+')'}">
        <div class="imageModul" :style="{display: 'flex',textAlign: 'center', alignItems: 'center'}">
            <img :style="{margin: 'auto'}" :src="imgSrc"/>
        </div>
        <div class="labelModul"><p>{{label}}</p></div>
        <div class="valueModul"><p>{{value}}</p></div>
    </div>
</template>

<script>
export default {
    name:'dataBox',
    props:{
        boxWidth:{
            default: 90
        },
        boxHeight:{
            default: 12
        },
        value: {
            default: 0.0
        },
        label: {
            default:"label"
        },
        imgSrc:{
            default:"./logo.png"
        },
        imgWidth: {
            default: 80
        },
        imgBorderLeftTopRadius:{
            default: 10
        },
        imgBorderRightTopRadius:{
            default: 10
        },
        boxBGLeft: {
            default: '#f68380'
        },
        boxBGRight: {
            default: '#f61a1a'
        }
    }
}
</script>

<style scoped>
    .dataBoxModul{
        border-radius: 10px;
    }
    .imageModul
    {
        float: left;
        width: 40%;
        height: 100%;
        text-align: center;
        border-top-left-radius: 10px;
        border-bottom-left-radius: 10px;
    }
    .valueModul{
        float: left;
        width: 60%;
        height: 6vh;
        text-align: center;
        font-size: 2.8vmin;
        font-weight: 700;
        border-top-right-radius: 10px;
        color: #fff;
    }
    .labelModul{
        line-height: 6vh;
        display: table-cell;
        font-size: 1.6vmin;
        font-size: 2.5vmin;
        color: #fff;
    }
    img{
        width: 80%;
    }
</style>


