package controller;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class Utilities {
	
	public static final String charsetISO = StandardCharsets.ISO_8859_1.displayName();


	public static void showRequestParameters(HttpServletRequest request) {

		Enumeration<String> parameterNames = request.getParameterNames();
		System.out.println("------------------------------------------");
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			String value = request.getParameter(parameterName);
			System.out.println("parameter name is " + parameterName + " -> " + value);
		}
		System.out.println("------------------------------------------");

	}

	public static boolean isValidSession(HttpSession sesh) {

		String user = (String) sesh.getAttribute("username");
		String pw = (String) sesh.getAttribute("password");
		String type = (String) sesh.getAttribute("type");

		if (user == null || user.isEmpty() || type == null || type.isEmpty()
				|| (type.equalsIgnoreCase("criador") && (pw == null || pw.isEmpty()))) {

			return false;
		}

		return true;
	}
	
	public static String invalidSession(HttpSession sesh) {
		
		

		String user = (String) sesh.getAttribute("username");
		String pw = (String) sesh.getAttribute("password");
		String type = (String) sesh.getAttribute("type");
		
		
		if(user == null || user.isEmpty()) {
			return "Nenhum user logado";
		}
		
		if(type == null || type.isEmpty()) {
			return "Nenhum tipo de user guardado";
		}
		
		if(type.equalsIgnoreCase("criador") && (pw == null || pw.isEmpty()) ){
			return "Nenhuma password guardada";
		}
		
		return "No idea";
	}
	
	public static boolean isValidType(HttpSession sesh, int level) {
		
		String type = (String) sesh.getAttribute("type");
		
		if(type == null || type.isEmpty()) {
			return false;
		}
		
		int userLevel = 0;
		
		switch (type) {
		case "convidado":userLevel=1;break;
		case "criador":userLevel=2;break;
		case "admin":userLevel=3;break;
		}


		
		if(userLevel < level) {
			return false;
		}
		return true;
	}
	
	public static JSONObject errorJSON(Exception e, String exceptionType) {
		
		JSONObject errorJSON = new JSONObject();
		
		errorJSON.put("success", false);
		errorJSON.put("error", e.getMessage());
		errorJSON.put("errorType", exceptionType);
		
		return errorJSON;
	}
	
	public static JSONObject successJSON(String message) {
		
		JSONObject successJSON = new JSONObject();
		
		successJSON.put("success", true);
		successJSON.put("data", message);
		
		return successJSON;
	}
	
	public static JSONObject insuccessJSON(String message) {
		
		JSONObject insuccessJSON = new JSONObject();
		
		insuccessJSON.put("success", false);
		insuccessJSON.put("error", message);
		
		return insuccessJSON;
	}
	
	public static boolean isValidParameter(String parameter) {
		
		if(parameter == null || parameter.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
