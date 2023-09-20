package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.BenutzerType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PropertiesServiceTemplateTest {

    private final PropertiesServiceTemplate propertiesServiceTemplate = new PropertiesServiceTemplate("benutzerId");

    @Test
    void getBenutzerTypeWithBenutzerId() {
        final BenutzerType expected = new BenutzerType();
        expected.setBenutzerId("benutzerId");

        assertThat(this.propertiesServiceTemplate.getBenutzerTypeWithBenutzerId(), is(expected));
    }

}
