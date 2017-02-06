package org.vbm.iceandflame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vbm on 02/02/2017.
 * Splash screen
 */

public class SplashActivity extends AppCompatActivity {

    //public static SplashActivity Me;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Me = this;
        Globals.databaseAccess = new DatabaseAccess(this, true);
        initSplash();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Me = null;
    }

    private void initSplash() {
        //textView = (TextView) findViewById(R.id.outout);
        if (!Globals.isBackground) {
            Globals.bRead.setContext(this);
            Globals.bRead.execute("");
            Globals.isBackground = true;
        } else if (Globals.bRead != null && Globals.bRead.getStatus() == AsyncTask.Status.FINISHED) {
            toBook();
        }
        if (Globals.bRead == null) toBook();
    }

    public void toBook() {
        Intent intent = new Intent(this, BookTabActivity.class);
        startActivity(intent);
        finish();
    }
}
