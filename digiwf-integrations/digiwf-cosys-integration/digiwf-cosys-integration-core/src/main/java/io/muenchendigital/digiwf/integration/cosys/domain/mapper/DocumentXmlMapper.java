package io.muenchendigital.digiwf.integration.cosys.domain.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.muenchendigital.digiwf.integration.cosys.domain.model.DocumentXml;

import java.io.IOException;
import java.util.Map.Entry;

public class DocumentXmlMapper extends JsonSerializer<DocumentXml> {

    @Override
    public void serialize(final DocumentXml dataSourceXml, final JsonGenerator generator, final SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        generator.writeStartObject("root");

        generator.writeObjectFieldStart("multi");
        generator.writeObjectField("processData", "XML:$(this);//data");
        generator.writeEndObject();

        generator.writeObjectFieldStart("data");
        for (final Entry<String, String> processVariable : dataSourceXml.getProcessVariables().entrySet()) {
            generator.writeObjectField(processVariable.getKey(), processVariable.getValue());
        }
        generator.writeEndObject();
        generator.writeEndObject();
    }

}
