<template>
  <Layout>
    <Header :style="{position: 'fixed', width: '100%',zIndex:99999,padding:'0',color:'#1b1754'}">
      <Menu mode="horizontal" active-name="1" :style="{color:'#1b1754'}">
        <div class="layout-logo"></div>
        <div class="layout-title">
          <h1><a  class="mainTitle" @click="goToMoudle({ path: '/UMMain'})">{{title}}</a></h1>
        </div>
        <div class="layout-nav">
          <div style="position: fixed;right: 20px;">
            <MenuItem v-for="(module,index) in modules" :key="module.name" :name="module.name">
              <Dropdown placement="bottom-start">
                <Button type="primary">
                  <Icon :type="module.frontIcon"></Icon>
                  {{module.name}}
                  <Icon :type="module.endIcon"></Icon>
                </Button>
                <DropdownMenu slot="list" :id="module.id">
                  <DropdownItem v-for="child in module.children"
                                :key="child.name"
                                :name="child.name" divided
                                @click.native="goToMoudle({ path: child.url})">
                    {{child.name}}
                  </DropdownItem>
                </DropdownMenu>
              </Dropdown>
            </MenuItem>
            <!-- 人物圆点 -->
            <Dropdown>
              <a href="javascript:void(0)">
                <Badge :count="countNum">
                  <Avatar :style="{background:'#f56a00'}">{{getUserName}}</Avatar>
                </Badge>
              </a>
              <DropdownMenu slot="list">
                <DropdownItem @click.native="goToMoudle({ path: '/UM/myNews/queryMyTask'})">我的消息</DropdownItem>
                <!-- <DropdownItem @click.native="goToMoudle({ path: '/UM/myApplication/query'})">我的申请</DropdownItem> -->
                <DropdownItem @click.native="goToMoudle({ path: '/UM/myTasks/query'})">我的任务</DropdownItem>
                <DropdownItem divided @click.native="logout">注销</DropdownItem>
              </DropdownMenu>
            </Dropdown>
          </div>

        </div>
      </Menu>
    </Header>
  </Layout>
</template>

<script>
import { LogoutService } from '../../services/logoutService'
import { UserService } from '../../services/userService'

  export default {
    name: "UMTop",
    data() {
      return {
        title: '综合管廊统一管理平台',
        modules: [
          {
            id: 1,
            name: '运维管理',
            frontIcon: 'ios-navigate',
            endIcon: 'arrow-down-b',
            children: [
              {
                name: '巡检管理',
                url: '/UM/Patrol/homePage'
              },
              {
                name: '巡检任务',
                url: '/UM/patrolTask/query'
              },
              {
                name: '缺陷管理',
                url: '/UM/defect/query'
              },
              {
                name: '维修管理',
                url: '/UM/overhaul/query'
              },
              {
                name: '设备管理',
                url: '/UM/equipment/homePage'
              },
              {
                name: '虚拟巡检',
                url: '/UM/virtualInspect'
              }
            ]
          },
          {
            id: 2,
            name: '运营管理',
            frontIcon: 'ios-keypad',
            endIcon: 'arrow-down-b',
            children: [
              {
                name: '管廊客户管理',
                url: '/UM/tunnelCustomer/list'
              },
              {
                name: '入廊申请管理',
                url: '/UM/enterGalleryApplication/query'
              },
              {
                name: '管廊能耗管理',
                url: '/UM/TunnelEnergy/homepage'
              },
              {
                name: '管廊空间管理',
                url: '/UM/OperatingSpace/tunnel/1'
              },
              {
                name: '管廊合同管理',
                url: '/UM/tunnelContract/list'
              },
              {
                name: '日志管理',
                url: '/UM/operationLog/query'
              },
            ]
          },
          {
            id: 3,
            name: '综合监控',
            frontIcon: 'ios-analytics',
            endIcon: 'arrow-down-b',
            children: [
              // {
              //   name: '管廊本体监测',
              //   url: '/UM/TunnelSupervise'
              // },
              {
                name: '管廊环境监测',
                url: '/UM/TunnelEnvironment'
              },
              {
                name: '管廊安防监测',
                url: '/UM/TunnelSafety'
              },
              // {
              //   name: '管线本体监测',
              //   url: '/UM/PipelineSupervise'
              // },
              // {
              //   name: '消防系统',
              //   url: ''
              // },
              // {
              //   name: '通信系统',
              //   url: ''
              // },
              {
                name: '数据分析',
                url: '/UM/DataAnalysis/QueryData'
              },
              // {
              //   name: '机器人监控系统',
              //   url: '/UM/RobotMonitoring/query'
              // },
              {
                name: '视频监控系统',
                url: '/UM/VideoMonitoring/details/1'
              },
              {
                name: '人员定位',
                url: '/UM/PersonnelPosition/tunnel'
              }
            ]
          },
          {
            id: 4,
            name: '应急管理',
            frontIcon: 'ios-paper',
            endIcon: 'arrow-down-b',
            children: [
              {
                name: '基础信息管理',
                url: '/UM/relatedUnits/query'
              },
              {
                name: '预案管理',
                url: '/UM/plans/detial/FirePlanProcess'
              },
              // {
              //   name: '调度管理',
              //   url: ''
              // },
              // {
              //   name: '灾害管理',
              //   url: '/UM/disaster/query'
              // },
            ]
          },
          {
            id: 5,
            name: '其他管理',
            frontIcon: 'document-text',
            endIcon: 'arrow-down-b',
            children: [
              {
                name: '资料管理',
                url: '/UM/tunnelDocuments/list'
              }
            ]
          }
        ],
        countNum: 1,
        getUserName: null
      }
    },
    mounted(){
      this.getCountInfoNum()
      /**.比较浪费带宽 先注释掉 方法可以使用 */
      // setInterval(this.getCountInfoNum,1000)
      this.getSessionUserName()
    },
    methods: {
      //退出登录
      logout: function () {
        var _this = this;
        this.$Modal.confirm({
          title: '注销',
          content: '<p>确认退出吗?</p>',
          onOk: () => {
            LogoutService.UMLogout().then(
              result=>{
                sessionStorage.removeItem('user');
                _this.$router.push('/UMlogin');
              },
              error=>{
                this.$Message.error(msg);
              })
            // this.axios.get('/logout').then(result => {
            //   let {msg, code, data} = result.data;
            //   if (code !== "200") {
            //     this.$Message.error(msg);
            //   } else {
            //     sessionStorage.removeItem('user');
            //     _this.$router.push('/UMlogin');
            //   }
            // });
          },
          onCancel: () => {
          }
        });
      },
      goToMoudle(path) {
        this.$router.push(path)
      },
      getCountInfoNum(){
        let _this = this
        UserService.getMessageCount().then(
          result=>{
            _this.countNum = result
          },
          error=>{
            _this.Log.info(error)
          })
        // axios.get('/users/msg/count').then(response=>{
        //   let{ code,data } = response.data
        //   if(code==200){
        //     this.countNum = data

        //   }
        // })
      },
      getSessionUserName:function () {
        if(sessionStorage.UMUerName!=null||sessionStorage.UMUerName!=undefined||sessionStorage.UMUerName!=''){
          var str =sessionStorage.UMUerName.substring(1,2)
          this.getUserName = str.substring(0,1).toUpperCase()
        }
      },
    }
  }
</script>

<style scoped>
  .ivu-layout-header,
  .ivu-menu-light {
    background: #357aa1

  }

  .layout-logo {
    width: 60px;
    height: 60px;
    border-radius: 3px;
    position: relative;
    background-image: url("../../assets/UM/Maxview.png");
    float: left;
    top: 0;
  }

  .layout-title {
    float: left;
    top: 1vh;
    margin-left: 2vw;
    color: #fff;
  }

  .layout-nav {
    width: calc(98vw - 360px);
    position: fixed;
    top:0;
  }

  .ivu-menu-horizontal {
    height: 64px;
    line-height: 64px;
  }

  .mainTitle{
    color: #fff;
  }
  .mainTitle:hover{
    color: #66ccee;
  }
</style>
