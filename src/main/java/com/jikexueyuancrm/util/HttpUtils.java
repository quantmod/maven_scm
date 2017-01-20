package com.jikexueyuancrm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.jikexueyuancrm.pojo.HttpWrapper;


public class HttpUtils {
	
	private static Logger log = Logger.getLogger(HttpUtils.class);
	
	/**
	 * @author yuan hai 2016年11月7日
	 * @param url
	 * @return
	 */
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
	
	
	
	
	
	//模拟浏览器请求获取cookie
	
	/**
	 * @author yuan hai 2016年11月24日
	 * @param urlString   
	 * @param refer    refer头
	 * @param cookieNameReserved   可变参数，要保留的cookie name，不传参数是大小为零的数组,所有cookie都保留
	 * @return
	 */
	public static  Set<Cookie> getCookie(String urlString,String refer,String ...cookieNameReserved ){
		
		WebClient webClient=null;
	    try {
	    	URL url = new URL(urlString);
	   	    WebRequest request=new WebRequest(url); 
	   	
			 webClient = new WebClient(BrowserVersion.FIREFOX_17);
			 webClient.getCookieManager().setCookiesEnabled(true);
				webClient.getOptions().setJavaScriptEnabled(true);
				webClient.getOptions().setCssEnabled(true);//正常浏览器都加载
				webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
				webClient.getOptions().setThrowExceptionOnScriptError(false); 
				webClient.getOptions().setTimeout(10000);
				webClient.setAjaxController(new NicelyResynchronizingAjaxController());
				request.setCharset("UTF-8");
				if(refer!=null){
				request.setAdditionalHeader("Referer", refer);
				}
				
				
				
				 HtmlPage page=webClient.getPage(request);
				  CookieManager cookieManager = webClient.getCookieManager();
				 
				  //返回的set集合只能读不能修改Collections.unmodifiableSet(copy)
				  Set<Cookie> cookies	=cookieManager.getCookies();
				
				  Set<Cookie> copy=  new HashSet<Cookie>(cookies);
				  
				  Iterator<Cookie> iterator = copy.iterator();
				  
				  
	     if(cookieNameReserved.length>0){	  
			 A:  while (iterator.hasNext()) {
				String cookieName = iterator.next().getName();
				
					for(int i=0;i<cookieNameReserved.length;i++){
						
						if (cookieName.equalsIgnoreCase(cookieNameReserved[i])){
							continue  A ;
						}
						
					}
					
					//不是则删除
					iterator.remove();
					
				
			   }

			}
			
			return copy;
	    }catch(Exception e) {
	    	log.error(e);
	    	e.printStackTrace();
	    	return null;
	    }finally{
	    	if(webClient!=null){
	    	webClient.closeAllWindows();
	    	}
	    	
	    }
	}
	
/*	
 * 
 *  
 *  
 *解释：当java程序在执行try块、catch块时如果遇到了return或者throw时，
     系统去寻找该异常处理流中是否包含了finally语句块，
     如果没有finally块，则程序会立即执行return或者throw语句，方法终止。
     如果有finally语句块，系统会开始立即执行finally语句块，只有当finally语句块中的代码被执行完了之后，系统才会回来再次执行try块或者catch块中的return或throw语句，
     但是如果finally块中也有return或者throw这样能是方法结束的语句，则finally块就会立即结束该方法，系统将不会跳回去执行try块或者catch中的任何语句。   
  *
  */	
	
	
	
	/**
	 * 规范化url
	 * @param url
	 * @return
	 */
	public static String regulateUrl(String url){
		
		//探测网页得到返回码
		
		if(!url.startsWith("http")){
			url="http://"+url;
		}
		
		//解决异常 Malformed escape pair at index 69
		url=url.replaceAll("%", "%25");
		return url;
	}
	
	
	
	
	
	public static String getHtmlByHtmlUnit(int sleeptime,boolean isJS,String url,  Set<Cookie> setCookies, String referer ,String UserAgent) {
		
		
		WebClient webClient =null;
		try{
			
			Thread.sleep(sleeptime+(int)(Math.random()*3000) );
			
			 webClient = new WebClient(BrowserVersion.FIREFOX_17);
			
			 if(isJS==false){
				 webClient.getOptions().setJavaScriptEnabled(false);
			}else{
				webClient.getOptions().setJavaScriptEnabled(true);	//开启js解析
			}
			 
			webClient.getOptions().setCssEnabled(true);
		    webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(30000);
			//https
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setRedirectEnabled(true);
			webClient.getCache().setMaxSize(0);
			webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
			CookieManager cookieManager = webClient.getCookieManager();
			if(setCookies!=null){
				
			for(  Cookie wxCookie :setCookies ){
				cookieManager.addCookie(wxCookie);
			}
			}
			
			WebRequest request=new WebRequest(new URL(url)); 
			request.setCharset("UTF-8");
			if(referer!=null){
			request.setAdditionalHeader("Referer", referer);
			}
		/*	request.setProxyHost("127.0.0.1");
			request.setProxyPort(8888);
			//用于Fiddler
			*/
			
			//添加useragent
			if(UserAgent!=null){
			log.info(UserAgent);
			request.setAdditionalHeader("User-Agent", UserAgent);
			}	
			
			//request.setAdditionalHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
			
			HtmlPage page = webClient.getPage(request);
			
		//	WebResponse webResponse = page.getWebResponse();
			
			//webResponse.cleanUp();
			  webClient.waitForBackgroundJavaScript(1000*5);  
		        webClient.setJavaScriptTimeout(0);
			String pageXml = page.asXml(); // 以xml的形式获取响应文本
			
			//webClient.closeAllWindows();
			return pageXml;
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			return null;
		}finally{
			webClient.closeAllWindows();
			webClient=null;
		}	
	}

	
public static HtmlPage getHtmlPageByHtmlUnit(int sleeptime,boolean isJS,String url,  Set<Cookie> setCookies, String Referer ,String UserAgent) {
		
		
		WebClient webClient =null;
		try{
			
			Thread.sleep(sleeptime+(int)(Math.random()*3000) );
			
			 webClient = new WebClient(BrowserVersion.FIREFOX_17);
			
			 if(isJS==false){
				 webClient.getOptions().setJavaScriptEnabled(false);
			}else{
				webClient.getOptions().setJavaScriptEnabled(true);	//开启js解析
			}
			 
			webClient.getOptions().setCssEnabled(true);
		    webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(30000);
			//https
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setRedirectEnabled(true);
			webClient.getCache().setMaxSize(0);
			webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
			CookieManager cookieManager = webClient.getCookieManager();
			if(setCookies!=null){
				
			for(  Cookie wxCookie :setCookies ){
				cookieManager.addCookie(wxCookie);
			}
			}
			
			WebRequest request=new WebRequest(new URL(url)); 
			request.setCharset("UTF-8");
			if(Referer!=null){
			request.setAdditionalHeader("Referer", Referer);
			}
		/*	request.setProxyHost("127.0.0.1");
			request.setProxyPort(8888);
			//用于Fiddler
			*/
			
			//添加useragent
			if(UserAgent!=null){
			log.info(UserAgent);
			request.setAdditionalHeader("User-Agent", UserAgent);
			}	
			
			//request.setAdditionalHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
			
			HtmlPage page = webClient.getPage(request);
			
		//	WebResponse webResponse = page.getWebResponse();
			
			//webResponse.cleanUp();
			  webClient.waitForBackgroundJavaScript(1000*5);  
		        webClient.setJavaScriptTimeout(0);
		
			return page;
			
			
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			return null;
		}finally{
			webClient.closeAllWindows();
			webClient=null;
		}	
	}
	
	
public static HttpWrapper getStatusCodeByHtmlUnit(boolean isJS,String url,  Set<Cookie> setCookies, String Referer ,String userAgent) {
		
		
		WebClient webClient =null;
		int code =999;//默认值
		String content=null;
		String expMsg=null;
		try{
			
			 webClient = new WebClient(BrowserVersion.FIREFOX_17);
			
			 if(isJS==false){
				 webClient.getOptions().setJavaScriptEnabled(false);
			}else{
				webClient.getOptions().setJavaScriptEnabled(true);	//开启js解析
			}
			 
			webClient.getOptions().setCssEnabled(true);
		    webClient.setAjaxController(new NicelyResynchronizingAjaxController()); 
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(30000);
			//https
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setRedirectEnabled(false);
			webClient.getCache().setMaxSize(0);
			webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
			CookieManager cookieManager = webClient.getCookieManager();
			if(setCookies!=null){
				
			for(  Cookie wxCookie :setCookies ){
				cookieManager.addCookie(wxCookie);
			}
			}
			
			WebRequest request=new WebRequest(new URL(url)); 
			request.setCharset("UTF-8");
			if(Referer!=null){
			request.setAdditionalHeader("Referer", Referer);
			}
		/*	request.setProxyHost("127.0.0.1");
			request.setProxyPort(8888);
			//用于Fiddler
			*/
			
			//添加useragent
			if(userAgent!=null){
			log.info(userAgent);
			request.setAdditionalHeader("User-Agent", userAgent);
			}	
			
			//request.setAdditionalHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
		//(HtmlPage/TextPage)
			HtmlPage page = webClient.getPage(request); 
		
		//	WebResponse webResponse = page.getWebResponse();
			
			//webResponse.cleanUp();
			  webClient.waitForBackgroundJavaScript(1000*5);  
		        webClient.setJavaScriptTimeout(0);
		 code	=page.getWebResponse().getStatusCode();
			
			//webClient.closeAllWindows();
		content=page.asText();
		if(content.contains("抱歉，您没有权限访问该版块")||content.contains("抱歉，指定的版块不存在")){
			code=999;
		}
		
			
		}catch(Exception e){
			
			log.error(e);
			log.info("-------------HtmlUnit异常开始");  
			e.printStackTrace();
		
			
			expMsg=e.getClass().getName()+":"+e.getMessage();
	

		
			 log.info("-------------HtmlUnit异常结束");  
		}finally{
			webClient.closeAllWindows();
			webClient=null;
		}
		
		
		HttpWrapper httpWrapper=new HttpWrapper();
		httpWrapper.setClient("HtmlUnit");
		httpWrapper.setCode(code);
		httpWrapper.setUrl(url);
		if(content!=null){
			httpWrapper.setContent(content);
		}
		
		if(expMsg!=null){
			httpWrapper.setExpMsg(expMsg);
		}
		return httpWrapper;	
		
		
		
	}

public static HttpWrapper getStatusCodeByHttpClient(String url) {
	
	
	int	responseCode=999;
	String content=null;
	DefaultHttpClient httpclient=null;
	  // 创建 HttpParams 以用来设置 HTTP 参数  
    HttpParams params = new BasicHttpParams();  
    // 设置连接超时和 Socket 超时，以及 Socket 缓存大小  
    HttpConnectionParams.setConnectionTimeout(params, 180 * 1000);  
    HttpConnectionParams.setSoTimeout(params, 180 * 1000);  
    HttpConnectionParams.setSocketBufferSize(params, 8192);
    // 设置重定向，缺省为 true  
    HttpClientParams.setRedirecting(params, false);  
    
    

	String expMsg=null;
	try {
		httpclient = new DefaultHttpClient(params);
	//  httpclient = new DefaultHttpClient(); 
	    HttpGet httpget = new HttpGet(url);	
		HttpResponse response =httpclient.execute(httpget);
		responseCode	 = response.getStatusLine().getStatusCode();  //获取响应码
		log.info(responseCode);
		
		HttpEntity entity = response.getEntity();  
        if (entity != null) {              
            //start 读取整个页面内容  
            InputStream is = entity.getContent();  
            BufferedReader in = new BufferedReader(new InputStreamReader(is));   
            StringBuffer buffer = new StringBuffer();   
            String line = "";  
            while ((line = in.readLine()) != null) {  
                buffer.append(line);  
            }  
            //end 读取整个页面内容  
           content = buffer.toString();  
        }  
		
	} catch (Exception e) {
		
		log.error(e);
		 log.info("-------------HttpClient异常开始"); 
			
		e.printStackTrace();
		expMsg=e.getClass().getName()+":"+e.getMessage();
		 log.info("-------------HttpClient异常结束");  
	}finally{
		
		if(httpclient != null)  
            httpclient.getConnectionManager().shutdown();  
		
		
	}
	
	

	
	
	HttpWrapper httpWrapper=new HttpWrapper();
	httpWrapper.setClient("HttpClient");
	httpWrapper.setCode(responseCode);
	if(content!=null){
		httpWrapper.setContent(content);
	}
	if(expMsg!=null){
		httpWrapper.setExpMsg(expMsg);
	}
	return httpWrapper;	
}	
	
//返回json
	public static String getJsonByHtmlUnit(String url,  Set<Cookie> setCookies, String Referer ,String UserAgent) {
		
		
		try{
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
		webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
		webClient.getOptions().setJavaScriptEnabled(true);//开启js解析
		webClient.getOptions().setCssEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setTimeout(20000);
		//https
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.getOptions().setRedirectEnabled(true);
		CookieManager cookieManager = webClient.getCookieManager();
		if(setCookies!=null){
		for(  Cookie wxCookie :setCookies ){
			cookieManager.addCookie(wxCookie);
		}
		}
		
		WebRequest request=new WebRequest(new URL(url)); 
		request.setCharset("UTF-8");
		if(Referer!=null){
		request.setAdditionalHeader("Referer", Referer);
		}
	/*	request.setProxyHost("127.0.0.1");
		request.setProxyPort(8888);
		//用于Fiddler
		*/
		
		//添加useragent
		if(UserAgent!=null){
		log.info(UserAgent);
		request.setAdditionalHeader("User-Agent", UserAgent);
		}	
		
		
		
		Page page = webClient.getPage(request);
		
	//	WebResponse webResponse = page.getWebResponse();
		
		//webResponse.cleanUp();
		
		WebResponse webResponse   = page.getWebResponse();
		
		String json="";
		if (webResponse.getContentType().equals("application/json")) {
			
			 json = webResponse.getContentAsString();
			
		}
		
		//webClient.closeAllWindows();
		return json;
	}catch(Exception e){
		log.error(e);
		e.printStackTrace();
		return null;
	}
	}
	

	
	
	//返回json
		/**
		 * @author yuan hai 2016年11月21日
		 * @param sleepTime  休眠时间单位:毫秒
		 * @param reqParam
		 * @param url
		 * @param setCookies
		 * @param Referer
		 * @param UserAgent
		 * @return
		 */
		public static String getJsonByPostMethod(int sleepTime,	List<NameValuePair> reqParam ,String url,  Set<Cookie> setCookies, String Referer ,String UserAgent) {
			
			
			try{
			
				Thread.sleep(sleepTime+(int)(Math.random()*3000) );
				
				
				
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
			webClient.getCookieManager().setCookiesEnabled(true);//开启cookie管理
			webClient.getOptions().setJavaScriptEnabled(true);//开启js解析
			webClient.getOptions().setCssEnabled(true);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setTimeout(20000);
			//https
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setRedirectEnabled(true);
			CookieManager cookieManager = webClient.getCookieManager();
			if(setCookies!=null){
			for(  Cookie wxCookie :setCookies ){
				cookieManager.addCookie(wxCookie);
			}
			}
			
			WebRequest request=new WebRequest(new URL(url),HttpMethod.POST); 
			
			
              if(reqParam!=null){
			request.setRequestParameters(reqParam);
              }
			
			
			
			request.setCharset("UTF-8");
			if(Referer!=null){
			request.setAdditionalHeader("Referer", Referer);
			}
		/*	request.setProxyHost("127.0.0.1");
			request.setProxyPort(8888);
			//用于Fiddler
			*/
			
			//添加useragent
			if(UserAgent!=null){
			log.info(UserAgent);
			request.setAdditionalHeader("User-Agent", UserAgent);
			}	
			
			
			
			Page page = webClient.getPage(request);
			
		//	WebResponse webResponse = page.getWebResponse();
			
			//webResponse.cleanUp();
			
			WebResponse webResponse   = page.getWebResponse();
			
			String json="";
			
				
				 json = webResponse.getContentAsString();
				
			
			
			//webClient.closeAllWindows();
			return json;
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			return null;
		}
		}
			
	
	
	
public static  Document getDocumentByJsoup(String url){
	Document doc =null;
	
	
	//使用jsoup解决htmlunit解析http://www.beelink.com/出现Too much redirect for http://www.beelink.com/404.htm异常
		// 获取请求连接Jsoup.connect(seedUrl);
		
		try {
			
		//	doc = Jsoup.connect(url).get();
			doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);		
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		
		return doc;
	}		
	
	
	 /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL)
    {
    Map<String, String> mapRequest = new HashMap<String, String>();
   
      String[] arrSplit=null;
     
    String strUrlParam=TruncateUrlPage(URL);
    if(strUrlParam==null)
    {
        return mapRequest;
    }
      //每个键值为一组 www.2cto.com
    arrSplit=strUrlParam.split("[&]");
    for(String strSplit:arrSplit)
    {
          String[] arrSplitEqual=null;         
          arrSplitEqual= strSplit.split("[=]");
         
          //解析出键值
          if(arrSplitEqual.length>1)
          {
              //正确解析
              mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
             
          }
          else
          {
              if(arrSplitEqual[0]!="")
              {
              //只有参数没有值，不加入
              mapRequest.put(arrSplitEqual[0], "");       
              }
          }
    }   
    return mapRequest;   
    }
	
    
    
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL)
    {
    String strAllParam=null;
      String[] arrSplit=null;
     
      strURL=strURL.trim();
     
      arrSplit=strURL.split("[?]");
      if(strURL.length()>1)
      {
          if(arrSplit.length>1)
          {
                  if(arrSplit[1]!=null)
                  {
                  strAllParam=arrSplit[1];
                  }
          }
      }
     
    return strAllParam;   
    }





	
}
