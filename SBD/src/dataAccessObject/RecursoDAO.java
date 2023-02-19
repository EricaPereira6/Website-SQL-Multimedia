package dataAccessObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Recurso;

public class RecursoDAO {

	public static Recurso buildResource(ResultSet rs) throws SQLException {

		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);
		String resumo = rs.getString(3);
		
		Blob ficheiroBlob = rs.getBlob(4);
		InputStream ficheiro = ficheiroBlob.getBinaryStream();
		Blob ilustracaoBlob = rs.getBlob(5);
		InputStream ilustracao = ilustracaoBlob.getBinaryStream();

		int faixaEtaria = rs.getInt(6);
		boolean bloqueado = rs.getBoolean(7);
		Timestamp dataPublicacao = rs.getTimestamp(8);

		Recurso recurso = new Recurso(titulo, carregadoPor, resumo, ficheiro, ilustracao, faixaEtaria, bloqueado,
				dataPublicacao);

		return recurso;
	}
	
	public static Recurso buildResourceMin(ResultSet rs) throws SQLException {

		String titulo = rs.getString(1);
		String carregadoPor = rs.getString(2);
		float classificacao = rs.getFloat(3);

		Recurso recurso = new Recurso(titulo, carregadoPor, classificacao);

		return recurso;
	}

	public static ArrayList<Recurso> selectAllRecursos() throws SQLException {

		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllUtilizador = "Select * from recurso";

		try {

			ps = connection.prepareStatement(selectAllUtilizador);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResource(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}

	public static boolean criarRecurso(String titulo, String carregadoPor, String resumo, InputStream ficheiro, InputStream ilustracao,
			int faixaEtaria, boolean bloqueado, Timestamp dataPublicacao) throws SQLException {

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;

		String insertRecurso = "INSERT INTO  recurso "
				+ "(titulo, carregadoPor, resumo, ficheiro, ilustracao, faixaEtaria, bloqueado)"
				+ "VALUES(?,?,?,?,?,?,?)";

		try {

			ps = connection.prepareStatement(insertRecurso);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);
			ps.setString(3, resumo);
			ps.setBlob(4, ficheiro);
			ps.setBlob(5, ilustracao);
			ps.setInt(6, faixaEtaria);
			ps.setBoolean(7, bloqueado);
//			ps.setTimestamp(8, dataPublicacao);

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
	
	public static Recurso getRecursoByTituloAndCarregadoPor(String titulo, String carregadoPor)
			throws SQLException {

		Recurso recurso = null; 
		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectRecursoByTituloAndFaixaEtaria = 
						"Select * from recurso " 
						+ "where titulo = ? "
						+ "and carregadoPor = ? ;";
		try {

			ps = connection.prepareStatement(selectRecursoByTituloAndFaixaEtaria);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);

			rs = ps.executeQuery();

			if(rs.next()) {

				recurso = buildResource(rs);
				if (!recurso.getBloqueado()) {
					
					recurso.setClassificacao(getClassificacaoRecurso(titulo, carregadoPor));
				}
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recurso;
	}


	public static ArrayList<Recurso> getRecursosByTituloAndFaixaEtaria(String titulo, int faixaEtaria)
			throws SQLException {

		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String likeTitulo = "%" + titulo + "%";

		String selectRecursoByTituloAndFaixaEtaria = "select r.titulo, r.carregadoPor, m.media from (" +
		
						"Select titulo, carregadoPor from recurso " 
						+ "where titulo like ? "
						+ "and bloqueado = 0 "
						+ "and recurso.faixaEtaria < ? ) as r, "
						
						+ "(SELECT tituloRecurso, utilizadorRecurso, ROUND(AVG(classificacao),1) as media "
						+ "FROM comentario GROUP BY CONCAT(tituloRecurso, ' - ', utilizadorRecurso)) as m "
						+ "where r.titulo = m.tituloRecurso "
						+ "and r.carregadoPor = m.utilizadorRecurso; ";
		try {

			ps = connection.prepareStatement(selectRecursoByTituloAndFaixaEtaria);
			ps.setString(1, likeTitulo);
			ps.setInt(2, faixaEtaria);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);
				recursos.add(r);

				// TODO ir buscar o recurso especifico
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}

	//TODO TA MAL, como eq cria um recurso a partir da tabela artistaRecurso?
	public static ArrayList<Recurso> getRecursosByArtistaAndFaixaEtaria(String nomeArtista, int faixaEtaria) {
		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		Conn Conn = new Conn();
		try {
			Conn.openSession();

			Connection connection = Conn.getConn();

			PreparedStatement ps = null;
			ResultSet rs = null;

			String likeArtista = "%" + nomeArtista + "%";

			
			// DAO de artista_recurso ??
			String selectRecursoByArtistaAndFaixaEtaria = 
							"Select * from artista_recurso as ar, recurso as r, " 
							+ "where ar.nomeArtista like ? " 
							+ "and r.faixaEtaria < ? ;";


			ps = connection.prepareStatement(selectRecursoByArtistaAndFaixaEtaria);
			ps.setString(1, likeArtista);
			ps.setInt(2, faixaEtaria);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResource(rs);
				recursos.add(r);

				// TODO ir buscar o artista especifico
			}

			connection.close();

		} catch (SQLException e) {

			System.out.println(e);
		}

		return recursos;
	}

	public static ArrayList<Recurso> getNRecursosOrderByClassificacao(int nRecursos, int faixaEtaria) throws SQLException {

		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "SELECT titulo, carregadoPor, c.media from recurso, ("
				+ "SELECT tituloRecurso, utilizadorRecurso, ROUND(AVG(classificacao),1) as media "
				+ "FROM comentario GROUP BY CONCAT(tituloRecurso, '-', utilizadorRecurso)"
				+ ") as c "
				+ "where "
				+ "titulo = c.tituloRecurso "
				+ "and bloqueado = 0 "
				+ "and recurso.faixaEtaria < ? "
				+ "order by media DESC "
				+ "limit ?;";
		

		try {

			ps = connection.prepareStatement(selectAllRecursos);
			ps.setInt(1, faixaEtaria);
			ps.setInt(2, nRecursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}

	
	public static ArrayList<Recurso> getNRecursosOrderByDataPublicacao(int nRecursos, int faixaEtaria) throws SQLException {

		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "(SELECT titulo, carregadoPor, media from recurso, ("
				+ "SELECT tituloRecurso, utilizadorRecurso, ROUND(AVG(classificacao),1) as media "
				+ "FROM comentario GROUP BY CONCAT(tituloRecurso, '-', utilizadorRecurso)"
				+ ") as c "
				+ "where "
				+ "titulo = c.tituloRecurso "
				+ "and carregadoPor = c.utilizadorRecurso "
				+ "and bloqueado = 0 "
				+ "and recurso.faixaEtaria < ? "
				+ "order by dataPublicacao DESC "
				+ "limit ?) order by media DESC"
				+ ";";

		try {

			ps = connection.prepareStatement(selectAllRecursos);
			ps.setInt(1, faixaEtaria);
			ps.setInt(2, nRecursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}
	
	public static ArrayList<Recurso> getRecursosRecomendados(int nRecursos, int faixaEtaria) throws SQLException {

		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "select res.titulo, res.carregadoPor, mm.media from "
				
				+ "(select titulo, carregadoPor from recurso "
				+ "where titulo in "
				+ "(select titulo from recurso as r, comentario as c, administrador as a "
				+ "where r.faixaEtaria < ? and r.bloqueado = 0 "
				+ "and r.titulo = c.tituloRecurso and c.utilizadorComentario = a.nomeUtilizador "
				+ "and (classificacao = 5 or classificacao = 4) "
				+ "order by c.classificacao DESC) ORDER BY RAND() limit ? ) as res, "
				
				+ "(SELECT tituloRecurso, utilizadorRecurso, ROUND(AVG(classificacao),1) as media "
				+ "FROM comentario GROUP BY CONCAT(tituloRecurso, ' - ', utilizadorRecurso)) as mm "
				+ "where res.titulo = mm.tituloRecurso "
				+ "and res.carregadoPor = mm.utilizadorRecurso;";
		

		try {

			ps = connection.prepareStatement(selectAllRecursos);
			ps.setInt(1, faixaEtaria);
			ps.setInt(2, nRecursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}
	
	public static float getClassificacaoRecurso(String titulo, String carregadoPor) throws SQLException {

		float classificacao = 0;

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "SELECT c.media from "
		        + "(SELECT tituloRecurso, utilizadorRecurso, ROUND(AVG(classificacao),1) as media "
				+ "FROM comentario GROUP BY CONCAT(tituloRecurso, '-', utilizadorRecurso)) as c "
				+ "where "
				+ "c.tituloRecurso = ? and c.utilizadorRecurso = ? ;";
		

		try {

			ps = connection.prepareStatement(selectAllRecursos);
			ps.setString(1, titulo);
			ps.setString(2, carregadoPor);

			rs = ps.executeQuery();

			while (rs.next()) {

				classificacao = rs.getFloat(1);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return classificacao;
	}
	
	public static Recurso getRecursoByTitleAndUser(String tituloRecurso, String utilizadorRecurso) throws SQLException {
		Recurso recurso = null;

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "select titulo, carregadoPor, 0 from recurso "
				+ "where titulo = ? and carregadoPor = ?;";
		

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(selectAllRecursos);
			ps.setString(1, tituloRecurso);
			ps.setString(2, utilizadorRecurso);

			rs = ps.executeQuery();

			while (rs.next()) {

				recurso = buildResourceMin(rs);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recurso;
	}
	
	public static ArrayList<Recurso> getRecursosByUserName(String user) {
		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "select r.titulo, r.carregadoPor, m.media from "
				
				+ "(select titulo, carregadoPor from recurso "
				+ "where carregadoPor = ? ) as r, "
				
				+ "(SELECT tituloRecurso, utilizadorRecurso, ROUND(AVG(classificacao),1) as media "
				+ "FROM comentario GROUP BY CONCAT(tituloRecurso, ' - ', utilizadorRecurso)) as m "
				+ "where r.titulo = m.tituloRecurso "
				+ "and r.carregadoPor = m.utilizadorRecurso;";
		
		try {

			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();
			
			ps = connection.prepareStatement(selectAllRecursos);
			ps.setString(1, user);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}
	
	public static ArrayList<Recurso> getRecursosBloqueados() {
		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "select titulo, carregadoPor, 0 from recurso "
				+ "where bloqueado = 1 ;";
		
		try {

			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();
			
			ps = connection.prepareStatement(selectAllRecursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}
	
	public static ArrayList<Recurso> getRecursosDesbloqueados() {
		ArrayList<Recurso> recursos = new ArrayList<Recurso>();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllRecursos = "select titulo, carregadoPor, 0 from recurso "
				+ "where bloqueado = 0 ;";
		
		try {

			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();
			
			ps = connection.prepareStatement(selectAllRecursos);

			rs = ps.executeQuery();

			while (rs.next()) {

				Recurso r = buildResourceMin(rs);

				recursos.add(r);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return recursos;
	}

	//TODO add tipo
		public static boolean deleteRecursoByTituloAndCarregadoPor(String titulo, String carregadoPor) throws SQLException {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			PreparedStatement ps = null;

			String selectRecursoByTituloAndFaixaEtaria = 
							"Delete from recurso " 
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

	
	public static void main(String[] args) throws SQLException, IOException {
		
		ArrayList<Recurso> recursos = getNRecursosOrderByClassificacao(10, 18);
		
		for (Recurso recurso : recursos) {
			System.out.println(recurso.getTitulo());
		}

//		ArrayList<Recurso> recursos = new ArrayList<Recurso>();
//
//		recursos = selectAllRecursos();
//
//		for (Recurso recurso : recursos) {
//
//			Blob b = null;
//
//			if (recurso.getTitulo().equals("Okavango")) {
//
//				b = recurso.getIlustracao();
//
//				byte barr[] = b.getBytes(1, (int) b.length());// 1 means first image
//
//				FileOutputStream fout = new FileOutputStream("C:\\Games\\sonoo.jpg");
//				fout.write(barr);
//
//				fout.close();
//			}
//		}
	}

	public static Boolean blockOrUnblockRecurso(String title, String publisher, boolean block) {
		

		PreparedStatement ps = null;

		String updateRecurso = "UPDATE recurso SET bloqueado = ? WHERE (titulo = ? ) and (carregadoPor = ? );";

		try {
			
			Conn Conn = new Conn();
			Conn.openSession();
			Connection connection = Conn.getConn();

			ps = connection.prepareStatement(updateRecurso);
			ps.setBoolean(1, block);
			ps.setString(2, title);
			ps.setString(3, publisher);

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

	
}
