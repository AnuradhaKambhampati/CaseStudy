package com.propertysearch.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.propertysearch.utils.PropertyType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Property {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private PropertyType type;
	private String legalDescription;
	private Double taxAmount;
	private String taxPaymentStatus;
	private boolean escrow;
	@ManyToMany(mappedBy = "propertyList")
	private List<Owner> ownerList;
	
	public Property() {
		
	}
	
	public Property(Long id, String address, PropertyType type, String legalDescription, Double taxAmount,
			String taxPaymentStatus, boolean escrow, List<Owner> ownerList) {
		this.id = id;
		this.address = address;
		this.type = type;
		this.legalDescription = legalDescription;
		this.taxAmount = taxAmount;
		this.taxPaymentStatus = taxPaymentStatus;
		this.escrow = escrow;
		this.ownerList = ownerList;
	}
	
	

}
