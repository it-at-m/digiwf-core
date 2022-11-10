package io.muenchendigital.digiwf.legacy.dms.muc.domain.service;

import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.*;
import io.muenchendigital.digiwf.legacy.dms.muc.external.client.DmsClient;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSObjectClass;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSSearchResult;
import io.muenchendigital.digiwf.legacy.dms.muc.properties.DmsProperties;
import io.muenchendigital.digiwf.legacy.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service to interact with the muc dms.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DmsService {

    private final DmsClient dmsClient;
    private final UserService userService;
    private final DmsProperties dmsProperties;

    /**
     * Get metadata for an object for the given url.
     * Request is performed with the given user.
     *
     * @param userId Id of the user to perform the request
     * @param url    Url of the object
     * @return metadata
     */
    public Metadata getMetadataByUrl(final String userId, final String url) {
        return this.getMetadata(userId, this.parseUrl(url));
    }

    /**
     * Get metadata for an object for the given url.
     * Request is performed with the given user.
     *
     * @param userId Id of the user to perform the request
     * @param coo    COO of the object
     * @return
     */
    public Metadata getMetadata(final String userId, final String coo) {
        val username = this.getUsername(userId);
        return this.dmsClient.readContentMetadata(coo, username);
    }

    /**
     * Search the COO of the Sachakte.
     *
     * @param searchString Search string
     * @param userId       Id of the user
     * @return COO of the Sachakte
     */
    public Optional<String> searchSachakte(final String searchString, final String userId) throws Exception {
        val username = this.getUsername(userId);
        try {
            final String sachakteCoo = this.searchObjectWithUser(searchString, DMSObjectClass.Sachakte, username);
            log.debug("Sachakte searched: {}", sachakteCoo);
            return Optional.ofNullable(sachakteCoo);
        } catch (final IllegalStateException e) {
            return Optional.empty();
        }
    }

    /**
     * Create a new Sachackte
     *
     * @param sachakteTitel Titel of the Sachackte
     * @param aktenplanName Name of the Aktenplan
     * @param userId        Id of the user
     * @return Sachakte
     */
    public Sachakte createSachakte(final String sachakteTitel, final String aktenplanName, final String userId) throws Exception {
        val username = this.getUsername(userId);
        val aktenPlanCoo = this.searchObjectWithUser(aktenplanName, DMSObjectClass.Aktenplaneintrag, username);
        val sachakte = new NeueSachakte(aktenPlanCoo, sachakteTitel);
        final Sachakte result = this.dmsClient.createSachakteWithUser(sachakte, username);
        log.debug("Sachakte created: {}", result.getCoo());
        return result;
    }

    /**
     * Create a new Vorgang.
     *
     * @param vorgangTitle Titel of the Vorgang
     * @param sachakteId   Id of the Sachakte
     * @param userId       Id of the user
     * @return Vorgang
     */
    public Vorgang createVorgang(final String vorgangTitle, final String sachakteId, final String userId) throws Exception {
        val username = this.getUsername(userId);
        val neuerVorgang = new NeuerVorgang(sachakteId, vorgangTitle, "");
        val vorgang = this.dmsClient.createVorgang(neuerVorgang, username);
        log.debug("Vorgang created: {}", vorgang.getCoo());
        return vorgang;
    }

    /**
     * Create a new Dokument.
     *
     * @param documenTitle Title of the document
     * @param vorgangCoo   COO of the Vorgang
     * @param userId       Id of the user
     * @return Dokument
     */
    public Dokument createDokument(
            final String documenTitle,
            final String vorgangCoo,
            final String userId) throws Exception {
        val username = this.getUsername(userId);
        val document = NeuesDokument.builder()
                .betreff(documenTitle)
                .einAusgehend(EinAusgehend.EINGEHEND)
                .kurzname(documenTitle)
                .vorgangId(vorgangCoo)
                .build();

        final Dokument resultDocument = this.dmsClient.createDokument(document, username);
        log.debug("Dokument created: {}", resultDocument.getCoo());
        return resultDocument;
    }

    /**
     * Update a Dokument.
     *
     * @param dokument       Dokument, that should be updated
     * @param schriftstuecke Schriftstücke that should be added
     * @param userId         Id of the user
     */
    public void updateDocument(
            final Dokument dokument,
            final List<NeuesSchriftstueck> schriftstuecke,
            final String userId) throws Exception {
        val username = this.getUsername(userId);
        this.dmsClient.updateEingehendesDokument(dokument, schriftstuecke, username);
    }

    /**
     * Get all Schriftstuecke of a document.
     *
     * @param dokumentCOO COO of the document
     * @param userId      Id of the user
     * @return
     * @throws Exception
     */
    public List<Schriftstueck> getAllSchrifstuecke(
            final String dokumentCOO,
            final String userId) throws Exception {
        val username = this.getUsername(userId);
        return this.dmsClient.getAllSchriftstuecke(dokumentCOO, username);
    }

    /**
     * Create a new Dokument.
     *
     * @param documenTitle   Title of the document
     * @param vorgangCoo     COO of the Vorgang
     * @param userId         Id of the user
     * @param schriftstuecke Schriftstücke, that should be added
     * @return Do
     */
    public Dokument createDokument(
            final String documenTitle,
            final String vorgangCoo,
            final String userId,
            final List<NeuesSchriftstueck> schriftstuecke) throws Exception {
        val username = this.getUsername(userId);
        val document = NeuesDokument.builder()
                .betreff(documenTitle)
                .einAusgehend(EinAusgehend.EINGEHEND)
                .kurzname(documenTitle)
                .vorgangId(vorgangCoo)
                .schriftstuecke(schriftstuecke)
                .build();

        final Dokument resultDocument = this.dmsClient.createDokument(document, username);
        log.debug("Dokument created: {}", resultDocument.getCoo());
        return resultDocument;
    }

    /**
     * Cancel the document with the given COO.
     *
     * @param userId      Id of the user to perfom the request
     * @param documentCOO COO of the document
     * @throws Exception
     */
    public void cancelDocument(@NotBlank final String userId, @NotBlank final String documentCOO) throws Exception {
        val username = this.getUsername(userId);
        this.dmsClient.cancelDokument(username, documentCOO);
    }

    /**
     * Updates a Schriftstück.
     *
     * @param schriftstueck Schriftstück that should be updated
     * @param userId        Id of the user to perform the request
     */
    public void updateSchriftstueck(final Schriftstueck schriftstueck, final String userId) throws Exception {
        val username = this.getUsername(userId);
        this.dmsClient.updateSchriftstueck(schriftstueck, username);
        log.debug("Dokument updated: {}", schriftstueck.getCoo());
    }

    /**
     * Deposit the Vorgang with the given COO.
     *
     * @param vorgangCoo COO of the Vorgang
     * @param userId     Id of the user.
     */
    public void depositVorgang(@NotBlank final String vorgangCoo, @NotBlank final String userId) throws Exception {
        val username = this.getUsername(userId);
        this.dmsClient.depositVorgang(vorgangCoo, username);
    }

    /**
     * Get metadata.
     *
     * @param schriftstuecke
     * @param userId
     * @return metadata
     */
    public List<Metadata> getMetadata(final List<Schriftstueck> schriftstuecke, final String userId) {
        val username = this.getUsername(userId);

        return schriftstuecke.stream()
                .map(obj -> this.dmsClient.readContentMetadata(obj.getCoo(), username))
                .collect(Collectors.toList());
    }

    /**
     * Get a Schriftstück.
     *
     * @param userId Id of the user
     * @param url    Url of the Schriftstück
     * @return Schriftstück
     */
    public Schriftstueck readSchriftstueck(final String userId, final String url) {
        val username = this.getUsername(userId);

        try {
            return this.dmsClient.readSchriftstueck(username, this.parseUrl(url));
        } catch (final Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Schriftstueck could no be read. User: %s Obj: %s", userId, url));
        }
    }

    // HELPER METHODS

    private String searchObjectWithUser(final String objectName, final DMSObjectClass dmsObjectClass, final String username) throws Exception {
        final List<DMSSearchResult> results = this.dmsClient.searchObjectWithUser(objectName, dmsObjectClass, username);
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0).objaddress;
    }

    private String getUsername(final String userLogin) {
        val defaultUser = this.dmsProperties.getDefaultUser();
        if (StringUtils.isNoneBlank(defaultUser)) {
            return this.userService.getUser(defaultUser).getUsername();
        }
        return this.userService.getUser(userLogin).getUsername();
    }

    /**
     * Replace whitespaces and stuff.
     *
     * @param string
     * @return
     */
    private String urlEncode(final String string) {
        try {
            return URLEncoder.encode(string, Charset.defaultCharset().name());
        } catch (final UnsupportedEncodingException e) {
            log.warn("Url encoding failed for {}", string, e);
            return string;
        }
    }

    private String parseUrl(final String url) {
        //alwdmstest02.muenchen.de/fsc/mx/COO.2150.9400.2.66044
        return url.substring(url.lastIndexOf("/mx/") + 4);
    }

}
