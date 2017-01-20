package com.jikexueyuancrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信标题图片实体类
 * 
 */


//@Entity
//@Table
public class WxTitleImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	   @Column(length=1000)
	private String fisrtImgurl;//微信文章第一个图片链接

	@Column(length=1000)
	private String contentUrl;//微信文章链接
	private Date createTime=new Date();//写入时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getFisrtImgurl() {
		return fisrtImgurl;
	}
	public void setFisrtImgurl(String fisrtImgurl) {
		this.fisrtImgurl = fisrtImgurl;
	}

}

