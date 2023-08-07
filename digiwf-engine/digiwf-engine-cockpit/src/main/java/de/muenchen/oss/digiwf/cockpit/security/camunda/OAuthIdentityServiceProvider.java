package de.muenchen.oss.digiwf.cockpit.security.camunda;


import de.muenchen.oss.digiwf.spring.security.SecurityConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.identity.*;
import org.camunda.bpm.engine.impl.GroupQueryImpl;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.TenantQueryImpl;
import org.camunda.bpm.engine.impl.UserQueryImpl;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import de.muenchen.oss.digiwf.spring.security.JwtClaims;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * This Identity Provider returns only the currently logged-in user's data. It is required for the camunda apps to work properly with OAuth
 * login. Only the necessary methods of the interface are implemented, most methods just return null or empty things.
 **/
public class OAuthIdentityServiceProvider extends AbstractManager implements ReadOnlyIdentityProvider {

  @Override
  public User findUserById(String userId) {
    return createUserQuery().userId(userId).singleResult();
  }

  @Override
  public UserQuery createUserQuery() {
    return new OAuthUserQueryImpl(this);
  }

  @Override
  public UserQuery createUserQuery(CommandContext commandContext) {
    return new OAuthUserQueryImpl(this);
  }

  @Override
  public NativeUserQuery createNativeUserQuery() {
    return null;
  }

  @Override
  public boolean checkPassword(String userId, String password) {
    return false;
  }

  @Override
  public Group findGroupById(String groupId) {
    return createGroupQuery().groupId(groupId).singleResult();
  }

  @Override
  public GroupQuery createGroupQuery() {
    return new OAuthGroupQueryImpl(this);
  }

  @Override
  public GroupQuery createGroupQuery(CommandContext commandContext) {
    return new OAuthGroupQueryImpl(this);
  }

  @Override
  public Tenant findTenantById(String tenantId) {
    return new NoTenantQueryImpl().tenantId(tenantId).singleResult();
  }

  @Override
  public TenantQuery createTenantQuery() {
    return new NoTenantQueryImpl();
  }

  @Override
  public TenantQuery createTenantQuery(CommandContext commandContext) {
    return new NoTenantQueryImpl();
  }


  private User single(OAuthUserQueryImpl oAuthUserQuery) {
    return getJwtAuthenticationToken().map(
        token ->
            (User) new OAuthUser(
                (String) token.getTokenAttributes().get(JwtClaims.USER_ID),
                (String) token.getTokenAttributes().getOrDefault(JwtClaims.GIVEN_NAME, ""),
                (String) token.getTokenAttributes().getOrDefault(JwtClaims.FAMILY_NAME, token.getTokenAttributes().get(JwtClaims.USER_ID)),
                (String) token.getTokenAttributes().getOrDefault(JwtClaims.EMAIL, token.getTokenAttributes().get(JwtClaims.USER_ID))
            )
    ).orElse(null);
  }

  private Group single(OAuthGroupQueryImpl oAuthGroupQuery) {
    return list(oAuthGroupQuery)
        .stream()
        .filter(group -> group.getId().equals(oAuthGroupQuery.getId()))
        .findFirst().orElse(null);
  }

  private List<Group> list(OAuthGroupQueryImpl oAuthGroupQuery) {
    return getJwtAuthenticationToken()
        .map(token -> token.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .map(role -> StringUtils.removeStart(role, SecurityConfiguration.SPRING_ROLE_PREFIX)) // currently not used, roles are mapped directly?
            .map(role -> (Group) new OAuthGroup(role, role, "oauth"))
            .collect(toList())
        ).orElse(Collections.emptyList());
  }

  private List<User> list(OAuthUserQueryImpl oAuthUserQuery) {
    return Collections.singletonList(single(oAuthUserQuery));
  }

  private long count(OAuthUserQueryImpl oAuthUserQuery) {
    return list(oAuthUserQuery).size();
  }

  private long count(OAuthGroupQueryImpl oAuthGroupQuery) {
    return list(oAuthGroupQuery).size();
  }

  private Optional<JwtAuthenticationToken> getJwtAuthenticationToken() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof JwtAuthenticationToken) {
      return Optional.of(((JwtAuthenticationToken) authentication));
    } else {
      return Optional.empty();
    }
  }

  static class OAuthUserQueryImpl extends UserQueryImpl {

    private final OAuthIdentityServiceProvider oAuthIdentityServiceProvider;

    public OAuthUserQueryImpl(OAuthIdentityServiceProvider oAuthIdentityServiceProvider) {
      this.oAuthIdentityServiceProvider = oAuthIdentityServiceProvider;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
      return oAuthIdentityServiceProvider.count(this);
    }

    @Override
    public List<User> executeList(CommandContext commandContext, Page page) {
      return oAuthIdentityServiceProvider.list(this);
    }

    @Override
    public User singleResult() {
      return oAuthIdentityServiceProvider.single(this);
    }
  }

  static class OAuthGroupQueryImpl extends GroupQueryImpl {

    private final OAuthIdentityServiceProvider oAuthIdentityServiceProvider;

    public OAuthGroupQueryImpl(OAuthIdentityServiceProvider oAuthIdentityServiceProvider) {
      this.oAuthIdentityServiceProvider = oAuthIdentityServiceProvider;
    }

    @Override
    public long executeCount(CommandContext commandContext) {
      return oAuthIdentityServiceProvider.count(this);
    }

    @Override
    public List<Group> executeList(CommandContext commandContext, Page page) {
      return oAuthIdentityServiceProvider.list(this);
    }

    @Override
    public Group singleResult() {
      return oAuthIdentityServiceProvider.single(this);
    }
  }

  static class NoTenantQueryImpl extends TenantQueryImpl {

    @Override
    public long executeCount(CommandContext commandContext) {
      return 0;
    }

    @Override
    public List<Tenant> executeList(CommandContext commandContext, Page page) {
      return Collections.emptyList();
    }
  }

  static class OAuthUser implements User {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;

    OAuthUser(String id, String firstName, String lastName, String emailAddress) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.emailAddress = emailAddress;
    }

    @Override
    public String getId() {
      return id;
    }

    @Override
    public void setId(String s) {
      throw new UnsupportedOperationException("Can't change user attributes");
    }

    @Override
    public String getFirstName() {
      return firstName;
    }

    @Override
    public void setFirstName(String s) {
      throw new UnsupportedOperationException("Can't change user attributes");
    }

    @Override
    public void setLastName(String s) {
      throw new UnsupportedOperationException("Can't change user attributes");
    }

    @Override
    public String getLastName() {
      return lastName;
    }

    @Override
    public void setEmail(String s) {
      throw new UnsupportedOperationException("Can't change user attributes");
    }

    @Override
    public String getEmail() {
      return emailAddress;
    }

    @Override
    public String getPassword() {
      throw new UnsupportedOperationException("Can't read user's password");
    }

    @Override
    public void setPassword(String s) {
      throw new UnsupportedOperationException("Can't change user attributes");
    }
  }

  static class OAuthGroup implements Group {

    private final String id;
    private final String name;
    private final String type;

    OAuthGroup(String id, String name, String type) {
      this.id = id;
      this.name = name;
      this.type = type;
    }

    @Override
    public String getId() {
      return this.id;
    }

    @Override
    public void setId(String id) {
      throw new UnsupportedOperationException("Can't set group id");
    }

    @Override
    public String getName() {
      return this.name;
    }

    @Override
    public void setName(String name) {
      throw new UnsupportedOperationException("Can't set group name");
    }

    @Override
    public String getType() {
      return this.type;
    }

    @Override
    public void setType(String string) {
      throw new UnsupportedOperationException("Can't set group type");
    }
  }
}
