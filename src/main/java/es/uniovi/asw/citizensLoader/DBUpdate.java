package es.uniovi.asw.citizensLoader;

import java.io.IOException;
import java.util.ArrayList;

import es.uniovi.asw.citizensLoader.parser.ReadList;
import es.uniovi.asw.model.User;
import es.uniovi.asw.participationSystem.dao.UserDao;


public class DBUpdate {
	
	public void main(String... args) {
		final DBUpdate runner = new DBUpdate();
		runner.insert(args);
	}

	
	private void insert(String... args) {
		System.out.println("#########\nCitizens Loader \n#########\n");
		
		ArrayList<User> users = new ArrayList<User>();
		ReadList reader = new ReadList();
		UserDao userdao = new UserDao();

		for (String file : args){
			users.clear();
			try {
				users = reader.readList(file);
				for(User us : users)  {
					userdao.saveUser(us);
					System.out.println("Saved " + us.toString());
				}
				System.out.println("Loaded " + users.size() + " users from " + file + "\n");
				
			}catch (IOException e){
				System.out.printf("\n[ERROR] The file %s could not be loaded\n\n", file);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}