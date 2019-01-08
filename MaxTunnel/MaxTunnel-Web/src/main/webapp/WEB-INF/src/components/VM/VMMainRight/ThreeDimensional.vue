<template >
    <div class="ThreeDimensionalContainer">
        <sm-viewer
                id="threeDViewer"
                ref="smViewer"
                :camera="camera"
                :openFlyLoop="true"
                :infoBox="false"
                :navigation="false"
                :refreshCameraPosition="refreshCameraPosition"
                :openImageryProvider="false"
                @showStorePosition="showStorePosition"
        ></sm-viewer>
        <vm-title :title-text="title" @click.native="sendAlarms"></vm-title>
        <select class="cd-select" id="list-dropdown" ></select>
        <select class="cd-select" id="area-dropdown" ></select>
        <select class="cd-select" id="store-dropdown" ></select>
        <show-store-position v-bind:currPosition="storePosition" ></show-store-position>
    </div>
</template>

<style scoped>
    .ThreeDimensionalContainer{
        width: 100%;
        height: 100%;
    }
    .ThreeDimensionalContainer >>> .ivu-select{
        position: absolute;
        top: 5%;
        left: 2%;
        width: 18%;
    }
    .ThreeDimensionalContainer >>> .ivu-select-dropdown,.ThreeDimensionalContainer >>> .ivu-select-selection{
        background-color: transparent;
    }
    .ThreeDimensionalContainer  >>> .ivu-select-selection{
        border: 1px solid #001A27;
    }
    .ThreeDimensionalContainer  >>>  .ivu-select-selection{
        border-color: #001A27;
    }
    .ThreeDimensionalContainer  >>> .ivu-select-selection:hover{
        border-color: #001A27;
    }
    .ThreeDimensionalContainer >>> .ivu-select:focus,.ThreeDimensionalContainer >>> .ivu-select-selection{
        border-color: #001A27;
        box-shadow: 0 0 0 2px rgba(0,26,39,.2);
    }
    .ThreeDimensionalContainer  >>>  .ivu-select-item, .ThreeDimensionalContainer  >>>  .ivu-select{
        color: #FFF;
    }
    .ThreeDimensionalContainer .ivu-select-item-selected, .ThreeDimensionalContainer  .ivu-select-item-selected:hover,.ThreeDimensionalContainer  .ivu-select-item:hover{
        background: #001A27;
    }

</style>
<style>
    .cesium-viewer-bottom{
        display:none
    }
    .ThreeDimensionalContainer .Areas{
        left: 15.5%;
    }
    .ThreeDimensionalContainer .Stores{
        left: 29%;
    }
</style>

<script>
    import VmTitle from '../VMTitle'
    import SmViewer from '../../Common/3D/3DViewer'
    import Vue from 'vue'
    import showStorePosition from '../../Common/Modal/showStorePosition'
    import { TunnelService } from '../../../services/tunnelService'

    export default {
        data() {
            return {
                title:'三维系统',
                id:'threeDimensional',
                camera:{
                    longitude:112.49397907438006,
                    latitude:37.710661662983384,
                    height:-1.7297007316681086,
                    roll:2.5845992013273644e-12,
                    pitch:-0.30235107580130394,
                    heading:1.7164865602395531
                },
                labelsID:[],
                addLabels:[],
                optionList: [],//所有管廊
                areaList:[],//当前管廊下所有区域
                storeList:[],//当前区域下所有仓
                location:[],//缓存当前管廊下所有区域的位置
                defaultOption: '',
                defaultArea:'',
                showArea:false,
                defaultAreaVal:1,
                switchType:false,
                unitsPosition:{
                    openPosition:true,
                    isShow:true,
                },
                refreshCameraPosition:{
                    enable:true
                },
                storePosition:{
                    tunnelName:'',
                    areaName:'',
                    storeName:''
                }
            }
        },
        components:{
            VmTitle,
            SmViewer,
            showStorePosition
        },
        mounted(){
            this.init();
        },
        watch:{
            optionList(){
                let _this=this;
                _this.addOption(_this.optionList,'list-dropdown');

                $('#list-dropdown').dropdown( {
                    gutter : 5,
                    stack : false,
                    slidingIn : 100,
                    onOptionSelect(opt){
                        _this.changeStatus(opt.attr('data-value'));
                    }
                } )
            },
            areaList(){
                let _this=this;
                _this.addOption(_this.areaList,'area-dropdown');

                $('#area-dropdown').dropdown( {
                    defaultClass:['cd-dropdown','Areas'],
                    gutter : 5,
                    stack : false,
                    slidingIn : 100,
                    onOptionSelect(opt){
                        _this.changeArea(opt.attr('data-value'));
                    }
                } );
            },
            storeList(){
                let _this=this;
                _this.addOption(_this.storeList,'store-dropdown');

                $('#store-dropdown').dropdown( {
                    defaultClass:['cd-dropdown','Stores'],
                    gutter : 5,
                    stack : false,
                    slidingIn : 100,
                    onOptionSelect(opt){
                        _this.changeArea(opt.attr('data-value'))
                    }
                } );
            }
        },
        methods:{
            init(){
                this.fetchData();
                this.changeStatus(this.defaultAreaVal);
                this.changeStore(1020);
            },
            fetchData(){
                let _this=this;
                _this.axios.get('/tunnels')
                    .then(result=>{
                        let { code,data }=result.data;
                        if(code==200){
                            _this.optionList.splice(0)//清空管廊数组
                            data.forEach(tunnel=>_this.optionList.push({value:tunnel.id,label:tunnel.name}));
                        }
                    })
            },
            changeStatus(val){
                let _this=this;

                _this.axios.get(`tunnels/${val}/areas/`)
                    .then(result=>{
                        let { code,data }=result.data;
                        if(code==200){
                            _this.showArea = true;
                            _this.areaList.splice(0)//清空管廊数组

                            if(data){
                                data.reverse().forEach(area => {
                                    _this.areaList.push({value:area.id,label:area.name})
                                })
                            }
                        }

                    })
            },
            changeArea(storeId){
                let _this=this;
                
                let [ curCamera ] = _this.location.filter( position => position.id == storeId );
                let { camera } = curCamera;
            
                try{
                    let location = camera.split(',');

                    this.$refs.smViewer.flyToMyLocation({
                        position:{
                            longitude:location[0],
                            latitude:location[1],
                            height:location[2],
                            roll:location[3],
                            pitch:location[4],
                            heading:location[5]
                        }
                    });
                }catch (e){

                }

            },
            changeStore(areaId){
                let _this = this;

                TunnelService.getStoresByAreaId({ areaId })
                    .then( stores => {
                        _this.storeList.splice(0);
                        _this.location.splice(0);
                        
                        stores.forEach( store => { 
                            _this.storeList.push({ value: store.store.id ,label: store.store.name });
                            _this.location.push({ 
                                id: store.store.id ,
                                camera: store.camera
                            })
                        } )

                    } )
            },
            addOption(optionList,id='cd-dropdown'){
                var $dropdown =$('#'+id), optionList=optionList,optlist='';

                for(let i=0;i<optionList.length;i++){
                    optlist+=`<Option value="${optionList[i].value}" >${optionList[i].label}</Option>`
                }

                $dropdown.empty().append(optlist);
                $dropdown.children('option').eq(0).attr('selected','selected');//设置第一个为默认选中项

            },
            sendAlarms(){
                // console.log('sendAlarms')
                // this.$refs.smViewer.playFly();
                setTimeout(()=>{
                    this.axios.post('/alarms',
                        {
                            alarmDate:+new Date(),
                            alarmLevel:1,
                            tunnelId:1002,
                            objectId:7001,
                            latitude: "112.49069725638859",
                            longitude: "37.71331808517105",
                            description:'sad',
                            isDistribute:true
                        })
                        .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                },1000)
                // setTimeout(()=>{        
                //     this.axios.post('/alarms',
                //         {
                //             alarmDate:+new Date(),
                //             alarmLevel:1,
                //             tunnelId:1002,
                //             objectId:7002,
                //             latitude: "37.70718914626341",
                //             longitude: "112.4940169552599",
                //             description:'sad'
                //         })
                //         .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                // },1000)
                // setTimeout(()=>{
                //     this.axios.post('/alarms',
                //         {
                //             alarmDate:+new Date(),
                //             alarmLevel:1,
                //             tunnelId:1002,
                //             objectId:7003,
                //             latitude: "37.70614188197423",
                //             longitude: "112.4940660989837",
                //             description:'sad'
                //         })
                //         .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                // },1000)
                // setTimeout(()=>{
                //     this.axios.post('/alarms',
                //         {
                //             alarmDate:+new Date(),
                //             alarmLevel:1,
                //             tunnelId:1002,
                //             objectId:7004,
                //             latitude: "37.707189145668366",
                //             longitude: "112.49406611961243",
                //             description:'sad'
                //         })
                //         .then(err=>console.log(err)).catch(err=>console.log('err2',err))
                // },1000)
            },
            showStorePosition(position){

                this.storePosition = position;
                console.log('storePosition',this.storePosition)
            }


        }
    }

</script>
