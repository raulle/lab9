package it.polito.tdp.metrodeparis.model;


import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private WeightedMultigraph<Fermata,DefaultWeightedEdge> grafo;
	
	
	public List<Fermata> getFermate(){
		MetroDAO dao=new MetroDAO();
		return dao.getAllFermate();
	}
	
	public void creaGrafo() {
		
		MetroDAO dao=new MetroDAO();
		
		this.grafo = new WeightedMultigraph<Fermata,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.getFermate());
		//System.out.println(grafo);
		//System.out.println(dao.getFermateAdiacenti());
		for(Connessione cp : dao.getFermateAdiacenti()){
			DefaultWeightedEdge e =grafo.addEdge(cp.getF1(), cp.getF2()) ;
			grafo.setEdgeWeight(e, this.getTempo(cp));
		}
		for(DefaultWeightedEdge e : grafo.edgeSet())
			System.out.println(grafo.getEdgeWeight(e));


	}
	
	private double getTempo(Connessione c){
		MetroDAO dao=new MetroDAO();
		int id=dao.getIdLinea(c);
		int vel=dao.getVelocita(id);
		double x= LatLngTool.distance(c.getF1().getCoords(),c.getF2().getCoords(), LengthUnit.KILOMETER);
		return (x/vel)*60;
	}
	
	

}
