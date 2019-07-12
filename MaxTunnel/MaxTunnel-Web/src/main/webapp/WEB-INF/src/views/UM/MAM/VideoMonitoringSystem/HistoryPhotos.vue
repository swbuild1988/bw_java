<template>
    <div class="allDiv">
        <div class="conditions">
            <Row>
                <Col span="11">
                    <Row style="width: 94%">
                        <Col span="2">
                            <div
                                style="width:100%;height:6vh;display: flex;justify-content: center;"
                            >
                                <span class="conditionSpan" style="align-self:center;">摄像头：</span>
                            </div>
                        </Col>
                        <Col span="5">
                            <Select v-model="conditions.tunnelId" @on-change="getStoreAndAreas">
                                <Option value="null">所有</Option>
                                <Option
                                    v-for="tunnel in conditionList.tunnels"
                                    :key="tunnel.id"
                                    :value="tunnel.id"
                                >{{tunnel.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="6">
                            <Select v-model="conditions.areaId" @on-change="getCameraList">
                                <Option value="null">所有</Option>
                                <Option
                                    v-for="area in conditionList.areas"
                                    :key="area.id"
                                    :value="area.id"
                                >{{area.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="5">
                            <Select v-model="conditions.storeId" @on-change="getCameraList">
                                <Option value="null">所有</Option>
                                <Option
                                    v-for="store in conditionList.stores"
                                    :key="store.id"
                                    :value="store.id"
                                >{{store.name}}</Option>
                            </Select>
                        </Col>
                        <Col span="6">
                            <Select v-model="conditions.cameraId">
                                <Option value="null">所有</Option>
                                <Option
                                    v-for="camera in conditionList.cameras"
                                    :key="camera.id"
                                    :value="camera.id"
                                >{{camera.name + ' '+ camera.id}}</Option>>
                            </Select>
                        </Col>
                    </Row>
                </Col>
                <Col span="5">
                    <span class="conditionSpan">开始时间：</span>
                    <DatePicker
                        v-model="conditions.startTime"
                        type="datetime"
                        class="condition"
                        placeholder="请选择开始时间"
                    ></DatePicker>
                </Col>
                <Col span="5">
                    <span class="conditionSpan">结束时间：</span>
                    <DatePicker
                        v-model="conditions.endTime"
                        type="datetime"
                        class="condition"
                        placeholder="请选择结束时间"
                    ></DatePicker>
                </Col>
                <Col span="3">
                    <Button type="primary" @click="resetPageAndSearch">查询</Button>
                    <Button type="error" @click="downLoadPhotoes" class="download">下载</Button>
                    <Button type="ghost" @click="back" class="back">返回</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <div style="padding-bottom:0.6vmin;margin:0.6vmin 2vmin;font-size: 1.6vmin;color:#fff;">
                <Checkbox :value="downLoad.checkPageAll" @click.prevent.native="handleCheckPageAll">
                    <span>本页全选</span>
                </Checkbox>
                <Checkbox :value="downLoad.checkAll" @click.prevent.native="handleCheckAll">
                    <span>所有全选</span>
                </Checkbox>
                <span>{{ '已选中' + downLoad.total + '个' }}</span>
            </div>
            <Row>
                <Col span="24" v-if="nodata" class="none">暂无历史照片</Col>
                <Col span="6" v-for="photo in photos" :key="photo.index">
                    <Card class="card">
                        <div style="text-align:center" @click="stateChange(photo)">
                            <img :src="photo.imgPath" class="photo">
                            <p>{{photo.tunnel + ' ' + photo.area + ' ' + photo.store}}</p>
                            <p>{{photo.name}}</p>
                            <p>{{photo.time}}</p>
                            <Checkbox :value="photo.checked" class="checkbox"></Checkbox>
                        </div>
                    </Card>
                </Col>
            </Row>
        </div>
        <!-- <div class="page"> -->
        <Page
            :total="page.pageTotal"
            :current="page.pageNum"
            :page-size="page.pageSize"
            show-sizer
            :page-size-opts="[8,16]"
            show-total
            placement="top"
            @on-change="handlePage"
            @on-page-size-change="handlePageSize"
            show-elevator
            :style="pageStyle"
        ></Page>
        <!-- </div> -->
    </div>
</template>

<script>
import { VideoService } from "../../../../services/videoService";
import { TunnelService } from "../../../../services/tunnelService";
import { saveAs } from "file-saver";

export default {
    name: "historyPhotos",
    data() {
        return {
            conditions: {
                cameraId: null,
                startTime: new Date(
                    new Date().getTime() - 1000 * 60 * 60 * 24 * 30
                ),
                endTime: new Date(Date.now()),
                storeId: null,
                areaId: null,
                tunnelId: null
            },
            conditionList: {
                stores: [],
                areas: [],
                cameras: [],
                tunnels: []
            },
            curCamera: {},
            page: {
                pageNum: 1,
                pageSize: 8,
                pageTotal: 0
            },
            pageStyle: {
                position: "absolute",
                bottom: "20px",
                right: "15px",
                color: "#fff"
            },
            photos: [],
            downLoad: {
                ids: [],
                checkAll: false,
                checkPageAll: false,
                total: 0
            },
            nodata: false,
            initState: true
        };
    },
    computed: {
        cameraQuery() {
            let param = {
                tunnelId:
                    this.conditions.tunnelId === "null"
                        ? null
                        : this.conditions.tunnelId,
                storeId:
                    this.conditions.storeId === "null"
                        ? null
                        : this.conditions.storeId,
                areaId:
                    this.conditions.areaId === "null"
                        ? null
                        : this.conditions.areaId
            };
            return Object.assign({}, param);
        },
        photoDatagridQuery() {
            let param = {
                tunnelId:
                    this.conditions.tunnelId === "null"
                        ? null
                        : this.conditions.tunnelId,
                storeId:
                    this.conditions.storeId === "null"
                        ? null
                        : this.conditions.storeId,
                areaId:
                    this.conditions.areaId === "null"
                        ? null
                        : this.conditions.areaId,
                id:
                    this.conditions.cameraId === "null"
                        ? null
                        : this.conditions.cameraId,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime,
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize
            };
            return param;
        },
        allPhotoesQuery() {
            let param = {
                tunnelId:
                    this.conditions.tunnelId === "null"
                        ? null
                        : this.conditions.tunnelId,
                storeId:
                    this.conditions.storeId === "null"
                        ? null
                        : this.conditions.storeId,
                areaId:
                    this.conditions.areaId === "null"
                        ? null
                        : this.conditions.areaId,
                id:
                    this.conditions.cameraId === "null"
                        ? null
                        : this.conditions.cameraId,
                startTime: this.conditions.startTime,
                endTime: this.conditions.endTime
            };
            return param;
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            TunnelService.getTunnels().then(
                res => {
                    this.conditionList.tunnels = res;
                    let param = this.$route.params;
                    if (param.camera) {
                        this.curCamera = param.camera;
                        this.conditions.tunnelId = +param.camera.tunnelId;
                        this.getStoreAndAreas();
                    } else {
                        this.conditions.startTime = new Date(
                            new Date().getTime() - 1000 * 60 * 60 * 24
                        );
                        this.resetPageAndSearch();
                    }
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        getStoreAndAreas() {
            Promise.all([
                TunnelService.getStoresByTunnelId(this.conditions.tunnelId),
                TunnelService.getAreasByTunnelId(this.conditions.tunnelId)
            ]).then(
                result => {
                    this.conditionList.stores = result[0];
                    this.conditionList.areas = result[1];
                    if (this.initState) {
                        this.conditions.storeId = this.curCamera.storeId
                            ? this.curCamera.storeId
                            : "null";
                        this.conditions.areaId = this.curCamera.areaId
                            ? this.curCamera.areaId
                            : "null";
                    }
                    this.getCameraList();
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        getCameraList() {
            VideoService.getCamerasByConditions(this.cameraQuery).then(
                res => {
                    this.conditionList.cameras = res;
                    if (this.initState) {
                        this.conditions.cameraId = this.curCamera.id
                            ? this.curCamera.id
                            : "null";
                        this.resetPageAndSearch();
                    }
                },
                error => {
                    this.Log.info(error);
                }
            );
        },
        back() {
            this.$router.back(-1);
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.search();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.resetPageAndSearch();
        },
        search() {
            VideoService.historyPhotosDatagrid(this.photoDatagridQuery).then(
                res => {
                    this.photos = [];
                    this.initState = false;
                    if (res.pagedList.length == 0) {
                        this.nodata = true;
                    } else {
                        this.nodata = false;
                        res.pagedList.forEach(item => {
                            VideoService.getPhoto({ path: item.strPath })
                                .then(res => {
                                    return (
                                        "data:image/png;base64," +
                                        btoa(
                                            new Uint8Array(res).reduce(
                                                (data, byte) =>
                                                    data +
                                                    String.fromCharCode(byte),
                                                ""
                                            )
                                        )
                                    );
                                })
                                .then(src => {
                                    console.log(src);
                                    this.photos.push({
                                        id: item.strToken,
                                        time: item.strStartTime.replace(
                                            /[TZ]/g,
                                            " "
                                        ),
                                        path: item.strPath,
                                        tunnel: item.tunnel,
                                        area: item.area,
                                        store: item.store,
                                        name: item.name,
                                        imgPath: src,
                                        checked: false
                                    });
                                });
                        });
                    }
                    this.page.pageTotal = res.total;
                    this.downLoad.total = 0;
                },
                error => {
                    this.$Message.error("查询失败");
                }
            );
        },
        resetPageAndSearch() {
            this.page.pageNum = 1;
            this.downLoad.checkPageAll = false;
            this.search();
        },
        downLoadPhotoes() {
            if (this.downLoad.total) {
                this.$Modal.confirm({
                    title: "历史记录",
                    content: "<p>确定下载照片吗</p>",
                    onOk: () => {
                        if (this.downLoad.checkAll) {
                            VideoService.allHistoryPhotoes(
                                this.allPhotoesQuery
                            ).then(
                                res => {
                                    let JSZip = require("jszip");
                                    let zip = new JSZip();
                                    let blogTitle =
                                        "历史照片" +
                                        new Date(Date.now()).format(
                                            "yyyy-MM-ddhh:m:ss"
                                        );
                                    let imgs = zip.folder(blogTitle);
                                    this.$Message.info("即将开始下载，请等待");
                                    res.forEach((item, index) => {
                                        VideoService.getPhoto({
                                            path: item.strPath
                                        })
                                            .then(photoStream => {
                                                return (
                                                    "data:image/png;base64," +
                                                    btoa(
                                                        new Uint8Array(
                                                            photoStream
                                                        ).reduce(
                                                            (data, byte) =>
                                                                data +
                                                                String.fromCharCode(
                                                                    byte
                                                                ),
                                                            ""
                                                        )
                                                    )
                                                );
                                            })
                                            .then(data => {
                                                let length = item.strPath.split(
                                                    "."
                                                ).length;
                                                let format = item.strPath.split(
                                                    "."
                                                )[length - 1];
                                                let time = item.strStartTime.replace(
                                                    /[TZ]/g,
                                                    ""
                                                );
                                                let name =
                                                    item.tunnel +
                                                    item.area +
                                                    item.store +
                                                    item.name +
                                                    time +
                                                    "." +
                                                    format;
                                                imgs.file(
                                                    name,
                                                    data.substring(22),
                                                    { base64: true }
                                                );
                                                if (index === res.length - 1) {
                                                    let _this = this;
                                                    zip.generateAsync({
                                                        type: "blob"
                                                    }).then(function(content) {
                                                        // see FileSaver.js
                                                        saveAs(
                                                            content,
                                                            blogTitle + ".zip"
                                                        );
                                                        _this.resetPhotos();
                                                        _this.downLoad.checkAll = false;
                                                    });
                                                }
                                            });
                                    });
                                },
                                error => {
                                    this.$Message.error("下载失败");
                                }
                            );
                        } else {
                            this.photos.map(photo => {
                                if (photo.checked) {
                                    // 生成一个a元素,添加属性并触发
                                    let a = document.createElement("a");
                                    let event = new MouseEvent("click");
                                    let length = photo.path.split(".").length;
                                    let format = photo.path.split(".")[
                                        length - 1
                                    ];
                                    let time = photo.time.replace(/ /g, "");
                                    a.download =
                                        photo.tunnel +
                                        photo.area +
                                        photo.store +
                                        photo.name +
                                        time +
                                        "." +
                                        format;
                                    let url =
                                        "/" +
                                        this.ApiUrl.split("/")[3] +
                                        "/snaps/download/" +
                                        photo.path.replace(/\//g, ",");
                                    a.href = url;
                                    a.dispatchEvent(event);
                                }
                            });
                            this.resetPhotos();
                            this.downLoad.checkPageAll = false;
                        }
                    },
                    onCancel: () => {
                        this.resetPhotos();
                        this.downLoad.checkAll = false;
                    }
                });
            } else {
                this.$Message.error("请至少选择一项");
            }
        },
        handleCheckPageAll() {
            this.downLoad.checkPageAll = !this.downLoad.checkPageAll;
            this.downLoad.total = 0;
            this.photos.map(photo => {
                photo.checked = this.downLoad.checkPageAll;
                if (photo.checked) this.downLoad.total++;
            });
        },
        stateChange(photo) {
            photo.checked = !photo.checked;
            let len = this.photos.length;
            this.downLoad.total = 0;
            this.photos.map(photo => {
                if (photo.checked) {
                    this.downLoad.total++;
                }
            });
            this.downLoad.checkPageAll =
                this.downLoad.total === len ? true : false;
        },
        handleCheckAll() {
            this.downLoad.checkAll = !this.downLoad.checkAll;
            this.downLoad.total = this.downLoad.checkAll
                ? this.page.pageTotal
                : 0;
            this.photos.map(photo => {
                photo.checked = this.downLoad.checkAll;
            });
        },
        resetPhotos() {
            this.photos.map(photo => {
                photo.checked = false;
            });
            this.downLoad.total = 0;
        }
    }
};
</script>

<style scoped>
.condition {
    width: 70%;
}
.card {
    width: 90%;
    margin: 1.2vmin 5%;
    position: relative;
    font-size: 1.4vmin;
    background: url("../../../../assets/UM/companyBg.png") no-repeat;
    background-size: 100% 100%;
}
.photo {
    width: 100%;
}
.checkbox {
    position: absolute;
    bottom: 0.2vmin;
    right: 0;
}
.list {
    padding-bottom: 2vmin;
    color: #fff;
}
.list >>> .ivu-checkbox-inner {
    width: 2vmin;
    height: 2vmin;
}
.list >>> .ivu-checkbox-inner:after {
    width: 0.8vmin;
    height: 1.2vmin;
}
.none {
    font-size: 2vmin;
    text-align: center;
}
.conditionSpan {
    color: #fff;
    font-size: 1.3vmin;
}
.conditions >>> .ivu-select-selection {
    border-radius: 1vmin;
}
.condition >>> .ivu-input {
    height: 3.2vmin;
    line-height: 3.2vmin;
    font-size: 1.3vmin;
}
.conditions .ivu-btn-primary {
    font-size: 1.2vmin !important;
}
.download {
    background: -webkit-linear-gradient(left, #7c83f2, #2734e1);
    background: -o-linear-gradient(right, #7c83f2, #2734e1);
    background: -moz-linear-gradient(right, #7c83f2, #2734e1);
    background: linear-gradient(to right, #7c83f2, #2734e1);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.2vmin !important;
}
.back {
    background: -webkit-linear-gradient(left, #e49b9b, #f61a1a);
    background: -o-linear-gradient(right, #e49b9b, #f61a1a);
    background: -moz-linear-gradient(right, #e49b9b, #f61a1a);
    background: linear-gradient(to right, #e49b9b, #f61a1a);
    border-color: #3e4f61;
    border-radius: 1vmin;
    font-size: 1.2vmin !important;
    color: #fff;
}
.list >>> .ivu-card-bordered {
    border: none;
}
</style>