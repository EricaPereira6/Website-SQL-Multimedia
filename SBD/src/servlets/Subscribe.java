package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import dataAccessObject.ConvidadoDAO;
import transactionalObjects.Convidado;
import dataAccessObject.AdministradorDAO;
import transactionalObjects.Administrador;

@WebServlet("/Subscribe")
public class Subscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Subscribe() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession sesh = request.getSession();

		String user = request.getParameter("username");

		JSONObject resultJSON = new JSONObject();
		
		Convidado currentConvidado = null;
		Administrador currentAdmin = null;		
		
		if (user != null && !user.isEmpty()) {

			try {

				currentConvidado = ConvidadoDAO.getConvidadoByNomeUtilizador(user);
				currentAdmin = AdministradorDAO.getAdministradorByNomeUtilizador(user);

				if (currentConvidado == null && currentAdmin == null) {

					resultJSON.put("success", false);
					resultJSON.put("error", "credenciais erradas");
					out.write(resultJSON.toString());
					out.flush();
					
				} else if (currentConvidado != null && currentConvidado.getBloqueado()) {
					
					resultJSON.put("success", false);
					resultJSON.put("error", "Utilizador bloqueado");
					out.write(resultJSON.toString());
					out.flush();
					
				} else if (currentAdmin != null) {
					
					sesh.setAttribute("username", user);
					sesh.setAttribute("type", "admin");
					String seshUser = (String) sesh.getAttribute("username");
					System.out.println(seshUser);

					resultJSON.put("success", true);
					resultJSON.put("admin", true);
					resultJSON.put("data", currentAdmin.toString());
					out.write(resultJSON.toString());
					out.flush();
					
				}else {
					
					sesh.setAttribute("username", user);
					sesh.setAttribute("type", "convidado");
					String seshUser = (String) sesh.getAttribute("username");
					System.out.println(seshUser);

					resultJSON.put("success", true);
					resultJSON.put("admin", false);
					resultJSON.put("data", currentConvidado.toString());
					out.write(resultJSON.toString());
					out.flush();
				}

			} catch (SQLException e) {

				resultJSON.put("success", false);
				resultJSON.put("error", e.getMessage());
				out.write(resultJSON.toString());
				out.flush();
			}

		} else {

			resultJSON.put("success", false);
			resultJSON.put("error", "credenciais em falta");
			out.write(resultJSON.toString());
			out.flush();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
