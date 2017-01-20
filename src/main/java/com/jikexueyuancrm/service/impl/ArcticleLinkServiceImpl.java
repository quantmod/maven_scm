package com.jikexueyuancrm.service.impl;


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
import com.jikexueyuancrm.entity.ArcticleLink;


//spring下载地址
//http://repo.springsource.org/libs-release-local/org/springframework/spring/



public class ArcticleLinkServiceImpl extends HibernateTemplate  {
	
	
	
	private static Logger log = Logger.getLogger(ArcticleLinkServiceImpl.class);
	
	
	
	
	
	

	
	public void saveArcticleLink(ArcticleLink arcticleRequest) {
		
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.save(arcticleRequest);
		
		log.info(arcticleRequest.getId());
	}

	
	public ArcticleLink get() {
		
		String queryString="from ArcticleLink ar where status=true and running=false and serverId="+Consumer.serverId+" order by id ";
	Query query=this.getSessionFactory().getCurrentSession().createQuery(queryString);
		query.setLockMode("ar", LockMode.PESSIMISTIC_WRITE);
		query.setMaxResults(1);
	
		ArcticleLink ar=	(ArcticleLink) query.uniqueResult();
		
		return ar;
	}



	public void updateArcticleLink(ArcticleLink ar) {
	
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.update(ar);
	
	}


	public void deleteArcticleLink(ArcticleLink ar) {
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.delete(ar);
		
	}
	
	
}
