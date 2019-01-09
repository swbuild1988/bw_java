<template>
    <div class="Top">
        <img
            :src="logo"
            class="Logo"
        >
        <span>{{title}}</span>
    </div>
</template>

<script>
import { VM_TILTE } from "../../../../static/VM/js/VMGlobalConfig";

export default {
    name: "title",
    data() {
        return {
            title: "",
            logo: ""
        };
    },
    mounted() {
        //logo image init
        this.logo = require("../../../assets/VM/Logo.png");
        //title init
        this.title = VM_TILTE;
    },
    methods: {}
};
</script>

<style scoped>
.Top {
    position: absolute;
    top: 15%;
    width: 100%;
    height: 70%;
    text-align: center;
}
.Top .Logo {
    height: 100%;
}
.Top > span {
    vertical-align: top;
    font-size: 5vmin;
    color: #fff;
    text-shadow: 0 0 6px #1765b3;
}
</style>
