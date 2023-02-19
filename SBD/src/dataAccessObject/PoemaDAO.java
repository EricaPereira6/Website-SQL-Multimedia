package dataAccessObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Poema;

public class PoemaDAO {
	public static Poema buildPoem(ResultSet rs) throws SQLException {

		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);
		
		Poema Poema = new Poema(titulo, carregadoPor);

		return Poema;
	}
	
	public static ArrayList<Poema> selectAllPoemas() throws SQLException {

		ArrayList<Poema> Poemas = new ArrayList<Poema>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllPoemas = "Select * from poema";

		try {

			ps = connection.prepareStatement(selectAllPoemas);

			rs = ps.executeQuery();

			while (rs.next()) {

				Poema Poema = buildPoem(rs);

				Poemas.add(Poema);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return Poemas;
	}
	
	public static boolean criarPoema(String titulo, String carregadoPor) throws SQLException {

		Poema Poema = new Poema(titulo, carregadoPor);

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertPoema = "INSERT INTO poema "
				+ "(titulo, carregadoPor)"
				+ "VALUES(?,?)";

		try {

			ps = connection.prepareStatement(insertPoema);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);
			

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

	public static void main(String[] args) throws SQLException, IOException {
		
	}

	public static Poema selectPoemaByTitleAndUser(String titulo, String utilizador) throws SQLException {
		Poema Poema = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllPoemas = "Select * from poema where titulo = ? and carregadoPor = ?";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectAllPoemas);
			ps.setString(1, titulo);
			ps.setString(2, utilizador);

			rs = ps.executeQuery();

			while (rs.next()) {

				Poema = buildPoem(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return Poema;
	}
	
		// TODO add tipo
	public static boolean deletePoemaByTituloAndCarregadoPor(String titulo, String carregadoPor)
			throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;

		String selectRecursoByTituloAndFaixaEtaria = "Delete from poema " 
				+ "where titulo = ? "
				+ "and carregadoPor = ? ;";

		ps = connection.prepareStatement(selectRecursoByTituloAndFaixaEtaria);
		ps.setString(1, titulo);
		ps.setString(2, carregadoPor);

		int result = ps.executeUpdate();

		if (result > 0) {

			return true;
		}

		connection.close();

		return false;
	}
}

