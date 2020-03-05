package com.rossotti.tournament.dto;

public class OrganizationDTO {
	private String organizationName;
	private String orgAddress1;
	private String orgAddress2;
	private String orgCity;
	private String orgState;
	private String orgZipCode;
	private String orgCountry;
	private String orgContactLastName;
	private String orgContactFirstName;
	private String orgContactEmail;
	private String orgContactPhone;
	private UserDTO user;

	public UserDTO getUserDTO() {
		return user;
	}
	public void setUserDTO(UserDTO user) {
		this.user = user;
	}

	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrgAddress1() {
		return orgAddress1;
	}
	public void setOrgAddress1(String orgAddress1) {
		this.orgAddress1 = orgAddress1;
	}

	public String getOrgAddress2() {
		return orgAddress2;
	}
	public void setOrgAddress2(String orgAddress2) {
		this.orgAddress2 = orgAddress2;
	}

	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getOrgState() {
		return orgState;
	}
	public void setOrgState(String orgState) {
		this.orgState = orgState;
	}

	public String getOrgZipCode() {
		return orgZipCode;
	}
	public void setOrgZipCode(String orgZipCode) {
		this.orgZipCode = orgZipCode;
	}

	public String getOrgCountry() {
		return orgCountry;
	}
	public void setOrgCountry(String orgCountry) {
		this.orgCountry = orgCountry;
	}

	public String getOrgContactLastName() {
		return orgContactLastName;
	}
	public void setOrgContactLastName(String orgContactLastName) {
		this.orgContactLastName = orgContactLastName;
	}

	public String getOrgContactFirstName() {
		return orgContactFirstName;
	}
	public void setOrgContactFirstName(String orgContactFirstName) {
		this.orgContactFirstName = orgContactFirstName;
	}

	public String getOrgContactEmail() {
		return orgContactEmail;
	}
	public void setOrgContactEmail(String orgContactEmail) {
		this.orgContactEmail = orgContactEmail;
	}

	public String getOrgContactPhone() {
		return orgContactPhone;
	}
	public void setOrgContactPhone(String orgContactPhone) {
		this.orgContactPhone = orgContactPhone;
	}
}
