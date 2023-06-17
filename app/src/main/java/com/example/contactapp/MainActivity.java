package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private contactAdapter contactAdapter;
    private FloatingActionButton fab;
    private RecyclerView contactRv;
    private ImageView contactDelete,deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DbHelper(this);


        //initialization
        contactRv=findViewById(R.id.contactRv);
        contactRv.setHasFixedSize(true);
        deleteAll=findViewById(R.id.deleteAll);

        fab=findViewById(R.id.fab);//
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddEditContact.class);
                startActivity(intent);
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteAllContact();
                onResume();
            }
        });
        loadData();
    }

    private void loadData() {
        contactAdapter = new contactAdapter(this,dbHelper.getAllDta());
        contactRv.setAdapter(contactAdapter);
        for (int i = 0; i < contactRv.getChildCount(); i++) {
            View view = contactRv.getChildAt(i);
            view.setScaleX(0f);
            view.setScaleY(0f);
            view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .setStartDelay(i * 100)
                    .start();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        loadData();//refresh data...the Activity class provides a core set of six callbacks: onCreate(), onStart(), onResume(), onPause(), onStop(), and onDestroy()
    }

}
//add dependencies
//add colors