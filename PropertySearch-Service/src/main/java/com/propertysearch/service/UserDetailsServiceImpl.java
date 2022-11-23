package com.propertysearch.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.propertysearch.entity.Owner;
import com.propertysearch.repository.OwnerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	OwnerRepository ownerRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Owner owner=ownerRepository.findByEmail(username);
		if(owner==null) {
			throw new UsernameNotFoundException("Owner Not Found with username: " + username);
		}else {
			UserDetailsImpl user=new UserDetailsImpl(owner);
			return user;
		}
	}

}
