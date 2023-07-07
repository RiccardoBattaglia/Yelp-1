package it.polito.tdp.yelp.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
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
	
}
