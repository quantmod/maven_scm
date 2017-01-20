package com.jikexueyuancrm.util;



import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @see SpringXML 文件操作类
 * @author Administrator
 * 
 */
public class SpringXmlCommon {

	public static String[] classpath = new String[] { "classpath:applicationContext.xml" };

	/**
	 * @see 加载SpinrgXML
	 * @return
	 */
	public static ApplicationContext init() {
		return new ClassPathXmlApplicationContext(classpath);
	}

	/**
	 * @see 取SessionFactory
	 * @param key
	 * @return
	 */
	public static SessionFactory getSessionFactory(String key) {
		return (SessionFactory) init().getBean(key);
	}

	/**
	 * @see 取Bean
	 * @param key
	 * @return
	 */
	public static Object getBean(String key) {
		return init().getBean(key);
	}

}
