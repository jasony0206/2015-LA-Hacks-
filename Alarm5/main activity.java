package com.example.jason.alarm5;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AlarmMainActivity extends Activity {

    public class Alarm{
        int aid;
        Calendar cal;
    };

    public Alarm[] alarmArray;
    private TimePicker timePicker;
    private int index;
    private int alarmID;
    ListView listView;
    public ArrayList<String> alarmString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm_main);
        index = 0;
        alarmArray = new Alarm[10];
        alarmString = new ArrayList<String>();

    }

    public void daySelected(View view) {
        Button b = (Button) view;
        if(b.isActivated()) {
            b.setTextColor(Color.parseColor("black"));
            b.setBackgroundColor(Color.rgb(102, 204, 255));
            b.setActivated(false);
        }
        else {
            b.setTextColor(Color.parseColor("white"));
            b.setBackgroundColor(Color.parseColor("blue"));
            b.setActivated(true);
        }
    }

    public void exitApp(View view) {
        finish();
    }

    public void setAlarm(View view) {
        //Create an offset from the current time in which the alarm will go off.
        Calendar tempCal = Calendar.getInstance();
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        tempCal.set(Calendar.HOUR, timePicker.getCurrentHour());
        tempCal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        tempCal.set(Calendar.SECOND, 0);
        alarmArray[index] = new Alarm();
        alarmArray[index].cal = tempCal;
        alarmArray[index].aid = alarmID;

        //Create a new PendingIntent and add it to the AlarmManager
        Intent intent = new Intent(this, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                alarmID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        //am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Locale locale = Locale.getDefault();
        String time =  Integer.toString(tempCal.get(Calendar.HOUR))
                + " : " + Integer.toString(tempCal.get(Calendar.MINUTE));
        if(tempCal.get(Calendar.AM_PM) == 1)
            time += " AM\n";
        else
            time += " PM\n";

        alarmString.add(index, time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alarmString);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        index++;
        index %= 10;
        alarmID++;
    }


}
