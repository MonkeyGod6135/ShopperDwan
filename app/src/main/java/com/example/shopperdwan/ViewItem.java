package com.example.shopperdwan;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class ViewItem extends AppCompatActivity {

    //declare a dbhandler
    DBHandler dbHandler;

    //declare intent
    Intent intent;

    //declare edit texts
    EditText nameEditText;
    EditText priceEditText;
    EditText quantityEditText;

    //Declare a Bundle and long for data sent from main activity
    Bundle bundle;
    long id;

    //declare strings to store clicked shopping list item data
    String name;
    String price;
    String quantity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //start bundle
        bundle = this.getIntent().getExtras();

        //use Bundle to get id
        id = bundle.getLong("_id");

        //start dbhandler
        dbHandler = new DBHandler(this,null);

        //start Edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);

        //disablded edit texts
        nameEditText.setEnabled(false);
        priceEditText.setEnabled(false);
        quantityEditText.setEnabled(false);

        //call the dbhandler getShoppinglistItem
        Cursor cursor = dbHandler.getShoppingListItems((int) id);

        //move to first row in the cursor
        cursor.moveToFirst();

        //get data and store into strings
        name = cursor.getString(cursor.getColumnIndex("name"));
        price = cursor.getString(cursor.getColumnIndex("price"));
        quantity = cursor.getString(cursor.getColumnIndex("quantity"));

        //set values in edit text
        nameEditText.setText(name);
        priceEditText.setText(price);
        quantityEditText.setText(quantity);

    }
    public void deleteItem(MenuItem menuItem){

    }
    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_item, menu);
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
}