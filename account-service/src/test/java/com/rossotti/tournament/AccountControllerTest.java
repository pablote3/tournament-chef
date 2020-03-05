package com.rossotti.tournament;

import com.rossotti.tournament.controller.AccountController;
import com.rossotti.tournament.dto.OrganizationDTO;
import com.rossotti.tournament.dto.UserDTO;
import com.rossotti.tournament.jpa.model.Organization;
import com.rossotti.tournament.jpa.service.OrganizationJpaService;
import com.rossotti.tournament.jpa.service.UserJpaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {
	@Mock
	private UserJpaService userJpaService;

	@Mock
	private OrganizationJpaService organizationJpaService;

	@InjectMocks
	private AccountController accountController;

	@Test
	public void userFindByEmail_userNotFound() {
		when(userJpaService.findByEmail(anyString()))
			.thenReturn(null);
		Organization organization = accountController.createAccount(createMockAccount());
		Assert.assertEquals("Bonfantini", organization.getContactLastName());
	}

	private OrganizationDTO createMockAccount() {
		OrganizationDTO organization = new OrganizationDTO();
		UserDTO user = new UserDTO();
		user.setUserEmail("elena.linari@gmail.com");
		user.setUserLastName("Linari");
		user.setUserFirstName("Elena");
		user.setUserPassword("elena123");
		organization.setUserDTO(user);
		organization.setOrganizationName("Fiesole School District");
		organization.setOrgAddress1("123 Main Street");
		organization.setOrgAddress2("Suite 101");
		organization.setOrgCity("Fiesole");
		organization.setOrgState("Tuscany");
		organization.setOrgCountry("Italy");
		organization.setOrgZipCode("50014");
		organization.setOrgContactEmail("bonfantini.agnes@telitaly.com");
		organization.setOrgContactLastName("Bonfantini");
		organization.setOrgContactFirstName("Agnes");
		organization.setOrgContactPhone("520-158-1258");
		return organization;
	}
}