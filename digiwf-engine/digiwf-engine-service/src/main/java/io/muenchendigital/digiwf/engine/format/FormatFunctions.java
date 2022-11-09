package io.muenchendigital.digiwf.engine.format;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component("formatter")
public class FormatFunctions {

    public String bool(final String value) {
        if (StringUtils.isBlank(value)) {
            return "false";
        }

        if (value.equalsIgnoreCase("Ja")) {
            return "true";
        }

        if (value.equalsIgnoreCase("Nein")) {
            return "false";
        }
        return value;
    }

    public String street(final String value) {
        return value;
    }

    public String houseNumber(final String value) {
        return value;
    }


}
