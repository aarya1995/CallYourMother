package com.example.alcar.callyourmother;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import java.util.ArrayList;


public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_NAME = "Contacts";
    public static final String COLUMN_ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String PRIORITY = "priority";

    private SQLiteDatabase database;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY" +
                " AUTOINCREMENT," + FIRST_NAME + " VARCHAR, " + LAST_NAME + " VARCHAR, " +
                PHONE_NUMBER + " VARACHAR, " + PRIORITY + " INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, contact.getFirstName());
        contentValues.put(LAST_NAME, contact.getLastName());
        contentValues.put(PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(PRIORITY, contact.getPriority());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void updateRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_NAME, contact.getFirstName());
        contentValues.put(LAST_NAME, contact.getLastName());
        contentValues.put(PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(PRIORITY, contact.getPriority());
        // primary key formed by first name and last name for now
        database.update(TABLE_NAME, contentValues, FIRST_NAME + " =? and "
                + LAST_NAME + " =? ", new String[] {contact.getFirstName(), contact.getLastName()});
        database.close();
    }

    public void deleteRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, FIRST_NAME + " =? and "
                + LAST_NAME + " =? ", new String[]{contact.getFirstName(), contact.getLastName()});
        database.close();
    }

    public ArrayList<ContactModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
        ContactModel contactModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                contactModel = new ContactModel();
                contactModel.setID(cursor.getString(0));
                contactModel.setFirstName(cursor.getString(1));
                contactModel.setLastName(cursor.getString(2));
                contactModel.setPhoneNumber(cursor.getString(3));
                contactModel.setPriority(cursor.getString(4));
                contacts.add(contactModel);
            }
        }
        cursor.close();
        database.close();
        return contacts;
    }
}
