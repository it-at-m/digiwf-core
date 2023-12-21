package de.muenchen.oss.digiwf.connector.adapter.camunda.rest.in;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CamundaClientConfiguration {

    public final static String MESSAGE_NAME = "app_message_name";
    public final static String TYPE_NAME = "app_type_name";
    public final static String TOPIC_NAME = "app_topic_name";
    
    private final List<String> filteredVariables;

    public List<String> getFilters() {
        final List<String> allFilters = new ArrayList<>();
        allFilters.addAll(this.filteredVariables);
        allFilters.addAll(List.of(MESSAGE_NAME, TOPIC_NAME, TYPE_NAME));
        return allFilters;
    }
}
