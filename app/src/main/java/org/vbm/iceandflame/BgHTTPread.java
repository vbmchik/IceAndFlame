package org.vbm.iceandflame;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.util.Scanner;

/**
 * Created by vbm on 01/02/2017.
 */

public class BgHTTPread extends AsyncTask<String, String, String> {
    private static final String BASE_URL = "http://anapioficeandfire.com/api/";
    private JSONArray bookshelf;

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        try {
            URL url = new URL(BASE_URL+strings[0]+"/?page=7&pageSize=50");
            Scanner s = new Scanner(url.openStream());
            do{
                result += s.nextLine();
            }while(s.hasNext());
            s.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return("-1");
        }
        try {
            bookshelf = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        result  = "";
        for( int i = 0 ; i < bookshelf.length(); ++i ){
            try {
                result += bookshelf.getJSONObject(i).getString("name")+"\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        if( s.equals("-1")){
            // Exception catch
            Toast.makeText(MainActivity.Me,"Error! of socket",Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(MainActivity.Me,s,Toast.LENGTH_SHORT).show();
        MainActivity.Me.textView.setText(s);
    }
}

