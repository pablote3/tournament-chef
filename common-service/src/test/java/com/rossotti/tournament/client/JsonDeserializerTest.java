package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.enumeration.GroupPlay;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.rossotti.tournament.config.ServiceConfig.class)
public class JsonDeserializerTest {

	private TemplateFinderService templateFinderService;

	@Autowired
	public void setTemplateFinderService(TemplateFinderService templateFinderService) {
		this.templateFinderService = templateFinderService;
	}

	@Test
	public void testDeserialize() {
		try {
			TemplateDTO template = templateFinderService.locateTemplate("four_x_four_rr");
			Assert.assertEquals(GroupPlay.RoundRobin, template.getGroupPlay1());
		}
		catch(Exception e) {

		}
	}
}