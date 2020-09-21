package com.rossotti.tournament.model;

import com.rossotti.tournament.enumeration.GameStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameRoundId", "startTime"}))
public class Game extends BaseEntity {
	@ManyToOne
	@JoinColumn(name="gameRoundId", referencedColumnName="id", nullable=false)
	private GameRound gameRound;
	public GameRound getGameRound() {
		return gameRound;
	}
	public void setGameRound(GameRound gameRound)  {
		this.gameRound = gameRound;
	}

	@OneToMany(mappedBy="game", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<GameTeam> gameTeams = new ArrayList<>();
	public List<GameTeam> getGameTeams()  {
		return gameTeams;
	}
	public void setGameTeams(List<GameTeam> gameTeams) {
		this.gameTeams = gameTeams;
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

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
	@NotNull(message="GameStatus is mandatory")
	private GameStatus gameStatus;
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
}