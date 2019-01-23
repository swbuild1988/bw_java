<template>
  <div class="main" :style="{height:fullHeight+'px'}">
    <!--Top标题-->
    <Row>
      <Col span="24">
      <div></div>
      <div id="top" class="topOpacity">
        <Row>
          <Col span="14">
          <span class="Icon"></span>
          <span class="titleName"> {{titleName}}</span>
      </Col>
      <Col span="6" offset="4">
      <div style="float: right;padding-top: 0px;padding-right: 5px;">
        <Dropdown divided @click.native="showAboutUs">
          <a>
            <Icon type="information-circled" color="#fff" title="关于我们" class="about"></Icon>
          </a>
        </Dropdown>
        <showAboutUs v-bind="aboutUs"></showAboutUs>
      </div>
      <div style="float: right;padding-top: 1vmin;">
        <Dropdown divided @click.native="logout">
          <a>
            <Icon type="power" color="#fff" class="power"></Icon>
          </a>
        </Dropdown>
      </div>
      </Col>
      <!--<img style="height: 2.5vh;width: 100%" :src="topBg"/>-->
    </Row>
      </div>
      </Col>
    </Row>
    <br><br><br> <br><br>
    <!--菜单内容-->
    <div class="modals">
      <Row  type="flex" justify="center" align="top" class="code-row-bg" >

        <!--运营管理-->
        <Col span="6">
        <h2 style="font-size: 2.4vmin">运营管理</h2>
        <Row class="rowTop">
          <Col span="8">
            <Button type="info" class="normalBtn"
                  @click="goToMoudle({ path:'/UM/tunnelCustomer/list'})">
              <div><img src="../../assets/UM/customer.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">管廊客户管理</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button type="info" class="normalBtn"
                  @click="goToMoudle({ path:'/UM/enterGalleryApplication/query'})">
              <div><img src="../../assets/UM/enterGallery.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">入廊申请管理</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button class="normalBtn" type="primary"
                    @click="goToMoudle({ path:'/UM/tunnelDocuments/list'})">
              <div><img src="../../assets/UM/documents.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">管廊资料管理</div>
              </div>
            </Button>
          </Col>
        </Row>
        <Row class="rowTop">
          <Col span="8">
            <Button class="normalBtn btn-quexian" type="primary"
                    @click="goToMoudle({ path:'/UM/TunnelEnergy/homepage'})">
              <div><img src="../../assets/UM/energy.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">管廊能耗管理</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button class="normalBtn" type="primary"
                    @click="goToMoudle({ path:'/UM/tunnelContract/list'})">
              <div><img src="../../assets/UM/contract.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">管廊合同管理</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button class="normalBtn" type="primary"
                    @click="goToMoudle({ path:'/UM/OperatingSpace/tunnel/1'})">
              <div><img src="../../assets/UM/空间.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">管廊空间管理</div>
              </div>
            </Button>
          </Col>
        </Row>
        <Row class="rowTop">
          <Col span="8">
            <Button class="normalBtn" type="primary"
                    @click="goToMoudle({ path:'/UM/relatedUnits/query'})">
              <div><img src="../../assets/UM/basicInfo.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">基础信息管理</div>
              </div>
            </Button>
          </Col>
        </Row>
        </Col>

        <!--监控与报警-->
        <Col span="6" offset="1">
        <h2  style="font-size: 2.4vmin">监控与报警</h2>
        <Row class="rowTop">
          <Col span="8">
            <Button type="info" class="normalBtn"
                    @click="goToMoudle({ path:'/UM/TunnelSafety'})">
              <div><img src="../../assets/UM/safe.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">管廊安防监测</div>
              </div>
            </Button>
          </Col>
          <Col span="16">
            <Button class="widthBtn btn-xunjian" @click="goToMoudle({ path:'/UM/TunnelEnvironment'})">
              <div><img src="../../assets/UM/environment.png" :width="imgWidth">
                <div  :style="{fontSize:mySize,textAlign:'center',color: '#fff'}">管廊环境监测</div>
              </div>
            </Button>
          </Col>
        </Row>
        <Row class="rowTop">
          <Col span="8">
            <Button class="normalBtn btn-guanxian" @click="goToMoudle({path: '/UM/VideoMonitoring/details/1'})">
              <div><img src="../../assets/UM/monitor.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">视频监控系统</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button type="primary" class="normalBtn"
                    @click="goToMoudle({ path:'/UM/DataAnalysis/QueryData'})">
              <div><img src="../../assets/UM/dataAnalysis.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">数据分析</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button type="primary" class="normalBtn" @click="goToMoudle({ path:'/UM/PersonnelPosition/tunnel'})">
              <div><img src="../../assets/UM/position.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">人员定位</div>
              </div>
            </Button>
          </Col>
        </Row>
        </Col>

        <!--设备实施运维-->
        <Col span="6" offset="1">
        <h2  style="font-size: 2.4vmin">设备实施运维</h2>
        <Row class="rowTop">
          <Col span="8" >
          <Button class=" btn-nenghao normalBtn"
                  style="box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);"
                  @click="goToMoudle({ path:'/UM/virtualInspect'})">
            <div><img src="../../assets/UM/virtualInspect.png" :width="imgWidth">
              <div :style="{fontSize:mySize}">虚拟巡检</div>
            </div>
          </Button>
          </Col>
          <Col span="8">
             <Button
            class=normalBtn type="primary"
            @click="goToMoudle({ path:'/UM/equipment/homePage'})">
              <div><img :width="imgWidth" src="../../assets/UM/equipments.png">
                <div :style="{fontSize:mySize}">设备管理</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
             <Button
            class=normalBtn type="primary"
            @click="goToMoudle({ path:'/UM/patrolTask/query'})">
              <div><img :width="imgWidth" src="../../assets/UM/patrolTask.png">
                <div :style="{fontSize:mySize}">巡检任务管理</div>
              </div>
            </Button>
          </Col>
          </Row>
          <Row  class="rowTop">
            <Col span="8">
               <Button
            class=normalBtn type="primary"
            @click="goToMoudle({ path:'/UM/defect/query'})">
                <div><img :width="imgWidth" src="../../assets/UM/defect.png">
                  <div :style="{fontSize:mySize}">缺陷管理</div>
                </div>
              </Button>
            </Col>
            <Col span="8">
            <Button class=" btn-yonhu normalBtn"
                    @click="goToMoudle({ path:'/UM/plans/detial/FirePlanProcess'})">
              <div><img :width="imgWidth" src="../../assets/UM/消防.png">
                <div :style="{fontSize:mySize}">预案管理</div>
              </div>
            </Button>
            </Col>
            <Col span="8">
              <Button class="normalBtn" @click="goToMoudle({ path:'/UM/patrol/homePage'})" type="primary">
                <div><img :width="imgWidth" src="../../assets/UM/巡检.png">
                  <div :style="{fontSize:mySize}">巡检管理</div>
                </div>
              </Button>
            </Col>
        </Row>
        <Row  class="rowTop">
          <Col span="8">
            <Button class="normalBtn btn-jiqiren"
                    @click="goToMoudle({ path:'/VMMain2'})">
              <div><img src="../../assets/UM/空间.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">可视化平台</div>
              </div>
            </Button>
          </Col>
          <Col span="8">
            <Button class="normalBtn" type="primary"
                    @click="goToMoudle({ path:'/UM/overhaul/query'})">
              <div><img src="../../assets/UM/检修.png" :width="imgWidth">
                <div :style="{fontSize:mySize}">检修管理</div>
              </div>
            </Button>
          </Col>
        </Row>
        </Col>

        <!--最右侧时间-->
        <Col span="3" offset="1">
        <Row style="margin-top: 16vh">
          <Col span="24">
          <p class="time"><Icon type="ios-time-outline"></Icon><span class="curTime">{{nowTime}} </span><span style="font-size: 1.8vmin;font-family: 7LED;">{{timeSection}}</span></p>
          </Col>
          <Col span="24">
          <p class="date">{{nowDate}}</p>
          </Col>
          <Col span="24" style="margin-top: 4vh">
            <span style="font-size: 2.4vmin">安全运行<span class="days">{{days.total}}</span>天</span>
          </Col>
        </Row>
        </Col>

      </Row>

    </div>

  </div>
</template>

<script>
  import {getSimpleDate} from '../../scripts/DateFormat.js';
  import {LoginService} from '../../services/loginService.js';
  import { TunnelService } from '../../services/tunnelService'
  import showAboutUs from '../../components/Common/Modal/ShowAboutUs'
  import ImageFromUrl from "../../components/Common/ImageFromUrl";
  import topBg from '../../assets/VM/footLine.png'
  export default {
    name: "UmMain",
    data() {
      return {
        topBg:topBg,
          aboutUs: {
              show: {state: false},
              company: {
                  version: "MaxTunnel-5.20",
                  name: "上海波汇科技有限公司",
                  email: "Bandweaver@.cn",
                  telephone: "12413321231",
                  address: "上海市浦东新区上科路88号豪威科技园1幢503",
              }
          },
        fullHeight: window.innerHeight,
        nowTime: null,
        nowDate:null,
        timeSection:null,
        titleName: "综合管廊智慧管理平台",
        mySize: "1.4rem",
        smSize: "16px",
        imgWidth: "52px",
        days: {
          total: null,
          safe: null
        }
      }
    },
    methods: {
      goToMoudle: function (path) {
        if(String(this.$router.history.current.fullPath).indexOf(String(path.path))<0){
          if(path.path == '/VMMain'){
            const {href} = this.$router.resolve({
              name: "可视化主页"
            });
            window.open(href, '_blank');
          } else {
            this.$router.push(path);
          }
        }
      },
        showAboutUs() {
            this.aboutUs.show.state = !this.aboutUs.show.state;
        },
        getUsData() {
            var _this = this;
            EnterGalleryService.getAboutUsData().then((result => {
                if (result) {
                    _this.aboutUs.company.version = result.version;
                    _this.aboutUs.company.email = result.email;
                    _this.aboutUs.company.telephone = result.contact;
                    _this.aboutUs.company.address = result.address;
                    _this.aboutUs.company.name = result.company;
                }
            }))

        },
        init(){
          let _this = this;
          TunnelService.getVmRunMessage().then(
              result=>{
                _this.days.total = result.total
                _this.days.safe = result.safe
              },
              error=>{
                _this.Log.info(error)
              })

        },
      logout: function () {
        var _this = this;
        this.$Modal.confirm({
          title: '注销',
          content: '<p>确认退出吗?</p>',
          onOk: () => {
            LoginService.UMLogout().then(
              result => {
                sessionStorage.removeItem('UMUser');
                _this.$router.push('/UMlogin');
              },
              error => {
                _this.$Message.error(msg);
              })
          },
          onCancel: () => {
            // this.$Message.info('Clicked cancel');
          }
        });
      },

      updateTime() {
        this.nowTime = new Date().format("hh:mm:ss");
        this.timeSection=new Date().getHours()>12?"PM":"AM";
        this.nowDate=getSimpleDate();
        this.fullHeight = window.innerHeight;
        if (window.innerWidth <= 1400) {
          this.mySize = "1.8vmin";
          this.smSize = "10px";
          this.imgWidth = "50vw";
        }
        else {
          if(window.innerWidth <= 1920){
            this.mySize = "1.5vmin";
            this.smSize = "16px";
            this.imgWidth = "50vw";
          } else {
            this.mySize = "1.5vmin";
            this.smSize = "16px";
            this.imgWidth = "100vw";
          }
        }
      },
    },
    mounted() {
      var _this = this;
      _this.updateTime();
      _this.init()
      setInterval(_this.updateTime, 1000);
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer);//在vue实例销毁钱，清除我们的定时器
      }
    },
      components: {
        ImageFromUrl,
        showAboutUs,
      },
  }
</script>


<style scoped>
  .titleName{
    width: 15vw;
    height: 4vh;
    font-size: 4vmin;
    padding-top: 0.5vmin;
    /*background-image: url("../../assets/UM/bordered.png");*/
    /*background-size: 100% 100%;*/
    /*-moz-background-size: 100% 100%;*/
    /*-webkit-background-size: 100% 100%;*/
  }
  .rowTop{
    margin-top: 0.5vh;
  }
  .time{
    font-size: 3.6vmin;
    text-align-last: justify;
  }
  .date{
    font-size: 1.8vmin;
    color: #f59e48;
    text-align-last: justify;
  }
  .normalBtn {
    width: 7vw;
    height: 7vw;
    margin-top: 0.2vmin;
    background-color: rgba(255,255,255,0.5);
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }
  .normalBtn:focus, .normalBtn:hover{
    background-color: rgba(255,255,255,0.7);
  }
  .normalBtn:active{
    background-color: rgba(255,255,255,0.86);
  }
  .widthBtn{
    width: 14.6vw;
    height: 7vw;
    margin-top: 0.2vmin;
    box-shadow: 3px 3px 20px rgba(6, 6, 18, 0.7);
  }
  .days{
    margin-left: 0.6vh;
    margin-right: 0.6vh;
    color: #99ff23;
    font-family: UnidreamLED;
    font-size: 4vmin;
  }
  .curTime{
    font-family: UnidreamLED;
    margin-left: 1vmin;
    font-size: 4vmin;
  }
  .main {
    background-image: url("../../assets/UM/UMMainbg2.png");
    color: #fff;
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
    -webkit-background-size: 100% 100%;
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
  .Icon {
    height: 4vh;
    width: 10vw;
    background-image: url("../../assets/VM/MaxviewLogo.png");
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
    -webkit-background-size: 100% 100%;
    float: left;
  }
  .power{
    font-size: 4vmin;
  }

  .about{
    font-size: 2vmin;
  }

  @media (max-width: 1921px){
    .modals{
      width: 90vw;
      margin-left: 5vw;
      margin-top: 4vh;
    }

  }
  @media (min-width: 1921px){
    .modals{
      width: 90vw;
      margin-left: 5vw;
      margin-top: 10vh;
    }
  }
</style>
