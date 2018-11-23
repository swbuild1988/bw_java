<template>
<div :style="backStyle">
    <Form ref="addRelatedUnitsInfo" :model="addRelatedUnitsInfo" :label-width="120" :rules="ruleValidate">
        <h2 class="formTitle" v-show="pageType!=pageTypes.Edit">添加相关单位</h2>
        <h2 class="formTitle" v-show="pageType==pageTypes.Edit">更新相关单位信息</h2>
        <FormItem label="单位名称：" prop="name">
            <Input type="text" v-model="addRelatedUnitsInfo.name"></Input>
        </FormItem>
        <FormItem label="联系人：" prop="contact">
            <Input type="text" v-model="addRelatedUnitsInfo.contact"></Input>
        </FormItem>
        <FormItem label="联系方式：" prop="tel"> 
            <Input type="text" v-model="addRelatedUnitsInfo.tel" placeholder="XXXXXXXXXXX或者000-0000000或者0000-0000000"></Input>
        </FormItem>
        <FormItem label="单位类别：" prop="unitType">
            <Select v-model="addRelatedUnitsInfo.unitType">
                <Option v-for="(item,index) in unitType" :value="item.val" :key="index">{{ item.key }}</Option>
            </Select>
        </FormItem>
        <FormItem label="单位地址：" prop="address">
            <Input type="text" v-model="addRelatedUnitsInfo.address"></Input>
        </FormItem>
        <FormItem label="管仓区段：" class="ivu-form-item-required">
            <Input type="text" icon="arrow-down-b"  v-model="sectionName" @click="showTree"></Input>                   
            <Tree class="tree"  :data="treeList" show-checkbox ref="tree" @on-check-change="choiceAll" v-show="isShow"></Tree>
            <div class="ivu-form-item-error-tip" v-show="validateSectionName==true">请选择所属仓段</div>
        </FormItem>
        <FormItem style="text-align: center">
            <Button type="primary" @click="submit('addRelatedUnitsInfo')" v-show="pageType!=pageTypes.Edit" :disabled="isDisable">提交</Button>
            <Button type="primary" @click="update('addRelatedUnitsInfo')" v-show="pageType==pageTypes.Edit" :disabled="isDisable">更新</Button>
            <Button type="ghost"  style="margin-left: 8px" @click="handleReset('addRelatedUnitsInfo')">取消</Button>
        </FormItem>
    </Form>
</div>       
</template>
<script>
import axios from "axios";
import { TunnelService } from '../../../../services/tunnelService'
import { EnumsService } from '../../../../services/enumsService'
import { RelatedUnitService } from '../../../../services/relatedUnitService'
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return{
            addRelatedUnitsInfo:{
                name: null,
                contact: null,
                tel: null,
                unitType: null,
                address: null,
                sectionIds: null
            },
            sections:[],
            unitType:[],
            treeList:[],
            isShow: false,
            sectionName: null,
            // 页面类型 1：查看 2：编辑 3：新增
            pageType: 1,
            pageTypes: types.pageType,
            isDisable: false,
            ruleValidate: {
                name: [
                    { required: true, message: '请填写单位名称', trigger: 'change' }
                ],
                contact: [
                    { required: true, message: '请填写联系人', trigger: 'blur' }
                ],
                tel: [
                    { required: true, message: '请填写联系方式', trigger: 'blur' },
                    { validator(rule,value,callback,source,options) {
                        var errors = []
                        if(!(/^((0\d{2,3}-\d{7,8})|(1[358496]\d{9}))$/).test(value)){
                        // if(!(/^1(3|4|5|7|8)\d{9}$/).test(value)||!(/0\d{2,3}-\d{7,8}/).test(value)){
                            callback("请输入正确的联系方式")
                        }
                        callback(errors)
                    }}
                ],
                unitType: [
                    { type: 'number', required: true, message: '请填写单位类别', trigger: 'change'}
                ],
                address: [
                    { required: true, message: '请填写单位地址', trigger: 'blur' }
                ],
            },
            validateSectionName: false,
            backStyle:{
                backgroundImage: "url(" + require("../../../../assets/UM/backImg.jpg") + ")",   
                height: '100%',
                position: 'relative',
                backgroundAttachment: 'fixed',
                backgroundSize: 'cover',
                minHeight: '100%',
                paddingTop: '40px'
            }
        }
    },
    created() {
        let body = document.querySelector('body')
        body.addEventListener('click',(e)=>{
            if( 
                e.target.className=='ivu-checkbox-input' ||
                e.target.className=='ivu-icon ivu-icon-arrow-down-b ivu-input-icon ivu-input-icon-normal' ||
                e.target.className=='ivu-icon ivu-icon-arrow-right-b' ||
                e.target.className=='ivu-tree-children' ||
                e.target.className=='ivu-tree-arrow ivu-tree-arrow-open' ||        
                e.target.className=='ivu-tree-title' ||
                e.target.className=='tree ivu-tree' ||
                e.target.className=='ivu-tree-arrow' ||
                e.target.nodeName=='UL' ||
                e.target.nodeName=='LI'
            ){
                this.isShow=true
            }else{
                this.isShow=false;
            }
        })
    },
    mounted(){
        this.addRelatedUnitsInfo.id = this.$route.params.id;
        this.pageType = this.$route.params.type;
        let _this = this;
        TunnelService.getTunnelsTree().then(
            function(result){
                result.forEach(element => {
                    var firstChildren = []
                    let temp={}
                    temp.title = element.name
                    temp.id = element.id
                    if(element.list.length!=0){
                        element.list.forEach(a=>{
                            var secondChildren = []
                            let obj = {}
                            obj.title = a.name
                            obj.id = a.id
                            obj.disabled = true
                            if(a.list.length!=0){
                                obj.disabled = false
                                 a.list.forEach(b=>{
                                    let secondInfo = {}
                                    secondInfo.title = b.name
                                    secondInfo.id = b.id
                                    secondChildren.push(secondInfo)
                                    obj.children = secondChildren
                                })
                            }
                            firstChildren.push(obj)                        
                            temp.children = firstChildren
                        })
                    }
                    _this.treeList.push(temp)                   
                });
            },
            function(error){
                console.log(error)
        })
        // this.axios.get('tunnels/tree').then(response=>{
        //     let{ code,data } = response.data
        //     if(code==200){
        //         data.forEach(element => {
        //             var firstChildren = []
        //             let temp={}
        //             temp.title = element.name
        //             temp.id = element.id
        //             if(element.list.length!=0){
        //                 element.list.forEach(a=>{
        //                     var secondChildren = []
        //                     let obj = {}
        //                     obj.title = a.name
        //                     obj.id = a.id
        //                     obj.disabled = true
        //                     if(a.list.length!=0){
        //                         obj.disabled = false
        //                          a.list.forEach(b=>{
        //                             let secondInfo = {}
        //                             secondInfo.title = b.name
        //                             secondInfo.id = b.id
        //                             secondChildren.push(secondInfo)
        //                             obj.children = secondChildren
        //                         })
        //                     }
        //                     firstChildren.push(obj)                        
        //                     temp.children = firstChildren
        //                 })
        //             }
        //             this.treeList.push(temp)                   
        //         });
        //     }
        // })
        EnumsService.getUnitType().then(
            function(result){
                _this.unitType = result
            },
            function(error){
                console.log(error)
        })
        // axios.get('/unittype-enums').then(response=>{
        //     let{ code,data } = response.data
        //     if(code==200){
        //         this.unitType = data;
        //     }
        // });
        RelatedUnitService.getRelatedUnitInfoById(_this.$route.params.id).then(
            function(result1){
                _this.addRelatedUnitsInfo = result1
                TunnelService.getSectionsByIds(_this.addRelatedUnitsInfo.sectionIds).then(
                        function(result2){
                            _this.sections = result2
                            var str = ''
                            for(var k=0; k<_this.sections.length; k++){
                                if(_this.sections.length==1){
                                    _this.sectionName=_this.sections[k].name
                                }else{
                                    if(_this.sections[k]==null){
                                        str=''
                                    }else{
                                        str += _this.sections[k].name + '，'
                                        _this.sectionName = str.substring(0,str.length-1)
                                    }
                                }
                            }
                        },
                        function(error2){
                            console.log(error2)
                    })
            },
            function(error1){
                console.log(error1)
            })
        // axios.get('relatedunits/'+this.$route.params.id).then(response=>{
        //     let{ code,data } = response.data
        //     if(code==200){
        //         this.addRelatedUnitsInfo = data;
        //         axios.get('sections/batch/'+this.addRelatedUnitsInfo.sectionIds).then(response=>{
        //             let{ code,data } = response.data
        //             if(code==200){
        //                 this.sections = data
        //                 var str = ''
        //                 for(var k=0; k<this.sections.length; k++){
        //                     if(this.sections.length==1){
        //                         this.sectionName=this.sections[k].name
        //                     }else{
        //                         if(this.sections[k]==null){
        //                             str=''
        //                         }else{
        //                             str += this.sections[k].name + '，'
        //                             this.sectionName = str.substring(0,str.length-1)
        //                         }
        //                     }
        //                 }
        //             }
        //         })
        //     }
        // });
    },
    methods:{
        choiceAll(){
            let arr = this.$refs.tree.getCheckedNodes()
            var str = ''
            var strIds = ''
            this.sectionName = null
            if(arr.length==0){
                this.addRelatedUnitsInfo.sectionIds = null
                this.validateSectionName = true
            }else{
                for(let index in arr){
                    str+=arr[index].title+','
                    this.sectionName = str.substring(0,str.length-1)
                    strIds += arr[index].id+','
                    this.addRelatedUnitsInfo.sectionIds = strIds.substring(0,strIds.length-1)
                }
                this.validateSectionName = false
            }
        },
        showTree(){
            this.isShow = true
        },
        submit(name){
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false
                this.choiceAll()
                this.$refs[name].validate((valid)=>{
                    if(valid&&this.validateSectionName==false){
                        this.axios.post('/relatedunits',this.addRelatedUnitsInfo).then(response=>{
                            this.$router.push("/UM/relatedUnits/query");
                        })
                    }
                })
            },2000)
            // axios.post('/relatedunits',this.addRelatedUnitsInfo).then(response=>{
            //     this.$router.push("/UM/relatedUnits/query");
            // })
        },
        update(name) {
            this.isDisable = true
            setTimeout(()=>{
                this.isDisable = false;
                this.$refs[name].validate((valid)=>{
                    if(valid){
                        axios.put('/relatedunits',this.addRelatedUnitsInfo).then(response=>{
                            this.$router.push("/UM/relatedUnits/query");
                        })  
                    }
                })
            },2000)
        },
        handleReset(name){
            this.$refs[name].resetFields()
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 680px;
    margin: 0px auto;
    background: #fff;
    padding: 10px 20px;
}
.tree{
    background: #fff;
    z-index: 2;
    border: 1px solid #cccccc;
}
</style>
