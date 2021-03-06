package hello;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;
import es.uniovi.asw.participationSystem.dao.CommentDao;
import es.uniovi.asw.participationSystem.dao.ProposalDao;
import es.uniovi.asw.participationSystem.dao.UserDao;
import es.uniovi.asw.participationSystem.dao.VoteDao;
public class TestPls {
	private static User currUser;
	private Proposal proposal = new Proposal(currUser, "Testing", "TestCategory", "TestText");
	
	@Before
	public void Setup() {
		new CommentDao();
		new ProposalDao();
		new VoteDao();
		new UserDao();
		currUser = UserDao.getUserByID(1679344);
	}
	
	
	@Test
	public void TestInsertProposal() {
		System.out.println(PropReader.get("minimumVotesNumber"));
		Proposal prop = new Proposal(currUser, "Testing", "TestCategory", "TestText");
		ProposalDao.save(prop);
		List<Proposal> list = ProposalDao.GetProposalByUser(currUser.getId());
		assert(list.get(list.size()-1).getTitle().equals(prop.getTitle()));
		assert(list.get(list.size()-1).getCategory().equals(prop.getCategory()));
		assert(list.get(list.size()-1).getText().equals(prop.getText()));
		assert(list.get(list.size()-1).getUser().getId() == prop.getUser().getId());
	}
	
	@Test
	public void TestProposalList() {
		int count1 = ProposalDao.GetProposalByUser(currUser.getId()).size();
		Proposal prop = new Proposal(currUser, "TestingList", "TestCategoryList", "TestTextList");
		ProposalDao.save(prop);
		int count2 = ProposalDao.GetProposalByUser(currUser.getId()).size();
		assert(count1 < count2);
	}

	
	
	@Test
	public void TestComment() {
		
		Comment comment = new Comment(currUser, proposal, "newComment");
		CommentDao.save(comment);
		
		assertTrue(comment.toString().contains("newComment"));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void TestCommentIllegal() {
		String notAllowed[] = PropReader.get("notAllowedWords").split(",");
		
		
		Comment comment = new Comment(currUser, proposal, notAllowed[0]);
		CommentDao.save(comment);
	}
	
	@Test
	public void TestPositiveVote() {
		List<Proposal> props = ProposalDao.GetProposalByUser(currUser.getId());
		Proposal prop = props.get(props.size()-1);
		int posVotes1 = prop.getPositiveVotes().size();
		prop.AddPositive(currUser);
		ProposalDao.save(prop);
		props = ProposalDao.GetProposalByUser(currUser.getId());
		prop = props.get(props.size()-1);
		int posVotes2 = prop.getPositiveVotes().size();
		assert(posVotes1 < posVotes2);
	}
	
	@Test
	public void TestNegativeVote() {
		List<Proposal> props = ProposalDao.GetProposalByUser(currUser.getId());
		Proposal prop = props.get(props.size()-1);
		int posVotes1 = prop.getNegativeVotes().size();
		prop.AddNegative(currUser);
		ProposalDao.save(prop);
		props = ProposalDao.GetProposalByUser(currUser.getId());
		prop = props.get(props.size()-1);
		int posVotes2 = prop.getNegativeVotes().size();
		assert(posVotes1 < posVotes2);
		
	}
}
