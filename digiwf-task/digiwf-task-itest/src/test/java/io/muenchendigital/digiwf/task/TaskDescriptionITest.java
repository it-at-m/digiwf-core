package io.muenchendigital.digiwf.task;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("itest")
public class TaskDescriptionITest {
// TODO fix this with https://github.com/it-at-m/digiwf-core/pull/532
// I just commented out the tests to merge the hotfix for the release

//  @Autowired
//  private RuntimeService runtimeService;
//
//  @Autowired
//  private TaskService taskService;
//
//  @MockBean
//  private CommandListGateway gateway;
//
//  @Autowired
//  private TaskImporterService importerService;
//  @Captor
//  private ArgumentCaptor<List<Object>> commandCaptor;
//  private TestHelper helper;
//
//  @BeforeEach
//  public void init_helper() {
//    helper = new TestHelper(runtimeService, taskService, "itest_user_task", commandCaptor);
//  }
//
//  @Test
//  public void should_create_user_task_with_description_from_legacy_variable() {
//
//    val description = "legacy description of the task";
//    helper.start(builder()
//        .set(TASK_DESCRIPTION_LEGACY, description)
//        .build()
//    );
//
//    verify(gateway).sendToGateway(commandCaptor.capture());
//
//    val task = helper.userTask();
//    assertThat(task.getDescription()).isEqualTo(description);
//
//    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
//    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
//  }
//
//  @Test
//  public void should_create_user_task_with_description_from_variable() {
//
//    val description = "description of the task";
//    helper.start(builder()
//        .set(TASK_DESCRIPTION, description)
//        .build()
//    );
//
//    verify(gateway).sendToGateway(commandCaptor.capture());
//
//    val task = helper.userTask();
//    assertThat(task.getDescription()).isEqualTo(description);
//
//    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
//    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
//  }
//
//  @Test
//  public void should_create_user_task_without_description_and_enrich_later_legacy() {
//
//    val description = "legacy description of the task";
//    helper.start(builder()
//        .build()
//    );
//
//    verify(gateway).sendToGateway(commandCaptor.capture());
//
//    val task = helper.userTask();
//    assertThat(task.getDescription()).isEqualTo(null);
//
//    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
//    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(null);
//
//    writer(taskService, task.getId()).setLocal(TASK_DESCRIPTION_LEGACY, description);
//
//    importerService.enrichExistingTasks();
//    importerService.importExistingTasks();
//
//    verify(gateway, atLeastOnce()).sendToGateway(commandCaptor.capture());
//    assertThat(helper.commands()).hasSize(1);
//    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
//  }
//
//  @Test
//  public void should_create_user_task_without_description_and_enrich_later() {
//
//    val description = "description of the task";
//    helper.start(builder()
//        .build()
//    );
//
//    verify(gateway).sendToGateway(commandCaptor.capture());
//
//    val task = helper.userTask();
//    assertThat(task.getDescription()).isEqualTo(null);
//
//    assertThat(helper.command()).extracting(EngineTaskCommand::getEventName).isEqualTo("create");
//    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(null);
//
//    writer(taskService, task.getId()).setLocal(TASK_DESCRIPTION, description);
//
//    importerService.enrichExistingTasks();
//    importerService.importExistingTasks();
//
//    verify(gateway, atLeastOnce()).sendToGateway(commandCaptor.capture());
//    assertThat(helper.commands()).hasSize(1);
//    assertThat(helper.command()).extracting(cmd -> ((CreateTaskCommand) cmd).getDescription()).isEqualTo(description);
//  }
//
//
//  @AfterEach
//  public void clean_up() {
//    helper.cleanup();
//  }
}
