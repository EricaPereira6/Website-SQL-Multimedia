//
//var filterQuestion;
//filterQuestion = /^([A-Za-zÀ-ÖØ-öø-ÿ]{1,}[A-Za-zÀ-ÖØ-öø-ÿ0-9 .?!]{0,})$/;
//
//function validateQuestion(theForm) {
//	
//	if (theForm.tema.value == ""){
//		theForm.tema.focus();
//		//alert( "escolher um tema" );
//		return false;
//	}
//	
//	if (!filterQuestion.test(theForm.pergunta.value)) {
//		theForm.pergunta.focus();
//		//alert( "escrever uma pergunta" );
//		return false;
//	}
//	
//	if (!filterQuestion.test(theForm.resposta1.value)) {
//		theForm.resposta1.focus();
//		//alert( "escrever resposta 1" );
//		return false;
//	}
//	
//	if (!filterQuestion.test(theForm.resposta2.value)) {
//		theForm.resposta2.focus();
//		//alert( "escrever resposta 2" );
//		return false;
//	}
//	
//	if (!filterQuestion.test(theForm.resposta3.value)) {
//		theForm.resposta3.focus();
//		//alert( "escrever resposta 3" );
//		return false;
//	}
//	
//	if (!filterQuestion.test(theForm.resposta4.value)) {
//		theForm.resposta4.focus();
//		//alert( "escrever resposta 4" );
//		return false;
//	}
//	
//	if (!theForm.correta1.checked && !theForm.correta2.checked && !theForm.correta3.checked && !theForm.correta4.checked) {
//		alert ( "tem de escolher pelo menos 1 resposta correta." );
//		return false;
//	}
//	
//	if (theForm.foto1.value == "") {
//		theForm.foto1.focus();
//		//alert( "escolher mínimo uma foto" );
//		return false;
//	}
//	return true;
//}
//
//function validateSaveQuestion(theForm) {
//	
//	if (theForm.tema.value == "") {
//		theForm.tema.focus();
//		//alert( "escolher um tema" );
//		return false;
//	}
//	if (theForm.pergunta.value == "") {
//		theForm.pergunta.focus();
//		//alert( "escolher uma pergunta" );
//		return false;
//	}
//	return true;
//}
