<!-- 仪表工具单个出借 -->
<template>
    <div>
        <Modal title="出借" v-model="show.state" width="500px" @on-ok="ok">
            <div>
                <Row style="margin:12px;">
                    <Col span="24">
                        <span>&emsp;借用人：</span>
                        <AutoComplete v-model="username.name" :data="data5" :filter-method="filterMethod" style="width:80%"></AutoComplete>
                    </Col>
                </Row>
                <Row style="margin:12px;">
                    <Col span="24">
                        <span>归还时间：</span>
                        <DatePicker type="date" v-model="ReturnDate" style="width:80%"></DatePicker>
                    </Col>
                </Row>
                <Row style="margin:12px;">
                    <Col span="24">
                        <span>借用目的：</span>
                        <Input v-model="value5" type="textarea" style="width:80%;"/>
                    </Col>
                </Row>
            </div>
        </Modal>
    </div>
</template>

<script>
// import { getFormatTime } from '../../../scripts/DateFormat.js';

export default {
    name: "pop-box-out-storage",
    props: {
        show:{
            type: Object,
        },
        equipmentLoanInfo:{
            //届时需要传来选中的设备信息
            default:{}
        }
    },
    data(){
        return {
            username: {},
            ReturnDate: null,
            value5:'',
            data5: ['admin','Jack','Molly','Susan']
        }
    },
    watch: {
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
            console.log("借出"+this.equipmentLoanInfo.name);
        },
        filterMethod (value, option) {
            return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
        }
    }
}
</script>

<style scoped>
</style>


