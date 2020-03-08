package com.rossotti.tournament.jpa.repository;

import com.rossotti.tournament.model.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {

	User findById(Long id);

	List<User> findAll();

	void save(User user);

	void deleteById(Long id);

	User findByEmail(String email);
}