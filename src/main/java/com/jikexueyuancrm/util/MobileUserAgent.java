package com.jikexueyuancrm.util;

import java.util.ArrayList;

public class MobileUserAgent {
	
	public static String Android_N1="Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	public static String Android_QQ="MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	public static String Android_UC="JUC (Linux; U; 2.3.7; zh-cn; MB200; 320*480) UCWEB7.9.3.103/139/999";
	public static String Android_Firefox="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:7.0a1) Gecko/20110623 Firefox/7.0a1 Fennec/7.0a1";
	public static String Android_Opera_Mobile="Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/build-1107180945; U; en-GB) Presto/2.8.149 Version/11.10";
	public static String Android_Pad_Moto_Xoom="Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13";
	public static String iPhone3="Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/1A542a Safari/419.3";
	public static String iPhone4="Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7";
	public static String iPad="Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10";
	public static String BlackBerry="Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; en) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.337 Mobile Safari/534.1+";
	public static String WebOS_HP_Touchpad="Mozilla/5.0 (hp-tablet; Linux; hpwOS/3.0.0; U; en-US) AppleWebKit/534.6 (KHTML, like Gecko) wOSBrowser/233.70 Safari/534.6 TouchPad/1.0";
	public static String Nokia_N97="Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124";
	public static String Windows_Phone_Mango ="Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; HTC; Titan)";
	//Galaxy S5   chrome
	public static String WeiXin_Version_6_3_18_WebView ="Mozilla/5.0 (Linux; Android 4.4.2; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36 MicroMessenger/6.3.18.800 NetType/WIFI Language/zh_CN";
	public static ArrayList<String> userAgents;
	
	
	
	static{
		
		userAgents	 =new ArrayList<String>();
		userAgents.add(Android_N1);
		userAgents.add(Android_QQ);
		userAgents.add(Android_UC);
		userAgents.add(Android_Firefox);
		userAgents.add(Android_Opera_Mobile);
		userAgents.add(Android_Pad_Moto_Xoom);
		userAgents.add(iPhone3);
		userAgents.add(iPhone4);
		userAgents.add(iPad);
		userAgents.add(BlackBerry);
		userAgents.add(WebOS_HP_Touchpad);
		userAgents.add(Nokia_N97);
		userAgents.add(Windows_Phone_Mango);
	}
	
	


}
