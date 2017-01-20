package com.jikexueyuancrm.test;
import java.util.concurrent.TimeUnit;  

import org.apache.commons.io.filefilter.FileFilterUtils;  
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;  
import org.apache.commons.io.monitor.FileAlterationObserver;  

import com.jikexueyuancrm.util.MyFileListener;
public class FileMonitorTest {  
  
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        // 监控目录  
        String montiorDir = "C:\\Users\\Administrator\\Desktop\\FiddlerAutoSave";  
        
        montiorDir=MyFileListener.getConfigValue("montiorDir");
        
        // 轮询间隔 5 秒  
        long interval = TimeUnit.SECONDS.toMillis(3);  
        //   
        FileAlterationObserver observer = new FileAlterationObserver(  
                                              montiorDir,   
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