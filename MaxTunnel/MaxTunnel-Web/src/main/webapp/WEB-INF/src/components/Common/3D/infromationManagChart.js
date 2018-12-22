
const alarmPrompt = '提示'; //提示告警
const alarmGeneral = '一般'; //一般告警
const alarmSerious = '严重'; //严重告警
const alarmCrisis = '危机'; //危机告警

export const infromationManagDetails =
    {
        unitsInfromations:
            [
                {key:'name',val:'单位名称'},
                {key:'contact',val:'联系人'},
                {key:'tel',val:'联系电话'},
                {key:'address',val:'单位地址'},
            ],
        flawInfromations:
            [
                {key:'tunnelName',val:'所属管廊'},
                {key:'name',val:'缺陷名称'},
                {key:'typeName',val:'缺陷类型'},
                {key:'levelName',val:'危险等级'},
                {key:'statusName',val:'缺陷状态'},
            ],
        alarmInfromations:
            [
                 {
                     key:'alarmLevel',
                     val:
                         {
                             key:'告警级别',
                             val:value => value == 1 ? alarmPrompt :( value == 2 ? alarmGeneral : ( value == 3 ? alarmSerious : alarmCrisis ))
                         }
                 },
                 {
                     key:'alarmDate',
                     val:
                         {
                             key:'告警时间',
                             val:objValue => new  Date(objValue).format('yyyy年/MM月/dd日 hh:mm')
                         }
                 },
                 {key:'alarmName',val:'告警名称'},
                 {key:'objectName',val:'对象名称'}
            ],
        eventsInfromations:
            [
                {key:'name',val:'事件名称'},
                {key:'levelName',val:'事件级别'},
                {key:'startTime',val:
                        {
                            key:'开始时间',
                            val:objValue => new  Date(objValue).format('yyyy年/MM月/dd日 hh:mm')
                        }
                },
                {key:'endTime',val:
                        {
                            key:'结束时间',
                            val:objValue => new  Date(objValue).format('yyyy年/MM月/dd日 hh:mm')
                        }
                }
            ]
    }