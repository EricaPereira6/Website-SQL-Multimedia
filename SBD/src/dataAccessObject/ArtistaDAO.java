package dataAccessObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Artista;

public class ArtistaDAO {
	public static Artista buildArtist(ResultSet rs) throws SQLException {

		String nomeArtista = rs.getString(1);
		Blob fotoBlob = rs.getBlob(2);
		InputStream foto = fotoBlob.getBinaryStream();
		
		Artista Artista = new Artista(nomeArtista, foto);

		return Artista;
	}
	
	public static ArrayList<Artista> selectAllArtistas() throws SQLException {

		ArrayList<Artista> artistas = new ArrayList<Artista>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllArtistas = "Select * from artista";

		try {

			ps = connection.prepareStatement(selectAllArtistas);

			rs = ps.executeQuery();

			while (rs.next()) {

				Artista artista = buildArtist(rs);

				artistas.add(artista);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return artistas;
	}
	
	public static Artista getArtistaByName(String nomeArtista) throws SQLException {

		Artista artista = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectArtista = "Select * from artista where nomeArtista = ?";

		try {

			ps = connection.prepareStatement(selectArtista);
			ps.setString(1, nomeArtista);

			rs = ps.executeQuery();

			while (rs.next()) {

				artista = buildArtist(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return artista;
	}
	
	public boolean criarArtista(String nomeArtista, InputStream foto) throws SQLException {

		Artista artista = new Artista(nomeArtista, foto);

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertArtista = "INSERT INTO Artista "
				+ "(nomeArtista, foto)"
				+ "VALUES(?,?)";

		try {

			ps = connection.prepareStatement(insertArtista);
			ps.setString(1, nomeArtista);
			ps.setBlob(2, foto);

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

	public static int getArtistaNumRecursos(String nomeArtista) {
		
		int count = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;

		String getCount = "Select sel.count FROM "
				+ "(Select COUNT(r.tituloRecurso) as count, r.nomeArtista from "
				+ "(Select distinct tituloRecurso, nomeArtista from artista_recurso) as r "
				+ "group by nomeArtista) as sel "
				+ "WHERE sel.nomeArtista = ? ;";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(getCount);
			ps.setString(1, nomeArtista);

			rs = ps.executeQuery();

			while (rs.next()) {

				count = rs.getInt(1);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return count;
	}

	
}
