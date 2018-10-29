<template>
    <div>
        <!-- 区域管理之修改区域 -->
        <Modal v-model="show.state" title="修改区域" :label-width="100">
            <Form ref="formValidate" :model="formValidate">
                <FormItem label="区域名称：" prop="name" class="formStyle">
                    <Input v-model="formValidate.name" placeholder="请输入区域名" class="inputStyle"/>
                </FormItem>
                <!-- <FormItem label="区域位置：" prop="location" class="formStyle">
                    <Input v-model="formValidate.location" placeholder="请输入区域位置" class="inputStyle"/>
                </FormItem> -->
                <FormItem label="所属管廊：" prop="tunnelId" class="formStyle">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="inputStyle">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="相机视角：" prop="camera" class="formStyle">
                    <Row>
                        <Col span="5">
                            <Input v-model="formValidate.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="formValidate.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="formValidate.highness" placeholder="请输入高度"/>
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
    name: 'area-modification',
    data(){
        return {
            tunnels:[],
            flag:true,
            formValidate:{
                name:'',
                // location:null,
                tunnelId:null,
                tunnelName:'',
                camera:'',
                longitude:null,
                latitude:null,
                highness:null
            },
            ruleValidate:{}
        }
    },
    watch:{
        modificationInfo:function(newValue,oldValue){
            this.formValidate = JSON.parse(JSON.stringify(newValue));
        },
        'formValidate.name':function(newValue,oldValue){
            if(newValue != null){
                this.checkName(newValue);
            }
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        modificationInfo:{}
    },
    mounted(){
        this.getTunnelList();
    },
    methods:{
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    //拼接相机视角字符串
                    this.formValidate.camera = this.formValidate.longitude + ',' + this.formValidate.latitude + ',' + this.formValidate.highness;
                    this.$emit("listenToChange",this.formValidate);
                }else{
                    this.$Message.error("修改失败!");
                }
            })
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

