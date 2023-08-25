package de.muenchen.oss.digiwf.process.instance.infrastructure.repository;

import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity;
import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProcessInstanceInfoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProcessInstanceInfoRepository processInstanceInfoRepository;


    @Test
    void findAllUserId() {
        createAndSaveProcessInstance(1);
        createAndSaveProcessInstance(2);
        createAndSaveProcessInstance(3);
        createAndSaveProcessInstance(4);
        createAndSaveProcessInstance(5);

        createAndSaveProcessAuthInstance(1, 1);
        createAndSaveProcessAuthInstance(2, 1);
        createAndSaveProcessAuthInstance(3, 1);

        createAndSaveProcessAuthInstance(1, 2);
        createAndSaveProcessAuthInstance(2, 2);

        createAndSaveProcessAuthInstance(3, 3);
        createAndSaveProcessAuthInstance(4, 3);


        val firstPageOfUser1 = processInstanceInfoRepository.findAllByUserId("user-1", PageRequest.of(0, 2));
        assertEquals(3, firstPageOfUser1.getTotalElements());
        assertEquals("instance-1", firstPageOfUser1.getContent().get(0).getInstanceId());
        assertEquals("instance-2", firstPageOfUser1.getContent().get(1).getInstanceId());

        val secondPageOfUser1 = processInstanceInfoRepository.findAllByUserId("user-1", PageRequest.of(1, 2));
        assertEquals("instance-3", secondPageOfUser1.getContent().get(0).getInstanceId());

        val firstPageOfUser2 = processInstanceInfoRepository.findAllByUserId("user-2", PageRequest.of(0, 2));
        assertEquals(2, firstPageOfUser2.getTotalElements());
        assertEquals("instance-1", firstPageOfUser2.getContent().get(0).getInstanceId());
        assertEquals("instance-2", firstPageOfUser2.getContent().get(1).getInstanceId());


        val firstPageOfUser3 = processInstanceInfoRepository.findAllByUserId("user-3", PageRequest.of(0, 2));
        assertEquals(2, firstPageOfUser3.getTotalElements());
        assertEquals("instance-3", firstPageOfUser3.getContent().get(0).getInstanceId());
        assertEquals("instance-4", firstPageOfUser3.getContent().get(1).getInstanceId());
    }

    @Test
    void searchAllByUserId() {
        createAndSaveProcessInstance(1);
        createAndSaveProcessInstance(2);
        createAndSaveProcessInstance(3);
        createAndSaveProcessInstance(4);
        createAndSaveProcessInstance(5);

        createAndSaveProcessAuthInstance(1, 1);
        createAndSaveProcessAuthInstance(2, 1);
        createAndSaveProcessAuthInstance(3, 1);

        createAndSaveProcessAuthInstance(1, 2);
        createAndSaveProcessAuthInstance(2, 2);

        createAndSaveProcessAuthInstance(3, 3);
        createAndSaveProcessAuthInstance(4, 3);

        val searchResultOfServiceByInstanceIdPart = processInstanceInfoRepository.searchAllByUserId("ce-1", "user-1", PageRequest.of(0, 2));
        assertEquals(1, searchResultOfServiceByInstanceIdPart.getTotalElements());
        assertEquals("instance-1", searchResultOfServiceByInstanceIdPart.getContent().get(0).getInstanceId());

        val searchResultOfDefinitionNameSearch = processInstanceInfoRepository.searchAllByUserId("definitionName".toLowerCase(), "user-1", PageRequest.of(0, 2));
        assertEquals(3, searchResultOfDefinitionNameSearch.getTotalElements());
        assertEquals("instance-1", searchResultOfDefinitionNameSearch.getContent().get(0).getInstanceId());
        assertEquals("instance-2", searchResultOfDefinitionNameSearch.getContent().get(1).getInstanceId());
    }

    private void createAndSaveProcessInstance(int idSuffix) {
        val process = ServiceInstanceEntity.builder()
                .instanceId("instance-" + idSuffix)
                .definitionKey("definition-key-" + idSuffix)
                .definitionName("definitionName")
                .startTime(Date.valueOf(LocalDate.now()))
                .build();
        entityManager.persistAndFlush(process);
    }

    private void createAndSaveProcessAuthInstance(int processIdSuffix, int userIdSuffix) {
        val processAuth = ServiceInstanceAuthorizationEntity.builder()
                .userId("user-" + userIdSuffix)
                .processInstanceId("instance-" + processIdSuffix)
                .build();
        entityManager.persistAndFlush(processAuth);
    }
}
