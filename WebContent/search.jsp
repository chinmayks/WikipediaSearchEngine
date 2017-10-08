<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main Page</title>
</head>

<script type="text/javascript">

function Welcome(){
	var message = "Welcome to Wikipedia Search Page";
	alert(message);
}
</script>

<body bgcolor="#ADD8E6">
<form action="search.do" name="search" method="post">
<div style="text-align:center;"><img alt="picture1" src="img/logo1.png"></div>
<table height=100% width=100%>
		<tr>
			<td>
			<div style="text-align:center;">
			<input type="text" id="searchTerm" name="searchTerm" style="width: 400px;height:25px" /><br/>
			</div>
			</td>
		</tr>
		<tr>
			<td height="10px" colspan="5" bgcolor="#ADD8E6"><div style="text-align:center;">
			<font size="80">
			<input type="submit" value="Search" id = "searchButton" style="height:50px; width:50px">
			<input type="reset" value="Reset"></font></div></td>
		</tr>
	</table>
</form>

</body>
</html>