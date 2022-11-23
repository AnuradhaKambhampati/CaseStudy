package com.propertysearch.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Owner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	@ManyToMany
	@JoinTable(
	name="owner_property",
	joinColumns = @JoinColumn(name="owner_id"),
	inverseJoinColumns = @JoinColumn(name="property_id"))
	private List<Property> propertyList;
	private String[] roles;
	private String[] authorities;
	
	public Owner() {
		
	}
	
	
	public Owner(Long id, String firstName, String lastName, String email, String password, List<Property> propertyList,
			String[] roles, String[] authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.propertyList = propertyList;
		this.roles = roles;
		this.authorities = authorities;
	}

	
}
