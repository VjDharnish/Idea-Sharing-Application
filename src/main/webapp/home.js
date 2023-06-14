window.onload = function() {
	checkLogin();
}

var beforeSignup = document.getElementById('before-signup');
var afterLogin = document.getElementById('after-login');
var createPost = document.getElementById('postscreen');
var topicsArray = [];
var postImage;
var addpostTopics = document.getElementById("postTopics");
var totalTopics = [];
var subButton = document.getElementById('sub-btn');
var row = document.querySelector('.row');
var postcreate  = document.getElementById("createPost");
var loginflag = false;
var popup = document.getElementById("popup");
var topicss= document.getElementById('topicss'); 
var popupcontent = document.querySelector(".popup-content");
addpostTopics.addEventListener('click', function(event) {
	let targ = event.target;
	if (targ.checked) {
		topicsArray.push(targ.value);
	}
	else {
		const index = topicsArray.indexOf(targ.value);
		if (index > -1) {
			topicsArray.splice(index, 1);
		}
	}

});



function isLoggedHome(){
	let isLogin = checkLogin();
	console.log(isLogin)
	if(isLogin){
		
	}
}
function checkLogin() {
	ajaxCall("http://localhost:8080/tedboss/checkLogin", "GET", "")
		.then(function(response) {
			if (response === 'true') {
				beforeSignup.style.display = "none";
				afterLogin.style.display = "block";
				loginflag=true;
				return true;
			}
			return false;
		})
}

function createpost(){
	if(!loginflag){
		alert("Please Login to Add Post");
		window.location.href="login.html";
		return;
	}
	
	var poster = document.getElementById('poster');
	poster.innerHTML='';	
	document.querySelector('.row').style.display='none';
	createPost.style.display = "block";
}
function addPost() {
	let title = document.getElementById("title").value;
	let content = document.getElementById("content").value;
	let image = document.getElementById("image");

}
function explore(){
	var header = document.querySelector('.header');
	header.style.display='none';
	createPost.style.display = 'none';
	
	
	if(!loginflag){
		alert("Please Login to Add Post");
		window.location.href="login.html";
		return;
	}	
	if (topicss.childNodes.length >5){
		topicss.style.display='block';
		return;
	}
	ajaxCall("http://localhost:8080/tedboss/explore?action=explore", "GET", "")
	.then(function(response){
		response= JSON.parse(response);
		console.log(response);
		const arr =response.topics;
		
		
		
		for(let i = 0;i<arr.length;i++){    
			const label = document.createElement('div');
			label.className="col-sm-6 col-md-4 col-lg-3";
			const topic= document.createElement('button');
			
			/*(function(index) {
    topic.onclick = getTopicContents(arr[i]);
  })(i); */
			topic.textContent=arr[i];
			topic.value=arr[i];
			topic.onclick=()=>{
				getTopicContents(arr[i]);
			}
			topic.className="btn btn-primary";
			label.prepend(topic);
			row.appendChild(label);
			
		}
		document.querySelector('.row').style.display='block';
	});
}


function getTopicContents(item){
	if(!loginflag){
		alert("Please Login to Add Post");
		window.location.href="login.html";
		return;
	}
	//
	
	ajaxCall("http://localhost:8080/tedboss/explore?action=topicposts&topic="+item, "GET", "")
	.then(function(response){
		if(response ==-1){
			openPopup("Currently No Ideas Available");
			popup.addEventListener("click",function(){
				closePopup();
				
			})
			return;
		}
		document.querySelector('.header').style.display='none';
		response = JSON.parse(response);
		postshower(response);
		
	})
		
}




function searchmodalvisible() {
	document.getElementById("searchmodal").style.display = "block";
}

function searchmodalhide() {
	document.getElementById("imagetext").value = "";
	document.getElementById("searchmodal").style.display = "none";
}

function showImages() {

	var searchimg = document.getElementById("imagetext").value;
	var apiurl = "https://api.pexels.com/v1/search?query=" + searchimg + "&per_page=5&page=1";
	fetch(apiurl, {
		headers: {
			Authorization: "uEsKXCWDV1A3vLN5ulaSjPpQIdGEkmudVKI6ZFdeXeuYtuEFK9MELwYg"
		}
	}).then(response => response.json())
		.then(data => {
			var imageContainer = document.getElementById("image-container");
			imageContainer.innerHTML = "";

			const photos = data.photos;
			for (let i = 0; i < photos.length; i++) {
				const photo = photos[i];
				imageContainer.innerHTML += `<img src="${photo.src.medium}" alt="${photo.photographer}" data-url="${photo.src.original}"  onclick ="addimage('${photo.src.medium}')">`;
			}
			const images = document.querySelectorAll("image-container img");


		})




}

function addimage(imgUrl) {
	var imgsrc = document.getElementById("imagetext").value;
	var img = document.getElementById("image");
	postImage = imgUrl;
	img.innerHTML = "";
	let show = `<img id = 'img-url' src="${imgUrl}" alt="" style="height: 30vh;width: 45vw;">`;

	img.innerHTML = show;
	searchmodalhide();
}



function getTopics() {
	ajaxCall("http://localhost:8080/tedboss/post?action=getTopics", "GET", "")
		.then(function(response) {
			response = JSON.parse(response);
			totalTopics = response.topics;
			document.querySelector('.addpost').style.display ='none';
			
			totalTopics.forEach((item, index) => {
				const label = document.createElement('label');
				label.innerHTML = item;

				const checkbox = document.createElement('input');
				checkbox.type = 'checkbox';
				checkbox.name = `item-${index}`;
				checkbox.value = item;
				checkbox.setAttribute('id', 'checkbox10');
				label.prepend(checkbox);
				addpostTopics.appendChild(label);
			});
			topicsFunction();

		});
}


function topicsFunction() {

	subButton.innerHTML += `<button onclick="addPost()">Submit</button>`;

}



function addPost() {

	var title = document.querySelector('.idea-title').value;
	console.log(title);
	var content = document.getElementById("post-content").value;
	console.log(content);
	if (title === "") {
		alert("Please Enter a Title")
		return;
	}
	if (content === "") {
		alert("Please fill your Idea");
	}

	if (topicsArray.length < 1 || topicsArray.length > 3) {
		alert("Select Atleast 1 topic related to the content");
		return;
	}
	if (postImage == undefined) {
		postImage = "";
	}
	var post = {
		'title': title,
		'content': content,
		'image': postImage,
		'topics': topicsArray
		
	};
    post  = JSON.stringify(post);
	let input = "action=addpost&Post=" + post;
	ajaxCall("http://localhost:8080/tedboss/post", "POST", input)
		.then(function(response) {
			if (response === 'true') {
				alert("Post Created Successfully");
				createPost.style.display ='none';
			}
			if(response== -5){
				alert("Login to Add Post");
				window.location.href="login.html";
				
			}
			title = "";
			content = "";
			topicsArray = [];
			postImage = "";
		})


}

function searchClick(){
	
	let search = document.getElementById('search').value;
	createPost.style.display='none';
	if(search ===""){
		openPopup("Please Enter What Idea you want");
			popup.addEventListener("click",function(){
				popup.style.display = 'none';
				
			});
			
			return;
	}
	ajaxCall("http://localhost:8080/tedboss/getposts?search="+search,"GET","")
	.then(function(response){
		// 
		if(response== -5){
			openPopup("Login to see posts");
			popup.addEventListener("click",function(){
				popup.style.display = 'none';
				window.location.href="login.html";
			});
			
			return;
		}
		if(response ==-1){
			openPopup("Currently No Posts Available");
			popup.addEventListener("click",function(){
				
				popup.style.display = 'none';
				
			});
			
			return;
		}
		response = JSON.parse(response);
		console.log(response);
		postshower(response);
		
	});
}
function postshower(posts){


var poster = document.getElementById('poster');
poster.innerHTML='';
const postboxContainer = document.createElement('div');
postboxContainer.id = 'postbox-container';
console.log(posts);

// Loop through the posts array and create post boxes dynamicallyp
posts.forEach((post, index) => {
  // Create the post box section
  const postboxSection = document.createElement('section');
  postboxSection.id = `postbox-${index + 1}`;

  // Create the idea container
  const ideaContainer = document.createElement('div');
  ideaContainer.id = `idea-${index + 1}`;
  ideaContainer.classList.add('ideas-1');

  // Create the post content container
  const postContent = document.createElement('div');
  postContent.classList.add('postcontent');

  // Create the first division
  const division1 = document.createElement('div');
  division1.classList.add('division-1');

  // Create the child elements within the first division
  const child1 = document.createElement('div');
  child1.classList.add('child-1');

  const ideaImage = document.createElement('div');
  ideaImage.classList.add('idea-image');

  const imageSpan = document.createElement('span');
  imageSpan.style.cssText =
    'box-sizing: border-box; display: block; overflow: hidden; width: initial; height: initial; background: none; opacity: 1; border: 0px; margin: 0px; padding: 0px; position: absolute; inset: 0px;';

  const ideaImg = document.createElement('img');
  ideaImg.alt = 'stories';
  ideaImg.sizes = '100vw';
  ideaImg.src = "https://deepstash.com/_next/image?url=https%3A%2F%2Fd1dfxfqogsjixt.cloudfront.net%2F295303-1683541442.jpg&w=2048&q=75";
  ideaImg.decoding = 'async';
  ideaImg.dataset.nimg = 'fill';
  ideaImg.classList.add('idea-card-image');
  ideaImg.style.cssText =
    'position: absolute; inset: 0px; box-sizing: border-box; padding: 0px; border: none; margin:auto; display: block; width:0px; height: 0px; min-width: 100%; max-width: 100%; min-height: 100%; max-height: 100%;object-fit: cover;';

  imageSpan.appendChild(ideaImg);
  ideaImage.appendChild(imageSpan);
  child1.appendChild(ideaImage);

  const child2 = document.createElement('div');
  child2.classList.add('child-2');

  const ideaTitle = document.createElement('h2');
  ideaTitle.classList.add('idea-title');
  ideaTitle.textContent = post.title;

  const contentBox = document.createElement('div');
  contentBox.classList.add('content-box');

  const outsideContent = document.createElement('div');
  outsideContent.classList.add('outside-content');

  const insideContent = document.createElement('p');
  insideContent.classList.add('inside-content');
  insideContent.style.marginBottom = '12px';
  insideContent.textContent = post.content;

  outsideContent.appendChild(insideContent);
  contentBox.appendChild(outsideContent);
  child2.appendChild(ideaTitle);
  child2.appendChild(contentBox);
  division1.appendChild(child1);
  division1.appendChild(child2);

  // Create the second division
  const division2 = document.createElement('div');
  division2.classList.add('division-2');

  // Create the user face container
  const userFace = document.createElement('div');
  userFace.classList.add('userface');
  userFace.style.display = 'flex';

  // Create the like button
  const likeButton = document.createElement('button');
  likeButton.id = `likeButton-${index + 1}`;
  likeButton.onclick = toggleLike;

  const likeIcon = document.createElement('i');
  likeIcon.classList.add('far', 'fa-heart');

  const likeFill = document.createElement('span');
  likeFill.id = `like-fill-${index + 1}`;

  likeButton.appendChild(likeIcon);
  likeButton.appendChild(likeFill);

  // Create the "likes" text
  const likesText = document.createElement('p');
  likesText.textContent = 'likes';

  // Create the bookmark button
  const bookmarkButton = document.createElement('button');
  bookmarkButton.id = `bookmarkButton-${index + 1}`;
  bookmarkButton.onclick = toggleBookmark;

  const bookmarkIcon = document.createElement('i');
  bookmarkIcon.classList.add('far', 'fa-bookmark');

  const bookmarkFill = document.createElement('span');
  bookmarkFill.id = `bookmark-fill-${index + 1}`;

  bookmarkButton.appendChild(bookmarkIcon);
  bookmarkButton.appendChild(bookmarkFill);

  // Create the "posted by" text
  const postedByText = document.createElement('p');
  postedByText.textContent = `posted by ${post.author}`;

  // Append the elements to the user face container
  userFace.appendChild(likeButton);
  userFace.appendChild(likesText);
  userFace.appendChild(bookmarkButton);
  userFace.appendChild(postedByText);

  // Append the elements to the post content container
  postContent.appendChild(division1);
  postContent.appendChild(division2);

  // Append the elements to the idea container
  ideaContainer.appendChild(postContent);

  // Append the elements to the post box section
  postboxSection.appendChild(ideaContainer);

  // Append the post box section to the post box container
  postboxContainer.appendChild(postboxSection);
});

// Append the post box container to the document body
document.body.appendChild(postboxContainer);

// Function to toggle the bookmarked class
function toggleBookmark() {
  const buttonId = this.id;
  const bookmarkButton = document.getElementById(buttonId);
  bookmarkButton.classList.toggle('bookmarked');
}

// Function to toggle the liked class
function toggleLike() {
  const buttonId = this.id;
  const likeButton = document.getElementById(buttonId);
  likeButton.classList.toggle('liked');
  
}

var space = document.createElement('div');
  space.classList.add('post-space');
  postboxContainer.appendChild(space);
  
  poster.appendChild(postboxContainer);

}




function ajaxCall(url, requestMethod, inputs) {
	let promise = new Promise(function(resolve, reject) {
		var xhr = new XMLHttpRequest();

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 &&  this.status ==200) {
				resolve(xhr.responseText);
			}
		}
		xhr.open(requestMethod, url);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		inputs !== "" && xhr.send(inputs);
		inputs === "" && xhr.send();
	});
	return promise;
};

// POROFILE SCREEN OPTIONS
 function editprofile() {
        window.location.href = "profile.jsp";
    }

/* function getUserDetails(){
	ajaxCall("http://localhost:8080/tedboss/UserDetails","GET","")
	.then(function(response){
		return response;
	});
}
*/





function toggleProfileScreen() {
	var isProfileOn =false
    var profileScreen = document.getElementById("profile-screen");
    var profileDetails = document.getElementById("profile-details");
    var loginSignup = document.getElementById("login-signup");

    // Toggle the profile screen
    if (profileScreen.style.right === "-300px") {
        profileScreen.style.right = "0";
    } else {
        profileScreen.style.right = "-300px";
    }

    // Check if the user is logged in
    ajaxCall("http://localhost:8080/tedboss/UserDetails","GET","")
	.then(function(response){
	  var 	userDetails =response;
    
     // Replace with your login check logic
     

    if (userDetails != -1) {
		userDetails =JSON.parse(userDetails);
		let name = userDetails.name;
		
		let	bio =(userDetails.bio !=null)?userDetails.bio:"";
		let email = userDetails.emailid;
		let profilepic =userDetails.profilePicture;
		
        let profilepicture = document.getElementById('profile-picture');
        let profileDiv = document.getElementById('pictureDiv');
        
		document.getElementById('uname').innerText= "username "+ name;
		document.getElementById('bio').innerText = "bio "+bio;
		document.getElementById('emaill').innerText= "email "+ email;
		profilepicture.setAttribute('src',profilepic);
		profileDiv.appendChild(profilepicture);
        profileDetails.style.display = "block";
        loginSignup.style.display = "none";
    } else {
        profileDetails.style.display = "none";
        loginSignup.style.display = "block";
    }
    
    });
}




 function openPopup(Message) {
    popup.style.display = "block";
    popupcontent.innerHTML= `<h2>${Message}</h2>`;
  }
  
  function closePopup() {
   
    popup.style.display = "none";
  }
  
  