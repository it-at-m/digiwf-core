package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.*;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CancelObjectPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.CreateProcedurePort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.DepositObjectPort;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.UpdateDocumentPort;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.dms.integration.domain.DocumentType;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FabasoftAdapter implements CreateProcedurePort, CreateDocumentPort, UpdateDocumentPort, DepositObjectPort, CancelObjectPort {

    private final FabasoftProperties properties;
    private final LHMBAI151700GIWSDSoap wsClient;

    private final DMSErrorHandler dmsErrorHandler = new DMSErrorHandler();

    @Override
    public Procedure createProcedure(Procedure procedure, String user) {
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

    @Override
    public String createDocument(final Document document, final String user) {
        switch (document.getType()) {
            case EINGEHEND:
                return this.createIncomingDocument(document, user);
            case AUSGEHEND:
                return this.createOutgoingDocument(document, user);
            case INTERN:
                return this.createInternalDocument(document, user);
            default:
                throw new AssertionError("must not happen");
        }
    }

    private String createIncomingDocument(final Document document, final String user) {
        //logging for dms team
        log.info("calling CreateIncomingGI"
                + " Userlogin: " + user
                + " Referrednumber: " + document.getProcedureCOO()
                + " Shortname: " + document.getTitle()
                + " Filesubj: " + document.getTitle()
        );

        final CreateIncomingGI request = new CreateIncomingGI();
        request.setUserlogin(user);
        request.setReferrednumber(document.getProcedureCOO());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(document.getTitle());
        request.setFilesubj(document.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Content content : document.getContents()) {
            files.add(this.parseContent(content));
        }

        request.setGiattachmenttype(attachmentType);

        final CreateIncomingGIResponse response = this.wsClient.createIncomingGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());

        return response.getObjid();
    }

    private String createOutgoingDocument(final Document document, final String user) {
        //logging for dms team
        log.info("calling CreateOutgoingGI"
                + " Userlogin: " + user
                + " Referrednumber: " + document.getProcedureCOO()
                + " Shortname: " + document.getTitle()
                + " Filesubj: " + document.getTitle()
        );

        final CreateOutgoingGI request = new CreateOutgoingGI();
        request.setUserlogin(user);
        request.setReferrednumber(document.getProcedureCOO());
        request.setBusinessapp(this.properties.getBusinessapp());

        request.setShortname(document.getTitle());
        request.setFilesubj(document.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Content content : document.getContents()) {
            files.add(this.parseContent(content));
        }

        request.setGiattachmenttype(attachmentType);
        //request.setSubfiletype("Dokumenttyp für Ausgangsdokumente");
        //request.setSubfiletype("BeZweck-Ausgang");

        final CreateOutgoingGIResponse response = this.wsClient.createOutgoingGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());

        return response.getObjid();
    }

    private String createInternalDocument(final Document document, final String user) {
        //logging for dms team
        log.info("calling CreateInternalGI"
                + " Userlogin: " + user
                + " Referrednumber: " + document.getProcedureCOO()
                + " Shortname: " + document.getTitle()
                + " Filesubj: " + document.getTitle()
        );

        final CreateInternalGI request = new CreateInternalGI();
        request.setUserlogin(user);
        request.setReferrednumber(document.getProcedureCOO());
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setShortname(document.getTitle());
        request.setFilesubj(document.getTitle());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Content content : document.getContents()) {
            files.add(this.parseContent(content));
        }

        request.setGiattachmenttype(attachmentType);

        final CreateInternalGIResponse response = this.wsClient.createInternalGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());

        return response.getObjid();
    }

    @Override
    public void updateDocument(final String documentCOO, final DocumentType type, final List<Content> contents, final String user) {
        switch (type) {
            case EINGEHEND:
                this.updateIncomingDocument(documentCOO, contents, user);
                return;
            case AUSGEHEND:
                this.updateOutgoingDocument(documentCOO, contents, user);
                return;
            case INTERN:
                this.updateInternalDocument(documentCOO, contents, user);
                return;
            default:
                throw new AssertionError("must not happen");
        }
    }

    private void updateIncomingDocument(final String documentCOO, final List<Content> contents, final String user) {
        log.info("calling UpdateIncomingGI: " + documentCOO);

        final UpdateIncomingGI request = new UpdateIncomingGI();
        request.setUserlogin(user);
        request.setObjaddress(documentCOO);
        request.setBusinessapp(this.properties.getBusinessapp());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Content content : contents) {
            files.add(this.parseContent(content));
        }

        request.setGiattachmenttype(attachmentType);

        final UpdateIncomingGIResponse response = this.wsClient.updateIncomingGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());
    }

    private void updateOutgoingDocument(final String documentCOO, final List<Content> contents, final String user) {
        log.info("calling UpdateOutgoingGI: " + documentCOO);

        final UpdateOutgoingGI request = new UpdateOutgoingGI();
        request.setUserlogin(user);
        request.setObjaddress(documentCOO);
        request.setBusinessapp(this.properties.getBusinessapp());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Content content : contents) {
            files.add(this.parseContent(content));
        }

        request.setGiattachmenttype(attachmentType);

        final UpdateOutgoingGIResponse response = this.wsClient.updateOutgoingGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());
    }

    private void updateInternalDocument(final String documentCOO, final List<Content> contents, final String user) {
        log.info("calling UpdateInternalGI: " + documentCOO);

        final UpdateInternalGI request = new UpdateInternalGI();
        request.setUserlogin(user);
        request.setObjaddress(documentCOO);
        request.setBusinessapp(this.properties.getBusinessapp());

        final ArrayOfLHMBAI151700GIAttachmentType attachmentType = new ArrayOfLHMBAI151700GIAttachmentType();
        final List<LHMBAI151700GIAttachmentType> files = attachmentType.getLHMBAI151700GIAttachmentType();

        for (final Content content : contents) {
            files.add(this.parseContent(content));
        }

        request.setGiattachmenttype(attachmentType);

        final UpdateInternalGIResponse response = this.wsClient.updateInternalGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());
    }


    @Override
    public void depositObject(String objectCoo, String user) {
        log.info("calling DepositObject: " + objectCoo);

        final DepositObjectGI request = new DepositObjectGI();
        request.setUserlogin(user);
        request.setBusinessapp(this.properties.getBusinessapp());
        request.setObjaddress(objectCoo);

        final DepositObjectGIResponse response = this.wsClient.depositObjectGI(request);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());
    }

    private LHMBAI151700GIAttachmentType parseContent(final Content content) {
        final LHMBAI151700GIAttachmentType attachment = new LHMBAI151700GIAttachmentType();
        attachment.setLHMBAI151700Filecontent(content.getContent());
        attachment.setLHMBAI151700Fileextension(content.getExtension());
        attachment.setLHMBAI151700Filename(content.getName());
        return attachment;
    }

    @Override
    public void cancelObject(final String objectCoo, final String user) {
        log.info("calling CancelObjectGI"
                + " Userlogin: " + user
                + " Objaddress: " + objectCoo
        );

        final CancelObjectGI cancelObjectGI = new CancelObjectGI();
        cancelObjectGI.setObjaddress(objectCoo);
        cancelObjectGI.setUserlogin(user);
        cancelObjectGI.setBusinessapp(this.properties.getBusinessapp());

        final CancelObjectGIResponse response = this.wsClient.cancelObjectGI(cancelObjectGI);

        dmsErrorHandler.handleError(response.getStatus(), response.getErrormessage());
    }
}
