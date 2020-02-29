package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "locationType")
public class AvailableLocation extends BaseEntity {

	@ManyToOne
	@JoinColumn(name="organizationId", referencedColumnName="id", nullable=false)
	private Organization organization;
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@OneToMany(mappedBy="availableLocation", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<GameLocation> gameLocations = new ArrayList<>();
	public List<GameLocation> getGameLocations()  {
		return gameLocations;
	}
	public void setGameLocations(List<GameLocation> gameLocations)  {
		this.gameLocations = gameLocations;
	}

	@Column(length=50, nullable=false)
	@NotBlank(message="LocationName is mandatory")
	private String locationName;
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
}
