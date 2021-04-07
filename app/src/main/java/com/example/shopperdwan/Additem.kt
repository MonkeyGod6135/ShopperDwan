package com.example.shopperdwan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Additem : AppCompatActivity(), OnItemSelectedListener {
    //Declare a Bundle and long for data sent from view list activity
    var bundle: Bundle? = null
    var id: Long = 0

    //declare DBHandler
    var dbHandler: DBHandler? = null

    //declare Intent
    //var intent: Intent? = null

    //declare editextxs
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null

    //declare Spinner
    var quantitySpinner: Spinner? = null

    //declare strings to store edit texts
    var quantity: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //start bundle
        bundle = getIntent().extras

        //use Bundle to get id
        id = bundle!!.getLong("_id")

        //start dbhandler
        dbHandler = DBHandler(this, null)

        //start edittexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText

        //start spinner
        quantitySpinner = findViewById<View>(R.id.quantitySpinner) as Spinner

        //start Arrayadaptor
        val adapter = ArrayAdapter.createFromResource(this, R.array.quantities, android.R.layout.simple_spinner_item)

        //styleize array
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        //set the arrayadapter
        quantitySpinner!!.adapter = adapter

        //register an onitemselectedlistener to spanner
        quantitySpinner!!.onItemSelectedListener = this
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_add_item, menu)
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
            R.id.action_view_list -> {
                intent = Intent(this, ViewList::class.java)
                //put the database id in the intent
                intent!!.putExtra("_id", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun additem(menuItem: MenuItem?) {
        val name = nameEditText!!.text.toString()
        val price = priceEditText!!.text.toString()
        if (name.trim { it <= ' ' } == "" || price.trim { it <= ' ' } == ""
                || quantity!!.trim { it <= ' ' } == "") {
            Toast.makeText(this, "Please enter a name, price, and quantity", Toast.LENGTH_LONG).show()
        } else {
            //add item to database
            dbHandler!!.addItemToList(name, price.toDouble(), quantity!!.toInt(), id.toInt())
            //display text
            Toast.makeText(this, "Item Added", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * This method gets called when an item in the spinner is selected.
     * @param parent Spinner AdapterView
     * @param view addItem View
     * @param position position of item
     * @param id database id of item
     */
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        quantity = parent.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}