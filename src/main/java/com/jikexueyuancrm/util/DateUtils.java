package com.jikexueyuancrm.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:DateUtils Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @version
 * @since Ver 1.1
 * @Date Nov 21, 2012 2:20:13 PM
 * 
 * @see @ deprecated
 */
public class DateUtils {
	/**
	 * Ӣ�ļ�д��Ĭ�ϣ��磺2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";

	/**
	 * Ӣ��ȫ�� �磺2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Ӣ��ȫ���磺2015/7/22 16:22:53
	 */
	public static String FORMAT_LONG_2 = "yyyy/MM/dd HH:mm:ss";

	/**
	 * Ӣ��ȫ�� �磺2010-12-01 23:15
	 */
	public static String FORMAT_LONG_15 = "yyyy-MM-dd HH:mm";

	/**
	 * Ӣ��ȫ�� �磺23:15:06 2010-12-01
	 */
	public static String FORMAT_LONG_HOUR_YEAR = "HH:mm:ss yyyy-MM-dd";

	/**
	 * ��ȷ�����������ʱ�� �磺yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * ���ļ�д �磺2010��12��01��
	 */
	public static String FORMAT_SHORT_CN = "yyyy��MM��dd��";

	/**
	 * ����ȫ�� �磺2010��12��01�� 23ʱ15��06��
	 */
	public static String FORMAT_LONG_CN = "yyyy��MM��dd��  HHʱmm��ss��";

	/**
	 * ��ȷ���������������ʱ��
	 */
	public static String FORMAT_FULL_CN = "yyyy��MM��dd��  HHʱmm��ss��SSS����";

	/**
	 * ��ȷ�������
	 */
	public static String FORMAT_FULL_SIMPLE = "yyyyMMddHHmmssSSS";
	
	/**
	 * ���Ĭ�ϵ� date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * ����Ԥ���ʽ���ص�ǰ����
	 * 
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * �����û���ʽ���ص�ǰ����
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * ʹ��Ԥ���ʽ��ʽ������
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * ʹ���û���ʽ��ʽ������
	 * 
	 * @param date
	 *            ����
	 * @param pattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * ʹ��Ԥ���ʽ��ȡ�ַ�������
	 * 
	 * @param strDate
	 *            �����ַ���
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * ʹ���û���ʽ��ȡ�ַ�������
	 * 
	 * @param strDate
	 *            �����ַ���
	 * @param pattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see ����ͷ��ʱ���תʱ��
	 * 
	 * @param longDate
	 * @return
	 */
	public static Date parseLongStr(String longDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_LONG);
		long unixLong = Long.parseLong(longDate) * 1000;
		return parse(sdf.format(unixLong));
	}

	/**
	 * ʹ���û���ʽ��ȡ�ַ�������
	 * 
	 * @param strDate
	 *            �����ַ���
	 * @param pattern
	 *            ���ڸ�ʽ
	 * @return
	 * @throws ParseException
	 */
	public static Date parseException(String strDate) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_LONG);
		return df.parse(strDate);
	}

	/**
	 * ʹ���û���ʽ��ȡ�ַ�������
	 * 
	 * @param strDate
	 *            �����ַ���
	 * @param pattern
	 *            ���ڸ�ʽ
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Timestamp changeDate(Date strDate)
			throws ParseException {
		java.sql.Date time = new java.sql.Date(strDate.getTime());
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time.getTime());
		return timestamp;
	}

	/**
	 * ��������������������
	 * 
	 * @param date
	 *            ����
	 * @param n
	 *            Ҫ���ӵ�����
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * ����������������
	 * 
	 * @param date
	 *            ����
	 * @param n
	 *            Ҫ���ӵ�����
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * ��ȡʱ���
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param date
	 *            ����
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * ��Ĭ�ϸ�ʽ���ַ���������������
	 * 
	 * @param date
	 *            �����ַ���
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * ���û���ʽ�ַ���������������
	 * 
	 * @param date
	 *            �����ַ���
	 * @param format
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * @see ���ݴ��������ʱ����ʱ����
	 * @param d2
	 * @return second
	 */
	public static int getDateBetween(java.util.Date d1, java.util.Date d2) {
		Date[] d = new Date[2];
		d[0] = d1;
		d[1] = d2;
		Calendar[] cal = new Calendar[2];
		for (int i = 0; i < cal.length; i++) {
			cal[i] = Calendar.getInstance();
			cal[i].setTime(d[i]);
			cal[i].set(Calendar.HOUR_OF_DAY, 0);
			cal[i].set(Calendar.MINUTE, 0);
			cal[i].set(Calendar.SECOND, 0);
		}
		long m = cal[0].getTime().getTime();
		long n = cal[1].getTime().getTime();
		return (int) ((m - n) / 1000);
	}

	public static int getDateBetweenSecond(java.util.Date d1, java.util.Date d2) {
		Date[] d = new Date[2];
		d[0] = d1;
		d[1] = d2;
		Calendar[] cal = new Calendar[2];
		for (int i = 0; i < cal.length; i++) {
			cal[i] = Calendar.getInstance();
			cal[i].setTime(d[i]);
			// cal[i].set(Calendar.HOUR_OF_DAY, 0);
			// cal[i].set(Calendar.MINUTE, 0);
			// cal[i].set(Calendar.SECOND, 0);
		}
		long m = cal[0].getTime().getTime();
		long n = cal[1].getTime().getTime();
		return (int) ((m - n) / 1000);
	}

	public static Date timestampToDate(String dateString) {
		Long newDateTime = Long.parseLong(dateString) * 1000L;
		Date date = null;
		try {
			date = new Date(newDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @see�������ڵĲ��(����)
	 */
	public static long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
		return totalDate;
	}

	/**
	 * @see ���ַ���ת��ΪJAVAʱ������(��ȷ����)��
	 * 
	 * @return Date date��JAVAʱ�����͡�
	 * @param String
	 *            ���ַ�����
	 */
	public static Date formatFullString(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see ��ʱ�����Сʱ
	 * @param dt
	 * @return
	 */
	public static Date addHourtoDate(Date dt, int hours) {
		if (null == dt) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.HOUR, hours);
		return c.getTime();
	}

	/**
	 * @see�������ڵ�Сʱ
	 */
	public static long getDistHours(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60);
		return totalDate;
	}

	/**
	 * @see ʱ�䴮��ʽ��
	 * @param time
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formatTime2(String time) {
		if (null == time)
			return time;
		time = time.replace("��", "").replaceAll(" ", "").replaceAll("��", "")
				.trim();
		String[] token = { "��", "��", "/" };
		for (String tk : token) {
			time = time.replaceAll(tk, "-");
		}
		String[] part = time.split("-");
		int partLn = part.length;
		if (partLn == 3) {
			if (part[0].length() == 2) {
				part[0] = "20" + part[0];
			}
			if (part[1].length() == 1) {
				part[1] = "0" + part[1];
			}
			String[] p3 = part[2].split(":");
			int p3l = p3.length;
			if (p3l == 1) {
				part[2] = "-" + part[2] + " 00:00:00";
			} else if (p3l == 2) {
				int p3ln = p3[0].length();
				part[2] = "-" + p3[0].substring(0, p3ln - 2) + " "
						+ p3[0].substring(p3ln - 2, p3ln) + ":00:00";
			} else if (p3l == 3) {
				int p3ln = p3[0].length();
				String t1 = "0" + p3[1];
				String t2 = "0" + p3[2];
				if (p3[1].length() == 2) {
					t1 = p3[1];
				}
				if (p3[2].length() == 2) {
					t2 = p3[2];
				}
				part[2] = "-" + p3[0].substring(0, p3ln - 2) + " "
						+ p3[0].substring(p3ln - 2, p3ln) + ":" + t1 + ":" + t2;
			}
			return part[0] + "-" + part[1] + part[2];
		} else if (partLn == 2) {
			String t1 = part[0];
			String t2 = part[1];
			if (t2.length() > 6) {
				return t1 + "-" + t2.substring(0, 2) + "-" + t2.substring(2, 4)
						+ " " + t2.substring(4, t2.length());
			}
		}
		return null;
	}

	/**
	 * @see ΢��ʱ�䴦��
	 * @param pushTime
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String handlePushTime(String pushTime) {
		if ("".equals(pushTime)) {
			return getFrontDate(0);
		}
		pushTime = pushTime.split("����")[0].toString().replace("?", " ");
		try {
			if (pushTime.indexOf("����ǰ") != -1) {
				int minute = Integer.parseInt(getNumfromStr(pushTime));
				pushTime = getFrontDate(minute);
			} else if (pushTime.indexOf("Сʱǰ") != -1) {
				int hour = Integer.parseInt(getNumfromStr(pushTime));
				pushTime = getFrontDate(hour * 60);
			} else if (pushTime.indexOf("����") != -1) {
				pushTime = pushTime.replace("����",
						getStringDateShort("yyyy-MM-dd"));
			} else if (pushTime.length() > 13) {
				pushTime = pushTime.replace("��", "-").replace("��", "").replace(
						"��", "-");
			} else {
				pushTime = DateUtils.getYear(new Date()) + "-"
						+ pushTime.replace("��", "-").replace("��", "");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pushTime;
	}

	
	/**
	 * @see �ٶ��ȵ�����ʱ�䴦��
	 * @param pushTime
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String handleShotTime(String pushTime) {
		if ("".equals(pushTime)) {
			return getFrontDate(0);
		}
		try {
			if (pushTime.indexOf("����ǰ") != -1) {
				int minute = Integer.parseInt(getNumfromStr(pushTime));
				pushTime = getFrontDate(minute);
			} else if (pushTime.indexOf("Сʱǰ") != -1) {
				int hour = Integer.parseInt(getNumfromStr(pushTime));
				pushTime = getFrontDate(hour * 60);
			}else if (pushTime.length() > 13) {
				pushTime = pushTime.replace("��", "-").replace("��", "").replace(
						"��", "-")+":00";
			} else {
				pushTime = DateUtils.getYear(new Date()) + "-"
						+ pushTime.replace("��", "-").replace("��", "");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pushTime;
	}

	
	/**
	 * @see ���ַ�����ȡ����
	 * @param orstr
	 * @return string
	 */
	public static String getNumfromStr(String orstr) {
		orstr = orstr.trim();
		String str2 = "";
		if (orstr != null && !"".equals(orstr)) {
			for (int i = 0; i < orstr.length(); i++) {
				if (orstr.charAt(i) >= 48 && orstr.charAt(i) <= 57) {
					str2 += orstr.charAt(i);
				}
			}

		}
		return str2;
	}

	/**
	 * @see ��ȡ����ʱ��
	 * @param format
	 *            (yyyy-MM-dd yyyy-MM-dd HH:mm:ss)
	 * @return String
	 */
	public static String getStringDateShort(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * @see ʱ���������
	 * @param min
	 * @return
	 */
	public static String getFrontDate(int min) {
		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date datetest1 = new Date();
			String datestr = format.format(datetest1);
			Date datetest2 = format.parse(datestr);
			long Time1 = (datetest2.getTime() / 1000) - 60 * min;
			datetest2.setTime(Time1 * 1000);
			datestr = format.format(datetest2);
			return datestr;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "";
	}

	public static final String rexNum = "[0-9]+";
	public static Pattern pattern = Pattern.compile(rexNum);

	private static boolean isNumber(String str) {
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
			return false;
		return true;
	}

	public static String formartTime(String time) {
		if (null == time || "".equals(time)) {
			return "";
		}
		StringBuffer timeStr = new StringBuffer();
		for (int i = 0; i < time.length(); i++) {
			String tmp = String.valueOf(time.charAt(i));
			if (isNumber(tmp)) {
				timeStr.append(tmp);
			} else {
				timeStr.append(" ");
			}
		}
		String mid = timeStr.toString().replaceAll("  ", " ").trim();
		String[] midStrs = mid.split(" ");
		StringBuffer retime = new StringBuffer();
		for (int k = 0; k < midStrs.length; k++) {
			if (k == 0 && midStrs[k].length() == 4) {
				retime.append(midStrs[k]);
			} else if (k == 0) {
				retime.append("20");
				retime.append(midStrs[k]);
			}
			if (k == 1 && midStrs[k].length() < 2) {
				retime.append("-");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 1) {
				retime.append("-");
				retime.append(midStrs[k]);
			}

			if (k == 2 && midStrs[k].length() < 2) {
				retime.append("-");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 2 && midStrs[k].length() > 2) {
				retime.append("-");
				String tmp = midStrs[k].substring(0, 2);
				if (Integer.parseInt(tmp) > 31) {
					tmp = "0" + midStrs[k].substring(0, 1);
					retime.append(tmp);
					break;
				}
				retime.append(tmp);
				break;
			} else if (k == 2) {
				retime.append("-");
				retime.append(midStrs[k]);
			}

			if (k == 3 && midStrs[k].length() < 2) {
				retime.append(" ");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 3) {
				retime.append(" ");
				retime.append(midStrs[k]);
			}

			if ((k == 4 || k == 5) && midStrs[k].length() < 2) {
				retime.append(":");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 4 || k == 5) {
				retime.append(":");
				retime.append(midStrs[k]);
			}
		}
		if (retime.length() == 10) {
			String time2 = format(new Date()).split(" ")[1];
			retime.append(" " + time2);
		} else if (retime.length() == 16) {
			retime.append(":00");
		}
		return retime.toString();
	}

	/**
	 * �����ʱ�������Ƚ�,��ʱ��������
	 * 
	 * @param input
	 * 
	 * 
	 * @return
	 */
	public static int compareDAY(Date input) {
		Calendar td = Calendar.getInstance();
		Calendar ipt = Calendar.getInstance();
		td.setTime(new Date());
		ipt.setTime(input);
		// ����ʱ��Ϊ0ʱ
		td.set(java.util.Calendar.HOUR_OF_DAY, 0);
		td.set(java.util.Calendar.MINUTE, 0);
		td.set(java.util.Calendar.SECOND, 0);
		ipt.set(java.util.Calendar.HOUR_OF_DAY, 0);
		ipt.set(java.util.Calendar.MINUTE, 0);
		ipt.set(java.util.Calendar.SECOND, 0);
		// �õ�����������������
		return ((int) (ipt.getTime().getTime() / 1000) - (int) (td.getTime()
				.getTime() / 1000)) / 3600 / 24;
	}

	/**
	 * @see �ж�ʱ���Ƿ�������10�㵽����2��
	 * 
	 * @param input
	 * @return
	 */
	public static boolean timeIn22Or02(Date input) {
		Calendar ipt = Calendar.getInstance();
		ipt.setTime(input);
		int hour = ipt.get(java.util.Calendar.HOUR_OF_DAY);
		return hour >= 22 || hour <= 02;
	}

	/**
	 * @see �ж�ʱ���Ƿ�������10�㵽����8��
	 * 
	 * @param input
	 * @return
	 */
	public static boolean timeIn22Or08(Date input) {
		Calendar ipt = Calendar.getInstance();
		ipt.setTime(input);
		int hour = ipt.get(java.util.Calendar.HOUR_OF_DAY);
		return hour >= 22 || hour <= 8;
	}

	public static void main(String[] args) {
		// 1384224519
		// System.out.println(parse("2013-11-15 08:00:22").getTime());
		// Date dt = parse("2013-11-15 00:51:22");
		// System.out.println(compareDAY(dt));
		// SimpleDateFormat df = new SimpleDateFormat(FORMAT_SHORT);
		try {
			// System.out.println(timeIn22Or02(parse("2013-11-19 22:00:22")));
			System.out.println(formartTimeFor("2014-7-28"));
			// System.out.println(compareDAY(parse("2010-05-19 00:00:00")));
			// if (DateUtils.compareDAY(parse("2009-10-12 00:00:00")) < -30)
			// return;
			// handlePushTime("22Сʱǰ");
			// System.out.println(new Date("1384224519"));
			// df.format(new Date("1384224519")); 1384483882
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����format��ʽ��ʱ���ַ�������Date
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date getDateWithFormat(String format, String date) {
		try {
			if (format == null || "".equals(format)) {
				format = FORMAT_LONG;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ʱ���ʽת������dateΪ�գ����ʼ��ʱ��Ϊi��ǰ/��
	 * 
	 * @param date
	 * @param format
	 * @param i
	 * @return
	 */
	public static Date initDate(String date, String format, int i) {
		try {
			if (date != null && !"".equals(date)) {
				return DateUtils.parse(date, format);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, i);
				return cal.getTime();
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ʱ���ʽת������dateΪ�գ����ʼ��ʱ��Ϊi��ǰ/��
	 * 
	 * @param date
	 * @param format
	 * @param i
	 * @return
	 */
	public static String initDateString(String date, String format, int i) {
		try {
			return DateUtils.format(initDate(date, format, i), format);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formartTimeFor(String time) {
		if (null == time || "".equals(time)) {
			return "";
		}
		StringBuffer timeStr = new StringBuffer();
		for (int i = 0; i < time.length(); i++) {
			String tmp = String.valueOf(time.charAt(i));
			if (isNumber(tmp)) {
				timeStr.append(tmp);
			} else {
				timeStr.append(" ");
			}
		}
		String mid = timeStr.toString().replaceAll("  ", " ").trim();
		String[] midStrs = mid.split(" ");
		StringBuffer retime = new StringBuffer();
		for (int k = 0; k < midStrs.length; k++) {
			if (k == 0 && midStrs[k].length() == 4) {
				retime.append(midStrs[k]);
			} else if (k == 0) {
				retime.append("20");
				retime.append(midStrs[k]);
			}
			if (k == 1 && midStrs[k].length() < 2) {
				retime.append("-");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 1) {
				retime.append("-");
				retime.append(midStrs[k]);
			}

			if (k == 2 && midStrs[k].length() < 2) {
				retime.append("-");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 2 && midStrs[k].length() > 2) {
				retime.append("-");
				String tmp = midStrs[k].substring(0, 2);
				if (Integer.parseInt(tmp) > 31) {
					tmp = "0" + midStrs[k].substring(0, 1);
					retime.append(tmp);
					break;
				}
				retime.append(tmp);
				break;
			} else if (k == 2) {
				retime.append("-");
				retime.append(midStrs[k]);
			}

			if (k == 3 && midStrs[k].length() < 2) {
				retime.append(" ");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 3) {
				retime.append(" ");
				retime.append(midStrs[k]);
			}

			if ((k == 4 || k == 5) && midStrs[k].length() < 2) {
				retime.append(":");
				retime.append("0");
				retime.append(midStrs[k]);
			} else if (k == 4 || k == 5) {
				retime.append(":");
				retime.append(midStrs[k]);
			}
		}
		if (retime.length() == 10) {
			retime.append(" 00:00:00");
		}
		return retime.toString();
	}
	
	
	public static String formartMtime(String time) {
		// has - or not
		if (StringUtils.isEmpty(time)) {
			return "";
		}
		String[] arr = new String[6];
		if (time.indexOf("-") != -1 && time.trim().length() > 10) {
			String htmp = "";
			String[] tmp = time.split("-");
			if (tmp[0].trim().length() == 2) {
				tmp[0] = "20" + tmp[0];
			}
			arr[0] = tmp[0];
			if (tmp[1].length() < 2) {
				arr[1] = "0" + tmp[1];
			} else {
				arr[1] = tmp[1];
			}

			htmp = tmp[2];
			arr[2] = htmp.substring(0, 2);
			if (arr[2].trim().length() < 2) {
				arr[2] = "0" + arr[2];
			}

			htmp = htmp.substring(2).trim();
			String[] hhour = htmp.split(":");
			arr[3] = hhour[0];
			if (arr[3].trim().length() < 2) {
				arr[3] = "0" + hhour[0];
			}
			arr[4] = hhour[1];
			if (arr[4].trim().length() < 2) {
				arr[4] = "0" + hhour[1];
			}
			if (hhour.length > 2) {
				arr[5] = hhour[2];
			} else {
				arr[5] = "00";
			}
		} else if (time.indexOf("-") != -1 && time.trim().length() <= 10) {
			String[] arr2 = time.trim().split("-");
			if (arr2.length != 3) {
				return "";
			}
			if (arr2[0].length() < 4) {
				arr[0] = "20" + arr2[0];
			} else {
				arr[0] = arr2[0];
			}

			if (arr2[1].length() < 2) {
				arr[1] = "0" + arr2[1];
			} else {
				arr[1] = arr2[1];
			}

			if (arr2[2].length() < 2) {
				arr[2] = "0" + arr2[2];
			} else {
				arr[2] = arr2[2];
			}
			arr[3] = "00";
			arr[4] = "00";
			arr[5] = "00";
		} else if (time.indexOf("��") != -1) {
			time = time.replace("��", "-").replace("��", "-");
			String[] sectime = time.split("-");
			if (sectime[0].trim().length() == 2) {
				sectime[0] = "20" + sectime[0];
			}
			arr[0] = sectime[0];
			if (sectime[1].trim().length() == 1) {
				sectime[1] = "0" + sectime[1];
			}
			arr[1] = sectime[1];

			String[] tmp = sectime[2].split("��");
			arr[2] = tmp[0];
			if (arr[2].trim().length() < 2) {
				arr[2] = "0" + arr[2];
			}
			if (tmp.length > 1 && tmp[1].indexOf(":") != -1) {
				String[] houtmp = tmp[1].split(":");
				arr[3] = houtmp[0].trim();
				arr[4] = houtmp[1].trim();

				if (arr[3].trim().length() < 2) {
					arr[3] = "0" + arr[3];
				}
				if (arr[4].trim().length() < 2) {
					arr[4] = "0" + arr[4];
				}

				if (houtmp.length > 2) {
					arr[5] = houtmp[2];
				} else {
					arr[5] = "00";
				}
			} else if (tmp.length > 1 && tmp[1].indexOf("ʱ") != -1) {
				String[] houtmp = tmp[1].split("ʱ");
				arr[3] = houtmp[0].trim();
				arr[4] = houtmp[1].trim();
				if (arr[3].trim().length() < 2) {
					arr[3] = "0" + arr[3];
				}
				if (arr[4].trim().length() < 2) {
					arr[4] = "0" + arr[4];
				}
				if (houtmp.length > 2) {
					arr[5] = houtmp[2];
				} else {
					arr[5] = "00";
				}
			} else {
				arr[3] = "00";
				arr[4] = "00";
				arr[5] = "00";
			}

		}
		StringBuffer buffer = new StringBuffer();
		int k = 0;
		for (String str : arr) {
			if (k == 1 || k == 2) {
				buffer.append("-");
			} else if (k == 3) {
				buffer.append(" ");
			} else if (k == 4 || k == 5) {
				buffer.append(":");
			}
			buffer.append(str);
			k++;
		}
		// System.out.println(buffer.toString());
		return buffer.toString();
	}
	
	
	/**
	   * ������ת��Ϊ������ʱ����
	   * 
	   * @author GaoHuanjie
	   */
	  public static String getYearMonthDayHourMinuteSecond(long timeMillis) {
		  Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));  
	      calendar.setTimeInMillis(timeMillis);
	      int year=calendar.get(Calendar.YEAR);
	      
	      int month=calendar.get(Calendar.MONTH) + 1;
	      String mToMonth=null;
	      if (String.valueOf(month).length()==1) {
	      	mToMonth="0"+month;
	      } else {
	      	mToMonth=String.valueOf(month);
	      }
	      
	      int day=calendar.get(Calendar.DAY_OF_MONTH);
	      String dToDay=null;
	      if (String.valueOf(day).length()==1) {
	      	dToDay="0"+day;
	      } else {
	      	dToDay=String.valueOf(day);
	      }
	      
	      int hour=calendar.get(Calendar.HOUR_OF_DAY);
	      String hToHour=null;
	      if (String.valueOf(hour).length()==1) {
	      	hToHour="0"+hour;
	      } else {
	      	hToHour=String.valueOf(hour);
	      }
	      
	      int minute=calendar.get(Calendar.MINUTE);
	      String mToMinute=null;
	      if (String.valueOf(minute).length()==1) {
	      	mToMinute="0"+minute;
	      } else {
	      	mToMinute=String.valueOf(minute);
	      }
	      
	      int second=calendar.get(Calendar.SECOND);
	      String sToSecond=null;
	      if (String.valueOf(second).length()==1) {
	      	sToSecond="0"+second;
	      } else {
	      	sToSecond=String.valueOf(second);
	      }
	      return  year+ "-" +mToMonth+ "-" +dToDay+ " "+hToHour+ ":" +mToMinute+ ":" +sToSecond; 	
	  }

	
}
