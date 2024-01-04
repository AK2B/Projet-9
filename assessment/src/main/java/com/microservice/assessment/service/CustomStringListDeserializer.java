package com.microservice.assessment.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomStringListDeserializer extends StdDeserializer<List<String>> {

    public CustomStringListDeserializer() {
        this(null);
    }

    public CustomStringListDeserializer(Class<?> vc) {
        super(vc);
    }

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
