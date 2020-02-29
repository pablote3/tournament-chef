package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameDateId", "availableLocationId"}))
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
	@JoinColumn(name="availableLocationId", referencedColumnName="id", nullable=false)
	private AvailableLocation availableLocation;
	public AvailableLocation getAvailableLocation() {
		return availableLocation;
	}
	public void setAvailableLocation(AvailableLocation availableLocation) {
		this.availableLocation = availableLocation;
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
