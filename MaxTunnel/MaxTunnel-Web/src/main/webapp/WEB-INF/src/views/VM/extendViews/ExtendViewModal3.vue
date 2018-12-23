<template>
    <div class="extendViewModel3">
        <div class="VmModel14 BackgroundImage">
            <vm-data-model v-if="ModelData14" :modelData="ModelData14"></vm-data-model>
        </div>
        <div class="VmModel15 BackgroundImage">
            <vm-data-model v-if="ModelData15" :modelData="ModelData15"></vm-data-model>
        </div>
        <div class="VmModel16 BackgroundImage">
            <vm-data-model v-if="ModelData16" :modelData="ModelData16"></vm-data-model>
        </div>
    </div>
</template>

<script>

import VmDataModel from "../../../components/VM/VMDataModel";
import { baseView } from '../mixins/BaseView'

export default {
    name:'extendViewModel3',
    mixins:[ baseView ],
    data() {
        return {
            ModelData14: null,
            ModelData15: null,
            ModelData16: null,
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
            this.initModelData14();
            this.initModelData15();
            this.initModelData16();
        },
        initModelData14(){
            this.ModelData14 = this.getModelData("vmModel14");
        },
        initModelData15(){
            this.ModelData15 = this.getModelData("vmModel15");
        },
        initModelData16(){
            this.ModelData16 = this.getModelData("vmModel16");
        },
    }
}
</script>

<style scoped>
    .extendViewModel3 {
        width: 100%;
        height: 100%;
    }
    .extendViewModel3 .VmModel14,
    .extendViewModel3 .VmModel15,
    .extendViewModel3 .VmModel16{
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



