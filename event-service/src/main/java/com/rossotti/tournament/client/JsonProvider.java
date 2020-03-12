package com.rossotti.tournament.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class JsonProvider {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static <T> T deserializeJson(final Class<T> type, final InputStream json) throws IOException {
//		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		return mapper.readValue(json, type);
	}
}