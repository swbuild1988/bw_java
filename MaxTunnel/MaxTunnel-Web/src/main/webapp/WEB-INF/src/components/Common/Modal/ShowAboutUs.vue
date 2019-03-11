<template>
    <Modal title="关于我们" v-model="show.state" width="600px">
        <div class="aboutFont">
            <Row>
                <Col span="24" style="text-align: center;">
                <h3>{{company.version}}</h3>
                </Col>
                <Col span="1">
                <span>
                    <Icon type="android-star" class="icons" color="#00a1ff"></Icon>
                </span>
                </Col>
                <Col span="23">
                <span>：{{company.name}}</span>
                </Col>
                <Col span="1">
                <span style="width: 20px;">
                    <Icon type="ios-location" class="icons" color="#00a1ff"></Icon>
                </span>
                </Col>
                <Col span="23">
                <span>：{{company.address}}</span>
                </Col>
                <Col span="1">
                <span>
                    <Icon type="ios-telephone" class="icons" color="#00a1ff"></Icon>
                </span>
                </Col>
                <Col span="23">
                <span> ：{{company.telephone}}</span>
                </Col>
                <Col span="1">
                <span>
                    <Icon type="ios-email" class="icons" color="#00a1ff"></Icon>
                </span>
                </Col>
                <Col span="23">
                <span>：{{company.email}}</span>
                </Col>
            </Row>
        </div>
        <div slot="footer"></div>
    </Modal>
</template>

<script>
    import {
        EnterGalleryService
    } from '../../../services/enterGalleryService.js'

    export default {
        name: "show-about-us",
        props: {
            show: {
                type: Object,
                default: function () {
                    return {
                        state: false
                    };
                }
            },
            company: {
                type: Object,
                default: function () {
                    return {
                        version: "MaxTunnel-Test",
                        name: "上海波汇科技有限公司",
                        email: "Bandweaver@.cn",
                        telephone: "12413321231",
                        address: "上海市浦东新区上科路88号豪威科技园1幢503"
                    }
                }
            }
        },
        mounted() {
            this.getUsData();
        },
        methods: {
            getUsData() {
                var _this = this;
                EnterGalleryService.getAboutUsData().then((result => {
                    if (result) {
                        _this.company.version = result.version;
                        _this.company.email = result.email;
                        _this.company.telephone = result.contact;
                        _this.company.address = result.address;
                        _this.company.name = result.company;
                    }
                }))
            },
        }
    }
</script>

<style scoped>
    .aboutFont {
        font-size: 1.66vmin;
        line-height: 3.2vmin;
    }

    .icons {
        font-size: 2vmin;
    }

    .ivu-modal-header-inner {
        font-size: 1.66vmin;
    }
</style>