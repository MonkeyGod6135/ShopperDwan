package com.example.shopperdwan;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * The ShoppingLists class will map the data selected from the shopping lists
 * table in the cursor to the shopping_list resource
 */
public class ShoppingLists extends CursorAdapter {
    /**
     *
     * @param context reference to the activity that initializes the shoppinglist cursoradapter
     * @param c reference to the cursor that contains the data selected
     * @param flags determines special behavior of the cursoradapter
     */
    public ShoppingLists(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     *
     * @param context reference to the activity that initializes the shoppinglist cursoradapter
     * @param cursor reference to the cursor that contains the data selected
     * @param parent reference
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list,parent,false);
    }

    /**
     * Bind new view to data in cursor
     * @param view reference to view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.storeTextView)).
                setText(cursor.getString(cursor.getColumnIndex("store")));
        ((TextView) view.findViewById(R.id.dateTextView)).
                setText(cursor.getString(cursor.getColumnIndex("date")));

    }
}
