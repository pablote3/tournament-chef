package com.rossotti.tournament.jpa.model;

import com.rossotti.tournament.jpa.enumeration.GroupPlay;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"templateName"}))
public class Template {
//	@OneToMany(mappedBy="template", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
//	private List<Event> events = new ArrayList<>();
//	public List<Event> getEvents()  {
//		return events;
//	}
//	public void setEvents(List<Event> events)  {
//		this.events = events;
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(length=50, nullable=false)
	private String templateName;
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

//	@Column(nullable=false)
//	private Short gridGroupRound1;
//	public Short getGridGroupRound1() {
//		return gridGroupRound1;
//	}
//	public void setGridGroupRound1(Short gridGroupRound1) {
//		this.gridGroupRound1 = gridGroupRound1;
//	}
//
//	@Column(nullable=false)
//	private Short gridTeamsRound1;
//	public Short getGridTeamsRound1() {
//		return gridTeamsRound1;
//	}
//	public void setGridTeamsRound1(Short gridTeamsRound1) {
//		this.gridTeamsRound1 = gridTeamsRound1;
//	}
//
//	@Enumerated(EnumType.STRING)
//	@Column(length=10, nullable=false)
//	private GroupPlay groupPlay1;
//	public GroupPlay getGroupPlay1() {
//		return groupPlay1;
//	}
//	public void setGroupPlay1(GroupPlay groupPlay1) {
//		this.groupPlay1 = groupPlay1;
//	}
//	public Boolean isGroupPlay1Pairing() {
//		return groupPlay1 == GroupPlay.Pairing;
//	}
//	public Boolean isGroupPlay1RoundRobin() {
//		return groupPlay1 == GroupPlay.RoundRobin;
//	}
//
//	@Column(nullable=false)
//	private Short groupPlayoffGamesRound1;
//	public Short getGroupPlayoffGamesRound1() {
//		return groupPlayoffGamesRound1;
//	}
//	public void setGroupPlayoffGamesRound1(Short groupPlayoffGamesRound1) {
//		this.groupPlayoffGamesRound1 = groupPlayoffGamesRound1;
//	}
//
//	@Column(nullable=false)
//	private Short gridGroupRound2;
//	public Short getGridGroupRound2() {
//		return gridGroupRound2;
//	}
//	public void setGridGroupRound2(Short gridGroupRound2) {
//		this.gridGroupRound2 = gridGroupRound2;
//	}
//
//	@Column(nullable=false)
//	private Short gridTeamsRound2;
//	public Short getGridTeamsRound2() {
//		return gridTeamsRound2;
//	}
//	public void setGridTeamsRound2(Short gridTeamsRound2) {
//		this.gridTeamsRound2 = gridTeamsRound2;
//	}
//
//	@Enumerated(EnumType.STRING)
//	@Column(length=10)
//	private GroupPlay groupPlay2;
//	public GroupPlay getGroupPlay2() {
//		return groupPlay2;
//	}
//	public void setGroupPlay2(GroupPlay groupPlay2) {
//		this.groupPlay2 = groupPlay2;
//	}
//	public Boolean isGroupPlay2Pairing() {
//		return groupPlay2 == GroupPlay.Pairing;
//	}
//	public Boolean isGroupPlay2RoundRobin() {
//		return groupPlay2 == GroupPlay.RoundRobin;
//	}
//
//	@Column(nullable=false)
//	private Short groupPlayoffGamesRound2;
//	public Short getGroupPlayoffGamesRound2() {
//		return groupPlayoffGamesRound2;
//	}
//	public void setGroupPlayoffGamesRound2(Short groupPlayoffGamesRound2) {
//		this.groupPlayoffGamesRound2 = groupPlayoffGamesRound2;
//	}
//
//	@Column(nullable=false)
//	private Short quarterFinalGames;
//	public Short getQuarterFinalGames() {
//		return quarterFinalGames;
//	}
//	public void setQuarterFinalGames(Short quarterFinalGames) {
//		this.quarterFinalGames = quarterFinalGames;
//	}
//
//	@Column(nullable=false)
//	private Short semiFinalGames;
//	public Short getSemiFinalGames() {
//		return semiFinalGames;
//	}
//	public void setSemiFinalGames(Short semiFinalGames) {
//		this.semiFinalGames = semiFinalGames;
//	}
}
