package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.GameType;
import com.rossotti.tournament.jpa.enumeration.RankingType;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameLocationId", "gameType"}))
public class GameRound {
	@ManyToOne
	@JoinColumn(name="gameLocationId", referencedColumnName="id", nullable=false)
	private GameLocation gameLocation;
	public GameLocation getGameLocation() {
		return gameLocation;
	}
	public void setGameLocation(GameLocation gameLocation) {
		this.gameLocation = gameLocation;
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

	@Enumerated(EnumType.STRING)
	@Column(length=14, nullable=false)
	private GameType gameType;
	public GameType getGameType() {
		return gameType;
	}
	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}
	public Boolean isGroupPlay() {
		return gameType == GameType.GroupPlay;
	}
	public Boolean isPlayoff() {
		return gameType == GameType.PlayOff;
	}
	public Boolean isQuarterFinal() {
		return gameType == GameType.QuarterFinal;
	}
	public Boolean isSemiFinal() {
		return gameType == GameType.SemiFinal;
	}
	public Boolean isFinal() {
		return gameType == GameType.Final;
	}

	@Column(nullable=false)
	private Short gameDuration;
	public Short getGameDuration() {
		return gameDuration;
	}
	public void setGameDuration(Short gameDuration) {
		this.gameDuration = gameDuration;
	}
}
