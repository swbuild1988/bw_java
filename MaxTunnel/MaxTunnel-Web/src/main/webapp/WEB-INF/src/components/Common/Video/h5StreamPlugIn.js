var H5StreamPlugIn = {
    preName: 'h5Stream',

    getVideoElement(index) {
        let domId = this.preName + index;
        let videoDom = document.getElementById(domId);

        if (videoDom) {

            console.log("获取video Dom " + index, videoDom);
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
            videoDom.autoplay = true;
            videoDom.webkitPlaysinline = true;
            videoDom.playsinline = true;
            // videoDom.addEventListener('dblclick', 'fullScreen', false);
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

            console.log("新建video Dom " + index, videoDom);
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

    }
};

export {
    H5StreamPlugIn
};