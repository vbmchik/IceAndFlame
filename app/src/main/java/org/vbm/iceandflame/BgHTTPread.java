package org.vbm.iceandflame;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by vbm on 01/02/2017.
 */

public class BgHTTPread extends AsyncTask<String, Integer, Integer> {
    private static final String BASE_URL = "http://anapioficeandfire.com/api/";
    protected JSONArray bookshelf;

    @Override
    protected Integer doInBackground(String... strings) {
        int res = 0;
        bookshelf = new JSONArray();
        try {
            res = getData("books");
            if (res == -1) return -1;
            res = getData("characters");
            if (res == -1) return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    private void proceedJSON(JSONArray jsonArray, String name) {
        String query = "false";
        if (name.equals("books"))
            query = makeBooksQuery(jsonArray);

        if (name.equals("characters"))
            query = makeCharacterQuery(jsonArray);
        if (!query.equals("false"))
            Globals.databaseAccess.execQuery(query);
    }

    private String makeBooksQuery(JSONArray jsonArray) {
        String query = "";
        query += "INSERT OR REPLACE INTO BOOKS ( _id, name, date, characters ) \n";
        query += "VALUES \n";

        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                query += i > 0 ? ",\n( " : "( ";
                JSONObject temp = jsonArray.getJSONObject(i);
                query += "'" + temp.getString("url").replace("http://anapioficeandfire.com/api/" + "books" + "/", "") + "', ";
                query += "\"" + temp.getString("name").replace("\"", "'") + "\", ";
                query += "'" + temp.getString("released") + "', ";
                query += "'" + makeString(temp.getJSONArray("characters"), "characters") + "' ";
                query += ")";
            } catch (JSONException e) {
                e.printStackTrace();
                return "false";
            }
        }
        return query;
    }

    private String makeCharacterQuery(JSONArray jsonArray) {
        String query = "";
        query += "INSERT OR REPLACE INTO CHARACTERS ( _id, name, aliases ) \n";
        query += "VALUES \n";

        for (int i = 0; i < jsonArray.length(); ++i) {
            try {
                query += i > 0 ? ",\n( " : "( ";
                JSONObject temp = jsonArray.getJSONObject(i);
                query += "'" + temp.getString("url").replace("http://anapioficeandfire.com/api/" + "characters" + "/", "") + "', ";
                query += "\"" + temp.getString("name") + "\", ";
                query += "\"" + makeString(temp.getJSONArray("aliases"), "characters").replace("\"", "'") + "\" ";
                query += ")";
            } catch (JSONException e) {
                e.printStackTrace();
                return "false";
            }
        }
        return query;
    }

    private String makeString(JSONArray jsonArray, String key) {
        String chars = "";
        try {
            //chars += "[ ";
            for (int i = 0; i < jsonArray.length(); ++i) {
                if (i > 0) chars += ", ";
                String s = jsonArray.getString(i);
                chars += s.replace("http://anapioficeandfire.com/api/" + key + "/", "");
            }
            //chars += " ]";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chars;
    }


    private Integer getData(String... strings) {
        Integer result = 0;
        try {
            for (int i = 1; i > -1; ++i) {
                JSONArray tempJSON;
                String tempS = "";
                URL url = new URL(BASE_URL + strings[0] + "/?page=" + i + "&pageSize=50");
                URLConnection con = url.openConnection();
                con.setConnectTimeout(1000);
                con.setReadTimeout(1000);
                BufferedReader stream = new BufferedReader(new InputStreamReader(con.getInputStream()));
                if (stream == null) return (-1);
                Scanner s = new Scanner(stream);
                do {
                    tempS += s.nextLine();
                } while (s.hasNext());
                if (tempS.length() < 3) {
                    s.close();
                    i = -1;
                    break;
                }
                tempJSON = new JSONArray(tempS);
                proceedJSON(tempJSON, strings[0]);
                for (int a = 0; a < tempJSON.length(); ++a)
                    bookshelf.put(tempJSON.get(a));
                s.close();

            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return (-1);
        } catch (JSONException e) {
            e.printStackTrace();
            return (-1);
        } catch (Exception e) {
            e.printStackTrace();
            return (-1);
        }

        publishProgress(bookshelf.length());

        return result;
    }

    @Override
    protected void onPostExecute(Integer s) {
        if (s == -1) {
            // Exception catch
            if (BooksActivity.Me != null)
                Toast.makeText(BooksActivity.Me, "Error! of socket", Toast.LENGTH_SHORT).show();
            if (SplashActivity.Me != null)
                Toast.makeText(SplashActivity.Me, "Network error.\nData was not synchronized!", Toast.LENGTH_SHORT).show();
        }
        //MainActivity.Me.startBooksActivity();
        //MainActivity.Me.textView.setText(s);
        if (SplashActivity.Me != null)
            SplashActivity.Me.toBook();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (BooksActivity.Me != null)
            Toast.makeText(BooksActivity.Me, "Page : " + values[0], Toast.LENGTH_SHORT).show();
        else if (SplashActivity.Me != null)
            Toast.makeText(SplashActivity.Me, "Page : " + values[0], Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) SplashActivity.Me.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}

