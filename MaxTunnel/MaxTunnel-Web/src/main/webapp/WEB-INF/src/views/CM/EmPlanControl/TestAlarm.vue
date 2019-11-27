<template>
    <div class="containerBox">
        <h2 class="formTitle">内部测试告警</h2>
        <Form ref="testAlarm" :model="testAlarm" :label-width="90" :rules="ruleValidate">
            <FormItem label="管廊：" prop="tunnelId">
                <Select v-model="testAlarm.tunnelId">
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="区域：" prop="areaId">
                <Select v-model="testAlarm.areaId">
                    <Option v-for="item in areas" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="管舱：" prop="storeId">
                <Select v-model="testAlarm.storeId">
                    <Option v-for="item in stores" :value="item.id" :key="item.id">{{item.name}}</Option>
                </Select>
            </FormItem>
            <FormItem label="告警级别：" prop="level">
                <Select v-model="testAlarm.level">
                    <Option v-for="item in alarmLevels" :value="item.val" :key="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
            <FormItem label="对象类型：" prop="objtypeId">
                <Select v-model="testAlarm.objtypeId">
                    <Option v-for="item in objTypes" :value="item.val" :key="item.val">{{item.key}}</Option>
                </Select>
            </FormItem>
        </Form>
        <div slot="footer" class="footer">
            <Button type="primary" @click="submitForm('testAlarm')">确定</Button>
            <Button type="primary" @click="testSound()">测试声音</Button>
        </div>
    </div>
</template>
<script>
    import {
        TunnelService
    } from '@/services/tunnelService'
    import {
        EnumsService
    } from "@/services/enumsService"
    import {
        AlarmService
    } from "@/services/alarmService"
    import {
        Audio
    } from '@/utils/Audio'
    export default {
        data() {
            return {
                tunnels: [],
                stores: [],
                areas: [],
                objTypes: [],
                alarmLevels: [],
                testAlarm: {
                    objtypeId: null,
                    tunnelId: null,
                    areaId: null,
                    storeId: null,
                    level: null
                },
                ruleValidate: {
                    objtypeId: [{
                        type: 'number',
                        required: true,
                        message: '对象类型不能为空',
                        trigger: 'blur'
                    }],
                    tunnelId: [{
                        type: 'number',
                        required: true,
                        message: '管廊不能为空',
                        trigger: 'blur'
                    }],
                    areaId: [{
                        type: 'number',
                        required: true,
                        message: '区域不能为空',
                        trigger: 'blur'
                    }],
                    storeId: [{
                        type: 'number',
                        required: true,
                        message: '管仓不能为空',
                        trigger: 'blur'
                    }],
                    level: [{
                        type: 'number',
                        required: true,
                        message: '告警级别不能为空',
                        trigger: 'blur'
                    }]
                }
            }
        },
        watch: {
            'testAlarm.tunnelId': function (newVal) {
                this.getAreasByTunnelId(newVal)
                this.getStoresByTunnelId(newVal)
            }
        },
        mounted() {
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
            EnumsService.getAlarmLevel().then(
                res => {
                    this.alarmLevels = res
                },
                error => {
                    this.Log.info(error);
                }
            )
        },
        methods: {
            //get areas
            getAreasByTunnelId(tunnelId) {
                this.testAlarm.areaId = null
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
            getStoresByTunnelId(tunnelId) {
                this.testAlarm.storeId = null
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
            submitForm(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        AlarmService.manualAddAlarm(this.testAlarm).then(res => {
                            if (res.length) {
                                this.$Message.error(res)
                            } else {
                                this.$Message.success("提交成功！")
                            }
                            for (let item in this.testAlarm) {
                                this.testAlarm[item] = null
                            }
                        })
                    } else {
                        this.$Message.error("提交失败！")
                    }
                })
            },
            testSound() {
                Audio.createAlarmVoice()
            }
        }
    }
</script>
<style scoped>
    .formTitle,
    .ivu-form,
    .ivu-form-label-right,
    .footer {
        width: 50%;
        max-width: 500px;
        margin: 10px auto 0px;
        margin-left: 30%;

    }

    .footer {
        text-align: center;
    }

    .containerBox {
        padding-top: 2vmin;
    }
</style>