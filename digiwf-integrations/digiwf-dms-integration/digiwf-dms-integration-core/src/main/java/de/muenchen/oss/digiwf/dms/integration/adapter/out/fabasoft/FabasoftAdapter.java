package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateIncomingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ArrayOfLHMBAI151700GIAttachmentType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIAttachmentType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateIncomingGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ReadDocumentGIObjects;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ReadDocumentGIObjectsResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIObjectType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.File;
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
public class FabasoftAdapter implements ProcedureRepository {

    private final FabasoftProperties properties;
    private final LHMBAI151700GIWSDSoap wsClient;

    @Override
    public Procedure createVorgang(Procedure procedure, String user) {
        log.info("calling CreateProcedureGI: " + procedure.toString());

        final CreateProcedureGI request = new CreateProcedureGI();
        request.setUserlogin(user);
        request.setReferrednumber(procedure.getFileCOO());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(procedure.getTitle());
        request.setFilesubj(procedure.getTitle());
        request.setFiletype("Elektronisch");

        final CreateProcedureGIResponse response = this.wsClient.createProcedureGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        return new Procedure(response.getObjid(), procedure.getFileCOO(), procedure.getTitle());
    }

    public Document createDocument(final Document document, final String user) {
        log.info("calling CreateIncomingGI: " + document.toString());

        switch (document.getType()) {
            case EINGEHEND:
                return this.createEingehendesDokumentWithUser(document, user);
            case AUSGEHEND:
                return this.createAusgehendesDokumentWithUser(document, user);
                //TODO Internes Dokument
            default:
                throw new AssertionError("must not happen");
        }
    }

    private Document createEingehendesDokumentWithUser(final Document document, final String user) {
        final CreateIncomingGI request = new CreateIncomingGI();
        request.setUserlogin(user);
        request.setReferrednumber(document.getProcedureCOO());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(document.getTitle());
        request.setFilesubj(document.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final File file : document.getFiles()) {
            files.add(this.parseSchriftstueck(file));
        }

        request.setGiattachmenttype(attachmentType);

        final CreateIncomingGIResponse response = this.wsClient.createIncomingGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        val schriftstuecke = this.checkSchriftstuecke(response.getObjid(), user, document.getFiles());
        return new Document(response.getObjid(), document.getProcedureCOO(), document.getTitle(), document.getType() , schriftstuecke);
    }

    private Document createAusgehendesDokumentWithUser(final Document document, final String user)  {
        final CreateOutgoingGI request = new CreateOutgoingGI();
        request.setUserlogin(user);
        request.setReferrednumber(document.getProcedureCOO());
        request.setBusinessapp(this.properties.getBusinessapp());

        request.setShortname(document.getTitle());
        request.setFilesubj(document.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final File file : document.getFiles()) {
            files.add(this.parseSchriftstueck(file));
        }

        request.setGiattachmenttype(attachmentType);
        //request.setSubfiletype("Dokumenttyp für Ausgangsdokumente"); // TODO: check
        request.setSubfiletype("BeZweck-Ausgang");

        final CreateOutgoingGIResponse response = this.wsClient.createOutgoingGI(request);

        final DMSStatusCode statusCode = DMSStatusCode.byCode(response.getStatus());
        if (statusCode != DMSStatusCode.UEBERTRAGUNG_ERFORLGREICH) {
            throw new IncidentError(response.getErrormessage());
        }

        val schriftstuecke = this.checkSchriftstuecke(response.getObjid(), user, document.getFiles());
        return new Document(response.getObjid(), document.getProcedureCOO(), document.getTitle(), document.getType() , schriftstuecke);
    }

    private LHMBAI151700GIAttachmentType parseSchriftstueck(final File file) {
        final LHMBAI151700GIAttachmentType attachment = new LHMBAI151700GIAttachmentType();
        attachment.setLHMBAI151700Filecontent(file.getContent());
        attachment.setLHMBAI151700Fileextension(file.getExtension());
        attachment.setLHMBAI151700Filename(file.getName());
        return attachment;
    }

    private List<File> checkSchriftstuecke(final String documentCoo, final String username, final List<File> schriftstuecke) {
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

        final List<File> erstellteSchriftstuecke = new ArrayList<>();

        for (int i = 0; i < schriftstuecke.size(); i++) {
            final File file = schriftstuecke.get(i);
            final LHMBAI151700GIObjectType erstelltesSchriftstueck = geladeneSchriftstuecke.get(i);
            if (!file.getName().equals(erstelltesSchriftstueck.getLHMBAI151700Objname())) {
                // Wir brauchen die IDs der Schriftstücke fürs herunterladen, leider gibt/gab es in der Schnittstelle bei der Antwort keine Infos zu
                // den IDs. Deswegen laden wir die mit leseIdsFuerSchriftstueckeMitUser nach, und hoffen das die Reihenfolge und Anzahl gleich wie beim hochladen ist.
                throw new BpmnError("TODO", "Reihenfolge der gelesenen IDs stimmt nicht mit der hochgeladenen überein. Kommentar dazu im Code lesen"); //TODO Error Code erstellen
            }
            erstellteSchriftstuecke.add(new File(file.getExtension(), file.getName(), file.getContent(), erstelltesSchriftstueck.getLHMBAI151700Objaddress()));
        }

        return erstellteSchriftstuecke;
    }

}
