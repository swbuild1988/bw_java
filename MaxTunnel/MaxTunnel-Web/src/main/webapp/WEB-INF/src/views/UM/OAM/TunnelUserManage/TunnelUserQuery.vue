<template>
  <div>
    <div class="queryCondition">
      <Row>
        <Col span="6">
        所属管廊:
        <Select v-model="conditions.tunnelId" style="width:60%">
          <Option value=null key="0">所有</Option>
          <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
        </Col>
      </Row>
    </div>
    <div class="queryUserList">
      <Row class="bottom">
        <Col span="5" class="userList">
        用户列表</Col>
        <Col span="19" align="right">
        <Input style="width: 300px;" v-model="conditions.fuzzy">
        <Button slot="append" icon="ios-search"></Button>
        </Input>
        </Col>
        <Col span="24">
        <div class="addList">
          <Icon type="plus-round"></Icon>
          添加
        </div>
        </Col>
        <ul class="table">
          <li v-for="(item,index) in userList" :key="item.index">
            <div class="inline-box">
              <img :src="item.imgUrl">
            </div>
            <div class="inline-box" style="width: 30%">
              <div class="UserName">{{ item.userName }}</div>
              <div>联系方式：{{ item.tel }}</div>
            </div>
            <div class="inline-box" style="width: 25%;text-align: right;padding-right: 20px;">
              <div>{{ item.unit }}</div>
              <div>{{ item.position }}</div>
            </div>
            <div class="inline-box" style="width: 25%">
              <div>当前位置：{{item.curLocation}}</div>
              <div>上次进入时间：{{ item.lastTime }}</div>
            </div>
            <div class="inline-box options" style="width: 10%">
              <span class="edit" @click="edit(index)">编辑</span>
              <span>|</span>
              <span @click="show(index)">查看</span>
              <span>|</span>
              <span @click="del(index)">删除</span>
            </div>
          </li>
        </ul>
        <div class="paging" v-cloak>
          <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
        </div>
      </Row>
    </div>
  </div>
</template>
<script>
  import { TunnelService } from '../../../../services/tunnelService'

  export default {
    data() {
      return {
        tunnels: [],
        conditions: {
          tunnelId: null,
          fuzzy: null
        },
        userList: [
          {
            userId: 1,
            userName: '张安',
            unit: '北京市燃气公司',
            position: '总督工',
            tel: '13756851203',
            lastTime: '2018-6-14 19:33:06',
            locationDec: '我在第一大区燃气管道',
            imgUrl: '../../../../../static/UM/01.jpg',
            curLocation: '管廊内'
          },
          {
            userId: 2,
            userName: '李铭顺',
            unit: '北京市燃气公司',
            position: '总督工',
            tel: '13725694585',
            lastTime: '2018-6-14 19:33:06',
            locationDec: '我在第一大区燃气管道',
            imgUrl: '../../../../../static/UM/02.jpeg',
            curLocation: '管廊内'
          },
          {
            userId: 3,
            userName: '孟冬秋',
            unit: '北京市燃气公司',
            position: '总施工',
            tel: '17256891585',
            lastTime: '2018-6-14 19:33:06',
            locationDec: '我在第一大区燃气管道',
            imgUrl: '../../../../../static/UM/03.jpg',
            curLocation: '管廊外'
          }
        ],
        page: {
          pageTotal: 0,
          pageNum: 1,
          pageSize: 10
        }
      }
    },
    mounted() {
      //从数据库读取select的option选项
      let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              _this.tunnels = result;
              _this.selectedTunnel = _this.tunnels.length > 0 ? _this.tunnels[0] : {};
              _this.selectedTunnelName = _this.selectedTunnel.name;
          },
          (error)=>{
              console.log(error)
      })
      // axios.get("/tunnels ").then(response => {
      //   let {code, data} = response.data;
      //   if (code == 200) {
      //     this.tunnels = data;
      //     this.selectedTunnel = this.tunnels.length > 0 ? this.tunnels[0] : {};
      //     this.selectedTunnelName = this.selectedTunnel.name;
      //   }
      // });
    },
    methods: {
      handlePage(value) {
        this.page.pageNum = value
        this.showTable()
      },
      handlePageSize(value) {
        this.page.pageSize = value
        this.showTable()
      },
      // type 1:查看， 2：编辑
      goToMoudle: function (index, type) {
        this.$router.push({
          name: 'UMTunnelUserDetails',
          params: {
            id: this.equipments[index].id,
            type: type,
          }
        });
      },
      show(index) {
        this.goToMoudle(index, types.pageType.Read);
      },
      edit(index) {
        this.goToMoudle(index, types.pageType.Edit);
      },
      add(path) {
        this.$router.push(path);
      }
    }
  }
</script>
<style scoped>
  .bottom {
    margin-top: 40px;
  }

  .userList {
    font-size: 17px;
    font-weight: 700;
  }

  .addList {
    border: 1px dashed #dfdfdf;
    text-align: center;
    line-height: 40px;
    color: #8b8b8b;
    margin-top: 20px;
    border-radius: 10px;
  }

  ul.table, li {
    width: 100%;
  }

  .table li {
    list-style: none;
    border-bottom: 1px solid #c5c2c2;
    padding: 5px;

  }

  .table li .inline-box {
    display: inline-block;
    vertical-align: middle;
    color: #8b8b8b;
  }

  .table li .inline-box div {
    line-height: 30px;
  }

  .inline-box img {
    width: 70px;
    height: 50px;
    background: #e8e8e8;
    margin-top: 10px;
    margin-right: 20px;
  }

  .UserName {
    font-size: 14px;
    font-weight: 700;
    color: black;
  }

  .options span {
    color: cornflowerblue;
    padding: 5px;
    cursor: pointer;
  }

  .paging {
    margin-top: 15px;
    text-align: right;
  }
</style>


