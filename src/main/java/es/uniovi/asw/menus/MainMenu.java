package es.uniovi.asw.menus;

import es.uniovi.asw.PropReader;
import es.uniovi.asw.model.User;

public class MainMenu extends AbstractMenu{

	private static MainMenu menu = null;
	public static MainMenu getInstance(){
		if (null == menu)
			menu = new MainMenu();
		return menu;
	}   
	
	@Override
	public Menu chooseOption(int option, User currentUser){
		if (option == 1){
			ProposalMenu.getInstance().addProposal(currentUser);
			return getInstance();
		}
		if (option == 2){
			ProposalMenu.getInstance().listProposals(); 
			return getInstance();
		}
		if (option == 3){
			ProposalMenu.getInstance().listProposalTitles();
			CommentMenu.getInstance().commentProposal(currentUser);
			return getInstance();
		}
		if (option == 4){
			ProposalMenu.getInstance().voteProposal(currentUser);
			return getInstance();
		}
		if (option == 5){
			ProposalMenu.getInstance().listProposalTitles();
			CommentMenu.getInstance().voteComment(currentUser);
			return getInstance();
		}
		throw new IllegalArgumentException();
	}
	
	private MainMenu(){
		this.menuOptions.add(PropReader.get("mainMenuOption1"));
		this.menuOptions.add(PropReader.get("mainMenuOption2"));
		this.menuOptions.add(PropReader.get("mainMenuOption3"));
		this.menuOptions.add(PropReader.get("mainMenuOption4"));
		this.menuOptions.add(PropReader.get("mainMenuOption5"));
	}
}
