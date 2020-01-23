package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationId", "email"}))
public class User extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="organizationId", referencedColumnName="id", nullable=false)
	private Organization organization;
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(length=50, nullable=false)
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=13, nullable=false)
	private UserType userType;
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Boolean isAdministrator() {
		return userType == UserType.Administrator;
	}
	public Boolean isManager() {
		return userType == UserType.Manager;
	}
	public Boolean isUser() {
		return userType == UserType.User;
	}
	public Boolean isGuest() {return userType == UserType.Guest;
	}
	public enum UserType {
		Administrator, Manager, User, Guest
	}

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
	private UserStatus userStatus;
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public Boolean isActive() {
		return userStatus == UserStatus.Active;
	}
	public Boolean isInactive() {
		return userStatus == UserStatus.Inactive;
	}
	public enum UserStatus {
		Active, Inactive
	}

	@Column(length=25, nullable=false)
	private String lastName;
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length=25, nullable=false)
	private String firstName;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length=50, nullable=false)
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
