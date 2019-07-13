<template>
    <Modal :title="title" v-model="show.state" :mask-closable="false" class="modal">
        <p style="font-size: 2vmin; text-align:center;">{{ tunnelName }}</p>
        <div class="deTableTitle" v-if="checkData.length">
            <div class="defenceItem"></div>
            <div
                v-for="title in checkData[0].store"
                :key="title.storeId"
                class="defenceItem"
            >{{title.storeName}}</div>
        </div>
        <div class="table">
            <div v-for="item in checkData" :key="item.areaId" class="defenceRow">
                <div class="defenceItem">
                    <span>{{item.areaName}}</span>
                </div>
                <div v-for="store in item.store" :key="store.storeId" class="defenceItem">
                    <Checkbox v-model="store.run.value"></Checkbox>
                </div>
            </div>
        </div>
        <footer class="deOptions">
            <Button type="primary" class="allCheck" size="small" @click="checkAll">全选</Button>
            <Button type="primary" class="noneCheck" size="small" @click="checkReverse">反选</Button>
            <Button type="primary" class="save" size="small" @click="save">确定</Button>
        </footer>
        <div slot="footer"></div>
    </Modal>
</template>
<script>
export default {
    name: "show-defence",
    props: {
        show: {
            state: {
                default: false
            }
        },
        title: {
            default: "布防/撤防",
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
                area.store.forEach(store => {
                    store.val = true;
                });
            });
        },
        checkReverse() {
            this.checkData.map(area => {
                area.store.forEach(store => {
                    store.val = !store.val;
                });
            });
        },
        save() {
            let data = [];
            this.checkData.map(area => {
                area.store.forEach(item => {
                    data.push({
                        key: item.id,
                        val: item.val
                    });
                });
            });
            this.$emit("saveDefence", data);
        }
    }
};
</script>
<style scoped>
.modal >>> .ivu-modal {
    width: 52vmin !important;
}
.table {
    max-height: 54vmin;
    overflow: auto;
    width: 44vmin;
}
.table::-webkit-scrollbar {
    width: 1vmin;
    height: 0.2vmin;
}
.table::-webkit-scrollbar-thumb {
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.table::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
.deOptions,
.deTableTitle,
.defenceRow {
    width: 44vmin;
    margin: 0 auto;
    text-align: center;
}
.defenceItem {
    width: 6vmin;
    height: 4vmin;
    display: inline-block;
    font-size: 1.4vmin;
}
.defenceItem >>> .ivu-checkbox-inner {
    width: 1.6vmin;
    height: 1.6vmin;
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

