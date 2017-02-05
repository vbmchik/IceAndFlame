package org.vbm.iceandflame;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by vbm on 03/02/2017.
 * Data saving into shpr to make cached requst - we will get only differential data
 */

class SharedPrefsReadWrite {

    public static String readFrom(Context context) {
        String myDate;
        SharedPreferences sharedPreferences = context.getSharedPreferences("oficeandflame", Context.MODE_PRIVATE);
        myDate = sharedPreferences.getString("date", "Tue, 15 Nov 1964 12:45:26 GMT");
        return myDate;
    }

    public static void writeTo(Context context) {
        // String date = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss").format( new Date()) +" GMT";
        String date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss").format(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime()) + " GMT";
        SharedPreferences sharedPreferences = context.getSharedPreferences("oficeandflame", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("date", date);
        ed.commit();
    }
}
