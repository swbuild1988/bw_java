<template>
    <div>
		<div style="margin:10px;position: relative;">
            <Form ref="lightDistance" :model="lightDistance" :rules="lightDistanceRules" :label-width="160" style="width: 70%">
                <FormItem label="管廊内灯光距离(m)：" prop="distance">
                    <Input v-model="lightDistance.distance" type="text" placeholder="单位米"/>
                </FormItem>
            </Form>
            <Button type="primary" size="small" @click="saveDistance" style="position: absolute; top:6px; right:40px;">保存</Button>
			 <Form ref="closeTime" :model="closeTime" :rules="closeTimeRules" :label-width="160" style="width: 70%">
                <FormItem label="入侵延迟关闭时间(s)：" prop="time">
                    <Input v-model="closeTime.time" type="text" placeholder="单位秒"/>
                </FormItem>
            </Form>
            <Button type="primary" size="small" @click="saveTime" style="position: absolute; top:64px; right:40px;">保存</Button>
        </div>
		<hr>
        <div class="tableWrapper">
            <div class="table">
                <Table stripe border :columns="unitColumns" :data="alarmTimeData"></Table>
            </div>
        </div>
    	<Modal v-model="modal.isShow" title="修改延迟时间" @on-cancel="handleReset()" :mask-closable="false">
            <Form ref="alarmParam" :model="modal.info" :rules="alarmParam" :label-width="120">
				<FormItem label="名称：" prop="levelName">
                    <Input v-model="modal.info.levelName" type="text" disabled/>
                </FormItem>
                <FormItem label="告警级别：" prop="id">
                    <Input v-model="modal.info.id" type="text" disabled/>
                </FormItem>
                <FormItem label="延迟时间：" prop="name">
                    <Input v-model="modal.info.name" type="text" />
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save()">确定</Button>
            </div> 
        </Modal>
    </div>
</template>
<script>
import { AlarmService } from "../../../services/alarmService";
import { TunnelService } from "../../../services/tunnelService";

export default {
    data(){
        return {
			unitColumns: [
				{
					title: "名称",
					key: "levelName",
					align: 'center'
				},
				{
					title: "告警级别",
					key: "id",
					align: 'center'
				},
                {
					title: "延迟时间",
					align: 'center',
					key: 'name',
				},
				{
					title: "操作",
					key: "action",
					align: "center",
					render: (h, params) => {
						return h("div", [
						h(
							"Button",
							{
							props: {
								type: "primary",
								size: "small"
							},
							style: {
								marginLeft: "5px"
							},
							on: {
								click: () => {
									this.edit(params.row);
								}
							}
							},
							"修改"
						)
						]);
					}
				}
			],
			alarmTimeData: [],
			modal: {
				isShow: false,
				info: {
					id: null,
					name: null,
                    levelName: null
				}
			},
			alarmParam: {
				name: [
					{ required: true, message: '请输入延迟时间', trigger: 'blur' }
                ]
			},
			lightDistance: {
				distance: null
			},
			lightDistanceRules: {
				distance: [
					{ required: true, message: '请输入灯光距离', trigger: 'blur' }
                ]
			},
			closeTime: {
				time: null
			},
			closeTimeRules: {
				time: [
					{ required: true, message: '请输入入侵延迟关闭时间', trigger: 'blur' }
                ]
			}
        }
    },
    mounted(){
		this.getalarmParams()
		this.getCloseTime()
		this.getLightDistance()
    },
    methods:{
		getCloseTime(){
			AlarmService.getCloseTime().then(
				res=>{
					this.closeTime.time = res
				},
				err => this.Log.info(err)
			)
		},
		getLightDistance(){
			TunnelService.getTunnelLightDistance().then(
				res=>{
					this.lightDistance.distance = res
				},
				err => this.Log.info(err)
			)
		},
        getalarmParams(){
            AlarmService.getAlarmTime().then(
                res=>{
					this.alarmTimeData = res
				},
				err => this.Log.info(err)
            )
		},
		edit(item){
			this.modal.info = item
			this.modal.isShow = true
		},
		save(){
			this.$refs.alarmParam.validate(valid => {
				if(valid){
					let index = this.modal.info._index
					let updateObj = {
						id: this.modal.info.id,
						name: this.modal.info.name,
						levelName: this.modal.info.levelName
					}
					this.alarmTimeData[index] = updateObj
					AlarmService.updateAlarmTime(this.alarmTimeData).then(
						res=>{
							this.$Message.success('修改成功')
							this.getalarmParams()
							this.modal.isShow = false
						},
						err => this.$Message.success('修改失败，请稍后再试')
					)
				} else {
					this.$Message.error('请正确填写')
				}
			})
		},
		handleReset(){
			this.modal.info = {
				id: null,
				name: null,
                levelName: null
			}
		},
		saveDistance(){
			TunnelService.updateTunnelLightDistance(this.lightDistance.distance).then(
				res=>{
					this.$Message.success("修改成功")
					this.getLightDistance()
				},
				err => {
					this.$Message.error("修改失败，请稍后再试")
				}
			)
		},
		saveTime(){
			AlarmService.updateCloseTime(this.closeTime.time).then(
				res=>{
					this.$Message.success("修改成功")
					this.getCloseTime()
				},
				err => {
					this.$Message.error("修改失败，请稍后再试")
				}
			)
		}
    }
}
</script>
<style scoped>
	.tableWrapper{
		width: 96%;
		margin: 0 auto;
		position: relative;
		padding-top: 48px;
	}
	.table{
		height: 400px;
		overflow-y: auto;
	}
</style>