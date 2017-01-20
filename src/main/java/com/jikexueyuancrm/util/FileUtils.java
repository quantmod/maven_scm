package com.jikexueyuancrm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.jetty.util.log.Log;

import com.jikexueyuancrm.entity.ErrorUrl;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.pojo.WorkbookWrapper;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;









public class FileUtils {

	private static String bigBlank="			"	;
	private static String smallBlank="		";
	
	private static String seperator=";";
	/**
	 * 将String写入txt
	 * 默认路径C:/Users/Administrator/Desktop/test.html
	 */
	private static Logger log = Logger.getLogger(FileUtils.class);
	
	public static void writeTxt(String path,String content) throws Exception{
		if(path==null||"".equals(path)){
			path="C:/Users/Administrator/Desktop/test.html";
		}
		File file = new File(path);
		if(!file.exists()) {
		    file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);//这里可以有额外参数设置文件的写入方式
//		OutputStreamWriter writer = new OutputStreamWriter(fos, "urf-8"); 
//		writer.write(content);
//		writer.flush();
//		writer.close();
		fos.write(content.getBytes());
		fos.close();
		System.out.println("end");
	}
	
	
	
	/**
	 * 将String写入txt
	 * 默认路径C:/Users/Administrator/Desktop/test.html
	 */
	public static void writeTxt(String content) throws Exception{
		writeTxt(null,content);
	}
	
	public final static String UTF_8 = "UTF-8";
	public final static String GBK = "GBK";
	
//非txt(每行文本分割装入map)
public static void loadFile2Map( boolean reverse ,String classPath, LinkedHashMap<String,String> map) {
		
		InputStream is = FileUtils.class.getResourceAsStream(classPath);
		if (is == null) {
			System.out.println("path " + classPath + " is not found");
			return;
		}
		
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8), 512);
			String line = null;
			do {
				line = br.readLine();
				if (line != null && !"".equals(line.trim())) {
					
				line=	line.replaceAll("\\s+", " ");
					String[] words = line.split(" ");
					if(words.length>=2){
					
					/*if(!words[1].startsWith("http://")){
						words[1]="http://"+words[1];
						}	*/
						
						
					if(reverse)	{
						
					if (!map.containsKey(words[1])) {
						
						map.put(words[1],words[0]);
						
						
					}
					
					}else{
						
						if (!map.containsKey(words[0])) {
							map.put(words[0],words[1]);
						}
						
					}
					
					
					
					
					}
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
	}



public static void loadFile2MapWithProvince( boolean reverse ,String classPath, LinkedHashMap<String,String[]> map) {
	
	InputStream is = FileUtils.class.getResourceAsStream(classPath);
	if (is == null) {
		System.out.println("path " + classPath + " is not found");
		return;
	}
	
	
	try {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8), 512);
		String line = null;
		do {
			line = br.readLine();
			if (line != null && !"".equals(line.trim())) {
				
			line=	line.replaceAll("\\s+", " ");
				String[] words = line.split(" ");
				if(words.length>=2){
				
				/*if(!words[1].startsWith("http://")){
					words[1]="http://"+words[1];
					}	*/
					
					
				if(reverse)	{
					
				if (!map.containsKey(words[1])) {
					
					map.put(words[1], new String[]{words[0],words.length>=3?words[2]:""});
					
				}else{
					//已经重复
					log.info("url exist:"+words[1]);
				}
				
				}else{
					
					if (!map.containsKey(words[0])) {
						map.put(words[0],new String[]{words[1],words.length>=3?words[2]:""});
						
					}
					
				}
				
				
				
				
				}
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
}










//加载多个文件
/**
 * @author yuan hai 2016年11月3日
 * @param reverse
 * @param prefix  文件名前缀
 * @param map
 */
public static void loadMutiFile2Map( boolean reverse ,final String prefix, LinkedHashMap<String,String[]> map) {

String classPath=	FileUtils.class.getResource("/").getPath();
log.info(classPath);
	File  directory=new File(classPath);
	log.info(directory.getAbsolutePath());
	
	File[] list=directory.listFiles(new FilenameFilter() {
		
		@Override
		public boolean accept(File dir, String name) {
			
			return name.startsWith(prefix);
			
		}
	});

	for(File file :list){
		log.info(file.getName());
		log.info(file.getAbsolutePath());
		
		loadFile2MapWithProvince(true, "/"+file.getName(), map);
	}
	
	

}



/**
 * @param symbol  分割符号
 * @param classPath 类路径
 * @param map   装入的map容器
 */

public static void loadFile2MapBySymbol( String symbol,String classPath, HashMap<String, String> map) {

	
	InputStream is = FileUtils.class.getResourceAsStream(classPath);
	if (is == null) {
		System.out.println("path " + classPath + " is not found");
		return;
	}
	
	
	try {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8), 512);
		String line = null;
		do {
			line = br.readLine();
			if (line != null && !"".equals(line.trim())) {
				
			
				String[] words = line.split(symbol);
				if(words.length>=2){
					words[0]=words[0].trim();
					
					words[1]=words[1].trim();
				
					
				if (!map.containsKey(words[0])) {
					map.put(words[0],words[1]);
				}
				}
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

	
}



//非txt文件	
public static void loadFile2Set(String path, HashSet<String> keymp)  {
		
		InputStream is = FileUtils.class.getResourceAsStream(path);
		if (is == null) {
			log.info("path " + path + " is not found");
			return;
		}
		
	
	
		
		
		try {
			
			
			log.info("before dereplication num:"+getTotalLines(path));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF_8), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					if (!keymp.contains(theWord)) {
						keymp.add(theWord);
					}else{
					log.info("already exist:"+ theWord);
					}
				}
			} while (theWord != null);

			log.info("after dereplication num:"+keymp.size());
			
			
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
	}


//结果写入文件
public static void write2file(int number,String directory,String value, ArrayList<Result> list)  throws Exception{
	
	 
	
	//String directory="C:/Users/Administrator/Desktop/Result/";
	Date date =new Date();
	String path=directory+number+"."+date.getTime();
	File file = new File(path);
	
	
	 if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	if(!file.exists()) {
	    file.createNewFile();
	}
	
	FileOutputStream fos = new FileOutputStream(file);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    //写抬头
    
    bw.write("url"+bigBlank+"section"+bigBlank+"level"+bigBlank+"seedUrl"+bigBlank+"rootUrl"+bigBlank+"rootSection"+bigBlank+"province");
    		bw.newLine();
	for (Result result:list) {
        bw.write(result.getUrl());
        bw.write(smallBlank);
        bw.write(result.getSection());
        bw.write(smallBlank);
        bw.write(result.getGb2312Text());
        bw.write(smallBlank);
        bw.write(String.valueOf(result.getLevel()));
        bw.write(smallBlank);
        bw.write(result.getSeedUrl());
        bw.write(smallBlank);
        bw.write(result.getRootUrl());
        bw.write(smallBlank);
        bw.write(result.getRootSection());
        bw.write(smallBlank);
        bw.write(result.getProvince());
        bw.newLine();
    }
    bw.close();
    fos.close();
}


//输出excel文件
public static void saveAsExcel(int number ,String directory, String value, ArrayList<Result> list) throws Exception {
	 WritableWorkbook wwb = null;
	Date date =new Date();
	 
	 String path=directory+number+"."+date.getTime()+".xls";
	 
	 File file = new File(path);
	  if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	
		if(!file.exists()) {
		    file.createNewFile();
		}
	  wwb=Workbook.createWorkbook(file);//创建excel文件
	   
	  WritableSheet ws = wwb.createSheet("sheet1", 0);//创建一个可写入的工作表
	       
	 
	
	  ws.setColumnView(0, 75);// 设置列的宽度
		ws.setColumnView(1, 30);
		ws.setColumnView(2, 30);
		ws.setColumnView(3, 20);
		ws.setColumnView(4, 75);
		ws.setColumnView(5, 75);
		ws.setColumnView(6, 20);
		ws.setColumnView(7, 20);
	  
	  //设置表头
	  ws.addCell(new Label(0,0,"url"));
	  ws.addCell(new Label(1,0,"版块名称"));
	  ws.addCell(new Label(2,0,"gb版块名称"));
	  ws.addCell(new Label(3,0,"层级"));
	  ws.addCell(new Label(4,0,"父级url"));
	  ws.addCell(new Label(5,0,"根url"));
	  ws.addCell(new Label(6,0,"根"));
	  ws.addCell(new Label(7,0,"省份"));
	   
	  //添加行数据，遍历需要写入excel的集合数据
	  for (int i=1;i<=list.size();i++){
	  Result result=list.get(i-1);
	  
	    ws.addCell(new Label(0,i,result.getUrl()));
	    ws.addCell(new Label(1,i,result.getSection()));
	    ws.addCell(new Label(2,i,result.getGb2312Text()));
	    ws.addCell(new Label(3,i,String.valueOf(result.getLevel())));
	    ws.addCell(new Label(4,i,result.getSeedUrl()));
	    ws.addCell(new Label(5,i,result.getRootUrl()));
	    ws.addCell(new Label(6,i,result.getRootSection()));
	    ws.addCell(new Label(7,i,result.getProvince()));
	  }
	             wwb.write();//从内存中写入文件中
	             wwb.close(); //关闭资源，释放内存
	
}



//所有结果写入一个文件
public static void appendToFile(String directory,String filename,ArrayList<Result> list) throws Exception{
	
	String path=directory+filename;
	File file = new File(path);
	
	
	 if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	if(!file.exists()) {
	    file.createNewFile();
	}
	
	FileOutputStream fos = new FileOutputStream(file,true);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
   
	for (Result result:list) {
        bw.write(result.getUrl());
        bw.write(smallBlank);
        bw.write(result.getSection());
        bw.write(smallBlank);
        bw.write(result.getGb2312Text());
        bw.write(smallBlank);
        bw.write(String.valueOf(result.getLevel()));
        bw.write(smallBlank);
        bw.write(result.getSeedUrl());
        bw.write(smallBlank);
        bw.write(result.getRootUrl());
        bw.write(smallBlank);
        bw.write(result.getRootSection());
        bw.write(smallBlank);
        bw.write(result.getProvince());
        bw.newLine();
    }
    bw.close();
	fos.close();
}


/**
 * @author yuan hai 2016年11月29日
 * @param directory
 * @param filename
 * @param fieldValues   要添加一行字段的值
 * @throws Exception
 */
public static void appendToFile(String directory,String filename,String ...fieldValues) throws Exception{
	
	String path=directory+filename;
	File file = new File(path);
	
	
	 if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	if(!file.exists()) {
	    file.createNewFile();
	}
	
	FileOutputStream fos = new FileOutputStream(file,true);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
   
    
    if(fieldValues.length>0){
     //写入行	   
    	
    	for(int i=1;i<=fieldValues.length;i++){
    	
	     bw.write(fieldValues[i-1]); 
	  
	     if(i<fieldValues.length){
	    	 bw.write(seperator);
	     }
	  
    	}
	  
	  bw.newLine();
   }
    
    
  
    bw.close();
	fos.close();
}



//所有结果写入一个excel的工作簿

public static void appendToExcelUrl(String directory,String filename, String sheetname,List<ErrorUrl> list) {
	WritableWorkbook wwb = null;
	 WritableSheet ws=null;
	 
	 try {
	String path=directory+filename;
	File file = new File(path);
	 if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	 
	 
	 
	 
		if (!file.exists()) {
			//第一次新建文件写入数据
		   file.createNewFile();
			
			wwb = Workbook.createWorkbook(file);// 创建excel文件
			ws = wwb.createSheet(sheetname, 0);// 创建一个可写入的工作表
			ws.setColumnView(0, 75);// 设置列的宽度
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 20);
			ws.setColumnView(5, 20);
			// 设置表头
			  ws.addCell(new Label(0,0,"url"));
			  ws.addCell(new Label(1,0,"code"));
			  ws.addCell(new Label(2,0,"newcode"));
			  ws.addCell(new Label(3,0,"type"));
			  ws.addCell(new Label(4,0,"times"));
			  ws.addCell(new Label(5,0,"crawlname"));
			  //添加行数据，遍历需要写入excel的集合数据
			  for (int i=1;i<=list.size();i++){
			  ErrorUrl result=list.get(i-1);
			    ws.addCell(new Label(0,i,result.getUrl()));
			    ws.addCell(new Label(1,i,String.valueOf(result.getCode())));
			    ws.addCell(new Label(2,i,String.valueOf(result.getNewcode())));
			    ws.addCell(new Label(3,i,result.getType()));
			    ws.addCell(new Label(4,i,result.getTimes()));
			    ws.addCell(new Label(5,i,result.getCrawlname()));
			  }
			             wwb.write();//从内存中写入文件中
			            
			             wwb.close(); //关闭资源，释放内存
		}else{
		
			//poi追加excel数据
		      FileInputStream fs=new FileInputStream(file);  
		        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
		        HSSFWorkbook wb=new HSSFWorkbook(ps); //无参数的构造方法表示新建   
		        HSSFSheet sheet=wb.getSheet(sheetname);  //获取到工作表，因为一个excel可能有多个工作表  
		        FileOutputStream out=new FileOutputStream(file);  
		        if(sheet!=null){
		        int lastRow= sheet.getLastRowNum();
		          
		       
		      
		        for(int i=1;i<=list.size();i++){
		        	ErrorUrl result=list.get(i-1);
		        	 HSSFRow row =sheet.createRow(lastRow+i); //在现有行号后追加数据  
		        row.createCell(0).setCellValue(result.getUrl()); //设置第一个单元格的数据  
		        row.createCell(1).setCellValue(result.getCode());
		        row.createCell(2).setCellValue(result.getNewcode());
		        row.createCell(3).setCellValue(result.getType());
		        row.createCell(4).setCellValue(result.getTimes());
		        row.createCell(5).setCellValue(result.getCrawlname());
		        }
		        
		        } else{
		        	
		        //创建一个新sheet,设置表头
		        	 HSSFSheet newsheet = wb.createSheet(sheetname);
		        	 newsheet.setColumnWidth(0, 75*256);
		        	 newsheet.setColumnWidth(1, 20*256);
		        	 newsheet.setColumnWidth(2, 20*256);
		        	 newsheet.setColumnWidth(3, 20*256);
		        	 newsheet.setColumnWidth(4, 20*256);
		        	 newsheet.setColumnWidth(5, 20*256);
		        	 HSSFRow titleRow= newsheet.createRow(0);
		        	 titleRow.createCell(0).setCellValue("url"); 
		        	 titleRow.createCell(1).setCellValue("code");
		        	 titleRow.createCell(2).setCellValue("newcode");
		        	 titleRow.createCell(3).setCellValue("type");
		        	 titleRow.createCell(4).setCellValue("times");
		        	 titleRow.createCell(5).setCellValue("crawlname");
		        	 
		       	  for (int i=1;i<=list.size();i++){
					  ErrorUrl result=list.get(i-1);
					  HSSFRow row =newsheet.createRow(i); 
				        row.createCell(0).setCellValue(result.getUrl()); 
				        row.createCell(1).setCellValue(result.getCode());
				        row.createCell(2).setCellValue(result.getNewcode());
				        row.createCell(3).setCellValue(result.getType());
				        row.createCell(4).setCellValue(result.getTimes());
				        row.createCell(5).setCellValue(result.getCrawlname());
					  }	 
		        }   
		        out.flush();  
		        wb.write(out);    
		        out.close(); 
			
			
		}
	 } catch (Exception e) {
			e.printStackTrace();
		}
		
}



 public static WorkbookWrapper readExcel(String path){
	 
     
     
	 File file = new File(path);
	 
	 if (!file.exists()) {
	 
		log.info("不存在此文件"); 
		
		return null;
		 
	 }
	 
	 try {
		 HSSFWorkbook wb = null;
	     POIFSFileSystem ps = null;
		FileInputStream fs=new FileInputStream(file);
		
		ps=new POIFSFileSystem(fs);
		wb=new HSSFWorkbook(ps);
	LinkedHashMap<String,String[][]> map=new LinkedHashMap<String,String[][]>();
		
	for(int i=0;i<	wb.getNumberOfSheets();i++){
		 HSSFSheet sheet=wb.getSheetAt(i);
		String sheetName=sheet.getSheetName();
		
		String[][] cells=new String[sheet.getPhysicalNumberOfRows()][sheet.getRow(0).getPhysicalNumberOfCells()];
		
		
		
		for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {//获取每行
            HSSFRow row=sheet.getRow(j);
            for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {//获取每个单元格
                System.out.print(row.getCell(k)+"\t");
                
                if(row.getCell(k)!=null){
                cells[j][k]=row.getCell(k).toString();
                }   else{
                	cells[j][k]="";
                	
                } 
                
            }
            System.out.print("\n");
        }
		
		 System.out.println("---Sheet表"+sheetName+"处理完毕---");
		map.put(sheetName, cells);
	}
		
	WorkbookWrapper wbw=new WorkbookWrapper();
	wbw.setSheets(map);
		return wbw;
	} catch(Exception e) {
		e.printStackTrace();
		return null;
	}  
	 
	 
	 
	 
	 
	 
	 
 }






public static void appendToExcel(String directory,String filename,ArrayList<Result> list) {
	WritableWorkbook wwb = null;
	 WritableSheet ws=null;
	 
	 try {
	String path=directory+filename;
	File file = new File(path);
	 if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	 
	 
	 
	 
		if (!file.exists()) {
			//第一次写入数据
		   file.createNewFile();
			
			wwb = Workbook.createWorkbook(file);// 创建excel文件

			ws = wwb.createSheet("sheet1", 0);// 创建一个可写入的工作表
			ws.setColumnView(0, 75);// 设置列的宽度
			ws.setColumnView(1, 30);
			ws.setColumnView(2, 30);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 75);
			ws.setColumnView(5, 75);
			ws.setColumnView(6, 20);
			ws.setColumnView(7, 20);
			// 设置表头
			  ws.addCell(new Label(0,0,"url"));
			  ws.addCell(new Label(1,0,"版块名称"));
			  ws.addCell(new Label(2,0,"gb版块名称"));
			  ws.addCell(new Label(3,0,"层级"));
			  ws.addCell(new Label(4,0,"父级url"));
			  ws.addCell(new Label(5,0,"根url"));
			  ws.addCell(new Label(6,0,"根"));
			  ws.addCell(new Label(7,0,"省份"));
			  
			  //添加行数据，遍历需要写入excel的集合数据
			  for (int i=1;i<=list.size();i++){
				  
				  
			  Result result=list.get(i-1);
		
			 
			  
			    ws.addCell(new Label(0,i,result.getUrl()));
			    ws.addCell(new Label(1,i,result.getSection()));
			    ws.addCell(new Label(2,i,result.getGb2312Text()));
			    ws.addCell(new Label(3,i,String.valueOf(result.getLevel())));
			    ws.addCell(new Label(4,i,result.getSeedUrl()));
			    ws.addCell(new Label(5,i,result.getRootUrl()));
			    ws.addCell(new Label(6,i,result.getRootSection()));
			    ws.addCell(new Label(7,i,result.getProvince()));
			  }
			             wwb.write();//从内存中写入文件中
			             wwb.close(); //关闭资源，释放内存
		}else{
		
		

			
			
			//poi追加excel数据
		      FileInputStream fs=new FileInputStream(file);  
		        POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  
		        HSSFWorkbook wb=new HSSFWorkbook(ps);    
		        HSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
		        int lastRow= sheet.getLastRowNum();
		          
		        FileOutputStream out=new FileOutputStream(file);  
		      
		        for(int i=1;i<=list.size();i++){
		        	Result result=list.get(i-1);
		        	 HSSFRow row =sheet.createRow(lastRow+i); //在现有行号后追加数据  
		        row.createCell(0).setCellValue(result.getUrl()); //设置第一个单元格的数据  
		        row.createCell(1).setCellValue(result.getSection()); //设置第二个单元格的数据  
		        row.createCell(2).setCellValue(result.getGb2312Text());
		        row.createCell(3).setCellValue(String.valueOf(result.getLevel())); 
		        row.createCell(4).setCellValue(result.getSeedUrl()); 
		        row.createCell(5).setCellValue(result.getRootUrl()); 
		        row.createCell(6).setCellValue(result.getRootSection()); 
		        row.createCell(7).setCellValue(result.getProvince()); 
		        }
		          
		        out.flush();  
		        wb.write(out);    
		        out.close(); 
			
			
		}
	 } catch (Exception e) {
			e.printStackTrace();
		}
		
}



public static void write2file(String directory,String filename,
		LinkedHashMap<String, String> noUrlFindWebSite) throws Exception{
	
	
	String path=directory+filename;
	File file = new File(path);
	
	
	 if(!file.getParentFile().exists()) {  
		  file.getParentFile().mkdirs();  
	  }
	if(!file.exists()) {
	    file.createNewFile();
	}
	
	FileOutputStream fos = new FileOutputStream(file,true);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

	for (Entry<String,String>   entry:noUrlFindWebSite.entrySet()) {
        bw.write(entry.getKey());
        bw.write(smallBlank);
        bw.write(entry.getValue());
        bw.write(smallBlank);
        bw.newLine();
    }
    bw.close();
    fos.close();
}


/**
 * 递归删除目录下的所有文件及子目录下所有文件
 * @param dir 将要删除的文件目录
 * @return boolean Returns "true" if all deletions were successful.
 *                 If a deletion fails, the method stops attempting to
 *                 delete and returns "false".
 *  
 *  file.delete()用于删除"某个文件或者空目录"
 */
public static boolean deleteDir(File dir) {
	
    if (!dir.exists()) {
        return false;
      }
    
   
	
    if (dir.isDirectory()) {
        String[] children = dir.list();
        //递归删除目录中的子目录下
        for (int i=0; i<children.length; i++) {
            boolean success = deleteDir(new File(dir, children[i]));
            if (!success) {
                return false;
            }
        }
    }
    // 目录此时为空，可以删除
    
    return dir.delete();
}


public static String readIndex(File file){
    
    try {
       
        FileInputStream in=new FileInputStream(file);
        InputStreamReader re=new InputStreamReader(in);
        BufferedReader reader=new BufferedReader(re);
        //逐行读取
        String line="";
        while((line=reader.readLine())!=null){
              return line;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "未获取到index";
}



public static void writeIndex(File file, String content) throws Exception {
	
	if(!file.exists()) {
	    file.createNewFile();
	}
	FileOutputStream fos=null;
	try {
		fos = new FileOutputStream(file);
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    bw.write(content);
	bw.newLine();
	
	
	bw.close();
	
	fos.close();
}



/**
* @author yuan hai 2016年11月14日
* @param fileName   绝对路径
* @return  文件行数
* @throws IOException
*/
public static  int getTotalLines(String path) throws IOException {
	InputStream is = FileUtils.class.getResourceAsStream(path);
	InputStreamReader in = new InputStreamReader(is);
       LineNumberReader reader = new LineNumberReader(in);
       String strLine = reader.readLine();
       int totalLines = 0;
       while (strLine != null) {
           totalLines++;
           strLine = reader.readLine();
       }
       reader.close();
       in.close();
       return totalLines;
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


	
	
	//单词去重
	
	/**
	 * @author yuan hai 2016年11月23日
	 * @param path  类路径
	 * @return   生成文件的绝对路径(在target目录下，不在config目录下)
	 * @throws Exception
	 */
	public static String keywordDereplication( String path) throws Exception {
		
		HashSet<String>  set=  new HashSet<String>();
		
     	loadFile2Set(path, set);
     	//把去重后的文件重新写入
     	
     	writeSet2File(FileUtils.class.getResource(path).getFile(),set);
     	
     	log.info("after dereplication ,new file path:"+FileUtils.class.getResource(path).getFile());
     	return FileUtils.class.getResource(path).getFile();
     	
     	
	}



	
	/**
	 * @author yuan hai 2016年11月23日
	 * @param path  绝对路径
	 * @param set
	 * @throws Exception
	 */
	public static void writeSet2File(String path, HashSet<String> set) throws Exception {
		
	
		File file = new File(path);
		
		if(!file.getParentFile().exists()) {  
			  file.getParentFile().mkdirs();  
		  }
		
		if(!file.exists()) {
		    file.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(file);
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (String keyword:set) {
	        bw.write(keyword);
	        bw.newLine();
	    }
	    bw.close();
	    fos.close();
	}
	
	

	
}
