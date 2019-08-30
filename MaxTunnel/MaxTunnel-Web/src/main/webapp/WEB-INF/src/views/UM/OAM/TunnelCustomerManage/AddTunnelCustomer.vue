<template>
    <div>
        <Steps :current="current" class="steps">
            <Step v-for="item in titles" :key="item" :title="item"></Step>
        </Steps>
        <div class="formWrapper">
            <p class="formTitle">{{titles[current]}}</p>
            <Form ref="addCompanyInfo" :model="companyInfo" :label-width="140">
                <div v-if="!current">
                    <FormItem
                        label="企业名称:"
                        prop="company.name"
                        :rules="{required: true, message: '请输入企业名称', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.name"
                            placeholder="请输入企业名称"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="信用代码:"
                        prop="company.creditNo"
                        :rules="{required: true, message: '请输入信用代码', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.creditNo"
                            placeholder="请输入信用代码"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="开户行:"
                        prop="company.bank"
                        :rules="{ required: true, message: '请输入开户行', trigger: 'blur' }"
                    >
                        <Input
                            v-model="companyInfo.company.bank"
                            placeholder="请输入开户行"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="账号:"
                        prop="company.account"
                        :rules="{required: true, message: '请输入账号', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.account"
                            placeholder="请输入账号"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="注册地址:"
                        prop="company.address"
                        :rules="{required: true, message: '请输入地址', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.address"
                            placeholder="请输入注册地址信息"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="注册电话:"
                        prop="company.phone"
                        :rules="[{required: true, message: '请输入注册电话', trigger: 'blur'},{ validator: validates.registerTel, trigger: 'blur' },
                        ]"
                    >
                        <Input
                            v-model="companyInfo.company.phone"
                            placeholder="请输入注册电话"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="邮箱:"
                        prop="company.mail"
                        :rules="[{required: true, message: '请输入邮箱', trigger: 'blur'},{ validator: validates.validateEmail, trigger: 'blur' }]"
                    >
                        <Input
                            v-model="companyInfo.company.mail"
                            placeholder="请输入邮箱"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                </div>
                <div v-if="current === 1">
                    <FormItem
                        label="姓名:"
                        prop="list[0].contact"
                        :rules="{required: true, message: '请输入一般联系人姓名', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.list[0].contact"
                            placeholder="请输入一般联系人姓名"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem label="职责:">
                        <Input
                            v-model="companyInfo.list[0].duty"
                            placeholder="请输入一般联系人职责"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="手机:"
                        prop="list[0].tel"
                        :rules="[{required: true, message: '请输入一般联系人手机', trigger: 'blur'},{ validator: validates.validatePhone, trigger: 'blur' }]"
                    >
                        <Input
                            v-model="companyInfo.list[0].tel"
                            placeholder="请输入一般联系人手机"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="电话:"
                        prop="list[0].tel2"
                        :rules="{ validator: validatePhone, trigger: 'blur' }"
                    >
                        <Input
                            v-model="companyInfo.list[0].tel2"
                            placeholder="请输入一般联系人电话"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem label="邮箱:" :rules="{ validator: validateEmail, trigger: 'blur' }">
                        <Input
                            v-model="companyInfo.list[0].mail"
                            placeholder="请输入一般联系人邮箱"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                </div>
                <div v-if="current === 2">
                    <FormItem
                        label="姓名:"
                        prop="list[1].contact"
                        :rules="{required: true, message: '请输入紧急联系人姓名', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.list[1].contact"
                            placeholder="请输入紧急联系人姓名"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem label="职责:">
                        <Input
                            v-model="companyInfo.list[1].duty"
                            placeholder="请输入紧急联系人职责"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="手机:"
                        prop="list[1].tel"
                        :rules="[{required: true, message: '请输入一般联系人手机', trigger: 'blur'},{ validator: validates.validatePhone, trigger: 'blur' }]"
                    >
                        <Input
                            v-model="companyInfo.list[1].tel"
                            placeholder="请输入紧急联系人手机"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="电话:"
                        prop="list[1].tel2"
                        :rules="{ validator: validatePhone, trigger: 'blur' }"
                    >
                        <Input
                            v-model="companyInfo.list[1].tel2"
                            placeholder="请输入紧急联系人电话"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem label="邮箱:" :rules="{ validator: validateEmail, trigger: 'blur' }">
                        <Input
                            v-model="companyInfo.list[1].mail"
                            placeholder="请输入紧急联系人邮箱"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                </div>
                <div v-if="current === 3">
                    <FormItem
                        label="周期:"
                        prop="company.inspectionNo"
                        :rules="{required: true, message: '请输入固定巡检周期', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.inspectionNo"
                            placeholder="请输入固定巡检周期"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="时间:"
                        prop="company.inspectionTime"
                        :rules="{required: true, message: '请输入固定巡检时间', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.inspectionTime"
                            placeholder="请输入固定巡检时间"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                </div>
                <div v-if="current === 4" class="inspectionItems">
                    <FormItem
                        label="姓名:"
                        prop="company.insName"
                        :rules="{required: true, message: '请输入姓名', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.insName"
                            placeholder="请输入固定巡检人员姓名"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="身份证号:"
                        prop="company.insIdentity"
                        :rules="{required: true, message: '请输入身份证', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.insIdentity"
                            placeholder="请输入固定巡检人员身份证号"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="职责:"
                        prop="company.insDuty"
                        :rules="{required: true, message: '请输入职责', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.insDuty"
                            placeholder="请输入固定巡检人员职责"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="手机："
                        prop="company.insPhone"
                        :rules="[{required: true, message: '请输入手机', trigger: 'blur'},{ validator: validates.validatePhone, trigger: 'blur' }]"
                    >
                        <Input
                            v-model="companyInfo.company.insPhone"
                            placeholder="请输入固定巡检人员电话"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="资格证类型:"
                        prop="company.insQualification"
                        :rules="{required: true, message: '请输入资格证类型', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.insQualification"
                            placeholder="请输入固定巡检人员资格证类型"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="编号:"
                        prop="company.qualificationNo"
                        :rules="{required: true, message: '请输入资格证编号', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.qualificationNo"
                            placeholder="请输入资格证编号"
                            class="input"
                            type="text"
                        />
                    </FormItem>
                    <FormItem
                        label="证书图片:"
                        prop="company.imgUrl"
                        :rules="{required: true, message: '请选择证书图片', trigger: 'blur'}"
                    >
                        <Input
                            placeholder="支持png,jpeg,jpg格式"
                            type="file"
                            @on-change="imgChange"
                            accept="image/png, image/jpeg, image/jpg"
                            class="imgInput"
                        />
                    </FormItem>
                    <FormItem>
                        <div class="imgReview">
                            <image-from-url
                                v-if="imgPreview.url"
                                v-show="imgPreview.isInit"
                                :url="imgPreview.url"
                                style="width: 100%; height: 100%"
                            ></image-from-url>
                            <img
                                v-show="!imgPreview.isInit && companyInfo.company.imgUrl"
                                :src="companyInfo.company.imgUrl"
                                style="width: 100%; height: 100%"
                            >
                        </div>
                    </FormItem>
                </div>
            </Form>
            <div class="options">
                <Button type="primary" class="prev" @click="prev" v-if="current">上一步</Button>
                <Button type="primary" class="next" @click="next">{{current === 4 ? '提交' : '下一步'}}</Button>
            </div>
        </div>
    </div>
</template>
<script>
import CompanyService from "../../../../services/companyService";
export default {
    data() {
        const validateEmail = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("请输入邮箱"));
            } else {
                let reg = new RegExp(
                    "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
                );
                if (!reg.test(value)) {
                    callback(new Error("邮箱格式不对"));
                } else {
                    callback();
                }
            }
        };
        const validatePhone = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("不能为空"));
            } else {
                let isPhone = /^1(3|4|5|7|8)\d{9}$/;
                if (isPhone.test(value)) {
                    callback();
                } else {
                    callback(new Error("请输入正确的格式"));
                }
            }
        };
        const validateTel = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("不能为空"));
            } else {
                let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
                if (isMob.test(value)) {
                    callback();
                } else {
                    callback(new Error("请输入正确的格式"));
                }
            }
        };
        const registerTel = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("不能为空"));
            } else {
                let isPhone = /^1(3|4|5|7|8)\d{9}$/;
                let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
                if (isPhone.test(value) || isMob.test(value)) {
                    callback();
                } else {
                    callback(new Error("请输入正确的格式"));
                }
            }
        };
        return {
            current: 0,
            titles: [
                "企业基本信息",
                "一般联系人信息",
                "紧急联系人信息",
                "固定巡检信息",
                "固定巡检人员备案"
            ],
            companyInfo: {
                company: {
                    name: null,
                    creditNo: null,
                    account: null,
                    bank: null,
                    address: null,
                    phone: null,
                    mail: null,
                    inspectionNo: null,
                    inspectionTime: null,
                    insName: null,
                    insIdentity: null,
                    insDuty: null,
                    insPhone: null,
                    insQualification: null,
                    qualificationNo: null,
                    imgUrl: null,
                    inspectionInfo: null
                },
                list: [
                    {
                        contact: null,
                        duty: null,
                        tel: null,
                        tel2: null,
                        mail: null,
                        type: "一般联系人"
                    },
                    {
                        contact: null,
                        duty: null,
                        tel: null,
                        tel2: null,
                        mail: null,
                        type: "紧急联系人"
                    }
                ]
            },
            validates: {
                validateEmail,
                validatePhone,
                validateTel,
                registerTel
            },
            imgPreview: {
                url: null,
                isInit: false
            }
        };
    },
    methods: {
        next() {
            if (this.current < 4) {
                this.current += 1;
            } else {
                this.$refs.addCompanyInfo.validate(valid => {
                    console.log(valid);
                    console.log(this.companyInfo);
                    if (valid) {
                        this.companyInfo.company.inspectionInfo = this.inspectionInfo;
                        let config = {
                            headers: { "Content-Type": "multipart/form-data" }
                        }; //添加请求头
                        CompanyService.addCompany(this.companyInfo).then(
                            response => {
                                if (response.id) {
                                    CompanyService.uploadQualification(
                                        response.id,
                                        this.uploadImageParam,
                                        config
                                    ).then(
                                        res => {
                                            this.$Message.success("添加成功");
                                            this.$nextTick(() => {
                                                this.$router.push(
                                                    "/UM/tunnelCustomer/list"
                                                );
                                            });
                                        },
                                        error => {
                                            this.$Message.error("添加失败");
                                        }
                                    );
                                }
                            },
                            error => {
                                this.$Message.error("添加失败");
                            }
                        );
                    } else {
                        this.$Message.error("请填写正确的客户信息");
                    }
                });
            }
        },
        prev() {
            this.current += -1;
        },
        imgChange(e) {
            this.imgPreview.isInit = false;
            this.companyInfo.company.imgUrl = window.URL.createObjectURL(
                e.srcElement.files[0]
            );
            // let reader = new FileReader();
            // reader.readAsDataURL(e.srcElement.files[0]);//发起异步请求
            // reader.onload = function(e){
            //     //读取完成后，将结果赋值给img的src
            //     this.imgPreview.url = e.target.result
            // }

            let file = e.target.files[0];
            let param = new FormData(); //创建form对象
            param.append("file", file, file.name); //通过append向form对象添加数据
            param.append("chunk", this.companyInfo); //添加form表单中其他数据
            this.uploadImageParam = param;
            // console.log(param.get("file")); //FormData私有类对象，访问不到，可以通过get判断值是否传进去
        }
    }
};
</script>

<style scoped>
.steps {
    width: 92%;
    margin-top: 2vmin;
    margin-left: 10%;
}
.steps >>> .ivu-steps-head,
.steps >>> .ivu-steps-title {
    background: transparent;
    color: #fff;
}
.steps >>> .ivu-steps-tail {
    padding: 0px 0.5vmin 0 13vmin;
}
.steps >>> .ivu-steps-tail:first-child {
    padding: 0px 0.5vmin 0 12vmin;
}
.steps >>> .ivu-steps-head-inner {
    width: 2.6vmin;
    height: 2.6vmin;
    line-height: 2.4vmin;
    font-size: 1.4vmin;
}
.steps >>> .ivu-steps-title {
    font-size: 1.4vmin;
}
.formWrapper {
    margin: 4vmin auto;
    width: 70%;
    height: 72vmin;
    position: relative;
}
.formTitle {
    font-size: 1.8vmin;
    color: #fff;
    text-align: center;
    margin: 2vmin;
}
.ivu-form-item >>> .ivu-form-item-label {
    width: 14vmin !important;
    line-height: 2.2vmin;
    font-size: 1.4vmin;
}
.ivu-form.ivu-form-label-right {
    width: 96%;
    padding: 10px 20px;
    border-radius: 4px;
    margin: 0 auto;
}
.ivu-form-item {
    margin-bottom: 2.4vmin;
}
.inspectionItems >>> .ivu-form-item {
    margin-bottom: 1.2vmin;
}
.ivu-form >>> .ivu-input {
    background: transparent;
    color: #eaeef2;
    border-radius: 1vmin;
}
.ivu-form >>> .ivu-input::-webkit-input-placeholder {
    color: #aaa;
}
.input {
    width: 86%;
}
.ivu-form >>> .ivu-form-item-label {
    color: #eaeef2;
}
.ivu-select,
.ivu-select >>> .ivu-select-selection,
.ivu-input-wrapper >>> .ivu-input,
.ivu-date-picker >>> .ivu-input,
.ivu-select.ivu-select-single >>> .ivu-select-selected-value,
.ivu-select.ivu-select-single >>> .ivu-select-placeholder {
    height: 3.2vmin;
    line-height: 3.2vmin;
    font-size: 1.4vmin;
}
.ivu-form-item-required .ivu-form-item-label:before {
    font-size: 1.6vmin !important;
}
.formWrapper >>> .ivu-form-item-error-tip {
    font-size: 1.3vmin;
}
.options {
    width: 40vmin;
    position: absolute;
    bottom: 4vmin;
    left: 40%;
}
.prev {
    background: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #61a2b3;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
    margin-right: 1vmin;
}
.next {
    background: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #61a2b3;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
}
.imgInput {
    width: 90%;
}
.imgInput >>> .ivu-input {
    border: 0px;
}
.imgReview {
    background-color: #38569173;
    border: 1px solid #83a6ed;
    border-radius: 1vmin;
    width: 29.6vmin;
    height: 16vmin;
}
.imgReview >>> img {
    border-radius: 1vmin;
}
@media (min-width: 2200px) {
    .formWrapper >>> .ivu-form-item-error-tip {
        left: 8vmin;
    }
    .inspectionItems >>> .ivu-form-item {
        margin-bottom: 2.2vmin;
    }
}
</style>
