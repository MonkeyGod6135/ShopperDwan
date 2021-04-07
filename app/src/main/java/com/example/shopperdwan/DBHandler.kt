package com.example.shopperdwan

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DBHandler
/**
 * Creates a version of the shopper database
 * @param context reference to the activity that starts dbhandler
 * @param factory null
 */
(context: Context?, factory: CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    /**
     * Creates shopper database tables
     * @param sqLiteDatabase
     */
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //define create statement for shopping list and store it in a string
        val query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT); "

        //execute the statement
        sqLiteDatabase.execSQL(query)

        //define create statement for shopping list and store it in a string
        val query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER);"

        //execute the statement
        sqLiteDatabase.execSQL(query2)
    }

    /**
     * Creates a new version
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //define drop statement and store it in a string
        val query = "DROP TABLE IF EXISTS $TABLE_SHOPPING_LIST"

        //execute statement
        sqLiteDatabase.execSQL(query)
        onCreate(sqLiteDatabase)

        //define drop statement and store it in a string
        val query2 = "DROP TABLE IF EXISTS $TABLE_SHOPPING_LIST_ITEM"

        //execute statement
        sqLiteDatabase.execSQL(query2)
        onCreate(sqLiteDatabase)
    }

    /**
     * This method gets called whhen the add button in the action bar of the createlist activity
     * gets clicked and makes a new row into the shopping list table
     * @param name Shopping list name
     * @param store Shopping list store
     * @param date Shopping list date
     */
    fun addShoppingList(name: String?, store: String?, date: String?) {
        //refernce to database
        val db = writableDatabase
        val values = ContentValues()

        //put data into content values
        values.put(COLUMN_LIST_NAME, name)
        values.put(COLUMN_LIST_STORE, store)
        values.put(COLUMN_LIST_DATE, date)

        //insert data in Content values into shopping list table
        db.insert(TABLE_SHOPPING_LIST, null, values)
        db.close()
    }

    //reference to database
    val shoppingLists: Cursor
        get() {
            //reference to database
            val db = writableDatabase

            //define statement and store it in string
            val query = "SELECT * FROM $TABLE_SHOPPING_LIST"

            //execute statement
            return db.rawQuery(query, null)
        }

    /**
     * This method gets called when the viewList Activity is started
     * @param id shopping List id
     * @return shopping List name
     */
    fun getShoppingListName(id: Int): String {

        //get reference to the shopper database
        val db = writableDatabase

        //declare and start the string that will be returned
        var name = ""

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id

        //execute select statemnet and store it in a cursor
        val cursor = db.rawQuery(query, null)

        //move to the first row
        cursor.moveToFirst()

        //check that name component isn't equal to null
        if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            name = cursor.getString(cursor.getColumnIndex("name"))
        }
        db.close()
        return name
    }

    fun addItemToList(name: String?, price: Double?, quantity: Int?, listId: Int?) {
        //refernce to database
        val db = writableDatabase
        val values = ContentValues()

        //put data into content values
        values.put(COLUMN_ITEM_NAME, name)
        values.put(COLUMN_ITEM_PRICE, price)
        values.put(COLUMN_ITEM_QUANTITY, quantity)
        values.put(COLUMN_ITEM_HAS, "false")
        values.put(COLUMN_ITEM_LIST_ID, listId)

        //insert data in Content values into shopping list item table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values)
        db.close()
    }

    /**
     * This method is called when the viewlist activity is launched
     * @param listId shopping list id
     * @return cursor that contains the shopping list id
     */
    fun getShoppingListItems(listId: Int): Cursor {

        //reference to database
        val db = writableDatabase

        //define statement and store it in string
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        //execute statement
        return db.rawQuery(query, null)
    }

    /**
     * This method is called when an item on the viewlist activity is clicked
     * @param itemId database id of the clicked item
     * @return
     */
    fun isItemUnPurchase(itemId: Int): Int {

        //reference to database
        val db = writableDatabase

        //define select statement and store it in a string
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_ID + " = " + itemId

        //execute select statement and store result in a cursor
        val cursor = db.rawQuery(query, null)

        //return count
        return cursor.count
    }

    /**
     * This method is called when an item on the viewlist activity is clicked
     * It sets items item_has to true
     * @param itemId
     */
    fun updateItem(itemId: Int) {

        //reference to database
        val db = writableDatabase

        //define update statement
        val query = "UPDATE " + TABLE_SHOPPING_LIST_ITEM + " SET " +
                COLUMN_ITEM_HAS + " = \"true\" " + " WHERE " +
                COLUMN_ITEM_ID + " = " + itemId

        //execute statement
        db.execSQL(query)

        //close db connection
        db.close()
    }

    fun getSHoppingListItem(itemId: Int): Cursor {

        //reference to database
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when the delete button in the action bar of view item activity is clicked
     * @param itemId
     */
    fun deleteShoppingListItem(itemId: Int) {
        //reference to database
        val db = writableDatabase

        //define in string
        val query = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                "WHERE " + COLUMN_ITEM_ID + " = " + itemId
        db.execSQL(query)
        db.close()
    }

    /**
     * This method is called when the delete button in the action bar is clicked
     * @param listId
     */
    fun deleteShoppingList(listId: Int) {

        //reference to database
        val db = writableDatabase

        //define in string
        val query1 = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                "WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        //execute
        db.execSQL(query1)

        //Define in string
        val query2 = "DELETE FROM " + TABLE_SHOPPING_LIST +
                "WHERE " + COLUMN_LIST_ID + " = " + listId

        //execute
        db.execSQL(query2)
        db.close()
    }

    fun getShoppingListTotalCost(listId: Int): String {
        //reference to database
        val db = writableDatabase

        //declare and start the String returned  by the method
        var cost = ""

        //declare a Double that will be used to compute the totoal cost
        var totalCost = 0.0

        //define select statement and store it
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                "WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        //execute and store in cursor
        val c = db.rawQuery(query, null)

        //loop through the rows in cursor
        while (c.moveToNext()) {
            //add the cost of the current row into total cost
            totalCost += c.getDouble(c.getColumnIndex("price")) *
                    c.getInt(c.getColumnIndex("quantity"))
        }

        //convert into string
        cost = totalCost.toString()
        db.close()


        //return string
        return cost
    }

    /**
     * This method is called when a shopping list item is clicked
     * @param listId
     * @return
     */
    fun getUnpurchasedItems(listId: Int): Int {
        //reference to database
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_LIST_ID + " = " + listId

        //execute select statement
        val cursor = db.rawQuery(query, null)
        return cursor.count
    }

    companion object {
        //intialize constants for db name and version
        const val DATABASE_NAME = "Shopper.db"
        const val DATABASE_VERSION = 2

        //Intilizes constants for the shopperinglist table
        const val TABLE_SHOPPING_LIST = "shoppinglist"
        const val COLUMN_LIST_ID = "_id"
        const val COLUMN_LIST_NAME = "name"
        const val COLUMN_LIST_STORE = "store"
        const val COLUMN_LIST_DATE = "date"

        //Intilizes constants for the shopperinglistitem table
        const val TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem"
        const val COLUMN_ITEM_ID = "_id"
        const val COLUMN_ITEM_NAME = "name"
        const val COLUMN_ITEM_PRICE = "price"
        const val COLUMN_ITEM_QUANTITY = "quantity"
        const val COLUMN_ITEM_HAS = "item_has"
        const val COLUMN_ITEM_LIST_ID = "list_id"
    }
}