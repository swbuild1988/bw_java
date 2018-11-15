<template>
    <ModulePage v-bind="pipeMonitorModule"></ModulePage>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
import { TunnelService } from '../../../../services/tunnelService'

export default {
    name: "PipeMain",
    data(){
        return{
            pipeMonitorModule: {
                moduleName: "管线本体监控",
                leftTree: [],
                selected:[0,-1]
            },
            treeNodeJumpUrl: "/UM/PipelineSupervise"
        }
    },
    components:{
        ModulePage
    },
    created(){
        this.pipeMonitorModule.leftTree=[];
        let _this = this
        TunnelService.getTunnels().then(
            (result)=>{
                result.forEach(a => {
                    let temp = {};
                    temp.id = a.id;
                    temp.name = a.name;
                    temp.url = _this.treeNodeJumpUrl + a.id;
                    // 给目录添加二级菜单
                    var child =[{ id:1, name: '电缆', url: '/UM/PipelineSupervise/electrivity'},
                                { id:2, name: '光缆', url: '/UM/PipelineSupervise/light'},
                                { id:3, name: '水管', url: '/UM/PipelineSupervise/water'},
                                ];
                                temp.childNode = child;
                    _this.pipeMonitorModule.leftTree.push(temp);
                })
            },
            (error)=>{
                console.log(error)
            })
    },
    mounted(){
    },
    methods:{
    //     goToMoudle(path) {
    //     this.$router.push(path);
    //   }
    }
};
</script>

<style scoped>

</style>
