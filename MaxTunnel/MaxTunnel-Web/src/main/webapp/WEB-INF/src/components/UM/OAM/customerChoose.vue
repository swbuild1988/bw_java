<template>
  <div>
    <div class="conditions">
        <Row>
            <Col span="6">
                <span>所属公司：</span>
                <Select v-model="conditions.companyId" @on-change="queryList()" style="width: 60%">
                    <Option value="null">所有</Option>
                    <Option v-for="item in company" :key="item.id" :value="item.id">{{item.name}}</Option>
                </Select>
            </Col>
            <Col span="6">
                <span>联系人：</span>
                <Input v-model="conditions.contact" placeholder="请输入联系人姓名" style="width: 60%" @on-keyup="queryList()"></Input>
            </Col>
            <Col span="6">
                <span>开始时间：</span>
                <DatePicker type="datetime" placeholder="请选择开始时间" style="width: 60%" v-model="conditions.startTime" @on-change="queryList()"></DatePicker>
            </Col>
            <Col span="6">
                <span>结束时间：</span>
                <DatePicker type="datetime" placeholder="请选择结束时间" style="width: 60%" v-model="conditions.endTime" @on-change="queryList()"></DatePicker>
            </Col>
        </Row>
    </div>
    <div class="list">
    <Row>
        <Col span="6" v-for="(item,index) in customers" :key="index">
            <div class="infoList">
                <input type="radio" class="radio" name="customers" :checked="item.id == customerId" @change="getCustomerId(item.id,item.contact)">
                <div class="company">
                    <Icon type="star"></Icon>
                    <span>{{item.company.name}}</span>
                </div>
                <div class="contactInfo">
                    <div class="contact">
                        <Icon type="android-person"></Icon>
                        <span>{{item.contact}}</span>
                    </div>
                    <div class="tel">
                        <Icon type="android-call"></Icon>
                        <span>{{item.tel}}</span>
                    </div>
                </div>
                <div class="crtTime">
                    <Icon type="android-time"></Icon>
                    <span>{{item.crtTime}}</span>
                </div>
            </div>
        </Col>
    </Row>
     <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-sizer show-total
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator style="text-align: right;margin-top: 20px;"></Page>
  </div>
</div>
</template>

<script>
// import BG from '../../assets/UM/BG.jpg'
export default {
  name: "customerChoose",
  props: {
    customerId:{
      type: Number,
      required: false
    }
  },
  data() {
    return {
      company:[],
      conditions:{
        companyId:null,
        contact: null,
        startTime: null,
        endTime: null
      },
      page: {
        pageNum: 1,
        pageSize: 10,
        pageTotal: 0
      },
      customers:[],
    };
  },
  mounted() {
    this.getCompany()
    this.queryList()
  },
  computed: {
    params() {
        let param = {
          pageNum: this.page.pageNum,
          pageSize: this.page.pageSize,
          companyId:this.conditions.companyId,
          contact: this.conditions.contact,
          startTime: new Date(this.conditions.startTime).getTime(),
          endTime: new Date(this.conditions.endTime).getTime()
        };
        return Object.assign({}, param);
    }
  },
  methods: {
    getCompany(){
      this.axios.get('/companies').then(res=>{
          let{ code,data } = res.data
          if(code==200){
              this.company = data;
          }
      })
    },
    queryList() {
          this.axios.post('customers/datagrid',(this.params)).then( response => {
              let { code,data } = response.data;
              if(code==200){
                  for( let index in data.list ){
                      data.list[index].crtTime = new Date( data.list[index].crtTime ).format( "yyyy-MM-dd hh:mm:s" )
                  }
                  this.customers = data.list;
                  this.page.pageTotal = data.total
              }
          })
      },
    handlePage(value) {
        this.page.pageNum = value
        this.queryList()
    },
    handlePageSize(value) {
        this.page.pageSize = value
        this.queryList()
    },
    getCustomerId(id,name) {
      let customer = {
        id: id,
        name: name
      };
      this.$emit('selectCustomer',customer)      
    },
  }
};
</script>

<style scoped>
.radio{
    position:absolute;
    top:48px;
    left:-20px;
}
.infoList{
    border: 1px solid #dddfe1;
    width: 200px;
    height: 100px;
    margin: 10px auto;
    border-radius: 4px;
    box-shadow: 5px 6px 4px rgba(0, 0, 0, .2);
    position: relative;
}
.company{
    padding-left: 10px;
    font-size: 20px;
}
.contact,.tel{
    padding-left: 10px;
    display: inline-block;
    line-height: 45px;
}
.crtTime{
    padding: 0px 0px 0px 10px;
}
.conditions{
     padding: 10px;
}
.list{
    margin-top: 10px;
}
.ivu-icon {
    margin-right: 5px;
    color: #ff9b00;
}
</style>

