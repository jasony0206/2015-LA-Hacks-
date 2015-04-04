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
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;


public class AlarmMainActivity extends Activity {

    private TimePicker timePicker;
    private int alarmID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_alarm_main);
        alarmID = 1;
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
        Calendar cal = Calendar.getInstance();
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        //cal.set(Calendar.HOUR, timePicker.getCurrentHour());
        cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
        cal.set(Calendar.SECOND, 0);

        //Create a new PendingIntent and add it to the AlarmManager
        Intent intent = new Intent(this, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                alarmID, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmID++;
        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }


}
