package citizensLoader.database;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;
import es.uniovi.asw.participationSystem.dao.ProposalDao;

public class SaveProposalTest {
	

	private ProposalDao proposalDao;

	@Before
	public void setUp() throws Exception {
		proposalDao = new ProposalDao();
	}
	
	@Test
	public void test() throws ParseException {
		assertNotNull(proposalDao);
		
		User user = new User("pepe", "fernandez", "pepe@uniovi.es", "27-06-1995", "Aviles", "Español", 987654);
		Proposal proposal = new Proposal(10, user, "proposalTitle", "proposalCategory", "proposalText", "26-06-2017");
		
		proposalDao.save(proposal);
		assertEquals("proposalTitle", proposal.getTitle());
	}

}
