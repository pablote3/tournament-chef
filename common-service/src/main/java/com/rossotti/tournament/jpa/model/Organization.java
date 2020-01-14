package com.rossotti.tournament.jpa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Organization extends BaseEntity {
	private String organizationName;
	private OrganizationStatus organizationStatus;


	@Enumerated(EnumType.STRING)
	public OrganizationStatus getOrganizationStatus() {
		return organizationStatus;
	}
	public void setOrganizationStatus(OrganizationStatus organizationStatus) {
		this.organizationStatus = organizationStatus;
	}
	public Boolean isPending() {
		return organizationStatus == OrganizationStatus.Pending;
	}
	public Boolean isActive() {
		return organizationStatus == OrganizationStatus.Active;
	}
	public Boolean isInactive() {
		return organizationStatus == OrganizationStatus.Inactive;
	}
	public enum OrganizationStatus {
		Pending,
		Active,
		Inactive
	}
}
