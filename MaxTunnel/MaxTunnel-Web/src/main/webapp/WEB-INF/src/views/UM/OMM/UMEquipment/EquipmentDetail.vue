<template>
    <div>
        <Row>
            <Col span="12">
                <Form :model="equipment" :label-width="100" @submit.native.prevent>
                    <Row>
                        <Col span="12">
                            <FormItem label="设备编号">
                                <Input v-model='equipment.assetNo' :disabled='pageType!=pageTypes.Add'></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备名称">
                                <Input v-model='equipment.name' :disabled='pageType==pageTypes.Read'></Input>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem label="所属管廊">
                                <Select v-model='equipment.tunnelId' :disabled='pageType==pageTypes.Read'>
                                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备类型">
                                <Select v-model='equipment.type' :disabled='pageType==pageTypes.Read'>
                                    <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem label="投运时间">
                                <Input v-model='equipment.startTimeFormat' :disabled='pageType==pageTypes.Read'></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备状态">
                                <Select v-model='equipment.status' :disabled='pageType==pageTypes.Read'>
                                    <Option v-for="item in equipmentStatus" :value="item.key" :key="item.key">{{ item.value }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="12">
                            <FormItem label="供应商">
                                 <Select v-model='equipment.venderId' :disabled='pageType==pageTypes.Read'>
                                    <Option v-for="item in venders" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label="设备型号" >
                                <Select v-model='equipment.modelId' :disabled='pageType==pageTypes.Read'>
                                    <Option v-for="item in equipmentModels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                                </Select>
                            </FormItem>
                        </Col>
                    </Row>
                    <Row>
                        <FormItem label="设备位置">
                            <Input v-model='equipment.tunnelName' :disabled='pageType==pageTypes.Read'></Input>
                        </FormItem>
                    </Row>
                    <Row>
                        <FormItem label="维修记录">
                            <Input v-model='equipment.maintainLog' type="textarea" :rows="5" :disabled='pageType==pageTypes.Read' v-show="pageType!=pageTypes.Create"></Input>
                        </FormItem>
                    </Row>
                </Form>
            </Col>
            <Col span="12">
                
            </Col>    
        </Row>
    </div>
</template>
<script>

import axios from "axios";
import types from '../../../../../static/Enum.json'

export default {
    data(){
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            // 待编辑的设备
            equipment:{
                id: 1,
                assetNo: 'NO1',
                name: "设备1",
                modelId: 1,
                venderId: 1,
                type: 1,
                tunnelId: 1,
                startTime: new Date(),
                status: 1,
            },
            // 设备型号
            equipmentModels: [
                {
                    id:1,
                    name:"model1"
                },
                {
                    id:2,
                    name:"model2"
                },
                {
                    id:3,
                    name:"model3"
                },
            ],
            // 供应商
            venders: [
                {
                    id:1,
                    name:"张三"
                },
                {
                    id:2,
                    name:"李四"
                },
                {
                    id:3,
                    name:"王五"
                },
            ],
            // 管廊
            tunnels:[],
            // 设备类型
            equipmentTypes: types.equipmentType,
            // 设备状态
            equipmentStatus: types.equipmentStatus,
        }
    },
    mounted(){
        this.equipment.id =  this.$route.params.id;
        this.pageType = this.$route.params.type;
        this.init();
    },
    methods: {
        init: function(){
            if (this.equipment.id == undefined) this.equipment.id = 1

            if (this.pageType != this.pageTypes.Add) {
                // 获取设备详细信息
                axios.get("/equipments/" + this.equipment.id).then(response =>{
                    let {code, data} = response.data;
                    if (code == "200") {
                        this.equipment = data;
                        this.equipment.startTime = new Date(data.startTime);
                        this.equipment.startTimeFormat = this.equipment.startTime.format("yyyy-MM-dd");
                    }
                });
            }

            // 获取所有的管廊
            axios.get("/tunnels ").then(response =>{
                let { code, data } = response.data;
                if (code == 200) {
                    this.tunnels = data;
                }
            });

            // 获取所有的供应商
            axios.get("/venders").then(response =>{
                let { code, data } = response.data;
                if (code == 200) {
                    this.venders = data;
                }
            });

            // 获取所有的型号
            axios.get("/equipment-models").then(response =>{
                let { code, data } = response.data;
                if (code == 200) {
                    this.equipmentModels = data;
                }
            });
        }
    }
}
</script>

