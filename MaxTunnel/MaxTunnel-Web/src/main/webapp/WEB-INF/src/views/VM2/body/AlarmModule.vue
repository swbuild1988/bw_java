<template>
  <div class="main">
    <div class="Title">
      <module-title :title="title"></module-title>
    </div>
    <div class="total">
      <div class="subTitle">
        <span class="name">本年度告警：</span>
        <span class="number">{{ yearTotal.number }}</span>
        <span class="yearIcon">
          <Icon :type="yearTotal.isRise ? 'arrow-up-c' : 'arrow-down-c'" :color="yearTotal.isRise ? 'rgb(247, 26, 56)': 'white'"></Icon>
        </span>
      </div>
      <div class="subTitle">
        <span style="letter-spacing: 0.25em;margin-right: -0.25em;" class="name">本月告警：</span>
        <span class="number">{{ monthTotal.number }}</span>
        <span class="monthIcon">
          <Icon :type="monthTotal.isRise ? 'arrow-up-c' : 'arrow-down-c'" :color="monthTotal.isRise ? 'rgb(247, 26, 56)': 'white'"></Icon>
        </span>
      </div>
    </div>
    <div class="pie">
      <simple-pie-chart v-bind="pieChart"></simple-pie-chart>
    </div>
    <div class="alarms">
      <span style="font-size: 1.5vmin;line-height: 2.8vmin;color: #fff;">告警信息</span>
      <Icon type="android-volume-up" color="#fff" :size="iconSize"></Icon>
      <div class="play" @click="changeState">
        <Icon :type="carouselInfo.isCarousel ? 'pause' : 'play'" class="playIcon"></Icon>
      </div>
      <Row :gutter="16" style="margin-top: 0.3vh;">
        <div :key="index" v-for="(alarm,index) in alarmShowData">
          <Col span="1" offset="1"style="padding-top:0.5vh;">
          <Icon type="checkmark-circled" color="#0196ff" :size="iconSize"></Icon>
          </Col>
          <Col span="5" offset="1" class="colSpan">
          <div>
            {{alarm.alarmDate}}
          </div>
          </Col>
          <Col span="6" class="colSpan">
          <div :title="alarm.name">
            {{alarm.name}}
          </div>
          </Col>
          <Col span="7" class="colSpan">
          <div :title="alarm.location">
            {{alarm.location}}
          </div>
          </Col>
          <Col span="3" class="colSpan">
          <div :style="{backgroundColor: alarm.color, textAlign: 'center', borderRadius: '5px'}">
            {{alarm.alarmLevel}}
          </div>
          </Col>
        </div>
      </Row>
    </div>
  </div>
</template>

<script>
  import SimplePieChart from "../../../components/Common/Chart/SimplePieChart";
  import pileBarChart from "../../../components/Common/Chart/PileBarChart";
  import ModuleTitle from "../../../components/VM2/ModuleTitle";
  import {
    AlarmService
  } from "../../../services/alarmService";

  export default {
    data() {
      return {
        title: "警报统计",
        iconSize: 12,
        yearTotal: {
          number: 271,
          isRise: false
        },
        monthTotal: {
          number: 32,
          isRise: true
        },
        pieChart: {
          requestUrl: "tunnels/alarm-count",
          id: "tunnelAlarmPieChart",
          titleSize: "9%",
          seriesSize: "10%",
          parameters: {
            option: {
              legend: {
                textStyle: {
                  color: "#fff"
                }
              },
            }
          }
        },
        pileBarChart: {
          id: "alarmsReportBar",
          requestUrl: "alarms/level-count–everymonth",
          parameters: {
            option: {
              legend: {
                data: ["严重", "一般", "提示", "致命"],
                textStyle: {
                  color: "white",
                  fontSize: "10%"
                },
                orient: "horizontal",
                left: 20
              },
              xAxis: {
                axisLabel: {
                  color: "white",
                  fontSize: "8%"
                }
              },
              yAxis: {
                axisLabel: {
                  color: "white",
                  fontSize: "8%"
                }
              }
            },
            prams: {}
          },
          type: "get"
        },
        words: ["暂无告警"],
        alarmShowData: [],
        alarmAllData: [],
        carouselInfo: {
          intervalId: null,
          totalPage: null,
          pageSize: 4,
          curPage: 1,
          isCarousel: true,
          time: 2000
        },
        refresh: {
          time: 300000,
          intervalId: null
        }
      };
    },
    components: {
      SimplePieChart,
      pileBarChart,
      ModuleTitle
    },
    mounted() {
      this.iconSize = window.innerWidth * 0.009;
      this.init();
      let _this = this;
      this.refresh.intervalId = setInterval(() => {
        _this.init();
      }, this.refresh.time);
      if (this.carouselInfo.isCarousel) {
        this.carouselInfo.intervalId = setInterval(() => {
          _this.carousel();
        }, this.carouselInfo.time);
      }
    },
    methods: {
      init() {
        let _this = this;
        AlarmService.getYearAndMonthAlarmCount().then(result => {
          result.forEach(item => {
            if (item.key == "year") {
              _this.yearTotal.number = item.value;
              _this.yearTotal.isRise = item.isRise;
            } else {
              _this.monthTotal.number = item.value;
              _this.monthTotal.isRise = item.isRise;
            }
          });
        });
        AlarmService.getNewAlarmsList().then(result => {
          if (result) {
            _this.words = [];
            result.forEach(alarm => {
              let info =
                alarm.description +
                " " +
                alarm.alarmLevel +
                " " +
                alarm.location +
                " " +
                alarm.alarmTime;
              _this.words.push(info);
            });
            _this.alarmAllData = result;
            _this.alarmAllData.forEach(alarm => {
              alarm.alarmLevel = alarm.alarmLevel.slice(0, 2);
              alarm.alarmDate = new Date(alarm.alarmDate).format("MM-dd  hh:mm");
              switch (alarm.alarmLevel) {
                case "提示":
                  alarm.color = "#0066ff";
                  break;
                case "一般":
                  alarm.color = "#ffff00";
                  break;
                case "严重":
                  alarm.color = "#ffae00";
                  break;
                case "危急":
                  alarm.color = "#ff0000";
                  break;
              }
            });
            _this.carouselInfo.totalPage =
              result.length % _this.carouselInfo.pageSize == 0 ?
              result.length / _this.carouselInfo.pageSize :
              parseInt(result.length / _this.carouselInfo.pageSize) + 1;
          }
        });
      },
      carousel() {
        this.alarmShowData = this.alarmAllData.slice(
          (this.carouselInfo.curPage - 1) * this.carouselInfo.pageSize,
          this.carouselInfo.curPage * this.carouselInfo.pageSize
        );
        if (this.carouselInfo.curPage === this.carouselInfo.totalPage) {
          this.carouselInfo.curPage = 1;
        } else {
          this.carouselInfo.curPage += 1;
        }
      },
      changeState() {
        this.carouselInfo.isCarousel = !this.carouselInfo.isCarousel;
        if (this.carouselInfo.isCarousel) {
          let _this = this;
          this.carouselInfo.intervalId = setInterval(() => {
            _this.carousel();
          }, this.carouselInfo.time);
        } else {
          clearInterval(this.carouselInfo.intervalId);
          clearInterval(this.refresh.intervalId);
        }
      }
    },
    beforeDestory() {
      clearInterval(this.carouselInfo.intervalId);
      clearInterval(this.refresh.intervalId);
    }
  };

</script>

<style scoped>
  .main {
    width: 100%;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
  }

  .total {
    width: 42%;
    height: 24%;
    position: absolute;
    top: 18%;
    left: 4%;
    background: url("../../../assets/UM/videoBody.png") no-repeat;
    background-size: 100% 100%;
  }

  .pie {
    width: 50%;
    height: 44%;
    position: absolute;
    top: 6%;
    right: 2%;
  }

  .alarms {
    position: absolute;
    padding: 10px;
    width: 98%;
    height: 50%;
    top: 42%;
    left: 0;
  }

  .Title {
    width: 42vmin;
    height: 5vmin;
  }

  .subTitle {
    font-size: 1vmin;
    color: white;
    margin: 0.2vh 0.8vw;
  }

  .name {
    vertical-align: middle;
  }

  .number {
    font-size: 2vmin;
    margin-left: 0.6vw;
    color: #37bbcc;
    vertical-align: middle;
  }

  .yearIcon {
    font-size: 2.6vmin;
    position: absolute;
    top: 0;
    right: 0.8vmin;
    vertical-align: middle;
  }

  .monthIcon {
    font-size: 2.6vmin;
    position: absolute;
    top: 3.2vmin;
    right: 0.8vmin;
    vertical-align: middle;
  }

  .colSpan {
    padding-top: 0.25vh;
    font-size: 1.22vmin;
    line-height: 2.5vmin;
    color: #fff;
    overflow: hidden;
    text-overflow: ellipsis;
    /*文本不换行，这样超出一行的部分被截取，显示...*/
    white-space: nowrap;
  }

  .play {
    float: right;
    margin-top: 0.5vmin;
    width: 2vmin;
    height: 2vmin;
    color: #aaa;
    border: 2px solid #aaa;
    border-radius: 50%;
    font-size: 1.2vmin;
    display: table;
    cursor: pointer;
  }

  .playIcon {
    display: table-cell;
    vertical-align: middle;
    text-align: center;
  }

</style>
