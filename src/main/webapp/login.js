/**
 * 
 */

 loginform = document.getElementById("login-form");
signupform = document.getElementById("signup-form");
loginbtn = document.getElementById("login-button");
signupbtn = document.getElementById("signup-button");

loginbtn.addEventListener("click",function(){
    loginform.style.display="block";
    signupform.style.display="none";
})
signupbtn.addEventListener("click",function(){
    signupform.style.display="block";
    loginform.style.display="none";
    
})

function validPassword(password){
    let passRegex=/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
    if (!passRegex.test(password)) {
        alert("Please enter a valid password. Password must contain at least 8 characters, including at least one uppercase letter, one lowercase letter, one digit, and one special character (!@#$%^&*)");
        return false;
      }
      return true;
}
function valid2password(password){
    if(!validPassword(password))
    return;
let repassword=document.getElementById("repassword").value;
if(password !== repassword){
    alert("Password Mismatch");
    return false;
    }
  return true;
}

function validateName(name){
	var nameRegex = /^[a-zA-Z ]+$/;
  if (!nameRegex.test(name)) {
    alert("Please enter a valid name");
    return false;
  }
  return true;
	
}

function validateEmail(email){
    var emailRegex = /\S+@\S+\.\S+/;
    if (!emailRegex.test(email)) {
      alert("Please enter a valid email address");
      return false;
    }
    return true;
}
function validateUser(){
	let email  = document.getElementById("email").value;
	let password  = document.getElementById("pass").value;
	if(!validateEmail(email) || !validPassword(password)){
		 return;
	}

	ajaxCall("http://localhost:8080/tedboss/login?email="+email+"&password="+password, "GET", "")
	.then(function(result){
		console.log(typeof result);
			if(result === 'failure'){		
			console.log("failure");
			alert("Invalid Credentials");
			return;
		}
		else{
		window.location.href="home.html";
		return ;
		}
	});
		
}
function addUser(){
	let emailid  = document.getElementById("emailid").value;
	let name  = document.getElementById("name").value;
	let password = document.getElementById("password").value;
	if(!validateEmail(emailid)||!valid2password(password)||!validateName(name))
		return;
	var input = "email="+emailid+"&name="+name+"&password="+password;
	ajaxCall("http://localhost:8080/tedboss/signup","POST",input)
	.then(function(response){
		if(response==='failure'){
			alert("Please Try Again Later");
			return;
		}
		if(response==='existing user'){
			alert("Already Existing User Please Login to continue");	
			loginform.style.display="block";
    		signupform.style.display="none";
    		return;	
		}
		response=JSON.parse(response);
		 var result =response.topics;
		 
		 var header= document.getElementById('header');
		 signupform.style.display="none";
		 header.style.display="block";
		 
		 var content = document.getElementById('content');

		//heading.innerHTML=`<h3>Select Minimum 3 Topics</h3>`;
		
		
		for(let i =0;i<result.length;i++){
			content.innerHTML+=`<button class = "btn-elm" onclick='addArray('${result[i]}')'><p class='elm'>${result[i]}</p></button>`;
			
		}
		header.innerHTML+=` <button class="next-btn" onclick="storeTopics()">Next
  </button>`
		
		
	})
	
	
		
}

var topicsArray=[];

function addArray(val){
	topicsArray.push(val);
}

function storeTopics(){
	if(topicsArray.length<3){
		alert("Select Atleast 3 Topics");
		return;
	}
	let jsonString = JSON.stringify(topicsArray); 
	
	ajaxCall("http://localhost:8080/tedboss/addTopics?myArray="+jsonString,"GET","")
	.then(function(response){
		if(response==='success'){
			window.location.href="home.html";
			return;
		}
		alert("Error");
		return;
	})
	
}



function ajaxCall(url, requestMethod, inputs){
	let promise = new Promise(function(resolve, reject){
		var xhr = new XMLHttpRequest();
	
		xhr.onreadystatechange = function() {
			if(xhr.readyState == 4) {
				resolve(xhr.responseText);
			}
		}
		xhr.open(requestMethod, url);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		inputs!== "" && xhr.send(inputs);
		inputs=== "" && xhr.send();
	});
	return promise;
}