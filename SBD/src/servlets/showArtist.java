package servlets;

import java.io.IOException;
import java.io.InputStream;
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

import dataAccessObject.ArtistaDAO;
import transactionalObjects.Artista;

@WebServlet("/showArtist")
@MultipartConfig
public class showArtist extends HttpServlet {
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
	String artista;
	String tipoRecurso;

	
	InputStream foto = null;

	
	public showArtist() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		resultJSON = new JSONObject();

		//recurso params
		artista = request.getParameter("artista");
		
		InputStream is = null;
				
		if(artista == null || artista.isEmpty()) {
			return;
		}

		try {
			Artista a = ArtistaDAO.getArtistaByName(artista);
			if (a == null) {
				System.out.println(artista);
			}
			else {
				is = a.getFoto();
			}
			
			
			
		} catch (SQLException e) {

			e.printStackTrace();

			//TODO errorResponse
			resultJSON.put("success", false);
			resultJSON.put("error", e.getMessage());
			out.write(resultJSON.toString());
			out.flush();
		}
		
		String fileExtension = ".png";
		response.setContentType("image/png");
		
		
		String filename = artista + fileExtension;
		
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