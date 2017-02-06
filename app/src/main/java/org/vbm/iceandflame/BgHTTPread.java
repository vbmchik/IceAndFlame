package org.vbm.iceandflame;

import android.content.Context;
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
 * Created by vbm on 01/02/2017. AsyncTask read from REST and write  sqlite
 */

class BgHTTPread extends AsyncTask<String, Integer, Integer> {
    private static final String BASE_URL = "http://anapioficeandfire.com/api/";
    //protected JSONArray bookshelf;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }
    @Override
    protected Integer doInBackground(String... strings) {
        if (context == null) return -3;
        int res;

        Long t1 = System.currentTimeMillis();
        //bookshelf = new JSONArray();
        try {
            res = getData("books");
            if (res != -1) res = getData("characters");
            if (res == 0) SharedPrefsReadWrite.writeTo(context);
        } catch (Exception e) {
            e.printStackTrace();
            res = -1;
        }
        while (System.currentTimeMillis() - t1 < 3000) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return res;
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
                query += "'" + temp.getString("url").replace(BASE_URL + "books" + "/", "") + "', ";
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
                query += "'" + temp.getString("url").replace(BASE_URL + "characters" + "/", "") + "', ";
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
                String s = jsonArray.getString(i);
                chars += "[" + s.replace(BASE_URL + key + "/", "") + "]";
                chars += ", ";
            }
            //chars += " ]";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chars;
    }


    private Integer getData(String... strings) {
        Integer result = 0;
        int q = 0;
        try {
            for (int i = 1; i > -1; ++i) {
                JSONArray tempJSON;
                String tempS = "";
                URL url = new URL(BASE_URL + strings[0] + "/?page=" + i + "&pageSize=50");
                URLConnection con = url.openConnection();
                //Tue, 15 Nov 1994 08:12:31 GMT
                con.setRequestProperty("If-Modified-Since", SharedPrefsReadWrite.readFrom(context));
                //con.setRequestProperty("If-Modified-Since", "Fri, 03 Feb 2017 08:12:31 GMT" );
                con.setConnectTimeout(1000);
                con.setReadTimeout(3000);
                BufferedReader stream = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Scanner s = new Scanner(stream);
                while (s.hasNext()) {
                    tempS += s.nextLine();
                }

                s.close();

                if (tempS.length() < 3)
                    if (i == 1)
                        return -2;
                    else {
                        //i = -1;
                        break;
                    }

                tempJSON = new JSONArray(tempS);
                proceedJSON(tempJSON, strings[0]);
                q += tempJSON.length();
                /*for (int a = 0; a < tempJSON.length(); ++a)
                    bookshelf.put(tempJSON.get(a));*/
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
        if (strings[0].equals("books"))
            publishProgress(1);
        else
            publishProgress(2);
        return result;
    }

    @Override
    protected void onPostExecute(Integer s) {
        if (s == -1) {
            // Exception catch
            if (context != null)
                Toast.makeText(context, "Network error.\nData was not synchronized!", Toast.LENGTH_SHORT).show();
        }
        if (s == -2) {
            // Exception catch
            if (context != null)
                Toast.makeText(context, "No data need to be synchronized!", Toast.LENGTH_SHORT).show();
        }
        //MainActivity.Me.startBooksActivity();
        //MainActivity.Me.textView.setText(s);
        if (context != null)
            ((SplashActivity) context).toBook();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        String msg;
        if (context != null) {
            switch (values[0]) {
                case 1:
                    msg = "Synchronized books...";
                    break;
                case 2:
                    msg = "Synchronized characters...";
                    break;
                default:
                    msg = "Syncronized something...";
                    break;
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

}

