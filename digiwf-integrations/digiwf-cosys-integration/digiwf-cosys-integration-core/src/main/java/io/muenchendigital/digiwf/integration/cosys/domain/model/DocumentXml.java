package io.muenchendigital.digiwf.integration.cosys.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.muenchendigital.digiwf.integration.cosys.domain.mapper.DocumentXmlMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@JacksonXmlRootElement(localName = "root")
@JsonSerialize(using = DocumentXmlMapper.class)
public class DocumentXml {

    private Map<String, String> processVariables = new HashMap<>();

    public void addProcessVariable(final String key, final String value) {
        if (StringUtils.isBlank(key) || value == null) {
            return;
        }

        this.processVariables.put(key, value);
    }

    public String toXml() throws JsonProcessingException {
        return new XmlMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(this);
    }

}
