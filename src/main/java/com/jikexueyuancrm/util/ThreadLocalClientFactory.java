package com.jikexueyuancrm.util;



import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

	public  class ThreadLocalClientFactory{
		
		
		private final static ThreadLocalClientFactory instance =new ThreadLocalClientFactory();
		
		public static ThreadLocalClientFactory getInstance(){
			return instance;
			}
		
		
		
      private ThreadLocal<WebClient> client = new ThreadLocal<WebClient>() {  
    
    	  @Override	
  	
      protected synchronized WebClient initialValue(){  
          WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);  
            
          
      	webClient.getCookieManager().setCookiesEnabled(true);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(true);
			
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			
			webClient.getOptions().setThrowExceptionOnScriptError(false); 
			webClient.getOptions().setTimeout(10000);
			
			webClient.getOptions().setDoNotTrackEnabled(false);
			webClient.getOptions().setUseInsecureSSL(true);
			webClient
					.setAjaxController(new NicelyResynchronizingAjaxController());
            
          return webClient;  
      }  
  };  
    
  
  public void setWebClient(WebClient wc) {  
      client.set(wc);  
  }  
    
  public WebClient getWebClient() {  
      return client.get();  
  } 
  

	}   
