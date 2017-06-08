package es.uniovi.asw.filters;

import java.util.Collections;
import java.util.List;

import comparator.RatioComparator;
import es.uniovi.asw.model.filtrable.Filtrable;

public class Ratio implements Filter{

	@Override
	public List<Filtrable> filter(List<Filtrable> listOfFiltrables) {
		Collections.sort(listOfFiltrables, new RatioComparator());
		return null;
	}

}
