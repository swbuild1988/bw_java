import { Message } from 'iview';
var H5StreamPlugIn = {
    preName: 'h5Stream',

    getVideoElement(index) {
        let domId = this.preName + index;
        let videoDom = document.getElementById(domId);

        if (videoDom) {

            // 将video从原来的父dom中移除
            let parentDom = videoDom.parentNode;
            parentDom.removeChild(videoDom);

            // 将video变为黑色，初始状态
            videoDom.load();
            videoDom.style.display = "block";

            return videoDom;

        } else {

            videoDom = document.createElement("VIDEO");
            videoDom.setAttribute('id', domId);
            videoDom.classList.add('videos')
            videoDom.autoplay = true;
            videoDom.webkitPlaysinline = true;
            videoDom.playsinline = true;
            videoDom.style.display = "block";
            videoDom.style.width = "100%";
            videoDom.style.height = "100%";
            videoDom.style.border = "1px solid black";
            videoDom.style.backgroundColor = "#000000";
            videoDom.style.objectFit = "fill";
            videoDom.style.position = "absolute";
            videoDom.style.left = 0;
            videoDom.style.top = 0;
            videoDom.style.zIndex = 1;
           
            return videoDom;

        }
    },

    clearVideoElement(videoDom) {
        // console.log("移除video Dom ", videoDom);

        // 将video从原来的父dom中移除
        let parentDom = videoDom.parentNode;
        parentDom.removeChild(videoDom);

        // 将dom存到body中，并且不显示
        videoDom.style.display = "none";
        document.body.appendChild(videoDom);

    },
    fullScreen(id){
        let requestFullscreen =
            document.body.requestFullscreen ||
            document.body.webkitRequestFullscreen ||
            document.body.mozRequestFullScreen ||
            document.body.msRequestFullscreen;
        let fullscreenEnabled =
            document.fullscreenEnabled ||
            document.mozFullScreenEnabled ||
            document.webkitFullscreenEnabled ||
            document.msFullscreenEnabled;
        if(!!(requestFullscreen && fullscreenEnabled)){
            let element = document.getElementById(id)
            if (element.requestFullscreen) {
                element.requestFullscreen();
            } else if (element.mozRequestFullScreen) {
                element.mozRequestFullScreen();
            } else if (element.webkitRequestFullscreen) {
                element.webkitRequestFullscreen();
            } else if (element.msRequestFullscreen) {
                element.msRequestFullscreen();
            }
        }else{
            Message.error({
                content: "此浏览器不支持或未开启全屏模式",
                duration: 5
            });
        }

    }
};

export {
    H5StreamPlugIn
};