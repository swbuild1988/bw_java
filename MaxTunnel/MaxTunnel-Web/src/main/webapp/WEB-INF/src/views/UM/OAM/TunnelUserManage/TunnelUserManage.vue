<template>
  <div class="layout">
    <Layout :style="{minHeight: '93vh'}">
      <Sider :width="screenWidth*0.14" collapsible :collapsed-width="78" v-model="isCollapsed">
        <Menu active-name="1-1" theme="dark" width="auto" :class="menuitemClasses">
         <Submenu name="1">
            <template slot="title">
              <Icon type="ios-navigate"></Icon>
              管廊用户管理
            </template>
            <MenuItem name="1-1" @click.native="goToMoudle({ path:'/UM/TunnelUser/user'})">管廊内用户</MenuItem>
            <MenuItem name="1-2" @click.native="goToMoudle({ path:'/UM/TunnelUser/query'})">列表</MenuItem>
            <MenuItem name="1-4" @click.native="goToMoudle({ path:'/UM/TunnelUser/details'})">详情</MenuItem>
          </Submenu>
        </Menu>
      </Sider>
      <Layout>
        <Content :style="{ padding: '24px', background: '#fff' }" style="overflow-y: auto;overflow-x: hidden;height: 200px;">
          <router-view></router-view>
        </Content>
        <Footer class="layout-footer-center">2011-2016 &copy; TalkingData</Footer>
      </Layout>

    </Layout>
  </div>
</template>

<script>
export default {
  name: "UM_main",
  data() {
    return {
      userName: "admin",
      isCollapsed: false,
      screenWidth: 1200,
      screenHeight: 900,
    };
  },
  mounted() {
    this.screenWidth = document.body.clientWidth;
    this.screenHeight = document.body.clientHeight;
    this.goToMoudle({ path:'/UM/TunnelUser/user' });
  },
  computed: {
    menuitemClasses: function() {
      return ["menu-item", this.isCollapsed ? "collapsed-menu" : ""];
    }
  },
  methods: {
    goToMoudle(path) {
      this.$router.push(path);
    }
  }
};
</script>

<style scoped>
.layout {
    background: #f5f7f9;
    position: relative;
    border-radius: 2px;
    overflow: hidden;
  }

  .menu-item span {
    display: inline-block;
    overflow: hidden;
    width: 100px;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width 0.2s ease 0.2s;
  }

  .menu-item i {
    transform: translateX(0px);
    transition: font-size 0.2s ease, transform 0.2s ease;
    vertical-align: middle;
    font-size: 16px;
  }

  .collapsed-menu span {
    width: 0px;
    transition: width 0.2s ease;
  }

  .collapsed-menu i {
    transform: translateX(5px);
    transition: font-size 0.2s ease 0.2s, transform 0.2s ease 0.2s;
    vertical-align: middle;
    font-size: 22px;
  }

  .layout-footer-center {
    text-align: center;
    background-color: #e2f0ff;
  }

  h1 {
    margin: 2% 1% 0 0;
    width: 8%;
    float: left;
  }
</style>
