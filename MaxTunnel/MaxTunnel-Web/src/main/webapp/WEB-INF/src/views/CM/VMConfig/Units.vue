<template>
    <div>
        <div class="tableWrapper">
            <Button type="primary" size="small" @click="addUnitParam" class="add">添加</Button>
            <div class="table">
                <Table stripe border :columns="unitColumns" :data="unitData"></Table>
            </div>
        </div>
    	<Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset()" :mask-closable="false">
            <Form ref="unitParam" :model="modal.info" :rules="unitParam" :label-width="120">
                <FormItem label="编号：" prop="id">
                    <Input v-model="modal.info.id" type="text" />
                </FormItem>
                <FormItem label="名称：" prop="name">
                    <Input v-model="modal.info.name" type="text" />
                </FormItem>
                <FormItem label="经度：" prop="longitude">
                    <Input v-model="modal.info.longitude" type="text" />
                </FormItem>
                <FormItem label="纬度：" prop="latitude">
                    <Input v-model="modal.info.latitude" type="text" />
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save()">确定</Button>
            </div> 
        </Modal>
    </div>
</template>
<script>
import { RelatedUnitService } from "../../../services/relatedUnitService";

export default {
    data(){
        return {
			unitColumns: [
				{
					title: "编号",
					key: "id",
					align: 'center'
				},
				{
					title: "名称",
					key: "name",
					align: 'center'
				},
				{
					title: "经度",
					align: 'center',
					key: 'longitude',
                },
                {
					title: "纬度",
					align: 'center',
					key: 'latitude',
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
			unitData: [],
			modal: {
				isShow: false,
				title: '新增参数',
				info: {
					id: null,
					name: null,
                    longitude: null,
                    latitude: null
				}
			},
			unitParam: {
				id: [
					{ required: true, message: '请输入编号', trigger: 'blur' }
				],
				name: [
					{ required: true, message: '请输入名称', trigger: 'blur' }
				],
				longitude: [
					{ required: true, message: '请输入经度', trigger: 'blur' }
                ],
                latitude: [
					{ required: true, message: '请输入纬度', trigger: 'blur' }
				]
			},
        }
    },
    mounted(){
        this.getUnitParams()
    },
    methods:{
        getUnitParams(){
            RelatedUnitService.getVMUnits().then(
                res=>{
					this.unitData = res
				},
				err => this.Log.info(err)
            )
		},
		edit(item){
			this.modal.info = item
            this.modal.title = '修改参数'
			this.modal.isShow = true
		},
		del(index){
			this.$Modal.confirm({
				title: '删除参数',
				content: '<p>确认删除'+this.unitData[index].name+'吗？</p>',
				onOk: () => {
					this.unitData.splice(index,1)
					RelatedUnitService.updateVMUnits(this.unitData).then(
						res=>{
							this.$Message.success('删除成功')
							this.getUnitParams()
						},
						err => this.$Message.success('删除失败，请稍后再试')
					)
				},
				onCancel: () => {}
			});
		},
		save(){
			this.$refs.unitParam.validate(valid => {
				if(valid){
					if(this.modal.title === '新增参数'){
						this.unitData.push(this.modal.info)
						RelatedUnitService.updateVMUnits(this.unitData).then(
							res=>{
								this.$Message.success('添加成功')
								this.getUnitParams()
								this.modal.isShow = false
							},
							err => this.$Message.success('添加失败，请稍后再试')
						)
					} else {
						let index = this.modal.info._index
						let updateObj = {
							id: this.modal.info.id,
							name: this.modal.info.name,
                            longitude: this.modal.info.longitude,
                            latitude: this.modal.info.latitude
						}
                        this.unitData[index] = updateObj
						RelatedUnitService.updateVMUnits(this.unitData).then(
							res=>{
								this.$Message.success('修改成功')
								this.getUnitParams()
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
		addUnitParam(){
			this.modal.title = '新增参数'
			this.modal.isShow = true
		},
		handleReset(){
			this.modal.info = {
				id: null,
				name: null,
                longitude: null,
                latitude: null
			}
		}
    }
}
</script>
<style scoped>
	.tableWrapper{
		width: 94%;
		margin: 0 auto;
		position: relative;
		padding-top: 48px;
	}
	.add{
		position: absolute;
		top: 12px;
		right: 10px;
	}
	.table{
		height: 500px;
		overflow-y: auto;
	}
</style>