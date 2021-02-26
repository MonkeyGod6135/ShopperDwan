package com.example.shopperdwan;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //start intent
    Intent intent;

    //start dbhandler
    DBHandler DBhandler;

    CursorAdapter shoppingListsCursorAdaptor;

    //start shopperlist
    ListView shopperListView;

    /**
     * This method intializies the action bar and view of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Start DbHandler
        DBHandler dbHandler = new DBHandler(this, null);

        shopperListView = (ListView) findViewById(R.id.shopperListView);

        shoppingListsCursorAdaptor = new ShoppingLists(this,
                dbHandler.getShoppingLists(),0);

        shopperListView.setAdapter(shoppingListsCursorAdaptor);

        //set onItemClickListener on the listview
        shopperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //start Intent for the viewList Activity
                intent = new Intent(MainActivity.this,ViewList.class);

                //put the database id in the intent
                intent.putExtra("_id",id);

                //start the viewList
                startActivity(intent);
            }
        });

    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.action_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list:
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    public void openCreateList(View view) {
        intent = new Intent(this, CreateList.class);
        startActivity(intent);
    }
}