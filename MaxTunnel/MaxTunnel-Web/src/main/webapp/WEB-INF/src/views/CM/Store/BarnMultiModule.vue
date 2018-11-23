<template>
    <div>
        <!-- 管仓管理的新增管仓 -->
        <Modal v-model="show.state" title="批量新增管仓">
            <Form ref="formValidate" :model="formValidate" :label-width="200">
                <FormItem label="所属管廊" prop="tunnelId">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="InputWidth">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="批量加入管仓类型">
                    <CheckboxGroup v-model="formValidate.types">
                        <Checkbox v-for="item in types" :key="item.id" :label="item.id">
                            <span>{{item.name}}</span>
                        </Checkbox>
                    </CheckboxGroup>
                </FormItem>
                <FormItem label="命名方式">
                    <RadioGroup  v-model="formValidate.nameChoose">
                        <Radio v-for="item in nameChooses" :key="item.id" :label="item.id">
                            <span>{{item.name}}</span>
                        </Radio>
                    </RadioGroup>
                </FormItem>
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
            nameChooses: [
                {
                    id: 1,
                    name: "同管仓类型"
                },
                {
                    id: 2,
                    name: '管廊名 + "-" + 管仓类型'
                }
            ],
            flag: true,
            formValidate: {
                types: [],
                nameChoose: 1,
                tunnelId: 0
            },
            longitude: 0,
            latitude: 0,
            highness: 0
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
        getTunnelList() {
            //获取所有管廊的简单列表
            this.axios.get("/tunnels").then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.tunnels = data;
                    this.formValidate.tunnelId = this.tunnels[0].id;
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
                console.log("types", this.types);
            });
        },
        sendMsg: function(data) {
            console.log("sendmsg:", data);
            let uploadStores = [];
            for (
                let index = 0;
                index < this.formValidate.types.length;
                index++
            ) {
                let t_type = this.types.find(
                    a => a.id == this.formValidate.types[index]
                );
                let t_tunnel = this.tunnels.find(
                    a => a.id == this.formValidate.tunnelId
                );
                let t_store = {
                    name:
                        this.formValidate.nameChoose == 1
                            ? t_type.name
                            : t_tunnel.name + "-" + t_type.name,
                    sn: t_type.sn,
                    tunnelId: this.formValidate.tunnelId,
                    storeTypeId: this.formValidate.types[index],
                    camera: ""
                };
                uploadStores.push(t_store);
            }
            console.log("uploadStores", uploadStores);
            this.$refs[data].validate(valid => {
                if (valid) {
                    this.$emit("listenToAddMulti", uploadStores);
                } else {
                    this.$Message.error("添加失败");
                }
            });
        },
        cleanFormValidate() {
            this.formValidate.types = [];
            this.formValidate.nameChoose = 1;
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


