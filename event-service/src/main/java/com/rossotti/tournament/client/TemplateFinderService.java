package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateFinderService {

	private static Map<String, Object> templates = new HashMap<>();

	public TemplateDTO locateTemplate(String templateType) {
		try {
			if (templates == null) {


				initializeTemplates();
			}
		}
		catch (IOException e) {

		}
		return null;
	}

	private void initializeTemplates() throws IOException {
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/templateClient.json");
		TemplateDTO[] temp = JsonProvider.deserializeJson(TemplateDTO[].class, json);
		TemplateDTO templateDTO;

		for (int i = 0; i < temp.length; i++) {
			templateDTO = temp[i];
			templates.put(templateDTO.getTemplateType().toString(), templateDTO);
		}
	}
}