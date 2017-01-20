package com.jikexueyuancrm.util;

public class PageUtils {
	
	
	/**
	 * 
	 * 得到总页数
	 * @param totalCount
	 * @param pageSize
	 * @return
	 */
	public static int getTotalPage(int totalCount,int pageSize ){
		
		int totalPage=0;
		if(totalCount % pageSize==0){
			
			totalPage=totalCount/pageSize;
		}else{
			
			totalPage=totalCount/pageSize+1;
		}
		
		return totalPage;
		
	}
	
	
	
	

}
