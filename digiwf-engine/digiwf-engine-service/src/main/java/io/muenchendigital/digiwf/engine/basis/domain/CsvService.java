/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.engine.basis.domain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service to handle CSV data.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
public class CsvService {

    /**
     * @param variables Keys of the data that should be exported
     * @param data      Data that should be exported
     * @return the generated csv string
     * @throws IOException
     */
    public String generateCsv(final List<String> variables, final List<Map<String, Object>> data) throws IOException {
        final StringWriter sw = new StringWriter();
        final CSVPrinter csvPrinter = new CSVPrinter(sw, CSVFormat.EXCEL);
        csvPrinter.printRecord(variables);
        data.forEach(objects -> {
            try {
                csvPrinter.printRecord(this.getRecord(variables, objects));
            } catch (final IOException e) {
                log.error("Record cloud not be created.", e);
                e.printStackTrace();
            }
        });
        return sw.toString();
    }

    /**
     * @param variables Keys of the data that should be exported
     * @param values    Data of the record
     * @return record
     */
    private List<String> getRecord(final List<String> variables, final Map<String, Object> values) {
        return variables.stream()
                .map(variable -> this.getValue(values, variable))
                .collect(Collectors.toList());
    }

    /**
     * Get value for the given data by key.
     * Takes care of escaping etc.
     *
     * @param values   data
     * @param variable key of the variable
     * @return escaped value
     */
    private String getValue(final Map<String, Object> values, final String variable) {
        String value = values.containsKey(variable) && values.get(variable) != null ? values.get(variable).toString() : "";
        value = value.replace('"', '\'');

        //Filter special characters
        if (StringUtils.startsWithAny(value, "=", "+", "-", "@", "|")) {
            return "\t" + value;
        }

        return value;
    }

}
