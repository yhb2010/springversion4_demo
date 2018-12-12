<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<div>
			<div class="spittleMessage">
				<c:out value="${spittle.message }" />
			</div>
			<div>
				<span class="spittleTime"><c:out value="${spittle.time }" /></span>
			</div>
		</div>
	</body>
</html>
