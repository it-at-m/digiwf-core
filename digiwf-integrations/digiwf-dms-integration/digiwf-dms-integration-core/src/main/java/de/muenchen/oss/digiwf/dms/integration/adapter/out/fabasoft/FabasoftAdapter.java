package de.muenchen.oss.digiwf.dms.integration.adapter.out.fabasoft;

import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateProcedureGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateIncomingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.ArrayOfLHMBAI151700GIAttachmentType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIAttachmentType;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateIncomingGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateOutgoingGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateInternalGI;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.CreateInternalGIResponse;
import com.fabasoft.schemas.websvc.lhmbai_15_1700_giwsd.LHMBAI151700GIWSDSoap;
import de.muenchen.oss.digiwf.dms.integration.application.port.out.ProcedureRepository;
import de.muenchen.oss.digiwf.dms.integration.domain.Content;
import de.muenchen.oss.digiwf.dms.integration.domain.Procedure;
import de.muenchen.oss.digiwf.dms.integration.domain.Document;
import de.muenchen.oss.digiwf.message.process.api.error.IncidentError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FabasoftAdapter implements ProcedureRepository {

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

    public String createDocument(final Document document, final String user) {
        //logging for dms team
        log.info("calling CreateIncomingGI"
                + " Userlogin: " + user
                + " Referrednumber: " + document.getProcedureCOO()
                + " Shortname: " + document.getTitle()
                + " Filesubj: " + document.getTitle()
        );

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

        dmsErrorHandler.handleError(response.getStatus(),response.getErrormessage());

        return response.getObjid();
    }

    private String createOutgoingDocument(final Document document, final String user)  {
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

        dmsErrorHandler.handleError(response.getStatus(),response.getErrormessage());

        return response.getObjid();
    }

    private String createInternalDocument(final Document document, final String user) {
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

        dmsErrorHandler.handleError(response.getStatus(),response.getErrormessage());

        return response.getObjid();
    }


    private LHMBAI151700GIAttachmentType parseContent(final Content content) {
        final LHMBAI151700GIAttachmentType attachment = new LHMBAI151700GIAttachmentType();
        attachment.setLHMBAI151700Filecontent(content.getContent());
        attachment.setLHMBAI151700Fileextension(content.getExtension());
        attachment.setLHMBAI151700Filename(content.getName());
        return attachment;
    }

}
