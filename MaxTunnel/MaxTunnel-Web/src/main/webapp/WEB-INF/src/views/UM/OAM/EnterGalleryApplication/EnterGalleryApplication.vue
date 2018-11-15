<template>
  <div>
    <ModulePage v-bind="enterGalleryApplication"></ModulePage>
  </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
import { TunnelService } from '../../../../services/tunnelService'
export default {
  name: "UM_main",
  components:{ModulePage},
  data() {
    return {
        enterGalleryApplication:{
          moduleName:"入廊申请管理",
          leftTree:[],
          selected:[0,-1]
        },
        treeNodeJumpUrl:"/UM/enterGalleryApplication/query",
    };
  },
  created(){
    this.enterGalleryApplication.leftTree = [];
    let _this = this
    TunnelService.getTunnels().then(
          (result)=>{
              result.forEach(a=>{
                  let temp={};
                  temp.id=a.id;
                  temp.name=a.name;
                  temp.url="/UM/enterGalleryApplication/query/"+a.id;
                  _this.enterGalleryApplication.leftTree.push(temp);
              })
              let element = {};
              element.id = result.length;
              element.name = '制定',
              element.url= '/UM/enterGalleryApplication/add'
              this.enterGalleryApplication.leftTree.push(element)
          },
          (error)=>{
              console.log(error)
      })
    

    // this.axios.get("/tunnels").then(result => {
    //   let {code, data} = result.data;
    //   var _this=this;
    //   if (code == 200) {
    //     data.forEach(a=>{
    //       let temp={};
    //       temp.id=a.id;
    //       temp.name=a.name;
    //       temp.url="/UM/enterGalleryApplication/query/"+a.id;
    //       _this.enterGalleryApplication.leftTree.push(temp);
    //     })
    //   }
    // let element = {};
    //     element.id = data.length;
    //     element.name = '制定',
    //     element.url= '/UM/enterGalleryApplication/add'
    //     this.enterGalleryApplication.leftTree.push(element)
    // });
  },
};
</script>