package com.jikexueyuancrm.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import com.jikexueyuancrm.entity.WebContent;
import com.jikexueyuancrm.entity.WxTitleImg;



public class WebContentServiceImpl extends HibernateTemplate  {
	
	
	private static Logger log = Logger.getLogger(WebContentServiceImpl.class);
	
	
	
	
	

	
	public void saveWebContent(WebContent webContent ){
		
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.save(webContent);
		log .info(webContent.getId());
	}




	
	
	
	
}
