<!-- 仪表工具单个归还 -->
<template>
    <div>
        <Modal title="归还" v-model="show.state" width="500px" @on-ok="ok">
            <div>
                <Row style="margin:12px;">
                    <Col span="24">
                        <span>&emsp;归还人：</span>
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
                        <span>设备状态：</span>
                        <Select v-model="model1" style="width:80%">
                            <Option v-for="item in cityList" :value="item.value" :key="item.value">{{item.label}}</Option>
                        </Select>
                    </Col>
                </Row>
            </div>
        </Modal>
    </div>
</template>

<script>
export default {
    name: "pop-box-return-storage",
    props:{
        show:{
            type: Object,
        },
        equipmentReturnInfo:{
            //届时需要传来选中的设备信息
            default:{}
        }
    },
    data(){
        return {
            username: {},
            ReturnDate: null,
            model1:'',
            data5: ['admin','Jack','Molly','Susan'],
            cityList: [
                {
                    value: "noDamage",
                    label: "无损坏"
                },
                {
                    value: "littleDamage",
                    label: "轻微损坏"
                },
                {
                    value: "badlyDamage",
                    label: "严重损坏"
                }
            ]
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
            console.log("归还"+this.equipmentReturnInfo.name);
        },
        filterMethod (value, option) {
            return option.toUpperCase().indexOf(value.toUpperCase()) !== -1;
        }
    }
}
</script>

<style scoped>

</style>


