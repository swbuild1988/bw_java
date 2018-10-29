<template>
  <div id="main" class="Main">
      <div class="MainLeft clear" id="mainLeft">
          <gallery-message tableid="information"></gallery-message>
          <equipment v-bind="equipment"></equipment>
          <fault v-bind="fault"></fault>
      </div>

      <div class="MainCentre" id="mainCentre">
          <vm-map></vm-map>
          <div class="MainCentreBottom clear" id="mainCentreBottom">
              <annual-pipe v-bind="pipeRack"></annual-pipe>
              <buddha-ridge v-bind="buddhaRidge"></buddha-ridge>
              <untreated v-bind="untreated"></untreated>
              <alarm-monitor v-bind="monitor"></alarm-monitor>
          </div>
      </div>

      <div class="MainRight" id="mainRight">
          <vm-video></vm-video>
          <three-dimensional></three-dimensional>
      </div>
  </div>
</template>

<script>
  import GalleryMessage from '../../../components/VM/VMBodyLeft/GalleryMessageChart'
  import Equipment from '../../../components/VM/VMBodyLeft/EquipmentChart'
  import Fault from '../../../components/VM/VMBodyLeft/FaultChart'
  import VmMap from '../../../components/VM/VMBodyCenter/Map'
  import AnnualPipe from '../../../components/VM/VMBodyCenter/AnnualPipeChart'
  import BuddhaRidge from '../../../components/VM/VMBodyCenter/BuddhaRidgeChart'
  import Untreated from '../../../components/VM/VMBodyCenter/UntreatedChart'
  import AlarmMonitor from '../../../components/VM/VMBodyCenter/AlarmMonitorChart'
  import VmVideo from '../../../components/VM/VMMainRight/Video'
  import ThreeDimensional from '../../../components/VM/VMMainRight/ThreeDimensional'

  export default {
    data(){
      return{
          equipment:{
              barSimpleid:'equipment',
              requestUrl:'equipments/type/count'
          },
          fault:{
              radarChartid:'fault',
              requestUrl:'equipments/type/status/count'
          },
          pipeRack:{
              pieChartid:'pipeRack',
              requestUrl:'tunnels/items/energies'
          },
          buddhaRidge:{
              timeSeriesid:'buddhaRidge',
              requestUrl:'tunnels/3/energies/one-year/1530255046'
          },
          stackedBar:{
              timeSeriesid:'stackedBar',
              requestUrl:''
          },
          monitor:{
              stackedBarid:'monitor',
              requestUrl:'tunnel/alarmcount'
          },
          untreated:{
              basiColumnid:'untreated',
              requestUrl:'tunnels/defectcount'
          }
      }
    },
      components:{
          GalleryMessage,
          Fault,
          Equipment,
          VmMap,
          AnnualPipe,
          BuddhaRidge,
          Untreated,
          AlarmMonitor,
          VmVideo,
          ThreeDimensional
      }
  }
</script>

<style scoped>
    .Main{
        width: 100%;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        background: url('../../../assets/VM/bg_image.png') no-repeat;
        background-size: 100% 100%;
    }
    #main .MainLeft{
      padding-left: 0.7%;
      width: 15%;
      height: 86%;
      position: absolute;
      top: 14%;
    }

    #mainLeft >>> .InformationContainer,#mainLeft >>> .FaultChartContainer,#mainLeft >>> .EquipmentContainer{
      position: relative;
      width: 100%;
      height: 33%;
      margin-bottom: 1%;

    }
    /*body conter*/
    #main .MainCentre,#main .MainRight{
        position: absolute;
        height: 94%;
        top: 6%;
    }
    #main .MainCentre{
        left: 15.7%;
        width: 59%;
    }
    #mainCentre >>>.Map{
        position: relative;
        width: 100%;
        height: 71%;
        margin-bottom: 0.5%;
        overflow: hidden;
    }
    /*body Centre Bottom*/
    #mainCentre .MainCentreBottom{
        width: 100%;
        height: 28%;
    }
    #mainCentreBottom >>> .PipeRackContainer,#mainCentreBottom >>> .BuddhaRidgeContainer,#mainCentreBottom >>> .UntreatedContainer{
        position: relative;
        height: 100%;
        width: 24.5%;
        margin-right: 0.5%;
    }
    #mainCentreBottom >>> .MonitoringContainer{
        position: relative;
        height: 100%;
        width: 25%;
    }
    /*body right*/
    #main .MainRight{
        right:0.7%;
        width:24%;
    }
    #mainRight >>> .VideoContainer{
        position: relative;
        height: 61%;
        margin-bottom: 1%;
    }
    #mainRight >>> .ThreeDimensionalContainer{
        position: relative;
        height: 38%;
    }
    .BackgroundImage{
        padding: 1.3%;
        border-radius: .5rem;
        border: 1px solid rgba(0,107,149,0.5);
        box-shadow: 0 0 1.5rem rgba(0,107,149,1) inset;
    }
    .BodyCenterPadding{
        padding: 0.3%;
    }
</style>
