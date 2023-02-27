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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import({ActRuTaskSearchRepository.class, TaskEntityDataCreator.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ActRuTaskSearchRepositoryTest {
    @Autowired
    private TaskEntityDataCreator taskEntityDataCreator;

    @Autowired
    private ActRuTaskSearchRepository actRuTaskSearchRepository;

    @Test
    public void shouldReturnPageWithCorrectAssignee() {
        this.taskEntityDataCreator.createAndSaveTask("1", "assignee");
        this.taskEntityDataCreator.createAndSaveTask("2", "assignee");
        this.taskEntityDataCreator.createAndSaveTask("3", "another-assignee");
        val result = this.actRuTaskSearchRepository.search("assignee", null, false, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
    }

    @Test
    public void shouldReturnPageWithCorrectAssigneeAndSearchTerm() {
        this.taskEntityDataCreator.createAndSaveTask("1", "assignee", "searchable-name");
        this.taskEntityDataCreator.createAndSaveTask("2", "assignee");
        this.taskEntityDataCreator.createAndSaveTask("3", "another-assignee");
        this.taskEntityDataCreator.createAndSaveTask("4", "assignee", "SEARCHABLE-name");
        val resultOfLowerCaseSearchQuery = this.actRuTaskSearchRepository.search("assignee", "searchable", false, PageRequest.of(0, 10));
        assertEquals(2, resultOfLowerCaseSearchQuery.getTotalElements());
        assertEquals("searchable-name", resultOfLowerCaseSearchQuery.getContent().get(0).getName());
        assertEquals("SEARCHABLE-name", resultOfLowerCaseSearchQuery.getContent().get(1).getName());

        val resultOfUpperCaseSearchQuery = this.actRuTaskSearchRepository.search("assignee", "SEARCHABLE", false, PageRequest.of(0, 10));
        assertEquals(2, resultOfUpperCaseSearchQuery.getTotalElements());
        assertEquals("searchable-name", resultOfUpperCaseSearchQuery.getContent().get(0).getName());
        assertEquals("SEARCHABLE-name", resultOfUpperCaseSearchQuery.getContent().get(1).getName());

    }

    @Test
    public void shouldReturnPageWithCorrectAssigneeAndFollowUpDate() {
        this.taskEntityDataCreator.createAndSaveTask("1", "assignee", null, null, null, null);
        this.taskEntityDataCreator.createAndSaveTask("2", "assignee", null, null, null, "2022-01-01");
        this.taskEntityDataCreator.createAndSaveTask("3", "assignee", null, null, null, "3333-01-01");
        val result = this.actRuTaskSearchRepository.search("assignee", null, true, PageRequest.of(0, 10));
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
        this.taskEntityDataCreator.createAndSaveTask("1", "assignee", "a-name");
        this.taskEntityDataCreator.createAndSaveTask("2", "assignee", "b-name");
        this.taskEntityDataCreator.createAndSaveTask("3", "assignee", "c-name");
        val ascResult = this.actRuTaskSearchRepository.search("assignee", null, false, PageRequest.of(0, 10, Sort.by(Sort.Order.asc("name"))));
        assertEquals(3, ascResult.getTotalElements());
        assertEquals("a-name", ascResult.getContent().get(0).getName());
        assertEquals("b-name", ascResult.getContent().get(1).getName());
        assertEquals("c-name", ascResult.getContent().get(2).getName());

        val descResult = this.actRuTaskSearchRepository.search("assignee", null, false, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("name"))));
        assertEquals(3, descResult.getTotalElements());
        assertEquals("c-name", descResult.getContent().get(0).getName());
        assertEquals("b-name", descResult.getContent().get(1).getName());
        assertEquals("a-name", descResult.getContent().get(2).getName());
    }

    @Test
    public void shouldReturnPageWithCorrectAssigneeAndABlankSearchTerm() {
        this.taskEntityDataCreator.createAndSaveTask("1", "assignee");
        this.taskEntityDataCreator.createAndSaveTask("2", "assignee");
        this.taskEntityDataCreator.createAndSaveTask("3", "another-assignee");
        val result = this.actRuTaskSearchRepository.search("assignee", " ", false, PageRequest.of(0, 10));
        assertEquals(2, result.getTotalElements());
        assertEquals("name-1", result.getContent().get(0).getName());
        assertEquals("name-2", result.getContent().get(1).getName());
    }

}
