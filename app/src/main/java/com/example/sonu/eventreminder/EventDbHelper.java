package com.example.sonu.eventreminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sonu.eventreminder.EventContracts.*;

public class EventDbHelper extends SQLiteOpenHelper {

    public static final String databaseName="events.db";
    public static final int databaseVersion=1;

    public EventDbHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sqlCreateEventsTable="create table "+EventEntry.tableName+
                "("+
                EventEntry._ID+" integer primary key autoincrement,"+
                EventEntry.about+" text not null,"+
                EventEntry.info+" text not null,"+
                EventEntry.time+" text not null,"+
                EventEntry.date+" text not null,"+
                EventEntry.duration+" text not null,"+
                EventEntry.location+" text not null"+
                ");";
        db.execSQL(sqlCreateEventsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+EventEntry.tableName);
        onCreate(db);
    }
}
