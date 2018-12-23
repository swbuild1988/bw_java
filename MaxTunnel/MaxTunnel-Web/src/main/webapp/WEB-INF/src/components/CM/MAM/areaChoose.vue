<template>
    <div>
        <div class="conditions">
            <Row>
                <Col span="8">
                    <div>
                        <span>区域名称：</span>
                        <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
                    </div>
                </Col>
                <Col span="8">
                    <div>
                        <span>所属管廊：</span>
                        <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                            <Option value="null">不限</Option>
                            <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                        </Select>
                    </div>
                </Col>
                <Col span="8">
                    <Button type="primary" size="small"  icon="ios-search" @click="research()">查询</Button>
                </Col>
            </Row>
        </div>
        <div class="list">
            <Row>
                <Table border ref="selection" :columns="columns7" :data="data6"></Table>
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize"  show-total
                placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator style="text-align: right;margin-top: 20px;"></Page>
            </Row>
        </div>
    </div>
</template>

<script>
import { TunnelService } from '../../../services/tunnelService'

export default {
    name: 'barn-choose',
    data(){
        return {
            columns7:[
                {
                    title: '选择',
                    width: 80,
                    align: 'center',
                    render: (h,params) => {
                        return h("div",[
                            h("Checkbox",{
                                props: {
                                    value: params.row.checkBox
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.data6.forEach((items)=>{
                                        this.$set(items,'checkBox',false)
                                    });
                                    this.data6[params.index].checkBox = val;
                                    this.sendMsg(params.index);
                                    }
                                }
                            })
                        ])
                    }
                },
                {
                    title: '区域名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '所属管廊',
                    key: 'tunnelName',
                    align: 'center'
                }
            ],
            data6:[],
            types:[],
            tunnels:[],
            researchInfo:{
                name: null,
                tunnelId:null
            },
            page: {
                pageNum: 1,
                pageSize: 5,
                pageTotal: 0
            }
        }
    },
    props:{
        tunnelId: {
            required: false
        }
    },
    mounted(){
        this.init();
        this.showTable();
    },
    watch:{
        'tunnelId': function(newValue,oldValue){
            this.researchInfo.tunnelId = newValue
            this.showTable()
        }
    },
    computed:{
        params(){
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name:this.researchInfo.name,
                tunnelId: this.tunnelId ? this.tunnelId : this.researchInfo.tunnelId
            };
            return Object.assign({}, param);
        }
    },
    methods:{
        init(){
            let _this = this
            TunnelService.getTunnels().then(
                result=>{
                    this.tunnels = result
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        showTable(){
            let _this = this
            TunnelService.areaDatagird(this.params).then(
                result=>{
                    let allinfo = [];
                    for( let index in result.list) {
                        let info={};
                        info.id = result.list[index].id;
                        info.name = result.list[index].name;
                        if(result.list[index].tunnel != null){
                            info.tunnelId = result.list[index].tunnel.id;
                            info.tunnelName = result.list[index].tunnel.name;
                        }
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = result.total;
                },
                error=>{
                    _this.Log.info(error)
                })
        },
        handlePage(value) {
            this.page.pageNum = value;
            this.showTable();
        },
        handlePageSize(value) {
            this.page.pageSize = value;
            this.showTable();
        },
        sendMsg: function(index){ 
            if(this.data6[index].checkBox == true){
                this.$emit("listenToAreaChoose",this.data6[index]);
            }else{
                this.$emit("listenToAreaChoose",'');
            }
        },
        research(){
            this.showTable();
        }
    }
}
</script>

<style scoped>
.conditions{
    padding: 10px;
}
.inputWidth{
    width: 60%;
}
.list{
    margin-top:10px;
}
</style>


