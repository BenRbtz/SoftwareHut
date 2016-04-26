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

public class onMovementForm extends AppCompatActivity {
    private EditText mEdit1, mEdit2, mEdit3, mEdit4,mEdit5,mEdit6;
    private String herdID,barID, sexID, colourID,birthDate,breedID;
    private String[] gender={"Male","Female"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_movement_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button send = (Button) findViewById(R.id.sendButtonOn);
        mEdit1   = (EditText)findViewById(R.id.herdIDOn);
        mEdit2   = (EditText)findViewById(R.id.barIDOn);
        mEdit3   = (EditText)findViewById(R.id.sexIDOn);
        mEdit4   = (EditText)findViewById(R.id.colourIDOn);
        mEdit5   = (EditText)findViewById(R.id.birthDateOn);
        mEdit6   = (EditText)findViewById(R.id.breedIDOn);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getFieldText();
                if (!(barID.isEmpty() && herdID.isEmpty() && sexID.isEmpty() &&
                        colourID.isEmpty() && birthDate.isEmpty() && breedID.isEmpty())) {

                    new AsyncTask<Void, Void, Boolean>() {

                        @Override
                        protected Boolean doInBackground(Void... params) {

                            MongoPortal portal = new MongoPortal();
                            portal.addEntry(herdID, barID, sexID, colourID, birthDate, breedID, true);
                            return true;
                        }
                    }.execute();
                    finish();
                } else {
                    Toast.makeText(onMovementForm.this, "Fill Empty Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                mEdit5.setText(sdf.format(myCalendar.getTime()));
            }

        };
        final ArrayAdapter<String> spinner_gender = new  ArrayAdapter<String>(onMovementForm.this,android.R.layout.simple_spinner_dropdown_item, gender);

        mEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(onMovementForm.this)
                        .setTitle("Select Gender")
                        .setAdapter(spinner_gender, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                mEdit3.setText(gender[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
        mEdit5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(onMovementForm.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void getFieldText(){
        herdID=mEdit1.getText().toString();
        barID=mEdit2.getText().toString();
        sexID=mEdit3.getText().toString();
        colourID=mEdit4.getText().toString();
        birthDate=mEdit5.getText().toString();
        breedID=mEdit6.getText().toString();
    }
}
