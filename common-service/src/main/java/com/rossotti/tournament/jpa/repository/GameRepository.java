package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.enumeration.GameStatus;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.model.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface GameRepository extends Repository<Game, Long> {

	Game findById(Long id);

	List<Game> findAll();

	void save(Game game);

	void deleteById(Long id);

	List<Game> findByGameStatus(GameStatus gameStatus);

	String findByTeamName =
			"select g from Game g " +
			"inner join g.gameTeams gt " +
			"inner join gt.eventTeam et " +
			"inner join et.organizationTeam ot " +
			"where ot.teamName = :teamName";
	@Query(findByTeamName)
	List<Game> findByTeamName(@Param("teamName") String teamName);

	String findByEventName =
			"select g from Game g " +
			"inner join g.gameRound gr " +
			"inner join gr.gameLocation gl " +
			"inner join gl.gameDate gd " +
			"inner join gd.event e " +
			"where e.eventName = :eventName";
	@Query(findByEventName)
	List<Game> findByEventName(@Param("eventName") String eventName);

	String findByGameDate =
			"select g from Game g " +
			"inner join g.gameRound gr " +
			"inner join gr.gameLocation gl " +
			"inner join gl.gameDate gd " +
			"where gd.gameDate = :gameDate";
	@Query(findByGameDate)
	List<Game> findByGameDate(@Param("gameDate") LocalDate gameDate);

	String findByEventNameTemplateTypeAsOfDate =
			"select g from Game g " +
			"inner join g.gameRound gr " +
			"inner join gr.gameLocation gl " +
			"inner join gl.gameDate gd " +
			"inner join gd.event e " +
			"where e.eventName = :eventName " +
			"and e.startDate <= :asOfDate " +
			"and e.endDate >= :asOfDate " +
			"and e.templateType = :templateType";
	@Query(findByEventNameTemplateTypeAsOfDate)
	List<Game> findByEventNameTemplateTypeAsOfDate(@Param("eventName") String eventName, @Param("templateType") TemplateType templateType, @Param("asOfDate") LocalDate asOfDate);

	String findByLocationName =
			"select g from Game g " +
			"inner join g.gameRound gr " +
			"inner join gr.gameLocation gl " +
			"inner join gl.organizationLocation ol " +
			"where ol.locationName = :locationName";
	@Query(findByLocationName)
	List<Game> findByLocationName(@Param("locationName") String locationName);

	String findByTeamNameGameDateTime =
			"select g from Game g " +
			"inner join g.gameTeams gt " +
			"inner join gt.eventTeam et " +
			"inner join et.organizationTeam ot " +
			"inner join g.gameRound gr " +
			"inner join gr.gameLocation gl " +
			"inner join gl.gameDate gd " +
			"where ot.teamName = :teamName " +
			"and g.startTime = :gameTime " +
			"and gd.gameDate = :gameDate";
	@Query(findByTeamNameGameDateTime)
	Game findByTeamNameGameDateTime(@Param("teamName") String teamName, @Param("gameDate") LocalDate gameDate, @Param("gameTime") LocalTime gameTime);
}