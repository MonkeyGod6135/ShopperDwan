package com.example.shopperdwan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        //define create statement for shopping list and store it in a string
        String query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "( " +
                COLUMN_LIST_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME+ "text, " +
                COLUMN_LIST_STORE+ "text, " +
                COLUMN_LIST_DATE+ "text); ";

        //execute the statement
        sqLiteDatabase.execSQL(query);


    }

    /**
     * Creates a new version
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //define drop statement and store it in a string
        String query = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST;

        //execute statement
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);

    }

    /**
     * This method gets called whhen the add button in the action bar of the createlist activity
     * gets clicked and makes a new row into the shopping list table
     * @param name Shopping list name
     * @param store Shopping list store
     * @param date Shopping list date
     */
    public void addShoppingList(String name, String store, String date){
        //refernce to database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //put data into content values
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_STORE, store);
        values.put(COLUMN_LIST_DATE, date);

        //insert data in Content values into shopping list table
        db.insert(TABLE_SHOPPING_LIST, null, values);

        db.close();



    }

    public Cursor getShoppingList(){
        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define statement and store it in string
        String query ="SELECT + FROM " + TABLE_SHOPPING_LIST;

        //execute statement
        return db.rawQuery(query, null);


    }

}
