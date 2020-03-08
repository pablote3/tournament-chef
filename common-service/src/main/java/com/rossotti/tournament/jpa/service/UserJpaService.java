package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserJpaService extends CrudService<User> {
	User findByEmail(String email);
}