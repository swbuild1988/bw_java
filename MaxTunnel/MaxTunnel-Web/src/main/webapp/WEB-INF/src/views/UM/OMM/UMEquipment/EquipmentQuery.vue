<template>
    <div>
        <Row>
            <Col span="16">
                <span class="planDec">所属管廊</span>
                <Select v-model="condition.tunnelId" style="width: 150px;margin-right: 15px;font-size: 14px;" @on-change='conditionChange()'>
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{ item.name }}</Option>
                </Select>
                <span class="planDec">设备类型</span>
                <Select v-model="condition.type" style="width: 150px" @on-change='conditionChange()'>
                    <Option value=null key="0">所有</Option>
                    <Option v-for="item in equipmentTypes" :value="item.key" :key="item.key">{{ item.value }}</Option>
                </Select>
            </Col>
            <Col span="8" align="right">
            <Button class="btn" @click="conditionChange()">查询</Button>
            <Button class="btn">导出</Button>
            <!-- <Button class="btn" @click.native="goToMoudle({ path:'/UM/main/addPlan'})">添加</Button> -->
            </Col>
        </Row>

        <Row>
            <Table :columns='columns' :data="equipments"></Table>
        </Row>
    </div>
</template>

<script>
import axios from "axios";
import types from '../../../../../static/Enum.json'
export default {
    name:"queryEquipment",
    data(){
        return {
            columns:[
                {
                    title:"设备号",
                    key:"assetNo"
                },
                {
                    title:"设备名称",
                    key:"name"
                },
                {
                    title:"设备类型",
                    key:"typeName"
                },
                {
                    title:"所属管廊",
                    key:"tunnelName"
                },
                {
                    title:"供应商",
                    key:"venderName"
                },
                {
                    title:"设备状态",
                    key:"statusName"
                },
                {
                    title:"操作",
                    key:"action",
                    width:150,
                    align:'center',
                    render:(h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.show(params.index)
                                    }
                                }
                            }, 'View'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.edit(params.index)
                                    }
                                }
                            }, 'Edit')
                        ]);
                    }
                }
            ],
            equipments:[],
            tunnels: [],
            equipmentTypes:[],
            condition:{
                tunnelId: null,
                type:null,
            }
        }
    },
    mounted(){
        //从数据库读取select的option选项
        axios.get("/tunnels ").then(response =>{
            let { code, data } = response.data;
            if (code == 200) {
                this.tunnels = data;
            }
        });

        axios.get("/equipments").then(result=>{
            let {code, data} = result.data;
            if (code == 200){
                this.equipments = data;
            }
        });
        
        this.equipmentTypes = types.equipmentType;
    },
    components:{
    },
    methods: {
        // type 1:查看， 2：编辑  3：新增
        goToMoudle: function (index, type) {
            this.$router.push({
                name: 'UMDetailEquipment',
                params: {
                    id: this.equipments[index].id,
                    type: type,
                }
            });
        },
        //将选中的option传到父组件
        conditionChange(){
            axios.post("/equipments/condition", this.condition).then(result=>{
                let {code, data} = result.data;
                if (code == 200){
                    this.equipments = data;
                }
            });
        },
        show(index){
            // console.log("show", this.equipments[index].name);
            this.goToMoudle(index, types.pageType.Read);
        },
        edit(index){
            // console.log("edit", this.equipments[index].name);
            this.goToMoudle(index, types.pageType.Edit);
        }
    }  
}
</script>

