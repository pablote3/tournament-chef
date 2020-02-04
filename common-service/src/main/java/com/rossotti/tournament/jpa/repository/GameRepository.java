package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.jpa.model.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends Repository<Game, Long> {

	Game findById(Long id);

	List<Game> findAll();

	void save(Game game);

	void deleteById(Long id);

	List<Game> findByGameStatus(String status);

//	String findByHomeTeamName =
//			"select g from Game g " +
//			"inner join g.gameTeam gt " +
//			"inner join gt.eventTeamId et " +
//			"where et.eventId = :id";
//	@Query(findByHomeTeamName)
//	List<Game> findByHomeTeamName(@Param("organizationName") Long id);
}