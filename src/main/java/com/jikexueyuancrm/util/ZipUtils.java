package com.jikexueyuancrm.util;  

import java.io.BufferedReader;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;  
import java.util.zip.ZipEntry;  
import java.util.zip.ZipInputStream;  
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

/** 
 * 解压缩测试 
 * @说明  
 * @author cuisuqiang 
 * @version 1.0 
 * @since 
 */  
public class ZipUtils {  
      
	
	private static Logger log = Logger.getLogger("com.jikexueyuancrm");	
	
	
	
	
    @SuppressWarnings( { "unchecked", "static-access" })  
    public static void main(String[] args) throws Exception {  
    	
    	
    	
    	
    	
    	

    	//解压文件
        ArrayList<String> a = Ectract("C:\\Users\\Administrator\\Desktop\\FiddlerAutoSave\\Fiddler_20161213_13-25-01.saz", "D:\\tempDir\\"); // 返回解压缩出来的文件列表 
        
        
        for(String s : a){  
            System.out.println(s);  
        }  
        
        
        //解析raw文件里的url
     
        ArrayList<String> appUrls = getAppUrl("D:\\tempDir\\raw");
        
        log.info(appUrls.size());
        //存入数据库
        
        
        
       FileUtils.deleteDir(new File("D:\\tempDir\\"));
        //压缩文件
    //    compress("D:\\temp\\a.zip", "D:\\Tomcat Cluster");
        
    }  

    
    //传入目录
    public static ArrayList<String> getAppUrl(String dir) {
    	ArrayList<String> urls=new ArrayList<String> ();
    	
           File directory=new File(dir);
    		
    		File[] list=directory.listFiles(new FilenameFilter() {
    			
    			@Override
    			public boolean accept(File dir, String name) {
    				
    				return name.endsWith("_c.txt");
    				
    			}
    		});

    		for(File file :list){
    			
    			log.info(file.getAbsolutePath());
    			String url = analysisUrl(file);
    			urls.add(url);
    		}	
	return urls;
		
	}



	private static String analysisUrl(File file) {
		
		
		
		InputStream is=null;
		
		
		try {
		 is = new FileInputStream(file);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String line = null;
			
			do {
			
				line = br.readLine();
				if (line != null && !"".equals(line.trim())) {
					
				line=	line.replaceAll("\\s+", " ");
					String[] words = line.split(" ");
					
					log.info(words[1]);
					
				return words[1];
					
					}
				} while (line != null);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
       
		
		return null;
		
	}



	/** 
     * 压缩文件列表到某ZIP文件 
     * @param zipFilename 要压缩到的ZIP文件 
     * @param paths 文件列表，多参数 
     * @throws Exception 
     */  
    public static void compress(String zipFilename, String... paths)  
            throws Exception {  
        compress(new FileOutputStream(zipFilename), paths);  
    }  
  
    /** 
     * 压缩文件列表到输出流 
     * @param os 要压缩到的流 
     * @param paths 文件列表，多参数 
     * @throws Exception 
     */  
    public static void compress(OutputStream os, String... paths)  
            throws Exception {  
        ZipOutputStream zos = new ZipOutputStream(os);  
        for (String path : paths) {  
            if (path.equals(""))  
                continue;  
            java.io.File file = new java.io.File(path);  
            if (file.exists()) {  
                if (file.isDirectory()) {  
                    zipDirectory(zos, file.getPath(), file.getName()  
                            + File.separator);  
                } else {  
                    zipFile(zos, file.getPath(), "");  
                }  
            }  
        }  
        zos.close();  
    }  
      
    private static void zipDirectory(ZipOutputStream zos, String dirName,  
            String basePath) throws Exception {  
        File dir = new File(dirName);  
        if (dir.exists()) {  
            File files[] = dir.listFiles();  
            if (files.length > 0) {  
                for (File file : files) {  
                    if (file.isDirectory()) {  
                        zipDirectory(zos, file.getPath(), basePath  
                                + file.getName().substring(  
                                        file.getName().lastIndexOf(  
                                                File.separator) + 1)  
                                + File.separator);  
                    } else  
                        zipFile(zos, file.getPath(), basePath);  
                }  
            } else {  
                ZipEntry ze = new ZipEntry(basePath);  
                zos.putNextEntry(ze);  
            }  
        }  
    }  
  
    private static void zipFile(ZipOutputStream zos, String filename,  
            String basePath) throws Exception {  
        File file = new File(filename);  
        if (file.exists()) {  
            FileInputStream fis = new FileInputStream(filename);  
            ZipEntry ze = new ZipEntry(basePath + file.getName());  
            zos.putNextEntry(ze);  
            byte[] buffer = new byte[8192];  
            int count = 0;  
            while ((count = fis.read(buffer)) > 0) {  
                zos.write(buffer, 0, count);  
            }  
            fis.close();  
        }  
    }  
    
    
    
    
    /** 
     * 解压缩 
     * @param sZipPathFile 要解压的文件 
     * @param sDestPath 解压到某文件夹 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static ArrayList Ectract(String sZipPathFile, String sDestPath) {  
        ArrayList<String> allFileName = new ArrayList<String>();  
        try {  
            // 先指定压缩档的位置和档名，建立FileInputStream对象  
            FileInputStream fins = new FileInputStream(sZipPathFile);  
            // 将fins传入ZipInputStream中  
            ZipInputStream zins = new ZipInputStream(fins);  
            ZipEntry ze = null;  
            byte[] ch = new byte[256];  
            while ((ze = zins.getNextEntry()) != null) {  
                File zfile = new File(sDestPath +File.separator+ ze.getName());  
                File fpath = new File(zfile.getParentFile().getPath());  
                if (ze.isDirectory()) {  
                    if (!zfile.exists())  
                        zfile.mkdirs();  
                    zins.closeEntry();  
                } else {  
                    if (!fpath.exists())  
                        fpath.mkdirs();  
                    FileOutputStream fouts = new FileOutputStream(zfile);  
                    int i;  
                    allFileName.add(zfile.getAbsolutePath());  
                    while ((i = zins.read(ch)) != -1)  
                        fouts.write(ch, 0, i);  
                    zins.closeEntry();  
                    fouts.close();  
                }  
            }  
            fins.close();  
            zins.close();  
        } catch (Exception e) {  
            System.err.println("Extract error:" + e.getMessage());  
        }  
        return allFileName;  
    }  
}  