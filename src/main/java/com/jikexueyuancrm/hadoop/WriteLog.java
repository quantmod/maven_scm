package com.jikexueyuancrm.hadoop;

            import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

            import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

            
            
            
           /*
            * 
            *将log4j日志直接写入到flume
            * 
            *log4j配置文件
            log4j.rootLogger=INFO,flume
            log4j.appender.flume = org.apache.flume.clients.log4jappender.Log4jAppender
            log4j.appender.flume.Hostname = localhost
            log4j.appender.flume.Port = 41414
            log4j.appender.flume.UnsafeMode = true
            log4j.appender.flume.layout=org.apache.log4j.PatternLayout
			log4j.appender.flume.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %p [%c:%L] - %m%n
                                    
           	  flume agent配置文件  flume-conf.properties                          
            a1.sources = r1
			a1.sinks = k1
			a1.channels = c1
			 
			# Describe/configure the source
			a1.sources.r1.type = avro
			a1.sources.r1.bind = localhost
			a1.sources.r1.port =  41414                       
                                    代码如下 */
            
import org.apache.log4j.PropertyConfigurator;

            import java.util.Date;

            import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


            public class WriteLog {
            	

            	
            	static{
            		
            		
            		//使用自定义文件名log4j配置文件,log4j默认的相对路径是工程下面，非src或者bin。
            		
            		//本例用绝对路径
            		PropertyConfigurator.configure("/home/smgadmin/test/java/conf/log4j-flume.properties");
            	
            		
            		
            	}
            	
            	 protected static final Log logger = LogFactory.getLog( WriteLog.class);
            	
               

                public static void main(String[] args) throws InterruptedException {
                    // TODO Auto-generated method stub
                    while (true) {
                        // 每隔两秒log输出一下当前系统时间戳
                        logger.info(new Date().getTime());
                        Thread.sleep(2000);
                        try {
                            throw new Exception("exception msg");
                        }
                        catch (Exception e) {
                            logger.error("error:" + e.getMessage());
                        }
                    }
                }
            }