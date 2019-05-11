package com.example.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class EntryDatabaseHelper extends SQLiteOpenHelper {

    private static EntryDatabaseHelper instance;

    // constructor
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
        String query = "CREATE TABLE entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT, mood TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(query);
        String example1 = "INSERT INTO entries (title, content, mood) VALUES ('leuke dag gehad', 'ik heb de hele nacht liggen kotsen maar het was leuk want we hadden pizza', '\uD83D\uDE02');";
        String example2 = "INSERT INTO entries (title, content, mood) VALUES ('o shit zwanger', 'de vraag is nu even of er een vader is en zo ja, wie dan', '\uD83D\uDE2D');";
        String example3 = "INSERT INTO entries (title, content, mood) VALUES ('de zon scheen', 'laat de zomer maar beginnen', '\uD83D\uDE0D');";
        String example4 = "INSERT INTO entries (title, content, mood) VALUES ('mijn eerste entry', 'waar zit de edit-knop??!?', '❤️');";
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
