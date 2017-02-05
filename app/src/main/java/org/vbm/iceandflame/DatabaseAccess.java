package org.vbm.iceandflame;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by vbm on 25/03/15.
 * Database working class
 */
public class DatabaseAccess {

    private static SQLiteDatabase dBase;
    final private String name;
    Cursor cursor;

    public DatabaseAccess(Context context, boolean createFlag) {
        this.name = "iceandflame";
        if (createFlag) createDataBase(context);
    }


    public void execQuery(String query) {
        dBase.execSQL(query);
    }

    private boolean createDataBase(Context context) {
        try {
            if (dBase != null) dBase.close();
            dBase = context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);

   /* Create a Table in the Database. */
            dBase.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "books"
                    + " ( _id VRCHAR(15) primary key, name VARCHAR(100), date DATETIME, characters VARCHAR )");
            dBase.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "characters"
                    + " ( _id INT primary key, name VARCHAR(25), aliases VARCHAR )");
            dBase.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "changes"
                    + " ( _date DATETIME primary key )");
            return true;
        } catch (Exception e) {
            Toast.makeText(context, "Ошибка операции с базами данных\n" + e.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public void clearTable(String tableName) {
        dBase.execSQL("DELETE from " + tableName);
    }

    public void initCursor(String query) {
        cursor = dBase.rawQuery(query, null);
    }
}