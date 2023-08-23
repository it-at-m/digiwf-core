package io.muenchendigital.digiwf.okewo.integration.service;

import io.muenchendigital.digiwf.okewo.integration.gen.model.BenutzerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertiesServiceTest {

    private final PropertiesService propertiesService = new PropertiesService("benutzerId");

    @Test
    void getBenutzerTypeWithBenutzerId() {
        final BenutzerType expected = new BenutzerType();
        expected.setBenutzerId("benutzerId");

        assertThat(this.propertiesService.getBenutzerTypeWithBenutzerId(), is(expected));
    }

}
