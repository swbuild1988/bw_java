<style scoped>
  .layout {
    border: 0px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;

  }

  .layout-footer-center {
    text-align: center;
  }
</style>

<template>
  <div class="layout">
    <u-m-top></u-m-top>

    <div :style="{marginTop:'65px',}">
      <transition name="fade" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>

  </div>
</template>

<script>
import UMTop from '../../components/UM/UMTop'

export default {
  name: "home",
  components: {
    UMTop
  }
}
</script>
