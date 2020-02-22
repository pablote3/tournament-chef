package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.OrganizationStatus;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationName", "startDate", "endDate"}))
public class Organization extends BaseEntity {
	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<UserOrganization> userOrganizations = new ArrayList<>();
	public List<UserOrganization> getUserOrganizations()  {
		return userOrganizations;
	}
	public void setUserOrganizations(List<UserOrganization> userOrganizations)  {
		this.userOrganizations = userOrganizations;
	}

	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<OrganizationTeam> teams = new ArrayList<>();
	public List<OrganizationTeam> getTeams()  {
		return teams;
	}
	public void setTeams(List<OrganizationTeam> teams)  {
		this.teams = teams;
	}

	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<OrganizationLocation> locations = new ArrayList<>();
	public List<OrganizationLocation> getLocations()  {
		return locations;
	}
	public void setLocations(List<OrganizationLocation> locations)  {
		this.locations = locations;
	}

	@OneToMany(mappedBy="organization", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Event> events = new ArrayList<>();
	public List<Event> getEvents()  {
		return events;
	}
	public void setEvents(List<Event> events)  {
		this.events = events;
	}

	@Column(length=50, nullable=false)
	@NotBlank(message="OrganizationName is mandatory")
	private String organizationName;
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
	@NotNull(message="OrganizationStatus is mandatory")
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

	@Column(nullable=false)
	@NotNull(message="StartDate is mandatory")
	private LocalDate startDate;
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Column(nullable=false)
	@NotNull(message="EndDate is mandatory")
	private LocalDate endDate;
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Column(length=40, nullable=false)
	@NotBlank(message="Address1 is mandatory")
	private String address1;
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Column(length=40)
	private String address2;
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(length=25, nullable=false)
	@NotBlank(message="City is mandatory")
	private String city;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Column(length=25, nullable=false)
	@NotBlank(message="State is mandatory")
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Column(length=9, nullable=false)
	@NotBlank(message="ZipCode is mandatory")
	private String zipCode;
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(length=25, nullable=false)
	@NotBlank(message="Country is mandatory")
	private String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Column(length=25, nullable=false)
	@NotBlank(message="ContactLastName is mandatory")
	private String contactLastName;
	public String getContactLastName() {
		return contactLastName;
	}
	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	@Column(length=25, nullable=false)
	@NotBlank(message="ContactFirstName is mandatory")
	private String contactFirstName;
	public String getContactFirstName() {
		return contactFirstName;
	}
	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
	}

	@Column(length=50, nullable=false)
	@NotBlank(message="ContactEmail is mandatory")
	@Email(message = "ContactEmail invalid format")
	private String contactEmail;
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Column(length=10, nullable=false)
	@NotBlank(message="ContactPhone is mandatory")
	private String contactPhone;
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
}
