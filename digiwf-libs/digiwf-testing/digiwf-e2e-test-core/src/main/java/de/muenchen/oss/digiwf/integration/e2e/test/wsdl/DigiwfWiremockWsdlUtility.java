package de.muenchen.oss.digiwf.integration.e2e.test.wsdl;

import com.github.tomakehurst.wiremock.WireMockServer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPMessage;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.function.Predicate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class DigiwfWiremockWsdlUtility {

    /**
     * Accepts a WebService response object (as defined in the WSDL) and marshals
     * to a SOAP envelope String.
     */
    static <T> String serializeObject(T object) {
        ByteArrayOutputStream byteArrayOutputStream;
        Class clazz = object.getClass();
        String responseRootTag = StringUtils.uncapitalize(clazz.getSimpleName());
        QName payloadName = new QName("your_namespace_URI", responseRootTag, "namespace_prefix");

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller objectMarshaller = jaxbContext.createMarshaller();

            JAXBElement<T> jaxbElement = new JAXBElement<>(payloadName, clazz, null, object);
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            objectMarshaller.marshal(jaxbElement, document);

            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
            SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();
            body.addDocument(document);

            byteArrayOutputStream = new ByteArrayOutputStream();
            soapMessage.saveChanges();
            soapMessage.writeTo(byteArrayOutputStream);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Exception trying to serialize [%s] to a SOAP envelope", object), e);
        }

        return byteArrayOutputStream.toString();
    }

    /**
     * Accepts a WebService request object (as defined in the WSDL) and unmarshals
     * to the supplied type.
     */
    static <T> T deserializeSoapRequest(String soapRequest, Class<T> clazz) {

        XMLInputFactory xif = XMLInputFactory.newFactory();
        JAXBElement<T> jb;
        try {
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(soapRequest));

            // Advance the tag iterator to the tag after Body, eg the start of the SOAP payload object
            do {
                xsr.nextTag();
            } while (!xsr.getLocalName().equals("Body"));
            xsr.nextTag();

            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            jb = unmarshaller.unmarshal(xsr, clazz);
            xsr.close();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Unable to deserialize request to type: %s. Request \n %s", clazz, soapRequest), e);
        }

        return jb.getValue();
    }

    public static <T> void stubOperation(String operation, Class<T> clazz, Predicate<T> predicate, Object response) {
        stubFor(requestMatching(new SoapObjectMatcher<>(clazz, operation, predicate))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/xml")
                        .withBody(serializeObject(response))));
    }

    public static <T> void stubOperation(WireMockServer server, String operation, Class<T> clazz, Predicate<T> predicate, Object response) {
        server.stubFor(requestMatching(new SoapObjectMatcher<>(clazz, operation, predicate))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/xml")
                        .withBody(serializeObject(response))));
    }
}
