<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Title List Page</title>

<style>
body {
	top: 200px;
	height: 80%;
	width: 90%;
	color:#282828;
	margin:auto;
}
.store{
	position:relative;
	text-align: left;
	margin:auto;
	top:10px;
	width: 70%;
}

b{
   color:gray;
   font-size:2em;
}

a:link {
    text-decoration: none;
    color: black;
}

a:visited{
     color: blue;
}


/* visited link */

a:hover{
   color: green;
   text-decoration: underline;
}
h4{
	top:40px;
	
	font-size:1em;
	color:#333333;
}
a.capitalize {
    text-transform: capitalize;
}

h3{
	font-size: 1.5em;
	color: #003333;
	font-weight: bold;
	
}



.button{
	width:80px ;
	height:80px;
}

header{
	width:100%;
	background-color:white;
}

table {
    margin: 0;
    border-collapse: separate;
    background-color:white;
}

td {
    background-clip: padding-box;
    
    color: black;
   
    margin:5px;
    background-color:#ADD8E6;
    border: 2px solid #a1a1a1;
    
}

tr:first-child td:first-child { border-top-left-radius: 20px; }
tr:first-child td:last-child { border-top-right-radius: 20px;}
tr:last-child td:first-child { border-bottom-left-radius: 20px; }
tr:last-child td:last-child { border-bottom-right-radius: 20px; }

.queryBox{
	display:block;
}
.flex-container {
    display: -webkit-flex;
    display: flex;
    width: 90%;
    height: auto;
    background-color: #ADD8E6;
    margin:auto;
}

.flex-item {
   
   
    margin: 10px;
}

form{
	background-color: #ADD8E6;
	border-radius:20px;

}
</style>

</head>

	<body >
	<header>		
		<a href="search.jsp" class="btn btn-info" role="button" id = "button"><img class ="button" alt="button" src="img/goback.png" title="Go back to search page" /></a>
		
	</header>
	
	<div class="queryBox">
		<h3>Your search query is: ${storedValues.query}</h3>
	</div>
	
	<form action="search1.do" method="POST">
	
		<div class="flex-container">
            <div class="flex-item">
				<h4 align="center" >
					Relevant Documents Returned by Latent Semantic Analysis (return related documents)
				</h4> 
				
				<hr/>
				
				<c:forEach var="post" items="${storedValues.titleLinkedList}">
				  
				   
				       <input type = "hidden" id="documentid" value = ${post.key}>
				       
				       <b>
				         <a href = "openPage.do?documentid=${post.key}" class="capitalize">${post.value}</a> 
				       </b>
				       
				       <br/>
				       <c:set var = "key" value = "${post.key}" />
				       <c:out value = "${storedValues.spoilers.get(key)}"  />
				       
				  <br/><br/><br/>
				</c:forEach>
		
		</div>
		
		<div class="flex-item">
				
			<h4 align="center" >
				Relevant Documents Returned by Vector Space Model (return exact matched documents)
			</h4> 
			
			<hr/>
			
			<c:forEach var="post" items="${storedValues.vsmTitleLinkedList}">
			  
			   
			       <input type = "hidden" id="documentid" value = ${post.key}>
			       
			       <b>
			         <a href = "openPage.do?documentid=${post.key}" class="capitalize">${post.value}</a> 
			       </b>
			       
			       <br/>
			       <c:set var = "key" value = "${post.key}" />
			       <c:out value = "${storedValues.vsmSpoilers.get(key)}"  />
			       
			  <br/><br/><br/>
			</c:forEach>
			
			
			
			</div>
	
		</div>
	</form>
	
	</body>
</html>
