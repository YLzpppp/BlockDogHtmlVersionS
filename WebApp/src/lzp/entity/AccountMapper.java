package lzp.entity;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.params.ParameterizedTest;


public interface AccountMapper {
		/*
		 * @param account
		 * 用于在用户登录的时候对IP地址进行更新
		 */
		public void updateIpByAccount(Account account );
		
		/*
		 * @param ip
		 * @return
		 * 
		 * 在抢狗的时候根据IP地址查询对应是哪个用户在操作
		 */
		public Account queryNameByIp(String ip);
		
		/*
		 *  在抢狗的时候根据IP地址对相应用户的最后一次操作时间进行更新
		 */
		public void updateTimeByIp(Account account);
		
		/*
		 *  根据账号获取密碼
		 */
		public String queryPasswordByAccount(String acc);
	
		/*
		 *  根据账号名获取用户名
		 */
		public String queryNameByAccount(String acc);
}
