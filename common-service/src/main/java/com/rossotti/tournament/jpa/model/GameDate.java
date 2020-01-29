package com.rossotti.tournament.jpa.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameDate"}))
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
	private LocalDate gameDate;
	public LocalDate getGameDate() {
		return gameDate;
	}
	public void setGameDate(LocalDate gameDate) {
		this.gameDate = gameDate;
	}
}
