package com.example.springboot.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * Description:日期时间操作的工具类
 * 
 * @author CZH
 */
public class DateUtil {
	/** 日期格式(yyyy-MM-dd) */
	public static final String yyyy_MM_dd_EN = "yyyy-MM-dd";
	/** 日期格式(yyyyMMdd) */
	public static final String yyyyMMdd_EN = "yyyyMMdd";
	/** 日期格式(yyyy-MM) */
	public static final String yyyy_MM_EN = "yyyy-MM";
	/** 日期格式(yyyyMM) */
	public static final String yyyyMM_EN = "yyyyMM";
	/** 日期格式(yyMMdd) */
	public static final String yyMMdd_EN = "yyMMdd";
	/** 日期格式(yyMMddHHmm) */
	public static final String yyMMddHHmm_EN = "yyMMddHHmm";
	/** 日期格式(yyyy-MM-dd) */
	public static final String yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";
	/** 日期格式(yyyy年MM月dd日) */
	public static final String MM_dd_CN = "MM月dd日";
	/** 日期格式(yyyy年MM月dd日) */
	public static final String yyyy_MM_CN = "yyyy年MM月";
	public static final String yyyy_MM_DD_CN = "yyyy年MM月dd日";
	/** 日期格式(yyyy年MM月dd日) */
	public static final String yyyy = "yyyy";
	/** DateFormat缓存 */
	private static Map<String, DateFormat> dateFormatMap = new HashMap<String, DateFormat>();

	/**
	 * 获取指定日期MM月dd日的形式
	 * @return
	 */
	public static String getOutDate(Date date) {
		return dateToDateString(date, yyyy_MM_dd_EN);
	}

	/**
	 * 获取当前日期MM月dd日的形式
	 * @return
	 */
	public static String getCurCNDate() {
		return getCurCNDate(getDate());
	}

	/**
	 * 获取指定日期MM月dd日的形式
	 * @return
	 */
	public static String getCurCNDate(Date date) {
		return dateToDateString(date, MM_dd_CN);
	}

    /**
     * 获取当前日期yyyy年MM月的形式
     * @return
     */
    public static String getCurCNYear() {
		return getCurCNYear(getDate());
    }

	/**
	 * 获取指定日期yyyy年MM月的形式
	 * @return
	 */
	public static String getCurCNYear(Date date) {
		return dateToDateString(date, yyyy_MM_CN);
	}

	/**
	 * 获取当前日期yyyy的形式
	 * @return
	 */
	public static String getCurYear() {
		return dateToDateString(new Date(), yyyy);
	}

	/**
	 * 获取当前时间的DateFormat格式字符串
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(String formatStr) {
		return dateToDateString(getDate(), formatStr);
	}

	/**
	 * 将Date转换成formatStr格式的字符串
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateToDateString(Date date, String formatStr) {
		DateFormat df = getDateFormat(formatStr);
		return df.format(date);
	}

	/**
	 * 获取DateFormat
	 * @param formatStr
	 * @return
	 */
	public static DateFormat getDateFormat(String formatStr) {
		DateFormat df = dateFormatMap.get(formatStr);
		if (df == null) {
			df = new SimpleDateFormat(formatStr);
			dateFormatMap.put(formatStr, df);
		}
		return df;
	}

	/**
	 * 获取当前时间
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 按照默认formatStr的格式，转化dateTimeStr为Date类型 dateTimeStr必须是formatStr的形式
	 * @param dateTimeStr
	 * @param formatStr
	 * @return
	 */
	public static Date getDate(String dateTimeStr, String formatStr) {
		try {
			if (dateTimeStr == null || dateTimeStr.equals("")) {
				return null;
			}
			DateFormat sdf = DateUtil.getDateFormat(formatStr);
			return sdf.parse(dateTimeStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * 获取Date中的分钟
	 * @param d
	 * @return
	 */
	public static int getMin(Date d) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(d);
		return now.get(Calendar.MINUTE);
	}

	/**
	 * 获取指定时间当天起始时间（默认当天）
	 * @return
	 */
	public static Date getTodayBeginDate(Date date){
		Calendar todayStart = Calendar.getInstance();
		if (date == null){
			date = getDate();
		}
		todayStart.setTime(date);
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 获取指定时间当天结束时间（默认当天）
	 * @return
	 */
	public static Date getTodayEndDate(Date date){
		Calendar todayEnd = Calendar.getInstance();
		if (date == null){
			date = getDate();
		}
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	/**
	 * 获取本月的第一天
	 */
	public static Date getFirstDayOfCurrentMonth() {
		return getFirstDayOfMonth(null);
	}

	/**
	 * 获取指定时间所在月的第一天
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date == null){
			date = getDate();
		}
		cal.setTime(date);
		// 获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取本月的最后一天
	 */
	public static Date getLastDayOfCurrentMonth() {
		return getFirstDayOfMonth(null);
	}

	/**
	 * 获取指定时间所在月的最后一天
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date == null){
			date = getDate();
		}
		cal.setTime(date);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 获取本年开始时间
	 * @return
	 */
	public static Date getFirstDayOfCurrentYear(){
		return getFirstDayOfYear(null);
	}

	/**
	 * 获取指定时间的本年开始时间（默认当年）
	 * @return
	 */
	public static Date getFirstDayOfYear(Date date){
		Calendar calendar = Calendar.getInstance();
		if (date == null){
			date = getDate();
		}
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}


}
