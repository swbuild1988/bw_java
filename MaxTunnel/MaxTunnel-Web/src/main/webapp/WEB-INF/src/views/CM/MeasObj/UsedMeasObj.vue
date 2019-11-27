<template>
    <div>
        <div class="tableWrapper">
            <Button type="primary" size="small" @click="addUnitParam" class="add">添加</Button>
            <div class="table">
                <Table stripe border :columns="unitColumns" :data="objTypes"></Table>
            </div>
        </div>
    	<Modal v-model="modal.isShow" title="添加类型" @on-cancel="handleReset()" :mask-closable="false">
            <Form ref="unitParam" :model="modal.info" :rules="unitParam" :label-width="120" class="form">
                <FormItem label="类型：" prop="ids">
                    <Select v-model="modal.info.ids" multiple>
                        <Option v-for="item in unshowTypes" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
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

export default {
    data(){
        const validateType = (rule, value, callback) => {
            if (!value.length) {
                callback(new Error('请选择类型'));
            } else {
                callback()
            }

        };
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
					title: "操作",
					key: "action",
					align: "center",
					render: (h, params) => {
						return h("div", [
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
			objTypes: [],
			modal: {
				isShow: false,
				info: {
					ids: [],
				}
			},
			unitParam: {
				ids: [
                    { validator: validateType, trigger: 'change' }
				]
            },
            unshowTypes: []
        }
    },
    mounted(){
        this.getUnitParams()
    },
    computed:{
        postParams(){
            let params = []
            this.objTypes.forEach(type=>{
                params.push(type.id)
            })
            return params
        }
    },
    methods:{
        getUnitParams(){
            MeasObjServer.getMeasObjTypes().then(
                res=>{
                    this.objTypes = res.show
                    this.unshowTypes = res.unshow
				},
				err => this.Log.info(err)
            )
		},
		del(index){
			this.$Modal.confirm({
				title: '删除参数',
				content: '<p>确认删除'+this.objTypes[index].name+'吗？</p>',
				onOk: () => {
                    this.objTypes.splice(index,1)
                    let delParams = []
                    this.objTypes.forEach(item=>{
                        delParams.push(item.id)
                    })
					MeasObjServer.updateMeasObjTypes(delParams).then(
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
                    let addParams = this.postParams.concat(this.modal.info.ids)
                    MeasObjServer.updateMeasObjTypes(addParams).then(
                        res=>{
                            this.$Message.success('添加成功')
                            this.getUnitParams()
                            this.modal.isShow = false
                        },
                        err => this.$Message.success('添加失败，请稍后再试')
                    )
				} else {
					this.$Message.error('请正确填写')
				}
			})
		},
		addUnitParam(){
			this.modal.isShow = true
		},
		handleReset(){
			this.modal.info.ids = []
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
    .form >>> .ivu-form-item-label:before{
        content: '*';
        display: inline-block;
        margin-right: 4px;
        line-height: 1;
        font-family: SimSun;
        font-size: 12px;
        color: #ed3f14;
    }
</style>