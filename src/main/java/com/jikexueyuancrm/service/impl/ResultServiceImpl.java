package com.jikexueyuancrm.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.log.Log;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
















import com.jikexueyuancrm.controller.Consumer;
import com.jikexueyuancrm.controller.MidSizeNewsWebsite;
import com.jikexueyuancrm.entity.ArcticleLink;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.util.FileUtils;






public class ResultServiceImpl extends HibernateTemplate  {
	
	
	
	private static Logger log = Logger.getLogger(ResultServiceImpl.class);
	
	public static ArrayList<Result> newUrl =new ArrayList<Result>();
	
	public void saveResult(Result result) {
		
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
	
		this.save(result);
		log.info(result.getId());
		}
		
		
public void saveResult(ArrayList<Result> list) {
		
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.COMMIT);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		for(Result result:list){
			if(this.isExist(result.getUrl())) {
			log.info("url exist!");	
				continue;	
			}
		log.info("new url found!");	
	newUrl.add(result);
		this.save(result);
		log.info(result.getId());
		
		}
		
	
		
		}
			
	



	public boolean isExist(String url) {
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		
		//排除只相差一个"/"
		if(url.endsWith("/")){
			
			String urlNo=url.substring(0, url.lastIndexOf("/"));
			
			List<Result> list= (List<Result>) this.find("from Result where url in ('"+url+"', '"+ urlNo+"')");

			if(list.size()>0){
				return true;
			}
		
		}else{
			String 	urlYes=url+"/";
			List<Result> list1= (List<Result>) this.find("from Result where url in ('"+url+"','"+ urlYes+"')");
		
			if(list1.size()>0)  return true;
		}
		return false;
	}

	

	
	
}
