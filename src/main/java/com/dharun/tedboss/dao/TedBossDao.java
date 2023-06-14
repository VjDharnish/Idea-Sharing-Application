package com.dharun.tedboss.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import com.dharun.tedboss.dto.Post;
import com.dharun.tedboss.dto.User;

public class TedBossDao {
	
	private static Connection connection;
	private static Statement statement;
	
	private static TedBossDao tedBossDao;
	private TedBossDao() {
		
	}
	public static TedBossDao getInstance() {
		if(tedBossDao==null) {
			try {
				//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRM", "root", "Idharu@10");
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tedboss","root","Idharu@10");
				statement = connection.createStatement();
				
				tedBossDao = new TedBossDao();
			}
			catch(SQLException e) {
				System.out.println(e);
				return null;
			}
		}
		return tedBossDao;
		
	}
	public boolean isValidUser(String email, String password) {
		String query ="SELECT password FROM credentials  where emailid= '"+email+"' ";
		try {
			ResultSet rs = statement.executeQuery(query);
			if(rs.next()) {
				if(password.equals(rs.getString("password"))) {
					return true;
				}
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String addUser(User user) {
		String email = user.getEmailid();
		String query1 = "SELECT emailid from credentials where emailid = '"+email+"' "; 
		try {
			ResultSet rs = statement.executeQuery(query1);
			if(rs.next()) {
				return "exisiting User";
			}
			String name =user.getName();
			String profilePicture= user.getProfilePicture();
			String password = user.getPassword();
			String query2= "INSERT INTO userinfo (emailid,name,profile_pic)values(?,?,?)";
			PreparedStatement stmt =connection.prepareStatement(query2);
			stmt.setString(1, email);
			stmt.setString(2,name);
			stmt.setString(3,profilePicture);
			if(stmt.executeUpdate()>0) {
				
				String query3= "INSERT INTO credentials(emailid,password)values(?,?)";
				PreparedStatement stmt2 =connection.prepareStatement(query3);
				stmt2.setString(1, email);
				stmt2.setString(2, password);
				if(stmt2.executeUpdate()>0) {
					System.out.println("add user hit");
					return "success";
					
				}
						
			}			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "failure";
	}
	public List<String> getTopics(String email) {
		List<String> topics = new ArrayList<>();
		String query="SELECT * from topics";
		try {
			ResultSet rs  = statement.executeQuery(query);
			while(rs.next()) {
				topics.add(rs.getString("topic_name"));
			}
			System.out.println("Topics get email");
			return topics;
		} catch (SQLException e) {
			System.out.println("Topics get Failed email");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public List<String> getAllTopics() {
		List<String> topics = new ArrayList<>();
		String query = "SELECT * FROM topics";
		try {
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				topics.add(rs.getString("topic_name"));
			}
			System.out.println("topics get");
			return topics;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("topics get failed");
			e.printStackTrace();
			return null;
		}
		
	}
	public boolean addTopics(String email, String[] topics) {
		for(int i =0;i<topics.length;i++) {
			String topic = topics[i];
			String query="INSERT INTO user_topics(emailid,topic)values(?,?)";
			try {
				PreparedStatement preparedStatement =connection.prepareStatement(query);
				preparedStatement.setString(1, email);
				preparedStatement.setString(2,topic);
				if(preparedStatement.executeUpdate()>0) {
					System.out.println("add topic hit");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return true;
	}
	public boolean addPost(Post newPost) {
		String email = newPost.getEmailid();
		String query1="INSERT INTO postinfo(emailid,title,content,image)values(?,?,?,?)";
		try {
			PreparedStatement stmt1=connection.prepareStatement(query1);
			stmt1.setString(1,email);
			stmt1.setString(2, newPost.getTitle());
			stmt1.setString(3,newPost.getContent());
			stmt1.setString(4,newPost.getImage());
			if(stmt1.executeUpdate()>0) {
				String query2= "SELECT postid from postinfo where title= ?";
				PreparedStatement stmt2=connection.prepareStatement(query2);
				stmt2.setString(1, newPost.getTitle());
				ResultSet rs  = stmt2.executeQuery();
				int postId=0;
				if(rs.next()) {
					postId= rs.getInt("postid");
				}
				String[] topics = newPost.getTopics();
				for(int i =0;i<topics.length;i++) {
					String topic = topics[i];
					String query3="INSERT INTO post_topic(name,postid)VALUES(?,?)";
					PreparedStatement stmt3=connection.prepareStatement(query3);
					stmt3.setString(1,topic);
					stmt3.setInt(2, postId);
					if(stmt3.executeUpdate()>0)
						System.out.println("topic updated");
				}
				
				System.out.println("Topic update COmplted");
				return true;
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public List<Post> getPosts(String search) {
		List<Post> posts = new ArrayList<>();
		String query = "SELECT * FROM postinfo pi LEFT JOIN post_topic pt ON pi.postid = pt.postid " +
	               "WHERE pt.name = ? OR pi.title LIKE ?";

		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, search);
			stmt.setString(2,"%" +search+"%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String emailid =rs.getString("emailid");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String image = rs.getString("image");
				int likes = rs.getInt("likes");
				posts.add(new Post(emailid,title,content,image,likes));
			}
			System.out.println("post of Search get");
			return posts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("post of Search get failed");
			e.printStackTrace();
			
		}
		return posts;
	}
	public PriorityQueue<Post> getTopicPosts(String topic) {
//		List<Post> posts =new ArrayList<>();
		PriorityQueue<Post> posts = new PriorityQueue<Post>(new Comparator<Post>() {
			public int compare(Post a,Post b) {
				int alikes = b.getLikes();
				int blikes = b.getLikes();
				return blikes-alikes;
			}
		});
		String query = "select * from postinfo pii inner join post_topic pt on pii.postid =pt.postid where pt.name = '"+topic+"' ";
		try {
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				String emailid =rs.getString("emailid");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String image = rs.getString("image");
				int likes = rs.getInt("likes");
				posts.add(new Post(emailid,title,content,image,likes));
			}
			System.out.println("post of topic get");
			return posts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("post of topic get failed");
			e.printStackTrace();
			return null;
		}
	}
	public User getUserDetails(String email) {
		User profile= null;
		String query = "SELECT * from userinfo where emailid=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String name =rs.getString("name");
				String bio = rs.getString("bio");
				String profilePic =rs.getString("profile_pic");
				int postCount = rs.getInt("posts");
				profile = new User(name,email,profilePic,postCount,bio);
			}		
			
		} catch (SQLException e) {
			System.out.println("ERROR IN GET USER DETAILS");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return profile;
	}
	public boolean editProfile(User user) {
		String query = "UPDATE userinfo SET name =?,profile_pic=?,bio=? where emailid=?" ; 
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, user.getName());
			
			stmt.setString(2,user.getProfilePicture());
			stmt.setString(3, user.getBio());
			stmt.setString(4, user.getEmailid());
			if(stmt.executeUpdate()>0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	
	
	
	
	}

