package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.UserStatus;
import com.rossotti.tournament.jpa.enumeration.UserType;
import javax.persistence.*;
import javax.validation.constraints.*;

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
	@NotBlank(message="Email is mandatory")
	@Email(message = "Email invalid format")
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=13, nullable=false)
	@NotNull(message="UserType is mandatory")
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

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
	@NotNull(message="UserStatus is mandatory")
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

	@Column(length=25, nullable=false)
	@NotBlank(message="LastName is mandatory")
	private String lastName;
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length=25, nullable=false)
	@NotBlank(message="FirstName is mandatory")
	private String firstName;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length=50, nullable=false)
	@Pattern(regexp="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$", message="Password invalid format")
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
