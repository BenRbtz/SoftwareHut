package alpha.team.farmerseasylog;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

public class addEvent extends AppCompatActivity {

    private String date;
    private EditText titleView,descView;

    /**
     * Sets up activity content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleView = (EditText) findViewById(R.id.titleView);
        descView = (EditText) findViewById(R.id.descView);

        Bundle b = getIntent().getExtras();
        date = b.getString("date");//gets date selected from bundle
    }

    /**
     * Adds form content to calender
     * @param v
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws URISyntaxException
     */
    public void submitButtonOnClick(View v) throws IOException, SAXException,
                                                ParserConfigurationException, URISyntaxException {

        XMLWritter write = new XMLWritter(this);// create xml writer

        String title = titleView.getText().toString();//gets title from title view
        String desc = descView.getText().toString();//gets description from desc view

        write.run(title,date,desc);//writes event to xml
        finish();//close activity
    }

}
