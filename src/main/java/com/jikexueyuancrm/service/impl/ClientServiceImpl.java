package com.jikexueyuancrm.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.jikexueyuancrm.entity.ErrorUrl;
import com.jikexueyuancrm.entity.OriginalUrl;
import com.jikexueyuancrm.entity.Result;
import com.jikexueyuancrm.entitycommon.ErrorReqUrlStat;
import com.jikexueyuancrm.util.DateUtils;






public class ClientServiceImpl extends HibernateTemplate  {
	
	
	
	private static Logger log = Logger.getLogger(ClientServiceImpl.class);
	






	

	




/**
 * @author yuan hai 2016年12月21日
 * @param name   客户端名称
 * @return
 */
public List<OriginalUrl> findByClient(String name) {
	this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
	
	List<OriginalUrl>  lists =(List<OriginalUrl>) this.find("from OriginalUrl where orgwebname  like '%"+name+"%'");
	return lists;
}














public void saveOriginalUrl(OriginalUrl originalUrl) {
	this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
	this.save( originalUrl);
	
}
	
	
	
	

	

	
	
	
	

	
	
}
