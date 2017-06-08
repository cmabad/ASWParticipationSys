package es.uniovi.asw.menus;

import java.io.IOException;

import es.uniovi.asw.dao.CategoryDao;
import es.uniovi.asw.dao.CommentDao;
import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.dao.UserDao;
import es.uniovi.asw.dao.VoteDao;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;

public class ProposalMenu extends AbstractMenu{

	private static ProposalMenu menu = null;
	
	public static ProposalMenu getInstance() {
		if (menu == null) {
			menu = new ProposalMenu();
			new ProposalDao();
			new UserDao();
			new CommentDao();
			new VoteDao();
		}
		return menu;		
	}

	public void addProposal(User user){
		try {
			String category = askForCategory();
			
			System.out.println("Please type the title of your proposal");
			
			String title = console.readLine();
			
			System.out.println("Please type the description of your proposal");
			
			String text = console.readLine(); 
					
			Proposal proposal = new Proposal(user, title, category, text);
			
			System.out.println(proposal.toString());
			int result = 0;
			// Meter en base de datos
			try {
				new ProposalDao();
				result = ProposalDao.save(proposal);
			}
			catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			
			if (result == 0)
				System.out.println("There was an error in the proposal creation.");
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void listProposals(){
		int counter = 1;
		for (Proposal prop : ProposalDao.getAllProposals()){
			System.out.println(">> " + counter + ". " + prop.toString());
			counter++;
		}
	}
	
	public void listProposalTitles(){ 
		int counter = 1;
		for (Proposal prop : ProposalDao.getAllProposals()){
			System.out.println(">> " + counter + ". " + prop.getTitle());
			counter++;
		} 
	}
	
	public void voteProposal(User currentUser){
		listProposalTitles();
		System.out.println("Please choose the proposal you would like to vote on");
		try {
			Proposal prop = ProposalDao.getAllProposals().get(Integer.parseInt(console.readLine())-1);
			System.out.println("Press 1 to vote positive and 2 to vote negative");
			String choice = console.readLine();
			if(choice.equals("1"))
				prop.AddPositive(currentUser);
			else if(choice.equals("2"))
				prop.AddNegative(currentUser);
			else
				System.out.println("Invalid choice");
			
			ProposalDao.save(prop);
		} catch (Exception e) {
			System.out.println("That's not valid");
		}
	}
	
	private ProposalMenu(){		
		this.menuOptions.add("TODO");
	}
	
	private String askForCategory() throws NumberFormatException, IOException{
		String[] categories = CategoryDao.listCategories();
		int theChosen = 0;
		while(true){
			System.out.println("\n----------\nChoose a category (type its number)\n---------");
			for (int n = 0; n < categories.length; n++)
				System.out.println(n+1 + ". " + categories[n]);
			
			theChosen = Integer.parseInt(console.readLine());
			try{
				return categories[theChosen-1];
			} catch(Exception e){
				System.out.println("That's not valid");
			}
		}
	}
}
