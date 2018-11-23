<template>
  <div class="main" :style="{height:fullHeight+'px'}">
    <Row>
      <Col span="24">
      <div id="top" class="topOpacity">
        <div class="Icon">
        </div>
        <span style="font-size: 38px; padding-top:4px"> {{titleName}}</span>
        <div  style="float: right;padding-top: 5px;padding-bottom: 10px;" >
          <Dropdown divided @click.native="logout">
            <a>
              <Icon type="power" size="40" color="#fff"></Icon>
            </a>
          </Dropdown>
        </div>
        <div style="font-size: 18px;float: right;padding-right: 10px;padding-top: 18px;" >
          {{nowFormat}}
        </div>
      </div>
      </Col>
      <br><br><br> <br><br><br>
      <Row type="flex" justify="center" align="top" class="code-row-bg">
        <Col span="6">
        <Button class=" btn-xunjian ywGuanli"
                @click="goToMoudle({ path:'/UM/patrol/homePage'})">
          <div><img :width="imgWidth" src="../../assets/UM/巡检.png">
            <div :style="{fontSize:mySize}">巡检管理</div>
          </div>
        </Button>
        <Button type="warning" class="ywGuanli" @click="goToMoudle({ path:'/UM/overhaul/query'})"
        >
          <div><img :width="imgWidth" src="../../assets/UM/检修.png">
            <div :style="{fontSize:mySize}">检修管理</div>
          </div>
        </Button>
        <Button class=" btn-quexian ywGuanli"
                @click="goToMoudle({ path:'/UM/defect/query'})">
          <div><img :width="imgWidth" src="../../assets/UM/缺陷管理.png">
            <div :style="{fontSize:mySize}">缺陷管理</div>
          </div>
        </Button>
        <Button type="primary" class="ywGuanli"
                @click="goToMoudle({ path:'/UM/equipment/homePage'})">
          <div><img :width="imgWidth" src="../../assets/UM/设备台账.png">
            <div :style="{fontSize:mySize}">设备管理</div>
          </div>
        </Button>
        </Col>
        <Col span="6">
         <!--<Button type="info" class="ywGuanli"-->
                <!--@click="goToMoudle({ path:'/UM/TunnelSupervise'})">-->
          <!--<div><img src="../../assets/UM/巡检.png">-->
            <!--<div :style="{fontSize:mySize}">管廊本体监测</div>-->
          <!--</div>-->
        <!--</Button>-->
        <Row>
          <Col span="12">
          <!--<Button class=" btn-guanxian renYuandinwei"-->
                  <!--@click="goToMoudle({ path:'/UM/PipelineSupervise'})">-->
            <!--<div><img src="../../assets/UM/管线.png">-->
              <!--<div :style="{fontSize:mySize}">管线本体监测</div>-->
            <!--</div>-->
          <!--</Button>-->
          <Button class=" btn-guanxian renYuandinwei"
                  @click="goToMoudle({ path:'/UM/TunnelEnvironment'})">
            <div><img src="../../assets/UM/巡检.png">
              <div :style="{fontSize:mySize}">管廊环境监测</div>
            </div>
          </Button>
          </Col>
          <Col span="12">
          <Button type="info" class="renYuandinwei"
                  @click="goToMoudle({ path:'/UM/TunnelSafety'})">
            <div><img src="../../assets/UM/巡检.png">
              <div :style="{fontSize:mySize}">管廊安防监测</div>
            </div>
          </Button>
          </Col>
        </Row>
        </Col>
        <Col span="3">
        <Button class="btn-quexian renYuandinwei" @click="goToMoudle({path:'/UM/tunnelDocuments/list'})">
          <div><img :width="imgWidth" src="../../assets/UM/缺陷管理.png">
            <div :style="{fontSize:mySize}">资料管理</div>
          </div>
        </Button>
        </Col>
      </Row>
      <Row type="flex" justify="center" align="top" class="code-row-bg">
        <Col span="6">
        <Button class=" btn-nenghao yjGuanLi"
                style="width:24.2vw;"
                @click="goToMoudle({ path:'/UM/TunnelEnergy/homepage'})">
          <div><img src="../../assets/UM/能耗.png">
            <div :style="{fontSize:mySize}">管廊能耗管理</div>
          </div>
        </Button>
        <Button class=" btn-tonxing ywGuanli"
                @click="goToMoudle({ path:'/UM/tunnelCustomer/list'})">
          <div><img src="../../assets/UM/用户.png">
            <div :style="{fontSize:mySize}">管廊客户管理</div>
          </div>
        </Button>
        <Button class="btn-jiqiren  ywGuanli"
                @click="goToMoudle({ path:'/UM/tunnelCustomer/list'})">
          <div><img src="../../assets/UM/用户.png">
            <div :style="{fontSize:mySize}">入廊申请管理</div>
          </div>
        </Button>
        <Button class=" btn-yonhu ywGuanli"
                @click="goToMoudle({ path:'/UM/tunnelCustomer/list'})">
          <div><img src="../../assets/UM/用户.png">
            <div :style="{fontSize:mySize}">管廊合同管理</div>
          </div>
        </Button>
        <Button type="info" class="ywGuanli"
                @click="goToMoudle({path: '/UM/OperatingSpace/tunnel/1'})">
          <div><img src="../../assets/UM/空间.png">
            <div :style="{fontSize:mySize}">管廊空间管理</div>
          </div>
        </Button>
        </Col>
        <Col span="6">
        <Button  type="success"  style="width:24.2vw;"
                class="yjGuanLi"  @click="goToMoudle({ path:'/UM/DataAnalysis/QueryData'})">
          <div><img src="../../assets/UM/数据.png">
            <div :style="{fontSize:smSize}">数据分析</div>
          </div>
        </Button>
        <!--<Button  type="error" class="yjGuanLi">-->
          <!--<div><img src="../../assets/UM/消防.png">-->
            <!--<div :style="{fontSize:smSize}">消防系统</div>-->
          <!--</div>-->
        <!--</Button>-->
        <Button  type="error" class="renYuandinwei" @click="goToMoudle({ path:'/UM/PersonnelPosition/tunnel'})">
        <div><img src="../../assets/UM/消防.png">
        <div :style="{fontSize:smSize}">人员定位</div>
        </div>
        </Button>
        <!--<Button class=" btn-guanxian  yjGuanLi">-->
          <!--<div><img src="../../assets/UM/通信.png">-->
            <!--<div :style="{fontSize:smSize}">通信系统</div>-->
          <!--</div>-->
        <!--</Button>-->
        <!--<Button class=" btn-jiqiren yjGuanLi" @click="goToMoudle({ path:'/UM/RobotMonitoring/query'})">-->
          <!--<div><img class="img" src="../../assets/UM/机器人.png">-->
            <!--<div :style="{fontSize:mySize}">机器人监控系统</div>-->
          <!--</div>-->
        <!--</Button>-->
        <Button class="renYuandinwei btn-shiping" @click="goToMoudle({path: '/UM/VideoMonitoring/details/1'})">
          <div><img class="img" src="../../assets/UM/摄像机.png">
            <div :style="{fontSize:mySize}">视频监控系统</div>
          </div>
        </Button>
        </Col>
        <Col span="3">
        <Button class=" btn-xunjian yjGuanLi" @click="goToMoudle({ path:'/UM/relatedUnits/query'})">
          <div><img src="../../assets/UM/巡检.png">
            <div :style="{fontSize:mySize}">基础信息管理</div>
          </div>
        </Button>
        <Button  type="warning" class="ywGuanli " @click="goToMoudle({ path:'/UM/plans/detial/FirePlanProcess'})">
          <div><img :width="imgWidth" src="../../assets/UM/检修.png">
            <div :style="{fontSize:mySize}">预案管理</div>
          </div>
        </Button>
        <!--<Button class="ywGuanli " type="primary">-->
          <!--<div><img :width="imgWidth" src="../../assets/UM/设备台账.png">-->
            <!--<div :style="{fontSize:mySize}">灾害管理</div>-->
          <!--</div>-->
        <!--</Button>-->
        <Button class="ywGuanli " type="primary"  @click="goToMoudle({path:'/UM/operationLog/query'})">
          <div><img :width="imgWidth" src="../../assets/UM/设备台账.png">
            <div :style="{fontSize:mySize}">日志管理</div>
          </div>
        </Button>
        </Col>
      </Row>

    </Row>
  </div>
</template>

<script>
  import {getFormatTime} from '../../scripts/DateFormat.js';
  import { LogoutService } from '../../services/logoutService';

  export default {
    name: "UmMain",
    data() {
      return {
        fullHeight: window.innerHeight,
        nowFormat: null,
        titleName: "综合管廊统一管理平台",
        mySize: "20px",
        smSize: "16px",
        imgWidth: "52px",
      }
    },
    methods: {
      goToMoudle: function (path) {
        this.$router.push(path);
      },
      logout: function () {
        var _this = this;
        this.$Modal.confirm({
          title: '注销',
          content: '<p>确认退出吗?</p>',
          onOk: () => {
            LogoutService.UMLogout().then(
              result=>{
                sessionStorage.removeItem('UMUser');
                _this.$router.push('/UMlogin');
              },
              error=>{
                _this.$Message.error(msg);
              })
            // this.axios.get('/logout').then(result => {
            //   let {msg, code, data} = result.data;
            //   if (code !== "200") {
            //     this.$Message.error(msg);
            //   } else {
            //     sessionStorage.removeItem('UMUser');
            //     _this.$router.push('/UMlogin');
            //   }
            // });
          },
          onCancel: () => {
            // this.$Message.info('Clicked cancel');
          }
        });
      },

      updateTime() {
        this.nowFormat = getFormatTime();
        this.fullHeight = window.innerHeight;
        if (window.innerWidth <= 1400) {
          this.mySize = "10px";
          this.smSize = "10px";
          this.imgWidth = "32%";
        }
        else {
          this.mySize = "20px";
          this.smSize = "16px";
          this.imgWidth = "52px";
        }
      },
    },
    mounted() {
      var _this = this;
      _this.updateTime();
      setInterval(_this.updateTime, 1000);
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer);//在vue实例销毁钱，清除我们的定时器
      }
    }

  }

</script>


<style scoped>
  .ywGuanli {
    width: 12vw;
    height: 13.8vh;
    margin-top: 0.5vh;
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }
  .renYuandinwei {
    height: 28vh;
    width: 12vw;
    margin-top: 0.5vh;
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }

  .zhJiankon {
    height: 27.2vh;
    width: 12vw;
    margin-top: 0.5vh;
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }

  .yyGuanLi {
    height: 13.8vh;
    width: 24.2vw;
    margin-top: 1vh;
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }

  .yjGuanLi {
    width: 12vw;
    height: 14vh;
    margin-top: 1.5vh;
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }

  .main {
    background-image: url("../../assets/UM/UMMainbg.png");
    color: #fff;
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
    -webkit-background-size: 100% 100%;
  }

  .Icon {
    height: 55px;
    width: 60px;
    background-image: url("../../assets/UM/Maxview.png");
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
    -webkit-background-size: 100% 100%;
    float: left;
  }

  .topOpacity {
    background-color: rgba(0, 0, 0, 0.2);
  }

  .btn-xunjian {
    color: #fff;
    background-color: #DF5631;
    border-color: #d58512;
    border-width: thin;
  }

  .btn-xunjian:focus, .btn-xunjian:hover {
    color: #fff;
    background-color: #CB500A;
    background-position: 0 -15px;
  }

  .btn-xunjian:active {
    color: #fff;
    background-color: #D83C38;
    border-color: #d58512;
  }

  .btn-quexian {
    color: #fff;
    background-color: #753BE5;
    border-color: #8e6ae5;
    border-width: thin;
  }

  .btn-quexian:focus, .btn-quexian:hover {
    color: #fff;
    background-color: #715FC8;
    background-position: 0 -15px;
  }

  .btn-quexian:active {
    color: #fff;
    background-color: #7123B9;
    border-color: #8e6ae5;
  }

  .btn-yonhu {
    color: #fff;
    background-color: #D500D1;
    border-color: #D50076;
    border-width: thin;
  }

  .btn-yonhu:focus, .btn-yonhu:hover {
    color: #fff;
    background-color: #EF00DF;
    background-position: 0 -15px;
  }

  .btn-yonhu:active {
    color: #fff;
    background-color: #B70071;
    border-color: #D50076;
  }

  .btn-nenghao {
    color: #fff;
    background-color: #323AED;
    border-color: #3A20ED;
    border-width: thin;
  }

  .btn-nenghao:focus, .btn-nenghao:hover {
    color: #fff;
    background-color: #3A1AC7;
    background-position: 0 -15px;
  }

  .btn-nenghao:active {
    color: #fff;
    background-color: #3A5AFF;
    border-color: #3A20ED;
  }

  .btn-tonxing {
    color: #fff;
    background-color: #D064C2;
    border-color: #D02CC2;
    border-width: thin;
  }

  .btn-tonxing:focus, .btn-tonxing:hover {
    color: #fff;
    background-color: #FA4586;
    background-position: 0 -15px;
  }

  .btn-tonxing:active {
    color: #fff;
    background-color: #D000B7;
    border-color: #D02CC2;
  }

  .btn-shiping {
    color: #fff;
    background-color: #F88252;
    border-color: #F83F58;
    border-width: thin;
  }

  .btn-shiping:focus, .btn-shiping:hover {
    color: #fff;
    background-color: #F83F52;
    background-position: 0 -15px;
  }

  .btn-shiping:active {
    color: #fff;
    background-color: #F80B6B;
    border-color: #F83F58;
  }

  .btn-jiqiren {
    color: #fff;
    background-color: #E5B23B;
    border-color: #E57F3B;
    border-width: thin;
  }

  .btn-jiqiren:focus, .btn-jiqiren:hover {
    color: #fff;
    background-color: #aeda3b;
    background-position: 0 -15px;
  }

  .btn-jiqiren:active {
    color: #fff;
    background-color: #AE993B;
    border-color: #E57F3B;
  }

  .btn-guanxian {
    color: #fff;
    background-color: #089FB2;
    border-color: #519FB2;
    border-width: thin;
  }

  .btn-guanxian:focus, .btn-guanxian:hover {
    color: #fff;
    background-color: #25C8B2;
    background-position: 0 -15px;
  }

  .btn-guanxian:active {
    color: #fff;
    background-color: #258486;
    border-color: #519FB2;
  }

</style>
