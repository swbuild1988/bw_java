<template>
  <div @touchmove.prevent>
    <h1 class="title">入廊管线详情</h1>
    <Row>
        <Col span="12">
          <div class="infos">
            <Card v-for="(item,index) in linesInfo" :key="index" class="lineInfo">
              <h3 class="name">{{ item.cableName }}</h3>
              <p>管线长度：{{ item.cableLength }}</p>
              <p>管线状态：{{ item.cableStatusName }}</p>
              <p>管线位置：{{ item.cableLocation }}</p>
              <p>客户名称：{{ item.contract.customer.company.name }}</p>
              <p>联系人：{{ item.contract.customer.contact }}</p>
              <p>联系电话：{{ item.contract.customer.tel }}</p>
            </Card>
          </div>
        </Col>
        <Col span="12"><img class="bim" src="../../../../assets/UM/bim.png">
        </Col>
    </Row>
  </div>
</template>

<script>
  export default {
    name: "operatingSpaceDetail",
    data() {
      return {
        sectionId: '',
        linesInfo:''
      }
    },
    // watch: {
    //   '$route': function () {
    //     // $route发生变化时再次赋值planId
    //     this.tunnelId = this.$route.params.id;
    //     this.tunnelList.forEach(a => {
    //       if (a.id == this.tunnelId) {
    //         this.curName = a.name;
    //         this.init();
    //         this.queryTable();
    //       }
    //     });
    //   }
    // },
    mounted() {
      this.sectionId = this.$route.params.id;
      console.log(this.sectionId);
      this.getData();
      // 获取所有的管廊
      // this.axios.get("/tunnels ").then(result => {
      //   let {code, data} = result.data;
      //   if (code == 200) {
      //     this.tunnelList = data;
      //     this.tunnelList.forEach(a => {
      //       if (a.id == this.tunnelId) {
      //         this.curName = a.name;
      //         this.init();
      //       }
      //     });
      //   }
      // });
      // this.queryTable();

    },
    methods: {
      getData() {
        this.axios.get('tunnels/areas/sections/' +this.sectionId + '/cables').then(res =>{
          let {code,data} = res.data;
          if(code == 200){
            this.linesInfo = data;
          }
        })
      }
    }
  }
</script>

<style scoped>
.body{
  overflow: hidden;
}
  .title {
    margin: 10px;
  }
  .infos{
    width:400px;
    color:white;
    /*overflow-y:scroll;*/
    margin:10px;
  }
  .lineInfo{
    background-color: rgb(204, 153, 102);
  }
  .name{
    margin:10px;
  }
  .bim{
    margin-top:20px;
    width:500px;
    height:520px;
  }
</style>
