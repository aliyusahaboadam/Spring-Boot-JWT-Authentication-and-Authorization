package codingtechniques.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import codingtechniques.model.User;

@Service
public class UsersMemoryService {
	
	public List<UserDetails> users () {
		
		UserDetails u1 = new User("Aliyu", "1234", "ADMIN");
		UserDetails u2 = new User("Yasir", "1234", "MANAGER");
		
		return List.of(u1, u2);
	}
	
	

}
