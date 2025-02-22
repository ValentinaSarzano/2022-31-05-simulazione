package it.polito.tdp.nyc.model;

import com.javadocmd.simplelatlng.LatLng;

public class City {
	
	private String nome;
	private LatLng posizione;
	private int numHotspot;
	
	public City(String nome, LatLng posizione, int numHotspot) {
		super();
		this.nome = nome;
		this.posizione = posizione;
		this.numHotspot = numHotspot;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LatLng getPosizione() {
		return posizione;
	}

	public void setPosizione(LatLng posizione) {
		this.posizione = posizione;
	}

	public int getNumHotspot() {
		return numHotspot;
	}

	public void setNumHotspot(int numHotspot) {
		this.numHotspot = numHotspot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	

}
