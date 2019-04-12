<template>
  <!--管仓 -->
  <div>
    <h1 style="text-align:center;margin-bottom:20px;margin-top:10px">管仓管理</h1>
    <Row style="marginLeft:25px;marginBottom:10px;">
      <Col span="6">
        <div>
          <span>管仓名称：</span>
          <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth"/>
        </div>
      </Col>
      <Col span="6">
        <div>
          <span>所属管廊：</span>
          <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
            <Option value="null">所有</Option>
            <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
          </Select>
        </div>
      </Col>
      <Col span="6">
        <div>
          <span>管仓类型：</span>
          <Select v-model="researchInfo.storeTypeId" placeholder="请选择管仓类型" class="inputWidth">
            <Option value="null">所有</Option>
            <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
          </Select>
        </div>
      </Col>
    </Row>
    <Row type="flex" align="middle" class="code-row-bg" style="marginLeft:25px;marginBottom:10px;">
        <Col span="6">
            <span>开始时间：</span>
            <DatePicker
            type="datetime"
            placeholder="请选择开始时间"
            class="inputWidth"
            v-model="researchInfo.startTime"
            ></DatePicker>
        </Col>
        <Col span="6">
            <span>结束时间：</span>
            <DatePicker
            type="datetime"
            placeholder="请选择结束时间"
            class="inputWidth"
            v-model="researchInfo.endTime"
            ></DatePicker>
        </Col>
        <Col span="10">
            <Button type="primary" size="small" icon="ios-search" @click="resetPageSearch()">查询</Button>
            <Button type="error" size="small" @click="addNewStore()">新增管仓</Button>
            <Button type="info" size="small" @click="addMultiStores()">批量新增管仓</Button>
            <Button v-show="deleteShow" type="warning" size="small" @click="alldelete()">批量删除</Button>
            <Button v-show="!deleteShow" disabled type="warning" size="small">批量删除</Button>
        </Col>
    </Row>
    <div>
        <Table border ref="selection" :data="storeData" :columns="storeColumns" @on-selection-change="startdelete"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" @on-change="handlePage" show-elevator :style="pageStyle" ></Page>
    </div>
    <div>
        <barn-multi-module v-bind="addMultiStoreInfo" v-on:listenToAddMulti="saveMultiStore"></barn-multi-module>
    </div>
    <div>
        <barn-modification ref="storeModule" v-bind="changeStoreInfo"  v-on:childIsRefresh="childIsRefresh" v-on:sendMsg="saveChangeStore"></barn-modification>
    </div>
  </div>
</template>

<script>
import BarnMultiModule from "../../CM/Store/BarnMultiModule.vue";
import BarnModification from "../../CM/Store/BarnModification.vue";
import { StoreService } from '@/services/storeService'
export default {
    name: "store-manage",
    components: {
        BarnMultiModule,
        BarnModification
    },
    data() {
        return {
            researchInfo: {
                name: null,
                tunnelId: null,
                storeTypeId: null,
                startTime: null,
                endTime: null
            },
            storeColumns: [
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
                    title: "管仓名称",
                    key: "name",
                    align: "center"
                },
                {
                    title: "所属管廊",
                    key: "tunnelName",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.tunnel.name)
                    }
                },
                {
                    title: "管仓类型",
                    key: "storeTypeName",
                    align: "center",
                    render: (h,params) => {
                        return h('div',params.row.storeType.name)
                    }
                },
                {
                    title: "类型编号",
                    key: "sn",
                    align: "center"
                },
                {
                    title: '宽度',
                    align: 'center',
                    key: 'width'
                },
                {
                    title: '高度',
                    align: 'center',
                    key: 'height'
                },
                {
                    title: 'L',
                    align: 'center',
                    key: 'l'
                },
                {
                    title: 'K',
                    align: 'center',
                    key: 'k'
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
                                            this.editStore(params.row.id);
                                        }
                                    }
                                },
                                "修改"
                            )
                        ]);
                    }
                }
            ],
            storeData: [],
            types: [],
            tunnels: [],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0
            },
            formValidate: {
                name: null,
                tunnelId: null,
                storeTypeId: null,
                camera: null,
                sn: null
            },
            addMultiStoreInfo: {
                show: { state: false },
                addInfo: {}
            },
            changeStoreInfo: {
                show: { state: false },
                type: null
            },
            deleteShow: false,
            deleteSelect: [],
            pageStyle: {
                position: 'absolute',
                bottom: '35px',
                right: '40px'
            },
        };
    },
    mounted() {
        this.showTable();
        StoreService.getTunnels().then(
            result => {
                this.tunnels = result;
            },
            error => {
                this.Log.info( error )
            }
        )
        StoreService.getStoreType().then(res=>{
            result => {
                this.types = result;
            },
            error => {
                this.Log.info( error )
            }
        })
    },
    computed: {
        params() {
            // 查询
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name: this.researchInfo.name,
                tunnelId: this.researchInfo.tunnelId,
                storeTypeId: this.researchInfo.storeTypeId,
                startTime: this.researchInfo.startTime,
                endTime: this.researchInfo.endTime
            };
            return Object.assign({}, param);
        }
    },
    methods: {
        showTable() {
            StoreService.queryStore(this.params).then(
                result => {
                    this.storeData = result.list;
                    this.page.pageTotal = result.total;
                },
                error => {
                    this.Log.info( error )
                }
            )
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        //添加
        addNewStore() {
            this.changeStoreInfo.type = 1
            this.changeStoreInfo.show.state = true;
        },
        // 编辑
        editStore(id) {
            this.changeStoreInfo.show.state = true;
            this.changeStoreInfo.type = 2
            this.$refs.storeModule.getStoreInfo(id)
        },
        //add
        childIsRefresh(){
            this.resetPageSearch()
            this.changeStoreInfo.show.state = false;
        },
        // 修改
        saveChangeStore() {
            this.resetPageSearch();
            this.changeStoreInfo.show.state = false;
            this.$Message.success("修改成功！");
        },
        addMultiStores() {
            this.addMultiStoreInfo.show.state = true;
        },
        saveMultiStore(_data) {
            this.formValidate = _data;
            this.axios.post("stores/multi", _data).then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.page.pageTotal = data.total;
                    this.$Message.success("添加成功！");
                    this.addMultiStoreInfo.show.state = !this.addMultiStoreInfo
                        .show.state;
                    this.resetPageSearch();
                }
            });
        },
        startdelete(selection) {
            if (selection.length != 0) {
                this.deleteShow = true;
                this.deleteSelect = selection;
            } else {
                this.deleteShow = false;
            }
        },
        alldelete() {
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除选中的信息吗？</p>",
                onOk: () => {
                    let ids = this.deleteSelect[0].id;
                    for (let i = 1; i < this.deleteSelect.length; i++) {
                        ids += "," + this.deleteSelect[i].id;
                    }
                    StoreService.bulkDelete(ids).then(
                        result => {
                            this.$Message.info("已删除");
                            this.deleteShow = false;
                            this.resetPageSearch();
                        },
                        error => {
                            this.Log.info( error )
                        }
                    )
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                    this.resetPageSearch();
                }
            });
        },
        resetPageSearch() {
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
.pageStyle {
    text-align: right;
    margin-top: 100px;
    margin-right: 10px;
}
</style>




