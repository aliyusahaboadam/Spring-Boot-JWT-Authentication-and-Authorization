package codingtechniques.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserDetailsService implements UserDetailsService{
	
	private List<UserDetails> users;
	
	public InMemoryUserDetailsService(List<UserDetails> users) {
		
		this.users = users;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return users.stream()
				.filter(u -> u.getUsername().equals(username))
				.findFirst()
				.orElseThrow( () -> new UsernameNotFoundException("user not found"));
	}

}
