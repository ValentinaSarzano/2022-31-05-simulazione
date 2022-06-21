package it.polito.tdp.nyc.model;

public class Adiacenza {
	
	private String City1;
	private String City2;
	private double peso;
	
	public Adiacenza(String city1, String city2, double peso) {
		super();
		City1 = city1;
		City2 = city2;
		this.peso = peso;
	}

	public String getCity1() {
		return City1;
	}

	public void setCity1(String city1) {
		City1 = city1;
	}

	public String getCity2() {
		return City2;
	}

	public void setCity2(String city2) {
		City2 = city2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	

}
