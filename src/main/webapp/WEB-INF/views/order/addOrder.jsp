<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<div>

	<sf:form commandName="order" action="order/save" method="post" >
		<fieldset>
			<legend>添加订单</legend>
			<p>
				<label for="customer">用户名：</label>
				<sf:input id="customer" path="customer"/>
			</p>
			<p>
				<label for="type">类型(网站还是手机下单)：</label>
				<sf:input id="type" path="type"/>
			</p>
			<p>
				<label for="item0">订单明细0：</label>
				<sf:input id="item0" path="items[0].id"/>
				<sf:input id="item0" path="items[0].product"/>
				<sf:input id="item0" path="items[0].price"/>
			</p>
			<p>
				<label for="item1">订单明细1：</label>
				<sf:input id="item1" path="items[1].id"/>
				<sf:input id="item1" path="items[1].product"/>
				<sf:input id="item1" path="items[1].price"/>
			</p>
			<p>
				<input type="submit" id="submit" value="提交" />
			</p>
		</fieldset>
	</sf:form>

	<a href="/order/listOrder">查看列表</a>

</div>
</body>
</html>