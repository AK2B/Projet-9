package com.microservice.assessment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Custom deserializer for converting JSON array or single value to a List of
 * Strings.
 */
public class CustomStringListDeserializer extends StdDeserializer<List<String>> {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for CustomStringListDeserializer.
	 */
	public CustomStringListDeserializer() {
		this(null);
	}

	/**
	 * Constructor for CustomStringListDeserializer.
	 *
	 * @param vc Class handled by this deserializer.
	 */
	public CustomStringListDeserializer(Class<?> vc) {
		super(vc);
	}

	/**
	 * Deserialize JSON array or single value to a List of Strings.
	 *
	 * @param jp   JSON parser.
	 * @param ctxt Deserialization context.
	 * @return List of Strings.
	 * @throws IOException If an I/O error occurs during deserialization.
	 */
	@Override
	public List<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		List<String> result = new ArrayList<>();

		JsonNode node = jp.getCodec().readTree(jp);
		if (node.isArray()) {
			Iterator<JsonNode> elements = node.elements();
			while (elements.hasNext()) {
				JsonNode element = elements.next();
				result.add(element.asText());
			}
		} else {
			result.add(node.asText());
		}

		return result;
	}
}