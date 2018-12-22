<template>
    <!-- 管仓管理的修改管仓 -->
    <div>
        <Modal v-model="show.state" title="修改管仓">
            <Form ref="formValidate" :label-width="100">
                <FormItem label="管仓名" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入管仓名" class="InputWidth"/>
                </FormItem>
                <FormItem label="所属管廊" prop="tunnelId">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="InputWidth">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="管仓类型" prop="storeTypeId">
                    <Select v-model="formValidate.storeTypeId" placeholder="请选择管仓类型" class="InputWidth">
                        <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="经度" prop="longitude">
                    <Input v-model="longitude" placeholder="请输入经度" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="纬度" prop="latitude">
                    <Input v-model="latitude" placeholder="请输入纬度" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="高度" prop="highness">
                    <Input v-model="highness" placeholder="请输入高度" class="InputWidth"></Input>
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
    name: 'store-modification',
    data(){
        return {
            tunnels:[],
            types:[],
            formValidate:{
                name:'',
                tunnelId:'',
                storeTypeId:'',
                camera:''
            },
            longitude:0,
            latitude:0,
            highness:0,
            ruleValidate:{
                name:[
                    { required:true, message:'管仓名不能为空', trigger:'blur'}
                ]
            }
        }
    },
    watch:{
        changeInfo:function(value,oldvalue){
            this.formValidate = JSON.parse(JSON.stringify(value));
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        changeInfo:{}
    },
    mounted(){
        // 获取所属管廊和管仓类型列表
        this.getTunnelList(),
        this.getStoreTypeList()
    },
    methods:{
        getTunnelList(){        
            this.axios.get('/tunnels').then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    this.tunnels = data;
                }
            })
        },
        getStoreTypeList(){
            this.axios.get('/store-type/list').then(res =>{
                let {code,data} = res.data;
                let _types = [];
                if(code == 200){
                    for(let i=0;i<data.length;i++){
                        let type = {};
                        type.id = data[i].id;
                        type.name = data[i].name;
                        _types.push(type);
                    }
                    this.types = _types;
                }
            })
        },
        sendMsg: function(data){
            this.formValidate.camera = this.longitude + ',' + this.latitude + ',' + this.highness;
            this.$refs[data].validate((valid) => {
                if(valid){
                    this.$emit("listenToChange",this.formValidate);
                }else{
                    this,$Message.error('修改失败')
                }
            })
        }
    }
}
</script>

<style scoped>
.InputWidth{
    width: 85%
}
</style>

