package comparator;

import java.util.Comparator;

import es.uniovi.asw.model.filtrable.Filtrable;

public class PopularityComparator implements Comparator<Filtrable> {

	@Override
	public int compare(Filtrable o1, Filtrable o2) {
		return o1.compareByVotes(o2);
	}
	
}
