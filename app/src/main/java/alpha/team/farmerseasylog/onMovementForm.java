package alpha.team.farmerseasylog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
/**
 * @author Ben Roberts
 */
public class onMovementForm extends AppCompatActivity {
    private EditText herdField,barNumField,sexField,colourField,calenderField,breedField;
    private Button sendButton;
    private String herdID, barID, sexID, colourID, birthDate, breedID;
    private final Calendar myCalendar = Calendar.getInstance();
    private ArrayAdapter<String> spinner_gender;
    private DatePickerDialog.OnDateSetListener date;
    private String[] gender = {"Male", "Female"};

    /**
     * Sets up onMovement form activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_movement_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewItems();
        sendButtonSetup();
        calenderSetuo();

        spinner_gender = new ArrayAdapter<String>(onMovementForm.this,
                                            android.R.layout.simple_spinner_dropdown_item, gender);
        fieldSetup();

    }

    /**
     * Sets up sex field listener and calender listener
     */
    private void fieldSetup() {
        sexField.setOnClickListener(new View.OnClickListener() {
            /**
             * opens alert dialog with gender selection
             * @param v
             */
            @Override
            public void onClick(View v) {
                //opens alert gender dialog
                new AlertDialog.Builder(onMovementForm.this)
                        .setTitle("Select Gender")
                        .setAdapter(spinner_gender, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                sexField.setText(gender[which].toString());//sets the select dropdown item to the sex Field
                                dialog.dismiss();//closes dialog
                            }
                        }).create().show();
            }
        });

        calenderField.setOnClickListener(new View.OnClickListener() {
            /**
             * creates a date picker dialog when calenderField is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                new DatePickerDialog(onMovementForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /**
     * Calender field setup
     */
    private void calenderSetuo() {

        date = new DatePickerDialog.OnDateSetListener() {
            /**
             * gets date pressed and inserts into calender field on form
             * @param view
             * @param year - year
             * @param monthOfYear - month
             * @param dayOfMonth - day
             */
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.UK);//uk date format

                calenderField.setText(format.format(myCalendar.getTime()));// sets date to calender field
            }

        };
    }

    /**
     * Sets up send button listener
     */
    private void sendButtonSetup() {
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getFieldText();//gets content of field and stores in variables
                //checks if any of the field variables were not empty
                if (!(barID.isEmpty() || herdID.isEmpty() || sexID.isEmpty() ||
                        colourID.isEmpty() || birthDate.isEmpty() || breedID.isEmpty())) {

                    new AsyncTask<Void, Void, Boolean>() {

                        @Override
                        protected Boolean doInBackground(Void... params) {

                            MongoPortal portal = new MongoPortal();// makes connection to db
                            portal.addEntry(herdID, barID, sexID, colourID, birthDate, breedID, true);//sends form to db
                            return true;
                        }
                    }.execute();
                    finish();//closes activity form
                } else {
                    Toast.makeText(onMovementForm.this, "Fill Empty Fields", Toast.LENGTH_SHORT).show();// small pop-up alerting for empty fields
                }

            }
        });
    }

    /**
     * finds all the view items and intialises
     */
    private void findViewItems() {
        sendButton = (Button) findViewById(R.id.sendButtonOn);//finds send button
        herdField = (EditText) findViewById(R.id.herdIDOn);//finds herd number field
        barNumField = (EditText) findViewById(R.id.barIDOn);//finds bar number field
        sexField = (EditText) findViewById(R.id.sexIDOn);//finds sex field
        colourField = (EditText) findViewById(R.id.colourIDOn);//finds colour field
        calenderField = (EditText) findViewById(R.id.birthDateOn);//finds birth date field
        breedField = (EditText) findViewById(R.id.breedIDOn);//finds breed field
    }

    /**
     * gets content of all form fields
     */
    private void getFieldText() {
        herdID = herdField.getText().toString();
        barID = barNumField.getText().toString();
        sexID = sexField.getText().toString();
        colourID = colourField.getText().toString();
        birthDate = calenderField.getText().toString();
        breedID = breedField.getText().toString();
    }
}
