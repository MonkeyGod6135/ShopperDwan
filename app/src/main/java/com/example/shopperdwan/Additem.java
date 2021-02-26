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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Additem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Declare a Bundle and long for data sent from view list activity
    Bundle bundle;
    long id;

    //declare DBHandler
    DBHandler dbHandler;

    //declare Intent
    Intent intent;

    //declare editextxs
    EditText nameEditText;
    EditText priceEditText;

    //declare Spinner
    Spinner quantitySpinner;

    //declare strings to store edit texts
    String quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //start bundle
        bundle = this.getIntent().getExtras();

        //use Bundle to get id
        id = bundle.getLong("_id");

        //start dbhandler
        dbHandler = new DBHandler(this,null);

        //start edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        //start spinner
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);

        //start Arrayadaptor
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantities, android.R.layout.simple_spinner_item);

        //styleize array
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //set the arrayadapter
        quantitySpinner.setAdapter(adapter);

        //register an onitemselectedlistener to spanner
        quantitySpinner.setOnItemSelectedListener(this);
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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
            case R.id.action_view_list :
                intent = new Intent(this, ViewList.class);
                //put the database id in the intent
                intent.putExtra("_id",id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    public void additem(MenuItem menuItem){

        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();

        if (name.trim().equals("") || (price.trim().equals(""))
        || quantity.trim().equals("")){
            Toast.makeText(this, "Please enter a name, price, and quantity", Toast.LENGTH_LONG).show();
        } else {
            //add item to database
            dbHandler.addItemToList(name, Double.parseDouble(price), Integer.parseInt(quantity), (int) id);
            //display text
            Toast.makeText(this, "Item Added", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * This method gets called when an item in the spinner is selected.
     * @param parent Spinner AdapterView
     * @param view addItem View
     * @param position position of item
     * @param id database id of item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quantity = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}