package org.vbm.iceandflame;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vbm on 06/02/2017.
 */

public class mPagerFragment extends Fragment {
    public static final String KEY_POSITION = "FrAgMeNt";
    int position;
    String[] characters;
    RecyclerView recyclerView;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_books, container, false);
        position = getArguments().getInt(KEY_POSITION);
        //characters = getArguments().getString(KEY_NAME).split(", ");
        LinearLayoutManager lm = new LinearLayoutManager(this.getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(lm);
        //cursor = Globals.databaseAccess.getGursor();
        cursor = Globals.databaseAccess.getGursor("select _id, name, aliases from characters where instr( (select characters from books where _id='" + (position + 1) + "'),'['||_id||'],') > 0 ");
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this.getContext(), cursor);
        recyclerView.setAdapter(customCursorAdapter);
        return rootView;
    }


}
