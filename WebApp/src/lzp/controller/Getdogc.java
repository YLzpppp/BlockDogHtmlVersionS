package lzp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lzp.dao.Dao;

@WebServlet("/Getdogc")
public class Getdogc extends HttpServlet {
	private static final long serialVersionUID = 121L;

	private boolean userlistsorted = false;
	private int dogcount = 2;
	private ArrayList<String> userList = new ArrayList<String>();
	private ArrayList<Long> timeList = new ArrayList<Long>();
	private Map<String, Long> ipandtime = new HashMap<String, Long>();
	private Map<String , Integer> rushtimes = new HashMap<String, Integer>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/plain");
		response.setHeader("Access-Control-Allow-Origin","*");
		PrintWriter writer = response.getWriter();
		
		System.out.println(userList.toString()+"\n"+ timeList+"\n"+ ipandtime+"\n"+rushtimes);
		/*
		 *   收到请求先查userList 是否有对应的，无则表示首次抢购
		 *   把用户IP和时间记录下来
		 */
		Calendar c = Calendar.getInstance();
		String remoteaddr = request.getRemoteAddr();
		
		if(!userList.contains(remoteaddr)) {
			//list中没有改用户IP地址记录，为首次请求
			String username = Dao.queryNameWithIp(remoteaddr);

			SimpleDateFormat sFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
			String consoletime = sFormat.format(c.getTime());
			System.out.println("用户："+username+"发起抢狗请求，时间为 "+consoletime+" 加入处理队列中，等待处理结果...\n");
			Long time = c.getTimeInMillis() ;
			//用于登记进数据库的时间戳
			userList.add(remoteaddr);
			timeList.add(time);
			ipandtime.put(remoteaddr, time);
			rushtimes.put(remoteaddr, 1);
			
			System.out.println("\n本时间段狗狗出售总数："+dogcount+"   目前抢狗请求队列待处理数："+userList.size()+"\n正在等待更多用户请求增加-----------------\n\n");
			String timemillisec = String.valueOf(time ); 
			//更新数据库时间戳
			Dao.updateTimeWithIp(timemillisec, remoteaddr);
			writer.write("30");
			System.out.println("第一批结果将于30秒后处理完成并等待用户再次发起请求来获取结果");
		}else {
			/*
			 * 请求IP地址的用户非本段时间首次请求，已是第二次回调请求更新处理结果
			 * 
			 * 判断userlistsorted 是否true，为false则对userList排序，为true则表示已有二次请求对
			 * userList排序了，并获得了结果
			 */
			System.out.println("正在准备数据返回给用户\n");
			
			int oldtimes = rushtimes.get(remoteaddr);
			rushtimes.replace(remoteaddr, ++oldtimes);
			
			if(!userlistsorted) {
				System.out.println("结果校对完成\n");
				Collections.sort(timeList);
				userlistsorted = true;
				//排序完成，返回结果
				System.out.println("服务端正在返回结果给用户\n");
				if(oldtimes > 2) {
					//发起抢购请求已经超过2次，直接返回 no 
					writer.write("no");
				}else if(oldtimes == 2) {
					//刚好是第二次请求处理结果
					Long origintime = ipandtime.get(remoteaddr);
					int index = timeList.indexOf(origintime);
					if((index+1) <= dogcount) {
						writer.write("yes");
					}else {
						writer.write("no");
					}
				}
				System.out.println("-------------------结果返回完毕-----------------\n");
				
			}else {
				//已排序
				//判断rushtimes对应的记录是2还是以上，2的话返回结果。否则返回 no
				//rushtimes记录是2 ， 判断ip对应的time记录在list中排序的序号是否小于等狗狗总数
				//小于等于则表示抢到了,返回 yes
				System.out.println("服务端正在返回结果给用户\n");
				
				if(oldtimes > 2) {
					//发起抢购请求已经超过2次，直接返回 no 
					writer.write("no");
				}else if(oldtimes == 2) {
					//刚好是第二次请求处理结果
					Long origintime = ipandtime.get(remoteaddr);
					int index = timeList.indexOf(origintime);
					if((index+1) <= dogcount) {
						writer.write("yes");
					}else {
						writer.write("no");
					}
				}
				System.out.println("-------------------结果返回完毕-----------------\n");

			}
		}
		
		
		writer.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
