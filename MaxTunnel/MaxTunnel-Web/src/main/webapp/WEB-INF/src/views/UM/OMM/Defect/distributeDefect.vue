<template>
    <div :style="backStyle">
        <Form :model="defectDetails" ref="defectDetails" :label-width="100" :rules="ruleValidate" @submit.native.prevent>
            <h2 class="formTitle">指派维修工单</h2>
            <FormItem label="所属管廊：">
                <Select v-model="defectDetails.tunnelId" disabled>
                    <Option v-for="(item,index) in tunnel" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="所属区段：">
                <Select v-model="defectDetails.area.id" disabled>
                    <Option v-for="(item,index) in areas" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="所属管仓：">
                <Select v-model="defectDetails.store.id" disabled>
                    <Option v-for="(item,index) in stores" :key="index" :value="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷名称：">
                <Input v-model="defectDetails.name" type="text" placeholder="请输入缺陷名称" readonly></Input>
            </FormItem>
            <FormItem label="发现时间：">
                <DatePicker type="datetime" v-model="defectDetails.createTime" placeholder="请输入发现时间" style="width: 100%" readonly></DatePicker>
            </FormItem>
            <FormItem label="缺陷类型：">
                <Select v-model="defectDetails.type" disabled>
                    <Option v-for="(item,index) in type" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="对象名：" v-show="defectDetails.type==2">
                <Select v-model="defectDetails.objId" disabled>
                    <Option v-for="(item,index) in objs" :key="index" :value="item.key">{{item.val}}</Option>
                </Select>
            </FormItem>
            <FormItem label="危险等级：">
                <Select v-model="defectDetails.level" disabled>
                    <Option v-for="(item,index) in level" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷状态：">
                <Select v-model="defectDetails.status" disabled>
                    <Option v-for="(item,index) in status" :key="index" :value="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="缺陷描述：">
                <Input v-model="defectDetails.description" type="textarea" :rows="4" placeholder="请输入缺陷描述" readonly></Input>
            </FormItem>
            <FormItem label="责任人：" prop="manId">
                <Select v-model="defectDetails.manId">
                    <Option v-for="(item,index) in liable" :value="item.accountId.toString()" :key="index">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="备注：">
                <Input v-model="defectDetails.remark" type="textarea" :rows="4" placeholder="请输入备注"></Input>
            </FormItem>
            <FormItem style="text-align: center">
                <Button type="primary" @click="submitForm('defectDetails')">提交</Button>
                <Button type="ghost" style="margin-left: 8px">取消 </Button>
            </FormItem>
        </Form>   
        <!-- <div style="text-align: center">
            <Button type="primary" @click="submitForm('defectDetails')">提交</Button>
            <Button type="ghost" style="margin-left: 8px">取消 </Button>
        </div>  -->
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
import { DefectService } from '../../../../services/defectService'
import { EnumsService } from '../../../../services/enumsService'    
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            defectDetails:{
                id: null,
                tunnelId: null,
                area:{
                    id: null,
                    name: null
                },
                store:{
                    id: null,
                    name: null
                },
                name: '',
                createTime: null,
                type: null,
                objId: null,
                level: null,
                status: null,
                description: null,
                manId: null,
                remark: null
            },
            ruleValidate:{
                manId:[
                    {required: true, message: '责任人不能为空', trigger: 'change'}
                ],
            },
            tunnel: [],
            type: [],
            level: [],
            status: [],
            liable:[],
            areas:[],
            stores:[],
            objs:[],
            processInstanceId: null,
            typeKey: null,
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
    watch:{
        '$route':function(){
            this.processInstanceId=this.$route.params.id;
        }
    },
    mounted(){
        this.processInstanceId =  this.$route.params.id;
        this.pageType = this.$route.params.type;
        this.typeKey = this.$route.params.typeKey;
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnels = result
            },
            (error)=>{
                _this.Log.info(error)
            })

        DefectService.getStaffs().then(
            (result)=>{
                _this.liable = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        // this.axios.get('/staffs').then(response=>{
        //     let{ code,data } = response.data
        //     if(code==200){
        //         this.liable = data
        //     }
        // })
        //缺陷类型
        EnumsService.getDefectType().then(
            (result)=>{
                _this.type = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //缺陷状态
        EnumsService.getDefectStatus().then(
            (result)=>{
                _this.status = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //危险等级
        EnumsService.getDefectLevel().then(
            (result)=>{
                _this.level = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        //获取工单详情
        DefectService.getDetailsBySheetId(this.processInstanceId).then(
            (result)=>{
                _this.defectDetails = result
                _this.defectDetails.createTime = new Date(result.createTime).format('yyyy-MM-dd hh:mm:s')
                _this.getAreas()
                _this.getStores()
            })
        // this.axios.get('orders/'+this.processInstanceId+'/defect').then(response=>{
        //     let{ code,data } = response.data
        //     if(code==200){
        //         this.defectDetails = data
        //         this.defectDetails.createTime = new Date(data.createTime).format('yyyy-MM-dd hh:mm:s')
        //         this.getAreas()
        //         this.getStores()
        //     }
        // })
    },
    methods: {
        //获取所属区域
        getAreas(){
            let _this = this
            TunnelService.getAreasByTunnelId(this.defectDetails.tunnelId).then(
                (result)=>{
                    _this.areas = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
            // this.axios.get('/tunnels/'+this.defectDetails.tunnelId+'/areas').then(response=>{
            //     let{code,data} = response.data
            //     if(code==200){
            //         this.areas=data
            //     }
            // })
            this.getObj()
        },

        //获取所属仓段
        getStores(){
            let _this = this
            TunnelService.getStoresByTunnelId(this.defectDetails.tunnelId).then(
                (result)=>{
                    _this.stores = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
            // this.axios.get('tunnels/'+this.defectDetails.tunnelId+'/stores').then(response=>{
            //     let{code,data} = response.data
            //     if(code==200){
            //         this.stores = data
            //     }
            // })
            this.getObj()
        },

        //获取对象名    
        getObj(){
            this.objs = []
            let mes={
                    areaId : this.defectDetails.area.id,
                    storeId : this.defectDetails.store.id
                }
            let _this = this
            DefectService.getObj(mes).then(
                (result)=>{
                    _this.objs = result
                },
                (error)=>{
                    _this.Log.info(error)
                })
            // this.axios.post("/section/measobjs",(mes)).then(response=>{
            //     let { code,data } = response.data
            //     if(code==200){
            //         this.objs = data
            //     }
            // })
        },
        //提交事件
        submitForm(name){
            this.$refs[name].validate(valid=>{
                if(valid){
                    var formInfo = {
                        id: parseInt(this.defectDetails.id),
                        manId: parseInt(this.defectDetails.manId),
                        remark: this.defectDetails.remark
                    }
                    let _this = this
                    DefectService.distributeDefect(formInfo).then(
                        (result)=>{
                            if(_this.$route.params.isFinished==undefined){
                                _this.$router.push('/UM/myNews/queryMyTask')
                            }else{
                                _this.$router.push('/UM/myTasks/query')
                            }
                        },
                        (error)=>{
                            _this.Log.info(error)
                        })
                    // this.axios.put('/maintenance-orders',formInfo).then(response=>{
                    //     if(this.$route.params.isFinished==undefined){
                    //         this.$router.push('/UM/myNews/queryMyTask');
                    //     }else{
                    //         this.$router.push('/UM/myTasks/query');
                    //     }
                    // })
                    // .catch(function(error){
                    //     console.log(error)
                    // })
                }
            })
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 680px;
    margin: 10px auto;
    background: #fff;
    padding: 10px 20px;
}
</style>


