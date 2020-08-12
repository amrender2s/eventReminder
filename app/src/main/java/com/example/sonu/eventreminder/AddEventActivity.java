package com.example.sonu.eventreminder;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import com.example.sonu.eventreminder.EventContracts;
import java.util.logging.Handler;

import static android.content.Context.ALARM_SERVICE;
import static java.util.Calendar.SECOND;


public class AddEventActivity extends AppCompatActivity  {
    AlarmManager processTimer;
    PendingIntent alarmIntent;

    String day, month, year, min, hour;
    Calendar cal;
    RadioButton today, tomorrow, calendar;
    Button bt1, bt2, bt3;
    String d, d1, d2;
    Timer timer;
    TimerTask timerTask;
    Handler handler;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventDbHelper eventDbHelper=new EventDbHelper(this);
        sqLiteDatabase=eventDbHelper.getWritableDatabase();

        today = (RadioButton) findViewById(R.id.today);
        tomorrow = (RadioButton) findViewById(R.id.today);
        calendar = (RadioButton) findViewById(R.id.onCalander);
        bt1 = (Button) findViewById(R.id.duration1);
        bt1.setBackgroundColor(Color.TRANSPARENT);
        bt2 = (Button) findViewById(R.id.duration2);
        bt2.setBackgroundColor(Color.TRANSPARENT);
        bt3 = (Button) findViewById(R.id.duration3);
        bt3.setBackgroundColor(Color.TRANSPARENT);
        cal = Calendar.getInstance();

        processTimer = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void today(View view) {
        d2 = DateFormat.getDateInstance().format(new Date());
        day = "" + cal.get(Calendar.DAY_OF_MONTH);
        month = "" + cal.get(Calendar.MONTH) + 1;
        year = "" + cal.get(Calendar.YEAR);
        Toast.makeText(getApplicationContext(), "Reminder is for : " + day + " " + "" + month + " " + year, Toast.LENGTH_SHORT).show();
    }

    public void tomorrow(View view) {
        d2 = DateFormat.getDateInstance().format(new Date());
        day = "" + (cal.get(Calendar.DAY_OF_MONTH) + 1);
        month = "" + cal.get(Calendar.MONTH) + 1;
        year = "" + cal.get(Calendar.YEAR);
        Toast.makeText(getApplicationContext(), "Reminder is for : " + day + " " + "" + month + " " + year, Toast.LENGTH_SHORT).show();
    }

    public void calendar(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearr, int monthh, int dayOfMonth) {
                day = "" + dayOfMonth;
                month = "" + (monthh + 1);
                year = "" + yearr;
                try {
                    d2 = DateFormat.getDateInstance().format(new SimpleDateFormat("dd/MM/yy").parse(dayOfMonth + "/" + monthh + "/" + yearr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Reminder is for : " + day + " " + "" + month + " " + year, Toast.LENGTH_SHORT).show();
            }
        };
        cal = Calendar.getInstance();
        int yearr = cal.get(java.util.Calendar.YEAR);
        int monthh = cal.get(java.util.Calendar.MONTH);
        int dayOfMonth = cal.get(java.util.Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, yearr, monthh, dayOfMonth);
        datePickerDialog.show();
    }

    public void time(View view) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                min = "" + minute;
                hour = "" + hourOfDay;
                EditText time = (EditText) findViewById(R.id.time);
                time.setText(hour + " : " + min);
                try {
                    d1 = DateFormat.getTimeInstance(DateFormat.SHORT).format(new SimpleDateFormat("HH:mm").parse(hour + ":" + min));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "At : " + hour + " : " + min, Toast.LENGTH_SHORT).show();
            }
        };
        Calendar now = Calendar.getInstance();
        int minute = now.get(Calendar.MINUTE);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        boolean is24Hour = true;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, hour, minute, is24Hour);
        timePickerDialog.show();

    }

    String dur;

    public void dur1(View view) {
        dur = 15 + " Minutes";
        Toast.makeText(this, "Event is for : " + dur + " Minutes", Toast.LENGTH_SHORT).show();
        view.setBackgroundColor(getResources().getColor(R.color.D1));
        bt2.setBackgroundColor(Color.TRANSPARENT);
        bt3.setBackgroundColor(Color.TRANSPARENT);
    }

    public void dur2(View view) {
        dur = 30 + " Minutes";
        Toast.makeText(this, "Event is for : " + dur + " Minutes", Toast.LENGTH_SHORT).show();
        view.setBackgroundColor(getResources().getColor(R.color.D2));
        bt1.setBackgroundColor(Color.TRANSPARENT);
        bt3.setBackgroundColor(Color.TRANSPARENT);
    }

    public void dur3(View view) {
        dur = 1 + " Hour";
        Toast.makeText(this, "Event is for : " + dur + " Hour", Toast.LENGTH_SHORT).show();
        view.setBackgroundColor(getResources().getColor(R.color.D3));
        bt2.setBackgroundColor(Color.TRANSPARENT);
        bt1.setBackgroundColor(Color.TRANSPARENT);
    }

    public void cancle(View view) {
        processTimer.cancel(alarmIntent);
        startActivity(new Intent(AddEventActivity.this,StartActivity.class));
        finish();
    }

    public void save(View view) {
        EditText about = (EditText) findViewById(R.id.about);
        EditText info = (EditText) findViewById(R.id.info);
        EditText location = (EditText) findViewById(R.id.location);
//
//        Intent i = new Intent(this, Receiver.class);
//        i.putExtra("info", info.getText().toString());
//        i.putExtra("data", about.getText().toString());
//        i.putExtra("location", location.getText().toString());
//        i.putExtra("duration", dur);
//        i.putExtra("time", d2 + " " + d1);
//        i.putExtra("day", day);
//        i.putExtra("month", month);
//        i.putExtra("year", year);
//        i.putExtra("hour", hour);
//        i.putExtra("minute", min);
//        alarmIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        processTimer.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*5, alarmIntent);

//        startActivity(new Intent(AddEventActivity.this,StartActivity.class)
//                .putExtra("time",d2 + " " + d1)
//                .putExtra("info", info.getText().toString())
//                .putExtra("data", about.getText().toString())
//                .putExtra("location", location.getText().toString())
//                .putExtra("duration", dur)
//        );
        ContentValues cv=new ContentValues();
        cv.put(EventContracts.EventEntry.about, String.valueOf(about.getText().toString()));
        cv.put(EventContracts.EventEntry.info, String.valueOf(info));
        cv.put(EventContracts.EventEntry.time, String.valueOf(d1));
        cv.put(EventContracts.EventEntry.date, String.valueOf(d2));
        cv.put(EventContracts.EventEntry.duration, String.valueOf(dur));
        cv.put(EventContracts.EventEntry.location, String.valueOf(location));

//        sqLiteDatabase.execSQL("insert into "+ EventContracts.EventEntry.tableName+"values(");
        sqLiteDatabase.insert(EventContracts.EventEntry.tableName,null,cv);
        sqLiteDatabase.close();
        about.getText().clear();
        info.getText().clear();
        location.getText().clear();

        startActivity(new Intent(AddEventActivity.this,StartActivity.class));
        finish();
    }

}
