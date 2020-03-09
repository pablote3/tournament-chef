package com.rossotti.tournament.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.rossotti.tournament.model.Template;
import org.springframework.boot.jackson.JsonComponent;
import java.io.*;

@JsonComponent
public class TemplateJsonDeserializer extends JsonDeserializer<Template> {

	@Override
	public Template deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
		TextNode template = (TextNode) treeNode.get("template");
		System.out.println("made it");
		return null;
	}
}