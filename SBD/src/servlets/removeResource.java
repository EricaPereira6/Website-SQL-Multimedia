package servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.jni.Time;
import org.json.JSONObject;

import controller.Utilities;
import dataAccessObject.ArtistaDAO;
import dataAccessObject.Artista_RecursoDAO;
import dataAccessObject.FilmeDAO;
import dataAccessObject.FotografiaDAO;
import dataAccessObject.Grupo_RecursoDAO;
import dataAccessObject.MusicaDAO;
import dataAccessObject.PoemaDAO;
import dataAccessObject.RecursoDAO;
import dataAccessObject.Tipo_RecursoDAO;
import transactionalObjects.Artista;
import transactionalObjects.Fotografia;
import transactionalObjects.Grupo_Recurso;
import transactionalObjects.Poema;
import transactionalObjects.Recurso;
import transactionalObjects.Tipo_Recurso;

@WebServlet("/removeResource")
@MultipartConfig
public class removeResource extends HttpServlet {
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

	InputStream foto = null;

	public removeResource() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		resultJSON = new JSONObject();
		

		//TODO Conn tem de ficar aqui pa fazer o commit ou rollback no final

		response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
		out = response.getWriter();
		//		response.setContentType("application/json");


		sesh = request.getSession();

		if(!Utilities.isValidSession(sesh)) {
			out.print(Utilities.insuccessJSON(Utilities.invalidSession(sesh)).toString());
			return;
		}

		if(!Utilities.isValidType(sesh, 2)) {
			out.print(Utilities.insuccessJSON("Falta privilégios").toString());
			return;
		}

		titulo = request.getParameter("titulo");
		carregadoPor = request.getParameter("carregadoPor");

		if(!(Utilities.isValidParameter(titulo) && Utilities.isValidParameter(carregadoPor))) {
			out.print(Utilities.insuccessJSON("Falta parametros").toString());
			return;
		}


		//ir buscar o recurso em questão
		try {
			//TODO !exists nao resulta em ex
			Recurso r = RecursoDAO.getRecursoByTituloAndCarregadoPor(titulo, carregadoPor);

			if(r == null) {

				throw new SQLException("Nenhum recurso com titulo:" + titulo + " carregado Por:" + carregadoPor);
			}

		} catch (SQLException e) {

			//TODO Conn rollback
			out.write(Utilities.errorJSON(e, "SQL").toString());
			out.flush();
			return;
		}

		//remover o recurso da bd
		try {

			RecursoDAO.deleteRecursoByTituloAndCarregadoPor(titulo, carregadoPor);

		} catch (SQLException e) {

			e.printStackTrace();

			//TODO Conn rollback
			out.write(Utilities.errorJSON(e, "SQL").toString());
			out.flush();
			return;
		}

		//vai buscar o tipo de recurso para saber a tabela especifica do recurso
		String tipo = null;
		try {

			//todo ex antes
			Tipo_Recurso tipoRecurso = Tipo_RecursoDAO.getTipoRecursoByTitleAndUser(titulo, carregadoPor);
			

			if(tipoRecurso == null) {
				
				throw new SQLException("Nenhum tipo associado ao recurso " + titulo + " carregado por " + carregadoPor);
			}
			
			tipo = tipoRecurso.getTipo();
			
		} catch (SQLException e) {

			e.printStackTrace();


			out.write(Utilities.errorJSON(e, "SQL").toString());
			out.flush();
			return;
		}

		//remover o recurso que é do tipo x da bd
		if(tipo.equalsIgnoreCase("fotografia")) {

			try {

				FotografiaDAO.deleteFotografiaByTituloAndCarregadoPor(titulo, carregadoPor);

			} catch (SQLException e) {

				e.printStackTrace();

				out.write(Utilities.errorJSON(e, "SQL").toString());
				out.flush();
				return;
			}
		}

		if(tipo.equalsIgnoreCase("filme")) {

			try {

				FilmeDAO.deleteFilmeByTituloAndCarregadoPor(titulo, carregadoPor);

			} catch (SQLException e) {

				e.printStackTrace();

				out.write(Utilities.errorJSON(e, "SQL").toString());
				out.flush();
				return;
			}
		}

		if(tipo.equalsIgnoreCase("poema")) {

			try {

				PoemaDAO.deletePoemaByTituloAndCarregadoPor(titulo, carregadoPor);

			} catch (SQLException e) {

				e.printStackTrace();

				out.write(Utilities.errorJSON(e, "SQL").toString());
				out.flush();
				return;
			}
		}

		if(tipo.contains("m�sica")) {

			try {

				MusicaDAO.deleteMusicaByTituloAndCarregadoPor(titulo, carregadoPor);

			} catch (SQLException e) {

				e.printStackTrace();

				out.write(Utilities.errorJSON(e, "SQL").toString());
				out.flush();
				return;
			}
		}
		
		try {

			ArrayList<Integer> gr = Grupo_RecursoDAO.getIdGrupoByRecurso(titulo, carregadoPor);
			
			for (int g : gr) {
				Grupo_RecursoDAO.deleteAssociation(g);
			}

		} catch (SQLException e) {

			e.printStackTrace();

			out.write(Utilities.errorJSON(e, "SQL").toString());
			out.flush();
			return;
		}

		//TODO Conn commit 
		resultJSON.put("success", true);
		out.write(resultJSON.toString());
		out.flush();
		return;
	}

	public static byte[] toByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int len;

		// read bytes from the input stream and store them in buffer
		while ((len = in.read(buffer)) != -1) {
			// write bytes from the buffer into output stream
			os.write(buffer, 0, len);
		}

		return os.toByteArray();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}

}