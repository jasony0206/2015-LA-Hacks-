package com.example.eric.lahacks2015;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import request.WeatherHttpClient;
import weather.WeatherObject;


public class MainActivity extends ActionBarActivity {
    TextView cityText;
    TextView condDescr;
    TextView temperature;
    TextView hum;
    ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String city = "Rome,IT";

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temperature = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        imgView = (ImageView) findViewById(R.id.condIcon);
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, WeatherObject> {
        @Override
        protected WeatherObject doInBackground(String... params) {
            WeatherObject weather = new WeatherObject();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
            weather = WeatherParser.parseJSON(data);
            weather.iconData = ((new WeatherHttpClient()).getImage(weather.icon));
            return weather;
        }

        @Override
        protected void onPostExecute(WeatherObject weather) {
            super.onPostExecute(weather);

            cityText.setText(weather.name);
            condDescr.setText(weather.description);
            temperature.setText(Double.toString(weather.temperature) + " Kelvin");
            hum.setText(String.valueOf(weather.humidity) + "%");
            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }
        }
    }

}
