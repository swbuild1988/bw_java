<template>
    <div>
        <Modal v-model="show.state" title="修改管仓类型">
            <Form ref="formValidate" :model="formValidate" :label-width="120">
                <FormItem label="管仓类型" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入管仓类型" @on-change="nameChanged"/>
                </FormItem>
                <span v-show="!flag" class="errorStyle">管仓类型名需唯一</span>
                <FormItem label="父仓类型编号" prop="parent">
                    <Input v-model="formValidate.parent" placeholder="请输入父仓类型"/>
                </FormItem>
            </Form>
            <div slot="footer" v-show="flag">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
            <div slot="footer" v-show="!flag">
                <Button disabled type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
export default {
    name: 'store-type-change',
    data(){
        return{
            flag:true,
            formValidate:{
                name:'',
                parent:''
            }
        }
    },
    watch:{
        changeInfo:function(value,oldvalue){
            this.formValidate = JSON.parse(JSON.stringify(value));
            this.flag = true
        }
    },
    props:{
        show:{
            state: false
        },
        changeInfo:{}
    },
    mounted(){

    },
    methods:{
        sendMsg: function(data){
            this.$refs[data].validate((valid) => {
                if(valid){
                    this.formValidate.crtTime = new Date(this.formValidate.crtTime).getTime()
                    this.$emit("listenToChange",this.formValidate);
                }else{
                    this,$Message.error('添加失败')
                }
            })
        },
        nameChanged(){
            if(this.formValidate.name != null){
                this.axios.get('/store-type/ajax/' + this.formValidate.name).then(res =>{
                    let {code,data} = res.data;
                    if(code == 200){
                        this.flag = data;
                    }
                })
            }
        }
    }
}
</script>

<style scoped>
.errorStyle{
    position:absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 105px;
    left: 140px;
}
</style>


