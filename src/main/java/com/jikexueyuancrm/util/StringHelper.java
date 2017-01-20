package com.jikexueyuancrm.util;

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class StringHelper {

	private static final String emptystr = "";
	private static final Pattern pattern = Pattern.compile("^http://(.*?)\\/");
	private static final Pattern patterns = Pattern
			.compile("^https://(.*?)\\/");

	public static String replaceTitle(String title) {
		return title.replace("<em>", "").replace("</em>", "");
	}

	public static String replaceTitleSogou(String title, String keyWord) {
		return title.replace("\n", "").replace("<em> <!--red_beg-->", "")
				.replace(" <!--red_end--></em>", "");
	}

	/**
	 * @see 取路径下所有文件名字
	 * @param path
	 * @return
	 */
	public static String fileNames(String path) {
		File f = new File(path);
		File[] list = f.listFiles(); /* 此处获取文件夹下的所有文件 */
		String jars = "";
		for (int i = 0; i < list.length; i++)
			if (!list[i].getName().equals(".svn")) {
				jars += "${PRJ_LIB_CLASSPATH}" + list[i].getName() + ";";// 打印全路径，可以更改为你自己需要的方法
			}
		System.out.println(jars);
		return jars;
	}

	/**
	 * @see 域名提取
	 * @param url
	 * @return String
	 */
	public static String getBaseUrl(String url) {
		if (url.indexOf("/", 12) == -1) {
			url = url + "/";
		}
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}

		Matcher matchers = patterns.matcher(url);
		if (matchers.find()) {
			return matchers.group(1);
		}
		return emptystr;
	}


	
	

	
	private final static float mNum = 64 / 5;
	private final static long size = (long) (1L * mNum * 1024 * 1024 * 8);
	/**
	 * @see 获取网址的编码
	 * @param url
	 *            must begin with 'http://'
	 * @return String
	 */
	public static String getUrlCode(String url) {
		if ( org.apache.commons.lang.StringUtils.isEmpty(url))
			return "";
		long code = hsval(url);
		return (code + "").replace("-", "N");
	}
	/**
	 * @see comput hs
	 * @param h
	 * @return long
	 */
	public static long hsval(String stream) {
		if (null == stream) {
			return 0;
		}
		stream = stream.replaceAll("/", "a").replaceAll("\\?", "b").replaceAll(
				":", "c").replaceAll("\\.", "d").replaceAll("=", "e")
				.replaceAll("&", "f").replaceAll("\\+", "g").replaceAll("-",
						"h").replaceAll("_", "l");
		return myHashCode(stream);
	}

	public static long myHashCode(String str) {
		long hash = 0;
		for (int i = 0; i < str.length(); i++) {
			hash = 31 * hash + str.charAt(i);
		}

		if (hash < 0) {
			hash *= -1;
		}

		return (hash % size);
	}

	/**
	 * hashcode
	 * @param str
	 * @return
	 */
	public static String hashCoder(String str) {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(str);
		String hascode = String.valueOf(builder.toHashCode());
		return hascode;
	}

	/**
	 * MD5
	 * @param str
	 * @return
	 */
	public static String md5Builder(String str) {
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte b[] = md.digest(); 
			int i; 
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) { 
				i = b[offset]; 
				if(i<0) i+= 256; 
				if(i<16) 
				buf.append("0"); 
				buf.append(Integer.toHexString(i)); 
			}
			return buf.toString();
		}catch(Exception e){
			e.printStackTrace();	
		}
		return null;
	}

	
	
	public static final String rexUrl = "(?<=href=\")(.*?)(\")";
	public static Pattern patternUrl = Pattern.compile(rexUrl, Pattern.DOTALL);

	public static String getHrefUrl(String html) {
		Matcher matcher = patternUrl.matcher(html);
		while (matcher != null && matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}
	
	/**
	 * @see html转为文本
	 * @param html
	 * @return string
	 */
	public static String preProcess(String html) {
		html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
		html = html.replaceAll("(?is)<!--.*?-->", "");
		html = html.replaceAll("(?is)<script.*?>.*?</script>", "");
		html = html.replaceAll("(?is)<style.*?>.*?</style>", "");
		html = html.replaceAll("(?is)style=\".*?\"", "");
		html = html.replaceAll("&.{2,5};|&#.{2,5};", " ");
		html = html.replaceAll("(?is)<.*?>", "");

		List<String> htmls = Arrays.asList(html.split("\n"));
		StringBuffer htmlBuffer = new StringBuffer();
		for (String htm : htmls) {
			if (htm.trim().length() == 0) {
				continue;
			}
			htmlBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;" + htm);
			htmlBuffer.append("</br></br>");
		}
		return htmlBuffer.toString();
	}

	
	
	/**
	 * @see html转为文本(除图片)
	 * (?is)单行忽略大小写模式
	 * @param html
	 * @return string
	 * @throws Exception 
	 */
	public static String preProcessHaveImg(String html) throws Exception {
		html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
		html = html.replaceAll("(?is)<!--.*?-->", "");
		html = html.replaceAll("(?is)<script.*?>.*?</script>", "");
		html = html.replaceAll("(?is)<style.*?>.*?</style>", "");
		html = html.replaceAll("(?is)style=\".*?\"", "");
		
//		html = html.replaceAll("&.{2,5};|&#.{2,5};", " ");
		//过滤出图片外其他标签
		html = html.replaceAll("(?is)<[^i].*?>", "").replaceAll("(?is)<i[^m].*?>", "");

		List<String> htmls = Arrays.asList(html.split("\n"));
		StringBuffer htmlBuffer = new StringBuffer();
		for (String htm : htmls) {
			if (htm.trim().length() == 0) {
				continue;
			}
			htmlBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;" + htm);
			htmlBuffer.append("</br></br>");
		}
		return htmlBuffer.toString();
	}
	
	


}
