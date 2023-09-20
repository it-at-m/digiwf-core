package de.muenchen.oss.digiwf.okewo.integration.service;

import de.muenchen.oss.digiwf.okewo.integration.gen.model.BenutzerType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PropertiesServiceTest {

    private final PropertiesService propertiesService = new PropertiesService("benutzerId");

    @Test
    void getBenutzerTypeWithBenutzerId() {
        final BenutzerType expected = new BenutzerType();
        expected.setBenutzerId("benutzerId");

        assertThat(this.propertiesService.getBenutzerTypeWithBenutzerId(), is(expected));
    }

}
