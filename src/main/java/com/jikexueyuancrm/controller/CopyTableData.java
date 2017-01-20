package com.jikexueyuancrm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate4.HibernateJdbcException;

import com.jikexueyuancrm.entity.ErrorUrl;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.entitycommon.ErrorReqUrlStat;
import com.jikexueyuancrm.pojo.HttpWrapper;
import com.jikexueyuancrm.service.impl.ErrorReqUrlStatImpl;
import com.jikexueyuancrm.service.impl.ErrorUrlServiceImpl;
import com.jikexueyuancrm.util.DeadlockConsoleHandler;
import com.jikexueyuancrm.util.DeadlockDetector;
import com.jikexueyuancrm.util.FileUtils;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.PageUtils;
import com.jikexueyuancrm.util.SpringXmlCommon;

public class CopyTableData {
	



	private static Logger log = Logger.getLogger(CopyTableData.class);
	
	


	

private  static	ErrorUrlServiceImpl dest;

private  static	ErrorReqUrlStatImpl src;








public final	static ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();


private static String currentDir;

public static String directory;
static {
	
/*	//启动死锁检测线程
	DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(CopyTableData.class.getName()), 30, TimeUnit.MINUTES);
	deadlockDetector.start();*/
	

	//common-logging自带日志实现类。它实现了Log接口。 其输出日志的方法中不进行任何操作。
	LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",    "org.apache.commons.logging.impl.NoOpLog");
	 
	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
	    .setLevel(Level.OFF);
	 
	java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
	    .setLevel(Level.OFF);
	
	try{

	dest= (ErrorUrlServiceImpl) SpringXmlCommon.getBean("errorUrlServiceImpl");
	
	
	src= (ErrorReqUrlStatImpl) SpringXmlCommon.getBean("errorReqUrlStatImpl");
	
	
	//获取绝对目录  //D:/ImportantNewsWebsite/lib/test.jar
 directory=CopyTableData.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	log.info(directory);
	directory=directory.substring(0,directory.lastIndexOf("/"));
	directory=directory.substring(0,directory.lastIndexOf("/"));
	currentDir=directory+"/";
	directory+="/"+"Result"+"/";
	
	}catch (Exception e){
		e.printStackTrace();
	     }
	}







private final static int pageSize = 10000;	

public static void main(String[] args) {
	
	
	
	
	 
	
	int totalCount =  src.getCount(404,"2016-11-01").intValue();
	
	
	log.info("totalCount:"+totalCount);
	if (totalCount == 0) {
		
		return;
	}
	
	//计算总页数开始分页循环查询和写入数据
	int totalPage=PageUtils.getTotalPage(totalCount, pageSize);
	
	log.info("totalPage:"+totalPage);
	for(int currentPage=1;currentPage <=totalPage ;currentPage ++){
		
		List<ErrorReqUrlStat> list = src.getListByPage((currentPage-1)*pageSize,pageSize,404,"2016-11-01");
		
		
		if(list==null||list.size()==0) break;
		
		log.info(list.size());
		
		for( ErrorReqUrlStat log:list ){
			ErrorUrl errorUrl=new ErrorUrl();
			errorUrl.setUrl(log.getUrl());
			errorUrl.setInsertDateTime(log.getInsertDatime());
			errorUrl.setTimes(String.valueOf(log.getFailureCount()));
			errorUrl.setCode(log.getHttpStatus());
			errorUrl.setCrawlname(log.getCrawlerName());
			errorUrl.setType(log.getCrawlerType());
			errorUrl.setClient("HtmlUnit");
			dest.saveResult(errorUrl);
		}
			
	}
	
	
	log.info("copy table finished!");
	
}








	

}
