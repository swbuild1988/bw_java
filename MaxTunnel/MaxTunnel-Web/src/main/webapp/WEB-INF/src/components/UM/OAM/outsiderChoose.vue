<template>
<div class="outSiderBox">
    <Row class="personCondition">
        <Col span="6">
            姓名：
            <Input placeholder="请输入访客姓名" v-model="conditions.name" class="conditionsWidth"></Input>
        </Col>
        <Col span="6">
            联系方式：
            <Input placeholder="请输入访客联系方式" v-model="conditions.tel" class="conditionsWidth"></Input>
        </Col>
        <Col span="6">
            身份证号：
            <Input placeholder="请输入访客身份证号" v-model="conditions.IDCard" class="conditionsWidth"></Input>
        </Col>
        <Col span="6">
            <Button size="small" @click="searchList" type="primary">查询</Button>
        </Col>
    </Row>
    <div class="list">
        <CheckboxGroup v-model="outsidesChoosed">
            <div class="inlineBox distanceBox" v-for="item in outSiders" :key="item.id" @mouseenter="showDetail(item.id)">
                <Poptip trigger="hover" :content=content placement="top-start">
                    <Checkbox :label="item.id+':'+item.name">
                        <span>{{item.name}}</span>
                    </Checkbox>
                </Poptip>
            </div>
            <Button size="small" @click="manageUserInfo">访客信息管理</Button>
        </CheckboxGroup>
        <div class="inlineBox distanceBox">
            <Button type="dashed" long icon="plus-round" @click="addOutsiders()">添加访客信息</Button>
        </div>
        <div class="distanceBox" >
            <span class="showChooseOutside" v-for="(item,index) in outsidesChoosed" :key="item">
                {{item.split(":")[1]}}
                <Icon type="ios-close-empty" color="red" @click="delOutside(index)" style="position: absolute;top: -0.8vmin;right: 0vmin;"></Icon>
            </span>
            <Button type="primary" size="small" @click="submit()">确定</Button>
        </div>
    </div>
    <Modal
        title="添加入廊用户"
        v-model="isOutStorage"
        width="42vw"
    >
        <Form ref="outsiders" :model="outsiders" :rules="ruleValidate" @submit.native.prevent class="addInfoForm">
            <FormItem>
                <Row v-for="(item,index) in items" :key="index" v-if="item.status">
                    <Col span="7">
                    姓名：
                        <Input type="text" v-model="item.name" placeholder="请输入访客姓名" @on-blur="validateName(index)" class="inputWidth"></Input>
                        <div class="ivu-form-item-error-tip"  v-show="check[index].checkName">访客姓名不能为空</div>
                    </Col>
                    <Col span="7">
                    身份证号：
                        <Input type="text" v-model="item.idCard" placeholder="请输入身份证号" @on-blur="validateIdCard(index)" class="inputWidth"></Input>
                        <div class="ivu-form-item-error-tip" v-show="check[index].checkidCard">身份证号不能为空</div>
                        <div class="ivu-form-item-error-tip" v-show="check[index].checkRightIdCard">请输入正确的身份证号</div>
                    </Col>
                    <Col span="7">
                    联系方式：
                        <Input type="text" v-model="item.tel" placeholder="请输入联系方式" @on-blur="validateTel(index)" class="inputWidth"></Input>
                        <div class="ivu-form-item-error-tip" v-show="check[index].checkTel">联系方式不能为空</div>
                        <div class="ivu-form-item-error-tip" v-show="check[index].checkRightTel">请输入正确的联系方式</div>
                    </Col>
                    <Col span="3" style="text-align: right">
                        <Button type="ghost" @click="handleRemove(index)">删除</Button>
                    </Col>
                </Row>
            </FormItem>
            <FormItem>
                <Row>
                    <Col span="12">
                        <Button type="dashed" long @click="handleAdd" icon="plus-round">添加访客信息</Button>
                    </Col>
                </Row>
        </FormItem>
        </Form>
        <div slot="footer">
            <!-- <Button type="default" @click="cancelReturn('toolReturn')">取消</Button> -->
            <Button type="primary" @click="submitOutsiders()" :disabled="isDisable">确定</Button>
        </div>
    </Modal>
    <Modal
        title="访客信息管理"
        width="42vw"
        v-model="userManage"
    >
    
        <Table :columns="userManageColumns" :data="outSiders"></Table>
    </Modal>
</div>    
</template>
<script>
import { EnterGalleryService } from '@/services/enterGalleryService'
export default {
    data(){
        return{
            conditions: {
                name: null,
                tel: null,
                IDCard: null
            },
            outSiders: [
                { id: 1, name: 'tom', tel: '17664102256', IDCard: 370982199602102542 },
                { id: 2, name: 'Firl', tel: '17664102256', IDCard: 370982199602102542 }
            ],
            outsidesChoosed: [],
            isOutStorage: false,
            outsiders: {
                name: null,
                idCard: null,
                tel: null
            },
            ruleValidate: {},
            items:[
                {
                    index: 1,
                    status: 1,
                    name: null,
                    idCard: null,
                    tel: null
                }
            ],
            index: 1,
            //验证
            check:[
                {
                    index: 1,
                    status: 1,
                    checkName: false,
                    checkidCard: false,
                    checkRightIdCard: false,
                    checkTel: false,
                    checkRightTel: false
                }
            ],
            checkStatus: false,
            isDisable:false,
            content: null,
            userManage: false,
            userManageColumns: [
                {
                    type: "index",
                    width: 60,
                    align: "center"
                },
                {
                    title: '姓名',
                    key: 'name',
                    align: 'center'
                },
                {
                    title: '身份证号',
                    key: 'identityNO',
                    align: 'center'
                },
                {
                    title: '联系方式',
                    key: 'telphone',
                    align: 'center'
                },
                {
                    title: '操作',
                    align: 'center',
                    render: (h, params) => {
                        return h('Button', {
                            props: {
                                size: 'small',
                                type: 'error'
                            },
                            on: {
                                click: ()=>{
                                    this.alldelete(params.row.id)
                                }
                            }
                        }, '删除')
                    }
                }
            ],
            deleteSelect: []
        }
    },
    mounted(){
        this.searchList()
    },
    methods:{
        searchList(){
            var params = {
                name: this.conditions.name,
                telphone: this.conditions.tel,
                identityNO: this.conditions.IDCard,
                outside: 2
            }
            this.axios.post('staffs/conditions',params).then(res=>{
                let { code,data } = res.data
                if(code==200){
                    this.outSiders = data
                }
            })
        },
        submit(){
            this.$emit('childByValue',this.outsidesChoosed)
        },
        delOutside(index){
            let arr = []
            arr = this.outsidesChoosed
            arr.splice(index,1)
            this.outsidesChoosed = arr
        },
        addOutsiders(){
            this.isOutStorage = true
        },
        handleRemove(index){
            this.items[index].status = 0;
        },
        handleAdd(){
            this.index++;
            this.items.push({
                index: this.index,
                status: 1,
                name: '',
                idCard: '',
                tel: ''
            });
            this.check.push({
                index: this.index,
                status: 1,
                checkName: false,
                checkidCard: false,
                checkRightIdCard: false,
                checkTel: false,
                checkRightTel: false
            })
        },
        validateName(index){
            if(this.items[index].name==null||this.items[index].name==undefined||this.items[index].name==""){
                this.check[index].checkName = true;
            }else{
                this.check[index].checkName = false;
            }
        },
        validateIdCard(index){
            if(this.items[index].idCard==null||this.items[index].idCard==undefined||this.items[index].idCard==""){
                this.check[index].checkidCard = true;
            }else{
                this.check[index].checkidCard = false;
                var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
                if(reg.test(this.items[index].idCard) === false) {
                    this.check[index].checkRightIdCard = true
                }else{
                    this.check[index].checkRightIdCard = false
                }
            }
        },
        validateTel(index){
            if(this.items[index].tel==null||this.items[index].tel==undefined||this.items[index].tel==""){
                this.check[index].checkTel = true;
            }else{
                this.check[index].checkTel = false;
                var reg = /^1[0-9]{10}$/;
                if(reg.test(this.items[index].tel)===false){
                   this.check[index].checkRightTel = true; 
                }else{
                    this.check[index].checkRightTel = false;
                }
            }
        },
        submitOutsiders(){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                for(var a in this.items){
                    this.validateName(a);
                    this.validateIdCard(a);
                    this.validateTel(a); 
                    if(this.check[a].checkName||this.check[a].checkidCard||this.check[a].checkTel||this.check[a].checkRightIdCard||this.check[a].checkRightTel){
                        this.checkStatus = false;
                    }else{
                        this.checkStatus = true;
                    }
                }
                if(this.checkStatus == true){
                    let arr = []
                    for(let index in this.items){
                        if(this.items[index].status==1){
                            arr.push({
                                name: this.items[index].name,
                                telphone: this.items[index].tel,
                                hireDate: new Date(),
                                outside: 2,
                                identityNO: this.items[index].idCard
                            })
                        }
                    }
                    this.axios.post('staffs/add-batch',arr).then(res=>{
                        let{ code,data } = res.data
                        if( code==200 ){
                            this.$Message.success('添加成功！')
                            this.isOutStorage = false
                            this.searchList()
                        }
                    })
                }
            },2000)
        },
        showDetail(id){
            this.axios.get('staffs/'+id).then(res=>{
                let{ code, data } = res.data
                if(code==200){
                    this.content = "手机号："+data.telphone+",身份证号："+data.identityNO
                }
            })
        },
        //访客信息管理
        manageUserInfo(){
            this.userManage = true
        },
        // handleSelectionChange(val) {
        //     this.deleteSelect = val;
        // },
        // startdelete(selection) {
        //     if (selection.length != 0) {
        //         this.deleteSelect = selection;
        //     }
        // },
        alldelete(id) {
            this.$Modal.confirm({
                title: "删除确认",
                content: "<p>确认要删除选中的信息吗？</p>",
                onOk: () => {
                    EnterGalleryService.delInfo(id).then(
                        result => {
                            this.$Message.info("已删除");
                            this.searchList();
                        },
                        error => {
                            this.Log.info(error)
                        }
                    )
                },
                onCancel: () => {
                    this.$Message.info("已取消操作");
                    this.searchList();
                }
            });
        },
    }
}
</script>
<style scoped>
.personCondition{
    margin-bottom: 0; 
    line-height: 4.5vmin;
}
.conditionsWidth{
    width: 60%
}
.outSiderBox{
    min-width: 700px;
    width: 40vw;
    background: #fff;
    border: 1px solid rgb(209, 209, 209);
    max-height: 20vh;
    overflow: auto;
}
.showChooseOutside{
    padding: 0.5vmin;
    background: #ccc;
    margin-right: 0.8vmin;
    border-radius: 0.4vmin;
    position: relative;
    padding-right: 1.3vmin;
}
.ivu-icon-ios-close-empty{
    font-size: 3vmin;
}
.inputWidth{
    width: 70%;
    min-width: 140px;
}
.addInfoForm .ivu-col-span-6{
    margin-bottom: 2vmin;
}
.inlineBox{
    display: inline-block;
}
.list{
    padding-bottom: 0px;
}
.distanceBox{
    margin: 0.5vmin;
}
.v-transfer-dom >>> .ivu-modal-wrap{
    z-index: 1999;
}
</style>
