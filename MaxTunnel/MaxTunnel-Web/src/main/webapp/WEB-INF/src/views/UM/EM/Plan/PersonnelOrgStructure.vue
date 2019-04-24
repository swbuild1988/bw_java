<template>
    <div>
        <!-- <vueTree :data="data"></vueTree> -->
        <treeChart v-bind="treeChartData"></treeChart>
        <Button type="primary" class="addPersonBtn" @click="addPersonBtn">添加人员</Button>
        <Modal
            v-model="isShowAdd"
            title="添加人员信息"
        >
            <Form :model="personInfo" :label-width="80">
                <FormItem label="姓名：">
                    <Input v-model="personInfo.name"></Input>
                </FormItem>
                <FormItem label="联系方式：">
                    <Input v-model="personInfo.tel"></Input>
                </FormItem>
                <FormItem label="直属上级：">
                    <Select v-model="personInfo.level" @on-change="changeLevel()">
                        <Option v-for="item in levelData" :key="item.key" :value="item.key">{{item.val}}</Option>
                    </Select>
                </FormItem>
                <!-- <FormItem label="所属父级">
                    <Select>
                        <Option></Option>
                    </Select>
                </FormItem> -->
            </Form>
            <div slot="footer">
                <Button type="primary" @click="addPerson">确定</Button>
            </div>
        </Modal>
    </div>
</template>
<script>
// import vueTree from './tree/components/org-tree/org-tree2'
import treeChart from '../../../../components/Common/Chart/TreeChart'
export default {

    components: {
        // TreeChart,
        // Vue2OrgTree,
        // vueTree
        treeChart
	},
    data(){
        return{
            treeChartData: {
                id: 'treeChart',
                url: '',
                data: {
                    name: '监控中心',
                    level: 1,
                    children: [
                        { name: '通风预案', level: 2, children: [
                            { name: '预案小组1', level: 3, children: [
                                { name: '张飒-135664102256', value: 3892, level: 4 },
                                { name: '顾明-14552102256', value:3812, level: 4 },
                                { name: '李晓风-13265412258', value: 3856, level: 4 },
                                { name: '窦唯-17865998541', value: 3816, level: 4 },
                                { name: 'tom-17865998541', value: 3817, level: 4 }
                            ] },
                            { name: 'graph', level: 3, children: [
                                { name: 'BetweennessCentrality', value: 3589, level: 4 },
                                { name: 'LinkDistance', value: 3578, level: 4 }
                            ] }
                        ] },
                        { name: "display", level: 2, children: [
                            {name: "DirtySprite", value: 8833, level: 3 },
                            {name: "LineSprite", value: 1732, level: 3 },
                            {name: "RectSprite", value: 3623, level: 3 },
                            {name: "TextSprite", value: 10066, level: 3 }
                        ] }
                    ]
                },
            },
            isShowAdd: false,
            personInfo: {
                name: null,
                tel: null,
                level: null
            },
            levelData: [
                { key: 1, val: '一级' },
                { key: 2, val: '二级' },
                { key: 3, val: '三级' },
                { key: 4, val: '四级' }
            ]
        }
    },
    methods: {
        addPersonBtn(){
            this.isShowAdd = true
        },
        changeLevel(){
            // var arr = this.treeChartData.data.children
            // var result = []
            // for(var i in arr){
            //     var obj = arr[i]
            //     if(obj.level==this.personInfo.level){
            //         break
            //     }
            // }
        },
        addPerson(){
            this.treeChartData.isShowAdd = false
        }
    }
}
</script>
<style scoped>
.addPersonBtn{
    position: absolute;
    z-index: 999;
    right: 20px;
    top: 2vh;
}
</style>


