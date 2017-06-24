package citizensLoader;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.junit.Test;

import es.uniovi.asw.citizensLoader.letters.PDFLetter;
import es.uniovi.asw.model.User;

public class PDFLetterTest {

	@Test
    public void PDFLetterCorrect() throws IOException {    
        PDFLetter letter = new PDFLetter();
        User user = new User();
        user.setEmail("pdfLetter@uniovi.es");
        user.setPassword("0123");
        
        letter.generateLetter(user);
         
        String expected = "User:"+"pdfLetter@uniovi.es"+"\tPassword:"+"0123";
        String real = getText("PersonalLetters/pdfLetter@uniovi.es.pdf");
        Assert.assertTrue (real.trim().equals(expected.trim()));
        System.out.println(real);
        System.out.println(expected);
    }
    
    static String getText(String filename) throws IOException {
    	File pdfFile = new File(filename);
        PDDocument doc = PDDocument.load(pdfFile);
        return new PDFTextStripper().getText(doc);
    }
}