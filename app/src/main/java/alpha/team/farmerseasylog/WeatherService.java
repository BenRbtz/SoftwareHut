package alpha.team.farmerseasylog;

import android.app.Dialog;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by jacrobertson on 26/04/16.
 */
public class WeatherService {

    private JSONObject data;
    private JSONObject results;


    public WeatherService() throws UnsupportedEncodingException {
        String file = createFile();

        try {
            data = new JSONObject(file);
            JSONObject query = data.optJSONObject("query");
            results = query.optJSONObject("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String createFile() throws UnsupportedEncodingException {

        String yql = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"Bangor Wales\")";
        String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", URLEncoder.encode(yql,"UTF-8"));

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(endpoint);
            URLConnection connection = url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            String line;
        while((line = reader.readLine()) != null){
                result.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }


    public String getTemperature(){

        String temp = results.optJSONObject("channel").optJSONObject("item").optJSONObject("condition").optString("temp");

        return temp;

    }

    public String getLocation(){
        String city = results.optJSONObject("channel").optJSONObject("location").optString("city");
        String region = results.optJSONObject("channel").optJSONObject("location").optString("region");

        String result = city + "," + region;

        return result;
    }

    public String getCondition(){

        String cond = results.optJSONObject("channel").optJSONObject("item").optJSONObject("condition").optString("text");
        return cond;


    }

    public int getCode(){
        int code = results.optJSONObject("channel").optJSONObject("item").optJSONObject("condition").optInt("code");
        return code;
    }

    public JSONArray getForecast() throws JSONException {
        JSONArray forecast = results.optJSONObject("channel").optJSONObject("item").optJSONArray("forecast");

        return forecast;

    }






}
