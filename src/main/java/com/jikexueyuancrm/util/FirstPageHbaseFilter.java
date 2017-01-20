package com.jikexueyuancrm.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.xd.iis.se.hbase.HbaseUtils;

public class FirstPageHbaseFilter {
	public static String tableName="check_weixin_repetition";

	public static Logger logger = Logger.getLogger(FirstPageHbaseFilter.class.getName());
	public static boolean alpha(String alpha) {
		String regex = ".*[a-zA-Z]+.*";
		Matcher m = Pattern.compile(regex).matcher(alpha);
		return m.matches();
	}
	public static String getHostName(String url) {
		int index = 0;
		if (url != null && url.indexOf("http://") == -1) {
			url = "http://" + url;
		}
		String candidate = getBaseUrl(url);
		for (; index >= 0;) {
			index = candidate.indexOf('.');
			String subCandidate = candidate.substring(index + 1);
			if (initMp.containsKey(subCandidate)) {
				return candidate;
			}
			candidate = subCandidate;
		}
		return candidate;

	}
	private static String getBaseUrl(String url) {
		if (url.indexOf("/", 12) == -1) {
			url = url + "/";
		}
		Pattern pattern = Pattern.compile("^http://(.*?)\\/");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}

		return "";
	}
	
	public static String getTableName(String url) {
		String name = "num";
		try {
			url = url.replace("http://", "").replace("www.", "");
			String tmp = url.substring(0, 1);
			if (alpha(tmp)) {
				name = tmp;
			}
		} catch (Exception e) {
			
		}
		return name;
	}
	public static String getUrlkey(String host, String rurl) {
		return hsval(host) + "_" + hsval(rurl);
	}
	public static String hsval(String stream) {
		if (null == stream) {
			return "0";
		}
		long code = strcode(stream.hashCode());
		return (code + "").replace("-", "0");
	}
	private static long strcode(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	static Map<String, String> initMp = new HashMap<String, String>();
	static {
		initMp.put("com", "com");
		initMp.put("cn", "cn");
		initMp.put("org", "org");
		initMp.put("net", "net");
		initMp.put("info", "info");
		initMp.put("co", "co");
		initMp.put("so", "so");
		initMp.put("com.cn", "com.cn");
		initMp.put("gov.cn", "gov.cn");
		initMp.put("tv", "tv");
		initMp.put("edu.cn", "edu.cn");
		initMp.put("ccoo.cn", "ccoo.cn");
	}
	
	
	/**
	 * 获取rowkey
	 * @param url
	 * @param sonUrl
	 * @param splipTableName
	 * @return
	 */
	public static String getRowKey(String url,String sonUrl){
		try{
			String host = getHostName(url);
			return getUrlkey(host, sonUrl);	
		}catch(Exception e){
			logger.error("----- getRowKey exception,url="+url+",sonUrl="+sonUrl,e);
		}
		return null;
	}
	/**
	 * 清洗url
	 * @param urlList
	 * @return Map<String,List<String>>
	 */
	public static void insert(List<String> urlList) {
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		for (String url : urlList) {
			String tablename = FirstPageHbaseFilter.getTableName(url);
			String rowKey = FirstPageHbaseFilter.getRowKey(url, url);
			List<String> list = new ArrayList<String>();
			Boolean exsit =  HbaseUtils.isexitFilter(rowKey, tablename);
			if(exsit){
				logger.info("------------url exist, skip.......");
				continue;
			}else{
				if(map.containsKey(tablename)){
					map.get(tablename).add(rowKey);
				}else{
					list.add(rowKey);
					map.put(tablename, list);
				}
			}
		}
		try {
			logger.error("-------------table size:" +map.size());
			logger.error("---------insert hbase start");
			for (String key : map.keySet()) {
				logger.info("insert tablename:"+ key + "------ urlList size :" + map.get(key).size());
				HbaseUtils.insertFilter(map.get(key), key);
			}
			logger.error("-----------All  insert hbase end");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(FirstPageHbaseFilter.getRowKey("http://news.baidu.com", "http://21cbh.media.baidu.com/"));
	}
	/**
	 * 判断是该url否存在于hbase
	 * @param url
	 * @return
	 */
	public static boolean isexit(String url) {
		if(url.indexOf("http://")==-1){
			url = "http://" + url;
		}
		String tablename = FirstPageHbaseFilter.getTableName(url);
		//判断表是否存在
		/*if(!HbaseUtils.exitTable(tablename)){
			HbaseUtils.createTable(tablename);
			return false;
		};*/
		String rowKey = FirstPageHbaseFilter.getRowKey(url, url);
		Boolean exsit =  HbaseUtils.isexitFilter(rowKey, tablename);
		if(exsit){
			logger.info("------------url exist, skip url:"+url);
			return true;
		}else{
			logger.info("------------new url , add url : "+url);
			return false;
		}
	}
	
	
	
	/**
	 * 判断是该url否存在于hbase
	 * @param url
	 * @return
	 */
	public static boolean isexistWx(String url) {
		Boolean exsit =  HbaseUtils.isexitFilter(url, "check_weixin_repetition");
		if(exsit){
			logger.info("------------url exist, skip.......");
			return true;
		}else{
			logger.info("------------new url , add url : "+url);
			return false;
		}
	}
	
	
	/**
	 * 清洗url
	 * @param urlList
	 */
	public static void insertWx(List<String> urlList) {
	
		List<String> insertList = new ArrayList<String>();
		//判断每个文章url是否在hbase的check_weixin_repetition表中
		for (String url : urlList) {
			
			Boolean exsit =  HbaseUtils.isexitFilter(url,tableName );
			if(exsit){
				logger.info("url exist, skip");
				continue;
			}else{
				insertList.add(url);
			}
		}
		
		
		try {
			logger.info("insert hbase start");
			if(CollectionUtils.isNotEmpty(insertList)){
				HbaseUtils.insertFilter(insertList, tableName);
			}
			logger.info("All insert hbase end");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
