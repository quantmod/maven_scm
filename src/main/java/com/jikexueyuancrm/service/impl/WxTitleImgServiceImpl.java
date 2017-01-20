package com.jikexueyuancrm.service.impl;


import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;








import com.jikexueyuancrm.entity.WxTitleImg;





public class WxTitleImgServiceImpl extends HibernateTemplate  {
	
	
	
	private static Logger log = Logger.getLogger(WxTitleImgServiceImpl.class);
	
	
	

	
	public void saveWxTitleImg(WxTitleImg wxTitleImg) {
		
		
		this.getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		//等价于this.getHibernateTemplate().getSessionFactory().getCurrentSession().setFlushMode(FlushMode.AUTO);
		this.save(wxTitleImg);
		 log .info(wxTitleImg.getId());
		 
		
	}




	
	
	
	
}
