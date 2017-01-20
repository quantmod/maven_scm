package com.jikexueyuancrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="Result")
public class Result {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String seedUrl;
	
	
	@Column(columnDefinition = "varchar(2048) DEFAULT NULL")	
	private String url;
	
	private String province;
	private int level;
	
	private   String section;
	
	private String gb2312Text;//section是gb2312编码的情况
	
	private String type;
	
	private String rootUrl;
	
	private String rootSection;
	
	private Integer code;
	
	private Date createTime=new Date();

	public String getSeedUrl() {
		return seedUrl;
	}

	public void setSeedUrl(String seedUrl) {
		this.seedUrl = seedUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSection() {
		return section;
	}

	
	public void setSection(String section) {
		this.section = section;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getRootSection() {
		return rootSection;
	}

	public void setRootSection(String rootSection) {
		this.rootSection = rootSection;
	}

	@Override
	public String toString() {
		return "Result [seedUrl=" + seedUrl + ", url=" + url + ", level="
				+ level + ", section=" + section + ", type=" + type
				+ ", rootUrl=" + rootUrl + ", rootSection=" + rootSection + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGb2312Text() {
		return gb2312Text;
	}

	public void setGb2312Text(String gb2312Text) {
		this.gb2312Text = gb2312Text;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}



}
