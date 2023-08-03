package de.muenchen.oss.digiwf.process.instance.api.mapper;

import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessInstancePageMapperTest {

    private final ProcessInstancePageMapper mapper = new ProcessInstancePageMapper();

    private final List<ServiceInstance> instances = List.of(
            createServiceInstance(1, "A"),
            createServiceInstance(2, "A"),
            createServiceInstance(3, "B"),
            createServiceInstance(4, "B"),
            createServiceInstance(5, "B"),
            createServiceInstance(6, "C"),
            createServiceInstance(7, "C"),
            createServiceInstance(8, "D")
    );

    @Test
    public void shouldSplitPageCorrectly() {

        val resultOfFirstPage = mapper.toPage(instances, 0, 3, null);
        assertEquals(8, resultOfFirstPage.getTotalElements());
        assertEquals(0, resultOfFirstPage.getNumber());
        assertEquals(3, resultOfFirstPage.getSize());
        assertEquals(3, resultOfFirstPage.getTotalPages());
        assertArrayEquals(List.of("id-1", "id-2", "id-3").toArray(), resultOfFirstPage.getContent().stream().map(ServiceInstance::getId).toArray());

        val resultOfSecondPage = mapper.toPage(instances, 1, 3, null);
        assertEquals(8, resultOfSecondPage.getTotalElements());
        assertEquals(1, resultOfSecondPage.getNumber());
        assertEquals(3, resultOfSecondPage.getSize());
        assertEquals(3, resultOfFirstPage.getTotalPages());
        assertArrayEquals(List.of("id-4", "id-5", "id-6").toArray(), resultOfSecondPage.getContent().stream().map(ServiceInstance::getId).toArray());
    }

    @Test
    public void shouldSearchCorrectlyInProperties() {
        val resultOfDescriptionSearch = mapper.toPage(instances, 0, 3, "-A");
        assertEquals(2, resultOfDescriptionSearch.getTotalElements());
        assertEquals(0, resultOfDescriptionSearch.getNumber());
        assertArrayEquals(List.of("id-1", "id-2").toArray(), resultOfDescriptionSearch.getContent().stream().map(ServiceInstance::getId).toArray());

        val resultOfNameSearch = mapper.toPage(instances, 0, 3, "name-7");
        assertEquals(1, resultOfNameSearch.getTotalElements());
        assertEquals(0, resultOfNameSearch.getNumber());
        assertArrayEquals(List.of("id-7").toArray(), resultOfNameSearch.getContent().stream().map(ServiceInstance::getId).toArray());

        val resultOfNonSuccessfullySearch = mapper.toPage(instances, 0, 3, "does-not-exist");
        assertEquals(0, resultOfNonSuccessfullySearch.getTotalElements());
        assertEquals(0, resultOfNonSuccessfullySearch.getNumber());
        assertEquals(0, resultOfNonSuccessfullySearch.getContent().size());
    }

    private ServiceInstance createServiceInstance(int id, String descriptionSuffix) {
        return new ServiceInstance(
                "id-" + id,
                "instance-key-" + id,
                "definition-name-" + id,
                "definition-key-" + id,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                "status",
                "status-key-1",
                "description-" + descriptionSuffix
        );
    }
}
