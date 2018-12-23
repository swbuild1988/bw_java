<template>
    <div>
        <!-- 区域管理之新增区域 -->
        <Modal v-model="show.state" title="新增区域" :label-width="100">
            <Form ref="formValidate" :model="formValidate">
                <FormItem label="所属管廊：" prop="tunnelId" class="formStyle">
                    <Select v-model="formValidate.tunnelId" placeholder="请选择所属管廊" class="inputStyle">
                        <Option v-for="item in tunnels" :value="item.id" :key="item.id">{{item.name}}</Option>
                    </Select>
                </FormItem>
                <label>开始:</label>
                <input v-model="formValidate.start"/>
                <label>结束:</label>
                <input v-model="formValidate.end"/>
                
                <FormItem label="SN命名方式">
                    <RadioGroup v-model="formValidate.snChoose">
                        <Radio v-for="item in snChooses" :key="item.id" :label="item.id">
                            <span>{{item.name}}</span>
                        </Radio>
                    </RadioGroup>
                </FormItem>
                
                <FormItem label="区域命名方式">
                    <RadioGroup  v-model="formValidate.areaChoose">
                        <Radio v-for="item in areaChooses" :key="item.id" :label="item.id">
                            <span>{{item.name}}</span>
                        </Radio>
                    </RadioGroup>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="primary" size="large" v-on:click="sendMsg('formValidate')">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
export default {
    name: "area-add-multi",
    data() {
        return {
            tunnels: [],
            formValidate: {
                tunnelId: 0,
                areaChoose: 1,
                snChoose: 1,
                start: 1,
                end: 20
            },
            snChooses: [
                {
                    id: 1,
                    name: "同区域号，2位（01）"
                },
                {
                    id: 2,
                    name: "同区域号，3位（001）"
                }
            ],
            areaChooses: [
                {
                    id: 1,
                    name: "同区域号"
                },
                {
                    id: 2,
                    name: "管廊名+ “-” + 区域号"
                }
            ]
        };
    },
    props: {
        show: {
            state: {
                default: false
            }
        }
    },
    watch: {},
    mounted() {
        this.getTunnelList();
    },
    methods: {
        sendMsg: function(name) {
            console.log(this.formValidate);
            let areas = [];
            for (
                let index = parseInt(this.formValidate.start);
                index <= parseInt(this.formValidate.end);
                index++
            ) {
                let area = {
                    name: "",
                    sn: "",
                    tunnelId: this.formValidate.tunnelId,
                    location: "",
                    camera: ""
                };
                if (this.formValidate.areaChoose == 1) {
                    area.name = index + "区";
                } else if (this.formValidate.areaChoose == 2) {
                    let t_tunnel = this.tunnels.find(
                        a => a.id == this.formValidate.tunnelId
                    );
                    area.name = t_tunnel.name + "-" + index + "区";
                }
                let sLength = index.toString().length;
                let nLength = this.formValidate.snChoose == 1 ? 2 : 3;
                if (sLength > nLength) return;
                for (let k = 0; k < nLength - sLength; k++) {
                    area.sn += "0";
                }
                area.sn += index;

                areas.push(area);
            }
            this.$emit("listenToAddMulti", areas);
        },
        getTunnelList() {
            //获取所有管廊的简单列表
            this.axios.get("/tunnels").then(res => {
                let { code, data } = res.data;
                if (code == 200) {
                    this.tunnels = data;
                    this.formValidate.tunnelId = this.tunnels[0].id;
                }
            });
        }
    }
};
</script>

<style scoped>
.inputStyle {
    width: 70%;
}
.formStyle {
    margin-left: 32px;
}
.errorStyle {
    position: absolute;
    font-size: 16px;
    color: #ed3f14;
    top: 100px;
    left: 140px;
}
</style>

