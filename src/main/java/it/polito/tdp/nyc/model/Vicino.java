package it.polito.tdp.nyc.model;

public class Vicino {
	private City quartiere;
	private Double distanza;
	
	public Vicino(City quartiere, Double distanza) {
		super();
		this.quartiere = quartiere;
		this.distanza = distanza;
	}

	public City getQuartiere() {
		return quartiere;
	}

	public void setQuartiere(City quartiere) {
		this.quartiere = quartiere;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}
	
	

}
