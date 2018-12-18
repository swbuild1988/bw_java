<template>
    <div class="moveControlPanel">
        <div class="ControlPanelTitleCommon  ControlPanelTitleDiff">{{ Title }}</div>  
        <div class="ControlPanelContent">
            <Button ghost class="flyMessageBtn" @click="startFly">{{ flyTextContent.startFly }}</Button>
            <Button ghost class="flyMessageBtn" @click="endFly">{{ flyTextContent.endFly }}</Button>
            <!-- <Button ghost class="flyMessageBtn">{{ flyTextContent.endFly }}</Button> -->
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return {
                Title:'控制面板',
                flyTextContent:{
                    startFly:'开始飞行',
                    stopFly:'暂停飞行',
                    endFly:'结束飞行',
                }
            }
        },
        mounted(){
        },
        methods:{
            startFly(){
                this.$parent.playFly()
            },
            endFly(){
                this.$parent.stopFly()
            }
            
        },
        beforeDestroy(){
        }
    }
</script>

<style scoped>
    .moveControlPanel{
        position: absolute;
        top: 15.5%;
        left: 2%;
        z-index: 1000;
        color: white;
        text-align: center;
        height: 16%;
        width: 20%;
        background: url(../../../assets/VM/alarmContent.png) no-repeat;
        background-size: contain;
        background-position: center;
    }
    .ControlPanelTitleCommon{
        text-align: center;
        color: rgba(0, 255, 255, 0.8);
        font-weight: bold;  
    }
    .ControlPanelContent{
            width: 100%;
            height: 50%;
        }
    .ControlPanelContent >>> .flyMessageBtn {
        width: 40%;
        height: 50%;
        background: #031324;
        color: #fff;
        padding: 0;
        border-color: rgba(0, 107, 149, 1);
    }
    /* 小屏幕（显示器，小于等于 1920px） */
    @media (max-width: 1920px) {
        .ControlPanelTitleDiff{
            font-size: 1rem;
            margin: .6rem 0 0.2rem;    
        }
        .ControlPanelContent >>> .flyMessageBtn {
            line-height: .5rem;
        }
        .ControlPanelContent >>> .flyMessageBtn > span {
            font-size: .5rem;
        }
        
    }
    /* 大屏幕（显示器，大于等于 1920px） */
    @media (min-width: 1921px) {
            
        .ControlPanelTitleDiff{
            font-size: 2rem;
            margin: 2.5rem 0 0.3rem;   
        }
        .ControlPanelContent >>> .flyMessageBtn > span {
            font-size: 1.5rem;
            line-height: 1.5rem;
        }
    }

</style>