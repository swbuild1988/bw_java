
<script>
    import {
        TunnelService
    } from '../../../../services/tunnelService'

    export default {
        data() {
            return {
                curModule: {
                    leftTree: [{
                        id:'001',
                        name:'总览'
                    }],
                    selected: [0, -1]
                },
            }
        },
        computed:{
            isShowComponent(){
                return this.$store.state.UMstate.isShowCardComponent
            }
        },
        created() {
            
            this.curModule.leftTree = [];
            let _this = this;

            TunnelService.getTunnels().then(
                (result) => {
                    result.forEach(a => {
                        let temp = {};
                        temp.id = a.id;
                        temp.name = a.name;
                        temp.childNode = [{
                                id: 21,
                                name: "监测总览",
                                url: "/UM/TunnelEnvironment/list/" + a.id,
                            },
                            {
                                id: 22,
                                name: "监测详情",
                                url: "/UM/TunnelEnvironment/details/" + a.id,
                            }
                        ];
                        _this.curModule.leftTree.push(temp);
                    })
                    let index = this.isShowComponent ? 0 : 1 ;

                    _this.goToMoudle({path: _this.curModule.leftTree[0].childNode[index].url});
                },
                (error) => {
                    console.log(error)
                })
        },
        methods: {
            goToMoudle(path) {
                this.$router.push(path);
            }
        }
    };
</script>