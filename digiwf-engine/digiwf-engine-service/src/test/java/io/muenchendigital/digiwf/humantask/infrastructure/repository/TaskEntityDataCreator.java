package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import io.muenchendigital.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import io.muenchendigital.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.Date;

@Component
@AllArgsConstructor
public class TaskEntityDataCreator {
    @Autowired
    private TestEntityManager entityManager;

    public void createAndSaveEntity(final String id, final String assignee) {
        createAndSaveEntity(id, assignee, null, null, null, null);
    }

    public void createAndSaveEntity(final String id, final String assignee, @Nullable final String name) {
        createAndSaveEntity(id, assignee, name, null, null, null);
    }

    public void createAndSaveEntity(final String id, final String assignee, @Nullable final String name, @Nullable final String description, @Nullable final String definitionName, @Nullable final String followUpDate) {
        val taskInfoEntity = TaskInfoEntity.builder()
                .id(id)
                .description(description != null ? description : "description-" + id)
                .definitionName(definitionName != null ? definitionName : "description-" + id)
                .assignee("assignee")
                .instanceId("intanceId")
                .build();
        entityManager.persist(taskInfoEntity);
        val actRuTaskEntity = ActRuTaskEntity.builder()
                .id(id)
                .assignee(assignee)
                .name(name != null ? name : "name-" + id)
                .createdAt(Date.valueOf("2022-12-20"))
                .followUpDate(followUpDate != null ? Date.valueOf(followUpDate) : null)
                .taskInfoEntity(taskInfoEntity)
                .build();
        entityManager.persist(actRuTaskEntity);
        entityManager.flush();
    }
}
