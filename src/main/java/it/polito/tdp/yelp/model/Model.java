package it.polito.tdp.yelp.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;
import org.jgrapht.alg.util.Pair;

public class Model {
	
	private YelpDao dao;
	private Graph<Business, DefaultWeightedEdge> grafo;
	private Map<String, Business> businessMap;
	private List<Business> listaB;
	
	public Model() {
		this.businessMap=new HashMap<>();
		this.dao = new YelpDao();
		this.listaB = this.dao.getAllBusiness();
		for(Business b : listaB) {
			this.businessMap.put(b.getBusinessId(), b);
		}
	}
	
	public List<String> getAllCity(){
		List<String> result = new LinkedList<>();
		for(String b : this.businessMap.keySet()) {
			result.add(this.businessMap.get(b).getCity());
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	public List<String> listaSenzaDuplicati(List<String> lista){
		List<String> result = new LinkedList<>();
		for(String i : lista) {
			if(!result.contains(i)) {
				result.add(i);
			}
		}
		return result;
	}
	
public void creaGrafo(String city) {
		
		this.grafo = new SimpleWeightedGraph<Business, DefaultWeightedEdge>(DefaultWeightedEdge.class);
			
		// Aggiunta VERTICI 
		List<Business> vertici = new LinkedList<>();
		
		for(String i : this.businessMap.keySet()) {
			if(this.businessMap.get(i).getCity().equals(city)) {
				vertici.add(this.businessMap.get(i));
			}
		}
		
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		// Aggiunta ARCHI
		for (Business v1 : vertici) {
			for (Business v2 : vertici) {
				if(!v1.equals(v2)) {
			this.grafo.addEdge(v1,v2);
			this.grafo.setEdgeWeight(this.grafo.getEdge(v1, v2), this.dao.getPeso(v1, v2));
			System.out.println(""+v1.getBusinessName()+" <-> "+v2.getBusinessName()+" - Distanza: "+this.grafo.getEdgeWeight(this.grafo.getEdge(v1, v2)));

				}
		}
		}
		
		
		
		}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> stampaPesi() {
		
		List<String> pesi = new LinkedList<>();
		Set<Business> vertici = this.grafo.vertexSet();
		for (Business v1 : vertici) {
			for (Business v2 : vertici) {
				pesi.add(""+v1.getCity()+" <-> "+v2.getCity()+" - Distanza: "+this.grafo.getEdgeWeight(this.grafo.getEdge(v1, v2)));
			}}	
		
		return pesi;
	}
		
	
	public List<String> getNameVertici(){
		
		List<String> name=new LinkedList<>();
		
		for(Business i : this.grafo.vertexSet()) {
			name.add(i.getBusinessName());
		}
		
		return name;
	}
	
	public String tutteDistanze(String c){
		

		Pair<String, Double> coppia = new Pair<String, Double>("",0.0);
		List<Pair<String, Double>> lista = new LinkedList<>();
		String ris = "";
		
		for(DefaultWeightedEdge b : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeSource(b).getBusinessName().equals(c)) {
				lista.add(new Pair<String, Double>(this.grafo.getEdgeTarget(b).getBusinessName(), this.grafo.getEdgeWeight(b)));
				
			}
		}
		
		double peso1=0.0;
		double peso2=0.0;
		
		for(Pair i : lista) {
			peso1 = Double.parseDouble(i.getSecond().toString());
			peso2 = coppia.getSecond();
			if(peso1>peso2) {
				coppia=i;
			}
		}
		
		ris=("Locale più distante: \n"+coppia.getFirst().toString()+" - Distanza: " + coppia.getSecond());
		
		return ris;
		
		
	}
	
	
}
