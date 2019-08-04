package lzp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.sun.accessibility.internal.resources.accessibility;

import lzp.entity.Account;
import lzp.entity.AccountMapper;

public class Dao{
	private static SqlSession getSession() throws IOException {
		String mybatisconf = "mybatis-config.xml";
	    InputStream  inputStream = Resources.getResourceAsStream(mybatisconf);
	    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
	    SqlSession session = factory.openSession();
	    return session;
	}

	public static String queryNameWithIp( String ip) throws IOException {
		SqlSession session = getSession();
		AccountMapper mapper = session.getMapper(AccountMapper.class);
		
		Account account = mapper.queryNameByIp(ip);
		session.commit();
		session.close();
		return account.getName();
	}
	public static void updateIpWithAccount(String acc,String ip) throws IOException {
		SqlSession session = getSession();
		AccountMapper mapper = session.getMapper(AccountMapper.class);
		
		Account account = new Account(null, null, acc, null, ip, null);
		mapper.updateIpByAccount(account);
		session.commit();
		session.close();
	}
	public static void updateTimeWithIp(String time,String ip) throws IOException {
		SqlSession session = getSession();
		AccountMapper mapper = session.getMapper(AccountMapper.class);
		
		Account account = new Account(null, null, null, null, ip, time);
		mapper.updateTimeByIp(account);
		session.commit();
		session.close();
	}
	public static String queryPasswordWithAccount(String acc) throws IOException{
		SqlSession session = getSession();
		AccountMapper mapper = session.getMapper(AccountMapper.class);
		
		String p = mapper.queryPasswordByAccount(acc);
		session.commit();
		session.close();
		return p;
	}
	public static String queryNameWithAccount(String acc) throws IOException{
		SqlSession session = getSession();
		AccountMapper mapper = session.getMapper(AccountMapper.class);
		
		String name = mapper.queryNameByAccount(acc);
		session.commit();
		session.close();
		return name;
	}
	@Test
	public void test() throws IOException {
			Calendar c = Calendar.getInstance();
		
			System.out.println(c.getTimeInMillis()+"\n"+c.getTime());
	}

	
}


//System.out.println(queryNameWithIp("192.168.1.100"));
//updateIpWithAccount("123456", "192.168.1.100");
//updateTimeWithIp("123456","192.168.1.100" );
//String s = queryPasswordWithAccount("1519");
//if(s==null) {
//	System.out.println("密碼错误");
//	};