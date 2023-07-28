package de.muenchen.oss.digiwf.humantask.infrastructure.repository;

import de.muenchen.oss.digiwf.humantask.infrastructure.entity.TaskInfoEntity;
import de.muenchen.oss.digiwf.humantask.infrastructure.entity.camunda.ActRuIdentityLinkEntity;
import de.muenchen.oss.digiwf.humantask.infrastructure.entity.camunda.ActRuTaskEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class TaskEntityDataCreator {
    @Autowired
    private TestEntityManager entityManager;

    public void createAndSaveTask(final String id, final String assignee) {
        createAndSaveTask(id, assignee, null, null, null, null);
    }

    public void createAndSaveTask(final String id, final String assignee, @Nullable final String name) {
        createAndSaveTask(id, assignee, name, null, null, null);
    }

    public void createAndSaveTask(final String id, final String assignee, @Nullable final String name, @Nullable final String description, @Nullable final String definitionName, @Nullable final String followUpDate) {
        val taskInfoEntity = TaskInfoEntity.builder()
                .id(id)
                .description(description != null ? description : "description-" + id)
                .definitionName(definitionName != null ? definitionName : "description-" + id)
                .assignee(assignee)
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

    public void createAndSaveGroupTask(final String id, final String assignee, final String group) {
        createAndSaveGroupTask(id, assignee, group, null, null, null);
    }

    public void createAndSaveGroupTask(final String id, final String assignee, final String group, @Nullable final String name) {
        createAndSaveGroupTask(id, assignee, group, name, null, null);
    }
    public void createAndSaveGroupTask(final String id, final String assignee, final String group, @Nullable final String name, @Nullable final String description, @Nullable final String definitionName) {
        val taskInfoEntity = TaskInfoEntity.builder()
                .id(id)
                .description(description != null ? description : "description-" + id)
                .definitionName(definitionName != null ? definitionName : "description-" + id)
                .assignee(assignee)
                .instanceId("intanceId")
                .build();
        entityManager.persist(taskInfoEntity);
        val actRuTaskEntity = ActRuTaskEntity.builder()
                .id(id)
                .assignee(assignee)
                .name(name != null ? name : "name-" + id)
                .createdAt(Date.valueOf("2022-12-20"))
                .followUpDate(null)
                .taskInfoEntity(taskInfoEntity)
                .build();
        entityManager.persist(actRuTaskEntity);
        val identityLink = ActRuIdentityLinkEntity.builder()
                .id("identityLink-" + id)
                .actRuTaskEntity(actRuTaskEntity)
                .userId(assignee)
                .groupId(group)
                .type("candidate")
                .build();
        entityManager.persist(identityLink);
        actRuTaskEntity.setActRuIdentities(List.of(identityLink));
        entityManager.persist(actRuTaskEntity);
        entityManager.flush();
    }
}
