<template>
    <div>
        <div class="environment" v-for="item in tunnelProps">
            <i-circle :percent="item.percent" :size="circleSize">
                <p>{{item.key}}</p></br>
                <p style="font-size:24px">{{item.val}}</p></br>
                <p>{{item.location}}</p>
            </i-circle>
        </div>
    </div>
</template>

<script>
export default {
    name: "environmentShow",
    props: {
        tunnelProps:{
            type: Array,
            required: true
        },
        circleSize:{
            default: 120
        }
    }
}
</script>

<style scoped>
.environment{
    float: left;
    width: 50%;
}
</style>



