package com.jikexueyuancrm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler","arcticelLinks"})
//@Entity
//@Table(name="HistoryArcticlesLink")
public class HistoryArcticlesLink  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -907379285097483859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	
	@OneToMany(mappedBy="historyArcticlesLinkMapping",fetch=FetchType.LAZY)//OneToMany指定了一对多的关系，mappedBy=""指定了由多的那一方来维护关联关系，mappedBy指的是多的一方对1的这一方的依赖的属性，(注意：如果没有指定由谁来维护关联关系，则系统会给我们创建一张中间表)
	@LazyCollection(LazyCollectionOption.EXTRA)//　LazyCollection属性设置成EXTRA指定了当如果查询数据的个数时候，只会发出一条 count(*)的语句，提高性能
	private Set<ArcticleLink>  arcticelLinks;
	
	
	public Set<ArcticleLink> getArcticelLinks() {
		return arcticelLinks;
	}
	public void setArcticelLinks(Set<ArcticleLink> arcticelLinks) {
		this.arcticelLinks = arcticelLinks;
	}
	public Date time=new Date();
    @Column(length=1000)
	public String fullurl;
	public String biz;
	
	
	public String uin;
	public String appkey;
	
	
	
public String pass_ticket;
	
	public String frommsgid;//起点
	
	 
	public Boolean isfollow=false;//关注
	
	
	public Integer count;
	
	public String getFullurl() {
		return fullurl;
	}
	public void setFullurl(String fullurl) {
		this.fullurl = fullurl;
	}
	public String getBiz() {
		return biz;
	}
	public void setBiz(String biz) {
		this.biz = biz;
	}
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	
	
	public Boolean getIsfollow() {
		return isfollow;
	}
	public void setIsfollow(Boolean isfollow) {
		this.isfollow = isfollow;
	}
	
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrommsgid() {
		return frommsgid;
	}
	public void setFrommsgid(String frommsgid) {
		this.frommsgid = frommsgid;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	
	public String getPass_ticket() {
		return pass_ticket;
	}
	public void setPass_ticket(String pass_ticket) {
		this.pass_ticket = pass_ticket;
	}
	
}
