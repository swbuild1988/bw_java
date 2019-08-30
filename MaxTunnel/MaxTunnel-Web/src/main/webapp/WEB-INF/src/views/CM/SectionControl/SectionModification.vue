<template>
    <div>
        <!-- 区段管理之修改区段 -->
        <Modal v-model="show.state" title="修改区段" :width="600">
            <Form ref="formValidate" :model="formValidate" :label-width="120" :rules="validateForm">
                <FormItem label="区段名称：" prop="name">
                    <Input v-model="formValidate.name" disabled placeholder="请输入区段名"/>
                </FormItem>
                <FormItem label="所属管廊：">
                    <Input v-model="formValidate.tunnel" disabled/>
                </FormItem>
                <FormItem label="所属管仓：">
                    <Input v-model="formValidate.store" disabled/>
                </FormItem>
                <FormItem label="所属区域：">
                    <Input v-model="formValidate.area" disabled/>
                </FormItem>
                <FormItem label="可安装管线数：">
                    <Input v-model="formValidate.totalCableNumber" placeholder="请输入可安装管线数："/>
                </FormItem>
                <FormItem label="s1：" prop="s1">
                    <Poptip trigger="hover" placement="top-start">
                        <img
                            slot="content"
                            :src="sections1s2"
                            placement="top"
                            alt="管廊方向说明图"
                            style="height: 300px;"
                        >
                        <div slot="content">s1为管仓到区头的距离,如果是综合仓、电力仓等之类的主仓，s1的值为0</div>
                        <Input v-model="formValidate.s1" placeholder="请输入s1的值"></Input>
                    </Poptip>
                </FormItem>
                <FormItem label="s2：" prop="s2">
                    <Poptip trigger="hover" placement="top-start">
                        <img
                            slot="content"
                            :src="sections1s2"
                            placement="top"
                            alt="管廊方向说明图"
                            style="height: 300px;"
                        >
                        <div slot="content">s2为管仓到区尾的距离,如果是综合仓、电力仓等之类的主仓，s2的值为0</div>
                        <Input v-model="formValidate.s2" placeholder="请输入s2的值"></Input>
                    </Poptip>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsgtoManage('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { TunnelService } from "@/services/tunnelService";
export default {
    name: "section-change",
    data() {
        // const checkTotalCableNumber = (rule, value, callback) => {
        //     console.log('value', value)
        //     if(value==null||value==''||value==undefined){
        //         callback(new Error('可安装管线数不能为空'))
        //     }else{
        //         callback()
        //     }
        // }
        return {
            tunnels: [],
            stores: [],
            areas: [],
            formValidate: {
                name: null,
                tunnel: null,
                store: null,
                area: null,
                totalCableNumber: null,
                s1: null,
                s2: null
            },
            sections1s2: require("@/assets/CM/section.png"),
            validateForm: {
                name: [
                    {
                        required: true,
                        message: "区段名称不能为空",
                        trigger: "blur"
                    }
                ],
                // totalCableNumber: [
                //     { required: true, trigger: 'blur', validator: checkTotalCableNumber }
                // ],
                s1: [
                    { required: true, message: "s1不能为空", trigger: "blur" }
                ],
                s2: [{ required: true, message: "s2不能为空", trigger: "blur" }]
            }
        };
    },
    props: {
        show: {
            state: {
                default: false
            }
        },
        sectionId: null
    },
    watch: {
        sectionId(newValue, oldValue) {
            if (newValue) {
                this.getSectionInfoById(newValue);
            }
        }
    },
    mounted() {
        this.getSectionInfoById(this.sectionId);
    },
    methods: {
        sendMsgtoManage(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    let params = {
                        id: this.sectionId,
                        totalCableNumber: this.formValidate.totalCableNumber,
                        s1: this.formValidate.s1,
                        s2: this.formValidate.s2
                    };
                    TunnelService.updateSection(params).then(
                        res => {
                            this.$emit("listenToChange");
                        },
                        err => this.$Message.error("区段信息填写错误")
                    );
                } else {
                    this.$Message.error("区段信息填写错误");
                }
            });
        },
        getSectionInfoById(id) {
            TunnelService.getSectionById(id).then(
                res => {
                    this.formValidate = {
                        name: res.name,
                        tunnel: res.store.tunnel.name,
                        store: res.store.name,
                        area: res.area.name,
                        totalCableNumber: res.totalCableNumber,
                        s1: res.s1 + "",
                        s2: res.s2 + ""
                    };
                },
                err => this.Log.info(err)
            );
        }
    }
};
</script>

<style scoped>
.directionImg {
    width: 20px;
    height: 20px;
}
.ivu-poptip {
    width: 100%;
}
.ivu-poptip >>> .ivu-poptip-rel {
    width: 100%;
}
</style>

