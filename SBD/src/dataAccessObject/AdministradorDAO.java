package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Administrador;
import transactionalObjects.Administrador;

public class AdministradorDAO {
	
	public static Administrador buildAdmin(ResultSet rs) throws SQLException {

		String nomeUtilizador = rs.getString(1);

		Administrador administrador = new Administrador(nomeUtilizador);

		return administrador;
	}
	

	public static ArrayList<Administrador> getAllAdministradores() throws SQLException {

		ArrayList<Administrador> administradores = new ArrayList<Administrador>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllAdministradores = "Select * from administrador";

		try {

			ps = connection.prepareStatement(selectAllAdministradores);

			rs = ps.executeQuery();

			while (rs.next()) {

				Administrador admin = buildAdmin(rs);

				administradores.add(admin);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return administradores;
	}
	
	public static Administrador getAdministradorByNomeUtilizador(String nomeUtilizador) throws SQLException {

		Administrador administradorPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAdministradorByNomeUtilizador = "Select * from administrador where nomeUtilizador = ? ";

		try {

			ps = connection.prepareStatement(selectAdministradorByNomeUtilizador);
			ps.setString(1, nomeUtilizador);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				administradorPesquisado = buildAdmin(rs);
			}

			connection.close();

		} catch (Exception e) {

			//System.out.println(e);
			System.out.println("Não existe um administrador com o nome " + nomeUtilizador);
		}

		return administradorPesquisado;
	} 


	/*
	 * Testes
	 * Comentar/Descomentar os testes
	 */
	public static void main(String[] args) throws SQLException {

		ArrayList<Administrador> administradores = getAllAdministradores();
		
		for (Administrador admin : administradores) {
			System.out.println(admin.toString());
		}
		
		Administrador a = getAdministradorByNomeUtilizador("Andressa Canecas");
		System.out.println(a);
	}

}
