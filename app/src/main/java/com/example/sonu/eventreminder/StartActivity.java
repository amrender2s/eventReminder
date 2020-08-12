package com.example.sonu.eventreminder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class StartActivity extends AppCompatActivity {
    ImageButton addEvent;
    ToggleButton toggleEvent;
    ImageView deleteEvent;
    TextView eventTitle;

    RecyclerView recyclerView;
    EventAdapter listAdapter;
    private SQLiteDatabase sqLiteDatabase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EventDbHelper eventDbHelper=new EventDbHelper(this);
        sqLiteDatabase1 =eventDbHelper.getWritableDatabase();
//        String t2=getIntent().getStringExtra("time");
//        String location=getIntent().getStringExtra("location");
//        String duration=getIntent().getStringExtra("duration");
//        final String about=getIntent().getStringExtra("data");
//        String info=getIntent().getStringExtra("info");
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listAdapter = new EventAdapter(this,getAllItem());

        recyclerView.setAdapter(listAdapter);
        listAdapter.swapCursor(getAllItem());

//        eventTitle=findViewById(R.id.item);
        addEvent=findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventButton();
            }
        });
    }

    private void addEventButton() {
        startActivity(new Intent(StartActivity.this,AddEventActivity.class));
        finish();
    }
    private Cursor getAllItem(){
        return sqLiteDatabase1.query(EventContracts.EventEntry.tableName,
                null,
                null,
                null,
                null,
                null,
                null
//                EventContracts.EventEntry.about
        );
    }
}

