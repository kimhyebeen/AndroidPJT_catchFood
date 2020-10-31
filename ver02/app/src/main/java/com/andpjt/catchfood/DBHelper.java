package com.andpjt.catchfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static final String TAG = "DBHelper";
    private static DBHelper dbHelper = DBHelper.getDbHelper();
    private static SQLiteDatabase db;
    Cursor cursor;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void setDbHelper(DBHelper dbH) {
        dbHelper = dbH;
    }

    public void createTable(SQLiteDatabase db) {
        Log.d(TAG, "create table");
        String sql = "create table if not exists menu " +
                "(_id integer PRIMARY KEY autoincrement," +
                "food text," +
                "prefer integer)";
        db.execSQL(sql);
    }

    public void deleteTable(SQLiteDatabase db, String food) {
        Log.d(TAG, "delete data in table");
        db.delete("menu", "food = \'"+food+"\'", null);
    }

    public void insertToTable(SQLiteDatabase database, ContentValues values) {
        Log.d(TAG, "add data to table");
        database.insert("menu", null, values);
    }

    public void updateData(SQLiteDatabase database, String food, ContentValues values) {
        Log.d(TAG, "update contents");
        database.update("menu", values, "food = \'"+food+"\'",null);
    }

    public Cursor selectAll(SQLiteDatabase database) {
        Log.d(TAG, "select all data");
        String sql = "SELECT * FROM menu";
        cursor = database.rawQuery(sql, null);
        return cursor;
    }

    public void dropTable(SQLiteDatabase db) {
        String sql = "drop table if exists menu";
        db.execSQL(sql);
        Log.d(TAG, "drop the table");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
