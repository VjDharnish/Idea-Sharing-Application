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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dharun.tedboss.dao.TedBossDao;
import com.dharun.tedboss.dto.User;
import com.google.gson.Gson;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User user  = new User(name,email,password);
		System.out.println(user);
		String message = TedBossDao.getInstance().addUser(user);
		System.out.println(message);
		if(message=="success") {
			HttpSession session=request.getSession();
			session.setAttribute("email", email);
			List<String> topics= TedBossDao.getInstance().getAllTopics();
			 JSONArray jsonArray = new JSONArray();
		        jsonArray.addAll(topics);
		        JSONObject jsonObject = new JSONObject();
		        jsonObject.put("topics", jsonArray);
		        PrintWriter out = response.getWriter();
		        out.println(jsonObject);
		        return;
		}
		response.getWriter().append(message);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
