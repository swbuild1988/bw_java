<template>
    <div>
        <!-- 区域管理之修改区域 -->
        <Modal v-model="show.state" :title='title'>
            <Form ref="areaFormInfo" :model="areaFormInfo" :rules="ruleValidate" :label-width="100">
                <FormItem label="区域名称：" prop="name">
                    <Input v-model="areaFormInfo.name" placeholder="请输入区域名"/>
                </FormItem>
                <FormItem label="区域编号：" prop="sn">
                    <Input v-model="areaFormInfo.sn" placeholder="请输入区域编号"/>
                </FormItem>
                <FormItem label="所属管廊：" prop="tunnelId">
                    <Select v-model="areaFormInfo.tunnelId" placeholder="请选择所属管廊">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <!-- <FormItem label="相机视角：" prop="camera">
                    <Row>
                        <Col span="7">
                            <Input v-model="areaFormInfo.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="areaFormInfo.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="areaFormInfo.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem> -->
                <FormItem label="长度：" prop="length">
                    <Input v-model="areaFormInfo.length" placeholder="请输入长度"></Input>
                </FormItem>
                <span v-show="!flag" class="errorStyle">任务名和任务组需唯一</span>
            </Form>
            <div slot="footer">
                <Button type="primary" v-if="type==1" size="large" v-on:click="saveAdd('areaFormInfo')">保存1</Button>
                <Button type="primary" v-if="type==2" size="large" v-on:click="saveEdit('areaFormInfo')">保存2</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { AreaService } from '@/services/areaService'
export default {
    name: 'area-modification',
    props:{
        show:{
            state:{
                default: false
            }
        },
        type: null
    },
    data(){
        return {
            tunnels:[],
            flag:true,
            areaFormInfo:{
                name:'',
                tunnelId: null,
                sn: null,
                length: null
            },
            ruleValidate:{
                name: [
                    { required: true, message: '区域名称不能为空', trigger: 'blur' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '所属管廊不能为空', trigger: 'change' }
                ],
                sn: [
                    { required: true, message: '区域编号不能为空', trigger: 'blur' }
                ],
                length: [
                    { required: true, message: '长度不能为空', trigger: 'blur' }
                ]
            },
            isRefresh: false,
            title: null
        }
    },
    watch:{
        'type': function(newVal){
            this.changeTitle(newVal)
        }
    },
    computed:{
        params(){
            let param = {
                name: this.areaFormInfo.name,
                tunnelId: this.areaFormInfo.tunnelId,
                camera: this.areaFormInfo.camera,
                length: this.areaFormInfo.length,
                sn: this.areaFormInfo.sn
            }
            return Object.assign({},param)
        }
    },
    mounted(){
        // this.getTunnelList();
        //获取所有管廊的简单列表
        AreaService.getTunnel().then(
            result => {
                this.tunnels = result
            },
            error => {
                this.$Message.error(error)
            }
        )
    },
    methods:{
        // sendMsg: function(name){
        //     this.$refs[name].validate((valid) => {
        //         if (valid) {
        //             //拼接相机视角字符串
        //             // this.areaFormInfo.camera = this.areaFormInfo.longitude + ',' + this.areaFormInfo.latitude + ',' + this.areaFormInfo.highness;
        //             this.$emit("listenToChange");
        //         }else{
        //             this.$Message.error("修改失败!");
        //         }
        //     })
        // },
        checkName(name){
            this.axios.get('/areas/ajax/' + name).then(res =>{
                let{code,data} = res.data;
                if(code == 200){
                    this.flag = data;
                }
            })
        },
        //获取area信息
        getAreasInfo(id){
            AreaService.getAreaInfo(id).then(
                result => {
                    this.areaFormInfo = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        saveAdd(name){
            this.$refs[name].validate((valid) => {
                if(valid){
                    AreaService.addArea(this.params).then(
                        result => {
                            this.isRefresh = true
                            this.$emit('childIsRefresh', this.isRefresh)
                            this.$Message.success("新增成功！")
                        },
                        error => {
                            this.Log.info( error )
                        }
                    )
                }else{
                    this.$Message.error("新增失败")
                }
            })
        },
        saveEdit(name){
            this.$refs[name].validate((valid) => {
                if(valid){
                    AreaService.editArea(this.params).then(
                        result => {
                            this.isRefresh = true
                            this.$emit('childIsRefresh', this.isRefresh)
                            this.$Message.success("修改成功！")
                        },
                        error => {
                            this.Log.info( error )
                        }
                    )
                }else{
                    this.$Message.error('修改失败')
                }
            })
        },
        handleReset(name){
            this.$refs[name].resetFields()
        },
        changeTitle(type){
            if(type==1){
                this.title = '新增区域'
                this.handleReset('areaFormInfo')
            }
            if(type==2){
                this.title = '编辑区域信息'
            }
        }
    }
}
</script>

<style scoped>
.errorStyle{
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 100px;
    left: 140px;
}
</style>

