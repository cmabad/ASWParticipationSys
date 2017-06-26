package citizensLoader.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.dao.CommentDao;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;

public class SaveCommentTest {

	private CommentDao commentDao;

	@Before
	public void setUp() throws Exception {
		commentDao = new CommentDao();
	}
	
	@Test
	public void saveCommentTest() throws ParseException {
		assertNotNull(commentDao);
		
		User user = new User("pepe", "fernandez", "pepe@uniovi.es", "27-06-1995", "Aviles", "Español", 987654);
		Proposal proposal = new Proposal(10, user, "proposalTitle", "proposalCategory", "proposalText", "26-06-2017");
		Comment comment = new Comment(user, proposal, "comentario");
		
		commentDao.save(comment);
		assertEquals("comentario", comment.getText());		
	}

}
