package com.jikexueyuancrm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;


import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.util.Cookie;

public class CookieUtils {
	

	public static String GETCOOKIEBASEURL="http://weixin.sogou.com/weixin?type=1&ie=utf8&_sug_=y&_sug_type_=&query=";
	private static Logger log = Logger.getLogger(CookieUtils.class);
	//cookie列表
	public static LinkedList<Set<Cookie>> cookiePool = new LinkedList<Set<Cookie>>();
	//cookie列表大小(本地测试最多40)
	public final static int poolSize =10;
	
	
	public  static String getRandomString(){
        String[]  chars = new String[] { "a","b","c","d","e","f","g",
						        		"h","i","j","k","l","m","n",
						        		"o","p","q","r","s","t",
						        		"u","v","w","x","y","z",
						        		"A","B","C","D","E","F","G",
						        		"H","I","J","K","L","M","N",
						        		"O","P","Q","R","S","T",
						        		"U","V","W","X","Y","Z",
						        		"1","2","3","4","5","6","7","8","9"};
	
      ArrayList<String> list=new ArrayList<String>();
      for( String string:chars){
    	  list.add(string);
      }
        //打乱集合
        Collections.shuffle(list, new Random());
        
        StringBuilder    stringBuilder =new StringBuilder();
        
        for(String string:list.subList(0, 5)){
        	stringBuilder.append(string);
        }
     
      return stringBuilder.toString();
	}
	
	

	
	
	

	
	
	 //获取cookie ,如果没有返回null
	public synchronized static Set<Cookie> getCookieFromPool() {
				Set<Cookie> cookie = cookiePool.pollFirst();
				return cookie;
	}


	
	
	
	//更新cookie池
	
	  public synchronized static void updateCookiePool()  {
		cookiePool.clear();
		int i=0;//获取到的cookie个数
		int j=0;//获取cookie失败的次数
		
		while(i<poolSize){
			Set<Cookie> cookie = getSouGouWxCookie();
			try {
				// 获取到的话
				if (cookie != null) {
					i++;
					log.info("i=" + i + " cookie:" + cookie);
					cookiePool.add(cookie);
					Thread.sleep(20000 + (int) (Math.random() * 5000));
				} else {// 获取不到的话长时间休息
					j++;
					log.error("no cookie, sleep " + j + "h ");
					Thread.sleep(j * 3600 * 1000);
				}
			} catch (Exception e) {
               log.error(e);
               e.printStackTrace();
			}
		}
		
		log.info("update cookie pool success");	
	}
	
	

	
	
	public static Set<Cookie>  getSouGouWxCookie(){
		
		Set<Cookie> cookie	= HttpUtils.getCookie(GETCOOKIEBASEURL+getRandomString(),"http://weixin.sogou.com/","SUID","SNUID","SUV");
	
	return cookie;
	
	}
	
	
	
	
	
	
	public static void main(String[] args) {
//测试微信cookie		
	//	Set<Cookie> cookie = HttpUtils.getCookie(GETCOOKIEBASEURL+getRandomString(),"http://weixin.sogou.com/","SUID","SNUID","SUV");
	
		//log.info(cookie);
		updateCookiePool();
		
		
		Set<Cookie> cookieFromPool = getCookieFromPool();
		
		log.info(cookieFromPool);
		
		
		log.info(getRandomString());
		log.info(getRandomString());
		log.info(getRandomString());
		log.info(getRandomString());
	}
	

	
	

}
