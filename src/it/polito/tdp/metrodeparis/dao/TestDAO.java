package it.polito.tdp.metrodeparis.dao;

public class TestDAO {

	public static void main(String[] args) {
		
		MetroDAO metroDAO = new MetroDAO();
		
		//System.out.println("Lista fermate");
		//List<Fermata> fermate = metroDAO.getAllFermate();
		//System.out.println(fermate);
		
		//List<Connessione> connessioni = metroDAO.getFermateAdiacenti();
		//System.out.println(connessioni);
		
		metroDAO.getFermateAdiacenti();		
	}

}
