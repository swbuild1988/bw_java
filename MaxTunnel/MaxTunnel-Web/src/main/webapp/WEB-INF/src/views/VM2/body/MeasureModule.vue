<template>
    <div class="Main">
        <p class="title">监测对象</p>
        <div class="left">
            <div class="item" v-for="(item,index) in extreData" :key="index">
                <p class="name">{{ item.name }}</p>
                <p class="number">{{ item.number + item.unit }}</p>
                <p class="location">{{ item.location }}</p>
            </div>
        </div>
        <div class="right">
            <div class="count" v-for="(item,index) in countData" :key="index">
                <p class="name">{{ item.name }}</p>
                <span class="number">{{ item.number + item.unit }}</span>
                 <span class="riseIcons"><Icon :type="item.isRise ? 'arrow-up-c' : 'arrow-down-c'" :color="item.isRise ? 'rgb(247, 26, 56)': 'white'"></Icon></span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            extreData: [
                {
                    name: '最高温度',
                    number: 21,
                    unit: '℃',
                    location: '古城大街3区污水仓'
                },
                {
                    name: '最高湿度',
                    number: 22,
                    unit: 'ppm',
                    location: '实验路2区综合仓'
                },
                {
                    name: '最低含氧量',
                    number: 22,
                    unit: '%',
                    location: '纬三路1区燃气仓'
                }
            ],
            countData: [
                {
                    name: '电子井盖打开',
                    number: 12,
                    unit: '次',
                    isRise: false
                },
                {
                    name: '门禁触发',
                    number: 15,
                    unit: '次',
                    isRise: true
                },
                {
                    name: '红外探测器触发',
                    number: 2,
                    unit: '次',
                    isRise: false
                }
            ]
        };
    },
    mounted() {},
    methods: {}
};
</script>

<style scoped>
.Main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url('../../../assets/VM/vm_module_bg.png') no-repeat;
    background-size: 100% 100%;
}
.title{
    font-size: 2.4vmin;
    position: absolute;
    top: 2.2vh;
    left: 2vw;
    color: white;
}
.left{
    position: absolute;
    width: 44%;
    height: 73%;
    top: 18%;
    left: 7%;
    overflow: auto;
}
.item{
    background: url('../../../assets/UM/videoBody.png') no-repeat;
    background-size: 100% 100%;
    width: 90%;
    margin: 0.4vh auto;
    text-align: center;
}
.name{
    color: white;
    font-size: 1.6vmin;
}
.number{
    color: #37bbcc;
    font-size: 2vmin;
}
.location{
    color: white;
    font-size: 1.2vmin;
}
.right{
    width: 40%;
    height: 73%;
    position: absolute;
    top: 18%;
    right: 8%;
    text-align: center;
    overflow: auto;
}
.count{
    margin: 1vh auto;
}
.riseIcons{
    font-size: 2vmin;
    float: right;
    margin-right: 1vw;
}
</style>
