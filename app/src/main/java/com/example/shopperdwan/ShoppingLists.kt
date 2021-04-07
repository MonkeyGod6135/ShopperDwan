package com.example.shopperdwan

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * The ShoppingLists class will map the data selected from the shopping lists
 * table in the cursor to the shopping_list resource
 */
class ShoppingLists
/**
 *
 * @param context reference to the activity that initializes the shoppinglist cursoradapter
 * @param c reference to the cursor that contains the data selected
 * @param flags determines special behavior of the cursoradapter
 */
(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    /**
     *
     * @param context reference to the activity that initializes the shoppinglist cursoradapter
     * @param cursor reference to the cursor that contains the data selected
     * @param parent reference
     * @return
     */
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list, parent, false)
    }

    /**
     * Bind new view to data in cursor
     * @param view reference to view
     * @param context
     * @param cursor
     */
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view.findViewById<View>(R.id.nameTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("name"))
        (view.findViewById<View>(R.id.storeTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("store"))
        (view.findViewById<View>(R.id.dateTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("date"))
    }
}