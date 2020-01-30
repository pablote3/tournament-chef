package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameDateId", "organizationLocationId"}))
public class GameLocation {
	@ManyToOne
	@JoinColumn(name="gameDateId", referencedColumnName="id", nullable=false)
	private GameDate gameDate;
	public GameDate getGameDate() {
		return gameDate;
	}
	public void setGameDate(GameDate gameDate) {
		this.gameDate = gameDate;
	}

	@ManyToOne
	@JoinColumn(name="organizationLocationId", referencedColumnName="id", nullable=false)
	private OrganizationLocation organizationLocation;
	public OrganizationLocation getOrganizationLocation() {
		return organizationLocation;
	}
	public void setOrganizationLocation(OrganizationLocation organizationLocation) {
		this.organizationLocation = organizationLocation;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable=false)
	private LocalTime startTime;
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
}
