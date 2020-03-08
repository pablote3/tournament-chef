package com.rossotti.tournament.model;

import com.rossotti.tournament.enumeration.UserStatus;
import com.rossotti.tournament.enumeration.UserType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"email"}))
public class User extends BaseEntity {

	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<UserOrganization> userOrganizations = new ArrayList<>();
	public List<UserOrganization> getUserOrganization()  {
		return userOrganizations;
	}
	public void setUserOrganization(List<UserOrganization> userOrganizations)  {
		this.userOrganizations = userOrganizations;
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
	public Boolean isOrganization() {
		return userType == UserType.Organization;
	}
	public Boolean isEvent() {
		return userType == UserType.Event;
	}
	public Boolean isGame() {return userType == UserType.Game;
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
