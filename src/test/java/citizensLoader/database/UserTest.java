package citizensLoader.database;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import es.uniovi.asw.dao.UserDao;
import es.uniovi.asw.model.User;

public class UserTest {
	
	UserDao userdao = new UserDao();

	@Test
	public void saveUserTest() throws ParseException {
		
		User newUser = new User("katia", "fernandez", "katia@uniovi.es", "27-06-1995", "oviedo", 
			 "España", 7190060);
		
		userdao.saveUser(newUser);
		
		User lookForNewUser = userdao.getUserByEmail("nuevousuario@uniovi.es");
		
		assertEquals(newUser, lookForNewUser);
	}
	
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
	}
	
	
	

}