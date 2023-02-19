package dataAccessObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Utilizador;

public class UtilizadorDAO {

	public static Utilizador buildUser(ResultSet rs) throws SQLException {

		String nomeUtilizador = rs.getString(1);
		String email = rs.getString(2);
		Date dataNascimento = rs.getDate(3);
		String nacionalidade = rs.getString(4);

		Utilizador utilizador = new Utilizador(nomeUtilizador, email, dataNascimento, nacionalidade);

		return utilizador;
	}
	

	public static ArrayList<Utilizador> getAllUtilizadores() throws SQLException {

		ArrayList<Utilizador> utilizadores = new ArrayList<Utilizador>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllUtilizador = "SELECT "
				+ "     u.nomeUtilizador, u.email, u.dataNascimento, n.nacionalidade "
				+ "FROM "
				+ "     utilizador as u "
				+ "INNER JOIN nacionalidade as n ON u.nacionalidade = n.codigo;";

		try {

			ps = connection.prepareStatement(selectAllUtilizador);

			rs = ps.executeQuery();

			while (rs.next()) {

				Utilizador utilizador = buildUser(rs);

				utilizadores.add(utilizador);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return utilizadores;
	}

	
	public static Utilizador getUtilizadorByNomeUtilizador(String nomeUtilizador) throws SQLException {

		Utilizador utilizadorPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectUtilizadorByNomeUtilizador = "Select * from utilizador where nomeUtilizador = ? ";

		try {

			ps = connection.prepareStatement(selectUtilizadorByNomeUtilizador);
			ps.setString(1, nomeUtilizador);
			rs = ps.executeQuery();

			//por cada resultado (cada utilizador devolvido)
			while (rs.next()) {

				//cria um utilizador a partir do recebido da db
				utilizadorPesquisado = buildUser(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return utilizadorPesquisado;
	} 


	/*
	 * Testes
	 * Comentar/Descomentar os testes
	 */
	public static void main(String[] args) throws SQLException {

		
	}
}
