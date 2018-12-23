<template>
    <div v-show="show.state" transition="modal" class="des-model">
        <div class="des-model-content">
            <div class="des-modal-container"
                 ref="content"
                 :style="contentStyles">
                <div class="modal-header"
                     :style="{ backgroundColor : tilteProps.backgroundColor }"
                     @mousedown="handleMoveStart">
                    <slot name="header">  <div class="model-header-inner" slot="header" >{{tilteProps.tilte}}</div>
                        <a class="ivu-modal-close">
                            <i class="ivu-icon ivu-icon-ios-close-empty" @click="show.state =! show.state" ></i> </a>
                    </slot>
                </div>
                <div class="modal-body">
                    <slot name="body" >
                        <Input v-model="describeValue" type="textarea" :rows="3" placeholder="清除描述..." />
                    </slot>
                </div>
                <div class="modal-footer" >
                    <slot name="footer">
                        <Button type="primary" size="small" @click.native="clearAlarm" style="float: right;margin-right: 5%;margin-bottom: 1%;background:#199FB0 ">确认清除</Button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import BaseData from '../../Common/Modal/mixins/modalBaseData'
    import dragMove from  './dragMove'
    import Vue from 'vue'

    export default {
        name: "alarmDataModel",
        mixins:[ BaseData,dragMove ],
        props:{
            draggable: {
                type: Boolean,
                default: true
            },
        },
        data(){
            return {
                describeValue:''
            }
        },
        computed:{
            entity(){
                return this.$store.state.VMstate.alarm.alarmEntity;
            },
            alarmObject(){
                return this.$store.state.VMstate.alarm.alarmObject;
            }
        },
        mounted(){
        },
        methods:{
            clearAlarm(){
                this.show.state =! this.show.state;
                this.$emit('removeByEntityId',this.entity); //通知移除实体
                Vue.prototype.IM.deleteInformation(this.alarmObject,'alarm','id'); //删除缓存对象
                this.axios.get(`alarms/${ this.entity._moId }/description/${this.describeValue}`)
                    .then()
            }
        }
    }
</script>

<style scoped>
    .des-model{
        z-index: 1006;
        position: absolute;
        left: 50%;
        top: 20%;
        transform: translateX(-50%);
    }
    .des-modal-container {
        border-radius: 6px;
        width: 300px;
        margin: 0px auto;
        transition: all .3s ease;
        font-family: Helvetica, Arial, sans-serif;
        overflow: hidden;
        border: 1px solid rgba(0,180,220,0.5);
        background: linear-gradient(180deg,rgba(0,180,220,0.3),rgba(0,180,220,0.1),rgba(0,180,220,0.1),rgba(0,180,220,0.3));
        box-shadow: 0 0 2rem rgba(0,180,220,.1) inset


    }
    .modal-header{
        padding: 7px 8px;
        border-bottom: 1px solid #aaa;
        line-height: 1px;
        cursor: move;
    }
    .modal-header .model-header-inner {
        display: inline-block;
        width: 100%;
        font-size: 14px;
        line-height: 20px;
        color: #fff;
        font-weight: 700;
        overflow: hidden;
        white-space: nowrap;
        /*#199FB0*/
    }
    .modal-header .ivu-modal-close{
        top:0;
        right:8px;
        color: #17233d;
        transition: color .2s ease;
    }
    .modal-body {
        padding: 16px;
        line-height: 1.5;
        overflow: hidden;
        transition: all .3s ease;
        color: #17233d;

    }
    .modal-enter, .modal-leave {
        opacity: 0;
    }
    .modal-enter .modal-container, .modal-leave .modal-container {
        -webkit-transform: scale(1.1);
        transform: scale(1.1);
    }
    .icon-chevron{
        font-size: 20px;
    }

</style>