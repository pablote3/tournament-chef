package com.rossotti.tournament.jpa.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossotti.tournament.model.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

@ActiveProfiles(profiles = "development")
@RunWith(SpringRunner.class)
@JsonTest
public class TemplateJsonDeserializerTest {
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testDeserialize() throws IOException {
		String json = "{\"template\":\"#f0f8ff\"}";
		Template template = objectMapper.readValue(json, Template.class);
//		Assert.assertEquals(Color.ALICEBLUE, user.getFavoriteColor());
	}
}