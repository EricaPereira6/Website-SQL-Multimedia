package dataAccessObject;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;

import transactionalObjects.Fotografia;

public class FotografiaDAO {
	
	public static Fotografia buildPhoto(ResultSet rs) throws SQLException {
		
		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);
		
		Fotografia fotografia = new Fotografia(titulo, carregadoPor);

		return fotografia;
	}
	
	public static ArrayList<Fotografia> selectAllFotografia() throws SQLException {

		ArrayList<Fotografia> fotografias = new ArrayList<Fotografia>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecurso = "Select * from fotografia";

		try {

			ps = connection.prepareStatement(selectAllRecurso);

			rs = ps.executeQuery();

			while (rs.next()) {

				Fotografia f = buildPhoto(rs);

				fotografias.add(f);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return fotografias;
	}
	
	
	public static boolean criarFotografia(String titulo, String carregadoPor) throws SQLException {


		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;

		String insertFotografia = "INSERT INTO  fotografia "
				+ "(titulo, carregadoPor)"
				+ "VALUES(?,?)";

		try {

			ps = connection.prepareStatement(insertFotografia);
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
	
		//TODO add tipo
		public static boolean deleteFotografiaByTituloAndCarregadoPor(String titulo, String carregadoPor) throws SQLException {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			PreparedStatement ps = null;

			String selectRecursoByTituloAndFaixaEtaria = 
							"Delete from fotografia " 
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
		
		public static Fotografia selectFotografiaByTitleAndUser(String titulo, String utilizador) throws SQLException {
			Fotografia fotografia = null;

			PreparedStatement ps = null;
			ResultSet rs = null;

			String selectAllfotografias = "Select * from fotografia where titulo = ? and carregadoPor = ?";

			try {
				
				Conn Conn = new Conn();
				Conn.openSession();
				Connection connection = Conn.getConn();

				ps = connection.prepareStatement(selectAllfotografias);
				ps.setString(1, titulo);
				ps.setString(2, utilizador);

				rs = ps.executeQuery();

				while (rs.next()) {

					fotografia = buildPhoto(rs);
				}

				connection.close();

			} catch (Exception e) {

				System.out.println(e);
			}

			return fotografia;
		}
}
