package de.muenchen.oss.digiwf.process.instance.infrastructure.repository;

import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity;
import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceEntity;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        assertThat(firstPageOfUser1.getTotalElements()).isEqualTo(3);
        assertThat(firstPageOfUser1.getContent().stream().map(ServiceInstanceEntity::getInstanceId).toList())
                .isEqualTo(List.of("instance-1", "instance-2"));

        val secondPageOfUser1 = processInstanceInfoRepository.findAllByUserId("user-1", PageRequest.of(1, 2));
        assertThat(secondPageOfUser1.getContent().stream().map(ServiceInstanceEntity::getInstanceId).toList())
                .isEqualTo(List.of("instance-3"));

        val firstPageOfUser2 = processInstanceInfoRepository.findAllByUserId("user-2", PageRequest.of(0, 2));
        assertThat(firstPageOfUser2.getTotalElements()).isEqualTo(2);
        assertThat(firstPageOfUser2.getContent().stream().map(ServiceInstanceEntity::getInstanceId).toList())
                .isEqualTo(List.of("instance-1", "instance-2"));

        val firstPageOfUser3 = processInstanceInfoRepository.findAllByUserId("user-3", PageRequest.of(0, 2));
        assertThat(firstPageOfUser3.getTotalElements()).isEqualTo(2);
        assertThat(firstPageOfUser3.getContent().stream().map(ServiceInstanceEntity::getInstanceId).toList())
                .isEqualTo(List.of("instance-3", "instance-4"));
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
        assertThat(searchResultOfServiceByInstanceIdPart.getTotalElements()).isEqualTo(1);
        assertThat(searchResultOfServiceByInstanceIdPart.getContent().stream().map(ServiceInstanceEntity::getInstanceId).toList())
                .isEqualTo(List.of("instance-1"));

        val searchResultOfDefinitionNameSearch = processInstanceInfoRepository.searchAllByUserId("definitionName".toLowerCase(), "user-1", PageRequest.of(0, 2));
        assertThat(searchResultOfDefinitionNameSearch.getTotalElements()).isEqualTo(3);
        assertThat(searchResultOfDefinitionNameSearch.getContent().stream().map(ServiceInstanceEntity::getInstanceId).toList())
                .isEqualTo(List.of("instance-1", "instance-2"));
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
