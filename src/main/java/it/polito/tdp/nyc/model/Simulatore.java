package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;


public class Simulatore {

	//Parametri in ingresso
	private int N; //num tecnici
	private City cityPartenza;
	private Graph<City, DefaultWeightedEdge> grafo;
	private List<City> vertci;
	
	//Parametri in uscita
	private int durataTot;
	private List<Integer> revisionati;
	
	//Modello del mondo
    private City cityCorrente; //Per recuperare il num hotspot
    private int daRevisionare;
    private List<City> daVisitare;
    private int tecniciOccupati;
	
	//PriorityQueue
	private PriorityQueue<Event> queue;

	public Simulatore(Graph<City, DefaultWeightedEdge> grafo, List<City> vertci) {
		super();
		this.grafo = grafo;
		this.vertci = vertci;
	}

	public void setN(int n) {
		N = n;
	}

	public void setCityPartenza(City cityPartenza) {
		this.cityPartenza = cityPartenza;
	}

	public int getDurataTot() {
		return durataTot;
	}

	public List<Integer> getRevisionati() {
		return revisionati;
	}
	
	public void init(int N, City partenza) {
		this.N = N;
		this.cityPartenza = partenza;
		
		this.durataTot = 0;
		this.revisionati = new ArrayList<>();
		//La squadra di n tecnici inizia dalla citta di partenza
		for(int i=0; i < N; i++) {
			revisionati.add(0); 
		}
		
		this.cityCorrente = cityPartenza;
		this.daVisitare = new ArrayList<>(this.vertci);
		this.daVisitare.remove(this.cityCorrente);
		this.daRevisionare = cityPartenza.getNumHotspot();
		this.tecniciOccupati = 0;
		
		this.queue = new PriorityQueue<>();
		
		//Ogni tecnico revisiona inizialmente un solo hotspot
		int i=0;
		while(this.tecniciOccupati < this.N && this.daRevisionare > 0) {
			Event e = new Event(0, EventType.INIZIO_HS, i);	
		    this.queue.add(e);
			daRevisionare--;
			tecniciOccupati++;
			i++;
		}
			
	}
	
	public void run() {
		while(!this.queue.isEmpty()) { //Estraggo il primo evento e lo processo, studiandone le possibili conseguenze
			Event e = this.queue.poll();
			this.durataTot = e.getTime();
			processEvent(e);
		}
	}
	
	
	
	private void processEvent(Event e) {
		int time = e.getTime();
		int tecnico = e.getTecnico();
		EventType type = e.getType();
		
		switch(type) {
		case INIZIO_HS:
			this.revisionati.set(tecnico, this.revisionati.get(tecnico)+1);
			
			if(Math.random() < 0.1) { //Nel 10% dei casi 
				//Procedura richiede durata aggiuntiva di 15 min (tot = 10+15)
		        this.queue.add(new Event(time+25, EventType.FINE_HS, tecnico));
			}else {
				this.queue.add(new Event(time+10, EventType.FINE_HS, tecnico));
			}
			break;
			
		case FINE_HS:
			
			this.tecniciOccupati--;
			
			//Se ci sono ancora hotspot da revisionare nello stesso quartiere
			if(this.daRevisionare > 0) {
				int random = (int) ((Math.random()*11)+10);
				this.daRevisionare--;
				this.tecniciOccupati++;
				this.queue.add(new Event(time+random, EventType.INIZIO_HS, tecnico));
				
			}else if(this.daVisitare.size() > 0) {//se abbiamo finito gli hotspot da revisionare, dobbiamo spostarci nella prossima citta
				//Tutti cambiano quartiere
				City prossima = getPiuVicina(this.cityCorrente);
				this.daVisitare.remove(prossima);
				int tempoDiSpostamento = (int) (this.grafo.getEdgeWeight(this.grafo.getEdge(cityCorrente, prossima))/ 50.0 * 60.0);
				this.cityCorrente = prossima;
				this.daRevisionare = this.cityCorrente.getNumHotspot();

			    queue.add(new Event(time+tempoDiSpostamento, EventType.NEXT_HS, -1));
			   
			 }else if(this.tecniciOccupati > 0) {
				 //non si fa nulla
			 }
	
			break;
			
		case NEXT_HS:
			
			int i = 0;
			while(this.tecniciOccupati < this.N && this.daRevisionare > 0) {
				this.queue.add(new Event(time, EventType.INIZIO_HS, i));
				this.daRevisionare--;
				this.tecniciOccupati++;
				i++;
			}
			break;
		}
			
	}

	private City getPiuVicina(City cityCorrente) {
		//City piu vicina = quella collegata alla city corrente con peso minimo
		City piuVicina = null;
		double pesoMin = 100000.0;
		for(City c: Graphs.neighborListOf(this.grafo, cityCorrente)) {
			if(this.grafo.getEdgeWeight(this.grafo.getEdge(c, cityCorrente)) < pesoMin) {
				pesoMin = this.grafo.getEdgeWeight(this.grafo.getEdge(c, cityCorrente));
				piuVicina = c;
			}
		}
		
		return piuVicina;
	}

	
	
	
}

	