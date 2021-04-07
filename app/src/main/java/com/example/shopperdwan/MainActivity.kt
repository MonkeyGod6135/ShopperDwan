package com.example.shopperdwan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.CursorAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.shopperdwan.MainActivity

class MainActivity : AppCompatActivity() {
    //start intent
    //var intent: Intent? = null

    //start dbhandler
    var DBhandler: DBHandler? = null
    var shoppingListsCursorAdaptor: CursorAdapter? = null

    //start shopperlist
    var shopperListView: ListView? = null

    /**
     * This method intializies the action bar and view of the activity
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //Start DbHandler
        val dbHandler = DBHandler(this, null)
        shopperListView = findViewById<View>(R.id.shopperListView) as ListView
        shoppingListsCursorAdaptor = ShoppingLists(this,
                dbHandler.shoppingLists, 0)
        shopperListView!!.adapter = shoppingListsCursorAdaptor

        //set onItemClickListener on the listview
        shopperListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id -> //start Intent for the viewList Activity
            intent = Intent(this@MainActivity, ViewList::class.java)

            //put the database id in the intent
            intent!!.putExtra("_id", id)

            //start the viewList
            startActivity(intent)
        }
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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

    fun openCreateList(view: View?) {
        intent = Intent(this, CreateList::class.java)
        startActivity(intent)
    }
}