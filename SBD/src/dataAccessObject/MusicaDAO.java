package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Musica;

public class MusicaDAO {

	public static Musica buildMusic(ResultSet rs) throws SQLException {

		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);

		Musica musica = new Musica(titulo, carregadoPor);

		return musica;
	}

	public static ArrayList<Musica> selectAllMusicas() throws SQLException {

		ArrayList<Musica> musicas = new ArrayList<Musica>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllMusica = "Select * from musica";

		try {

			ps = connection.prepareStatement(selectAllMusica);

			rs = ps.executeQuery();

			while (rs.next()) {

				Musica musica = buildMusic(rs);

				musicas.add(musica);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return musicas;
	}

	public static boolean criarMusica(String titulo, String carregadoPor) throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		String insertFilme = "INSERT INTO  filme "
				+ "(titulo, carregadoPor)"
				+ "VALUES(?,?)";

		try {

			ps = connection.prepareStatement(insertFilme);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);


			int result = ps.executeUpdate();

			if (result > 0) {

				return true;
			}

			connection.close();

		} catch (Exception e) {

			throw new SQLException(e);
		}

		return false;
	}

	// TODO add tipo
	public static boolean deleteMusicaByTituloAndCarregadoPor(String titulo, String carregadoPor)
			throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;

		String selectRecursoByTituloAndFaixaEtaria = "Delete from musica " 
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
	
	public static Musica selectMusicaByTitleAndUser(String titulo, String utilizador) throws SQLException {
			Musica Musica = null;

			PreparedStatement ps = null;
			ResultSet rs = null;

			String selectAllMusicas = "Select * from musica where titulo = ? and carregadoPor = ?";

			try {
				
				Conn Conn = new Conn();
				Conn.openSession();
				Connection connection = Conn.getConn();

				ps = connection.prepareStatement(selectAllMusicas);
				ps.setString(1, titulo);
				ps.setString(2, utilizador);

				rs = ps.executeQuery();

				while (rs.next()) {

					Musica = buildMusic(rs);
				}

				connection.close();

			} catch (Exception e) {

				System.out.println(e);
			}

			return Musica;
	}
	
}
