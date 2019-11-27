<template>
    <div class="formBG">
        <p
            class="formTitle"
        >{{pageType==pageTypes.Edit ? '更新相关单位信息' : (pageType == pageTypes.Read ? '相关单位详情' : '添加相关单位')}}</p>
        <Form
            ref="addRelatedUnitsInfo"
            :model="addRelatedUnitsInfo"
            :label-width="120"
            :rules="ruleValidate"
            @submit.native.prevent
        >
            <FormItem label="单位名称：" prop="name">
                <Input
                    type="text"
                    v-model="addRelatedUnitsInfo.name"
                    :readonly="pageType == pageTypes.Read"
                />
            </FormItem>
            <FormItem label="联系人：" prop="contact">
                <Input
                    type="text"
                    v-model="addRelatedUnitsInfo.contact"
                    :readonly="pageType == pageTypes.Read"
                />
            </FormItem>
            <FormItem label="联系方式：" prop="tel">
                <Input
                    type="text"
                    v-model="addRelatedUnitsInfo.tel"
                    placeholder="XXXXXXXXXXX或者000-0000000或者0000-0000000"
                    :readonly="pageType == pageTypes.Read"
                ></Input>
            </FormItem>
            <FormItem label="单位类别：" prop="unitType">
                <Select v-model="addRelatedUnitsInfo.unitType">
                    <Option
                        v-for="(item,index) in unitType"
                        :value="item.val"
                        :key="index"
                    >{{ item.key }}</Option>
                </Select>
            </FormItem>
            <FormItem label="单位地址：" prop="address">
                <Input
                    type="text"
                    v-model="addRelatedUnitsInfo.address"
                    :readonly="pageType == pageTypes.Read"
                />
            </FormItem>
            <FormItem label="坐标经度：" prop="longitude">
                <Input
                    type="text"
                    v-model="addRelatedUnitsInfo.longitude"
                    :readonly="pageType == pageTypes.Read"
                />
            </FormItem>
            <FormItem label="坐标纬度：" prop="latitude">
                <Input
                    type="text"
                    v-model="addRelatedUnitsInfo.latitude"
                    :readonly="pageType == pageTypes.Read"
                />
            </FormItem>
            <FormItem label="重要级别：" prop="unitLevel">
                <Select v-model="addRelatedUnitsInfo.unitLevel">
                    <Option v-for="item in levels" :value="item.val" :key="item.key">{{ item.key }}</Option>
                </Select>
            </FormItem>
            <FormItem label="管仓区段：" class="ivu-form-item-required">
                <Input
                    type="text"
                    icon="arrow-down-b"
                    v-model="sectionName"
                    @click="showTree"
                    readonly
                />
                <Tree
                    class="tree"
                    :data="treeList"
                    show-checkbox
                    ref="tree"
                    @on-check-change="choiceAll"
                    v-show="isShow"
                ></Tree>
                <div class="ivu-form-item-error-tip" v-show="validateSectionName==true">请选择所属仓段</div>
            </FormItem>
            <div style="text-align: center;margin-top: 8vmin;">
                <Button type="ghost" style="margin-right: 2vmin" @click="goBack()" class="back">返回</Button>
                <Button
                    type="primary"
                    @click="submit('addRelatedUnitsInfo')"
                    v-show="pageType==pageTypes.Add"
                    :disabled="isDisable"
                    class="save"
                >提交</Button>
                <Button
                    type="primary"
                    @click="update('addRelatedUnitsInfo')"
                    v-show="pageType==pageTypes.Edit"
                    :disabled="isDisable"
                    class="save"
                >更新</Button>
            </div>
        </Form>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnelService";
import { EnumsService } from "../../../../services/enumsService";
import { RelatedUnitService } from "../../../../services/relatedUnitService";
import types from "../../../../../static/Enum.json";
export default {
    data() {
        return {
            addRelatedUnitsInfo: {
                name: null,
                contact: null,
                longitude: null,
                latitude: null,
                unitLevel: null,
                tel: null,
                unitType: null,
                address: null,
                sectionIds: null
            },
            sections: [],
            unitType: [],
            treeList: [],
            isShow: false,
            sectionName: null,
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 3,
            pageTypes: types.pageType,
            isDisable: false,
            ruleValidate: {
                name: [
                    {
                        required: true,
                        message: "请填写单位名称",
                        trigger: "change"
                    }
                ],
                contact: [
                    { required: true, message: "请填写联系人", trigger: "blur" }
                ],
                tel: [
                    {
                        required: true,
                        message: "请填写联系方式",
                        trigger: "blur"
                    },
                    {
                        validator(rule, value, callback, source, options) {
                            var errors = [];
                            if (
                                !/^((0\d{2,3}-\d{7,8})|(1[358496]\d{9}))$/.test(
                                    value
                                )
                            ) {
                                // if(!(/^1(3|4|5|7|8)\d{9}$/).test(value)||!(/0\d{2,3}-\d{7,8}/).test(value)){
                                callback("请输入正确的联系方式");
                            }
                            callback(errors);
                        }
                    }
                ],
                unitType: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择单位类别",
                        trigger: "change"
                    }
                ],
                unitLevel: [
                    {
                        type: "number",
                        required: true,
                        message: "请选择重要级别",
                        trigger: "change"
                    }
                ],
                address: [
                    {
                        required: true,
                        message: "请填写单位地址",
                        trigger: "blur"
                    }
                ],
                latitude: [
                    {
                        required: true,
                        message: "请填写坐标纬度",
                        trigger: "blur"
                    }
                ],
                longitude: [
                    {
                        required: true,
                        message: "请填写坐标经度",
                        trigger: "blur"
                    }
                ]
            },
            validateSectionName: false,
            levels: [
                {
                    key: "低级",
                    val: 3
                },
                {
                    key: "中级",
                    val: 2
                },
                {
                    key: "高级",
                    val: 1
                }
            ]
        };
    },
    created() {
        let body = document.querySelector("body");
        body.addEventListener("click", e => {
            if (
                e.target.className == "ivu-checkbox-input" ||
                e.target.className ==
                    "ivu-icon ivu-icon-arrow-down-b ivu-input-icon ivu-input-icon-normal" ||
                e.target.className == "ivu-icon ivu-icon-arrow-right-b" ||
                e.target.className == "ivu-tree-children" ||
                e.target.className == "ivu-tree-arrow ivu-tree-arrow-open" ||
                e.target.className == "ivu-tree-title" ||
                e.target.className == "tree ivu-tree" ||
                e.target.className == "ivu-tree-arrow" 
                // ||
                // e.target.nodeName == "UL" ||
                // e.target.nodeName == "LI"
            ) {
                this.isShow = true;
            } else {
                this.isShow = false;
            }
        });
    },
    mounted() {
        if (this.$route.params.id || sessionStorage["detailId"]) {
            this.addRelatedUnitsInfo.id = this.$route.params.id;
            this.pageType = this.$route.params.type
                ? this.$route.params.type
                : sessionStorage["pageType"];
        }
        let _this = this;
        TunnelService.getTunnelsTree().then(
            function(result) {
                result.forEach(element => {
                    var firstChildren = [];
                    let temp = {};
                    temp.title = element.name;
                    temp.id = element.id;
                    if (element.list.length != 0) {
                        element.list.forEach(a => {
                            var secondChildren = [];
                            let obj = {};
                            obj.title = a.name;
                            obj.id = a.id;
                            obj.disabled = true;
                            if (a.list.length != 0) {
                                obj.disabled = false;
                                a.list.forEach(b => {
                                    let secondInfo = {};
                                    secondInfo.title = b.name;
                                    secondInfo.id = b.id;
                                    secondChildren.push(secondInfo);
                                    obj.children = secondChildren;
                                });
                            }
                            firstChildren.push(obj);
                            temp.children = firstChildren;
                        });
                    }
                    _this.treeList.push(temp);
                });
            },
            function(error) {
                console.log(error);
            }
        );
        EnumsService.getUnitType().then(
            function(result) {
                _this.unitType = result;
            },
            function(error) {
                console.log(error);
            }
        );
        RelatedUnitService.getRelatedUnitInfoById(_this.$route.params.id).then(
            function(result1) {
                _this.addRelatedUnitsInfo = result1;
                TunnelService.getSectionsByIds(
                    _this.addRelatedUnitsInfo.sectionIds
                ).then(
                    function(result2) {
                        _this.sections = result2;
                        var str = "";
                        for (var k = 0; k < _this.sections.length; k++) {
                            if (_this.sections.length == 1) {
                                _this.sectionName = _this.sections[k].name;
                            } else {
                                if (_this.sections[k] == null) {
                                    str = "";
                                } else {
                                    str += _this.sections[k].name + "，";
                                    _this.sectionName = str.substring(
                                        0,
                                        str.length - 1
                                    );
                                }
                            }
                        }
                    },
                    function(error2) {
                        console.log(error2);
                    }
                );
            },
            function(error1) {
                console.log(error1);
            }
        );
    },
    methods: {
        choiceAll() {
            let arr = this.$refs.tree.getCheckedNodes();
            var str = "";
            var strIds = "";
            this.sectionName = null;
            if (arr.length == 0) {
                this.addRelatedUnitsInfo.sectionIds = null;
                this.validateSectionName = true;
            } else {
                for (let index in arr) {
                    str += arr[index].title + ",";
                    this.sectionName = str.substring(0, str.length - 1);
                    strIds += arr[index].id + ",";
                    this.addRelatedUnitsInfo.sectionIds = strIds.substring(
                        0,
                        strIds.length - 1
                    );
                }
                this.validateSectionName = false;
            }
        },
        showTree() {
            this.isShow = true;
        },
        submit(name) {
            this.isDisable = true;
            setTimeout(() => {
                this.isDisable = false;
                this.choiceAll();
                this.$refs[name].validate(valid => {
                    if (valid && this.validateSectionName == false) {
                        RelatedUnitService.addRelatedUnit(this.addRelatedUnitsInfo).then(
                            result => {
                                this.$router.push("/UM/relatedUnits/query");
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }
                });
            }, 2000);
        },
        update(name) {
            this.isDisable = true;
            setTimeout(() => {
                this.isDisable = false;
                this.$refs[name].validate(valid => {
                    if (valid) {
                        RelatedUnitService.updateRelatedUnit(this.addRelatedUnitsInfo).then(
                            result => {
                                this.$router.push("/UM/relatedUnits/query");
                            },
                            error => {
                                this.Log.info(error)
                            }
                        )
                    }
                });
            }, 2000);
        },
        handleReset(name) {
            this.$refs[name].resetFields();
        },
        //返回
        goBack() {
            this.$router.back(-1);
        }
    }
};
</script>
<style scoped>
.ivu-form.ivu-form-label-right {
    width: 68vmin;
    margin: 0px auto;
    padding: 10px 20px;
}
.tree {
    background: rgba(50, 103, 156);
    z-index: 2;
    border: 1px solid #cccccc;
    height: 18vmin;
    overflow-y: auto;
}
.tree::-webkit-scrollbar {
    width: 1vmin;
    height: 0.2vmin;
}
.tree::-webkit-scrollbar-thumb {
    border-radius: 1vmin;
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    background: #83a6ed;
}
.tree::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
    border-radius: 1vmin;
    background: #ededed;
}
.tree >>> .ivu-tree-title,
.tree >>> .ivu-tree-arrow {
    color: #fff;
}
.tree >>> .ivu-tree-title:hover,
.tree >>> .ivu-tree-title-selected {
    background: rgba(255, 255, 255, 0.8);
    color: #333;
}
.formTitle {
    /* text-align: center;
    margin-bottom: 4vmin;
    margin-top: -1vmin; */
    font-size: 2.2vmin;
    color: #fff;
    margin-top: -3vh;
    text-align: center;
}
.save {
    background: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #61a2b3;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
    margin-right: 1vmin;
}
.back {
    background: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #61a2b3;
    border-radius: 1vmin;
    font-size: 1.4vmin !important;
    color: #fff;
}
.ivu-form-item >>> .ivu-form-item-label {
    width: 15vmin !important;
    line-height: 2vmin;
    font-size: 1.4vmin;
    color: #fff;
}
.ivu-form >>> .ivu-input,
.ivu-form >>> .ivu-select-selection {
    background: transparent;
    color: #fff;
    border-radius: 1vmin;
}

.ivu-form >>> .ivu-input::-webkit-input-placeholder,
.ivu-form >>> .ivu-select-selection .ivu-select-placeholder {
    color: #aaa;
}
.ivu-form >>> .ivu-input-icon {
    color: #fff;
}
.ivu-form-item >>> .ivu-form-item-content {
    margin-left: 15vmin !important;
    line-height: 3.2vmin;
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
.formBG {
    background: url("../../../../assets/UM/itemPageBg.png") no-repeat;
    background-size: 100% 100%;
    padding-top: 3vmin;
    padding-bottom: 3vmin;
    height: 81vmin;
}

.formBG >>> .ivu-form-item-label {
    color: #fff;
}
.formBG >>> .ivu-form .ivu-form-item-required .ivu-form-item-label:before,
.formBG .ivu-form >>> .ivu-form-item-label:before {
    color: #00fff6;
    content: "★";
    display: inline-block;
    margin-right: 4px;
    line-height: 1;
    font-family: SimSun;
    font-size: 1.2vmin;
}
@media (min-width: 2200px) {
    .ivu-input-number,
    .ivu-input-number >>> .ivu-input-number-input {
        height: 3.2vmin;
        line-height: 3.2vmin;
        font-size: 1.4vmin;
    }
    input[type="number"] {
        height: 4vmin;
        width: 8vmin;
        font-size: 1.6vmin;
    }
    .tree >>> .ivu-tree-title {
        font-size: 1.4vmin;
    }
    .tree.ivu-tree >>> .ivu-checkbox-inner {
        width: 1.4vmin;
        height: 1.4vmin;
        border: 0.1vmin solid #dddee1;
    }
    .tree >>> .ivu-tree-arrow {
        width: 1.2vmin;
    }
    .tree.ivu-tree >>> .ivu-checkbox-inner:after {
        width: 0.6vmin;
        height: 0.9vmin;
        top: 0.1vmin;
        left: 0.4vmin;
    }
}
</style>
