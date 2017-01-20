package com.jikexueyuancrm.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wanghaomiao.xpath.model.JXDocument;

import com.jikexueyuancrm.common.Constants;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.service.impl.ResultServiceImpl;
import com.jikexueyuancrm.util.DeadlockConsoleHandler;
import com.jikexueyuancrm.util.DeadlockDetector;
import com.jikexueyuancrm.util.EMailUtils;
import com.jikexueyuancrm.util.FileNameUtils;
import com.jikexueyuancrm.util.FileUtils;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.ProvinceUtils;
import com.jikexueyuancrm.util.SpringXmlCommon;
import com.xd.iis.se.hbutils.MeUtils;

public class MidSizeNewsWebsite {

	
	
	private static  AtomicInteger  count=new AtomicInteger(0);

	
	
	private static Logger log = Logger.getLogger(MidSizeNewsWebsite.class);
	
	

	//去重url集合
	private static HashSet<String> deReplication=new HashSet<String>();
	
	//过滤特殊域名
	private static HashSet<String> filterDomain=new HashSet<String>();
	
	
	private static HashSet<String> fuzzyMatchingFilterKeyword=new HashSet<String>();
	//result结果结合
	private static ArrayList<Result> list=new ArrayList<Result>();

	
	public static int startIndex=1;

private  static	ResultServiceImpl resultServiceImpl;

public static String serverId;
public static String urlRegex="(http(s)?://)\\w+(\\.\\w+)+((:\\d{1,5})|)(/\\w*)*(/\\w+\\.(\\w+|))?([\\w- ./?%&=]*)?";


private static String contentRegex="content_\\d+\\.";


public static LinkedHashMap<String ,String> midSizeWebSite=new LinkedHashMap<String ,String>();

public static LinkedHashMap<String ,String> provinceShort=new LinkedHashMap<String ,String>();

public static LinkedHashMap<String ,String> manualSection=new LinkedHashMap<String ,String>();


public static LinkedList<Result>  linkedList=new LinkedList<Result>();

public static LinkedHashMap<String ,String> noUrlFindWebSite=new LinkedHashMap<String ,String>();

public final	static ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
public static HashSet<String>	specialDomain  =new HashSet<String>();
public static HashMap<String,String>	specialClass  =new HashMap<String,String>();

private static String currentDir;
public static HashSet<String>	needJsUrl  =new HashSet<String>();
public static String directory;
static {
	log.info(System.getProperty("file.encoding"));
	//启动死锁检测线程
	DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(MidSizeNewsWebsite.class.getName()), 30, TimeUnit.MINUTES);
	deadlockDetector.start();
	
	//common-logging自带日志实现类。它实现了Log接口。 其输出日志的方法中不进行任何操作。
	LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",    "org.apache.commons.logging.impl.NoOpLog");
	 
	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit")
	    .setLevel(Level.OFF);
	 
	java.util.logging.Logger.getLogger("org.apache.commons.httpclient")
	    .setLevel(Level.OFF);
	
	try{

	resultServiceImpl= (ResultServiceImpl) SpringXmlCommon.getBean("resultServiceImpl");
	
	Properties pro = MeUtils.getProperties("/crawler-param.properties");
	serverId = pro.getProperty("serverId");
	
	log.info(serverId);
	
	//加载中型网站
	FileUtils.loadFile2Map(true,"/index_midSize",midSizeWebSite);
	
	//加载省份简称
	FileUtils.loadFile2Map(true,"/ProvinceShort",provinceShort);
	 
	//加载过滤域名
	FileUtils.loadFile2Set("/filterDomain", filterDomain);
	
	
	//需要js解析的url
	FileUtils.loadFile2Set("/needJsUrl", needJsUrl);
	//加载过滤主题关键字
	
	FileUtils.loadFile2Set("/fuzzyMatchingFilterKeyword", fuzzyMatchingFilterKeyword);
	
	
	//加载特殊class提取标签
	
	FileUtils.loadFile2MapBySymbol("--","/specialClass",specialClass);
	
	//加载手工填写的标题
	FileUtils.loadFile2Map(false,"/manual",manualSection);
	
	
	
	//获取绝对目录  //D:/ImportantNewsWebsite/lib/test.jar
 directory=MidSizeNewsWebsite.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	log.info(directory);
	directory=directory.substring(0,directory.lastIndexOf("/"));
	directory=directory.substring(0,directory.lastIndexOf("/"));
	currentDir=directory+"/";
	directory+="/"+MidSizeNewsWebsite.class.getSimpleName()+"/";
	

	

	
	/*for(;;){
	
	}
	*/
	}catch (Exception e){
		e.printStackTrace();
	     }
	}

static int retryTime=0;



private static int stopLevel=2;




public static void main(String[] args) {
	
	if(args!=null&&args.length==2){
		
		midSizeWebSite.clear();
		midSizeWebSite.put(args[0], args[1]);
	}
	
	final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
	
	timer.scheduleWithFixedDelay( new Runnable() {
		
		public void run() {
			
			try{
		
				
				  //看文件是否存在，如果已存在读取
	              File file=new File(currentDir+"index");
	              
	  		    if(file.exists()){
	  		    startIndex=Integer.valueOf(FileUtils.readIndex(file));
	  		    
	  		    }
	  		  	
	  		    if(startIndex==1){
	  		    	
	  		    //删除结果目录
					setUp();	
	  		    }else{
	  		    	
	  		    	startIndex=startIndex+1;
	  		    }
				
	  		    
	  		    
	  		    
	  		    log.info("index:"+startIndex);	
				
				
			for(int i=startIndex;i<=midSizeWebSite.entrySet().size();i++){ 
				
				Entry<String,String > entry=(Entry<String, String>) midSizeWebSite.entrySet().toArray()[i-1];
				//中国经济导报老式网页解析，只需要迭代一层
				if("http://www.fawan.com/".equals(entry.getKey())){
					
					String html=HttpUtils.getHtmlByHtmlUnit(0,true,entry.getKey(), null, null,null);
					
					if(html==null){
						continue;
					}
					
					Document document = Jsoup.parse(html);
					
					Elements elements = document.select("ul.main_navigation_ul > li");
					
					
					for(Element   li:elements){
						
					//onclick="window.open('http://www.fawan.com/sports/');"	
					String url=	li.attr("onclick").trim();
					
					if(url.length()>16){
					url=url.substring(13,url.length()-3);
					
						String section=li.text().trim();
						Result result=new Result();
						result.setUrl(url);
						result.setLevel(1);
						result.setSeedUrl(entry.getKey());
						result.setSection(section);
						//对于gb2312编码处理一下防止乱码
						try{
							result.setGb2312Text(new String(section.getBytes("gb2312"),"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
							result.setGb2312Text("转化出错");
						}
						
						result.setType("wz");
						list.add(result);
					}	
						
						}
					
				}
				else
				
				{
		    	crawlCommon(i,entry.getKey(),entry.getValue());
				}
				
				
				
				//如果因为网络异常未抓取导数据,则休息重试
				if(list.size()==0){
					//重试超过次数
					if(retryTime>=3){
						noUrlFindWebSite.put(entry.getKey(), entry.getValue());
						retryTime=0;
						//未抓取到url的网站追加写入到文件noUrlFindWebSite
				    	FileUtils.write2file(directory,"noUrlFindWebSite",noUrlFindWebSite);
				    	noUrlFindWebSite.clear();
				    	
					}else{
						
						Thread.sleep(60000);
						i--;
						retryTime++;
						log.info(entry.getKey()+"--retryTime:"+retryTime);	
					}
				}else{
					
					//成功后 retryTime也要置为0
					retryTime=0;
					handleResult(i, entry.getKey(), entry.getValue());
				}
				
		    	
		    	//最后清理工作
		    	cleanUp();
		    	//提醒回收垃圾，并不马上回收
		    	 System.gc();
		    	 //写索引位
		    	
		    	 FileUtils.writeIndex(file,String.valueOf(i+1));
		    	 
			}
			//一次更新结束
	    	//发送邮件
			  startIndex=1;
			EMailUtils.sendMail("jethai@126.com","jethai", "csrhit7293380", new String[]{"yuanhai@miduchina.com","lixue@miduchina.com","jintai@miduchina.com","junjun@miduchina.com"}, "Mid_Result", "中型新闻网站列表页爬取结果见附件！", new String[]{directory+"mid_deltaResult_"+week+".xls",directory+"mid_allResult_"+week+".xls" }, null);
			
	    	log.info("loop   end!!!!!!!");
	    	//删除index文件
			
			 if(file.exists()){
				file.delete(); 
			 }
			 
		}catch (Exception e){
			e.printStackTrace();
		}
		
		}
	}, 0, 7, TimeUnit.DAYS);
	
}



public  static void crawlCommon(int i,String key ,String value) {
	
	
	long start =System.currentTimeMillis();
	
	
	
	
	 getNavUrl(null,key,0);
	
	
		
		//轮询linkedList,开启一个线程
	
	while(!linkedList.isEmpty()){
		
	final Result first=	linkedList.removeFirst();
	
	if(first.getLevel()>=stopLevel)          break;
	
	Future<?> future = newSingleThreadExecutor.submit(new Runnable() {
		
		@Override
		public void run() {
			
		getNavUrl(first.getSeedUrl(),  first.getUrl(),first.getLevel());
		}
	});
	
	  
	try {
		future.get();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	

	
	
/*	Pattern  pattern=Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(html);
		
		while(matcher.find()){
			String url=matcher.group();
			 url=	url.replace("&quot", "").trim();  
			 
			 url=	url.replace("&amp", "").trim();  
			if( homePageUrlFilter(url,seedUrl)){
			arrayList.add(url);
			}
		}*/
	long end =System.currentTimeMillis();
	log.info("handler time "+(end-start)/1000+"s");	
	
}






//处理结果
private  static void handleResult(int i, String key, String value)
		throws Exception {
	//查询省份
	
			String province=ProvinceUtils.getProvince(key);
			
			
			province=provinceShort.get(province);
			
			if(province==null){
				province="北京";
			}
	
	//添加上根站点
	for(  Result result: list){
	
		result.setRootUrl(key);
		result.setRootSection(value);
		result.setProvince(province);
		result.setCreateTime(new Date());
		log.info(result);
	}
	//加入种子
	Result rootResult=new Result();

	rootResult.setUrl(key);
	rootResult.setLevel(0);
	rootResult.setSeedUrl(key);
	
	rootResult.setSection(value);
	
	rootResult.setGb2312Text(value);
	
	rootResult.setType("wz");
	rootResult.setRootUrl(key);
	rootResult.setRootSection(value);
	rootResult.setProvince(province);
	rootResult.setCreateTime(new Date());
	list.add(rootResult);
	log.info(list.size());
	
	//结果存入文件
	
	FileUtils.write2file(i,directory,key,list);//多个文件
	FileUtils.appendToFile(directory,"allResult", list);//一个文件
	
	//结果转成excel文件
	FileUtils.saveAsExcel(i,directory,key,list);
	FileUtils.appendToExcel(directory,"mid_allResult_"+week+".xls", list);
	
	//写入数据库
	resultServiceImpl.saveResult(list);
	
//写入增量数据	
	FileUtils.appendToFile(directory,"deltaResult", ResultServiceImpl.newUrl);
	FileUtils.appendToExcel(directory, "mid_deltaResult_"+week+".xls",ResultServiceImpl.newUrl);
//创建noUrlFindWebSite文件
	FileUtils.write2file(directory,"noUrlFindWebSite",noUrlFindWebSite);
}


public static String week=FileNameUtils.getFileNameByWeek();


//提取url
public static void getNavUrl(String refer,String seedUrl,int level){
	level++;
	if(level>stopLevel) return;
	
	if( filterDomain.contains(getDomain(seedUrl))||filterDomain.contains(seedUrl)){
		return;
	}
	
	//多语言网站,替换成中文访问路径
	if("http://www.china.com/".equals(seedUrl)){
		seedUrl="http://www.china.com/index.html";
	}
	
	

	Document doc =null;
	String html=null;
	
	//使用jsoup解决htmlunit解析http://www.beelink.com/出现Too much redirect for http://www.beelink.com/404.htm异常
	if("http://www.beelink.com/".equals(seedUrl)){
		
		Connection con = Jsoup.connect(seedUrl);// 获取请求连接Jsoup.connect(seedUrl);
		
		try {
			doc = con.get();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		if(doc==null) return;
		
	}else{
		
		if(needJsUrl.contains(seedUrl)){
			
			 html=HttpUtils.getHtmlByHtmlUnit(0,true,seedUrl, null, refer,null);
		}else{
			 html=HttpUtils.getHtmlByHtmlUnit(0,false,seedUrl, null, refer,null);
		}
		
		
		
		
		log.info(seedUrl);
		
		if(html==null){
			return;
		}
		doc=Jsoup.parse(html);
	}
	
	
	
	
	List<Element> subList=null;
	
	
	if(specialClass.containsKey(seedUrl)){

	
			
			if(specialClass.get(seedUrl).startsWith("direct")){
				
				//直接取导航链接a
				Elements elements = doc.select( specialClass.get(seedUrl).substring(7));	
				handleEveryHref(seedUrl, level,elements);		
			}else{
			//取a标签的上一级
				
				if(specialClass.get(seedUrl).equals("table")){
					//处理table标签中url过多的问题
					
						if(doc.select("table").size()>=5){
						subList = doc.select("table").subList(0, 5);		
						}else{
							subList = doc.select("table");	
						}	
					
					
				}else{
				subList=doc.select(specialClass.get(seedUrl));
				}
				
			}
			
			
			
			
		
		
		
		
	}else{
		
		//一般提取标签类
				subList = doc.select("[class*=nav]");
				if(subList.size()==0){
					
					subList = doc.select("[id*=nav]");	
				}
				
			    if(subList.size()==0){
					
					subList = doc.select("[class*=daohang]");	
				}
				
			    if(subList.size()==0){
					
			  		subList = doc.select("nav");	
			  	}
				  if(subList.size()==0){
						
				  		subList = doc.select("[class*=menu]");	
				  	}
				  if(subList.size()==0){
						subList = doc.select("[id*=menu]");	
					}
				  if(subList.size()==0){
						subList = doc.select("[id*=nv]");	
					}
				  
	}
	
	
	

	if(subList!=null&&subList.size()>0){
		log.info(seedUrl +":"+subList.size());
			
				if (subList.size() >=20) {

					subList = subList.subList(0, 20);
				}	
		
		
target1:	for (Element   element:subList){
		//根据a标签个数判断是否为导航标签
		if(!isNav(level, element)) continue;
		
		//去除其中的footer foot bottom底部标签
		
	for(String	className :element.classNames()){
		
		if(className.contains("foot")||className.contains("footer")||className.contains("bottom")){
			
			continue target1;
		}
		
	}
		
	
	//处理每个href
	
		//area对应于map标签
	
	handleEveryHref(seedUrl, level, element.select("a,area"));	
	
	
	
	}	
}	
}


private static void handleEveryHref(String seedUrl, int level, Elements elements) {
	target:	for(  Element   a:elements){
		
			
		String url=	a.attr("href").trim();
		if(url.startsWith("javascript")||url.equals("")||url.startsWith("#")||url.equals("/")) continue;
		
		
		if(!url.startsWith("http")){
			String hostUrl=null;
			//给每个url末尾加上/
			if(!seedUrl.endsWith("/"))  { 
				
				hostUrl=seedUrl+"/";
			
			}else{
				
				hostUrl=seedUrl;
			}
			
			
			
			hostUrl=hostUrl.substring(0, hostUrl.indexOf("/", 7)+1);
			
			if(url.startsWith("/")){
			
			url=hostUrl.substring(0, hostUrl.length()-1)+url;
			}else if(url.startsWith("./")){
				
				url=hostUrl.substring(0, hostUrl.length()-1)+url.substring(1);
				
			}
			else
			{
				url=hostUrl.substring(0, hostUrl.length())+url;	
				
			}
		}
		
		
		String section=a.text().trim().replace(" ", "").replace("[", "").replace("]", "").replace("|", "");
		
		if(section.startsWith("·")||section.startsWith("・")){
			section=section.replace("·", "").replace("・", "");
		}
		
		//只有两位或者三位数字代表页码
		if(section.matches("\\d{2,3}")){
			continue;
		}
		
		//过滤关键词
		for( String keyword:fuzzyMatchingFilterKeyword){
			
			if(section.contains(keyword)){
				continue target;
			}
		}		
	
		//如果section为图片,查找其下img标签的src属性代替
		if(section.equals("")&&manualSection.containsKey(url)){
			
			
			section=manualSection.get(url);
			
		/*	if(a.tagName().equals("a")){
			
			for(  Element img:a.select("img")){
				
				section="图片链接:"+img.attr("src").trim();	
			
			       break;
			}
			}
			
			if(a.tagName().equals("area")){
				
				section="map picture";
				}	*/
			
		}else{
			
			//太多和太少都不要
			if(section.length()>9 ||section.length()<2) continue;
			
		}
		
		
	
		//过滤去重并且标题内容不超过6个字符
		if( listUrlFilter(url,seedUrl,level) &&!deReplication.contains(url)&&!section.equals("")
				){
			
			
		
			deReplication.add(url);
		
			Result result=new Result();
			result.setUrl(url);
			result.setLevel(level);
			result.setSeedUrl(seedUrl);
			
			result.setSection(section);
			//对于gb2312编码处理一下防止乱码
			try{
				
				//生僻字网站:http://www.sun0769.com/ 
				//GBK
				result.setGb2312Text(new String(section.getBytes("gb2312"),"UTF-8"));
			
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				result.setGb2312Text("转化出错");
			}
			
			
			result.setType("wz");
			list.add(result);
			
			//Result加入队列
			
			linkedList.addLast(result);
			
			
	
		
		
		
		
		
		
			
		//	getNavUrl(seedUrl,url, level, stopLevel);
			}			
		}
}






/**
 * 
 * 
 * 列表页url过滤
 * @param url
 * @param seedUrl
 * @param level
 * @return
 */
	public static boolean listUrlFilter(String url, String seedUrl,int level) {

		
			
		//央视网 首页回顾  	http://www.cctv.com/homepagesave/20160902.shtml		
			if("http://www.cctv.com/".equals(seedUrl)){
				
				if(url.contains("homepagesave"))  return false;
			}
			
			
			//符合url匹配
			
			Pattern  pattern=Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(url);
			
			if(!matcher.matches()){
				return false; 
			}
				
			
			
			//content_123456.htm去除这种的url
			Pattern  pattern1=Pattern.compile(contentRegex, Pattern.CASE_INSENSITIVE);
			Matcher matcher1=pattern1.matcher(url);
			
			if(matcher1.find()){
				return false; 
			}
			
			
			
			
			
			
			if(filterDomain.contains(getDomain(url))){
				
				return false;
			}
			
			if(url.equals(seedUrl)){
				
				return false;
			}
			
		/*	//得到域名
			String host = new URL(seedUrl).getHost();
            	  host=host.replace("www.", "");
           
            	  
           if(!specialDomain.contains(seedUrl))   {
			if (!url.contains(host)) {

				return false;

			}
		    }
			*/
           
			//包含XX的过滤掉	
			for(  String  urlContain:Constants.urlContain){
				
				if(url.contains(urlContain)){
					
					return false;
				}
			}

			
		
			
		//以XX结尾的过滤掉	
			for(  String  suffix :Constants.urlSuffix){
				
				if(url.endsWith(suffix)){
					
					return false;
				}  
			}
			
			
			
	

		return true;

	}





	public static String getDomain(String url) {
		
		Pattern  pattern=Pattern.compile("//([\\s\\S]+?)/", Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(url);
		String domain=null;
		while(matcher.find()){
			 domain=matcher.group(1).replace("www.", "");
			 break;
			}
		
		return domain;
	}

//根据链接个数判断是否是导航标签
	private static boolean isNav(int level,Element element) {
		if(level==1&&(element.select("a,area").size()>=5)){
			
			return true;
		
		
		}
		
       if(level>1&&(element.select("a").size()>=5)&&(element.select("a").size()<=16)){
			
			return true;
		
		
		}
		
		
		return false;
	}


	//初始化工作
	public static void setUp() {
	//删除Result目录及其下所有文件	
		File dir=new File(directory);
		
		
		FileUtils.deleteDir(dir);
		
		
	}

	
	private static void cleanUp() {
		list.clear();
		deReplication.clear();
		linkedList.clear();
		ResultServiceImpl.newUrl.clear();
	}
	
	

}
