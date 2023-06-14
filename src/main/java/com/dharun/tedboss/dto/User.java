package com.dharun.tedboss.dto;

public class User {
	private String name;
	private String emailid;
	private String password;
	private String profilePicture="https://www.bing.com/ck/a?!&&p=b266d885d091b9e2JmltdHM9MTY4MTA4NDgwMCZpZ3VpZD0zNGJlMjIwMS01ZmVhLTY3NjctMTczZS0zMGRiNWUzODY2ZTgmaW5zaWQ9NTYyNQ&ptn=3&hsh=3&fclid=34be2201-5fea-6767-173e-30db5e3866e8&u=a1L2ltYWdlcy9zZWFyY2g_cT1EZWZhdWx0JTIwSW1hZ2UlMjBGb3IlMjBQcm9maWxlJkZPUk09SVFGUkJBJmlkPTBFQzVCNTg5NEU4Qzk5MzhDMEU5MTQyQzhEQjNBRDZEQTg4OUI2QjE&ntb=1"; 
	private int postCount;
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	private String bio;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPostCount() {
		return postCount;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public User(String name, String emailid, String password) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.password = password;
		
	}
	
	public User(String name, String emailid, String profilePicture, int postCount, String bio) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.profilePicture = profilePicture;
		this.postCount = postCount;
		this.bio = bio;
	}
	public User(String name, String emailid, String profilePicture, String bio) {
		super();
		this.name = name;
		this.emailid = emailid;
		this.profilePicture = profilePicture;

		this.bio = bio;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", emailid=" + emailid + ", password=" + password + ", profilePicture="
				+ profilePicture + ", bio=" + bio + "]";
	}
	
	
	
	
}
