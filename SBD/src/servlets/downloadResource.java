//package servlets;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.sql.Blob;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Enumeration;
//
//import javax.print.attribute.standard.DateTimeAtCompleted;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.Part;
//
//import org.apache.tomcat.jni.Time;
//import org.json.JSONObject;
//
//import dataAccessObject.ArtistaDAO;
//import dataAccessObject.Artista_RecursoDAO;
//import dataAccessObject.FotografiaDAO;
//import dataAccessObject.RecursoDAO;
//import transactionalObjects.Artista;
//import transactionalObjects.Fotografia;
//import transactionalObjects.Recurso;
//
//
//
//@WebServlet("/downloadResource")
//@MultipartConfig
//public class downloadResource extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
////	HttpServletRequest request, HttpServletResponse response;
//	
//	PrintWriter out;
//	JSONObject resultJSON;
//	HttpSession sesh;
//	
//
//	//recurso PK
//	String carregadoPor;
//	String titulo;
//
//	//Download this
//	InputStream ficheiro = null;
//
//	
//	public downloadResource() {
//		super();
//	}
//
//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		
//		resultJSON = new JSONObject();
//		
//		//TODO Conn tem de ficar aqui pa fazer o commit ou rollback no final
//
//		//TODO CHARSET PT
//		out = response.getWriter();
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//
//		sesh = request.getSession();	
//		
//		titulo = request.getParameter("t�tulo");
//		carregadoPor = request.getParameter("carregadoPor");
//		
//		try {
//			Recurso r = RecursoDAO.getRecursoByTituloAndCarregadoPor(titulo, carregadoPor);
//			InputStream is = r.getFicheiro();
//			
//			//TODO return inputStream
//			
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//			
//			resultJSON.put("success", false);
//			resultJSON.put("error", e.getMessage());
//			out.write(resultJSON.toString());
//			out.flush();
//		}
//		
//		
//	}
//
//
//	
//	private void addFotografia(String nomeFotografo) {
//		
//		//ver se artista com esta profissao existe para adicionar
//		Artista fotografo;
//		try {
//			
//			//check se pode mandar ex caso n�o haja
//			fotografo = ArtistaDAO.getArtistaByName(nomeFotografo);
//			
//		} catch (SQLException e) {
//		
//			e.printStackTrace();
//			
//			resultJSON.put("success", false);
//			resultJSON.put("error", e.getMessage());
//			out.write(resultJSON.toString());
//			out.flush();
//			
//			return;			
//		}
//		
//		if(fotografo != null) {
//			
//			//por recurso
//			
//			boolean isRecursoSaved = false;
//			
//			try {
//			
//				isRecursoSaved = RecursoDAO.criarRecurso(titulo, carregadoPor, resumo, 
//						ficheiro, ilustracao, faixaEtaria, bloqueado, dataPublicacao);
//			
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//				
//				resultJSON.put("success", false);
//				resultJSON.put("error", e.getMessage());
//				resultJSON.put("errorType", "SQL");
//				out.write(resultJSON.toString());
//				out.flush();
//				return;
//			}
//			
//			if(isRecursoSaved) {
//				
//				//por foto
//				
//				boolean isFotografiaSaved;
//				
//				try {
//					
//					isFotografiaSaved = FotografiaDAO.criarFotografia(titulo, carregadoPor);
//			
//				} catch (SQLException e) {
//
//					e.printStackTrace();
//					
//					resultJSON.put("success", false);
//					resultJSON.put("error", e.getMessage());
//					resultJSON.put("errorType", "SQL");
//					out.write(resultJSON.toString());
//					out.flush();
//					return;
//				}
//			
//				if(isFotografiaSaved) {
//					
//					//criar rel artista_recurso
//					
//					String nomeArtista = nomeFotografo;
//					String tituloRecurso=titulo;
//					String profissao = "fot�grafo";
//					String utilizadorRecurso = carregadoPor;
//					
//					try {
//					
//						Artista_RecursoDAO.criarArtista_Recurso(nomeArtista, profissao, tituloRecurso, utilizadorRecurso);
//					
//					} catch (SQLException e) {
//						
//						e.printStackTrace();
//						
//						//TODO Conn rollback
//						
//						resultJSON.put("success", false);
//						resultJSON.put("error", e.getMessage());
//						resultJSON.put("errorType", "SQL");
//						out.write(resultJSON.toString());
//						out.flush();
//						return;
//					}
//					
//					boolean isArtistaRecursoAssociated = true;
//					
//					if(isArtistaRecursoAssociated) {
//						
//						//TODO Conn commit 
//						resultJSON.put("success", true);
//						out.write(resultJSON.toString());
//						out.flush();
//						return;
//					}
//				}
//			}			
//		}
//		
//		else {
//			
//			//por artista na bd 
//			//ArtistaDAO.criarArtista(nomeArtista, foto);
//			
//			if(foto == null) {
//				
//				//mudar blobs
//				//foto = "C:\\default.png";
//			}
//			
//			//criar rela��o entre artista e recurso
//		}
//	}
//
//	public static byte[] toByteArray(InputStream in) throws IOException {
//
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//
//		byte[] buffer = new byte[1024];
//		int len;
//
//		// read bytes from the input stream and store them in buffer
//		while ((len = in.read(buffer)) != -1) {
//			// write bytes from the buffer into output stream
//			os.write(buffer, 0, len);
//		}
//
//		return os.toByteArray();
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		doGet(request, response);
//	}
//
//}