<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<h1><s:message code="hello" /></h1>
		<a href="<c:url value='/mvc/spittles' />">Spittles</a>
		<a href="<c:url value='/mvc/spitter/register' />">Register</a><br/><br/>

		<form action="/redis/save" method="post">
			key：<input type="text" name="key" />
			value：<input type="text" name="value" />
			<input type="submit" value="提交" />
		</form>

		====================================================<br/>
		<a href="/jms/user?name=zhang">jms demo</a><br/>
		<a href="/jms/clientSayJms?name=chen">jms RPC demo</a><br/>
		<a href="/jms/pubsub?name=chen">jms pubsub demo</a><br/>
	</body>
</html>
