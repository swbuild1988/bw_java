<template>
    <div>
        <div class="conditions">
            <Row>
                <Col span="6">
                    <div>
                        <span>管仓名称：</span>
                        <Input v-model="researchInfo.name" placeholder="支持模糊查询" class="inputWidth" />
                    </div>
                </Col>
                <Col span="6">
                    <div>
                        <span>所属管廊：</span>
                        <Select v-model="researchInfo.tunnelId" placeholder="请选择所属管廊" class="inputWidth">
                            <Option value="null">不限</Option>
                            <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>         
                        </Select>
                    </div>
                </Col>
                <Col span="6">
                    <span>管仓类型：</span>
                    <Select v-model="researchInfo.storeTypeId" placeholder="请选择管仓类型" class="inputWidth">
                        <Option value="null">不限</Option>
                        <Option v-for="item in types" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </Col>
                <Col span="6">
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
                    title: '管仓名称',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '所属管廊',
                    key: 'tunnelName',
                    align: 'center'
                },
                {
                    title: '管仓类型',
                    key: 'storeTypeName',
                    align: 'center'
                }
            ],
            data6:[],
            types:[],
            tunnels:[],
            researchInfo:{
                name:'',
                tunnelId:null,
                storeTypeId:null
            },
            page: {
                pageNum: 1,
                pageSize: 5,
                pageTotal: 0
            }
        }
    },
    mounted(){
        this.gettunnel();
        this.getstoretype();
        this.showTable();
    },
    computed:{
        params(){
            let param = {
                pageNum: this.page.pageNum,
                pageSize: this.page.pageSize,
                name:this.researchInfo.name,
                tunnelId: this.researchInfo.tunnelId,
                storeTypeId: this.researchInfo.storeTypeId,
            };
            return Object.assign({}, param);
        }
    },
    methods:{
        gettunnel(){
            this.axios.get('/tunnels').then(res =>{
                let {code,data} = res.data;
                let _tunnels = [];
                if(code == 200){
                    for(let i=0;i<data.length;i++){
                        let tunnel = {};
                        tunnel.id = data[i].id;
                        tunnel.name = data[i].name;
                        _tunnels.push(tunnel);
                    }
                    this.tunnels = _tunnels;
                }
            })
        },
        getstoretype(){
            this.axios.get('/store-type/list').then(res =>{
                let {code,data} = res.data;
                let _types = [];
                if(code == 200){
                    for(let i=0;i<data.length;i++){
                        let type = {};
                        type.id = data[i].id;
                        type.name = data[i].name;
                        _types.push(type);
                    }
                this.types = _types;
                }
            })
        },
        showTable(){
            this.axios.post('/tunnels/stores/datagrid',(this.params)).then(res =>{
                let {code,data} = res.data;
                if(code == 200){
                    let allinfo = [];
                    for( let index in data.list) {
                        let info={};
                        info.id = data.list[index].id;
                        info.name = data.list[index].name;
                        if(data.list[index].tunnel != null){
                            info.tunnelId = data.list[index].tunnel.id;
                            info.tunnelName = data.list[index].tunnel.name;
                        }
                        if(data.list[index].storeType != null){
                            info.storeTypeId = data.list[index].storeType.id;
                            info.storeTypeName = data.list[index].storeType.name;
                        }
                        allinfo.push(info);
                    }
                    this.data6 = allinfo;
                    this.page.pageTotal = data.total;
                }
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
                this.$emit("listenToBarnChoose",this.data6[index]);
            }else{
                this.$emit("listenToBarnChoose",'');
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


