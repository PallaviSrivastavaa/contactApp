package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddEditContact extends AppCompatActivity {

    private EditText nameEt, phoneEt, emailEt;
    private LinearLayout add;
    String name, phone, email;
    Button back;
    private String id,  addedTime, updatedTime;
    private Boolean isEditMode;

    private int f;
    //database helper
    private DbHelper dbHelper;


    // string array of permission

    private String[] storagePermission;
    private Button fab;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);
        dbHelper = new DbHelper(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditContact.this, MainActivity.class);
                startActivity(intent);
            }
        });


        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        phoneEt = findViewById(R.id.phoneEt);
        add=findViewById(R.id.add);
        Intent intent = getIntent();

        isEditMode = intent.getBooleanExtra("isEditMode", false);


       if (isEditMode) {


           //get the other value from intent
           id = intent.getStringExtra("ID");

           name = intent.getStringExtra("NAME");
           phone = intent.getStringExtra("PHONE");
           email = intent.getStringExtra("EMAIL");

           addedTime = intent.getStringExtra("ADDED-TIME");
           updatedTime = intent.getStringExtra("DATETIME");


           //set value in editText field
           nameEt.setText(name);
           phoneEt.setText(phone);
           emailEt.setText(email);
       }



            fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveData();
                    if(f==1) {
                        Intent intent = new Intent(AddEditContact.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            });

        //}

    }
        //check edit or add mode to save data in sql




    private void saveData() {
        f = 0;
        // Take user-given data in variables
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString();

        // Get current time to save as added time
        String timeStamp = "" + System.currentTimeMillis();

        // Check field data
        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            f = 1;

            if (!isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate phone number format using regex
            if (!isValidPhone(phone)) {
                Toast.makeText(getApplicationContext(), "Invalid phone number format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditMode) {
                // Edit mode
                dbHelper.updateContact(
                        name,
                        name,
                        phone,
                        email
                );
                Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                long newId = dbHelper.insertContact(
                        name,
                        phone,
                        email,
                        timeStamp,
                        timeStamp
                );

                // To check if data is inserted successfully, show a toast message
                Toast.makeText(getApplicationContext(), "Inserted Successfully!", Toast.LENGTH_SHORT).show();
            }

            // Return to the MainActivity
            Intent intent = new Intent(AddEditContact.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Fill the fields!", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{10}$"; // Assuming 10-digit phone number format
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}



