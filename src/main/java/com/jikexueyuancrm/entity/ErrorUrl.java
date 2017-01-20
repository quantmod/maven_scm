package com.jikexueyuancrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Index;
@Entity
@Table(name="error")
public class ErrorUrl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String times;
	

	//@Column(unique=true)
	//@Index(name="urlindex")
	private String url;//唯一索引
	
	@Column(columnDefinition = "int(11) DEFAULT NULL")
	private Integer code;
	
	private String description;
	
	
	private String type;
	private String crawlname;
	
	@Column(columnDefinition = "varchar(255) NOT NULL DEFAULT 'HtmlUnit'")
	private String client;
	
	
	@Column(columnDefinition = "varchar(1024) DEFAULT NULL" )
	private String expMsg;
	
	
   @Column(columnDefinition="LONGTEXT",nullable=true)
		 //  @Column(length=65535)会报错
	private String content;
	@Column(name="newcode")
	private Integer newcode;
	
	@Column(name="isdeleted",columnDefinition = "int(10) NOT NULL DEFAULT '0'")
	private   int isdeleted;
	
	
	
	@Column(name="ishandled",columnDefinition = "int(10) NOT NULL DEFAULT '0'")
 
	private int ishandled;
	
	@Column(name="updateTime",columnDefinition ="timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	
	private Date updateTime=new Date();
	 
	private Date insertDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	

	public int getIshandled() {
		return ishandled;
	}

	public void setIshandled(int ishandled) {
		this.ishandled = ishandled;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	

	public Integer getNewcode() {
		return newcode;
	}

	public void setNewcode(Integer newcode) {
		this.newcode = newcode;
	}

	public int getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(int isdeleted) {
		this.isdeleted = isdeleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCrawlname() {
		return crawlname;
	}

	public void setCrawlname(String crawlname) {
		this.crawlname = crawlname;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getExpMsg() {
		return expMsg;
	}

	public void setExpMsg(String expMsg) {
		this.expMsg = expMsg;
	}

	public Date getCreateTime() {
		return insertDateTime;
	}

	public void setCreateTime(Date createTime) {
		this.insertDateTime = createTime;
	}

	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}
	
	



}
