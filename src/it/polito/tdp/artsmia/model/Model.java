package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private List<Mostra> mostreDaAnno;
	
	private DirectedGraph<Mostra, DefaultEdge> graph;

	public List<Integer> loadYears() {

		List<Integer> years = new ArrayList<Integer>();
		ArtsmiaDAO dao = new ArtsmiaDAO();
		
		years = dao.getAllYears();
		
		return years;
	}
	
	private List<Mostra> getMostreDaAnno(Integer year){
		
		ArtsmiaDAO dao = new ArtsmiaDAO();
	
		return dao.getMostreDaAnno(year);
		
	}

	public void creaGrafo(Integer year) {

		this.graph = new SimpleDirectedGraph<Mostra, DefaultEdge>(DefaultEdge.class);
		
		// creo vertici
		
		this.mostreDaAnno = getMostreDaAnno(year);
		
		Graphs.addAllVertices(this.graph, this.mostreDaAnno);
		
		// aggiungo archi
		
		for(Mostra m1 : graph.vertexSet()){
			for(Mostra m2 : graph.vertexSet()){
				if(!m1.equals(m2) && m1.getBegin()<=m2.getBegin() && m2.getBegin()<=m1.getEnd()){
					Graphs.addEdgeWithVertices(this.graph, m1, m2);
					System.out.println("ADDED EDGE : "+m1.toString()+" -> "+m2.toString());
				}
			}
		}
				
	}

	public Mostra getmostraMigliore() {
		
		Mostra best = null;
		Integer max = Integer.MIN_VALUE;
		
		for(Mostra m : mostreDaAnno){
			
			if(m.getnObjects()>max){
				best = m;
				max = m.getnObjects();
			}
			
		}
		
		return best;
		
	}

	public boolean isStronglyConnected() {
		
		List<Mostra> visited = new ArrayList<Mostra>();
		BreadthFirstIterator<Mostra, DefaultEdge> bfv = new BreadthFirstIterator<Mostra, DefaultEdge>(graph, this.mostreDaAnno.get(0));
		
		while(bfv.hasNext()){
			visited.add(bfv.next());
		}
		
		if(visited.size()==this.graph.vertexSet().size())
			return true;
		else
			return false;
		
	}

}
