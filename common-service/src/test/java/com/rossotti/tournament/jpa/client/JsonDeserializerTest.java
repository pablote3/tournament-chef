package com.rossotti.tournament.jpa.client;

import com.rossotti.tournament.client.JsonProvider;
import com.rossotti.tournament.dto.TemplatesDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.io.InputStream;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@JsonTest
public class JsonDeserializerTest {

	@Test
	public void testDeserialize() throws IOException {
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/templateClient.json");
		TemplatesDTO template = JsonProvider.deserializeJson(TemplatesDTO.class, json);
		Assert.assertEquals(2, template.templates.length);
	}
}