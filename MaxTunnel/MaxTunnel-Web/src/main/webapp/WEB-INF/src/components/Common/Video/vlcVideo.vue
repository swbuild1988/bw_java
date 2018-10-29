<template>
  <!--<iframe id="dialog" style="position:relative; z-index:2;background: none; opacity: 0.1;">-->
	<div class="videoContent">
      <object name="wmode" value="transparent" z-index="1" type='application/x-vlc-plugin' pluginspage="http://www.videolan.org/" events='true' height="100%" width="100%" :id="id" >
        <param name='mrl' value="" />
        <param name='volume' value='50' />
        <param name='autoplay' value='true' />
        <param name='loop' value='false' />
        <param name='fullscreen' value='true' />
        <param name='controls' value='false' />
        <param name="wmode" value="transparent">
      </object>
	</div>
  <!--</iframe>-->
</template>

<script>
export default {
    name: "vlc",
    props: {
        video: {
            type: Object,
            required: true
        },
        id: {
            type: String,
            required: true
        }
    },
    data: function() {
        return {};
    },
    watch: {
        "video.url": function() {
            let isInstalled = this.isInstalled();
            if (isInstalled) {
                let vlc = document.getElementById(this.id);
                vlc.playlist.stop();
                vlc.playlist.clear();
                let itemId = vlc.playlist.add(this.video.url);
                vlc.playlist.playItem(itemId);
            } else {
                this.$Message.error("请先安装vlc");
            }
        }
    },
    methods: {
        isInstalled() {
            let userAgent = navigator.userAgent;
            let vlcInstalled = false;
            if (userAgent.indexOf("Firefox") > -1) {
                var numPlugins = navigator.plugins.length;
                for (let i = 0; i < numPlugins; i++) {
                    plugin = navigator.plugins[i];
                    if (
                        plugin.name.indexOf("VideoLAN") > -1 ||
                        plugin.name.indexOf("VLC") > -1
                    ) {
                        return (vlcInstalled = true);
                    } else {
                        return (vlcInstalled = false);
                    }
                }
            } else {
                if (
                    userAgent.indexOf("Trident") > -1 &&
                    userAgent.indexOf("rv:11.0") > -1
                ) {
                    let vlcObj = null;

                    try {
                        vlcObj = new ActiveXObject("VideoLAN.Vlcplugin.2");
                        if (vlcObj != null) {
                            return (vlcInstalled = true);
                        }
                    } catch (e) {
                        return (vlcInstalled = false);
                    }
                } else {
                    if (userAgent.indexOf("Chrome") > -1) {
                        return (vlcInstalled = true);
                    }
                }
            }
        }
    },
    mounted() {
        let isInstalled = this.isInstalled();
        // isInstalled=true;
        if (isInstalled) {
            let vlc = document.getElementById(this.id);
            if (this.video.url && this.video.url.length > 0) {
                let itemId = vlc.playlist.add(this.video.url);
                vlc.playlist.playItem(itemId);
            }
        } else {
            this.$Message.error("请先安装vlc");
        }
    }
};
</script>

<style scoped>
.videoContent {
    position: relative;
    width: 100%;
    height: 100%;
    z-index: 101;
}
</style>

