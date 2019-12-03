<template>
    <div>
        <div class="tableWrapper">
            <Button type="primary" size="small" @click="addCableType" class="add">添加</Button>
            <div class="table">
                <Table stripe border :columns="unitColumns" :data="cableTypes"></Table>
            </div>
        </div>
        <Modal v-model="modal.isShow" :title="modal.title" @on-cancel="handleReset()" :mask-closable="false">
            <Form ref="unitParam" :model="modal.info" :rules="unitParam" :label-width="120">
                <FormItem label="名称：" prop="typeName">
                    <Input v-model="modal.info.typeName" type="text" />
                </FormItem>
                <FormItem label="可放管仓：" prop="inStoreTypeIds">
                    <Select v-model="modal.info.inStoreTypeIds" multiple>
                        <Option v-for="item in storeTypes" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <FormItem label="入廊长度" prop="length">
                    <Input v-model="modal.info.length" type="number" />
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" @click="save()">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
    import {
        MeasObjServer
    } from "../../../services/MeasObjectSerivers";
    import {
        EnumsService
    } from "../../../services/enumsService";
    import {
        StoreService
    } from "../../../services/storeService";

    export default {
        data() {
            return {
                unitColumns: [{
                        title: "名称",
                        key: "typeName",
                        align: 'center'
                    },
                    {
                        title: "可放管仓",
                        align: 'center',
                        key: 'inStoreTypeIds',
                    },
                    {
                        title: "入廊长度",
                        align: 'center',
                        key: 'length',
                    },
                    {
                        title: "操作",
                        key: "action",
                        align: "center",
                        render: (h, params) => {
                            return h("div", [
                                h(
                                    "Button", {
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
                                    "Button", {
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
                cableTypes: [],
                modal: {
                    isShow: false,
                    title: '新增类型',
                    info: {
                        id: null,
                        typeName: null,
                        inStoreType: null,
                        length: 0,
                        inStoreTypeIds: []
                    }
                },
                unitParam: {
                    typeName: [{
                        required: true,
                        message: '请选择名称',
                        trigger: 'change'
                    }]
                },
                storeTypes: []
            }
        },
        mounted() {
            this.getCableTypes()
            this.getStoreTypes()
        },
        methods: {
            getStoreTypes() {
                StoreService.getStoreType().then(
                    res => {
                        this.storeTypes = res
                        this.Log.info("storeTypes:", this.storeTypes)
                    },
                    err => this.Log.info(err)
                )
            },
            getCableTypes() {
                EnumsService.getCableTypes().then(
                    res => {
                        this.cableTypes = res
                    },
                    err => this.Log.info(err)
                )
            },
            edit(item) {
                this.modal.info = item
                this.modal.title = '修改类型'
                this.modal.isShow = true
            },
            del(index) {
                this.$Modal.confirm({
                    title: '删除参数',
                    content: '<p>确认删除' + this.cableTypes[index].typeName + '吗？</p>',
                    onOk: () => {
                        EnumsService.deleteCableType(this.cableTypes[index].id).then(
                            res => {
                                this.$Message.success('删除成功')
                                this.getCableTypes()
                            },
                            err => this.$Message.success('删除失败，请稍后再试')
                        )
                    },
                    onCancel: () => {}
                });
            },
            save() {
                this.$refs.unitParam.validate(valid => {
                    if (valid) {
                        if (this.modal.title === '新增类型') {
                            let newCableType = {
                                typeName: this.modal.info.typeName,
                                inStoreType: this.modal.info.inStoreTypeIds.join(',')
                            }
                            EnumsService.addCableType(newCableType).then(
                                res => {
                                    this.$Message.success('添加成功')
                                    this.getCableTypes()
                                    this.modal.isShow = false
                                    this.modal.info = {
                                        id: null,
                                        typeName: null,
                                        inStoreType: null,
                                        length: 0,
                                        inStoreTypeIds: []
                                    }
                                },
                                err => this.$Message.success('添加失败，请稍后再试')
                            )
                        } else {
                            let editCableType = {
                                id: this.modal.info.id,
                                typeName: this.modal.info.typeName,
                                inStoreType: this.modal.info.inStoreTypeIds.join(','),
                                length: this.modal.info.length
                            }
                            EnumsService.editCableType(editCableType).then(
                                res => {
                                    this.$Message.success('编辑成功')
                                    this.getCableTypes()
                                    this.modal.isShow = false
                                    this.modal.info = {
                                        id: null,
                                        typeName: null,
                                        inStoreType: null,
                                        length: 0,
                                        inStoreTypeIds: []
                                    }
                                }
                            )
                        }
                    } else {
                        this.$Message.error('请正确填写')
                    }
                })
            },
            addCableType() {
                this.modal.title = '新增类型'
                this.modal.isShow = true
            },
            handleReset() {
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
    .tableWrapper {
        width: 94%;
        margin: 0 auto;
        position: relative;
        padding-top: 48px;
    }

    .add {
        position: absolute;
        top: 12px;
        right: 10px;
    }

    .table {
        height: 500px;
        overflow-y: auto;
    }
</style>