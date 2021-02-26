package com.example.shopperdwan;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ViewList extends AppCompatActivity {

    //Declare a Bundle and long for data sent from main activity
    Bundle bundle;
    long id;

    //declare DBHandler
    DBHandler dbHandler;

    //declare Intent
    Intent intent;

    //declare a shopping list cursor adaptor
    ShoppingListItems shoppingListItemsAdapter;

    //declare a listview
    ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //start bundle
        bundle = this.getIntent().getExtras();

        //use Bundle to get id
        id = bundle.getLong("_id");

        //start dbhandler
        dbHandler = new DBHandler(this,null);

        //call getShoppingListNameMethod
        String shoppingListName = dbHandler.getShoppingListName((int) id);

        //set the title of the viewlist activity
        this.setTitle(shoppingListName);

        //initialize the listview
        itemListView = (ListView) findViewById(R.id.itemsListView);

        //initalize the shoppingListItems
        shoppingListItemsAdapter = new ShoppingListItems(this, dbHandler.getShoppingListItems((int)id),0);

        //set the shoppinglist items
        itemListView.setAdapter(shoppingListItemsAdapter);

    }
    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
        return true;
    }

    /**
     * This method is called when a menu item in a overflow menu is selected
     * and it controls what happens when it is selected
     * @param item selected item in overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home :
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list :
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            case R.id.action_add_item :
                intent = new Intent(this, Additem.class);
                //put the database id in the intent
                intent.putExtra("_id",id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    /**
     * This method gets called when the add floating action button is clicked
     * It starts the addietm activity
     * @param view viewlist
     */
    public void openAddItem(View view) {
        intent = new Intent(this, Additem.class);
        //put the database id in the intent
        intent.putExtra("_id",id);
        startActivity(intent);
    }
}