package org.vbm.iceandflame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vbm on 02/02/2017.
 */

public class SplashActivity extends AppCompatActivity {

    public static SplashActivity Me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Me = this;
        Globals.databaseAccess = new DatabaseAccess(this, true);
        initSplash();


    }

    private void initSplash() {
        //textView = (TextView) findViewById(R.id.outout);
        BgHTTPread bRead = new BgHTTPread();
        bRead.execute("");
    }

    public void toBook() {
        Intent intent = new Intent(this, BooksActivity.class);
        startActivity(intent);
        finish();
    }
}
