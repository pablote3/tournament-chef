package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.exception.NoSuchEntityException;
import com.rossotti.tournament.model.Event;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateFinderService {

	private static Map<String, Object> templates = null;

	public TemplateDTO findTemplateType(Event event) throws Exception {
		String templateType = event.getTemplateType().name();
		if (templates == null) {
			initializeTemplates();
		}
		TemplateDTO template = (TemplateDTO)templates.get(templateType);
		if (template == null) {
			throw new NoSuchEntityException(TemplateFinderService.class);
		}
		else {
			return template;
		}
	}

	private void initializeTemplates() throws IOException {
		templates = new HashMap<>();
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/templateClient.json");
		TemplateDTO[] templateIn = JsonProvider.deserializeJson(TemplateDTO[].class, json);
		TemplateDTO templateDTO;

		for (TemplateDTO dto : templateIn) {
			templateDTO = dto;
			templates.put(templateDTO.getTemplateType().toString(), templateDTO);
		}
	}
}