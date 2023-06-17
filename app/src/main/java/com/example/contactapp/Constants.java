package com.example.contactapp;

public class Constants {
    public static final String DATABASE_NAME="CONTACT_DB";
    public static final int DTABASE_VERSION=1;
    public static final String TABLE_NAME="CONTACT_TABLE";
    public static final String C_ID="ID";
    public static final String C_NAME="NAME";
    public static final String C_PHONE="PHONE";

    public static final String C_EMAIL="EMAIL";
    public static final String C_ADDED_TIME="ADDED_TIME";
    public static final String  C_UPDATED_TIME="UPDATED_TIME";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

            + C_NAME + " TEXT, "
            + C_PHONE + " TEXT, "
            + C_EMAIL + " TEXT, "

            + C_ADDED_TIME + " TEXT, "
            + C_UPDATED_TIME + " TEXT"
            + " );";
}
