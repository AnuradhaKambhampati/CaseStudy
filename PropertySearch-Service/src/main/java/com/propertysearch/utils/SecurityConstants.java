package com.propertysearch.utils;

public class SecurityConstants {

	public static final long EXPIRATION_TIME=3600000;
	public static final String TOKEN_PREFIX="Bearer";
	public static final String JWT_TOKEN_HEADER="Jwt-Token";
	public static final String BLUE_SKY="BLUE SKY";
	public static final String BLUE_SKY_ADMINISTRATION="Property Search System";
	public static final String TOKEN_CANNOT_BE_VERIFIED="Token cannot be verified";
	public static final String AUTHORITIES="Authorities";
	public static final String FORBIDDEN_MESSAGE="Log in to access this page";
	public static final String ACCESS_DENIED_MESSAGE="You do not have permission to access this page";
	public static final String[] PUBLIC_URLS= {"/propertySearch/login","/propertySearch/signup"};
	
}
