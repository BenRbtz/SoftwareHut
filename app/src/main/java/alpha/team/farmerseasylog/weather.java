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

/**
 * @author Jac Robertson
 */
public class weather extends AppCompatActivity {
    private ImageView iconView;
    private TextView tempView,locationView,conditionView;
    private WeatherService service;
    private ArrayList<String> forecastArray;
    private ArrayAdapter<String> forecastAdapter;
    private ListView forecastView;

    /**
     * initalises the activity content
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewItems();
        forecastArray = new ArrayList<>();
        getWeatherForecast();
    }

    /**
     * finds all the view items and intialises
     */
    private void findViewItems() {
        iconView = (ImageView) findViewById(R.id.iconView);
        tempView = (TextView) findViewById(R.id.temperatureTextView);
        locationView = (TextView) findViewById(R.id.locationTextView);
        conditionView = (TextView) findViewById(R.id.conditionTextView);
        forecastView = (ListView) findViewById(R.id.forecastListvView);

    }

    /**
     * gets the weather forecast
     */
    public void getWeatherForecast(){
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    service = new WeatherService();//gets new weather service
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                createTodayWeather();// gets today weather
                try {
                    createForecastWeather();//gets weeks weather
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /**
     * Gets Today's weather and displays on top of view
     */
    public void createTodayWeather() {
        tempView.setText(service.getTemperature() + "F");//adds temperature to temp view
        locationView.setText(service.getLocation()); //adds location to location view
        conditionView.setText(service.getCondition()); // adds conditions to condition view
        int resourceID = getResources().getIdentifier("drawable/icon_" + service.getCode(), null, getPackageName());
        iconView.setImageResource(resourceID);//displays weather image
    }

    /**
     * Get weather forecast for the week and displays
     * @throws JSONException
     */
    public void createForecastWeather() throws JSONException {
        JSONArray forecast = service.getForecast();//gets weather
        //7 Day Forecast
        for (int i = 0; i < 6; i++) {
            JSONObject obj = forecast.getJSONObject(i);//gets weather for day
            String title = obj.optString("date") + ": " + obj.optString("text");//formats weather
            forecastArray.add(title);//adds to forecast array
        }

        forecastAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, forecastArray);
        forecastView.setAdapter(forecastAdapter);//updates forecast
    }
}
