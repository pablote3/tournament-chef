package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.RankingType;
import javax.persistence.*;

@Entity
@Table
public class EventTeamRanking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="eventTeamId", referencedColumnName="id", nullable=true)
	private EventTeam eventTeam;
	public EventTeam getEventTeam() {
		return eventTeam;
	}
	public void setEventTeam(EventTeam eventTeam) {
		this.eventTeam = eventTeam;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=14, nullable=false)
	private RankingType rankingType;
	public RankingType getRankingType() {
		return rankingType;
	}
	public void setRankingType(RankingType rankingType) {
		this.rankingType = rankingType;
	}
	public Boolean isInitial() {
		return rankingType == RankingType.Initial;
	}
	public Boolean isRound1() {
		return rankingType == RankingType.Round1;
	}
	public Boolean isRound2() {
		return rankingType == RankingType.Round2;
	}
	public Boolean isQuarterFinals() {
		return rankingType == RankingType.QuarterFinals;
	}
	public Boolean isSemiFinals() {
		return rankingType == RankingType.SemiFinals;
	}
	public Boolean isFinals() {
		return rankingType == RankingType.Finals;
	}
	public Boolean isEnd() {
		return rankingType == RankingType.End;
	}

	@Column(nullable=false)
	private Short ranking;
	public Short getRanking() {
		return ranking;
	}
	public void setRanking(Short ranking) {
		this.ranking = ranking;
	}
}
