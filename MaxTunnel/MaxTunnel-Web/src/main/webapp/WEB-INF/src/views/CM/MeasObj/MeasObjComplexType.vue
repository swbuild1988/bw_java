<template>
    <div>
        <div style="margin:10px;position: relative;">
            <Form ref="AIData" :model="AIData" :rules="AIDataRules" :label-width="160" class="decimal">
                <FormItem label="AI数据小数点位数：" prop="decimal">
                    <Input v-model="AIData.decimal" type="text" />
                </FormItem>
            </Form>
            <Button type="primary" size="small" @click="saveDecimal" style="position: absolute; top:6px; right:40px;">保存</Button>
        </div>
        <hr>
        <div class="tableWrapper">
            <Button type="primary" size="small" @click="addUnitParam" class="add">添加</Button>
            <div class="table">
                <Table stripe border :columns="unitColumns" :data="complexType"></Table>
            </div>
        </div>
    	<Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset()" :mask-closable="false" width="800">
            <Form ref="unitParam" :model="modal.info" :rules="unitParam" :label-width="120">
                <FormItem label="对象名称：" prop="objectName">
                    <Input v-model="modal.info.objectName" type="text" />
                </FormItem>
                <FormItem label="对象类型：" prop="objectType">
                    <Select v-model="modal.info.objectType">
                        <Option v-for="item in objtypes" :key="item.val" :value="item.val+''">{{item.key}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="原始Id：" prop="originalId">
                    <Input v-model="modal.info.originalId" type="text" />
                </FormItem>
                <FormItem label="故障对应的Id：">
                    <Input v-model="modal.info.faultIds" type="text" placeholder="数字之间请用逗号隔开"/>
                </FormItem>
                <FormItem
                    v-for="item in modal.info.convertTypes"
                    v-if="item.status"
                    :key="item.addConvertIndex"
                    :label="'转换后类型' + item.addConvertIndex+':'"
                    :rules="{required: true, message: '类型 ' + item.index +' 不能为空', trigger: 'blur'}">
                    <Row>
                        <Col span="3">
                            <Input type="text" v-model="item.convertId" placeholder="Id" />
                        </Col>
                        <Col span="3">
                            <Input type="text" v-model="item.type" placeholder="类型" />
                        </Col>
                        <Col span="5">
                            <Input type="text" v-model="item.describe" placeholder="描述" />
                        </Col>
                        <Col span="4">
                            <Input type="text" v-model="item.code" placeholder="英文" />
                        </Col>
                        <Col span="4">
                            <Input type="text" v-model="item.value" placeholder="对应点位的值" />
                        </Col>
                        <Col span="3">
                            <Input type="text" v-model="item.index" placeholder="排序字段"/> 
                        </Col>
                        <Col span="2">
                            <Button type="ghost" @click="handleRemove(item.addConvertIndex)">Delete</Button>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem>
                    <Row>
                        <Col span="12">
                            <Button type="dashed" long @click="handleAdd" icon="plus-round">添加转换后的类型</Button>
                        </Col>
                    </Row>
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
					title: "对象名称",
					key: "objectName",
					align: 'center'
				},
				{
					title: "对象类型",
					key: "objectType",
                    align: 'center',
                    render: (h,params) => {
                        let id = params.row.objectType
                        let typeName = this.objtypes.find(type => {
							return type.val === id
						})
						return h('div',typeName.key)
                    }
				},
				{
					title: "原始Id",
					align: 'center',
					key: 'originalId',
                },
                {
					title: "故障对应的Id",
					align: 'center',
                    key: 'faultIds',
                    render: (h,params) => {
                        if(params.row.faultIds){
                            return h('div',params.row.faultIds.join(','))
                        } else {
                            return h('div','')
                        }
                    }
                },
                {
					title: "转换后的类型",
					align: 'center',
                    key: 'convertTypes',
                    render: (h,params) => {
                        let states = []
                        params.row.convertTypes.forEach(type=>{
                            states.push(type.describe)
                        })
                        return h('div',states.join(','))
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
			complexType: [],
			modal: {
				isShow: false,
				title: '新增参数',
				info: {
					objectType: null,
					objectName: null,
                    faultIds: null,
                    originalId: null,
                    convertTypes: [
                        {
                            code: null,
                            type: null,
                            describe: null,
                            index: null,
                            convertId: null,
                            value: null,
                            addConvertIndex: 1,
                            status: 1
                        }
                    ]
				}
			},
			unitParam: {
				objectName: [
					{ required: true, message: '请输入名称', trigger: 'blur' }
				],
				objectType: [
					{ required: true, message: '请选择对象类型', trigger: 'change' }
				],
				originalId: [
					{ required: true, message: '请输入原始Id', trigger: 'blur' }
                ]
            },
            AIData: {
                decimal: null
            },
            AIDataRules: {
                decimal: [
					{ required: true, message: '请输入数字', trigger: 'blur' }
				]
            },
            objtypes: [],
            addConvertIndex: 1
        }
    },
    computed:{
        addParams(){
            let addParams = this.modal.info
            addParams.faultIds = addParams.faultIds.length ? addParams.faultIds.split(',') : null
            addParams.convertTypes.forEach(convert=>{
                delete convert.addConvertIndex
                delete convert.status
            })
            return addParams
        }
    },
    mounted(){
        this.getObjTypes()
        this.getObjComplexType()
        this.getAIDataDecimal()
    },
    methods:{
        getObjTypes(){
            EnumsService.getObjType().then(
                result => {
                    this.objtypes = result;
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        getObjComplexType(){
            MeasObjServer.getObjComplexType().then(
                res=>{
					this.complexType = res
				},
				err => this.Log.info(err)
            )
        },
        getAIDataDecimal(){
            MeasObjServer.getAIDataDecimal().then(
                res=>{
                    this.AIData.decimal = res
                },
                err => this.Log.info(err)
            )
        },
		edit(item){
            this.modal.info = item
            this.modal.info.objectType = this.modal.info.objectType + ''
            this.modal.info.convertTypes.forEach((convert,index)=>{
                convert.addConvertIndex = index + 1
                convert.status = 1
            })
            this.addConvertIndex = item.convertTypes.length
            this.modal.title = '修改参数'
			this.modal.isShow = true
		},
		del(index){
			this.$Modal.confirm({
				title: '删除参数',
				content: '<p>确认删除'+this.complexType[index].objectName+'吗？</p>',
				onOk: () => {
					this.complexType.splice(index,1)
					MeasObjServer.updateObjComplexType(this.complexType).then(
						res=>{
							this.$Message.success('删除成功')
							this.getObjComplexType()
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
                        this.complexType.push(this.addParams)
						MeasObjServer.updateObjComplexType(this.complexType).then(
							res=>{
								this.$Message.success('添加成功')
                                this.getObjComplexType()
                                this.handleReset()
                                this.modal.isShow = false
                                this.addConvertIndex = 1
							},
							err => this.$Message.success('添加失败，请稍后再试')
						)
					} else {
						let index = this.modal.info._index
                        this.complexType[index] = this.addParams
						MeasObjServer.updateObjComplexType(this.complexType).then(
							res=>{
								this.$Message.success('修改成功')
                                this.getObjComplexType()
                                this.handleReset()
                                this.modal.isShow = false
                                this.addConvertIndex = 1
							},
							err => this.$Message.error('修改失败，请稍后再试')
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
				objectType: null,
                objectName: null,
                faultIds: null,
                originalId: null,
                convertTypes: null
			}
        },
        saveDecimal(){
            MeasObjServer.updateAIDataDecimal(this.AIData.decimal).then(
                res=>{
                    this.$Message.success("修改成功")
                    this.getAIDataDecimal()
                },
                err => this.$Message.error('修改失败，请稍后再试')
            )
        },
        handleAdd () {
            this.addConvertIndex++;
            this.modal.info.convertTypes.push({
                code: null,
                type: null,
                describe: null,
                index: null,
                convertId: null,
                value: null,
                addConvertIndex: this.addConvertIndex,
                status: 1
            });
        },
        handleRemove (index) {
            let curType = this.modal.info.convertTypes.find(type=>{
                return type.addConvertIndex === index
            })
            curType.status = 0;
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
		height: 440px;
		overflow-y: auto;
	}
    .decimal{
        width: 70%;
    }
</style>