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

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ActRuTaskSearchRepository.class, TaskEntityDataCreator.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ActRuTaskSearchRepositoryTest {
    @Autowired
    private TaskEntityDataCreator taskEntityDataCreator;

    @Autowired
    private ActRuTaskSearchRepository actRuTaskSearchRepository;

    @Test
    public void shouldReturnPageWithCorrectAssignee() {
        taskEntityDataCreator.createAndSaveTask("1", "assignee");
        taskEntityDataCreator.createAndSaveTask("2", "assignee");
        taskEntityDataCreator.createAndSaveTask("3", "another-assignee");
        val result = actRuTaskSearchRepository.search("assignee", null, false, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
    }

    @Test
    public void shouldReturnPageWithCorrectAssigneeAndSearchTerm() {
        taskEntityDataCreator.createAndSaveTask("1", "assignee", "searchable-name");
        taskEntityDataCreator.createAndSaveTask("2", "assignee");
        taskEntityDataCreator.createAndSaveTask("3", "another-assignee");
        val result = actRuTaskSearchRepository.search("assignee", "searchable", false, PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
        assertEquals("searchable-name", result.getContent().get(0).getName());
    }
    @Test
    public void shouldReturnPageWithCorrectAssigneeAndFollowUpDate() {
        taskEntityDataCreator.createAndSaveTask("1", "assignee", null, null, null, null);
        taskEntityDataCreator.createAndSaveTask("2", "assignee", null, null, null, "2022-01-01");
        taskEntityDataCreator.createAndSaveTask("3", "assignee", null, null, null, "3333-01-01");
        val result = actRuTaskSearchRepository.search("assignee", null, true, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        val firstElement = result.getContent().get(0);
        val secondElement = result.getContent().get(1);
        assertEquals("1", firstElement.getId());
        assertNull(firstElement.getFollowUpDate());

        assertEquals("2", secondElement.getId());
        assertEquals(Date.valueOf("2022-01-01"), secondElement.getFollowUpDate());
    }

    @Test
    public void shouldReturnPageWithCorrectOrder() {
        taskEntityDataCreator.createAndSaveTask("1", "assignee", "a-name");
        taskEntityDataCreator.createAndSaveTask("2", "assignee", "b-name");
        taskEntityDataCreator.createAndSaveTask("3", "assignee", "c-name");
        val ascResult = actRuTaskSearchRepository.search("assignee", null, false, PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name"))));
        assertEquals(3, ascResult.getTotalElements());
        assertEquals("a-name", ascResult.getContent().get(0).getName());
        assertEquals("b-name", ascResult.getContent().get(1).getName());
        assertEquals("c-name", ascResult.getContent().get(2).getName());

        val descResult = actRuTaskSearchRepository.search("assignee", null, false, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("name"))));
        assertEquals(3, descResult.getTotalElements());
        assertEquals("c-name", descResult.getContent().get(0).getName());
        assertEquals("b-name", descResult.getContent().get(1).getName());
        assertEquals("a-name", descResult.getContent().get(2).getName());
    }

    @Test
    public void shouldReturnPageWithCorrectAssigneeAndABlankSearchTerm() {
        taskEntityDataCreator.createAndSaveTask("1", "assignee");
        taskEntityDataCreator.createAndSaveTask("2", "assignee");
        taskEntityDataCreator.createAndSaveTask("3", "another-assignee");
        val result = actRuTaskSearchRepository.search("assignee", " ", false, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
    }

}
