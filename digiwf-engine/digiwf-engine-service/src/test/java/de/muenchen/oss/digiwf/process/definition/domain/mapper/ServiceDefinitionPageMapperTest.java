package de.muenchen.oss.digiwf.process.definition.domain.mapper;

import de.muenchen.oss.digiwf.process.definition.domain.model.ServiceDefinition;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceDefinitionPageMapperTest {

    private final ServiceDefinitionPageMapper mapper = new ServiceDefinitionPageMapper();

    private final List<ServiceDefinition> definitions = List.of(
            new ServiceDefinition("key-1", "name-1", "description-A", "1.0.0"),
            new ServiceDefinition("key-2", "name-2", "description-A", "1.0.0"),
            new ServiceDefinition("key-3", "name-3", "description-B", "1.0.0"),
            new ServiceDefinition("key-4", "name-4", "description-B", "1.0.0"),
            new ServiceDefinition("key-5", "name-5", "description-C", "1.0.0"),
            new ServiceDefinition("key-6", "name-6", "description-D", "1.0.0"),
            new ServiceDefinition("key-7", "name-7", "description-D", "1.0.0"),
            new ServiceDefinition("key-8", "name-8", "description-D", "1.0.0"));

    @Test
    public void shouldSplitPageCorrectly() {

        val resultOfFirstPage = mapper.toPage(definitions, 0, 3, null);
        assertEquals(8, resultOfFirstPage.getTotalElements());
        assertEquals(0, resultOfFirstPage.getNumber());
        assertEquals(3, resultOfFirstPage.getSize());
        assertEquals(3, resultOfFirstPage.getTotalPages());
        assertArrayEquals(List.of("key-1", "key-2", "key-3").toArray(), resultOfFirstPage.getContent().stream().map(ServiceDefinition::getKey).toArray());

        val resultOfSecondPage = mapper.toPage(definitions, 1, 3, null);
        assertEquals(8, resultOfSecondPage.getTotalElements());
        assertEquals(1, resultOfSecondPage.getNumber());
        assertEquals(3, resultOfSecondPage.getSize());
        assertEquals(3, resultOfFirstPage.getTotalPages());
        assertArrayEquals(List.of("key-4", "key-5", "key-6").toArray(), resultOfSecondPage.getContent().stream().map(ServiceDefinition::getKey).toArray());
    }

    @Test
    public void shouldSearchCorrectlyInProperties() {
        val resultOfDescriptionSearch = mapper.toPage(definitions, 0, 3, "-A");
        assertEquals(2, resultOfDescriptionSearch.getTotalElements());
        assertEquals(0, resultOfDescriptionSearch.getNumber());
        assertArrayEquals(List.of("key-1", "key-2").toArray(), resultOfDescriptionSearch.getContent().stream().map(ServiceDefinition::getKey).toArray());

        val resultOfNameSearch = mapper.toPage(definitions, 0, 3, "name-7");
        assertEquals(1, resultOfNameSearch.getTotalElements());
        assertEquals(0, resultOfNameSearch.getNumber());
        assertArrayEquals(List.of("key-7").toArray(), resultOfNameSearch.getContent().stream().map(ServiceDefinition::getKey).toArray());

        val resultOfNonSuccessfullySearch = mapper.toPage(definitions, 0, 3, "does-not-exist");
        assertEquals(0, resultOfNonSuccessfullySearch.getTotalElements());
        assertEquals(0, resultOfNonSuccessfullySearch.getNumber());
        assertEquals(0, resultOfNonSuccessfullySearch.getContent().size());
    }
}
