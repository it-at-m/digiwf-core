package de.muenchen.oss.digiwf.cockpit.security.authorization;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.springframework.lang.NonNull;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;
import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;

@Slf4j
public class AuthorizationHelper {

  public static void setupGroupAppPermissions(@NonNull AuthorizationService authorizationService, @NonNull String groupId) {
    if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.APPLICATION)) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up Web App Permissions for group '{}'", groupId);

    val appAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    appAuth.setGroupId(groupId);
    appAuth.addPermission(Permissions.ACCESS);
    appAuth.setResource(Resources.APPLICATION);
    appAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(appAuth);

  }

  public static void setupGroupAuthorizationPermissions(@NonNull AuthorizationService authorizationService, @NonNull String groupId) {
    if (existsByGroupIdAndResourceType(authorizationService, groupId, Resources.AUTHORIZATION)) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up Authorization Permissions for group '{}'", groupId);

    val authorizationAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    authorizationAuth.setGroupId(groupId);
    authorizationAuth.addPermission(Permissions.ALL);
    authorizationAuth.setResource(Resources.AUTHORIZATION);
    authorizationAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(authorizationAuth);
  }

  public static void setupGroupGroupPermissions(@NonNull AuthorizationService authorizationService, @NonNull String groupId) {
    if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.GROUP).count() != 0) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up Group Permissions for group '{}'", groupId);

    val groupAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    groupAuth.setGroupId(groupId);
    groupAuth.addPermission(Permissions.ALL);
    groupAuth.setResource(Resources.GROUP);
    groupAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(groupAuth);
  }

  public static void setupGroupGroupMembershipPermissions(@NonNull AuthorizationService authorizationService, @NonNull String groupId) {
    if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.GROUP_MEMBERSHIP).count() != 0) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up Group Membership Permissions for group '{}'", groupId);

    val groupAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    groupAuth.setGroupId(groupId);
    groupAuth.addPermission(Permissions.ALL);
    groupAuth.setResource(Resources.GROUP_MEMBERSHIP);
    groupAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(groupAuth);
  }
  public static void setupGroupUserPermissions(@NonNull AuthorizationService authorizationService, @NonNull String groupId) {
    if (authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(Resources.USER).count() != 0) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up User Permissions for group '{}'", groupId);

    val groupAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    groupAuth.setGroupId(groupId);
    groupAuth.addPermission(Permissions.ALL);
    groupAuth.setResource(Resources.USER);
    groupAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(groupAuth);
  }

  public static void setupUserBatchPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.BATCH)) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up Batch Permissions for user '{}'", userId);

    val batchAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    batchAuth.setUserId(userId);
    batchAuth.addPermission(Permissions.CREATE);
    batchAuth.addPermission(Permissions.READ);
    batchAuth.addPermission(Permissions.DELETE);
    batchAuth.addPermission(Permissions.UPDATE);
    batchAuth.setResource(Resources.BATCH);
    batchAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(batchAuth);
  }

  public static void setupUserAppPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.APPLICATION)) {
      // there are permissions present, avoid initialization
      return;
    }

    log.info("Setting up Web App Permissions for user '{}'", userId);

    val appAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    appAuth.setUserId(userId);
    appAuth.addPermission(Permissions.ACCESS);
    appAuth.setResource(Resources.APPLICATION);
    appAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(appAuth);
  }

  public static void setupUserTaskPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.TASK)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Task Permissions for user '{}'", userId);
    val taskAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    taskAuth.setUserId(userId);
    taskAuth.addPermission(Permissions.ALL);
    taskAuth.setResource(Resources.TASK);
    taskAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(taskAuth);
  }

  public static void setupUserProcessDefinitionPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.PROCESS_DEFINITION)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Process Definition Permissions for user '{}'", userId);

    val processDefinitionAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    processDefinitionAuth.setUserId(userId);
    processDefinitionAuth.addPermission(Permissions.ALL);
    processDefinitionAuth.setResource(Resources.PROCESS_DEFINITION);
    processDefinitionAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(processDefinitionAuth);
  }

  public static void setupUserHistoricProcessInstancePermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.HISTORIC_PROCESS_INSTANCE)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Historic Process Instance Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.HISTORIC_PROCESS_INSTANCE);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserDashboardPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.DASHBOARD)) {
      return;
    }

    log.info("Setting up Camunda Dashboard Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.DASHBOARD);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserOpLogPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.REPORT)) {
      return;
    }
    if (authorizationService.createAuthorizationQuery().userIdIn(userId).resourceType(Resources.OPERATION_LOG_CATEGORY).count() != 0) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Operation Log Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.OPERATION_LOG_CATEGORY);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserReportPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.REPORT)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Report Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.REPORT);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserDeploymentPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.DEPLOYMENT)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Deployment Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.DEPLOYMENT);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserDecisionRequirementPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.DECISION_REQUIREMENTS_DEFINITION)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda DRD Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.DECISION_REQUIREMENTS_DEFINITION);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserDecisionPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.DECISION_DEFINITION)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Decision Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.DECISION_DEFINITION);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserSystemPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.SYSTEM)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda System Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.SYSTEM);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }

  public static void setupUserHistoricTaskPermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if (existsByUserIdAndResourceType(authorizationService, userId, Resources.HISTORIC_TASK)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Historic Task Permissions for user '{}'", userId);
    val historicProcessInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    historicProcessInstanceAuth.setUserId(userId);
    historicProcessInstanceAuth.addPermission(Permissions.ALL);
    historicProcessInstanceAuth.setResource(Resources.HISTORIC_TASK);
    historicProcessInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(historicProcessInstanceAuth);
  }


  public static void setupUserProcessInstancePermissions(@NonNull AuthorizationService authorizationService, @NonNull String userId) {
    if(existsByUserIdAndResourceType(authorizationService, userId, Resources.PROCESS_INSTANCE)) {
      // there are permissions present, avoid initialization
      return;
    }
    log.info("Setting up Camunda Process Instance Permissions for user '{}'", userId);
    val processInstanceAuth = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
    processInstanceAuth.setUserId(userId);
    processInstanceAuth.addPermission(Permissions.ALL);
    processInstanceAuth.setResource(Resources.PROCESS_INSTANCE);
    processInstanceAuth.setResourceId(ANY);
    authorizationService.saveAuthorization(processInstanceAuth);
  }

  private static boolean existsByUserIdAndResourceType(@NonNull AuthorizationService authorizationService, @NonNull String userId, @NonNull Resources resources) {
    return authorizationService.createAuthorizationQuery().userIdIn(userId).resourceType(resources).count() != 0;
  }

  private static boolean existsByGroupIdAndResourceType(@NonNull AuthorizationService authorizationService, @NonNull String groupId, @NonNull Resources resources) {
    return authorizationService.createAuthorizationQuery().groupIdIn(groupId).resourceType(resources).count() != 0;
  }
}
