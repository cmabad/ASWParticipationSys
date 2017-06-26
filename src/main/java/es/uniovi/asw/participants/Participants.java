package es.uniovi.asw.participants;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import es.uniovi.asw.model.User;
import es.uniovi.asw.participants.repository.DBManagement;



//@RestController
public class Participants {

	private static final Logger LOG = LoggerFactory.getLogger(Participants.class);

    //@Autowired
    private DBManagement repository;

    //@PostMapping("/user")
    public ResponseEntity<User> getGivenCredentials(
            @RequestBody Credentials credentials) {
        LOG.info("Logging in, user:  " + credentials.getEmail());
        
        User userLogged = repository.getParticipant(credentials.getEmail(),
        		credentials.getPassword());

        if(userLogged != null){
        	//The introduced credentials are correct
            return new ResponseEntity<>(userLogged, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    public void changeInfo(User user){
    	repository.updateInfo(user);
    }
}