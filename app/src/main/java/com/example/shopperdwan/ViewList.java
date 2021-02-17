package com.example.shopperdwan;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class ViewList extends AppCompatActivity {

    //Declare a Bundle and long for data sent from main activity
    Bundle bundle;
    long id;

    //declare DBHandler
    DBHandler dbHandler;

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

    }

    public void openAddItem(View view) {
    }
}