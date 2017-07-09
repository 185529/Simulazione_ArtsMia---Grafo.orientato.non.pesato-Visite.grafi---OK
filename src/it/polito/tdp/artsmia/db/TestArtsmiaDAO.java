package it.polito.tdp.artsmia.db;

import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Mostra;

public class TestArtsmiaDAO {

	public static void main(String[] args) {
		
		ArtsmiaDAO dao = new ArtsmiaDAO() ;
		
		List<ArtObject> objects = dao.listObject() ;
		System.out.println(objects.size());
		
		List<Mostra> mostre = dao.getMostreDaAnno(2011);
		
		for(Mostra m : mostre){
			System.out.println(m.toString());
		}

	}

}
