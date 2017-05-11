package it.polito.tdp.metrodeparis.model;

public class Connessione {
	
	private Fermata f1;
	private Fermata f2;
	private int idConnessione;
	
	public Connessione(Fermata f1, Fermata f2, int idConnessione) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.idConnessione=idConnessione;
	}

	public Fermata getF1() {
		return f1;
	}

	public void setF1(Fermata f1) {
		this.f1 = f1;
	}

	public Fermata getF2() {
		return f2;
	}

	public void setF2(Fermata f2) {
		this.f2 = f2;
	}
	
	public int getIdConnessione() {
		return idConnessione;
	}

	public void setIdConnessione(int idConnessione) {
		this.idConnessione = idConnessione;
	}

	@Override
	public String toString() {
		return f1.getNome()+" - "+f2.getNome()+" - "+idConnessione;
	}
	
	

}
