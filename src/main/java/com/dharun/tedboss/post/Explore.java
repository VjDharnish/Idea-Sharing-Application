package com.dharun.tedboss.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.dharun.tedboss.dao.TedBossDao;
import com.dharun.tedboss.dto.Post;
import com.google.gson.Gson;

/**
 * Servlet implementation class Explore
 */
@WebServlet("/explore")
public class Explore extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONObject getAllTopics() {
		List<String> topics = TedBossDao.getInstance().getAllTopics();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(topics);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topics", jsonArray);

		return jsonObject;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action= request.getParameter("action");
		PrintWriter out = response.getWriter();
		
		if(action.equals("topicposts")) {
			String topic  = request.getParameter("topic");
			System.out.println(topic);
			PriorityQueue<Post> tempPosts= getTopicPosts(topic);
			HashSet<Post> posts = new HashSet<>();
			posts.addAll(tempPosts);
			System.out.println(posts);
			if(posts==null|| posts.size() == 0) {
				out.print(-1);
				return;
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(posts);
			out.println(json);
			return;
			
		}
		
		JSONObject jsonObject = getAllTopics();
		
		out.println(jsonObject);
	}
	private PriorityQueue<Post> getTopicPosts(String topic) {
		PriorityQueue<Post> posts = TedBossDao.getInstance().getTopicPosts(topic);
		return posts;
	}

	

}
