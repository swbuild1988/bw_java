<template>
    <div>
        <div class="tableWrapper">
            <Button type="primary" size="small" @click="addUnitParam" class="add">添加</Button>
            <div class="table">
                <Table stripe border :columns="unitColumns" :data="extremum"></Table>
            </div>
        </div>
    	<Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset()" :mask-closable="false">
            <Form ref="unitParam" :model="modal.info" :rules="unitParam" :label-width="120">
                <FormItem label="名称：" prop="typeName" v-if="modal.title === '修改参数'">
                    <Input v-model="modal.info.typeName" type="text" disabled/>
                </FormItem>
                <FormItem label="名称：" prop="typeName" v-if="modal.title === '新增参数'">
                    <Select v-model="modal.info.typeName">
                        <Option v-for="item in dataTypes" :key="item.val" :value="item.key">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="可测量最小值：" prop="min">
                    <Input v-model="modal.info.min" type="text" />
                </FormItem>
                <FormItem label="可测量最大值：" prop="max">
                    <Input v-model="modal.info.max" type="text" />
                </FormItem>
                 <FormItem label="正常值最小值：" prop="normalMin">
                    <Input v-model="modal.info.normalMin" type="text" />
                </FormItem>
                <FormItem label="正常值最大值：" prop="normalMax">
                    <Input v-model="modal.info.normalMax" type="text" />
                </FormItem>
            </Form>   
            <div slot="footer">
                <Button type="primary" @click="save()">确定</Button>
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
			unitColumns: [
				{
					title: "名称",
					key: "typeName",
					align: 'center'
				},
				{
					title: "可测量最小值",
					align: 'center',
					key: 'min',
                },
                {
					title: "可测量最大值",
					align: 'center',
					key: 'max',
                },
                {
					title: "正常值最小值",
					align: 'center',
					key: 'normalMin',
                },
                {
					title: "正常值最大值",
					align: 'center',
					key: 'normalMax',
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
			extremum: [],
			modal: {
				isShow: false,
				title: '新增参数',
				info: {
					id: null,
					typeName: null,
                    min: null,
                    max: null,
                    normalMax: null,
                    normalMin: null
				}
			},
			unitParam: {
				typeName: [
					{ required: true, message: '请选择名称', trigger: 'change' }
				],
				min: [
					{ required: true, message: '请输入可测量最小值', trigger: 'blur' }
                ],
                max: [
					{ required: true, message: '请输入可测量最大值', trigger: 'blur' }
                ],
                normalMin: [
					{ required: true, message: '请输入正常值最小值', trigger: 'blur' }
                ],
                normalMax: [
					{ required: true, message: '请输入正常值最大值', trigger: 'blur' }
				]
            },
            dataTypes: []
        }
    },
    mounted(){
        this.getUnitParams()
        this.getDataTypes()
    },
    methods:{
        getDataTypes(){
            EnumsService.getDataType().then(
                res=>{
                    let dataTypes = res.find(item=>{
                        return item.val === 1
                    })
                    this.dataTypes = dataTypes.list
                },
				err => this.Log.info(err)
            )
        },
        getUnitParams(){
            MeasObjServer.getMeasObjExtremun().then(
                res=>{
                    this.extremum = res
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
				content: '<p>确认删除'+this.extremum[index].name+'吗？</p>',
				onOk: () => {
					this.extremum.splice(index,1)
					MeasObjServer.updateMeasObjExtremun(this.extremum).then(
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
                        let curItem = this.dataTypes.find(type=>{
                            return type.key === this.modal.info.typeName
                        })
                        this.modal.info.id = curItem.val
						this.extremum.push(this.modal.info)
						MeasObjServer.updateMeasObjExtremun(this.extremum).then(
							res=>{
								this.$Message.success('添加成功')
								this.getUnitParams()
								this.modal.isShow = false
							},
							err => this.$Message.success('添加失败，请稍后再试')
						)
					} else {
						let index = this.modal.info._index
                        delete this.modal.info._index
                        delete this.modal.info._rowKey
                        this.extremum[index] = this.modal.info
						MeasObjServer.updateMeasObjExtremun(this.extremum).then(
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
				typeName: null,
                min: null,
                max: null,
                normalMax: null,
                normalMin: null
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