package org.vbm.iceandflame;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vbm on 02/02/2017.
 * For my RecycleView this simple adapter from sqLite cursor
 */

class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.CustomViewHolder> {

    private Cursor cursor;
    private Context activityContext;


    CustomCursorAdapter(Context context) {
        super();
        cursor = Globals.databaseAccess.cursor;
        cursor.moveToFirst();
    }

    CustomCursorAdapter(Context context, Cursor cursor) {
        super();
        this.cursor = cursor;
        activityContext = context;
        cursor.moveToFirst();
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bookView.setText(cursor.getInt(0) + " " + cursor.getString(1));

    }

    @Override
    public int getItemCount() {

        return cursor.getCount();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView bookView;

        CustomViewHolder(final View itemView) {
            super(itemView);
            bookView = (TextView) itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activityContext,CharacterActivity.class);
                    Bundle sets = new Bundle();
                    sets.putInt(CharacterActivity.KEY,Integer.parseInt(bookView.getText().toString().split(" ")[0]));
                    intent.putExtras(sets);
                    activityContext.startActivity(intent,sets);
                }
            });
        }


    }
}
