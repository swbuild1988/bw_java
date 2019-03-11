<template>
    <div class='grid1'>
        <div class="gridTitle">
            <p>{{title}}</p>
        </div>
        <div class="subTitle">
            <div class="name">
                <p> {{subTitle[0]}} </p>
            </div>
            <div class="val">
                <p> {{subTitle[1]}} </p>
            </div>
            <div class="percent">
                <p> {{subTitle[2]}} </p>
            </div>
        </div>
        <div
            v-for="item in data"
            class="row"
            :key="item.name"
        >
            <div class="name">
                <p>{{item.name}}</p>
            </div>
            <div class="val">
                <p>{{item.value + item.unit}}</p>
            </div>
            <div class="percent">
                <p>{{item.percent}}</p>
            </div>
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
    height: 25%;
}
.gridTitle > p {
    font-size: 1.7vmin;
    color: #fff;
}
.subTitle {
    position: relative;
    width: 100%;
    height: 15%;
}
.subTitle >>> p {
    color: #ccc;
    font-size: 1.25vmin;
}
.row {
    position: relative;
    width: 100%;
    height: 16%;
    background: url("../../assets/VM/common_item_bg.png");
    background-size: 100% 100%;
    margin: 2% 0;
}
.row >>> p {
    font-size: 1.45vmin;
    color: #fff;
}
.subTitle .name {
    position: absolute;
    height: 100%;
    width: 50%;
    left: 0%;
    top: 0%;
    padding-left: 1.65vmin;
}
.subTitle .val {
    text-align: center;
    position: absolute;
    height: 100%;
    width: 25%;
    left: 50%;
    top: 0%;
}
.subTitle .percent {
    text-align: right;
    position: absolute;
    height: 100%;
    width: 25%;
    left: 75%;
    top: 0%;
}
.row .name{
    position: absolute;
    height: 100%;
    width: 50%;
    left: 0%;
    top: 8%;
    padding-left: 1.65vmin;
}
.row .val {
    text-align: center;
    position: absolute;
    height: 100%;
    width: 25%;
    left: 50%;
    top: 8%;
}
.row .percent {
    text-align: right;
    position: absolute;
    height: 100%;
    width: 25%;
    left: 75%;
    top: 8%;
}
</style>
