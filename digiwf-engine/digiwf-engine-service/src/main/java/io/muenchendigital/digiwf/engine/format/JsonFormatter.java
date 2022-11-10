package io.muenchendigital.digiwf.engine.format;

import org.camunda.bpm.engine.impl.context.Context;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

@Component("J")
public class JsonFormatter {

    public Object list(final String variable) {
        final JSONArray value = (JSONArray) Context.getBpmnExecutionContext().getExecution().getVariableTyped(variable).getValue();
        return value.toList();
    }

}
