package com.jikexueyuancrm.util;
/*package com.jikexueyuancrm.util;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;
//然后创建你自己的webClient：new InterceptWebConnection(webClient);
//忽略执行某个JavaScript文件  
class InterceptWebConnection extends FalsifyingWebConnection{
    public InterceptWebConnection(WebClient webClient) throws IllegalArgumentException{
        super(webClient);
    }
    @Override
    public WebResponse getResponse(WebRequest request) throws IOException {
        WebResponse response=super.getResponse(request);
        if(response.getWebRequest().getUrl().toString().endsWith("query-1.11.1.min.js")){
            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
        }
        return super.getResponse(request);
    }
}*/