/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.user.external.client;

import de.muenchen.oss.digiwf.legacy.user.domain.model.User;
import de.muenchen.oss.digiwf.legacy.user.external.client.query.LdapQueryFactory;
import de.muenchen.oss.digiwf.legacy.user.external.configuration.ServiceAuthLdapProperties;
import de.muenchen.oss.digiwf.legacy.user.external.mapper.UserAttributesMapper;
import de.muenchen.oss.digiwf.legacy.user.infrastructure.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;

import javax.naming.ldap.LdapName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * The {@link LdapTemplate} to query the ldap directory.
 *
 * The LHM ldap tree is divided into two seperate sections, a data section (for persons/groups etc.) and an orga section (for orga units).
 * Each section has a different base, "o=Landeshauptstadt München,c=de" for orga and "o=lhm,c=de" for data.
 */
@Slf4j
public class LhmLdapClient extends LdapTemplate implements UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LhmLdapClient.class);

    public static final String CACHE_USERS = "ldapusers";
    public static final String CACHE_USERBYNAME = "ldapuserbyname";
    public static final String CACHE_OUTREE = "ldapoutree";
    public static final String CACHE_OUS = "ldapous";
    private static final String LDAP_TYPE_OU = "ou";
    private static final String LHM_OBJECT_PATH = "lhmObjectPath";
    private static final String LHM_OU_SHORTNAME = "lhmOUShortname";
    private static final String ATTRIBUTE_OBJECT_CLASS = "objectClass";
    private static final String LHM_ORGANIZATIONAL_UNIT = "lhmOrganizationalUnit";

    private final LdapQueryFactory ldapQueryFactory;
    private final ServiceAuthLdapProperties serviceAuthLdapProperties;

    public LhmLdapClient(final ContextSource contextSource, final LdapQueryFactory ldapQueryFactory, final ServiceAuthLdapProperties serviceAuthLdapProperties) {
        super(contextSource);
        this.ldapQueryFactory = ldapQueryFactory;
        this.serviceAuthLdapProperties = serviceAuthLdapProperties;
    }

    /**
     * The method queries for user within the LDAP-Directory
     *
     * @param id The uid for which the search within LDAP is performed.
     * @return Returns the found object with the uid username as a {@link User} if only one user is found otherwise null.
     */
    @Override
    @Cacheable(CACHE_USERS)
    public Optional<User> findById(final String id) {
        LOG.debug("Get LDAP information for user {}.", id);
        final LdapQuery query = ldapQueryFactory.createPersonByIdQuery(id);

        val userList = super.search(query, new UserAttributesMapper());
        if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        }

        if (userList.isEmpty()) {
            LOG.warn("No user with lhmObjId {} in LDAP found.", id);
            return Optional.empty();
        }

        LOG.warn("More than one user with lhmObjId {} in LDAP found.", id);
        return Optional.empty();
    }

    /**
     * The method queries for the user within the LDAP-Directory
     *
     * @param username The uid for which the search within LDAP is performed.
     * @return Returns the found object with the uid username as a {@link User} if only one user is found otherwise null.
     */
    @Override
    @Cacheable(CACHE_USERBYNAME)
    public Optional<User> findByUsername(final String username) {
        LOG.debug("Get LDAP information for user {}.", username);
        final LdapQuery query = ldapQueryFactory.createPersonByUsernameQuery(username);
        val userList = super.search(query, new UserAttributesMapper());
        if (userList.size() == 1) {
            return Optional.ofNullable(userList.get(0));
        }

        if (userList.isEmpty()) {
            LOG.warn("No user with username {} in LDAP found.", username);
            return Optional.empty();
        }

        LOG.warn("More than one user with username {} in LDAP found.", username);
        return Optional.empty();
    }

    /**
     * This method searches for users in the ldap for the given filter and ou's.
     *
     * @param expression    filter to search in surname and givenname
     * @param ouFilters ou's to limit the search result
     * @return user
     */
    @Override
    @Cacheable(CACHE_USERS)
    public List<User> findByNamesLike(final String expression, final List<String> ouFilters) {
        LOG.debug("Get LDAP information for name expression {}.", expression);
        final LdapQuery query = ldapQueryFactory.createPersonByNamePatternAndOuQuery(expression, ouFilters);
        return super.search(query, new UserAttributesMapper());
    }

    /**
     * This methods searches for users in the ldap by the given filters and ou's.
     *
     * @param firstExpression  Filter to search for in surname or givenname, connected with or
     * @param secondExpression Filter to search for in surname or givenname, connected with or
     * @param ouFilters    ou's to limit the search result
     * @return users
     */
    @Override
    @Cacheable(CACHE_USERS)
    public List<User> findByNamesLike(final String firstExpression, final String secondExpression, final List<String> ouFilters) {
        LOG.debug("Get LDAP information for users with {} {}.", firstExpression, secondExpression);
        final LdapQuery query = ldapQueryFactory.createPersonByNamePatternsAndOuQuery(firstExpression, secondExpression, ouFilters);
        return super.search(query, new UserAttributesMapper());
    }

    /**
     * The method queries for all ous the specified user is part of.
     *
     * Copy & paste from https://git.muenchen.de/km23/ezLDAP/ezLDAP/-/blob/master/lib-core/src/main/java/de/muenchen/itm/km23/ezldap/core/LdapService.java
     *
     * @param userid The uid for which the search within LDAP is performed.
     * @return a list of ous the user is in
     */
    @Override
    @Cacheable(CACHE_OUTREE)
    public List<String> findOuTree(final String userid) {
        LOG.debug("Get LDAP ou tree for user {}.", userid);

        final LdapQuery query = ldapQueryFactory.createPersonByIdQuery(userid);

        // Get the users OU to search for the OUs outree
        List<String> ouShortCodes = super.search(query, (AttributesMapper<String>) attrs -> {
            if (null != attrs.get(LDAP_TYPE_OU)) {
                return (String) attrs.get(LDAP_TYPE_OU).get();
            }
            return null;
        });
        ouShortCodes = ouShortCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (ouShortCodes.isEmpty()) {
            log.debug("Found no ou tree");
            return new ArrayList<>();
        }
        final String ouShortCode = ouShortCodes.get(0);

        final LdapQuery ouQuery = ldapQueryFactory.createOuTreeByShortcodeQuery(ouShortCode);

        return this.findOuTree(ouQuery).orElse(new ArrayList<>());
    }

    /**
     * Find ou by its short name.
     *
     * @param shortName shortname of the ou
     * @return the resulting ou
     */
    @Override
    @Cacheable(CACHE_OUS)
    public Optional<User> findOuByShortName(final String shortName) {
        LOG.debug("Get LDAP ou of {}.", shortName);
        final LdapQuery query = ldapQueryFactory.createOuByShortNameQuery(shortName);
        final List<User> ous = super.search(query, new UserAttributesMapper());

        if (ous.size() == 1) {
            return Optional.ofNullable(ous.get(0));
        }

        if (ous.isEmpty()) {
            LOG.warn("No ou with shortname {} in LDAP found.", shortName);
            return Optional.empty();
        }

        LOG.warn("More than one ou with shortname {} in LDAP found.", shortName);
        return Optional.empty();
    }

    /**
     * Copy & paste from https://git.muenchen.de/km23/ezLDAP/ezLDAP/-/blob/master/lib-core/src/main/java/de/muenchen/itm/km23/ezldap/core/LdapService.java
     *
     * @param query
     * @return
     */
    private Optional<List<String>> findOuTree(final LdapQuery query) {
        List<LdapName> ldapNames = super.search(query, (AttributesMapper<LdapName>) attrs -> {
            if (null != attrs.get(LHM_OBJECT_PATH)) {
                return new LdapName((String) attrs.get(LHM_OBJECT_PATH).get());
            }
            return null;
        });
        // clean ldapNames from null values
        ldapNames = ldapNames.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (ldapNames.isEmpty()) {
            log.debug("Found no ou tree");
            return Optional.empty();
        }
        final LdapName ldapName = ldapNames.get(0);

        // ou tree always starts with LHM
        final List<String> ouTree = new ArrayList<>(List.of("LHM"));

        // iterate through ou tree and search for ou short codes
        String parentBase = this.serviceAuthLdapProperties.getOuSearchBase();
        for (int i = 0; i < ldapName.getRdns().size(); i++) {
            // ignore all rdn's except ou
            if (!ldapName.getRdn(i).getType().equals(LDAP_TYPE_OU)) {
                continue;
            }
            final String ouLongName = ldapName.getRdn(i).getValue().toString();
            ouTree.addAll(this.findOUShortCodeForOULongName(ouLongName, parentBase));

            // update parent base by adding the current ou to the base path
            parentBase = ldapName.get(i) + "," + parentBase;
        }
        return Optional.of(ouTree);
    }

    /**
     * Copy & paste from https://git.muenchen.de/km23/ezLDAP/ezLDAP/-/blob/master/lib-core/src/main/java/de/muenchen/itm/km23/ezldap/core/LdapService.java
     *
     * Helper method to look up the ou short code for a given ou long name in a given base path.
     *
     * @param ouLongName OU long name to search for
     * @param basePath Base path to search for the ou short code
     * @return List of ou short codes
     */
    private List<String> findOUShortCodeForOULongName(final String ouLongName, final String basePath) {
        final Filter createOuNameFilter = new AndFilter()
                .and(new EqualsFilter(LDAP_TYPE_OU, ouLongName))
                .and(new EqualsFilter(ATTRIBUTE_OBJECT_CLASS, LHM_ORGANIZATIONAL_UNIT));

        final LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .base(basePath)
                .filter(createOuNameFilter);
        log.debug("Searching for ou='{} & objectClass='{}' in subtree '{}' ...", ouLongName, LHM_ORGANIZATIONAL_UNIT, basePath);

        final List<String> ouShortCodes = super.search(query, (AttributesMapper<String>) attrs -> {
            if (null != attrs.get(LHM_OU_SHORTNAME)) {
                return (String) attrs.get(LHM_OU_SHORTNAME).get();
            }
            return null;
        });
        // clean ouShortCodes from null values
        List<String> cleanedOuShortCodes = ouShortCodes.stream().filter(Objects::nonNull).collect(Collectors.toList());
        log.debug("Resolved ou shortcodes for ouLongName='{}': {}", ouLongName, cleanedOuShortCodes);
        return cleanedOuShortCodes;
    }

}
