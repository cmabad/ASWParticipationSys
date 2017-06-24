package es.uniovi.asw.citizensLoader.letters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import es.uniovi.asw.model.User;


public class TxtLetter implements LetterGenerator{
	
	/**
	 * Writes a txt letter for each user with the credentials to access the system
	 */
	public void generateLetter(User user) throws IOException {
		BufferedWriter file;

		String fileName = "PersonalLetters/"+user.getEmail()+".txt";
		file = new BufferedWriter(new FileWriter(fileName));

		String message ="User:"+user.getEmail()+"\tPassword:"+user.getPassword();
		file.write(message);
		file.close();
	}

}