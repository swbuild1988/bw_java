<template>
    <div class="top-div">
        <Row>
            <Col span="16">
                <span class="planDec">所属管廊</span>
                <Select v-model="selectedTunnelId" style="width: 150px;margin-right: 15px;font-size: 14px;" @on-change='changeTunnel()'>
                    <Option value="0" key="0">所有</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
                <span class="planDec">设备类型</span>
                <Select v-model="selectedEquipmentTypeKey" style="width: 150px">
                    <Option value="0" key="0">所有</Option>
                    <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
            </Col>
            <Col span="8" align="right">
            <Button class="btn" @click="changeTunnel()">查询</Button>
            <Button class="btn">导出</Button>
            <!-- <Button class="btn" @click.native="goToMoudle({ path:'/UM/main/addPlan'})">添加</Button> -->
            </Col>
        </Row>
        <hr>
    </div>
</template>
<script>
import axios from "axios";
import types from '../../../../../static/Enum.json'
export default {
    data () {
        return {  
            selectedTunnelId:0,
            tunnels: [],
            originalTunnels: [],
            equipmentTypes:[],
            selectedEquipmentTypeKey:0,
        }
    },
    mounted(){
        //从数据库读取select的option选项
        axios.get("/tunnels ").then(response =>{
            let { code, data } = response.data;
            if (code == 200) {
                this.originalTunnels = data;
                this.tunnels = data;
                this.selectedTunnel = this.tunnels.length > 0 ? this.tunnels[0] : {};
                this.selectedTunnelName = this.selectedTunnel.name;
            }
        });
        
        this.equipmentTypes = types.equipmentType;
    },
    methods: {
        goToMoudle: function (index) {
           
        },
        //将选中的option传到父组件
        changeTunnel(){
            let selectedTunnelId = this.selectedTunnelName;
            this.$emit('changeTunnel',selectedTunnelId);
        }
    }    
}
</script>

<style scoped>
    .planDec {
        padding-right: 5px;
        font-size: 14px;
    }
    hr{
        margin-top: 20px;
        margin-bottom: 40px;
    }
</style>

