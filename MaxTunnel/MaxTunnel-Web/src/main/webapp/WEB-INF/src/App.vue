<template>
  <div id="app">
    <!-- <transition name="fade" mode="out-in"> -->
      <!-- <router-view></router-view> -->
      <keep-alive>
        <router-view v-if="$route.meta.keepAlive"></router-view>
      </keep-alive>
      <router-view v-if="!$route.meta.keepAlive"></router-view>
    <!-- </transition> -->
  </div>
</template>
<script>
export default {
  name: 'app',
}
</script>

<style scoped>
</style>
