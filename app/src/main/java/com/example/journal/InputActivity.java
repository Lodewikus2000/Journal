package com.example.journal;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("New entry");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        Spinner spinner = findViewById(R.id.spinnerMood);

        // Create an ArrayAdapter for the drop down mood chooser.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.moods_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void addEntry(View view) {

        TextView titleView = findViewById(R.id.editTextTitle);
        String title = titleView.getText().toString();

        // Check if the title is not just whitespace.
        if (!Pattern.matches("[[\\s]*[\\S]+[\\s]*]+", title)){
            Toast.makeText(InputActivity.this, "Please provide a title", Toast.LENGTH_SHORT).show();
            return;
        }
        // Title must not be too long.
        if (title.length() > 64){
            Toast.makeText(InputActivity.this, "Title should be no longer than 64 characters", Toast.LENGTH_SHORT).show();
            return;
        }


        TextView contentView = findViewById(R.id.editTextContent);
        String content = contentView.getText().toString();

        // Check if the entry is not just whitespace.
        if (!Pattern.matches("[[\\s]*[\\S]+[\\s]*]+", content)){
            Toast.makeText(InputActivity.this, "Please provide an entry", Toast.LENGTH_SHORT).show();
            return;
        }


        Spinner moodView = findViewById(R.id.spinnerMood);
        String mood = moodView.getSelectedItem().toString();


        JournalEntry entry = new JournalEntry();
        entry.setContent(content);
        entry.setTitle(title);
        entry.setMood(mood);


        // Get the database and insert the entry.
        EntryDatabaseHelper.getInstance(this).insert(entry);


        // Instead of just calling finish(), this way we refresh the MainActivity.
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
