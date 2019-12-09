<template>
    <div>
        <ElectricityData v-bind:Obj="Obj" v-if="Obj.objtypeId == 30"></ElectricityData>
        <simulated-data v-else-if="Obj.datatypeId == 1" v-bind:Obj="Obj" v-on="$listeners"></simulated-data>
        <switch-data v-else-if="Obj.datatypeId == 2" v-bind:propObj="Obj" :control="Obj.control" v-on="$listeners">
        </switch-data>
        <analog-channel v-bind:propObj="Obj" v-else :control="Obj.control" :reset="Obj.reset" v-on="$listeners">
        </analog-channel>
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
        mounted() {}
    };
</script>
<style scoped>
</style>