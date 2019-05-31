<template>
    <div class="allDiv">
        <Row class="queryCondition">
            <Col span="6">
                <span class="conditionTitle">所属管仓段：</span>
                <Input
                    type="text"
                    icon="arrow-down-b"
                    style="width: 60%"
                    :value="sectionName"
                    @click="showTree"
                    readonly
                ></Input>
                <Tree
                    class="tree"
                    :data="treeList"
                    show-checkbox
                    ref="tree"
                    @on-check-change="choiceAll"
                    v-show="isShow"
                ></Tree>
            </Col>
            <Col span="6">
                <span class="conditionTitle">单位名称：</span>
                <Input type="text" v-model="conditions.name" id="getW" style="width:60%"></Input>
            </Col>
            <Col span="6">
                <span class="conditionTitle">联系人：</span>
                <Input type="text" v-model="conditions.contact" style="width:60%"></Input>
            </Col>
            <Col span="6">
                <span class="conditionTitle">单位类别：</span>
                <Select v-model="conditions.unitType" style="width:60%">
                    <Option value="null">所有</Option>
                    <Option
                        v-for="(item,index) in unitType"
                        :value="item.val"
                        :key="index"
                    >{{ item.key }}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span class="word025 conditionTitle">开始时间：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择开始时间"
                    style="width: 60%"
                    v-model="conditions.startTime"
                ></DatePicker>
            </Col>
            <Col span="6">
                <span class="conditionTitle">结束时间：</span>
                <DatePicker
                    type="datetime"
                    placeholder="请选择结束时间"
                    style="width: 60%"
                    v-model="conditions.endTime"
                ></DatePicker>
            </Col>
            <Col span="6">
                <Button type="primary" icon="ios-search" @click="queryUnits()">查询</Button>
            </Col>
        </Row>
        <div class="list">
            <Row type="flex" align="top" class="code-row-bg">
                <Col span="6" v-for="(item,index) in unitInfo" :key="index">
                    <div class="unitBox">
                        <div class="title">
                            <Icon type="star" style="font-size: 2vmin;"></Icon>
                            {{ item.name + ' / ' + item.unitTypeName }}
                        </div>
                        <div class="unitItem">
                            <Icon type="ios-location"></Icon>
                            {{item.address}}
                        </div>
                        <div class="unitItem">
                            <Icon type="android-person"></Icon>
                            {{item.contact}}
                        </div>
                        <div class="unitItem">
                            <Icon type="ios-telephone"></Icon>
                            {{item.tel}}
                        </div>
                        <div class="unitItem">
                            <Icon type="star"></Icon>
                            <span
                                class="showSectionsName"
                                v-for="(item,index) in sections"
                                :key="index"
                            >{{item.name}}</span>
                            <span class="sectionName" :title="item.sectionIds">{{item.sectionIds}}</span>
                        </div>
                        <div class="unitItem">
                            <Icon type="android-time"></Icon>
                            {{item.crtTime}}
                        </div>
                        <div style="text-align: center;margin:1vmin">
                            <Button size="small" type="primary" @click="edit(index)" class="edit">编辑</Button>
                            <Button size="small" type="error" @click="del(index)" class="del">删除</Button>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
        <Page
            :total="page.pageTotal"
            :current="page.pageNum"
            :page-size="page.pageSize"
            show-sizer
            show-total
            :page-size-opts="[8,16,24]"
            placement="top"
            @on-change="handlePage"
            @on-page-size-change="handlePageSize"
            show-elevator
            :style="pageStyle"
        ></Page>
    </div>
</template>
<script>
import { TunnelService } from "../../../../services/tunnelService";
import { EnumsService } from "../../../../services/enumsService";
import { RelatedUnitService } from "../../../../services/relatedUnitService";
import Enum from "../../../../../static/Enum.json";
// import oneTree from '../../../../components/UM/EM/oneTree'

export default {
    data() {
        return {
            pageSizeComputed: null,
            unitInfo: [],
            conditions: {
                name: null,
                contact: null,
                unitType: null,
                startTime: null,
                endTime: null
            },
            page: {
                pageNum: 1,
                pageSize: 8,
                pageTotal: null
            },
            tunnelId: null,
            unitType: [],
            sections: [],
            treeList: [],
            sectionIds: null,
            sectionName: null,
            isShow: false,
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px",
                color: "#fff"
            },
            align: "center"
        };
    },
    watch: {
        $route: function() {
            this.tunnelId = this.$route.params.id;
            this.queryUnits();
        }
    },
    computed: {
        params() {
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.conditions.name,
                contact: this.conditions.contact,
                unitType: this.conditions.unitType,
                startTime: new Date(this.conditions.startTime).getTime(),
                endTime: new Date(this.conditions.endTime).getTime(),
                sectionIds: this.sectionIds
            };
            return Object.assign({}, param);
        }
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
                e.target.className == "ivu-tree-arrow" ||
                e.target.nodeName == "UL" ||
                e.target.nodeName == "LI"
            ) {
                this.isShow = true;
            } else {
                this.isShow = false;
            }
        });
    },
    mounted() {
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
        this.queryUnits();
    },
    methods: {
        queryUnits() {
            let _this = this;
            if (
                new Date(_this.conditions.startTime) >
                new Date(_this.conditions.endTime)
            ) {
                _this.$Message.error("开始时间必须小于结束时间！");
                return;
            }
            RelatedUnitService.relatedUnitDataGrid(_this.params).then(
                function(result) {
                    _this.page.pageTotal = result.total;
                    _this.unitInfo = result.list;
                    for (let index in _this.unitInfo) {
                        _this.unitInfo[index].crtTime = new Date(
                            _this.unitInfo[index].crtTime
                        ).format("yyyy-MM-dd hh:mm:s");
                        var str = "";
                        for (let k in _this.unitInfo[index].sectionList) {
                            if (_this.unitInfo[index].sectionList.length == 1) {
                                _this.unitInfo[index].sectionIds =
                                    _this.unitInfo[index].sectionList[k].name;
                            } else {
                                str +=
                                    _this.unitInfo[index].sectionList[k].name +
                                    ",";
                                _this.unitInfo[
                                    index
                                ].sectionIds = str.substring(0, str.length - 1);
                            }
                        }
                    }
                },
                function(error) {
                    console.log(error);
                }
            );
        },
        del(index) {
            this.$Modal.confirm({
                title: "相关单位",
                width: "24vw",
                content: "<p>是否删除这条相关单位信息</p>",
                onOk: () => {
                    let _this = this;
                    RelatedUnitService.deleteRelatedUnit(
                        _this.unitInfo[index].id
                    ).then(
                        function(result) {
                            _this.unitInfo.splice(_this.unitInfo[index].id, 1);
                            _this.queryUnits();
                        },
                        function(error) {
                            console.log(error);
                        }
                    );
                },
                onCancel: () => {}
            });
        },
        goToMoudle: function(index, type) {
            this.$router.push({
                name: "编辑相关单位",
                params: {
                    id: this.unitInfo[index].id,
                    type: type
                }
            });
        },
        edit(index) {
            this.goToMoudle(index, Enum.pageType.Edit);
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.queryUnits();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.queryUnits();
        },
        choiceAll() {
            let arr = this.$refs.tree.getCheckedNodes();
            var str = "";
            var strIds = "";
            this.sectionName = null;
            if (arr.length == 0) {
                this.sectionIds = null;
            } else {
                for (let index in arr) {
                    str += arr[index].title + ",";
                    this.sectionName = str.substring(0, str.length - 1);
                    strIds += arr[index].id + ",";
                    this.sectionIds = strIds.substring(0, strIds.length - 1);
                }
            }
            this.queryUnits();
        },
        showTree() {
            this.isShow = true;
        }
    }
};
</script>
<style scoped>
.unitBox {
    background: url("../../../../assets/UM/energyBorder2.png") no-repeat;
    background-size: 100% 100%;
    width: 85%;
    margin: 10px auto;
    padding: 0.4vmin 2vmin;
    color: #fff;
    font-size: 1.4vmin;
}
.title {
    font-size: 1.8vmin;
    text-align: center;
    padding: 1vmin 0 2vmin 0;
}
.unitItem {
    padding: 0.4vmin 1vmin;
}

.ivu-icon {
    margin-right: 5px;
    color: #00fff6;
}
.sectionTitle {
    float: left;
}
.ivu-dropdown-item >>> .ivu-select-dropdown {
    margin-left: 14px;
}
.word025 {
    letter-spacing: 0.25em;
    margin-right: -0.25em;
}
.tree {
    background: #fff;
    z-index: 2;
    position: absolute;
    margin-left: 75px;
    border: 1px solid #cccccc;
    width: 60%;
}
.sectionName {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 90%;
    display: inline-block;
    vertical-align: middle;
}
.edit {
    background-color: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.3vmin !important;
    margin-right: 1vmin;
}
.del {
    background-color: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.3vmin !important;
}
@media (min-width: 2200px) {
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
    .queryCondition {
        font-size: 1.4vmin;
    }
    .unitBox {
        border: 0.1vmin solid#dddfe1;
        margin: 1vmin auto;
        padding: 0.5vmin 0px;
        border-radius: 0.4vmin;
        box-shadow: 0.5vmin 0.6vmin 0.4vmin rgba(0, 0, 0, 0.2);
    }
    .title {
        font-size: 2vmin;
        padding: 0px 1vmin;
    }
    .address {
        padding: 0px 1vmin;
    }
    .contact {
        line-height: 3.5vmin;
        margin: 1vmin 0px;
        padding: 0px 1vmin;
    }
    .crtTime {
        font-size: 1.6vmin;
        line-height: 4vmin;
    }
    .option {
        padding: 0px 0.4vmin;
    }
    .ivu-icon {
        margin-right: 0.5vmin;
    }
    .sectionName {
        margin-left: 7.7vmin;
    }
    .ivu-dropdown-item >>> .ivu-select-dropdown {
        margin-left: 1.4vmin;
    }
    .tree {
        margin-left: 7.5vmin;
        border: 0.1vmin solid #cccccc;
    }
    .address,
    .contact {
        font-size: 1.4vmin;
    }
    .ivu-icon {
        font-size: 1.5vmin !important;
    }
    .ivu-tree ul {
        font-size: 1.4vmin;
    }
    .ivu-tree-arrow {
        font-size: 1.2vmin;
    }
    .ivu-checkbox-inner {
        width: 1.4vmin;
        height: 1.4vmin;
        border: 0.1vmin solid #dddee1;
        border-radius: 0.2vmin;
    }
}
</style>
