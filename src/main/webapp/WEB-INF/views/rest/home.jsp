<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
	<head>
		<script src="/resources/jquery.js"></script>
		<script src="/resources/underscore.js"></script>
	</head>
	<body>

	</body>
	<script>
	$(function(){
		$.ajax({
			url:'/rest/list',
			type:'get',
			dataType:'json',
			success:function(data){
				_.forEach(data, function(spittle){
					console.log(spittle.message);
				});
			},
			error:function(){
				alert('System is wrong.');
			}
		});

		var spittle = new Object();
		spittle.id = 1001;
		spittle.message = "spittle1001";
		$.ajax({
			url:'/rest/save',
			type:'post',
			contentType : 'application/json',
		    data : JSON.stringify(spittle),
			success:function(data){
				console.log(data.message);
			},
			error:function(){
				alert('System is wrong.');
			}
		});

		$.ajax({
			//url:'/rest/666661',
			url:'/rest/1',
			type:'get',
			dataType:'json',
			success:function(data){
				if(data.code){
					console.log(data.message);
				}else{
					console.log(data.message);
				}
			},
			error:function(){
				alert('System is wrong.');
			}
		});
	})
	</script>
</html>
