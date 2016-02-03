/**
 *
 */
package com.lip.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Li Pei
 *
 * Andrew ID : lip
 */

public class DOMParserDemo {
    public void readInXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse("dblp_truncate.xml");
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        document.getDocumentElement().normalize();
        System.out.println("Root Element : " + document.getDocumentElement().getNodeName());
        NodeList nodeList = document.getElementsByTagName("article");
        System.out.println(nodeList.getLength());

        DatabaseIO db = new DatabaseIO();

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            // Identifying the child tag of employee encountered
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Publication pub = new Publication();

                pub.idkey = node.getAttributes().getNamedItem("key").getNodeValue();
                pub.mdate = node.getAttributes().getNamedItem("mdate").getNodeValue();

                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);
                    if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                        String content = cNode.getLastChild().getTextContent().trim();

                        // replace all ' with space
                        if (content.indexOf('\'') != -1) {
                            content = content.replace("'", "''");
                        }

                        switch (cNode.getNodeName()) {
                        case "author":
                            pub.author.add(content);
                            break;
                        case "title":
                            pub.title = content;
                            break;
                        case "pages":
                            pub.pages = content;
                            break;
                        case "year":
                            pub.year = content;
                            break;
                        case "volume":
                            pub.volume = content;
                            break;
                        case "journal":
                            pub.journal = content;
                            break;
                        case "number":
                            pub.numbers = content;
                            break;
                        case "url":
                            pub.url = content;
                        case "ee":
                            pub.ee = content;
                            break;
                        }
                    }

                } // end of for and build a complete publication
                db.addToDatabase(pub,temp+1);
            }
        }
    }


    public static void main(String[] args) {
        DatabaseIO db = new DatabaseIO();
        db.createDataBase();
        DOMParserDemo domparser = new DOMParserDemo();
        domparser.readInXML();

    }


}
