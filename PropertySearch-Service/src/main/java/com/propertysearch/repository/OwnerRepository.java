package com.propertysearch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.propertysearch.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

	public Owner findByEmail(String email);
	
}
