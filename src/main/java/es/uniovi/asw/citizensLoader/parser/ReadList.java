package es.uniovi.asw.citizensLoader.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import es.uniovi.asw.citizensLoader.letters.LetterGenerator;
import es.uniovi.asw.citizensLoader.letters.PDFLetter;
import es.uniovi.asw.citizensLoader.letters.PasswordGenerator;
import es.uniovi.asw.citizensLoader.letters.TxtLetter;
import es.uniovi.asw.model.User;

public class ReadList {
	
	private LetterGenerator letterGenerator = new TxtLetter();
	private LetterGenerator letterGenerator2 = new PDFLetter();
	
	public ReadList()
	{
		
	}

	/**
	 * Reads the users from the given filem saves them in the database and generates a 
	 * txt and pdf letter with the credentials to access the system
	 * 
	 * @param filename
	 * @return arrayList of users 
	 * @throws IOException
	 * @throws ParseException 
	 */
	public ArrayList<User> readList(String filename) throws IOException, ParseException {

		ArrayList<User> users = new ArrayList<User>();

		FileInputStream file;
		int rows;

		file = new FileInputStream(new File(filename));
		
		 XSSFWorkbook hwb = new XSSFWorkbook(file);
		 XSSFSheet sheet = hwb.getSheetAt(0);
		 
		 rows = sheet.getPhysicalNumberOfRows();
		 
		 for(int i= 0; i<rows; i++){
			 Row row = sheet.getRow(i);
			 User us = userFromRow(row);
			 if(us != null)
				 users.add(us);	
		 }
	 
		 for(User user : users){
			 letterGenerator.generateLetter(user);
			 letterGenerator2.generateLetter(user);
		 }
		 
		 hwb.close();
		 file.close();
		 
		 return users;
	}

	/**
	 * generates a new User from the excel data file
	 * 
	 * @param excel row from which the person data is obtained
	 * @return new user
	 */
	private User userFromRow(Row row){

		
		User us = null;
		
		if (row != null) {			
			String name = row.getCell(0).getStringCellValue();
			String LName = row.getCell(1).getStringCellValue();
			String email = row.getCell(2).getStringCellValue();
			String DOB = row.getCell(3).toString();
			String address = row.getCell(4).getStringCellValue();
			String nationality = row.getCell(5).getStringCellValue();
			int id = (int) row.getCell(6).getNumericCellValue();

			us = new User(name,LName, email, DOB, address, nationality, id);
		
		us.setPassword(PasswordGenerator.generateRandomPassword());
		}
		return us;
	}		
}