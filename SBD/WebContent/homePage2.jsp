<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Página Principal</title>
<%@include file="links.html"%>

</head>

<%@include file="navbar.html"%>

<%

//session & cookie

%>

<style>
html, body {
	height: 100%;
}
</style>

<body onload="isCriador(); recomendados();">

	<div class="w3-sidebar w3-grey w3-bar-block" style="width:20%; border-radius: 25px;">
	  <h3 class="w3-bar-item">Menu</h3>
	  <a href="#" class="w3-bar-item w3-button" onclick="meusRecursos()">Meus Recursos</a>
	  <a href="#" class="w3-bar-item w3-button" id="logout">Logout</a>
	</div>
	
	<!-- Page Content -->
	<div style="margin-left:20%">
	
		<!--
		<div>
		<button id="logout" type="button" class="btn btn-danger btn-sm" style="position: absolute; right: 5px;">
			log out
		</button>
		</div>
		<br>
		-->
		
		<div id="resourcesDiv" class="container-fluid text-center">
	
		</div>
		
		<div id="adminReturn">
		
		</div>
	
	</div>
	
	
	<script>
	
		$("#logout").click(function(){
		
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "Logout", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
				 	
					window.location.href = "http://localhost:8080/SBD/login.jsp";
					
					
			  }});
		
		});
	
	</script>


	<script>
	
	function isCriador() {
		$.ajax({dataType: "json", 
			  url: "verifyUser", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
				  
				  if (result.errorType == null) {
				  	console.log("null");
				  }
				  if (result.errorType == "login") {
			  		window.location.href = "http://localhost:8080/SBD/login.jsp";
			  	  } else if (result.type == "convidado") {
			  		window.location.href = "http://localhost:8080/SBD/homePage1.jsp";
			  	  } else if (result.type == "admin") {
			  		window.location.href = "http://localhost:8080/SBD/homePage3.jsp";
			  	  } 
		  }});
		});
	
	</script>


</body>

</html>