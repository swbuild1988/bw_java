<template>
    <div :style="backStyle">
        <Form ref="companyInfo" :model="companyInfo" :label-width="140">
            <h2 class="formTitle" v-show="pageType!=pageTypes.Edit">添加企业客户信息</h2>
            <h2 class="formTitle" v-show="pageType==pageTypes.Edit">修改企业客户信息</h2>
            <Row>
                <Col span="7">
                    <p class="subTitle">企业基本信息：</p>
                    <FormItem
                        label="企业名称:"
                        prop="company.name"
                        :rules="{required: true, message: '请输入企业名称', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.name" placeholder="请输入企业名称"/>
                    </FormItem>
                    <FormItem
                        label="信用代码:"
                        prop="company.creditNo"
                        :rules="{required: true, message: '请输入信用代码', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.creditNo" placeholder="请输入信用代码"/>
                    </FormItem>
                    <FormItem
                        label="开户行:"
                        prop="company.bank"
                        :rules="{ required: true, message: '请输入开户行', trigger: 'blur' }"
                    >
                        <Input v-model="companyInfo.company.bank" placeholder="请输入开户行"/>
                    </FormItem>
                    <FormItem
                        label="账号:"
                        prop="company.account"
                        :rules="{required: true, message: '请输入账号', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.account" placeholder="请输入账号"/>
                    </FormItem>
                    <FormItem
                        label="注册地址:"
                        prop="company.address"
                        :rules="{required: true, message: '请输入地址', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.address" placeholder="请输入注册地址信息"/>
                    </FormItem>
                    <FormItem
                        label="注册电话:"
                        prop="company.phone"
                        :rules="[{required: true, message: '请输入注册电话', trigger: 'blur'},{ validator: validates.registerTel, trigger: 'blur' },
                    ]"
                    >
                        <Input v-model="companyInfo.company.phone" placeholder="请输入注册电话"/>
                    </FormItem>
                    <FormItem
                        label="邮箱:"
                        prop="company.mail"
                        :rules="[{required: true, message: '请输入邮箱', trigger: 'blur'},{ validator: validates.validateEmail, trigger: 'blur' }]"
                    >
                        <Input v-model="companyInfo.company.mail" placeholder="请输入邮箱"/>
                    </FormItem>
                </Col>
                <Col span="7" offset="1">
                    <p class="subTitle">一般联系人信息：</p>
                    <FormItem
                        label="姓名:"
                        prop="list[0].contact"
                        :rules="{required: true, message: '请输入一般联系人姓名', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.list[0].contact" placeholder="请输入一般联系人姓名"/>
                    </FormItem>
                    <FormItem label="职责:">
                        <Input v-model="companyInfo.list[0].duty" placeholder="请输入一般联系人职责"/>
                    </FormItem>
                    <FormItem
                        label="手机:"
                        prop="list[0].tel"
                        :rules="[{required: true, message: '请输入一般联系人手机', trigger: 'blur'},{ validator: validates.validatePhone, trigger: 'blur' }]"
                    >
                        <Input v-model="companyInfo.list[0].tel" placeholder="请输入一般联系人手机"/>
                    </FormItem>
                    <FormItem
                        label="电话:"
                        prop="list[0].tel2"
                        :rules="{ validator: validatePhone, trigger: 'blur' }"
                    >
                        <Input v-model="companyInfo.list[0].tel2" placeholder="请输入一般联系人电话"/>
                    </FormItem>
                    <FormItem label="邮箱:" :rules="{ validator: validateEmail, trigger: 'blur' }">
                        <Input v-model="companyInfo.list[0].mail" placeholder="请输入一般联系人邮箱"/>
                    </FormItem>
                    <p class="subTitle">紧急联系人信息：</p>
                    <FormItem
                        label="姓名:"
                        prop="list[1].contact"
                        :rules="{required: true, message: '请输入紧急联系人姓名', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.list[1].contact" placeholder="请输入紧急联系人姓名"/>
                    </FormItem>
                    <FormItem label="职责:">
                        <Input v-model="companyInfo.list[1].duty" placeholder="请输入紧急联系人职责"/>
                    </FormItem>
                    <FormItem
                        label="手机:"
                        prop="list[1].tel"
                        :rules="[{required: true, message: '请输入一般联系人手机', trigger: 'blur'},{ validator: validates.validatePhone, trigger: 'blur' }]"
                    >
                        <Input v-model="companyInfo.list[1].tel" placeholder="请输入紧急联系人手机"/>
                    </FormItem>
                    <FormItem
                        label="电话:"
                        prop="list[1].tel2"
                        :rules="{ validator: validatePhone, trigger: 'blur' }"
                    >
                        <Input v-model="companyInfo.list[1].tel2" placeholder="请输入紧急联系人电话"/>
                    </FormItem>
                    <FormItem label="邮箱:" :rules="{ validator: validateEmail, trigger: 'blur' }">
                        <Input v-model="companyInfo.list[1].mail" placeholder="请输入紧急联系人邮箱"/>
                    </FormItem>
                </Col>
                <Col span="8" offset="1" class="inspectionItems">
                    <p class="subTitle">固定巡检信息：</p>
                    <FormItem
                        label="周期:"
                        prop="company.inspectionNo"
                        :rules="{required: true, message: '请输入固定巡检周期', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.inspectionNo" placeholder="请输入固定巡检周期"/>
                    </FormItem>
                    <FormItem
                        label="时间:"
                        prop="company.inspectionTime"
                        :rules="{required: true, message: '请输入固定巡检时间', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.inspectionTime"
                            placeholder="请输入固定巡检时间"
                        />
                    </FormItem>
                    <p class="subTitle">固定巡检人员备案：</p>
                    <FormItem
                        label="姓名:"
                        prop="company.insName"
                        :rules="{required: true, message: '请输入姓名', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.insName" placeholder="请输入固定巡检人员姓名"/>
                    </FormItem>
                    <FormItem
                        label="身份证号:"
                        prop="company.insIdentity"
                        :rules="{required: true, message: '请输入身份证', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.insIdentity"
                            placeholder="请输入固定巡检人员身份证号"
                        />
                    </FormItem>
                    <FormItem
                        label="职责:"
                        prop="company.insDuty"
                        :rules="{required: true, message: '请输入职责', trigger: 'blur'}"
                    >
                        <Input v-model="companyInfo.company.insDuty" placeholder="请输入固定巡检人员职责"/>
                    </FormItem>
                    <FormItem
                        label="手机："
                        prop="company.insPhone"
                        :rules="[{required: true, message: '请输入手机', trigger: 'blur'},{ validator: validates.validatePhone, trigger: 'blur' }]"
                    >
                        <Input v-model="companyInfo.company.insPhone" placeholder="请输入固定巡检人员电话"/>
                    </FormItem>
                    <FormItem
                        label="资格证类型:"
                        prop="company.insQualification"
                        :rules="{required: true, message: '请输入资格证类型', trigger: 'blur'}"
                    >
                        <Input
                            v-model="companyInfo.company.insQualification"
                            placeholder="请输入固定巡检人员资格证类型"
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
                        />
                    </FormItem>
                    <FormItem>
                        <image-from-url
                            v-if="imgPreview.isInit"
                            :url="imgPreview.url"
                            style="width: 29.6vmin; height: 16vmin"
                        ></image-from-url>
                        <img
                            v-else
                            :src="companyInfo.company.imgUrl"
                            style="width: 29.6vmin; height: 16vmin"
                        >
                    </FormItem>
                </Col>
            </Row>
            <div style="margin: 0 auto; width: 14vmin;" class="btn">
                <Button type="primary" @click="submitcompanyInfo('companyInfo')">保存</Button>
                <Button type="ghost" @click="goBack()" style="margin-right: 8px">返回</Button>
            </div>
        </Form>
    </div>
</template>
<script>
import types from "../../../../../static/Enum.json";
import { EnumsService } from "../../../../services/enumsService";
import CompanyService from "../../../../services/companyService";
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";

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
            pageType: 2,
            pageTypes: types.pageType,
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
            isDisable: false,
            backStyle: {
                backgroundImage:
                    "url(" + require("../../../../assets/UM/backImg.jpg") + ")",
                height: "100%",
                position: "relative",
                backgroundAttachment: "fixed",
                backgroundSize: "cover",
                minHeight: "100%",
                paddingTop: "40px"
            },
            uploadImageParam: null,
            imgPreview: {
                url: null,
                isInit: false
            }
        };
    },
    computed: {
        inspectionInfo() {
            let company = this.companyInfo.company;
            return (
                company.insName +
                "-" +
                company.insIdentity +
                "-" +
                company.insDuty +
                "-" +
                company.insPhone +
                "-" +
                company.insQualification +
                "-" +
                company.qualificationNo
            );
        }
    },
    mounted() {
        if (this.$route.params.id) {
            this.companyInfo.company.id = this.$route.params.id;
        }
        this.pageType = this.$route.params.type ? this.$route.params.type : 3;
        this.getParams();
    },
    components: {
        ImageFromUrl
    },
    methods: {
        submitcompanyInfo(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    this.companyInfo.company.inspectionInfo = this.inspectionInfo;
                    let config = {
                        headers: { "Content-Type": "multipart/form-data" }
                    }; //添加请求头
                    switch (this.pageType) {
                        case this.pageTypes.Add:
                            CompanyService.addCompany(this.companyInfo).then(
                                response => {
                                    console.log(response);
                                    if (response.id) {
                                        CompanyService.uploadQualification(
                                            response.id,
                                            this.uploadImageParam,
                                            config
                                        ).then(
                                            res => {
                                                this.$Message.success(
                                                    "添加成功"
                                                );
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
                                    this.Log.info("添加失败");
                                }
                            );
                            break;
                        case this.pageTypes.Edit:
                            Promise.all([
                                CompanyService.editCompany(this.companyInfo),
                                CompanyService.uploadQualification(
                                    this.companyInfo.company.id,
                                    this.uploadImageParam,
                                    config
                                )
                            ]).then(
                                response => {
                                    this.$Message.success("修改成功");
                                    this.$nextTick(() => {
                                        this.$router.push(
                                            "/UM/tunnelCustomer/list"
                                        );
                                    });
                                },
                                error => {
                                    this.Log.info("修改失败");
                                }
                            );
                            break;
                    }
                } else {
                    this.$Message.error("请填写正确的客户信息");
                }
            });
        },
        getParams() {
            if (this.pageType == this.pageTypes.Edit) {
                CompanyService.getcompanyDetail(
                    this.companyInfo.company.id
                ).then(
                    response => {
                        this.companyInfo.company = response;
                        this.imgPreview.url =
                            "/companies/" +
                            this.companyInfo.company.id +
                            "/img";
                        this.imgPreview.isInit = true;
                        // let len = response.imgUrl.split('\\').length
                        // this.companyInfo.company.imgUrl = response.imgUrl.split('\\')[len - 1]
                        if (response.customers.length) {
                            this.companyInfo.list = response.customers;
                        } else {
                            this.companyInfo.list.map(contact => {
                                for (let item in contact) {
                                    contact[item] = null;
                                }
                            });
                        }
                        this.parseInspectionInfo(response.inspectionInfo);
                    },
                    error => {
                        this.Log.info(error);
                    }
                );
            }
        },
        parseInspectionInfo(inspectionInfo) {
            let strings = inspectionInfo.split("-");
            let company = this.companyInfo.company;
            company.insName = strings[0];
            company.insIdentity = strings[1];
            company.insDuty = strings[2];
            company.insPhone = strings[3];
            company.insQualification = strings[4];
            company.qualificationNo = strings[5];
        },
        //返回
        goBack() {
            this.$router.back(-1);
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
h2 {
    font-size: 1.8vmin;
}
.ivu-form.ivu-form-label-right {
    width: 80%;
    background: #fff;
    padding: 10px 20px;
    border-radius: 4px;
    margin: 0 auto;
}
.subTitle {
    font-size: 1.66vmin;
    margin-left: 1.6vmin;
}
.ivu-form-item {
    margin-bottom: 2.4vmin;
}
.inspectionItems >>> .ivu-form-item {
    margin-bottom: 1.6vmin;
}
@media (min-width: 2200px) {
    .ivu-form-item >>> .ivu-form-item-label {
        width: 11vmin !important;
        line-height: 3.2vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content {
        margin-left: 11vmin !important;
        line-height: 3.2vmin;
    }
    .ivu-select,
    .ivu-select >>> .ivu-select-selection,
    .ivu-input-wrapper >>> .ivu-input,
    .ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,
    .ivu-select.ivu-select-single >>> .ivu-select-placeholder {
        height: 3.6vmin;
        line-height: 3.6vmin;
        font-size: 1.4vmin;
    }
    .ivu-form-item-required .ivu-form-item-label:before {
        font-size: 1.6vmin !important;
    }
    .ivu-form-item-error-tip {
        font-size: 1.4vmin;
    }
}
</style>
