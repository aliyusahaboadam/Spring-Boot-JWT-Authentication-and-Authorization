package codingtechniques.jwtutil;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	private SecretKey key = Jwts.SIG.HS256.key().build();
	private long currentTimeMillis = System.currentTimeMillis();
	private long tokenValidity = TimeUnit.MINUTES.toMillis(30);
	
	
	public String createToken (String username) {
		
	return	Jwts.builder()
		.header()
		.add("alg", "HS256")
		.add("type", "JWT")
		.and()
		
		.signWith(key)
		.issuer("Coding Techniques")
		.issuedAt(new Date(currentTimeMillis))
		.expiration(new Date(currentTimeMillis + tokenValidity))
		.claim("username", username)
		.compact();
	
	}
	
	public Claims verifyJws (String token) {
		if (token != null) {
			return Jwts.parser().verifyWith(key)
					.build().parseSignedClaims(token)
					.getPayload();
		}
		return null;	
	}
	
	
	public boolean checkValidity (String token) {
		return verifyJws(token).getExpiration().after(new Date(currentTimeMillis));
		
	}
	
	
}
