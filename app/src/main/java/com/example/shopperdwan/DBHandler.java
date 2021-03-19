package com.example.shopperdwan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHandler extends SQLiteOpenHelper {

    //intialize constants for db name and version
    public static final String DATABASE_NAME = "Shopper.db";
    public static final int DATABASE_VERSION = 2;

    //Intilizes constants for the shopperinglist table
    public static final String TABLE_SHOPPING_LIST = "shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";

    //Intilizes constants for the shopperinglistitem table
    public static final String TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem";
    public static final String COLUMN_ITEM_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_QUANTITY = "quantity";
    public static final String COLUMN_ITEM_HAS = "item_has";
    public static final String COLUMN_ITEM_LIST_ID = "list_id";


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
        String query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME+ " TEXT, " +
                COLUMN_LIST_STORE+ " TEXT, " +
                COLUMN_LIST_DATE+ " TEXT); ";

        //execute the statement
        sqLiteDatabase.execSQL(query);

        //define create statement for shopping list and store it in a string
        String query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER);";

        //execute the statement
        sqLiteDatabase.execSQL(query2);


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

        //define drop statement and store it in a string
        String query2 = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM;

        //execute statement
        sqLiteDatabase.execSQL(query2);

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

    public Cursor getShoppingLists() {
        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define statement and store it in string
        String query ="SELECT * FROM " + TABLE_SHOPPING_LIST;

        //execute statement
        return db.rawQuery(query, null);


    }

    /**
     * This method gets called when the viewList Activity is started
     * @param id shopping List id
     * @return shopping List name
     */
    public String getShoppingListName(int id){

        //get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        //declare and start the string that will be returned
        String name = "";

        //define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        //execute select statemnet and store it in a cursor
        Cursor cursor = db.rawQuery(query,null);

        //move to the first row
        cursor.moveToFirst();

        //check that name component isn't equal to null
        if ((cursor.getString(cursor.getColumnIndex("name")) !=null)){
            name = cursor.getString(cursor.getColumnIndex("name"));
        }
        db.close();

        return name;

    }
    public void addItemToList(String name, Double price, Integer quantity, Integer listId){
        //refernce to database
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        //put data into content values
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_HAS, "false");
        values.put(COLUMN_ITEM_LIST_ID,listId);

        //insert data in Content values into shopping list item table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values);

        db.close();



    }

    /**
     * This method is called when the viewlist activity is launched
     * @param listId shopping list id
     * @return cursor that contains the shopping list id
     */
    public Cursor getShoppingListItems(Integer listId){

        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define statement and store it in string
        String query ="SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        //execute statement
        return db.rawQuery(query, null);

    }

    /**
     * This method is called when an item on the viewlist activity is clicked
     * @param itemId database id of the clicked item
     * @return
     */
    public int isItemUnPurchase(Integer itemId){

        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_ID + " = " + itemId;

        //execute select statement and store result in a cursor
        Cursor cursor = db.rawQuery(query,null);

        //return count
        return(cursor.getCount());

    }

    /**
     * This method is called when an item on the viewlist activity is clicked
     * It sets items item_has to true
     * @param itemId
     */
    public void updateItem(Integer itemId){

        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define update statement
        String query = "UPDATE " + TABLE_SHOPPING_LIST_ITEM + " SET " +
                COLUMN_ITEM_HAS + " = \"true\" " + " WHERE " +
                COLUMN_ITEM_ID + " = " + itemId;

        //execute statement
        db.execSQL(query);

        //close db connection
        db.close();
    }
    public Cursor getSHoppingListItem(Integer itemId){

        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define select statement
       String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId;
       return db.rawQuery(query, null);
    }

    /**
     *  This method gets called when the delete button in the action bar of view item activity is clicked
     * @param itemId
     */
    public void deleteShoppingListItem(Integer itemId){
        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define in string
        String query = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                "WHERE " + COLUMN_ITEM_ID + " = " + itemId;

        db.execSQL(query);

        db.close();

    }

    /**
     * This method is called when the delete button in the action bar is clicked
     * @param listId
     */
    public void deleteShoppingList(Integer listId){

        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define in string
        String query1 = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                "WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        //execute
        db.execSQL(query1);

        //Define in string
        String query2 = "DELETE FROM " + TABLE_SHOPPING_LIST +
                "WHERE " + COLUMN_LIST_ID + " = " + listId;

        //execute
        db.execSQL(query2);

        db.close();

    }

    public String getShoppingListTotalCost(int listId){
        //reference to database
        SQLiteDatabase db = getWritableDatabase();

        //declare and start the String returned  by the method
        String cost = "";

        //declare a Double that will be used to compute the totoal cost
        Double totalCost= 0.0;

        //define select statement and store it
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                "WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId;

        //execute and store in cursor
        Cursor c = db.rawQuery(query,null);

        //loop through the rows in cursor
        while(c.moveToNext()){
            //add the cost of the current row into total cost
            totalCost +=(c.getDouble(c.getColumnIndex("price")) *
                    (c.getInt(c.getColumnIndex("quantity"))));
        }

        //convert into string
        cost = String.valueOf(totalCost);

        db.close();


        //return string
        return cost;
    }

}