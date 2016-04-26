/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package alpha.team.farmerseasylog;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author jacrobertson
 */

public class XMLWritter {

    static Element mainRootElement;
    static Document doc;
    static DocumentBuilder builder;
    static DocumentBuilderFactory domFactory;
    Context context;
    File file;

    public XMLWritter(Context context){
        this.context = context;

    }

    public  void run(String title, String date, String desc) throws ParserConfigurationException, IOException, SAXException, URISyntaxException {



        File file = new File(context.getFilesDir(),"events.xml");
        domFactory = DocumentBuilderFactory.newInstance();
        builder = domFactory.newDocumentBuilder();



        doc = builder.parse(file);


        mainRootElement = (Element) doc.getFirstChild();

        try{
            mainRootElement.appendChild(setSearches(doc, title, date, desc));
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();



            StreamResult result = new StreamResult(file);

            transformer.transform(source,result);


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }


    }


    public Element createStartElements(){
        Element main = (Element) doc.createElement("events");

        return main;



    }

    private static Node setSearches(Document doc,String title, String date, String desc){
        Element event = doc.createElement("event");

        event.appendChild(setNode(doc, "title", title));
        event.appendChild(setNode(doc,"date",date));
        event.appendChild(setNode(doc,"description",desc));
       return event;
    }

    private static Node setNode(Document doc, String tag, String value) {
        Element node = doc.createElement(tag);

        node.appendChild(doc.createTextNode(value));

        return node;

    }






}
