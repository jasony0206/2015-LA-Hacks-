package com.example.eric.lahacks2015;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weather.WeatherObject;

/**
 * Created by Eric on 4/4/2015.
 */
public class WeatherParser {
    private static final String TAG = "WeatherParser";
    public static WeatherObject parseJSON(String json){
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WeatherObject mWeatherObj = new WeatherObject();
        //Get name
        try {
            mWeatherObj.name = obj.getString("name");
        }catch (JSONException e){
            Log.e(TAG, "Something wrong with getting name" + e.getMessage());
        }
        //Get temperature and humidity
        try {
            JSONObject main = obj.getJSONObject("main");
            mWeatherObj.temperature = main.getDouble("temp");
            mWeatherObj.humidity = main.getInt("humidity");
        }catch (JSONException e){
            Log.e(TAG, "Something wrong with getting name" + e.getMessage());
        }
        //Get description and icon
        try {
            JSONArray weather = obj.getJSONArray("weather");
            mWeatherObj.description = weather.getJSONObject(0).getString("description");
            mWeatherObj.icon = weather.getJSONObject(0).getString("icon");
        }catch (JSONException e){
            Log.e(TAG, "Something wrong with getting name" + e.getMessage());
        }

        return mWeatherObj;
    }
}
