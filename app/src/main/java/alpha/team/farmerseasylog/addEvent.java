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

    String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        date = b.getString("date");




    }

    public void submitButtonOnClick(View v) throws IOException, SAXException, ParserConfigurationException, URISyntaxException {


        XMLWritter write = new XMLWritter(this);


        EditText titleView = (EditText) findViewById(R.id.titleView);
        EditText descView = (EditText) findViewById(R.id.descView);


        String title = titleView.getText().toString();
        String desc = descView.getText().toString();


        write.run(title,date,desc);

    }

}
