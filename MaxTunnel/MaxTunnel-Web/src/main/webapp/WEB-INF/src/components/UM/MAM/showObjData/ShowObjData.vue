<template>
    <div>
        <simulated-data v-if="Obj.datatypeId==1" v-bind:Obj="Obj" @changeStatus="changeStatus"></simulated-data>
        <switch-data v-if="Obj.datatypeId==2" v-bind:Obj="Obj" @changeStatus="changeStatus"></switch-data>
        <electrictiy-data v-if="Obj.datatypeId==3" v-bind:Obj="Obj"></electrictiy-data>
    </div>
</template>
<script>
import SimulatedData from "../ShowSimulatedData";
import SwitchData from "../ShowSwitchData";
import ElectricityData from "../ShowElectricityData";
export default {
    name: "show-obj-data",
    props: {
        Obj: {
            required: true,
            type: Object
        }
    },
    components: {
        SimulatedData,
        SwitchData,
        ElectricityData
    },
    methods: {
        changeStatus(id, ObjVal, datatypeId, clickStatus) {
            this.$emit("changeStatus", id, ObjVal, datatypeId, clickStatus);
        }
    }
};
</script>
<style scoped>
</style>

