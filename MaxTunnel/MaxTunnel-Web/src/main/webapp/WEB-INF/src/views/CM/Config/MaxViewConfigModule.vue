<template>
    <div>
        <Modal v-model="show.state" title="新增流程节点">
            <Form ref="data" :model="data" :rules="rule" :label-width="120">
                <FormItem label="终端名称" prop="name">
                    <Input v-model="data.name" placeholder="请输入终端名称" class="InputWidth"/>
                </FormItem>
                <FormItem label="IP" prop="ip">
                    <Input v-model="data.ip" placeholder="请输入终端IP" class="InputWidth"/>
                </FormItem>
                <FormItem label="端口号" prop="port">
                    <Input v-model="data.port" placeholder="请输入终端端口号" class="InputWidth"/>
                </FormItem>
                <FormItem label="登录账号" :prop="isDisabled ? '' : 'username'">
                   <Input v-model="data.username" placeholder="请输入登录账号" class="InputWidth" :disabled="isDisabled"/>
                </FormItem>
                <FormItem label="登录密码" v-if="!isDisabled">
                    <Input v-model="data.password" placeholder="请输入登录密码" class="InputWidth" type="password"/>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('data')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import { EnumsService } from '../../../services/enumsService'
import { TunnelService } from '../../../services/tunnelService'
export default {
    name: 'MaxViewModule',
    data(){
        return {
            data:{
                id: null,
                name: null,
                ip: null,
                port: null,
                username: null,
                password: null
            },
            rule: {
                name: [
                    { required: true, message: '终端名称不能为空', trigger: 'blur' }
                ],
                ip: [
                    { required: true, message: '终端IP不能为空', trigger: 'blur' }
                ],
                port: [
                    { required: true, message: '端口号不能为空', trigger: 'blur' }
                ],
                username: [
                    { required: true, message: '登录账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '登录密码不能为空', trigger: 'blur' }
                ]
            },
            isDisabled: false
        }
    },
    props:{
        show:{
            state:{
                default: false
            }
        },
        editInfo: {
            required: true
        },
        type: {
            required: true
        }
    },
    watch:{
        'show.state': function(newValue,oldValue){
            if(this.type == 'edit'){
                for(let item in this.editInfo){
                    for(let info in this.data){
                        if(item === info && item != 'password'){
                            this.data[info] = this.editInfo[item]
                        }

                    }
                }
                this.isDisabled = true
                this.data.id = this.editInfo.id
            } else {
                for(let item in this.data){
                    this.data[item] = null
                }
                this.isDisabled = false
            }
        }
    },
    methods:{
        sendMsg(name){
            this.$refs[name].validate((valid) => {
                if (valid) {
                    if(this.type == 'add'){
                        this.$emit("addOrEdit",this.data)
                    } else {
                        let editParams = {
                            id: this.data.id,
                            name: this.data.name,
                            ip: this.data.ip,
                            port: this.data.port,
                            username: this.data.username
                        }
                        this.$emit("addOrEdit",editParams)
                    }
                    // ;
                } else {
                    this.$Message.error('操作失败，请稍后重试');
                }
            });
       }
    }
}
</script>

<style scoped>
.InputWidth{
    width: 85%;
}
</style>
