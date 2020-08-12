package com.example.sonu.eventreminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ListHolder>{

    private Cursor cursor;
    private Context context;
    private SQLiteDatabase mDatabase;

    public EventAdapter(Context context, Cursor cursor) {
        this.context=context;
        this.cursor=cursor;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View listItem=layoutInflater.inflate(R.layout.events_items,viewGroup,false);
        return new ListHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, final int position) {
        if(!cursor.moveToPosition(position))
            return;
        EventDbHelper eventDbHelper=new EventDbHelper(context);
        mDatabase=eventDbHelper.getReadableDatabase();
        final String about=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry.about));
        String info=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry.info));
        String time=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry.time));
        String date=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry.date));
        String duration=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry.duration));
        String location=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry.location));
        final String id=cursor.getString(cursor.getColumnIndex(EventContracts.EventEntry._ID));
        listHolder.item.setText(about);
        listHolder.delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.delete(EventContracts.EventEntry.tableName,
                        EventContracts.EventEntry._ID + " = " + id , null);
            }
        });
//        listHolder.item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(view.getContext(), MessageActivity.class);
//                view.getContext().startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor!=null)
            cursor.close();
        cursor=newCursor;
        if(newCursor!=null)
            notifyDataSetChanged();

    }

    public class ListHolder extends RecyclerView.ViewHolder{
        ImageButton delete1;
        TextView item;
        Switch aSwitch;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            delete1 =(ImageButton) itemView.findViewById(R.id.deleteIcon);
            item =(TextView)itemView.findViewById(R.id.item);
            aSwitch=itemView.findViewById(R.id.eventSwitch);
        }
    }
}