package com.dharun.tedboss.post;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dharun.tedboss.dao.TedBossDao;
import com.dharun.tedboss.dto.Post;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean addPost(String emailid, String postString) {
		JSONParser parser = new JSONParser();
		try {
			System.out.println(postString);
			JSONObject obj = (JSONObject) parser.parse(postString);

			String title = (String) obj.get("title");
			String content = (String) obj.get("content");

			String image = (String) obj.get("image");

			JSONArray topics = (JSONArray) obj.get("topics");

			String[] topicArray = new String[topics.size()];
			int k = 0;
			for (Object topic : topics) {
				topicArray[k++] = (String) topic;
			}
			System.out.println(Arrays.toString(topicArray));
			Post newPost = new Post(emailid, title, content, topicArray, image);
			boolean addPost = TedBossDao.getInstance().addPost(newPost);
			return addPost;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Post ServletbExcept Add Post");
			e.printStackTrace();
		}

		return false;

	}

	private JSONObject getAllTopics() {
		List<String> topics = TedBossDao.getInstance().getAllTopics();
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(topics);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("topics", jsonArray);

		return jsonObject;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		String emailid = (String) session.getAttribute("email");
		System.out.println(emailid);
		if(emailid==null) {
			response.getWriter().append("-5");
			return;
		}
		if (action.equals("addpost")) {

			String postString = request.getParameter("Post");
			String topic = request.getParameter("title");
			System.out.println("Post hit");
			boolean postAdd = addPost(emailid, postString);
			response.getWriter().print(postAdd);

		}
		if (action.equals("getTopics")) {
			JSONObject jsonObject = getAllTopics();
			PrintWriter out = response.getWriter();
			out.println(jsonObject);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
