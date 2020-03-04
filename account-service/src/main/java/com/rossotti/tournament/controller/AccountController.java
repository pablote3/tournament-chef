package com.rossotti.tournament.controller;

import com.rossotti.tournament.dto.AccountDTO;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.InactiveEntityException;
import com.rossotti.tournament.exception.UnauthorizedEntityException;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

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

	private Organization createAccount(AccountDTO accountDTO) {
		try {
			User user = userJpaService.findByEmail(accountDTO.getUserEmail());
			if (user == null) {
				//new user persist
			}
			else {
				logger.debug("CreateAccount - Email " + user.getEmail() + " exists - " +
						"Status = " + user.getUserStatus() + " " +
						"UserType = " + user.getUserStatus());
				if (user.isActive()) {
					if (user.isAdministrator() || user.isOrganization()) {
						if (organizationJpaService.findByOrganizationNameAsOfDate(accountDTO.getOrgName(), LocalDate.now()) == null) {
							if (organizationJpaService.findByOrganizationName(accountDTO.getOrgName()).size() == 0) {
								//new user persist
							}
							else {
								throw new InactiveEntityException(Organization.class);
							}
						}
						else {
							throw new EntityExistsException(Organization.class);
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
