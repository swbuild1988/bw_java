<template>
    <div class='Grid1'>
        <div class="Title">
            <p>{{title}}</p>
        </div>
        <div
            v-for="item in data"
            class="Row"
            :key="item.name"
        >
            <div class="Name">
                <p>{{item.name}}</p>
            </div>
            <div class="Result">
                <div class="Value">
                    <p>{{item.value}}</p>
                </div>
                <div class="Unit">
                    <p>{{item.unit}}</p>
                </div>
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
.Grid1 {
    position: relative;
    left: 10%;
    width: 80%;
    height: 100%;
    overflow: auto;
}
.Title {
    width: 100%;
    height: 20%;
}
.Title > p {
    font-size: 1vmin;
    color: #fff;
}
.Row {
    width: 100%;
    height: 15%;
}
.Row >>> p {
    font-size: 0.75vmin;
    color: #fff;
}
.Row .Name {
    float: left;
    height: 100%;
}
.Row .Result {
    float: right;
    height: 100%;
}
.Result .Value {
    float: left;
    height: 100%;
}
.Result .Unit {
    margin-left: 0.2vw;
    float: left;
    height: 100%;
}
</style>
