package es.uniovi.asw.citizensLoader.letters;

import java.io.IOException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import es.uniovi.asw.model.User;

public class PDFLetter implements LetterGenerator{

	/**
	 * Writes a pdf letter for each user with the credentials to access the system
	 */
	public void generateLetter(User user) throws IOException {
        PdfWriter writer = new PdfWriter("PersonalLetters/"+user.getEmail()+".pdf");
 
        PdfDocument pdf = new PdfDocument(writer);
 
        Document document = new Document(pdf);
        document.add(new Paragraph("User:" + user.getEmail()+ "\tPassword:"+user.getPassword()));
        document.close();
	}
}