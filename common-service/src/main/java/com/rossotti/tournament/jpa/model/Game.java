package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.GameStatus;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameRoundId", "startTime", "homeTeamName"}))
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
	private LocalTime startTime;
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	@Column(length=25, nullable=false)
	private String homeTeamName;
	public String getHomeTeamName() {
		return homeTeamName;
	}
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	@Column(length=25, nullable=false)
	private String awayTeamName;
	public String getAwayTeamName() {
		return awayTeamName;
	}
	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=9, nullable=false)
	private GameStatus gameStatus;
	public GameStatus getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	public Boolean isScheduled() {
		return gameStatus == GameStatus.Scheduled;
	}
	public Boolean isCancelled() {
		return gameStatus == GameStatus.Cancelled;
	}
	public Boolean isForfeited() {
		return gameStatus == GameStatus.Forfeited;
	}
	public Boolean isCompleted() {
		return gameStatus == GameStatus.Completed;
	}
}
