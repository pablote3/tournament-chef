package com.rossotti.tournament.jpa.service;

import com.rossotti.tournament.jpa.model.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserJpaService extends CrudService<User> {
	List<User> findByEmail(String email);
	User findByOrganizationNameAndUserEmail(String email, String organizationName);
}