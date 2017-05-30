package com.ljheee.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 获取当前时间 距离solidTime还有多长[假定solidTime是未来时间点]
	 * @param solidTime
	 * @return
	 */
	public static String getTimeInterval(String solidTime) {
		String result = null;
		int dd[] = timeInterval(solidTime);
		result = dd[0] + "天" + dd[1] + "小时" + dd[2] + "分钟" + dd[3] + "秒";
		return result;
	}

	/**
	 * 获取当前时间 距离solidTime还有多长[假定solidTime是未来时间点]
	 * 
	 * @param solidTime
	 * @return
	 */
	public static int[] timeInterval(String solidTime) {
		int[] result = null;
		try {
			Date futherDate = sdf.parse(solidTime);

			long diff = futherDate.getTime() - System.currentTimeMillis();
			int day = (int) (diff / (24 * 60 * 60 * 1000));
			long off = diff - day * (24 * 60 * 60 * 1000);
			int hour = (int) (off / (60 * 60 * 1000));
			off = off - hour * (60 * 60 * 1000);
			int minute = (int) (off / (60 * 1000));
			off = off - minute * (60 * 1000);
			int second = (int) (off / 1000);

			result = new int[4];
			result[0] = day;
			result[1] = hour;
			result[2] = minute;
			result[3] = second;

			// System.out.println(day+"day"+hour+"hour"+minute+"minute"+second+"second");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 获取前后i天的日期 
	 * @param i -i为正数 向后推迟i天，负数时向前提前i天
	 * @return
	 */
	public static Date getDate(int i) {
		Date dat = null;
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, i);
		dat = cd.getTime();
		Timestamp date = Timestamp.valueOf(sdf.format(dat));
		return date;
	}
	

	public static void main(String[] args) {
		// timeInterval("2017-5-3 21:58:23");
		// System.out.println(getTimeInterval("2017-5-6 22:20:23"));
		System.out.println(getDate(-948).toLocaleString());
	}

}
