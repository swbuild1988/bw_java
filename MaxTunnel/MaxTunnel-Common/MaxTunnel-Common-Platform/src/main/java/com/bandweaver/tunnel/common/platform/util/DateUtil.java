package com.bandweaver.tunnel.common.platform.util;



import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bandweaver.tunnel.common.biz.constant.TimeEnum;

public class DateUtil {
	
	
	public static Date getCurrentDate() {
		return new Date();
	}
	
	
    /**
     * 获取当天的开始时间
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当天的结束时间
     * @return
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获取昨天的开始时间
     * @return
     */
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天的结束时间
     * @return
     */
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取明天的开始时间
     * @return
     */
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }
    //获取明天的结束时间
    public static Date getEndDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取本周的开始时间
     * @return
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        return getBeginDayOfWeek(date);
    }
    /**
     * 获取本周的开始时间
     * @return
     */
    public static Date getBeginDayOfWeek(Date date) {
    	if (date == null) {
    		return null;
    	}
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
    	if (dayofweek == 1) {
    		dayofweek += 7;
    	}
    	cal.add(Calendar.DATE, 2 - dayofweek);
    	return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     * @return
     */
    public static Date getEndDayOfWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }
    
    /**获取本周的结束时间 
     * @param date
     * @return   
     * @author shaosen
     * @Date 2018年9月12日
     */
    public static Date getEndDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取本月的开始时间
     * @return
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getDayStartTime(calendar.getTime());
    }
    /**
     * 获取本月的开始时间
     * @return
     */
    public static Date getBeginDayOfMonth(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , 1);
        return getDayStartTime(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getDayEndTime(calendar.getTime());
    }


    /**
     * 获取本月的结束时间
     * @return
     */
    public static Date getEndDayOfMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) , day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取本年的开始时间
     * @return
     */
    public static Date getBeginDayOfYear() {
        return getBeginDayOfYear(new Date());
    }

    /**
     * 获得time所在年的第一天
     * @param time
     * @return
     */
    public static Date getBeginDayOfYear(Date time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本年的结束时间
     * @return
     */
    public static Date getEndDayOfYear() {
        return getEndDayOfYear(new Date());
    }


    /**
     * 获取本年的结束时间
     * @return
     */
    public static Date getEndDayOfYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     * 获取某个日期的开始时间
     * @param d
     * @return
     */
    public static Date getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
//        return new Timestamp(calendar.getTimeInMillis());
        return calendar.getTime();
    }

    /**
     * 获取某个日期的结束时间
     * @param d
     * @return
     */
    public static Date getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if(null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
//        return new Timestamp(calendar.getTimeInMillis());
       return calendar.getTime();
    }

    /**
     * 获取今年是哪一年
     * @return
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * 获取本月是哪一月
     * @return
     */
    public static int getNowMonth() {
        Date date = new Date();
        return getNowMonth(date);
    }
    /**
     * 获取本月是哪一月
     * @return
     */
    public static int getNowMonth(Date date) {
    	GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
    	gc.setTime(date);
    	return gc.get(2) + 1;
    }
    
    /**
     * @Description: 获取当日是第几周
     * @param @param date
     * @param @return   
     * @return int  
     * @throws
     * @author shaosen
     * @date 2018年6月1日
     */
    public static int getNowWeek(Date date) {
    	Calendar ca = Calendar.getInstance();
    	ca.setTime(date);
    	return ca.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 两个日期相减得到的天数
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDiffDays(Date beginDate, Date endDate) {

        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }

        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);

        int days = new Long(diff).intValue();

        return days;
    }
    
    /**
     * @Description: 获取两个日期的时间差
     * @param @param beginDate
     * @param @param endDate   
     * @return long  
     * @throws
     * @author shaosen
     * @date 2018年6月13日
     */
    public static long getDiffHours(Date beginDate, Date endDate) {
    	long between = dateDiff(beginDate, endDate);
    	long day = between / (24 * 60 * 60 * 1000);
    	long hours = (between / (60 * 60 * 1000) - day * 24);
    	return day*24+hours;
    }

    /**
     * 两个日期相减得到的毫秒数
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long dateDiff(Date beginDate, Date endDate) {
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return date2ms - date1ms;
    }

    /**
     * 获取两个日期中的最大日期
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date max(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return beginDate;
        }
        return endDate;
    }

    /**
     * 获取两个日期中的最小日期
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Date min(Date beginDate, Date endDate) {
        if (beginDate == null) {
            return endDate;
        }
        if (endDate == null) {
            return beginDate;
        }
        if (beginDate.after(endDate)) {
            return endDate;
        }
        return beginDate;
    }

    /**
     * 返回某月该季度的第一个月
     * @param date
     * @return
     */
    public static Date getFirstSeasonDate(Date date) {
        final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int sean = SEASON[cal.get(Calendar.MONTH)];
        cal.set(Calendar.MONTH, sean * 3 - 3);
        return cal.getTime();
    }

    /**
     * 返回某个日期下几天的日期
     * @param date
     * @param i
     * @return
     */
    public static Date getNextDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
        return cal.getTime();
    }

    /**
     * 返回某个日期前几天的日期
     * @param date
     * @param i
     * @return
     */
    public static Date getFrontDay(Date date, int i) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
        return cal.getTime();
    }

    /**
     * 获取某年某月到某年某月按天的切片日期集合（间隔天数的日期集合）
     * @param beginYear
     * @param beginMonth
     * @param endYear
     * @param endMonth
     * @param k
     * @return
     */
    public static List getTimeList(int beginYear, int beginMonth, int endYear,
                                   int endMonth, int k) {
        List list = new ArrayList();
        if (beginYear == endYear) {
            for (int j = beginMonth; j <= endMonth; j++) {
                list.add(getTimeList(beginYear, j, k));

            }
        } else {
            {
                for (int j = beginMonth; j < 12; j++) {
                    list.add(getTimeList(beginYear, j, k));
                }

                for (int i = beginYear + 1; i < endYear; i++) {
                    for (int j = 0; j < 12; j++) {
                        list.add(getTimeList(i, j, k));
                    }
                }
                for (int j = 0; j <= endMonth; j++) {
                    list.add(getTimeList(endYear, j, k));
                }
            }
        }
        return list;
    }

    /**
     * 获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
     * @param beginYear
     * @param beginMonth
     * @param k
     * @return
     */
    public static List getTimeList(int beginYear, int beginMonth, int k) {
        List list = new ArrayList();
        Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
        int max = begincal.getActualMaximum(Calendar.DATE);
        for (int i = 1; i < max; i = i + k) {
            list.add(begincal.getTime());
            begincal.add(Calendar.DATE, k);
        }
        begincal = new GregorianCalendar(beginYear, beginMonth, max);
        list.add(begincal.getTime());
        return list;
    }

    /**
     * 将精确到s的时间戳转化为时间
     * @param t
     * @return
     */
    public static Date setTimestamp2Date(long t){
        return new Date(t * 1000);
    }

    /**
     * 将精确到毫秒的时间戳转化为时间
     * @param t
     * @return
     */
    public static Date setMillisTimestamp2Date(long t){
        return new Date(t);
    }

    /**
     * 将时间转化为s的时间戳
     * @param d
     * @return
     */
    public static Long setDate2Timestamp(Date d){
        return d.getTime() / 1000;
    }

    /**
     * 将时间转化为毫秒为单位的时间戳
     * @param d
     * @return
     */
    public static Long setDate2MillisTimestamp(Date d){
        return d.getTime();
    }

    /**
     * string格式转Date yyyy-MM-dd HH:mm:ss
     * @param s
     * @return
     */
    public static Date getString2Date(String s){
    	if(s==null||s.trim().length()<0) {
    		return null;
    	}
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(s, pos);
    }


    /**
     * 将时间转化为string格式 yyyy-MM-dd HH:mm:ss
     * @param d
     * @return
     */
    public static String getDate2String(Date d){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(d);
    }
    
    public static String getDate2YYYYMMdd(Date d){
    	DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    	return formatter.format(d);
    }
    
    
    
    /**获取前n个小时的时间 
     * @param date
     * @param hour
     * @return   
     * @author shaosen
     * @Date 2018年9月12日
     */
    public static Date getBeforHour(Date date , int hour) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - hour );
		return c.getTime();
    }
    
    /**获取后n个小时的时间 
     * @param date
     * @param hour
     * @return   
     * @author shaosen
     * @Date 2018年9月12日
     */
    public static Date getAfterHour(Date date , int hour) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + hour );
    	return c.getTime();
    }
    
    /**获取小时开始时间 
     * @param date
     * @return   
     * @author shaosen
     * @Date 2018年9月12日
     */
    public static Date getHourBeginTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
    
    /**获取小时结束时间 
     * @param date
     * @return   
     * @author shaosen
     * @Date 2018年9月12日
     */
    public static Date getHourEndTime(Date date) {
  		Calendar c = Calendar.getInstance();
  		c.setTime(date);
  		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), 59, 59);
  		c.set(Calendar.MILLISECOND, 999);
  		return c.getTime();
  	}
    
    
    

	/**按照周期分别查找 
	 * @param em TimeEnum枚举
	 * @return   
	 * @author shaosen
	 * @Date 2018年11月6日
	 */
	public static List<Map<String, Date>> getStartTimeAndEndTimeByIntervalvalue(TimeEnum em) {
		List<Map<String, Date>> list = new ArrayList<>();
		if (em == TimeEnum.WEEK) {
			Date beginDayOfWeek = DateUtil.getBeginDayOfWeek();// 获取本周的开始时间
			Map<String, Date> map = new HashMap<>();
			map.put("startDay", beginDayOfWeek);
			map.put("endDay", new Date());
			list.add(map);
			list = getStartTimeAndEndTimeByWeek(beginDayOfWeek, list);
		} else if (em == TimeEnum.MONTH) {
			Date beginDayOfMonth = DateUtil.getBeginDayOfMonth();
			Map<String, Date> map = new HashMap<>();
			map.put("startDay", beginDayOfMonth);
			map.put("endDay", new Date());
			list.add(map);
			list = getStartTimeAndEndTimeByMonth(beginDayOfMonth, list);
		} else if (em == TimeEnum.DAY) {
			Date dayBegin = DateUtil.getDayBegin();
			Map<String, Date> map = new HashMap<>();
			map.put("startDay", dayBegin);
			map.put("endDay", new Date());
			list.add(map);
			list = getStartTimeAndEndTimeByDay(dayBegin, list);
		}

		return list;
	}
	
	
	private static List<Map<String, Date>> getStartTimeAndEndTimeByDay(Date date, List<Map<String, Date>> list) {
		Date endDay = DateUtil.getFrontDay(date, 1);// 返回某个日期前几天的日期
		Date startDay = DateUtil.getDayStartTime(endDay);

		if (startDay.after(DateUtil.getBeginDayOfYear())) {
			Map<String, Date> map = new HashMap<>();
			map.put("startDay", startDay);
			map.put("endDay", date);
			list.add(map);
			getStartTimeAndEndTimeByDay(startDay, list);
		} else {
			Map<String, Date> lastMap = new HashMap<>();
			lastMap.put("startDay", DateUtil.getBeginDayOfYear());
			lastMap.put("endDay", date);
			list.add(lastMap);
		}
		return list;
	}
	
	
	private static List<Map<String, Date>> getStartTimeAndEndTimeByMonth(Date date, List<Map<String, Date>> list) {

		Date endDay = DateUtil.getFrontDay(date, 1);// 返回某个日期前几天的日期
		Date startDay = DateUtil.getBeginDayOfMonth(endDay);

		if (startDay.after(DateUtil.getBeginDayOfYear())) {
			Map<String, Date> map = new HashMap<>();
			map.put("startDay", startDay);
			map.put("endDay", date);
			list.add(map);
			// 继续找上月开始时间和结束时间
			getStartTimeAndEndTimeByMonth(startDay, list);
		} else {
			// 获取本年第一月开始日期和结束日期
			Map<String, Date> lastMap = new HashMap<>();
			lastMap.put("startDay", DateUtil.getBeginDayOfYear());
			lastMap.put("endDay", date);
			list.add(lastMap);
		}
		return list;
	}
	
	
	private static List<Map<String, Date>> getStartTimeAndEndTimeByWeek(Date date, List<Map<String, Date>> list) {

		Date endDay = DateUtil.getFrontDay(date, 1);// 返回某个日期前几天的日期
		Date startDay = DateUtil.getBeginDayOfWeek(endDay);

		if (startDay.after(DateUtil.getBeginDayOfYear())) {
			Map<String, Date> map = new HashMap<>();
			map.put("startDay", startDay);
			map.put("endDay", date);
			list.add(map);
			// 继续找上周开始时间和结束时间
			getStartTimeAndEndTimeByWeek(startDay, list);
		} else {
			// 获取本年第一周开始日期和结束日期
			Map<String, Date> lastMap = new HashMap<>();
			lastMap.put("startDay", DateUtil.getBeginDayOfYear());
			lastMap.put("endDay", date);
			list.add(lastMap);
		}
		return list;
	}
}