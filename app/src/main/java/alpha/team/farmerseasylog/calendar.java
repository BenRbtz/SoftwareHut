package alpha.team.farmerseasylog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.app.Dialog;
import android.app.DialogFragment;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;


import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import static android.R.layout.simple_list_item_1;

/**
 * @author Jac Robertson
 */
public class calendar extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private TextView title;
    private CalendarView calendarView;
    private static Document doc;
    private static DocumentBuilder builder;
    private static DocumentBuilderFactory domFactory;
    private String date;
    private ListView lViews;
    private ArrayList<String> titleItems;
    private ArrayAdapter<String> eventsAdapter;
    private File file;

    /**
     * initalises the activity content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewItems();

        setupListViewListener();
    }

    /**
     * finds all the view items and intialises
     */
    public void findViewItems(){
        //gets activity items
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        title = (TextView) findViewById(R.id.eventsTitle);
        lViews = (ListView) findViewById(R.id.eventsList);
    }
    /**
     * sets up view listener
     */
    private void setupListViewListener() {

        lViews.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    /**
                     * delete dialog appears after long hold
                     * @param adapter
                     * @param item
                     * @param pos
                     * @param id
                     * @return
                     */
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        taskSelected(pos);
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    /**
     * Delete dialog to delete an event
     * @param position
     */
    private void taskSelected(final int position) {
       new AlertDialog.Builder(calendar.this)
                .setTitle("Do You Want To Delete Event?")
                .setMessage(eventsAdapter.getItem(position))
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            remove(position);
                        } catch (XPathExpressionException e) {
                            e.printStackTrace();
                        } catch (TransformerException e) {
                            e.printStackTrace();
                        }
                        titleItems.remove(position);// remove event from list
                        eventsAdapter.notifyDataSetChanged();//updates list
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();//closes dialog
                    }
                }).create().show();//creates and shows dialog
    }

    /**
     * updates date variables and displays
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

        date = dayOfMonth + "-" + (month + 1) + "-" + year;//date format
        title.setText(date);//adds date to title
        titleItems = new ArrayList<>();

        try {
            searchFile();//finds events for selected date
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens addEvent activity form
     * @param v
     */
    public void addButtonOnClick(View v) {
        startActivity(new Intent(this, addEvent.class).putExtra("date", date));
    }

    /**
     * Searchs xml for events for dates
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     */
    public void searchFile() throws ParserConfigurationException, IOException, SAXException,
                                                                        XPathExpressionException {

        file = new File(this.getFilesDir(), "events.xml");//find xml in local directory
        domFactory = DocumentBuilderFactory.newInstance();
        builder = domFactory.newDocumentBuilder();
        doc = builder.parse(file);

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        NodeList eventsNodes = (NodeList) xpath.evaluate("/events/event[date = '" + date
                                                        + "']/title", doc, XPathConstants.NODESET);

        for (int i = 0; i < eventsNodes.getLength(); i++) {
            Node n = eventsNodes.item(i);//gets events from xml
            titleItems.add(n.getFirstChild().getNodeValue());//adds to list
        }
        //updates list view with events
        if (!titleItems.isEmpty()) {
            eventsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titleItems);
            lViews.setAdapter(eventsAdapter);
        } else {
            lViews.setAdapter(null);
        }


    }

    /**
     * removes event from xml file
     * @param position
     * @throws XPathExpressionException
     * @throws TransformerException
     */
    public void remove(int position) throws XPathExpressionException, TransformerException {

        //new instialise xpath
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        //finds item by date and title
        XPathExpression expression = xpath.compile("/events/event[date = '" + date + "' and title = '"
                                                + eventsAdapter.getItem(position).toString() + "']");

        //finds node via expression
        Node b13Node = (Node) expression.evaluate(doc, XPathConstants.NODE);

        b13Node.getParentNode().removeChild(b13Node);//removes node

        //rewrites file
        DOMSource source = new DOMSource(doc);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }

}



