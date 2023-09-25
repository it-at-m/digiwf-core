package de.muenchen.oss.digiwf.camunda.connector.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CamundaOutputConfiguration {

    private final List<String> filteredVariables;

    public final static String MESSAGE_NAME = "app_message_name";
    public final static String TYPE_NAME = "app_type_name";
    public final static String TOPIC_NAME = "app_topic_name";

    public List<String> getFilteredVariables() {
        final List<String> allFilters = new ArrayList<>();
        allFilters.addAll(this.filteredVariables);
        allFilters.addAll(List.of(MESSAGE_NAME, TOPIC_NAME, TYPE_NAME));
        return allFilters;
    }
}
