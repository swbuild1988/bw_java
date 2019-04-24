<template>
    <div id="app">
        <keep-alive>
            <div v-if="$route.meta.keepAlive">
                <router-view v-if="isRouterAlive"></router-view>
            </div>
        </keep-alive>
        <div v-if="!$route.meta.keepAlive">
            <router-view v-if="isRouterAlive"></router-view>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'app',
        provide() {
            return {
                reload: this.reload
            }
        },
        data() {
            return {
                isRouterAlive: true
            }
        },
        methods: {
            reload() {
                this.isRouterAlive = false;
                this.$nextTick(function () {
                    this.isRouterAlive = true
                })
            }
        },
        components: {},
    }
</script>

<style scoped>
</style>