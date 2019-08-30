<template>
    <div>
        <Modal v-model="show.state" :title="title" width="600">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="140">
                <FormItem label="监测对象名称：" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入监测对象名称" class="InputWidth"/>
                </FormItem>
                <FormItem label="监测对象ID：" prop="id">
                    <Input
                        v-model="formValidate.id"
                        placeholder="请输入监测对象ID"
                        :disabled="type==2"
                        class="InputWidth"
                    />
                </FormItem>
                <FormItem label="监测对象类型：" prop="objtypeId">
                    <Select
                        v-model="formValidate.objtypeId"
                        placeholder="请选择监测对象类型"
                        class="InputWidth"
                    >
                        <Option
                            v-for="item in objtypes"
                            :value="item.val + ''"
                            :key="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="关联预案：">
                    <Select
                        v-model="relaPrePlan"
                        multiple
                        placeholder="请选择关联预案"
                        class="InputWidth"
                        @on-change="getTypeOf()"
                    >
                        <Option
                            v-for="item in planIds"
                            :value="item.val"
                            :key="item.val"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="管廊位置：" class="location">
                    <Row class="InputWidth">
                        <Col span="8">
                            <Select
                                v-model="formValidate.tunnelId"
                                placeholder="请选择管廊"
                                @on-change="clearStoreAndArea"
                            >
                                <Option
                                    v-for="item in tunnels"
                                    :value="item.id"
                                    :key="item.id"
                                >{{item.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="8">
                            <Select
                                v-model="formValidate.storeId"
                                placeholder="请选择管仓"
                                @on-change="getVideos"
                            >
                                <Option
                                    v-for="item in stores"
                                    :value="item.id"
                                    :key="item.id"
                                >{{item.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="8">
                            <Select
                                v-model="formValidate.areaId"
                                placeholder="请选择区域"
                                @on-change="getVideos"
                            >
                                <Option
                                    v-for="item in areas"
                                    :value="item.id"
                                    :key="item.id"
                                >{{item.name}}</Option>
                            </Select>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="关联视频：">
                    <Input
                        @on-focus="changeIsShow"
                        class="InputWidth"
                        :disabled="readonlyVideo"
                        v-model="formValidate.videoIds"
                    ></Input>
                </FormItem>
                <FormItem label="是否激活：">
                    <Select v-model="formValidate.actived" placeholder="请选择是否激活" class="InputWidth">
                        <Option
                            v-for="item in isActived"
                            :value="item.val"
                            :key="item.key"
                        >{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="描述：">
                    <Input
                        type="textarea"
                        v-model="formValidate.description"
                        placeholder="请输入描述"
                        class="InputWidth"
                    />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button
                    type="primary"
                    size="large"
                    v-show="type==1"
                    v-on:click="sendMsg('formValidate')"
                >保存</Button>
                <Button
                    type="primary"
                    size="large"
                    v-show="type==2"
                    v-on:click="updateMeasobj('formValidate')"
                >更新</Button>
            </div>
        </Modal>
        <showVideo :videoInfo="videoInfo" v-on:childByValue="childByValue"></showVideo>
    </div>
</template>

<script>
import { EnumsService } from "../../../services/enumsService";
import { TunnelService } from "../../../services/tunnelService";
import { MeasObjServer } from "../../../services/MeasObjectSerivers";
import showVideo from "./ShowVideosModal";
export default {
    name: "meas-obj-module",
    components: {
        showVideo
    },
    data() {
        const validateInt = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("不能为空"));
            } else {
                if (isNaN(value) || value % 1 !== 0) {
                    callback(new Error("请输入整数"));
                } else {
                    MeasObjServer.checkObjId(value).then(result => {
                        if (!result) {
                            callback(new Error("ID重复，请输入其他ID"));
                        } else {
                            callback();
                        }
                    });
                }
            }
        };
        const checkString = (rule, value, callback) => {
            if (value === "") {
                callback(new Error("不能为空"));
            } else {
                let regEn = /[`!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;
                let regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
                if (regEn.test(value) || regCn.test(value)) {
                    callback(new Error("输入了非法字符，请重新输入"));
                } else {
                    callback();
                }
            }
        };

        return {
            formValidate: {
                name: null,
                id: null,
                objtypeId: null,
                description: null,
                actived: null,
                tunnelId: null,
                storeId: null,
                areaId: null,
                planIds: null,
                videoIds: null
            },
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "监测对象名称不能为空",
                        trigger: "blur"
                    },
                    { validator: checkString, trigger: "blur" }
                ],
                id: [
                    {
                        required: true,
                        message: "监测对象ID不能为空",
                        trigger: "blur"
                    },
                    { validator: validateInt, trigger: "blur" }
                ],
                objtypeId: [
                    {
                        required: true,
                        message: "监测对象类型不能为空",
                        trigger: "change"
                    }
                ]
            },
            objtypes: [],
            tunnels: [],
            areas: [],
            stores: [],
            isActived: [{ val: 0, key: "否" }, { val: 1, key: "是" }],
            title: "新增监测对象",
            planIds: [],
            videos: [],
            videoInfo: {
                isShowVideo: false,
                videoData: []
            },
            readonlyVideo: false,
            relaPrePlan: null
        };
    },
    props: {
        show: {
            state: {
                default: false
            }
        },
        type: null,
        id: null
    },
    watch: {
        "show.state": function(newValue, oldValue) {
            if (this.type == 1) {
                this.cleanFormValidate();
                this.relaPrePlan = [];
                this.title = "新增监测对象";
            } else {
                this.cleanFormValidate();
                this.title = "编辑监测对象";
                this.getMeasObjInfo();
            }
        },
        "formValidate.tunnelId": function(newVal) {
            if (newVal != null || newVal != undefined) {
                this.getArea(newVal);
                this.getStore(newVal);
            }
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            let _this = this;
            EnumsService.getObjType().then(
                result => {
                    _this.objtypes = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            TunnelService.getTunnels().then(
                result => {
                    _this.tunnels = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            EnumsService.getPlanType().then(
                result => {
                    _this.planIds = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        //add
        sendMsg: function(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    MeasObjServer.addMeasObj(this.formValidate).then(
                        result => {
                            this.$emit("addMeasObj", result);
                        },
                        error => {
                            this.Log.info(error);
                        }
                    );
                } else {
                    this.$Message.error("添加失败，请稍后重试");
                }
            });
        },
        //edit
        updateMeasobj: function(name) {
            this.$refs[name].validate(valid => {
                if (valid) {
                    MeasObjServer.updateMeasObj(this.formValidate).then(
                        result => {
                            this.$emit("ListenUpdateMeasObj", result);
                        },
                        error => {
                            this.Log.info(error);
                        }
                    );
                } else {
                    this.$Message.error("更新失败，请重新操作");
                }
            });
        },
        getArea(tunnelId) {
            TunnelService.getStoresByTunnelId(tunnelId).then(
                result => {
                    this.stores = result;
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        getStore(tunnelId) {
            TunnelService.getAreasByTunnelId(tunnelId).then(
                result => {
                    this.areas = result;
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        clearStoreAndArea() {
            if (this.type == "edit") {
                this.formValidate.areaId = null;
                this.formValidate.storeId = null;
            }
        },
        cleanFormValidate() {
            for (let item in this.formValidate) {
                this.formValidate[item] = null;
            }
        },
        changeIsShow() {
            this.videoInfo.isShowVideo = true;
        },
        //获取视频
        getVideos() {
            if (
                this.formValidate.storeId != null &&
                this.formValidate.areaId != null
            ) {
                MeasObjServer.getSectionVideos(
                    this.formValidate.storeId,
                    this.formValidate.areaId
                ).then(
                    result => {
                        if (result.length != 0) {
                            this.videoInfo.videoData = result;
                            this.readonlyVideo = false;
                        } else {
                            this.readonlyVideo = true;
                        }
                    },
                    error => {
                        this.Log.info(error);
                    }
                );
            }
        },
        childByValue(childValue) {
            this.videoInfo.isShowVideo = false;
            this.formValidate.videoIds = childValue.selectdVideo.toString();
        },
        getTypeOf() {
            this.formValidate.planIds = this.relaPrePlan.toString();
        },
        getMeasObjInfo(id) {
            MeasObjServer.getObjById(id).then(
                result => {
                    this.formValidate = result;
                    this.formValidate.objtypeId =
                        this.formValidate.objtypeId + "";
                    if (result.actived == true) {
                        this.formValidate.actived = 1;
                    } else {
                        this.formValidate.actived = 0;
                    }
                    if (this.formValidate.planIds != null) {
                        this.relaPrePlan = this.formValidate.planIds.split(",");
                        this.formValidate.planIds.split(",").forEach(item => {
                            this.relaPrePlan.push(Number(item));
                        });
                    } else {
                        this.relaPrePlan = [];
                    }
                },
                error => {
                    this.Log.info(error);
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
.location >>> .ivu-form-item-label:before {
    content: "*";
    display: inline-block;
    margin-right: 4px;
    line-height: 1;
    font-family: SimSun;
    font-size: 12px;
    color: #ed3f14;
}
</style>
