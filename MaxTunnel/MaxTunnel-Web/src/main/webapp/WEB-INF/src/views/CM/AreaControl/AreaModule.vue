<template>
    <div>
        <!-- 区域管理之新增区域 -->
        <Modal v-model="show.state" title="新增区域">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
                <FormItem label="区域名称：" prop="name" class="formStyle">
                    <Input v-model="formValidate.name" placeholder="请输入区域名" class="inputStyle"/>
                </FormItem>
                <FormItem label="区域编号：" prop="sn" class="formStyle">
                    <Input v-model="formValidate.sn" placeholder="区域编号由0-9的数字组成" class="inputStyle"/>
                </FormItem>
                <FormItem label="所属管廊：" prop="tunnelId" class="formStyle">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="inputStyle">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="相机视角：" class="formStyle">
                    <Row>
                        <Col span="5">
                        <FormItem prop="longitude">
                            <Input v-model="formValidate.longitude" placeholder="请输入经度"/>
                        </FormItem>
                        </Col>
                        <Col span="5" offset="1">
                            <FormItem prop="latitude">
                                <Input v-model="formValidate.latitude" placeholder="请输入纬度"/>
                            </FormItem>
                        </Col>
                        <Col span="5" offset="1">
                            <FormItem prop="highness">
                                <Input v-model="formValidate.highness" placeholder="请输入高度"/>
                            </FormItem>
                        </Col>
                    </Row>
                </FormItem>
                <span v-show="!flag" class="errorStyle">任务名和任务组需唯一</span>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
export default {
    name: "area-add",
    data(){
        return{
            tunnels:[],
            flag:true,
            formValidate:{
                name:'',
                tunnelId:null,
                tunnelName:'',
                camera:'',
                longitude:null,
                latitude:null,
                highness:null,
                sn: null
            },
            ruleValidate:{
                name: [
                    { required: true, message: '区域名称不能为空', trigger: 'blur' }
                ],
                sn:[
                    { required: true, message: '区域编号不能为空', trigger: 'blur' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '所属管廊不能为空', trigger: 'change' }
                ],
                longitude: [
                    { required: true, message: '经度不能为空', trigger: 'blur' },
                    { validator: this.checkLongitude, trigger: 'blur' }
                ],
                latitude: [
                    { required: true, message: '纬度不能为空', trigger: 'blur' },
                    { validator: this.checklatitude, trigger: 'blur' }
                ],
                highness: [
                    { required: true, message: '高度不能为空', trigger: 'blur' },
                    { validator: this.checkHighness, trigger: 'blur' }
                ]
            },
            re: /^\d+(\.\d+)?$/
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        addInfo:{}
    },
    watch:{
        'show.state':function(newValue,oldValue){
            this.cleanFormValidate();
        },
        'formValidate.name':function(newValue,oldValue){
            if(newValue != null){
                this.checkName(newValue);
            }
        }
    },
    mounted(){
        this.getTunnelList();
    },
    methods:{
        sendMsg: function(name){
            if(this.flag){
                this.$refs[name].validate((valid) => {
                    if(valid) {
                        //给相机视角和坐标拼接字符串
                        this.formValidate.camera = this.formValidate.longitude + ',' + this.formValidate.latitude + ',' + this.formValidate.highness;
                        this.$emit("listenToAdd",this.formValidate);
                    } else {
                        this.$Message.error('添加失败');
                    }
                })
            }
        },
        getTunnelList(){        //获取所有管廊的简单列表
            this.axios.get('/tunnels').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.tunnels = data;
                }
            })
        },
        checkName(name){
            this.axios.get('/areas/ajax/' + name).then(res =>{
                let{code,data} = res.data;
                if(code == 200){
                    this.flag = data;
                }
            })
        },
        cleanFormValidate(){
            this.formValidate.name = null;
            this.formValidate.tunnelId = null;
            this.formValidate.tunnelName = null;
            this.formValidate.location = null;
            this.formValidate.camera = null;
            this.formValidate.longitude = null;
            this.formValidate.latitude = null;
            this.formValidate.highness = null;
            this.formValidate.sn = null;
            this.flag = true;
        },
        checkLongitude(rule, value, callback){
            if(!this.re.test(this.formValidate.longitude)){
                callback(new Error("请输入正确的经度"))
            }else{
                callback()
            }
        },
        checklatitude(rule, value, callback){
            if(!this.re.test(this.formValidate.latitude)){
                callback(new Error("请输入正确的纬度"))
            }else{
                callback()
            }
        },
        checkHighness(rule, value, callback){
            if(!this.re.test(this.formValidate.highness)){
                callback(new Error("请输入正确的高度"))
            }else{
                callback()
            }
        }
    }    
}
</script>

<style scoped> 
.inputStyle{
    width:70%;
}
.formStyle{
    margin-left: 32px;
}
.errorStyle{
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 100px;
    left: 140px;
}
</style>

