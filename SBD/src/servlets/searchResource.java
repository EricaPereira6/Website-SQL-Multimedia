package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import dataAccessObject.AdministradorDAO;
import dataAccessObject.ConvidadoDAO;
import dataAccessObject.CriadorDAO;
import dataAccessObject.RecursoDAO;
import dataAccessObject.UtilizadorDAO;
import transactionalObjects.Administrador;
import transactionalObjects.Convidado;
import transactionalObjects.Criador;
import transactionalObjects.Recurso;
import transactionalObjects.Utilizador;

@WebServlet("/searchResource")
public class searchResource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public searchResource() {
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
							
				String titulo = request.getParameter("titulo");
				
				//int idadeParaFaixaEtaria = getAge(seshUser.getDataNascimento());
				int idadeParaFaixaEtaria = utilizador.getAge();
		
				ArrayList<Recurso> res = new ArrayList<Recurso>();
				res = RecursoDAO.getRecursosByTituloAndFaixaEtaria(titulo, idadeParaFaixaEtaria);
			
				// colocar todos os recursos numa string com um caracter de separa√ß√£o
				// ou fazer json.put() de cada recurso diferenciado por chave
				String recursosString = "";
				JSONObject recursos = new JSONObject();
			
				for (Recurso r : res) {
			
					recursosString += r.toString();
					recursosString += "|";
			        //recursos.put(key, value)
				}
			
				resultJSON.put("success", true);
				resultJSON.put("data", recursosString);
				out.write(resultJSON.toString());
				out.flush();	
		
				System.out.println("Success!");
				
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
	
	public static int getAge(Date dateOfBirth) {
	    
		Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();
	    
	    birthDate.setTime(dateOfBirth);
	    
	    if (birthDate.after(today)) {
	        throw new IllegalArgumentException("Ainda n„o nasceu");
	    }
	    
	    int todayYear = today.get(Calendar.YEAR);
	    int birthDateYear = birthDate.get(Calendar.YEAR);
	    int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
	    int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
	    int todayMonth = today.get(Calendar.MONTH);
	    int birthDateMonth = birthDate.get(Calendar.MONTH);
	    int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
	    int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
	    int age = todayYear - birthDateYear;
	
	    if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
	        age--;
	    
	    } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
	        age--;
	    }
	    return age;
	}
}