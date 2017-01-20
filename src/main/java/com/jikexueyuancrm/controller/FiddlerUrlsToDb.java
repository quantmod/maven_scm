package com.jikexueyuancrm.controller;

import java.util.concurrent.TimeUnit;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;

import com.jikexueyuancrm.util.MyFileListener;


public class FiddlerUrlsToDb {
	private static Logger log = Logger.getLogger(FiddlerUrlsToDb.class);	
	
	
	 public static void main(String[] args) throws Exception {  
	        // 监控目录  
	        String rootDir = "C:\\Users\\Administrator\\Desktop\\FiddlerAutoSave";  
	        // 轮询间隔 5 秒  
	        long interval = TimeUnit.SECONDS.toMillis(5);  
	        //   
	        FileAlterationObserver observer = new FileAlterationObserver(  
	                                              rootDir,   
	                                              FileFilterUtils.and(  
	                                               FileFilterUtils.fileFileFilter(),  
	                                               FileFilterUtils.suffixFileFilter(".saz")),   
	                                              null);  
	        observer.addListener(new MyFileListener());  
	        FileAlterationMonitor monitor = new FileAlterationMonitor(interval,observer);  
	        // 开始监控  
	        monitor.start();  
	    }  
	
	 
	 
	 
}
