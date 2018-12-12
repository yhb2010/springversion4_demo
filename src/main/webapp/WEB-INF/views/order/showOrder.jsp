<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div>

	<p>
		<label for="customer">用户名：</label>
		<label>${order.customer }</label>
	</p>
	<p>
		<label for="type">类型(网站还是手机下单)：</label>
		<label>${order.type }</label>
	</p>
	<c:forEach items="${order.items }" var="item">
		<p>
			<label for="item">订单明细：</label>
			<label>${item.id }</label>
			<label>${item.product }</label>
			<label>${item.price }</label>
		</p>
	</c:forEach>

</div>
</body>
</html>