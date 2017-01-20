package com.atguigu.spring.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component//基本注解
public class EmployeeDao {
    
    @Autowired
    public JdbcTemplate jdbcTemplate;
    
    
  //获取配置文件中的address值
  	@Value("${address}")
  	public String address;
  	
  	
    public Employee get(Integer id){
        String sql="select id,last_name,email from tables where id=?";
        RowMapper<Employee> rowMapper=new BeanPropertyRowMapper(Employee.class);
        Employee employee=jdbcTemplate.queryForObject(sql, rowMapper, id);
        return employee;
    }
}