package com.propertysearch.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.propertysearch.utils.JwtTokenProvider;
import com.propertysearch.utils.SecurityConstants;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	private JwtTokenProvider jwtTokenProvider;
	
	public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader==null && !authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token=authorizationHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
		String username=jwtTokenProvider.getSubject(token);
		if(jwtTokenProvider.isTokenValid(username, token)) {
			List<GrantedAuthority> authorities=jwtTokenProvider.getAuthorities(token);
			Authentication authentication=jwtTokenProvider.getAuthentication(username, authorities, request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}else {
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}

}
