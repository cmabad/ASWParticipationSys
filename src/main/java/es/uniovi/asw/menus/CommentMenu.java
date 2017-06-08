package es.uniovi.asw.menus;

import es.uniovi.asw.dao.CommentDao;
import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.dao.VoteDao;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;
import es.uniovi.asw.model.filtrable.Filtrable;

public class CommentMenu extends AbstractMenu{

	private static CommentMenu menu = null;
	
	public static CommentMenu getInstance(){
		if (menu == null)
			menu = new CommentMenu();
		return menu;
	}
	
	public void voteComment(User currentUser){
		System.out.println("Please choose a proposal");
		
		try {
			int proposal = Integer.parseInt(console.readLine());
			
			System.out.println("Choose the comment you want to vote");
			Proposal propos = ProposalDao.getAllProposals().get(proposal-1);
			
			new CommentDao();
			propos.setComments(CommentDao.getCommentsOf(propos));
			
			showCommentsOf(propos);
			
			int comment = Integer.parseInt(console.readLine());
			
			Comment comm = (Comment) propos.getComments().get(comment-1);
			
			System.out.println("Press 1 to vote positive and 2 to vote negative");
			String choice = console.readLine();
			if("1".equals(choice))
				comm.AddPositive(currentUser);
			else if(("2").equals(choice))
				comm.AddNegative(currentUser);
			
			VoteDao.SaveVotes(comm);
			
		} catch (Exception e) {
			e.printStackTrace();		
			}
		
		System.out.println("TODO: votar un comentario positivo o negativo");
	}
	 
	private void showCommentsOf(Proposal proposal) {
		int counter = 1;
		for (Filtrable comment : proposal.getComments()){
			System.out.println(counter + ". " +((Comment) comment).getText());
			counter++;
		}
	}

	private CommentMenu(){  
		this.menuOptions.add("TODO");
	}

	public void commentProposal(User currentUser) {
		try {
			System.out.println("Please choose which proposal to comment");

			Proposal propos = ProposalDao.getAllProposals().get(Integer.parseInt(console.readLine())-1);
			
			System.out.println("You've chosen " + propos.getTitle());
			System.out.println("Write your comment:");

			Comment comment = new Comment(currentUser, propos, console.readLine());
			
			propos.addComment(comment);
			
			System.out.println(propos.toString());
			//System.out.println(propos.getComments().get(0).toString());
			
			CommentDao.save(comment);
			
		} catch (Exception e){
			System.out.println("That's not valid");
		}
	}
}
