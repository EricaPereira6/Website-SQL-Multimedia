package dataAccessObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Convidado;

public class ConvidadoDAO {
	
	public static Convidado buildGuest(ResultSet rs) throws SQLException {

		String nomeUtilizador = rs.getString(1);
		boolean bloqueado = rs.getBoolean(2);

		Convidado convidado = new Convidado(nomeUtilizador, bloqueado);

		return convidado;
	}
	

	public static ArrayList<Convidado> getAllConvidados() throws SQLException {

		ArrayList<Convidado> convidados = new ArrayList<Convidado>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllConvidados = "Select * from convidado";

		try {

			ps = connection.prepareStatement(selectAllConvidados);

			rs = ps.executeQuery();

			while (rs.next()) {

				Convidado convidado = buildGuest(rs);

				convidados.add(convidado);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return convidados;
	}

	
	public static Convidado getConvidadoByNomeUtilizador(String nomeUtilizador) throws SQLException {

		Convidado convidadoPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectConvidadoByNomeUtilizador = "Select * from convidado where nomeUtilizador = ? ";

		try {

			ps = connection.prepareStatement(selectConvidadoByNomeUtilizador);
			ps.setString(1, nomeUtilizador);
			rs = ps.executeQuery();

			//por cada resultado (cada convidado devolvido)
			while (rs.next()) {

				//cria um convidado a partir do recebido da db
				convidadoPesquisado = buildGuest(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return convidadoPesquisado;
	} 


	/*
	 * Tipo 3 alínea 3 - done
	 */
	public static boolean blockOrUnblockConvidadoByNomeUtilizador(String nomeUtilizador, boolean isBlocked) throws SQLException {

		//Ligação à BD
		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		//Query - fazer no workbench e a mesma coisa
		String selectConvidadoByNomeUtilizador = "Update convidado set bloqueado = ? where nomeUtilizador = ? ";

		try {

			//preencher os ? com as variaveis
			ps = connection.prepareStatement(selectConvidadoByNomeUtilizador);
			ps.setBoolean(1, isBlocked);
			ps.setString(2, nomeUtilizador);

			//resultado pode ser o numero de tabelas/modificações (int) ou tabela (ResultSet)
			int result = ps.executeUpdate();

			//update realizado com sucesso
			if(result > 0) {

				return true;
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return false;
	} 


	/*
	 * Testes
	 * Comentar/Descomentar os testes
	 */
	public static void main(String[] args) throws SQLException {

		String nomeUtilizador = "Teresa Godinho";

		boolean a = blockOrUnblockConvidadoByNomeUtilizador(nomeUtilizador, false);
				
		System.out.println(a);

		//		Convidado cPesquisado = selectConvidadoByNomeUtilizador(nomeUtilizador);
		//		System.out.println(cPesquisado);
		//		
		//		ArrayList<Convidado> cs = selectAllConvidado();
		//
		//		for (Convidado c : cs) {
		//
		//			System.out.println(c.toString());
		//		}
	}

}
