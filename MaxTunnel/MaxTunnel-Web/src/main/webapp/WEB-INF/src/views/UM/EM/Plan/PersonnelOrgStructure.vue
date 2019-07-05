<template>
    <Tabs value="通风预案" class="boxBG">
        <TabPane v-for="(item, index) in treeData" :label="item.name" :name="item.name" :key="item.name" style="color: #fff;">
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
.boxBG{
    background: url("../../../../assets/UM/infoBox.png") no-repeat;
    background-size: 100% 100%;
    padding: 1%;
    height: 84vh;
}
.boxBG.ivu-tabs >>> .ivu-tabs-tab{
    color: #fff;
}
.boxBG.ivu-tabs >>> .ivu-tabs-tab:hover {
    color: #57a3f3;
}
.addPersonBtn{
    position: absolute;
    z-index: 999;
    right: 20px;
    top: 2vh;
}
</style>


