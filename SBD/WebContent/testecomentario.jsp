<%@ page language="java" contentType="text/html; charset=iso-8859-1"
	pageEncoding="iso-8859-1"%>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Language" content="pt">
<meta charset="iso-8859-1">
<title>Mostrar Recurso</title>
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

		<br />

		<div class="row justify-content-center">
			<img src="photos/isel.png" alt="ISEL">
		</div>

		<br /> <br />

		<div class="row">

			<div class="col-sm"></div>

			<div class="col-sm">


				<input id="utilizadorComentario" type="text" class="form-control" value="Ana luis erica">

				<input id="tituloRecurso" type="text" class="form-control" value="Autopsiografia">

				<input id="utilizadorRecurso" type="text" class="form-control" value="Miguel Lança">
				
				
				<button id="Comentario" class="btn btn-lg btn-light btn-block"
					type="button" value="Subscribe">Comentario</button>

				<div id="content"></div>

			</div>

			<div class="col-sm btn-group-vertical"></div>

		</div>

	</div>


	<script>
	

	$("#Comentario").click(function(){
		
		$x=$("#content");
		
		
		x = "Ana luis erica";
		y= "Autopsiografia";
		z = "Miguel Lança";
		
		$.ajax({method: "POST", 
			dataType: "JSON",
			data: {utilizadorComentario:x , tituloRecurso: y, utilizadorRecurso: z }, 
			url: "getComment", 
		 
			error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },

			  success: function(result){
				 	
					if(result.success){
						$.notify(result.classificacao);

					}
					else{
						$.notify(result.error);
					}
					
			  }});
			});

	

	

	</script>
	
</body>

</html>