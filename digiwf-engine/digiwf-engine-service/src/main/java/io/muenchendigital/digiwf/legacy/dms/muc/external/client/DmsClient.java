/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.legacy.dms.muc.external.client;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.*;
import io.muenchendigital.digiwf.legacy.dms.muc.domain.model.*;
import io.muenchendigital.digiwf.legacy.dms.muc.external.mapper.DMSSearchResultMapper;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSException;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSObjectClass;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSSearchResult;
import io.muenchendigital.digiwf.legacy.dms.muc.external.transport.DMSStatusCode;
import io.muenchendigital.digiwf.legacy.dms.muc.properties.DmsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Client to interact with the muc dms.
 *
 * @author externer.dl.horn
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DmsClient {

    private final LHMBAI151700GIWSDSoap wsClient;
    private final DMSSearchResultMapper searchResultMapper;
    private final DmsProperties properties;

    /**
     * Create a Sachakte.
     *
     * @param sachakte Sachakte
     * @param username Name of the user
     * @return Sachakte
     */
    public Sachakte createSachakteWithUser(final NeueSachakte sachakte, final String username) throws DMSException {
        //logging for dms team
        log.info("calling CreateFileGI"
                + " Userlogin: " + username
                + " Apentry: " + sachakte.getAktenplanId()
                + " Filesubj: " + sachakte.getBetreff()
                + " Shortname: " + sachakte.getKurzname()
                + " Apentrysearch: true"
                + " Procedureaccdef: " + sachakte.getZugriffsDefinitionVorgaenge()
        );

        final CreateFileGI request = new CreateFileGI();
        request.setUserlogin(username);
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setApentry(sachakte.getAktenplanId());
        request.setFilesubj(sachakte.getBetreff());
        request.setShortname(sachakte.getKurzname());
        request.setApentrysearch(true); // looks for free parent entry
        request.setProcedureaccdef(sachakte.getZugriffsDefinitionVorgaenge());

        final CreateFileGIResponse response = this.wsClient.createFileGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        return new Sachakte(sachakte, response.getObjid());
    }

    /**
     * Search for an object.
     *
     * @param objectname     Name of the Object
     * @param dmsObjectClass Object class to search for
     * @param username       Name of the user
     * @return Search Result
     */
    public List<DMSSearchResult> searchObjectWithUser(final String objectname, final DMSObjectClass dmsObjectClass, final String username)
            throws DMSException {
        //logging for dms team
        log.info("calling SearchObjNameGI"
                + " Userlogin: " + username
                + " SearchString: " + objectname
                + " Objclass: " + dmsObjectClass.getName()
        );

        final SearchObjNameGI params = new SearchObjNameGI();
        params.setUserlogin(username);
        params.setBusinessapp(this.properties.getBusinessapp());
        params.setObjclass(dmsObjectClass.getName());
        params.setSearchstring(objectname);

        final SearchObjNameGIResponse response = this.wsClient.searchObjNameGI(params);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        if (response.getGiobjecttype() == null ||
                response.getGiobjecttype().getLHMBAI151700GIObjectType() == null) {
            log.debug("No search results found");
            return Collections.emptyList();
        }
        return this.searchResultMapper.map2TO(response.getGiobjecttype().getLHMBAI151700GIObjectType());
    }

    /**
     * Create a new Vorgang.
     *
     * @param vorgang  Vorgang
     * @param username Name of the user
     * @return Vorgang
     */
    public Vorgang createVorgang(final NeuerVorgang vorgang, final String username) throws DMSException {
        //logging for dms team
        log.info("calling CreateProcedureGI"
                + " Userlogin: " + username
                + " Referrednumber: " + vorgang.getSachakteId()
                + " Shortname: " + vorgang.getKurzname()
                + " Filesubj: " + vorgang.getBetreff()
                + " Filetype: " + vorgang.getArt().getValue()
        );

        final CreateProcedureGI request = new CreateProcedureGI();
        request.setUserlogin(username);
        request.setReferrednumber(vorgang.getSachakteId());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(vorgang.getKurzname());
        request.setFilesubj(vorgang.getBetreff());
        request.setFiletype(vorgang.getArt().getValue());

        final CreateProcedureGIResponse response = this.wsClient.createProcedureGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        return new Vorgang(vorgang, response.getObjid());
    }

    /**
     * Create a new Dokument.
     *
     * @param dokument Dokument
     * @param username Name of the user
     * @return
     */
    public Dokument createDokument(final NeuesDokument dokument, final String username) throws DMSException {
        //logging for dms team
        log.info("calling CreateIncomingGI"
                + " Userlogin: " + username
                + " Referrednumber: " + dokument.getVorgangId()
                + " Shortname: " + dokument.getKurzname()
                + " Filesubj: " + dokument.getBetreff()
        );

        switch (dokument.getEinAusgehend()) {
            case EINGEHEND:
                return this.createEingehendesDokumentWithUser(dokument, username);
            case AUSGEHEND:
                return this.createAusgehendesDokumentWithUser(dokument, username);
            default:
                throw new AssertionError("must not happen");
        }
    }

    /**
     * Update a Schriftstück.
     *
     * @param schriftstueck Schriftstück
     * @param username      Name of the user
     * @return COO of the new object
     */
    public String updateSchriftstueck(final Schriftstueck schriftstueck, final String username) throws DMSException {
        //logging for dms team
        log.info("calling UpdateContentObjectGI"
                + " Userlogin: " + username
                + " Objaddress: " + schriftstueck.getCoo()
        );

        final UpdateContentObjectGI request = new UpdateContentObjectGI();
        request.setObjaddress(schriftstueck.getCoo());
        request.setUserlogin(username);
        request.setBusinessapp(this.properties.getBusinessapp());

        final LHMBAI151700GIAttachmentType attachmentType = this.parseSchriftstueck(schriftstueck);
        request.setGiattachmenttype(attachmentType);

        final UpdateContentObjectGIResponse response = this.wsClient.updateContentObjectGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        return response.getObjid();
    }

    /**
     * Cancel a Dokument.
     *
     * @param username    Name of the user.
     * @param documentCOO COO of the document.
     */
    public void cancelDokument(final String username, final String documentCOO) throws DMSException {
        //logging for dms team
        log.info("calling CancelObjectGI"
                + " Userlogin: " + username
                + " Objaddress: " + documentCOO
        );

        final CancelObjectGI cancelObjectGI = new CancelObjectGI();
        cancelObjectGI.setObjaddress(documentCOO);
        cancelObjectGI.setUserlogin(username);
        cancelObjectGI.setBusinessapp(this.properties.getBusinessapp());

        final CancelObjectGIResponse response = this.wsClient.cancelObjectGI(cancelObjectGI);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }
    }

    /**
     * Update a Dokument
     *
     * @param dokument       Dokument
     * @param schriftstuecke Schriftstücke that should be added
     * @param username       Name of the user
     */
    public void updateEingehendesDokument(final Dokument dokument, final List<NeuesSchriftstueck> schriftstuecke, final String username)
            throws DMSException {
        log.info("calling UpdateIncomingGI"
                + " Userlogin: " + username
        );
        val request = new UpdateIncomingGI();
        request.setUserlogin(username);
        request.setObjaddress(dokument.getCoo());
        request.setShortname(dokument.getKurzname());
        request.setFilesubj(dokument.getBetreff());
        request.setBusinessapp(this.properties.getBusinessapp());

        val attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        val files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (val schriftstueck : schriftstuecke) {
            files.add(this.parseSchriftstueck(schriftstueck));
        }

        request.setGiattachmenttype(attachmentType);
        val response = this.wsClient.updateIncomingGI(request);
        val statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }
    }

    /**
     * Get Metadata information for the given COO.
     *
     * @param coo      COO of the object
     * @param username Name of the user
     * @return metadata
     */
    public Metadata readContentMetadata(final String coo, final String username) {
        log.info("calling ReadContentObjectMetaDataGI"
                + " Userlogin: " + username
                + " COO: " + coo
        );

        val request = new ReadContentObjectMetaDataGI();
        request.setObjaddress(coo);
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setUserlogin(username);
        val response = this.wsClient.readContentObjectMetaDataGI(request);

        return Metadata.builder()
                .url(String.format(this.properties.getUiurl(), coo))
                .name(response.getGimetadatatype().getLHMBAI151700Filename())
                .type(response.getGimetadatatype().getLHMBAI151700Objclass())
                .build();
    }

    /**
     * Deposit the Vorgang.
     *
     * @param vorgangCoo COO of the Vorgang
     * @param username   Name of the user
     */
    public void depositVorgang(final String vorgangCoo, final String username) throws DMSException {
        //logging for dms team
        log.info("calling DepositObjectGI"
                + " Userlogin: " + username
                + " Objaddress: " + vorgangCoo
        );

        final DepositObjectGI depositObjectGI = new DepositObjectGI();
        depositObjectGI.setObjaddress(vorgangCoo);
        depositObjectGI.setBusinessapp(this.properties.getBusinessapp());
        depositObjectGI.setUserlogin(username);

        final DepositObjectGIResponse response = this.wsClient.depositObjectGI(depositObjectGI);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }
    }

    /**
     * Read Schriftstück.
     *
     * @param username
     * @param coo      COO of the Schriftstück
     * @return Schriftstück
     */
    public Schriftstueck readSchriftstueck(final String username, final String coo) throws DMSException, IOException {
        //logging for dms team
        log.info("calling DepositObjectGI"
                + " Userlogin: " + username
                + " Objaddress: " + coo
        );

        val request = new ReadContentObjectGI();
        request.setObjaddress(coo);
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setUserlogin(username);

        val response = this.wsClient.readContentObjectGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        return Schriftstueck.builder()
                .coo(coo)
                .name(response.getGiattachmenttype().getLHMBAI151700Filename())
                .extension(response.getGiattachmenttype().getLHMBAI151700Fileextension())
                .content(IOUtils.toByteArray(response.getGiattachmenttype().getLHMBAI151700Filecontent().getInputStream()))
                .build();
    }

    // HELPER METHODS

    private LHMBAI151700GIAttachmentType parseSchriftstueck(final NeuesSchriftstueck schriftstueck) {
        final LHMBAI151700GIAttachmentType attachment = new LHMBAI151700GIAttachmentType();
        final DataHandler dataHandler = new DataHandler(new ByteArrayDataSource(schriftstueck.getContent(), schriftstueck.getExtension()));
        attachment.setLHMBAI151700Filecontent(dataHandler);
        attachment.setLHMBAI151700Fileextension(schriftstueck.getExtension());
        attachment.setLHMBAI151700Filename(schriftstueck.getName());
        return attachment;
    }

    private LHMBAI151700GIAttachmentType parseSchriftstueck(final Schriftstueck schriftstueck) {
        final LHMBAI151700GIAttachmentType attachment = new LHMBAI151700GIAttachmentType();
        final DataHandler dataHandler = new DataHandler(new ByteArrayDataSource(schriftstueck.getContent(), schriftstueck.getExtension()));
        attachment.setLHMBAI151700Filecontent(dataHandler);
        attachment.setLHMBAI151700Fileextension(schriftstueck.getExtension());
        attachment.setLHMBAI151700Filename(schriftstueck.getName());
        return attachment;
    }

    private Dokument createEingehendesDokumentWithUser(final NeuesDokument dokument, final String username) throws DMSException {
        final CreateIncomingGI request = new CreateIncomingGI();
        request.setUserlogin(username);
        request.setReferrednumber(dokument.getVorgangId());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(dokument.getKurzname());
        request.setFilesubj(dokument.getBetreff());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final NeuesSchriftstueck schriftstueck : dokument.getSchriftstuecke()) {
            files.add(this.parseSchriftstueck(schriftstueck));
        }

        request.setGiattachmenttype(attachmentType);

        final CreateIncomingGIResponse response = this.wsClient.createIncomingGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        val schriftstuecke = this.checkSchriftstuecke(response.getObjid(), username, dokument.getSchriftstuecke());
        return new Dokument(dokument, response.getObjid(), schriftstuecke);
    }

    private List<Schriftstueck> checkSchriftstuecke(final String documentCoo, final String username, final List<NeuesSchriftstueck> schriftstuecke)
            throws DMSException {
        if (schriftstuecke.size() == 0) {
            return Collections.emptyList();
        }

        final ReadDocumentGIObjects readRequest = new ReadDocumentGIObjects();
        readRequest.setObjaddress(documentCoo);
        readRequest.setBusinessapp(this.properties.getBusinessapp());
        readRequest.setUserlogin(username);
        final ReadDocumentGIObjectsResponse readResponse = this.wsClient.readDocumentGIObjects(readRequest);
        final List<LHMBAI151700GIObjectType> geladeneSchriftstuecke = readResponse.getGiobjecttype().getLHMBAI151700GIObjectType();

        if (geladeneSchriftstuecke.size() != schriftstuecke.size()) {
            final String message = String.format("dms hat nicht die richtige anzahl an schriftstücken erstellt (erwartet: %s, ist: %d)",
                    geladeneSchriftstuecke.size(),
                    schriftstuecke.size()
            );
            throw new DMSException(message);
        }

        final List<Schriftstueck> erstellteSchriftstuecke = new ArrayList<>();

        for (int i = 0; i < schriftstuecke.size(); i++) {
            final NeuesSchriftstueck schriftstueck = schriftstuecke.get(i);
            final LHMBAI151700GIObjectType erstelltesSchriftstueck = geladeneSchriftstuecke.get(i);
            if (!schriftstueck.getName().equals(erstelltesSchriftstueck.getLHMBAI151700Objname())) {
                // Wir brauchen die IDs der Schriftstücke fürs herunterladen, leider gibt/gab es in der Schnittstelle bei der Antwort keine Infos zu
                // den IDs. Deswegen laden wir die mit leseIdsFuerSchriftstueckeMitUser nach, und hoffen das die Reihenfolge und Anzahl gleich wie beim hochladen ist.
                throw new DMSException("Reihenfolge der gelesenen IDs stimmt nicht mit der hochgeladenen überein. Kommentar dazu im Code lesen");
            }
            erstellteSchriftstuecke.add(new Schriftstueck(schriftstueck, erstelltesSchriftstueck.getLHMBAI151700Objaddress()));
        }

        return erstellteSchriftstuecke;
    }

    private Dokument createAusgehendesDokumentWithUser(final NeuesDokument dokument, final String username) throws DMSException {
        final CreateOutgoingGI request = new CreateOutgoingGI();
        request.setUserlogin(username);
        request.setReferrednumber(dokument.getVorgangId());
        request.setBusinessapp(this.properties.getBusinessapp());

        request.setShortname(dokument.getKurzname());
        request.setFilesubj(dokument.getBetreff());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final NeuesSchriftstueck schriftstueck : dokument.getSchriftstuecke()) {
            files.add(this.parseSchriftstueck(schriftstueck));
        }

        request.setGiattachmenttype(attachmentType);
        //request.setSubfiletype("Dokumenttyp für Ausgangsdokumente"); // TODO: check
        request.setSubfiletype("BeZweck-Ausgang");

        final CreateOutgoingGIResponse response = this.wsClient.createOutgoingGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new DMSException(statusCode, response.getErrormessage());
        }

        val schriftstuecke = this.checkSchriftstuecke(response.getObjid(), username, dokument.getSchriftstuecke());
        return new Dokument(dokument, response.getObjid(), schriftstuecke);
    }

    public List<Schriftstueck> getAllSchriftstuecke(final String documentCoo, final String username) throws DMSException {
        final ReadDocumentGIObjects readRequest = new ReadDocumentGIObjects();
        readRequest.setObjaddress(documentCoo);
        readRequest.setUserlogin(username);
        readRequest.setBusinessapp(this.properties.getBusinessapp());
        final ReadDocumentGIObjectsResponse readResponse = this.wsClient.readDocumentGIObjects(readRequest);

        if (readResponse.getGiobjecttype() == null) {
            return new ArrayList<>();
        }

        return readResponse.getGiobjecttype().getLHMBAI151700GIObjectType()
                .stream()
                .map(obj -> Schriftstueck.builder()
                        .coo(obj.getLHMBAI151700Objaddress())
                        .name(obj.getLHMBAI151700Objname())
                        .build())
                .collect(Collectors.toList());
    }

}
