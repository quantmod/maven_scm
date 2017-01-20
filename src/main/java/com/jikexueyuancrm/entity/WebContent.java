package com.jikexueyuancrm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//@Entity
//@Table
public class WebContent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	  @Column(length=1000)
	private String title;
	private String resourse;//文章发布者
	   @Column(length=1000)
	private String url;
	  @Column(columnDefinition="TEXT",nullable=true)
	 //  @Column(length=65535)会报错
	private String content;
	private String captureWebsite;
	private String originType;
	private int browseTime;
	private int commentTime;
	private Date published;
	private Date createTime =new Date();
	private int status =1;
	private String titleHs;
	private Integer crawlerId;
	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(String praiseNum) {
		this.praiseNum = praiseNum;
	}



	private String readNum;
	private String praiseNum;
	

	
	

	private String biz;
	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}


	
	private String author;
	
	public WebContent() {

	}

	/**
	 * 
	 * @param title
	 *            标题
	 * 
	 * @param resourse
	 *            来源
	 * 
	 * @param url
	 *            链接
	 * 
	 * @param content
	 *            内容
	 * 
	 * @param captureWebsite
	 *            采集网站
	 * 
	 * @param originType
	 *            来源类型('wz','lt','bk','wb','jw','tb','wx')
	 * 
	 * @param browseTime
	 *            浏览次数
	 * 
	 * @param commentTime
	 *            评论次数
	 * 
	 * @param published
	 *            发布时间
	 * 
	 * @param createTime
	 *            入库时间
	 * 
	 * @param status
	 *            '是否有效 1：有效，0：无效',
	 * 
	 * @param crawlerId
	 *            爬虫Id
	 */
	public WebContent(String title, String resourse, String url,
			String content, String captureWebsite, String originType,
			int browseTime, int commentTime, Date published, Date createTime,
			int status) {
		super();
		if(content!=null&&content.length()>5000){
			content = content.substring(0,5000)+"...";
		}
		this.title = title;
		this.resourse = resourse;
		this.url = url;
		this.content = content;
		this.captureWebsite = captureWebsite;
		this.originType = originType;
		this.browseTime = browseTime;
		this.commentTime = commentTime;
		this.published = published;
		this.createTime = createTime;
		this.status = status;
	}
	
	public WebContent(String title, String resourse, String url,
			String content, String captureWebsite, String originType,
			int browseTime, int commentTime, Date published, Date createTime,
			int status, String titleHs ,int crawlerId) {
		if(content!=null&&content.length()>5000){
			content = content.substring(0,5000)+"...";
		}
		this.title = title;
		this.resourse = resourse;
		this.url = url;
		this.content = content;
		this.captureWebsite = captureWebsite;
		this.originType = originType;
		this.browseTime = browseTime;
		this.commentTime = commentTime;
		this.published = published;
		this.createTime = createTime;
		this.status = status;
		this.titleHs = titleHs;
		this.crawlerId = crawlerId;
	}
	public WebContent(String title, String resourse, String url,
			String content, String captureWebsite, String originType,
			int browseTime, int commentTime, Date published, Date createTime,
			int status, Integer crawlerId) {
		super();
		if(content!=null&&content.length()>5000){
			content = content.substring(0,5000)+"...";
		}
		this.title = title;
		this.resourse = resourse;
		this.url = url;
		this.content = content;
		this.captureWebsite = captureWebsite;
		this.originType = originType;
		this.browseTime = browseTime;
		this.commentTime = commentTime;
		this.published = published;
		this.createTime = createTime;
		this.status = status;
		this.crawlerId = crawlerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getResourse() {
		return resourse;
	}

	public void setResourse(String resourse) {
		this.resourse = resourse;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		if(content!=null&&content.length()>5000){
			content = content.substring(0,5000)+"...";
		}
		this.content = content;
	}

	public String getCaptureWebsite() {
		return captureWebsite;
	}

	public void setCaptureWebsite(String captureWebsite) {
		this.captureWebsite = captureWebsite;
	}

	public String getOriginType() {
		return originType;
	}

	public void setOriginType(String originType) {
		this.originType = originType;
	}

	public int getBrowseTime() {
		return browseTime;
	}

	public void setBrowseTime(int browseTime) {
		this.browseTime = browseTime;
	}

	public int getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(int commentTime) {
		this.commentTime = commentTime;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitleHs() {
		return titleHs;
	}

	public void setTitleHs(String titleHs) {
		this.titleHs = titleHs;
	}

	public Integer getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(Integer crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
	
	
}