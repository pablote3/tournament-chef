package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.enumeration.GameStatus;
import com.rossotti.tournament.jpa.model.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface GameRepository extends Repository<Game, Long> {

	Game findById(Long id);

	List<Game> findAll();

	void save(Game game);

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

	String findByLocationName =
			"select g from Game g " +
			"inner join g.gameRound gr " +
			"inner join gr.gameLocation gl " +
			"inner join gl.organizationLocation ol " +
			"where ol.locationName = :locationName";
	@Query(findByLocationName)
	List<Game> findByLocationName(@Param("locationName") String locationName);
}