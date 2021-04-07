package com.example.shopperdwan

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.shopperdwan.CreateList
import java.text.SimpleDateFormat
import java.util.*

class CreateList : AppCompatActivity() {
    //var intent: Intent? = null

    //declare edittexts
    var nameEditText: EditText? = null
    var storeEditText: EditText? = null
    var dateEditText: EditText? = null

    //declare calender
    var calendar: Calendar? = null
    var dbHandler: DBHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        storeEditText = findViewById<View>(R.id.storeEditText) as EditText
        dateEditText = findViewById<View>(R.id.dateEditText) as EditText

        //intialize calander
        calendar = Calendar.getInstance()

        //Intilize datepicker
        val date = OnDateSetListener { datePicker, year, month, dayOfMonth ->
            /**
             * This method handles the onDateSet event
             * @param datePicker DatePickerview
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */
            /**
             * This method handles the onDateSet event
             * @param datePicker DatePickerview
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */

            //set the year, month, and day into calender
            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, month)
            calendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            //call method
            updateDueDate()
        }
        //register an onclick
        dateEditText!!.setOnClickListener { //display DatePickerDialog
            DatePickerDialog(this@CreateList,
                    date,
                    calendar!!.get(Calendar.YEAR),
                    calendar!!.get(Calendar.MONTH),
                    calendar!!.get(Calendar.DAY_OF_MONTH)).show()
        }
        dbHandler = DBHandler(this, null)
    }

    private fun updateDueDate() {
        //create format for date in calender
        val SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateEditText!!.setText(SimpleDateFormat.format(calendar!!.time))
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_create_list, menu)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun createList(menuItem: MenuItem?) {
        //get data input in EditTexts and store into strings
        val name = nameEditText!!.text.toString()
        val store = storeEditText!!.text.toString()
        val date = dateEditText!!.text.toString()
        if (name.trim { it <= ' ' } == "" || store.trim { it <= ' ' } == "" || date.trim { it <= ' ' } == "") {
            Toast.makeText(this, "Please enter name, store, and date!",
                    Toast.LENGTH_LONG).show()
        } else {
            dbHandler!!.addShoppingList(name, store, date)
            Toast.makeText(this, "Shopping List Created!",
                    Toast.LENGTH_LONG).show()
        }
    }
}