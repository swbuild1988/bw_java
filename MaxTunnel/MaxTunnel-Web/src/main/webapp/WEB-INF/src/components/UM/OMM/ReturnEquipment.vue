<template>
  <div class="queryEquipment">
    <Row class="top">
      <Col span="6">
      <span class="planDec">设备类型：</span>
      <Select v-model="condition.type" @on-change='showTable()' style="width:60%">
        <Option value=null key="0">所有</Option>
        <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">设备状态：</span>
      <Select v-model="condition.status" @on-change='showTable()' style="width:60%">
        <Option value=null key="0">所有</Option>
        <Option v-for="item in equipmentStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
      </Select>
      </Col>
      <Col span="6">
      <span class="planDec">入库时间：</span>
      <DatePicker type="date" placeholder="选择时间" style="width: 220px" v-model="condition.startEquipmentTime"
                  @on-change='showTable()'></DatePicker>
      </Col>
      <Col span="1" offset="5" >
      <div style="position: relative;float: right;right: 8px;">
        <Button type="primary"  icon="ios-search">查询</Button>
      </div>
      </Col>
    </Row>
    <div class="bottom">
      <Row>
        <Col span="5" class="equipmentList">
        备品列表</Col>
        <Col span="4" offset="15">
        <div style="position: relative;float: right">
          <Button type="success" @click="add({path: '/UM/equipment/add'})"  icon="forward">增加入库</Button>
          <Button type="success"  icon="reply">批量出库</Button>
        </div>
        </Col>
        <Col span="24">
        <Row :gutter="8">
          <Col span="6" v-for="(item,index) in equipments" :key="index"style="margin-top: 6px;">
          <div :style="{backgroundImage:'url(' + item.imgUrl + ')'}" class="backGoundBox">
            <div class="topBox">
              <p class="equipentTitle">{{ item.name }}</p>
              <div class="imgBox"><img :src="item.imgUrl"></div>
            </div>
          </div>
          <div class="detailsBox">
            <p>设备状态：{{item.statusName}}</p>
            <p>设备类型：{{ item.typeName }}</p>
            <p>入库时间：{{ item.crtTime }}</p>
          </div>
          <div class="operation">
            <Row>
              <Col span="8" class="operationSee">
              <Icon type="reply" size=20></Icon>
              <p @click="show(index)">取用出库</p>
              </Col>
              <Col span="8" class="operationEdit">
              <Icon type="edit" size=19></Icon>
              <p @click="edit(index)">编辑</p>
              </Col>
              <Col span="8" class="operationDel">
              <Icon type="trash-a" size=20></Icon>
              <p @click="del(index)">删除</p>
              </Col>
            </Row>
          </div>
          </Col>
        </Row>
        </Col>
      </Row>
    </div>
    <Page   :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show-sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
  </div>
</template>

<script>
    export default {
        name: "return-equipment"
    }
</script>

<style scoped>

</style>
