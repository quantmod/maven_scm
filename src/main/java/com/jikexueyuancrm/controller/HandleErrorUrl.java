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
import com.jikexueyuancrm.entitycommon.ErrorReqUrlStat;
import com.jikexueyuancrm.pojo.HttpWrapper;
import com.jikexueyuancrm.service.impl.ErrorReqUrlStatImpl;
import com.jikexueyuancrm.service.impl.ErrorUrlServiceImpl;
import com.jikexueyuancrm.util.DeadlockConsoleHandler;
import com.jikexueyuancrm.util.DeadlockDetector;
import com.jikexueyuancrm.util.FileNameUtils;
import com.jikexueyuancrm.util.FileUtils;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.PageUtils;
import com.jikexueyuancrm.util.SpringXmlCommon;

public class HandleErrorUrl {
	private static  final HashSet<String> normalDeleteText=new HashSet<String>();

static{
	//加载过滤主题关键字
	
		FileUtils.loadFile2Set("/normalDeleteText", normalDeleteText);
}	
	

	
	
	public static class CrawlThread extends Thread {
		
		

		private ErrorUrl errorUrl;

		public CrawlThread(ErrorUrl error) {
			this.errorUrl=error;
		}

		@Override
		public void run() {
			try{	

			log.info(errorUrl.getUrl());
			
			//探测网页得到返回码
			String url=HttpUtils.regulateUrl(errorUrl.getUrl());
			
			
		HttpWrapper httpWrapper = HttpUtils.getStatusCodeByHtmlUnit(true, url, null, null, null);
	   
		//如果是TextPage则使用httpclient发起请求
		if(httpWrapper.getCode()==999){
		//httpclient发起请求	
			httpWrapper=HttpUtils.getStatusCodeByHttpClient(url);
		}
		
		
			errorUrl.setNewcode(httpWrapper.getCode());
			if(httpWrapper.getContent()!=null){
			errorUrl.setContent(httpWrapper.getContent());
			}
	       
			if(httpWrapper.getExpMsg()!=null){
				errorUrl.setExpMsg(httpWrapper.getExpMsg());
				}
			
		errorUrl.setIshandled(1);	
		errorUrl.setClient(httpWrapper.getClient());
			//更新数据库记录
		errorUrl.setUpdateTime(new Date());
		
		errorUrlServiceImpl.updateErrorUrl(errorUrl);
			}catch (HibernateJdbcException e){
				log.info(e.getMessage(), e);
				e.printStackTrace();
				//解决这个异常:Incorrect string value: '\xF2\xBF\xAA\xB5\xEF\xBF...' for column 'content' at row 1
			
			if(e.getRootCause().toString().contains("Incorrect string value:")&&e.getRootCause().toString().contains("for column 'content' at row 1")){
				errorUrl.setContent(e.getClass().getName()+":"+e.getMessage());
				
				errorUrlServiceImpl.updateErrorUrl(errorUrl);
			}
			
			}catch(Exception e){
				log.info(e.getMessage(), e);
				log.info("包名"+":::"+e.getClass().getPackage().getName());
				e.printStackTrace();
			
			}	
		}

	}







	private static Logger log = Logger.getLogger(HandleErrorUrl.class);
	
	


	

private  static	ErrorUrlServiceImpl errorUrlServiceImpl;



private  static	ErrorReqUrlStatImpl src;

public static String urlRegex="(http(s)?://)\\w+(\\.\\w+)+((:\\d{1,5})|)(/\\w*)*(/\\w+\\.(\\w+|))?([\\w- ./?%&=]*)?";


private static String contentRegex="content_\\d+\\.";








public final	static ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();


private static String currentDir;

public static String directory;
static {
	
	//启动死锁检测线程
	DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(HandleErrorUrl.class.getName()), 30, TimeUnit.MINUTES);
	deadlockDetector.start();
	

	//common-logging自带日志实现类。它实现了Log接口。 其输出日志的方法中不进行任何操作。
	LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",    "org.apache.commons.logging.impl.NoOpLog");
	 
	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
	    .setLevel(Level.OFF);
	 
	java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
	    .setLevel(Level.OFF);
	
	try{

	errorUrlServiceImpl= (ErrorUrlServiceImpl) SpringXmlCommon.getBean("errorUrlServiceImpl");
	
	
	
	
	
	src= (ErrorReqUrlStatImpl) SpringXmlCommon.getBean("errorReqUrlStatImpl");
	//获取绝对目录  //D:/ImportantNewsWebsite/lib/test.jar
 directory=HandleErrorUrl.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	log.info(directory);
	directory=directory.substring(0,directory.lastIndexOf("/"));
	directory=directory.substring(0,directory.lastIndexOf("/"));
	currentDir=directory+"/";
	directory+="/"+"Result"+"/";
	
	}catch (Exception e){
		e.printStackTrace();
	     }
	}








static final  int  threadNum=2;


//sh crawler.sh start  2016-08-09启动程序    位置参数传递需要中间变量
public static void main(String[] args) {
	
	final ExecutorService pool = Executors.newFixedThreadPool(threadNum);
	
	
	final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
	
	//迁移数据   参数形式2016-08-12
	copyTableData(args[0]);
	
	
	
	timer.scheduleWithFixedDelay( new Runnable() {
		
		public void run() {
			
			try{
			
				//设置状态码和线程数
				 List<ErrorUrl> list=	errorUrlServiceImpl.getByCodeAndThreadNum(404, threadNum);
				
				if(list==null||list.size()==0){
				return;	
				}
			
				ArrayList<Future<?>> futureList=new ArrayList<Future<?>>();
				for( ErrorUrl errorUrl:list){
				
				CrawlThread thread =new CrawlThread(errorUrl);
				Future<?> future  = pool.submit(thread);
				futureList.add(future);
				}
				
				
				for( Future<?>  future: futureList){
						try {
							future.get();
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
				
				
				
			
			}catch (Exception e){
				
			e.printStackTrace();
		}
		
		}
	}, 0, 2, TimeUnit.SECONDS);
	
	
	//根据newcode>299删除记录并写入excel
final ScheduledExecutorService timerDay = Executors.newScheduledThreadPool(1);
	
	timerDay.scheduleWithFixedDelay( new Runnable() {
		
		public void run() {
			
			try{
				log.info("start write excel!");
				
			//处理200不存在的url
				List<ErrorUrl> noramlList = errorUrlServiceImpl.getList();		
				
				List<ErrorUrl> toDeleteList= new ArrayList<ErrorUrl>();
				for( ErrorUrl  normal:noramlList){
					if(normal.getContent()!=null){
						
					for( String deleteText: normalDeleteText){
					
						if(normal.getContent().contains(deleteText)){
						toDeleteList.add(normal);
						}
					}		
					}
				
					
				}
				
				handleErrorUrl(toDeleteList);
				
		////---------------------
				
				
				
			while(true){
				
			
			List<ErrorUrl> dblist = errorUrlServiceImpl.getList(1000);	
			
			if(dblist!=null&&dblist.size()>0){
				
				
						handleErrorUrl(dblist);
				
			
			}else{
				log.info("no url to delete");
				break;
				
			}
			}
			
			}catch (Exception e){
				
			e.printStackTrace();
		}
		
		}

		
		public void handleErrorUrl(List<ErrorUrl> dblist) {
			LinkedHashMap<Integer ,LinkedHashMap<String ,ArrayList<ErrorUrl>>> codeMap=new 	LinkedHashMap<Integer ,LinkedHashMap<String ,ArrayList<ErrorUrl>>>();
							
			
						for( ErrorUrl  errorUrl:dblist){
							
							
							if(codeMap.containsKey(errorUrl.getCode())){
								
					LinkedHashMap<String ,ArrayList<ErrorUrl>>	 crawlNameMap=	codeMap.get(errorUrl.getCode());
					
									if(crawlNameMap.containsKey(errorUrl.getCrawlname())){
										
										
										crawlNameMap.get(errorUrl.getCrawlname()).add(errorUrl);
										
									}else{
										
										ArrayList<ErrorUrl>	list=new ArrayList<ErrorUrl>(); 
										list.add(errorUrl);
										crawlNameMap.put(errorUrl.getCrawlname(),list);
									}
									
						}else{
							
							//如果不包含状态码
							ArrayList<ErrorUrl>	list=new ArrayList<ErrorUrl>(); 
							list.add(errorUrl);
							LinkedHashMap<String ,ArrayList<ErrorUrl>>	 crawlNameMap=	new LinkedHashMap<String ,ArrayList<ErrorUrl>>();
							crawlNameMap.put(errorUrl.getCrawlname(), list);
							codeMap.put(errorUrl.getCode(), crawlNameMap);
							
						}
						
							
						
							
					     }	
						
						
						
					//按周与状态码写入excel
					
						
						String week =FileNameUtils.getFileNameByWeek();
						
						
						//按照爬虫名字写入
						
						for(  Integer codekey:codeMap.keySet()){
							
						        for (String   crawlname: codeMap.get(codekey).keySet()){
						        	
						        	//相同keycode,相同crawlname的集合
						        	
						        	ArrayList<ErrorUrl> list=        	codeMap.get(codekey).get(crawlname);
						        	
						        	FileUtils.appendToExcelUrl(directory,week+"_" +codekey+".xls", crawlname, list);
						        }
							
				
					
						}
						
						
						
					//update数据库
						for(ErrorUrl ls:dblist){
							
							ls.setIsdeleted(1);
							ls.setUpdateTime(new Date());
						}
					
						
						errorUrlServiceImpl.updateList(dblist)	;
		}
		
		
	}, 0, 5, TimeUnit.MINUTES);
	
	
	
	
	
	
	
	
	
}


private final static int pageSize = 10000;
public static void copyTableData(String date) {
	
	
	
	log.info(date);
	int totalCount =  src.getCount(404,date).intValue();
	
	
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
			errorUrlServiceImpl.saveResult(errorUrl);
		}
			
	}
	
	
	log.info("copy table finished!");
	
}







	

}
