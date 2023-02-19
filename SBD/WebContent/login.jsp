 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Login</title>
<%@include file="links.html"%>

</head>

<%

//session & cookie

%>

<style>
html, body {
	height: 100%;
}
</style>

<body>



	<div class="container-fluid text-center">
	
		<br/>

		<div class="row justify-content-center">
			<img src="photos/isel.png" alt="ISEL">
		</div>

		<br/>
		<br/>

		<div class="row">

			<div class="col-sm">

			</div>

			<div class="col-sm">

				
					<input id="user" type="text" class="form-control" name="username"
						placeholder="Type your username" value="Andressa Canecas"> 
			
					<input id="pw" type="password" class="form-control" name="password"
						placeholder="Type your password" value="andy77">


				<button id="login" class="btn btn-lg btn-primary btn-block"
					type="submit" value="Send">Login</button>

		
				
				<button id="subscribe" class="btn btn-lg btn-light btn-block"
					type="button" value="Subscribe">Subscreve</button>
				

			</div>

			<div class="col-sm btn-group-vertical">
				
				
			</div>

		</div>

	</div>


	<script>
	
	$("#login").click(function(){
		
		$.ajax({method: "POST", 
			dataType: "json", 
			data: {username: $("#user").val(), password: $("#pw").val()}, 
			url: "Login", 
			 error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			success: function(result){
			 	
				if(result.success){
					window.location.href = "http://localhost:8080/SBD/homePage2.jsp";
					//jquery 
				}
				else{
					$.notify(result.error);
				}
				
		  }});
		});
	
	</script>
	
	<script>
	
	$("#subscribe").click(function() {
			window.location.href = "http://localhost:8080/SBD/subscribe.jsp";
	});
	</script>
	
</body>

</html>