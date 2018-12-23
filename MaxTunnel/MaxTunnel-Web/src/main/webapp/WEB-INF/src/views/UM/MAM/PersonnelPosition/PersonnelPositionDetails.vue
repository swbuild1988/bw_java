<template>
    <div class="whole">
        <Row class="conditions">
           <!--  <Col span="8" offset="1">
                监测仓:
                <Select style="width:20vw;" v-model="query.storeId">
                    <Option value="">全部</Option>
                    <Option v-for="item in selectList.stores" :value="item.id" :key="item.name">{{ item.name }}
                    </Option>
                </Select>
            </Col>
            <Col span="10" offset="2">
                区域:
                <Select style="width:20vw;" v-model="query.areaId">
                    <Option value="">全部</Option>
                    <Option v-for="item in selectList.areas" :value="item.id" :key="item.name">{{ item.name }}</Option>
                </Select>
            </Col>
            <Col span="3">
                <Button type="primary" icon="ios-search">查询</Button>
            </Col> -->
            <Col span="23" offset="1">
              <h1>入廊人员信息</h1>
            </Col>
        </Row>
        <Row class="list">
            <Col span="15" class="view" id="GISbox">
                <!-- <sm-viewer id="personnelPositionSMViewer" ref="smViewer"></sm-viewer> -->
                <!-- <sm-viewer id="personnelPositionSMViewer" :cameraPosition="VMConfig.CAMERA"  :personnelPosition="personnelPosition" @onload="onload" style="height:74vh">
                </sm-viewer> -->
                <TestSmViewer :cameraPosition="VMConfig.CAMERA" ref="smViewer"></TestSmViewer>
            </Col>
            <Col span="8" offset="1">
                <Collapse v-model="curPerson" accordion v-if="!none">
                    <Panel v-for="person in personnelInfo" :name="person.id+''" :key="person.id">
                        {{ person.username }}
                        <div slot="content" class="content">
                            <img src="../../../../assets/UM/能耗.png" width="100" height="100">
                            <ul class="perList">
                                <li>所属公司：{{ person.companyName }}</li>
                                <li>手机号：{{ person.phoneNum }}</li>
                                <li>身份证号：{{ person.idCard }}</li>
                            </ul>
                            <Button type="primary" class="call">通话</Button>
                        </div>
                    </Panel>
                </Collapse>
                <h2 v-if="none">暂无入廊人员或未分配设备</h2>
            </Col>
        </Row>
    </div>
</template>
<script>
  import { personnelPositionService } from '../../../../services/personnelPositionService'
//   import SmViewer from "../../../../components/Common/3D/3DViewer"
  import TestSmViewer from "../../../../components/Common/3D/Test3DViewer";
import { TunnelService } from '../../../../services/tunnelService';
//   import { setViewAngle ,bubble ,doSqlQuery , addBillboard,processFailed,getEntitySet,switchShowEntity,getEntityProperty  } from '../../../../scripts/commonFun'

  export default {
      data() {
          return {
            tunnelId: null,
            selectList: {
              stores: null,
              areas: null
            },
            query: {
              storeId: null,
              areaId: null
            },
            curPerson: '1',
            personnelInfo: [
              // {
              //   id: '1',
              //   name: '章散',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 13678263529,
              //   idCard: 223911199208102238
              // },
              // {
              //   id: '2',
              //   name: '里斯',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 13678263525,
              //   idCard: 213811199203102238
              // },
              // {
              //   id: '3',
              //   name: '王武',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 13978263529,
              //   idCard: 223911199208102641
              // },
              // {
              //   id: '4',
              //   name: '朱柳',
              //   figure: '../../../../assets/UM/用户.png',
              //   phoneNum: 18378263525,
              //   idCard: 213811199203107264
              // }
            ],
            none: true
        }
    },
    components: { 
        // SmViewer
        TestSmViewer 
    },
    beforeRouteLeave(to,from,next){
        if(to.name == 'UMPatrolHomePage' || to.name == '设备管理主页' || to.name == '人员定位详情' || to.name == '设备分配' 
            || to.name == '管廊安防监控列表' || to.name == '管廊环境监控列表'
            || from.name == 'UMPatrolHomePage' || from.name == '设备管理主页' || from.name == '人员定位详情' || from.name == '设备分配' 
            || from.name == '管廊安防监控列表' || from.name == '管廊安防监控详情' || from.name == '管廊环境监控列表'
            || from.name == 'UMDetailEquipment'
        ){
            from.meta.keepAlive = true
            to.meta.keepAlive= true
            this.$destroy()
            next()
        }else{
            from.meta.keepAlive = false
            to.meta.keepAlive = false
            this.$destroy()
            next()
        }
    },
    watch: {
        '$route': function () {
          this.tunnelId = this.$route.params.id
          this.getVisitors()
        }
    },
    mounted() {
        this.tunnelId = this.$route.params.id
        this.$refs.smViewer.startPersonnelPosition()
        this.getVisitors()
        this.setGIS()
    },
    methods: {
        setGIS(){
            var gis = document.getElementById("newID");
            gis.style.display = "block";
            gis.style.position = 'absolute';
            gis.style.top = '0px';
            gis.style.height = '100%';
            gis.style.width = '100%'    
            document.body.removeChild(gis)
            document.getElementById("GISbox").appendChild(gis)
            this.$refs.smViewer.setViewAngAngle({
                longitude:112.49658193083268,
                latitude:37.70723582609472,
                height:2150.5826793988153,
                roll:1.9123609362736715e-9,
                pitch:-1.5254146937977628,
                heading:0.360004849679342
            });
        },
      getVisitors() {
          let _this = this
          personnelPositionService.getActiveLocators().then(
            (result)=>{
                  _this.personnelInfo = []
                  if(result.length != 0){
                    _this.none = false
                    result.forEach(locator=>{
                      let temp = {}
                      temp.id = locator.id
                      temp.height = locator.height
                      temp.latitude = locator.latitude
                      temp.longitude = locator.longitude
                      temp.username = locator.owner.name
                      temp.companyName = locator.owner.companyName
                      temp.idCard = locator.owner.idCard
                      temp.phoneNum = locator.owner.phoneNum
                      _this.personnelInfo.push(temp)
                    })
                  } else {
                    _this.none = true
                  }
                  
            },(error)=>{
                console.log(error)
            }
            )
      },
    },
    beforeDestroy() {
        this.$refs.smViewer.stopPersonnelPosition()
        var gis = document.getElementById("newID");
        gis.style.display = "none";
        document.getElementById("GISbox").removeChild(gis)
        document.body.appendChild(gis)
    }
  }
</script>
<style scoped>
  .whole{
    min-height: 100%;
    position: relative;
    background-color: white;
  }
  .view{
    border: 1px solid #b3b0b0;
    height: 74vh;
  }
  .perList{
    display: inline-block;
    margin-left: 40px;
    list-style-type: none;
    position: relative;
    top: -32px;
  }
  .call{
    position: absolute;
    bottom: 0;
    right: 4px;
  }
  .content{
    position: relative;
    height: 16vh;
  }

</style>
