package com.jikexueyuancrm.util;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ProvinceUtils {

	private static Logger log = Logger.getLogger(ProvinceUtils.class);
	
	
	public static String getProvince(String url) {
		String province=null;
		try{
	
		String icpUrl="http://icp.alexa.cn/index.php?q=";
		icpUrl=icpUrl+url;
		
		String html=HttpUtils.getHtmlByHtmlUnit(0,false, icpUrl, null, null,null);
		
		
		if(html==null){
			return null;
		}
		
		
		Document doc=Jsoup.parse(html);
		
		province=doc.select("table.infoTab  tr").get(2).select("td").text().trim().substring(0,1);
		
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
		log.info(province);
		return province;

	}
}
