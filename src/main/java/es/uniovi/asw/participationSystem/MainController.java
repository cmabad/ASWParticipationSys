package es.uniovi.asw.participationSystem;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uniovi.asw.Message;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;
import es.uniovi.asw.participationSystem.dao.CommentDao;
import es.uniovi.asw.participationSystem.dao.ProposalDao;
import es.uniovi.asw.participationSystem.dao.UserDao;
import es.uniovi.asw.participationSystem.dao.VoteDao;
import es.uniovi.asw.participationSystem.kafka.KafkaProducer;
@Controller
public class MainController {
	private User loggedUser;
    //@Autowired
    //private KafkaProducer kafkaProducer;
    private List<Proposal> proposals;
    
    /*
     * ejecutarlo en los html, con bucle 
     * <tr th:each="p : ${allProposals}">
     * p.getTitle()...
     */
    @ModelAttribute("allProposals")
    public List<Proposal> getAllProposals(){
    	new ProposalDao();
   		proposals = ProposalDao.getAllProposals();
    	return proposals;
    }
    
    @ModelAttribute("Comment")
    public Comment getComment() {
    	return new Comment();
    }
    
    @ModelAttribute("Proposal")
    public Proposal getProposal() {
    	return new Proposal();
    }
    @RequestMapping("/")
    public ModelAndView landing(Model model) {
    	//model.addAttribute("message", new Message());
    	ModelAndView model2 = new ModelAndView("login");
    	model.addAttribute("id", "");
    	model.addAttribute("password", "");
    	model.addAttribute("errorMsg", "");
        return model2;
    }
    
    @RequestMapping("/send")
    public String send(Model model, @ModelAttribute Message message) {
        KafkaProducer.send("exampleTopic", message.getMessage());
        return "redirect:/";
    }
	
    @RequestMapping("/showAddProposals")
    public String showAddProposals(@RequestParam(value = "id") String uId, @RequestParam(value = "password") String uPass, 
    									Model model, HttpServletRequest request,	HttpServletResponse response) {
    	System.out.println(uId + " - " + uPass);
    	if(uId.isEmpty()) return("login");
    	User user = UserDao.getUserLog(Integer.parseInt(uId), uPass);
    	
		if (user == null)
			return ("login");
		else {
			// see getAllProposals(). We get the proposals from html.
			//((ModelAndView) model).addObject("proposals", getAllProposals());
			loggedUser = user;
			return "showAddProposals";
		}
    }
    
    @RequestMapping("/commentProposal/{id}")
    //move to commentProposal.html
    public ModelAndView commentProposal(@PathVariable("id") String idProposal, Model model){
    	new ProposalDao();
    	Proposal p = ProposalDao.GetProposalByID(Integer.parseInt(idProposal));
    	ModelAndView mav = new ModelAndView("commentProposal");
    	model.addAttribute("p", p);
    	mav.addObject("p", p);
    	return mav;
    }
    
    @RequestMapping("/upvoteProposal/{id}")
    public String upvoteProposal(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesProp(Integer.parseInt(id), loggedUser.getId(), 1);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	KafkaProducer.send("votedProposal", id+":"+loggedUser.getId()+":"+1);
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/downvoteProposal/{id}")
    public String downvoteProposal(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesProp(Integer.parseInt(id),  loggedUser.getId(), 0);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	KafkaProducer.send("votedProposal", id+":"+loggedUser.getId()+":"+0);
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/createProposal")
    public String createProposal(@ModelAttribute("Proposal") Proposal proposal, @RequestParam(value="title") String title, @RequestParam(value="text") String text,
    							@RequestParam(value="category") String category, Model model, RedirectAttributes request){
    	new ProposalDao();
    	Proposal prp = new Proposal(loggedUser, title, category, text);
    	request.addAttribute("id", loggedUser.getId());
		request.addAttribute("password", loggedUser.getPassword());
    	try{
    		ProposalDao.save(prp);
    		KafkaProducer.send("createdProposal", String.valueOf(prp.getId()) + ":" + loggedUser.getId());
    		return "redirect:showAddProposals";
    		//return new ModelAndView("showAddProposals");
    	} catch(IllegalArgumentException e){
    		//return new ModelAndView("error");
    		request.addAttribute("error", "You've written an invalid word: " + e.getMessage().split(":")[1]);
    		return "redirect:error";
    	}
    }
    
    @RequestMapping("/createComment/{id}")
    // {id} proposal ID
    public String createComment(@ModelAttribute("Comment") Comment comment, @PathVariable("id") String proposalID, 
    										@RequestParam(value="text") String text,  RedirectAttributes request, Model model){
    	new CommentDao();
    	new ProposalDao();
    	Comment com = new Comment(loggedUser,ProposalDao.GetProposalByID(Integer.parseInt(proposalID)), text);
    	try{
    		CommentDao.save(com);
    		KafkaProducer.send("createdComment", String.valueOf(com.getId()) + ":" + loggedUser.getId());
    		return "redirect:/commentProposal/" + proposalID;
    	} catch(IllegalArgumentException illegalWord){
    		request.addAttribute("error", "You've written an invalid word: " + illegalWord.getMessage().split(":")[1]);
    		return "redirect:error";
    	}
    }
    
    @RequestMapping("/upvoteComment/{id}")
    public String upvoteComment(@PathVariable("id") String idComment, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesCom(Integer.parseInt(idComment),  loggedUser.getId(), 1);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	KafkaProducer.send("votedComment", idComment+":"+loggedUser.getId()+":"+1);
    	return "showAddProposals";
    }
    
    @RequestMapping("/downvoteComment/{id}")
    public String downvoteComment(@PathVariable("id") String idComment, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesCom(Integer.parseInt(idComment),  loggedUser.getId(), 0);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	KafkaProducer.send("votedComment", idComment+":"+loggedUser.getId()+":"+0);
    	return "showAddProposals";
    }
    

    
}