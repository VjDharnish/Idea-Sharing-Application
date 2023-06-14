package com.dharun.tedboss.post;

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
import com.dharun.tedboss.dto.Post;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetPosts
 */
@WebServlet("/getposts")
public class GetPosts extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public GetPosts() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		if(session.getAttribute("email")==null) {
			out.print("-5");
			return;
		}
		String search  = request.getParameter("search");
		List<Post> posts = TedBossDao.getInstance().getPosts(search);
		System.out.println(posts);
		if(posts.size() ==0) {
			out.print("-1");
			return;
		}
		Gson gson = new Gson();
		String json = gson.toJson(posts);
		out.println(json);
		
		
		
	}


}
