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
 * @author Jac Robertson
 */
public class XMLWritter {

    private static Element mainRootElement;
    private static Document doc;
    private static DocumentBuilder builder;
    private static DocumentBuilderFactory domFactory;
    private Context context;
    private File file;

    /**
     * sets context local
     * @param context
     */
    public XMLWritter(Context context){
        this.context = context;
    }

    /**
     *adds weather forecast into xml file
     * @param title - event title
     * @param date - date of event
     * @param desc - description of event
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws URISyntaxException
     */
    public  void run(String title, String date, String desc) throws ParserConfigurationException,
                                                    IOException, SAXException, URISyntaxException {

        file = new File(context.getFilesDir(),"events.xml");//gets xml file from local directory
        //creates dom writer
        domFactory = DocumentBuilderFactory.newInstance();
        builder = domFactory.newDocumentBuilder();
        doc = builder.parse(file);

        mainRootElement = (Element) doc.getFirstChild();//gets root node

        try{
            mainRootElement.appendChild(setSearches(doc, title, date, desc));// adds event as root child
            //writes to file
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

    /**
     * makes child event node
     * @param doc - xml file
     * @param title - event title
     * @param date - event date
     * @param desc - event description
     * @return event - event node
     */
    private static Node setSearches(Document doc,String title, String date, String desc){
        Element event = doc.createElement("event");

        event.appendChild(setNode(doc, "title", title));
        event.appendChild(setNode(doc,"date",date));
        event.appendChild(setNode(doc,"description",desc));
       return event;
    }

    /**
     *makes a node
     * @param doc - xml file
     * @param tag - event tag
     * @param value - event tag value
     * @return node - tag node
     */
    private static Node setNode(Document doc, String tag, String value) {
        Element node = doc.createElement(tag);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}
