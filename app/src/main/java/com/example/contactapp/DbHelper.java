package com.example.contactapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//class for database helper
public class DbHelper extends SQLiteOpenHelper {
    String id;
    private static final String dbname="contact.db";
    public DbHelper(@Nullable Context context) {
        super(context, dbname, null, Constants.DTABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create table on database
        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //upgrade table if any structure change in db

        // drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        // create table again
        onCreate(db);

    }

    // Insert Function to insert data in database
    public long insertContact(String name, String phone, String email, String addedTime, String updatedTime) {

        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();

        // id will save automatically as we write query

        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_PHONE, phone);
        contentValues.put(Constants.C_EMAIL, email);

        contentValues.put(Constants.C_ADDED_TIME, addedTime);
        contentValues.put(Constants.C_UPDATED_TIME, updatedTime);

        //insert data in row, It will return id of record
         long id = db.insert(Constants.TABLE_NAME, null, contentValues);

        // close db
        db.close();

        //return id
        return id;

    }
    public String getContactPhoneNumber(String id) {
        SQLiteDatabase db = getReadableDatabase();
        String phoneNumber = "";

        String selectQuery = "SELECT " + Constants.C_PHONE + " FROM " + Constants.TABLE_NAME +
                " WHERE " + Constants.C_ID + " = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
        }

        cursor.close();
        db.close();

        return phoneNumber;
    }



    // Update Function to update data in database
    // Update Function to update data in database based on name
// Update Function to update data in database based on name
    public void updateContact(String name, String newName, String newPhone, String newEmail) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_NAME, newName);
        contentValues.put(Constants.C_PHONE, newPhone);
        contentValues.put(Constants.C_EMAIL, newEmail);

        // Specify the WHERE clause to identify the rows to update
        String whereClause = Constants.C_NAME + " = ?";
        String[] whereArgs = {name};

        // Update the rows
        int rowsAffected = db.update(Constants.TABLE_NAME, contentValues, whereClause, whereArgs);

        db.close();

        Log.d("UpdateContact", "Rows affected: " + rowsAffected);
    }



    // delete data by id
    public void deleteContact(String id) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //delete query
        db.delete(Constants.TABLE_NAME,  Constants.C_ID+" =? ", new String[]{id});

        db.close();
    }

    // delete all data

        //get writable database
        public void deleteAllContact() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(Constants.TABLE_NAME, null, null);
            db.close();
        }

        public ArrayList<ModelContact> getAllDta(){
     SQLiteDatabase db= getReadableDatabase();
        ArrayList<ModelContact> arraylist=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ Constants.TABLE_NAME;



        Cursor cursor =db.rawQuery(selectQuery,null,null);

        if(cursor.moveToFirst())
        {
            do{
                ModelContact modelContact=new ModelContact(
                        ""+ cursor.getInt(cursor.getColumnIndexOrThrow((Constants.C_ID))),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                       ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME))

                );
                arraylist.add(modelContact);

            }while (cursor.moveToNext());
        }
     db.close();
        cursor.close();
     return arraylist ;

 }

    // get data

    // search data in sql Database


    }


