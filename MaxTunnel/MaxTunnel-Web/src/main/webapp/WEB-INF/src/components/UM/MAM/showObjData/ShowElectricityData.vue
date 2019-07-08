<template>
    <article class="elecDataWrapper">
        <header class="title">{{obj.name}}</header>
        <Row>
            <Col span="24" v-for="item in obj.data" :key="item.id">
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
        obj: {
            type: Object,
            default: {
                id: 1,
                name: "51号电表",
                data: [
                    {
                        key: "有功电能",
                        val: 23
                    },
                    {
                        key: "无功电能",
                        val: 2
                    },
                    {
                        key: "三相线电压",
                        val: 23
                    },
                    {
                        key: "有功电能",
                        val: 23
                    },
                    {
                        key: "无功电能",
                        val: 2
                    },
                    {
                        key: "三相线电压",
                        val: 23
                    },
                    {
                        key: "有功电能",
                        val: 23
                    },
                    {
                        key: "无功电能",
                        val: 2
                    },
                    {
                        key: "三相线电压",
                        val: 23
                    }
                ]
            }
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
