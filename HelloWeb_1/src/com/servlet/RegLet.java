package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class RegLet extends HttpServlet {

	private static final long serialVersionUID = -4415294210787731608L;

	/**
	 * The doGet method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		// 新建服务对象
		Service serv = new Service();
		
		// 接收注册信息
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
		// 返回信息
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
		// 验证处理
		boolean reged = serv.register(username,password,nickname);
		if (reged) {
			System.out.print("Succss in Register");
			request.getSession().setAttribute("username", username);
			
			out.print("success");
		} else {
			System.out.print("failed");
			out.print("failed");
		}

		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the Server let.
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
