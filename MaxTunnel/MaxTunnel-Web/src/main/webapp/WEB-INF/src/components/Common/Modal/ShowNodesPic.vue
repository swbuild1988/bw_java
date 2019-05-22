<template>
    <div>
        <Modal
            :styles="{top: '30%'}"
            width="30vw;"
            style="z-index: 1001; "
            v-model="nodeModal.showFlag"
            title="执行日志"
        >
            <div>
                <image-from-url :url="imgUrl"></image-from-url>
            </div>
            <div slot="footer">
                <Button type="parimay" @click="confirmPlan()">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import ImageFromUrl from "../ImageFromUrl";

export default {
    name: "show-start-plan",
    props: {
        showFlag: {
            required: true,
            type: Boolean
        },
        imgUrl: {
            required: true,
            type: String
        }
    },
    data() {
        return {};
    },
    computed: {},
    methods: {
        confirmPlan() {
            this.$emit("confirmPlan", false);
        }
    },
    watch: {},
    mounted() {}
};
</script>

<style scoped>
</style>
