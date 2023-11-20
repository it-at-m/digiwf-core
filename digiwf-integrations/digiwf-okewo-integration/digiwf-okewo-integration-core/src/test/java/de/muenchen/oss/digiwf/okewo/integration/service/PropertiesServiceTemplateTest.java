package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.BenutzerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PropertiesServiceTemplateTest {

    private final PropertiesServiceTemplate propertiesServiceTemplate = new PropertiesServiceTemplate("benutzerId");

    @Test
    void getBenutzerTypeWithBenutzerId() {
        final BenutzerType expected = new BenutzerType();
        expected.setBenutzerId("benutzerId");

        assertEquals(expected, this.propertiesServiceTemplate.getBenutzerTypeWithBenutzerId());
    }

}
