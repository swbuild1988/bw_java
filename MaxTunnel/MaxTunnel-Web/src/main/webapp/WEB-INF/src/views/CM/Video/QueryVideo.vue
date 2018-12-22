<template>
    <div>
        <h1 style="margin: 10px 0px 0px 20px;">查询视频</h1>
        <Row style="margin:20px;">
            <Col span="6">
                供应商：
                <Select style="width: 60%">
                    <Option value="null">所有</Option>
                </Select>
            </Col>
            <Col span="6">
                <Button type="primary" size="small" @click="addVideoService=true">添加视频</Button>
            </Col>
        </Row>
        <div style="margin:20px;">
            <Table stripe border :columns="columns1" :data="data1"></Table>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
            placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
            :style='pageStyle'></Page>
        </div>
        <Modal 
            v-model="addVideoService" 
            width='900' 
            style="padding-left: 20px;padding-right: 20px;"
            title="添加视频">
            <Form ref="video" :model="video" :rules="ruleValidate"  label-position="top">
                <Row>
                    <Col span="12" style="padding: 10px">
                        <FormItem label="ID" prop="id">
                            <Input v-model="video.id" placeholder="请输入视频名称"></Input>
                        </FormItem>
                        <FormItem label="所属管廊" prop="tunnelId">
                            <Select v-model="video.tunnelId">
                                <Option v-for="item in tunnels" :key="item.id" :value="item.tunnelId">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属仓段" prop="areaName">
                            <Select v-model="video.areaName">
                                <Option v-for="item in tunnels" :key="item.id" :value="item.tunnelId">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属区段" prop="storeName">
                            <Select v-model="video.storeName">
                                <Option v-for="item in tunnels" :key="item.id" :value="item.tunnelId">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="视频名称" prop="videoName">
                            <Input v-model="video.videoName" placeholder="请输入视频名称"></Input>
                        </FormItem>
                        <FormItem label="视频描述" prop="videoDesc">
                            <Input type="textarea" v-model="video.videoDesc"></Input>
                        </FormItem>
                    </Col>
                    <Col span="12" style="padding: 10px">
                        <FormItem label="视频对象类型" prop="videoObjectType">
                            <Input v-model="video.videoObjectType" placeholder="请输入视频对象类型"></Input>
                        </FormItem>
                        <FormItem label="视频数据类型" prop="videoDataType">
                            <Input v-model="video.videoDataType" placeholder="请输入视频数据类型"></Input>
                        </FormItem>
                        <FormItem label="所属服务商" prop="supplier">
                            <Select v-model="video.supplier">
                                <Option v-for="item in tunnels" :key="item.id" :value="item.tunnelId">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属通道号" prop="channelNumber">
                            <Select v-model="video.channelNumber">
                                <Option v-for="item in tunnels" :key="item.id" :value="item.tunnelId">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="所属场景" prop="scenario">
                            <Select v-model="video.scenario">
                                <Option v-for="item in tunnels" :key="item.id" :value="item.tunnelId">{{item.name}}</Option>
                            </Select>
                        </FormItem>
                        <FormItem label="是否使用" prop="isUser">
                            <Select v-model="video.scenario">
                                <Option v-for="item in idUsers" :key="item.id" :value="item.key">{{item.val}}</Option>
                            </Select>
                        </FormItem>
                    </Col>    
                </Row>
            </Form> 
            <div slot="footer">
                <Button type="primary" @click="submitForm('video')">提交</Button>
                <Button type="default" @click="cancel('video')">取消</Button>
            </div>  
        </Modal>
    </div>    
</template>
<script>
import { TunnelService } from '../../../services/tunnelService'
export default {
    data(){
        return{
            columns1:[
                {
                    title: 'ID',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: '所属管廊',
                    key: 'tunnelId',
                    align: 'center'
                },
                {
                    title: '所属管仓',
                    align: 'center',
                    render: (h,params) =>{
                        let temp = params.row.area.name
                        if(temp!=undefined){
                            return h('span',params.row.area.name)
                        }
                    }
                },
                {
                    title: '所属区段',
                    align: 'center',
                    render: (h,params) => {
                        let temp = params.row.store.name
                        if(temp!=undefined){
                            return h('span',params.row.store.name)
                        }
                    }
                },
                {
                    title: '视频名称',
                    key: 'videoName',
                    align: 'center'
                },
                {
                    title: '视频对象类型',
                    key: 'videoObjectType',
                    align: 'center'
                },
                {
                    title: '视频数据类型',
                    key: 'videoDataType',
                    align: 'center'
                },
                {
                    title: '所属服务商',
                    key: 'supplier',
                    align: 'center'
                },
                {
                    title: '所属通道号',
                    key: 'channelNumber',
                    align: 'center'
                },
                {
                    title: '所属场景',
                    key: 'scenario',
                    align: 'center'
                },
                {
                    title: '视频描述',
                    key: 'videoDesc',
                    align: 'center'
                },
                {
                    title: '是否使用',
                    key: 'isUser',
                    align: 'center'
                },
            ],
            data1:[
                {id:1,tunnelId: 1,area:{id: 1, name: '污水仓'},store:{id:1,name:'十二段'},videoName:'电力仓视频',videoObjectType:3,
                videoDataType:4,supplier:3,channelNumber:2,scenario:1,videoDesc:'可以正常投入使用',isUser:0}
            ],
            page: {
                pageNum: 1,
                pageSize: 10,
                pageTotal: 0,
            },
            pageStyle: {
                position: 'absolute',
                bottom: '35px',
                right: '40px'
            },
            addVideoService: false,
            video:{
                id: null,
                tunnelId: null,
                areaName: null,
                storeName: null,
                videoName: null,
                videoObjectType: null,
                videoDataType: null,
                supplier: null,
                channelNumber: null,
                scenario: null,
                videoDesc: null,
                isUser: 0
            },
            ruleValidate:{
                id: [
                    { required: true, message: 'ID不能为空', trigger: 'blur' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '请选择所属管廊', trigger: 'change' }
                ],
                areaName: [
                    { type: 'number', required: true, message: '请选择所属仓段', trigger: 'change' }
                ],
                storeName: [
                    { type: 'number', required: true, message: '请选择所属区段', trigger: 'change' }
                ],
                videoName: [
                    { required: true, message: '视频名称不能为空', trigger: 'blur' }
                ],
                videoObjectType: [
                    { type: 'number', required: true, message: '请选择视频对象类型', trigger: 'change' }
                ],
                videoDataType: [
                    { type: 'number', required: true, message: '请选择视频数据类型', trigger: 'change' }
                ],
                supplier: [
                    { type: 'number', required: true, message: '请选择所属服务商', trigger: 'change' }
                ],
                channelNumber: [
                    { type: 'number', required: true, message: '请选择所属通道号', trigger: 'change' }
                ],
                scenario: [
                    { type: 'number', required: true, message: '请选择所属场景', trigger: 'change' }
                ],
                videoDesc: [
                    { required: true, message: '视频描述不能为空', trigger: 'blur' }
                ],
                isUser: [
                    { type: 'number', required: true, message: '请选择是否使用', trigger: 'change' }
                ],
            },
            suppliers:[
                {key:1,val:'视频厂商1'},
                {key:2,val:'视频厂商2'},
                {key:3,val:'视频厂商3'},
            ],
            idUsers:[
                { key: 0, val: '否' },
                { key: 1, val: '是' }
            ],
            tunnels:[]
        }
    },
    mounted(){
        TunnelService.getTunnels().then(
            (result) => {
                this.tunnels = result
            },
            (error) => {
                this.Log.info(error)
            }
        )
    },
    methods:{
        submitForm(name){
            this.$refs[name].validate((valid)=>{
                if(valid){
                    this.addVideoService=false
                }
            })
        },
        cancel(name){
            this.$refs[name].resetFields()
            this.addVideoService=false
        },
    }
}
</script>
<style scoped>

</style>
