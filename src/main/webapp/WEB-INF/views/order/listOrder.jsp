<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div>

	<sf:form action="/order/searchOrder" commandName="order" >
		<sf:input path="customer" /><input type="submit" id="submit" value="查询" />
	</sf:form>
	<c:forEach items="${orderList }" var="order">
		<li id="order_<c:out value='${order.id }' />">
			<div class="">
				<c:out value="${order.customer }" />
			</div>
			<div>
				<a href="/order/detail/<c:out value='${order.id }' />">查看</a>
			</div>
		</li>
	</c:forEach>

</div>
</body>
</html>