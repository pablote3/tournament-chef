package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.exception.NoSuchEntityException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class TemplateFinderTest {

	private TemplateFinderService templateFinderService;

	@Autowired
	public void setTemplateFinderService(TemplateFinderService templateFinderService) {
		this.templateFinderService = templateFinderService;
	}

	@Test
	public void testLocateTemplate_found() throws Exception {
		TemplateDTO template = templateFinderService.findTemplateType(TemplateType.four_x_four_rr_15D_2L.name());
		Assert.assertNotNull(template);
		Assert.assertEquals(4, template.getGridGroups());
		Assert.assertEquals(4, template.getGridTeams());
		Assert.assertEquals(16, template.getTotalTeams());
		Assert.assertNotNull(template.getRoundDTO());
		Assert.assertNotNull(template.getGameDTO());
	}

	@Test
	public void testLocateTemplate_notFound() {
		NoSuchEntityException exception = assertThrows(NoSuchEntityException.class, () -> templateFinderService.findTemplateType("four_x_four_qq"));
		Assert.assertTrue(exception.getMessage().contains("TemplateFinderService does not exist"));
	}
}