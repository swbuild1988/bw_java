<template>
  <div>
    <Modal v-model="show.state" width="500">
      <p slot="header" style="color:#1e8ef5;text-align:left">
        <Icon type="information-circled"></Icon>
        <span>告警详细信息</span>
      </p>
      <Row :gutter="16" v-if="alarmData!=null&&alarmData!=''">
        <Col span="12" style="margin-top: 10px;">
        <label>告警编号:</label> <Input v-model="alarmData.id" disabled   style="width: 70%;" icon="pound"></Input>
        </Col>
        <Col span="12" style="text-align:right;margin-top: 10px;">
        <label >告警类型:</label><Input v-model="alarmData.alarmType" disabled   style="width: 70%;" icon="ios-pricetags-outline "></Input>
        </Col>
        <Col span="12"  style="margin-top: 10px;">
        <label>对象编号:</label> <Input v-model="alarmData.ObjectNo" disabled   style="width: 70%;" icon="disc"></Input>
        </Col>
        <Col span="12" style="text-align:right;margin-top: 10px;">
        <label >对象名称:</label><Input v-model="alarmData.relatedObjectName" disabled   style="width: 70%;" icon="social-buffer-outline "></Input>
        </Col>
        <Col span="12"  style="margin-top: 10px;">
        <label>监测区域</label>:</label> <Input v-model="alarmData.location" disabled   style="width: 70%;"   icon="ios-location"></Input>
        </Col>
        <Col span="12" style="text-align:right;margin-top: 10px;">
        <label >告警源:</label><Input v-model="alarmData.alarmScore" disabled   style="width: 70%;"  icon="pinpoint"></Input>
        </Col>
        <Col span="12"  style="margin-top: 10px;">
        <label>告警级别:</label> <Input v-model="alarmData.alarmLevel" disabled   style="width: 70%;" icon="levels"></Input>
        </Col>
        <Col span="12" style="text-align:right;margin-top: 10px;">
        <label >告警时间:</label><Input v-model="alarmData.alatmTime" disabled   style="width: 70%;"  icon="ios-clock-outline"></Input>
        </Col>
        <Col span="24"  style="margin-top: 10px;">
        <label >描述信息:</label><Input v-model="alarmData.description" disabled  type="textarea" :autosize="{minRows: 2,maxRows: 6}"  style="width: 89%;" icon="document-text "></Input>
        </Col>
      </Row>
      <div slot="footer"  style="margin-top: 10px;">
        <Button type="info" size="large" long @click="confirmAlarm">确认</Button>
      </div>
    </Modal>
  </div>

</template>
<script>
    export default {
        name: "alarm-detial",
      data(){
          return{
            modal_loading: false,
          }
      },
      props:{
          show:{
            type:Object
          },
        alarmData:{
            default:{
              id:1001,
              alarmType:"event",
              alarmLevel:"一般",
              location:"长虹路电力仓区段8",
              alarmScore:"234米",
              alatmTime:"2018-08-13",
              ObjectNo:"111",
              relatedObjectName:"氧气浓度1",
              description:"描述信息信息信息信息",
              img:"",
            }
        }
      },
      methods:{
          //确认告警
          confirmAlarm(){
            this.show.state=false;
          }
      }
    }
</script>

<style scoped>

</style>
