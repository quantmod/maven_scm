package com.jikexueyuancrm.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.spring.jdbc.EmployeeDao;

 
@RunWith(SpringJUnit4ClassRunner.class) //让测试运行于spring测试环境,在使用所有注解前必须使用,需要junit 4.5以上
@ContextConfiguration(locations = "classpath:applicationContext-jdbc.xml")  
public class SpringJUnitTest {  

    @Autowired  
    private  JdbcTemplate jdbcTemplate;
    
    @Autowired  
   private EmployeeDao   employeeDao;
    
    
    @Test
    public void testUpdate(){
        String sql="update tables set last_name=? where id=?";
        jdbcTemplate.update(sql, "caoni", 1);
    }
    
    @Test
    public void testEmployeeDao(){
        System.out.println(employeeDao.get(1).getEmail());
        System.out.println(employeeDao.address);
    }
    
}  