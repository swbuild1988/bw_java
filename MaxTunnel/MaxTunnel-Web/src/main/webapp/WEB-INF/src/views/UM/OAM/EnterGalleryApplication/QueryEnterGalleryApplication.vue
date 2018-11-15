<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="6">
                    <span class="conditionTitle">入廊目的：</span>
                    <Select v-model="conditions.actionId"  @on-change='queryRecords()' style="width:60%">
                        <Option value=null>所有</Option>
                        <Option v-for="(item,index) in actions" :value="item.val" :key="index">{{ item.key }}</Option>
                    </Select>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">是否完成：</span>
                    <Select v-model="conditions.isFinished"  @on-change='queryRecords()' style="width:60%">
                        <Option value=null>所有</Option>
                        <Option v-for="(item,index) in isFinished" :value="item.key" :key="index">{{ item.val }}</Option>
                    </Select>
                </Col>

                <Col span="6">
                    <span class="conditionTitle">开始时间：</span>
                    <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime" @on-change="queryRecords()"></DatePicker>
                </Col>
                <Col span="6">
                    <span class="conditionTitle">结束时间：</span>
                    <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime" @on-change="queryRecords()"></DatePicker>
                </Col>
            </Row>  
        </div>      
        <div class="list">
            <Table :columns="columns1" :data="applicationRecordList"></Table>
            <Modal
                title="参观人员信息"
                v-model="modal10"
                class-name="vertical-center-modal">
                <Table :columns="columns2" :data="visitorInfo" width="500" :height="300"></Table>
            </Modal>
            <Modal
                title="申请状态"
                v-model="modal1"
                class-name="vertical-center-modal">
                <image-from-url :url="imageUrl"></image-from-url>
            </Modal>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
                    placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator :style="pageStyle"></Page>
        </div>
    </div>
</template>
<script>
import ImageFromUrl from "../../../../components/Common/ImageFromUrl";
import { TunnelService } from '../../../../services/tunnelService'
import { EnumsService } from '../../../../services/enumsService'
import { EnterGalleryService } from '../../../../services/enterGalleryService'

export default {
  components: {
    ImageFromUrl
  },
  data() {
    return {
      imageUrl: "",
      columns1: [
        {
          type: "index",
          align: "center",
          width: 40
        },
        {
          title: "申请人",
          key: "staff",
          align: "center",
          render: (h, params) => {
            return h("div", params.row.staff.name);
          }
        },
        {
          title: "入廊目的",
          key: "actionName",
          align: "center"
        },
        {
          title: "管廊名称",
          key: "tunnel",
          align: "center",
          render: (h, params) => {
            return h("div", params.row.tunnel.name);
          }
        },
        {
          title: "计划入廊时间",
          key: "preTime",
          align: "center",
          // width: 150,
          render: (h, params) => {
            return h(
              "div",
              new Date(params.row.preTime).format("yyyy-MM-dd hh:mm:s")
            );
          }
        },
        {
          title: "流程状态",
          key: "processStatus",
          align: "center",
          // render: (h, params) => {
          //   let tmpStr = "";
          //   if (params.row.status == 3) {
          //     tmpStr = "拒绝";
          //   } else if (params.row.status == 2) {
          //     tmpStr = "通过";
          //   } else if(params.row.status == 1){
          //     tmpStr = "审核中";
          //   }else{
          //     tmpStr = "结束";
          //   }
          //   return h(
          //     "span",
          //     {
          //       style: {
          //         color:
          //           params.row.status == 3 ? "#ff6600"
          //             : params.row.status == 2 ? "#19be6b" 
          //             : params.row.status == 1 ? "#2d8cf0" : "#a005fdb3"
          //       }
          //     },
          //     tmpStr
          //   );
          // }
        },
        {
          title: '是否完成',
          key: 'isFinished',
          align: 'center',
          render: (h,params) => {
            let temp = ''
            if(params.row.isFinished==true){
              temp = '是'
            }else if(params.row.isFinished==false){
              temp = '否'
            }
            return h(
              'span',temp
            )
          } 
        },
        {
          title: "查看申请状态",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.showPic(params.row.id);
                    }
                  }
                },
                "查看"
              )
            ]);
          }
        },
        // {
        //   title: "创建时间",
        //   key: "crtTime",
        //   align: "center",
        //   // width: 150,
        //   render: (h, params) => {
        //     return h(
        //       "div",
        //       new Date(params.row.crtTime).format("yyyy-MM-dd hh:mm:s")
        //     );
        //   }
        // },
        {
          title: "进入管廊时间",
          key: "enterTime",
          align: "center",
          // width: 150,
          render: (h, params) => {
            let temp = ''
            if(params.row.enterTime==null){
              temp = '无'
            }else{
              temp = new Date(params.row.enterTime).format("yyyy-MM-dd hh:mm:s")
            }
            return h(
              "div",temp
            );
          }
        },
        {
          title: "离开管廊时间",
          key: "exitTime",
          align: "center",
          // width: 150,
          render: (h, params) => {
            let temp = ''
            if(params.row.exitTime==null){
              temp = '无'
            }else{
              temp = new Date(params.row.exitTime).format("yyyy-MM-dd hh:mm:s")
            }
            return h(
              "div",temp
            );
          }
        },
        {
          title: "参观人数",
          key: "visitorNumber",
          align: "center"
        },
        {
          title: "所属公司",
          key: "company",
          align: "center",
          render: (h, params) => {
            return h("div", params.row.company.name);
          }
        },
        {
          title: "参观人员详细信息",
          width: 140,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.show(params.row.id);
                    }
                  }
                },
                "信息"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "error",
                    size: "small"
                  },
                  on: {
                    click: () => {
                      this.del(params.row.id);
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      applicationRecordList: [],
      modal10: false,
      modal1: false,
      visitorInfo: [],
      Pic: null,
      img: null,
      columns2: [
        {
          type: "index",
          align: "center",
          width: 30
        },
        {
          title: "姓名",
          key: "name",
          align: "center"
        },
        {
          title: "身份证",
          key: "idCard",
          align: "center",
          width: 210
        },
        {
          title: "联系方式",
          key: "tel",
          width: 140,
          align: "center"
        }
      ],
      page: {
        pageNum: 1,
        pageSize: 10,
        pageTotal: 0
      },
      conditions: {
        isFinished: null,
        actionId: null,
        startTime: null,
        endTime: null
      },
      tunnels: [],
      actions: [],
      status: [],
      tunnelId: null,
      pageStyle: {
          position: 'absolute',
          bottom: '20px',
          right: '15px'
      },
      isFinished:[
        {key: 1, val: '是'},
        {key: 0, val: '否'}
      ]
    };
  },
  watch: {
    $route: function() {
      //2. $route发生变化时再次赋值planId
      this.tunnelId = this.$route.params.id;
      this.tunnels.forEach(a => {
        if (a.id == this.tunnelId) {
          this.queryRecords();
        }
      });
    }
  },
  computed: {
    params() {
      let param = {
        pageNum: this.page.pageNum,
        pageSize: this.page.pageSize,
        tunnelId: this.tunnelId,
        action: this.conditions.actionId,
        startTime: new Date(this.conditions.startTime).getTime(),
        endTime: new Date(this.conditions.endTime).getTime(),
        status: this.conditions.statusId,
        isFinished: this.conditions.isFinished
      };
      return Object.assign({}, param);
    }
  },
  mounted() {
    this.tunnelId = this.$route.params.id;
    this.queryRecords()
    let _this = this
    TunnelService.getTunnels().then(
      (result)=>{
        _this.tunnels = result
      },
      (error)=>{
        _this.Log.info(error)
      })
    EnumsService.getEnterActionType().then(
      (result)=>{
        _this.actions = result
      },
      (error)=>{
        _this.Log.info(error)
      })
    // this.axios.get("/tunnels ").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.tunnels = data;
    //   }
    // });
    // this.axios.get("/action-enums").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.actions = data;
    //   }
    // });
    // this.axios.get("/reqstatus-enums").then(response => {
    //   let { code, data } = response.data;
    //   if (code == 200) {
    //     this.status = data;
    //   }
    // });
  },
  methods: {
    queryRecords() {
      let _this = this
      EnterGalleryService.enterGalleryDatagrid(_this.params).then(
        (result)=>{
          _this.applicationRecordList = result.list;
          _this.page.pageTotal = result.total;
        },
        (error)=>{
          _this.Log.info(error)
        })
      // this.axios.post("req-historys/datagrid", this.params).then(response => {
      //   let { code, data } = response.data;
      //   if (code == 200) {
      //     this.applicationRecordList = data.list;
      //     this.page.pageTotal = data.total;
      //   }
      // });
    },
    handlePage(value) {
      this.page.pageNum = value;
      this.queryRecords();
    },
    handlePageSize(value) {
      this.page.pageSize = value;
      this.queryRecords();
    },
    del(id) {
      this.$Modal.confirm({
        title: '入廊申请',
        content: '<p>是否删除这条入廊申请</p>',
        onOk: () => {
          let _this = this
          EnterGalleryService.deleteEnterGallery(id).then(
            (result)=>{
              _this.applicationRecordList.splice(id, 1);
              _this.queryRecords();
            },
            (error)=>{
              _this.Log.info(error)
            })
        },
        onCancel: () => {

        }
      })
      // this.axios.delete("/req-historys/" + id).then(response => {
      //   this.applicationRecordList.splice(id, 1);
      //   this.queryRecords();
      // });
    },
    show(id) {
      let _this = this
      EnterGalleryService.getDetailsById(id).then(
        (result)=>{
          if(result != null){
            var arr = new Array();
            for (let index in result.visitorInfo.split(",")) {
              var str = result.visitorInfo.split(",")[index];
              arr.push(str);
            }
            var arr2 = new Array();
            for (let k in arr) {
              var obj = {
                name: arr[k].split("-")[0],
                idCard: arr[k].split("-")[1],
                tel: arr[k].split("-")[2]
              };
              arr2.push(obj);
            }
            _this.visitorInfo = arr2;
            }
        },
        (error)=>{
          _this.Log.info(error)
        })
      // this.axios.get("/req-historys/" + id).then(response => {
      //   if (response.data != null) {
      //     var arr = new Array();
      //     for (let index in response.data.data.visitorInfo.split(",")) {
      //       var str = response.data.data.visitorInfo.split(",")[index];
      //       arr.push(str);
      //     }
      //     var arr2 = new Array();
      //     for (let k in arr) {
      //       var obj = {
      //         name: arr[k].split("-")[0],
      //         idCard: arr[k].split("-")[1],
      //         tel: arr[k].split("-")[2]
      //       };
      //       arr2.push(obj);
      //     }
      //     this.visitorInfo = arr2;
      //   }
      // });
      this.modal10 = true;
    },
    showPic(id) {
        this.imageUrl = "/req-historys/" + id + "/activiti-png";
        this.modal1 = true;
    }
  }
};
</script>
<style scoped>
.allDiv{
    position: relative;
    min-height: 100%;
    padding-bottom: 60px;
}
.conditions {
    height: 60px;
    line-height: 60px;
    background: #ffffff;
    padding-left: 10px;
}
.list{
  background: #ffffff;
  margin-top: 10px;
}
</style>
