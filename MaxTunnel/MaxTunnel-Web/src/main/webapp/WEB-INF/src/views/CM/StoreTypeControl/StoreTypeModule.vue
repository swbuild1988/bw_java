<template>
    <div>
        <!-- 管仓类型管理的新增管仓类型 -->
        <Modal v-model="show.state" title="新增管仓类型">
            <Form ref="formValidate"  :model="formValidate" :rules="ruleValidate" :label-width="150">
                <FormItem label="管仓类型" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入新管仓类型"/>
                </FormItem>
                <span v-show="!flag" class="errorStyle">管仓类型名需唯一</span>
                <FormItem label="管仓类型编号" prop="sn">
                    <Input v-model="formValidate.sn" placeholder="请输入新管仓类型编号"/>
                </FormItem>
            </Form>
            <div slot="footer" v-show="flag">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
            <div slot="footer" v-show="!flag">
                <Button disabled type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
export default {
    name: "store-type-add",
    data() {
        return {
            flag: true,
            formValidate: {
                name: "",
                sn: ""
            },
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "管仓类型不能为空",
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
        "formValidate.name": function(value, oldValue) {
            if (value != null) {
                this.axios.get("/store-type/ajax/" + value).then(res => {
                    let { code, data } = res.data;
                    if (code == 200) {
                        this.flag = data;
                    }
                });
            }
        }
    },
    methods: {
        sendMsg: function(data) {
            this.$refs[data].validate(valid => {
                if (valid) {
                    this.$emit("listenToAdd", this.formValidate);
                } else {
                    this, $Message.error("添加失败");
                }
            });
        },
        cleanFormValidate() {
            this.formValidate.name = null;
        }
    }
};
</script>

<style scoped>
.errorStyle {
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 105px;
    left: 120px;
}
</style>


