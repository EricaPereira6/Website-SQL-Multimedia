package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Tipo_Utilizador;

public class Tipo_UtilizadorDAO {

	public static Tipo_Utilizador buildTypeResource(ResultSet rs) throws SQLException{
		String nomeUtilizador = rs.getString(1);
		int tipo = rs.getInt(2);
		
		Tipo_Utilizador TipoUtilizador = new Tipo_Utilizador(nomeUtilizador, tipo);
		
		return TipoUtilizador;
	}
	
	public static boolean criarTipoUtilizador(String nomeUtilizador, int tipo) throws SQLException {

		Tipo_Utilizador tu = new Tipo_Utilizador(nomeUtilizador, tipo);

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertTipoUtilizador = "INSERT INTO  tipo_utilizador "
				+ "(nomeUtilizador, tipo)"
				+ "VALUES(?,?)";

		try {

			ps = connection.prepareStatement(insertTipoUtilizador);
			ps.setString(1, nomeUtilizador);
			ps.setInt(2, tipo);
			

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
	public static ArrayList<Tipo_Utilizador> getAllTipoUtilizadores() throws SQLException {

		ArrayList<Tipo_Utilizador> TipoUtilizadores = new ArrayList<Tipo_Utilizador>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllTipoUtilizadores = "Select * from tipo_utilizador";

		try {

			ps = connection.prepareStatement(selectAllTipoUtilizadores);

			rs = ps.executeQuery();

			while (rs.next()) {

				Tipo_Utilizador TipoUtilizador = buildTypeResource(rs);

				TipoUtilizadores.add(TipoUtilizador);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return TipoUtilizadores;
	}
	

	public static Tipo_Utilizador getTipoUtilizadorByName(String nomeUtilizador) throws SQLException {

		Tipo_Utilizador TipoUtilizadorPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectTipoUtilizadorByName = "Select * from tipo_utilizador where nomeUtilizador = ?;";

		try {

			ps = connection.prepareStatement(selectTipoUtilizadorByName);
			ps.setString(1, nomeUtilizador);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				TipoUtilizadorPesquisado = buildTypeResource(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return TipoUtilizadorPesquisado;
	} 
}
