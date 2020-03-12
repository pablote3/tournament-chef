package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.io.InputStream;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
public class JsonDeserializerTest {

	@Test
	public void testDeserialize() throws IOException {
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/templateClient.json");
		TemplateDTO[] templates = JsonProvider.deserializeJson(TemplateDTO[].class, json);
		Assert.assertEquals(2, templates.length);
	}
}