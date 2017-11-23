<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
		<title>Welcome</title>
	</head>
	<body>
	
		<h1>Please choose a JSON file</h1><br />
		<form name="myForm" method="POST" action="${pageContext.request.contextPath}/webServlet" enctype="multipart/form-data">
			<input name = "myFile" type="file" accept=".json" > <br />
			<input type="submit" name="submit" value="Submit"> <br />
			<input type="radio" name="style" value="style1.css" checked>Design 1<br />
			<input type="radio" name="style" value="style2.css">Design 2
		</form>
		
	</body>
</html>