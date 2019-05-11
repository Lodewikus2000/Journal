package com.example.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EntryAdapter adapter;
    private EntryDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView journalList = findViewById(R.id.entryListView);
        journalList.setOnItemClickListener(new ListItemClickListener());
        journalList.setOnItemLongClickListener(new ListItemLongClickListener());


        db = EntryDatabaseHelper.getInstance(getApplicationContext());

        // get all records from the database??!
        Cursor cursor = db.selectAll();

        // Make new EntryAdapter
        adapter = new EntryAdapter(this, cursor);

        // Link listview to adapter
        journalList.setAdapter(adapter);

    }



    private class ListItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor clickedJournalEntry = (Cursor) parent.getItemAtPosition(position);
            int intID = clickedJournalEntry.getInt(clickedJournalEntry.getColumnIndex("_id"));
            String title = clickedJournalEntry.getString(clickedJournalEntry.getColumnIndex("title"));
            String content = clickedJournalEntry.getString(clickedJournalEntry.getColumnIndex("content"));
            String mood = clickedJournalEntry.getString(clickedJournalEntry.getColumnIndex("mood"));
            String timestamp = clickedJournalEntry.getString(clickedJournalEntry.getColumnIndex("timestamp"));

            JournalEntry entry = new JournalEntry(intID, title, content, mood, timestamp);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("journalEntry", entry);

            startActivity(intent);

        }
    }


    
    private class ListItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            Cursor clickedJournalEntry = (Cursor) parent.getItemAtPosition(position);

            // Get the index of the column named "_id" from the cursor.
            int idIndex = clickedJournalEntry.getColumnIndex("_id");

            // Get the value from the "_id" column (the entry we want to delete).
            long longId = clickedJournalEntry.getLong(idIndex);

            db.delete(longId);


            // Get a new cursor.
            Cursor newCursor = db.selectAll();
            // Swap the old cursor with the new one
            adapter.swapCursor(newCursor);

            return true;
        }
    }



    public void writeEntry(View view) {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }

}
