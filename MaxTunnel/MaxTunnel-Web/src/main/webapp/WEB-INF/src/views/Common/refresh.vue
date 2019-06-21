<template>
    <div></div>
</template>

<script>
export default {
    name: "refresh",
    data() {
        return {};
    },
    beforeRouteEnter(to, from, next) {
        next(vm => {
            sessionStorage.setItem("refreshAddress", from.path);
            vm.$router.replace(from.path);
        });
    }
};
</script>

