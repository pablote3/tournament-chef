package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationName", "startDate", "endDate"}))
public class Organization extends BaseEntity {
	@Column(length=50, nullable=false)
	private String organizationName;
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
	private OrganizationStatus organizationStatus;
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
		Pending, Active, Inactive
	}

	@Column(nullable=false)
	private LocalDate startDate;
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Column(nullable=false)
	private LocalDate endDate;
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Column(length=25, nullable=false)
	private String address1;
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(length=25)
	private String address2;
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(length=25, nullable=false)
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Column(length=25, nullable=false)
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Column(length=9, nullable=false)
	private String zipCode;
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(length=25, nullable=false)
	private String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Column(length=25, nullable=false)
	private String contactLastName;
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	@Column(length=25, nullable=false)
	private String contactFirstName;
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	@Column(length=50, nullable=false)
	private String contactEmail;
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Column(length=10, nullable=false)
	private String contactPhone;
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@Column(nullable = false)
	private LocalDateTime createTs;
	public LocalDateTime getCreateTs() {
		return createTs;
	}
	public void setCreateTs(LocalDateTime createTs) {
		this.createTs = createTs;
	}
}
