package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateFinderService {

	public void testDeserialize_toMap() throws IOException {
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/templateClient.json");
		TemplateDTO[] templates = JsonProvider.deserializeJson(TemplateDTO[].class, json);
		int i;
		TemplateDTO templateDTO;
		Map<String, Object> map = new HashMap<>();
		for (i = 0; i < templates.length; i++) {
			templateDTO = templates[i];
			map.put(templateDTO.getTemplateType().toString(), templateDTO);
		}
	}
}
