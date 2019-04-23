<template>
    <div>
        <!-- 区段管理之修改区段 -->
        <Modal v-model="show.state" title="修改区段" :width="600">
            <Form ref="formValidate" :model="formValidate" :label-width="120" :rules="validateForm">
                <FormItem label="区段名称：" prop="name">
                    <Input v-model="formValidate.name" disabled placeholder="请输入区段名"/>
                </FormItem>
                <FormItem label="所属管廊：" prop="tunnelId">
                    <Select v-model="formValidate.tunnelId" disabled  placeholder="请选择所属管廊">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属管仓：" prop="storeId">
                    <Select v-model="formValidate.storeId" disabled placeholder="请选择所属管仓">
                        <Option v-for="item in stores" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="所属区域：" prop="areaId">
                    <Select v-model="formValidate.areaId" disabled placeholder="请选择所属区域">
                        <Option v-for="item in areas" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="可安装管线数：" prop="totalCableNumber">
                    <Input v-model="formValidate.totalCableNumber" placeholder="请输入可安装管线数："/>
                </FormItem>
                <!-- <FormItem label="相机视角：" prop="camera">
                    <Row>
                        <Col span="7">
                            <Input v-model="camera.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="camera.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="camera.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="开始坐标：" prop="startPoint">
                    <Row>
                        <Col span="7">
                            <Input v-model="startPoint.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="startPoint.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="startPoint.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="结束坐标：" prop="endPoint">
                    <Row>
                        <Col span="7">
                            <Input v-model="endPoint.longitude" placeholder="请输入经度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="endPoint.latitude" placeholder="请输入纬度"/>
                        </Col>
                        <Col span="7" offset="1">
                            <Input v-model="endPoint.highness" placeholder="请输入高度"/>
                        </Col>
                    </Row>
                </FormItem> -->
                <FormItem label="s1：" prop="s1">
                    <Poptip trigger="hover" placement="top-start">
                        <img slot="content" :src="sections1s2" placement="top" alt="管廊方向说明图" style="height: 300px;">
                        <div slot="content">s1为管仓到区头的距离,如果是综合仓、电力仓等之类的主仓，s1的值为0</div>
                        <Input v-model="formValidate.s1" placeholder="请输入s1的值"></Input>
                    </Poptip>
                </FormItem>
                <FormItem label="s2：" prop="s2">
                    <Poptip trigger="hover" placement="top-start">
                        <img slot="content" :src="sections1s2" placement="top" alt="管廊方向说明图" style="height: 300px;">
                        <div slot="content">s2为管仓到区尾的距离,如果是综合仓、电力仓等之类的主仓，s2的值为0</div>
                        <Input v-model="formValidate.s2" placeholder="请输入s2的值"></Input>
                    </Poptip>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsgtoManage('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>    
</template>

<script>
export default {
    name: "section-change",
    data(){
        return {
            tunnels:[],
            stores:[],
            areas:[],
            formValidate:{
                name: null,
                tunnelId: null,
                storeId: null,
                areaId: null,
                totalCableNumber: null,
                s1: null,
                s2: null
            },
            sections1s2: require('@/assets/CM/section.png'),
            validateForm: {
                name: [
                    { required: true, message: '区段名称不能为空', trigger: 'blur' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '所属管廊不能为空', trigger: 'blur' }
                ],
                storeId: [
                    { type: 'number', required: true, message: '所属管仓不能为空', trigger: 'blur' }
                ],
                areaId: [
                    { type: 'number', required: true, message: '所属区域不能为空', trigger: 'blur' }
                ],
                totalCableNumber: [
                    { type: 'number', required: true, message: '可安装管线数不能为空', trigger: 'blur' }
                ],
                s1: [
                    { required: true, message: 's1不能为空', trigger: 'blur' }
                ],
                s2: [
                    { required: true, message: 's2不能为空', trigger: 'blur' }
                ]
            }
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        sectionId: null
    },
    watch:{
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
        sendMsgtoManage: function(name){
            this.$refs[name].validate((valid) => {
                if(valid) {
                    this.axios.put("/sections", this.formValidate).then(res => {
                        let { code, data } = res.data;
                        if (code == 200) {
                            this.$emit("listenToChange");
                        }else{
                            this.$Message.error('修改失败');
                        }
                    });
                } else {
                    this.$Message.error('区段信息填写错误');
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
        getSectionInfoById(id){
            this.axios.get('sections/'+id).then(res=>{
                let{ code, data } = res.data
                if(code==200){
                    this.formValidate = data
                    this.formValidate.areaId = data.area.id
                    this.formValidate.storeId = data.store.id
                    this.formValidate.s1 = data.s1.toString()
                    this.formValidate.s2 = data.s2.toString()
                }
            })
        }
    }
}
</script>

<style scoped>
.directionImg{
    width: 20px;
    height: 20px;
}
.ivu-poptip{
    width: 100%;
}
.ivu-poptip >>> .ivu-poptip-rel{
    width: 100%;
}
</style>

