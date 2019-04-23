<template>
    <!-- 管廊管理的修改管廊 -->
    <div>
       <Modal v-model="show.state" title="修改管廊" width='700'>
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="120">
                <FormItem label="管廊名：" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入新管廊名" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="管廊长度：" prop="length">
                    <Input v-model="formValidate.length" placeholder="请输入新管廊长度" class="InputWidth"></Input> 
                </FormItem>
                <FormItem label="管廊方向：" prop="direction">
                    <Poptip trigger="hover" title="管廊方向说明" placement="top">
                        <img slot="content" :src="directionExplation" placement="top" alt="管廊方向说明图" style="height: 200px;">
                        <Select v-model="formValidate.direction" placeholder="请选择管廊方向" style="width: 100%">
                            <Option v-for="item in tunnelDirection" :key="item.id" :value="item.value">
                                {{item.direction}}
                                <img :src="item.pic" :alt=item.direction class="directionImg"/>
                            </Option>
                        </Select>
                    </Poptip>
                </FormItem>
                <FormItem label="起点：" prop="startPoint">
                    <Row>
                        <Col span="5">
                            <Input v-model="startPoint.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="startPoint.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="startPoint.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="终点：" prop="endPoint">
                    <Row>
                        <Col span="5">
                            <Input v-model="endPoint.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="endPoint.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="endPoint.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="负责人：" prop="responsibilityId">
                    <Select v-model="formValidate.responsibilityId" placeholder="请选择负责人" class="InputWidth">
                        <Option v-for="(item,index) in staffs" :value="item.id" :key="index">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="建筑单位：" prop="constructId">
                    <Select v-model="formValidate.constructId" placeholder="请选择建筑单位" class="InputWidth">
                        <Option v-for="(item,index) in companies" :value="item.id" :key="index">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="运营单位：" prop="operationId">
                    <Select v-model="formValidate.operationId" placeholder="请选择运营单位" class="InputWidth">
                        <Option v-for="(item,index) in companies" :value="item.id" :key="index">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="MaxView终端：" prop="maxviewConfigId">
                    <Select v-model="formValidate.maxviewConfigId" placeholder="请选择MaxView终端" class="InputWidth">
                        <Option v-for="item in terminals" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsgtoManage('formValidate')">保存</Button>
            </div>
        </Modal> 
    </div>
</template>

<script>
import {PipeService} from '@/services/pipeService'
export default {
    name: 'pipe-modification',
    data(){
        return {
            staffs:[],
            companies:[],
            terminals: [],
            formValidate: {
                name: null,
                length: null,
                responsibilityId: null,
                constructId: null,
                operationId: null,
                maxviewConfigId: null,
                startPoint: null,
                endPoint: null,
                direction: null
            },
            startPoint: {
                longitude: null,
                latitude: null,
                highness: null
            },
            endPoint: {
                longitude: null,
                latitude: null,
                highness: null
            },
            ruleValidate: {
                name: [
                    { required: true, message: '管廊名不能为空', trigger: 'blur' }
                ],
                length: [
                    { type: 'number', required: true, message: '管廊长度不能为空', trigger: 'blur' }
                ],
                direction: [
                    { type: 'number', required: true, message: '管廊方向不能为空', trigger: 'blur' }
                ],
                responsibilityId: [
                    { type: 'number', required: true, message: '负责人不能为空', trigger: 'blur' }
                ],
                constructId: [
                    { type: 'number', required: true, message: '建筑单位不能为空', trigger: 'blur' }
                ],
                operationId: [
                    { type: 'number', required: true, message: '运营单位不能为空', trigger: 'blur' }
                ],
                maxviewConfigId: [
                    { type: 'number', required: true, message: 'MaxView终端不能为空', trigger: 'blur' }
                ]
            },
            tunnelDirection: [
                { id: 1, direction: '东北方向', pic: require('@/assets/CM/en.png') , value: 1 },
                { id: 2, direction: '西南方向', pic: require('@/assets/CM/ws.png') , value: 2 },
                { id: 3, direction: '西北方向', pic: require('@/assets/CM/wn.png') , value: 3 },
                { id: 4, direction: '东南方向', pic: require('@/assets/CM/es.png') , value: 4 }
            ],
            directionExplation: require('@/assets/CM/directExplain.png')
        }
    },
    props:{
        show:{
            state:{
                default: false
            },
        }
    },
    mounted(){
        this.getStaffs()

        PipeService.getCompanies().then(
            result => {
                this.companies = result;
            },
            error => {
                this.Log.info(error)
            }
        )               

        // maxview终端
        PipeService.getMaxView().then(
            result => {
                this.terminals = result
            },
            error => {
                this.Log.info(error)
            }
        )
    },
    methods:{
        sendMsgtoManage: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.formValidate.startPoint = this.startPoint.longitude+','+this.startPoint.latitude+','+this.startPoint.highness
                    this.formValidate.endPoint = this.endPoint.longitude+','+this.endPoint.latitude+','+this.endPoint.highness
                    console.log(this.formValidate)
                    PipeService.saveEditTunnelInfo(this.formValidate).then(
                        result => {
                            this.$emit("sendMsgtoManage")
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                } else {
                    this.$Message.error('修改失败');
                }
            })
        },
        //负责人
        getStaffs() {
            var param = {
                outside: 1
            }
            PipeService.getStaffs(param).then(
                result => {
                    this.staffs = result;
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //获取管廊详情
        getTunnelInfo(id){
            PipeService.getTunnelInfo(id).then(
                result => {
                    this.formValidate = result
                    this.formValidate.constructId = result.construct.id
                    this.formValidate.maxviewConfigId = result.maxviewConfig.id
                    this.formValidate.operationId = result.operation.id
                    // this.formValidate.responsibilityId = result.responsibility.id
                    if(result.startPoint!=null){
                        var startArray = result.startPoint.split(",")
                        this.startPoint.longitude = startArray[0]
                        this.startPoint.latitude = startArray[1]
                        this.startPoint.highness = startArray[2]
                    }
                    if(result.endPoint!=null){
                        var endArray = result.endPoint.split(",")
                        this.endPoint.longitude = endArray[0]
                        this.endPoint.latitude = endArray[1]
                        this.endPoint.highness = endArray[2]
                    }
                },
                error => {
                    this.Log.info(error)
                }
            )
        }
    }
}
</script>

<style scoped>
.InputWidth,.ivu-poptip{
    width: 85%
}
.ivu-poptip >>> .ivu-poptip-rel{
    width: 100%;
}
</style>



