<template>
    <div class="my-tabs">
        <div class="tabs-bar">
            <div class="tabs-bar-nav">
                <div class="tabs-tab" v-for="tab in tabList" @click="changeTab">
                    <span class="active"
                    :class="[tabIndex == tab.index ? 'tabs-active' : '']">{{ tab.name }}</span>
                </div>
            </div>
        </div>
        <div class="tabs-content">
            <slot></slot>
        </div>
    </div>
</template>
<script>
export default {
    name:'tabs',
    props:{
        tabList: Array,
        tabIndex: Number
    },
    data () {
        return {
        }
    },
    methods: {
        changeTab(e) {
            let targetInnerHtml = e.target.innerHTML;
            
            let [ tabs ] = this.tabList.filter( tab => tab.name === targetInnerHtml );

            this.$emit('changeTab', tabs)
        }
    }
}
</script>
<style scoped>
.my-tabs {
  font-size: 14px;
  color: #444;
}
.tabs-bar {
   padding: 5px 0;
 }
 .tabs-bar-nav {
    position: absolute;
    width: 2%;
    right: 0;
    height: 88%;
 }
 .tabs-tab {
    box-sizing: border-box;
    height: 50%;
    text-align: center;
    padding: 300% 0 100% 0;
    padding-left:4%; 
    cursor: pointer;
 }
 .active {
     display: inline-block;
 }
 .tabs-active {
    background: rgb(29, 95, 135);
    color: #fff;
 }
 .tabs-content {
   padding-right: 4%;
 }
/* 大屏幕（显示器，大于等于 1920px） */
@media (min-width: 1921px) {
    .tabs-active{
        font-size:2rem;
    }
    .active{
        font-size:2rem;
        padding: 3% 6%;
        border-radius: 10%;
    }
}
/* 小屏幕（显示器，小于等于 1920px） */
@media (max-width: 1920px) {
    .active{
        /*font-size: 12px;*/
        padding: 2% 1%;
        border-radius: 7%;
    }
}
</style>

