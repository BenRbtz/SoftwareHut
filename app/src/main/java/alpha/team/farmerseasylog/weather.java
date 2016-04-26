package alpha.team.farmerseasylog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class weather extends AppCompatActivity {


    ImageView iconView;
    TextView tempView;
    TextView locationView;
    TextView conditionView;
    WeatherService service;
    ArrayList<String> forecastArray;
    private ArrayAdapter<String> forecastAdapter;
    ListView forecastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iconView = (ImageView) findViewById(R.id.iconView);
        tempView = (TextView) findViewById(R.id.temperatureTextView);
        locationView = (TextView) findViewById(R.id.locationTextView);
        conditionView = (TextView) findViewById(R.id.conditionTextView);
        forecastView = (ListView) findViewById(R.id.forecastListvView);

        forecastArray = new ArrayList<>();


        new AsyncTask <Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    service = new WeatherService();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(Void v) {
                createTodayWeather();
                try {
                    createForecaseWeather();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();




    }

    public void createTodayWeather(){
        tempView.setText(service.getTemperature() + "F");
        locationView.setText(service.getLocation());
        conditionView.setText(service.getCondition());
        int resouceID = getResources().getIdentifier("drawable/icon_" + service.getCode(),null,getPackageName());
        iconView.setImageResource(resouceID);




}

    public void createForecaseWeather() throws JSONException {
        JSONArray forecast = service.getForecast();


        for (int i = 0; i < 6; i++){
            JSONObject obj = forecast.getJSONObject(i);
            String title = obj.optString("date") + ": " + obj.optString("text");
            forecastArray.add(title);
        }

        forecastAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,forecastArray);
        forecastView.setAdapter(forecastAdapter);









    }




}
