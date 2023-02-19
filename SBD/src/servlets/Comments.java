package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dataAccessObject.ComentarioDAO;
import dataAccessObject.CriadorDAO;
import dataAccessObject.RecursoDAO;
import transactionalObjects.Comentario;
@WebServlet("/getComment")
@MultipartConfig
public class Comments  extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	String utilizadorComentario ;
	String tituloRecurso;
	String utilizadorRecurso;
	int classificacao;
	String conteudo;
	Timestamp dataComentario;
	JSONObject resultJSON;
	PrintWriter out;
	public Comments() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		resultJSON = new JSONObject();

		out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		
		utilizadorComentario = request.getParameter("utilizadorComentario");
		tituloRecurso = request.getParameter("tituloRecurso");
		utilizadorRecurso = request.getParameter("utilizadorRecurso");
		
		try {
			Comentario c = ComentarioDAO.getComentariobyUserAndRecurso(utilizadorComentario, tituloRecurso, utilizadorRecurso);
			int a = c.getClassificacao();
			
			resultJSON.put("success", true);
			resultJSON.put("classificacao", a);
			out.write(resultJSON.toString());
			out.flush();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			resultJSON.put("success", false);
			resultJSON.put("error", e.getMessage());
			out.write(resultJSON.toString());
			out.flush();
		}
		
		if (utilizadorRecurso == null || utilizadorComentario == null) {
			resultJSON.put("success", false);
			resultJSON.put("error", "Este utilizador não existe na base de dados");
			resultJSON.put("errorType", "user");
			out.write(resultJSON.toString());
			out.flush();
				
			return;
		}
		
	}


	private boolean mudarClassificacao() {
		boolean cla = false;
		try {
			cla = ComentarioDAO.mudarClassificacao(utilizadorComentario, tituloRecurso, utilizadorRecurso, classificacao, conteudo);
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			resultJSON.put("success", false);
			resultJSON.put("error", e.getMessage());
			out.write(resultJSON.toString());
			out.flush();
			
			return cla;	
		}
		return cla;
		
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
