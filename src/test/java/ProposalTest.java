import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;

public class ProposalTest {
	

	private ProposalDao proposalDao = new ProposalDao();
	
	User user = new User("maria", "fernandez", "maria@uniovi.es", "27-06-1995", "Oviedo", "Español", 11111);

//	@SuppressWarnings("static-access")
//	@Test
//	public void getAllProposalsTest() {
//		
//		List<Proposal> proposals = proposalDao.getAllProposals();
//		
//		for(Proposal p : proposals) {
//			assertNotNull(proposalDao.GetProposalByID(p.getId()));
//		}
//	}
	
//	@Test
//	public void saveProposalTest() {
//		Proposal proposal = new Proposal(user, "title", "category", "test");
//		
//		try {
//			proposalDao.save(proposal); 
//		} catch(Exception e) {
//			
//		}
//		
//		List<Proposal> proposals = proposalDao.getAllProposals();
//		Proposal created = proposals.get(proposals.size()-1);
//		
//		assertEquals(proposal.getTitle(), created.getTitle());		
//	}
}
