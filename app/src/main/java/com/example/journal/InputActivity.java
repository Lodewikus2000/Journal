package com.example.journal;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);



        Spinner spinner = (Spinner) findViewById(R.id.spinnerMood);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.moods_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }





    public void addEntry(View view) {
        JournalEntry entry = new JournalEntry();

        TextView contentView = findViewById(R.id.editTextContent);
        String content = contentView.getText().toString();
        entry.setContent(content);


        TextView titleView = findViewById(R.id.editTextTitle);
        String title = titleView.getText().toString();
        entry.setTitle(title);

        Spinner moodView = findViewById(R.id.spinnerMood);
        String mood = moodView.getSelectedItem().toString();
        entry.setMood(mood);

        // Get the database and insert the entry.
        EntryDatabaseHelper.getInstance(this).insert(entry);

        Log.d("the content is:", content);


        // Instead of just calling finish(), this is better since it refreshes the MainActivity.
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);




    }
}
