package com.rossotti.tournament.jpa.service.impl;

import com.rossotti.tournament.exception.CustomException;
import com.rossotti.tournament.exception.ValidationMessages;
import com.rossotti.tournament.jpa.enumeration.GameStatus;
import com.rossotti.tournament.jpa.model.Game;
import com.rossotti.tournament.jpa.repository.GameRepository;
import com.rossotti.tournament.jpa.service.GameJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
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
	public Game save(Game game) throws ResponseStatusException {
		try {
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
		}
		catch (Exception e) {
			if (e instanceof TransactionSystemException) {
				throw new CustomException(e.getCause().getCause().getMessage(), HttpStatus.BAD_REQUEST);
			}
			else if (e instanceof ConstraintViolationException) {
				throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
			else {
				throw new CustomException(ValidationMessages.MSG_VAL_0000, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return game;
	}

	@Override
	public Game delete(Long id) {
		try {
			Game game = getById(id);
			if (game != null) {
				gameRepository.deleteById(game.getId());
				return game;
			}
			else {
				throw new CustomException(ValidationMessages.MSG_VAL_0012, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		catch(CustomException ce) {
			throw ce;
		}
		catch(Exception e) {
			throw new CustomException(ValidationMessages.MSG_VAL_0007, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}