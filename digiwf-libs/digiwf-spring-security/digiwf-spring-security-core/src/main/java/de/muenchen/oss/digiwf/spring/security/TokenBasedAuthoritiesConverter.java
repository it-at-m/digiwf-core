package de.muenchen.oss.digiwf.spring.security;

import com.nimbusds.jose.shaded.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Stream;

import static de.muenchen.oss.digiwf.spring.security.SecurityConfiguration.SPRING_ROLE_PREFIX;
import static java.util.stream.Collectors.toList;

/**
 * @deprecated just for reference, not used yet.
 * The {@link TokenBasedAuthoritiesConverter} parses the JWT and extracts the granted roles of the user. In the current
 * Keycloak setup roles are provided under the claim "realm_access". Client roles are provided per client in the claim
 * "resource_access".<br>
 * <br>
 * Please note, that the extractor does only extract the client roles of the given clientId. If no clientId is given in
 * the configuration, only the Realm roles are used for checking authorization.<br>
 * <br>
 * Example:<br>
 * <pre>
 * {
 *   ...
 *   "realm_access" : {
 *     "roles" : [
 *       "realm_role1",
 *       "realm_role2",
 *       "realm_roleN"
 *     ]
 *   },
 *   "resource_access" : {
 *       "client1" : {
 *           roles: [
 *            "client_role1",
 *            "client_role2"
 *            "client_role3"
 *           ]
 *       },
 *       "client2" : {
 *           roles: [
 *            "client_role4",
 *            "client_role5"
 *            "client_role6"
 *           ]
 *       }
 *    }
 * }
 * </pre>
 */
@Deprecated
public class TokenBasedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final String ROLE_DECLARATIONS = "roles";
  private static final String REALM_ROLES_CLAIM = "realm_access";
  private static final String CLIENTS_CLAIM = "resource_access";
  /**
   * LHM Claim containing the user roles mapped from the current client via client scope protocol mapper.
   */
  private static final String USER_ROLES_CLAIM = "user_roles";
  private static final String CLIENT_ROLE_SEPARATOR = ":";


  @Override
  public Collection<GrantedAuthority> convert(@NonNull Jwt source) {
    return extractAuthorities(source);
  }

  protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {

    // Retrieve client roles of all clients
    final Collection<String> clientAuthorities = getClientAuthorities(jwt);

    final Collection<String> userRoles = getUserRolesClaimAuthorities(jwt);

    // Retrieve realm roles
    final Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ROLES_CLAIM);

    Collection<String> realmAuthorities = Collections.emptyList();
    if (realmAccess != null && realmAccess.containsKey(ROLE_DECLARATIONS)) {
      //noinspection unchecked
      realmAuthorities = (Collection<String>) realmAccess.get(ROLE_DECLARATIONS);
    }

    return Stream.concat(
        Stream.concat(realmAuthorities.stream(), clientAuthorities.stream()).map(s -> SPRING_ROLE_PREFIX + s),
        userRoles.stream()
    ).map(SimpleGrantedAuthority::new).collect(toList());
  }

  public static List<String> getClientAuthorities(ClaimAccessor jwt) {
    // retrieve client roles of all clients
    final List<String> clientAuthorities = new ArrayList<>();
    Map<String, Object> clientClaims = jwt.getClaimAsMap(CLIENTS_CLAIM);
    if (clientClaims != null) {
      clientClaims.forEach((client, claims) -> clientAuthorities.addAll(extractRoles(client, (JSONObject) claims)));
    }
    return clientAuthorities;
  }

  public static List<String> getUserRolesClaimAuthorities(ClaimAccessor jwt) {
    // retrieve roles
    return jwt.getClaimAsStringList(USER_ROLES_CLAIM);
  }


  static List<String> extractRoles(String client, JSONObject clientObject) {
    @SuppressWarnings("unchecked") final Collection<String> clientRoles = (Collection<String>) clientObject.get(ROLE_DECLARATIONS);
    if (clientRoles != null) {
      return clientRoles
          .stream()
          .map(role -> client + CLIENT_ROLE_SEPARATOR + role)
          .collect(toList());
    } else {
      return Collections.emptyList();
    }
  }
}
