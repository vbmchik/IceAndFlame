package org.vbm.iceandflame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static MainActivity Me;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.databaseAccess = new DatabaseAccess(this, true);
        initSplash();
        Me = this;
    }

    private void initSplash() {
        textView = (TextView) findViewById(R.id.outout);
        Globals.bRead.execute("");
    }

    public void startBooksActivity() {
        startActivity(new Intent(this, BooksActivity.class));
    }
}
