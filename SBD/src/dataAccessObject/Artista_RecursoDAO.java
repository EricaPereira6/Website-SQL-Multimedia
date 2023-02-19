package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Artista_Recurso;

public class Artista_RecursoDAO {
	
	public static Artista_Recurso buildArtistResource(ResultSet rs) throws SQLException{
		String nomeArtista = rs.getString(1); 
		String profissao= rs.getString(2);
		String tituloRecurso = rs.getString(3);
		String utilizadorRecurso = rs.getString(4);
		
		Artista_Recurso artista_recurso = new Artista_Recurso(nomeArtista, profissao, tituloRecurso, utilizadorRecurso);
		
		return artista_recurso;
	}
	
	public static boolean criarArtista_Recurso(String nomeArtista, String profissao, String tituloRecurso,String utilizadorRecurso) throws SQLException {

		Artista_Recurso ar = new Artista_Recurso(nomeArtista, profissao, tituloRecurso, utilizadorRecurso);

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertArtista_Recurso = "INSERT INTO  artista_recurso "
				+ "(nomeArtista, profissao, tituloRecurso, utilizadorRecurso)"
				+ "VALUES(?,?,?,?)";

		try {

			ps = connection.prepareStatement(insertArtista_Recurso);
			ps.setString(1, nomeArtista);
			ps.setString(2, profissao);
			ps.setString(3, tituloRecurso);
			ps.setString(4, utilizadorRecurso);
			

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
	public static ArrayList<Artista_Recurso> getAllArtista_Recursos() throws SQLException {

		ArrayList<Artista_Recurso> artista_recursos = new ArrayList<Artista_Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllArtista_Recursos = "Select * from artista_recurso";

		try {

			ps = connection.prepareStatement(selectAllArtista_Recursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Artista_Recurso artista_recurso = buildArtistResource(rs);

				artista_recursos.add(artista_recurso);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return artista_recursos;
	}
	
	public static ArrayList<Artista_Recurso> getArtista_RecursosByAge(int faixaEtaria) throws SQLException {

		ArrayList<Artista_Recurso> artista_recursos = new ArrayList<Artista_Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllArtista_Recursos = "Select ar.* "
				+ "from artista_recurso as ar, recurso as r "
				+ "where "
				+ "r.carregadoPor = ar.utilizadorRecurso "
				+ "and r.titulo = ar.tituloRecurso "
				+ "and r.faixaEtaria < ? "
				+ "and r.bloqueado = 0 order by ar.nomeArtista;";

		try {

			ps = connection.prepareStatement(selectAllArtista_Recursos);
			ps.setInt(1, faixaEtaria);
			rs = ps.executeQuery();

			while (rs.next()) {

				Artista_Recurso artista_recurso = buildArtistResource(rs);

				artista_recursos.add(artista_recurso);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return artista_recursos;
	}
	

	public static Artista_Recurso getArtista_RecursoByTituloRecurso(String tituloRecurso) throws SQLException {

		Artista_Recurso Artista_RecursoPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectArtista_RecursoByTituloRecurso = "Select * from Artista_Recurso where tituloRecurso = ? ";

		try {

			ps = connection.prepareStatement(selectArtista_RecursoByTituloRecurso);
			ps.setString(1, tituloRecurso);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				Artista_RecursoPesquisado = buildArtistResource(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return Artista_RecursoPesquisado;
	} 

	public static Artista_Recurso getArtista_RecursoByAll(String nomeArtista, String profissao, String tituloRecurso, String utilizadorRecurso) throws SQLException {
		

		Artista_Recurso Artista_RecursoPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectArtista_RecursoByProfissao = "Select * from Artista_Recurso "
				+ "where nomeArtista = ? "
				+ "and profissao = ? "
				+ "and tituloRecurso = ? "
				+ "and utilizadorRecurso = ? ";

		try {

			ps = connection.prepareStatement(selectArtista_RecursoByProfissao);
			ps.setString(1, nomeArtista);
			ps.setString(2, profissao);
			ps.setString(3, tituloRecurso);
			ps.setString(4, utilizadorRecurso);
			
			rs = ps.executeQuery();

			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				Artista_RecursoPesquisado = buildArtistResource(rs);
			}

			connection.close();

		} catch (Exception e) {

			throw new SQLException(e);
		}

		return Artista_RecursoPesquisado;
	} 
	
	public static Artista_Recurso getArtista_RecursoByProfissao(String profissao) throws SQLException {

		Artista_Recurso Artista_RecursoPesquisado = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectArtista_RecursoByProfissao = "Select * from Artista_Recurso where profissao = ? ";

		try {

			ps = connection.prepareStatement(selectArtista_RecursoByProfissao);
			ps.setString(1, profissao);
			rs = ps.executeQuery();

			//por cada resultado (cada Administrador devolvido)
			while (rs.next()) {

				//cria um Administrador a partir do recebido da db
				Artista_RecursoPesquisado = buildArtistResource(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return Artista_RecursoPesquisado;
	} 
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Artista_Recurso> artista_recursos = getAllArtista_Recursos();
		
		for (Artista_Recurso art_rec : artista_recursos) {
			System.out.println(art_rec.toString());
		}

	}

	public static ArrayList<String> getProfissaoByArtista(String nomeArtista) {
		ArrayList<String> profissoes = new ArrayList<String>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectProfissaoByArtista = "select distinct profissao from artista_recurso where nomeArtista = ? ";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectProfissaoByArtista);
			ps.setString(1, nomeArtista);
			rs = ps.executeQuery();

			while (rs.next()) {

				String profissao = rs.getString(1); 

				profissoes.add(profissao);
			}

			connection.close();

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return profissoes;
	}

}
