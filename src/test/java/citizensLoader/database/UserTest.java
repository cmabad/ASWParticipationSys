package citizensLoader.database;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

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
	
	@Test
	public void getUserByIdTest() throws ParseException {

		User user = new User("pepe", "fernandez", "pepe@uniovi.es", "27-06-1995", "Oviedo", "Español", 987654);

		user.setPassword("01234");
		user.setGender(true);
		
		User found = userdao.getUserByID(987654);
		assertEquals(user, found);
	}
}