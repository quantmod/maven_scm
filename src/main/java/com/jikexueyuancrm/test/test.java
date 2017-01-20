package com.jikexueyuancrm.test;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;
import info.monitorenter.util.FileUtil;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;





import org.junit.Test;
import org.python.util.PythonInterpreter;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.jikexueyuancrm.controller.AnHui;
import com.jikexueyuancrm.controller.ImportantNewsWebsite;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.pojo.WorkbookWrapper;
import com.jikexueyuancrm.util.DateUtils;
import com.jikexueyuancrm.util.DeadlockConsoleHandler;
import com.jikexueyuancrm.util.DeadlockDetector;
import com.jikexueyuancrm.util.EMailUtils;
import com.jikexueyuancrm.util.FileUtils;
import com.jikexueyuancrm.util.HttpUtils;
import com.jikexueyuancrm.util.ProvinceUtils;
import com.jikexueyuancrm.util.UseCpdetector;

public class test {

	
	
	private static Logger log = Logger.getLogger(test.class);
	private static String contentRegex="content_\\d+\\.";
	
	//xpath解析   JXDocument
	@Test
	public void testXpathJXDocument() 	{
		String seedUrl="http://midchina.xinhuanet.com/";
		String html=HttpUtils.getHtmlByHtmlUnit(0,true ,seedUrl, null, null,null);
		
		if(html==null){
			return;
		}
		
		Document document = Jsoup.parse(html);
		String xpath="/html/body/table[2]/tbody/tr/td/table/tbody/tr/td/a";
		JXDocument jxDocument = new JXDocument(document);
		
		List<Object> rs =null;
		try{
		rs= jxDocument.sel(xpath);
		}catch(Exception e){
			e.printStackTrace();
		}
		for (Object o:rs){
			      if (o instanceof Element){
			          int index = ((Element) o).siblingIndex();
			          System.out.println(index);
			      }
			     System.out.println(o.toString());
			 }

	}
	
	
	//sax  测试成功
	@Test
	public void testXpathSax() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException 	{
		
		
		String seedUrl="http://midchina.xinhuanet.com/";
		String html=HttpUtils.getHtmlByHtmlUnit(0,true ,seedUrl, null, null,null);
		
		
		if(html==null){
			return;
		}
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		  factory.setNamespaceAware(true);
		  DocumentBuilder builder = factory.newDocumentBuilder();
		  
		  
		  
		  
		org.w3c.dom.Document doc = builder.parse(getStringStream(html));
		
		
		log.info(doc.toString());
		
		
		log.info(doc.getChildNodes().getLength());
		  
		  
		  XPathFactory xFactory = XPathFactory.newInstance();
		  XPath xpath = xFactory.newXPath();
		  XPathExpression expr = xpath.compile("/html/body/table[2]/tbody/tr/td/table/tbody/tr/td/a");
		  Object result = expr.evaluate(doc, XPathConstants.NODESET);
		  NodeList nodes = (NodeList) result;
		 log.info(nodes.getLength());
		  for (int i = 0; i < nodes.getLength(); i++) {
		
		  log.info(nodes.item(i).getAttributes().getNamedItem("href").getNodeValue());
		  log.info(nodes.item(i).getAttributes().getNamedItem("href").getNodeName());
		  log.info(nodes.item(i).getAttributes().getNamedItem("href").getNodeType());
		  }
		
		

	}
	
	
	
	
	
	
	
	
	//测试单独解析标签
		@Test
		public void AParentTag() throws NoSuchAxisException, NoSuchFunctionException, XpathSyntaxErrorException, IOException{
			
			String seedUrl="http://www.hiputian.com/";
			String cssQuery="div.nav-line-cen >dl>dd";
		//	%是URL中的转义符，比如 %20 表示空格，如果你要表达一个%本身，需要使用 %25表示。
			//js中可以使用 escape() 来编码。
			seedUrl=seedUrl.replace("%", "%25");
		//	log.info(seedUrl);
		//	log.info(URLDecoder.decode(seedUrl,"UTF-8"));
			
			String html=HttpUtils.getHtmlByHtmlUnit(0,false,seedUrl, null, null,null);
			
			
			if(html==null){
				return;
			}
			
		//	log.info(Jsoup.clean(html, "http://stock.gucheng.com", new Whitelist()));
	Document doc=Jsoup.parse(html);
			
			//
		//	Document doc=HttpUtils.getDocumentByJsoup(seedUrl);
			
			
			
			
			Elements	elements = doc.select(cssQuery);
			//Elements	elements = doc.getElementsByClass("nav");
		//Elements	elements = doc.getElementsByAttributeValueContaining("class","nav");
		//	Elements	elements = doc.getElementsByTag("nav");
			for(Element   element:elements){
			for(Element  ele	:element.select("a")){
				
				log.info(ele.attr("href"));
				log.info(ele.text());
				
			}	
				}

		}
		
		
	//直接测试a标签
	@Test
	public void ATag(){
		
		
		
	//	log.info(Charset.defaultCharset());
		String url="http://bbs.zgwg.com.cn/";
		String cssQuery="div.bbs_column>ul>li  div.index_left>div> a";
		String html=HttpUtils.getHtmlByHtmlUnit(0,false,url, null, null,null);
		
		Document doc =Jsoup.parse(html);
		Elements elements = doc.select(cssQuery);
		
		for (Element  element: elements){
			
		log.info(element.attr("href"));	
		log.info(element.text());
		}
		
		
		//

	}
	
	//测试追加excel文件
	
	@Test
	public void testAppendToExcel(){
		String desktop="C:/Users/Administrator/Desktop/";
		
		
		
		
		Result result=new Result();
		result.setLevel(1);
		result.setRootSection("新浪");
		result.setRootUrl("www.sina.com.cn");
		
		result.setSection("娱乐");
		
		
		result.setUrl("yule.sina.comc.cn");
		result.setType("wz");
		
		ArrayList<Result> list =new ArrayList<Result>();
		
		list.add(result);
		FileUtils.appendToExcel(desktop, "allResult.xls",list);
		
		
		//追加		
		
		
	}
	
	//测试删除目录
	
	@Test
	public void deleteDir(){
		
		
	String dir ="C:/Users/Administrator/Desktop/result1/";
		File path=new File(dir);
		FileUtils.deleteDir(path);
		
	}
	
	
	//测试同时读取多个文件
	@Test
	public void readMutiFile(){
		LinkedHashMap map =new LinkedHashMap();
		FileUtils.loadMutiFile2Map(true, "index", map);
	
		
		
		log.info("");
	}
	
	
	
	
	
	
	@Test
	public void ascii(){
		//使用Integer.valueOf就可以直接将char类型的数据转为十进制数据表现形式.
		int value=Integer.valueOf(' ');
		log.info(value);//32
		int value1=Integer.valueOf(' ');
		log.info(value1);
	log.info(ImportantNewsWebsite.getDomain("http://www.cntv.cn"));	
	
	
	Date beginDate = DateUtils.parse("2016-07-01", DateUtils.FORMAT_SHORT);
	
	log.info(beginDate);
	}
	
	
	@Test
	public void  getProvince(){
		
		log.info(ProvinceUtils.getProvince("http://www.shucheng.gov.cn/"));
		
		
		String icpUrl="http://icp.alexa.cn/index.php?q=http://www.shucheng.gov.cn/";
		
		
		String html=HttpUtils.getHtmlByHtmlUnit(0,false, icpUrl, null, null,null);
		
		
		if(html==null){
			return ;
		}
		
		
		Document doc=Jsoup.parse(html);
		
	//	log.info(doc.select("table.result-table  tr").get(2).select("td").get(1).text().trim().substring(0,1));
		
		
		
	}
	
	
	
	@Test
	public void substring(){
		String url="window.open('http://www.fawan.com/sports/');";
		url=url.substring(13,url.length()-3);
		log.info(url);
	
		
		
		
		
	 url="Men('11','/News/Index/2')";
	 url=url.substring(url.indexOf("/")+1,url.lastIndexOf("'"));
	 log.info(url);
	}
	
	
	//启动死锁检测
	@Test
	public void deadLock(){
		
		DeadlockDetector deadlockDetector = new DeadlockDetector(new DeadlockConsoleHandler(""), 5, TimeUnit.SECONDS);
		deadlockDetector.start();

	}
	
	
	
	@Test
	public void  contentRegex(){
		
		Pattern  pattern1=Pattern.compile(contentRegex, Pattern.CASE_INSENSITIVE);
		Matcher matcher1=pattern1.matcher("content_2.htm");
		
		log.info(matcher1.find());
		
		
	}
	@Test
	public  void  httpclient(){
		
		String url="http://www.ifcbbs.com/news/";
		
	HttpUtils.getStatusCodeByHttpClient(url);
	
	log.info(HttpUtils.getStatusCodeByHtmlUnit(false, url, null, null, null).getCode());
	}
	
	
	
	//测试发送邮件
	
	@Test
	public void readExcel(){
		
	//	EMailUtils.sendMail("jethai@126.com","jethai", "csrhit7293380", new String[]{"jethai@126.com","934033381@qq.com"}, "测试", "这是一封测试邮件", new String[]{"C:/Users/Administrator/Desktop/test.jpg","C:/Users/Administrator/Desktop/test2.jpg"}, null);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMW");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间
		
	log.info(date);
	
	WorkbookWrapper ww=	FileUtils.readExcel("C:\\Users\\Administrator\\Desktop\\result\\2016114_404.xls");
	Map<String, String[][]> map=ww.getSheets();
	int total=0;
	for( String key :map.keySet()){
		
		String[][] sheet= map.get(key);
		int row=sheet.length;
		//打印每个sheet的行数
		total=row-1+total;
		System.out.println(key+":  "+(row-1));
	}

	System.out.println("总数:"+total);
	
	}
	
	
	//测试网页和文件编码
	
	@Test
	public void getCharsetByCpdetector() throws IOException{
		//gb2312编码网站
		//String urlStr="http://www.fj.chinanews.com/";
		
		
		String urlStr=	"http://ay.shangdu.com/";
		//String charset=CharsetDetector.
		URL url=new URL(urlStr);
		log.info(System.getProperty("file.encoding"));
		   String charset =	UseCpdetector.getUrlEncode(url);
		   File file=new File("C:\\Users\\Administrator\\Desktop\\404.txt");
		   URL fileUrl=	   file.toURI().toURL();
		   log.info(fileUrl);
		   String fileCharset =	UseCpdetector.getUrlEncode(fileUrl);
	   log.info(charset);
	   log.info(fileCharset);
	    }
	
	
	//生僻字测试,排除数据库的原因,jsoup解析出就是乱码,修改操作系统代码页
	//54936 —简体中文（GB18030） mode con cp select=54936
	@Test
	public void   	uncommonCharacter() throws Exception{
		//生僻字网站
		String urlStr="http://www.sun0769.com/";
		String uncommonCharacter="道滘,高埗";
		 File file=new File("C:\\Users\\Administrator\\Desktop\\uncommonCharacter.txt");
		   
		FileUtils.writeIndex(file, uncommonCharacter);
		
	/*	String html=HttpUtils.getHtmlByHtmlUnit(true,urlStr, null, null,null);
		
		if(html==null){
			return;
		}
		
		Document doc=Jsoup.parse(html);*/
		
		Document doc=HttpUtils.getDocumentByJsoup(urlStr);
		
		Elements elements = doc.select("a.fw");
		for(  Element element:elements){
		log.info(element.text());
		}
	}
	
	
	//
	@Test
	public  void testLines() throws Exception{
		
		String filePath="C:/Users/Administrator/Desktop/error_request_origurl_wz.txt";
		log.info(FileUtils.getTotalLines(filePath));
		log.info(AnHui.class.getSimpleName());
		FileUtils.keywordDereplication("/fuzzyMatchingFilterKeyword");
	}
	
	
	
	
    // 将一个字符串转化为输入流
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
						sInputString.getBytes("UTF-8"));
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	
public static	String qqUrl="https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=716027609&pt_3rd_aid=100290348&daid=383&pt_skey_valid=0&style=35&s_url=http%3A%2F%2Fconnect.qq.com&refer_cgi=authorize&which=&state=60160f3fiaFhGKFwqXF6b25lX3Nuc6FzoKFyAqF2AqFpAKFoq3RvdXRpYW8uY29toW0AoW7A&redirect_uri=http%3A%2F%2Fapi.snssdk.com%2Fauth%2Flogin_success%2F&response_type=code&client_id=100290348&scope=get_user_info%2Cadd_share%2Cadd_t%2Cadd_pic_t%2Cget_info%2Cget_other_info%2Cget_fanslist%2Cget_idollist%2Cadd_idol%2Cget_repost_list&display=mobile";
	@Test
	public void toutiao() throws Exception{
		
		
		
	HtmlPage htmlPage=	HttpUtils.getHtmlPageByHtmlUnit(0, true, qqUrl, null, null, null);
		if(htmlPage!=null){
			
			HtmlElement account	=	(HtmlElement) htmlPage.getElementById("u");
			account.click();	
			account.type("934033381");
			

			HtmlElement password	=	(HtmlElement) htmlPage.getElementById("p");
			password.click();	
			password.type("csrhit7293380");
			
			
			HtmlButton loginBtn = (HtmlButton)htmlPage.getElementById("go");  
			
			Page resultPage = loginBtn.click();  
			
			log.debug(resultPage); 
			
			
			
			}
		
		
		
	}
	
	
	
	@Test
	public void toutiaoHttpClient() throws Exception {
	//1.
		 String htmlGetSource = "";  
		
		String checkUrl="https://ssl.ptlogin2.qq.com/check?pt_tea=2&uin=934033381&appid=716027609&ptlang=2052&regmaster=&pt_uistyle=35&r=0.35622953240973254";
		HttpClient httpClient = new DefaultHttpClient();  
		httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");  
	
		
			HttpGet httpGet1 = new HttpGet(checkUrl);  

			httpGet1.setHeader("Referer", qqUrl);
		HttpResponse response1 = httpClient.execute(httpGet1); 
		
		htmlGetSource = getHtmlStringByHttpResonse(response1);  
		log.info(htmlGetSource);
		
		

		 String ptvfsession="";
		 String verifycode="";
		 String username="934033381";
		 //salt="\x00\x00\x00\x00\x37\xac\x37\xe5"
		 verifycode=htmlGetSource.substring(htmlGetSource.indexOf("!"),htmlGetSource.indexOf("!")+4);
		 log.info("verifycode:"+verifycode);
		 Header[] set_cookies = response1.getHeaders("Set-Cookie");  
		 for( Header  cookie:set_cookies){
		 log.info(cookie.getValue());
		 
		 if(cookie.getValue().contains("ptvfsession=")){
			 
			 ptvfsession=cookie.getValue().substring(12,cookie.getValue().indexOf(";"));
			 log.info("ptvfsession:"+ptvfsession);
		 }
			 
		 }  
		
//2 openlogin
	
		 String openLogin="https://ssl.ptlogin2.qq.com/pt_open_login?openlogin_data=which%3D%26refer_cgi%3Dauthorize%26response_type%3Dcode%26client_id%3D100290348%26state%3D60160f3fiaFhGKFwqXF6b25lX3Nuc6FzoKFyAqF2AqFpAKFoq3RvdXRpYW8uY29toW0AoW7A%26display%3Dmobile%26openapi%3D1010_1011_2010_2020_2030%26switch%3D0%26src%3D1%26sdkv%3D%26sdkp%3D%26tid%3D1480561756%26pf%3D%26need_pay%3D0%26browser%3D0%26browser_error%3D%26serial%3D%26token_key%3D%26redirect_uri%3Dhttp%253A%252F%252Fapi.snssdk.com%252Fauth%252Flogin_success%252F%26sign%3D%26time%3D%26status_version%3D%26status_os%3D%26status_machine%3D%26page_type%3D1%26has_auth%3D1%26update_auth%3D1%26auth_time%3D1480562210114&pt_vcode_v1=0&pt_verifysession_v1="+ptvfsession+"&verifycode="+verifycode+"&u="+username+"&p="+"3X3zBpjFe6Mj9Cza*NdHd5NmFtYwoOj9IcPXXzaUA8B7MEs5O*k8tPy0FmlzT4MNL-9RROlwjETr7tO1SLI1Vwq5XWUG57kzmbUM6unm3GafntJ668ltFhhsahDX56YPFYvBQ2n5bguwdE69oiJYwMgLf2hswJKWl6I3Q1OWK2AMuIjpGh5-EU1fBfrQs2M5aHhywxWCjiQAgtOPybRfznVdsveFKQxYGRhUFbuqeNW8uU3AnKAZvzJi7whgO8vf75E0Y0ydGVCRIHhgJB530QkzlFoW5GZhfNNK*o9AowdTGi1iUIx-IARvYFeLfstOSskdhO11VuZ43HLkv3mH3w__"+"&pt_randsalt=2&ptlang=2052&low_login_enable=0&u1=http%3A%2F%2Fconnect.qq.com&from_ui=1&fp=loginerroralert&device=2&aid=716027609&daid=383&pt_3rd_aid=100290348&ptredirect=1&h=1&g=1&pt_uistyle=35&regmaster=&";
		 
		 
			HttpGet httpGet2 = new HttpGet(openLogin);  
			httpGet2.setHeader("Referer", qqUrl);
			HttpResponse response2= httpClient.execute(httpGet2); 
			
			htmlGetSource=getHtmlStringByHttpResonse(response2); 
		 log.info(htmlGetSource);
}

		@Test
		public void javascript() throws Exception{
 
			//下面的代码将会打印出当前的 JDK 所支持的所有脚本引擎
		ScriptEngineManager factory = new ScriptEngineManager();
		for (ScriptEngineFactory available : factory.getEngineFactories()) {
		log.info(available.getEngineName());
		}  
	
	
	
		//执行js文件中的函数
		ScriptEngineManager manager = new ScriptEngineManager(); 
	    ScriptEngine engine = manager.getEngineByName("javascript");
	    String jsfile="C:\\Users\\Administrator\\Desktop\\expression.js";
	    engine.eval(new FileReader(jsfile));  
	    if(engine instanceof Invocable) {    
	    	Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数    

	    	// c = merge(2, 3);    

	    	String c = (String)invoke.invokeFunction("hexchar2bin","98a0baf6f11d9141c17e265660a54030");  //函数名，参数  

	    	log.info("c = " + c);   
	    	}   
	    
	    
	    //直接运行javascript语句
	    engine.eval("var a=3; var b=4;print (a+b);");
	    
	    
	   //执行Python脚本(jython) 

	    PythonInterpreter interpreter = new PythonInterpreter();  
	    interpreter.exec("print 'strHello'");  
	}
	
	
		//公匙
	public static String rsaModule="e9a815ab9d6e86abbf33a4ac64e9196d5be44a09bd0ed6ae052914e1a865ac8331fed863de8ea697e9a7f63329e5e23cda09c72570f46775b7e39ea9670086f847d3c9c51963b131409b1e04265d9747419c635404ca651bbcbc87f99b8008f7f5824653e3658be4ba73e4480156b390bb73bc1f8b33578e7a4e12440e9396f2552c1aff1c92e797ebacdc37c109ab7bce2367a19c56a033ee04534723cc2558cb27368f5b9d32c04d12dbd86bbd68b1d99b7c349a8453ea75d1b2e94491ab30acf6c46a36a75b721b312bedf4e7aad21e54e9bcbcf8144c79b6e3c05eb4a1547750d224c0085d80e6da3907c3d945051c13c7c1dcefd6520ee8379c4f5231ed";
	
	public static String rsaExponent="10001";
	
	@Test
	public  void getEncryption(/*String password,String salt*/) throws Exception{
		
	   String   md5=md5("csrhit7293380");
	   log.info(md5);
	// String hex=   hexchar2bin(md5)  ;//
	  //  log.info(hex);
	String  salt="0000000037ac37e5";    
	String mix= md5(md5 +salt)  ; 
	    log.info(mix);
	    
	}
	
	
	//字符串转化为md5
	private static String md5(String str) throws Exception {
		try {  
            MessageDigest md5 = MessageDigest.getInstance("MD5");  
            md5.update(str.getBytes("UTF-8"));  
            byte[] encryption = md5.digest();  

            StringBuffer strBuf = new StringBuffer();  
            for (int i = 0; i < encryption.length; i++) {  
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {  
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));  
                } else {  
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));  
                }  
            }  

            return strBuf.toString();  
        } catch (NoSuchAlgorithmException e) {  
            return "";  
        } 
    }  
	
	byte[] arraycat(byte[] buf1,byte[] buf2)
	{
		byte[] bufret=null;
		int len1=0;
		int len2=0;
		if(buf1!=null)
			len1=buf1.length;
		if(buf2!=null)
			len2=buf2.length;
		if(len1+len2>0)
			bufret=new byte[len1+len2];
		if(len1>0)
			System.arraycopy(buf1,0,bufret,0,len1);
		if(len2>0)
			System.arraycopy(buf2,0,bufret,len1,len2);
		return bufret;
	}
	
	
	//md5转为16进制
	private static String hexchar2bin(String md5){
		
		StringBuilder sb=new StringBuilder();
	      for(int i=0;i<=md5.length()-2;i=i+2){
	    	 String hex ="\\x" +md5.substring(i, i+2);
	    	sb.append(hex)  ;
	      }
		return sb.toString();
		
	}

	public String getHtmlStringByHttpResonse(HttpResponse response)  {
	String	 htmlGetSource="";
		 
		try{
			
		HttpEntity entity = response.getEntity(); 
		
	       if(entity!=null){  
	            InputStream in = entity.getContent();  
	            String str = "";  
	            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));  
	            while((str=br.readLine())!=null){  
	                htmlGetSource += str+"\n";  
	            }  
	        }
	       
		}catch (Exception e){
			e.printStackTrace();
		}
		return htmlGetSource;
	}
	
	
	
	
	@Test
	public void testXXX() throws Exception{
		
		
		HtmlPage homePage = getHomePage("http://www.toutiao.com/group/6363790097485299970/", true);
		
		
		
		
		String text=homePage.asXml();
		for(;;);
	}
	
	
   public static HtmlPage getHomePage(String urlstr, boolean jsflg)
			/*     */     throws Exception
			/*     */   {
			/*     */     try
			/*     */     {
			/* 179 */       WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
			/* 180 */       webClient.getOptions().setJavaScriptEnabled(jsflg);
			/* 181 */       webClient.getOptions().setCssEnabled(jsflg);
			/* 182 */       webClient.getOptions().setTimeout(20000);
			/* 183 */       webClient.getOptions().setThrowExceptionOnScriptError(false);
			/* 184 */       webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			/*     */ 
			/* 188 */       URL url = new URL(urlstr);
			/* 189 */       return (HtmlPage)webClient.getPage(url);
			/*     */     }
			/*     */     catch (Throwable t) {
			/* 192 */       t.printStackTrace();
			/* 193 */       System.out.println(" Connection " + urlstr + " has error: " + 
			/* 194 */         t.getMessage());
			/* 195 */     }return null;
			/*     */   }
	
	
	
	
	
	
	
	
}