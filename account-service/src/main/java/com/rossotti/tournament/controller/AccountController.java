package com.rossotti.tournament.controller;

import com.rossotti.tournament.exception.InactiveEntityException;
import com.rossotti.tournament.exception.UnauthorizedEntityException;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.model.UserOrganization;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

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

	private Organization createAccount(User userIn) {
		try {
			User user = userJpaService.findByEmail(userIn.getEmail());
			if (user == null) {
				//new user persist
			}
			else {
				logger.debug("CreateAccount - Email " + user.getEmail() + " exists - " +
							 "Status = " + user.getUserStatus() + " " +
							 "UserType = " + user.getUserStatus());
				if (user.isActive()) {
					if (user.isAdministrator() || user.isOrganization()) {
						if (user.getUserOrganization().size() == 0) {
							//new org persist
						} else {
							Iterator<UserOrganization> iter = user.getUserOrganization().iterator();
							boolean organizationExists = false;
							while (iter.hasNext()) {
								String organizationName = iter.next().getOrganization().getOrganizationName();
								if (organizationName.equals(userIn.getUserOrganization().get(0).getOrganization().getOrganizationName()) ){
									organizationExists = true;
									break;
								}
							}
						}
					}
					else {
						throw new UnauthorizedEntityException(Organization.class);
					}
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
