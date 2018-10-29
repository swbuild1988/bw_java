<template>
    <div>
        <!-- 区段管理之新增区段 -->
        <Modal v-model="show.state" title="新增区段" :label-width="100">
            <Form ref="formValidate" :model="formValidate">
                <FormItem label="区段名称：" prop="name" class="formStyle">
                    <Input v-model="formValidate.name" placeholder="请输入区段名" class="inputStyle"/>
                </FormItem>
                <FormItem label="所属管廊：" prop="tunnelId" class="formStyle">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="inputStyle">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属管仓：" prop="storeId" class="formStyle">
                    <Select v-model="formValidate.storeId" placeholder="请选择所属管仓" class="inputStyle">
                        <Option v-for="item in stores" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属区域：" prop="areaId" class="formStyle">
                    <Select v-model="formValidate.areaId" placeholder="请选择所属区域" class="inputStyle">
                        <Option v-for="item in areas" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="可安装管线数：" prop="totalCableNumber">
                    <Input v-model="formValidate.totalCableNumber" placeholder="请输入可安装管线数：" style="width:66%;"/>
                </FormItem>
                <FormItem label="相机视角：" prop="camera" class="formStyle">
                    <Row>
                        <Col span="5">
                            <Input v-model="camera.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="camera.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="5" offset="1">
                            <Input v-model="camera.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="开始坐标：" prop="startPoint" class="formStyle">
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
                <FormItem label="结束坐标：" prop="endPoint" class="formStyle">
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
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>    
</template>

<script>
export default {
    name: "secion-add",
    data(){
        return {
            camera:{
                longitude:'',
                latitude:'',
                highness:''
            },
            startPoint:{
                longitude:'',
                latitude:'',
                highness:'' 
            },
            endPoint:{
                longitude:'',
                latitude:'',
                highness:''
            },
            tunnels:[],
            stores:[],
            areas:[],
            formValidate:{
                name:null,
                tunnelId:null,
                tunnelName:null,
                storeId:null,
                storeName:null,
                areaId:null,
                areaName:null,
                totalCableNumber:null,
                camera:null,
                startPoint:null,
                endPoint:null
            },
            ruleValidate:{}
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
        'formValidate.tunnelId':function(newValue,oldValue){   //newValue是更改选择后的管廊ID
            this.getstorelist(newValue);
            this.getarealist(newValue);
        },
        'formValidate.areaId':function(newValue,oldValue){
            if(newValue!=null && this.formValidate.storeId!=null){
                this.updateName(newValue,this.formValidate.storeId);
            }
        },
        'formValidate.storeId':function(newValue,oldValue){
            if(newValue!=null && this.formValidate.areaId!=null){
                this.updateName(this.formValidate.areaId,newValue);
            }
        }
    },
    mounted(){
        this.gettunnel();
    },
    methods:{
        gettunnel(){            //获取所有管廊的简单列表
            this.axios.get('/tunnels').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.tunnels = data;
                }
            })
        },
        getstorelist(id){         //根据管廊ID查询管仓列表
            this.axios.get('/tunnels/'+ id + '/stores').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.stores = data;
                }
            })
        },
        getarealist(id){        //根据管廊ID查询区域列表
            this.axios.get('/tunnels/' + id + '/areas').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.areas = data;
                }
            })
        },
        sendMsg: function(name){
            this.$refs[name].validate((valid) => {
                if(valid) {
                    //给相机视角和坐标拼接字符串
                    this.formValidate.camera = this.camera.longitude + ',' + this.camera.latitude + ',' + this.camera.highness;
                    this.formValidate.startPoint = this.startPoint.longitude + ',' + this.startPoint.latitude + "," + this.startPoint.highness;
                    this.formValidate.endPoint = this.endPoint.longitude + ',' + this.endPoint.latitude + ',' + this.endPoint.highness;
                    this.$emit("listenToAdd",this.formValidate);
                } else {
                    this.$Message.error('添加失败');
                }
            })
        },
        updateName(_area,_store){
            // 根据ID获取管仓和区域的名字
            this.axios.get('/areas/' + _area).then(res =>{
                let{code,data} = res.data;
                if(code == 200){
                    this.formValidate.areaName = data.name;
                    this.formValidate.name = this.formValidate.areaName + '-' + this.formValidate.storeName;
                }
            });
            this.axios.get('/tunnels/stores/' + _store).then(res =>{
                let{code,data} = res.data;
                if(code == 200){
                    this.formValidate.storeName = data.name;
                    this.formValidate.name = this.formValidate.areaName + '-' + this.formValidate.storeName;
                }
            });
        },
        cleanFormValidate(){
            this.formValidate.name = null;
            this.formValidate.tunnelId = null;
            this.formValidate.storeId = null;
            this.formValidate.areaId = null;
            this.formValidate.totalCableNumber = null;
            this.camera.longitude = null;
            this.camera.latitude = null;
            this.camera.highness = null;
            this.startPoint.longitude = null;
            this.startPoint.latitude = null;
            this.startPoint.highness = null;
            this.endPoint.longitude = null;
            this.endPoint.latitude = null;
            this.endPoint.highness = null;
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
</style>

