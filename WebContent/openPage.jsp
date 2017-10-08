<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Full description</title>
<style>
body {
	position:relative;
	top:20px;
	height:80&;
	width: 70%;
	text-align: left;
	margin:auto;
	background-color:white;

}
p{
	line-height:130%;
	font-size:1.2em;
	text-indent: 70px;
}
h1.capitalize{
	text-transform: capitalize;
	text-align: center;
}
.button{
	width:80px ;
	height:80px;
}
header{
	width:100%;
	background-color:white;
}
article{
	
	background-color: #ADD8E6;
	border-radius:20px;
}
.background-wrap {
				position: fixed;
				z-index: -1000;
				width: 100%;
				height: 100%;
				overflow: hidden;
				top: 0;
				left: 0;
			}
			
			#video-bg-elem {
				position: absolute;
				top: 0;
				left: 0;
				min-height: 100%;
				min-width: 100%;
			}

.button {
    background-color: #4CAF50;
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 4px 2px;
    cursor: pointer;
     height:40px;
     width:200px;
}

</style>

	</head>

	<body >
		
		
		<button class="button" onclick="goBack()">Click to go Back</button>
	
		<article>
		<h1 class="capitalize">
			${full.title}
		</h1>
		<c:forEach items="${full.setAnswerBody}" var="item">
         <p><i>${item}</i></p><br>
       </c:forEach>
		</article>
		<script>
function goBack() {
    window.history.back();
}
</script>
	</body>
	
</html>