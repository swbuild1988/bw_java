<template>
    <div>
        <!-- 管仓管理的新增管仓 -->
        <Modal v-model="show.state" title="新增管仓">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
                <FormItem label="管仓名" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入新管仓名" class="InputWidth"/>
                </FormItem>
                <FormItem label="所属管廊" prop="tunnelId">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="InputWidth">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="管仓类型" prop="storeTypeId">
                    <Select v-model="formValidate.storeTypeId" placeholder="请选择管仓类型" class="InputWidth" @on-change="storeTypeChange">
                        <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="编号" prop="sn">
                    <Input v-model="formValidate.sn" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="经度" prop="longitude">
                    <Input v-model="longitude" placeholder="请输入经度" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="纬度" prop="latitude">
                    <Input v-model="latitude" placeholder="请输入纬度" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="高度" prop="highness">
                    <Input v-model="highness" placeholder="请输入高度" class="InputWidth"></Input>
                </FormItem>
                <span v-show="!flag" class="errorStyle">任务名和任务组需唯一</span>
            </Form> 
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
export default {
    name: "store-add",
    data() {
        return {
            tunnels: [],
            types: [],
            flag: true,
            formValidate: {
                name: "",
                sn: "",
                tunnelId: "",
                storeTypeId: "",
                camera: ""
            },
            longitude: 0,
            latitude: 0,
            highness: 0,
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "管仓名不能为空",
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    props: {
        show: {
            state: {
                default: false
            }
        },
        addInfo: {}
    },
    watch: {
        "show.state": function(newValue, oldValue) {
            this.cleanFormValidate();
        },
        "formValidate.name": function(newValue, oldValue) {
            if (newValue != null) {
                this.checkName(newValue);
            }
        }
    },
    mounted() {
        this.getTunnelList();
        this.getStoreTypeList();
    },
    methods: {
        storeTypeChange() {
            console.log("types", this.types);
            let store = this.types.find(
                a => a.id === this.formValidate.storeTypeId
            );
            console.log("storeTypeChange:", store);
            if (this.formValidate.name == "")
                this.formValidate.name = store.name;
            this.formValidate.sn = store.sn;
        },
        getTunnelList() {
            //获取所有管廊的简单列表
            this.axios.get("/tunnels").then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.tunnels = data;
                }
            });
        },
        getStoreTypeList() {
            this.axios.get("/store-type/list").then(res => {
                let { code, data } = res.data;
                let _types = [];
                if (code == 200) {
                    for (let i = 0; i < data.length; i++) {
                        let type = {};
                        type.id = data[i].id;
                        type.name = data[i].name;
                        type.sn = data[i].sn;
                        _types.push(type);
                    }
                    this.types = _types;
                }
            });
        },
        sendMsg: function(data) {
            this.formValidate.camera =
                this.longitude + "," + this.latitude + "," + this.highness;
            this.$refs[data].validate(valid => {
                if (valid) {
                    this.$emit("listenToAdd", this.formValidate);
                } else {
                    this.$Message.error("添加失败");
                }
            });
        },
        checkName(name) {
            this.axios.get("/stores/ajax/" + name).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.flag = data;
                }
            });
        },
        cleanFormValidate() {
            this.formValidate.name = null;
            this.formValidate.tunnelId = null;
            this.formValidate.storeTypeId = null;
            this.longitude = 0;
            this.latitude = 0;
            this.highness = 0;
            this.flag = true;
        }
    }
};
</script>

<style scoped>
.InputWidth {
    width: 85%;
}
.errorStyle {
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 100px;
    left: 120px;
}
</style>


