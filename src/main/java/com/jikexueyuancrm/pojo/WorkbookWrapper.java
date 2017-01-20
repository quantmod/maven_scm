package com.jikexueyuancrm.pojo;

import java.util.LinkedHashMap;

public class WorkbookWrapper {

	
	private LinkedHashMap<String,String[][]> sheets;

	public LinkedHashMap<String, String[][]> getSheets() {
		return sheets;
	}

	public void setSheets(LinkedHashMap<String, String[][]> sheets) {
		this.sheets = sheets;
	}
}
