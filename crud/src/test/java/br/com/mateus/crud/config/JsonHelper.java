package br.com.mateus.crud.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHelper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonHelper.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            LOG.info("Converted json {} ", json);
            return json;
        } catch (JsonProcessingException ex) {
            LOG.error("Error to convert object, return an empty object", ex);
            return "{}";
        }
    }
}
