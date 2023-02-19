 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Subscreve</title>
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
						placeholder="Type your username" value="Ana luis erica"> 


				<button id="subscribe" class="btn btn-lg btn-primary btn-block"
					type="submit" value="Send">Subscreve</button>

		
				
				<button id="login" class="btn btn-lg btn-light btn-block"
					type="button" value="Login">Login</button>
				

			</div>

			<div class="col-sm btn-group-vertical">
				
				
			</div>

		</div>

	</div>


	<script>
	
	$("#subscribe").click(function(){
		
		$.ajax({method: "POST", 
			dataType: "json", 
			data: {username: $("#user").val(), password: $("#pw").val()}, 
			url: "Subscribe", 
			 error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			success: function(result){
			 	
				if(result.success){

					if (result.admin){
						window.location.href = "http://localhost:8080/SBD/homePage3.jsp";
					}else {
						window.location.href = "http://localhost:8080/SBD/homePage1.jsp";
					}
					
					//jquery 
				}
				else{
					$.notify(result.error);
				}
				
		  }});
		});
	
	</script>
	
	<script>
	
	$("#login").click(function() {
			window.location.href = "http://localhost:8080/SBD/login.jsp";
	});
	</script>
	
</body>

</html>