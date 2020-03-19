package com.rossotti.tournament.jpa.service.impl;


import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.enumeration.GameStatus;
import com.rossotti.tournament.model.Game;
import com.rossotti.tournament.jpa.repository.GameRepository;
import com.rossotti.tournament.jpa.service.GameJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameJpaServiceImpl implements GameJpaService {

	private GameRepository gameRepository;

	@Autowired
	public void setGameRepository(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	@Override
	public Game getById(Long id) {
		return gameRepository.findById(id);
	}

	@Override
	public List<?> listAll() {
		return new ArrayList<>(gameRepository.findAll());
	}

	@Override
	public List<Game> findByGameStatus(GameStatus gameStatus) {
		return gameRepository.findByGameStatus(gameStatus);
	}

	@Override
	public List<Game> findByTeamName(String teamName) {
		return gameRepository.findByTeamName(teamName);
	}

	@Override
	public List<Game> findByEventName(String eventName) {
		return gameRepository.findByEventName(eventName);
	}

	@Override
	public List<Game> findByGameDate(LocalDate gameDate) {
		return gameRepository.findByGameDate(gameDate);
	}

	@Override
	public List<Game> findByLocationName(String locationName) {
		return gameRepository.findByLocationName(locationName);
	}

	@Override
	public Game findByTeamNameGameDateTime(String teamName, LocalDate gameDate, LocalTime gameTime) {
		return gameRepository.findByTeamNameGameDateTime(teamName, gameDate, gameTime);
	}

	@Override
	public Game save(Game game) {
		String gameTeam = game.getGameTeams().get(0).getEventTeam().getOrganizationTeam().getTeamName();
		LocalDate gameDate = game.getGameRound().getGameLocation().getGameDate().getGameDate();
		Game findGame = findByTeamNameGameDateTime(gameTeam, gameDate, game.getStartTime());
		if (findGame != null) {
			findGame.setStartTime(game.getStartTime());
			findGame.setGameStatus(game.getGameStatus());
			findGame.setLupdTs(game.getLupdTs());
			findGame.setLupdUserId(game.getLupdUserId());
			findGame.setGameTeams(game.getGameTeams());
			findGame.setGameRound(game.getGameRound());
			gameRepository.save(findGame);
		}
		else {
			gameRepository.save(game);
		}
		return game;
	}

	@Override
	public void delete(Long id) {
		Game game = getById(id);
		if (game != null) {
			gameRepository.deleteById(game.getId());
		}
		else {
			throw new NoSuchEntityException(Game.class);
		}
	}
}