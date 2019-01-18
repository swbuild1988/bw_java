<template>
    <div class="dataBoxModul" v-bind:style="{width: boxWidth+'%', height: boxHeight+'vh'}">
        <div class="imageModul" :style="{background: imgBac,display: 'flex',textAlign: 'center'}"><img :style="{width: imgWidth+'%',height: 'auto',margin: 'auto'}" :src="imgSrc"/></div>
        <div class="valueModul" :style='{background: valBackground,color: valColor}'><p>{{value}}</p></div>
        <div class="labelModul" :style='{background: labelBackground,color: labelColor}'><p>{{label}}</p></div>
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
        imgBac:{
            default: '#fff'
        },
        imgWidth: {
            default: 100
        },
        imgBorderLeftTopRadius:{
            default: 10
        },
        imgBorderRightTopRadius:{
            default: 10
        },
        valBackground:{
            default: '#CC9966'
        },
        valColor:{
            default: '#fff'
        },
        labelColor:{
            default: '#fff'
        },
        labelBackground:{
            default: '#CC9999'
        }
    },
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
    .valueModul
    {
        float: left;
        width: 60%;
        height: 6vh;
        line-height: 6vh;
        display: table-cell;
        text-align: center;
        vertical-align: middle;
        font-size: 1.8vmin;
        font-weight: 700;
        border-top-right-radius: 10px;
    }
    .labelModul
    {
        float: left;
        width: 60%;
        height: 6vh;
        line-height: 6vh;
        display: table-cell;
        text-align: center;
        vertical-align: middle;
        font-size: 1.6vmin;
        border-bottom-right-radius: 10px;
    }
    img{
        width: 90%;
        height: 80% ;
    }
</style>


