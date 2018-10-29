<template>
    <div>
        <!-- 管仓类型管理的新增管仓类型 -->
        <Modal v-model="show.state" title="修改管仓类型">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
                <FormItem label="管仓类型" prop="name">
                    <Input v-model="formValidate.name" placeholder="请输入管仓类型"/>
                </FormItem>
                <span v-show="!flag" class="errorStyle">管仓类型名需唯一</span>
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
                name:''
            },
            ruleValidate:{
                name:[
                    { required:true, message:'管仓类型不能为空', trigger:'blur'}
                ]
            }
        }
    },
    watch:{
        changeInfo:function(value,oldvalue){
            this.formValidate = JSON.parse(JSON.stringify(value));
        },
        'formValidate.name':function(value,oldValue){
            if(value != null){
                this.axios.get('/store-type/ajax/' + value).then(res =>{
                    let {code,data} = res.data;
                    if(code == 200){
                        this.flag = data;
                    }
                })
            }
        }
    },
    props:{
        show:{
            state: false
        },
        changeInfo:{}
    },
    methods:{
        sendMsg: function(data){
            this.$refs[data].validate((valid) => {
                if(valid){
                    this.$emit("listenToChange",this.formValidate);
                }else{
                    this,$Message.error('添加失败')
                }
            })
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
    left: 120px;
}
</style>


