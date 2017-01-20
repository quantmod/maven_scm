package com.jikexueyuancrm.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.jikexueyuancrm.entity.ErrorUrl;
import com.jikexueyuancrm.entity.Result;






public class ErrorUrlServiceImpl extends HibernateTemplate  {
	
	
	
	private static Logger log = Logger.getLogger(ErrorUrlServiceImpl.class);
	

	
	public void saveResult(ErrorUrl result) {
		
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
	
		this.save(result);
		log.info(result.getId());
		}


	public  List<ErrorUrl> getByCodeAndThreadNum(int code,int threadNum) {
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		String hql="from ErrorUrl where ishandled=0 and code=?  order by id asc ";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(threadNum);
		query.setParameter(0, code);
		 List<ErrorUrl> list =     query.list();
		return list;
	}


	public void updateErrorUrl(ErrorUrl error) {
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		this.update(error);
		log.info(error.getId());
		
	}


	public  List<ErrorUrl> getList(int limit) {
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		
		
		String hql="from ErrorUrl where ishandled=1 and  isdeleted =0 and newcode > 299  order by id asc ";
		
		
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(limit);
		
	    List<ErrorUrl> list = query.list();
	    
	
		return list ;
		
	}

	
public  List<ErrorUrl> getList() {
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		
		
		String hql="from ErrorUrl where ishandled=1 and  isdeleted =0 and newcode =200   order by id asc ";
		
		
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);
	
		
	    List<ErrorUrl> list = query.list();
	    
	
		return list ;
		
	}
	
	
	
public  Long getCount() {
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		String count="select count(*) from ErrorUrl where ishandled=1 and  isdeleted =0 and newcode > 299  order by id asc ";
	    Query queryCount = this.getSessionFactory().getCurrentSession().createQuery(count);
	   Long num= (Long) queryCount.uniqueResult();
	    log.info("totalNum:"+num);
		return num ;
		
	}
	
	
	
	

	public void updateList(List<ErrorUrl> list) {
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
	   	for(  ErrorUrl ls: list ){
	   		
	   		this.update(ls);
	   	}
	}

	
	
	
	

	
	
}
