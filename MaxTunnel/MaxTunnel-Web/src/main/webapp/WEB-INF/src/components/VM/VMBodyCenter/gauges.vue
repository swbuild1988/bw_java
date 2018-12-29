<template>
    <div class="gasState" :style="{ top }">
        <div class="mapgaugeLast">
            <map-ganue :style="{height:'100%'}" v-bind="gauge_CO">
            </map-ganue>
        </div>
        <div class="mapgaugeLast">
            <map-ganue :style="{height:'100%'}" v-bind="gauge_H2S">
            </map-ganue>
        </div>
        <div class="mapgaugeLast">
            <map-ganue :style="{height:'100%'}" v-bind="gauge_CH4">
            </map-ganue>
        </div>
        <div class="mapgaugeLast">
            <map-ganue :style="{height:'100%'}" v-bind="gauge_O2">
            </map-ganue>
        </div>
        <div class="mapgaugeLast">
            <map-ganue :style="{height:'100%'}" v-bind="gauge_Temp">
            </map-ganue>
        </div>
        <div class="mapgaugeLast">
            <map-ganue :style="{height:'100%'}" v-bind="gauge_West">
            </map-ganue>
        </div>
    </div>
</template>

<style scoped>
    .gasState{
        position: absolute;
        height: 80%;
        top: 15.5%;
        z-index: 1003;
        right: 2%;
        width: 8.5%;
    }
    .mapgaugeLast {
        width: 100%;
        height: 16%;
        position: relative;
        /*box-shadow: 0 0 1rem rgba(26,216,239,1) inset;*/
        background: url("../../../assets/VM/gauge_bg.png") no-repeat;
        background-size: 100% 100%;
    }
</style>

<script>
    import MapGanue from '../../Common/Chart/gauge'
    import config from '../../../../static/VM/js/VMGlobalConfig'

    export default {
        name:'gauges',
        data() {
            return {
                gauge_CO: {
                    gaugeId: 'gaugeCO',
                    requestUrl: 'tunnels/items/energies',
                    titleName: '一氧化碳浓度',
                    startColor: '#67d1ff',
                    endColor: '#2eff31',
                    startDynamicStyle:true,
                },
                gauge_H2S: {
                    gaugeId: 'gaugeH2S',
                    requestUrl: 'tunnels/items/energies',
                    titleName: '硫化氢浓度',
                    startColor: '#edff27',
                    endColor: '#ff9019',
                    startDynamicStyle:true,
                },
                gauge_CH4: {
                    gaugeId: 'gaugeCH4',
                    requestUrl: 'tunnels/items/energies',
                    titleName: '甲烷浓度',
                    startColor: '#51ffe7',
                    endColor: '#bdbfff',
                    startDynamicStyle:true,
                },
                gauge_O2: {
                    gaugeId: 'gaugeO2',
                    requestUrl: 'tunnels/items/energies',
                    titleName: '氧气浓度',
                    startColor: '#67d7bc',
                    endColor: '#ff1317',
                    startDynamicStyle:true,
                },
                gauge_Temp: {
                    gaugeId: 'gaugeTemp',
                    requestUrl: 'tunnels/items/energies',
                    titleName: '温度',
                    startColor: '#DBF131',
                    endColor: '#3ec8ff',
                    startDynamicStyle:true,
                },
                gauge_West: {
                    gaugeId: 'gaugeWest',
                    requestUrl: 'tunnels/items/energies',
                    titleName: '湿度',
                    startColor: '#2bff7b',
                    endColor: '#0909ff',
                    startDynamicStyle:true,
                },
                top: '15.5%'
            }
        },
        components: {
            MapGanue,
        },
        mounted(){
            this.init();
        },
        methods:{
            init(){
                let { networkAnomaly }=config;

                this.top = !networkAnomaly ? '15.5%' : '2%';
            }
        }
    }

</script>
    