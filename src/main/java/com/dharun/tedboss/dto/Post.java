package com.dharun.tedboss.dto;

public class Post {
	private String emailid;
	private String title;
	private String content;
	private String[] topics;
	private String image;
	private int likes=0;;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getTopics() {
		return topics;
	}
	public void setTopics(String[] topics) {
		this.topics = topics;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public Post(String emailid,String title, String content, String[] topics, String image) {
		super();
		this.emailid=emailid;
		this.title = title;
		this.content = content;
		this.topics = topics;
		this.image = image;
	}
	public Post(String emailid,String title, String content, String image,int likes) {
		super();
		this.emailid=emailid;
		this.title = title;
		this.content = content;
		this.image = image;
		this.likes=likes;
	}
	
	
	
	public String getEmailid() {
		return emailid;
		
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	@Override
	public boolean equals(Object o) {
		  if (o == null || getClass() != o.getClass()) return false;
		String content = ((Post)o).getContent();
		return this.content.equals(content);
	}

}
