<template>
    <div class="Main">
        <div class="Title">
            <module-title :title="title"></module-title>
        </div>
    </div>
</template>

<script>
import ModuleTitle from "../../../components/VM2/ModuleTitle";

export default {
    data() {
        return {
            title: "巡检"
        };
    },
    components: {
        ModuleTitle
    },
    mounted() { },
    methods: {}
};
</script>

<style scoped>
.Main {
    width: 100%;
    height: 100%;
    background: url("../../../assets/VM/module_bg.png") no-repeat;
    background-size: 100% 100%;
}
.Main .Title {
    width: 100%;
    height: 15%;
}
</style>
