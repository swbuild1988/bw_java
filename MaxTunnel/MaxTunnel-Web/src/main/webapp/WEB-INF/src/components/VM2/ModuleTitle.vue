<template>
    <div class='ModuleTitle'>
        <img
            class="left"
            src='../../../src/assets/VM/vm_module_title_bg.png'
        >
        <p>{{title}}</p>
    </div>
</template>

<script>
export default {
    name: "ModuleTitle",
    props: {
        title: {
            type: String,
            require: true
        }
    }
};
</script>

<style scoped>
.ModuleTitle {
    /*flex 布局*/
    display: flex;
    /*实现垂直居中*/
    align-items: center;
    /*实现水平居中*/
    justify-content: left;

    width: 100%;
    height: 100%;
}
.ModuleTitle > img {
    height: 1.5vmin;
}
.ModuleTitle .left {
    margin-left: 1.5vw;
}
.ModuleTitle .right {
    margin-left: 0.5vw;
}
.ModuleTitle > p {
    margin-left: 0.5vw;
    font-size: 2vmin;
    color: #00f6fc;
}
</style>
