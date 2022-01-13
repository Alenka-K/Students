package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class XmlApi {


    public static Document buildDocument(String  nameFile) throws ParserConfigurationException {

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    dbFactory.setValidating(true); // проверка DTD (анализатор будет проверять документ по мере его анализа)
    DocumentBuilder builder = dbFactory.newDocumentBuilder();
    builder.setErrorHandler(new StudentErrorHandler());// StudentErrorHandler будет использоваться синтаксическим анализаторо

    Document doc = null;
    try {
        doc = builder.parse(nameFile);//анализирует содержимое файла как XML-документ и верните новый Document объект
    } catch (SAXException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    doc.getDocumentElement().normalize();
    return doc;
}


    public static void validAverage (Document document){

        NodeList studentList = document.getElementsByTagName("student"); // получаем узлы с именем student
        for (int i = 0; i < studentList.getLength(); i++) {
            if (studentList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element student = (Element) studentList.item(i);
                System.out.println(student.getAttribute("firstname") + " " + student.getAttribute("lastname") + " (група: " + student.getAttribute("groupnumber") + ")");
                NodeList childNodes = student.getChildNodes();
                int sumMark = 0;
                int sumSubject = 0;
                String average;
                String checkAverage;
                for (int j = 0; j < childNodes.getLength(); j++) {
                    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNodes.item(j);
                        switch (childElement.getNodeName()) {
                            case "subject": {
                                System.out.println(childElement.getAttribute("title") + " : " + childElement.getAttribute("mark"));
                                sumMark = sumMark + Integer.parseInt(childElement.getAttribute("mark"));
                                sumSubject = sumSubject + 1;
                            }
                            break;
                            case "average": {
                                average = childElement.getTextContent();
                                checkAverage = String.format("%.1f", (double) sumMark / (double) sumSubject);
                                if (!average.equals(checkAverage)) {
                                    childElement.setTextContent(checkAverage);
                                    System.out.println("Середній бал (" + average + ") був обчислений не корректно! Правильний розрахунок - " + childElement.getTextContent());
                                } else {
                                    System.out.println("Середній бал: " + childElement.getTextContent());
                                }
                                System.out.println("______________________________");
                            }
                            break;
                        }

                    }

                }

            }
        }
    }

    public static void writeNewXml(Document document) {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (
                TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(document);
        StreamResult consoleResult = new StreamResult("result.xml");
        try {
            transformer.transform(source, consoleResult);
        } catch (
                TransformerException e) {
            e.printStackTrace();
        }
    }
}
