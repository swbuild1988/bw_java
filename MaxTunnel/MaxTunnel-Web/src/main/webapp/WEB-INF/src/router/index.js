import UMLogin from '../views/UM/UMLogin'
import CMLogin from '../views/CM/CMLogin'
import VMLogin from '../views/VM/VMLogin'
import NotFound from '../views/Common/404'
import UMPage from '../views/UM/UMPage'
import TunnelEnergy from '../views/UM/OAM/EnergyConsumptionManage/EnergyConsumption'
import Patrol from '../views/UM/OMM/PatrolScheme/PatrolScheme'
import UMMain from '../views/UM/UMMain'
import VMMain from '../Views/VM/VMMain'
import UMQueryPlan from '../views/UM/OMM/PatrolScheme/QueryPlan'
import UMAddPlan from '../views/UM/OMM/PatrolScheme/AddPlan'
import UMEditPlan from '../views/UM/OMM/PatrolScheme/EditPlan'
import UMPatrolDetails from '../views/UM/OMM/PatrolScheme/PatrolDetails'
//设备管理
import UMEquipmentHomePage from '../views/UM/OMM/UMEquipment/EquipmentHomePage.vue'
import UMEquipmentMain from '../views/UM/OMM/UMEquipment/Equipment.vue'
import UMQueryBackup from '../views/UM/OMM/UMEquipment/QueryBackup.vue'
import UMQueryTool from '../views/UM/OMM/UMEquipment/QueryTool.vue'
import UMQueryEquipment from '../views/UM/OMM/UMEquipment/QueryEquipment.vue'
import UMPatrolHomePage from '../views/UM/OMM/PatrolScheme/PatrolHomePage'
import UMDetailEquipment from '../views/UM/OMM/UMEquipment/DetailEquipment.vue'
import ToolHistoryCount from '../views/UM/OMM/UMEquipment/toolHistoryCount.vue'
import BackupHistoryCount from '../views/UM/OMM/UMEquipment/BackupHistoryCount.vue'
import TunnelHistoryCount from '../views/UM/OMM/UMEquipment/TunnelHistoryCount.vue'
import UMAddBackUp from '../views/UM/OMM/UMEquipment/AddBackUp.vue'
//能耗管理
import TunnelEnergyHomePage from '../views/UM/OAM/EnergyConsumptionManage/TunnelEnergyHomePage'
import TunnelEnergyDetial from '../views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionDetail'
import TunnelEnergyCatetory from '../views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionCategory'
import UMAddEquipment from '../views/UM/OMM/UMEquipment/AddEquipment.vue'
//缺陷管理
import UMDefectMain from '../views/UM/OMM/Defect/DefectMain'
import UMDefectDetail from '../views/UM/OMM/Defect/DefectDetail'
import UMDefectQuery from '../views/UM/OMM/Defect/DefectQuery'
import UMDistributeDefect from '../views/UM/OMM/Defect/distributeDefect'
// import UMTunnelUserMain from '../views/UM/OAM/TunnelUserManage/TunnelUserManage'
// import UMTunnelInUser  from '../views/UM/OAM/TunnelUserManage/TunnelInUser'
// import UMTunnelUserQuery  from '../views/UM/OAM/TunnelUserManage/TunnelUserQuery'
// import UMTunnelUserDetails  from '../views/UM/OAM/TunnelUserManage/TunnelUserDetails'
import UMTunnelSupervise from '../views/UM/MAM/TunnelSupervise/TunnelSupervise'
import UMDetailsTunnelSupervise from '../views/UM/MAM/TunnelSupervise/DetailsTunnelSupervise'
import UMListTunnelSupervise from '../views/UM/MAM/TunnelSupervise/ListTunnelSupervise'
import UMRobotMonitoringSystem from '../views/UM/MAM/RobotMonitoringSystem/RobotMonitoringSystem'
import UMRobotQuery from '../views/UM/MAM/RobotMonitoringSystem/RobotQuery'
import UMRobotDetails from '../views/UM/MAM/RobotMonitoringSystem/RobotDetails'
import UMVideoMonitoringSystem from '../views/UM/MAM/VideoMonitoringSystem/VideoMonitoringSystem'
import UMVideoMonitoringDetails from '../views/UM/MAM/VideoMonitoringSystem/VideoMonitoringDetails'
import UMVideoMonitoringHistory from '../views/UM/MAM/VideoMonitoringSystem/VideoMonitoringHistory'
import UMVideoPositionSetting from '../views/UM/MAM/VideoMonitoringSystem/VideoPositionSetting'
import UMOperatingSpace from '../views/UM/OAM/OperatingSpace/operatingSpace'
import UMOperatingSpaceTunnel from '../views/UM/OAM/OperatingSpace/operatingSpaceTunnel'
import UMListTunnelCustomer from '../views/UM/OAM/TunnelCustomerManage/ListTunnelCustomer'
// import UMEnterGalleryApplication from '../views/UM/OAM/EnterGalleryApplication/EnterGalleryApplication'
import UMAddTunnelCustomer from '../views/UM/OAM/TunnelCustomerManage/AddTunnelCustomer'
import UMTunnelCustomerManage from '../views/UM/OAM/TunnelCustomerManage/TunnelCustomerManage'
/*入廊管理 */
import UMEnterGalleryApplication from '../views/UM/OAM/EnterGalleryApplication/EnterGalleryApplication'
import UMQueryEnterGalleryApplication from '../views/UM/OAM/EnterGalleryApplication/QueryEnterGalleryApplication'
import UMAddEnterGalleryApplication from '../views/UM/OAM/EnterGalleryApplication/AddEnterGalleryApplication'
/*操作日志 */
import UMOperatingLogManage from '../views/UM/OAM/OperationLogManage/OperationLogManage'
import UMQueryOperatingLog from '../views/UM/OAM/OperationLogManage/QueryOperationLog'
/*我的消息 */
import UMQueryMyTask from '../views/UM/UW/MyNews/MyTask'
import UMMyNews from '../views/UM/UW/MyNews/MyNews'
import UMExamineApprove from '../views/UM/UW/MyNews/ExamineApprove'
import UMExaminPlans from '../views/UM/UW/MyNews/ExaminePlans'
/*我的申请 */
import UMMyApplication from '../views/UM/UW/MyApplication/MyApplication'
import UMQueryMyApplication from '../views/UM/UW/MyApplication/QueryMyApplication'
import UMApplicationExamineApprove from '../views/UM/UW/MyApplication/ApplicationExamineApprove'
import UMApplicationExaminePlans from '../views/UM/UW/MyApplication/ApplicationExaminePlans'
/*相关单位 */
import UMRelatedUnits from '../views/UM/EM/RelatedUnits/RelatedUnits'
import UMQueryRelatedUnits from '../views/UM/EM/RelatedUnits/QueryRelatedUnits'
import UMAddRelatedUnits from '../views/UM/EM/RelatedUnits/AddRelatedUnits'
/*预案管理*/
import UMPlans from '../views/UM/EM/Plan/Plan'
import DetialPlan from '../views/UM/EM/Plan/DetialPlan'
import ExecutePlan from '../views/UM/EM/Plan/ExecutePlan'
import textPlan from '../views/UM/EM/Plan/TextPlan'
/*灾害管理*/
import UMDisaster from '../views/UM/EM/Disaster/Disaster'
/*巡检任务 */
import UMPatrolTask from '../views/UM/OMM/PatrolTask/PatrolTask'
import UMQueryTask from '../views/UM/OMM/PatrolTask/queryTask'
import UMAddTask from '../views/UM/OMM/PatrolTask/addTask'
import UMDistributeTask from '../views/UM/OMM/PatrolTask/distributeTask'
import UMSubmitPatrolTask from '../views/UM/OMM/PatrolTask/submitPatrolTask'
// 合同管理
import UMContract from '../views/UM/OAM/TunnelContractManage/UMTunnelContractManage'
import UMListTunnelContract from '../views/UM/OAM/TunnelContractManage/ListTunnelContract'
import UMAddTunnelContract from '../views/UM/OAM/TunnelContractManage/AddTunnelContract'
/**检修管理 */
import UMOverhaulManage from '../views/UM/OMM/OverhaulManage/OverhaulManage'
import UMQueryOverhaul from '../views/UM/OMM/OverhaulManage/queryOverhaul'
import UMAddOverhaul from '../views/UM/OMM/OverhaulManage/addOverhaul'
import UMDetailsOverhaul from '../views/UM/OMM/OverhaulManage/detailsOverhaul'
/*管线本体监控*/
import UMPipelineSupervise from '../views/UM/MAM/PipelineSupervise/PipelineSupervise'
import UMDetailsPipeSupervise from '../views/UM/MAM/PipelineSupervise/DetailsPipeSupervise'
import UMListPipeSupervise from '../views/UM/MAM/PipelineSupervise/ListPipeSupervise'
/*我的任务 */
import UMMyTasks from '../views/UM/UW/MyTasks/myTasks.vue'
import UMQueryMyTaskList from '../views/UM/UW/MyTasks/queryTasks'
/* 管廊基本资料 */
import UMTunnelDocument from '../views/UM/DM/TunnelDocuments/TunnelDocuments'
import UMListTunnelDocuments from '../views/UM/DM/TunnelDocuments/ListTunnelDocuments'
import UMAddTunnelDocument from '../views/UM/DM/TunnelDocuments/AddTunnelDocument'
/* 其他资料 */
// import UMOtherDocument from '../views/UM/DM/OtherDocuments/OtherDocuments'
// import UMListOtherDocuments from '../views/UM/DM/OtherDocuments/ListOtherDocuments'
// import UMAddOtherDocument from '../views/UM/DM/OtherDocuments/AddOtherDocument'
//人员定位
import UMPersonnelPosition from '../views/UM/MAM/PersonnelPosition/PersonnelPositionSystem'
import UMDetailsPersonnelPosition from '../views/UM/MAM/PersonnelPosition/PersonnelPositionDetails'
import UMEquipmentDistribution from '../views/UM/MAM/PersonnelPosition/EquipmentDistribution'
//虚拟巡检
import UMVirtualInspect from '../views/UM/OMM/VirtualInspect/virtualInspect'
import UMVirtualInspectEdit from '../views/UM/OMM/VirtualInspect/VirtualInspectEdit'

// 数据分析
import DataAnalysis from '../views/UM/MAM/DataAnalysis/DataAnalysis'
import DataCountChart from '../views/UM/MAM/DataAnalysis/DataChart/DataCountChart'
import DataPatrolTask from '../views/UM/MAM/DataAnalysis/DataChart/patrolTaskDataChart'
import EnergyConsumptionReport from '../views/UM/MAM/DataAnalysis/DataChart/energyConsumptionReport'
import QueryAlarmData from '../views/UM/MAM/DataAnalysis/QueryAlarmData'
import QueryData from '../views/UM/MAM/DataAnalysis/QueryData'
import DataCompare from '../views/UM/MAM/DataAnalysis/DataCompare'

// 管廊环境监测
import UMListTunnelEnvironment from '../views/UM/MAM/TunnelEnvironment/ListTunnelEnvironment'
import UMTunnelEnvironment from '../views/UM/MAM/TunnelEnvironment/TunnelEnvironment'
import UMDetailsTunnelEnvironment from '../views/UM/MAM/TunnelEnvironment/DetailsTunnelEnvironment'

// 管廊安防监测
import UMListTunnelSafety from '../views/UM/MAM/TunnelSafety/ListTunnelSafety'
import UMTunnelSafety from '../views/UM/MAM/TunnelSafety/TunnelSafety'
import UMDetailsTunnelSafety from '../views/UM/MAM/TunnelSafety/DetailsTunnelSafety'



//CM后台管理系统
import CMMain from '../views/CM/Main/CMMain.vue'
import UserInfoManage from '../views/CM/User/UserInfoManage'
import BarnManage from '../views/CM/Store/BarnManage'
import PipeManage from '../views/CM/Tunnel/PipeManage'
import MaxViewConfig from '../views/CM/Config/MaxViewConfig'
import ScheduleJob from '../views/CM/ScheduleJob/ScheduleJob'
import StoreType from '../views/CM/StoreTypeControl/StoreTypeControl'
import SectionControl from '../views/CM/SectionControl/SectionControl'
import AreaController from '../views/CM/AreaControl/AreaController'
import StaffControl from '../views/CM/Staff/StaffControl'
import EmPlanControl from '../views/CM/EmPlanControl/EmPlanControl'
import QueryVideoService from '../views/CM/Video/QueryVideoService'
import QueryVideo from '../views/CM/Video/QueryVideo'
import MeasObj from '../views/CM/MeasObj/MeasObj'
let routes = [{
  path: '/UMLogin',
  component: UMLogin,
  name: '综合监控登录',
}, {
  path: '/VMLogin',
  component: VMLogin,
  name: '可视化登录',
}, {
  path: '/CMLogin',
  component: CMLogin,
  name: '后台管理登录',
}, {
  path: '/CMMain',
  component: CMMain,
  name: '后台管理主页',
  children: [{
    path: 'user',
    component: UserInfoManage,
    name: '用户信息管理'
  }, {
    path: 'store',
    component: BarnManage,
    name: '管仓管理'
  }, {
    path: 'storeType',
    component: StoreType,
    name: '管仓类型管理'
  }, {
    path: 'section',
    component: SectionControl,
    name: '区段管理'
  }, {
    path: 'pipe',
    component: PipeManage,
    name: '管廊管理'
  }, {
    path: 'config',
    component: MaxViewConfig,
    name: '配置管理'
  }, {
    path: 'schedulejob',
    component: ScheduleJob,
    name: '定时任务调度管理'
  }, {
    path: 'area',
    component: AreaController,
    name: '区域管理'
  }, {
    path: 'staff',
    component: StaffControl,
    name: '员工管理'
  }, {
    path: 'EmPlan',
    component: EmPlanControl,
    name: '应急管理'
  }, {
    path: 'queryVideoService',
    component: QueryVideoService,
    name: '视频服务查询'
  }, {
    path: 'queryVideo',
    component: QueryVideo,
    name: '视频查询'
  }, {
    path: 'MeasObj',
    component: MeasObj,
    name: '监测对象'
  }],
}, {
  path: '/404',
  component: NotFound,
  name: '',
  hidden: true
}, {
  path: '/UMMain',
  component: UMMain,
  meta: {
    auth: true
  }, //需要权限
  name: '主页'
}, {
  path: '/VMMain',
  component: VMMain,
  meta: {
    auth: true
  }, //需要权限
}, {
  path: '/UM',
  component: UMPage,
  name: 'MaxTunnel',
  meta: {
    auth: true
  },
  children: [{
      path: 'Patrol',
      component: Patrol,
      name: '巡检管理',
      children: [{
        path: 'query/:id',
        name: 'UMQueryPlan',
        component: UMQueryPlan,
      }, {
        path: 'add',
        component: UMAddPlan,
        name: 'UMAddPlan'
      }, {
        path: 'edit',
        component: UMEditPlan,
        name: 'UMEditPlan'
      }, {
        path: 'details/:id',
        component: UMPatrolDetails,
        name: 'UMPatrolDetails'
      }, {
        path: 'homePage',
        component: UMPatrolHomePage,
        name: 'UMPatrolHomePage',
        meta: {
          keepAlive: true //需要被缓存
        }
      }]
    }, {
      path: 'patrolTask',
      component: UMPatrolTask,
      name: '巡检任务',
      children: [{
        path: 'query',
        component: UMQueryTask,
        name: 'UMQueryTask'
      }, {
        path: 'query/:id',
        component: UMQueryTask,
        name: 'UMQueryTask'
      }, {
        path: 'add',
        component: UMAddTask,
        name: 'UMAddTask'
      }, {
        path: 'add/:id',
        component: UMAddTask,
        name: 'UMAddTask'
      }, {
        path: 'distribute',
        component: UMDistributeTask,
        name: 'UMDistributeTask'
      }]
    }, {
      path: 'equipment',
      name: '设备管理',
      component: UMEquipmentMain,
      children: [{
        path: 'homePage',
        component: UMEquipmentHomePage,
        name: '设备管理主页',
        meta: {
          keepAlive: true //需要被缓存
        }
      }, {
        path: 'queryequipment',
        component: UMQueryEquipment,
        name: '管廊设备'
      }, {
        path: 'querybackup',
        component: UMQueryBackup,
        name: '备品备件'
      }, {
        path: 'querytool',
        component: UMQueryTool,
        name: '仪表工具'
      }, {
        path: 'details/:id',
        component: UMDetailEquipment,
        name: 'UMDetailEquipment',
        meta: {
          keepAlive: true //需要被缓存
        }
      }, {
        path: 'add',
        component: UMAddEquipment,
        name: '添加设备'
      }, {
        path: 'toolHistoryCount',
        component: ToolHistoryCount,
        name: '仪表工具历史统计'
      }, {
        path: 'backupHistoryCount',
        component: BackupHistoryCount,
        name: '备品备件历史统计'
      }, {
        path: 'tunnelHistoryCount',
        component: TunnelHistoryCount,
        name: '管廊设备历史统计'
      },{
        path: 'addBackUp',
        component: UMAddBackUp,
        name: '添加备品'
      }]
    }, {
      path: 'defect',
      name: '缺陷管理',
      component: UMDefectMain,
      children: [{
        path: 'query',
        name: 'UMQueryDefect',
        component: UMDefectQuery
      }, {
        path: 'query/:id',
        name: 'UMQueryDefect',
        component: UMDefectQuery
      }, {
        path: 'detail',
        name: 'UMDetailDefect',
        component: UMDefectDetail
      }, {
        path: 'distribute/:id',
        name: 'UMDistributeDefect',
        component: UMDistributeDefect
      }, {
        path: 'details/:id',
        component: UMAddOverhaul,
        name: 'DefectDetailsOverhaul'
      }]
    }, {
      path: 'overhaul',
      name: '检修管理',
      component: UMOverhaulManage,
      children: [{
        path: 'query',
        component: UMQueryOverhaul,
        name: 'UMQueryOverhaul'
      }, {
        path: 'query/:id',
        component: UMQueryOverhaul,
        name: 'UMQueryOverhaul'
      }, {
        path: 'add',
        component: UMAddOverhaul,
        name: 'UMAddOverhaul'
      }, {
        path: 'details/:id',
        component: UMAddOverhaul,
        name: 'UMDetailsOverhaul'
      }]
    }, {
      path: 'TunnelEnergy',
      name: '管廊能耗管理',
      component: TunnelEnergy,
      children: [{
        path: 'homePage',
        component: TunnelEnergyHomePage,
        name: '管廊能耗主页'
      }, {
        path: 'detials/:id',
        component: TunnelEnergyDetial,
        name: '管廊能耗详情'
      }, {
        path: 'categories/:id',
        component: TunnelEnergyCatetory,
        name: '能耗类别详情'
      }, ]
    },
    // {
    //   path: 'UMEnterGalleryApplication',
    //   name: '管廊用户管理',
    //   component: UMEnterGalleryApplication,
    //   // children: [
    //   //   { path: 'user', component: UMTunnelInUser ,name: '管廊内用户'},
    //   //   { path: 'query',component: UMTunnelUserQuery, name: '管廊用户列表'},
    //   //   { path: 'details/:id', component: UMTunnelUserDetails, name: '管廊用户详情'}
    //   // ]
    // },
    {
      path: 'tunnelCustomer',
      name: '管廊客户管理',
      component: UMTunnelCustomerManage,
      children: [{
        path: 'list',
        component: UMListTunnelCustomer,
        name: '管廊客户列表'
      }, {
        path: 'add',
        component: UMAddTunnelCustomer,
        name: '添加管廊客户'
      }]
    },
    /*fan*/
    {
      path: 'enterGalleryApplication',
      name: '入廊申请管理',
      component: UMEnterGalleryApplication,
      children: [{
        path: 'query/:id',
        component: UMQueryEnterGalleryApplication,
        name: '查询入廊申请记录'
      }, {
        path: 'query',
        component: UMQueryEnterGalleryApplication,
        name: '查询入廊申请记录'
      }, {
        path: 'add',
        component: UMAddEnterGalleryApplication,
        name: '添加入廊申请'
      }, ]
    }, {
      path: 'operationLog',
      name: '操作日志管理',
      component: UMOperatingLogManage,
      children: [{
        path: 'query',
        component: UMQueryOperatingLog,
        name: '查询操作日志'
      }]
    }, {
      path: 'TunnelSupervise',
      name: '管廊本体监控',
      component: UMTunnelSupervise,
      children: [{
        path: 'details/:id',
        component: UMDetailsTunnelSupervise,
        name: '管廊本体监控详情'
      }, {
        path: 'list/:id',
        component: UMListTunnelSupervise,
        name: '管廊本体监控列表'
      }]
    }, {
      path: 'TunnelEnvironment',
      name: '管廊环境监控',
      component: UMTunnelEnvironment,
      children: [{
        path: 'list/:id',
        component: UMListTunnelEnvironment,
        name: '管廊环境监控列表',
        meta: {
          keepAlive: true //需要被缓存
        }
      }, {
        path: 'details/:id',
        component: UMDetailsTunnelEnvironment,
        name: '管廊环境监控详情',
        meta: {
          keepAlive: true //需要被缓存
        }
      }]
    }, {
      path: 'TunnelSafety',
      name: '管廊安防监控',
      component: UMTunnelSafety,
      children: [{
        path: 'list/:id',
        component: UMListTunnelSafety,
        name: '管廊安防监控列表',
        meta: {
          keepAlive: true //需要被缓存
        }
      }, {
        path: 'details/:id',
        component: UMDetailsTunnelSafety,
        name: '管廊安防监控详情',
        meta: {
          keepAlive: true //需要被缓存
        }
      }]
    }, {
      path: 'PipelineSupervise',
      name: '管线本体监控',
      component: UMPipelineSupervise,
      children: [{
        path: 'electrivity',
        component: UMDetailsPipeSupervise,
        name: '管线电缆监控详情'
      }, {
        path: 'light',
        component: UMListPipeSupervise,
        name: '管线光缆监控详情'
      }, {
        path: 'water',
        component: UMListPipeSupervise,
        name: '管线水管监控详情'
      }]
    }, {
      path: 'RobotMonitoring',
      name: '机器人监控系统',
      component: UMRobotMonitoringSystem,
      children: [{
        path: 'query',
        component: UMRobotQuery,
        name: '机器人监控系统总览'
      }, {
        path: 'details',
        component: UMRobotDetails,
        name: '机器人监控系统详情'
      }]

    }, {
      path: 'VideoMonitoring',
      name: '视频监控系统',
      component: UMVideoMonitoringSystem,
      children: [{
        path: 'details/:id',
        component: UMVideoMonitoringDetails,
        name: '视频详情'
      }, {
        path: 'history',
        component: UMVideoMonitoringHistory,
        name: '历史详情'
      }, {
        path: 'positionSetting',
        component: UMVideoPositionSetting,
        name: '预置位'
      }]
    }, {
      path: 'PersonnelPosition',
      name: '人员定位',
      component: UMPersonnelPosition,
      children: [{
        // path: 'tunnel/:id',
        path: 'tunnel',
        component: UMDetailsPersonnelPosition,
        name: '人员定位详情',
        meta: {
          keepAlive: true //需要被缓存
        }
      }, {
        path: 'equipmentDistribution',
        name: '设备分配',
        component: UMEquipmentDistribution
      }]
    }, {
      path: 'OperatingSpace',
      name: '管廊空间管理',
      component: UMOperatingSpace,
      children: [{
        path: 'tunnel/:id',
        component: UMOperatingSpaceTunnel,
        name: '管廊空间管理'
      }]
    }, {
      path: 'virtualInspect',
      name: '虚拟巡检',
      component: UMVirtualInspect,
      meta: {
        keepAlive: true //需要被缓存
      }
    }, {
      path: 'virtualInspectEdit',
      name: '编辑虚拟巡检',
      component: UMVirtualInspectEdit,
    }, {
      path: 'myNews',
      name: '我的消息',
      component: UMMyNews,
      children: [{
          path: 'queryMyTask',
          component: UMQueryMyTask,
          name: '我的待办'
        }, {
          path: 'examineApprove/:processInstanceId/:processType',
          component: UMExamineApprove,
          name: 'UMExamineApprove'
        }, {
          path: 'examinePlans/:processInstanceId',
          component: UMExaminPlans,
          name: 'examinPlans'
        }, {
          path: 'submitPatralTask/:id',
          component: UMSubmitPatrolTask,
          name: 'submitPatralTask'
        },
        // {
        //   path: 'distributeTask/:id',
        //   component: UMDistributeTask,
        //   name: 'distributeTask'
        // },
        //未完结工单
        // { path: 'submit/:id',component: UMAddOverhaul, name: 'UMAddOverhaul'},
        //完结工单
        {
          path: 'details/:id',
          component: UMAddOverhaul,
          name: 'UWDetailsOverhaul'
        }, {
          //确认出廊
          path: 'applicationApprove/:processInstanceId/:processType',
          component: UMApplicationExamineApprove,
          name: 'newsExamineApprove'
        }, {
          path: 'distribute/:id',
          component: UMDistributeDefect,
          name: 'newsDistributeDefect',
        },
      ]
    }, {
      path: 'myApplication',
      name: '我的申请',
      component: UMMyApplication,
      children: [{
        path: 'query',
        component: UMQueryMyApplication,
        name: '查看我的申请'
      }, {
        path: 'applicationApprove/:processInstanceId/:processType',
        component: UMApplicationExamineApprove,
        name: 'UMApplicationExamineApprove'
      }, {
        path: 'applicationPlans/:processInstanceId',
        component: UMApplicationExaminePlans,
        name: 'UMApplicationExaminePlans'
      }]
    }, {
      path: 'myTasks',
      name: '我的任务',
      component: UMMyTasks,
      children: [{
          path: 'query',
          component: UMQueryMyTaskList,
          name: 'UMQueryMyTaskList'
        }, {
          path: 'queryMyApprove/:processInstanceId',
          component: UMApplicationExamineApprove,
          name: 'queryMyApprove'
        }, {
          //工单详情 未完结
          path: 'details/:id',
          component: UMAddOverhaul,
          name: 'TaskDetailsOverhaulFalse'
        }, {
          ////工单详情 已完结
          path: 'details/:id',
          component: UMAddOverhaul,
          name: 'TaskDetailsOverhaulTrue'
        }, {
          path: 'examinePlans/:processInstanceId',
          component: UMExaminPlans,
          name: 'TaskExaminPlans'
        },
        //入廊申请审批中
        {
          path: 'examineApprove/:processInstanceId/:processType',
          component: UMExamineApprove,
          name: 'taskExamineApprove'
        },
        //提交巡检任务结果、
        {
          path: 'submitPatralTask/:id',
          component: UMSubmitPatrolTask,
          name: 'taskSubmitPatralTask'
        },
        //分配巡检任务
        {
          path: 'distribute/:id',
          name: 'taskDistributeDefect',
          component: UMDistributeDefect
        }
      ]
    }, {
      path: 'relatedUnits',
      name: '相关单位',
      component: UMRelatedUnits,
      children: [{
        path: 'query',
        component: UMQueryRelatedUnits,
        name: '查看相关单位'
      }, {
        path: 'add',
        component: UMAddRelatedUnits,
        name: 'UMAddRelatedUnits'
      }, {
        path: 'add/:id',
        component: UMAddRelatedUnits,
        name: 'UMAddRelatedUnits'
      }, ]
    }, {
      path: 'plans',
      name: '预案管理',
      component: UMPlans,
      children: [{
        path: 'detial/:processKey',
        component: DetialPlan,
        name: '预案详请'
      }, {
        path: 'execute/:processKey',
        component: ExecutePlan,
        name: '执行预案'
      }, {
        path: 'textPage/:processKey',
        component: textPlan,
        name: '测试页面'
      }]
    }, {
      path: 'disaster',
      name: '灾害管理',
      component: UMDisaster,
      children: [{
        path: 'query',
        component: null,
        name: '灾害管理'
      }]
    }, {
      path: 'tunnelContract',
      name: '合同管理',
      component: UMContract,
      children: [{
        path: 'list',
        component: UMListTunnelContract,
        name: 'UMQueryContract'
      }, {
        path: 'add',
        component: UMAddTunnelContract,
        name: 'UMAddContract'
      }, {
        path: 'edit',
        component: UMAddTunnelContract,
        name: 'UMEditContract'
      }, {
        path: 'detail',
        component: UMAddTunnelContract,
        name: 'UMDetailContract'
      }]
    }, {
      path: 'tunnelDocuments',
      name: '管廊基本资料',
      component: UMTunnelDocument,
      children: [{
        path: 'list',
        component: UMListTunnelDocuments,
        name: 'UMTunnelDocumentsList'
      }, {
        path: 'add',
        component: UMAddTunnelDocument,
        name: 'UMTunnelDocumentAdd'
      }]
    }, {
      path: 'DataAnalysis',
      name: '数据分析',
      component: DataAnalysis,
      children: [{
        path: "QueryAlarmData",
        name: "告警查询",
        component: QueryAlarmData
      }, {
        path: "DataCompare",
        name: "同比数据分析",
        component: DataCompare
      }, {
        path: "QueryData",
        name: "数据查询",
        component: QueryData
      }, {
        path: "CountChart",
        name: "统计报表",
        component: DataCountChart
      }, {
        path: "energyConsumptionReport",
        name: "能耗报表",
        component: EnergyConsumptionReport
      }, {
        path: 'taskChart',
        name: '巡检报表',
        component: DataPatrolTask
      }],
    }
  ],
}, {
  path: '*',
  hidden: true,
  redirect: {
    path: '/404'
  }
}, {
  path: '/',
  redirect: {
    path: '/UMMain'
  }
}];
export default routes;