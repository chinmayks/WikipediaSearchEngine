<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Title List Page</title>
<script>
function goBack() {
    window.history.back();
}
</script>
</head>
<body bgcolor="#33FF93">
<form action="search.do" method="POST">
<div id="stored" style="MARGIN-LEFT: 98px;MARGIN-RIGHT: 139px;">
<!-- <h2>${storedValues.titleLinkedList}</h2> -->
<div id="moving" style="text-align:center;">
<marquee behavior="scroll" bgcolor="yellow" loop="-1" width="70%"><font size="2" face="Verdana"><b>Data Fetched from LSI Algorithm Search</b></font></marquee>
</div>
<c:forEach var="post" items="${storedValues.titleLinkedList}">
   <!--  <h3 align="center">${post.value}</h3><br/> -->
       <input type = "hidden" id="documentid" value = ${post.key}>
       <b>
         <a href = "openPage.do?documentid=${post.key}">${post.value}</a> 
       </b><br/>
       <c:set var = "key" value = "${post.key}" />
       <p><c:out value = "${storedValues.spoilers.get(key)}"  /></p>
       
  <br/><br/><br/>
</c:forEach>
</div>
<div style="text-align:center;"><button type="button" 
        onclick="goBack()"><b>Go Back</b></button></div>
</form>
</body>
</html>
