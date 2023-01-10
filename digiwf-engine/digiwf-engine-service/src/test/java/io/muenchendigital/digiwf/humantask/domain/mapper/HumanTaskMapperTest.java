package io.muenchendigital.digiwf.humantask.domain.mapper;

import io.muenchendigital.digiwf.humantask.domain.model.ActRuTask;
import io.muenchendigital.digiwf.humantask.domain.model.TaskInfo;
import lombok.val;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HumanTaskMapperTest {

    private final HumanTaskMapper humanTaskMapper = new HumanTaskMapper();

    @Test
    public void shouldMapActRuTaskIfTaskInfoIsExisting() throws Exception{
        val createdDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-10");
        val taskInfo = TaskInfo.builder()
                .id("id")
                .description("description")
                .definitionName("definitionName")
                .assignee("formatted assignee")
                .instanceId("instanceId")
                .build();
        val actRuTask = ActRuTask.builder()
                .id("id")
                .name("name")
                .assignee("assignee")
                .createdAt(createdDate)
                .followUpDate(createdDate)
                .taskInfo(taskInfo)
                .build();

    val humanTask = humanTaskMapper.map2Model(actRuTask);
    assertEquals("formatted assignee", humanTask.getAssigneeFormatted());
    assertEquals("description", humanTask.getDescription());
    assertEquals("definitionName", humanTask.getProcessName());
    assertEquals("2023-01-10", humanTask.getFollowUpDate());
    }

    @Test
    public void shouldMapActRuTaskIfTaskInfoIsNotExisting() {
        val createdDate = DateTime.now().toDate();
        val actRuTask = ActRuTask.builder()
                .id("id")
                .name("name")
                .assignee("assignee")
                .createdAt(createdDate)
                .followUpDate(null)
                .taskInfo(null)
                .build();

        val humanTask = humanTaskMapper.map2Model(actRuTask);
        assertNull(humanTask.getAssigneeFormatted());
        assertNull(humanTask.getDescription());
        assertEquals("", humanTask.getProcessName());
    }
}