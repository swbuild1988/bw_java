<template>
    <div :style="backStyle">
        <Form ref="defectDetails" :model="defectDetails" :rules="validateRules" :label-width="120" @submit.native.prevent>
            <h2 class="formTitle" v-show="this.pageType!=1&&this.pageType!=2">添加缺陷</h2>
            <h2 class="formTitle" v-show="this.pageType==1">缺陷详情</h2>
            <h2 class="formTitle" v-show="this.pageType==2">编辑缺陷详情</h2>
            <FormItem label="所属管廊：" v-show="this.pageType!=1&&this.pageType!=2" prop="tunnelId">
                <Select v-model="defectDetails.tunnelId" :disabled="this.pageType==1">
                    <Option v-for="(item,index) in tunnel" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="所属区段：" prop="area.id">
                <Select v-model="defectDetails.area.id" :disabled="this.pageType==1">
                    <Option v-for="(item,index) in areas" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="所属管仓：" prop="store.id">
                <Select v-model="defectDetails.store.id" :disabled="this.pageType==1">
                    <Option v-for="(item,index) in stores" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷名称：" prop="name">
                <Input v-model="defectDetails.name" type="text" placeholder="请输入缺陷名称" :readonly=this.isTrue></Input>
            </FormItem>
            <FormItem label="发现时间：" prop="createTime">
                <DatePicker type="datetime" v-model="defectDetails.createTime" placeholder="请输入发现时间" style="width: 100%" :readonly=this.isTrue></DatePicker>
            </FormItem>
            <FormItem label="缺陷类型：" prop="type">
                <Select v-model="defectDetails.type" :disabled="this.pageType==1">
                    <Option v-for="(item,index) in type" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="对象名：" v-if="defectDetails.type==2" :disabled="this.pageType==1" prop="objectId">
                <Select v-model="defectDetails.objectId" @on-change="getObj()">
                    <Option v-for="(item,index) in objs" :key="index" :value="item.key">{{item.val}}</Option>
                </Select>
            </FormItem>
            <FormItem label="危险等级：" prop="level">
                <Select v-model="defectDetails.level" :disabled="this.pageType==1">
                    <Option v-for="(item,index) in level" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷状态：" prop="status">
                <Select v-model="defectDetails.status" :disabled="this.pageType==1">
                    <Option v-for="(item,index) in status" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷描述：" prop="description">
                <Input v-model="defectDetails.description" type="textarea" :rows="4" placeholder="请输入缺陷描述" :readonly=this.isTrue></Input>
            </FormItem>
            <FormItem style="text-align: center;" v-show="this.pageType!=1&&this.pageType!=2">
                <Button type="primary" @click="submitDefect('defectDetails')" :disabled="isDisable">提交</Button>
                <Button type="ghost" style="margin-left: 8px" @click="handleReset('defectDetails')">取消 </Button>
            </FormItem>
        </Form> 
    </div>   
</template>
<script>
import types from '../../../../../static/Enum.json'
import { TunnelService } from '../../../../services/tunnelService'
export default {
    data(){
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            defectDetails:{
                id: null,
                tunnelId: null,
                createTime: null,
                type: null,
                typeName: null,
                objectId: null,
                objName: null,
                level: null,
                levelName: null,
                status: null,
                statusName: null,
                description: null,
                area:{
                    id: null,
                    name: ''
                },
                store:{
                    id: null,
                    name: ''  
                },
                name: '',
            },
            tunnel: [],
            type: [],
            level: [],
            status: [],
            areas: [],
            stores:[],
            objs:[],
            isTrue: false,
            isDisable: false,
            validateRules:{
                tunnelId:[
                    { type:'number',required: true,message:'请选择所属管廊',trigger: 'change' }
                ],
                'area.id':[
                    { type:'number',required: true,message:'请选择所属区段',trigger: 'change' }
                ],
                'store.id':[
                    { type:'number',required: true,message:'请选择所属管仓',trigger: 'change' }
                ],
                name:[
                    { required: true, message: '请输入缺陷名称', trigger: 'blur' }
                ],
                createTime:[
                    { type: 'date', required: true, message: '请选择发现时间', trigger: 'change'}
                ],
                type:[
                    { type: 'number', required: true, message: '请选择缺陷类型', trigger: 'change' }
                ],
                objectId:[
                    { type: 'number', required: true, message: '请选择对象名', trigger: 'change' }
                ],
                level:[
                    { type: 'number', required: true, message: '请选择危险等级',trigger: 'change' }
                ],
                status:[
                    { type: 'number', required: true, message: '请选择缺陷状态',trigger: 'change' }
                ],
                description:[
                    { required: true, message: '请输入缺陷描述', trigger: 'blur' }
                ]
            },
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                position: 'relative',
                paddingTop: '20px',
                paddingBottom: '20px',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%'
            }
        }    
    },
    computed:{
        tunnelId(){
            return this.defectDetails.tunnelId
        },
        areaId(){
            return this.defectDetails.area.id
        },
        storeId(){
            return this.defectDetails.store.id
        }
    },
    watch:{
        tunnelId(curVal,oldVal){
            this.defectDetails.tunnelId = curVal
            this.getAreas()
            this.getStores()
        },
        areaId(curVal,old){
            this.defectDetails.area.id = curVal
            this.getObj()
        },
        storeId(curVal,oldVal){
            this.defectDetails.store.id = curVal
            this.getObj()
        }
    },
    mounted(){
        this.defectDetails.id =  this.$route.params.id;
        this.pageType = this.$route.params.type;
        if(this.pageType == 1){
            this.isTrue = true
        }
        this.axios.get('/tunnels').then(response=>{
            let{code,data} = response.data
            if(code==200){
                this.tunnel = data
            }
        })
        //缺陷类型
        this.axios.get("/defectType/enum").then(response=>{
            let { code,data } = response.data
            if(code==200){
                this.type = data
            }
        })
        //缺陷状态
        this.axios.get("/defectStatus/enum").then(response=>{
            let { code,data } = response.data
            if(code==200){
                this.status = data
            }
        })
        //危险等级
        this.axios.get("/defectLevel/enum").then(response=>{
            let { code,data } = response.data
            if(code==200){
                this.level = data
            }
        })

        //获取缺陷详情
        this.axios.get("/defects/"+this.defectDetails.id).then(response=>{
            let{ code,data } = response.data
            if(code==200){
                this.defectDetails = data
                this.defectDetails.store.id = data.store.id
                this.defectDetails.createTime = new Date(data.createTime).format('yyyy-MM-dd hh:mm:s')
                this.getAreas()
                this.getStores()
            }
        })
    },
    methods: {
        //获取所属区域
        getAreas(){
            this.axios.get('/tunnels/'+this.defectDetails.tunnelId+'/areas').then(response=>{
                let{code,data} = response.data
                if(code==200){
                    this.areas=data
                }
            })
            this.getObj()
        },

        //获取所属仓段
        getStores(){
            this.axios.get('tunnels/'+this.defectDetails.tunnelId+'/stores').then(response=>{
                let{code,data} = response.data
                if(code==200){
                    this.stores = data
                }
            })
            this.getObj()
        },

        //获取对象名    
        getObj(){
            this.objs = []
            let mes={
                areaId : this.defectDetails.area.id,
                storeId : this.defectDetails.store.id
            }
            this.axios.post("/section/measobjs",(mes)).then(response=>{
                let { code,data } = response.data
                if(code==200){
                    this.objs = data
                }
            })
        },

        //提交缺陷
        submitDefect(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.$refs[name].validate((valid)=>{
                    if(valid){
                        this.axios.post('defects',(this.defectDetails)).then(response=>{
                            this.$router.push("/UM/defect/query/"+this.defectDetails.tunnelId);
                        })
                        .catch(function(error) {
                            console.log(error);
                        });
                    }
                })
            },2000)
        },
        handleReset(name){
            this.$refs[name].resetFields()
        }

        //更新缺陷
        // updateDefect(){
        //     this.axios.put('defects',(this.defectDetails)).then(response=>{
        //         this.$router.push("/UM/defect/query/"+this.defectDetails.tunnelId);
        //     })
        //     .catch(function(error) {
        //         console.log(error);
        //     });
        // }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 700px;
    margin: 10px auto;
    background: #fff;
    padding: 10px 20px;
    margin-top: 30px;
    border-radius: 4px;
}
</style>


