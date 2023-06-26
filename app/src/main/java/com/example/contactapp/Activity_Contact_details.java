package com.example.contactapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class Activity_Contact_details extends AppCompatActivity {
    private TextView phoneTv,nameTv,emailTv,addedTimeTv,updatedTimeTv;
    private GifImageView profileIv;
    private Button back2,contactDelete;
    private Context context;
    private String id;

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        dbHelper = new DbHelper(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");

        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);

        emailTv = findViewById(R.id.emailTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);
        back2=findViewById(R.id.back2);
        profileIv = findViewById(R.id.profileIv);
        Glide.with(this).load(R.drawable.frog2).into(profileIv);


        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Contact_details.this, MainActivity.class);
                startActivity(intent);
            }
        });




        loadDataById();
    }

    private void loadDataById() {
        //query for find data by id
        //String selectQuery="SELECT *FROM" +Constants.TABLE_NAME+"WHERE"+Constants.C_ID+" =\""+ id+"\"";
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " = '" + id + "'";

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);//Cursor object that points to the result set.
        if(cursor.moveToFirst())//The code checks if the cursor has any rows by calling moveToFirst(). If it returns true, it means there is at least one matching row.
             {
            do{//inside the do-while loop, the values of each column are retrieved from the cursor using the getColumnIndexOrThrow() method.
                // The column names are obtained from Constants class constants

                String name=  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String phone=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String addTime=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                String updateTime =""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = ""+ DateFormat.format("dd/MM/yy hh:mm:aa", calendar.getTime());

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = ""+ DateFormat.format("dd/MM/yy hh:mm:aa", calendar.getTime());

                //set data
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                addedTimeTv.setText(timeAdd);
                updatedTimeTv.setText(timeUpdate);



           }while (cursor.moveToNext());
        }
        db.close();

    }
}