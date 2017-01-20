package com.jikexueyuancrm.util;  

import java.io.File;  
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;  
import org.apache.log4j.Logger;

import com.jikexueyuancrm.app.client.JinRiTouTiao;
import com.jikexueyuancrm.controller.AnHui;
import com.jikexueyuancrm.entity.OriginalUrl;
import com.jikexueyuancrm.service.impl.ClientServiceImpl;
import com.jikexueyuancrm.service.impl.ResultServiceImpl;
import com.xd.iis.se.hbutils.MeUtils;

/** 
 * 自定义文件监听器 
 * @author   
 * @date    2010-11-16 
 * @file    org.demo.file.MyFileListener.java 
 */  
public class MyFileListener extends FileAlterationListenerAdaptor{ 
	
	 public static    String tempDir="D:\\tempDir\\";   
   	
       
	 public static       String backupDir="D:\\backup\\";   
       
	
	
	public static String cfg_dir_path = "D:\\config\\";
	
	
	  public static String cfgfile = cfg_dir_path + "crawler_config.cfg";
	  
	
	 
	 
	
	private static Logger log = Logger.getLogger(MyFileListener.class);
	
	private static ClientServiceImpl clientServiceImpl;
	
	
	static {
	
		
		
		try{
			
			 tempDir = getConfigValue("tempDir");
			 
			 backupDir=getConfigValue("backupDir");

		clientServiceImpl= (ClientServiceImpl) SpringXmlCommon.getBean("clientServiceImpl");
		
		}catch (Exception e){
			log.error(e);
			e.printStackTrace();
		     }
		}
	
	
	
	
    @Override  
    public void onFileCreate(File file) { 
    	
        log.info("[新建]:" + file.getAbsolutePath());  
    
    //拷贝saz文件到临时目录并且解析url存入数据库
        try {
			
        
           
           
           
           
        	//解压文件到临时目录
            ArrayList<String> a = ZipUtils.Ectract(file.getAbsolutePath(),  tempDir); // 返回解压缩出来的文件列表 	
            
            
            
            for(String s : a){  
               log.info(s);  
            }  
            
          //备份saz文件(移动)
            
            file.renameTo(new File(backupDir+file.getName()));
            
            //解析raw文件夹里的url
         
            ArrayList<String> appUrls =  ZipUtils.getAppUrl(tempDir+"raw");
            
           log.info(appUrls.size());
         
            
            
            //解析列表页 ，，获取文章
            
            for(        String     topicUrl : appUrls  ){
            	
            	if(!topicUrl.contains("/api/news/feed/v48/?category=")) continue;
            	
            	String[] urlArray = topicUrl.split("\\?");
                String[] urlArgs = urlArray[1].split("&");
            	
                topicUrl=urlArray[0]+"?"+urlArgs[0];//+"&device_id=34678402600";
            	
            	
            		log.info( topicUrl);
            	
            	    //解析出的内容url
            		List<String> urls = new JinRiTouTiao().start(topicUrl);
            		
            		
            		//解析出url说明是列表页
            		if(urls.size()>0){
            			
            			
            			
            		List <OriginalUrl> originalUrls=	clientServiceImpl.findByClient(JinRiTouTiao.name);
            	
            	
            		ArrayList<String> existUrls=new ArrayList<String>();
            		
            		for(  OriginalUrl originalUrl: originalUrls){
            			existUrls.add(originalUrl.getUrl());
            		}
            		
            			
            	
            			if(existUrls.contains(topicUrl) ){
            				
            				log.info("标题url:"+topicUrl+"已存在");
            				//TODO更新数据库记录
            			
            				
            			
            			}else{
            				
            				//将topicUrl存入数据库	
            				log.info("插入新标题url:"+topicUrl);	
            				
            				OriginalUrl originalUrl=new OriginalUrl();
            				originalUrl.setCreateDate(new Date());
            				originalUrl.setOrgwebname(JinRiTouTiao.name);
            				originalUrl.setUrl(topicUrl);
            				clientServiceImpl.saveOriginalUrl(originalUrl)	;
            			}
            			
            			
            		}else{
            			
            			log.info("未解析到标题url");
            		}
            		
            		
            		
            		
            }
            
            
            
            
            
            
            
            
             FileUtils.deleteDir(new File("D:\\tempDir\\"));
      
        	
        	
        	
        	
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
    
    }  
    @Override  
    public void onFileChange(File file) {  
        log.info("[修改]:" + file.getAbsolutePath());  
    }  
    @Override  
    public void onFileDelete(File file) {  
       log.info("[删除]:" + file.getAbsolutePath());  
    }  
    
    
    public static String getConfigValue(String key)
    {
      PropertiesConfiguration pc = null;
      String value = "";
      try
      {
        pc = new PropertiesConfiguration(cfgfile);
        value = pc.getString(key);
      }
      catch (ConfigurationException e)
      {
       log.error(e);
       e.printStackTrace();
      }
      return value;
    }

    
    
}