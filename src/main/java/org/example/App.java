package org.example;

import org.w3c.dom.Document;
import javax.xml.parsers.*;


public class App {

    public static void main(String[] args) throws ParserConfigurationException {


        Document students = XmlApi.buildDocument(args[0]);
        XmlApi.validAverage(students);
        XmlApi.writeNewXml(students);



    }
}
