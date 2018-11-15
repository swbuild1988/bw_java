<template>
  <div>
    <div class="queryCondition">
      <Row>
        <Col span="6">
        所属监测仓:
        <Select v-model="conditions.tunnelId" style="width:60%">
          <Option value=null key="0">所有</Option>
          <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
        </Col>
        <Col span="6">
        所属区段:
        <Select v-model="conditions.tunnelId" style="width:60%">
          <Option value=null key="0">所有</Option>
          <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
        </Select>
        </Col>
      </Row>
    </div>
    <Row>
      <Col span="8">
      <div class="superviseTop">
        <img :src="superviseTop">
      </div>
      <div class="superviseBottom">
        <img :src="superviseBottom">
      </div>
      </Col>
      <Col span="15" offset="1">
      <div class="superviseDetails">
        <Row>
          <Col v-for="(item,index) in superviseDetails" v-if="index%2==0" span="11" :key="index">
          <Card>
            <p slot="title">{{item.name}}</p>
            <ul class="secondLevel">
              <li class="secondLevelLi" v-for="(a,index) in item.list" v-on:click.stop="doThis(index)" :key="a.index">
                <div>{{a.title}}</div>
                <div>{{a.voltage}}</div>
                <div>{{a.status}}</div>
                <div :class="{'normal':a.status==='正常','abnormal': a.status==='不正常'}"></div>
              </li>
            </ul>
          </Card>
          </Col>
          <Col v-else span="11" offset="2">
          <Card>
            <p slot="title">{{item.name}}</p>
            <ul class="secondLevel">
              <li class="secondLevelLi" v-for="(a,index) in item.list" v-on:click.stop="doThis(index)" :key="a.index">
                <div>{{a.title}}</div>
                <div>{{a.voltage}}</div>
                <div>{{a.status}}</div>
                <div :class="{'normal':a.status==='正常','abnormal': a.status==='不正常'}"></div>
              </li>
            </ul>
          </Card>
          </Col>
        </Row>
      </div>
      </Col>
    </Row>
  </div>
</template>
<script>
  import { TunnelService } from '../../../../services/tunnelService'
  import superviseTop from '../../../../assets/UM/superviseTop.png'
  import superviseBottom from '../../../../assets/UM/superviseBottom.png'
  export default {
    data() {
      return {
        color: '',
        conditions: {
          tunnelId: null
        },
        tunnels: [],
        superviseDetails: [
          {
            name: '电力监控',
            list: [
              {title: 'DY-1', voltage: '2000v', status: '正常'},
              {title: 'DY-2', voltage: '2000v', status: '正常'},
              {title: 'DY-3', voltage: '2000v', status: '不正常'},
            ],
            show: false
          },
          {
            name: '温度监测',
            list: [
              {title: 'DY-1', voltage: '2000v', status: '正常'},
              {title: 'DY-2', voltage: '2000v', status: '正常'},
              {title: 'DY-3', voltage: '2000v', status: '正常'},
            ],
            show: false
          },
          {
            name: '气体监测',
            list: [
              {title: 'DY-1', voltage: '2000v', status: '正常'},
              {title: 'DY-2', voltage: '2000v', status: '正常'},
              {title: 'DY-3', voltage: '2000v', status: '正常'},
            ],
            show: false
          },
          {
            name: '照明状态',
            list: [
              {title: 'DY-1', voltage: '2000v', status: '正常'},
              {title: 'DY-2', voltage: '2000v', status: '正常'},
              {title: 'DY-3', voltage: '2000v', status: '正常'},
            ],
            show: false
          },
          {
            name: '井盖状态',
            list: [
              {title: 'DY-1', voltage: '2000v', status: '正常'},
              {title: 'DY-2', voltage: '2000v', status: '正常'},
              {title: 'DY-3', voltage: '2000v', status: '正常'},
            ],
            show: false
          },
          {
            name: '排风控制',
            list: [
              {title: 'DY-1', voltage: '2000v', status: '正常'},
              {title: 'DY-2', voltage: '2000v', status: '正常'},
              {title: 'DY-3', voltage: '2000v', status: '正常'},
            ],
            show: false
          },
        ]
      }
    },
    mounted() {
      let _this = this;
      TunnelService.getTunnels().then(
          (result)=>{
              _this.tunnels = result
          },
          (error)=>{
              console.log(error)
      })
    },
    methods: {
      doThis(index) {

      }
    }
  }
</script>
<style scoped>
  .queryCondition {
    margin-bottom: 10px;
    margin-top: 8px;
  }

  img {
    width: 100%;
    height: auto;
    max-width: 100%;
    display: block;
  }

  .superviseDetails {
    height: 80vh;
    width: 100%;
    display: inline-block;
    vertical-align: top;
  }

  .superviseTop {
    height: 45vh;
    margin-bottom: 1vh;
  }

  .superviseBottom {
    height: 34vh;

  }

  .superviseDetails, .stair .stairLi {
    border: 1px solid #b3b0b0;
    border-radius: 8px;
    overflow-y: auto;
  }

  .superviseDetails ul li {
    list-style: none;
  }

  .secondLevelLi div {
    display: inline-block;
    line-height: 5vh;
    width: 24%;
  }

  .normal {
    width: 15px !important;
    height: 15px !important;
    border-radius: 100%;
    background: green;
  }

  .abnormal {
    width: 15px !important;
    height: 15px !important;
    border-radius: 100%;
    background: red;
  }
</style>
