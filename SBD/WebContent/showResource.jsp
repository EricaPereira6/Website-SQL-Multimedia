<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Language" content="pt">
<meta charset="UTF-8">
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

<body onload="getUrlVars()">



	<div class="container-fluid text-center">

		<div class="row justify-content-left">
			<button class="col col-lg-2" onclick="returnHomePage()" 
			style="padding:20px; margin:20px; background-color: transparent; border: 0; bottom:100px;'">
				<img style="width:200px; height:auto" src="photos/isel.png" alt="ISEL">
			</button>
			<div class="col" id="showMiniatureHere" style="margin:20px;">
			</div>
			<div id="relacionados" class="col col-lg-2"></div>
		</div>
		
		<div style="margin:0 0 0 20px;" id="showContentHere"></div>
		<br>
		<div style="margin:0 0 0 20px;" id="showContentInfoHere"></div>
		</br>
		<div style="margin:0 0 0 20px;" id="putButtonsHere"></div>
		</br>
		<div id="makeCommentHere">
			<div class="row">
				<div class ="col"></div>
				<div class ="col-7" style="margin:0 0 0 20px; padding: 20px; background-color: rgba(0,0,0,0.7); 
				border-radius: 10px;" id="addCommentHere">
					<label for="stars" style="color: #AAAAAA;">Classificação:</label>
					<select name="stars" id="starsRank">
					    <option value="1">1</option>
					    <option value="2">2</option>
					    <option value="3">3</option>
					    <option value="4">4</option>
					    <option value="5">5</option>
					  </select>
					  <input id="comentarioEscrito" type="text" class="form-control" name="Comentario"
							placeholder="Deixa um comentário">
					  <br>
					  <button onclick='makeComment()' id='makeClassification' class='btn btn-outline-success my-2 my-sm-0'
					type='submit'>Classificar</button>
				</div>
				<div class ="col"></div>
			</div>
		</div>
		</br>
		<div style="margin:0 0 0 40px;" id="putCommentsHere"></div>
		</br>

	</div>


	<script>	
	
	function getUrlVars() {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		
	    const tituloRecurso = urlParams.get('title');
	    const criadorRecurso = urlParams.get('publisher');
	    
	    showResource(tituloRecurso, criadorRecurso);
	    showResourceInfo(tituloRecurso, criadorRecurso);
	    showAssociated(tituloRecurso, criadorRecurso);
	    showComments(tituloRecurso, criadorRecurso);
	}
	
	function showResource(tituloRecurso, criadorRecurso) {
		
		$.ajax({method: "POST", 
			  dataType: "json",
			  data: {titulo: tituloRecurso, carregadoPor: criadorRecurso},  
			  url: "getResourceType", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
						window.location.href = "http://localhost:8080/SBD/login.jsp";
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
				  if (!result.success) {
					  $.notify("error: ", result.errorType);
					  
				  } else if (result.data != "") {
					  $tipoRecurso = result.data;
					  
					  if ($tipoRecurso != "" && $tipoRecurso != "Fotografia") {
						    showMiniature($("#showMiniatureHere"), tituloRecurso, criadorRecurso, "Ilustracao");
						    
						    var $button = "<button id='mostrarRecurso' class='btn btn-info' " +
							"type='submit' href='#' value='" + tituloRecurso + " | " + criadorRecurso + 
							" | " + $tipoRecurso + "'>Mostrar Recurso</button><br/><br/><div id='content'></div>";
							$("#showContentHere").append($button);
							
							$("#mostrarRecurso").click(function(){	
								$("#mostrarRecurso").attr("disabled", true);
								
								$x = $("#content");
								
								$buttonValue = $("#mostrarRecurso").val();
								$tituloRecurso = $buttonValue.split(" | ")[0];
								$criadorRecurso = $buttonValue.split(" | ")[1];
								$tipoRecurso = $buttonValue.split(" | ")[2];
								
								if ($tipoRecurso == "Filme") {
									showFilm($x, $tituloRecurso, $criadorRecurso, $tipoRecurso);
									
								} else if ($tipoRecurso == "Música") {
									showMusic($x, $tituloRecurso, $criadorRecurso, $tipoRecurso);
									
								} else if ($tipoRecurso == "Poema") {
									showText($x, $tituloRecurso, $criadorRecurso, $tipoRecurso);
									
								}
								
							});
							
					    } else if ($tipoRecurso != "" && $tipoRecurso == "Fotografia") {
					    	showMiniature($("#showMiniatureHere"), tituloRecurso, criadorRecurso, $tipoRecurso);
					    }
				  }
			  }
	    });
	}
	
	function showResourceInfo(tituloRecurso, criadorRecurso) {
		
		$.ajax({method: "POST", 
			  dataType: "json",
			  data: {titulo: tituloRecurso, carregadoPor: criadorRecurso},  
			  url: "getResource", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
				  if (!result.success) {
					  $.notify("error: ", result.errorType);
					  
				  } else if (result.data != "") {
					  
					    const info = result.data.split(', ');
						
						var $title = "";
						var $publisher = "";
						var $stars = "";
						var $resumo = ""; 
						var $faixaEtaria = "";
						var $bloqueado = false;
						var $data = "";
						$.each(info, function( key, val ) { 
							if (val.includes(" = ")) {
							
								if (val.includes("Titulo")) {
									$title = val.split(' = ')[1];
										  
								} else if (val.includes("carregado")) {
									$publisher = val.split(' = ')[1];
									
								} else if (val.includes("Resumo")) {
									$resumo = val.split(' = ')[1];
									
								} else if (val.includes("Faixa Etaria")) {
									$faixaEtaria = val.split(' = ')[1];
									
								} else if (val.includes("bloqueado")) {
									$bloqueado = (val.split(' = ')[1] == "true") ? true: false;
									
								} else if (val.includes("Data da Publicacao")) {
									$data = val.split(' = ')[1];
										
								} else if (val.includes("classificacaoMedia")) {
								    $stars = val.split(' = ')[1].substring(0, val.split(' = ')[1].length - 1);
										  
								}
								
							} else if (val != ""){
								$resumo += ", " + val;
							}
								  
						});
							  
						if ($title != "") {
								  
							var $div = '<div class="row"><div class="col"></div>' +
							'<div class="col-5" style="padding:10px 20px 20px 20px; text-align: left; color: #AAAAAA;' +
							'background-color: rgba(0,0,0,0.7); border-radius: 10px;" id="resourcesInfoRow">' + 
							'<div class="row"><div class="col"><h1 style="text-indent: 1em;">' + $title + '</h1></div>' + 
							'<div id="putStarHere" class="col col-lg-2"></div></div>' +
							'<h5 style="text-indent: 0px;">Carregado por ' + $publisher + '</h5>' + 
							'<p style="text-indent: 0em;">Data de publicação: ' + $data + '</p>' + 
							'<h5 style="text-indent: 0px;"><strong>Descrição</strong></h5>' +
							'<p style="font-size: 14px; text-indent: 1em;">' + $resumo + '</br>M/' + $faixaEtaria + '</p>' + 
							'</div><div class="col"></div></div>';
							$('#showContentInfoHere').append($div);
							
							if ($stars != "" && $stars != "0.0") {
								$div = '<div><img style="float:right; display:block; width:auto; height:17px;" ' +
								'src="photos/star.png" alt="STAR"/>' + 
								'<h6 style="float:right; display:block; position: relative; bottom: 10px; right: 5px;"> ' + $stars +
								'</h6></div>';
							    $('#putStarHere').append($div);
							}
							
							if($bloqueado) {
								addButtons(tituloRecurso, criadorRecurso, "Desbloquear");
							} else {
								addButtons(tituloRecurso, criadorRecurso, "Bloquear");
							}
						}
				  }
			  }
	    });
	}
	
	function showAssociated(tituloRecurso, criadorRecurso) {
		$.ajax({method: "POST", 
			  dataType: "json",
			  data: {titulo: tituloRecurso, carregadoPor: criadorRecurso},  
			  url: "getAssociated", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
				  if (!result.success) {
					  $.notify("error: ", result.errorType);
					  
				  } else if (result.data != "") {
					  
					  	var $div = '<div id="associadosDiv" style="margin: 20px 0 0 0; padding:10px 20px 20px 20px;' +
						'text-align: left; color: #AAAAAA; background-color: rgba(0,0,0,0.7); border-radius: 10px;">' + 
						'<h4>Relacionados</h4></br>';
					  	$('#relacionados').append($div);
					  	
					  
					    const resources = result.data.split('|');
						
						var $title = "";
						var $publisher = "";
						$.each(resources, function( key, val ) { 
							
							const info = val.split(', ');
							$.each(info, function( key2, val2 ) { 
								
								if (val2.includes("tituloRecurso")) {
									$title = val2.split(' = ')[1];
										  
								} else if (val2.includes("carregadoPor")) {
									$publisher = val2.split(' = ')[1];
										
								} else if (val2 == "") {
									$title = "";
									$publisher = "";  
								} 
							});
								
							if ($title != "") {	
								const titleU = $title;
								const publisherU = $publisher;
								const uri = "http://localhost:8080/SBD/showResource.jsp?title="+titleU+"&publisher="+publisherU;
								$div = "<a href='" + uri + "'>" + $title + " por " + $publisher + "</a></br></br>";
								$('#associadosDiv').append($div);
							}
							
						});
							  
						var $div = '</div>';
					  	$('#relacionados').append($div);

				  }
			  }
	    });
	}
	
	function addButtons(tituloRecurso, criadorRecurso, nomeBotao){
		
		$.ajax({method: "POST", 
			  dataType: "json",
			  url: "verifyUser", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					else
	 					$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
			  
				  if (result.errorType == "login") {
			  		window.location.href = "http://localhost:8080/SBD/login.jsp";
			  		
			  	  } else if (result.type == "criador") {
				  		if (criadorRecurso == result.user) {
				  			var $button = "<button id='" + tituloRecurso + " | " + criadorRecurso + 
				  			"' onclick='eliminarRecurso(this.value)' class='btn btn-danger btn-sm' " +
				  			"type='button' value='" + tituloRecurso + " | " + criadorRecurso + "' >Eliminar Recurso</button>";
				  			$("#putButtonsHere").append($button);
				  		}
				  		
			  	  } else if (result.type == "admin") {
			  		  
			  		  $("#makeCommentHere").empty();
			  		  
			  		  if (nomeBotao == "Desbloquear") {
			  			
			  			var $cm = '<div class="row"><div class ="col"></div> <div class ="col-7" style="margin:0 0 0 20px;' +
			  			'padding: 20px; background-color: rgba(0,0,0,0.7); border-radius: 10px;" id="addCommentHere">' +
						'<label for="stars" style="color: #AAAAAA;">Classificação:</label>' +
						'<select name="stars" id="starsRank"><option value="1">1</option><option value="2">2</option>' +
						'<option value="3">3</option><option value="4">4</option><option value="5">5</option></select>' +
						'<input id="comentarioEscrito" type="text" class="form-control" name="Comentario"' +
						'placeholder="Deixa um comentário"><br>' +
						
						'<button id="' + tituloRecurso + ' | ' + criadorRecurso + 
				  		'" onclick="bloquearRecurso(this.value)" class="btn btn-primary btn-sm" ' +
			  			'type="button" value="' + tituloRecurso + ' | ' + criadorRecurso + '">Desbloquear Recurso</button>' +
			  			'</div><div class ="col"></div></div>';
						$("#makeCommentHere").append($cm);
			  			  
			  		  } else if (nomeBotao == "Bloquear") {
			  			var $cm = '<div class="row"><div class ="col"></div> <div class ="col-7" style="margin:0 0 0 20px;' +
			  			'padding: 20px; background-color: rgba(0,0,0,0.7); border-radius: 10px;" id="addCommentHere">' +
						'<label for="stars" style="color: #AAAAAA;">Classificação:</label>' +
						'<select name="stars" id="starsRank"><option value="1">1</option><option value="2">2</option>' +
						'<option value="3">3</option><option value="4">4</option><option value="5">5</option></select>' +
						'<input id="comentarioEscrito" type="text" class="form-control" name="Comentario"' +
						'placeholder="Deixa um comentário"><br>' +
						'<button onclick="makeComment()" id="makeClassification" class="btn btn-outline-success my-2 my-sm-0"' +
						'type="submit">Classificar</button></div><div class ="col"></div></div>';
						$("#makeCommentHere").append($cm);
						
			  			var $button = "<button id='" + tituloRecurso + " | " + criadorRecurso + 
				  		"' onclick='bloquearRecurso(this.value)' class='btn btn-danger btn-sm' " +
			  			"type='button' value='" + tituloRecurso + " | " + criadorRecurso + "'>Bloquear Recurso</button>";
			  			$("#putButtonsHere").append($button);
			  		  }
			  			
			  	  } 
			  }
			});
	}
	
	function eliminarRecurso(x) {
		document.getElementById(x).disabled = true;
		
		var title = x.split(" | ")[0];
		var publisher = x.split(" | ")[1];
		
		$.ajax({method: "POST", 
			  dataType: "json",
			  data: {titulo: title, carregadoPor: publisher},
			  url: "removeResource", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					else
	 					$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
			  
				  if (result.errorType == "login") {
			  		window.location.href = "http://localhost:8080/SBD/login.jsp";
			  		
			  	  } else if (result.success) {
			  		$.notify("Eliminado com sucesso");
			  		window.location.href = "http://localhost:8080/SBD/homePage2.jsp";	
			  	  } else if (!result.success) {
				  	$.notify("Ocurreu um erro. Tente mais tarde");
				  	window.location.href = "http://localhost:8080/SBD/homePage2.jsp";	
				  } 
			  }
			});
		
	}
	
	function bloquearRecurso(x) {
		
		document.getElementById(x).disabled = true;
		
		var title = x.split(" | ")[0];
		var publisher = x.split(" | ")[1];
		
		$.ajax({method: "POST", 
			  dataType: "json",
			  data: {titulo: title, carregadoPor: publisher},
			  url: "blockResource", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					else
	 					$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
			  
				  if (result.errorType == "login") {
			  		window.location.href = "http://localhost:8080/SBD/login.jsp";
			  		
			  	  } else if (result.success) {
			  		if (result.data == "Desbloquear") {
						document.getElementById(x).className = "btn btn-primary btn-sm";
						location.reload();
					}else {
			  			makeComment();
						document.getElementById(x).className = "btn btn-danger btn-sm";
					}
					document.getElementById(x).innerHTML = result.data + " Recurso";
					document.getElementById(x).disabled = false;
			  	  } 
			  }
			});
		
	}
	
	function showComments(tituloRecurso, criadorRecurso) {
		
		$.ajax({method: "POST", 
			  dataType: "json",
			  data: {titulo: tituloRecurso, carregadoPor: criadorRecurso},  
			  url: "getResourceComments", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
				  if (!result.success) {
					  $.notify("error: ", result.errorType);
					  
				  } else if (result.data != "") {					  	
					  
					    const resources = result.data.split('|');
						
						var $publisher = "";
						var $classificacao = "";
						var $conteudo = "";
						var $data = "";
						$.each(resources, function( key, val ) { 
							
							const info = val.split(', ');
							$.each(info, function( key2, val2 ) { 
								
								if (val2.includes(" = ")) {
									if (val2.includes("Utilizador do Comentario")) {
										$publisher = val2.split(' = ')[1];
											  
									} else if (val2.includes("Classificacao")) {
										$classificacao = val2.split(' = ')[1];
										
									} else if (val2.includes("Conteudo")) {
										$conteudo = val2.split(' = ')[1];
										if ($conteudo == "null") {
											$conteudo = "Sem Comentário";
										}
										
									} else if (val2.includes("Data do Comentario")) {
										$data = val2.split(' = ')[1];
											
									} 
									
								} else if (val2 != "") {
									$conteudo += ", " + val2;
									
								} else {
									$publisher = "";
									$classificacao = "";
									$conteudo = "";
									$data = "";
								}

								
							});
								
							if ($publisher != "") {	
								
								$div = '<div class="row"><div class ="col"></div>' +
								'<div class ="col-7" style="padding:10px 20px 20px 20px; text-align: left; color: #AAAAAA;' +
								'background-color: rgba(0,0,0,0.7); border-radius: 10px;"' +
								'id="getCommentHere">' +
								'<div class="row"><div class="col"><h5 style="text-indent: 1em;">' + $publisher + 
								' - ' + $data + '</h5></div>' + 
								'<div id="putStarHere" class="col col-lg-2"><div><img style="float:right; display:block;  ' +
								'width:auto; height:17px;" src="photos/star.png" alt="STAR"/>' + 
								'<h6 style="float:right; display:block; position: relative; bottom: 10px; right: 5px;"> ' + 
								$classificacao + '</h6></div></div></div>' +
								'<p>' + $conteudo + '</p>' +
								'</div><div class ="col"></div></div></br>';
								$("#putCommentsHere").append($div);
							}
							
						});
							  
						var $div = '</div>';
					  	$('#relacionados').append($div);

				  }
			  }
	    });
	}
	
	function makeComment() {
		
		var classificacao = $("#starsRank").val();	
		var comentario = $("#comentarioEscrito").val();
      	if(comentario == "") {
      		comentario = "Sem comentário";
        }
      	const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
		
	    const tituloRecurso = urlParams.get('title');
	    const criadorRecurso = urlParams.get('publisher');
	    
	    $.ajax({method: "POST", 
			  dataType: "json",
			  url: "verifyUser", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					else
	 					$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
				  
				  if (result.errorType == "login") {
				  		window.location.href = "http://localhost:8080/SBD/login.jsp";
				  		
				  } else {
					  var comentador = result.user;
				  	  var comentadorType = result.type;
			
					    $.ajax({method: "POST", 
							  dataType: "json",
							  data: {titulo: tituloRecurso, carregadoPor: criadorRecurso, comentadoPor: comentador,
								  classificacao: classificacao, conteudo: comentario},
							  url: "makeComment", 
							  error: function(xhr, status, error){
									if(xhr.status == 500){
										$.notify("O servidor está offline");
										window.location.href = "http://localhost:8080/SBD/login.jsp";
									}
									else
					 					$.notify("Erro: " + xhr.responseText);	    
							  },
							  success: function(result){
							  
								  if (result.errorType == "login") {
							  		window.location.href = "http://localhost:8080/SBD/login.jsp";
							  		
							  	  } else if (result.success) {
								  		location.reload();
								  		
							  	  } else {
							  		if (comentadorType == "admin") {
							  			bloquearRecurso(tituloRecurso + " | " + criadorRecurso);
							  		}
							  		  $.notify("Ocurreu um erro. Tente mais a tarde");
							  	  } 
							  }
						});
				  }
			  }
		});
	}
	
	
	function returnHomePage() {
		$.ajax({method: "POST", 
			  dataType: "json",
			  url: "verifyUser", 
			  error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
						window.location.href = "http://localhost:8080/SBD/login.jsp";
					}
					else
	 					$.notify("Erro: " + xhr.responseText);	    
			  },
			  success: function(result){
			  
				  if (result.errorType == "login") {
			  		window.location.href = "http://localhost:8080/SBD/login.jsp";
			  		
			  	  } else if (result.type == "criador") {
				  		window.location.href = "http://localhost:8080/SBD/homePage2.jsp";
				  		
			  	  } else if (result.type == "admin") {
			  			window.location.href = "http://localhost:8080/SBD/homePage3.jsp";
			  			
			  	  } else if (result.type == "convidado") {
			  			window.location.href = "http://localhost:8080/SBD/homePage1.jsp";
			  	  } 
			  }
			});
	}
	
	function showMiniature($localParaMostrar, tituloRecurso, criadorRecurso, tipoRecurso){
		
		var titulo = encodeURIComponent(tituloRecurso);
		var carregadoPor = encodeURIComponent(criadorRecurso);
		var tipoRecurso = encodeURIComponent(tipoRecurso);
		
		$miniature = "<img id='miniature' style='box-shadow: 0 4px 15px 0 rgba(0, 0, 0, 0.5), 0 12px 40px 0 rgba(0, 0, 0, 0.49);'"
		+ "src='showResource?titulo="+titulo+"&carregadoPor="+carregadoPor+"&tipoRecurso="+tipoRecurso+"'>";
		
		$localParaMostrar.append($miniature);
		
		$("#miniature").css('height','350');
      	$("#miniature").css('width','auto');
	}
	
	function showFoto($localParaMostrar, tituloRecurso, criadorRecurso, tipoRecurso){
		
		var titulo = encodeURIComponent(tituloRecurso);
		var carregadoPor = encodeURIComponent(criadorRecurso);
		var tipoRecurso = encodeURIComponent(tipoRecurso);
		
		$foto = "<img id='foto' style='box-shadow: 0 4px 15px 0 rgba(0, 0, 0, 0.5), 0 12px 40px 0 rgba(0, 0, 0, 0.49);'"
	    	+ "src='showResource?titulo="+titulo+"&carregadoPor="+carregadoPor+"&tipoRecurso="+tipoRecurso+"'>";
		
		$localParaMostrar.append($foto);
		
		$("#foto").css('height','450');
      	$("#foto").css('width','auto');
	}
	
	function showMusic($localParaMostrar, tituloRecurso, criadorRecurso, tipoRecurso){
		
		var titulo = encodeURIComponent(tituloRecurso);
		var carregadoPor = encodeURIComponent(criadorRecurso);
		var tipoRecurso = encodeURIComponent(tipoRecurso);
		
		$player ="<audio controls>"
					+"<source type='audio/mpeg' src='showResource?titulo="+titulo+"&carregadoPor="+carregadoPor+"&tipoRecurso="+tipoRecurso+"'>"
				+"</audio>";

		$localParaMostrar.append($player);
		
	}
	
	function showFilm($localParaMostrar, tituloRecurso, criadorRecurso, tipoRecurso){
		
		var titulo = encodeURIComponent(tituloRecurso);
		var carregadoPor = encodeURIComponent(criadorRecurso);
		var tipoRecurso = encodeURIComponent(tipoRecurso);

		 $player="<video controls preload='auto'>"
			  		+"<source src='showResource?titulo="+titulo+"&carregadoPor="+carregadoPor+"&tipoRecurso="+tipoRecurso+"'>"
				+'</video>';
		
		$localParaMostrar.append($player);

	}
	
	function showText($localParaMostrar, tituloRecurso, criadorRecurso, tipoRecurso){
		
		$txt = '<div id="txt">';
		$localParaMostrar.append($txt);
		
		$.ajax({method: "POST", 
			dataType: "text/html",
			data: {titulo:tituloRecurso, carregadoPor: criadorRecurso, tipoRecurso: tipoRecurso}, 
			url: "showResource", 
		 
			error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },

			success : function(result) {

				
				//$txt.append(result);
				
				$("#txt").append(result);
				$("#txt").css('background','white');
				
				}
			});
	}
	
	
	
	
	
	function buscarRecurso($localParaMostrar,titulo, carregadoPor){
		
		//TESTE usar isEmpty
		$localParaMostrar = $("#content");
		
		//TODO ver ond se vai buscar o titulo
		var titulo = $("#titulo").val();
		var tituloRecurso = encodeURIComponent(titulo);
		
		//TODO ver ond se vai buscar o titulo
		var carregadoPor = $("#carregadoPor").val();
		var criadorRecurso = encodeURIComponent(carregadoPor);
		
		$.ajax({method: "POST", 
			dataType: "json",
			data: {titulo:tituloRecurso, carregadoPor: criadorRecurso}, 
			url: "getResourceType", 
		 
			error: function(xhr, status, error){
					if(xhr.status == 500){
						$.notify("O servidor está offline");
					}
					//else
	 				//	$.notify("Erro: " + xhr.responseText);	    
			  },

			success : function(result) {

					if(result.data.equals("Fotografia")){
						
						showFoto($localParaMostrar, tituloRecurso, criadorRecurso, result.data);
					}
					
					if(result.data.equals("Música")){
						
						showMusic($localParaMostrar, tituloRecurso, criadorRecurso, result.data);
					}
					
					if(result.data.equals("Poema")){
						
						showText($localParaMostrar, tituloRecurso, criadorRecurso, result.data);
					}
					
					if(result.data.equals("Filme")){
						
						showFilm($localParaMostrar, tituloRecurso, criadorRecurso, result.data);
					}
					
					else{
						
						$.notify("Tipo de Recurso desconhecido: " + result.data);
					}
				}
			});
	}
	

	</script>
	
</body>

</html>