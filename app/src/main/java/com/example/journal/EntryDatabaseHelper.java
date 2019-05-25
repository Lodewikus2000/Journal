package com.example.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class EntryDatabaseHelper extends SQLiteOpenHelper {

    private static EntryDatabaseHelper instance;


    private EntryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    public static EntryDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            // Make a new, empty one if there is no current one.
            instance = new EntryDatabaseHelper(context, "entryDatabase", null, 1);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, timestamp DATETIME DEFAULT (datetime('now', 'localtime')));";
        db.execSQL(query);

        // Make some example entries.
        String example1 = "INSERT INTO entries (title, content, mood) VALUES ('Lang verhaal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla sit amet porta orci. Integer mollis, quam at dignissim fermentum, lectus felis aliquet neque, eget ullamcorper ante ex at purus. Pellentesque orci eros, congue sit amet tortor a, pharetra semper nunc. Vivamus at diam libero. Fusce elit ipsum, aliquam vel dui et, bibendum faucibus elit. Aliquam cursus molestie arcu, egestas laoreet turpis eleifend vel. Vivamus tincidunt lacus sed dignissim luctus. Etiam a pulvinar dolor. Integer eget tortor urna. Vivamus et varius erat, eu eleifend nisl.\n" +
                "\n" +
                "Nam tempus lorem non arcu pretium finibus. In eleifend viverra elit quis ultrices. Maecenas quis libero elementum, pretium ipsum nec, cursus justo. Duis vel viverra urna. In placerat facilisis metus, sit amet maximus risus faucibus ullamcorper. Cras venenatis pellentesque metus porttitor eleifend. Duis varius pretium sapien, sit amet rutrum lorem. Donec viverra lectus eget erat tempus, ut pellentesque risus rhoncus. Duis vel dictum enim, ut scelerisque purus. Pellentesque lobortis bibendum quam hendrerit scelerisque. Duis varius enim risus, et ornare nulla elementum finibus. Nunc ultricies nisl nec tellus fringilla ultrices. Phasellus fermentum rhoncus orci, a lacinia dolor.\n" +
                "\n" +
                "Cras pretium lobortis nibh, sagittis pellentesque dui. Duis massa purus, imperdiet vitae tellus nec, posuere elementum nisl. Vivamus vulputate risus tincidunt velit tempor consectetur. Aenean rutrum ultricies nisi, id sagittis erat vestibulum semper. Vestibulum vehicula tristique dui, nec molestie nisl. Aliquam facilisis vel lacus vel semper. In eu risus maximus, posuere sem consequat, ornare quam. Curabitur nec sagittis lacus. Aliquam facilisis feugiat lorem. Vivamus et massa cursus, finibus arcu sed, tempor diam. Aliquam tincidunt posuere orci, at bibendum odio cursus vel.\n" +
                "\n" +
                "Mauris augue est, feugiat a metus eu, tincidunt auctor lacus. Mauris nulla urna, ullamcorper lacinia metus at, convallis condimentum eros. Donec eleifend mattis scelerisque. In suscipit vel quam eget tincidunt. Donec venenatis vitae justo a posuere. Phasellus ut mi posuere magna egestas pretium et non metus. Morbi sed turpis in velit efficitur suscipit. Praesent feugiat nibh ullamcorper leo vulputate, vel elementum ligula elementum. Donec rutrum, neque sollicitudin condimentum aliquet, nisl orci convallis mauris, eget convallis odio tellus non risus. In finibus erat velit, eu eleifend magna accumsan euismod. Nunc mollis nibh vehicula felis iaculis, vel hendrerit odio pharetra. Integer ac felis eget dui vulputate ultricies ac vitae odio. Suspendisse laoreet nec dui eu cursus.', '\uD83D\uDE02');";
        String example2 = "INSERT INTO entries (title, content, mood) VALUES ('Wat er nu gebeurd is...', 'De vraag is nu even of dit ooit nog goed komt.', '\uD83D\uDE01');";
        String example3 = "INSERT INTO entries (title, content, mood) VALUES ('De zon scheen', 'Laat de zomer maar beginnen!!!!!!', '\uD83E\uDD91');";
        String example4 = "INSERT INTO entries (title, content, mood) VALUES ('Mijn eerste entry en ik maak hier meteen de langste titel van die ik ooit geschreven heb!', 'Maar waar zit de edit-knop??!?', '\uD83D\uDC45');";
        db.execSQL(example1);
        db.execSQL(example2);
        db.execSQL(example3);
        db.execSQL(example4);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS entries";
        db.execSQL(drop);
        onCreate(db);
    }


    public Cursor selectAll() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM entries ORDER BY timestamp DESC", null);
        return cursor;
    }


    public void insert(JournalEntry journalEntry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", journalEntry.getTitle());
        values.put("content", journalEntry.getContent());
        values.put("mood", journalEntry.getMood());
        db.insert("entries", null, values);
    }

    public void delete(long id){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("entries","_id=?", new String[]{Long.toString(id)});

    }

}
