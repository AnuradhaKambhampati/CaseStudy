package com.propertysearch.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.propertysearch.utils.JwtTokenProvider;
import com.propertysearch.utils.SecurityConstants;

public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	private JwtTokenProvider jwtTokenProvider;
	
	public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader==null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		
	}

}
