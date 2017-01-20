package com.jikexueyuancrm.common;

import java.util.HashSet;

public class Constants {
	
	public static HashSet<String> urlSuffix=new HashSet<String>();
	
	
	public static HashSet<String> urlContain=new HashSet<String>();
	
	static  {
		 urlSuffix.add(".js");
		 urlSuffix.add(".exe");
		 urlSuffix.add(".pdf");
		 urlSuffix.add(".gif");
		 urlSuffix.add(".png");
		 urlSuffix.add(".jpg");
		 urlSuffix.add(".jpeg");
		 urlSuffix.add(".svg");
		 urlSuffix.add(".doc");
		 urlSuffix.add(".swf");
		 
		 urlContain.add("guide");
		 urlContain.add("help");
		 urlContain.add("search");
		 urlContain.add("login");
		 urlContain.add("register");
		 urlContain.add("blog");
		//big5表示繁体
		 urlContain.add("big5");
		 urlContain.add(".js");
		 urlContain.add(".bbs");
		 urlContain.add("install");
		 urlContain.add("webapp"); 
		 urlContain.add("username");  
		 urlContain.add("space-uid-");
		 urlContain.add("orderby=");
		 urlContain.add("mod=space&uid=");
		 
		 urlContain.add("mod=ranklist");
		 urlContain.add("uid=");
		 urlContain.add(	 "thread-");
		 urlContain.add("ThreadID");
		 urlContain.add("tid=");
		 urlContain.add("baike.chinaso.com/wiki/doc-view-");
		 urlContain.add("/detail/");
	    urlContain.add("/Content/");
		 urlContain.add("/dispuser.asp?id=");
		 urlContain.add("/DocHtml/");
	}
	
	
	
	
	
	
	

}
