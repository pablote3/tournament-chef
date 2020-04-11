package com.rossotti.tournament.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameDateId", "organizationLocationId", "baseLocationName"}))
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

	@OneToMany(mappedBy="gameLocation", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<GameRound> gameRounds = new ArrayList<>();
	public List<GameRound> getGameRounds()  {
		return gameRounds;
	}
	public void setGameRounds(List<GameRound> gameRounds) {
		this.gameRounds = gameRounds;
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

	@Column(length=50, nullable=false)
	@NotBlank(message= "BaseLocationName is mandatory")
	private String baseLocationName;
	public String getBaseLocationName() {
		return baseLocationName;
	}
	public void setBaseLocationName(String baseLocationName) {
		this.baseLocationName = baseLocationName;
	}

	@Column(nullable=false)
	@NotNull(message="StartTime is mandatory")
	private LocalTime startTime;
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
}
