package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("O")
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"organizationId", "locationName"}))
public class OrganizationLocation extends AvailableLocation {

	@Column(length=25, nullable=false)
	@NotBlank(message="Address1 is mandatory")
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
}
