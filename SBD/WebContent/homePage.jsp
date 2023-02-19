<%--  <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%-- 	pageEncoding="UTF-8"%> --%>

<!-- <!DOCTYPE html> -->
<!-- <html> -->

<!-- <head> -->

<!-- <meta charset="UTF-8"> -->
<!-- <title>Página Principal</title> -->
<%-- <%@include file="links.html"%> --%>

<!-- </head> -->

<%-- <%@include file="navbar.html"%> --%>

<%-- <% --%>

// //session & cookie

<%-- %> --%>

<!-- <style> -->
/* html, body { */
/* 	height: 100%; */
/* } */
<!-- </style> -->

<!-- <body  onload="recomendados();"> -->

<!-- 	<div id="resourcesDiv" class="container-fluid text-center"> -->

<!-- 	</div> -->


<!-- 	<div id="output"></div> -->


<!-- 	<script> -->
	
// 	$("#login").click(function(){
		
// 		$.ajax({method: "POST", 
// 			dataType: "json", 
// 			data: {username: $("#user").val(), password: $("#pw").val()}, 
// 			url: "Login", 
// 			 error: function(xhr, status, error){
// 					if(xhr.status == 500){
// 						$.notify("O servidor está offline");
// 					}
// 					//else
// 	 				//	$.notify("Erro: " + xhr.responseText);	    
// 			  },
// 			success: function(result){
			 	
// 				if(result.success){
// 					$.notify(result.data);
// 					//redirect page tipo X
// 					//jquery 
// 				}
// 				else{
// 					$.notify(result.error);
// 				}
				
// 				$("#selectTheme").prop('disabled', false);
				
// 		  }});
// 		});
	
<!-- 	</script> -->

<!-- 	<script> -->
	
// 	$("#selectTheme").click(function(){
		
// 		$("#selectQuestion").prop('disabled', false);
		
// 		});
	
<!-- 	</script> -->

<!-- 	<script> -->
	
// 	$("#selectQuestion").click(function(){
		
// 		$("#selectStudent").prop('disabled', false);
		
// 		});
	
<!-- 	</script> -->

<!-- 	<script> -->
	
// 	$("#selectStudent").focus(function(){
// 		  $.ajax({dataType: "json", 
// 			  url: "ListStudents", 
// 			  error: function(xhr, status, error){
// 					if(xhr.status == 500){
// 						$.notify("O servidor está offline");
// 					}
// 					//else
// 	 				//	$.notify("Erro: " + xhr.responseText);	    
// 			  },
// 			  success: function(result){
			  
// 			  	var nStudents = Object.keys(result).length; 
// 			  	$("#nStudents").text(nStudents);

			  
// 			  	$("#selectStudent").empty();			  
			  
// 			 	$.each( result, function( key, val ) { 
// 			 		var i = 0;
// 				 	 var $option = '<option value="'+key+'">'+val+'</option>';
// 				  	$("#selectStudent").append($option);
				  	
// 				  	if(i==0){
// 				  		$("#send1").prop("disabled",false);
// 					  	$("#sendRand").prop("disabled",false);
// 						$("#sendAll").prop("disabled",false);
// 						i=1;
// 				  	}
				  
// 			  	});

			 	
				
			
// 		  }});
// 		});
	
<!-- 	</script> -->

<!-- 	<script> -->
	
// 	$("#selectQuestion").focus(function(){
// 		  $.ajax({method: "POST", 
// 			  data: {theme: $('#selectTheme').find(":selected").text()} ,
// 			  dataType: "json", 
// 			  url: "ListQuestions", 
// 			  error: function(xhr, status, error){
// 					if(xhr.status == 500){
// 						$.notify("O servidor está offline");
// 					}
// 					//else
// 	 				//	$.notify("Erro: " + xhr.responseText);	    
// 			  },
// 			  success: function(result){
			  
// 			  //var nSQuestions = Object.keys(result).length; 
// 			  //$("#nStudents").text(nStudents);

			  
// 			  	$("#selectQuestion").empty();
			  
// 			  	$.each( result, function( key, val ) { 
// 				 	 var $option = '<option value="'+key+'">'+val+'</option>';
// 				 	 $("#selectQuestion").append($option);
// 			 	});
	
// 		  }});
		  
// 		});
	
<!-- 	</script> -->

<!-- 	<script> -->
	
// 	function waitAnswer(){
		
// 		$("#send1").prop("disabled",true);
// 		$("#sendRand").prop("disabled",true);
// 		$("#sendAll").prop("disabled",true);
		
// 		progress(60, 60, $('#progressBar'));

// 	}
	
<!-- 	</script> -->

<!-- 	<script> -->
	
// 	$("#send1").click(function(){
		
// 		var stu = $('#selectStudent').find(":selected").val();
// 		var the =  $('#selectTheme').find(":selected").text();
// 		var que = $('#selectQuestion').find(":selected").val();
			
// 		  $.ajax({method: "POST", 
// 			  data: {student: stu, question: que, theme: the} ,
// 			  dataType: "json", 
// 			  url: "SendQuestion",
// 			  error: function(xhr, status, error){
// 					if(xhr.status == 500){
// 						$.notify("O servidor está offline");
// 					}
// 					//else
// 	 				//	$.notify("Erro: " + xhr.responseText);	    
// 			  },
// 			  success: function(result){
// 			 	$.notify(result);			
// 		  }});
		  
// 		  waitAnswer();
// 		});
	
// 	$("#sendRand").click(function(){
		
// 		var len = $("#selectStudent")[0].length;
// 		//var len = $("#selectStudent").length;
// 		var rand = Math.floor(Math.random() * len);  
// 		var stu = $('#selectStudent')[0][rand].value;
// 		var the = $('#selectTheme').find(":selected").text();
// 		var que =$('#selectQuestion').find(":selected").val();
			
// 		  $.ajax({method: "POST", 
// 			  data: {student: stu, question: que, theme: the} ,
// 			  dataType: "json", 
// 			  url: "SendQuestion", 
// 			  error: function(xhr, status, error){
// 					if(xhr.status == 500){
// 						$.notify("O servidor está offline");
// 					}
// 					//else
// 	 				//	$.notify("Erro: " + xhr.responseText);	    
// 			  },
// 			  success: function(result){
// 			 	$.notify(result);			
// 		  }});
		  
// 		  waitAnswer();
// 		});
	
// 	$("#sendAll").click(function(){
		
// 		var stu = 0;
// 		var the =  $('#selectTheme').find(":selected").text();
// 		var que = $('#selectQuestion').find(":selected").val();
			
// 		  $.ajax({method: "POST",
// 			  data: {student: stu, question: que, theme: the},
// 			  dataType: "json", 
// 			  url: "SendQuestion", 
// 			  error: function(xhr, status, error){
// 					if(xhr.status == 500){
// 						$.notify("O servidor está offline");
// 					}
// 					//else
// 	 				//	$.notify("Erro: " + xhr.responseText);	    
// 			  },
// 			  success: function(result){
// 				 $.notify(result);			
// 		  }});
		  
// 		  waitAnswer();
// 		});
	
<!-- 	</script> -->
	
<!-- 	 --> -->


<!-- 	<!-- Import Modal --> -->
<!-- 	<div class="modal" id="importModal"> -->
<!-- 		<div class="modal-dialog"> -->
<!-- 			<div class="modal-content"> -->

<!-- 				Modal Header -->
<!-- 				<div class="modal-header"> -->
<!-- 					<h4 class="modal-title">Guardar Pergunta</h4> -->
<!-- 					<button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 				</div> -->

<!-- 				Modal body -->
<!-- 				<br> <br> -->
<!-- 				<form action="saveQuestion" method="post" -->
<!-- 					onsubmit="return validateSaveQuestion(this)"> -->
<!-- 					Tema: <select name="tema" id="temaS"> -->
<!-- 						<option value="" disabled selected></option> -->
<!-- 						<option value="culture">Cultura</option> -->
<!-- 						<option value="geography">Geografia</option> -->
<!-- 						<option value="science">Ciência</option> -->
<!-- 					</select> <br> <br> Pergunta: <select name="pergunta" -->
<!-- 						id="perguntaS"> -->
<!-- 						<option value="" disabled selected></option> -->
<!-- 					</select> <br> <br> -->

<!-- 					Modal footer -->
<!-- 					<div class="modal-footer"> -->
<!-- 						<input type="submit" class="btn btn-primary" value="Submeter" /> -->
<!-- 						<button type="button" class="btn btn-danger" data-dismiss="modal">Fechar</button> -->
<!-- 					</div> -->
<!-- 				</form> -->

<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->

<!-- <!-- -->

<!-- 	<script> -->
	
<!-- 	$("#perguntaS").focus(function(){ -->
<!-- 		  $.ajax({ -->
<!-- 			  method: "POST",  -->
<!-- 			  data: { theme: $('#temaS').find(":selected").text()}, -->
<!-- 			  dataType: "json",  -->
<!-- 			  url: "ListQuestions",  -->
<!-- 			  error: function(xhr, status, error){ -->
<!-- 					if(xhr.status == 500){ -->
<!-- 						$.notify("O servidor está offline"); -->
<!-- 					} -->
<!-- 					//else -->
<!-- 	 				//	$.notify("Erro: " + xhr.responseText);	     -->
<!-- 			  }, -->
<!-- 			  success: function(result) { -->
			  
<!-- 			  	$("#perguntaS").empty(); -->
			  
<!-- 			 	 $.each( result, function( key, val ) {  -->
<!-- 				 	 var $option = '<option value="'+key+'">'+val+'</option>'; -->
<!-- 				 	 $("#perguntaS").append($option); -->
<!-- 			  });	   -->
			
<!-- 		  }}); -->
<!-- 		}); -->
	
<!-- 	</script> -->

<!-- 	<!-- Export Modal --> -->
<!-- 	<div class="modal" id="exportModal"> -->
<!-- 		<div class="modal-dialog"> -->
<!-- 			<div class="modal-content"> -->

<!-- 				Modal Header -->
<!-- 				<div class="modal-header"> -->
<!-- 					<h4 class="modal-title">Criar Recurso</h4> -->
<!-- 					<button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 				</div> -->

<!-- 				Modal body -->
<!-- 				<br> <br> -->
<!-- 				<form id="exportForm" action="addResource" method="post" -->
<!-- 					enctype="multipart/form-data" class="jquerySubmit"> -->
					
<!-- 					Título:  -->
<!-- 					<input type="text" name="título" value=""/> * -->
<!-- 					<br> -->
<!-- 					Resumo:  -->
<!-- 					<input type="text" name="resumo" value="" /> -->
<!-- 					<br> -->
<!-- 					Faixa Etária:  -->
<!-- 					<input type="text" name="faixaEtária" value="" /> -->
<!-- 					<br> -->
<!-- 					Tipo: -->
<!-- 					<select name="tipo" onchange="changeResource(value);"> -->
<!-- 					<option value="" disabled selected></option> -->
<!-- 					<option value="poema">Poema</option> -->
<!-- 					<option value="filme">Filme</option> -->
<!-- 					<option value="fotografia">Fotografia</option> -->
<!-- 					<option value="música">Música</option> -->
<!-- 					</select> -->
					
<!-- 					<script type="text/javascript"> -->
					
// 					function changeResource(tipo){
// 						if(tipo == "música" || tipo == "poema"){
							
// 							$recurso = $('#recurso');
// 							$escritor='Escritor: <input type="text" name="escritor" value=""/> *';
							
// 							$recurso.append($escritor);

// 						}
						
// 						if(tipo == "fotografia"){
							
// 							$recurso = $('#recurso');
// 							$escritor='Fotógrafo: <input type="text" name="nomeFotógrafo" value=""/> *';
							
// 							$recurso.append($escritor);

// 						}
						
// 						if(tipo == "filme"){
							
// 							$recurso = $('#recurso');
// 							$escritor='Fotógrafo: <input type="text" name="nomeFotógrafo" value=""/> *';
// 							$todo = "TODO";
							
// 							$recurso.append($escritor);
// 							$recurso.append($todo);

// 						}
// 					}
					
<!-- 					</script> -->
					
<!-- 					<br> -->
<!-- 					Conteúdo:  -->
<!-- 					<input type="file" name="conteúdo"/> * -->
<!-- 					<br> -->
<!-- 					Ilustração:  -->
<!-- 					<input type="file" name="ilustração" accept="image/png, image/jpeg, image/jpg"/> -->
<!-- 					<br> -->
<!-- 					<div id="recurso"></div> -->
<!-- 					<br> -->
<!-- 					* - Obrigatório -->

<!-- 					Modal footer -->
<!-- 					<div class="modal-footer"> -->
<!-- 						<input id="submitForm" type="submit" class="btn btn-primary" /> -->
<!-- 						<button type="button" class="btn btn-danger" data-dismiss="modal">Fechar</button> -->
<!-- 					</div> -->
<!-- 				</form> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->

<!-- <!-- -->

<!-- 	<script> -->
	
<!-- 	$('#exportForm').submit(function(e){ -->
<!-- 	    e.preventDefault(); -->
	    
<!-- 	    if(validateQuestion(document.forms.exportForm)){ -->
<!-- 	    	 var formData = new FormData(this); -->
	 	    
<!-- 	 	    $.ajax({ -->
<!-- 	 	        url: 'addQuestion', -->
<!-- 	 	        type: 'post', -->
<!-- 	 	        contentType: false, -->
<!-- 	 	        processData: false, -->
<!-- 	 	        data:formData, -->
<!-- 	 	        error: function(xhr, status, error){ -->
<!-- 	 				if(xhr.status == 500){ -->
<!-- 	 					$.notify("O servidor está offline"); -->
<!-- 	 				} -->
<!-- 	 				//else -->
<!-- 	 				//	$.notify("Erro: " + xhr.responseText);	     -->
<!-- 	 		  }, -->
<!-- 	 	        success:function(result){ -->
<!-- 	 	            // Whatever you want to do after the form is successfully submitted -->
<!-- 	 	            $.notify(result); -->
<!-- 	 	        } -->
<!-- 	 	    }); -->
	 	    
<!-- 	 	   $("#closeForm").one("click"); -->
	    
<!-- 	    }else{ -->
	    	
<!-- 	    	$.notify("Formulário inválido"); -->
<!-- 	    } -->
	    
	   
<!-- 	}); -->
		
<!-- 		//$("#submitForm").click(function(){ -->
			
<!-- 		//$(document).on('submit','form.jquerySubmit',function(){ -->
			
<!-- 		//		$("#exportForm").one("submit"); -->
<!-- 		//		$("#closeForm").trigger("click"); -->
<!-- 		//	}  -->
<!-- 		//}); -->
	
		
	
<!-- 	</script> -->

<!-- 	<!-- Search Modal --> -->
<!-- 	<div class="modal" id="searchModal"> -->
<!-- 		<div class="modal-dialog"> -->
<!-- 			<div class="modal-content"> -->


<!-- 				Modal body -->
<!-- 				<div id="addQuestionSearched"></div> -->

<!-- 				Modal footer -->
<!-- 				<div class="modal-footer"> -->
<!-- 					<button id="closeForm" type="button" class="btn btn-danger" data-dismiss="modal">Close</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
	

<!-- 	<!-- Students Modal  -->
<!-- 	<div class="modal" id="studentsModal"> -->
<!-- 		<div class="modal-dialog"> -->
<!-- 			<div class="modal-content"> -->

<!-- 				Modal Header -->
<!-- 	<!-- -->
<!-- 				<div class="modal-header"> -->
<!-- 					<h4 class="modal-title">Alunos Presentes</h4> -->
<!-- 					<button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 				</div> -->

<!-- 				Modal body -->
<!-- 	<!-- -->
<!-- 				<div class="modal-body">aluno 1</div> -->
<!-- 				<div class="modal-body">aluno 2</div> -->


<!-- 				Modal footer -->
<!-- 	<!-- -->
<!-- 				<div class="modal-footer"> -->
<!-- 					<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	--> -->

<!-- </body> -->

<!-- </html> -->