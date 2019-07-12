<template>
    <article class="elecDataWrapper">
        <header class="title">{{Obj.area + Obj.store + Obj.name}}</header>
        <Row>
            <Col span="24" v-for="item in eleList" :key="item.key">
                <section class="itemCol">
                    <span>{{item.key}}</span>
                    <span class="values">{{item.val}}</span>
                </section>
            </Col>
        </Row>
    </article>
</template>
<script>
export default {
    name: "show-electricity-data",
    props: {
        Obj: {
            type: Object,
            required: true
        }
    },
    computed: {
        eleList() {
            let data = [];
            for (let item in this.Obj.curValue) {
                data.push({
                    key: this.Obj.curValue[item].descript,
                    val: this.Obj.curValue[item].value
                });
            }
            return data;
        }
    }
};
</script>
<style scoped>
.elecDataWrapper {
    background: url("../../../../assets/UM/detailsBg.png") no-repeat;
    background-size: 100% 100%;
    color: #fff;
    font-size: 1.6vmin;
}
.title {
    font-size: 1.8vmin;
    text-align: center;
    margin: 1vmin auto;
}
.itemCol {
    line-height: 4vmin;
    width: 74%;
    margin: auto;
}
.values {
    float: right;
}
</style>
