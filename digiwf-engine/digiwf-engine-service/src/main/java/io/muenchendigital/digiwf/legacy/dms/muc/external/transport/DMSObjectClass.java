/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package io.muenchendigital.digiwf.legacy.dms.muc.external.transport;

/**
 * All known object classes to provide soap requests with.
 *
 * @author martin.dietrich
 */
public enum DMSObjectClass {

    Fileplan("COOELAK@1.1001:Fileplan"),
    Aktenplaneintrag("COOELAK@1.1001:SubjectArea"),
    Ausgang("COOELAK@1.1001:Outgoing"),
    AutoCAD_Zeichnung("FSCAUTOCAD@1.1001:Drawing"),
    Benutzer("COOSYSTEM@1.1:User"),
    Bericht("FSCAREXT@1.1001:Report"),
    Bild("FSCWEBCONT@1.1001:ImageObject"),
    Dokumentkategorie("FSCFOLIO@1.1001:DocumentCategory"),
    Dokumenttyp("COOELAK@1.1001:Subject"),
    E_Mail_Microsoft_Office_Outlook("COOMAPI@1.1:MailObject"),
    E_Mail_MIME("FSCMIME@1.1001:MIMEObject"),
    Eingang("COOELAK@1.1001:Incoming"),
    Freemind_Mindmap("CFGMUENCHEN@15.1700:FreemindMindmap"),
    Frist("ELAKGOV@1.1001:Deadline"),
    GIF_Objekt("FSCWEBCONT@1.1001:GIFObject"),
    Hewlett_Packard_Graphic_Language("CFGMUENCHEN@15.1700:HPGraphicLanguage"),
    Inhalt_erweitert("CFGMUENCHEN@15.1700:GenericContent"),
    Inhalt_unbekannter_Typ("GENCONT@1.1:ContentObject"),
    JPEG_Objekt("FSCWEBCONT@1.1001:JPEGObject"),
    Kostenstelle("CFGMUENCHEN@15.1700:CostCenter"),
    Medieninhalt("FSCDIGITALASSET@1.1001:DigitalAsset"),
    Microsoft_Excel_Arbeitsblatt("COOMSOFFICE@1.1:ExcelObject"),
    Microsoft_PowerPoint_Präsentation("COOMSOFFICE@1.1:PowerPointObject"),
    Microsoft_Project_Objekt("COOMSPROJECT@1.1001:MSProject"),
    Microsoft_Visio_Zeichnung("VISIO@1.1:VisioObject"),
    Microsoft_Word_Objekt("COOMSOFFICE@1.1:WinWordObject"),
    OpenDocument_Formel("FSCOOFFICE@1.1001:OOorgMathObject"),
    OpenOffice_org_Vorlage("CFGMUENCHEN@15.1700:OOorgTemplateObject"),
    Ordner("COODESK@1.1:Folder"),
    Organisation("FSCFOLIO@1.1001:Organisation"),
    Organisationseinheit("COOSYSTEM@1.1:Group"),
    PDF_Dokument("FSCACROBAT@1.1:PDFObject"),
    Person("FSCFOLIO@1.1001:Person"),
    Personenakte("CFGMUENCHEN@15.1700:PersonalSubjectAreaFile"),
    PNG_Objekt("FSCWEBCONT@1.1001:PNGObject"),
    Postkorb("FSCVGOV@1.1001:Inbox"),
    Präsentation("FSCOOFFICE@1.1001:OOorgPresObject"),
    Projektordner("COOELAK@1.1001:ProjectFolder"),
    Rechnungsposition("CFGMUENCHEN@15.1700:InvoiceItem"),
    Rich_Text_Format("CFGMUENCHEN@15.1700:RichTextFormat"),
    Sachakte("DEPRECONFIG@15.1001:SubjectAreaFile"),
    Schlagwort("FSCTERM@1.1001:Term"),
    Schmierzettel("ELAKGOV@1.1001:FileMemo"),
    Schriftstück("COOSYSTEM@1.1:ContentObject "),
    Serena_OpenProj_Projekt("CFGMUENCHEN@15.1700:SerenaOpenProj"),
    Suchordner("COOQBOL@1.1:GenericStoredQBOL"),
    Tabelle("FSCOOFFICE@1.1001:OOorgCalcObject"),
    Teamroom("FSCTEAMROOM@1.1001:TeamRoom"),
    Text_Dokument("NOTE@1.1:NoteObject"),
    Textbaustein("COOAR@1.1:BasicTextModuleEx_Web"),
    Textdokument("FSCOOFFICE@1.1001:OOorgTextObject"),
    TIFF_Objekt("FSCWEBCONT@1.1001:TIFFObject"),
    Verteiler("COOELAK@1.1001:DistributionList"),
    Verteilerliste("COOWF@1.1:ParticipantInstance"),
    Vorgang("DEPRECONFIG@15.1001:Procedure"),
    Vorlagenkategorie("COOTC@1.1001:TemplateCategory"),
    XDOMEA_Paket("FSCGOVXMLDE@1.1001:XDOMEAPackage"),
    Zeichnung("FSCOOFFICE@1.1001:OOorgDrawObject"),
    Zip_Archiv("WINZIP@1.1001:WinZipObject");

    private final String name;

    DMSObjectClass(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
