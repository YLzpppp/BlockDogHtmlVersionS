package lzp.controller;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lzp.dao.Dao;

@WebServlet("/Loginc")
public class Loginc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/plain");
		response.setHeader("Access-Control-Allow-Origin","*");
		PrintWriter writer = response.getWriter();
		
		String remoteip = request.getRemoteAddr().toString();
		
		String acc = request.getParameter("account");
		String pwd = request.getParameter("password");
		
		String p = Dao.queryPasswordWithAccount(acc);
		if(p == null) {
			writer.write("accnotexist");
			System.out.println("用户输入的账号为: "+acc+"   该账号不存在 |  客户端IP地址为: "+remoteip);
		}else { //根据账号查找密碼，不null，则找到了账号对应的密碼，进行对比
			if(p.equals(pwd)) {
				//找到的密碼与用户输入的密碼相匹配。 更新用户IP地址，并打印log
				String username= Dao.queryNameWithAccount(acc);
				System.out.println("用户："+username+"  成功登陆APP");
				Dao.updateIpWithAccount(acc, remoteip);
				writer.write("success");
			}else {
				//用户密碼错误
				writer.write("pwdwrong");
			}
		}
		writer.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
