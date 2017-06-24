package es.uniovi.asw.citizensLoader.letters;

import java.io.IOException;

import es.uniovi.asw.model.User;

public interface LetterGenerator {
	
	void generateLetter(User user) throws IOException;
}