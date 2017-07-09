package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Mostra;

public class ArtsmiaDAO {

	public List<ArtObject> listObject() {
		
		String sql = "SELECT * from objects";

		List<ArtObject> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAllYears() {
		
		String sql = "SELECT DISTINCT begin FROM exhibitions ORDER BY begin ASC";

		List<Integer> result = new ArrayList<Integer>();

		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				result.add(new Integer(res.getInt("begin")));
				
			}

			res.close();
			st.close();
			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public List<Mostra> getMostreDaAnno(Integer year) {
		
		final String sql1 = "SELECT * FROM exhibitions WHERE begin>=?";
		final String sql2 = "SELECT COUNT(object_id) AS n_objects FROM exhibition_objects WHERE exhibition_id=?";

		List<Mostra> result = new ArrayList<Mostra>();

		try {
			
			Connection conn = DBConnect.getConnection();
			
			PreparedStatement st1 = conn.prepareStatement(sql1);

			st1.setInt(1, year);
			
			ResultSet res = st1.executeQuery();

			while (res.next()) {
				
				Mostra mostra = new Mostra(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), res.getInt("begin"), res.getInt("end"), 0);
				
				PreparedStatement st2 = conn.prepareStatement(sql2);

				st2.setInt(1, mostra.getId());
				
				ResultSet res2 = st2.executeQuery();
				
				res2.next();
				
				mostra.setnObjects(res2.getInt("n_objects"));
				
				result.add(mostra);
				
				res2.close();
				st2.close();
				
			}

			res.close();
			st1.close();
			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
}
