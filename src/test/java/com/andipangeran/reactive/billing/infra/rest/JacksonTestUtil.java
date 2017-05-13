package com.andipangeran.reactive.billing.infra.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vavr.jackson.datatype.VavrModule;

/**
 * [Documentation]
 *
 * @author Andi Pangeran
 */
public class JacksonTestUtil {

    private static final ObjectMapper OBJECT_MAPPER = defaultObjectMapper();

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.registerModule(new VavrModule());
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        return objectMapper;
    }

    public static String writeValueAsString(Object value) {
        String val = null;
        try {
            val = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return val;
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        Object data = null;
        try {
            data = OBJECT_MAPPER.readValue(content, valueType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (T) data;
    }
}