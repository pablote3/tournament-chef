package com.rossotti.tournament.controller;

import com.rossotti.tournament.dto.OrganizationDTO;
import com.rossotti.tournament.exception.EntityExistsException;
import com.rossotti.tournament.exception.InactiveEntityException;
import com.rossotti.tournament.exception.UnauthorizedEntityException;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.model.User;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.modelmapper.ModelMapper;
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

	public Organization createAccount(OrganizationDTO organizationDTO) {
		User user = userJpaService.findByEmail(organizationDTO.getUserDTO().getUserEmail());
		if (user == null) {
			logger.debug("createAccount - findByUserEmail: email = " + organizationDTO.getUserDTO().getUserEmail() + " does not exist");
			return createOrganization(organizationDTO);
		}
		else {
			logger.debug("createAccount - findByUserEmail: email = " + user.getEmail() + " exists");
			if (user.isActive()) {
				logger.debug("createAccount - activeUser " + user.getUserStatus());
				if (user.isAdministrator() || user.isOrganization()) {
					Organization organization = createOrganization(organizationDTO);
					return organization;
				}
				else {
					logger.debug("createAccount - unauthorizedUser " + user.getUserStatus());
					throw new UnauthorizedEntityException(User.class);
				}
			}
			else {
				logger.debug("createAccount - inactiveUser " + user.getUserStatus());
				throw new InactiveEntityException(User.class);
			}
		}
	}

	private Organization createOrganization(OrganizationDTO organizationDTO) {
		LocalDate currentDate = LocalDate.now();
		if (organizationJpaService.findByOrganizationNameAsOfDate(organizationDTO.getOrganizationName(), currentDate) == null) {
			logger.debug("createOrganization - findByOrganizationNameAsOfDate: orgName = " + organizationDTO.getOrganizationName() + ", asOfDate = " + currentDate + " does not exist");
			if (organizationJpaService.findByOrganizationName(organizationDTO.getOrganizationName()).size() == 0) {
				logger.debug("createOrganization - findByOrganizationName: orgName = " + organizationDTO.getOrganizationName() + " does not exist");
				ModelMapper modelMapper = new ModelMapper();
				logger.debug("createOrganization - saveOrganization: orgName = " + organizationDTO.getOrganizationName() + ", userEmail = " + organizationDTO.getUserDTO().getUserEmail());
				Organization organization = organizationJpaService.save(modelMapper.map(organizationDTO, Organization.class));
				return organization;
			}
			else {
				logger.debug("createOrganization - findByOrganizationName: orgName = " + organizationDTO.getOrganizationName() + " exists");
				throw new InactiveEntityException(Organization.class);
			}
		}
		else {
			logger.debug("createOrganization - findByOrganizationNameAsOfDate: orgName = " + organizationDTO.getOrganizationName() + " exists");
			throw new EntityExistsException(Organization.class);
		}
	}
}
