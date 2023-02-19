package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Grupo_Recurso;
import transactionalObjects.Grupo_Recurso;

public class Grupo_RecursoDAO {

	public static Grupo_Recurso buildGroupResource(ResultSet rs) throws SQLException{
		int idGrupo = rs.getInt(1);
		String tituloRecurso = rs.getString(2);
		String carregadoPor = rs.getString(3);
		
		Grupo_Recurso GrupoRecurso = new Grupo_Recurso(idGrupo, tituloRecurso, carregadoPor);
		
		return GrupoRecurso;
	}
	
	public static boolean criarGrupoRecurso(int grupo, String tituloRecurso, String carregadoPor) throws SQLException {

		Grupo_Recurso gr = new Grupo_Recurso(grupo, tituloRecurso, carregadoPor);

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertGrupoRecurso = "INSERT INTO grupo_recurso "
				+ "(idGrupo, tituloRecurso, carregadoPor)"
				+ "VALUES(?,?,?)";

		try {

			ps = connection.prepareStatement(insertGrupoRecurso);
			ps.setInt(1, grupo);
			ps.setString(2, tituloRecurso);
			ps.setString(3, carregadoPor);
			

			int result = ps.executeUpdate();

			if (result > 0) {

				return true;
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return false;
	}
	public static ArrayList<Grupo_Recurso> getAllGrupoRecursos() throws SQLException {

		ArrayList<Grupo_Recurso> GrupoRecursoes = new ArrayList<Grupo_Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllGrupoRecursoes = "Select * from grupo_recurso order by idGrupo;";

		try {

			ps = connection.prepareStatement(selectAllGrupoRecursoes);

			rs = ps.executeQuery();

			while (rs.next()) {

				Grupo_Recurso GrupoRecurso = buildGroupResource(rs);

				GrupoRecursoes.add(GrupoRecurso);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return GrupoRecursoes;
	}
	
	
	public static ArrayList<Integer> getIdGrupoByRecurso(String tituloRecurso, String carregadoPor) throws SQLException {

		ArrayList<Integer> GrupoRecursos = new ArrayList<Integer>();
		Grupo_Recurso GrupoRecurso = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectGrupoRecursoByRecurso = "Select distinct idGrupo "
				+ "from grupo_recurso where tituloRecurso = ? "
				+ "and carregadoPor = ? ;";

		try {

			ps = connection.prepareStatement(selectGrupoRecursoByRecurso);
			ps.setString(1, tituloRecurso);
			ps.setString(2, carregadoPor);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				
				GrupoRecursos.add(rs.getInt(1));
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return GrupoRecursos;
	} 	
	

	public static ArrayList<Grupo_Recurso> getGrupoRecursoByRecurso(String tituloRecurso, String carregadoPor) throws SQLException {

		ArrayList<Grupo_Recurso> GrupoRecursos = new ArrayList<Grupo_Recurso>();
		Grupo_Recurso GrupoRecurso = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectGrupoRecursoByRecurso = "Select distinct 0, gr.tituloRecurso, gr.carregadoPor from grupo_recurso as gr "
				+ "where idGrupo "
				+ "in "
				+ "(Select idGrupo from grupo_recurso where tituloRecurso = ? "
				+ "and carregadoPor = ? ) "
				+ "and not (tituloRecurso = ? "
				+ "and carregadoPor = ? ) " 
				+ "and concat(gr.tituloRecurso, gr.carregadoPor) "
				+ "in (Select concat(titulo, carregadoPor) from recurso where bloqueado = 0);";

		try {

			ps = connection.prepareStatement(selectGrupoRecursoByRecurso);
			ps.setString(1, tituloRecurso);
			ps.setString(2, carregadoPor);
			ps.setString(3, tituloRecurso);
			ps.setString(4, carregadoPor);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				GrupoRecurso = buildGroupResource(rs);
				GrupoRecursos.add(GrupoRecurso);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return GrupoRecursos;
	} 
	
	public static int getLastIdGrupo() throws SQLException {

		int idGrupo = 0;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectGrupoRecursoByRecurso = "Select max(idGrupo) from grupo_recurso;";

		try {

			ps = connection.prepareStatement(selectGrupoRecursoByRecurso);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				idGrupo = rs.getInt(1);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return idGrupo;
	}

	public static boolean deleteAssociation(int idGrupo) {

		PreparedStatement ps = null;

		String insertGrupoRecurso = "DELETE FROM `isel_share`.`grupo_recurso` WHERE (`idGrupo` = ?)";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(insertGrupoRecurso);
			ps.setInt(1, idGrupo);
			

			int result = ps.executeUpdate();

			if (result > 0) {

				return true;
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return false;
	}
}

