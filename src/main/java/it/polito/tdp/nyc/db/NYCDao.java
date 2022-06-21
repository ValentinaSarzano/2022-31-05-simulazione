package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	
	public List<String> getProviders(){
		String sql = "SELECT DISTINCT n.Provider "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "ORDER BY n.Provider ASC ";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("n.Provider"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getVertici(String provider) {
		String sql = "SELECT DISTINCT n.City AS city "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Provider = ?";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("city"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Adiacenza> getAdiacenze(String provider){
		String sql = "SELECT DISTINCT n1.City AS city1, n2.City AS city2, AVG(n1.Latitude) AS lat1, AVG(n1.Longitude) AS long1, AVG(n2.Latitude) AS lat2, AVG(n2.Longitude) AS long2 "
				+ "FROM nyc_wifi_hotspot_locations n1, nyc_wifi_hotspot_locations n2 "
				+ "WHERE n1.City < n2.City "
				+ "AND n1.Provider = n2.Provider AND n1.Provider = ? "
				+ "GROUP BY n1.City, n2.City";
		List<Adiacenza> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, provider);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				LatLng posizione1 = new LatLng(res.getDouble("lat1"), res.getDouble("long1"));
				LatLng posizione2 = new LatLng(res.getDouble("lat2"), res.getDouble("long2"));
				double peso = LatLngTool.distance(posizione1, posizione2, LengthUnit.KILOMETER);
				result.add(new Adiacenza(res.getString("city1"), res.getString("city2"), peso));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
}
