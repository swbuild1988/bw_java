<template>
    <div>
        <h1 style="margin: 10px 0px 0px 20px;">H5Stream配置</h1>
        <Row style="margin:20px;">
            <Col span="6" offset="18" style="font-size: 16px">
                <vue-xlsx-table @on-select-file="getData">选择H5Stream配置文件</vue-xlsx-table>
                <Button type="error" v-on:click="save" style="margin-left: 20px" v-if="saveFlag">保存</Button>
            </Col>
        </Row>
        <div style="margin:20px;">
            <Table stripe border :columns="columns" :data="videoConfig"></Table>
        </div>
    </div>    
</template>
<script>
import { VideoService } from '../../../services/videoService'
export default {
    data(){
        return{
            videoConfig:[],
            columns:[
                {
                    title: 'ID',
                    key: 'id',
                    align: 'center'
                },
                {
                    title: 'IP',
                    key: 'ip',
                    align: 'center'
                },
                {
                    title: '端口',
                    key: 'port',
                    align: 'center'
                },
                {
                    title: '用户名',
                    key: 'user',
                    align: 'center'
                },
                // {
                //     title: '密码',
                //     key: 'password',
                //     align: 'center'
                // },
                {
                    title: '供应商',
                    key: 'vendor',
                    align: 'center'
                },
                {
                    title: '频道',
                    key: 'channelNo',
                    align: 'center'
                }
            ],
            saveFlag: false
        }
    },
    mounted(){
        
    },
    methods:{
        getData(data){
            // let sha256 = require('js-sha256')
            this.videoConfig = []
            data.body.forEach(config=>{
                let temp = {}
                temp.id = config[data.header[0]],
                temp.ip = config[data.header[1]],
                temp.port = config[data.header[2]],
                temp.user = config[data.header[3]],
                temp.password = config[data.header[4]],
                temp.vendor = config[data.header[5]],
                temp.channelNo = config[data.header[6]]
                this.videoConfig.push(temp)
            })
            this.saveFlag = true
        },
        save() {
            let _this = this
            this.Log.info(this.videoConfig)
            VideoService.batchAddH5StreamConfig(this.videoConfig).then(
                result=>{
                    _this.$Message.success("添加成功！")
                    _this.saveFlag = false
                },
                error=>{
                    _this.Log.info(error)
                })
        }
    }
}
</script>
<style scoped>

</style>
