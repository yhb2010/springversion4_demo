<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<div>
			<c:out value="${spitter.userName }" /><br/>
			<c:out value="${spitter.firstName }" /><br/>
			<c:out value="${spitter.lastName }" /><br/>
		</div>
	</body>
</html>
