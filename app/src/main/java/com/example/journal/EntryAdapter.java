package com.example.journal;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class EntryAdapter extends ResourceCursorAdapter {
    private int layout;
    private Cursor cursor;

    // constructor
    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor);
        this.layout = R.layout.entry_row;
        this.cursor = cursor;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get the entry that we will use to fill the views with.
        int titleInt = cursor.getColumnIndex("title");
        String title = cursor.getString(titleInt);

        int moodInt = cursor.getColumnIndex("mood");
        String mood = cursor.getString(moodInt);

        int timestampInt = cursor.getColumnIndex("timestamp");
        String timestamp = cursor.getString(timestampInt);
        Log.d("timestamp", timestamp);


        TextView titleView = view.findViewById(R.id.textViewTitle);
        titleView.setText(title);

        TextView timeView = view.findViewById(R.id.textViewTime);
        timeView.setText(timestamp);

        TextView moodView = view.findViewById(R.id.textViewMood);
        moodView.setText(mood);
    }


}
