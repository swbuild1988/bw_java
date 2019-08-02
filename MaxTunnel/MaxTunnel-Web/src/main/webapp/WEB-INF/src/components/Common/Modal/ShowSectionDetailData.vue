<template>
    <div class="section-details-content" v-show="showDetailsModel">
        <div class="panel panel-primary">
            <table class="table table-bordered table-striped text-center">
                <tbody>
                    <tr v-for="(item,index) in curPageData" :key="index">
                        <td>
                            <img class="titleImg" :src="item.img">
                        </td>
                        <td>{{item.description ? item.description + item.name : item.name}}</td>
                        <td>{{item.cv}}</td>
                        <td>{{item.unit}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import Vue from "vue";

export default {
    name: "sectionDetails",
    props: {
        dataDetails: {
            type: Array,
            default: []
        },
        pageSize: {
            default: 10
        }
    },
    data() {
        return {
            curPageData: [],
            curPage: 1,
            totalPage: 1,
            loopTimerId: null,
            loopTime: 2000,
            middleData: [],
            showDetailsModel: false
        };
    },
    computed: {
        titleImg() {
            return Vue.prototype.VMConfig.detectionObjTitleImg;
        }
    },
    watch: {
        dataDetails(val) {
            this.curPage = 1;
            val ? this.init() : this.clearTimer();
            this.showDetailsModel = val ? true : false;
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.transformData();
            this.curPageData = this.middleData.slice(0, this.pageSize);
            this.totalPage = Math.ceil(this.middleData.length / this.pageSize);
            this.refresh();
        },
        changePage() {
            let newPage =
                this.curPage === 1
                    ? this.totalPage
                    : this.curPage === this.totalPage
                    ? 1
                    : this.curPage + 1;
            let startIndex = (newPage - 1) * this.pageSize;
            this.curPageData = this.middleData.slice(
                startIndex,
                startIndex + this.pageSize
            );
            this.curPage = newPage;
        },
        refresh() {
            this.clearTimer();
            let _this = this;
            this.loopTimerId = setInterval(() => {
                _this.changePage();
            }, this.loopTime);
        },
        clearTimer() {
            clearInterval(this.loopTimerId);
        },
        transformData() {
            if (!this.dataDetails.length) return;
            this.middleData.splice(0); //清空数组
            let currData = null;

            for (let i = 0, data = this.dataDetails; i < data.length; i++) {
                switch (data[i].dataType) {
                    case 1:
                        currData = data[i];
                        this.addTitleImg(currData, data[i].objectType);
                        break;
                    case 2:
                    case 3:
                        currData = {
                            name: data[i].name,
                            cv: !data[i].cv
                                ? "关闭"
                                : data[i].cv == 1
                                ? "打开"
                                : "故障",
                            unit: null
                        };
                        this.addTitleImg(currData, data[i].objectType);
                        break;
                    case 7:
                        if (this.searchLastIndex(this.dataDetails, 7) !== i) {
                            break;
                        }
                        currData = {
                            name: data[i].name,
                            cv: this.searchDataTypeLenght(this.dataDetails, 7),
                            unit: "个"
                        };
                        this.addTitleImg(currData, data[i].objectType);
                        break;
                }
                // this.addTitleImg(currData,data[i].objectType);
            }
        },
        searchDataTypeLenght(array, dataType) {
            return array.filter(item => item.dataType === dataType).length;
        },
        searchLastIndex(arr, unit) {
            for (let i = arr.length - 1; i > -1; i--) {
                if (arr[i].dataType === unit) return i;
            }
        },
        addTitleImg(data, id) {
            let [titleImgObject] = this.titleImg.filter(
                item => item.key === id
            );
            let currImg = null;
            try {
                let imageName = !titleImgObject
                    ? "methane"
                    : titleImgObject.val;

                currImg = require(`../../../assets/UM/${imageName}.png`);
            } catch (err) {
                console.warn(err);
            }

            this.middleData.push(Object.assign(data, { img: currImg }));
        }
    },
    beforeDestroy() {
        this.clearTimer();
    }
};
</script>

<style>
.section-details-content {
    padding: 5vmin 2vmin 2vmin 3vmin;
    position: absolute;
    z-index: 900;
    font-size: 1.3vmin;
    /* bottom: 30%; */
    color: #fff;
    right: 1vmin;
    background: url("../../../assets/UM/sectionDataBg.png") no-repeat;
    background-size: 100% 100%;
    /* border-radius: 0.5rem;
    border: 1px solid rgba(0, 180, 220, 0.4);
    background: linear-gradient(
        180deg,
        rgba(0, 180, 220, 0.2),
        rgba(0, 180, 220, 0.1),
        rgba(0, 180, 220, 0.1),
        rgba(0, 180, 220, 0.2)
    );
    box-shadow: 0 0 2rem rgba(0, 180, 220, 0.1) inset; */
    width: 35vmin;
    min-height: 31.4vmin;
    top: 0vmin;
}
.panel,
.panel table {
    width: 100%;
}
.panel td {
    width: 33.3%;
    text-align: center;
    padding: 0.7vmin 0.3vmin 0;
}
.panel td:first-child {
    text-align: left;
    width: 0%;
}
.panel td:nth-of-type(2),
.panel td:nth-of-type(4) {
    text-align: left;
}
.panel td:nth-of-type(3) {
    text-align: right;
}
.page {
    position: absolute;
    bottom: 1vmin;
    right: 1vmin;
}
.titleImg {
    width: 1.5vmin;
    height: 1.5vmin;
}
</style>
