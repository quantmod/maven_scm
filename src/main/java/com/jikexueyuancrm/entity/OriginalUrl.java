package com.jikexueyuancrm.entity;

// Generated 2016-12-21 10:10:53 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OriginalUrl generated by hbm2java
 */
@Entity
@Table(name = "original_url")
public class OriginalUrl implements java.io.Serializable {

	private Long id;
	private String url;
	private String orgwebname;
	private Byte status;
	private Integer count;
	private String totime;

	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	private Date createDate;
	private Date updateDate;
	public OriginalUrl() {
	}

	public OriginalUrl(String url, String orgwebname, Byte status,
			Integer count, String totime) {
		this.url = url;
		this.orgwebname = orgwebname;
		this.status = status;
		this.count = count;
		this.totime = totime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "url", length = 1024)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "orgwebname", length = 50)
	public String getOrgwebname() {
		return this.orgwebname;
	}

	public void setOrgwebname(String orgwebname) {
		this.orgwebname = orgwebname;
	}

	@Column(name = "status")
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "totime", length = 50)
	public String getTotime() {
		return this.totime;
	}

	public void setTotime(String totime) {
		this.totime = totime;
	}

}
