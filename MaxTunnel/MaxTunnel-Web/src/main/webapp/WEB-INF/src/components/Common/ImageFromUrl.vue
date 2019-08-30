<template>
    <div class="ImageFromUrl" v-if="imgShowFlag">
        <img style="width:100%; height:100%;" v-bind:src="imgSrc">
    </div>
</template>

<script>
import { get } from "@/utils/http";

export default {
    name: "ImageFromUrl",
    props: {
        url: {
            default: ""
        }
    },
    data() {
        return {
            imgSrc: "",
            imgShowFlag: false
        };
    },
    watch: {
        url: function(val) {
            this.loadImage(val);
        }
    },
    mounted() {
        this.loadImage(this.url);
    },
    methods: {
        loadImage(url) {
            return new Promise((resolve, reject) => {
                get(url, "arraybuffer").then(
                    response=>{
                        return (
                            "data:image/png;base64," +
                            btoa(
                                new Uint8Array(response.data).reduce(
                                    (data, byte) =>
                                        data + String.fromCharCode(byte),
                                    ""
                                )
                            )
                        );
                    }
                )
                .then(data => {
                    this.imgSrc = data;
                    this.imgShowFlag = true;
                });
            })
        }
    }
};
</script>


<style scoped>
.ImageFromUrl {
    width: 100%;
    height: 100%;
}
</style>

