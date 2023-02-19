package dataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.Conn;
import transactionalObjects.Nacionalidade;

public class NacionalidadeDAO {
	
	public static Nacionalidade buildNationality(ResultSet rs) throws SQLException {
		
		int codigo = rs.getInt(1);
		String nacionalidade = rs.getString(2);
	
		Nacionalidade nacionalidade1= new Nacionalidade(codigo, nacionalidade);

		return nacionalidade1;
	}

	public static ArrayList<Nacionalidade> selectAllNacionalidade() throws SQLException {

		ArrayList<Nacionalidade> nacionalidades = new ArrayList<Nacionalidade>();

		Conn Conn = new Conn();
		Conn.openSession();
		Connection connection = Conn.getConn();

		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectAllNacionalidade = "Select * from nacionalidade";

		try {

			ps = connection.prepareStatement(selectAllNacionalidade);

			rs = ps.executeQuery();

			while (rs.next()) {

				Nacionalidade n = buildNationality(rs);

				nacionalidades.add(n);
			}

			connection.close();

		} catch (Exception e) {

			System.out.println(e);
		}

		return nacionalidades;
		
	}

}
