package citizensLoader.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.dao.UserDao;
import es.uniovi.asw.model.User;

public class UserTest {
	
	UserDao userdao = new UserDao();


	@Test
	public void getUserByEmailTest() throws ParseException {

		
		int dniUser = 123456;
		
		User userByEmail = userdao.getUserByEmail("nuevousuario@uniovi.es");
		int dniFound = userByEmail.getId();
		
		assertEquals(dniFound, dniUser);
		
		int dniUser2 = 7190060;
		
		User userByEmail2 = userdao.getUserByEmail("katia@uniovi.es");
		int dniFound2 = userByEmail2.getId();
		
		assertEquals(dniFound2, dniUser2);
		
		int dniUser3 = 987654;
		
		User userByEmail3 = userdao.getUserByEmail("pepe@uniovi.es");
		int dniFound3 = userByEmail3.getId();
		
		assertEquals(dniFound3, dniUser3);
	}
	
	
	private User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void test() throws ParseException {
		assertNotNull(user);

		user.setName("userName");
		assertEquals("userName", user.getName());


		user.setId(111222);
		assertEquals(111222, user.getId());

		user.setEmail("email");
		assertEquals("email", user.getEmail());


		user.setDOB("27-06-1995");
		assertEquals("27-06-1995", user.getDOB());

		assertEquals("User [id=111222, name=userName, email=email, gender=Female]", user.toString());
		
	}
}