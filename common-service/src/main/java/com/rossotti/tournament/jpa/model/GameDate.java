package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"eventId", "gameDate"}))
public class GameDate {
	@ManyToOne
	@JoinColumn(name="eventId", referencedColumnName="id", nullable=false)
	private Event event;
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

	@OneToMany(mappedBy="gameDate", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<GameLocation> gameLocations = new ArrayList<>();
	public List<GameLocation> getGameLocations()  {
		return gameLocations;
	}
	public void setGameLocations(List<GameLocation> gameLocations) {
		this.gameLocations = gameLocations;
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
	@NotNull(message="GameDate is mandatory")
	private LocalDate gameDate;
	public LocalDate getGameDate() {
		return gameDate;
	}
	public void setGameDate(LocalDate gameDate) {
		this.gameDate = gameDate;
	}
}
