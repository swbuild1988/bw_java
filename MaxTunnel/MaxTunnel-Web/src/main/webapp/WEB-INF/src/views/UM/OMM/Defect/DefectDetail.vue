<template>
    <div class="formBG">
        <div class="formTitle" v-show="this.pageType!=1&&this.pageType!=2">添加缺陷</div>
        <div class="formTitle" v-show="this.pageType==1">缺陷详情</div>
        <div class="formTitle" v-show="this.pageType==2">编辑缺陷详情</div>
        <div class="formHeight">
            <Form
                ref="defectDetails"
                :model="defectDetails"
                :rules="validateRules"
                :label-width="120"
                @submit.native.prevent
            >
                <FormItem label="所属管廊：" prop="tunnelId">
                    <Select
                        v-model="defectDetails.tunnelId"
                        :disabled="this.pageType==1"
                        @on-change="changeTunnel()"
                    >
                        <Option
                            v-for="(item,index) in tunnel"
                            :key="index"
                            :value="item.id"
                        >{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属区段：" prop="area.id" v-show="this.pageType!=1">
                    <Select v-model="defectDetails.area.id" :disabled="this.pageType==1">
                        <Option v-for="item in areas" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属区段：" v-show="this.pageType==1" prop="area.id">
                    <Input v-model="defectDetails.area.name" readonly></Input>
                </FormItem>
                <FormItem label="所属管仓：" prop="store.id" v-show="this.pageType!=1">
                    <Select v-model="defectDetails.store.id" :disabled="this.pageType==1">
                        <Option
                            v-for="(item,index) in stores"
                            :key="index"
                            :value="item.id"
                        >{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属管仓：" v-show="this.pageType==1" prop="store.id">
                    <Input v-model="defectDetails.store.name" readonly></Input>
                </FormItem>
                <FormItem label="缺陷名称：" prop="name">
                    <Input
                        v-model="defectDetails.name"
                        type="text"
                        placeholder="请输入缺陷名称"
                        :readonly="this.isTrue"
                    ></Input>
                </FormItem>
                <FormItem label="发现时间：" prop="createTime">
                    <DatePicker
                        type="datetime"
                        v-model="defectDetails.createTime"
                        placeholder="请输入发现时间"
                        style="width: 100%"
                        :readonly="this.isTrue"
                    ></DatePicker>
                </FormItem>
                <FormItem label="缺陷类型：" prop="type">
                    <Select v-model="defectDetails.type" :disabled="this.pageType==1">
                        <Option
                            v-for="(item,index) in type"
                            :key="index"
                            :value="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="对象名：" v-if="defectDetails.type==2&&this.pageType!=1">
                    <Select v-model="defectDetails.objectId" @on-change="getObj()">
                        <Option
                            v-for="(item,index) in objs"
                            :key="index"
                            :value="item.key"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="对象名：" v-show="defectDetails.type==2&&this.pageType==1">
                    <Input v-model="defectDetails.objName" readonly></Input>
                </FormItem>
                <div class="ivu-form-item-error-tip" v-show="choosedObjName">对象名不能为空</div>
                <FormItem label="危险等级：" prop="level">
                    <Select v-model="defectDetails.level" :disabled="this.pageType==1">
                        <Option
                            v-for="(item,index) in level"
                            :key="index"
                            :value="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="缺陷状态：" prop="status">
                    <Select v-model="defectDetails.status" :disabled="this.pageType==1">
                        <Option
                            v-for="(item,index) in status"
                            :key="index"
                            :value="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="缺陷描述：" prop="description">
                    <Input
                        v-model="defectDetails.description"
                        type="textarea"
                        :rows="4"
                        placeholder="请输入缺陷描述"
                        :readonly="this.isTrue"
                    ></Input>
                </FormItem>
                <div
                    style="text-align: center;margin-left: 10vmin;"
                    v-show="this.pageType!=1&&this.pageType!=2"
                >
                    <Button type="ghost" style="margin-right: 8px" @click="goBack()">返回</Button>
                    <Button
                        type="primary"
                        @click="submitDefect('defectDetails')"
                        :disabled="isDisable"
                    >提交</Button>
                </div>
                <div style="text-align: center;margin-left: 100px" v-show="this.pageType==1">
                    <Button type="ghost" @click="goBack()">返回</Button>
                </div>
            </Form>
        </div>
    </div>
</template>
<script>
import types from "../../../../../static/Enum.json";
import { TunnelService } from "../../../../services/tunnelService";
import { DefectService } from "../../../../services/defectService";
import { EnumsService } from "../../../../services/enumsService";
export default {
    data() {
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            defectDetails: {
                id: null,
                tunnelId: null,
                createTime: null,
                type: null,
                typeName: null,
                objectId: null,
                objName: null,
                level: null,
                levelName: null,
                status: null,
                statusName: null,
                description: null,
                area: {
                    id: null,
                    name: ""
                },
                store: {
                    id: null,
                    name: ""
                },
                name: ""
            },
            tunnel: [],
            type: [],
            level: [],
            status: [],
            areas: [],
            stores: [],
            objs: [],
            isTrue: false,
            isDisable: false,
            validateRules: {
                tunnelId: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择所属管廊",
                        trigger: "change"
                    }
                ],
                "area.id": [
                    {
                        type: "number",
                        required: true,
                        message: "请选择所属区段",
                        trigger: "change"
                    }
                ],
                "store.id": [
                    {
                        type: "number",
                        required: true,
                        message: "请选择所属管仓",
                        trigger: "change"
                    }
                ],
                name: [
                    {
                        required: true,
                        message: "请输入缺陷名称",
                        trigger: "blur"
                    }
                ],
                createTime: [
                    {
                        type: "date",
                        required: true,
                        message: "请选择发现时间",
                        trigger: "change"
                    }
                ],
                type: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择缺陷类型",
                        trigger: "change"
                    }
                ],
                // objectId:[
                //     { type: 'number', required: true, message: '请选择对象名', trigger: 'change' }
                // ],
                level: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择危险等级",
                        trigger: "change"
                    }
                ],
                status: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择缺陷状态",
                        trigger: "change"
                    }
                ],
                description: [
                    {
                        required: true,
                        message: "请输入缺陷描述",
                        trigger: "blur"
                    }
                ]
            },
            choosedObjName: false
        };
    },
    computed: {
        tunnelId() {
            return this.defectDetails.tunnelId;
        },
        areaId() {
            return this.defectDetails.area.id;
        },
        storeId() {
            return this.defectDetails.store.id;
        }
    },
    watch: {
        tunnelId(curVal, oldVal) {
            // this.defectDetails.area.id = null;
            this.defectDetails.tunnelId = curVal;
            this.getAreas();
            this.getStores();
        },
        areaId(curVal, old) {
            // this.defectDetails.store.id = null
            this.defectDetails.area.id = curVal;
            this.getObj();
        },
        storeId(curVal, oldVal) {
            this.defectDetails.store.id = curVal;
            this.getObj();
        },
        $route: function() {
            this.defectDetails.tunnelId = Number(this.$route.params.tunnelId);
        }
    },
    mounted() {
        if (this.$route.params.type == 4) {
            this.defectDetails.tunnelId = Number(this.$route.params.tunnelId);
        }
        this.defectDetails.id = this.$route.params.id
            ? this.$route.params.id
            : sessionStorage["detailId"];
        this.pageType = this.$route.params.type
            ? this.$route.params.type
            : sessionStorage["pageType"];
        if (this.pageType == 1) {
            this.isTrue = true;
        }
        TunnelService.getTunnels().then(
            result => {
                this.tunnel = result;
            },
            error => {
                this.Log.info(error);
            }
        );
        //缺陷类型
        EnumsService.getDefectType().then(
            result => {
                this.type = result;
            },
            error => {
                this.Log.info(error);
            }
        );
        //缺陷状态
        EnumsService.getDefectStatus().then(
            result => {
                this.status = result;
            },
            error => {
                this.Log.info(error);
            }
        );
        //危险等级
        EnumsService.getDefectLevel().then(
            result => {
                this.level = result;
            },
            error => {
                this.Log.info(error);
            }
        );

        //获取缺陷详情
        DefectService.getDetailByDefectId(this.defectDetails.id).then(
            result => {
                this.defectDetails = result;
                this.defectDetails.store.id = result.store.id;
                this.defectDetails.createTime = new Date(
                    result.createTime
                ).format("yyyy-MM-dd hh:mm:s");
                this.getAreas();
                this.getStores();
            },
            error => {
                this.Log.info(error);
            }
        );
    },
    methods: {
        //获取所属区域
        getAreas() {
            TunnelService.getAreasByTunnelId(this.defectDetails.tunnelId).then(
                result => {
                    this.areas = result;
                },
                error => {
                    this.Log.info(error);
                }
            );
            this.getObj();
        },

        //获取所属仓段
        getStores() {
            TunnelService.getStoresByTunnelId(this.defectDetails.tunnelId).then(
                result => {
                    this.stores = result;
                },
                error => {
                    this.Log.info(error);
                }
            );
            this.getObj();
        },

        //获取对象名
        getObj() {
            this.objs = [];
            let mes = {
                areaId: this.defectDetails.area.id,
                storeId: this.defectDetails.store.id
            };
            DefectService.getObj(mes).then(
                result => {
                    this.objs = result;
                },
                error => {
                    this.Log.info(error);
                }
            );
            if (this.defectDetails.objectId == null) {
                this.choosedObjName = true;
            } else {
                this.choosedObjName = false;
            }
        },

        //提交缺陷
        submitDefect(name) {
            this.isDisable = true;
            setTimeout(() => {
                this.isDisable = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                        if (
                            this.defectDetails.type == 2 &&
                            this.defectDetails.objectId == null
                        ) {
                            this.choosedObjName = true;
                        } else {
                            this.choosedObjName = false;
                            DefectService.addDefect(this.defectDetails).then(
                                result => {
                                    this.$router.push(
                                        "/UM/defect/query/" +
                                            this.defectDetails.tunnelId
                                    );
                                },
                                error => {
                                    this.Log.info(error);
                                }
                            );
                        }
                    } else {
                        if (
                            this.defectDetails.type == 2 &&
                            this.defectDetails.objectId == null
                        ) {
                            this.choosedObjName = true;
                        } else {
                            this.choosedObjName = false;
                        }
                    }
                });
            }, 2000);
        },
        //返回
        goBack() {
            this.$router.back(-1);
        },
        changeTunnel() {
            this.defectDetails.area.id = null;
            this.defectDetails.store.id = null;
        }
    }
};
</script>
<style scoped>
.ivu-form.ivu-form-label-right {
    width: 740px;
    margin: 1vmin auto;
    padding: 1vmin 2vmin;
    margin-top: 3vmin;
    border-radius: 0.4vmin;
}
.goBack {
    position: absolute;
    bottom: 2vh;
    right: 3vw;
}
.formBG {
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
    background-size: 100% 100%;
    padding-top: 3vmin;
    padding-bottom: 3vmin;
}

.formBG >>> .ivu-form-item-label,
.formTitle {
    color: #fff;
}
.formBG >>> .ivu-form .ivu-form-item-required .ivu-form-item-label:before,
.formBG .ivu-form >>> .ivu-form-item-label:before {
    color: #00fff6;
    content: "★";
    display: inline-block;
    margin-right: 0.4vmin;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
}
.formTitle {
    width: auto;
    text-align: center;
    margin-left: 45%;
    margin-right: 45%;
    font-size: 2.2vmin;
    margin-top: -3.2vmin;
}
.ivu-form-item {
    margin-bottom: 2.4vmin;
}
@media (min-width: 2200px) {
    .ivu-form.ivu-form-label-right {
        width: 50%;
        padding: 1vmin 2vmin;
    }
    .ivu-form-item >>> .ivu-form-item-label {
        width: 15vmin !important;
        line-height: 2.5vmin;
    }
    .ivu-form-item >>> .ivu-form-item-content {
        margin-left: 15vmin !important;
        line-height: 4.5vmin;
    }
    .ivu-select,
    .ivu-select >>> .ivu-select-selection,
    .ivu-input-wrapper >>> .ivu-input,
    .ivu-date-picker >>> .ivu-input,
    .ivu-select.ivu-select-single >>> .ivu-select-selected-value,
    .ivu-select.ivu-select-single >>> .ivu-select-placeholder {
        height: 4vmin;
        line-height: 4vmin;
        font-size: 1.4vmin;
    }
}
</style>


