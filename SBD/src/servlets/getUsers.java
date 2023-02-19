package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dataAccessObject.AdministradorDAO;
import dataAccessObject.ArtistaDAO;
import dataAccessObject.Artista_RecursoDAO;
import dataAccessObject.ConvidadoDAO;
import dataAccessObject.CriadorDAO;
import dataAccessObject.RecursoDAO;
import dataAccessObject.Tipo_UtilizadorDAO;
import dataAccessObject.UtilizadorDAO;
import transactionalObjects.Administrador;
import transactionalObjects.Artista;
import transactionalObjects.Convidado;
import transactionalObjects.Criador;
import transactionalObjects.Recurso;
import transactionalObjects.Tipo_Utilizador;
import transactionalObjects.Utilizador;

@WebServlet("/getUsers")
public class getUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public getUsers() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		JSONObject resultJSON = new JSONObject();

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession sesh = request.getSession();

		//TODO quando √© convidado, qual √© a faixa Et√°ria?
		String user = (String) sesh.getAttribute("username");
		String pw = (String) sesh.getAttribute("password");
		String type = (String) sesh.getAttribute("type");
		
		if ((!type.isEmpty() && type != null) || (type.equals("admin") && user != null && !user.isEmpty()) ||
				(type.equals("convidado") && user != null && !user.isEmpty()) ||
				(type.equals("criador") && user != null && !user.isEmpty() && pw != null && !pw.isEmpty())) {
		
			Utilizador utilizador = null;
			Administrador admin = null;
			Convidado convidado = null;
			Criador criador = null;
				
			try {
					
				utilizador = UtilizadorDAO.getUtilizadorByNomeUtilizador(user);
				admin = AdministradorDAO.getAdministradorByNomeUtilizador(user);
				convidado = ConvidadoDAO.getConvidadoByNomeUtilizador(user);
				criador = CriadorDAO.getCriadorByNomeUtilizador(user);	
				
			} catch (SQLException e1) {
				//e1.printStackTrace();	
				System.out.print("searchRecommended - try catch:");
				System.err.println(" get user by name exception");
			}
				
			if (utilizador == null || (admin == null && convidado == null && criador == null)) {
					
				resultJSON.put("success", false);
				resultJSON.put("error", "Este utilizador n„o existe na base de dados");
				resultJSON.put("errorType", "user");
				out.write(resultJSON.toString());
				out.flush();
					
				return;
					
			} else if (criador != null && !pw.equals(criador.getPalavraPasse())) {
		
				resultJSON.put("success", false);
				resultJSON.put("error", "credenciais erradas");
				resultJSON.put("errorType", "pw");
				out.write(resultJSON.toString());
				out.flush();
					
				return;		
			
			}
			
			try {
				
				ArrayList<Utilizador> users = new ArrayList<Utilizador>();
				users = UtilizadorDAO.getAllUtilizadores();
			
				// colocar todos os recursos numa string com um caracter de separa√ß√£o
				// ou fazer json.put() de cada recurso diferenciado por chave
				String recursosString = "";
				JSONObject recursos = new JSONObject();
			
				for (Utilizador u : users) {
					
					if (!u.getNomeUtilizador().equals(user)) {
						
						Tipo_Utilizador tipo = Tipo_UtilizadorDAO.getTipoUtilizadorByName(u.getNomeUtilizador());		
						String attrs = "";
						
						if (tipo.getTipo() == 1) {
							Convidado guest = ConvidadoDAO.getConvidadoByNomeUtilizador(u.getNomeUtilizador());
							attrs = guest.toString();
						}
						else if (tipo.getTipo() == 2) {
							Criador criator = CriadorDAO.getCriadorByNomeUtilizador(u.getNomeUtilizador());
							attrs = criator.toString();
						}
						else {
							Administrador admins = AdministradorDAO.getAdministradorByNomeUtilizador(u.getNomeUtilizador());
							attrs = admins.toString();
						}
				
						recursosString += u.toString().substring(0, u.toString().length() - 1) + ", ";
						recursosString += attrs.substring(0, attrs.toString().length() - 1);
						recursosString += "|";
				        //recursos.put(key, value)
					}
				}
			
				resultJSON.put("success", true);
				resultJSON.put("data", recursosString);
				out.write(resultJSON.toString());
				out.flush();	
	
				return;
			
			} catch (Exception e) {
			
				//e.printStackTrace();
				resultJSON.put("success", false);
				resultJSON.put("error", e.getMessage());
				resultJSON.put("errorType", "resources");
				out.write(resultJSON.toString());
				out.flush();
				
			}
		} else {
				
				resultJSON.put("success", false);
				resultJSON.put("error", "N„o est· loggado");
				resultJSON.put("errorType", "login");
				out.write(resultJSON.toString());
				out.flush();
				
				return;
			}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}