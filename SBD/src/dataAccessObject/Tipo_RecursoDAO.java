package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Tipo_Recurso;

public class Tipo_RecursoDAO {

	public static Tipo_Recurso buildTypeResource(ResultSet rs) throws SQLException{
		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);
		String tipo = rs.getString(3);
		
		Tipo_Recurso tipo_recurso = new Tipo_Recurso(titulo, carregadoPor, tipo);
		
		return tipo_recurso;
	}
	
	public static boolean criarTipo_Recurso(String titulo, String carregadoPor, String tipo) throws SQLException {

		Tipo_Recurso tr = new Tipo_Recurso(titulo, carregadoPor, tipo);

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertTipoRecurso = "INSERT INTO  tipo_recurso "
				+ "(titulo, carregadoPor, tipo)"
				+ "VALUES(?,?,?)";

		try {

			ps = connection.prepareStatement(insertTipoRecurso);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);
			ps.setString(3, tipo);
			

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
	public static ArrayList<Tipo_Recurso> getAllTipoRecursos() throws SQLException {

		ArrayList<Tipo_Recurso> TipoRecursos = new ArrayList<Tipo_Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllTipoRecursos = "Select * from tipo_recurso";

		try {

			ps = connection.prepareStatement(selectAllTipoRecursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Tipo_Recurso TipoRecurso = buildTypeResource(rs);

				TipoRecursos.add(TipoRecurso);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return TipoRecursos;
	}
	
	public static ArrayList<Tipo_Recurso> getTipoRecursosByAge(int faixaEtaria) throws SQLException {

		ArrayList<Tipo_Recurso> TipoRecursos = new ArrayList<Tipo_Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllTipoRecursos = "Select tr.* "
				+ "from tipo_recurso as tr, recurso as r "
				+ "where "
				+ "r.carregadoPor = tr.utilizadorRecurso "
				+ "and r.titulo = tr.tituloRecurso "
				+ "and r.faixaEtaria < ? "
				+ "and r.bloqueado = 0;";

		try {

			ps = connection.prepareStatement(selectAllTipoRecursos);
			ps.setInt(1, faixaEtaria);
			rs = ps.executeQuery();

			while (rs.next()) {

				Tipo_Recurso TipoRecurso = buildTypeResource(rs);

				TipoRecursos.add(TipoRecurso);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return TipoRecursos;
	}
	

	public static Tipo_Recurso getTipoRecursoByTitleAndUser(String titulo, String carregadoPor) throws SQLException {

		Tipo_Recurso TipoRecursoPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectTipoRecursoByTitleAndUser = "Select * from tipo_recurso where titulo = ? " +
													" and carregadoPor = ?;";

		try {

			ps = connection.prepareStatement(selectTipoRecursoByTitleAndUser);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				TipoRecursoPesquisado = buildTypeResource(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return TipoRecursoPesquisado;
	} 
}
