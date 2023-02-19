package dataAccessObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import controller.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import transactionalObjects.Comentario;
import transactionalObjects.Criador;



public class ComentarioDAO {
	
	public static Comentario buildComment(ResultSet rs) throws SQLException { 
		
		String utilizadorComentario = rs.getString(1);
		String tituloRecurso = rs.getString(2);
		String utilizadorRecurso = rs.getString(3);
		int classificacao = rs.getInt(4);
		String conteudo = rs.getString(5);
		Timestamp dataComentario = rs.getTimestamp(6);
		
		Comentario comentario = new Comentario(utilizadorComentario, tituloRecurso, utilizadorRecurso, 
				classificacao, conteudo, dataComentario);

		return comentario;
	}
	
	public static ArrayList<Comentario> getAllComentarios() throws SQLException {

		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllComentarios = "Select * from comentario";

		try {

			ps = connection.prepareStatement(selectAllComentarios);

			rs = ps.executeQuery();

			while (rs.next()) {

				Comentario comentario = buildComment(rs);

				comentarios.add(comentario);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return comentarios;
	}
	
	public static Comentario getComentariobyUserAndRecurso(String utilizadorComentario, 
			String tituloRecurso, String utilizadorRecurso) throws SQLException {

		Comentario comentarioEscrito = null;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectComentarioByConteudo = "Select * from comentario "
				+ "where utilizadorComentario = ? "
				+ "and tituloRecurso = ? "
				+ "and utilizadorRecurso = ?";

		try {

			ps = connection.prepareStatement(selectComentarioByConteudo);
			ps.setString(1, utilizadorComentario);
			ps.setString(2, tituloRecurso);
			ps.setString(3, utilizadorRecurso);
			rs = ps.executeQuery();

			//por cada resultado (cada Criador devolvido)
			while (rs.next()) {

				//cria um Criador a partir do recebido da db
				comentarioEscrito = buildComment(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return comentarioEscrito;
	} 
	
	public static boolean criarComentario(String utilizadorComentario, String tituloRecurso,
			String utilizadorRecurso,int classificacao, String conteudo) throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertComentario = "INSERT INTO  comentario "
				+ "(utilizadorComentario, tituloRecurso, utilizadorRecurso, classificacao, conteudo)"
				+ "VALUES(?,?,?,?,?)";

		try {

			ps = connection.prepareStatement(insertComentario);
			ps.setString(1, utilizadorComentario);
			ps.setString(2, tituloRecurso);
			ps.setString(3, utilizadorRecurso);
			ps.setInt(4, classificacao);
			ps.setString(5, conteudo);
			

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
	
	public static boolean criarComentario(String utilizadorComentario, String tituloRecurso,
			String utilizadorRecurso,int classificacao) throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String insertComentario = "INSERT INTO  comentario "
				+ "(utilizadorComentario, tituloRecurso, utilizadorRecurso, classificacao)"
				+ "VALUES(?,?,?,?)";

		try {

			ps = connection.prepareStatement(insertComentario);
			ps.setString(1, utilizadorComentario);
			ps.setString(2, tituloRecurso);
			ps.setString(3, utilizadorRecurso);
			ps.setInt(4, classificacao);
			

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
	
	public static boolean hasUserComment(String utilizadorComentario, String tituloRecurso, String utilizadorRecurso) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectComentarioByConteudo = "Select * from comentario "
				+ "where utilizadorComentario = ? "
				+ "and tituloRecurso = ? "
				+ "and utilizadorRecurso = ?";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectComentarioByConteudo);
			ps.setString(1, utilizadorComentario);
			ps.setString(2, tituloRecurso);
			ps.setString(3, utilizadorRecurso);
			
			rs = ps.executeQuery();

			//por cada resultado (cada Criador devolvido)
			while (rs.next()) {

				connection.close();
				
				return true;
			}

		} catch (Exception e) {

			System.out.println(e);
		}
		return false;
	}
	
	public static boolean mudarClassificacao(String utilizadorComentario, String tituloRecurso,
			String utilizadorRecurso,int classificacao, String conteudo) throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String updateClassificacao = "UPDATE comentario SET classificacao = ?, conteudo = ?, dataComentario = CURRENT_TIMESTAMP " + 
								" WHERE (utilizadorComentario = ? ) " + 
								"and (tituloRecurso = ? ) " + 
								"and (utilizadorRecurso = ? ); ";

		try {

			ps = connection.prepareStatement(updateClassificacao);
			ps.setInt(1, classificacao);
			ps.setString(2, conteudo);
			ps.setString(3, utilizadorComentario);
			ps.setString(4, tituloRecurso);
			ps.setString(5, utilizadorRecurso);
			
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



	public static ArrayList<Comentario> getComentariosbyRecurso(String tituloRecurso, String utilizadorRecurso) {
		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectComentarioByConteudo = "Select * from comentario "
				+ "where tituloRecurso = ? "
				+ "and utilizadorRecurso = ?";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectComentarioByConteudo);
			ps.setString(1, tituloRecurso);
			ps.setString(2, utilizadorRecurso);
			rs = ps.executeQuery();

			//por cada resultado (cada Criador devolvido)
			while (rs.next()) {

				//cria um Criador a partir do recebido da db
				Comentario comentario = buildComment(rs);
				comentarios.add(comentario);
				
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return comentarios;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
