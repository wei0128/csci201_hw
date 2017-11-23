<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<form action="${pageContext.request.contextPath}/ChooseFile"
		method="post" enctype="multipart/form-data">
		<div>
			<label>Please choose a JSON file</label> <br /> <input accept=".json"
				type="file" id="file" name="file">
		</div>
		<div>
			<input type="submit" value="Upload File" />
		</div>
	</form>
</body>
</html>