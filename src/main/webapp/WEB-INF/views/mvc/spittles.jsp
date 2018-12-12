<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<c:forEach items="${spittleList }" var="spittle">
			<li id="spittle_<c:out value='${spittle.id }' />">
				<div class="spittleMessage">
					<c:out value="${spittle.message }" />
				</div>
				<div>
					<span class="spittleTime"><c:out value="${spittle.time }" /></span>
					<a href="/mvc/spittles/<c:out value='${spittle.id }' />">查看</a>
				</div>
			</li>
		</c:forEach>
	</body>
</html>
