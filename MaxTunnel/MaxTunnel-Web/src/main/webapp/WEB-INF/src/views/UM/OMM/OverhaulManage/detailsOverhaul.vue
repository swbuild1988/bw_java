<template>
    <div>
        <Form :model="detailsOverhaul" :label-width="100">
            <h2 class="formTitle">工单详情</h2>
            <FormItem label="所属管廊：">   
                <Input type="text" v-model="detailsOverhaul.tunnelName" readonly></Input>
            </FormItem>
            <FormItem label="所属缺陷：">
                <Input type="text" v-model="detailsOverhaul.defectName" readonly></Input>
            </FormItem>
            <FormItem label="检修人：">
                <Input type="text" v-model="detailsOverhaul.overhaulPerson" readonly></Input>
            </FormItem>
        </Form>    
    </div>
</template>
<script>
import { TunnelService } from '../../../../services/tunnelService'
export default {
    data(){
        return {
            // 页面类型 1：查看 2：编辑 3：新增
            // pageType: 1,
            // pageTypes: types.pageType,
            detailsOverhaul:{
                id: 1,
                tunnelId: 1,
                tunnelName: '凤岭北路',
                defectName: '凤岭北路电力故障',
                overhaulPerson: '',
            },
            tunnel: [],
            overdaulResult:[
                {key: '故障已排除', val: 1},{key: '故障未排除', val: 2},{key: '故障未发现', val: 3}
            ],
            isShow: false
        }    
    },
    mounted(){
        this.detailsOverhaul.id = this.$route.params.id
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                _this.tunnel = result
            },
            (error)=>{
                _this.Log.info(error)
            })
        // this.axios.get('/tunnels').then(response=>{
        //     let{code,data} = response.data
        //     if(code==200){
        //         this.tunnel = data
        //     }
        // })
        this.getSessionUserName()
    },
    methods: {
        getSessionUserName:function () {
            if(sessionStorage.UMUerName!=null||sessionStorage.UMUerName!=undefined||sessionStorage.UMUerName!=''){
                var str = sessionStorage.UMUerName.substring(1,sessionStorage.UMUerName.length-1)
                this.detailsOverhaul.overhaulPerson = str
            }
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


