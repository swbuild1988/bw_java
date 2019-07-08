<template>
    <Modal :title="title" v-model="show.state" :mask-closable="false" class="modal">
        <p style="font-size: 2vmin; text-align:center;">{{ tunnelName }}</p>
        <Row class="itemWrapper">
            <Col span="8" v-for="item in checkData" :key="item.areaId">
                <div class="item">
                    <span style="width:30%">{{item.areaName}}</span>
                    <Checkbox v-model="item.val"></Checkbox>
                </div>
            </Col>
        </Row>
        <footer class="options">
            <Button type="primary" class="allCheck" size="small" @click="checkAll">全选</Button>
            <Button type="primary" class="noneCheck" size="small" @click="checkReverse">反选</Button>
            <Button type="primary" class="save" size="small" @click="save">确定</Button>
        </footer>
        <div slot="footer"></div>
    </Modal>
</template>
<script>
export default {
    name: "show-linkage",
    props: {
        show: {
            state: {
                default: false
            }
        },
        title: {
            default: "联动投入/联动切除",
            type: String
        },
        tunnelName: {
            default: "古城大街",
            type: String
        },
        data: {
            required: true,
            type: Array
        }
    },
    data() {
        return {};
    },
    computed: {
        checkData() {
            return this.data;
        }
    },
    mounted() {},
    methods: {
        checkAll() {
            this.checkData.map(area => {
                area.val = true;
            });
        },
        checkReverse() {
            this.checkData.map(area => {
                area.val = !area.val;
            });
        },
        save() {
            let data = [];
            this.checkData.map(item => {
                data.push({
                    key: item.id,
                    val: item.val
                });
            });
            this.$emit("saveLinkage", data);
        }
    }
};
</script>
<style scoped>
.modal >>> .ivu-modal {
    width: 52vmin !important;
}
.itemWrapper,
.options {
    width: 44vmin;
    margin: 0 auto;
    text-align: center;
}
.itemWrapper {
    margin: 1vmin auto;
}
.item {
    display: flex;
    justify-content: center;
    align-items: center;
}
.allCheck,
.save,
.noneCheck {
    margin: 0.4vmin;
    border-color: #b4b9bf;
    border-radius: 1vmin;
    font-size: 1.4vmin;
}
.allCheck {
    background-color: -webkit-linear-gradient(left, #dcd77c, #cabf11);
    background: -o-linear-gradient(right, #dcd77c, #cabf11);
    background: -moz-linear-gradient(right, #dcd77c, #cabf11);
    background: linear-gradient(to right, #dcd77c, #cabf11);
}
.save {
    background-color: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
}
.noneCheck {
    background-color: -webkit-linear-gradient(left, #bcdca9, #6be421);
    background: -o-linear-gradient(right, #bcdca9, #6be421);
    background: -moz-linear-gradient(right, #bcdca9, #6be421);
    background: linear-gradient(to right, #bcdca9, #6be421);
}
</style>


