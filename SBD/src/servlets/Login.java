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
import dataAccessObject.CriadorDAO;
import transactionalObjects.Criador;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Set the MIME type for the response message
		// response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession sesh = request.getSession();

		String user = request.getParameter("username");
		String pw = request.getParameter("password");

		// String errorMessage = "none";

		JSONObject resultJSON = new JSONObject();

		Criador currentUser = null;
		
		
		//registo
//		String regex = "^[\\p{L}0-9][\\p{L}0-9 ]{0,}+$";
//		Pattern pattern = Pattern.compile(regex);
//		Matcher userMatcher = pattern.matcher(seshUser);
//
//		regex = "^[\\p{L}0-9][\\p{L}0-9 ]{0,}+$";
//		pattern = Pattern.compile(regex);
//		Matcher pwMatcher = pattern.matcher(seshPW);
		
		
		
		if (user != null && !user.isEmpty() && pw != null && !pw.isEmpty()) {

			try {

				currentUser = CriadorDAO.getCriadorByNomeUtilizador(user);

				if (currentUser == null || !pw.equals(currentUser.getPalavraPasse())) {

					resultJSON.put("success", false);
					resultJSON.put("error", "credenciais erradas");
					out.write(resultJSON.toString());
					out.flush();
					
				} else if (currentUser.getBloqueado()) {
					
					resultJSON.put("success", false);
					resultJSON.put("error", "Utilizador bloqueado");
					out.write(resultJSON.toString());
					out.flush();
					
				} else {
					
					sesh.setAttribute("username", user);
					sesh.setAttribute("password", pw);
					sesh.setAttribute("type", "criador");
					
					String seshUser = (String) sesh.getAttribute("username");
					System.out.println(seshUser);
					String seshPW = (String) sesh.getAttribute("password");
					System.out.println(seshPW);

					resultJSON.put("success", true);
					resultJSON.put("data", currentUser.toString());
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
