<template>
    <div>
        <ModulePage v-bind="myNews"></ModulePage>
    </div>
</template>

<script>
import ModulePage from '../../../../components/Common/ModulePage'
export default {
    name: "UM_main",
    components:{ModulePage},
    data() {
        return {
            myNews:{
            moduleName:"个人中心",
            leftTree:[
                {
                    id: 1,
                    name: '密码修改',
                    url: '/UM/personCenter/editPass'
                },
            ],
            selected:[0,-1]  
            },
            treeNodeJumpUrl:"/UM/personCenter/editPass",
        };
    },
    created(){
        if (sessionStorage["refreshAddress"] == "" || sessionStorage["refreshAddress"].indexOf("/UM/myNews") < 0) {
            this.goToMoudle({path: this.myNews.leftTree[0].url});
            sessionStorage.setItem('selectedName','')
        }
            sessionStorage.setItem("refreshAddress", "");
    },
    methods: {
        goToMoudle(path) {
        this.$router.push(path);
        }
    }
};
</script>