package com.jikexueyuancrm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.python.jline.internal.Log;

public class ImageUtils {

	 /** 
     * 测试 
     * @param args 
     */  
    public static void main(String[] args) {  
        String strUrl = "http://www.baidu.com/img/baidu_sylogo1.gif";  
      
            writeImageToDiskFromUrl(strUrl,"C:\\1\\img\\");  
            
            byte[] bs = getImageFromNetByUrl("http://m14.urlpic.com/2016/upload/image/20161204/120400262599.jpg");
         System.out.print(bs);
           
         writeImageToDiskFromBytes(bs,"C:\\1\\img\\","my.jpg");
         
           //使用IoUtils 
         try {  
             URL url = new URL(strUrl);  
             HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
             conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:50.0) Gecko/20100101 Firefox/50.0");
             conn.setRequestMethod("GET");  
             conn.setConnectTimeout(5 * 1000);  
             InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
             File f= new File("C:\\2\\img\\");
             f.mkdirs(); 
             File file = new File("C:\\2\\img\\" +"1.jpg");  
             FileOutputStream fops = new FileOutputStream(file);  
             
             IOUtils.copy(inStream, fops);
         } catch (Exception e) {  
             e.printStackTrace();  
         }     
            
        
    }  
    
  
    
    
    /**
     * 网络图片写入文件
     * @author yuan hai 2016年12月4日
     * @param url   网络图片链接
     * @param dir   本地文件目录
     *
     */
    public static void writeImageToDiskFromUrl(String imgUrl,String dir){  
       
        byte[] btImg = getImageFromNetByUrl(imgUrl); 
        
        
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1, imgUrl.length());  
        
        if(null != btImg && btImg.length > 0){  
            System.out.println("读取到：" + btImg.length + " 字节");  
            writeImageToDiskFromBytes(btImg, dir, UUID.randomUUID()+"___"+fileName);
        }else{  
            System.out.println("没有从该连接获得内容");  
        }  
         
       
    } 
    
    
    /**
     * 字节数组写入文件
     * @author yuan hai 2016年12月4日
     * @param img  
     * @param dir   目录
     * @param fileName 文件名
     */
    public static void writeImageToDiskFromBytes(byte[] img, String dir, String fileName){  
        try {  
        	
        	File f= new File(dir);
             f.mkdirs(); 
        	
            File file = new File(dir + fileName);  
            FileOutputStream fops = new FileOutputStream(file);  
            fops.write(img);  
            fops.flush();  
            fops.close();  
            System.out.println("图片已经写入到硬盘");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    
    /** 
     * 根据地址获得数据的字节流 
     * @param strUrl 网络连接地址 
     * @return 
     */  
    public static byte[] getImageFromNetByUrl(String strUrl){  
        try {  
            URL url = new URL(strUrl);  
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:50.0) Gecko/20100101 Firefox/50.0");
            conn.setRequestMethod("GET");  
            conn.setConnectTimeout(20 * 1000);  
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据  
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据  
            return btImg;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    /** 
     * 从输入流中获取数据 
     * @param inStream 输入流 
     * @return 
     * @throws Exception 
     */  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    }  	
	
	
	
}
