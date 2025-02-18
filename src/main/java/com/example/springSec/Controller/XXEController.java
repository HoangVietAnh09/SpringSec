package com.example.springSec.Controller;


import com.example.springSec.transform.XmlData;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@RestController
@RequestMapping("/xxe")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class XXEController {


    //vuln code
    @PostMapping("/1")
    public String parseXML1(@RequestBody String data) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(data)));


        XPath xPath = XPathFactory.newInstance().newXPath();
        String res = xPath.evaluate("/root/data", doc);

        return res;
    }

    //safe code
    @PostMapping("/1/safe")
    public String parseXML1Safe(@RequestBody String data) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        String res = "";
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(data)));

            XPath xPath = XPathFactory.newInstance().newXPath();
            res = xPath.evaluate("/root/data", doc);
        } catch (Exception e){
            log.warn(e.getMessage());
        }
        return res;

    }



    //vuln code
    @PostMapping("/2")
    public String parseXML2(@RequestBody String data) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        StringBuilder sb = new StringBuilder();

        DefaultHandler handler = new DefaultHandler() {
            boolean tag = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if("data".equals(qName)) {
                    tag = true;
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if(tag) {
                    sb.append(new String(ch, start, length));
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if("data".equals(qName)) {
                    tag = false;
                }
            }
        };

        saxParser.parse(new InputSource(new StringReader(data)), handler);
        return sb.toString();
    }


    //safe code
    @PostMapping("/2/safe")
    public String parseXML2Safe(@RequestBody String data) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        StringBuilder sb = new StringBuilder();

        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.setXIncludeAware(false);
        factory.setValidating(false);



        try{
            DefaultHandler handler = new DefaultHandler() {
                boolean tag = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if("data".equals(qName)) {
                        tag = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if(tag) {
                        sb.append(new String(ch, start, length));
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if("data".equals(qName)) {
                        tag = false;
                    }
                }
            };

            saxParser.parse(new InputSource(new StringReader(data)), handler);
        } catch (Exception e){
            log.warn(e.getMessage());
        }

        return sb.toString();
    }


//    @PostMapping("/3")
//    public String parseXml3(@RequestBody String xmlData) throws Exception {
//        JAXBContext context = JAXBContext.newInstance(XmlData.class);
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//
//        unmarshaller.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_DTD, "");
//        unmarshaller.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
//        unmarshaller.setProperty(javax.xml.XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
//
//
//        XmlData data = (XmlData) unmarshaller.unmarshal(new StreamSource(new StringReader(xmlData)));
//
//        return data.getData();
//    }




}
