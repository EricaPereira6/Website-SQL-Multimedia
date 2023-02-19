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

<body  onload="isAdmin(); recomendados();">

	<div class="w3-sidebar w3-grey w3-bar-block" style="width:20%; border-radius: 25px;">
	  <h4 class="w3-bar-item">Menu</h4>
	  <a href="#" class="w3-bar-item w3-button" onclick="meusRecursos()">Meus Recursos</a>
	  <a href="#" class="w3-bar-item w3-button" onclick="associarRecursos()">Associar Recursos</a>
	  <a href="#" class="w3-bar-item w3-button" onclick="recursosBloqueados()">Recursos Bloqueados</a>
	  <a href="#" class="w3-bar-item w3-button" onclick="utilizadores()">Utilizadores</a>
	  <a href="#" class="w3-bar-item w3-button" onclick="artistasDisponiveis()">Artistas Disponíveis</a>
	  <h4 class="w3-bar-item">Settings</h4>
	  <a href="http://localhost:8080/SBD/homePage2.jsp" class="w3-bar-item w3-button">Visualizar página do criador</a>
	  <a href="http://localhost:8080/SBD/homePage1.jsp" class="w3-bar-item w3-button">Visualizar página do convidado</a>
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
	
		function recursosBloqueados() {
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "getBlocked", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
				 	
					console.log("helooo " + result.data);
					presentResources(result);
				}
					
			 });
		}
	
	</script>
	
	<script>
	
		function utilizadores() {
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "getUsers", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
				 	
					presentUsersAdmin(result);
				}
					
			 });
		}
	
	</script>
	
	<script>
	
		function AssociativeButtons() {
			var $buttons = "<div id='associateButtons' class='row' style='justify-content: center;'>" + 
			"<button disabled onclick='associarRecursos()' id='associationButton' class='btn btn-secondary' " +
			"type='submit' href='#' style='margin:10px;'>Associar Recursos</button>" +
			"<button onclick='desassociarRecursos()' id='desassociationButton' class='btn btn-info' " +
			"type='submit' href='#' style='margin:10px;'>Desassociar Recursos</button></div>";
			$('#resourcesDiv').append($buttons);
		}
		
		function DesassociativeButtons() {
			var $buttons = "<div id='associateButtons' class='row' style='justify-content: center;'>" + 
			"<button onclick='associarRecursos()' id='associationButton' class='btn btn-info' " +
			"type='submit' href='#' style='margin:10px;'>Associar Recursos</button>" +
			"<button disabled onclick='desassociarRecursos()' id='desassociationButton' class='btn btn-secondary' " +
			"type='submit' href='#' style='margin:10px;'>Desassociar Recursos</button></div>";
			$('#resourcesDiv').append($buttons);
		}
		
	</script>
		
	<script>
		
		function desassociarRecursos() {
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "getAssociatedResources", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
					
					desassociateResources(result);
				}
					
			 });
		}
	
		function associarRecursos() {
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "getResources", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
					
					associateResources(result);
				}
					
			 });
		}

	</script>
		
	<script>
		
		function desassociateResources(result) {
			$("#resourcesDiv").empty();
			
			DesassociativeButtons();
			
			const resources = result.data.split('|');
			
			var $index = 0;
			var $id = 1;
			var $idR = 1;
			
			var $resource = "";
			var $row = "<div id='divAssociations" + $id + "' " + 
			  "style='background-color: rgba(136, 136, 136, 0.8); margin: 30px; padding: 15px; border-radius: 25px;" + 
			  " justify-content: center;'><div id='resourcesRow" + $idR + "' class='row'>";
			$("#resourcesDiv").append($row);
			
			var $idGroup = resources[0].split(', ')[0].split(" = ")[1];
			var $title = "";
			var $publisher = "";
			$.each(resources, function( key, val ) { 
				
				  const info = val.split(', ');  
				  $.each(info, function( key2, val2 ) {
					  
					  if (val2.includes("idgrupo")) {
						  if ($idGroup != val2.split(' = ')[1]){
							  
							  while ($index % 4 != 0) {
								  $resource = "<div class='col-sm'></div>";
						          $("#resourcesRow" + $idR).append($resource);
						          $index++;
							  }
							  $row = "</div><button onclick='deleteAssociation(" + $idGroup + ")' class='btn btn-danger' " +
								"type='submit' href='#' style='margin:10px;' id='buttondelete'>Eliminar Associação</button></div>";
							  $("#resourcesDiv").append($row);
							  
							  $idGroup = val2.split(' = ')[1];
							  $id++;
							  $idR++;
							  $row = "<div id='divAssociations" + $id + "' " + 
							  "style='background-color: rgba(136, 136, 136, 0.8); margin: 30px; padding: 15px; border-radius: 25px;" + 
							  " justify-content: center;'><div id='resourcesRow" + $idR + "' class='row'>";
							$("#resourcesDiv").append($row);
						  }
						  
					  } else if (val2.includes("tituloRecurso")) {
						  $title = val2.split(' = ')[1];
						  
					  } else if (val2.includes("carregadoPor")) {
						  $publisher = val2.split(' = ')[1];
						  
					  } else if (val2 == "") {
						  $title = "";
						  $publisher = "";
					  }
					  
				  });
				  
				  if ($title != "") {
					  
					  var $div = "<div id='y' class='col-sm' value='" + $title + " | " + $publisher + "' " + 
					  "style='background-color: #888888; margin: 30px; border-radius: 25px;'><h4>" + $title + 
					  "</h4><h6> Publicado por " + $publisher + "</h6></div>"; 
					  $("#resourcesRow" + $idR).append($div);
			          
			          $index++;
					  if ($index % 4 == 0) {
							$idR++;
							$row = "</div><div id='resourcesRow" + $idR + "' class='row'>";
							$("#divAssociations" + $id).append($row);
					  }
				  }  
			  });
			
			  while ($index % 4 != 0) {
				  $resource = "<div class='col-sm'></div>";
		          $("#resourcesRow" + $idR).append($resource);
		          $index++;
			  }
			  $row = "</div><button onclick='deleteAssociation(" + $idGroup + ")' class='btn btn-danger' " +
				"type='submit' href='#' style='margin:10px; id='buttondelete''>Eliminar Associação</button></div>";
			  $("#resourcesDiv").append($row);
	
		}
		
		
		function associateResources(result) {
			$("#resourcesDiv").empty();
			
			AssociativeButtons();
			
			const resources = result.data.split('|');
			
			var $index = 0;
			var $id = 1;
			
			var $row = "<div id='resourcesRow" + $id + "' class='row'>";
			document.getElementById('resourcesDiv').innerHTML += $row;
			
			var $title = "";
			var $publisher = "";
			$.each(resources, function( key, val ) { 
				
				  const info = val.split(', ');  
				  $.each(info, function( key2, val2 ) {
					  
					  if (val2.includes("Titulo")) {
						  $title = val2.split(' = ')[1];
						  
					  } else if (val2.includes("carregado")) {
						  $publisher = val2.split(' = ')[1];
						  
					  } else if (val2 == "") {
						  $title = "";
						  $publisher = "";
					  }
					  
				  });
				  
				  if ($title != "") {
					  
					  var $div = "<div id='y' class='col-sm' " + 
					  "style='background-color: #888888; margin: 30px; border-radius: 25px;'><h4>" + $title + 
					  "</h4><h6> Publicado por " + $publisher + "</h6>" +
					  " <input style='position:absolute; bottom: 10px; right: 15px;' type='checkbox' id='" +
					  $title + $publisher + "' value='" + $title + " | " + $publisher + "'></div>"; 
					  $("#resourcesRow" + $id).append($div);
			          
			          $index++;
					  if ($index % 4 == 0) {
						    $row = "</div>";
							document.getElementById('resourcesDiv').innerHTML += $row;
							$id++;
							var $row = "<div id='resourcesRow" + $id + "' class='row'>";
							document.getElementById('resourcesDiv').innerHTML += $row;
					  }
				  }
				  
			  });
			
			
			  while ($index % 4 != 0) {
				  var $resource = "<div class='col-sm'></div>";
		          document.getElementById('resourcesRow' + $id).innerHTML += $resource;
		          $index++;
			  }
			
			$row = "</div><div><button onclick='sendAssociation()' id='association' class='btn btn-outline-success my-2 my-sm-0' " +
			"type='submit' href='#'>Enviar</button></div>";
			document.getElementById('resourcesDiv').innerHTML += $row;
			
		}
		
	</script>
		
	<script>
		
		function deleteAssociation(idGroup) {
			
			document.getElementById('buttondelete').disabled = true;
			
			$.ajax({method: "POST", 
				dataType: "json", 
				data: {grupo : idGroup},
				url: "deleteAssociation", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
					
					if (result.data != "") {
					
						$.notify(result.data);
						desassociarRecursos();
					}
				}
					
			 });
		}
		
		
		
		function sendAssociation() {
			document.getElementById('association').disabled = true;
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "getResources", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
					
					if (result.data != "") {
					
						var $resAssociated = "";
						var $numRes = 0;
						
						const resources = result.data.split('|');
						
						var $title = "";
						$.each(resources, function( key, val ) { 
							if (val.includes("Titulo")) {
								  var val2 = val.split(', ')[0];
								  $title = val2.split(' = ')[1];
								  val2 = val.split(', ')[1];
								  $publisher = val2.split(' = ')[1];
								  if (document.getElementById($title + $publisher).checked) {
									  $resAssociated += document.getElementById($title + $publisher).value;
									  $resAssociated +=  ", ";
									  $numRes++;
								  }
							}
							
						});
						
						if ($numRes > 1) {
							console.log($resAssociated);
							$.ajax({method: "POST", 
								dataType: "json", 
								data: ({resources: $resAssociated}),
								url: "associateResources", 
								 error: function(xhr, status, error){
										if(xhr.status == 500){
											$.notify("O servidor está offline");
										}
										//else
						 				//	$.notify("Erro: " + xhr.responseText);	    
								  },
								success: function(result){
									
									$.notify(result.data);
									associarRecursos();
								}
									
							 });
						}
						else {
							document.getElementById('association').disabled = false;
							$.notify("selecione mais do que 1 recurso");
						}
					}
					else {
						document.getElementById('association').disabled = false;
					}
				}
					
			 });
		}
		
	
	</script>
	
	<script>
	
		function artistasDisponiveis() {
			$.ajax({method: "POST", 
				dataType: "json", 
				url: "getArtists", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
				 	
					presentArtistsAdmin(result);
				}
					
			 });
		}
	
	</script>
	
	<script>
	
		function presentUsersAdmin(result) {
			
			$("#resourcesDiv").empty();
			
			const resources = result.data.split('|');
			
			$id = 1;
			$pastArtist = "";
			
			var $row = "<div id='divUsers' " + 
				  "style='background-color: rgba(136, 136, 136, 0.8); margin: 30px; padding: 15px; border-radius: 25px;" + 
				  " justify-content: center;'>";
			document.getElementById('resourcesDiv').innerHTML += $row; 
			
			var $user = "";
			var $email = "";
			var $dataNasc = "";
			var $nacional = "";
			var $tipoUser = "";
			var $pontos = "";
			var $blocked = "";
			$.each(resources, function( key, val ) { 
				
				  const info = val.split(', ');  
				  $.each(info, function( key2, val2 ) {
					  
					  if (val2.includes("Nome Utilizador")) {
						  $user = val2.split(' = ')[1];
						  
					  } else if (val2.includes("Email")) {
						  $email = val2.split(' = ')[1];
					  
					  } else if (val2.includes("Data de Nascimento")) {
						  $dataNasc = val2.split(' = ')[1];
						  
					  } else if (val2.includes("Nacionalidade")) {
						  $nacional = val2.split(' = ')[1];
						  
					  } else if (val2.includes("Bloqueado")) {
					  	$blocked = (val2.split(' = ')[1] == "true") ? "Desbloquear" : "Bloquear"; 
					  	
					  } else if (val2.includes("Pontos de Reputação")) {
							$pontos = val2.split(' = ')[1];
						  
					  } else if (val2 == "") {
						  $user = "";
						  $email = "";
						  $dataNasc = "";
						  $nacional = "";
						  $tipoUser = "";
						  $pontos = "";
						  $blocked = "";
					  }
					  
				  	if (val2.includes("Convidado")) {
					  $tipoUser = " Convidado ";
					  
				  	} else if (val2.includes("Criador")) {
					  $tipoUser = " Criador ";
					  
				  	} else if (val2.includes("Administrador")) {
					  $tipoUser = " Administrador ";
				  	}
					  
				  });
				  
				  if ($user != "") {

					  var $div = "<div class='row' value='" + $user + "' " + 
					  "style='background-color: #AAAAAA; margin: 30px; border-radius: 25px; width: 80%; '>" + 
					  
					  "<div class='col-sm'><h4> Nome: " + $user + "</h4> " + 
					  "<h6>E-mail: " + $email + "</h6> " + 
					  "<h6>Data de Nascimento: " + $dataNasc + "</h6> " + 
					  "<h6>Nacionalidade: " + $nacional + "</h6></div> " + 
					  "<div class='col-sm'><h4>" + $tipoUser + "</h4><h6> " + $pontos + "</h6>" + 
					  "<button id='"+ $user + "' onclick='blockUtilizador(this.value);' class='btn btn-danger btn-sm' value='" + 
					  $user + "' >" + $blocked + "</button></div></div>"; 
					  
					  $("#divUsers").append($div);
					  
					  if ($blocked == "Desbloquear") {
							document.getElementById($user).className = "btn btn-primary btn-sm";
						}else {
							document.getElementById($user).className = "btn btn-danger btn-sm";
						}
					  
					  $pontos = "";
					  $blocked = "";
				  }
				  
			  });
			
			$row = "</div>";
			document.getElementById('resourcesDiv').innerHTML += $row;
			
		}
		
		
		function userClick(x) {
			console.log("userclick");
		}
		
		function blockUtilizador(x) {
			$.ajax({method: "POST", 
				dataType: "json", 
				data: {userBlock: x}, 
				url: "blockUser", 
				 error: function(xhr, status, error){
						if(xhr.status == 500){
							$.notify("O servidor está offline");
						}
						//else
		 				//	$.notify("Erro: " + xhr.responseText);	    
				  },
				success: function(result){
				 	
					if (result.update) {
						if (result.data == "Desbloquear") {
							document.getElementById(x).className = "btn btn-primary btn-sm";
						}else {
							document.getElementById(x).className = "btn btn-danger btn-sm";
						}
						document.getElementById(x).innerHTML = result.data;
					}
				}
					
			 });
		}
	
	</script>
	
	<script>
	
		function presentArtistsAdmin(result) {
			
			$("#resourcesDiv").empty();
			
			const resources = result.data.split('|');
			
			var $index = 0;
			var $id = 1;
			var $idImgA = 1;
			
			var $row = "<div id='resourcesRow" + $id + "' class='row'>";
			document.getElementById('resourcesDiv').innerHTML += $row;
			
			var $art = "";
			var $foto = "";
			var $count = "";
			var $profissoes = [];
			$.each(resources, function( key, val ) { 
				
				  const info = val.split(', ');  
				  $.each(info, function( key2, val2 ) {
					  
					  if (val2.includes("Artista")) {
						  $art = val2.split(' = ')[1];
						  
					  } else if (val2.includes("Foto")) {
						  //$foto = val2.split(' = ')[1];
						  
					  } else if (val2.includes("count")) {
						  $count = val2.split(' = ')[1];
						  
					  } else if (val2.includes("profissoes")) {
						  
						  const prof = val2.split(' = ')[1].split('+');
						  var $idp = 0;
						  $.each(prof, function( key3, val3 ) {
							  if (val3 != "") {
								  $profissoes[$idp] = val3;
								  $idp++;
							  }
						  });
						  
					  } else if (val2 == "") {
						  	$art = "";
							$foto = "";
							$count = "";
							$profissoes = [];
					  }
					  
				  });
				  
				  if ($art != "") {
					  
					  var $div = '<button onclick="artistClick(this.value);" id="y" class="col-sm" value="' + $art + '" ' + 
					  'style="background-color: #888888; margin: 30px; border-radius: 25px;"><h4>' + $art + 
					  '</h4><div id="showArtistImg' + $idImgA + '"></div><h5>';  
					  
					  for ( var i = 0; i < $profissoes.length; i++ ) {
						  if (i == $profissoes.length - 1) {
							  $div += $profissoes[i];
						  }else {
							  $div += $profissoes[i] + ", ";
						  }
						}				  					  
					  $profissoes = [];
					  $div += "</h5><h6> Associado a " + $count + " recursos</h6></button>";
					  $("#resourcesRow" + $id).append($div);
					  
					  showArtistMiniature($("#showArtistImg" + $idImgA), $art, $idImgA);
					  $idImgA++;
			          
			          $index++;
					  if ($index % 4 == 0) {
						    $row = "</div>";
							document.getElementById('resourcesDiv').innerHTML += $row;
							$id++;
							var $row = "<div id='resourcesRow" + $id + "' class='row'>";
							document.getElementById('resourcesDiv').innerHTML += $row;
					  }
				  }
				  
			  });
			
			
			  while ($index % 4 != 0) {
				  var $resource = "<div class='col-sm'></div>";
		          document.getElementById('resourcesRow' + $id).innerHTML += $resource;
		          $index++;
			  }
			
			$row = "</div>";
			document.getElementById('resourcesDiv').innerHTML += $row;
			
		}
		
		function artistClick(x) {
			//window.location.href = "http://localhost:8080/SBD/showResource.jsp";
			console.log("artistclick");
		}
		
		
	
	</script>


</body>

</html>