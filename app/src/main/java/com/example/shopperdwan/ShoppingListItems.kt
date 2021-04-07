package com.example.shopperdwan

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class ShoppingListItems
/**
 *
 * @param context reference to the activity that initializes the shoppinglistitem cursoradapter
 * @param c reference to the cursor that contains the data selected
 * @param flags determines special behavior of the cursoradapter
 */
(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    /**
     *
     * @param context reference to the activity that initializes the shoppinglistitem cursoradapter
     * @param cursor reference to the cursor that contains the data selected
     * @param parent reference
     * @return
     */
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.li_item_list, parent, false)
    }

    /**
     * Bind new view to data in cursor
     * @param view reference to view
     * @param context
     * @param cursor
     */
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view.findViewById<View>(R.id.nameTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("name"))
        (view.findViewById<View>(R.id.priceTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("price"))
        (view.findViewById<View>(R.id.quantityTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("quantity"))
        (view.findViewById<View>(R.id.hasTextView) as TextView).text = "Item Purchased " + cursor.getString(cursor.getColumnIndex("item_has"))
    }
}