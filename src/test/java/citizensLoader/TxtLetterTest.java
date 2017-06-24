package citizensLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import es.uniovi.asw.citizensLoader.letters.TxtLetter;
import es.uniovi.asw.model.User;

public class TxtLetterTest {

	@Test
    public void txtLetterCorrect() throws IOException {    
        TxtLetter letter = new TxtLetter();
        User user = new User();
        user.setEmail("txtletter@uniovi.es");
        user.setPassword("0123");
        
        letter.generateLetter(user);
        
        String expected ="User:"+"txtletter@uniovi.es"+"\tPassword:"+"0123";
        String real = getText("PersonalLetters/txtletter@uniovi.es.txt");
        Assert.assertEquals(expected, real);
    }
    
	private String getText(String filename) throws IOException {
		BufferedReader file;
		file = new BufferedReader(new FileReader(filename));
		String result = file.readLine();
		file.close();
		return result;	
	}
}