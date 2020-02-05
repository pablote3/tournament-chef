//package com.rossotti.tournament.jpa.service.impl;
//
//import com.rossotti.tournament.jpa.model.User;
//import com.rossotti.tournament.jpa.repository.UserRepository;
//import com.rossotti.tournament.jpa.service.UserJpaService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserJpaServiceImpl implements UserJpaService {
//
//	private UserRepository userRepository;
//
//	@Autowired
//	public void setUserRepository(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}
//
//	@Override
//	public List<User> findByEmail(String email) {
//		return userRepository.findByEmail(email);
//	}
//
//	@Override
//	public User findByOrganizationNameAndUserEmail(String email, String organizationName) {
//		return userRepository.findByOrganizationNameAndUserEmail(email, organizationName);
//	}
//
//	@Override
//	public List<?> listAll() {
//        return new ArrayList<>(userRepository.findAll());
//	}
//
//	@Override
//	public User getById(Long id) {
//		return userRepository.findById(id);
//	}
//
//	@Override
//	public User create(User createUser) throws AppException {
//		User user = findByOrganizationNameAndUserEmail(createUser.getOrganization().getOrganizationName(), createUser.getEmail());
//		if (user != null) {
//			userRepository.save(createTeam);
//			createTeam.setStatusCode(StatusCodeDAO.Created);
//			return createTeam;
//		}
//		else {
//			return team;
//		}
//	}
//
//	@Override
//	public Team update(Team updateTeam) {
//		Team team = findByTeamKeyAndAsOfDate(updateTeam.getTeamKey(), updateTeam.getFromDate());
//		if (team.isFound()) {
//			team.setLastName(updateTeam.getLastName());
//			team.setFirstName(updateTeam.getFirstName());
//			team.setFullName(updateTeam.getFullName());
//			team.setAbbr(updateTeam.getAbbr());
//			team.setFromDate(updateTeam.getFromDate());
//			team.setToDate(updateTeam.getToDate());
//			team.setConference(updateTeam.getConference());
//			team.setDivision(updateTeam.getDivision());
//			team.setCity(updateTeam.getCity());
//			team.setState(updateTeam.getState());
//			team.setSiteName(updateTeam.getSiteName());
//			userRepository.save(team);
//			team.setStatusCode(StatusCodeDAO.Updated);
//		}
//		return team;
//	}
//
//	@Override
//	public Team delete(Long id) {
//		Team findTeam = getById(id);
//		if (findTeam != null && findTeam.isFound()) {
//			userRepository.deleteById(findTeam.getId());
//			findTeam.setStatusCode(StatusCodeDAO.Deleted);
//			return findTeam;
//		}
//		else {
//			return new Team(StatusCodeDAO.NotFound);
//		}
//	}
//}