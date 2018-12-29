<template>
    <div class="queryPlan">
      <queryCondition v-on:changeTunnel="selectdTunnelName"></queryCondition>
      <h1 v-show="false">今日统计:</h1>
          <Tag class="tag" color="blue">今日巡检计划：10</Tag>
          <Tag class="tag" color="green">今日巡检路线：5</Tag>
          <Tag class="tag" color="red">今日巡检点：245</Tag>
          <Tag class="tag" color="yellow">今日已巡检：8</Tag>
        <!-- <Table ref="selection" border stripe :columns="columns1" :data="data1"></Table> -->
        <table class="table border" border="1">
          <thead>
            <tr>
              <th v-for="item in columns">{{item.title}}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(datasome,index) in data">
              <!-- <td :v-html="input type='checkbox'"></td> -->
              <td>{{index+1}}</td>
              <td>{{datasome.id}}</td>
              <td>{{datasome.name}}</td>
              <td>{{datasome.tunnelName}}</td>
              <td>{{datasome.workOrderStatus}}</td>
              <td>{{datasome.groupName}}</td>
              <td>{{datasome.responsibilityPerson}}</td>
              <td>{{datasome.createTime}}</td>
              <td>{{datasome.inspectTime}}</td>
              <td>
                <Button @click="edit(index)">编辑</Button>
                <Button @click="del(index)">删除</Button>
              </td>
            </tr>
          </tbody>
        </table>
    </div>  
</template>
<script>
import axios from "axios";
import queryCondition from '../../../../components/UM/OMM/patrolScheme/queryCondition.vue'
export default {
  data() {
    return {
      data: [],
      originalData: [],
      columns: [
        // {
        //   type: "selection",
        //   width: 60,
        //   align: "center"
        // },
        {
          type: "index",
          width: 60,
          align: "center",
          title: "#"
        },
        {
          title: "计划编号",
          key: "id",
          align: "center"
        },
        {
          title: "计划名称",
          key: "name",
          align: "center"
        },
        {
          title: "所属管廊    ",
          key: "tunnelId",
          align: "center"
        },
        {
          title: "计划状态",
          key: "workOrderStatus",
          align: "center"
        },
        {
          title: "责任班组",
          key: "groupId",
          align: "center"
        },
        {
          title: "责任人",
          key: "responsibilityPerson",
          align: "center"
        },
        {
          title: "开始时间",
          key: "createTime",
          align: "center"
        },
        {
          title: "结束时间",
          key: "inspectTime",
          align: "center"
        },
        {
          title: "操作",
          key: "operation",
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
                      this.show(params.index);
                    }
                  }
                },
                "编辑"
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
                      this.remove(params.index);
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ]
    };
  },
  mounted() {
      //渲染table
      axios.get("inspection-plans").then(response => {
        let { code, data } = response.data;
        if (code == 200) {
          this.originalData = data;
          this.data = data;
          //转换时间戳为  yyyy-mm-dd 的形式
          Date.prototype.toLocaleString = function() {
            return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
          };
          for(let index in data){
            data[index].createTime = new Date(data[index].createTime).toLocaleString();
            data[index].inspectTime = new Date(data[index].inspectTime).toLocaleString();
          }
        }
      });
  },
  methods: {
    handleSelectAll(status) {
      this.$refs.selection.selectAll(status);
    },
    //条件查询
    selectdTunnelName: function(selectedTunnelId){
      this.val = selectedTunnelId;
      if (selectedTunnelId > 0){
        this.data = this.originalData.filter(datasome => datasome.tunnelId == selectedTunnelId);  
      } 
    },
    edit(index){
      console.log(index);
    },
    del(index){
      this.originalData.splice(index, 1); 
      // axios.get("inspection-plans").then(response => {
      //   let { code, data } = response.data;
      //   if (code == 200) {
      //     this.originalData = data;
      //     console.log(this.originalData);
      //     // this.data = data;
      //     //转换时间戳为  yyyy-mm-dd 的形式
      //     Date.prototype.toLocaleString = function() {
      //       return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
      //     };
      //     for(let index in data){
      //       data[index].createTime = new Date(data[index].createTime).toLocaleString();
      //       data[index].inspectTime = new Date(data[index].inspectTime).toLocaleString();
      //     }
      //   }
      // }); 
    }
  },
  components: {
    queryCondition
  }
};
</script>
<style scoped>
.table {
  width: 100%;
  border-collapse:collapse;
}
.border {
  border: 1px solid #ccc;
  padding: 10px;
}
.border tr td,.border tr th {
  border: 1px solid #cdd1d5;
  padding: 10px;
  text-align: center;
}
</style>


