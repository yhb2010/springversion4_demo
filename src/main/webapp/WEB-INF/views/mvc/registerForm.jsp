<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<title></title>
		<style>
		span.error label.error {
			color : red;
		}
		div.error {
			background-color : #ffcccc;
			border : 2px solid red;
		}
		input.error {
			background-color : #ffcccc;
		}
		</style>
	</head>
	<body>
		<sf:form action="/mvc/spitter/register" method="post" commandName="spitter" enctype="multipart/form-data">
			<sf:label path="firstName" cssErrorClass="error" >first name:</sf:label><sf:input path="firstName" cssErrorClass="error" /><sf:errors path="firstName" cssClass="error" /><br/>
			<sf:label path="lastName" cssErrorClass="error" >last name:</sf:label><sf:input path="lastName" cssErrorClass="error" /><sf:errors path="lastName" cssClass="error" /><br/>
			<sf:label path="userName" cssErrorClass="error" >username:</sf:label><sf:input path="userName" cssErrorClass="error" /><sf:errors path="userName" cssClass="error" /><br/>
			<sf:label path="password" cssErrorClass="error" >password:</sf:label><sf:password path="password" cssErrorClass="error" /><sf:errors path="password" cssClass="error" /><br/>
			email:<sf:input path="email" type="email" /><br/>
			<!-- 新增加了type属性，用于支持html5
			birthday:<sf:input path="birthday" type="date" /><br/> -->
			Profile picture：<input name="profilePicture" type="file" accept="image/jpeg, image/png, image/gif"/><br/>
			<input type="submit" value="submit" />
			<!-- *：匹配所有错误 -->
			<sf:errors path="*" cssClass="errors" element="div" />
		</sf:form>
	</body>
</html>
