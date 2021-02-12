package com.example.shopperdwan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateList extends AppCompatActivity {

    Intent intent;

    //declare edittexts
    EditText nameEditText;
    EditText storeEditText;
    EditText dateEditText;

    //declare calender
    Calendar calendar;

    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        storeEditText = (EditText) findViewById(R.id.storeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        //intialize calander
        calendar = calendar.getInstance();

        //Intilize datepicker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            /**
             * This method handles the onDateSet event
             * @param datePicker DatePickerview
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //set the year, month, and day into calender
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //call method
                updateDueDate();

            }
        };
        //register an onclick
        dateEditText.setOnClickListener(new View.OnClickListener() {
            /**
             * This method handles the onclick method
             * @param view
             */
            @Override
            public void onClick(View view) {
                //display DatePickerDialog
                new DatePickerDialog(CreateList.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        dbHandler = new DBHandler(this,null);

    }
    private void updateDueDate(){
        //create format for date in calender
        SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        dateEditText.setText(SimpleDateFormat.format(calendar.getTime()));
    }

    /**
     * This method further intializes the action bar activity
     * @param menu menu resource file for the activity
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_list, menu);
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
                intent = new Intent(this, CreateList.class);
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

    public void createList(MenuItem menuItem){
        //get data input in EditTexts and store into strings
        String name = nameEditText.getText().toString();
        String store = storeEditText.getText().toString();
        String date = dateEditText.getText().toString();

        if(name.trim().equals("")||store.trim().equals("")||date.trim().equals("")){
            Toast.makeText(this, "Please enter name, store, and date!",
                    Toast.LENGTH_LONG).show();
        } else{
            dbHandler.addShoppingList(name, store, date);

            Toast.makeText(this, "Shopping List Created!",
                    Toast.LENGTH_LONG).show();

        }

    }

}