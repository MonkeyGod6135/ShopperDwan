package com.example.shopperdwan

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ViewList : AppCompatActivity() {
    //Declare a Bundle and long for data sent from main activity
    var bundle: Bundle? = null
    var id: Long = 0
    var listId: Long = 0

    //declare DBHandler
    var dbHandler: DBHandler? = null

    //declare Intent
   // var intent: Intent? = null

    //declare a shopping list cursor adaptor
    var shoppingListItemsAdapter: ShoppingListItems? = null
    var shoppingListName: String? = null

    //declare a listview
    var itemListView: ListView? = null

    //declare Notification manager used to show (display) the notification
    var notificationManagerCompat: NotificationManagerCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //start bundle
        bundle = getIntent().extras

        //use Bundle to get id
        id = bundle!!.getLong("_id")
        listId = bundle!!.getLong("_list_id")

        //start dbhandler
        dbHandler = DBHandler(this, null)

        //call getShoppingListNameMethod
        shoppingListName = dbHandler!!.getShoppingListName(id.toInt())

        //set the title of the viewlist activity.
        this.title = shoppingListName

        //initialize the listview
        itemListView = findViewById<View>(R.id.itemsListView) as ListView

        //initialize the shoppingListItems
        shoppingListItemsAdapter = ShoppingListItems(this, dbHandler!!.getShoppingListItems(id.toInt()), 0)

        //set the shoppinglist items
        itemListView!!.adapter = shoppingListItemsAdapter

        //register an onclickitemlistener
        itemListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            /**
             * This method is called when an item on the listview is clicked
             * @param parent itemlistview
             * @param view viewlist activity view
             * @param position postiion of clicked item
             * @param id database id of clicked item
             */
            /**
             * This method is called when an item on the listview is clicked
             * @param parent itemlistview
             * @param view viewlist activity view
             * @param position postiion of clicked item
             * @param id database id of clicked item
             */

            //call method that updates the clicked items item_has to true
            // if it's false
            updateItem(id)

            //start intent for viewitem activity
            intent = Intent(this@ViewList, ViewItem::class.java)

            //put the database id in the intent
            intent!!.putExtra("_id", id)

            //put the database id in the intent
            intent!!.putExtra("_list_id", this@ViewList.id)

            //start the viewList
            startActivity(intent)
        }

        //set the subtitle to the total cost of the shoppinglist 
        toolbar.subtitle = "Total Cost: $" + dbHandler!!.getShoppingListTotalCost(id.toInt())

        //intialize the notification manager
        notificationManagerCompat = NotificationManagerCompat.from(this)
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_list, menu)
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
                intent!!.putExtra("_id", listId)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the add floating action button is clicked
     * It starts the addietm activity
     * @param view viewlist
     */
    fun openAddItem(view: View?) {
        intent = Intent(this, Additem::class.java)
        //put the database id in the intent
        intent!!.putExtra("_id", id)
        startActivity(intent)
    }

    /**
     * This method is called when an item is clicked on the ListView
     * It updates the clicked item's item_has to true if it's false
     * @param id database id of item
     */
    fun updateItem(id: Long) {
        //check if the clicked item is unpurchased
        if (dbHandler!!.isItemUnPurchase(id.toInt()) == 1) {
            //make clicked item purchased
            dbHandler!!.updateItem(id.toInt())

            //refresh Listview with updated data
            shoppingListItemsAdapter!!.swapCursor(dbHandler!!.getShoppingListItems(this.id.toInt()))
            shoppingListItemsAdapter!!.notifyDataSetChanged()

            //display Toast indicating item is purchased
            Toast.makeText(this, "Item Purchased", Toast.LENGTH_SHORT).show()
        }
        //if all shopping list items have been purchased
        if (dbHandler!!.getUnpurchasedItems(this.id.toInt()) == 0) {
            //initilize Notification
            val notification = NotificationCompat.Builder(this,
                    App.CHANNEL_SHOPPER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Shopper")
                    .setContentText(shoppingListName + "completed!").build()

            //show Notifation
            notificationManagerCompat!!.notify(1, notification)
        }
    }

    fun deleteList(menuItem: MenuItem?) {
        dbHandler!!.deleteShoppingList(id.toInt())
        Toast.makeText(this, "List Deleted!", Toast.LENGTH_LONG).show()
    }
}