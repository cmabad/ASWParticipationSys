package es.uniovi.asw.filters;

import java.util.ArrayList;
import java.util.List;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.filtrable.Filtrable;

public class Category implements Filter{
	private String category;
	public Category(String category) {
		this.category = category;
	}
	
	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltables) {
		List<Filtrable> ret = new ArrayList<Filtrable>();
		for(Filtrable f : listOfFiltables) {
			if(!(f instanceof Proposal)) continue;
			if(((Proposal)f).getCategory().equalsIgnoreCase(category)) ret.add(f);
		}
		return ret;
		
	}

}
