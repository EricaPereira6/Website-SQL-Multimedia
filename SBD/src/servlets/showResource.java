package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import dataAccessObject.RecursoDAO;
import transactionalObjects.Recurso;

@WebServlet("/showResource")
@MultipartConfig
public class showResource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//	HttpServletRequest request, HttpServletResponse response;

	PrintWriter out;
	JSONObject resultJSON;
	HttpSession sesh;

	boolean uploadOk;

	Timestamp dataPublicacao; 
	String carregadoPor;
	boolean bloqueado;
	int faixaEtaria;
	InputStream ilustracao = null;
	InputStream ficheiro = null;
	String resumo;
	String titulo;
	String tipoRecurso;

	
	InputStream foto = null;

	
	public showResource() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		resultJSON = new JSONObject();

		//recurso params
		titulo = request.getParameter("titulo");
		carregadoPor = request.getParameter("carregadoPor");
		tipoRecurso = request.getParameter("tipoRecurso");
		
		InputStream is = null;
				
		if(titulo == null || titulo.isEmpty() || carregadoPor == null || carregadoPor.isEmpty() || tipoRecurso == null || 
				tipoRecurso.isEmpty()) {
			return;
		}

		try {
			Recurso r = RecursoDAO.getRecursoByTituloAndCarregadoPor(titulo, carregadoPor);
			if (r == null) {
				System.out.println(titulo);
			}
			if(tipoRecurso.equalsIgnoreCase("Ilustracao")) {
				is = r.getIlustracao();
			}
			else {
				is = r.getFicheiro();
			}
			
			
			
		} catch (SQLException e) {

			e.printStackTrace();

			//TODO errorResponse
			resultJSON.put("success", false);
			resultJSON.put("error", e.getMessage());
			out.write(resultJSON.toString());
			out.flush();
		}
		
		String fileExtension="";
		
		//TODO get String from InputStream
		if(tipoRecurso.equalsIgnoreCase("Poema")) {
		
			fileExtension = ".txt";
			response.setContentType("text/html");
		}
		
		else if(tipoRecurso.equalsIgnoreCase("Filme")) {
			
			fileExtension = ".mp4";
			response.setContentType("video/mp4");
		}
		
		else if(tipoRecurso.equalsIgnoreCase("Fotografia")) {
			
			fileExtension = ".png";
			response.setContentType("image/png");
		}
		
		else if(tipoRecurso.equalsIgnoreCase("Ilustracao")) {
			
			fileExtension = ".png";
			response.setContentType("image/png");
		}
		
		else if(tipoRecurso.equalsIgnoreCase("Música")) {
			
			fileExtension = ".mp3";
			response.setContentType("audio/mpeg");
		}
		
		else {
			
			System.out.println("Tipo nÃ£o reconhecido: " + tipoRecurso);
			return;
		}
		
		
		String filename = titulo + fileExtension;
		
		//response.setContentType("APPLICATION/OCTET-STREAM");

		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");


		ServletOutputStream out = response.getOutputStream();
		
		byte[] outputByte = new byte[4096];
		while(is.read(outputByte, 0, 4096) != -1)
		{
			out.write(outputByte, 0, 4096);
		}
		is.close();
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}

}