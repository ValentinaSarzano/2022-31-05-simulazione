package it.polito.tdp.nyc.model;

public class Vicino {
	private String quartiere;
	private Double distanza;
	
	public Vicino(String quartiere, Double distanza) {
		super();
		this.quartiere = quartiere;
		this.distanza = distanza;
	}

	public String getQuartiere() {
		return quartiere;
	}

	public void setQuartiere(String quartiere) {
		this.quartiere = quartiere;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}
	

}
