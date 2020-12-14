package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.stereotype.remote;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
@Slf4j
public class LunCalInfoItem {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    static {
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(false);
    }

    private static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();

    static LunCalInfoItem unmarshal(final Unmarshaller unmarshaller, final Node node) throws JAXBException {
        requireNonNull(unmarshaller, "unmarshaller is null");
        requireNonNull(node, "node is null");
        final String nodeName = node.getNodeName();
        if (!"item".equals(nodeName)) {
            throw new IllegalArgumentException("node.nodeName(" + nodeName + ") is not 'item'");
        }
        return unmarshaller.unmarshal(node, LunCalInfoItem.class).getValue();
    }

    public static LunCalInfoItem unmarshal(final Node node) throws JAXBException {
        requireNonNull(node, "node is null");
        if (!"item".equals(node.getLocalName())) {
            throw new IllegalArgumentException("node.localName(" + node.getLocalName() + ") is not 'item'");
        }
        final JAXBContext context = JAXBContext.newInstance(LunCalInfoItem.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshal(unmarshaller, node);
    }

    public static void acceptItems(final Document document, final Consumer<? super LunCalInfoItem> consumer)
            throws XPathExpressionException, JAXBException {
        requireNonNull(document, "document is null");
        final XPath xPath = X_PATH_FACTORY.newXPath();
        final String resultCode = (String) xPath
                .compile("/*[local-name()='response'][1]"
                         + "/*[local-name()='header'][1]"
                         + "/*[local-name()='resultCode'][1]"
                         + "/text()")
                .evaluate(document, XPathConstants.STRING);
        if (!"00".equals(resultCode)) {
            final String resultMessage = (String) xPath
                    .compile("/*[local-name()='response'][1]"
                             + "/*[local-name()='header'][1]"
                             + "/*[local-name()='resultMessage'][1]"
                             + "/text()")
                    .evaluate(document, XPathConstants.STRING);
            throw new IllegalArgumentException(
                    "unsuccessful resultCode: " + resultCode + ", resultMessage: " + resultMessage);
        }
        final NodeList nodeList;
        {
            final Element element = (Element) xPath
                    .compile("/*[local-name()='response'][1]"
                             + "/*[local-name()='body'][1]"
                             + "/*[local-name()='items'][1]")
                    .evaluate(document, XPathConstants.NODE);
            nodeList = element.getChildNodes();
        }
        final JAXBContext context = JAXBContext.newInstance(LunCalInfoItem.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        for (int index = 0; index < nodeList.getLength(); index++) {
            final Node node = nodeList.item(index);
            assert node != null;
            if (node.getNodeType() != Node.ELEMENT_NODE || !"item".equals(node.getNodeName())) {
                continue;
            }
            consumer.accept(unmarshal(unmarshaller, node));
        }
    }

    public static List<LunCalInfoItem> getItems(final Document document)
            throws XPathExpressionException, JAXBException {
        final List<LunCalInfoItem> items = new ArrayList<>();
        acceptItems(document, items::add);
        return items;
    }

    public static List<LunCalInfoItem> getItems(final InputSource source)
            throws ParserConfigurationException, SAXException, IOException, XPathExpressionException,
                   JAXBException {
        requireNonNull(source, "source is null");
        final Document document = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().parse(source);
        if (false) {
            try {
                final TransformerFactory factory = TransformerFactory.newInstance();
                final Transformer transformer = factory.newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                transformer.transform(new DOMSource(document),
                                      new StreamResult(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)));
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return getItems(document);
    }

    // -----------------------------------------------------------------------------------------------------------------
    public LunCalInfo toLunCalInfo() {
        final LunCalInfo lunCalInfo = new LunCalInfo();
        lunCalInfo.setSolarDate(getSolarDate());
        lunCalInfo.setLunarDate(getLunarDate());
        lunCalInfo.setSecha(getLunSecha());
        lunCalInfo.setWolgeon(getLunWolgeon());
        lunCalInfo.setIljin(getLunIljin());
        return lunCalInfo;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public LocalDate getLunarDate() {
        return LocalDate.of(Integer.parseInt(lunYear), Integer.parseInt(lunMonth), Integer.parseInt(lunDay));
    }

    public LocalDate getSolarDate() {
        return LocalDate.of(Integer.parseInt(solYear), Integer.parseInt(solMonth), Integer.parseInt(solDay));
    }

    public boolean isLeapMonth() {
        return "윤".equals(lunLeapmonth);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @XmlElement
    private String lunYear;

    @XmlElement
    private String lunMonth;

    @XmlElement
    private String lunDay;

    @XmlElement
    private String solYear;

    @XmlElement
    private String solMonth;

    @XmlElement
    private String solDay;

    @XmlElement
    private String solLeapyear;

    @XmlElement
    private String lunLeapmonth;

    @XmlElement
    private String solWeek;

    @XmlElement
    private String lunSecha;

    @XmlElement(required = false)
    private String lunWolgeon;

    @XmlElement
    private String lunIljin;

    @XmlElement
    private int lunNday;

    @XmlElement
    private int solJd;
}
