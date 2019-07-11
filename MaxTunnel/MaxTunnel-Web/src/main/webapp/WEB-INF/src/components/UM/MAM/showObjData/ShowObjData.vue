<template>
    <div>
        <simulated-data v-if="Obj.datatypeId==1" v-bind:Obj="Obj" @changeStatus="changeStatus"></simulated-data>
        <switch-data v-else-if="Obj.datatypeId==2" v-bind:propObj="Obj" @changeStatus="changeStatus"
            :control="Obj.control"></switch-data>
        <analog-channel v-bind:propObj="Obj" v-else :control="Obj.control" :reset="Obj.reset"
            @changeStatus="changeStatus"  v-on="$listeners"></analog-channel>
        <!-- <electrictiy-data v-bind:Obj="Obj"></electrictiy-data> -->
    </div>
</template>
<script>
    import SimulatedData from "./ShowSimulatedData";
    import SwitchData from "./SwitchTypeData";
    import ElectricityData from "./ShowElectricityData";
    import analogChannel from "./AnalogChannelTypeData";
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
            ElectricityData,
            analogChannel
        },
        methods: {
            changeStatus(id, ObjVal, datatypeId, clickStatus) {
                this.$emit("changeStatus", id, ObjVal, datatypeId, clickStatus);
            },
            // reset(id){
            //     this.$emit("reset", id);
            // }
        }
    }
</script>
<style scoped>
</style>