package codingtechniques.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import codingtechniques.filter.JwtAuthenticationFilter;
import codingtechniques.jwtutil.JwtUtil;
import codingtechniques.service.InMemoryUserDetailsService;
import codingtechniques.service.UsersMemoryService;
import jakarta.websocket.Session;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private UsersMemoryService usersMemoryService;
	
	@Bean
	public UserDetailsService userDetailsService () {
		return new InMemoryUserDetailsService(usersMemoryService.users());
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManager (
			PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
		
		var daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(daoAuthenticationProvider);
		
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity, JwtUtil jwtUtil, UserDetailsService userDetailsService) throws Exception {
		httpSecurity.csrf(c -> c.disable());
		httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/css/**", "/login-page").permitAll().anyRequest().authenticated());
		httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
		httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return httpSecurity.build();
		
	}
	

}
