package es.uniovi.asw;


import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

import es.uniovi.asw.dao.CommentDao;
import es.uniovi.asw.dao.ProposalDao;
import es.uniovi.asw.dao.UserDao;
import es.uniovi.asw.dao.VoteDao;
import es.uniovi.asw.kafka.KafkaProducer;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;
@Controller
public class MainController {
	private User loggedUser;
    @Autowired
    private KafkaProducer kafkaProducer;
    private List<Proposal> proposals;
    
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
        kafkaProducer.send("exampleTopic", message.getMessage());
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
    public ModelAndView commentProposal(@PathVariable("id") String id, Model model){
    	new ProposalDao();
    	System.out.println(id);
    	Proposal p = ProposalDao.GetProposalByID(Integer.parseInt(id));
    	ModelAndView mav = new ModelAndView("commentProposal");
    	System.out.println(p);
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
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/downvoteProposal/{id}")
    public String downvoteProposal(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesProp(Integer.parseInt(id),  loggedUser.getId(), 0);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/createProposal")
    public String createProposal(@ModelAttribute("Proposal") Proposal proposal, @RequestParam(value="title") String title, @RequestParam(value="text") String text,
    							@RequestParam(value="category") String category, Model model, RedirectAttributes request){
    	new ProposalDao();
    	Proposal prp = new Proposal(loggedUser, title, category, text);
    	ProposalDao.save(prp);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:showAddProposals";
    }
    
    @RequestMapping("/createComment/{id}")
    // {id} proposal ID
    public String createComment(@ModelAttribute("Comment") Comment comment, @PathVariable("id") String proposalID, @RequestParam(value="text") String text){
    	new CommentDao();
    	new ProposalDao();
    	Comment com = new Comment(loggedUser,ProposalDao.GetProposalByID(Integer.parseInt(proposalID)), text);
    	CommentDao.save(com);
    	return "redirect:/commentProposal/" + proposalID;
    }
    
    @RequestMapping("/upvoteComment/{id}")
    public String upvoteComment(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesCom(Integer.parseInt(id),  loggedUser.getId(), 1);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "showAddProposals";
    }
    
    @RequestMapping("/downvoteComment/{id}")
    public String downvoteComment(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	new VoteDao();
    	VoteDao.InsertVotesCom(Integer.parseInt(id),  loggedUser.getId(), 0);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "showAddProposals";
    }
    
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
    
}