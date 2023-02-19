package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Criador;

public class CriadorDAO {

	public static Criador buildCriator(ResultSet rs) throws SQLException {

		String nomeUtilizador = rs.getString(1);
		String palavraPasse = rs.getString(2);
		boolean bloqueado = rs.getBoolean(3);
		float pontosReputacao = rs.getFloat(4);

		Criador Criador = new Criador(nomeUtilizador, palavraPasse, bloqueado, pontosReputacao);

		return Criador;
	}
	

	public static ArrayList<Criador> getAllCriadores() throws SQLException {

		ArrayList<Criador> criadores = new ArrayList<Criador>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllCriadores = "Select * from criador";

		try {

			ps = connection.prepareStatement(selectAllCriadores);

			rs = ps.executeQuery();

			while (rs.next()) {

				Criador criador = buildCriator(rs);

				criadores.add(criador);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return criadores;
	}

	
	public static Criador getCriadorByNomeUtilizador(String nomeUtilizador) throws SQLException {
		
		updatePontosRepucacao();

		Criador criadorPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectCriadorByNomeUtilizador = "Select * from criador where nomeUtilizador = ? ";

		try {

			ps = connection.prepareStatement(selectCriadorByNomeUtilizador);
			ps.setString(1, nomeUtilizador);
			rs = ps.executeQuery();

			//por cada resultado (cada Criador devolvido)
			while (rs.next()) {

				//cria um Criador a partir do recebido da db
				criadorPesquisado = buildCriator(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return criadorPesquisado;
	} 


	/*
	 * Tipo 3 alínea 3 - done
	 */
	public static boolean blockOrUnblockCriadorByNomeUtilizador(String nomeUtilizador, boolean isBlocked) throws SQLException {

		//Ligação à BD
		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		//Query - fazer no workbench e a mesma coisa
		String selectCriadorByNomeUtilizador = "Update criador set bloqueado = ? where nomeUtilizador = ? ";

		try {

			//preencher os ? com as variaveis
			ps = connection.prepareStatement(selectCriadorByNomeUtilizador);
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
	
	public static boolean updatePontosRepucacao() {
		
		ArrayList<Criador> criadores = new ArrayList<Criador>();
		Criador criador = null;
		
		//Ligação à BD
				

		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;

		// Query - fazer no workbench e a mesma coisa
		String selectCriadorByNomeUtilizador = "Select * from criador";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectCriadorByNomeUtilizador);
			rs = ps.executeQuery();

			// por cada resultado (cada Criador devolvido)
			while (rs.next()) {

				// cria um Criador a partir do recebido da db
				criador = buildCriator(rs);
				criadores.add(criador);
			}

			for (Criador c : criadores) {

				String updateCriadorByNomeUtilizador = "UPDATE `isel_share`.`criador` SET `pontosReputacao` = "
						+ "(Select sum(classificacao) from comentario where utilizadorRecurso = ? ) " + " WHERE "
						+ " (`nomeUtilizador` = ? );";

				// preencher os ? com as variaveis
				ps2 = connection.prepareStatement(updateCriadorByNomeUtilizador);
				ps2.setString(1, c.getNomeUtilizador());
				ps2.setString(2, c.getNomeUtilizador());

				// resultado pode ser o numero de tabelas/modificações (int) ou tabela
				// (ResultSet)
				int result = ps2.executeUpdate();

				// update realizado com sucesso
				if (!(result > 0)) {

					return false;
				}

			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return true;		
	}


	/*
	 * Testes
	 * Comentar/Descomentar os testes
	 */
	public static void main(String[] args) throws SQLException {

		String nomeUtilizador = "Andressa Canecas";

		boolean a = blockOrUnblockCriadorByNomeUtilizador(nomeUtilizador, false);
				
		System.out.println(a);

		//		Criador cPesquisado = selectCriadorByNomeUtilizador(nomeUtilizador);
		//		System.out.println(cPesquisado);
		//		
		//		ArrayList<Criador> cs = selectAllCriador();
		//
		//		for (Criador c : cs) {
		//
		//			System.out.println(c.toString());
		//		}
	}
}
