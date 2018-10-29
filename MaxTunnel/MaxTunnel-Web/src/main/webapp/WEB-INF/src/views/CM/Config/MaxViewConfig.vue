<template>
    <div>
        <h1 style="text-align:center;margin-bottom:20px;margin-top:10px;">管廊配置</h1>
        <div style="margin:20px;">
            <Table stripe :columns="columns7" :data="data6"></Table>
        </div>
        <div>
            <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-total show sizer
                  placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize' show-elevator
                  :style='pageStyle'></Page>
        </div>
    </div>
</template>

<script>
export default {
    name: "maxview-config",
    data(){
        return {
            columns7: [
                {
                    title: 'IP',
                    key: 'ip',
                },
                {
                    title: '端口',
                    key: 'port'
                },
                {
                    title: '用户名',
                    key: 'username',
                },
                {
                    title: '密码',
                    key: 'password'
                },
                {
                    title: '操作',
                    key: 'action',
                    render: (h,params) => {
                        return h('div', [
                            h('Button',{
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '10px'
                                },
                                on: {
                                    click: () => {
                                        this.edit();
                                    }
                                }
                            },'修改'),
                            h('Button',{
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on:{
                                    click: () => {
                                        this.delete();
                                    }
                                }
                            },'删除')
                        ])
                    }
                }
            ],
            data6: [
                {
                    ip: '123.72.158.66',
                    port: 8080,
                    username: 'abc',
                    password: 123456
                }
            ],
            page: {
                pageNum: 1,
                pageTotal: 0,
                pageSize :10
            },
            pageStyle: {
                position: 'absolute',
                bottom: '35px',
                right: '40px'
            }
        }
    },
    methods: {
        edit(){
        },
        delete(){
        }   
    }
}
</script>

<style scoped>

</style>


