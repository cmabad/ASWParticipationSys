package es.uniovi.asw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uniovi.asw.participationSystem.dao.CategoryDao;
import es.uniovi.asw.participationSystem.dao.CommentDao;
import es.uniovi.asw.participationSystem.dao.ProposalDao;
import es.uniovi.asw.participationSystem.dao.UserDao;
import es.uniovi.asw.participationSystem.dao.VoteDao;

@SpringBootApplication
public class Main {

	//private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	//private static User currentUser = null;

	public static void main (String[] args){
		/*logUser();
		Thread kafka = new Thread() {
		    public void run() {
		        KafkaConsumer kfc = new KafkaConsumer();
		        kfc.Subscribe("votedProposal");
		        kfc.Subscribe("votedComment" );
		        kfc.Subscribe("createdProposal" );
		        kfc.Subscribe("createdComment" );
		        kfc.Subscribe("deletedProposal" );
		        kfc.Read();
		    }  
		};
		kafka.run();
		///currentUser = new User("Andrei Manu", 1679344);
		mainMenu();  */
		new CategoryDao();
		new CommentDao();
		new ProposalDao();
		new UserDao();
		new VoteDao();
	    SpringApplication.run(Main.class, args);
	    
//	    KafkaProducer.send("topic1", "data1");
//	    KafkaProducer.send("topic2", "data2");
	}
	/*
	private static User logUser(){
		new UserDao();
		try {
			while (currentUser == null) {
				System.out.println("Log with a valid user (National ID w/ no letters)");
				currentUser = UserDao.getUserByID(Integer.parseInt(console.readLine()));
			}
		} catch (IOException e) {
			currentUser = null;
		}
		
		return currentUser;
	}
	
	private static void mainMenu(){
		String option = "";
		Menu currentMenu = MainMenu.getInstance();
		
		while (!"exit".equals(option)){
			System.out.println("");
			currentMenu.showOptions();
			try{  
				option = console.readLine();
				Menu newMenu = currentMenu.chooseOption(Integer.parseInt(option), currentUser);
				currentMenu = (newMenu == null) ? currentMenu:newMenu;
			} catch (IllegalArgumentException e){
				if (!"exit".equals(option))
					System.out.println("That's not a valid option");
			} catch (Exception e){
				System.out.println("Something went wrong");
			}
		}
		
		System.out.println("Bye!");
	}
	*/
}
