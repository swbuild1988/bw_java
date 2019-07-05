<template>
    <!--区域管理 -->
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">区域管理</h1>
        <Row style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
            <div>
                <span>区域名称：</span>
                <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
            </div>
            </Col>
            <Col span="6">
            <div>
                <span>所属管廊：</span>
                <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                    <Option value=null>所有</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                </Select>
            </div>
            </Col>
            <Col span="10">
                <Button type="primary" size="small"  icon="ios-search" @click="resetPageSearch()">查询</Button>
                <Button type="error" size="small" @click="addNewArea()">新增区域</Button> 
                <Button type="info" size="small" @click="addMultiArea()">批量新增区域</Button> 
                <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button> 
                <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
            </Col>  
        </Row>
        <Row type="flex" align="middle" class="code-row-bg" style="marginLeft:25px;marginBottom:10px;">
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" class="inputWidth" v-model="researchInfo.startTime">
                </DatePicker>
                </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" class="inputWidth" v-model="researchInfo.endTime">
                </DatePicker>
            </Col>  
        </Row>
        <Table border ref="selection" :columns="areaColums" :data="areaData" @on-selection-change="startdelete" style="margin:20px;"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator class="pageStyle"></Page>
        <div>
            <area-multi-module v-bind="addMultiAreaInfo" v-on:listenToAddMulti="saveMultiArea"></area-multi-module>
        </div>
        <div>
            <area-modifiation ref="areaModule" v-bind="modificationAreaInfo" v-on:childIsRefresh="childIsRefresh"></area-modifiation>
        </div>
    </div>
</template>

<script>
import AreaMultiModule from "../../../views/CM/AreaControl/AreaMultiModule.vue";
import AreaModifiation from "../../../views/CM/AreaControl/AreaModification.vue";
import { AreaService } from '@/services/areaService'
export default {
    name: "barn-manage",
    components: {
        AreaModifiation,
        AreaMultiModule
    },
    data() {
        return {
            researchInfo: {
                name: null,
                tunnelId: null,
                startTime: null,
                endTime: null
            },
            areaColums: [
                {
                    type: "selection",
                    width: 60,
                    align: "center"
                },
                {
                    type: "index",
                    align: "center",
                    width: 60
                },
                {
                    title: "区域名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "区域编号",
                    key: "sn",
                    align: "center"
                },
                {
                    title: "所属管廊",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.tunnel.name)
                    }
                },
                {
                    title: '长度',
                    align: 'center',
                    key: 'length'
                },
                {
                    title: "创建时间",
                    key: "crtTime",
                    align: "center",
                    width: 190,
                    render: (h,params) => {
                        let temp = new Date(params.row.crtTime).format('yyyy-MM-dd hh:mm:s')
                        return h('div',temp)
                    }
                },
                {
                    title: "操作",
                    key: "action",
                    align: "center",
                    width: 80,
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.editArea(params.row.id);
                                        }
                                    }
                                },
                                "修改"
                            )
                        ]);
                    }
                }
            ],
            areaData: [],
            types: [],
            tunnels: [],
            areas: [],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            addMultiAreaInfo: {
                show: { state: false },
                addInfo: {}
            },
            modificationAreaInfo: {
                show: { state: false },
                type: null
            },
            deleteShow: false,
            deleteSelect: []
        };
    },
    mounted() {
        AreaService.getTunnel().then(
            result => {
                this.tunnels = result
            },
            error => {
                this.$Message.error(error)
            }
        )
        this.showTable();
    },
    computed: {
        researches() {
            let research = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                tunnelId: this.researchInfo.tunnelId,
                startTime: this.researchInfo.startTime,
                endTime: this.researchInfo.endTime
            };
            return Object.assign({}, research);
        }
    },
    methods: {
        showTable() {
            AreaService.queryAreas(this.researches).then(
                result => {
                    this.areaData = result.list;
                    this.page.pageTotal = result.total;
                },
                error => {
                    this.$Message.error(error)
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.resetPageSearch();
        },
        //新增modal
        addNewArea() {
            this.modificationAreaInfo.show.state = true;
            this.modificationAreaInfo.type = 1
        },
        //修改modal
        editArea(id) {
            this.modificationAreaInfo.show.state = true;
            this.modificationAreaInfo.type = 2
            this.$refs.areaModule.getAreasInfo(id)
        },
        childIsRefresh(isRefresh){
            if(isRefresh==true){
                this.resetPageSearch()
                this.modificationAreaInfo.show.state = false;
            }
        },
        //批量新增modal
        addMultiArea() {
            this.addMultiAreaInfo.show.state = !this.addMultiAreaInfo.show.state;
        },
        //批量保存新区域
        saveMultiArea(_data) {
            this.axios.post("/areas/multi", _data).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addMultiAreaInfo.show.state = !this.addMultiAreaInfo.show.state;
                    this.resetPageSearch();
                }
            });
        },
        
        //保存修改
        saveChangeArea(data) {
            this.resetPageSearch();
        },
        getBarn(_data) {
            //获取所属管仓
            this.researchInfo.storeId = _data.id;
            this.barnNameShow = _data.name;
        },
        //选中与删除
        startdelete(selection) {
            if (selection.length != 0) {
                this.deleteShow = true;
                this.deleteSelect = selection;
            } else {
                this.deleteShow = false;
            }
        },
        //选中与删除
        alldelete() {
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除选中的信息吗？</p>",
                onOk: () => {
                    let ids = this.deleteSelect[0].id;
                    for (let i = 1; i < this.deleteSelect.length; i++) {
                        ids += "," + this.deleteSelect[i].id;
                    }
                    AreaService.bulkDelete(ids).then(
                        result => {
                            this.$Message.success("已删除");
                            this.deleteShow = false;
                            this.resetPageSearch();
                        },
                        error => {
                            this.$Message.error(error)
                        }
                    )
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                    this.resetPageSearch();
                }
            });
        },
        resetPageSearch(){
            this.page.pageNum = 1;
            this.showTable();
        }
    }
};
</script>

<style scoped>
.inputWidth {
    width: 60%;
}
.pop {
    max-height: 500px;
    overflow-y: auto;
}
.pageStyle {
    text-align: right;
    margin-top: 20px;
    margin-right: 10px;
}
.ivu-poptip {
    width: 100%;
}
</style>




