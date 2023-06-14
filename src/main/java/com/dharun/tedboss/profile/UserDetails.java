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
import com.google.gson.Gson;

/**
 * Servlet implementation class UserDetails
 */
@WebServlet("/UserDetails")
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		PrintWriter out = response.getWriter();
		System.out.println("UsreDetails Hit");
		if(email==null) {
			out.print("-1");
			return;
		}
		User user = TedBossDao.getInstance().getUserDetails(email);
		System.out.println(user);
		Gson gson = new Gson();
        String json = gson.toJson(user);
   
      //  response.setContentType("application/json");
      //  response.setCharacterEncoding("UTF-8");
       System.out.println(json);
        out.print(json);
			
	}


}
