package com.rossotti.tournament.jpa.service.impl;

import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.repository.UserRepository;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserJpaServiceImpl implements UserJpaService {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User getById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public List<?> listAll() {
		return new ArrayList<>(userRepository.findAll());
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		User findUser = findByEmail(user.getEmail());
		if (findUser != null) {
			findUser.setUserType(user.getUserType());
			findUser.setUserStatus(user.getUserStatus());
			findUser.setLastName(user.getLastName());
			findUser.setFirstName(user.getFirstName());
			findUser.setPassword(user.getPassword());
			userRepository.save(findUser);
		}
		else {
			userRepository.save(user);
		}
		return user;
	}

	@Override
	public User delete(Long id) {
		User user = getById(id);
		if (user != null) {
			userRepository.deleteById(user.getId());
		}
		else {
			throw new NoSuchEntityException(User.class);
		}
		return user;
	}
}