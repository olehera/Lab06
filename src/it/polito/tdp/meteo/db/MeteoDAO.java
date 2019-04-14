package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {
	
	public List<Citta> getAllLocalita() {
		
		final String sql = "SELECT DISTINCT localita FROM situazione";
		
		List<Citta> localita = new ArrayList<Citta>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				localita.add(new Citta(rs.getString("Localita")));
			}

			conn.close();
			return localita;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE Data LIKE ? AND Localita = ? ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			if (mese < 10)
				st.setString(1, "2013-0"+mese+"-%");
			else
				st.setString(1, "2013-"+mese+"-%");
			
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {
		
		double media = 0.0;
		final String sql = "SELECT AVG(umidita) AS media FROM situazione WHERE DATA LIKE ? AND localita = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			if (mese < 10)
				st.setString(1, "2013-0"+mese+"-%");
			else
				st.setString(1, "2013-"+mese+"-%");
			
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				media = rs.getDouble("media");
			}

			conn.close();
			return media;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
