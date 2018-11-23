<!--数据对比-->
<template>
  <div>
    <Row class="top" style="margin-bottom: 0px;">
      <Col span="6" class="col">
      <span class="planDec">对象类型:</span>
      <Select v-model="queryPrams.objectType" style="width:65%">
        <OptionGroup v-for="(group, index) in objectList" :label="group.key" :key="index" style="font-size: 18px;">
          <Option v-for="item in group.objectTypeList" :value="item.val" :key="item.val">{{ item.key }}</Option>
        </OptionGroup>
      </Select>
      </Col>
      <Col span="6" class="col">
      <span class="planDec">监测区域:</span>
      <treeselect :options="zoneList" placeholder="请选择" v-model="queryPrams.queryZone"
                  style="width: 60%;float: left;left: 8px"/>
      </Col>
      <Col span="6" class="col">
      <span class="planDec">数据类型:</span>
      <Select v-model="queryPrams.dataType" style="width:65%" @on-change="changeDataType">
        <Option v-for="item in dataTypeEnum" :value="item.val" :key="item.val">{{ item.key }}</Option>
      </Select>
      </Col>
    </Row>

    <Row class="top" style="margin-top: 0px;">
      <Col span="6" class=" col">
      <span class="planDec">监测对象:
      </span>
      <Input v-model="queryPrams.monitorObject" readonly style="width: 65%;">
      <Button slot="append" icon="ios-search" style="height: 35px;" @click="queryObject"></Button>
      </Input>
      </Col>
      <transition name="slide-fade" mode="in-out">
        <div v-if="queryPrams.dataType==1">
          <Col span="10" class="col">
          <span class="planDec">取整范围:</span>
          最小值
          <Input v-model="queryPrams.minVal" style="width: 100px"></Input>
          最大值
          <Input v-model="queryPrams.maxVal" style="width: 100px"></Input>
          </Col>
        </div>
      </transition>
      <transition name="slide-fade" mode="in-out">
        <div v-if="queryPrams.dataType==2">
          <Col span="10" class="col">
          <span class="planDec">开关状态:</span>
          <CheckboxGroup v-model="queryPrams.dataRangeGroup" style="position: relative;float: left;left:8px;top:6px;"
                         size="large">
            <Checkbox label="1">开</Checkbox>
            <Checkbox label="2">关</Checkbox>
          </CheckboxGroup>
          </Col>
        </div>
      </transition>
      <transition name="slide-fade" mode="in-out">
        <div v-if="queryPrams.dataType==4||queryPrams.dataType==5">
          <Col span="10" class="col">
          <span class="planDec">监测位置:</span>
          <Input v-model="queryPrams.pleace" style="width: 120px;margin-left: 8px;  "></Input>
          （米）
          </Col>
        </div>
      </transition>
      <Col span="1" style="position: relative;float: right;right: 10px;top:-20px;">
      <Button type="primary" shape="circle" icon="ios-search" @click="queryTableData" title="查询" size="large"></Button>
      </Col>
      <ShowMonitorObjectSelect v-bind="dataObjectSelect"></ShowMonitorObjectSelect>
    </Row>
    <transition name="slide-fade" mode="in-out">
      <div v-if="!viewHistory">
        <Row style="padding-top: 0px;padding-right: 9px;padding-left: 9px;">
          <Col span="24">
          <Table class="tablesize" stripe border :columns="tableColumn" :data="tableData" ref="selection"
                 @on-selection-change="selectionClick"></Table>
          <div style="position:relative; line-height: 40px;background-color:#f1f1f1;">
            <Button type="primary" shape="circle" icon="forward" size="large" title="查看历史数据"
                    @click="queryHistoryData"></Button>
            <Button type="primary" shape="circle" icon="ios-cloud-download" size="large" title="导出"
                    @click="exportData"></Button>
            <Page class="nextPage" @on-change="changePage" @on-page-size-change="handlePageSize"
                  :total="queryPrams.total"
                  show-total show-elevator
                  :page-size="queryPrams.pageSize"></Page>
          </div>
          </Col>
        </Row>
      </div>
    </transition>
    <transition name="fade" mode="out-in">
      <div v-if="viewHistory">
        <Row style="padding: 9px;padding-top: 0px">
          <Col span="24">
          <div style="position:relative;" class="queryHis">
            <div class="chartSize">
              <SimplelineChart style="width: 100%;" v-bind="curlineChart"></SimplelineChart>
            </div>
            <div style="height: 50px;">
              <div style="position: relative;float: left;">
                <span>图表类型:</span>
                <Select v-model="historyPrams.chartType" style="width:250px;margin-right: 4px;margin-left: 4px;"
                        @on-change="changeAlarmType" placement="top">
                  <Option v-for="item in chartOptions" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
                <span>时间周期:</span>
                <Select v-model="historyPrams.dateType" style="width:250px;margin-right: 4px;margin-left: 4px;"
                        @on-change="changeAlarmType" placement="top">
                  <Option v-for="item in historyDateType" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
              </div>
              <div v-if="historyPrams.chartType=='1'" style="position: relative;float: left; ">
                <span>开始时间:</span>
                <DatePicker v-model="historyPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间"
                            style="width: 220px;margin-right: 4px;"></DatePicker>
                <span>结束时间:</span>
                <DatePicker v-model="historyPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间"
                            style="width:220px;margin-right: 14px;"></DatePicker>
              </div>
              <div v-else style="position: relative;float: left;">
                <span>时间周期1:</span>
                <DatePicker v-model="historyPrams.startTime" :readonly="isReady" type="datetime" placeholder="开始时间"
                            style="width: 220px;margin-right: 4px;"></DatePicker>
                <span>时间周期2:</span>
                <DatePicker v-model="historyPrams.endTime" type="datetime" :readonly="isReady" placeholder="结束时间"
                            style="width:220px;margin-right: 14px;"></DatePicker>
              </div>
              <div style="position:relative;float: right;right: 20px;">
                <Button type="primary" shape="circle" icon="ios-search" size="large" title="查询历史数据"></Button>
                <Button type="primary" shape="circle" @click="backToCurPage" icon="reply" size="large"
                        title="返回"></Button>
              </div>
            </div>
          </div>
          </Col>
        </Row>
      </div>
    </transition>
  </div>
</template>

<script>
    export default {
        name: "data-compare"
    }
</script>

<style scoped>

</style>
