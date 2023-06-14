package com.dharun.tedboss.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dharun.tedboss.dao.TedBossDao;
import com.google.gson.Gson;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password=request.getParameter("password");
		boolean isValidUser =TedBossDao.getInstance().isValidUser(email,password);
		if(isValidUser) {
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			response.getWriter().print("success");
			return;
		}
		response.getWriter().print("failure");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
