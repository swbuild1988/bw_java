<template>
    <!--仪表工具批量借出-->
    <div>
        <Modal title="批量借出" v-model="show.state" width="800">
            <Row style="margin:12px;">
                <Col span="12">
                    <span class="planDec">&emsp;借用人：&emsp;</span>
                    <AutoComplete v-model="username.name" :data="data5" :filter-method="filterMethod" style="width:70%"></AutoComplete>
                </Col>
                <Col span="12">
                    <span class="planDec">&emsp;&emsp;&emsp;归还时间：</span> 
                    <DatePicker type="date" v-model="ReturnDate" style="width:70%"></DatePicker>
                </Col>
            </Row>
            <Row style="margin:12px;">
                <Col span="24">
                    <span class="planDec">&emsp;借用目的：</span>
                    <Input v-model="value5" type="textarea" style="width:89%;"/>
                </Col>
            </Row>
            <Table style="margin-top: 10px;" stripe border ref="selection" :columns="tableColumns" @on-selection-change="selectEquipment"
                   :data="tableData"></Table>
        </Modal>
    </div>
</template>

<script>
export default {
    name: "batch-loan",
    props: {
        show:{
            type: Object,
        },
        equipments:{
        }
    },
    data(){
        return {
            username: {},
            ReturnDate: null,
            value5:'',
            data5: ['admin','Jack','Molly','Susan'],
            tableColumns: [
            {
                type: 'selection',
                width: 60,
                align: 'center'
            },
            {
                title: '设备编号',
                key: 'No'
            },
            {
                title: '设备名称',
                key: 'Name'
            },
            {
                title: '设备类型',
                key: 'Type'
            },
            {
                title: '状态',
                key: 'status'
            },
            {
                title: '入库时间',
                key: 'time'
            }],
            tableData: [
            {
                No: "001",
                Name: "海康摄像头1",
                time: "2018-10-1",
                Type: "视频设备",
                status: "良好",
            },
            {
                No: "002",
                Name: "海康摄像头2",
                time: "2018-10-1",
                Type: "视频设备",
                status: "良好",
            },
            {
                No: "003",
                Name: "海康摄像头3",
                time: "2018-10-1",
                Type: "视频设备",
                status: "良好",
            }],
        }
    },
    mounted(){
        this.getNowUser();
        this.getNowDate();
    },
    methods:{
        getNowUser(){
            //从sessionStorage获取当前用户
            this.username = JSON.parse(sessionStorage.getItem('UMUser'));
        },
        getNowDate(){
            let date = new Date();
            let seperator1 = "-";
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            let currentdate = year + seperator1 + month + seperator1 + strDate;
            this.ReturnDate = currentdate;
            },
        ok(){
            //根据选中设备的信息进行借出操作
            console.log("批量借出"+this.equipmentLoanInfo.name);
        },
        filterMethod (value, option) {
            return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
        },
        selectEquipment(arr) {
        // console.log(arr);
        },
    }
}
</script>

<style scoped>
  .vertical-center-modal {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .vertical-center-modal > .ivu-modal {
    top: 0;
  }
</style>


