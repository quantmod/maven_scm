package com.jikexueyuancrm.test;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
//测试类
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.atguigu.spring.jdbc.Employee;
import com.atguigu.spring.jdbc.EmployeeDao;
public class JDBCTest {
    
	private static ApplicationContext ctx = null;
	private static JdbcTemplate jdbcTemplate;
	private static EmployeeDao employeeDao;
	private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	static {
		ctx = new ClassPathXmlApplicationContext("applicationContext-jdbc.xml");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
		employeeDao = ctx.getBean(EmployeeDao.class);
		namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
         System.out.println(employeeDao.address);
	
	}

	
	
	@Test
	public void testNamedParameterJdbcTemplate3() {
	List<Integer> ids = new ArrayList<Integer>();
	ids.add(1);
	ids.add(2);
	ids.add(3);
	ids.add(5);
	String sql = "SELECT * FROM tables WHERE ID IN(:ids)";
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("ids", ids);
	
	RowMapper<Employee> rowMapper=new BeanPropertyRowMapper<Employee>(Employee.class);
	
	List<Employee> result = namedParameterJdbcTemplate.query(sql, params,rowMapper);
	
	  System.out.println(result.size());
	
	}

   /*** 使用命名参数(参数别名)时, 可以使用 update(String sql, SqlParameterSource paramSource) 方法进行更新操作
   * 1. SQL 语句中的参数名和类的属性一致!即上边employee中属性一致
　　* 2. 使用 SqlParameterSource 的 BeanPropertySqlParameterSource 实现类作为参数.
　　*/
	@Test
	public void testNamedParameterJdbcTemplate2() {
		String sql = "insert into tables(last_name,email,dept_id) values(:lastName,:email,:deptId)";
		Employee employee = new Employee();
		employee.setLastName("panpan");
		employee.setEmail("@panpan");
		employee.setDeptId(4);
		
		//其实NamedParameterJdbcTemplate为咱们提供的参数模型不止Map,还有SqlParameterSource和BeanPropertySqlParameterSource。
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(
				employee);
		namedParameterJdbcTemplate.update(sql, paramSource);
	}



//可以为参数起名字
//好处: 若有多个参数, 则不用再去对应位置, 直接对应参数名, 便于维护
//缺点: 较为麻烦.
@Test
	public void testNamedParameterJdbcTemplate() {
		String sql = "insert into tables(last_name,email,dept_id) values(:la,:em,:de)";

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("la", "fsd");
		paramMap.put("em", "@ji");
		paramMap.put("de", 3);

		namedParameterJdbcTemplate.update(sql, paramMap);

	}


	
	
	
	

    @Test
    //对基于注解的方法测试
    public void testEmployeeDao(){
        System.out.println(employeeDao.get(1).getEmail());
    }
    
    
    //获取单个列的值, 或做统计查询
    //使用 queryForObject(String sql, Class<Long> requiredType) 
    @Test
    public void testQueryForObject2(){
        String sql="select count(id) from tables";
        long count=jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println(count);
    }
    
    //查到实体类的集合
    //注意调用的不是 queryForList 方法
    @Test
    public void testQueryForList(){
        String sql="select id,last_name,email from tables where id>=?";
        RowMapper<Employee> rowMapper=new BeanPropertyRowMapper<Employee>(Employee.class);
        List<Employee> employees=jdbcTemplate.query(sql, rowMapper, 1);
        
        System.out.println(employees.get(1).getLastName());
    }
    
    /**
     * 从数据库中获取一条记录, 实际得到对应的一个对象
     * 注意不是调用 queryForObject(String sql, Class<Employee> requiredType, Object... args) 方法!
     * 而需要调用 queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
     * 1. 其中的 RowMapper 指定如何去映射结果集的行, 常用的实现类为 BeanPropertyRowMapper
     * 2. 使用 SQL中列的别名完成列名和类的属性名的映射. 例如 last_name lastName
     * 3. 不支持级联属性. JdbcTemplate 到底是一个 JDBC 的小工具, 而不是 ORM 框架
     */
    @Test
    public void testQueryForObject(){
        String sql="select id,last_name lastName,email,dept_id  from tables where id=?";
        
        RowMapper<Employee> rowMapper=new BeanPropertyRowMapper<Employee>(Employee.class);
        Employee employee=jdbcTemplate.queryForObject(sql, rowMapper,1);
        
        System.out.println(employee.getLastName());
    }
    
    //执行批量更新: 批量的 INSERT, UPDATE, DELETE
    //最后一个参数是 Object[] 的 List 类型: 因为修改一条记录需要一个 Object 的数组, 
    //那么多条就需要多个 Object 的数组
    @Test
    public void testBatchUpdate(){
        String sql="insert into tables(last_name,email,dept_id) values(?,?,?)";
        
        List<Object[]> batchArgs=new ArrayList<Object[]>();
        
        batchArgs.add(new Object[]{"AAA","testa",5});
        batchArgs.add(new Object[]{"BBB","JIJK",6});
        batchArgs.add(new Object[]{"CCC","panpN",3});
        batchArgs.add(new Object[]{"DDD","jfids",2});
        batchArgs.add(new Object[]{"EEE","tfsd",4});
        
        jdbcTemplate.batchUpdate(sql,batchArgs);
    }
    
    //执行 INSERT, UPDATE, DELETE，只修改sql语句，即可
    @Test
    public void testUpdate(){
        String sql="update tables set last_name=? where id=?";
        jdbcTemplate.update(sql, "权", 1);
    }
    
    //测试数据库连接池是否连接成功
    @Test
    public void testDataSource() throws SQLException {
        DataSource  dataSource=ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }

}