package com.jikexueyuancrm.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.ImageUtils;

public class PictureCrawl {
	
	
	private static Logger log = Logger.getLogger(PictureCrawl.class);
	
	public static String refer="http://xorx.cf/index.php?u=335041&vcencode=8385148061";
	//列表页
	public static String seedUrl="http://xorx.cf/thread0806.php?fid=2&search=&page=";
	public static void main(String[] args) {
		
	
		
		for(int i=1;i<=1;i++){
			
			
		   crawlUrlByList(seedUrl+i);
			   
		   }
			
		}
		
		
		

	

	private static void crawlUrlByList(String url) {
		
		String html = HttpUtils.getHtmlByHtmlUnit(0,true, url, null, refer, null);
		
		
		if(html==null)  return;
		log.info(html);
		org.jsoup.nodes.Document doc=Jsoup.parse(html);
		
	List<Element> hrefs=	doc.select("tr[class=tr3 t_one]  h3 > a");
	
	hrefs=hrefs.subList(5, hrefs.size());
	
	
	log.info(hrefs.size());
	
	for(  Element a:hrefs){
		
		
	getImgsByUrl(url.substring(0,url.lastIndexOf("/")+1)+	a.attr("href").trim());
		
		
	}
		
		
	}






	private static void getImgsByUrl(String url) {
		
	log.info(url);
	String html = HttpUtils.getHtmlByHtmlUnit(0,true, url, null, null, null);
	
	
	if(html==null)  return;
	log.info(html);
	org.jsoup.nodes.Document doc=Jsoup.parse(html);
	
	
	
	
	 List<Element> imgs =doc.select("div[class=tpc_content do_not_catch] img");
	 
	/* if(imgs.size()>6){
		 imgs=imgs.subList(0, 5);
	 }*/
	 
	 for(Element img:imgs){
		 
		String imgUrl= img.attr("src").trim();
		if(!imgUrl.endsWith(".jpg") && !imgUrl.endsWith(".JPG"))  continue;
	log.info(imgUrl);	
		//保存文件
		ImageUtils.writeImageToDiskFromUrl(imgUrl, "E:\\1\\images\\");
	 }
	 
	 
	 
	}

}
