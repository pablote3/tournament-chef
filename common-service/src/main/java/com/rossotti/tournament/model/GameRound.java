package com.rossotti.tournament.model;

import com.rossotti.tournament.enumeration.GameRoundType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"gameLocationId", "gameRoundType"}))
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

	@OneToMany(mappedBy="gameRound", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
	private List<Game> games = new ArrayList<>();
	public List<Game> getGames()  {
		return games;
	}
	public void setGames(List<Game> games) {
		this.games = games;
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
	@NotNull(message="GameRoundType is mandatory")
	private GameRoundType gameRoundType;
	public GameRoundType getGameType() {
		return gameRoundType;
	}
	public void setGameRoundType(GameRoundType gameRoundType) {
		this.gameRoundType = gameRoundType;
	}
	public Boolean isGroupPlay() {
		return gameRoundType == GameRoundType.GroupPlay;
	}
	public Boolean isPlayoff() {
		return gameRoundType == GameRoundType.PlayOff;
	}
	public Boolean isQuarterFinal() {
		return gameRoundType == GameRoundType.QuarterFinal;
	}
	public Boolean isSemiFinal() {
		return gameRoundType == GameRoundType.SemiFinal;
	}
	public Boolean isChampionship() {
		return gameRoundType == GameRoundType.Championship;
	}

	@Column(nullable=false)
	@NotNull(message="GameDuration is mandatory")
	private Short gameDuration;
	public Short getGameDuration() {
		return gameDuration;
	}
	public void setGameDuration(Short gameDuration) {
		this.gameDuration = gameDuration;
	}
}
