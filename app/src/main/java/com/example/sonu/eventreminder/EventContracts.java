package com.example.sonu.eventreminder;

import android.provider.BaseColumns;

public class EventContracts {
    public EventContracts() { }

    public static final class EventEntry implements BaseColumns {
        public static final String tableName="events";
        public static final String timeStamp="timestamp";
        public static final String about="about";
        public static final String info="info";
        public static final String time="time";
        public static final String date="date";
        public static final String duration="duration";
        public static final String location="location";
    }
}
