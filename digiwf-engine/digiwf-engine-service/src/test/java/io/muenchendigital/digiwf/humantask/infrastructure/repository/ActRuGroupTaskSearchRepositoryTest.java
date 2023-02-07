package io.muenchendigital.digiwf.humantask.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ActRuGroupTaskSearchRepository.class, TaskEntityDataCreator.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ActRuGroupTaskSearchRepositoryTest {
    @Autowired
    private TaskEntityDataCreator taskEntityDataCreator;

    @Autowired
    private ActRuGroupTaskSearchRepository actRuGroupTaskSearchRepository;

    @Test
    public void shouldReturnPageOfAssignedTasksWithCorrectAssigneeAndGroup() {
        taskEntityDataCreator.createAndSaveGroupTask("1", "assignee", "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("2", "assignee", "group-2");
        taskEntityDataCreator.createAndSaveGroupTask("3", "another-assignee", "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("4", "assignee", "another-group");
        taskEntityDataCreator.createAndSaveGroupTask("5", "another-assignee", "another-group");
        val result = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1", "group-2"), null, true, PageRequest.of(0, 10));
        assertEquals(4, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
        assertEquals("name-3", result.getContent().get(2).getName());
        assertEquals("name-4", result.getContent().get(3).getName());
    }

    @Test
    public void shouldReturnPageOfAssignedTasksWithCorrectAssigneeAndGroupAndSearchTerm() {
        taskEntityDataCreator.createAndSaveGroupTask("1", "assignee", "group-1", "searchable");
        taskEntityDataCreator.createAndSaveGroupTask("2", "assignee", "group-2");
        taskEntityDataCreator.createAndSaveGroupTask("3", "another-assignee", "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("4", "assignee", "another-group");
        taskEntityDataCreator.createAndSaveGroupTask("5", "another-assignee", "another-group", "searchable");
        val result = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1", "group-2"), "earchabl", true, PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("searchable", result.getContent().get(0).getName());
    }

    @Test
    public void shouldReturnPageOfAssignedTasksWithCorrectAssigneeAndGroupAndCorrectOrder() {
        taskEntityDataCreator.createAndSaveGroupTask("1", "assignee", "group-1", "a-name");
        taskEntityDataCreator.createAndSaveGroupTask("2", "assignee", "group-2", "b-name");
        taskEntityDataCreator.createAndSaveGroupTask("3", "another-assignee", "group-1", "c-name");
        taskEntityDataCreator.createAndSaveGroupTask("4", "assignee", "another-group", "d-name");
        taskEntityDataCreator.createAndSaveGroupTask("5", "another-assignee", "another-group", "e-name");
        val resultAsc = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1", "group-2"), null, true, PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name"))));
        assertEquals(4, resultAsc.getTotalElements());
        assertEquals("a-name", resultAsc.getContent().get(0).getName());
        assertEquals("b-name", resultAsc.getContent().get(1).getName());
        assertEquals("c-name", resultAsc.getContent().get(2).getName());
        assertEquals("d-name", resultAsc.getContent().get(3).getName());
        val resultDesc = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1", "group-2"), null, true, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("name"))));
        assertEquals(4, resultDesc.getTotalElements());
        assertEquals("d-name", resultDesc.getContent().get(0).getName());
        assertEquals("c-name", resultDesc.getContent().get(1).getName());
        assertEquals("b-name", resultDesc.getContent().get(2).getName());
        assertEquals("a-name", resultDesc.getContent().get(3).getName());
    }

    @Test
    public void shouldReturnPageOfUnAssignedTasksWithCorrectGroup() {
        taskEntityDataCreator.createAndSaveGroupTask("1", null, "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("2", null, "group-2");
        taskEntityDataCreator.createAndSaveGroupTask("3", null, "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("4", null, "another-group");
        taskEntityDataCreator.createAndSaveGroupTask("5", null, "another-group");
        val result = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1", "group-2"), null, false, PageRequest.of(0, 10));
        assertEquals(3, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
        assertEquals("name-3", result.getContent().get(2).getName());
    }

    @Test
    public void shouldReturnPageOfAssignedTasksWithCorrectAssigneeAndGroupAndABlankSearchTerm() {
        taskEntityDataCreator.createAndSaveGroupTask("1", "assignee", "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("2", "assignee", "group-2");
        taskEntityDataCreator.createAndSaveGroupTask("3", "another-assignee", "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("4", "assignee", "another-group");
        taskEntityDataCreator.createAndSaveGroupTask("5", "another-assignee", "another-group");
        val result = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1", "group-2"), " ", true, PageRequest.of(0, 10));
        assertEquals(4, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
        assertEquals("name-3", result.getContent().get(2).getName());
        assertEquals("name-4", result.getContent().get(3).getName());
    }

    @Test
    public void shouldBeCaseInsensitiveForUsingGroupsInAssignedTasks() {
        taskEntityDataCreator.createAndSaveGroupTask("1", "assignee", "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("2", "assignee-2", "GROUP-1");
        val result = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1"), null, true, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
    }

    @Test
    public void shouldBeCaseInsensitiveForUsingGroupsInUnAssignedTasks() {
        taskEntityDataCreator.createAndSaveGroupTask("1", null, "group-1");
        taskEntityDataCreator.createAndSaveGroupTask("2", null, "GROUP-1");
        val result = actRuGroupTaskSearchRepository.search("assignee", List.of("group-1"), null, false, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
    }
}
