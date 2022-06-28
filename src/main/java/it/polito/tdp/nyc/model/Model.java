package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private NYCDao dao;
	private Graph<City, DefaultWeightedEdge> grafo;
	private List<City> vertici;
	private Map<String, City> idMap;
	
	private Simulatore sim;
	
	public Model() {
		super();
		this.dao = new NYCDao();
	}


	public List<String> getProviders(){
		return this.dao.getProviders();
	}
	
	public void creaGrafo(String provider) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap = new HashMap<>();
		
		this.dao.getVertici(provider, idMap);
		
		this.vertici= new ArrayList<>(idMap.values());
		
		//Aggiungo i vertici 
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		//Aggiungo gli archi
		for(Adiacenza a: this.dao.getAdiacenze(provider, idMap)) {
			if(this.grafo.containsVertex(a.getC1()) && this.grafo.containsVertex(a.getC2())) {
				Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2(), a.getPeso());
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
	
	public List<City> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public List<Vicino> getAdiacenti(City quartiere){
		List<Vicino> adiacenti = new ArrayList<>();
		for(City c: Graphs.neighborListOf(this.grafo, quartiere)) {
			adiacenti.add(new Vicino(c, this.grafo.getEdgeWeight(this.grafo.getEdge(c, quartiere))));
		}
		Collections.sort(adiacenti, new Comparator<Vicino>() {

			@Override
			public int compare(Vicino o1, Vicino o2) {
				return o1.getDistanza().compareTo(o2.getDistanza());
			}
			
		});
		
		return adiacenti;
	}
	
	public void doSimula(int N, City partenza) {
		sim = new Simulatore(this.grafo, this.vertici);
		sim.init(N, partenza);
		sim.run();
		
	}
	
	public int getDurataTot() {
		return sim.getDurataTot();
	}

	public List<Integer> getRevisionati() {
		return sim.getRevisionati();
	}
}
