package com.dharun.tedboss.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.dharun.tedboss.dao.TedBossDao;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Servlet implementation class AddTopics
 */
@WebServlet("/addTopics")
public class AddTopics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			System.out.println(email);
			String jsonString = request.getParameter("myArray");
	        Gson gson = new Gson();
	        JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
	        String[] stringArray = new String[jsonArray.size()];
	        for (int i = 0; i < jsonArray.size(); i++) {
	            stringArray[i] = jsonArray.get(i).getAsString();
	        }
	        System.out.println(Arrays.toString(stringArray));
	        
	        boolean isAdded =TedBossDao.getInstance().addTopics(email,stringArray);
	        PrintWriter out= response.getWriter();
	        System.out.println(isAdded);
	        if(isAdded) {
	        	out.println("success");
	        	return;
	        }
	        out.println("failure");
	        
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
