<template>
    <Tabs value="通风预案">
        <TabPane v-for="(item, index) in treeData" :label="item.name" :name="item.name" :key="item.name">
            <treeChart :data="item" :id='"treeId"+index'></treeChart>
        </TabPane>
    </Tabs>
    <!-- <div>
        <treeChart v-bind="treeChartData"></treeChart>
    </div> -->
</template>
<script>
import treeChart from '../../../../components/Common/Chart/TreeChart'
import { PlanPersonService } from "@/services/planPersonal"
export default {
    components: {
        treeChart
	},
    data(){
        return{
            // treeChartData: {
                
            //     data: {
            //         name: '通风预案',
            //         level: 1,
            //         children: [
            //             { name: '安全小组1-张三-1564112222', level: 2-1, children: [
            //                 { name: '组员-李四-156442222',  value: 3589 },
            //                 { name: 'graph', value: 8833 }
            //             ] },
            //             { name: "display", level: 2-1, children: [
            //                 {name: "DirtySprite", value: 8833, level: 2-1-1 },
            //                 {name: "LineSprite", value: 1732, level: 2-1-2 },
            //                 {name: "RectSprite", value: 3623, level: 2-1-3 },
            //                 {name: "TextSprite", value: 10066, level: 2-1-4 }
            //             ] }
            //         ]
            //     },
            // },
            id: 'treeChart',
            treeData: []
        }
    },
    mounted(){
        PlanPersonService.getEchartsData().then(
            result => {
                this.treeData = result
            },
            error => {
                this.Log.info(error)
            }
            
        )
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


