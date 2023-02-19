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
import dataAccessObject.ConvidadoDAO;
import dataAccessObject.CriadorDAO;
import dataAccessObject.Grupo_RecursoDAO;
import dataAccessObject.RecursoDAO;
import dataAccessObject.UtilizadorDAO;
import transactionalObjects.Administrador;
import transactionalObjects.Convidado;
import transactionalObjects.Criador;
import transactionalObjects.Grupo_Recurso;
import transactionalObjects.Recurso;
import transactionalObjects.Utilizador;

@WebServlet("/associateResources")
public class associateResources extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public associateResources() {
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
				
				String str = request.getParameter("resources");
				String[] s = str.split(", ");
				
				ArrayList<Grupo_Recurso> res = new ArrayList<Grupo_Recurso>();

				for (String r : s) {
					if (!r.equals("")) {
						String[] f = r.split(" \\| ");
						String title = f[0];
						String publisher = f[1];
						res.add(new Grupo_Recurso(1, title, publisher));
					}
				}
				
				int id = 1;
				int igual = 0;
				boolean existe = false;
				ArrayList<Grupo_Recurso> gr = Grupo_RecursoDAO.getAllGrupoRecursos();
				
				for (int i = 0; i < gr.size(); i++) {
					
					if (id != gr.get(i).getIdGrupo()) {
						id = gr.get(i).getIdGrupo();
						igual = 0;
					}
					
					for (int j = 0; j < res.size(); j++) {
						
						if (gr.get(i).gettituloRecurso().equals(res.get(j).gettituloRecurso()) &&
								gr.get(i).getCarregadoPor().equals(res.get(j).getCarregadoPor())) {
							igual++;
						}
						if (igual == res.size()) {
							existe = true;
							i = gr.size();
							break;
						}
					}
				}
				
				boolean added = false;
				
				if (existe) {
					resultJSON.put("success", true);
					resultJSON.put("data", "J· existe esta associaÁ„o");
					out.write(resultJSON.toString());
					out.flush();	
					
					return;
				}
				
				id = Grupo_RecursoDAO.getLastIdGrupo() + 1;
				
				for (int j = 0; j < res.size(); j++) {
							
					added = Grupo_RecursoDAO.criarGrupoRecurso(id, res.get(j).gettituloRecurso(), res.get(j).getCarregadoPor());
					if (!added) {
						
						resultJSON.put("success", true);
						resultJSON.put("data", "Ocurreu um erro. Tente mais tarde");
						out.write(resultJSON.toString());
						out.flush();	
						
						return;
					}
				}
				
				resultJSON.put("success", true);
				resultJSON.put("data", "AssociaÁ„o adicionada com sucesso");
				out.write(resultJSON.toString());
				out.flush();				
				
			
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