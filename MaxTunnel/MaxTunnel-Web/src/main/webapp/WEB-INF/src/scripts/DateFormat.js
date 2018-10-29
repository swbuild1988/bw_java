Date.prototype.format = function(format) {
  var date = {
    "M+": this.getMonth() + 1,
    "d+": this.getDate(),
    "h+": this.getHours(),
    "m+": this.getMinutes(),
    "s+": this.getSeconds(),
    "q+": Math.floor((this.getMonth() + 3) / 3),
    "S+": this.getMilliseconds()
  };
  if (/(y+)/i.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  for (var k in date) {
    if (new RegExp("(" + k + ")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1
        ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
    }
  }
  return format;
}

/**
 * 时间管理类
 * transTime : 时间转换为时间戳
 * timetrans : 时间戳转为时间
 * getSomeDayDate : 获取n天后的时间
 * comparisonDate : 比较时间，精确到秒
 */
export class DateManage {
    constructor(){

    }
    transTime(time){
        let date=new Date();

        date.setFullYear(time.substring(0,4));
        date.setMonth(time.substring(5,7)-1);
        date.setDate(time.substring(8,10));
        date.setHours(time.substring(11,13));
        date.setMinutes(time.substring(14,16));
        date.setSeconds(time.substring(17,19));

        return Date.parse(date);
    };
    timetrans(date){
        var date = new Date( date / 1000 );//如果date为10位需要乘1000

        let Y = date.getFullYear() + '-';
        let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        let D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
        let h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        let m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        let s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());

        return Y + M + D + h + m + s;
    };
    getSomeDayDate(addDay){
        var dd = new Date();

        dd.setDate(dd.getDate() + addDay);//获取AddDayCount天后的日期
        let Y = dd.getFullYear();
        let M = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
        let D = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
        let h = dd.getHours()<10?"0"+dd.getHours():dd.getHours();
        let m = dd.getMinutes()<10?"0"+dd.getMinutes():dd.getMinutes();
        let s = dd.getDate()<10?"0"+dd.getDate():dd.getDate();

        return Y+"-"+M+"-"+D+' '+h+":"+m+":"+s;
    };
    comparisonDate(addDay,endDate){
        return parseFloat(this.transTime(this.getSomeDayDate(addDay))) > parseFloat(endDate);
    }
}

export function getFormatTime() {
    var date =new  Date().format('yyyy年-MM月-dd日 hh:mm:ss');
    var week = "星期" + "日一二三四五六".charAt(new  Date().getDay());
    var result = date + " " + week ;
    return result;
}
