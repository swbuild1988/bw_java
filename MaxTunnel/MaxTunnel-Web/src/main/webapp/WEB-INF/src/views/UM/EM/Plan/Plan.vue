  <template>
  <div>
    <module-page v-bind="plans"></module-page>
  </div>
</template>

<script>
  import ModulePage from "../../../../components/Common/ModulePage";
  import { EnumsService } from '../../../../services/enumsService';
  import SockJS from 'sockjs-client'; // 'sockjs-client' 必须与package.json文件当中dependencies 当中的一模一样
  export default {
    name: "UM_plan",
    components: {ModulePage},
    data() {
      return {
        plans: {
          moduleName: "预案管理",
          leftTree: [],
        },
        // treeNodeJumpUrl: "/UM/plans/detial/processKey",
        treeNodeJumpUrl: "/UM/plans/detial/",
        executeNodeUrl: "/UM/plans/execute/processKey",
      };
    },
    mounted() {
    },
    created() {
      this.plans.leftTree = [];
      let _this = this
      EnumsService.getPlanType().then(
        result=>{
            result.forEach(a => {
            let temp = {};
            temp.name = a.key;
            temp.id = a.val;
            temp.processKey = a.processKey;
            temp.url = _this.treeNodeJumpUrl + a.processKey;
            temp.childNode = [
              {
                id: 11,
                name: "预案详情",
                url: _this.treeNodeJumpUrl + a.processKey,
              },
              {
                id: 12,
                name: "执行预案",
                url: _this.executeNodeUrl + a.processKey,
              }
            ]
            _this.plans.leftTree.push(temp);
          });
          //添加零时测试页面
          _this.plans.leftTree.push({
            name: "测试预案页面",
            id: 31,
            processKey: "textPlan",
            url: "/UM/plans/textPage/processKey"
          });
        },
        error=>{
          _this.Log.info(error)
        })
    },
    methods: {
      goToMoudle(path) {
        this.$router.push(path);
      }
    }
  };
</script>
