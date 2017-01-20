package com.jikexueyuancrm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.python.jline.internal.Log;

public class TextUtils {
	
	
	
	private static Logger log = Logger.getLogger(TextUtils.class);
	
	
	
	//提取出字符串中的文本
	
	
	
	
	public static  String extractNumber(String str){
		
		
		
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(str);  
		
	    return 	m.replaceAll("").trim();	
		
	}

	
	
	public static boolean isContainsChinese(String str)
	{
	 String regEx = "[\u4e00-\u9fa5]";
	 Pattern patChinese = Pattern.compile(regEx);
	Matcher matcher = patChinese.matcher(str);
	boolean flg = false;
	if (matcher.find())    {
	flg = true;
	}
	return flg;
	}
	
	
	public static boolean isContainsEnglish(String str)
	{
	 String regEx = "[a-zA-z]";
	 Pattern patChinese = Pattern.compile(regEx);
	Matcher matcher = patChinese.matcher(str);
	boolean flg = false;
	if (matcher.find())    {
	flg = true;
	}
	return flg;
	}
	
	
	
	public static boolean HasDigit(String str)
	{
	 String regEx = ".*\\d+.*";
	 Pattern patChinese = Pattern.compile(regEx);
	Matcher matcher = patChinese.matcher(str);
	boolean flg = false;
	if (matcher.find())    {
	flg = true;
	}
	return flg;
	}
	
	

public static void main(String[] args) {  
	
	log.info(HasDigit("rrtrt"));
	
}
}

