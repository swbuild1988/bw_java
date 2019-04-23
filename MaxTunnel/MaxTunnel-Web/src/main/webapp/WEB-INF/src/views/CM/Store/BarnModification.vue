<template>
    <!-- 管仓管理的修改管仓 -->
    <div>
        <Modal v-model="show.state" :title='title'>
            <Form ref="formValidate" :model="formValidate" :label-width="100" :rules="ruleValidate">
                <FormItem label="管仓名：" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入管仓名" class="InputWidth"/>
                </FormItem>
                <FormItem label="所属管廊：" prop="tunnelId">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="InputWidth">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="管仓类型：" prop="storeTypeId">
                    <Select v-model="formValidate.storeTypeId" placeholder="请选择管仓类型" class="InputWidth">
                        <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="类型编号：" prop="sn">
                    <Input v-model="formValidate.sn" placeholder="请输入sn" class="InputWidth"></Input>
                </FormItem>
                <FormItem label="宽度：" prop="width">
                    <Poptip trigger="hover" placement="top-start" style="max-height: 100px;">
                        <img slot="content" :src="storelk" placement="top" alt="管廊方向说明图" style="height: 280px;">
                        <div slot="content" class="poptipExplain" placement="top">
                            注！剖面是从起点向终点看！！x,y为每个区的中心（分起点和终点，这里拿起点做例子），x为经度，y为纬度。
                        </div>
                        <div slot="content" class="poptipExplain">
                            上图中，k2就是0，其余按照实际计算
                        </div>
                        <div slot="content" class="poptipExplain">
                            w表示管仓的宽度
                        </div>
                        <Input v-model="formValidate.width" placeholder="请输入管仓的宽度"></Input>
                    </Poptip>
                </FormItem>
                <FormItem label="高度：" prop="height">
                    <Poptip trigger="hover" placement="top-start">
                        <img slot="content" :src="storelk" placement="top" alt="管廊方向说明图" style="height: 300px;">
                        <div slot="content" class="poptipExplain" style="">
                            注！剖面是从起点向终点看！！x,y为每个区的中心（分起点和终点，这里拿起点做例子），x为经度，y为纬度。
                        </div>
                        <div slot="content" class="poptipExplain">
                            上图中，k2就是0，其余按照实际计算
                        </div>
                        <div slot="content" class="poptipExplain">
                            h表示管仓的高度
                        </div>
                        <Input v-model="formValidate.height" placeholder="请输入管仓的高度"></Input>
                    </Poptip>
                </FormItem>
                <FormItem label="L：" prop="l">
                    <Poptip trigger="hover" placement="top-start">
                        <img slot="content" :src="storelk" placement="top" alt="管廊方向说明图" style="height: 300px;">
                        <div slot="content" class="poptipExplain" style="">
                            注！剖面是从起点向终点看！！x,y为每个区的中心（分起点和终点，这里拿起点做例子），x为经度，y为纬度。
                        </div>
                        <div slot="content" class="poptipExplain">
                            上图中，k2就是0，其余按照实际计算
                        </div>
                        <div slot="content" class="poptipExplain">
                            L为管廊中心点到某管仓中心点垂线的距离
                        </div>
                        <Input v-model="formValidate.l" placeholder="请输入L的值"></Input>
                    </Poptip>
                </FormItem>
                <FormItem label="K：" prop="k">
                    <Poptip trigger="hover" placement="top-start">
                        <img slot="content" :src="storelk" placement="top" alt="管廊方向说明图" style="height: 300px;">
                        <div slot="content" class="poptipExplain" style="">
                            注！剖面是从起点向终点看！！x,y为每个区的中心（分起点和终点，这里拿起点做例子），x为经度，y为纬度。
                        </div>
                        <div slot="content" class="poptipExplain">
                            上图中，k2就是0，其余按照实际计算
                        </div>
                        <div slot="content" class="poptipExplain">
                            k为管仓中心点管廊中心点的垂直地面距离
                        </div>
                        <Input v-model="formValidate.k" placeholder="请输入K的值"></Input>
                    </Poptip>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button v-if="type==1" type="primary" size="large" v-on:click="addStoreInfo('formValidate')">保存</Button>
                <Button v-if="type==2" type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { StoreService } from '@/services/storeService'
export default {
    name: 'store-modification',
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
            types:[],
            formValidate:{
                name: null,
                tunnelId: null,
                storeTypeId: null,
                sn: null,
                width: null,
                height: null,
                l: null,
                k: null
            },
            ruleValidate:{
                name:[
                    { required:true, message:'管仓名不能为空', trigger:'blur' }
                ],
                tunnelId: [
                    { type: 'number', required: true, message: '所属管廊不能为空', trigger: 'blur' }
                ],
                storeTypeId: [
                    { type: 'number', required: true, message: '管仓类型不能为空', trigger: 'blur' }
                ],
                sn: [
                    { required: true, message: '管仓类型不能为空', trigger: 'blur' }
                ],
                width: [
                    { required: true, message: '宽度不能为空', trigger: 'blur' }
                ],
                height: [
                    { required: true, message: '高度不能为空', trigger: 'blur' }
                ],
                l: [
                    { required: true, message: 'L不能为空', trigger: 'blur' }
                ],
                k: [
                    { required: true, message: 'K不能为空', trigger: 'blur' }
                ]
            },
            storelk: require('@/assets/CM/storelk.png'),
            title: null
        }
    },
    watch:{
        'type': function(newVal){
            this.changeTitle(newVal)
        }
    },
    computed: {
        params(){
            let param = {
                name: this.formValidate.name,
                tunnelId: this.formValidate.tunnelId,
                storeTypeId: this.formValidate.storeTypeId,
                sn: this.formValidate.sn,
                width: this.formValidate.width,
                height: this.formValidate.height,
                l: this.formValidate.l,
                k: this.formValidate.k
            }
            return Object.assign({}, param);
        }
    },
    mounted(){
        // 获取所属管廊和管仓类型列表
        this.getTunnelList(),
        this.getStoreTypeList()   
    },
    methods:{
        getTunnelList(){    
            StoreService.getTunnels().then(
                result => {
                    this.tunnels = result;
                },
                error => {
                    this.Log.info(error)
                }
            )  
        },
        getStoreTypeList(){
            StoreService.getStoreType().then(
                result => {
                    this.types = result;
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        sendMsg: function(data){
            this.$refs[data].validate((valid) => {
                if(valid){
                    StoreService.saveEditStore(this.params).then(
                        result => {
                            this.$emit('sendMsg')
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error('修改失败！')
                }
            })
        },
        //新增管仓
        addStoreInfo(name){

            this.$refs[name].validate((valid) => {
                if(valid){
                    let addParams = {
                        name: this.formValidate.name,
                        tunnelId: this.formValidate.tunnelId,
                        storeTypeId: this.formValidate.storeTypeId,
                        sn: this.formValidate.sn,
                        width: this.formValidate.width,
                        height: this.formValidate.height,
                        l: this.formValidate.l,
                        k: this.formValidate.k
                    }
                    StoreService.saveAddStore(addParams).then(
                        result => {
                            this.$emit('childIsRefresh')
                            this.$Message.success("保存成功！")
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                }else{
                    this.$Message.error("保存失败")
                }
            })

        },
        getStoreInfo(id){
            this.axios.get('tunnels/stores/'+id).then(res=>{
                let{ code, data } = res.data
                if( code == 200 ){
                    this.formValidate = data
                    this.formValidate.width = data.width.toString()
                    this.formValidate.height = data.height.toString()
                    this.formValidate.k = data.k.toString()
                    this.formValidate.l = data.l.toString()
                }
            })
        },
        handleReset(name){
            this.$refs[name].resetFields()
        },
        changeTitle(type){
            if(type==1){
                this.title = '新增管仓'
                this.handleReset('formValidate')
            }
            if(type==2){
                this.title = '编辑管仓信息'
            }
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
    max-height: 100px;
}
.poptipExplain{
    white-space: normal;
    word-break: break-all;
    line-height: 25px;
    font-size: 14px;
}
</style>

