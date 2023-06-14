<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="com.dharun.tedboss.dao.TedBossDao" 
    import ="com.dharun.tedboss.dto.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
</head>
<body>
<%! 

	User profile;
%>

<%
	String email = (String)session.getAttribute("email");
	if(email==null)
		return;
	 profile = TedBossDao.getInstance().getUserDetails(email);

%>
<form action='EditProfile' method='post'>
	<table>
		<tr><td><img name='profilepic' src='<%= profile.getProfilePicture()%>'/></td></tr>
		<tr><td>Name:</td><td><input type='text' name='name' value='<%= profile.getName()%>'/></td></tr>
		<tr><td>Bio:</td><td><input type='text' name='bio' value='<%= (profile.getBio()==null)?"":profile.getBio()%>'/></td></tr>
		<tr><td colspan='2'><input type='submit' value='Edit & Save '/></td></tr>	
	</table>
	
</form>

</body>
</html>