package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Connessione;
import it.polito.tdp.metrodeparis.model.Fermata;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}


	public List<Connessione> getFermateAdiacenti() {
		final String sql = "SELECT f1.id_fermata as id_fermata1, f1.nome as nome1, f1.coordX as coordX1, f1.coordY as coordY1, "+
			 "f2.id_fermata as id_fermata2, f2.nome as nome2, f2.coordX as coordX2, f2.coordY as coordY2,connessione.id_connessione "+
	         "FROM 	connessione, fermata f1, fermata f2	"+ 
	         "WHERE connessione.id_stazP = f1.id_fermata "+
	         "AND connessione.id_stazA = f2.id_fermata";
	
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
						
			ResultSet rs = st.executeQuery() ;
			
			List<Connessione> list = new ArrayList<>() ;
			
			while(rs.next()) {
				Fermata f1 = new Fermata(rs.getInt("id_Fermata1"), rs.getString("nome1"), new LatLng(rs.getDouble("coordx1"), rs.getDouble("coordy1")));
				Fermata f2 = new Fermata(rs.getInt("id_Fermata2"), rs.getString("nome2"), new LatLng(rs.getDouble("coordx2"), rs.getDouble("coordy2")));
				Connessione c = new Connessione(f1,f2,rs.getInt("id_connessione"));
				list.add(c);
				//System.out.println(c);
				//System.out.println("Fermata partenza: "+rs.getInt("id_Fermata1")+rs.getString("nome1")+"\n"+"Fermata arrivo: "+rs.getInt("id_Fermata2")+rs.getString("nome2")+"\n\n");
			}
			
			rs.close();
			conn.close();
			
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public int getIdLinea(Connessione c) {
		final String sql = "SELECT id_linea FROM connessione WHERE id_connessione=?";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, c.getIdConnessione());		
			ResultSet rs = st.executeQuery() ;
			int idLinea=0;	
			
			if(rs.next()) 
				idLinea=rs.getInt("id_linea");
			
			rs.close();
			conn.close();
			
			return idLinea;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getVelocita(int idLinea) {
		final String sql = "SELECT velocita FROM linea WHERE id_linea=?";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idLinea);		
			ResultSet rs = st.executeQuery() ;
			int velocita=0;	
			
			if(rs.next()) 
				velocita=rs.getInt("velocita");
			
			rs.close();
			conn.close();
			
			return velocita;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}