package com.example.shopperdwan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHandler extends SQLiteOpenHelper {

    //intialize constants for db name and version
    public static final String DATABASE_NAME = "Shopper.db";
    public static final int DATABASE_VERSION = 1;

    //Intilizes constants for the shopperinglist table
    public static final String TABLE_SHOPPING_LIST = "shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";


    /**
     * Creates a version of the shopper database
     * @param context reference to the activity that starts dbhandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates shopper database tables
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    /**
     * Creates a new version
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
