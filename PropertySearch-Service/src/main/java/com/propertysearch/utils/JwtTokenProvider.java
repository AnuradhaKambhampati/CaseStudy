package com.propertysearch.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.propertysearch.service.UserDetailsImpl;

@Component
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String secret;

	private String generateJwtToken(UserDetailsImpl userDetails) {
		String[] claims= getClaimsFromUser(userDetails);
		return JWT.create().withIssuer(SecurityConstants.BLUE_SKY)
						   .withAudience(SecurityConstants.BLUE_SKY_ADMINISTRATION)
						   .withIssuedAt(new Date())
						   .withSubject(userDetails.getUsername())
						   .withArrayClaim(SecurityConstants.AUTHORITIES, claims)
						   .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
						   .sign(Algorithm.HMAC512(secret.getBytes()));
	}

	
	public List<GrantedAuthority> getAuthorities(String token){
		String[] claims=getClaimsFromToken(token);
		return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken userPasswordAuthToken=new UsernamePasswordAuthenticationToken(username, null,authorities);
		userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return userPasswordAuthToken;
	}
	
	public boolean isTokenValid(String username,String token) {
		JWTVerifier verifier=getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
	}
	
	public String getSubject(String token) {
		JWTVerifier verifier=getJWTVerifier();
		return verifier.verify(token).getSubject();
	}
	
	private boolean isTokenExpired(JWTVerifier verifier, String token) {
		Date expiration=verifier.verify(token).getExpiresAt();
		return expiration.after(new Date());
	}

		
	private String[] getClaimsFromUser(UserDetailsImpl userDetails) {
		List<String> authorities=new ArrayList<>();
		for(GrantedAuthority grantedAuthority:userDetails.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		String[] array=new String[authorities.size()];
		authorities.toArray(array);
		return array;
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier=getJWTVerifier();
		return verifier.verify(token).getClaim(SecurityConstants.AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm=Algorithm.HMAC512(secret);
			verifier=JWT.require(algorithm).withIssuer(SecurityConstants.BLUE_SKY).build();
		}catch(JWTVerificationException e) {
			throw new JWTVerificationException(SecurityConstants.TOKEN_CANNOT_BE_VERIFIED);
		}
		return verifier;
	}
}





