package es.uniovi.asw;

import java.util.List;

import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.Removable;

public class Util {

	public int delete (Proposal proposal){
		return proposal.delete();
	}
	public int delete (Comment comment){
		return comment.delete();
	}
	public int delete (List<Removable> listRemovables){
		int correctDeletion = 1;
		for (Removable rem : listRemovables)
			if (rem.delete() == 0)
				correctDeletion = 0;
		return correctDeletion;
	}
	
	public int add(Proposal proposal){
		// TODO: ¿?
		return 0;
	}
	public int add (Comment comment){
		// TODO: ¿?
		return 0;
	}
//	public int add (List<Addables> listAddables){
//		 TODO: ¿?
//		return 0;
//	}
	
	
}
