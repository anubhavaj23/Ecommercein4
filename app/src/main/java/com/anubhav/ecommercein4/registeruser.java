package com.anubhav.ecommercein4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

/**
 * Created by anubh on 14-Dec-16.
 */

public class registeruser extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ecommerce1.db";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_MOBILENO = "mobileno";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_LOGGEDIN = "loggedin";

    public registeruser(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_NAME + " TEXT," +
                COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                COLUMN_MOBILENO + " TEXT UNIQUE," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_LOGGEDIN +" INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean addUser(Users user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MOBILENO, user.getMobileno());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_LOGGEDIN, user.getLoggedinstatus());
        SQLiteDatabase db = this.getWritableDatabase();
        long status = db.insert(TABLE_NAME, null, values);
        db.close();
        if(status != -1)
            return true;
        else
            return false;
    }

    public boolean readUser(String inputemail, String inputpassword){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE "+COLUMN_EMAIL+"='"+inputemail+"';";

        //cursor points to a location in results
        Cursor c = db.rawQuery(query, null);
        //Move cursor to first row
        c.moveToFirst();
        while(!c.isAfterLast())
            if(c.getString(c.getColumnIndex(COLUMN_PASSWORD)).equals(inputpassword)) {
                db.close();
                return true;
            }
        db.close();
        return false;
    }

    public Users getloggedinuser(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + COLUMN_LOGGEDIN + "=1;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if(!c.isAfterLast()){
            Users user = new Users(c.getString(c.getColumnIndex(COLUMN_NAME)),c.getString(c.getColumnIndex(COLUMN_EMAIL)),
                    c.getString(c.getColumnIndex(COLUMN_MOBILENO)),c.getString(c.getColumnIndex(COLUMN_PASSWORD)),
                    c.getInt(c.getColumnIndex(COLUMN_LOGGEDIN)));
            db.close();
            return user;
        }
        db.close();
        return null;
    }

    public boolean updateuser(Users user, Users olduser){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MOBILENO, user.getMobileno());
        values.put(COLUMN_PASSWORD, user.getPassword());

        long status = db.update(TABLE_NAME, values,COLUMN_EMAIL+"='"+ olduser.getEmail() +"'", null);
        db.close();
        if(status != -1)
            return true;
        else
            return false;

    }

    public void setloggedinstatus(String inputemail){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+COLUMN_LOGGEDIN+"=0;");
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+COLUMN_LOGGEDIN+"=1 WHERE "+COLUMN_EMAIL+"='"+inputemail+"';");

        db.close();

    }

    public void logoutusers(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+COLUMN_LOGGEDIN+"=0;");
        db.close();
    }


}
