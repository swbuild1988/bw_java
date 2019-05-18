<template>
    <div>
        <h1 style="margin-left: 20px;margin-top:10px;display: inline-block">巡检小组管理</h1>
        <Button type="primary" class="addBtn" @click="add">新增</Button>
        <Table border ref="selection" :columns="groupColums" :data="groupData" style="margin:20px;"></Table>
        <Page :total="page.pageTotal" :current="page.pageNum" show-total placement="top" 
              @on-change="handlePage" show-elevator class="pageStyle"></Page>
        <Modal v-model="isShow" :title=title>
            <Form ref="groupInfo" :model="groupInfo" :label-width="80">
                <FormItem label="名称：">
                    <Input v-model="groupInfo.name"></Input>
                </FormItem>
                <FormItem label="负责人：">
                    <Select v-model="groupInfo.leaderId">
                        <Option v-for="item in leaders" :key="item.id" :value="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" @click="addGroup" v-show="isAdd">确定1</Button>
                <Button type="primary" @click="editGroup" v-show="isEdit">确定2</Button>
            </div>
        </Modal>
        <Modal v-model="isTransfer" title="小组成员管理">
            <Transfer
                :titles="transferTitles"
                :data="numbers"
                :target-keys="targetKeys1"
                :render-format="wender1"
                @on-change="handleChange1"
            ></Transfer>
            <div slot="footer">
                <Button type="primary" @click="addNumbers">确定</Button>
            </div>
        </Modal>
    </div>    
</template>
<script>
import { GroupServices } from '@/services/groupService.js'
export default {
    data(){
        return{
            page: {
                pageTotal: 0,
                pageNum: 1,
                pageSize: 10
            },
            groupInfo: {
                name: null,
                leaderId: null
            },
            groupColums: [
                {
                    type: 'index',
                    align: 'center',
                    width: 60
                },
                {
                    title: '名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '负责人',
                    key: 'leaderName',
                    align: 'center'
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button',{
                                props: {
                                    type: "primary",
                                    size: "small"
                                },
                                style: {
                                    marginRight: "5px"
                                },
                                on: {
                                    click: () => {
                                        this.eidt(params.row.id);
                                    }
                                }
                            },'修改'),
                            h('Button', {
                                props: {
                                    type: 'info',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: "5px"
                                },
                                on: {
                                    click: () => {
                                        this.addGroupNumbers(params.row.id)
                                    }    
                                }
                            },'新增小组成员'),
                            h('Button',{
                               props: {
                                    type: "error",
                                    size: "small"
                                },
                                on: {
                                    click: () => {
                                        this.del(params.row.id);
                                    }
                                } 
                            }, '删除')
                        ])
                    }
                }
            ],
            groupData: [],
            isShow: false,
            leaders: [],
            transferTitles: ['员工列表', '组员表'],
            numbers: [],
            targetKeys1: this.getTargetKeys(),
            isTransfer: false,
            groupId: null,
            isAdd: false,
            isEdit: false,
            title: null
        }
    },
    mounted(){
        this.getGroupsDatagrid()
    },
    methods:{
        getGroupsDatagrid(){
            var param = {
                // name: this.groupInfo.name,
                // leaderId: this.groupInfo.leaderId,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            }
            GroupServices.getGroupDatagrid(param).then(
                result => {
                    this.groupData = result.list
                    this.page.pageTotal = result.total
                },
                error => {
                    this.Log.info(error)
                }
            )

        },
        handlePage(value){
            this.page.pageNum = value;
            this.getGroupsDatagrid();
        },
        add(){
            this.isShow = true
            this.getStaff()
            this.isAdd = true
            this.isEdit = false
            this.title = '新增责任班组'
        },
        addGroup(){
            GroupServices.addGroup(this.groupInfo).then(
                result => {
                    this.$Message.success('添加成功！')
                    this.getGroupsDatagrid()
                    this.isShow = false
                },
                error => {
                    this.$Message.error('添加失败')
                }
            )
        },
        //添加员工时使用,选择leader
        getStaff(){
            GroupServices.getStaffs().then(
                result => {
                    this.leaders = result
                    let arr = []
                    result.forEach(element => {
                        arr.push({
                            key: element.id.toString(),
                            label: element.name,
                        })
                    });
                    this.numbers = arr
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        //修改leader时使用
        getLeaderInEidt(id){
            GroupServices.getLeaderInEidt(id).then(
                result => {
                    this.leaders = result
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        eidt(id){
            this.getLeaderInEidt(id)
            this.isShow = true
            this.isAdd = false
            this.isEdit = true
            this.title = '修改责任班组'
        },
        editGroup(){
            GroupServices.editGroup(this.groupInfo).then(
                result => {
                    this.$Message.success('修改成功')
                    this.getGroupsDatagrid()
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        del(id){
            this.$Modal.confirm({
                title: '责任班组管理',
                content: '<p>确定删除该责任班组吗</p>',
                onOk: () => {
                    GroupServices.delGroup(id).then(
                        result => {
                            this.$Message.success('删除成功')
                            this.getGroupsDatagrid()
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                },
                onCancel: () => {}
            })
        },
        getTargetKeys(){
            // return this.numbers.filter().map(item => item.key)
        }, 

        wender1(item){
            return item.label
        },
        handleChange1(newTargetKeys){
            this.targetKeys1 = newTargetKeys
        },
        addGroupNumbers(id){
            this.isTransfer = true
            this.groupId = id
            GroupServices.getStaffs(id).then(
                result => {
                    let arr = []
                    result.forEach(element => {
                        arr.push({
                            key: element.id.toString(),
                            label: element.name,
                        })
                    });
                    this.numbers = arr
                },
                error => {
                    this.Log.info(error)
                }
            )
            GroupServices.getInspectionGroup(id).then(
                result => {
                    let arr = []
                    result.members.forEach(item => {
                        if(result.leaderId != item.id){
                            arr.push(item)
                        }
                    })
                    let newArr = []
                    let targetKeysArr = []
                    arr.forEach(element => {
                        newArr.push({
                            key: element.id.toString(),
                            label: element.name,
                        })
                        targetKeysArr.push(element.id.toString())
                    })
                    this.targetKeys1 = targetKeysArr
                    this.numbers = this.numbers.concat(newArr)
                },
                error => {
                    this.Log.info(error)
                }
            )
        },
        addNumbers(){
            if(this.targetKeys1.length!=0){
                GroupServices.addNumbers(this.groupId, this.targetKeys1).then(
                    result => {
                        this.isTransfer = false
                        this.$Message.success('添加成功！')
                    },
                    error => {
                        this.$Message.error('添加失败！')
                    }
                )
            }else{
                this.$Message.info("请选择小组成员！")
            }
        }
    }
}
</script>
<style scoped>
.pageStyle {
    text-align: right;
    margin-top: 20px;
    margin-right: 10px;
}
.addBtn{
    float: right;
    margin-top: 2vmin;
    margin-right: 2vmin;
}
</style>
