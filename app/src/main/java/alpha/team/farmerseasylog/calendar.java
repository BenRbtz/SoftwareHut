package alpha.team.farmerseasylog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class calendar extends AppCompatActivity implements CalendarView.OnDateChangeListener {

   private TextView title;
   private CalendarView calendarView;

    static Document doc;
    static DocumentBuilder builder;
    static DocumentBuilderFactory domFactory;
    private String date;
    private ListView lViews;
    private ArrayList<String> titleItems;
    private ArrayAdapter<String> eventsAdapter;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(this);
        title = (TextView) findViewById(R.id.eventsTitle);
        lViews = (ListView) findViewById(R.id.eventsList);
        setupListViewListener();

    }

    private void setupListViewListener() {

        lViews.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        taskSelected(pos);
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    private void taskSelected(final int position) {
        // 1
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(calendar.this);

        // 2
        alertDialogBuilder.setTitle("Do You Want To Delete Task?");

        // 3
        alertDialogBuilder
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
                        titleItems.remove(position);
                        eventsAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // 4
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 5
        alertDialog.show();
    }






        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

            date = dayOfMonth + "-" + (month+1) + "-" + year;
            title.setText(date);

            titleItems = new ArrayList<>();

            try {
                searchFile();
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

        public void addButtonOnClick(View v) {

            Intent i = new Intent(this, addEvent.class);
            i.putExtra("date", date);
            startActivity(i);

        }

        public void searchFile() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

            file = new File(this.getFilesDir(),"events.xml");
            domFactory = DocumentBuilderFactory.newInstance();
            builder = domFactory.newDocumentBuilder();
            doc = builder.parse(file);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();


            NodeList eventsNodes = (NodeList) xpath.evaluate("/events/event[date = '" + date + "']/title", doc, XPathConstants.NODESET);
            int length = eventsNodes.getLength();

            for(int i = 0; i < length; i++){
                Node n = eventsNodes.item(i);
                titleItems.add(n.getFirstChild().getNodeValue());
            }

            if (!titleItems.isEmpty()){
                eventsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titleItems);
                lViews.setAdapter(eventsAdapter);
            }
            else{
                lViews.setAdapter(null);
            }


    }



    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){

    }

        public void setEventsBar(NodeList eventNodes, int length,String date){
            /*TextView titleView = (TextView) findViewById(R.id.titleView);
            TextView titleView1 = (TextView) findViewById(R.id.titleView1);

            if (length == 1){
                Node sdf = eventNodes.item(1);
                titleView.setText(sdf.getNodeValue());
            }
            else if (length > 1) {
                Node sdf = eventNodes.item(2);
                titleView1.setText(sdf.getNodeValue());
            }*/

        }

        public void remove(int position) throws XPathExpressionException, TransformerException {

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression expression = xpath.compile("/events/event[date = '" + date + "' and title = '" + eventsAdapter.getItem(position).toString() + "']");

            Node b13Node = (Node) expression.evaluate(doc, XPathConstants.NODE);

        b13Node.getParentNode().removeChild(b13Node);

        DOMSource source = new DOMSource(doc);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        StreamResult result = new StreamResult(file);

        transformer.transform(source,result);




        }

    }



