package com.jikexueyuancrm.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.jikexueyuancrm.entity.ErrorUrl;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.entitycommon.ErrorReqUrlStat;
import com.jikexueyuancrm.util.DateUtils;






public class ErrorReqUrlStatImpl extends HibernateTemplate  {
	
	
	
	private static Logger log = Logger.getLogger(ErrorReqUrlStatImpl.class);
	






	

	
 public  List<ErrorReqUrlStat> getListByPage(int start,int pageSize,int code,String date) {
	  Date beginDate = DateUtils.parse(date, DateUtils.FORMAT_SHORT);
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		
		//setMaxResults 是想要返回的最大记录数。
		//setFetchSize 是关于sql优化的，需要数据库的支持。如：你想要1000条，fetch size设置的是100，则数据库返回100条，然后再100条，总共10次。
		String hql="from ErrorReqUrlStat  where httpStatus = ? and insertDatime >= ?  order by id asc ";
		
		
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		 query.setParameter(0, code);
		    query.setParameter(1, beginDate);
		query.setFirstResult(start);
		query.setMaxResults(pageSize);
	    List<ErrorReqUrlStat> list = query.list();
	    
	
		return list ;
		
	}
	
	
	
public  Long getCount(int code,String date) {
		
	    Date beginDate = DateUtils.parse(date, DateUtils.FORMAT_SHORT);
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		String count="select count(*) from ErrorReqUrlStat where httpStatus = ? and insertDatime >= ?  ";
	    Query queryCount = this.getSessionFactory().getCurrentSession().createQuery(count);
	    queryCount.setParameter(0, code);
	    queryCount.setParameter(1, beginDate);
	   Long num= (Long) queryCount.uniqueResult();
	    log.info("totalNum:"+num);
		return num ;
		
	}
	
	
	
	

	

	
	
	
	

	
	
}
