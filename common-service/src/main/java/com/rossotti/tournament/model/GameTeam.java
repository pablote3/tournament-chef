package com.rossotti.tournament.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameId", "eventTeamId"}))
public class GameTeam {
	@ManyToOne
	@JoinColumn(name="gameId", referencedColumnName="id", nullable=false)
	private Game game;
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

	@ManyToOne
	@JoinColumn(name="eventTeamId", referencedColumnName="id", nullable=false)
	private EventTeam eventTeam;
	public EventTeam getEventTeam() {
		return eventTeam;
	}
	public void setEventTeam(EventTeam eventTeam) {
		this.eventTeam = eventTeam;
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
	@NotNull(message="PointsScored is mandatory")
	private Short pointsScored;
	public Short getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(Short pointsScored) {
		this.pointsScored = pointsScored;
	}

	@Column(nullable=false)
	@NotNull(message="HomeTeam is mandatory")
	private Boolean homeTeam;
	public Boolean getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(Boolean homeTeam) {
		this.homeTeam = homeTeam;
	}
}
