package com.rossotti.tournament.controller;

import com.rossotti.tournament.exception.InactiveEntityException;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountController {

	private final UserJpaService userJpaService;
	private final OrganizationJpaService organizationJpaService;
	private final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	public AccountController(UserJpaService userJpaService, OrganizationJpaService organizationJpaService) {
		this.userJpaService = userJpaService;
		this.organizationJpaService = organizationJpaService;
	}

	private Organization createAccount(User user) {
		try {
			User foundUser = userJpaService.findByEmail(user.getEmail());
			if (foundUser == null) {

			}
			else {
				logger.debug("CreateAccount - Email " + user.getEmail() + " already exists with Status " + user.getUserStatus());
				if (user.isActive()) {

				}
				else {
					throw new InactiveEntityException(User.class);
				}
			}
		}
		catch (Exception e) {
		}
		return null;
	}
}
