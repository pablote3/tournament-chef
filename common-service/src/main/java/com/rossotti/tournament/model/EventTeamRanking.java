package com.rossotti.tournament.model;

import com.rossotti.tournament.enumeration.RankingType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class EventTeamRanking {
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

	@Enumerated(EnumType.STRING)
	@Column(length=14, nullable=false)
	@NotNull(message="RankingType is mandatory")
	private RankingType rankingType;
	public RankingType getRankingType() {
		return rankingType;
	}
	public void setRankingType(RankingType rankingType) {
		this.rankingType = rankingType;
	}

	@Column(nullable=false)
	@NotNull(message="Ranking is mandatory")
	private Short ranking;
	public Short getRanking() {
		return ranking;
	}
	public void setRanking(Short ranking) {
		this.ranking = ranking;
	}
}
