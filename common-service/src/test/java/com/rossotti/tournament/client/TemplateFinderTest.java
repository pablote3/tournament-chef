package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.TemplateType;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.model.Event;
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
		Event event = new Event();
		event.setTemplateType(TemplateType.four_x_four_rr_15D_2L);
		TemplateDTO template = templateFinderService.findTemplateType(event);
		Assert.assertNotNull(template);
		Assert.assertEquals(4, template.getGridGroups());
		Assert.assertEquals(4, template.getGridTeams());
		Assert.assertEquals(16, template.getTotalTeams());
		Assert.assertNotNull(template.getRoundDTO());
		Assert.assertNotNull(template.getGameDTO());
	}

	@Test
	public void testLocateTemplate_notFound() {
		Event event = new Event();
		event.setTemplateType(TemplateType.eight_x_two_pp_20D_2L);
		NoSuchEntityException exception = assertThrows(NoSuchEntityException.class, () -> templateFinderService.findTemplateType(event));
		Assert.assertTrue(exception.getMessage().contains("TemplateFinderService does not exist"));
	}
}