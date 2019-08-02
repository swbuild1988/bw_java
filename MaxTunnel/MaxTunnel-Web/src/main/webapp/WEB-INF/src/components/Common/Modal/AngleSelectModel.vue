<template>
    <div class="angleLists" style="color: #fff;">
        <ul style="list-style:none;position: absolute;left: 21.5vmin;">
            <li
                v-for="item in angleLists"
                @click="switchAngle(item.id)"
                :class="{ 'activeAngle' : curAngleId == item.id }"
                :key="item.id"
            >{{item.name}}</li>
        </ul>
    </div>
</template>

<script>
export default {
    data() {
        return {
            curAngleId: 1
        };
    },
    computed: {
        angleLists() {
            return this.MapAngleLists;
        }
    },
    methods: {
        switchAngle(id) {
            let [curAngle] = this.angleLists.filter(angle => angle.id == id);
            this.curAngleId = curAngle.id;
        }
    }
};
</script>

<style scoped>
.angleLists ul > li {
    float: left;
    border-right: 1px solid #2dbad3;
    background: rgba(3, 37, 86, 0.7);
    padding: 0.1vmin 0.4vmin;
    font-size: 0.7vmin;
    cursor: pointer;
}
.angleLists ul > li:first-child {
    border-radius: 0.5vmin 0 0 0.5vmin;
}
.activeAngle {
    color: #063570;
    background: rgba(255, 255, 255, 0.7) !important;
}
.angleLists ul > li:last-child {
    border-radius: 0 0.5vmin 0.5vmin 0;
    border-right: none;
}
</style>
