<template>
  <div>
    <Modal v-model="show.state" title="批量添加监测对象" width=950>
      <Form :label-width="160">
        <Row>
          <Col span="6">
            <FormItem label="管廊编号起始：">
              <Input v-model="spellRule.tunnel.start" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="区域编号起始：">
              <Input v-model="spellRule.area.start" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="管仓编号起始：">
              <Input v-model="spellRule.storeType.start" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="设备类型编号起始：">
              <Input v-model="spellRule.objtype.start" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="管廊编号长度：">
              <Input v-model="spellRule.tunnel.length" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="区域编号长度：">
              <Input v-model="spellRule.area.length" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="管仓编号长度：">
              <Input v-model="spellRule.storeType.length" class="InputWidth"/>
            </FormItem>
          </Col>
          <Col span="6">
            <FormItem label="设备类型长度：">
              <Input v-model="spellRule.objtype.length" class="InputWidth"/>
            </FormItem>
          </Col>
        </Row>
      </Form>
      <hr>
      <div class="vueXlsxBox">
        <vue-xlsx-table @on-select-file="equipmentMatchesSelectedFile">选择设备类型文件</vue-xlsx-table>
      </div>
        <Row>
          <Col span="8" v-for="match in equipmentMatches" :key="match.sn" style="height: 50px;">
            <div>
              <div class="sn">{{match.sn}}</div>
              <div class="name">{{match.name}}</div>
              <Select v-model="match.objtype" style="width: 50%;">
                <Option v-for="type in objtypes" :key="type.val" :value="type.val">{{type.key}}</Option>
              </Select>
            </div>
          </Col>
        </Row>
      <hr>
      <!-- <div v-for="measObj in importMeasObjs" :key="measObj.id">
                    <label>{{measObj.id}}</label>
                    <label>{{measObj.name}}</label>
      </div>-->
      <div class="vueXlsxBox">
        <vue-xlsx-table @on-select-file="measObjsSelectedFile">选择添加的监测对象</vue-xlsx-table>
        <!-- <Button type="primary" size="small" v-on:click="translate">转换</Button> -->
      </div>
      <Table stripe border :columns="columns7" :data="page.data"></Table>
      <div style="text-align: right;margin-top: 10px;">
        <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total   
      placement="top" @on-change="onChange" show-elevator></Page>
      </div>
      <div slot="footer">
        <Button type="primary" size="large" v-on:click="sendMsg">保存</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import { EnumsService } from "../../../services/enumsService";
import { TunnelService } from "../../../services/tunnelService";

export default {
  name: "meas-obj-module",
  data() {
    return {
      columns7: [
        {
          title: "设备ID",
          key: "id",
          align: 'center'
        },
        {
          title: "名称",
          key: "name",
          align: 'center'
        },
        {
          title: "管廊",
          align: 'center',
          key: 'tunnelName'
        },
        {
          title: "区域",
          key: "areaName",
          align: 'center'
        },
        {
          title: '仓段',
          key: 'storeName',
          align: 'center'
        },
        {
          title: "设备类型",
          key: "objtypeName",
          align: 'center'
        }
      ],
      spellRule: {
        tunnel: {
          start: 0,
          length: 2
        },
        area: {
          start: 2,
          length: 2
        },
        storeType: {
          start: 4,
          length: 2
        },
        objtype: {
          start: 6,
          length: 2
        }
      },
      tunnels: [],
      equipmentMatches: [
        {
          name: "氧气检测仪",
          sn: "03",
          objtype: 1
        },
        {
          name: "声光报警器",
          sn: "32",
          objtype: 10
        },
        {
          name: "温度仪",
          sn: "24",
          objtype: 0
        },
        // {
        //   name: "电子井盖",
        //   sn: "33",
        //   objtype: 0
        // }
      ],
      importMeasObjs: [
        {
          id: "0203010104",
          name: "红外摄像机"
        },
        {
          id: "0203022401",
          name: "温度仪"
        }
      ],
      objtypes: [
        {
          val: 1,
          key: "xxx"
        }
      ],
      page: {
        data: [],
        totalList: [],
        pageNum: 1,
        pageSize: 10,
        pageTotal: 0
      }
    };
  },
  props: {
    show: {
      state: {
        default: false
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      var _this = this;
      EnumsService.getObjType().then(
        result => {
          _this.objtypes = result;
        },
        error => {
          _this.Log.info(error);
        }
      );
    },
    equipmentMatchesSelectedFile(data) {
      this.equipmentMatches = [];
      data.body.forEach(element => {
        let tmp = {
          name: element[data.header[1]],
          sn: element[data.header[0]],
          objtype: 0
        };
        if (data.header.length > 2)
          tmp.objtype = parseInt(element[data.header[2]]);
        this.equipmentMatches.push(tmp);
      });
      console.log("获得后", this.equipmentMatches);
    },
    //添加监测对象
    measObjsSelectedFile(data) {
      this.importMeasObjs = [];
      this.page.pageTotal = data.body.length
      data.body.forEach(element => {
        let tmp = {
          name: element[data.header[0]],
          id: element[data.header[1]]
        };   
        this.importMeasObjs.push(tmp)
        if(data.body.length<=this.page.pageSize){
          this.page.data.push(tmp)
        }
        
      });
      this.onChange(1)
      this.translate()
    },
    //分页
    onChange(curPage){
      this.page.data.slice(0,this.page.data)
      this.page.data = []
      for(var i = 10*(curPage - 1) + 1;
        i<= ((this.page.pageTotal>10*curPage)?(10*curPage):(this.page.pageTotal));
        i++){
        this.page.data.push(this.importMeasObjs[i-1]);
      }
    },
    //转换
    translate() {
      this.importMeasObjs.forEach(element => {
        var tunnel_sn = element.id.substr(
          this.spellRule.tunnel.start,
          this.spellRule.tunnel.length
        );
        var area_sn = element.id.substr(
          this.spellRule.area.start,
          this.spellRule.area.length
        );
        var store_sn = element.id.substr(
          this.spellRule.storeType.start,
          this.spellRule.storeType.length
        );
        var type_sn = element.id.substr(
          this.spellRule.objtype.start,
          this.spellRule.objtype.length
        );

        var tunnel = this.tunnels.find(a => a.sn == tunnel_sn);
        var area = tunnel.areas.find(a => a.sn == area_sn);
        var store = tunnel.stores.find(a => a.sn == store_sn);
        var objtype = this.equipmentMatches.find(a => a.sn == type_sn)

        element.tunnelId = tunnel.id;
        element.tunnelName = tunnel.name
        element.areaId = area.id;
        element.areaName = area.name;
        element.storeId = store.id;
        element.storeName = store.name
        element.objtypeId = objtype.objtype;
        element.objtypeName = objtype.name
      });

      console.log("objs", this.importMeasObjs);
    },
    sendMsg() {
      this.$emit("saveMulti", this.importMeasObjs);
      this.show.state = false
    }
  }
};
</script>
<style scoped>
.sn,.name{
  display: inline-block
}
.sn{
  width: 30px;
}
.name{
  width: 80px;
}
.vueXlsxBox{
  margin: 20px 0px;
}
</style>
    