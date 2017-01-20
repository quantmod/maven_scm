package com.jikexueyuancrm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class FileNameUtils {
	
	
	//获取按周的文件名
	
	/**
	 * @author Administrator 2016年11月3日
	 * @return
	 */
	public static String getFileNameByWeek(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMW");//设置日期格式
		String week = df.format(new Date());// new Date()为获取当前系统时间
		
		return week;
	}

}
