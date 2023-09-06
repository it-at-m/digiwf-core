package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.*;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.VorgangRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Dokument;
import de.muenchen.oss.digiwf.dms.integration.domain.Schriftstueck;
import de.muenchen.oss.digiwf.dms.integration.domain.Vorgang;
import de.muenchen.oss.digiwf.message.process.api.error.BpmnError;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FabasoftAdapter implements VorgangRepository {

    private final FabasoftProperties properties;
    private final LHMBAI151700GIWSDSoap wsClient;

    @Override
    public Vorgang createVorgang(Vorgang vorgang, String user) {
        log.info("calling CreateProcedureGI: " + vorgang.toString());

        final CreateProcedureGI request = new CreateProcedureGI();
        request.setUserlogin(user);
        request.setReferrednumber(vorgang.getSachakteCoo());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(vorgang.getTitle());
        request.setFilesubj(vorgang.getTitle());
        request.setFiletype(vorgang.getArt().toString());

        final CreateProcedureGIResponse response = this.wsClient.createProcedureGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        return new Vorgang(response.getObjid(), vorgang.getSachakteCoo(), vorgang.getTitle(), vorgang.getArt());
    }

    public Dokument createDokument(final Dokument dokument, final String user) {
        log.info("calling CreateIncomingGI: " + dokument.toString());

        switch (dokument.getArt()) {
            case EINGEHEND:
                return this.createEingehendesDokumentWithUser(dokument, user);
            case AUSGEHEND:
                return this.createAusgehendesDokumentWithUser(dokument, user);
            default:
                throw new AssertionError("must not happen");
        }
    }

    private Dokument createEingehendesDokumentWithUser(final Dokument dokument, final String user) {
        final CreateIncomingGI request = new CreateIncomingGI();
        request.setUserlogin(user);
        request.setReferrednumber(dokument.getVorgangCoo());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(dokument.getTitle());
        request.setFilesubj(dokument.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Schriftstueck schriftstueck : dokument.getSchriftstuecke()) {
            files.add(this.parseSchriftstueck(schriftstueck));
        }

        request.setGiattachmenttype(attachmentType);

        final CreateIncomingGIResponse response = this.wsClient.createIncomingGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        val schriftstuecke = this.checkSchriftstuecke(response.getObjid(), user, dokument.getSchriftstuecke());
        return new Dokument(response.getObjid(), dokument.getVorgangCoo(), dokument.getTitle(), dokument.getArt() , schriftstuecke);
    }

    private Dokument createAusgehendesDokumentWithUser(final Dokument dokument, final String user)  {
        final CreateOutgoingGI request = new CreateOutgoingGI();
        request.setUserlogin(user);
        request.setReferrednumber(dokument.getVorgangCoo());
        request.setBusinessapp(this.properties.getBusinessapp());

        request.setShortname(dokument.getTitle());
        request.setFilesubj(dokument.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Schriftstueck schriftstueck : dokument.getSchriftstuecke()) {
            files.add(this.parseSchriftstueck(schriftstueck));
        }

        request.setGiattachmenttype(attachmentType);
        //request.setSubfiletype("Dokumenttyp für Ausgangsdokumente"); // TODO: check
        request.setSubfiletype("BeZweck-Ausgang");

        final CreateOutgoingGIResponse response = this.wsClient.createOutgoingGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        val schriftstuecke = this.checkSchriftstuecke(response.getObjid(), user, dokument.getSchriftstuecke());
        return new Dokument(response.getObjid(), dokument.getVorgangCoo(), dokument.getTitle(), dokument.getArt() , schriftstuecke);
    }

    private LHMBAI151700GIAttachmentType parseSchriftstueck(final Schriftstueck schriftstueck) {
        final LHMBAI151700GIAttachmentType attachment = new LHMBAI151700GIAttachmentType();
        attachment.setLHMBAI151700Filecontent(schriftstueck.getContent());
        attachment.setLHMBAI151700Fileextension(schriftstueck.getExtension());
        attachment.setLHMBAI151700Filename(schriftstueck.getName());
        return attachment;
    }

    private List<Schriftstueck> checkSchriftstuecke(final String documentCoo, final String username, final List<Schriftstueck> schriftstuecke) {
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
            throw new BpmnError("TODO", message); //TODO Error Code erstellen
        }

        final List<Schriftstueck> erstellteSchriftstuecke = new ArrayList<>();

        for (int i = 0; i < schriftstuecke.size(); i++) {
            final Schriftstueck schriftstueck = schriftstuecke.get(i);
            final LHMBAI151700GIObjectType erstelltesSchriftstueck = geladeneSchriftstuecke.get(i);
            if (!schriftstueck.getName().equals(erstelltesSchriftstueck.getLHMBAI151700Objname())) {
                // Wir brauchen die IDs der Schriftstücke fürs herunterladen, leider gibt/gab es in der Schnittstelle bei der Antwort keine Infos zu
                // den IDs. Deswegen laden wir die mit leseIdsFuerSchriftstueckeMitUser nach, und hoffen das die Reihenfolge und Anzahl gleich wie beim hochladen ist.
                throw new BpmnError("TODO", "Reihenfolge der gelesenen IDs stimmt nicht mit der hochgeladenen überein. Kommentar dazu im Code lesen"); //TODO Error Code erstellen
            }
            erstellteSchriftstuecke.add(new Schriftstueck(schriftstueck.getExtension(), schriftstueck.getName(), schriftstueck.getContent(), erstelltesSchriftstueck.getLHMBAI151700Objaddress()));
        }

        return erstellteSchriftstuecke;
    }

}
