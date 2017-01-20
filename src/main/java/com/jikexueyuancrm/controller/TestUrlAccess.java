package com.jikexueyuancrm.controller;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.seleniumhq.jetty7.util.log.Log;

import com.jikexueyuancrm.pojo.HttpWrapper;
import com.jikexueyuancrm.util.FileUtils;
import com.jikexueyuancrm.util.HttpUtils;

public class TestUrlAccess {
	
	
	public static String directory;
	
	public static String currentDir;
	private static Logger log = Logger.getLogger(TestUrlAccess.class);
	
	static {
		
		
		directory=TestUrlAccess.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		directory=directory.substring(0,directory.lastIndexOf("/"));
		directory=directory.substring(0,directory.lastIndexOf("/"));
		currentDir=directory+"/";
		
	}
	

	
	
	public static void main(String[] args) throws Exception {
		final LinkedHashMap<String ,String> urls=new LinkedHashMap<String ,String>();

		FileUtils.loadFile2Map(true, "/index_1lt", urls);
		
		log.info("");
		
		for(   String    url: urls.keySet()){
			
			HttpWrapper httpWrapper = HttpUtils.getStatusCodeByHtmlUnit(true, url, null, null, null);
			
			if(httpWrapper.getCode()>=300){
				
				log.info(url);
				FileUtils.appendToFile(currentDir, "check", httpWrapper.getUrl(),String.valueOf(httpWrapper.getCode()),httpWrapper.getExpMsg()==null?"":httpWrapper.getExpMsg());
			}
		
			
			
			
			
			
			
			
		}
		
		
		
		
	
	}
	

}
