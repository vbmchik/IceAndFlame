package org.vbm.iceandflame;

import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CharacterActivity extends AppCompatActivity {
    RelativeLayout linearlayout;
    public static final String KEY = "PoSiTiOn";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        initControls();
    }

    @Override
    protected void onDestroy() {
        linearlayout.setBackground(null);
        super.onDestroy();

    }

    void initControls(){
        Random random = new Random();
        int number =  getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 5;
        number += random.nextInt(4);
        linearlayout = (RelativeLayout) findViewById(R.id.activity_character);
        linearlayout.setBackground(null);
        Drawable gotImage = getDrawable(getResources().getIdentifier("i"+String.valueOf(number),"drawable","org.vbm.iceandflame" ));
        gotImage.setAlpha(60);
        linearlayout.setBackground(gotImage);
        linearlayout.setDrawingCacheEnabled(false);
        gotImage = null;
        int id = getIntent().getIntExtra(KEY,0);
        if( id == 0 ){
            Toast.makeText(this,"No character id passed - contact developer!",Toast.LENGTH_SHORT).show();
            finish();
        }

        Cursor cur = Globals.databaseAccess.getGursor("select * from characters where _id = '"+id+"'");
        cur.moveToFirst();
        textView = (TextView) findViewById(R.id.charInfo);
        String out = "";
        out += "ID : " + cur.getInt(0) + "\n" ;
        out += "Name : " + cur.getString(1) + "\n" ;
        out += "Aliases : " + cur.getString(2) + "\n" ;
        cur.close();
        textView.setText(out);
    }
}
