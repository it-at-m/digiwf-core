package io.muenchendigital.digiwf.taskana.connector.core;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * This class is responsible for configuring the ObjectMapper of Jackson.
 */
public final class JacksonConfigurator {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private JacksonConfigurator() {
    }

    public static ObjectMapper createAndConfigureObjectMapper() {

        final ObjectMapper mapper = new ObjectMapper();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        mapper.setDateFormat(dateFormat);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper;
    }
}
