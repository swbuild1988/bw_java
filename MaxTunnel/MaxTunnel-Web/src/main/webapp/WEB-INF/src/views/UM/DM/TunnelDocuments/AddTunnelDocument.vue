<template>
  <div>
    <Form ref="addDocument" :model="addDocument" class="form">
        <h2 class="title">添加文件</h2>
        <div class="content">
            <FormItem label="资料类型：">
                <Select v-model="addDocument.docType" class="inputWidth">
                    <Option v-for="type in selectList.docTypes" :value="type.val" :key="type.val">{{ type.key }}</Option>
                </Select>
            </FormItem>
            <FormItem label="文件类型：">
                <Select v-model="addDocument.fileType" class="inputWidth">
                    <Option v-for="type in selectList.fileTypes" :value="type.val" :key="type.val">{{ type.key  +'  支持'+ type.type}}</Option>
                </Select>
            </FormItem>
            <FormItem label="文件：">
                <Upload multiple  ref="upload" :data="addDocument" type="drag" :action="'/'+apiUrl+'/files/upload'" :on-success="success" :on-error="error" class="inputWidth">
                    <div class="upload">
                        <Icon type="android-upload" size="52" color="#3399ff"></Icon>
                        <p>选择或拖拽要上传的文件</p>
                    </div>
                </Upload>
            </FormItem>
        </div>
    </Form>
  </div>
</template>
<script>
import { EnumsService } from '../../../../services/enumsService'
import types from '../../../../../static/Enum.json'
export default {
    data(){
        return{
            pageType: 3,
            pageTypes: types.pageType,
            addDocument:{
                fileType: 1,
                docType: 1
            },
            // ruleValidate: {
            //     fileType: [
            //         { required: true, message: '请填写文件名称', trigger: 'change' }
            //     ],
            //     docType: [
            //         { required: true, message: '请选择文件类型', trigger: 'change' }
            //     ]
            // },
            validateTelRules: true,
            isDisable:false,
            selectList:{
                docTypes:[],
                fileTypes:[]
            },
            apiUrl: null
        }
    },
    mounted(){
        this.pageType = this.$route.params.type
        this.getParams()
        this.apiUrl = this.ApiUrl.split('/')[3]
        // this.$Message.config({
        //     top: 300,
        //     duration: 10
        // })
    },
    // watch: {
    //   '$route': function () {
    //         this.pageType = 3;
    //         this.addDocument = {
    //             fileType: null,
    //             docType: null
    //         }
    //     }
    // },
    methods:{
        getParams() {
            let _this = this;
            EnumsService.getFileType().then(
                function(result){
                    _this.selectList.fileTypes = result
                },
                function(error){
                  console.log(error)
            })
            EnumsService.getDocType().then(
            function(result){
                    _this.selectList.docTypes = result
                },
                function(error){
                  console.log(error)
            })
        },
        success(res,file) {
            if(res.code == 200){
                // this.$Message.success({
                //     content: 'Upload Successfully',
                //     top: 600,
                //     duration: 10,
                // })
                this.$Modal.success({
                    title: '消息',
                    content: '上传成功'
                });
            }else{  
                let message = res.data.split(':')[1]
                this.$Modal.error({
                    title: '消息',
                    content: message
                });
                const fileList = this.$refs.upload.fileList;
                this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
            }
        },
        error(error) {
            this.$Message.error({
                content: error,
                duration: 10,
                top: 600
            })
        }
    }
}
</script>
<style scoped>
.ivu-form.ivu-form-label-right{
    width: 60vmin;
    margin: 2vmin auto;
}
h2{
    text-align: center;
    margin-bottom: 20px;
}
.tip{
    font-size: 0.6vmin;
}
.upload{
    height: 200px;
    padding: 48px 0;
}
.content{
    margin-top: 10vh;
}
.title{
    margin-top: 5vh;
    font-size: 1.8vmin;
}
.form >>> .ivu-form-item-label{
    width: 12vmin;
}
.inputWidth{
    width: 40vmin;
}

.form >>> .ivu-select-selection{
    height: 3.2vmin;
}

.form >>> .ivu-select-placeholder{
    font-size: 1.28vmin;
    padding-top: 0.64vmin;
    height: 2.2vmin;
    line-height: 2vmin;
}
.form >>> .ivu-select-selected-value{
    font-size: 1.28vmin;
    padding-top: 0.64vmin;
    height: 2.2vmin;
    line-height: 2vmin;
}
</style>
