package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

/**
 * Servlet implementation class SetUserInfo
 */
@WebServlet("/SetUserInfo")
public class SetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username=request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String nickname=request.getParameter("nickname");
		nickname = new String(nickname.getBytes("ISO-8859-1"), "UTF-8");
		String brief_intro=request.getParameter("brief_intro");
		brief_intro = new String(brief_intro.getBytes("ISO-8859-1"), "UTF-8");
	    Service serv = new Service();

	    // 返回信息
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 
		// 验证处理
		boolean ok = serv.setUserInfo(username,nickname,brief_intro);
		if (ok) {
			System.out.print("Succss in setUserInfo");
			request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
			out.print("success");
			
		} else {
			System.out.print("Failed to setUserInfo");
			out.print("failed");
		}
	    
		 out.flush();
		 out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
