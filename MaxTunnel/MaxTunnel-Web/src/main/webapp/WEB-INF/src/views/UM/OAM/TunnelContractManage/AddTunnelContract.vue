<template>
    <div :style="backStyle">
        <Form ref="contractInfo" :model="contractInfo" :rules="ruleValidate" class="form">
            <p class="title">{{title}}</p>
            <Row>
                <Col span="12">
                    <FormItem label="合同名称：" prop="name">
                        <Input
                            v-model="contractInfo.name"
                            placeholder="请输入合同名称"
                            :readonly="read"
                            class="inputWidth"
                        />
                    </FormItem>
                    <FormItem label="合同开始日期：" prop="contractStartTime">
                        <DatePicker
                            type="datetime"
                            placeholder="请选择合同开始日期"
                            v-model="contractInfo.contractStartTime"
                            class="inputWidth"
                            :readonly="read"
                        ></DatePicker>
                    </FormItem>
                    <FormItem label="合同结束日期：" :prop="read ? null : 'contractEndTime'">
                        <DatePicker
                            type="datetime"
                            placeholder="请选择合同结束日期"
                            v-model="contractInfo.contractEndTime"
                            class="inputWidth"
                            :readonly="read"
                        ></DatePicker>
                    </FormItem>
                    <FormItem label="付款方式：" prop="payType">
                        <Select v-model="contractInfo.payType" :disabled="read" class="inputWidth">
                            <Option
                                v-for="(item,index) in payTypes"
                                :value="item.val.toString()"
                                :key="index"
                            >{{ item.key }}</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="合同状态：" v-if="pageType==pageTypes.Edit || read">
                        <Select
                            v-model="contractInfo.contractStatus"
                            :disabled="read"
                            class="inputWidth"
                        >
                            <Option
                                v-for="(item,index) in contractStatus"
                                :value="item.val.toString()"
                                :key="index"
                            >{{ item.key }}</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="管线名称：" prop="cableName">
                        <Input
                            v-model="contractInfo.cableName"
                            placeholder="请输入管线名称"
                            :readonly="read"
                            class="inputWidth"
                        />
                    </FormItem>
                    <FormItem label="管线长度：" prop="cableLength">
                        <Input
                            v-model="contractInfo.cableLength"
                            placeholder="请输入管线长度"
                            :readonly="read"
                            class="inputWidth"
                        />
                    </FormItem>
                    <FormItem label="管线状态：" v-if="pageType==pageTypes.Edit || read">
                        <Select
                            v-model="contractInfo.cableStatus"
                            :disabled="read"
                            class="inputWidth"
                        >
                            <Option
                                v-for="(item,index) in cableStatus"
                                :value="item.val.toString()"
                                :key="index"
                            >{{ item.key }}</Option>
                        </Select>
                    </FormItem>
                    <FormItem label="录入人：">
                        <Input v-model="editor.name" class="inputWidth" readonly/>
                    </FormItem>
                    <FormItem label="企业：" :prop="read ? null : 'companyId'">
                        <Poptip placement="top">
                            <Input
                                v-model="customerName"
                                placeholder="请选择企业"
                                class="inputWidth"
                                readonly
                            />
                            <div class="pop" slot="content" v-show="pageType != pageTypes.Read">
                                <customer-choose
                                    @selectCustomer="getcompanyId"
                                    v-bind:companyId="contractInfo.companyId"
                                ></customer-choose>
                            </div>
                        </Poptip>
                    </FormItem>
                    <FormItem label="管线位置：">
                        <Row style="width: 45vmin;margin-left: 14vmin">
                            <Col span="12">
                                <Select
                                    v-model="contractInfo.tunnelId"
                                    :disabled="read"
                                    @on-change="tunnelChange"
                                >
                                    <!--   <Option disabled value="0">管廊</Option> -->
                                    <Option
                                        v-for="item in cableLocation.tunnels"
                                        :value="item.id"
                                        :key="item.id"
                                    >{{ item.name }}</Option>
                                </Select>
                                <div
                                    class="ivu-form-item-error-tip location"
                                    v-if="checkCable.tunnel"
                                >请选择管廊</div>
                            </Col>
                            <Col span="12">
                                <Select
                                    v-model="contractInfo.storeId"
                                    @on-change="check('store')"
                                    :disabled="read"
                                >
                                    <Option
                                        v-for="(item,index) in cableLocation.stores"
                                        :value="item.id"
                                        :key="index"
                                    >{{ item.name }}</Option>
                                </Select>
                                <div
                                    class="ivu-form-item-error-tip location"
                                    v-if="checkCable.store"
                                >请选择仓</div>
                            </Col>
                            <!-- <Col span="10">
					<Select
					multiple
					v-model="contractInfo.areaIds"
					@on-change="check('area')"
					v-if="!read"
					>
					<Option
						v-for="(item,index) in cableLocation.areas"
						:value="item.id"
						:key="index"
					>{{ item.name }}</Option>
					</Select>
					<p
						v-if="read && contractInfo.areaIds.indexOf(item.id) != -1"
						v-for="(item,index) in cableLocation.areas"
						:key="index"
					>{{ item.name }}</p>
					<div class="ivu-form-item-error-tip location" v-if="checkCable.area">请选择区域</div>
                            </Col>-->
                        </Row>
                    </FormItem>
                </Col>
                <Col span="12">
                    <FormItem label="合同文件:" prop="file" v-show="!read">
                        <Input
                            placeholder="暂时只支持pdf格式"
                            type="file"
                            @on-change="fileChange"
                            class="inputWidth"
                            accept="application/pdf"
                        />
                        <div class="ivu-form-item-error-tip" v-show="file.typeFlag">请选择pdf文件</div>
                    </FormItem>
                    <FormItem>
                        <pdf
                            :page="file.curPage"
                            :class="['pdfContent',{'pdfOverflow': file.src}]"
                            @num-pages="file.totalPage=$event"
                            @page-loaded="file.curPage=$event"
                            @loaded="loadPdfHandler"
                            :src="file.src"
                        ></pdf>
                        <div style="margin-left: 20vmin;">
                            <Button
                                type="primary"
                                @click="changePdfPage(0)"
                                size="small"
                                v-show="file.src && file.curPage > 1"
                            >上一页</Button>
                            <Button
                                type="primary"
                                @click="changePdfPage(1)"
                                size="small"
                                style="float:right;margin: 0.4vmin 5vmin"
                                v-show="file.src && file.curPage < file.totalPage"
                            >下一页</Button>
                        </div>
                    </FormItem>
                </Col>
            </Row>
            <div style="text-align: center">
                <Button
                    type="primary"
                    @click="submitcontractInfo('contractInfo')"
                    v-if="pageType!=pageTypes.Edit && pageType != pageTypes.Read"
                >提交</Button>
                <Button
                    type="primary"
                    @click="submitEditContractInfo('contractInfo')"
                    v-if="pageType==pageTypes.Edit"
                >更新</Button>
                <Button
                    type="ghost"
                    @click="handleReset('contractInfo')"
                    style="margin-left: 8px"
                >返回</Button>
            </div>
        </Form>
        <Button
            type="primary"
            @click="prev"
            v-if="pageType!=pageTypes.Add && curIndex > 0"
            class="prev"
        >上一页</Button>
        <Button
            type="primary"
            @click="next"
            v-if="pageType!=pageTypes.Add && curIndex < contractIds.length"
            class="next"
        >下一页</Button>
    </div>
</template>
<script>
import { EnumsService } from "../../../../services/enumsService";
import { TunnelService } from "../../../../services/tunnelService";
import { ContractService } from "../../../../services/contractService";
import { PatrolService } from "../../../../services/patrolService";
import types from "../../../../../static/Enum.json";
import CustomerChoose from "../../../../components/UM/OAM/CustomerChoose";
import pdf from "vue-pdf";
export default {
    data() {
        return {
            backStyle: {
                backgroundImage:
                    "url(" + require("../../../../assets/UM/backImg.jpg") + ")",
                position: "relative",
                paddingTop: "20px",
                paddingBottom: "20px",
                height: "100%",
                backgroundRepeat: "no-repeat",
                backgroundSize: "100% 100%",
                position: "relative"
            },
            pageType: 3,
            pageTypes: types.pageType,
            contractInfo: {
                name: null,
                contractStartTime: new Date(),
                contractEndTime: new Date(
                    new Date().getTime() + 1000 * 60 * 60 * 24 * 365
                ).format("yyyy-MM-dd hh:mm:ss"),
                payType: "1",
                companyId: 0,
                cableName: null,
                cableLength: null,
                areaIds: [],
                storeId: null,
                id: null,
                cableId: null,
                contractStatus: null,
                cableStatus: null,
                tunnelId: null,
                operateUsername: null,
                path: null
            },
            payTypes: [],
            customers: [],
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "请填写合同名称",
                        trigger: "blur"
                    }
                ],
                contractStartTime: [
                    {
                        required: true,
                        type: "date",
                        message: "请选择合同开始日期",
                        trigger: "change"
                    }
                ],
                contractEndTime: [
                    {
                        required: true,
                        type: "date",
                        message: "请选择合同结束日期",
                        trigger: "change"
                    }
                ],
                payType: [
                    {
                        required: true,
                        message: "请选择付款方式",
                        trigger: "change"
                    }
                ],
                companyId: [{ required: true, message: "请选择企业" }],
                cableName: [
                    {
                        required: true,
                        message: "请填写管线名称",
                        trigger: "blur"
                    }
                ],
                cableLength: [
                    {
                        required: true,
                        message: "请填写管线长度",
                        trigger: "blur"
                    }
                ],
                path: [
                    {
                        required: true,
                        message: "请选择合同文件",
                        trigger: "blur"
                    }
                ]
            },
            customerName: "",
            sectionNames: "",
            tunnels: [],
            areas: [],
            contractStatus: [],
            cableLocation: {
                tunnels: [],
                areas: [],
                stores: []
            },
            cableStatus: [],
            checkCable: {
                tunnel: false,
                area: false,
                store: false
            },
            read: false,
            curIndex: null,
            contractIds: [],
            editor: null,
            file: {
                editInit: false,
                uploadParam: null,
                src: null,
                typeFlag: false,
                curPage: 1,
                totalPage: 1
            },
            title: null
        };
    },
    components: { CustomerChoose, pdf },
    mounted() {
        if (this.$route.params.contractId) {
            this.contractInfo.id = this.$route.params.contractId;
        } else {
            if (this.$route.params.curIndex != null) {
                this.curIndex = this.$route.params.curIndex;
                this.contractIds = this.$route.params.contractIds;
                this.contractInfo.id = this.contractIds[this.curIndex];
            }
        }

        this.pageType = this.$route.params.type;
        switch (this.pageType) {
            case this.pageTypes.Read:
                this.title = "合同信息详情";
                this.read = true;
                break;
            case this.pageTypes.Edit:
                this.title = "修改合同信息";
                this.read = false;
                break;
            default:
                this.title = "添加合同信息";
                this.read = false;
                break;
        }

        this.getInitData();
        this.getParams();
    },
    watch: {
        $route: function() {
            this.pageType = 3;
            this.contractInfo = {
                name: null,
                contractStartTime: new Date(),
                contractEndTime: new Date(
                    new Date().getTime() + 1000 * 60 * 60 * 24 * 365
                ).format("yyyy-MM-dd hh:mm:ss"),
                payType: "1",
                companyId: 0,
                cableName: null,
                cableLength: null,
                areaIds: [],
                storeId: null,
                tunnelId: null
            };
            this.customerName = " ";
            this.read = false;
        }
    },
    methods: {
        getInitData() {
            let _this = this;
            EnumsService.getPayType().then(
                result => {
                    _this.payTypes = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            TunnelService.getTunnels().then(
                result => {
                    _this.cableLocation.tunnels = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            EnumsService.getContractStatus().then(
                result => {
                    _this.contractStatus = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            EnumsService.getCableStatus().then(
                result => {
                    _this.cableStatus = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            PatrolService.getApprover().then(
                res => {
                    _this.editor = res[0];
                    _this.contractInfo.operateUsername = res[0].name;
                },
                err => {
                    _this.Log.info(err);
                }
            );
        },
        submitcontractInfo(name) {
            this.$refs[name].validate(valid => {
                let check =
                    this.contractInfo.companyId &&
                    this.contractInfo.tunnelId &&
                    // this.contractInfo.areaIds &&
                    this.contractInfo.storeId;
                if (valid && check) {
                    if (
                        new Date(this.contractInfo.contractStartTime) >
                        new Date(this.contractInfo.contractEndTime)
                    ) {
                        this.$Message.error("开始时间必须小于结束时间！");
                        return;
                    } else {
                        let _this = this;
                        let config = {
                            headers: { "Content-Type": "multipart/form-data" }
                        }; //添加请求头
                        ContractService.uploadFile(
                            this.file.uploadParam,
                            config
                        ).then(
                            res => {
                                _this.contractInfo.path = res;
                                ContractService.addContract(
                                    _this.contractInfo
                                ).then(
                                    result => {
                                        _this.$Message.success("添加成功");
                                        _this.$nextTick(() => {
                                            _this.$router.push(
                                                "/UM/tunnelContract/list"
                                            );
                                        });
                                    },
                                    error => {
                                        _this.Log.info(error);
                                    }
                                );
                            },
                            error => {
                                _this.$Message.error("添加失败");
                            }
                        );
                    }
                } else {
                    this.$Message.error("请正确填写客户信息");
                }
            });
        },
        getParams() {
            if (
                this.pageType == this.pageTypes.Edit ||
                this.pageType == this.pageTypes.Read
            ) {
                let _this = this;
                let id = _this.contractInfo.id;
                let url =
                    "/" +
                    _this.ApiUrl.split("/")[3] +
                    "/contracts/" +
                    id +
                    "/view";
                this.file.src = pdf.createLoadingTask({ url: url });
                ContractService.getDetailsByContractId(id).then(
                    result => {
                        _this.file.editInit = true;
                        _this.contractInfo = {
                            id,
                            name: result.cableDto.contract.name,
                            contractStartTime: new Date(
                                result.cableDto.contract.contractStartTime
                            ),
                            contractEndTime: new Date(
                                result.cableDto.contract.contractEndTime
                            ),
                            payType: result.cableDto.contract.payType.toString(),
                            companyId: result.cableDto.contract.company.id,
                            cableName: result.cableDto.cableName,
                            cableLength: result.cableDto.cableLength.toString(),
                            operateUsername:
                                result.cableDto.contract.operateUsername,
                            path: result.cableDto.contract.path,
                            cableStatus: result.cableDto.cableStatus.toString(),
                            contractStatus: result.cableDto.contract.contractStatus.toString(),
                            cableId: result.cableDto.id,
                            tunnelId: result.tunnelId
                        };

                        _this.customerName = result.cableDto.contract.company.name.toString();
                        _this.getStoreAndAreas();
                        // _this.contractInfo.areaIds = result.areaIds;
                        _this.contractInfo.storeId = result.storeId;
                    },
                    error => {
                        _this.Log.info(error);
                    }
                );
            }
        },
        submitEditContractInfo(name) {
            this.$refs[name].validate(valid => {
                let check =
                    this.contractInfo.companyId &&
                    this.contractInfo.tunnelId &&
                    // this.contractInfo.areaIds &&
                    this.contractInfo.storeId;
                if (valid && check) {
                    if (
                        new Date(this.contractInfo.contractStartTime) >
                        new Date(this.contractInfo.contractEndTime)
                    ) {
                        this.$Message.error("开始时间必须小于结束时间！");
                        return;
                    } else {
                        let _this = this;
                        if (this.file.editInit) {
                            ContractService.updateContract(
                                _this.contractInfo
                            ).then(
                                result => {
                                    _this.$Message.success("修改成功");
                                    _this.$nextTick(() => {
                                        _this.$router.push(
                                            "/UM/tunnelContract/list"
                                        );
                                    });
                                },
                                error => {
                                    _this.Log.info(error);
                                }
                            );
                        } else {
                            let config = {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }; //添加请求头
                            ContractService.uploadFile(
                                this.file.uploadParam,
                                config
                            ).then(
                                res => {
                                    _this.contractInfo.path = res;
                                    ContractService.updateContract(
                                        _this.contractInfo
                                    ).then(
                                        result => {
                                            _this.$Message.success("修改成功");
                                            _this.$nextTick(() => {
                                                _this.$router.push(
                                                    "/UM/tunnelContract/list"
                                                );
                                            });
                                        },
                                        error => {
                                            _this.Log.info(error);
                                        }
                                    );
                                },
                                error => {
                                    _this.$Message.error("修改失败");
                                }
                            );
                        }
                    }
                } else {
                    this.$Message.error("请正确填写客户信息");
                }
            });
        },
        handleReset(name) {
            // this.$router.push("/UM/tunnelContract/list");
            this.$router.back(-1);
        },
        getcompanyId(data) {
            this.contractInfo.companyId = data.id;
            this.customerName = data.name.toString();
        },
        check(type) {
            if (this.contractInfo.tunnelId == undefined) {
                this.checkCable.store = false;
                this.checkCable.area = false;
            } else {
                if (type == "store") {
                    if (!this.contractInfo.storeId) {
                        this.checkCable.store = true;
                    } else {
                        this.checkCable.store = false;
                    }
                } else if (type == "area") {
                    if (!this.contractInfo.storeId) {
                        this.checkCable.store = true;
                    } else {
                        this.checkCable.store = false;
                    }
                    // if (!this.contractInfo.areaIds[0]) {
                    // 	this.checkCable.area = true;
                    // } else {
                    // 	this.checkCable.area = false;
                    // }
                }
            }
        },
        tunnelChange() {
            if (!this.contractInfo.tunnelId) {
                this.checkCable.tunnel = false;
            } else {
                // this.contractInfo.storeId = null
                // this.contractInfo.areaIds = null
                this.getStoreAndAreas();
            }
        },
        getStoreAndAreas() {
            let _this = this;
            TunnelService.getStoresByTunnelId(_this.contractInfo.tunnelId).then(
                result => {
                    _this.cableLocation.stores = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
            TunnelService.getAreasByTunnelId(_this.contractInfo.tunnelId).then(
                result => {
                    _this.cableLocation.areas = result;
                },
                error => {
                    _this.Log.info(error);
                }
            );
        },
        prev() {
            this.curIndex = this.curIndex - 1;
            this.contractInfo.id = this.contractIds[this.curIndex];
            this.getParams();
        },
        next() {
            this.curIndex = this.curIndex + 1;
            this.contractInfo.id = this.contractIds[this.curIndex];
            this.getParams();
        },
        fileChange(e) {
            this.file.editInit = false;
            let file = e.target.files[0];
            let param = new FormData(); //创建form对象
            param.append("file", file, file.name); //通过append向form对象添加数据
            param.append("chunk", this.companyInfo); //添加form表单中其他数据
            this.file.uploadParam = param;
            this.file.src = window.URL.createObjectURL(e.srcElement.files[0]);
            let fileName = e.target.files[0].name.split(".");
            if (fileName[fileName.length - 1] !== "pdf") {
                this.file.typeFlag = true;
            } else {
                this.file.typeFlag = false;
            }
        },
        changePdfPage(val) {
            if (val === 0 && this.file.curPage > 1) {
                this.file.curPage--;
            }
            if (val === 1 && this.file.curPage < this.file.totalPage) {
                this.file.curPage++;
            }
        },
        loadPdfHandler(e) {
            this.file.curPage = 1;
        }
    }
};
</script>
<style scoped>
.ivu-form.ivu-form-label-right {
    width: 74%;
    margin: 2vh auto;
    background: #fff;
    padding: 1vmin 2vmin;
}
h2 {
    text-align: center;
    margin-bottom: 20px;
}
.pop {
    max-height: 300px;
    overflow-y: auto;
}
.form >>> .ivu-poptip-popper {
    width: 100vmin;
}
.form >>> .ivu-form-item-label {
    width: 14vmin;
}
.prev {
    position: absolute;
    bottom: 6vh;
    left: 2vw;
}
.next {
    position: absolute;
    bottom: 6vh;
    right: 2vw;
}
.title {
    font-size: 2.5vmin;
    text-align: center;
}
.inputWidth {
    width: 45vmin;
}
.pdfContent {
    width: 44vmin;
    height: 48vmin;
    margin-left: 14vmin;
}
.pdfOverflow {
    overflow-y: auto;
    overflow-x: hidden;
}
.form >>> .ivu-form-item {
    margin-bottom: 2.4vmin;
}
@media (min-width: 2000px) {
    .form >>> .ivu-form-item-content {
        line-height: 2.6vmin;
    }
}
/*select*/
.form >>> .ivu-select-selection {
    height: 3.2vmin !important;
}
.form >>> .ivu-select-multiple .ivu-select-selection {
    height: 3.2vmin !important;
}
.form >>> .ivu-select-selected-value {
    font-size: 1.26vmin !important;
    padding-top: 0.2vmin !important;
    width: 90% !important;
}
.form >>> .ivu-select-placeholder {
    font-size: 1.26vmin !important;
    padding-top: 0.2vmin !important;
    width: 90% !important;
}
.form >>> .ivu-select-multiple .ivu-select-placeholder {
    line-height: 3.2vmin !important;
    height: 3.2vmin !important;
}
.form >>> .ivu-tag {
    height: 2.4vmin;
    line-height: 2.2vmin;
    font-size: 1.2vmin;
    margin: 0.3vmin 0.4vmin 0.3vmin 0;
}
.form >>> .ivu-form-item-error-tip {
    left: 14vmin;
}
.form >>> .location {
    left: 0;
}

/*input*/
.form >>> .ivu-input {
    font-size: 1.4vmin;
    height: 3.2vmin;
}

/*datepicker*/
.form >>> .ivu-date-picker-header {
    height: 3.2vmin;
    line-height: 3.2vmin;
}

.form >>> .ivu-picker-panel-icon-btn {
    font-size: 1.66vmin;
    width: 1.28vmin;
    height: 2.5vmin;
}

.form >>> .ivu-date-picker-header-label {
    font-size: 1.66vmin;
}

@media (min-width: 1921px) {
    .form >>> .ivu-date-picker-cells {
        width: 15vmin;
        font-size: 1.66vmin;
    }

    .form >>> .ivu-date-picker-cells-cell {
        width: 2vmin;
    }
    .form >>> .ivu-date-picker-cells-header span {
        padding-right: 2.5rem;
    }
}
</style>
