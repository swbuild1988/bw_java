import Vue from 'vue';
import Router from 'vue-router';
Vue.use(Router);
export const routes = [{
        path: '/UMLogin',
        component: (resolve) => require(['@/views/UM/UMLogin'], resolve),
        name: '综合监控登录',
    },
    {
        path: '/VMLogin',
        component: (resolve) => require(['@/views/VM2/VMLogin'], resolve),
        name: '可视化登录',
    },
    {
        path: '/CMLogin',
        component: (resolve) => require(['@/views/CM/CMLogin'], resolve),
        name: '后台管理登录',
    },
    {
        path: '/refresh',
        component: (resolve) => require(['@/views/Common/refresh'], resolve),
        name: '刷新页面',
    },
    {
        path: '/404',
        component: (resolve) => require(['@/views/Common/404'], resolve),
        name: '丢失页面',
        hidden: true,
    },
    {
        path: '/',
        redirect: {
            path: '/UMLogin',
        },
    },
];

// 异步加载路由表
export const asyncRouterMap = [{
        path: '/VMMain2',
        name: '可视化主页',
        roleName: 'admin',
        component: (resolve) => require(['@/views/VM2/VMMain'], resolve),
        meta: {
            title: 'permission',
            keepAlive: true, // 需要被缓存
            roles: ['admin', 'editor'],
        },
    },
    {
        path: '/UM',
        component: (resolve) => require(['@/views/UM/UMPage'], resolve),
        name: '综合管廊模块',
        meta: {
            title: 'permission',
            roles: ['admin', 'editor'],
        },
        children: [{
                path: 'Patrol',
                component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/PatrolScheme'], resolve),
                name: '巡检管理',
                meta: {
                    title: 'permission',
                    roles: ['admin', 'editor'],
                },
                children: [{
                    path: 'query/:id',
                    name: '查询巡检计划',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/QueryPlan'], resolve),
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/AddPlan'], resolve),
                    name: '添加巡检计划',
                }, {
                    path: 'edit',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/EditPlan'], resolve),
                    name: '编辑巡检计划',
                }, {
                    path: 'details/:id',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/PatrolDetails'], resolve),
                    name: '查看巡检计划详情',
                }, {
                    path: 'homePage',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/PatrolHomePage'], resolve),
                    name: '巡检计划总览',
                }],
            },
            {
                path: 'planPatrol',
                component: (resolve) => require(['@/views/UM/OMM/PlanPatrol/PlanPatrol'], resolve),
                name: '计划与任务',
                meta: {
                    title: 'permission',
                    roles: ['admin', 'editor'],
                },
                children: [{
                    path: 'queryAnnualPlan',
                    component: (resolve) => require(['@/views/UM/OMM/PlanPatrol/annualPlan'], resolve),
                    name: '查询年度计划',
                }, {
                    path: 'queryMonthPlan',
                    component: (resolve) => require(['@/views/UM/OMM/PlanPatrol/monthPlan'], resolve),
                    name: '查询本月计划',
                }, {
                    path: 'patrolDetails',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolScheme/PatrolDetails'], resolve),
                    name: '计划详情',
                }],
            },
            {
                path: 'patrolTask',
                component: (resolve) => require(['@/views/UM/OMM/PatrolTask/PatrolTask'], resolve),
                name: '巡检任务',
                children: [{
                    path: 'query',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolTask/queryTask'], resolve),
                    name: '巡检任务总览',
                }, {
                    path: 'query/:id',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolTask/queryTask'], resolve),
                    name: '查询巡检任务',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolTask/addTask'], resolve),
                    name: '添加巡检任务',
                }, {
                    path: 'add/:id',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolTask/addTask'], resolve),
                    name: '编辑巡检任务',
                }, {
                    path: 'distribute',
                    component: (resolve) => require(['@/views/UM/OMM/PatrolTask/distributeTask'], resolve),
                    name: '分配巡检任务',
                }],
            },
            {
                path: 'equipment',
                name: '设备管理',
                component: (resolve) => require(['@/views/UM/OMM/UMEquipment/Equipment'], resolve),
                children: [{
                    path: 'homePage',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/EquipmentHomePage'], resolve),
                    name: '设备管理主页',
                    meta: {
                        keepAlive: true, // 需要被缓存
                    },
                }, {
                    path: 'queryequipment',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/QueryEquipment'], resolve),
                    name: '管廊设备',
                }, {
                    path: 'querybackup',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/QueryBackup'], resolve),
                }, {
                    path: 'querytool',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/QueryTool'], resolve),
                    name: '仪表工具',
                }, {
                    path: 'details/:id',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/DetailEquipment'], resolve),
                    name: '设备详情',
                    meta: {
                        keepAlive: true, // 需要被缓存
                    },
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/AddEquipment'], resolve),
                    name: '添加设备',
                }, {
                    path: 'toolHistoryCount',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/toolHistoryCount'], resolve),
                    name: '仪表工具历史统计',
                }, {
                    path: 'backupHistoryCount',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/BackupHistoryCount'], resolve),
                    name: '备品备件历史统计',
                }, {
                    path: 'tunnelHistoryCount',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/TunnelHistoryCount'], resolve),
                    name: '管廊设备历史统计',
                }, {
                    path: 'addBackUp',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/AddBackUp'], resolve),
                    name: '添加备品',
                }, {
                    path: 'addTools',
                    component: (resolve) => require(['@/views/UM/OMM/UMEquipment/AddTools'], resolve),
                    name: '添加仪表工具',
                }],
            },
            {
                path: 'defect',
                name: '缺陷管理',
                component: (resolve) => require(['@/views/UM/OMM/Defect/DefectMain'], resolve),
                children: [{
                        path: 'query',
                        name: '缺陷总览',
                        component: (resolve) => require(['@/views/UM/OMM/Defect/DefectQuery'], resolve),
                    },
                    {
                        path: 'query/:id',
                        name: '查询缺陷',
                        component: (resolve) => require(['@/views/UM/OMM/Defect/DefectQuery'], resolve),
                    },
                    {
                        path: 'detail',
                        name: '缺陷详情',
                        component: (resolve) => require(['@/views/UM/OMM/Defect/DefectDetail'], resolve),
                    }, {
                        path: 'distribute/:id',
                        name: '分配缺陷',
                        component: (resolve) => require(['@/views/UM/OMM/Defect/distributeDefect'], resolve),
                    }, {
                        path: 'details/:id',
                        component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/addOverhaul'], resolve),
                        name: '维修工单详情',
                        meta: {
                            keepAlive: true, // 需要被缓存
                        },
                    },
                ],
            },
            {
                path: 'overhaul',
                name: '检修管理',
                component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/OverhaulManage'], resolve),
                children: [{
                    path: 'query',
                    component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/queryOverhaul'], resolve),
                    name: '检修总览',
                }, {
                    path: 'query/:id',
                    component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/queryOverhaul'], resolve),
                    name: '查询检修',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/addOverhaul'], resolve),
                    name: '添加检修',
                }, {
                    path: 'details/:id',
                    component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/addOverhaul'], resolve),
                    name: '检修详情',
                }],
            },
            {
                path: 'TunnelEnergy',
                name: '管廊能耗管理',
                component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/EnergyConsumption'], resolve),
                children: [{
                    path: 'homePage',
                    component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/TunnelEnergyHomePage'], resolve),
                    name: '管廊能耗主页',
                }, {
                    path: 'detials/:id',
                    component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionDetail'], resolve),
                    name: '管廊能耗详情',
                }, {
                    path: 'EnergyConsumptionDetailLevel1/:id',
                    component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionDetailLevel1'], resolve),
                    name: '管廊能耗详情层级1',
                }, {
                    path: 'EnergyConsumptionDetailLevel2/:id',
                    component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionDetailLevel2'], resolve),
                    name: '管廊能耗详情层级2',
                }, {
                    path: 'EnergyConsumptionDetailLevel3/:id',
                    component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionDetailLevel3'], resolve),
                    name: '管廊能耗详情层级3',
                }, {
                    path: 'categories/:id',
                    component: (resolve) => require(['@/views/UM/OAM/EnergyConsumptionManage/EnergyConsumptionCategory'], resolve),
                    name: '能耗类别详情',
                }],
            },
            {
                path: 'tunnelCustomer',
                name: '管廊客户管理',
                component: (resolve) => require(['@/views/UM/OAM/TunnelCustomerManage/TunnelCustomerManage'], resolve),
                children: [{
                    path: 'list',
                    component: (resolve) => require(['@/views/UM/OAM/TunnelCustomerManage/ListTunnelCustomer'], resolve),
                    name: '管廊客户列表',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OAM/TunnelCustomerManage/AddTunnelCustomer'], resolve),
                    name: '添加管廊客户',
                }],
            },
            {
                path: 'enterGalleryApplication',
                name: '入廊申请管理',
                component: (resolve) => require(['@/views/UM/OAM/EnterGalleryApplication/EnterGalleryApplication'], resolve),
                children: [{
                    path: 'query/:id',
                    component: (resolve) => require(['@/views/UM/OAM/EnterGalleryApplication/QueryEnterGalleryApplication'], resolve),
                    name: '查询入廊申请记录',
                }, {
                    path: 'query',
                    component: (resolve) => require(['@/views/UM/OAM/EnterGalleryApplication/QueryEnterGalleryApplication'], resolve),
                    name: '查询入廊申请记录',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OAM/EnterGalleryApplication/AddEnterGalleryApplication'], resolve),
                    name: '添加入廊申请',
                }],
            },
            {
                path: 'operationLog',
                name: '操作日志管理',
                component: (resolve) => require(['@/views/UM/OAM/OperationLogManage/OperationLogManage'], resolve),
                children: [{
                    path: 'query',
                    component: (resolve) => require(['@/views/UM/OAM/OperationLogManage/QueryOperationLog'], resolve),
                    name: '查询操作日志',
                }],
            },
            // {
            //     path: 'TunnelSupervise',
            //     name: '管廊本体监控',
            //     component: (resolve) => require(['@/views/UM/MAM/TunnelSupervise/TunnelSupervise'], resolve),
            //     children: [{
            //         path: 'details/:id',
            //         component: (resolve) => require(['@/views/UM/MAM/TunnelSupervise/DetailsTunnelSupervise'], resolve),
            //         name: '管廊本体监控详情',
            //     }, {
            //         path: 'list/:id',
            //         component: (resolve) => require(['@/views/UM/MAM/TunnelSupervise/ListTunnelSupervise'], resolve),
            //         name: '管廊本体监控列表',
            //     }],
            // },
            {
                path: 'TunnelEnvironment',
                name: '管廊环境监控',
                component: (resolve) => require(['@/views/UM/MAM/TunnelEnvironment/TunnelEnvironment'], resolve),
                children: [{
                    path: 'list/:id',
                    component: (resolve) => require(['@/views/UM/MAM/TunnelEnvironment/ListTunnelEnvironment'], resolve),
                    name: '管廊环境监控列表',
                    meta: {
                        keepAlive: true, // 需要被缓存
                    },
                }, {
                    path: 'details/:id',
                    component: (resolve) => require(['@/views/UM/MAM/TunnelEnvironment/DetailsTunnelEnvironment'], resolve),
                    name: '管廊环境监控详情',
                    meta: {
                        keepAlive: true, // 需要被缓存
                    },
                }],
            },
            {
                path: 'TunnelSafety',
                name: '管廊安防监控',
                component: (resolve) => require(['@/views/UM/MAM/TunnelSafety/TunnelSafety'], resolve),
                children: [{
                    path: 'list/:id',
                    component: (resolve) => require(['@/views/UM/MAM/TunnelSafety/ListTunnelSafety'], resolve),
                    name: '管廊安防监控列表',
                    meta: {
                        keepAlive: true, // 需要被缓存
                    },
                }, {
                    path: 'details/:id',
                    component: (resolve) => require(['@/views/UM/MAM/TunnelSafety/DetailsTunnelSafety'], resolve),
                    name: '管廊安防监控详情',
                    meta: {
                        keepAlive: true, // 需要被缓存
                    },
                }],
            },
            // {
            //     path: 'PipelineSupervise',
            //     name: '管线本体监控',
            //     component: (resolve) => require(['@/views/UM/MAM/PipelineSupervise/PipelineSupervise'], resolve),
            //     children: [{
            //         path: 'electrivity',
            //         component: (resolve) => require(['@/views/UM/MAM/PipelineSupervise/DetailsPipeSupervise'], resolve),
            //         name: '管线电缆监控详情',
            //     }, {
            //         path: 'light',
            //         component: (resolve) => require(['@/views/UM/MAM/PipelineSupervise/ListPipeSupervise'], resolve),
            //         name: '管线光缆监控详情',
            //     }, {
            //         path: 'water',
            //         component: (resolve) => require(['@/views/UM/MAM/PipelineSupervise/ListPipeSupervise'], resolve),
            //         name: '管线水管监控详情',
            //     }],
            // },
            // {
            //     path: 'RobotMonitoring',
            //     name: '机器人监控系统',
            //     component: (resolve) => require(['@/views/UM/MAM/RobotMonitoringSystem/RobotMonitoringSystem'], resolve),
            //     children: [{
            //         path: 'query',
            //         component: (resolve) => require(['@/views/UM/MAM/RobotMonitoringSystem/RobotQuery'], resolve),
            //         name: '机器人监控系统总览',
            //     }, {
            //         path: 'details',
            //         component: (resolve) => require(['@/views/UM/MAM/RobotMonitoringSystem/RobotDetails'], resolve),
            //         name: '机器人监控系统详情',
            //     }],
            // },
            {
                path: 'VideoMonitoring',
                name: '视频监控系统',
                component: (resolve) => require(['@/views/UM/MAM/VideoMonitoringSystem/VideoMonitoringSystem'], resolve),
                children: [{
                    path: 'details/:id',
                    component: (resolve) => require(['@/views/UM/MAM/VideoMonitoringSystem/VideoMonitoringDetails'], resolve),
                    name: '视频详情',
                }, {
                    path: 'history',
                    component: (resolve) => require(['@/views/UM/MAM/VideoMonitoringSystem/VideoMonitoringHistory'], resolve),
                    name: '历史详情',
                }, {
                    path: 'positionSetting',
                    component: (resolve) => require(['@/views/UM/MAM/VideoMonitoringSystem/VideoPositionSetting'], resolve),
                    name: '预置位',
                }, {
                    path: 'historyPhotos',
                    component: (resolve) => require(['@/views/UM/MAM/VideoMonitoringSystem/HistoryPhotos'], resolve),
                    name: '历史照片',
                }],
            },
            {
                path: 'PersonnelPosition',
                name: '人员定位',
                component: (resolve) => require(['@/views/UM/MAM/PersonnelPosition/PersonnelPositionSystem'], resolve),
                children: [{
                    path: 'detail/:userId',
                    component: (resolve) => require(['@/views/UM/MAM/PersonnelPosition/PersonnelPositionDetails'], resolve),
                    name: '人员定位详情',
                    // meta: {
                    //     keepAlive: true //需要被缓存
                    // }
                }, {
                    path: 'equipmentDistribution',
                    name: '设备分配',
                    component: (resolve) => require(['@/views/UM/MAM/PersonnelPosition/EquipmentDistribution'], resolve),
                }, {
                    path: 'visitors',
                    component: (resolve) => require(['@/views/UM/MAM/PersonnelPosition/Visitors'], resolve),
                    name: '人员定位总览',
                }],
            },
            {
                path: 'OperatingSpace',
                name: '管廊空间管理',
                component: (resolve) => require(['@/views/UM/OAM/OperatingSpace/operatingSpace'], resolve),
                children: [{
                    path: 'tunnel/:id',
                    component: (resolve) => require(['@/views/UM/OAM/OperatingSpace/operatingSpaceTunnel'], resolve),
                    name: '管廊空间管理',
                }],
            },
            {
                path: 'virtualInspect',
                name: '虚拟巡检',
                component: (resolve) => require(['@/views/UM/OMM/VirtualInspect/virtualInspect'], resolve),
                meta: {
                    keepAlive: true, // 需要被缓存
                },
            },
            {
                path: 'virtualInspectEdit',
                name: '编辑虚拟巡检',
                component: (resolve) => require(['@/views/UM/OMM/VirtualInspect/VirtualInspectEdit'], resolve),
            },
            {
                path: 'myNews',
                name: '我的消息',
                component: (resolve) => require(['@/views/UM/UW/MyNews/MyNews'], resolve),
                children: [{
                        path: 'queryMyTask',
                        component: (resolve) => require(['@/views/UM/UW/MyNews/MyTask'], resolve),
                        name: '我的待办',
                    }, {
                        path: 'examineApprove/:processInstanceId/:processType',
                        component: (resolve) => require(['@/views/UM/UW/MyNews/ExamineApprove'], resolve),
                        name: '入廊审批',
                    }, {
                        path: 'examinePlans/:processInstanceId',
                        component: (resolve) => require(['@/views/UM/UW/MyNews/ExaminePlans'], resolve),
                        name: '巡检计划审批',
                    }, {
                        path: 'submitPatralTask/:id',
                        component: (resolve) => require(['@/views/UM/OMM/PatrolTask/submitPatrolTask'], resolve),
                        name: '提交巡检任务结果',
                    },
                    {
                        path: 'details/:id',
                        component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/addOverhaul'], resolve),
                        name: '工单详情',
                    }, {
                        // 确认出廊
                        path: 'applicationApprove/:processInstanceId/:processType',
                        component: (resolve) => require(['@/views/UM/UW/MyApplication/ApplicationExamineApprove'], resolve),
                        name: '查看入廊申请进度',
                    }, {
                        path: 'distribute/:id',
                        component: (resolve) => require(['@/views/UM/OMM/Defect/distributeDefect'], resolve),
                        name: '分配巡检任务',
                    },
                ],
            },
            {
                path: 'myApplication',
                name: '我的申请',
                component: (resolve) => require(['@/views/UM/UW/MyApplication/MyApplication'], resolve),
                children: [{
                    path: 'query',
                    component: (resolve) => require(['@/views/UM/UW/MyApplication/QueryMyApplication'], resolve),
                    name: '查看我的申请',
                }, {
                    path: 'applicationApprove/:processInstanceId/:processType',
                    component: (resolve) => require(['@/views/UM/UW/MyApplication/ApplicationExamineApprove'], resolve),
                    name: '查看入廊申请进度',
                }, {
                    path: 'applicationPlans/:processInstanceId',
                    component: (resolve) => require(['@/views/UM/UW/MyApplication/ApplicationExaminePlans'], resolve),
                    name: '查看巡检计划申请进度',
                }],
            },
            {
                path: 'myTasks',
                name: '我的任务',
                component: (resolve) => require(['@/views/UM/UW/MyTasks/myTasks'], resolve),
                children: [{
                        path: 'query',
                        component: (resolve) => require(['@/views/UM/UW/MyTasks/queryTasks'], resolve),
                        name: '任务列表',
                    }, {
                        path: 'queryMyApprove/:processInstanceId',
                        component: (resolve) => require(['@/views/UM/UW/MyApplication/ApplicationExamineApprove'], resolve),
                        name: '入廊申请详情',
                    }, {
                        // 工单详情 未完结
                        path: 'details/:id',
                        component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/addOverhaul'], resolve),
                        name: '未完结工单详情',
                    }, {
                        // //工单详情 已完结
                        path: 'details/:id',
                        component: (resolve) => require(['@/views/UM/OMM/OverhaulManage/addOverhaul'], resolve),
                        name: '已完结工单详情',
                    }, {
                        path: 'examinePlans/:processInstanceId',
                        component: (resolve) => require(['@/views/UM/UW/MyNews/ExaminePlans'], resolve),
                        name: '我的巡检计划详情',
                    },
                    // 入廊申请审批中
                    {
                        path: 'examineApprove/:processInstanceId/:processType',
                        component: (resolve) => require(['@/views/UM/UW/MyNews/ExamineApprove'], resolve),
                        name: '入廊申请审批',
                    },
                    // 提交巡检任务结果、
                    {
                        path: 'submitPatralTask/:id',
                        component: (resolve) => require(['@/views/UM/OMM/PatrolTask/submitPatrolTask'], resolve),
                        name: '提交巡检任务结果',
                    },
                    // 分配巡检任务
                    {
                        path: 'distribute/:id',
                        name: '分配巡检任务',
                        component: (resolve) => require(['@/views/UM/OMM/Defect/distributeDefect'], resolve),
                    },
                ],
            },
            {
                path: 'relatedUnits',
                name: '相关单位',
                component: (resolve) => require(['@/views/UM/EM/RelatedUnits/RelatedUnits'], resolve),
                children: [{
                    path: 'query',
                    component: (resolve) => require(['@/views/UM/EM/RelatedUnits/QueryRelatedUnits'], resolve),
                    name: '查看相关单位',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/EM/RelatedUnits/AddRelatedUnits'], resolve),
                    name: '添加相关单位',
                }, {
                    path: 'add/:id',
                    component: (resolve) => require(['@/views/UM/EM/RelatedUnits/AddRelatedUnits'], resolve),
                    name: '编辑相关单位',
                }],
            },
            {
                path: 'plans',
                name: '预案管理',
                component: (resolve) => require(['@/views/UM/EM/Plan/Plan'], resolve),
                children: [{
                    path: 'detial/:processKey',
                    component: (resolve) => require(['@/views/UM/EM/Plan/DetialPlan'], resolve),
                    name: '预案详请',
                }, {
                    path: 'execute/:processKey',
                    component: (resolve) => require(['@/views/UM/EM/Plan/ExecutePlan'], resolve),
                    name: '执行预案',
                }],
            },
            {
                path: 'disaster',
                name: '灾害管理',
                component: (resolve) => require(['@/views/UM/EM/Disaster/Disaster'], resolve),
                children: [{
                    path: 'query',
                    component: null,
                    name: '灾害管理',
                }],
            },
            {
                path: 'tunnelContract',
                name: '合同管理',
                component: (resolve) => require(['@/views/UM/OAM/TunnelContractManage/UMTunnelContractManage'], resolve),
                children: [{
                    path: 'list',
                    component: (resolve) => require(['@/views/UM/OAM/TunnelContractManage/ListTunnelContract'], resolve),
                    name: '查询合同',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/OAM/TunnelContractManage/AddTunnelContract'], resolve),
                    name: '添加合同',
                }, {
                    path: 'edit',
                    component: (resolve) => require(['@/views/UM/OAM/TunnelContractManage/AddTunnelContract'], resolve),
                    name: '编辑合同',
                }, {
                    path: 'detail',
                    component: (resolve) => require(['@/views/UM/OAM/TunnelContractManage/AddTunnelContract'], resolve),
                    name: '合同详情',
                }],
            },
            {
                path: 'tunnelDocuments',
                name: '管廊基本资料',
                component: (resolve) => require(['@/views/UM/DM/TunnelDocuments/TunnelDocuments'], resolve),
                children: [{
                    path: 'list',
                    component: (resolve) => require(['@/views/UM/DM/TunnelDocuments/ListTunnelDocuments'], resolve),
                    name: '查询管廊资料',
                }, {
                    path: 'add',
                    component: (resolve) => require(['@/views/UM/DM/TunnelDocuments/AddTunnelDocument'], resolve),
                    name: '添加管廊资料',
                }],
            },
            {
                path: 'personCenter',
                name: '个人中心',
                component: (resolve) => require(['@/views/UM/UW/PersonCenter/personCenter'], resolve),
                children: [{
                    path: 'editPass',
                    component: (resolve) => require(['@/views/UM/UW/PersonCenter/editPassword'], resolve),
                    name: '修改密码',
                }],
            },
            {
                path: 'DataAnalysis',
                name: '数据分析',
                component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataAnalysis'], resolve),
                children: [{
                    path: 'QueryAlarmData',
                    name: '告警查询',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/QueryAlarmData'], resolve),
                }, {
                    path: 'DataCompare',
                    name: '同比数据分析',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataCompare'], resolve),
                }, {
                    path: 'QueryData',
                    name: '数据查询',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/QueryData'], resolve),
                }, {
                    path: 'CountChart',
                    name: '统计报表',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataChart/DataCountChart'], resolve),
                }, {
                    path: 'energyConsumptionReport',
                    name: '能耗报表',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataChart/energyConsumptionReport'], resolve),
                }, {
                    path: 'taskChart',
                    name: '巡检报表',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataChart/patrolDataAnalysis'], resolve),
                }, {
                    path: 'AlarmTop10Chart',
                    name: '告警Top10报表',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataChart/AlarmTop10Chart'], resolve),
                }, {
                    path: 'equipmentData',
                    name: '设备报告',
                    component: (resolve) => require(['@/views/UM/MAM/DataAnalysis/DataChart/EquipmentDataAnalysis'], resolve),
                }],
            },
            // {
            //     path: 'Communication',
            //     name: '通讯系统',
            //     component: (resolve) => require(['@/views/UM/MAM/Communication/CommunicationSystem'], resolve),
            //     children: [{
            //         path: 'diales',
            //         component: (resolve) => require(['@/views/UM/MAM/Communication/DialedCalls'], resolve),
            //         name: 'DialedCalls',
            //     }, {
            //         path: 'answers',
            //         component: (resolve) => require(['@/views/UM/MAM/Communication/AnsweredCalls'], resolve),
            //         name: 'AnsweredCalls',
            //     }, {
            //         path: 'extensions',
            //         component: (resolve) => require(['@/views/UM/MAM/Communication/Extensions'], resolve),
            //         name: 'Extensions',
            //     }],
            // },
        ],
    },
    {
        path: '/CMMain',
        meta: {
            title: 'permission',
            roles: ['admin', 'editor'],
        },
        component: (resolve) => require(['@/views/CM/Main/CMMain'], resolve),
        name: '后台管理主页模块',
        children: [{
            path: 'user',
            component: (resolve) => require(['@/views/CM/User/UserInfoManage'], resolve),
            name: '用户信息管理',
        }, {
            path: 'store',
            component: (resolve) => require(['@/views/CM/Store/BarnManage'], resolve),
            name: '管仓管理',
        }, {
            path: 'storeType',
            component: (resolve) => require(['@/views/CM/StoreTypeControl/StoreTypeControl'], resolve),
            name: '管仓类型管理',
        }, {
            path: 'section',
            component: (resolve) => require(['@/views/CM/SectionControl/SectionControl'], resolve),
            name: '区段管理',
        }, {
            path: 'pipe',
            component: (resolve) => require(['@/views/CM/Tunnel/PipeManage'], resolve),
            name: '管廊管理',
        }, {
            path: 'config',
            component: (resolve) => require(['@/views/CM/Config/MaxViewConfig'], resolve),
            name: '配置管理',
        }, {
            path: 'schedulejob',
            component: (resolve) => require(['@/views/CM/ScheduleJob/ScheduleJob'], resolve),
            name: '定时任务调度管理',
        }, {
            path: 'area',
            component: (resolve) => require(['@/views/CM/AreaControl/AreaController'], resolve),
            name: '区域管理',
        }, {
            path: 'staff',
            component: (resolve) => require(['@/views/CM/Staff/StaffControl'], resolve),
            name: '员工管理',
        }, {
            path: 'EmPlan',
            component: (resolve) => require(['@/views/CM/EmPlanControl/EmPlanControl'], resolve),
            name: '应急管理',
        }, {
            path: 'queryVideoService',
            component: (resolve) => require(['@/views/CM/Video/QueryVideoService'], resolve),
            name: '视频服务查询',
        }, {
            path: 'queryVideo',
            component: (resolve) => require(['@/views/CM/Video/QueryVideo'], resolve),
            name: '视频查询',
        }, {
            path: 'h5StreamConfig',
            component: (resolve) => require(['@/views/CM/Video/H5StreamConfig'], resolve),
            name: '视频配置',
        }, {
            path: 'MeasObj',
            component: (resolve) => require(['@/views/CM/MeasObj/MeasObj'], resolve),
            name: '监测对象配置',
        }, {
            path: 'MeasObjMap',
            component: (resolve) => require(['@/views/CM/MeasObj/MeasObjMap'], resolve),
            name: '监测对象映射',
        }, {
            path: 'permissionConfig',
            component: (resolve) => require(['@/views/CM/Permission/Permissions'], resolve),
            name: '权限管理',
        }, {
            path: 'roleConfig',
            component: (resolve) => require(['@/views/CM/Permission/Roles'], resolve),
            name: '权限管理',
        }, {
            path: 'objPreplan',
            component: (resolve) => require(['@/views/CM/EmPlanControl/ObjPrePlan'], resolve),
            name: '监测对象与应急预案'
        }],
    },
    {
        path: '/UMMain',
        // meta: {
        //   title: 'permission',
        //   roles: ['admin', 'editor'],
        // },
        component: (resolve) => require(['@/views/UM/UMMain'], resolve),
        name: '综合管廊主页',
        meta: {
            title: 'permission',
            keepAlive: true, // 需要被缓存
        },
    },
    {
        path: '*',
        redirect: '/404',
        hidden: true,
    },
];

export default new Router({
    mode: 'history',
    routes: routes,
    base: "/", //服务器地址，不设置时，默认为服务器根目录下
});
