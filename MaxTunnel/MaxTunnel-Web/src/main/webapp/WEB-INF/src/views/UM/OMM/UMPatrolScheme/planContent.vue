  <template>
    <div>
        <div class="planBox">
            <span class="planDec">计划编号</span>
            <Input v-model="projectNum" placeholder="" style="width: 150px;margin-right: 15px;"></Input>
            <span class="planDec">计划名称</span>
            <Input v-model="projectName" placeholder="" style="width: 150px;margin-right: 15px;"></Input>
        </div>
        <div class="planBox">
            <span class="planDec">所属管廊</span>
            <Select v-model="model1" style="width: 150px;margin-right: 15px;font-size: 14px;">
              <Option v-for="item in affiliatedPipeList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
            <span class="planDec">计划状态</span>
            <Select v-model="model2" style="width: 150px">
              <Option v-for="item in workOrderStatusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
        </div>
        <div class="planBox">
            <span class="planDec">责任班组</span>
            <Select v-model="model3" style="width: 150px;margin-right: 15px;font-size: 14px;">
              <Option v-for="item in responsibilityTeamList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
            <span class="planDec personInCharge">责任人</span>
            <Select v-model="model4" style="width: 150px">
              <Option v-for="item in responsibilityPersonList" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
        </div>
        <!-- <div class="planBox">
            <span class="planDec">开始时间</span>
            <DatePicker type="date" placeholder="请选择开始时间" style="width: 150px;margin-right: 15px;font-size: 14px;"></DatePicker>
            <span class="planDec">结束时间</span>
            <DatePicker type="date" placeholder="请选择结束时间" style="width: 150px;margin-right: 15px;font-size: 14px;"></DatePicker>
        </div> -->
        <div class="planBox">
            <span class="planDec">计划描述</span>
            <Input v-model="textarea1" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入计划描述"></Input>
        </div>
        <div class="planBox">
            <span class="planDec">审批记录</span>
            <Input v-model="textarea2" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入审批记录"></Input>
        </div>
        <div class="planBox planBtn">
            <Button type="primary" class="btn">保存</Button>
            <Button type="primary" class="btn">提交</Button>
        </div>
    </div>      
</template>

<script>
export default {
    data () {
      return{
        affiliatedPipeList: [
          {
            value: '凤凰岭北段',
            label: '凤凰岭北段'
          },
          {
            value: '凤凰岭南段',
            label: '凤凰岭南段'
          },
          {
            value: '渠北区北段',
            label: '渠北区南段'
          }
        ],
        workOrderStatusList: [
          {
            value: '已分配',
            label: '已分配'
          },
          {
            value: '未分配',
            label: '未分配'
          }
        ],
        responsibilityTeamList: [
          {
            value: '维护一组',
            label: '维护一组'
          },
          {
            value: '维护二组',
            label: '维护二组'
          }
        ],
        responsibilityPersonList: [
          {
            value: '张三',
            label: '张三'
          },
          {
            value: '李四',
            label: '李四'
          },
          {
            value: '王五',
            label: '王五'
          }
        ],
        model1: '',
        model2: '',
        model3: '',
        model4: '',
        textarea1: '',
        textarea2: '',
        projectNum: 'MA1000400011',
        projectName: '凤凰岭北段2#井盖故障'
      }
    }
};
</script>

<style  scoped>
  .planDec {
    padding-right: 5px;
    font-size: 14px;
  }
  .personInCharge{
    letter-spacing: 0.5em;
    margin-right: -0.5em;
  }
  .planBox{
    padding-top: 20px;
  }
  .ivu-input-wrapper{
    width: 385px;
  }
  .planBtn{
    padding-top: 30px;
    padding-left: 307px;
  }
  .btn{
    margin-left: 10px;
  }

</style>


