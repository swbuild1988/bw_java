<!--开始预案-->
<template>
  <div>
    <Modal  :styles="{top: '30%'}" width="30vw;" style="z-index: 1001; " v-model="modalPrams.state" title="启动预案" @on-ok="openPlan" ok-text="启动" cancel-text="取消">
        <Form  :label-width="labelWidth"  style="font-size: 1.22vmin;">
          <FormItem label="预案类型">
            <Select v-model="prams.processKey" style="width: 15vw;float: left;margin-left: 1vw;">
              <Option v-for="item in planList" :value="item.key" :key="item.key">{{ item.val }}</Option>
            </select>
          </FormItem>
          <FormItem label="位置" >
            <treeselect :options="locationList" placeholder="请选择" v-model="prams.location"  style="width: 15vw;float: left;margin-left: 1vw;"/>
          </FormItem>
          <FormItem label="关联告警">
            <Input v-model="prams.alarmId" placeholder="无"  style="width: 15vw;float: left;margin-left: 1vw;" readonly h></Input>
          </FormItem>
        </Form>
    </Modal>
  </div>
</template>

<script>
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import {EnumsService} from '../../../services/enumsService.js'
  import {PlanService} from '../../../services/planService.js'

  export default {
    name: "show-start-plan",
    props: {
      modalPrams: {
        type: Object,
        default: function () {
          return {state: false,selectPlan:0};
        }
      }
    },
    data() {
      return {
        labelWidth:120,
        planList: [],
        locationList: [],
        prams: {
            processKey: "",
          location: null,
          alarmId: "",
        }
      }
    },
    created() {
      this.initData();
    },
    computed: {
        planData: {
            get() {
                return this.$store.getters.getPlanData
            },
            set(value) {
                this.$store.commit('setPlanData', value)
            }
        }
    },
    methods: {
        openPlan() {
            let _this = this;
            let location = (_this.prams.location+"").split("_");
            let parms = {
                tunnelId:parseInt(location[0]),
                storeId:location.length>1?parseInt(location[1]):null,
                sectionId:location.length>2?parseInt(location[2]):null,
            };
            EnumsService.getPlanType().then((result)=>{
                parms.processValue= result.filter(a=>a.processKey==_this.prams.processKey)[0].val;
                PlanService.startPlan(parms).then(()=>{
                    if(_this.$router.history.current.name=="执行预案"){
                        _this.$router.push("/UM/plans/execute/"+_this.prams.processKey);
                    }
                });
            })
        },
      initData() {
        var _this = this;
        EnumsService.getMonitorZone().then((result) => {
          if (result) {
            result.forEach(a => {
              var temp = {};
              temp.id = a.id;
              temp.label = a.name;
              temp.children = [];
              _this.locationList.push(temp);
              if (a.list.length > 0) {
                a.list.forEach(b => {
                  var child = {};
                  child.id = a.id + "_" + b.id;
                  child.label = b.name;
                  child.children = [];
                  temp.children.push(child);
                  if (b.list.length > 0) {
                    b.list.forEach(c => {
                      var child2 = {};
                      child2.id = a.id + "_" + b.id + "_" + c.id;
                      child2.label = c.name;
                      child.children.push(child2);
                    })
                  }
                })
              }
            })
          }
        });
        EnumsService.getPlanType().then(
          result => {
            result.forEach(a => {
              let temp = {};
              temp.key = a.processKey;
              temp.val = a.key;
              _this.planList.push(temp);
            });
          },
          error => {
            _this.Log.info(error)
          })
      },
    },
      watch:{
          "modalPrams.selectPlan": function () {
              this.prams.processKey=this.modalPrams.selectPlan;
          },
      },
      mounted(){
      this.labelWidth=window.innerWidth*0.08
      },
    components: {
      Treeselect
    },
  }
</script>

<style scoped>
 .ivu-input-type >>> .ivu-input {
    font-size: 1.22vmin;
    height: 4vmin;
  }

 .vertical-center-modal{
   display: flex;
   align-items: center;
   justify-content: center;
 }
</style>
