<template>
      <div class="InformationContainer BackgroundImage">
        <div class="Information" :id=tableid>
          <Table
                  stripe
                  disabled-hover
                  :height="offsetHeight"
                  :columns="columns"
                  :show-header="false"
                  :data="tableData">
          </Table>
        </div>
        <vm-title :title-text="title"></vm-title>
      </div>
</template>

<style scoped>
  .Information{
    position: relative;
    width: 100%;
    height: 85%;
    top: 15%;
  }
  /*iview table*/
  .Information >>> .ivu-table-wrapper{
    border: 1px solid transparent;
    border-bottom: 0;
    border-right: 0;
  }
  .Information >>> .ivu-table {
    color: #E1E4E5;
    background-color: transparent;
  }
  .Information >>> .ivu-table td{
    background-color: transparent;
    border-bottom: transparent;
  }
  .Information >>> .ivu-table-stripe .ivu-table-body tr:nth-child(2n) td{
    background: transparent;
  }
  .Information >>> .ivu-table:after,.Information >>> .ivu-table:before{
    background-color: transparent;
  }
  .Information >>> .ivu-table-row td:nth-child(2){
    color: #00FC41;
    font-weight: bold;
    text-align: right;
  }
</style>

<script>
    import VmTitle from '../VMTitle'
    export default {
        name:'Information',
        props:['tableid'],
        data() {
            return {
                title: '入廊信息',
                tableData: [{
                data: '12',
                name: '电力公司电缆',
                unit: 'km',
              }, {
                data: '3',
                name: '自来水公司',
                unit: '个'
              }, {
                data: '4',
                name: '燃气公司',
                unit: '个'
              }, {
                data: '6',
                name: '中国移动',
                unit: '条'
              }],
                columns: [
                    {
                        key: 'name'
                    },
                    {
                        key: 'data'
                    },
                    {
                        key: 'unit'
                    }
                ],
                offsetHeight:0
            }
        },
        components:{
            VmTitle
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                this.offsetHeight=document.getElementById(this.tableid).offsetHeight;
            },

        }
    }

</script>
