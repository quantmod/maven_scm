package com.jikexueyuancrm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class FolderCopy {

	
	public static boolean fileCopy(String src,String des){
		
		File srcFile=new File(src);
		File desFile=new File(des);
		byte[] b=new byte[1024];
		  try {
			  FileInputStream fis=new FileInputStream(srcFile);
			  FileOutputStream fos=new FileOutputStream(desFile,false);
			  while(true){
				  int i=fis.read(b);
				  if(i==-1)break;
				  fos.write(b,0,i);
			  }
			  fos.close();
			  fis.close();
			  return true;
		} catch (Exception e){ 
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	/**
	 * @author yuan hai 2016年11月16日
	 * @param src   源目录路径
	 * @param des   目标目录路径
	 * @return   
	 */
	public static boolean folderCopy(String src,String des){
		File srcFile=new File(src);
		File desFile=new File(des);
		File []files=srcFile.listFiles();
		boolean flag = false;
		if(!desFile.exists())desFile.mkdir();
		for(int i=0;i<files.length;i++){
			String path=files[i].getAbsolutePath(); 
			if(files[i].isDirectory()){
				File newFile=new File(path.replace(src,des));
				if(!newFile.exists())newFile.mkdir();//不存在新建文件夹
				folderCopy(path,path.replace(src,des));
			}
			else
			  flag=fileCopy(path,path.replace(src,des));//文件复制函数
			}
		return flag;
	}
	
	
	
	public static void main(String[] args) {
	folderCopy("d:\\1","C:\\1" );

	}

	
	
	
	
	
	
}
