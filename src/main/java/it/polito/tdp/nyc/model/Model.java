package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> vertici;
	
	
	public Model() {
		super();
		this.dao = new NYCDao();
	}


	public List<String> getProviders(){
		return this.dao.getProviders();
	}
	
	public void creaGrafo(String provider) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		//Aggiungo i vertici
		this.vertici = new ArrayList<>(this.dao.getVertici(provider)); 
		Graphs.addAllVertices(this.grafo, vertici);
		
		//Aggiungo gli archi
		for(Adiacenza a: this.dao.getAdiacenze(provider)) {
			if(this.grafo.containsVertex(a.getCity1()) && this.grafo.containsVertex(a.getCity2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getCity1(), a.getCity2(), a.getPeso());
			}
		}

		 System.out.println("Grafo creato!");
		 System.out.println("#VERTICI: "+ this.grafo.vertexSet().size());
		 System.out.println("#ARCHI: "+ this.grafo.edgeSet().size());
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public boolean grafoCreato() {
		if(this.grafo== null)
			return false;
		else 
			return true;
	}
	
	public List<String> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public List<Vicino> getAdiacenti(String quartiere){
		List<Vicino> adiacenti = new ArrayList<>();
		for(String q: Graphs.neighborListOf(this.grafo, quartiere)) {
			adiacenti.add(new Vicino(q, this.grafo.getEdgeWeight(this.grafo.getEdge(q, quartiere))));
		}
		Collections.sort(adiacenti, new Comparator<Vicino>() {

			@Override
			public int compare(Vicino o1, Vicino o2) {
				return o1.getDistanza().compareTo(o2.getDistanza());
			}
			
		});
		
		return adiacenti;
	}
}
