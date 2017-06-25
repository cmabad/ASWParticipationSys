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
	}
	
	
	

}