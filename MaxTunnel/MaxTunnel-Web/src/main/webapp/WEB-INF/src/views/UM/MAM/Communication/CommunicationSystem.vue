<template>
  <ModulePage v-bind="curModule"></ModulePage>
</template>

<script>
  import ModulePage from '../../../../components/Common/ModulePage'

  export default {
    name: "communication",
    data() {
      return {
        curModule: {
          moduleName: "通讯系统",
          leftTree: [],
          selected:[0,0]
        },
      }
    },
    components: {
      ModulePage
    },
    created() {
      this.curModule.leftTree = [
        { 
            id: 1,
            name: '通话',
            childNode: [
                {
                    id: 1,
                    name: '来电',
                    url: '/UM/Communication/answers'
                },
                {
                    id: 2,
                    name: '去电',
                    url: '/UM/Communication/diales'
                }
            ]
        },
        { 
            id: 2,
            name: '分机',
            childNode: [
                {
                    id: 1,
                    name: '分机列表',
                    url: '/UM/Communication/extensions'
                }
            ] 
        }
      ];

    },
    // beforeRouteUpdate (to, from, next) {
    //   next()
    // }, 
    mounted() {
    },
    methods: {
      goToMoudle(path) {
        this.$router.push(path);
      }
    }
  };
</script>
