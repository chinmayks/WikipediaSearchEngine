<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>

<body bgcolor="black"> 

<div id="moving" style="text-align:center;">
<marquee behavior="scroll" bgcolor="yellow" loop="-1" width="70%"><font size="2" face="Verdana"><b>WELCOME TO WIKIPEDIA</b></font></marquee>
</div> 
<br/>
<div align="center">
<div id="dynamic">
<object width="640" height="385">
<param name="movie" value="https://www.youtube.com/watch?v=1iebtzmkbWM&start=1"></param>
<param name="allowscriptaccess" value="always"></param>
<embed src="https://www.youtube.com/watch?v=1iebtzmkbWM&start=1" type="application/x-shockwave-flash" allowscriptaccess="always" width="640" height="385"></embed>
</object>
</div>
</div>
<br/>
<form action="connection.do" method="POST">
<div style="text-align:center;"><button type="submit" class="action"><b>Click to Enter</b></button></div>
</form>
</body>
</html>