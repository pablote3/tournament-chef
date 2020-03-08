package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.enumeration.GameStatus;
import com.rossotti.tournament.model.Game;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public interface GameJpaService extends CrudService<Game> {
	List<Game> findByGameStatus(GameStatus gameStatus);
	List<Game> findByTeamName(String teamName);
	List<Game> findByEventName(String eventName);
	List<Game> findByGameDate(LocalDate gameDate);
	List<Game> findByLocationName(String locationName);
	Game findByTeamNameGameDateTime(String eventName, LocalDate gameDate, LocalTime gameTime);
}