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
			url:'/rest2/list',
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

		$.ajax({
			url:'/rest2/10',
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

		$.ajax({
			url:'/rest2/1',
			type:'delete',
			dataType:'json',
			error:function(){
				alert('System is wrong.');
			}
		});

		var spittle = new Object();
		spittle.id = 5;
		spittle.message = "spittle5555";
		$.ajax({
			url:'/rest2/5',
			type:'put',
			contentType : 'application/json',
		    data : JSON.stringify(spittle),
			error:function(){
				alert('System is wrong.');
			}
		});

		var spittle = new Object();
		spittle.id = 1011;
		spittle.message = "spittle1011";
		$.ajax({
			url:'/rest2/save',
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

		var spittle2 = new Object();
		spittle2.id = 1012;
		spittle2.message = "spittle1012";
		$.ajax({
			url:'/rest2/save2',
			type:'post',
			contentType : 'application/json',
		    data : JSON.stringify(spittle2),
			success:function(data){
				console.log(data.message);
			},
			error:function(){
				alert('System is wrong.');
			}
		});

	})
	</script>
</html>
