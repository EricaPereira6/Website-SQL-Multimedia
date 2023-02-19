package dataAccessObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Filme;

public class FilmeDAO {

	public static Filme buildMovie(ResultSet rs) throws SQLException {

		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);
		Date anoLancamento = rs.getDate(3);

		Filme filme = new Filme(titulo, carregadoPor, anoLancamento);

		return filme;
	}

	public static ArrayList<Filme> getAllFilmes() throws SQLException {

		ArrayList<Filme> filmes = new ArrayList<Filme>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllFilmes = "Select * from filme";

		try {

			ps = connection.prepareStatement(selectAllFilmes);

			rs = ps.executeQuery();

			while (rs.next()) {

				Filme filme = buildMovie(rs);

				filmes.add(filme);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return filmes;
	}

	public static boolean criarFilme(String titulo, String carregadoPor, int anoLancamento) throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;

		String insertFilme = "INSERT INTO filme " 
		+ "(titulo, carregadoPor, anoLancamento)" 
		+ "VALUES(?,?,?)";

		try {

			ps = connection.prepareStatement(insertFilme);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);
			ps.setInt(3, anoLancamento);

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

	// TODO add tipo
	public static boolean deleteFilmeByTituloAndCarregadoPor(String titulo, String carregadoPor)
			throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;

		String selectRecursoByTituloAndFaixaEtaria = "Delete from filme " 
		+ "where titulo = ? "
		+ "and carregadoPor = ? ;";
		try {

			ps = connection.prepareStatement(selectRecursoByTituloAndFaixaEtaria);
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
	
		public static Filme selectFilmeByTitleAndUser(String titulo, String utilizador) throws SQLException {
		Filme filme = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllFilmes = "Select * from filme where titulo = ? and carregadoPor = ?";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectAllFilmes);
			ps.setString(1, titulo);
			ps.setString(2, utilizador);

			rs = ps.executeQuery();

			while (rs.next()) {

				filme = buildMovie(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return filme;
	}
}
