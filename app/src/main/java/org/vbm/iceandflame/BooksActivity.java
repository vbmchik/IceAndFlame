package org.vbm.iceandflame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class BooksActivity extends AppCompatActivity {
    //public static BooksActivity Me;
    RecyclerView booksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Me = this;
        Globals.bRead = null;
        setContentView(R.layout.activity_books);
        initControls();
    }


    private void initControls() {
        booksView = (RecyclerView) findViewById(R.id.recycler);
        Globals.databaseAccess.initCursor("select name from books order by _id");
        LinearLayoutManager lm = new LinearLayoutManager(this);
        booksView.setLayoutManager(lm);
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this);
        booksView.setAdapter(customCursorAdapter);
        //RecyclerView.Adapter mAdapter = new RecyclerView.Adapter<>();
    }

}
