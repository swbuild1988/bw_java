<template>
    <div class='grid1'>
        <div class="gridTitle">
            <p>{{title}}</p>
        </div>
        <div class="subTitle">
            <span v-for="sub in subTitle">
                {{ sub }}
            </span>
        </div>
        <div
            v-for="item in data"
            class="row"
            :key="item.name"
        >
            <span class="name">{{item.name}}</span>
            <span class="val">{{item.value + item.unit}}</span>
            <span class="percent">{{item.percent}}</span>
        </div>
    </div>
</template>

<script>
export default {
    name: "Grid1",
    props: {
        title: {
            type: String,
            require: true
        },
        subTitle: {
            type: Array,
            require: true
        },
        data: {
            // 传进来的样式
            // [{
            //     name: "距离",
            //     value: 0,
            //     unit: "km"
            // }]
            type: Array,
            require: true
        }
    }
};
</script>

<style scoped>
.grid1 {
    position: relative;
    left: 10%;
    width: 80%;
    height: 100%;
    overflow: auto;
}
.gridTitle {
    width: 100%;
    height: 22%;
}
.gridTitle > p {
    font-size: 1.6vmin;
    color: #fff;
}
.subTitle{
    margin: 0 0 0 0.8vmin;
}
.subTitle span{
    margin: 0 0 0 1.58vmin;
    color: #aaa;
    font-size: 1.24vmin;
}
.subTitle span:last-child{
    float: right;
}
.row {
    width: 100%;
    height: 16%;
    background: url('../../assets/VM/common_item_bg.png');
    background-size: 100% 100%;
    margin: 0.6vmin 0;
}
.row >>> span {
    font-size: 1.34vmin;
    color: #fff;
}
.name{
    margin: 0 1.6vmin;
}
.percent{
    float: right;
    margin-right: 0.5vmin;
}


</style>
