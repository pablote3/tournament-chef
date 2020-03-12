package com.rossotti.tournament.client;

import com.rossotti.tournament.dto.TemplateDTO;
import com.rossotti.tournament.exception.NoSuchEntityException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateFinderService {

	private static final Map<String, Object> templates = new HashMap<>();

	public TemplateDTO locateTemplate(String templateType) throws Exception {
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
		InputStream json = this.getClass().getClassLoader().getResourceAsStream("mockClient/templateClient.json");
		TemplateDTO[] temp = JsonProvider.deserializeJson(TemplateDTO[].class, json);
		TemplateDTO templateDTO;

		for (TemplateDTO dto : temp) {
			templateDTO = dto;
			templates.put(templateDTO.getTemplateType().toString(), templateDTO);
		}
	}
}