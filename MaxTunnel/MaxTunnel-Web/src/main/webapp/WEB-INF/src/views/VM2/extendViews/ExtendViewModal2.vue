<template>
    <div class="extendViewModel2">
        <div class="VmModel11 BackgroundImage">
            <vm-data-model v-if="ModelData11" :modelData="ModelData11"></vm-data-model>
        </div>
        <div class="VmModel12 BackgroundImage">
            <vm-data-model v-if="ModelData12" :modelData="ModelData12"></vm-data-model>
        </div>
        <div class="VmModel13 BackgroundImage">
            <vm-data-model v-if="ModelData13" :modelData="ModelData13"></vm-data-model>
        </div>
    </div>
</template>

<script>

import VmDataModel from "../../../components/VM/VMDataModel";
import { baseView } from '../mixins/BaseView'

export default {
    name:'extendViewModel2',
    mixins:[ baseView ],
    data() {
        return {
            ModelData11: null,
            ModelData12: null,
            ModelData13: null,
        };
    },
    components: {
        VmDataModel
    },
    mounted() {
        this.init();
    },
    methods:{
        init(){
            this.initModelData11();
            this.initModelData12();
            this.initModelData13();
        },
        initModelData11(){
            this.ModelData11 = this.getModelData("vmModel11");
        },
        initModelData12(){
            this.ModelData12 = this.getModelData("vmModel12");
        },
        initModelData13(){
            this.ModelData13 = this.getModelData("vmModel13");
        },
    }
}
</script>

<style scoped>
    .extendViewModel2 {
        width: 100%;
        height: 100%;
    }
    .extendViewModel2 .VmModel11,
    .extendViewModel2 .VmModel12,
    .extendViewModel2 .VmModel13{
        position: relative;
        width: 100%;
        height: 33%;
        margin-bottom: 1%;
    }
    .BackgroundImage {
        padding: 1.3%;
        border-radius: 0.5rem;
        border: 1px solid rgba(0, 107, 149, 0.5);
        box-shadow: 0 0 1.5rem rgba(0, 107, 149, 1) inset;
    }
</style>



