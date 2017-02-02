package org.vbm.iceandflame;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vbm on 02/02/2017.
 */

public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.CustomViewHolder> {

    Cursor cursor;


    public CustomCursorAdapter() {
        super();
        cursor = Globals.databaseAccess.cursor;
        cursor.moveToFirst();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bookView.setText(cursor.getString(0));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView bookView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            bookView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(BooksActivity.Me, bookView.getText() + " : " + Integer.toString(getAdapterPosition() + 1), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
