package com.dharun.tedboss.profile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dharun.tedboss.dao.TedBossDao;
import com.dharun.tedboss.dto.User;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session= request.getSession();
		String email =(String) session.getAttribute("email");
		String name = request.getParameter("name");
		String profilepic = request.getParameter("profilepic");
		String bio = request.getParameter("bio");
		User user = new User(name,email,profilepic,bio);
		boolean edited = TedBossDao.getInstance().editProfile(user);
		PrintWriter out  =response.getWriter();
		System.out.println(edited);
		out.println("<script>alert("+"Edited Successfully"+")</script>");
		response.sendRedirect("home.html");
		
		return;
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
