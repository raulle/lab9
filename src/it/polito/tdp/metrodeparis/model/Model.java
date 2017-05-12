package it.polito.tdp.metrodeparis.model;


import java.util.ArrayList;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private DirectedWeightedMultigraph<Fermata,DefaultWeightedEdge> grafo;
	private DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra ;
	
	
	public List<Fermata> getFermate(){
		MetroDAO dao=new MetroDAO();
		return dao.getAllFermate();
	}
	
	public void creaGrafo() {
		
		MetroDAO dao=new MetroDAO();
		
		this.grafo = new DirectedWeightedMultigraph<Fermata,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.getFermate());
		//System.out.println(grafo);
		//System.out.println(dao.getFermateAdiacenti());
		for(Connessione cp : dao.getFermateAdiacenti()){
			DefaultWeightedEdge e =grafo.addEdge(cp.getF1(), cp.getF2()) ;
			grafo.setEdgeWeight(e, this.getTempo(cp));
		}
		//for(DefaultWeightedEdge e : grafo.edgeSet())
		//	System.out.println(grafo.getEdgeWeight(e));

	}
	
	private double getTempo(Connessione c){
		MetroDAO dao=new MetroDAO();
		int id=dao.getIdLinea(c);
		int vel=dao.getVelocita(id);
		double x= LatLngTool.distance(c.getF1().getCoords(),c.getF2().getCoords(), LengthUnit.KILOMETER);
		return (x/vel)*60;
	}
	
	public double getTempoStimato(Fermata f1, Fermata f2){
		if(grafo==null)
			this.creaGrafo();
		dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, f1,f2);
		return dijkstra.getPathLength();
		//for(DefaultWeightedEdge e : grafo.edgeSet()){
		//	Fermata f1 = grafo.getEdgeSource(e);
		//	Fermata f2 = grafo.getEdgeTarget(e);
		//	double f= grafo.getEdgeWeight(e);
		//	dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, f1,f2);
		//	double d=dijkstra.getPathLength();
		//	System.out.println(f1.getNome()+ " - "+f2.getNome()+" - "+"peso: "+f+" minimo: "+d);
		//	return;
		}
	
	public List<Fermata> getPercorsoArrivo(Fermata f1, Fermata f2){
		if(grafo==null)
			this.creaGrafo();
		List<Fermata> fermateA= new ArrayList<Fermata>();
		dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, f1,f2);
		for(DefaultWeightedEdge e : dijkstra.getPathEdgeList())
			fermateA.add(grafo.getEdgeTarget(e));
		return fermateA;
	}
	
	public List<Fermata> getPercorsoPartenza(Fermata f1, Fermata f2){
		if(grafo==null)
			this.creaGrafo();
		List<Fermata> fermateP= new ArrayList<Fermata>();
		dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(grafo, f1,f2);
		for(DefaultWeightedEdge e : dijkstra.getPathEdgeList())
			fermateP.add(grafo.getEdgeSource(e));
		return fermateP;
	}
		
}
	


