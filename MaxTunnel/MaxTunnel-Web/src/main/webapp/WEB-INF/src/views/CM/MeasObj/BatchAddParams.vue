<template>
    <div>
        <div class="form">
            <Row>
                <Col span="6">
                	<span>管廊编号起始：</span>
                    <Input v-model="tunnelParam.tunnelNumber" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span>区域编号起始：</span>
                    <Input v-model="tunnelParam.areaNumber" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span>管仓编号起始：</span>
                    <Input v-model="tunnelParam.storeNumber" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span>设备类型编号起始：</span>
                    <Input v-model="tunnelParam.typeNumber" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span>管廊编号长度：</span>
                    <Input v-model="tunnelParam.tunnelLength" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span>区域编号长度：</span>
                    <Input v-model="tunnelParam.areaLength" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span>管仓编号长度：</span>
                    <Input v-model="tunnelParam.storeLength" class="InputWidth" />
                </Col>
                <Col span="6">
                	<span class="word">设备类型长度</span><span>：</span>
                    <Input v-model="tunnelParam.typeLength" class="InputWidth" />
                </Col>
				<Col span="2" offset="22">
					<Button type="primary" size="small" @click="saveTunnelParam" style="margin-top: 4px;margin-left: 30px;">保存</Button>
				</Col>
            </Row>
        </div>
		<hr>
		<div class="tableWrapper">
			<Button type="primary" size="small" @click="addEquParam" class="add">添加</Button>
			<div class="table">
				<Table stripe border :columns="equipmentMatches" :data="objdata"></Table>
			</div>
		</div>
		<Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset()" :mask-closable="false">
            <Form ref="equipParam" :model="modal.info" :rules="equipParam" :label-width="120">
                <FormItem label="编号：" prop="sn">
                    <Input v-model="modal.info.sn" type="text" />
                </FormItem>
                <FormItem label="名称：" prop="name">
                    <Input v-model="modal.info.name" type="text" />
                </FormItem>
                <FormItem label="类型：" prop="typeId">
                    <Select v-model="modal.info.typeId">
                        <Option v-for="item in objtypes" :key="item.val" :value="item.val+''">{{item.key}}</Option>
                    </Select>
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="saveEquUpdate()">确定</Button>
            </div> 
        </Modal>
    </div>
</template>
<script>
import { MeasObjServer } from "../../../services/MeasObjectSerivers";
import { EnumsService } from "../../../services/enumsService";
export default {
    data(){
        return {
			tunnelParam: {
				areaLength: 0,
				areaNumber: 0,
				storeLength: 0,
				storeNumber: 0,
				tunnelLength: 0,
				tunnelNumber: 0,
				typeLength: 0,
				typeNumber: 0
			},
			equipmentMatches: [
				{
					title: "编号",
					key: "sn",
					align: 'center'
				},
				{
					title: "名称",
					key: "name",
					align: 'center'
				},
				{
					title: "类型",
					align: 'center',
					key: 'typeId',
					render: (h,params) => {
						let typeId = params.row.typeId
						let typeName = this.objtypes.find(type => {
							return type.val === typeId
						})
						return h('div',typeName.key)
					}
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
						),
						h(
							"Button",
							{
							props: {
								type: "error",
								size: "small"
							},
							style: {
								marginLeft: "5px"
							},
							on: {
								click: () => {
									this.del(params.index);
								}
							}
							},
							"删除"
						)
						]);
					}
				}
			],
			objtypes: [],
			objdata: [],
			modal: {
				isShow: false,
				title: '新增参数',
				info: {
					sn: null,
					name: null,
					typeId: null
				}
			},
			equipParam: {
				sn: [
					{ required: true, message: '请输入编号', trigger: 'blur' }
				],
				name: [
					{ required: true, message: '请输入名称', trigger: 'blur' }
				],
				typeId: [
					{ required: true, message: '请选择类型', trigger: 'change' }
				]
			},
        }
    },
    mounted(){
		this.getAllObjType()
        this.getBatchAddObjParams()
    },
    methods:{
		getAllObjType(){
			EnumsService.getObjType().then(
				res=>{
					this.objtypes = res
				},
				err => this.Log.info(err)
			)
		},
        getBatchAddObjParams(){
            MeasObjServer.getBatchAddObjParams().then(
                res=>{
					this.tunnelParam = res.tunnelParam
					this.objdata = res.equipmentInfo
				},
				err => this.Log.info(err)
            )
		},
		saveTunnelParam(){
			MeasObjServer.updateTunnelParams(this.tunnelParam).then(
				res=>{
					this.$Message.success('修改成功')
					this.getBatchAddObjParams()
				},
				err => this.$Message.success('修改失败，请稍后再试')
			)
		},
		edit(item){
			this.modal.info = item
			this.modal.info.typeId = this.modal.info.typeId + ''
			this.modal.title = '修改参数'
			this.modal.isShow = true
		},
		del(index){
			this.$Modal.confirm({
				title: '删除参数',
				content: '<p>确认删除'+this.objdata[index].name+'吗？</p>',
				onOk: () => {
					this.objdata.splice(index,1)
					MeasObjServer.updateEqiuParams(this.objdata).then(
						res=>{
							this.$Message.success('删除成功')
							this.getBatchAddObjParams()
						},
						err => this.$Message.success('删除失败，请稍后再试')
					)
				},
				onCancel: () => {}
			});
		},
		saveEquUpdate(){
			this.$refs.equipParam.validate(valid => {
				if(valid){
					if(this.modal.title === '新增参数'){
						this.objdata.push(this.modal.info)
						MeasObjServer.updateEqiuParams(this.objdata).then(
							res=>{
								this.$Message.success('添加成功')
								this.getBatchAddObjParams()
								this.modal.isShow = false
							},
							err => this.$Message.success('添加失败，请稍后再试')
						)
					} else {
						let index = this.modal.info._index
						let updateObj = {
							sn: this.modal.info.sn,
							name: this.modal.info.name,
							typeId: parseInt(this.modal.info.typeId)
						}
						this.objdata[index] = updateObj
						MeasObjServer.updateEqiuParams(this.objdata).then(
							res=>{
								this.$Message.success('修改成功')
								this.getBatchAddObjParams()
								this.modal.isShow = false
							},
							err => this.$Message.success('修改失败，请稍后再试')
						)
					}
				} else {
					this.$Message.error('请正确填写')
				}
			})
		},
		addEquParam(){
			this.modal.title = '新增参数'
			this.modal.isShow = true
		},
		handleReset(){
			this.modal.info = {
				sn: null,
				name: null,
				typeId: null
			}
		}
    }
}
</script>
<style scoped>
	.sn,
	.name {
		display: inline-block
	}

	.sn {
		width: 30px;
	}

	.name {
		width: 80px;
	}
	.InputWidth{
		width: 60%;
		margin-bottom: 4px;
	}
	.form{
		margin:2vmin;
	}
	.tableWrapper{
		width: 94%;
		margin: 0 auto;
		position: relative;
		padding-top: 34px;
	}
	.word{
		letter-spacing: 0.45em;
		margin-right: -0.45em;
	}
	.add{
		position: absolute;
		top: 5px;
		right: 10px;
	}
	.table{
		height: 400px;
		overflow-y: auto;
	}
</style>