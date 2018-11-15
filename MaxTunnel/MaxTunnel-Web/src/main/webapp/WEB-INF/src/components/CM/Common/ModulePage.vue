<template>
  <div class="layout">
    <Layout>
      <div class="layout-logo"></div>
      <Header class="layout-header-bar">
        <h1 style="color: #eeeeee;margin-left: 32px;">{{title}}</h1>
      </Header>
    <!-- </Layout> -->
    <!-- <div class="layout"> -->
      <Layout class="coment">
        <Sider :width="screenWidth*0.14+1" collapsible :collapsed-width="78" v-model="isCollapsed"
               :style="{background: '#fff'}">
          <Menu  theme="light" width="auto" accordion active-name="1" :open-names="['mainMenu']">
            <Submenu name="mainMenu">
              <template slot="title">
                <Icon type="ios-navigate"></Icon>
                后台管理
              </template>
              <div v-for="(item,index) in leftTree" :key="item.key" style="text-align: center">
                <!--二级菜单-->
                <Submenu :name="item.id" v-if="item.childNode && item.childNode.length>0">
                  <template slot="title"  @click="chooseMenu(item.name)">
                    <Icon :type="item.typeName" size="50" color="#052752" style="display: block"></Icon>
                    <div style="font-size: 14px;display: inline">{{ item.name}}</div>
                  </template>
                  <MenuItem v-for="(child,childIndex) in item.childNode" :name="childIndex+'child'" :key="child.id"
                            @click.native="goToMoudle({ path:child.url },child.name)">
                    <span> {{child.name}}</span>
                  </MenuItem>
                </Submenu>
                <!--一级菜单-->
                <MenuItem :name="index" v-else @click.native="goToMoudle({ path:item.url},item.name)">
                  <Icon type="ios-paper" size="50"></Icon>
                  <div style="font-size: 14px;"> {{item.name}}</div>
                </MenuItem>
              </div>
            </Submenu>
          </Menu>
          <div slot="trigger"></div>
        </Sider>
        <Layout>
          <!-- <div>管廊管理>区域管理</div> -->
          <Breadcrumb :style="{padding: '24px 20px',background: '#fff'}">
              <BreadcrumbItem>后台管理</BreadcrumbItem>
              <BreadcrumbItem>{{firstFrade}}</BreadcrumbItem>
              <BreadcrumbItem>{{secondGrade}}</BreadcrumbItem>
          </Breadcrumb>
          <Content :style="{margin: '20px', background: '#fff', minHeight: '220px',padding: '5px;'}">
            <router-view></router-view>
          </Content>
        </Layout>
      </Layout>
      <!--<Footer class="layout-footer-center">2009-2018 &copy; Bandweaver</Footer>-->
    <!-- </div> -->
    </Layout>
  </div>

</template>

<script>
  export default {
    name: "mudulePage",
    props: {
      moduleName: {
        default: "后台管理"
      },
      leftTree: {
        default: [
          {
            id: 1,
            name: "管廊1",
            url: "",
            childNode: [
              {
                id: 11,
                name: "管廊11",
                url: "",
              },
              {
                id: 12,
                name: "管廊12",
                url: "",
              }
            ]
          },
          {
            id: 2,
            name: "管廊2",
            url: "",
          }
        ]
      },
      selected: {
        type: Array,
        default: function () {
          return [-1, -1]
        }
      }
    },
    data() {
      return {
        isCollapsed: false,
        screenWidth: 1200,
        title: "综合管廊后台管理平台",
        secondGrade: null,
        firstFrade: null
      };
    },
    computed: {
      menuitemClasses: function () {
        return [
          'menu-item',
          this.isCollapsed ? 'collapsed-menu' : ''
        ]
      }
    },
    mounted() {
      this.screenWidth = document.body.clientWidth;
    },
    computed: {
      menuitemClasses: function () {
        return ["menu-item", this.isCollapsed ? "collapsed-menu" : ""];
      },
      selectedActive: function () {
        return this.selected
      }
    },
    methods: {
      goToMoudle(path,name) {
        this.$router.push(path);
        this.$emit('childByValue', path)
        this.secondGrade = name
        var arr = document.getElementsByClassName('ivu-menu-opened')[1].innerText.split(/\s+/)
        this.firstFrade = arr[0]
      },
      //设置告警面板中分页按钮的显隐
      changestatu() {
        this.showPage = !this.showPage;
      },
      //点击告警中的分页按钮
      alarmDataChangePage(index) {
        this.page.current = index;
      },
      //获取告警分页数据
      getAlatmPageData() {
        let pram = {};
        let _this = this;
        pram.pagesize = 4;
        pram.current = this.page.current;
        this.post("").then(result => {
          let {code, data} = result.data;
          if (code == 200) {
            _this.alarmData = data;
            _this.page.total = data.total;
          }
        })
      },

      //点击告警详情按钮
      showAlarmDetial(index) {
        this.alarmModal.alarmData = this.alarmData[index];
        this.alarmModal.show.state = true;
        // this.post().then(result=>{
        //
        // });

      },

      //确认告警
      confirmAlarm(index) {
        console.log("this alarm is confirmed!");
      },

      warning() {
        this.$Notice.warning({
          title: '告警提示',
          desc: '告警的概要信息. '
        });
      },
    },
    components: {},
  };
</script>

<style scoped>
  .ivu-menu-item {
    border-radius: 10px;
    font-size: 18px;
    /* background-color: #4f635f87; */
    margin: 10px 0px;
    line-height: 35px;
  }
  .ivu-menu >>> .ivu-menu-submenu-has-parent-submenu{
  border-radius: 10px;
  border: 1px solid #f9fff3
}
  .ivu-menu >>> .ivu-menu-item:hover {
    color: #fbfbfb !important;
    background-color: #5c85c2;
    margin: 10px 20px;
    border-radius: 20px;
  }
  .coment {
    height: calc(100vh - 74px);
  }

  .layout-logo {
    width: 60px;
    height: 60px;
    border-radius: 3px;
    position: absolute;
    background-image: url("../../../assets/UM/Maxview.png");
    float: left;
    top: 0;
    left: 10px;
  }

  .layout {
    border: 1px solid #d7dde4;
    background: #e0edf12e;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }

  .layout-header-bar {
    background: #fff;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
    background: #052752;
  }

  .menu-item span {
    display: inline-block;
    overflow: hidden;
    width: 160px;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width .2s ease .2s;
  }

  .menu-item i {
    transform: translateX(0px);
    transition: font-size .2s ease, transform .2s ease;
    vertical-align: middle;
    font-size: 16px;
  }

  .collapsed-menu span {
    width: 0px;
    transition: width .2s ease;
  }

  .collapsed-menu i {
    transform: translateX(5px);
    transition: font-size .2s ease .2s, transform .2s ease .2s;
    vertical-align: middle;
    font-size: 22px;
  }

  .ivu-menu-dark.ivu-menu-vertical .ivu-menu-opened >>> .ivu-menu-submenu-title {
    /* background: #0a756187 !important; */
    background: #052752 !important;
  }

  .ivu-menu >>> div:hover, .ivu-menu-dark.ivu-menu-vertical .ivu-menu-item:hover {
    color: #052752 !important;
    /* background-color: #66ccee; */
  }
  .ivu-menu-dark .ivu-menu-dark.ivu-menu-vertical .ivu-menu-opened .ivu-menu-submenu-title {
    background: #105f4fb5;
  }
  .ivu-menu-vertical .ivu-menu-item, .ivu-menu-vertical .ivu-menu-submenu-title{
    padding: 0px;
    padding-left: 0px !important;
  }
  .ivu-menu-item.ivu-menu-item-active.ivu-menu-item-selected{
    margin: 10px 20px;
    border-radius: 20px;
    line-height: 35px;
    background: #07418a;
    color: #ffffff;
    padding-left: 0px !important
  }
  .ivu-menu-submenu.ivu-menu-opened.ivu-menu-submenu-has-parent-submenu .ivu-menu-submenu-title{
    color: #052752 !important;
  }
  .ivu-menu-submenu-title{
    padding-left: 0px !important;
  }
  /*去掉footer*/
  .v-transfer-dom{
    padding: 0px !important
  }
  .ivu-loading-bar{
    height: 0px !important;
  }
</style>

