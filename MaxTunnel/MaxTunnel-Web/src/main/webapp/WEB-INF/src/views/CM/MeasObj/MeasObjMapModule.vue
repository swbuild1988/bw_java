<template>
    <div>
        <Modal v-model="show.state" title="新增流程节点" width="600">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="监测对象Id：" prop="objectId">
                    <Input
                        v-model="formValidate.objectId"
                        placeholder="请输入监测对象名称"
                        class="InputWidth"
                    />
                </FormItem>
                <FormItem label="输入值：" prop="inputValue">
                    <Select v-model="formValidate.inputValue" class="InputWidth">
                        <Option
                            v-for="item in lists"
                            :key="item.val"
                            :value="item.val+''"
                        >{{ item.key }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="MaxView监测对象Id：" prop="objectId2">
                    <Input
                        v-model="formValidate.objectId2"
                        placeholder="请输入MaxView监测对象"
                        class="InputWidth"
                    />
                </FormItem>
                <FormItem label="输出值：" prop="outputValue">
                    <Select v-model="formValidate.outputValue" class="InputWidth">
                        <Option
                            v-for="item in lists"
                            :key="item.val"
                            :value="item.val+''"
                        >{{ item.key }}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { MeasObjServer } from "../../../services/MeasObjectSerivers";
import { EnumsService } from "../../../services/enumsService";

export default {
    name: "meas-obj-module",
    data() {
        return {
            formValidate: {
                id: null,
                objectId: null,
                inputValue: 0,
                objectId2: null,
                outputValue: 0
            },
            ruleValidate: {
                objectId: [
                    {
                        required: true,
                        message: "监测对象不能为空",
                        trigger: "blur"
                    }
                ],
                objectId2: [
                    {
                        required: true,
                        message: "MaxView监测对象不能为空",
                        trigger: "blur"
                    }
                ],
                inputValue: [
                    {
                        required: true,
                        message: "输入值不能为空",
                        trigger: "blur"
                    }
                ],
                outputValue: [
                    {
                        required: true,
                        message: "输出值不能为空",
                        trigger: "blur"
                    }
                ]
            },
            lists: []
        };
    },
    props: {
        show: {
            state: {
                default: false
            }
        },
        editInfo: {
            required: true
        },
        type: {
            required: true
        }
    },
    watch: {
        "show.state": function(newValue, oldValue) {
            if (this.type == "add") {
                this.cleanFormValidate();
            } else {
                for (let item in this.editInfo) {
                    for (let info in this.formValidate) {
                        if (item === info) {
                            this.formValidate[info] = this.editInfo[item];
                        }
                    }
                }
            }
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        sendMsg: function(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    this.$emit("addOrEdit", this.formValidate);
                } else {
                    this.$Message.error("操作失败，请稍后重试");
                }
            });
        },
        cleanFormValidate() {
            for (let item in this.formValidate) {
                this.formValidate[item] = null;
            }
        },
        init() {
            let _this = this;
            EnumsService.getSwitch().then(
                result => {
                    _this.lists = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        }
    }
};
</script>

<style scoped>
.InputWidth {
    width: 85%;
}
</style>
