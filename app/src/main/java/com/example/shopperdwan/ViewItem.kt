package com.example.shopperdwan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ViewItem : AppCompatActivity() {
    //declare a dbhandler
    var dbHandler: DBHandler? = null

    //declare intent
    //var intent: Intent? = null

    //declare edit texts
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null
    var quantityEditText: EditText? = null

    //Declare a Bundle and long for data sent from main activity
    var bundle: Bundle? = null
    var id: Long = 0

    //declare strings to store clicked shopping list item data
    var name: String? = null
    var price: String? = null
    var quantity: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //start bundle
        bundle = getIntent().extras

        //use Bundle to get id
        id = bundle!!.getLong("_id")

        //start dbhandler
        dbHandler = DBHandler(this, null)

        //start Edittexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText
        quantityEditText = findViewById<View>(R.id.quantityEditText) as EditText

        //disablded edit texts
        nameEditText!!.isEnabled = false
        priceEditText!!.isEnabled = false
        quantityEditText!!.isEnabled = false

        //call the dbhandler getShoppinglistItem
        val cursor = dbHandler!!.getShoppingListItems(id.toInt())

        //move to first row in the cursor
        cursor.moveToFirst()

        //get data and store into strings
        name = cursor.getString(cursor.getColumnIndex("name"))
        price = cursor.getString(cursor.getColumnIndex("price"))
        quantity = cursor.getString(cursor.getColumnIndex("quantity"))

        //set values in edit text
        nameEditText!!.setText(name)
        priceEditText!!.setText(price)
        quantityEditText!!.setText(quantity)
    }

    fun deleteItem(menuItem: MenuItem?) {

        //delete shopping list item from data base
        dbHandler!!.deleteShoppingList(id.toInt())

        //toast
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_LONG).show()
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_item, menu)
        return true
    }

    /**
     * This method is called when a menu item in a overflow menu is selected
     * and it controls what happens when it is selected
     * @param item selected item in overflow menu
     * @return true if menu item is selected, else false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_create_list -> {
                intent = Intent(this, CreateList::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_item -> {
                intent = Intent(this, Additem::class.java)
                //put the database id in the intent
                intent!!.putExtra("_id", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}