package com.jikexueyuancrm.app.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





import com.jikexueyuancrm.util.HttpUtils;




public class JinRiTouTiao implements ClientNewsCrawler{
	
	private static Logger log = Logger.getLogger(JinRiTouTiao.class);
	
	/**
	 * 抓取今日头条客户端
	 * @param url
	 * @param jinritoutiaoCapturesite
	 * @param origintypeWz
	 * @param jsOff
	 */

public	final static String name="今日头条客户端";
	
	

	@Override
	public void getContent(List<String> urlList, String captureWebsite,
			String originType, Boolean jsStatus) {
	
		
	}

	@Override
	public List<String> getUrlList(String url) {
		
		
		
		ArrayList<String> lists=new ArrayList<String>();
		
	String json=	HttpUtils.getJsonByHtmlUnit(url, null, null, null);
   if(json!=null)	{

	   
	   JSONObject jsonObject = JSONObject.fromObject(json);	
		
	   
	   if( jsonObject.get("data")!=null){
		   JSONArray contents= jsonObject.getJSONArray("data");
		
		for (int i=0;i<contents.size();i++){
			
			JSONObject	content=	contents.getJSONObject(i);
			
		String	display_url	=	(String) ((JSONObject) content.get("content")).get("display_url");
		log.info("display_url:"+display_url);
		
		if(display_url!=null){
			
			
			if(display_url.contains("toutiao.com")){
				lists.add(display_url);
				}	
			
			
			
				
		}else{
			
			String	share_url	=	(String) ((JSONObject) content.get("content")).get("share_url");
			log.info("share_url:"+share_url);
			if(share_url!=null&&share_url.contains("toutiao.com")){
				lists.add(share_url);
				}		
		}
		
		
		
		
		
		}
	
		
		
		
	   }
		
	}	
	
	return lists;
		
		
	}
	
	
	

	
	public List<String> start(String url){
	
		
		List<String> urlList = getUrlList(url);
	
		
		return urlList;
	}

}
