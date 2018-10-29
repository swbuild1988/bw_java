<template>
    <Table :height="height"
           :width="width"
           :columns="messageTitle"
           :data="message"
           :show-header="true"
    ></Table>
</template>

<style scoped>
    .ivu-table-wrapper >>> .ivu-table{
        color: rgba(0,0,0,.7);
        /*边框样式*/
        background-color: rgba(249,249,249,.2);
        /*color: #19485f;*/
    }
    .ivu-table-wrapper >>> .ivu-table-header{
        background: transparent;
        color: rgba(0,0,0,.7);
        /*边框样式*/
        /*background: linear-gradient(rgb(0,20,30), rgb(0,40,70));*/
        /*color: rgba(255,255,255,.7);*/
        /*border: 2px solid rgba(0,90,120,.3);*/
    }
    .ivu-table-wrapper >>> .ivu-table th{
        background-color: transparent;
        border: 1px solid transparent;
    }
    .ivu-table-wrapper >>> .ivu-table td{
        background-color: rgba(0,26,39,0);
    }
    /*边框样式*/
    .ivu-table >>>　.ivu-table td{
        border-bottom: 1px solid transparent;
    }

</style>

<script>

    export default {
        name:'warnUnit',
        data() {
            return {
                messageTitle: [
                    {
                        title: '单位',
                        key: 'name',
                        align:'center'
                    },
                    {
                        title: '联系人',
                        key: 'contact',
                        align:'center'
                    },
                    {
                        title: '手机号',
                        key: 'tel',
                        align:'center'
                    }
                ],
                message: [
                    {
                        name: '上海市第一人民医院',
                        contact:"张三",
                        tel: '13702569817',
                    },
                    {
                        name: '上海市第一人民医院',
                        contact:"李四",
                        tel: '13702569817',
                    },
                    {
                        name: '上海市第一人民医院',
                        contact:"王五",
                        tel: '13702569817',
                    },
                    {
                        name: '上海市第一人民医院',
                        contact:"小明",
                        tel: '13702569817',
                    },
                    {
                        name: '上海市第一人民医院',
                        contact:"小红",
                        tel: '13702569817',
                    },
                ],
            }
        },
        props: ['width', 'height'],
    }
</script>
    