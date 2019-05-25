package com.example.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    JournalEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Journal entry");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        entry = (JournalEntry) intent.getSerializableExtra("journalEntry");

        TextView titleView = findViewById(R.id.textViewTitle);
        titleView.setText(entry.getTitle());

        TextView timeView = findViewById(R.id.textViewTimestamp);
        timeView.setText(entry.getTimestamp());

        TextView contentView = findViewById(R.id.textViewContent);
        contentView.setText(entry.getContent());

        TextView moodView = findViewById(R.id.textViewMood);
        moodView.setText(entry.getMood());

    }
}
