package com.rossotti.tournament.jpa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Organization extends BaseEntity {
	private String organizationName;
//	private OrganizationStatus organizationStatus;
	private String address1;

	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

//	@Enumerated(EnumType.STRING)
//	public OrganizationStatus getOrganizationStatus() {
//		return organizationStatus;
//	}
//	public void setOrganizationStatus(OrganizationStatus organizationStatus) {
//		this.organizationStatus = organizationStatus;
//	}
//	public Boolean isPending() {
//		return organizationStatus == OrganizationStatus.Pending;
//	}
//	public Boolean isActive() {
//		return organizationStatus == OrganizationStatus.Active;
//	}
//	public Boolean isInactive() {
//		return organizationStatus == OrganizationStatus.Inactive;
//	}
//	public enum OrganizationStatus {
//		Pending,
//		Active,
//		Inactive
//	}

}
