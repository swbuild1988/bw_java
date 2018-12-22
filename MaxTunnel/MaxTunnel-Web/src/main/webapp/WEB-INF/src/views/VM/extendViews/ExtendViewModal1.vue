<template>
    <div class="extendViewModel1">
        <div class="VmModel8 BackgroundImage">
            <vm-data-model v-if="ModelData8" :modelData="ModelData8"></vm-data-model>
        </div>
        <div class="VmModel9 BackgroundImage">
            <vm-data-model v-if="ModelData9" :modelData="ModelData9"></vm-data-model>
        </div>
        <div class="VmModel10 BackgroundImage">
            <vm-data-model v-if="ModelData10" :modelData="ModelData10"></vm-data-model>
        </div>
    </div>
</template>

<script>

import VmDataModel from "../../../components/VM/VMDataModel";
import { baseView } from '../mixins/BaseView'

export default {
    name:'extendViewModel1',
    mixins:[ baseView ],
    data() {
        return {
            ModelData8: null,
            ModelData9: null,
            ModelData10: null,
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
            this.initModelData8();
            this.initModelData9();
            this.initModelData10();
        },
        initModelData8(){
            this.ModelData8 = this.getModelData("vmModel8");
        },
        initModelData9(){
            this.ModelData9 = this.getModelData("vmModel9");
        },
        initModelData10(){
            this.ModelData10 = this.getModelData("vmModel10");
        },
    }
}
</script>

<style scoped>
    .extendViewModel1 {
        width: 100%;
        height: 100%;
    }
    .extendViewModel1 .VmModel8,
    .extendViewModel1 .VmModel9,
    .extendViewModel1 .VmModel10{
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



