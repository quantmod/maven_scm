package com.jikexueyuancrm.entitycommon;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="error_req_url_stat")
public class ErrorReqUrlStat {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="crawler_name",columnDefinition = "char(32) NOT NULL")
	public String crawlerName;
	
	@Column(name="crawler_type",columnDefinition = "enum('lt','wz') NOT NULL")
	public String crawlerType;
	
	@Column(name="url",columnDefinition = "DEFAULT NULL")
	public String url;
	
	
	@Column(name="http_status",columnDefinition = " DEFAULT NULL")
	public Integer httpStatus;
	@Column(name="failure_count",columnDefinition = " DEFAULT NULL")
	public Integer failureCount;
	
	@Column(name="insert_datime",columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP")
	public Date insertDatime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCrawlerName() {
		return crawlerName;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}

	public String getCrawlerType() {
		return crawlerType;
	}

	public void setCrawlerType(String crawlerType) {
		this.crawlerType = crawlerType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public Date getInsertDatime() {
		return insertDatime;
	}

	public void setInsertDatime(Date insertDatime) {
		this.insertDatime = insertDatime;
	}

}
