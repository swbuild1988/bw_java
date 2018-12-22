<template>
    <div>
        <canvas></canvas>
    </div>

</template>

<script>
export default {
  mounted(){
    var script=document.createElement("script");
    script.setAttribute("src", this.ServerConfig+"/3DMap/js/LoginBG.js");
    document.getElementsByTagName("head")[0].appendChild(script);
  }
}
</script>
<style scoped>
canvas { width: 100%; height: 100%; position: absolute; top: 0px; background: #092170;}
</style>
