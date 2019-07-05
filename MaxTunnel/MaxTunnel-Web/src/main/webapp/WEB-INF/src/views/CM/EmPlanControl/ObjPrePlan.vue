<template>
    <div class="containerBox">
        <h2 class="formTitle">监测对象关联应急预案</h2>
        <Form ref="objPlanForm" :model="objPlanForm" :label-width="90" :rules="ruleValidate">
            <FormItem label="对象类型：" prop="objType">
                <Select v-model="objPlanForm.objType">
                    <Option v-for="item in objTypes" :value="item.val" :key="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="关联预案：">
                <Select v-model="relaPrePlan"  multiple placeholder="请选择关联预案"  @on-change="getTypeOf()"> 
                    <Option v-for="item in prePlan" :value="item.val" :key="item.key">{{item.key}}</Option>
                </Select>
            </FormItem>
        </Form>
        <div slot="footer" class="footer">
            <Button type="primary" @click="submitForm('objPlanForm')">确定</Button>    
        </div>    
    </div>
</template>
<script>
import { TunnelService } from '@/services/tunnelService'
import { EnumsService } from "@/services/enumsService";
export default {
    data(){
        return {
            tunnels: [],
            stores: [],
            areas: [],
            objTypes: [],
            prePlan: [],
            objPlanForm: {
                objType: null,
                plandIds: null
            },
            ruleValidate: {
                objType: [
                    { type: 'number', required: true, message: '对象类型不能为空', trigger: 'blur' }
                ]
            },
            relaPrePlan: []
        }
    },
    watch:{
        'objPlanForm.tunnelId': function(newVal){
            this.getAreasByTunnelId(newVal)
            this.getStoresByTunnelId(newVal)
        }
    },
    mounted(){
        //get tunnels
        TunnelService.getTunnels().then(
            result => {
                this.tunnels = result
            },
            error => {
                this.Log.info(error)
            }
        ),
        //get 对象类型
        EnumsService.getObjType().then(
            result => {
                this.objTypes = result;
            },
            error => {
                this.Log.info(error);
            }
        );
        //get 预览 
        EnumsService.getPlanType().then(
            result => {
                this.prePlan = result
            },
            error => {
                this.Log.info(error)
            }
        )
    },
    methods: {
        //get areas
        getAreasByTunnelId(tunnelId){
            this.objPlanForm.areaId = null
            TunnelService.getAreasByTunnelId(tunnelId).then(
                result => {
                    this.areas = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //get stores
        getStoresByTunnelId(tunnelId){
            this.objPlanForm.storeId = null
            TunnelService.getStoresByTunnelId(tunnelId).then(
                result => {
                    this.stores = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //提交
        submitForm(name){
            this.$refs[name].validate((valid) => {
                if(valid){
                    var params = {
                        objtypeId: this.objPlanForm.objType,
                        planIds: this.objPlanForm.plandIds 
                    }
                    this.axios.post("measobjs/conf/plans",params).then(res=>{
                        let{ code, data } = res.data
                        if( code == 200 ){
                            this.$Message.success("提交成功！")
                            this.objPlanForm.objType = null
                            this.relaPrePlan = null 
                        }
                    })
                }else{
                    this.$Message.error("提交失败！")
                }
            })
        },
        getTypeOf(){
            this.objPlanForm.plandIds = this.relaPrePlan.toString()
        },
    }    
}
</script>
<style scoped>
.formTitle,.ivu-form,.ivu-form-label-right,.footer{
    width: 50%;
    max-width: 500px;
    margin: 10px auto 0px;
    margin-left: 30%;

}
.footer{
    text-align: center;
}
.containerBox{
    padding-top: 2vmin;
}
</style>
