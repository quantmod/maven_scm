package com.jikexueyuancrm.app.client;

import java.util.List;

public interface ClientNewsCrawler {
	

	/**
	 * 获取抓取内容
	 * @param urlList
	 */
	public void getContent(List<String> urlList,String captureWebsite,String originType,Boolean jsStatus);
	
	/**
	 * 获取抓取urlList
	 * @param url
	 * @return urlList
	 */
	public List<String> getUrlList(String url);
	
	
	

}
