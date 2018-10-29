<template>
  <Modal title="批量出库" v-model="show.state" width="800">
    <div>
      <Row class="top">
        <Col span="10">
        <span class="planDec">设备类型：</span>
        <Select v-model="condition.type" @on-change='showTable()' style="width:60%">
          <Option value=null key="0">所有</Option>
          <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
        </Select>
        </Col>
        <Col span="10">
        <span class="planDec">入库时间：</span>
        <DatePicker type="date" placeholder="选择时间" style="width: 220px" v-model="condition.startEquipmentTime"
                    @on-change='showTable()'></DatePicker>
        </Col>
        <Col span="2" offset="2">
        <div style="position: relative;float: right;right: 0">
          <Button type="primary" icon="ios-search" @click="getFilterData">查询</Button>
        </div>
        </Col>
      </Row>
      <Table style="margin-top: 10px;" stripe border ref="selection" :columns="tableColumns" @on-selection-change="selectEquipment"
             :data="tableData"></Table>
    </div>
    <div slot="footer">
      <Button type="text" @click="show.state=!show.state">取消</Button>
      <Button type="info" @click="outStorage">出库</Button>
    </div>
  </Modal>
</template>

<script>
  import types from '../../../../static/Enum.json'
  export default {
    name: "out-storage-equipment",
    props: {
      show: {
        type: Object,
      },
      equipmentList: {
        default: {}
      },
    },
    data() {
      return {
        tableColumns: [
          {
            type: 'selection',
            width: 60,
            align: 'center'
          },
          {
            title: '设备编号',
            key: 'No'
          },
          {
            title: '设备名称',
            key: 'Name'
          },
          {
            title: '设备类型',
            key: 'Type'
          },
          {
            title: '状态',
            key: 'status'
          },
          {
            title: '入库时间',
            key: 'time'
          }
        ],
        tableData: [
          {
            No: "001",
            Name: "海康摄像头1",
            time: "2018-10-1",
            Type: "视频设备",
            status: "良好",
          },
          {
            No: "002",
            Name: "海康摄像头2",
            time: "2018-10-1",
            Type: "视频设备",
            status: "良好",
          },
          {
            No: "003",
            Name: "海康摄像头3",
            time: "2018-10-1",
            Type: "视频设备",
            status: "良好",
          },
        ],
        condition:{},
        equipmentStatus: types.equipmentStatus,
        equipmentTypes: types.equipmentType,
      }
    },
    watch: {
      'show.state': function () {
        // show.state发生变化时再次赋值
        if (this.show.state == true) {
          this.clearTableSelect();
        }
      },
    },
    mounted() {
    },
    methods: {
      clearTableSelect() {
        this.$refs.selection.selectAll(false);
      },

      //获取过滤数据
      getFilterData() {
        this.axios.post("").then(result => {
          let {code, data} = result.data;
          if (code == 200) {
            this.equipmentList = data;
          }
        })
      },

      //勾选设备
      selectEquipment(arr) {
        // console.log(arr);
      },

      //设备出库
      outStorage() {

      },
    },

  }
</script>

<style scoped>
  .vertical-center-modal {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .vertical-center-modal > .ivu-modal {
    top: 0;
  }
</style>
