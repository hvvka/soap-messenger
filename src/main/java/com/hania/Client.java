package com.hania;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

//TODO delete
public class Client {

    private static final Logger LOG = Logger.getLogger(Client.class);
    // If you want to add namespace to the header, follow this constant
    private static final String PREFIX_NAMESPACE = "ns";
    private static final String NAMESPACE = "http://other.namespace.to.add.to.header";
    private String uriSOAPServer;
    private SOAPConnection soapConnection = null;

    public Client(final String url) {
        this.uriSOAPServer = url;

        try {
            createSOAPConnection();
        } catch (UnsupportedOperationException | SOAPException e) {
            LOG.error(e);
        }
    }

    public static void main(String[] args) {

    }

    /**
     * Send a SOAP request for a specific operation
     *
     * @param xmlRequestBody the body of the SOAP message
     * @param operation      the operation from the SOAP server invoked
     * @return a response from the server
     * @throws SOAPException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public String sendMessageToSOAPServer(String xmlRequestBody, String operation)
            throws SOAPException, SAXException, IOException, ParserConfigurationException {

        // Send SOAP Message to SOAP Server
        final SOAPElement stringToSOAPElement = stringToSOAPElement(xmlRequestBody);
        final SOAPMessage soapResponse = soapConnection.call(
                createSOAPRequest(stringToSOAPElement, operation),
                uriSOAPServer);

        // Print SOAP Response
        LOG.info("Response SOAP Message : " + soapResponse.toString());
        return soapResponse.toString();
    }

    /**
     * Create a SOAP connection
     *
     * @throws UnsupportedOperationException
     * @throws SOAPException
     */
    private void createSOAPConnection() throws SOAPException {
        SOAPConnectionFactory soapConnectionFactory;
        soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();
    }

    /**
     * Create a SOAP request
     *
     * @param body      the body of the SOAP message
     * @param operation the operation from the SOAP server invoked
     * @return the SOAP message request completed
     * @throws SOAPException
     */
    private SOAPMessage createSOAPRequest(SOAPElement body, String operation) throws SOAPException {

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(PREFIX_NAMESPACE, NAMESPACE);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        soapBody.addChildElement(body);

        // Mime Headers
        MimeHeaders headers = soapMessage.getMimeHeaders();
        LOG.info("SOAPAction : " + uriSOAPServer + operation);
        headers.addHeader("SOAPAction", uriSOAPServer + operation);

        soapMessage.saveChanges();

        /* Print the request message */
        LOG.info("Request SOAP Message :" + soapMessage.toString());
        return soapMessage;
    }

    /**
     * Transform a String to a SOAP element
     *
     * @param xmlRequestBody the string body representation
     * @return a SOAP element
     * @throws SOAPException
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private SOAPElement stringToSOAPElement(String xmlRequestBody)
            throws SOAPException, SAXException, IOException, ParserConfigurationException {

        // Load the XML text into a DOM Document
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        InputStream stream = new ByteArrayInputStream(xmlRequestBody.getBytes());
        Document doc = builderFactory.newDocumentBuilder().parse(stream);

        // Use SAAJ to convert Document to SOAPElement
        // Create SoapMessage
        MessageFactory msgFactory = MessageFactory.newInstance();
        SOAPMessage message = msgFactory.createMessage();
        SOAPBody soapBody = message.getSOAPBody();

        // This returns the SOAPBodyElement that contains ONLY the Payload
        return soapBody.addDocument(doc);
    }
}