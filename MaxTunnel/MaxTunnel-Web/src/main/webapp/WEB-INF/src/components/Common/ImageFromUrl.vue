<template>
    <div class="ImageFromUrl">
        <img style="max-width:100%; max-height:100%;" v-bind:src=imgSrc>
    </div>                
</template>

<script>
import axios from "axios";

export default {
  name: "ImageFromUrl",
  props: {
    url: {
      default: ""
    }
  },
  data() {
    return {
      imgSrc: ""
    };
  },
  watch: {
    url: function(val) {
      this.loadImage(val);
    }
  },
  methods: {
    loadImage(url) {
      axios
        .get(url, {
          responseType: "arraybuffer"
        })
        .then(response => {
          return (
            "data:image/png;base64," +
            btoa(
              new Uint8Array(response.data).reduce(
                (data, byte) => data + String.fromCharCode(byte),
                ""
              )
            )
          );
        })
        .then(data => {
          this.imgSrc = data;
        });
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

