<template>
    <div :class="warpClasses">
        <div class="ControlPanelTitleCommon  ControlPanelTitleDiff">{{ Title }}</div>  
        <div class="ControlPanelContent">
            <span class="vm-btn" @click="startFly">{{ flyTextContent.startFly }}</span>
            <span class="vm-btn" @click="endFly">{{ flyTextContent.endFly }}</span>
        </div>
    </div>
</template>

<script>
    export default {
        props:{
            prefixCls:{
                type: String,
                default: 'LLPanel'
            },
        },
        computed:{
            warpClasses(){
                return `${ this.prefixCls }  moveControlPanel`
            }
        },
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
        /*left: 2%;*/
        z-index: 1000;
        color: white;
        text-align: center;
        height: 13%;
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
    .vm-btn {
        width: 36%;
        height: 75%;
        border-radius: 36px;
        color: #fff;
        background-color: #031324;
        padding: .3rem 0 0 0;
        font-size: .8rem;
        display: inline-block;
        text-align: center;
        cursor: pointer;
    }
    .vm-btn:hover {
        color: #fff5e6;

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
            font-size: 2.5rem;
            margin: 2.5rem 0 0.3rem;   
        }
        .ControlPanelContent >>> .flyMessageBtn > span {
            font-size: 1.6rem;
            line-height: 1.6rem;
        }
    }

</style>