package com.example.kolokvijum_1.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_COMMENTS = "comments";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_PASSWORD= "password";
    private static final String KEY_COMMENT="comment";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_NAME + " TEXT PRIMARY KEY," +
                KEY_PASSWORD + " TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Comments Table
        String CREATE_COMMENTS_TABLE = "CREATE TABLE " + TABLE_COMMENTS +
                "(" +
                KEY_USER_NAME + " TEXT," +
                KEY_COMMENT + " TEXT," +
                "FOREIGN KEY(" + KEY_USER_NAME + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_NAME + ")" +
                ")";
        db.execSQL(CREATE_COMMENTS_TABLE);

        // Insert initial data into both tables
        insertInitialData(db);
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Create tables again
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        // User data to be inserted
        Object[][] users = {
                {"user1", "password1"},
                {"user2", "password2"},
                {"user3", "password3"}
        };

        for (Object[] user : users) {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_NAME, (String) user[0]);
            values.put(KEY_PASSWORD, (String) user[1]);
            db.insert(TABLE_USERS, null, values);
        }

        // Comments data to be inserted
        Object[][] comments = {
                {"user1", "This is a comment from user1."},
                {"user2", "User2 says hello!"},
                {"user3", "Comment made by user3."}
        };

        for (Object[] comment : comments) {
            ContentValues commentValues = new ContentValues();
            commentValues.put(KEY_USER_NAME, (String) comment[0]);
            commentValues.put(KEY_COMMENT, (String) comment[1]);
            db.insert(TABLE_COMMENTS, null, commentValues);
        }
    }

    public boolean checkUserExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM users WHERE username = ? AND password = ?", new String[]{username, password});

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    @SuppressLint("Range")
    public List<String> getCommentsByUser(String username) {
        List<String> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT comment FROM " + TABLE_COMMENTS + " WHERE username = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                comments.add(cursor.getString(cursor.getColumnIndex(KEY_COMMENT)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return comments;
    }

    public List<String> getAllComments() {
        List<String> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, comment FROM " + TABLE_COMMENTS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(KEY_USER_NAME));
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex(KEY_COMMENT));
                comments.add(username + ": " + comment); // Combine username and comment for easy display
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return comments;
    }

    public void insertComment(String username, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, username);
        values.put(KEY_COMMENT, comment);
        db.insert(TABLE_COMMENTS, null, values);
        db.close();
    }



}
